/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.dugex;

import db.model.Job;
import db.model.Log;
import db.model.Subsurface;
import db.model.Volume;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.LogServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.job.job0.JobType0Model;
import fend.volume.volume0.Volume0;
import java.util.List;
import db.services.LogService;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import fend.volume.volume1.Volume1;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.openide.util.Exceptions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DugLogManager {
    private JobType0Model job;
    private VolumeService volumeService=new VolumeServiceImpl();
    private LogService logsService=new LogServiceImpl();
    private SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();
    private DugioScripts dugioScripts=new DugioScripts();
    private JobService jobService=new JobServiceImpl();
    
    public DugLogManager(JobType0Model job) {
        this.job = job;
        Job dbJob=jobService.getJob(this.job.getId());
        
        List<Volume0> vols=job.getVolumes();
        for(Volume0 vol:vols){
            Volume dbVol=volumeService.getVolume(vol.getId());
            List<Log> existingLogsInDb=logsService.getLogsFor(dbVol);
            List<FileWrapper> filesExistingInDB=new ArrayList<>();
            for(Log log:existingLogsInDb){
                File f=new File(log.getLogpath());
                FileWrapper fw=new FileWrapper();
                fw.fwrap=f;
                filesExistingInDB.add(fw);
            }
            
            List<FileWrapper> filesToCommit=new ArrayList<>();
            File[] allFilesOnDisk=vol.getLogFolder().listFiles();
            
            for(File f:allFilesOnDisk){
                FileWrapper fw=new FileWrapper();
                fw.fwrap=f;
                if(!filesExistingInDB.contains(fw)) filesToCommit.add(fw);       // files that aren't present in the database 
            }
            
            System.out.println("middleware.dugex.LogManager.<init>(): Listing the files that are to be considered for commit");
            for(FileWrapper fw:filesToCommit){
                System.out.println("file: "+fw.fwrap.getName());
            }
            
            
            /**
             * For the files to commit, extract subsurface,insight and timestamp
             */
            List<Subsurface> subsurfacesInCurrentFolder=new ArrayList<>();
            List<LogInformation> listWithLogInformation=extractInformation(filesToCommit);
            for(LogInformation li:listWithLogInformation){
                Log log=new Log();
                log.setJob(dbJob);
                log.setVolume(dbVol);
                log.setSubsurface(li.linename);
                subsurfacesInCurrentFolder.add(li.linename);
                log.setLogpath(li.log.getAbsolutePath());
                log.setInsightVersion(li.insightVersion);
                log.setTimestamp(li.timestamp);
                logsService.createLogs(log);
            }
            
            System.out.println("middleware.dugex.DugLogManager.<init>(): updating versions");
            
            
            for(Subsurface sub:subsurfacesInCurrentFolder){
                
            List<Log> listOfdbLogsForSubInJob=logsService.getLogsByTimeFor(dbJob,sub);                   //ascending order by time. element zero is version 0;
            for(int i=0;i<listOfdbLogsForSubInJob.size();i++){
                //System.out.println("middleware.dugex.DugLogManager.<init>(): version "+i+" time: "+listOfdbLogsForSubInJob.get(i).getTimestamp()+" file: "+listOfdbLogsForSubInJob.get(i).getLogpath());
                Log ll=listOfdbLogsForSubInJob.get(i);
                Long ver=Long.valueOf(i);
                ll.setVersion(ver);
                logsService.updateLogs(ll.getIdLogs(), ll);
            
            
            }
            
                
            }
            
            System.out.println("middleware.dugex.DugLogManager.<init>(): updating workflows...");
            WorkflowManager workflowManager=new WorkflowManager(dbVol);
            System.out.println("middleware.dugex.DugLogManager.<init>(): updating run status...");
            LogStatusManager logStatusManager=new LogStatusManager(vol);
            
        }
    }

    private List<LogInformation> extractInformation(List<FileWrapper> filesToCommit) {
        List<FileWrapper> listOfPendingFiles=new ArrayList<>();
        List<LogInformation> logInformation=new ArrayList<>();
        
        for(FileWrapper fw:filesToCommit){
            try {
                
                // if files are still running, skip those files,start a new thread , sleep and create a new instance of DugLogManager
                
                
                Process process=new ProcessBuilder(dugioScripts.getSubsurfaceInsightVersionForLog().getAbsolutePath(),fw.fwrap.getAbsolutePath()).start();
                InputStream is = process.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br=new BufferedReader(isr);
                
                String value;
                while((value=br.readLine())!=null){
                    //System.out.println("middleware.dugex.LogManager.extractInformation(): value: for file: "+fw.fwrap.getName()+"  :  "+value);    //value= "lineName=<><space>Insight=<>"
                    String linename=value.substring(9,value.indexOf(" "));
                    String insight=value.substring(value.indexOf(" ")+9);
                    //System.out.println("middleware.dugex.LogManager.extractInformation(): linename= "+linename+" Insight: "+insight);
                    
                    LogInformation li=new LogInformation();
                    li.log=fw.fwrap;
                    li.linename=subsurfaceService.getSubsurfaceObjBysubsurfacename(linename);
                    li.insightVersion=insight;
                    li.timestamp=hackTimeStamp(fw.fwrap);
                    logInformation.add(li);
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
            
        }
        return logInformation;
    }

    
    /***
     * There HAS TO BE A BETTER WAY of doing this that I am not aware of!
     * function to convert File time to a yyyyMMddHHmmss format
     */
    private String hackTimeStamp(File fwrap) {
        BasicFileAttributes attr=null;
           try {
              attr=Files.readAttributes(Paths.get(fwrap.getAbsolutePath()),BasicFileAttributes.class);
           } catch (IOException ex) {
               Logger.getLogger(Volume1.class.getName()).log(Level.SEVERE, null, ex);
           }
                    
                    DateTimeFormatter formatter=DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    DateTime dt=formatter.parseDateTime(attr.creationTime().toString());
                    DateTimeFormatter opformat=new DateTimeFormatterBuilder()
                      .appendYear(4, 4)
                      .appendMonthOfYear(2)
                      .appendDayOfMonth(2)
                      .appendHourOfDay(2)
                      .appendMinuteOfHour(2)
                      .appendSecondOfMinute(2)
                      .toFormatter();
                    
                    
                    return opformat.print(dt);
    }

    

    
    private class FileWrapper{
        File fwrap;

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 97 * hash + Objects.hashCode(this.fwrap.getAbsolutePath());
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final FileWrapper other = (FileWrapper) obj;
            if (!Objects.equals(this.fwrap.getAbsolutePath(), other.fwrap.getAbsolutePath())) {
                return false;
            }
            return true;
        }
        
    }
    private class LogInformation{
        File log;
        String insightVersion;
        Subsurface linename;
        String timestamp;
              
    }
}
