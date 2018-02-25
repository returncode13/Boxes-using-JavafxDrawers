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
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
//import org.openide.util.Exceptions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DugLogManager {
    private String[] exclusionNames={"dunav.log","runHistory.txt"};                         //files not to be queried
    private JobType0Model job;
    private Job dbJob;
    private VolumeService volumeService=new VolumeServiceImpl();
    private LogService logsService=new LogServiceImpl();
    private SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();
    private DugioScripts dugioScripts=new DugioScripts();
    private JobService jobService=new JobServiceImpl();
    private List<FileWrapper> exclusionList=new ArrayList<>();
    private ExecutorService exec;
    
    public DugLogManager(JobType0Model job) {
        this.job = job;
        this.dbJob=jobService.getJob(this.job.getId());
       // exec=Executors.newCachedThreadPool(threadFactory)
       
        
        List<Volume0> vols=job.getVolumes();
        for(Volume0 vol:vols){
            Volume dbVol=volumeService.getVolume(vol.getId());
           // List<Log> existingLogsInDb=logsService.getLogsFor(dbVol);
            String latestTimeForVolumeInDatabase=logsService.getLatestLogTimeFor(dbVol);
            BigInteger latestTimeForVolumeInt=new BigInteger(latestTimeForVolumeInDatabase);
            System.out.println("middleware.dugex.DugLogManager.<init>(): latest Time for Volume: "+dbVol.getId()+" time: "+latestTimeForVolumeInt);
            /*List<FileWrapper> filesExistingInDB=new ArrayList<>();
            for(Log log:existingLogsInDb){
            File f=new File(log.getLogpath());
            FileWrapper fw=new FileWrapper();
            fw.fwrap=f;
            filesExistingInDB.add(fw);
            }*/
            
            List<FileWrapper> filesToCommit=new ArrayList<>();
            File[] allFilesOnDisk=vol.getLogFolder().listFiles();
            exclusionList.clear();
             //build the exclusion List
                for(String s: exclusionNames){
                    FileWrapper efw=new FileWrapper();
                    efw.fwrap=new File(vol.getLogFolder().getAbsoluteFile()+"/"+s);
                    System.out.println("middleware.dugex.DugLogManager.<init>(): Adding "+efw.fwrap.getAbsolutePath()+" to the exclusion List");
                    exclusionList.add(efw);
                }
            
            
            
            for(File f:allFilesOnDisk){
                FileWrapper fw=new FileWrapper();
                fw.fwrap=f;
                String time=hackTimeStamp(f);
                BigInteger timeofFile=new BigInteger(time);
                if(latestTimeForVolumeInt.compareTo(timeofFile)<0 && !exclusionList.contains(fw) ) {
                    filesToCommit.add(fw);
                }
               // if(!filesExistingInDB.contains(fw) && !exclusionList.contains(fw)) filesToCommit.add(fw);       // files that aren't present in the database 
            }
            
            System.out.println("middleware.dugex.LogManager.<init>(): Listing the files that are to be considered for commit");
            for(FileWrapper fw:filesToCommit){
                System.out.println("file: "+fw.fwrap.getName());
            }
            
            
            /**
             * For the files to commit, extract subsurface,insight and timestamp
             */
            List<Subsurface> subsurfacesInCurrentFolder=new ArrayList<>();
            List<LogInformation> listWithLogInformation=extractInformation(dbVol,filesToCommit,vol.getType());
            System.out.println("middleware.dugex.DugLogManager.<init>(): creating log entries");
            List<Callable<String>> tasks=new ArrayList<>();
            exec=Executors.newFixedThreadPool(5);
            
            
            for(LogInformation li:listWithLogInformation){
                
                 Callable<String> task= new Callable<String>(){
                     @Override
                     public String call() throws Exception {
                         Log log=new Log();
                        log.setJob(dbJob);
                        log.setVolume(dbVol);
                        log.setSubsurface(li.linename);
                        subsurfacesInCurrentFolder.add(li.linename);
                        log.setLogpath(li.log.getAbsolutePath());
                        log.setInsightVersion(li.insightVersion);
                        log.setVersion(li.version);
                        log.setTimestamp(li.timestamp);
                        logsService.createLogs(log);
                        
                        return "Created log entry for "+li.linename;
                     }
                 };
                System.out.println("middleware.dugex.DugLogManager.extractInformation(): Adding a task for "+li.linename);
                tasks.add(task);
                    
            }
             try {
                List<Future<String>> futures=exec.invokeAll(tasks);
                for(Future<String> future:futures){
                    System.out.println("future.get: "+future.get());
                }
                exec.shutdown();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            System.out.println("middleware.dugex.DugLogManager.<init>(): updating versions");
            
            
            /*for(Subsurface sub:subsurfacesInCurrentFolder){
            
            List<Log> listOfdbLogsForSubInJob=logsService.getLogsByTimeFor(dbJob,sub);                   //ascending order by time. element zero is version 0;
            for(int i=0;i<listOfdbLogsForSubInJob.size();i++){
            System.out.println("middleware.dugex.DugLogManager.<init>(): version "+i+" time: "+listOfdbLogsForSubInJob.get(i).getTimestamp()+" file: "+listOfdbLogsForSubInJob.get(i).getLogpath());
            Log ll=listOfdbLogsForSubInJob.get(i);
            Long ver=Long.valueOf(i);
            ll.setVersion(ver);
            logsService.updateLogs(ll.getIdLogs(), ll);
            
            
            }
            
            
            }*/
            
            System.out.println("middleware.dugex.DugLogManager.<init>(): updating workflows...");
            WorkflowManager workflowManager=new WorkflowManager(dbVol);
            System.out.println("middleware.dugex.DugLogManager.<init>(): updating run status...");
            LogStatusManager logStatusManager=new LogStatusManager(vol);
            
        }
    }

    private List<LogInformation> extractInformation(Volume dbVol,List<FileWrapper> filesToCommit,Long volumeType) {
        List<FileWrapper> listOfPendingFiles=new ArrayList<>();
        final List<LogInformation> logInformation=new ArrayList<>();
        List<Callable<String>> tasks=new ArrayList<>();
        exec=Executors.newFixedThreadPool(5);
        
        
        
         if(volumeType.equals(JobType0Model.PROCESS_2D)){
             
             Map<String,List<LogInformation>> mapOfLogs=new HashMap<>();     //map of subsurfaces and their logs
             Set<String> allSubs=new HashSet<>();                //list of all logs 
             
            for(FileWrapper fw:filesToCommit){
             
            
            
                    Callable<String> task= new Callable<String>(){
                        @Override
                        public String call() throws Exception {
                            
                    
                    // if files are still running, skip those files,start a new thread , sleep and create a new instance of DugLogManager <<TO DO
                    
                    
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
                            li.volume=dbVol;
                            li.log=fw.fwrap;
                            li.linename=subsurfaceService.getSubsurfaceObjBysubsurfacename(linename);
                            li.insightVersion=insight;
                            li.timestamp=hackTimeStamp(fw.fwrap);
                           // logInformation.add(li);
                            allSubs.add(li.linename.getSubsurface());
                             if(!mapOfLogs.containsKey(li.linename.getSubsurface())){
                                                mapOfLogs.put(li.linename.getSubsurface(), new ArrayList<>());
                                                mapOfLogs.get(li.linename.getSubsurface()).add(li);
                                             }else{
                                                mapOfLogs.get(li.linename.getSubsurface()).add(li);
                                            }
                        }
                        return "Finished for "+fw.fwrap.getName();
                        }
                    };
                  
               
                     System.out.println("middleware.dugex.DugLogManager.extractInformation(): Adding a task for "+fw.fwrap.getName());
                    tasks.add(task);
            }
            
            
            try {
                List<Future<String>> futures=exec.invokeAll(tasks);
                for(Future<String> future:futures){
                    System.out.println("future.get: "+future.get());
                }
                exec.shutdown();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                for(String subname:allSubs){
                                    List<LogInformation> listOfLogsForSub=mapOfLogs.get(subname);
                                    Collections.sort(listOfLogsForSub);
                                    
                                        for(int ver=0;ver<listOfLogsForSub.size();ver++){
                                            listOfLogsForSub.get(ver).version=Long.valueOf(ver);
                                        }
                                        
                                        logInformation.addAll(listOfLogsForSub);
                }
          
            
        }
         
         
         
         
         
         
         
         
        
        if(volumeType.equals(JobType0Model.SEGD_LOAD)){
            
            for(FileWrapper fw:filesToCommit){
                
                Callable<String> task= new Callable<String>(){
                    @Override
                    public String call() throws Exception {
                         //Assume that all logs are completed. Need to code work for logs that are still building    ..Use the checkIfSegDLogIsDone(File f) function
                                            List<LogInformation> modifiedList=getModifiedContents(dbVol,fw.fwrap); 
                                            getInsightVersionsFromLog(fw.fwrap,modifiedList);
                    
                                            for (LogInformation li : modifiedList) {
                                                logInformation.add(li);
                                            }
                                            return "Finished for "+fw.fwrap.getName();
                    }
                    
                };
                    
                   System.out.println("middleware.dugex.DugLogManager.extractInformation(): Adding a task for "+fw.fwrap.getName());
                tasks.add(task);
            }
            try {
                List<Future<String>> futures=exec.invokeAll(tasks);
                for(Future<String> future:futures){
                    System.out.println("future.get: "+future.get());
                }
                exec.shutdown();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
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

     /*
    check if the gun_cable.log files are done updating
    */
    private boolean checkIfSegDLogIsDone(File f) {
                            DugioScripts ds=new DugioScripts();
                            Process process=null;
                                try {
                                    process = new ProcessBuilder(ds.getSegdLoadCheckIfGCLogsFinished().getAbsolutePath(),f.getAbsolutePath()).start();
                                } catch (IOException ex) {
                                   ex.printStackTrace();
                                }
                            InputStream is = process.getInputStream();
                            InputStreamReader isr=new InputStreamReader(is);
                            BufferedReader br=new BufferedReader(isr);
                            String line;
                            boolean isdone=false;
                                try {
                                    while((line=br.readLine())!=null){    //script will return either 0(success) or 1 (fail)
                                        int done=Integer.valueOf(line);
                                        System.out.println(".checkIfSegDLogIsDone(): linevalue is: "+line);
                                            if(done==0){
                                                isdone=true;
                                            }else{
                                                isdone=false;
                                            }
                                    }
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            return isdone;
                        }

    
      /*
    check contents of file not present in db
    Used for SEGD_LOAD logs
    Type 2 Volumes
    */
    
    private List<LogInformation> getModifiedContents(Volume dbVol,File gcfile) {
        
        Map<String,List<LogInformation>> mapOfLogs=new HashMap<>();
        
                            DugioScripts ds=new DugioScripts();
                            Process process=null;
                                try {
                                    process = new ProcessBuilder(ds.getSegdLoadLinenameTimeFromGCLogs().getAbsolutePath(),gcfile.getAbsolutePath()).start();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            InputStream is = process.getInputStream();
                            InputStreamReader isr=new InputStreamReader(is);
                            BufferedReader br=new BufferedReader(isr);
                            String line;
                            List<LogInformation> allResults=new ArrayList<>();
                            System.out.println(".getModifiedContents():Script results for file: "+gcfile.getAbsolutePath());
                                try {
                                    while((line=br.readLine())!=null){          //line will be of form  01-Nov-2017T10:50:41 0327-1P11234A082_cable2_gun2
                                        String timeStamp=line.substring(0,line.indexOf(" "));
                                        String linename=line.substring(line.indexOf(" ")+1,line.length());
                                        //String seq=line.substring(line.indexOf("_")-3,line.indexOf("_"));
                                        //System.out.println("middleware.dugex.DugLogManager.getModifiedContents(): timeStamp: "+timeStamp+" linename: "+linename);
                                     //   /* String dateTime=timeStamp.substring(0,timeStamp.indexOf("T"));
                                    //    String timeOfDay=timeStamp.substring(timeStamp.indexOf("T")+1,timeStamp.length());*/
                                        
                               
                                        
                                        DateTimeFormatter formatter=DateTimeFormat.forPattern("dd-MMM-yyyy'T'HH:mm:ss");
                                        DateTime dt=formatter.parseDateTime(timeStamp);
                                        DateTimeFormatter opformat=new DateTimeFormatterBuilder()
                                                            .appendYear(4, 4)
                                                            .appendMonthOfYear(2)
                                                            .appendDayOfMonth(2)
                                                            .appendHourOfDay(2)
                                                            .appendMinuteOfHour(2)
                                                            .appendSecondOfMinute(2)
                                                            .toFormatter();
                                        
                                        
                                        
                                        
                                        
                                        
                                        String dateTime=opformat.print(dt);
                                        
                                        LogInformation lw=new LogInformation();
                                        lw.log=gcfile;
                                        lw.linename=subsurfaceService.getSubsurfaceObjBysubsurfacename(linename);
                                        lw.volume=dbVol;
                                       // lw.timestamp=timeStamp;
                                        lw.timestamp=dateTime;
                                        allResults.add(lw);
                                        if(!mapOfLogs.containsKey(lw.linename.getSubsurface())){
                                                mapOfLogs.put(lw.linename.getSubsurface(), new ArrayList<>());
                                                mapOfLogs.get(lw.linename.getSubsurface()).add(lw);
                                             }else{
                                                mapOfLogs.get(lw.linename.getSubsurface()).add(lw);
                                            }
                                    }   
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                
                                
                                List<LogInformation> modifiedContents=new ArrayList<>();
                                for(LogInformation l:allResults){
                                try {
                                    List<LogInformation> listOfLogsForSub=mapOfLogs.get(l.linename.getSubsurface());
                                    Collections.sort(listOfLogsForSub);
                                    
                                        for(int ver=0;ver<listOfLogsForSub.size();ver++){
                                            listOfLogsForSub.get(ver).version=Long.valueOf(ver);
                                        }
                                    
                                    
                                        /*Log log=logsService.getLogsFor(l.volume, l.linename, l.timestamp, l.log.getAbsolutePath());
                                        if(log==null){              //if the database doesn't contain any log for the above params, then add to the list of modified.
                                        modifiedContents.add(l);
                                        
                                        
                                        }*/             // no files are been considered which are created after the maxTimestamp in the database
                                        
                                        modifiedContents.add(l);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                }
                                
                                
                                
                               return modifiedContents;
                            
                        }
    
    
     
    /*
    get insight version and link it to the subline
    */
    
   private void getInsightVersionsFromLog(File gcfile,List<LogInformation> modifiedList) {
      
                           // DugioScripts ds=new DugioScripts();
                            System.out.println("middleware.dugex.DugLogManager.getInsightVersionsFromLog(): gcfile: "+gcfile.getAbsolutePath());
                            System.out.println("middleware.dugex.DugLogManager.getInsightVersionsFromLog(): script  "+dugioScripts.getSegdLoadSaillineInsightFromGCLogs().getAbsolutePath() );
                            Process process=null;
                                try {
                                    process = new ProcessBuilder(dugioScripts.getSegdLoadSaillineInsightFromGCLogs().getAbsolutePath(),gcfile.getAbsolutePath()).start();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            InputStream is = process.getInputStream();
                            InputStreamReader isr=new InputStreamReader(is);
                            BufferedReader br=new BufferedReader(isr);
                            String line;
                            Map<String,String> sailInsMap=new HashMap<>();  //sailline insight Map
                                try {
                                    
                                    /***
                                     * The script returns results of the format:
                                     *  --
                                        Insight 5.0 (703280)
                                        Info    : [time = 16 Sep 2017 11:55:25] Started geom2d for line AMN-7715J13169
                                        --
                                        Insight 5.0 (703280)
                                        Info    : [time = 17 Sep 2017 07:21:03] Started geom2d for line AMN-7679J13170
                                        --

                                     **/
                                    
                                    
                                    while((line=br.readLine())!=null){  
                                        /* String insVersion=line.substring(0,line.indexOf(" "));
                                        String sailline=line.substring(line.indexOf(" ")+1,line.length());
                                        System.out.println(".getInsightVersionsFromLog(): sailline: "+sailline+" insight: "+insVersion);
                                        sailInsMap.put(sailline, insVersion);*/
                                        
                                         String line1=line;
                                        String insightCheck=line1.substring(0,line1.indexOf(" "));
                                        //System.out.println(".call() insightCheck: "+insightCheck);
                                        if(insightCheck.equalsIgnoreCase("insight")){    //check for the line containing "insight"
                                            String version = line1.substring(line1.indexOf(" ")+1,line.length());   //rest of the line is the version
                                            String line3=br.readLine(); //next Line
                                            if(line3.contains("Started geom2d for line")){    //this line will contain the linename
                                                String sailine=line3.substring(line3.lastIndexOf(" "),line3.length());
                                                System.out.println(sailine+" -- "+version);
                                                br.readLine();                               // ignore "--"
                                                
                                                sailInsMap.put(sailine, version);
                                            }
                                        }
                                        
                                        
                                        
                                        
                                    }   
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                
                            for (Map.Entry<String, String> entry : sailInsMap.entrySet()) {
                                String saill = entry.getKey();
                                String ins = entry.getValue();
                                
                                for(LogInformation l:modifiedList){
                                    if(l.linename.getSubsurface().contains(saill)){
                                        System.out.println(".getInsightVersionsFromLog(): adding insight version"+ins+" to "+l.linename+" which belongs to sailline: "+saill);
                                        l.insightVersion=ins;
                                    }
                                }
                                
                            }     
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
    private class LogInformation implements Comparable<LogInformation>{
        File log;
        String insightVersion;
        Subsurface linename;
        String timestamp;
        Volume volume;
        Long version;      

        @Override
        public int compareTo(LogInformation log2) {
            int later= this.timestamp.compareTo(log2.timestamp);            //later > 0 of this timestamp is greater (later) than log2.timestamp.  
                                                                            //later < 0 if this timestamp is lesser (earlier) than log2.timestamp.
                                                                            //later=0 if both are equal;
            return later;
        }
    }
}
