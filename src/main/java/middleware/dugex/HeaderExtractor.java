/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.dugex;

import app.properties.AppProperties;
import db.model.Header;
import db.model.Job;
import db.model.Log;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.SubsurfaceJob;
import db.model.Volume;
import db.services.HeaderService;
import db.services.HeaderServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.LogService;
import db.services.LogServiceImpl;
import db.services.SubsurfaceJobService;
import db.services.SubsurfaceJobServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.job.job0.JobType0Model;
import fend.job.job1.JobType1Model;
import fend.job.job2.JobType2Model;
import fend.volume.volume0.Volume0;
import fend.volume.volume1.Volume1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import middleware.sequences.SubsurfaceHeaders;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
//import org.openide.util.Exceptions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class HeaderExtractor {
    JobType0Model job;
    Job dbjob;
    SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();
    SubsurfaceJobService subsurfaceJobService=new SubsurfaceJobServiceImpl();
    HeaderService headerService=new HeaderServiceImpl();
    VolumeService volumeService=new VolumeServiceImpl();
    LogService logService=new LogServiceImpl();
    JobService jobService=new JobServiceImpl();
    private DugioScripts dugioscript=new DugioScripts();
    DugioMetaHeader dmh=new DugioMetaHeader();
    ExecutorService exec;
    double percentageOfProcessorsUsed=AppProperties.PERCENTAGE_OF_PROCESSORS_USED;
    List<SubsurfaceJob> subsurfaceJobs=new ArrayList<>();
    List<Header> headers=new ArrayList<>();
    List<HeaderHolder> headerHolderList=new ArrayList<>();        
            
    public HeaderExtractor(JobType0Model j) throws Exception{
        System.out.println("middleware.dugex.HeaderExtractor.<init>(): Entered ");
        job=j;
       // dbjob=jobService.getJob(job.getId());
        dbjob=job.getDatabaseJob();
        List<Volume0> volumes=job.getVolumes();
        Set<Header> setOfHeadersInJob=new HashSet<>();
        Set<Subsurface> setOfSubsurfacesInJob=new HashSet<>();
        Set<SubsurfaceJob> setOfSubsurfaceJobs=new HashSet<>();
        List<Callable<String>> callableTasks=new ArrayList<>();
        int processors=Runtime.getRuntime().availableProcessors();
        System.out.println("middleware.dugex.HeaderExtractor.<init>(): Number of available processors : "+processors);
      
    
        if(job.getType().equals(JobType0Model.PROCESS_2D)||job.getType().equals(JobType0Model.SEGD_LOAD)){
         //  subsurfaceJobs=new ArrayList<>();
         //  headers=new ArrayList<>();
           
           
          for(Volume0 vol:volumes){
              
              Volume dbvol=volumeService.getVolume(vol.getId());
              System.out.println("middleware.dugex.HeaderExtractor.<init>(): calling volume "+vol.getName().get()+" id: "+dbvol.getId());
              //Job dbjob=dbvol.getJob();
              String summaryTime=new DateTime(1986,6,6,00,00,00,DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
              List<SubsurfaceHeaders> subsInVol=vol.getSubsurfaces();     //these have the timestamp of the latest runs
              String latestCommitToHeadersTable=headerService.getLatestTimeStampFor(dbvol);   //get the latest(max) timestamp for this volume
              System.out.println("middleware.dugex.HeaderExtractor.<init>(): Latest time found "+latestCommitToHeadersTable);
              
              
              
              
              
              
              BigInteger latestTimeStampForVol=new BigInteger(latestCommitToHeadersTable);
              
              
               List<Callable<String>> tasks=new ArrayList<>();
               /*int procsUsed=(int) (Runtime.getRuntime().availableProcessors()*percentageOfProcessorsUsed);
               if(procsUsed <= 1){
               System.out.println("middleware.dugex.HeaderExtractor.<init>(): Not enough resources . PR-TCount: "+procsUsed);
               return;
               }*/
                exec=Executors.newFixedThreadPool(processorsUsed());
            
              
              
              
              for(SubsurfaceHeaders sub:subsInVol){
                  
                
                            System.out.println("middleware.dugex.HeaderExtractor.<init>(): subsurfacename:  from file: "+sub.getSubsurfaceName());

                            Callable<String> task= new Callable<String>(){
                                @Override
                                public String call() throws Exception {
                                   
                           
                            String latestTimestamp=sub.getTimeStamp();
                            BigInteger latestTimeStampForSub=new BigInteger(latestTimestamp);
                            System.out.println(".call(): is latestTimeStampForVol ("+latestTimeStampForVol+") < latestTimeStampInFile ("+latestTimeStampForSub+"):  "+latestTimeStampForVol.compareTo(latestTimeStampForSub));
                            //if latesttimestamp > maxTimeStamp for  vol in headers table.  Do this maxTime query once in the beginning
                            
                      
                                if(latestTimeStampForVol.compareTo(latestTimeStampForSub)<0){  //i.e. this sub was created after the latesttime present for any sub in that volume
                                    
                                    
                                    Subsurface dbsub=subsurfaceService.getSubsurfaceObjBysubsurfacename(sub.getSubsurfaceName());
                                    String updateTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);

                                    Sequence dbseq=dbsub.getSequence();
                                    setOfSubsurfacesInJob.add(dbsub);
                                    SubsurfaceJob dbSubjob;
                                    // if((dbSubjob=subsurfaceJobService.getSubsurfaceJobFor(dbjob, dbsub))==null){   //this will always return null. since this job-sub combination does not exist in the database. else the  latestTimeForSub < latestTime in the volume
                                    dbSubjob=new SubsurfaceJob();
                                    dbSubjob.setJob(dbjob);
                                    dbSubjob.setSubsurface(dbsub);
                                    dbSubjob.setUpdateTime(updateTime);
                                    dbSubjob.setSummaryTime(summaryTime);
                                
                                HeaderHolder headerHolder = new HeaderHolder();
                                headerHolder.subjob=dbSubjob;
                                     System.out.println("middleware.dugex.HeaderExtractor.<init>(): got the subsurface: "+dbsub.getSubsurface());
                               // subsurfaceJobs.add(dbSubjob);
                                System.out.println("middleware.dugex.HeaderExtractor.<init>(): creating a new Header");
                                Header header=new Header();
                                header.setJob(dbjob);
                                header.setSubsurfaceJob(dbSubjob);
                                header.setVolume(dbvol);
                                header.setSubsurface(dbsub);
                                header.setTimeStamp(latestTimestamp);
                                
                                //header.setSequence(dbsub.getSequence());
                              populate(header);
                              setOfHeadersInJob.add(header);
                              
                              //headers.add(header);
                              headerHolder.header=header;
                              headerHolderList.add(headerHolder);
//                                    System.out.println(".call(): Job: "+dbjob.getId()+"Subsurface: "+dbsub.getSubsurface()+" --> Size of headers: "+headers.size()+" of subjs: "+subsurfaceJobs.size());
                              
                              //dbjob.setHeaders(setOfHeadersInJob);
                             // dbjob.setSubsurfaces(setOfSubsurfacesInJob);
                              //dbjob.getSubsurfaceJobs().add(dbSubjob);
                             // dbjob.setSequences(setOfSequencesInJob);
                              
                              System.out.println("middleware.dugex.HeaderExtractor.<init>(): Checking for multiple instances");
                                 // headerService.getMultipleInstances(dbjob, dbsub);
                            }else{
                                System.out.println("middleware.dugex.HeaderExtractor.<init>(): Headers with same timestamp already exists in the database");
                               System.out.println("middleware.dugex.HeaderExtractor.<init>(): Checking for multiple instances");
                                 // headerService.getMultipleInstances(dbjob, dbsub);
                            }
                            
                             
                                return "Finished header extraction for sub: "+sub.getSubsurfaceName();
                                
                                }
                            };  
                                
                         System.out.println("middleware.dugex.HeaderExtractor.<init>(): Adding task for "+sub.getSubsurfaceName());
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
            
                    
                    /**
                     * 
                     * Database ops
                     * 
                    */
                    
                  
                   // exec=Executors.newFixedThreadPool(processorsUsed());
                   /*  for(Header h:headers){
                   subsurfaceJobs.add(h.getSubsurfaceJob());
                   }*/
                   
                   for(HeaderHolder hh:headerHolderList){
                       subsurfaceJobs.add(hh.subjob);
                       headers.add(hh.header);
                   }
                    System.out.println("middleware.dugex.HeaderExtractor.<init>(): "+timeNow()+"   Creating "+subsurfaceJobs.size()+" subsurfaceJob entries");
                    
                    subsurfaceJobService.createBulkSubsurfaceJob(subsurfaceJobs);
                    System.out.println("middleware.dugex.HeaderExtractor.<init>(): "+timeNow()+"   Created "+subsurfaceJobs.size()+" subsurfaceJob entries");
                    System.out.println("middleware.dugex.HeaderExtractor.<init>(): "+timeNow()+"   Committing "+headers.size()+" headers");
                    headerService.createBulkHeaders(headers);
                    System.out.println("middleware.dugex.HeaderExtractor.<init>(): "+timeNow()+"   Created "+headers.size()+" headers");
                    System.out.println("middleware.dugex.HeaderExtractor.<init>(): "+timeNow()+"   Bulk update of logs for headers");
                    for(Header h:headers){
                        logService.bulkUpdateOnLogs(dbvol, h, h.getSubsurface());
                    }
                    System.out.println("middleware.dugex.HeaderExtractor.<init>(): "+timeNow()+"   Completed update of logs for "+headers.size()+" headers");
                 //   jobService.updateJob(dbjob.getId(), dbjob);
                    job.setDatabaseJob(dbjob);
              
          }
         
          
          // System.out.println("middleware.dugex.HeaderExtractor.<init>(): Checking for multiple instances");
                
                   //((JobType2Model)job).setHeadersCommited(true);
                   System.out.println("middleware.dugex.HeaderExtractor.<init>(): shutting down executorService");
       
          
        }
        
        
        //if Acquisition
        if(job.getType().equals(JobType0Model.ACQUISITION)){
            
            List<SubsurfaceJob> subjobsToBeCommited=new ArrayList<>();
            
            /* List<Subsurface> totalSubsurfacesTillNow=subsurfaceService.getSubsurfaceList();
            List<Subsurface> subsurfacesPresentInJob=subsurfaceJobService.getSubsurfacesForJob(dbjob);*/
            
            
            List<Subsurface> totalSubsurfacesTillNow=new ArrayList<>();
            
            List<Subsurface> l1=subsurfaceService.getSubsurfaceList();
            Subsurface[] totalSubsArray=new Subsurface[l1.size()];
            for(int i=0;i<l1.size();i++){
                totalSubsArray[i]=l1.get(i);
            }
           
            Collections.addAll(totalSubsurfacesTillNow,totalSubsArray);
            
            
            List<Subsurface> subsurfacesPresentInJob=new ArrayList<>();
            
            List<Subsurface> l2=subsurfaceJobService.getSubsurfacesForJob(dbjob);
            
            Subsurface[] sjarray=new Subsurface[l2.size()];
            for(int i=0;i<l2.size();i++){
                sjarray[i]=l2.get(i);
            }
            System.out.println("middleware.dugex.HeaderExtractor.<init>(): size of l2: "+l2.size()+" size of sjarray: "+sjarray.length);
            
            Collections.addAll(subsurfacesPresentInJob, sjarray);
            
            
            System.out.println("middleware.dugex.HeaderExtractor.<init>(): for job : "+dbjob.getNameJobStep()+" found "+subsurfacesPresentInJob.size()+" subsurfaces : total subsurfaces Acquired: "+totalSubsurfacesTillNow.size());
            //get all subsurfaces inside a job. S
            //if dbSub is not present in S then create dbjob,dbsub
            totalSubsurfacesTillNow.removeAll(subsurfacesPresentInJob);   //keep only the ones left to be added
            
            System.out.println("middleware.dugex.HeaderExtractor.<init>(): to add "+totalSubsurfacesTillNow.size());
            
            String summaryTime=new DateTime(1986,6,6,00,00,00,DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
            String updateTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);   //replace this with time in orca
            
            System.out.println("middleware.dugex.HeaderExtractor.<init>(): NOTE THAT THE UPDATE TIME SHOULD BE SET TO THE DATE OF ACQ FROM ORCA!. currently set to now(): "+updateTime);
            for(Subsurface dbsub:totalSubsurfacesTillNow){
                SubsurfaceJob dbSubjob;
                
                //  if((dbSubjob=subsurfaceJobService.getSubsurfaceJobFor(dbjob, dbsub))==null){   //if there is no entry then create
                      dbSubjob=new SubsurfaceJob();
                      dbSubjob.setJob(dbjob);
                      dbSubjob.setSubsurface(dbsub);
                      
                      dbSubjob.setUpdateTime(updateTime);                                 //replace by acquisition time from db
                      subjobsToBeCommited.add(dbSubjob);
                
                  
            }
            
            subsurfaceJobService.createBulkSubsurfaceJob(subjobsToBeCommited);
            
        }
       // exec.shutdown();
    }

    private void populate(Header hdr) {
        
        /*try {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Callable<Void>() {
        @Override
        public Void call() throws Exception {*/
                    
                    
                    System.out.println("middleware.dugex.HeaderExtractor.populate(): populating headers for hdrs: id: "+hdr.getHeaderId());
                    Long traceCount=0L;
                    Long cmpMax=0L;
                    Long cmpMin=0L;
                    Long cmpInc=0L;
                    
                    Long inlineMax=0L;
                    Long inlineMin=0L;
                    Long inlineInc=0L;
                    Long xlineMax=0L;
                    Long xlineMin=0L;
                    Long xlineInc=0L;
                    Long dugShotMax=0L;
                    Long dugShotMin=0L;
                    Long dugShotInc=0L;
                    Long dugChannelMax=0L;
                    Long dugChannelMin=0L;
                    Long dugChannelInc=0L;
                    Long offsetMax=0L;
                    Long offsetMin=0L;
                    Long offsetInc=0L;
                    
                    
                    
                    
                    if(hdr.getVolume().getVolumeType().equals(Volume0.PROCESS_2D) || hdr.getVolume().getVolumeType().equals(Volume0.SEGD_LOAD) ){
                        try{
                            traceCount=Long.valueOf(forTraces(hdr));
                            cmpMax=Long.valueOf(forEachKey(hdr,dmh.cmpMax));
                            cmpMin=Long.valueOf(forEachKey(hdr,dmh.cmpMin));
                            cmpInc=Long.valueOf(forEachKey(hdr,dmh.cmpInc));
                            
                            inlineMax=Long.valueOf(forEachKey(hdr,dmh.inlineMax));
                            inlineMin=Long.valueOf(forEachKey(hdr,dmh.inlineMin));
                            inlineInc=Long.valueOf(forEachKey(hdr,dmh.inlineInc));
                            xlineMax=Long.valueOf(forEachKey(hdr,dmh.xlineMax));
                            xlineMin=Long.valueOf(forEachKey(hdr,dmh.xlineMin));
                            xlineInc=Long.valueOf(forEachKey(hdr,dmh.xlineInc));
                            dugShotMax=Long.valueOf(forEachKey(hdr,dmh.dugShotMax));
                            dugShotMin=Long.valueOf(forEachKey(hdr,dmh.dugShotMin));
                            dugShotInc=Long.valueOf(forEachKey(hdr,dmh.dugShotInc));
                            dugChannelMax=Long.valueOf(forEachKey(hdr,dmh.dugChannelMax));
                            dugChannelMin=Long.valueOf(forEachKey(hdr,dmh.dugChannelMin));
                            dugChannelInc=Long.valueOf(forEachKey(hdr,dmh.dugChannelInc));
                            offsetMax=Long.valueOf(forEachKey(hdr,dmh.offsetMax));
                            offsetMin=Long.valueOf(forEachKey(hdr,dmh.offsetMin));
                            offsetInc=Long.valueOf(forEachKey(hdr,dmh.offsetInc));
                            
                        }catch(NumberFormatException nfe){
                            traceCount=-1L;
                            cmpMax=-1L;
                            cmpMin=-1L;
                            cmpInc=-1L;
                            
                            inlineMax=-1L;
                            inlineMin=-1L;
                            inlineInc=-1L;
                            xlineMax=-1L;
                            xlineMin=-1L;
                            xlineInc=-1L;
                            dugShotMax=-1L;
                            dugShotMin=-1L;
                            dugShotInc=-1L;
                            dugChannelMax=-1L;
                            dugChannelMin=-1L;
                            dugChannelInc=-1L;
                            offsetMax=-1L;
                            offsetMin=-1L;
                            offsetInc=-1L;
                        }catch(IOException ioe){
                            System.out.println("middleware.dugex.HeaderExtractor.populate(): IOException: "+ioe.getMessage());
                        }
                    }
                    
                    
                    
                    
                    
                    hdr.setTraceCount(traceCount);
                    hdr.setCmpMax(cmpMax);
                    hdr.setCmpMin(cmpMin);
                    hdr.setCmpInc(cmpInc);
                    hdr.setInlineMax(inlineMax);
                    hdr.setInlineMin(inlineMin);
                    hdr.setInlineInc(inlineInc);
                    hdr.setXlineMax(xlineMax);
                    hdr.setXlineMin(xlineMin);
                    hdr.setXlineInc(xlineInc);
                    hdr.setDugShotMax(dugShotMax);
                    hdr.setDugShotMin(dugShotMin);
                    hdr.setDugShotInc(dugShotInc);
                    hdr.setDugChannelMax(dugChannelMax);
                    hdr.setDugChannelMin(dugChannelMin);
                    hdr.setDugChannelInc(dugChannelInc);
                    hdr.setOffsetMax(offsetMax);
                    hdr.setOffsetMin(offsetMin);
                    hdr.setOffsetInc(offsetInc);
                    
                   // System.out.println("middleware.dugex.HeaderExtractor.populate(): Assign Latest insight and workflow versions from logs");
                    Log latestLog=this.job.getLatestLogForSubsurfaceMap().get(hdr.getSubsurface());              // the log table should be commited by now.
                    
                   // Log latestLog=logService.getLatestLogFor(hdr.getVolume(), hdr.getSubsurface());
                   /*  System.out.println("middleware.dugex.HeaderExtractor.populate(): for header "+
                   hdr.getHeaderId()+" Latest Log: "+
                   latestLog.getIdLogs());*/
                    if(latestLog!=null){
                    hdr.setInsightVersion(latestLog.getInsightVersion());
                    hdr.setWorkflowVersion(latestLog.getWorkflow().getWfversion());
                    hdr.setNumberOfRuns(latestLog.getVersion()+1);
                    }
                    else{
                       hdr.setInsightVersion("ERROR");
                       hdr.setWorkflowVersion(-1L);
                       hdr.setNumberOfRuns(-1L);
                    }
                    
                    
                    //System.out.println("middleware.dugex.HeaderExtractor.populate(): Updating logs with the corresponding headers");
                    
                    System.out.println("middleware.dugex.HeaderExtractor.populate(): finished storing headers for : "+hdr.getSubsurface().getSubsurface());
                  //  headerService.createHeader(hdr);
                    
                
                    //bulk update for logs belonging to headers
                  //  logService.bulkUpdateOnLogs(hdr.getVolume(), hdr, hdr.getSubsurface());
           
         
                         
    }
    
    private String forEachKey(Header hdr,String key) throws IOException {
                       /// System.out.println("Inside forEach key with key ="+key);
        
         try{
                 Process process=new ProcessBuilder(dugioscript.getDugioHeaderValuesSh().getAbsolutePath(),hdr.getVolume().getPathOfVolume(),hdr.getSubsurface().getSubsurface(),key).start();
                        InputStream is = process.getInputStream();
                        InputStreamReader isr=new InputStreamReader(is);
                        BufferedReader br=new BufferedReader(isr);
                        
                        String value;
                        while((value=br.readLine())!=null){
                        //    System.out.println("DHVEx: forEachKey Volume: "+volume+" sub: "+hdr.getSubsurface()+" key: "+key+" = "+value);
                            return value;
                        }
         }catch(Exception ex){
            // logger.severe(ex.getMessage());
         }         
           return null;
                 
                       
                        
    }
    
    private String forTraces(Header hdr) throws IOException{
                      //  System.out.println("Inside forTraces key with NO key");
                      try{
                        Process process=new ProcessBuilder(dugioscript.getDugioGetTraces().getAbsolutePath(),hdr.getVolume().getPathOfVolume(),hdr.getSubsurface().getSubsurface()).start();
                        InputStream is = process.getInputStream();
                        InputStreamReader isr=new InputStreamReader(is);
                        BufferedReader br=new BufferedReader(isr);
                        
                        String value;
                        while((value=br.readLine())!=null){
                         //   System.out.println("DHVEx: forTraces Volume: "+volume+" sub: "+hdr.getSubsurface()+" Traces ="+value+"");
                            return value;
                        }
                      }catch(Exception ex){
                         // logger.severe(ex.getMessage());
                      }
                        return null;
                        
    }
    
    
    private int processorsUsed() throws Exception{
        int procsUsed=(int) (Runtime.getRuntime().availableProcessors()*percentageOfProcessorsUsed);
               if(procsUsed <= 1){
                   System.out.println("middleware.dugex.HeaderExtractor.<init>(): Not enough resources . PR-TCount: "+procsUsed);
                   throw new Exception("Not enough resources . PR-TCount: "+procsUsed);
               }
        
               return procsUsed;
    }
    
    private String timeNow(){
        return DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
    }
    
    /**
     * a fix for threading issues
     **/
    
    private class HeaderHolder{
        SubsurfaceJob subjob;
        Header header;
    }
}

 
