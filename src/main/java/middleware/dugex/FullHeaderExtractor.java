/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.dugex;

import app.properties.AppProperties;
import db.model.Fheader;
import db.model.Fheader;
import db.model.Job;
import db.model.Log;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.SubsurfaceJob;
import db.model.Volume;
import db.services.FheaderService;
import db.services.FheaderServiceImpl;
import db.services.LogService;
import db.services.LogServiceImpl;
import db.services.SubsurfaceJobService;
import db.services.SubsurfaceJobServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.job.job0.JobType0Model;
import fend.volume.volume0.Volume0;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import middleware.sequences.SubsurfaceHeaders;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class FullHeaderExtractor {
    
    JobType0Model job;
    Job dbjob;
    SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();
    FheaderService fheaderService=new FheaderServiceImpl();
    SubsurfaceJobService subsurfaceJobService=new SubsurfaceJobServiceImpl();
    List<SubsurfaceJobKey> existingSubsurfaceJobs=new ArrayList<>();
    List<FheaderHolder> fheaderHolderList=new ArrayList<>();        
    VolumeService volumeService=new VolumeServiceImpl();
    Map<VolumeSubsurfaceKey,Fheader> existingFheadersInThisJob=new HashMap<>(); 
    List<Fheader> fheaders=new ArrayList<>();
    List<SubsurfaceJob> subsurfaceJobs=new ArrayList<>();
    LogService logService=new LogServiceImpl();
    ExecutorService exec;
    double percentageOfProcessorsUsed=AppProperties.PERCENTAGE_OF_PROCESSORS_USED;
    private DugioFullHeaders dugioFullHeaders=new DugioFullHeaders();
    
    List<String> headerNames=new ArrayList<>();

    public FullHeaderExtractor(JobType0Model job) {
        this.job = job;
        this.dbjob = this.job.getDatabaseJob();
        
        
        
    }
    
    
    
    
    public void work() throws IllegalArgumentException, IllegalAccessException, Exception  {
        
        Class chandle=DugioFullHeaders.class;
        Field[] fields=chandle.getDeclaredFields();                        //all public headers by Reflection
        
        for(int j=0;j<fields.length;j++){
            headerNames.add((String) fields[j].get(dugioFullHeaders));
        }
        
        
        exec=Executors.newFixedThreadPool(processorsUsed());
        
        List<Volume0> volumes=job.getVolumes();
        List<Fheader> headersExistingInJob=fheaderService.getHeadersFor(dbjob);
                for(Fheader h:headersExistingInJob){
                    VolumeSubsurfaceKey key=generateVolumeSubsurfaceKey(h.getVolume(), h.getSubsurface());
                    
                        existingFheadersInThisJob.put(key, h);
                 
                }
                
          for(Volume0 vol:volumes){ 
              
              List<String> subsurfacesOnDisk=new ArrayList<>();
              subsurfaceJobs.clear();
              fheaders.clear();
              fheaderHolderList.clear();
               List<Subsurface> subsExistingInJob=subsurfaceJobService.getSubsurfacesForJob(dbjob);
        
                for(Subsurface s:subsExistingInJob){
                    SubsurfaceJobKey skey=generateSubsurfaceJobKey(s, dbjob);
                    existingSubsurfaceJobs.add(skey);
                }
                
                
              Volume dbvol=volumeService.getVolume(vol.getId()); 
              System.out.println("middleware.dugex.HeaderExtractor.<init>(): calling volume "+vol.getName().get()+" id: "+dbvol.getId());
              //Job dbjob=dbvol.getJob();
              String summaryTime=new DateTime(1986,6,6,00,00,00,DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
              List<SubsurfaceHeaders> subsInVol=vol.getSubsurfaces();     //these have the timestamp of the latest runs
              String latestCommitToHeadersTable=fheaderService.getLatestTimeStampFor(dbvol);   //get the latest(max) timestamp for this volume
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
                            subsurfacesOnDisk.add(sub.getSubsurfaceName());
                
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
                                   // setOfSubsurfacesInJob.add(dbsub);
                                    SubsurfaceJob dbSubjob;
                                    // if((dbSubjob=subsurfaceJobService.getSubsurfaceJobFor(dbjob, dbsub))==null){   //this will always return null. since this job-sub combination does not exist in the database. else the  latestTimeForSub < latestTime in the volume
                                    dbSubjob=new SubsurfaceJob();
                                    dbSubjob.setJob(dbjob);
                                    dbSubjob.setSubsurface(dbsub);
                                    dbSubjob.setUpdateTime(updateTime);
                                    dbSubjob.setSummaryTime(summaryTime);
                                
                                FheaderHolder FheaderHolder = new FheaderHolder();
                                //headerHolder.subjob=dbSubjob;
                                     System.out.println("middleware.dugex.HeaderExtractor.<init>(): got the subsurface: "+dbsub.getSubsurface());
                               // subsurfaceJobs.add(dbSubjob);
                                
                                /*fheader fheader=new fheader();
                                fheader.setJob(dbjob);
                                fheader.setSubsurfaceJob(dbSubjob);
                                fheader.setVolume(dbvol);
                                fheader.setSubsurface(dbsub);
                                */
                                VolumeSubsurfaceKey vskey=generateVolumeSubsurfaceKey(dbvol, dbsub);
                                 Fheader fheader;
                                if(existingFheadersInThisJob.containsKey(vskey)){
                                    System.out.println("middleware.dugex.HeaderExtractor.<init>(): updating existing Header");
                                    fheader=existingFheadersInThisJob.get(vskey);
                                }else{
                                    System.out.println("middleware.dugex.HeaderExtractor.<init>(): creating a new Header");
                                    fheader=new Fheader();
                                    fheader.setJob(dbjob);
                                    fheader.setSubsurfaceJob(dbSubjob);
                                    fheader.setVolume(dbvol);
                                    fheader.setSubsurface(dbsub);
                                }
                                fheader.setTimeStamp(latestTimestamp);
                                
                                //header.setSequence(dbsub.getSequence());
                              populate(fheader);
                             // setOfFheadersInJob.add(fheader);
                              
                              //headers.add(fheader);
                              FheaderHolder.fheader=fheader;
                              FheaderHolder.subjob=fheader.getSubsurfaceJob();
                              fheaderHolderList.add(FheaderHolder);
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
                    
                  
                  
                   for(FheaderHolder fh:fheaderHolderList){
                       SubsurfaceJobKey skey=generateSubsurfaceJobKey(fh.subjob.getSubsurface(), fh.subjob.getJob());
                       if(!existingSubsurfaceJobs.contains(skey)){
                                    subsurfaceJobs.add(fh.subjob);
                                    existingSubsurfaceJobs.add(skey);
                        }
                       fheaders.add(fh.fheader);
                   }
                    System.out.println("middleware.dugex.HeaderExtractor.<init>(): "+timeNow()+"   Creating "+subsurfaceJobs.size()+" subsurfaceJob entries");
                    
                    subsurfaceJobService.createBulkSubsurfaceJob(subsurfaceJobs);
                    System.out.println("middleware.dugex.HeaderExtractor.<init>(): "+timeNow()+"   Created "+subsurfaceJobs.size()+" subsurfaceJob entries");
                    System.out.println("middleware.dugex.HeaderExtractor.<init>(): "+timeNow()+"   Committing "+fheaders.size()+" headers");
                    fheaderService.createBulkHeaders(fheaders);
                    System.out.println("middleware.dugex.HeaderExtractor.<init>(): "+timeNow()+"   Created "+fheaders.size()+" headers");
                    System.out.println("middleware.dugex.HeaderExtractor.<init>(): "+timeNow()+"   Bulk update of logs for headers");
                    for(Fheader h:fheaders){
                        logService.bulkUpdateOnLogs(dbvol, h, h.getSubsurface()); 
                    }
                    System.out.println("middleware.dugex.HeaderExtractor.<init>(): "+timeNow()+"   Completed update of logs for "+fheaders.size()+" headers");
                
                    job.setDatabaseJob(dbjob);
              
                    System.out.println("middleware.dugex.HeaderExtractor.<init>(): updating delete flags for volume: "+vol.getName());
                    fheaderService.updateDeleteFlagsFor(dbvol,subsurfacesOnDisk);
          }
                  
                        System.out.println("middleware.dugex.HeaderExtractor.<init>(): Checking for any repeated subs in job: "+dbjob.getNameJobStep());
                        fheaderService.checkForMultipleSubsurfacesInHeadersForJob(dbjob);
                
                   System.out.println("middleware.dugex.HeaderExtractor.<init>(): shutting down executorService");
    }

    
    
    
    
    private final DugioScripts dugioScripts=new DugioScripts();
            
    private void populate(Fheader fheader) throws  IllegalAccessException, IllegalArgumentException,IOException, InvocationTargetException{
         Map<String,Long> headerValueMap=new HashMap<>();
         
       headerValueMap=extractFullHeaders(fheader);
      // for(String header:headerNames){
      fheader.setAll(0L);
 fheader.setTotalTraces(headerValueMap.get(dugioFullHeaders.getTotalTraces()));
 fheader.setMinTracr(headerValueMap.get(dugioFullHeaders.getMinTracr()));
 fheader.setMaxTracr(headerValueMap.get(dugioFullHeaders.getMaxTracr()));
 fheader.setIncTracr(headerValueMap.get(dugioFullHeaders.getIncTracr()));
 fheader.setFirstTracr(headerValueMap.get(dugioFullHeaders.getFirstTracr()));
 fheader.setLastTracr(headerValueMap.get(dugioFullHeaders.getLastTracr()));
 fheader.setMinFldr(headerValueMap.get(dugioFullHeaders.getMinFldr()));
 fheader.setMaxFldr(headerValueMap.get(dugioFullHeaders.getMaxFldr()));
 fheader.setIncFldr(headerValueMap.get(dugioFullHeaders.getIncFldr()));
 fheader.setFirstFldr(headerValueMap.get(dugioFullHeaders.getFirstFldr()));
 fheader.setLastFldr(headerValueMap.get(dugioFullHeaders.getLastFldr()));
 fheader.setMinTracf(headerValueMap.get(dugioFullHeaders.getMinTracf()));
 fheader.setMaxTracf(headerValueMap.get(dugioFullHeaders.getMaxTracf()));
 fheader.setIncTracf(headerValueMap.get(dugioFullHeaders.getIncTracf()));
 fheader.setFirstTracf(headerValueMap.get(dugioFullHeaders.getFirstTracf()));
 fheader.setLastTracf(headerValueMap.get(dugioFullHeaders.getLastTracf()));
 fheader.setMinEp(headerValueMap.get(dugioFullHeaders.getMinEp()));
 fheader.setMaxEp(headerValueMap.get(dugioFullHeaders.getMaxEp()));
 fheader.setIncEp(headerValueMap.get(dugioFullHeaders.getIncEp()));
 fheader.setFirstEp(headerValueMap.get(dugioFullHeaders.getFirstEp()));
 fheader.setLastEp(headerValueMap.get(dugioFullHeaders.getLastEp()));
 fheader.setMinCdp(headerValueMap.get(dugioFullHeaders.getMinCdp()));
 fheader.setMaxCdp(headerValueMap.get(dugioFullHeaders.getMaxCdp()));
 fheader.setIncCdp(headerValueMap.get(dugioFullHeaders.getIncCdp()));
 fheader.setFirstCdp(headerValueMap.get(dugioFullHeaders.getFirstCdp()));
 fheader.setLastCdp(headerValueMap.get(dugioFullHeaders.getLastCdp()));
 fheader.setMinCdpt(headerValueMap.get(dugioFullHeaders.getMinCdpt()));
 fheader.setMaxCdpt(headerValueMap.get(dugioFullHeaders.getMaxCdpt()));
 fheader.setIncCdpt(headerValueMap.get(dugioFullHeaders.getIncCdpt()));
 fheader.setFirstCdpt(headerValueMap.get(dugioFullHeaders.getFirstCdpt()));
 fheader.setLastCdpt(headerValueMap.get(dugioFullHeaders.getLastCdpt()));
 fheader.setMinTrid(headerValueMap.get(dugioFullHeaders.getMinTrid()));
 fheader.setMaxTrid(headerValueMap.get(dugioFullHeaders.getMaxTrid()));
 fheader.setIncTrid(headerValueMap.get(dugioFullHeaders.getIncTrid()));
 fheader.setFirstTrid(headerValueMap.get(dugioFullHeaders.getFirstTrid()));
 fheader.setLastTrid(headerValueMap.get(dugioFullHeaders.getLastTrid()));
 fheader.setMinDuse(headerValueMap.get(dugioFullHeaders.getMinDuse()));
 fheader.setMaxDuse(headerValueMap.get(dugioFullHeaders.getMaxDuse()));
 fheader.setIncDuse(headerValueMap.get(dugioFullHeaders.getIncDuse()));
 fheader.setFirstDuse(headerValueMap.get(dugioFullHeaders.getFirstDuse()));
 fheader.setLastDuse(headerValueMap.get(dugioFullHeaders.getLastDuse()));
 fheader.setDuse(headerValueMap.get(dugioFullHeaders.getDuse()));
 fheader.setMinOffset(headerValueMap.get(dugioFullHeaders.getMinOffset()));
 fheader.setMaxOffset(headerValueMap.get(dugioFullHeaders.getMaxOffset()));
 fheader.setIncOffset(headerValueMap.get(dugioFullHeaders.getIncOffset()));
 fheader.setFirstOffset(headerValueMap.get(dugioFullHeaders.getFirstOffset()));
 fheader.setLastOffset(headerValueMap.get(dugioFullHeaders.getLastOffset()));
 fheader.setMinGelev(headerValueMap.get(dugioFullHeaders.getMinGelev()));
 fheader.setMaxGelev(headerValueMap.get(dugioFullHeaders.getMaxGelev()));
 fheader.setIncGelev(headerValueMap.get(dugioFullHeaders.getIncGelev()));
 fheader.setFirstGelev(headerValueMap.get(dugioFullHeaders.getFirstGelev()));
 fheader.setLastGelev(headerValueMap.get(dugioFullHeaders.getLastGelev()));
 fheader.setMinSelev(headerValueMap.get(dugioFullHeaders.getMinSelev()));
 fheader.setMaxSelev(headerValueMap.get(dugioFullHeaders.getMaxSelev()));
 fheader.setIncSelev(headerValueMap.get(dugioFullHeaders.getIncSelev()));
 fheader.setFirstSelev(headerValueMap.get(dugioFullHeaders.getFirstSelev()));
 fheader.setLastSelev(headerValueMap.get(dugioFullHeaders.getLastSelev()));
 fheader.setMinSdepth(headerValueMap.get(dugioFullHeaders.getMinSdepth()));
 fheader.setMaxSdepth(headerValueMap.get(dugioFullHeaders.getMaxSdepth()));
 fheader.setIncSdepth(headerValueMap.get(dugioFullHeaders.getIncSdepth()));
 fheader.setFirstSdepth(headerValueMap.get(dugioFullHeaders.getFirstSdepth()));
 fheader.setLastSdepth(headerValueMap.get(dugioFullHeaders.getLastSdepth()));
 fheader.setMinSwdep(headerValueMap.get(dugioFullHeaders.getMinSwdep()));
 fheader.setMaxSwdep(headerValueMap.get(dugioFullHeaders.getMaxSwdep()));
 fheader.setIncSwdep(headerValueMap.get(dugioFullHeaders.getIncSwdep()));
 fheader.setFirstSwdep(headerValueMap.get(dugioFullHeaders.getFirstSwdep()));
 fheader.setLastSwdep(headerValueMap.get(dugioFullHeaders.getLastSwdep()));
 fheader.setMinGwdep(headerValueMap.get(dugioFullHeaders.getMinGwdep()));
 fheader.setMaxGwdep(headerValueMap.get(dugioFullHeaders.getMaxGwdep()));
 fheader.setIncGwdep(headerValueMap.get(dugioFullHeaders.getIncGwdep()));
 fheader.setFirstGwdep(headerValueMap.get(dugioFullHeaders.getFirstGwdep()));
 fheader.setLastGwdep(headerValueMap.get(dugioFullHeaders.getLastGwdep()));
 fheader.setMinScalel(headerValueMap.get(dugioFullHeaders.getMinScalel()));
 fheader.setMaxScalel(headerValueMap.get(dugioFullHeaders.getMaxScalel()));
 fheader.setIncScalel(headerValueMap.get(dugioFullHeaders.getIncScalel()));
 fheader.setFirstScalel(headerValueMap.get(dugioFullHeaders.getFirstScalel()));
 fheader.setLastScalel(headerValueMap.get(dugioFullHeaders.getLastScalel()));
 fheader.setScalel(headerValueMap.get(dugioFullHeaders.getScalel()));
 fheader.setMinScalco(headerValueMap.get(dugioFullHeaders.getMinScalco()));
 fheader.setMaxScalco(headerValueMap.get(dugioFullHeaders.getMaxScalco()));
 fheader.setIncScalco(headerValueMap.get(dugioFullHeaders.getIncScalco()));
 fheader.setFirstScalco(headerValueMap.get(dugioFullHeaders.getFirstScalco()));
 fheader.setLastScalco(headerValueMap.get(dugioFullHeaders.getLastScalco()));
 fheader.setScalco(headerValueMap.get(dugioFullHeaders.getScalco()));
 fheader.setMinSx(headerValueMap.get(dugioFullHeaders.getMinSx()));
 fheader.setMaxSx(headerValueMap.get(dugioFullHeaders.getMaxSx()));
 fheader.setIncSx(headerValueMap.get(dugioFullHeaders.getIncSx()));
 fheader.setFirstSx(headerValueMap.get(dugioFullHeaders.getFirstSx()));
 fheader.setLastSx(headerValueMap.get(dugioFullHeaders.getLastSx()));
 fheader.setMinSy(headerValueMap.get(dugioFullHeaders.getMinSy()));
 fheader.setMaxSy(headerValueMap.get(dugioFullHeaders.getMaxSy()));
 fheader.setIncSy(headerValueMap.get(dugioFullHeaders.getIncSy()));
 fheader.setFirstSy(headerValueMap.get(dugioFullHeaders.getFirstSy()));
 fheader.setLastSy(headerValueMap.get(dugioFullHeaders.getLastSy()));
 fheader.setMinGx(headerValueMap.get(dugioFullHeaders.getMinGx()));
 fheader.setMaxGx(headerValueMap.get(dugioFullHeaders.getMaxGx()));
 fheader.setIncGx(headerValueMap.get(dugioFullHeaders.getIncGx()));
 fheader.setFirstGx(headerValueMap.get(dugioFullHeaders.getFirstGx()));
 fheader.setLastGx(headerValueMap.get(dugioFullHeaders.getLastGx()));
 fheader.setMinGy(headerValueMap.get(dugioFullHeaders.getMinGy()));
 fheader.setMaxGy(headerValueMap.get(dugioFullHeaders.getMaxGy()));
 fheader.setIncGy(headerValueMap.get(dugioFullHeaders.getIncGy()));
 fheader.setFirstGy(headerValueMap.get(dugioFullHeaders.getFirstGy()));
 fheader.setLastGy(headerValueMap.get(dugioFullHeaders.getLastGy()));
 fheader.setMinWevel(headerValueMap.get(dugioFullHeaders.getMinWevel()));
 fheader.setMaxWevel(headerValueMap.get(dugioFullHeaders.getMaxWevel()));
 fheader.setIncWevel(headerValueMap.get(dugioFullHeaders.getIncWevel()));
 fheader.setFirstWevel(headerValueMap.get(dugioFullHeaders.getFirstWevel()));
 fheader.setLastWevel(headerValueMap.get(dugioFullHeaders.getLastWevel()));
 fheader.setWevel(headerValueMap.get(dugioFullHeaders.getWevel()));
 fheader.setMinSwevel(headerValueMap.get(dugioFullHeaders.getMinSwevel()));
 fheader.setMaxSwevel(headerValueMap.get(dugioFullHeaders.getMaxSwevel()));
 fheader.setIncSwevel(headerValueMap.get(dugioFullHeaders.getIncSwevel()));
 fheader.setFirstSwevel(headerValueMap.get(dugioFullHeaders.getFirstSwevel()));
 fheader.setLastSwevel(headerValueMap.get(dugioFullHeaders.getLastSwevel()));
 fheader.setSwevel(headerValueMap.get(dugioFullHeaders.getSwevel()));
 fheader.setMinSut(headerValueMap.get(dugioFullHeaders.getMinSut()));
 fheader.setMaxSut(headerValueMap.get(dugioFullHeaders.getMaxSut()));
 fheader.setIncSut(headerValueMap.get(dugioFullHeaders.getIncSut()));
 fheader.setFirstSut(headerValueMap.get(dugioFullHeaders.getFirstSut()));
 fheader.setLastSut(headerValueMap.get(dugioFullHeaders.getLastSut()));
 fheader.setMinGut(headerValueMap.get(dugioFullHeaders.getMinGut()));
 fheader.setMaxGut(headerValueMap.get(dugioFullHeaders.getMaxGut()));
 fheader.setIncGut(headerValueMap.get(dugioFullHeaders.getIncGut()));
 fheader.setFirstGut(headerValueMap.get(dugioFullHeaders.getFirstGut()));
 fheader.setLastGut(headerValueMap.get(dugioFullHeaders.getLastGut()));
 fheader.setMinSstat(headerValueMap.get(dugioFullHeaders.getMinSstat()));
 fheader.setMaxSstat(headerValueMap.get(dugioFullHeaders.getMaxSstat()));
 fheader.setIncSstat(headerValueMap.get(dugioFullHeaders.getIncSstat()));
 fheader.setFirstSstat(headerValueMap.get(dugioFullHeaders.getFirstSstat()));
 fheader.setLastSstat(headerValueMap.get(dugioFullHeaders.getLastSstat()));
 fheader.setMinGstat(headerValueMap.get(dugioFullHeaders.getMinGstat()));
 fheader.setMaxGstat(headerValueMap.get(dugioFullHeaders.getMaxGstat()));
 fheader.setIncGstat(headerValueMap.get(dugioFullHeaders.getIncGstat()));
 fheader.setFirstGstat(headerValueMap.get(dugioFullHeaders.getFirstGstat()));
 fheader.setLastGstat(headerValueMap.get(dugioFullHeaders.getLastGstat()));
 fheader.setMinLagb(headerValueMap.get(dugioFullHeaders.getMinLagb()));
 fheader.setMaxLagb(headerValueMap.get(dugioFullHeaders.getMaxLagb()));
 fheader.setIncLagb(headerValueMap.get(dugioFullHeaders.getIncLagb()));
 fheader.setFirstLagb(headerValueMap.get(dugioFullHeaders.getFirstLagb()));
 fheader.setLastLagb(headerValueMap.get(dugioFullHeaders.getLastLagb()));
 fheader.setMinNs(headerValueMap.get(dugioFullHeaders.getMinNs()));
 fheader.setMaxNs(headerValueMap.get(dugioFullHeaders.getMaxNs()));
 fheader.setIncNs(headerValueMap.get(dugioFullHeaders.getIncNs()));
 fheader.setFirstNs(headerValueMap.get(dugioFullHeaders.getFirstNs()));
 fheader.setLastNs(headerValueMap.get(dugioFullHeaders.getLastNs()));
 fheader.setNs(headerValueMap.get(dugioFullHeaders.getNs()));
 fheader.setMinDt(headerValueMap.get(dugioFullHeaders.getMinDt()));
 fheader.setMaxDt(headerValueMap.get(dugioFullHeaders.getMaxDt()));
 fheader.setIncDt(headerValueMap.get(dugioFullHeaders.getIncDt()));
 fheader.setFirstDt(headerValueMap.get(dugioFullHeaders.getFirstDt()));
 fheader.setLastDt(headerValueMap.get(dugioFullHeaders.getLastDt()));
 fheader.setDt(headerValueMap.get(dugioFullHeaders.getDt()));
 fheader.setMinSfs(headerValueMap.get(dugioFullHeaders.getMinSfs()));
 fheader.setMaxSfs(headerValueMap.get(dugioFullHeaders.getMaxSfs()));
 fheader.setIncSfs(headerValueMap.get(dugioFullHeaders.getIncSfs()));
 fheader.setFirstSfs(headerValueMap.get(dugioFullHeaders.getFirstSfs()));
 fheader.setLastSfs(headerValueMap.get(dugioFullHeaders.getLastSfs()));
 fheader.setMinSfe(headerValueMap.get(dugioFullHeaders.getMinSfe()));
 fheader.setMaxSfe(headerValueMap.get(dugioFullHeaders.getMaxSfe()));
 fheader.setIncSfe(headerValueMap.get(dugioFullHeaders.getIncSfe()));
 fheader.setFirstSfe(headerValueMap.get(dugioFullHeaders.getFirstSfe()));
 fheader.setLastSfe(headerValueMap.get(dugioFullHeaders.getLastSfe()));
 fheader.setMinSlen(headerValueMap.get(dugioFullHeaders.getMinSlen()));
 fheader.setMaxSlen(headerValueMap.get(dugioFullHeaders.getMaxSlen()));
 fheader.setIncSlen(headerValueMap.get(dugioFullHeaders.getIncSlen()));
 fheader.setFirstSlen(headerValueMap.get(dugioFullHeaders.getFirstSlen()));
 fheader.setLastSlen(headerValueMap.get(dugioFullHeaders.getLastSlen()));
 fheader.setSlen(headerValueMap.get(dugioFullHeaders.getSlen()));
 fheader.setMinStyp(headerValueMap.get(dugioFullHeaders.getMinStyp()));
 fheader.setMaxStyp(headerValueMap.get(dugioFullHeaders.getMaxStyp()));
 fheader.setIncStyp(headerValueMap.get(dugioFullHeaders.getIncStyp()));
 fheader.setFirstStyp(headerValueMap.get(dugioFullHeaders.getFirstStyp()));
 fheader.setLastStyp(headerValueMap.get(dugioFullHeaders.getLastStyp()));
 fheader.setMinStas(headerValueMap.get(dugioFullHeaders.getMinStas()));
 fheader.setMaxStas(headerValueMap.get(dugioFullHeaders.getMaxStas()));
 fheader.setIncStas(headerValueMap.get(dugioFullHeaders.getIncStas()));
 fheader.setFirstStas(headerValueMap.get(dugioFullHeaders.getFirstStas()));
 fheader.setLastStas(headerValueMap.get(dugioFullHeaders.getLastStas()));
 fheader.setMinStae(headerValueMap.get(dugioFullHeaders.getMinStae()));
 fheader.setMaxStae(headerValueMap.get(dugioFullHeaders.getMaxStae()));
 fheader.setIncStae(headerValueMap.get(dugioFullHeaders.getIncStae()));
 fheader.setFirstStae(headerValueMap.get(dugioFullHeaders.getFirstStae()));
 fheader.setLastStae(headerValueMap.get(dugioFullHeaders.getLastStae()));
 fheader.setStae(headerValueMap.get(dugioFullHeaders.getStae()));
 fheader.setMinAfilf(headerValueMap.get(dugioFullHeaders.getMinAfilf()));
 fheader.setMaxAfilf(headerValueMap.get(dugioFullHeaders.getMaxAfilf()));
 fheader.setIncAfilf(headerValueMap.get(dugioFullHeaders.getIncAfilf()));
 fheader.setFirstAfilf(headerValueMap.get(dugioFullHeaders.getFirstAfilf()));
 fheader.setLastAfilf(headerValueMap.get(dugioFullHeaders.getLastAfilf()));
 fheader.setAfilf(headerValueMap.get(dugioFullHeaders.getAfilf()));
 fheader.setMinAfils(headerValueMap.get(dugioFullHeaders.getMinAfils()));
 fheader.setMaxAfils(headerValueMap.get(dugioFullHeaders.getMaxAfils()));
 fheader.setIncAfils(headerValueMap.get(dugioFullHeaders.getIncAfils()));
 fheader.setFirstAfils(headerValueMap.get(dugioFullHeaders.getFirstAfils()));
 fheader.setLastAfils(headerValueMap.get(dugioFullHeaders.getLastAfils()));
 fheader.setAfils(headerValueMap.get(dugioFullHeaders.getAfils()));
 fheader.setMinLcf(headerValueMap.get(dugioFullHeaders.getMinLcf()));
 fheader.setMaxLcf(headerValueMap.get(dugioFullHeaders.getMaxLcf()));
 fheader.setIncLcf(headerValueMap.get(dugioFullHeaders.getIncLcf()));
 fheader.setFirstLcf(headerValueMap.get(dugioFullHeaders.getFirstLcf()));
 fheader.setLastLcf(headerValueMap.get(dugioFullHeaders.getLastLcf()));
 fheader.setLcf(headerValueMap.get(dugioFullHeaders.getLcf()));
 fheader.setMinLcs(headerValueMap.get(dugioFullHeaders.getMinLcs()));
 fheader.setMaxLcs(headerValueMap.get(dugioFullHeaders.getMaxLcs()));
 fheader.setIncLcs(headerValueMap.get(dugioFullHeaders.getIncLcs()));
 fheader.setFirstLcs(headerValueMap.get(dugioFullHeaders.getFirstLcs()));
 fheader.setLastLcs(headerValueMap.get(dugioFullHeaders.getLastLcs()));
 fheader.setLcs(headerValueMap.get(dugioFullHeaders.getLcs()));
 fheader.setMinHcs(headerValueMap.get(dugioFullHeaders.getMinHcs()));
 fheader.setMaxHcs(headerValueMap.get(dugioFullHeaders.getMaxHcs()));
 fheader.setIncHcs(headerValueMap.get(dugioFullHeaders.getIncHcs()));
 fheader.setFirstHcs(headerValueMap.get(dugioFullHeaders.getFirstHcs()));
 fheader.setLastHcs(headerValueMap.get(dugioFullHeaders.getLastHcs()));
 fheader.setMinYear(headerValueMap.get(dugioFullHeaders.getMinYear()));
 fheader.setMaxYear(headerValueMap.get(dugioFullHeaders.getMaxYear()));
 fheader.setIncYear(headerValueMap.get(dugioFullHeaders.getIncYear()));
 fheader.setFirstYear(headerValueMap.get(dugioFullHeaders.getFirstYear()));
 fheader.setLastYear(headerValueMap.get(dugioFullHeaders.getLastYear()));
 fheader.setYear(headerValueMap.get(dugioFullHeaders.getYear()));
 fheader.setMinDay(headerValueMap.get(dugioFullHeaders.getMinDay()));
 fheader.setMaxDay(headerValueMap.get(dugioFullHeaders.getMaxDay()));
 fheader.setIncDay(headerValueMap.get(dugioFullHeaders.getIncDay()));
 fheader.setFirstDay(headerValueMap.get(dugioFullHeaders.getFirstDay()));
 fheader.setLastDay(headerValueMap.get(dugioFullHeaders.getLastDay()));
 fheader.setDay(headerValueMap.get(dugioFullHeaders.getDay()));
 fheader.setMinHour(headerValueMap.get(dugioFullHeaders.getMinHour()));
 fheader.setMaxHour(headerValueMap.get(dugioFullHeaders.getMaxHour()));
 fheader.setIncHour(headerValueMap.get(dugioFullHeaders.getIncHour()));
 fheader.setFirstHour(headerValueMap.get(dugioFullHeaders.getFirstHour()));
 fheader.setLastHour(headerValueMap.get(dugioFullHeaders.getLastHour()));
 fheader.setMinMinute(headerValueMap.get(dugioFullHeaders.getMinMinute()));
 fheader.setMaxMinute(headerValueMap.get(dugioFullHeaders.getMaxMinute()));
 fheader.setIncMinute(headerValueMap.get(dugioFullHeaders.getIncMinute()));
 fheader.setFirstMinute(headerValueMap.get(dugioFullHeaders.getFirstMinute()));
 fheader.setLastMinute(headerValueMap.get(dugioFullHeaders.getLastMinute()));
 fheader.setMinSec(headerValueMap.get(dugioFullHeaders.getMinSec()));
 fheader.setMaxSec(headerValueMap.get(dugioFullHeaders.getMaxSec()));
 fheader.setIncSec(headerValueMap.get(dugioFullHeaders.getIncSec()));
 fheader.setFirstSec(headerValueMap.get(dugioFullHeaders.getFirstSec()));
 fheader.setLastSec(headerValueMap.get(dugioFullHeaders.getLastSec()));
 fheader.setMinGrnors(headerValueMap.get(dugioFullHeaders.getMinGrnors()));
 fheader.setMaxGrnors(headerValueMap.get(dugioFullHeaders.getMaxGrnors()));
 fheader.setIncGrnors(headerValueMap.get(dugioFullHeaders.getIncGrnors()));
 fheader.setFirstGrnors(headerValueMap.get(dugioFullHeaders.getFirstGrnors()));
 fheader.setLastGrnors(headerValueMap.get(dugioFullHeaders.getLastGrnors()));
 fheader.setGrnors(headerValueMap.get(dugioFullHeaders.getGrnors()));
 fheader.setMinGrnofr(headerValueMap.get(dugioFullHeaders.getMinGrnofr()));
 fheader.setMaxGrnofr(headerValueMap.get(dugioFullHeaders.getMaxGrnofr()));
 fheader.setIncGrnofr(headerValueMap.get(dugioFullHeaders.getIncGrnofr()));
 fheader.setFirstGrnofr(headerValueMap.get(dugioFullHeaders.getFirstGrnofr()));
 fheader.setLastGrnofr(headerValueMap.get(dugioFullHeaders.getLastGrnofr()));
 fheader.setGrnofr(headerValueMap.get(dugioFullHeaders.getGrnofr()));
 fheader.setMinGaps(headerValueMap.get(dugioFullHeaders.getMinGaps()));
 fheader.setMaxGaps(headerValueMap.get(dugioFullHeaders.getMaxGaps()));
 fheader.setIncGaps(headerValueMap.get(dugioFullHeaders.getIncGaps()));
 fheader.setFirstGaps(headerValueMap.get(dugioFullHeaders.getFirstGaps()));
 fheader.setLastGaps(headerValueMap.get(dugioFullHeaders.getLastGaps()));
 fheader.setMinOtrav(headerValueMap.get(dugioFullHeaders.getMinOtrav()));
 fheader.setMaxOtrav(headerValueMap.get(dugioFullHeaders.getMaxOtrav()));
 fheader.setIncOtrav(headerValueMap.get(dugioFullHeaders.getIncOtrav()));
 fheader.setFirstOtrav(headerValueMap.get(dugioFullHeaders.getFirstOtrav()));
 fheader.setLastOtrav(headerValueMap.get(dugioFullHeaders.getLastOtrav()));
 fheader.setMinCdpx(headerValueMap.get(dugioFullHeaders.getMinCdpx()));
 fheader.setMaxCdpx(headerValueMap.get(dugioFullHeaders.getMaxCdpx()));
 fheader.setIncCdpx(headerValueMap.get(dugioFullHeaders.getIncCdpx()));
 fheader.setFirstCdpx(headerValueMap.get(dugioFullHeaders.getFirstCdpx()));
 fheader.setLastCdpx(headerValueMap.get(dugioFullHeaders.getLastCdpx()));
 fheader.setMinCdpy(headerValueMap.get(dugioFullHeaders.getMinCdpy()));
 fheader.setMaxCdpy(headerValueMap.get(dugioFullHeaders.getMaxCdpy()));
 fheader.setIncCdpy(headerValueMap.get(dugioFullHeaders.getIncCdpy()));
 fheader.setFirstCdpy(headerValueMap.get(dugioFullHeaders.getFirstCdpy()));
 fheader.setLastCdpy(headerValueMap.get(dugioFullHeaders.getLastCdpy()));
 fheader.setMinInline(headerValueMap.get(dugioFullHeaders.getMinInline()));
 fheader.setMaxInline(headerValueMap.get(dugioFullHeaders.getMaxInline()));
 fheader.setIncInline(headerValueMap.get(dugioFullHeaders.getIncInline()));
 fheader.setFirstInline(headerValueMap.get(dugioFullHeaders.getFirstInline()));
 fheader.setLastInline(headerValueMap.get(dugioFullHeaders.getLastInline()));
 fheader.setMinCrossline(headerValueMap.get(dugioFullHeaders.getMinCrossline()));
 fheader.setMaxCrossline(headerValueMap.get(dugioFullHeaders.getMaxCrossline()));
 fheader.setIncCrossline(headerValueMap.get(dugioFullHeaders.getIncCrossline()));
 fheader.setFirstCrossline(headerValueMap.get(dugioFullHeaders.getFirstCrossline()));
 fheader.setLastCrossline(headerValueMap.get(dugioFullHeaders.getLastCrossline()));
 fheader.setMinShotpoint(headerValueMap.get(dugioFullHeaders.getMinShotpoint()));
 fheader.setMaxShotpoint(headerValueMap.get(dugioFullHeaders.getMaxShotpoint()));
 fheader.setIncShotpoint(headerValueMap.get(dugioFullHeaders.getIncShotpoint()));
 fheader.setFirstShotpoint(headerValueMap.get(dugioFullHeaders.getFirstShotpoint()));
 fheader.setLastShotpoint(headerValueMap.get(dugioFullHeaders.getLastShotpoint()));
 fheader.setMinScalsp(headerValueMap.get(dugioFullHeaders.getMinScalsp()));
 fheader.setMaxScalsp(headerValueMap.get(dugioFullHeaders.getMaxScalsp()));
 fheader.setIncScalsp(headerValueMap.get(dugioFullHeaders.getIncScalsp()));
 fheader.setFirstScalsp(headerValueMap.get(dugioFullHeaders.getFirstScalsp()));
 fheader.setLastScalsp(headerValueMap.get(dugioFullHeaders.getLastScalsp()));
 fheader.setMinTcm(headerValueMap.get(dugioFullHeaders.getMinTcm()));
 fheader.setMaxTcm(headerValueMap.get(dugioFullHeaders.getMaxTcm()));
 fheader.setIncTcm(headerValueMap.get(dugioFullHeaders.getIncTcm()));
 fheader.setFirstTcm(headerValueMap.get(dugioFullHeaders.getFirstTcm()));
 fheader.setLastTcm(headerValueMap.get(dugioFullHeaders.getLastTcm()));
 fheader.setMinTid(headerValueMap.get(dugioFullHeaders.getMinTid()));
 fheader.setMaxTid(headerValueMap.get(dugioFullHeaders.getMaxTid()));
 fheader.setIncTid(headerValueMap.get(dugioFullHeaders.getIncTid()));
 fheader.setFirstTid(headerValueMap.get(dugioFullHeaders.getFirstTid()));
 fheader.setLastTid(headerValueMap.get(dugioFullHeaders.getLastTid()));
 fheader.setTid(headerValueMap.get(dugioFullHeaders.getTid()));
 fheader.setMinSedm(headerValueMap.get(dugioFullHeaders.getMinSedm()));
 fheader.setMaxSedm(headerValueMap.get(dugioFullHeaders.getMaxSedm()));
 fheader.setIncSedm(headerValueMap.get(dugioFullHeaders.getIncSedm()));
 fheader.setFirstSedm(headerValueMap.get(dugioFullHeaders.getFirstSedm()));
 fheader.setLastSedm(headerValueMap.get(dugioFullHeaders.getLastSedm()));
 fheader.setMinSede(headerValueMap.get(dugioFullHeaders.getMinSede()));
 fheader.setMaxSede(headerValueMap.get(dugioFullHeaders.getMaxSede()));
 fheader.setIncSede(headerValueMap.get(dugioFullHeaders.getIncSede()));
 fheader.setFirstSede(headerValueMap.get(dugioFullHeaders.getFirstSede()));
 fheader.setLastSede(headerValueMap.get(dugioFullHeaders.getLastSede()));
 fheader.setSede(headerValueMap.get(dugioFullHeaders.getSede()));
 fheader.setMinSmm(headerValueMap.get(dugioFullHeaders.getMinSmm()));
 fheader.setMaxSmm(headerValueMap.get(dugioFullHeaders.getMaxSmm()));
 fheader.setIncSmm(headerValueMap.get(dugioFullHeaders.getIncSmm()));
 fheader.setFirstSmm(headerValueMap.get(dugioFullHeaders.getFirstSmm()));
 fheader.setLastSmm(headerValueMap.get(dugioFullHeaders.getLastSmm()));
 fheader.setMinSme(headerValueMap.get(dugioFullHeaders.getMinSme()));
 fheader.setMaxSme(headerValueMap.get(dugioFullHeaders.getMaxSme()));
 fheader.setIncSme(headerValueMap.get(dugioFullHeaders.getIncSme()));
 fheader.setFirstSme(headerValueMap.get(dugioFullHeaders.getFirstSme()));
 fheader.setLastSme(headerValueMap.get(dugioFullHeaders.getLastSme()));
 fheader.setMinSmu(headerValueMap.get(dugioFullHeaders.getMinSmu()));
 fheader.setMaxSmu(headerValueMap.get(dugioFullHeaders.getMaxSmu()));
 fheader.setIncSmu(headerValueMap.get(dugioFullHeaders.getIncSmu()));
 fheader.setFirstSmu(headerValueMap.get(dugioFullHeaders.getFirstSmu()));
 fheader.setLastSmu(headerValueMap.get(dugioFullHeaders.getLastSmu()));
 fheader.setMinUi1(headerValueMap.get(dugioFullHeaders.getMinUi1()));
 fheader.setMaxUi1(headerValueMap.get(dugioFullHeaders.getMaxUi1()));
 fheader.setIncUi1(headerValueMap.get(dugioFullHeaders.getIncUi1()));
 fheader.setFirstUi1(headerValueMap.get(dugioFullHeaders.getFirstUi1()));
 fheader.setLastUi1(headerValueMap.get(dugioFullHeaders.getLastUi1()));
 fheader.setMinUi2(headerValueMap.get(dugioFullHeaders.getMinUi2()));
 fheader.setMaxUi2(headerValueMap.get(dugioFullHeaders.getMaxUi2()));
 fheader.setIncUi2(headerValueMap.get(dugioFullHeaders.getIncUi2()));
 fheader.setFirstUi2(headerValueMap.get(dugioFullHeaders.getFirstUi2()));
 fheader.setLastUi2(headerValueMap.get(dugioFullHeaders.getLastUi2()));
 fheader.setUi2(headerValueMap.get(dugioFullHeaders.getUi2()));
 fheader.setMinVer(headerValueMap.get(dugioFullHeaders.getMinVer()));
 fheader.setMaxVer(headerValueMap.get(dugioFullHeaders.getMaxVer()));
 fheader.setIncVer(headerValueMap.get(dugioFullHeaders.getIncVer()));
 fheader.setSampleRate(headerValueMap.get(dugioFullHeaders.getSampleRate()));
 fheader.setRecordLength(headerValueMap.get(dugioFullHeaders.getRecordLength()));
 fheader.setUnitVer(headerValueMap.get(dugioFullHeaders.getUnitVer()));

           
 
 Log latestLog=this.job.getLatestLogForSubsurfaceMap().get(fheader.getSubsurface());              // the log table should be commited by now.
                    
                   // Log latestLog=logService.getLatestLogFor(hdr.getVolume(), hdr.getSubsurface());
                   /*  System.out.println("middleware.dugex.HeaderExtractor.populate(): for pheader "+
                   hdr.getHeaderId()+" Latest Log: "+
                   latestLog.getIdLogs());*/
                    if(latestLog!=null){
                    fheader.setInsightVersion(latestLog.getInsightVersion()); 
                    fheader.setWorkflowVersion(latestLog.getWorkflow().getWfversion()); 
                    fheader.setNumberOfRuns(latestLog.getVersion()+1); 
                    }
                    else{
                       fheader.setInsightVersion("ERROR"); 
                       fheader.setWorkflowVersion(-1L); 
                       fheader.setNumberOfRuns(-1L); 
                    }
           
           System.out.println("middleware.dugex.HeaderExtractor.populate(): finished extracting headers for : "+fheader.getSubsurface().getSubsurface());
     //  }
    }
    
    private Map<String, Long> extractFullHeaders(Fheader fheader) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Volume v=fheader.getVolume();
        String volumePath=v.getPathOfVolume();
        String subsurfaceName=fheader.getSubsurface().getSubsurface();
        Map<String,Long> fheaderMap=new HashMap<>();
        Class handler=DugioFullHeaders.class;
        Method[] methods=handler.getDeclaredMethods();
         for(Method m:methods){
             if(m.getName().startsWith("get") && m.getName().length()>3)
                //m.invoke(dugioFullHeaders);
             fheaderMap.put((String) m.invoke(dugioFullHeaders),0L);
         }
         Process process=new ProcessBuilder(dugioScripts.getDugioFullHeaderExtractor().getAbsolutePath(),volumePath,subsurfaceName).start();
                        InputStream is = process.getInputStream();
                        InputStreamReader isr=new InputStreamReader(is);
                        BufferedReader br=new BufferedReader(isr);
                        
                        String value;
                        while((value=br.readLine())!=null){   //script returns string of the form lastGwdep=276634
                            
                            String[] parts=value.split("=");
                            System.out.println("middleware.dugex.FullHeaderExtractor.extractFullHeaders(): "+value+" ==> "+parts[0]+" = "+parts[1]);
                            Long val=0L;
                            try{
                                val=Long.valueOf(parts[1]);
                            }catch(NumberFormatException nfe){
                                if(parts[1].length()>2 && parts[1].endsWith("ms")){
                                    String vs=parts[1].substring(0, parts[1].indexOf("m"));
                                    val=Long.valueOf(vs);
                                }else{
                                    val=0L;
                                }
                            }
                            fheaderMap.put(parts[0], val);
                           
                        }
                        System.out.println("middleware.dugex.FullHeaderExtractor.extractFullHeaders(): finished loop for "+subsurfaceName);
                        return fheaderMap;
    }
    
    private class FheaderHolder{
        SubsurfaceJob subjob;
        Fheader fheader;
    }
    
    
    private class VolumeSubsurfaceKey{
        Volume vol;
        Subsurface subsurface;

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 61 * hash + Objects.hashCode(this.vol);
            hash = 61 * hash + Objects.hashCode(this.subsurface);
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
            final VolumeSubsurfaceKey other = (VolumeSubsurfaceKey) obj;
            if (!Objects.equals(this.vol, other.vol)) {
                return false;
            }
            if (!Objects.equals(this.subsurface, other.subsurface)) {
                return false;
            }
            return true;
        }
        
        
        
    }
    
    private VolumeSubsurfaceKey generateVolumeSubsurfaceKey(Volume vol,Subsurface sub){
        VolumeSubsurfaceKey key=new VolumeSubsurfaceKey();
        key.vol=vol;
        key.subsurface=sub;
                
        
        return key;
    }
    
    
    private class SubsurfaceJobKey {
        Subsurface subsurface;
        Job job;

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 59 * hash + Objects.hashCode(this.subsurface);
            hash = 59 * hash + Objects.hashCode(this.job);
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
            final SubsurfaceJobKey other = (SubsurfaceJobKey) obj;
            if (!Objects.equals(this.subsurface, other.subsurface)) {
                return false;
            }
            if (!Objects.equals(this.job, other.job)) {
                return false;
            }
            return true;
        }
        
                
    }
    
    private SubsurfaceJobKey generateSubsurfaceJobKey(Subsurface sub,Job job){
        SubsurfaceJobKey key=new SubsurfaceJobKey();
        key.subsurface=sub;
        key.job=job;
        
        return key;
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
    
    private String timeNow(){
        return DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
    }

     private int processorsUsed() throws Exception{
        int procsUsed=(int) (Runtime.getRuntime().availableProcessors()*percentageOfProcessorsUsed);
               if(procsUsed <= 1){
                   System.out.println("middleware.dugex.HeaderExtractor.<init>(): Not enough resources . PR-TCount: "+procsUsed);
                   throw new Exception("Not enough resources . PR-TCount: "+procsUsed);
               }
        
               return procsUsed;
    }
}

