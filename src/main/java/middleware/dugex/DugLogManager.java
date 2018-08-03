/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.dugex;

import app.properties.AppProperties;
import db.model.Job;
import db.model.Log;
import db.model.Subsurface;
import db.model.Volume;
import db.model.Workflow;
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
import db.services.WorkflowService;
import db.services.WorkflowServiceImpl;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    //private JobService jobService=new JobServiceImpl();
    private List<FileWrapper> exclusionList=new ArrayList<>();
    private ExecutorService exec;
    private final WorkflowService workflowService=new WorkflowServiceImpl();
    private Set<Workflow> workflowsToBeCreated=new HashSet<>();
    private Map<Subsurface,Log> latestLogForSub=new HashMap<>();
    private Set<LogInformation> latestLogSet=new HashSet<>();
    
    public DugLogManager(JobType0Model job) {
        this.job = job;
        System.out.println("middleware.dugex.DugLogManager.<init>()..getting dbjob: "+job.getId());
       // this.dbJob=jobService.getJob(this.job.getId());
       this.dbJob=this.job.getDatabaseJob();
    }
    
    public void work(){
        System.out.println("middleware.dugex.DugLogManager.<init>(): got job .now querying for volumes ");
        
        List<Volume0> vols=job.getVolumes();
        System.out.println("middleware.dugex.DugLogManager.<init>(): Number of Volumes present: "+vols.size());
        
        for(Volume0 vol:vols){
            System.out.println("middleware.dugex.DugLogManager.<init>()..fetching db vol: +"+vol.getId());
            Volume dbVol=volumeService.getVolume(vol.getId());
           // List<Log> existingLogsInDb=logsService.getLogsFor(dbVol);
            System.out.println("middleware.dugex.DugLogManager.<init>(): fetching the latest time for "+dbVol.getId());
            String latestTimeForVolumeInDatabase=logsService.getLatestLogTimeFor(dbVol);
            BigInteger latestTimeForVolumeInt=new BigInteger(latestTimeForVolumeInDatabase);
            System.out.println("middleware.dugex.DugLogManager.<init>(): latest Time for Volume: "+dbVol.getId()+" time: "+latestTimeForVolumeInt);
          
            
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
                System.out.println("middleware.dugex.DugLogManager.<init>(): for file "+fw.fwrap.getName()+" time of file : "+timeofFile+" latestTimeforVol: "+latestTimeForVolumeInt+" compares: "+latestTimeForVolumeInt.compareTo(timeofFile));
                if(latestTimeForVolumeInt.compareTo(timeofFile)<0 && !exclusionList.contains(fw) ) {
                    filesToCommit.add(fw);
                }
               // if(!filesExistingInDB.contains(fw) && !exclusionList.contains(fw)) filesToCommit.add(fw);       // files that aren't present in the database 
            }
            
            System.out.println("middleware.dugex.LogManager.<init>(): Listing the files that are to be considered for commit");
            totalNumberOfLogs=filesToCommit.size();
            for(FileWrapper fw:filesToCommit){
                System.out.println("file: "+fw.fwrap.getName());
            }
            
            
            /**
             * For the files to commit, extract subsurface,insight and timestamp
             */
            List<Subsurface> subsurfacesInCurrentFolder=new ArrayList<>();
            Map<String,List<LogInformation>> mapofLogs=null;
            int recursionCounter=AppProperties.LOG_RECURSION_COUNTER;
            workflowsToBeCreated.clear();
            Set<LogInformation> listWithLogInformation=extractInformation(dbVol,filesToCommit,vol.getType(),mapofLogs,recursionCounter);
            
            System.out.println("middleware.dugex.DugLogManager.<init>(): Creating "+workflowsToBeCreated.size()+" workflows");
            
            exec=Executors.newFixedThreadPool(5);
            List<Callable<String>> workflowTasks=new ArrayList<>();
                for(Workflow w:workflowsToBeCreated){
                    Callable<String> wfTask= new Callable<String>(){
                        @Override
                        public String call() throws Exception {
                               workflowService.createWorkFlow(w);
                               message.set("creating workflows");
                               return "Created workflow: "+w.getId();
                        }
                    };
                    workflowTasks.add(wfTask);
                }
            
             try {
                List<Future<String>> futures=exec.invokeAll(workflowTasks);
                for(Future<String> future:futures){
                    System.out.println("future.get: "+future.get());
                }
                exec.shutdown();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            
            System.out.println("middleware.dugex.DugLogManager.<init>(): creating "+listWithLogInformation.size()+" log entries");
            List<Callable<String>> tasks=new ArrayList<>();
            exec=Executors.newFixedThreadPool(5);
            
            message.set("commiting logs");
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
                       // log.setVersion(li.version);
                        log.setTimestamp(li.timestamp);
                        log.setWorkflow(li.workflowHolder.workflow);
                        List<String> inputVolumes=li.inputVolumeNames;
                        String concat=new String();
                        for(String inputVol:inputVolumes){
                            concat+=inputVol+";";
                        }
                        log.setInputVolumeNames(concat);
                        logsService.createLogs(log);
                        ++commitedLogs;
                        progress.set((double)commitedLogs/totalNumberOfLogs);
                            if(latestLogSet.contains(li)){
                                System.out.println(".call(): for "+li.linename.getSubsurface()+" latestLog: "+log.getIdLogs()+" version: "+li.version);
                                latestLogForSub.put(li.linename, log);
                            }
                        
                        return "Created log entry for "+li.linename.getSubsurface();
                     }
                 };
                System.out.println("middleware.dugex.DugLogManager.<init>: Adding a task for "+li.linename.getSubsurface());
                tasks.add(task);
                    
            }
             try {
                List<Future<String>> futures=exec.invokeAll(tasks);
                for(Future<String> future:futures){
                    System.out.println("future.get: "+future.get());
                }
                job.setLatestLogForSubsurfaceMap(latestLogForSub);
                exec.shutdown();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
         
            
        }
    }

    private Set<LogInformation> extractInformation(Volume dbVol,List<FileWrapper> filesToCommit,Long volumeType,Map<String,List<LogInformation>> mapofLogs,int recursionCounter){
        List<FileWrapper> listOfPendingFiles=new ArrayList<>();
        final Set<LogInformation> logInformation=new HashSet<>();
        List<Callable<String>> tasks=new ArrayList<>();
        exec=Executors.newFixedThreadPool(5);
        
        message.set("extracting logs");
        
         if(volumeType.equals(Volume0.PROCESS_2D) ||volumeType.equals(Volume0.SEGY)){
             
             Map<String,List<LogInformation>> mapOfSubsurfaceLogs;
             if(mapofLogs==null) {
                 mapOfSubsurfaceLogs=new HashMap<>();     //map of subsurfaces and their logs
             }else{
                 mapOfSubsurfaceLogs=mapofLogs;
             }
             Set<String> allSubs=new HashSet<>();                //list of all logs 
            
            for(int ii=0;ii<filesToCommit.size();ii++){
          //  for(FileWrapper fw:filesToCommit){
                FileWrapper fw=filesToCommit.get(ii);
            
                final int iii=ii;
                    Callable<String> task= new Callable<String>(){
                        @Override
                        public String call() throws Exception {
                            final int fwc=iii+1;
                    
                    // if files are still running, skip those files,start a new thread , sleep and create a new instance of DugLogManager <<TO DO
                    
                    
                    Process process=new ProcessBuilder(dugioScripts.getSubsurfaceInsightVersionForLog().getAbsolutePath(),fw.fwrap.getAbsolutePath()).start();
                    InputStream is = process.getInputStream();
                    InputStreamReader isr=new InputStreamReader(is);
                    BufferedReader br=new BufferedReader(isr);
                    
                    String value;
                    int lenCharLinename="lineName=".length();
                    int lenCharInsight="Insight=".length();
                    String inputFile="Input_File=";
                     message.set("reading logs");
                    while((value=br.readLine())!=null){
                            
                            progress.set((double)fwc/filesToCommit.size());
                            System.out.println("middleware.dugex.LogManager.extractInformation(): value: for file: "+fw.fwrap.getName()+"  :  "+value+" file# "+fwc+" total "+filesToCommit.size()+" Progress: "+(double)fwc/filesToCommit.size());    //
                                                                                                                                                           //value = lineName=<><space>Insight=<><space>Input_File=file_1<space>Input_File=file_2<space>...Input_File=<file_n> 
                            String linename=value.substring(9,value.indexOf(" "));
                            int insightSearchBegin = lenCharLinename+linename.length()+1+lenCharInsight;
                            int indexOfFirstINPUT_FILE=value.indexOf(inputFile);
                            
                            String insight=new String();
                            if(indexOfFirstINPUT_FILE>0){
                                insight=value.substring(insightSearchBegin,indexOfFirstINPUT_FILE);
                            }else{
                                insight=value.substring(insightSearchBegin);
                            }
                                    
                            insight=insight.trim();
                            
                           
                            
                            //System.out.println("middleware.dugex.LogManager.extractInformation(): linename= "+linename+" Insight: "+insight);
                            
                            LogInformation li=new LogInformation();
                            li.volume=dbVol;
                            li.log=fw.fwrap;
                            try{
                                Subsurface sbb=subsurfaceService.getSubsurfaceObjBysubsurfacename(linename);
                                sbb.getSubsurface();
                                li.linename=sbb;
                                li.insightVersion=insight;
                                li.timestamp=hackTimeStamp(fw.fwrap);
                                if(indexOfFirstINPUT_FILE>0){
                                     String inputFiles=value.substring(indexOfFirstINPUT_FILE);
                                    String[] volumeNames=inputFiles.split(inputFile);
                                    li.inputVolumeNames=Arrays.asList(volumeNames);
                                }
                            String sub=li.linename.getSubsurface();
                           // logInformation.add(li);
                          
                            }
                            catch(NullPointerException npe){
                                System.out.println("middleware.dugex.DugLogManager.extractInformation(): COULD NOT ASSOCIATE A SUBSURFACE FOR LOG FILE: "+li.log.getAbsolutePath());
                                continue;
                            }
                            
                              allSubs.add(li.linename.getSubsurface());
                            System.out.println("middleware.dugex.DugLogManager.extractInformation():: looking for "+li.linename.getSubsurface()+" insight: "+li.insightVersion+" input volume(s): "+li.inputVolumeNames.toString());
                             if(!mapOfSubsurfaceLogs.containsKey(li.linename.getSubsurface())){
                                                mapOfSubsurfaceLogs.put(li.linename.getSubsurface(), new ArrayList<>());
                                                mapOfSubsurfaceLogs.get(li.linename.getSubsurface()).add(li);
                                             }else{
                                                mapOfSubsurfaceLogs.get(li.linename.getSubsurface()).add(li);
                                            }
                        }
                        return "Finished for "+fw.fwrap.getName();
                        }
                    };
                  
                    
               
                     System.out.println("middleware.dugex.DugLogManager.extractInformation(): Adding a task for "+fw.fwrap.getName());
                    tasks.add(task);
            }
            
             System.out.println("middleware.dugex.DugLogManager.extractInformation(): Waiting for the logs to finish. number of tasks : "+tasks.size());
            
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
                                    List<LogInformation> listOfLogsForSub=mapOfSubsurfaceLogs.get(subname);
                                    Collections.sort(listOfLogsForSub);
                                    
                                        for(int ver=0;ver<listOfLogsForSub.size();ver++){
                                            listOfLogsForSub.get(ver).version=Long.valueOf(ver);
                                        }
                                        int highest=listOfLogsForSub.size()-1;
                                        if(listOfLogsForSub.size()>=highest){
                                        System.out.println("middleware.dugex.DugLogManager.extractInformation(): Adding highest "+highest+" ==> "+listOfLogsForSub.get(highest).log.getName()+" as latest version "+listOfLogsForSub.get(highest).version+" for "+listOfLogsForSub.get(highest).linename.getSubsurface()+" : ");
                                        }
                                        latestLogSet.add(listOfLogsForSub.get(listOfLogsForSub.size()-1));         // the highest version in the sorted equals value (size-1) of the list
                                       
                                        logInformation.addAll(listOfLogsForSub);
                }
          
            try {
                System.out.println("middleware.dugex.DugLogManager.extractInformation(): getWorkFlowInformationFor2D..for logInformation.size() : "+logInformation.size()+" dbVol.name = "+dbVol.getNameVolume()+" files To Commit size : "+filesToCommit.size());
                message.set("fetching workflow info");
                progress.set(0);
                workflowsToBeCreated.addAll(getWorkFlowInformationFor2D(logInformation,dbVol));
            } catch (IOException ex) {
                Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
            }
         
             if(logInformation.size()!=filesToCommit.size()){
                 List<FileWrapper> filesPending=calculatePendingFiles(logInformation,filesToCommit);
                 message.set("recursing due to log mismatch");
                 progress.set(0);
                 recursionCounter=--recursionCounter;
                 if(recursionCounter>0){
                     int attemptNo=AppProperties.LOG_RECURSION_COUNTER-recursionCounter;
                     System.out.println("middleware.dugex.DugLogManager.extractInformation(): Mismatch of log count. logInformationList.size(" +logInformation.size()+")  "
                             + "while filesToCommit.size("+filesToCommit.size()+"). attempt # "+attemptNo+" Recursion will halt at the "+AppProperties.LOG_RECURSION_COUNTER+"th attempt");
                     Set<LogInformation> logI=extractInformation(dbVol, filesPending, volumeType, mapOfSubsurfaceLogs,recursionCounter);
                     //logInformation.clear();
                     logInformation.addAll(logI);
                 }
                 
             }
            
         }
         
         
         
         
       
         
         
         
         
         
        
        if(volumeType.equals(JobType0Model.SEGD_LOAD)){
            
          
                  for(int ii=0;ii<filesToCommit.size();ii++){
          //  for(FileWrapper fw:filesToCommit){
                FileWrapper fw=filesToCommit.get(ii);
            
                final int iii=ii;
                Callable<String> task= new Callable<String>(){
                    @Override
                    public String call() throws Exception {
                        final int fwc=iii+1;
                        progress.set((double)(fwc/filesToCommit.size()));
                         //Assume that all logs are completed. Need to code work for logs that are still building    ..Use the checkIfSegDLogIsDone(File f) function
                                            List<LogInformation> modifiedList=getModifiedContents(dbVol,fw.fwrap); 
                                            //getInsightVersionsFromLog(fw.fwrap,modifiedList);
                    
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
            
            System.out.println("middleware.dugex.DugLogManager.extractInformation(): Attaching Insight Versions to Saillines");
            getInsightVersionsFromLog(filesToCommit.get(0).fwrap, logInformation);                          //all gcfiles have the same information about insight and sailline.
            try {
                message.set("fetching workflow info");
                progress.set(0);
                workflowsToBeCreated=getWorkFlowforSegD(logInformation,dbVol);
            } catch (IOException ex) {
                Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
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
    check contents of file . returns a list of logs not present in db
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
                                        //latestLogForSub.put(l.linename,listOfLogsForSub.get(listOfLogsForSub.size()-1));
                                        int highest=listOfLogsForSub.size()-1;
                                        System.out.println("middleware.dugex.DugLogManager.getModifiedContents(): Adding highest "+highest+" ==> "+listOfLogsForSub.get(highest).log.getName()+" as latest version "+listOfLogsForSub.get(highest).version+" for "+listOfLogsForSub.get(highest).linename.getSubsurface()+" : ");
                                        latestLogSet.add(listOfLogsForSub.get(listOfLogsForSub.size()-1));         // the highest version in the sorted equals value (size-1) of the list
                                       
                                   
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
    
   private void getInsightVersionsFromLog(File gcfile,Set<LogInformation> listOfLogsToBeCommited) {
      
                           // DugioScripts ds=new DugioScripts();
                           /*  System.out.println("middleware.dugex.DugLogManager.getInsightVersionsFromLog(): gcfile: "+gcfile.getAbsolutePath());
                           System.out.println("middleware.dugex.DugLogManager.getInsightVersionsFromLog(): script  "+dugioScripts.getSegdLoadSaillineInsightFromGCLogs().getAbsolutePath() );
                           System.out.println("middleware.dugex.DugLogManager.getInsightVersionsFromLog(): size of modifiedList: "+listOfLogsToBeCommited.size());*/
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
                                                String sailine=line3.substring(line3.lastIndexOf(" ")+1,line3.length());
                                                System.out.println(sailine+" -- "+version);
                                                br.readLine();                               // ignore "--"
                                                
                                                sailInsMap.put(sailine, version);
                                            }
                                        }
                                        
                                        
                                        
                                        
                                    }   
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                //System.out.println("middleware.dugex.DugLogManager.getInsightVersionsFromLog(): size of the sailline Insight Map: "+sailInsMap.size());
                            for (Map.Entry<String, String> entry : sailInsMap.entrySet()) {
                                String saill = entry.getKey();
                                String ins = entry.getValue();
                               // System.out.println("middleware.dugex.DugLogManager.getInsightVersionsFromLog(): "+saill+" <---> "+ins+"\n attaching subsurfacenames to sailline");
                                for(LogInformation l:listOfLogsToBeCommited){
                               //     System.out.println("middleware.dugex.DugLogManager.getInsightVersionsFromLog(): "+l.linename.getSubsurface()+" --- ? ---***SPACE***"+saill+" contains: ? "+l.linename.getSubsurface().contains(saill));
                                    if(l.linename.getSubsurface().contains(saill.trim())){
                                       // System.out.println(".getInsightVersionsFromLog(): adding insight version"+ins+" to "+l.linename.getSubsurface()+" which belongs to sailline: "+saill);
                                        l.insightVersion=ins;
                                    }
                                }
                                
                            }     
                        }
    
    
   
   /***
    * Input parameter is the volume
    * the workflow information is stored under volume/logs/notes.txt
    * 
    **/
   private Set<Workflow> getWorkFlowforSegD(Set<LogInformation> logInformation,Volume volume) throws IOException, NoSuchAlgorithmException{
        Set<Workflow> workflows=new HashSet<>();
        MessageDigest md;
        message.set("extract workflow info");
                Process process=new ProcessBuilder(dugioScripts.getSegdLoadNotesTxtTimeWorkflowExtractor().getAbsolutePath(),volume.getPathOfVolume()).start();
                InputStream is = process.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br=new BufferedReader(isr);
                String value;
                String content=new String();
                
                while((value=br.readLine())!=null){
                    //System.out.println("middleware.dugex.DugLogManager.getWorkFlowforSegD(): "+value);
                    content+=value;
                    content+="\n";
                };
                
                md=MessageDigest.getInstance("MD5");
                byte[] bytesofContent=content.getBytes("UTF8");
                md.update(content.getBytes("UTF8"));
                byte[] digest=md.digest();
                StringBuffer sbuf=new StringBuffer();
                for(byte b:digest){
                    sbuf.append(String.format("%02x", b & 0xff));
                }
               String md5=new String(sbuf.toString());                                             //just the one workflow version for segd for a volume from the single source (notes.txt)
               
               Long highestVersion=workflowService.getHighestWorkFlowVersionFor(volume);
               //Long highestVersion=new Long(highestVersionOfWorkFlowInVolume);
               
                
               Workflow w=null;
                if((w=workflowService.getWorkFlowWith(md5, volume))==null){
                    w=new Workflow();
                    w.setMd5sum(md5);
                    w.setContents(content);
                    w.setWfversion(++highestVersion);
                    w.setVolume(volume);
                    workflows.add(w);
                }
                
                for (LogInformation log : logInformation) {
                    log.workflowHolder.workflow=w;
                }
                
                
        return workflows;
   }
   

   
   /**
    * Input parameters are LogInformation objects .i.e logs that will be committed to the database
    * Extract workflow for each and populate the loginformation.workflowholder.workflow variable of each
    * return the set of workflows that need to be committed to the database
    **/
    private Set<Workflow> getWorkFlowInformationFor2D(Set<LogInformation> logInformation,Volume volume) throws IOException, NoSuchAlgorithmException {
        System.out.println("middleware.dugex.DugLogManager.getWorkFlowInformationFor2D(): logInformation.size "+logInformation.size()+" dbVol.name: "+volume.getNameVolume());
         Map<String,List<LogInformation>> md5MapForWorkflow=new HashMap<>();   //map of md5 of workflows and their logs.
        Set<Workflow> workflows=new HashSet<>();
         int totall=logInformation.size();
         message.set("extract workflow info");
         MessageDigest md;
         int c=0;
         for (LogInformation log : logInformation) {
             c++;
             progress.set((double)c/totall);
                Process process=new ProcessBuilder(dugioScripts.getWorkflowExtractor().getAbsolutePath(),log.log.getAbsolutePath()).start();
                InputStream is = process.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br=new BufferedReader(isr);
                String value;
                String content=new String();
                while((value=br.readLine())!=null){
               //     System.out.println("middleware.dugex.DugLogManager.getWorkFlowInformationFor2D(): "+value);
                    content+=value;
                    content+="\n";
                };
                log.workflowHolder.contents=content;
                md=MessageDigest.getInstance("MD5");
                byte[] bytesofContent=log.workflowHolder.contents.getBytes("UTF8");
                md.update(log.workflowHolder.contents.getBytes("UTF8"));
                byte[] digest=md.digest();
                StringBuffer sbuf=new StringBuffer();
                for(byte b:digest){
                    sbuf.append(String.format("%02x", b & 0xff));
                }
                log.workflowHolder.md5=new String(sbuf.toString());
                
                if(!md5MapForWorkflow.containsKey(log.workflowHolder.md5)){
                    md5MapForWorkflow.put(log.workflowHolder.md5, new ArrayList<>());
                    md5MapForWorkflow.get(log.workflowHolder.md5).add(log);
                }else{
                    md5MapForWorkflow.get(log.workflowHolder.md5).add(log);
                }
                
                
        }
         
         Long highestVersion=workflowService.getHighestWorkFlowVersionFor(volume);
         //Long highestVersion=new Long(highestVersionOfWorkFlowInVolume);
         
         System.out.println("middleware.dugex.DugLogManager.getWorkFlowInformationFor2D(): size of the md5Map: "+md5MapForWorkflow.size());
         System.out.println("middleware.dugex.DugLogManager.getWorkFlowInformationFor2D(): highest Workflow Version present in volume: "+highestVersion);
         for (Map.Entry<String, List<LogInformation>> entry : md5MapForWorkflow.entrySet()) {
            String md5 = entry.getKey();
            List<LogInformation> logs = entry.getValue();
             
            Workflow w=null;
            if((w=workflowService.getWorkFlowWith(md5, volume))==null){
                w=new Workflow();
                w.setMd5sum(md5);
                w.setContents(logs.get(0).workflowHolder.contents);
                w.setWfversion(++highestVersion);
                w.setVolume(volume);
                workflows.add(w);
            }
                for (LogInformation log : logs) {
                    log.workflowHolder.workflow=w;                         //all these logs now have the same workflow
                }
                
                
           
            
        }
         return workflows;
    }

    /**
     * return  a list of filewrapper, files of which are present in filesToCommit but not in loginformation
     */
    
    private List<FileWrapper> calculatePendingFiles(Set<LogInformation> logInformation, List<FileWrapper> filesToCommit) {
        List<FileWrapper> filesPending=new ArrayList<>();
        Map<String,LogInformation> mL=new HashMap<>();
        Map<String,FileWrapper> mF=new HashMap<>();
        for(FileWrapper f:filesToCommit){
          mF.put(f.fwrap.getAbsolutePath(), f);
        }
        for(LogInformation l:logInformation){
            mL.put(l.log.getAbsolutePath(), l);
        }
        
        for (Map.Entry<String, FileWrapper> entry : mF.entrySet()) {
            String fileName = entry.getKey();
            FileWrapper value = entry.getValue();
            if(!mL.containsKey(fileName))filesPending.add(value);
            
        }
        return filesPending;
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
        WorkflowHolder workflowHolder=new WorkflowHolder();
        List<String> inputVolumeNames=new ArrayList<>();
        
        @Override
        public int compareTo(LogInformation log2) {
            int later= this.timestamp.compareTo(log2.timestamp);            //later > 0 of this timestamp is greater (later) than log2.timestamp.  
                                                                            //later < 0 if this timestamp is lesser (earlier) than log2.timestamp.
                                                                            //later=0 if both are equal;
            return later;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 89 * hash + Objects.hashCode(this.log);
            hash = 89 * hash + Objects.hashCode(this.linename);
            hash = 89 * hash + Objects.hashCode(this.timestamp);
            hash = 89 * hash + Objects.hashCode(this.volume);
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
            final LogInformation other = (LogInformation) obj;
            if (!Objects.equals(this.timestamp, other.timestamp)) {
                return false;
            }
            if (!Objects.equals(this.log, other.log)) {
                return false;
            }
            if (!Objects.equals(this.linename, other.linename)) {
                return false;
            }
            if (!Objects.equals(this.volume, other.volume)) {
                return false;
            }
            return true;
        }

        
       
       
       
        
        
        
    }
    private class WorkflowHolder{
        String md5;
        String contents;
        Workflow workflow;

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 37 * hash + Objects.hashCode(this.md5);
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
            final WorkflowHolder other = (WorkflowHolder) obj;
            if (!Objects.equals(this.md5, other.md5)) {
                return false;
            }
            return true;
        }
        
        
    }
    
    private StringProperty message=new SimpleStringProperty();
    private ReadOnlyDoubleWrapper progress=new ReadOnlyDoubleWrapper();
    
    public ReadOnlyDoubleProperty progressProperty(){
        return progress.getReadOnlyProperty();
    }  
    
    public final double getProgress(){
        return   progressProperty().get();
    }
    
    public StringProperty messageProperty(){
        return message;
    }
    
    
    private long totalNumberOfLogs=Long.MAX_VALUE;
    private long commitedLogs=0L;
    

    public long getTotalNumberOfLogs() {
        return totalNumberOfLogs;
    }

    public long getCommitedLogs() {
        return commitedLogs;
    }
    
    
    
    
}
