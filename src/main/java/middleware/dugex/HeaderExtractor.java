/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.dugex;

import db.model.Header;
import db.model.Job;
import db.model.Log;
import db.model.Subsurface;
import db.model.Volume;
import db.services.HeaderService;
import db.services.HeaderServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.LogService;
import db.services.LogServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.job.job0.JobType0Model;
import fend.job.job1.JobType1Model;
import fend.volume.volume0.Volume0;
import fend.volume.volume1.Volume1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import middleware.sequences.SubsurfaceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class HeaderExtractor {
    JobType0Model job;
    Job dbjob;
    SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();
    HeaderService headerService=new HeaderServiceImpl();
    VolumeService volumeService=new VolumeServiceImpl();
    LogService logService=new LogServiceImpl();
    JobService jobService=new JobServiceImpl();
    private DugioScripts dugioscript=new DugioScripts();
    DugioMetaHeader dmh=new DugioMetaHeader();
    
    
    public HeaderExtractor(JobType0Model j){
        System.out.println("middleware.dugex.HeaderExtractor.<init>(): Entered ");
        job=j;
        dbjob=jobService.getJob(job.getId());
        List<Volume0> volumes=job.getVolumes();
        Set<Header> setOfHeadersInJob=new HashSet<>();
        Set<Subsurface> setOfSubsurfacesInJob=new HashSet<>();
        //type1 extraction
        if(job.getType().equals(JobType0Model.PROCESS_2D)){
           
            
          for(Volume0 vol:volumes){
              System.out.println("middleware.dugex.HeaderExtractor.<init>(): calling volume "+vol.getName().get());
              Volume dbvol=volumeService.getVolume(vol.getId());
              //Job dbjob=dbvol.getJob();
              List<SubsurfaceHeaders> subsInVol=vol.getSubsurfaces();     //these have the timestamp of the latest runs
              for(SubsurfaceHeaders sub:subsInVol){
                  Subsurface dbsub=subsurfaceService.getSubsurfaceObjBysubsurfacename(sub.getSubsurfaceName());
                  setOfSubsurfacesInJob.add(dbsub);
                  System.out.println("middleware.dugex.HeaderExtractor.<init>(): subsurfacename:  from file: "+sub.getSubsurfaceName());
                
                  
                  System.out.println("middleware.dugex.HeaderExtractor.<init>(): got the subsurface: "+dbsub.getSubsurface());
                  String latestTimestamp=sub.getTimeStamp();
                  if(headerService.getHeadersFor(dbvol,dbsub,latestTimestamp)==null){
                      System.out.println("middleware.dugex.HeaderExtractor.<init>(): creating a new Header");
                      Header header=new Header();
                      header.setJob(dbvol.getJob());
                      header.setVolume(dbvol);
                      header.setSubsurface(dbsub);
                      header.setTimeStamp(latestTimestamp);
                      //header.setSequence(dbsub.getSequence());
                    populate(header);
                    setOfHeadersInJob.add(header);
                      
                  }else{
                      System.out.println("middleware.dugex.HeaderExtractor.<init>(): Headers with same timestamp already exists in the database");
                     
                  }
                  
              }
          }
          
          dbjob.setSubsurfaces(setOfSubsurfacesInJob);
          dbjob.setHeaders(setOfHeadersInJob);
          jobService.updateJob(dbjob.getId(), dbjob);
           System.out.println("middleware.dugex.HeaderExtractor.<init>(): Checking for multiple instances");
               //   headerService.getMultipleInstances(dbjob, dbsub);
                   ((JobType1Model)job).setHeadersCommited(true);
          
        }
    }

    private void populate(Header hdr) {
        
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
               
         
        System.out.println("middleware.dugex.HeaderExtractor.populate(): populating headers for hdrs: id: "+hdr.getId());
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
        
        
        
        
       if(hdr.getVolume().getVolumeType().equals(1L)){
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
                
             System.out.println("middleware.dugex.HeaderExtractor.populate(): Assign Latest insight and workflow versions from logs");
             Log latestLog=logService.getLatestLogFor(hdr.getVolume(), hdr.getSubsurface());
             hdr.setInsightVersion(latestLog.getInsightVersion());
             hdr.setWorkflowVersion(latestLog.getWorkflow().getWfversion());
             hdr.setNumberOfRuns(latestLog.getVersion()+1);
               
                System.out.println("middleware.dugex.HeaderExtractor.populate(): Updating logs with the corresponding headers");
             
                         System.out.println("dugex.DugioHeaderValuesExtractor.calculateRemainingHeaders(): finished storing headers for : "+hdr.getSubsurface().getSubsurface());
                         headerService.createHeader(hdr);
                         
                List<Log> logsForHeader=logService.getLogsFor(hdr.getVolume(), hdr.getSubsurface());
                Set<Log> setOfLogsForHeader=new HashSet<>(logsForHeader);
                hdr.setLogs(setOfLogsForHeader);
                for(Log l:setOfLogsForHeader){
                    l.setHeader(hdr);
                    logService.updateLogs(l.getIdLogs(), l);
                }
                
                headerService.updateHeader(hdr.getId(), hdr);
                headerService.getMultipleInstances(dbjob, hdr.getSubsurface());
                
                           
            return null;
            }
        });
                         
         
                         
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
    
}
