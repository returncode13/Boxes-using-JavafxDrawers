/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.dugex;

import middleware.watcher.*;
import db.model.Log;
import db.model.Volume;
import db.model.Workflow;
import db.services.LogServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import db.services.WorkflowService;
import db.services.WorkflowServiceImpl;
import middleware.dugex.DugioScripts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import db.services.LogService;
import fend.volume.volume0.Volume0;

/*
 * @author sharath nair
 */
class WorkflowHolder{
    String md5;
    String context;
    
    
}
public class WorkflowManager {
    
    private Volume volume;
    private final VolumeService vserv=new VolumeServiceImpl();
    private final LogService lserv=new LogServiceImpl();
    private final WorkflowService wserv=new WorkflowServiceImpl();
    private List<Log> loglist;
    private Map<Log,WorkflowHolder> mlogwfholder=new HashMap<>();
    private WorkflowHolder workflowHolder;
    private MessageDigest md;
    private DugioScripts dugioscripts;
    /* private TimerTask task;
    private Timer timer;
    */
    
    
    public WorkflowManager(Volume volume)  {
        this.volume = volume;
        dugioscripts=new DugioScripts();
        watchForWorkflows();
    }
    
    private void watchForWorkflows(){
        
          Workflow wnull=null;
        loglist=lserv.getLogsFor(volume,wnull);
        try {
            
         ExecutorService executorService= Executors.newFixedThreadPool(1);
        if(volume.getVolumeType().equals(Volume0.PROCESS_2D)){
            
        
            executorService.submit(new Callable<Void>(){
             @Override
             public Void call() throws Exception {
                for (Iterator<Log> iterator = loglist.iterator(); iterator.hasNext();) {
            
                Log next = iterator.next();
                String logpath=next.getLogpath();
                Process process=new ProcessBuilder(dugioscripts.getWorkflowExtractor().getAbsolutePath(),logpath).start();
                InputStream is = process.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br=new BufferedReader(isr);
                workflowHolder=new WorkflowHolder();
                String value;
                String content=new String();
                while((value=br.readLine())!=null){
                   // System.out.println("watcher.WorkflowManager.init<>.call(): "+value);
                    content+=value;
                    content+="\n";
                };
                
                workflowHolder.context=content;
                md=MessageDigest.getInstance("MD5");
                byte[] bytesofContent=workflowHolder.context.getBytes("UTF8");
                md.update(workflowHolder.context.getBytes("UTF8"));
                byte[] digest=md.digest();
                StringBuffer sbuf=new StringBuffer();
                for(byte b:digest){
                    sbuf.append(String.format("%02x", b & 0xff));
                }
                workflowHolder.md5=new String(sbuf.toString());
                   // System.out.println("watcher.WorkflowManager.init<>.call(): checking for  : md5"+workflowHolder.md5+" and content size: "+workflowHolder.context.length());
                List<Workflow> wlist=wserv.getWorkFlowWith(workflowHolder.md5, WorkflowManager.this.volume);
                
                if(wlist==null){                    // no workflow for vol with md5, so create a new entry for such a workflow
                    Workflow wver=wserv.getWorkFlowVersionFor(WorkflowManager.this.volume); //get the  workflow with the highest version for this volume
                    Long vers=-1L;
                    if(wver==null){
                        vers=0L;
                    }
                    else{
                        vers=wver.getWfversion();               //get the version from the workflow
                    }
                    Workflow newWorkflow=new Workflow();
                    newWorkflow.setContents(workflowHolder.context);
                    newWorkflow.setMd5sum(workflowHolder.md5);
                    newWorkflow.setWfversion(++vers);       //increment the version
                    newWorkflow.setVolume(WorkflowManager.this.volume);
                    System.out.println("middleware.dugex.WorkflowManager.watchForWorkflows(): creating entry with : md5 "+workflowHolder.md5+" and version: "+vers);
                    wserv.createWorkFlow(newWorkflow);      //create the workflow in the db table
                    next.setWorkflow(newWorkflow);          //set the log in question to have this new workflow
                }
                else{                                       //found a workflow for vol with md5 . use that 
                    Workflow w=wlist.get(0);                //there should be just one such entry. Put a check for this 
                    next.setWorkflow(w);                    //the log in question now has the workflow
                    
                }
                
             //   mlogwfholder.put(next, workflowHolder);
                    System.out.println("middleware.dugex.WorkflowManager.watchForWorkflows(): updating logs");
            lserv.updateLogs(next.getIdLogs(), next);       //update all these logs . all logs now have a workflow assigned
             }
                return null;
             }
                
         }).get();
        }
        
        if(volume.getVolumeType().equals(2L)){
            System.out.println("Started middleware.dugex.WorkflowManager.watchForWorkflows(): loglist Size: "+loglist.size());
        
            executorService.submit(new Callable<Void>(){
             @Override
             public Void call() throws Exception {
             
                String logpath=volume.getPathOfVolume();                //type 2 workflow information stored in volPath/notes.txt  . the path to notes.txt is hardcoded in the dugioscript
            
                Process process=new ProcessBuilder(dugioscripts.getSegdLoadNotesTxtTimeWorkflowExtractor().getAbsolutePath(),logpath).start();
            
                InputStream is = process.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br=new BufferedReader(isr);
                workflowHolder=new WorkflowHolder();
           
                String value;
                String content=new String();
                String delimiter="Contents:";  //Split time and contents here. This is based on a word hardcoded in the extraction script
                String time =new String();
               //  System.out.println(".call(): about to read the results from the script");
                while((value=br.readLine())!=null){
                  //  System.out.println("watcher.WorkflowManager.init<>.call(): "+value);
                    time= value.substring(0,value.indexOf(delimiter));
                  //  System.out.println(".call(): time inside: "+time);
                    content+=value.substring(value.indexOf(delimiter)+delimiter.length(),value.length());
                 
                };
              //      System.out.println("watcher.WorkflowManager.watchForWorkflows().SGDLOAD Volume: call(): Time: "+time);
              //      System.out.println("watcher.WorkflowManager.watchForWorkflows().SGDLOAD Volume: call(): Content:****"+content+"*****END");
                workflowHolder.context=content;
                md=MessageDigest.getInstance("MD5");
                byte[] bytesofContent=workflowHolder.context.getBytes("UTF8");
                md.update(workflowHolder.context.getBytes("UTF8"));
                byte[] digest=md.digest();
                StringBuffer sbuf=new StringBuffer();
                for(byte b:digest){
                    sbuf.append(String.format("%02x", b & 0xff));
                }
                workflowHolder.md5=new String(sbuf.toString());
                 //   System.out.println("watcher.WorkflowManager.watchForWorkflows().SGDLOAD Volume: call(): MD5: "+md);
                   // System.out.println("watcher.WorkflowManager.init<>.call(): checking for  : md5"+workflowHolder.md5+" and content size: "+workflowHolder.context.length());
                   List<Workflow> wlist=wserv.getWorkFlowWith(workflowHolder.md5, WorkflowManager.this.volume);
                   Workflow wfForLog=null;
                   if(wlist==null){                    // no workflow for vol with md5, so create a new entry for such a workflow
                   Workflow wver=wserv.getWorkFlowVersionFor(WorkflowManager.this.volume); //get the  workflow with the highest version for this volume
                   Long vers=-1L;
                   if(wver==null){
                   vers=0L;
                   }
                   else{
                   vers=wver.getWfversion();               //get the version from the workflow
                   }
                   Workflow newWorkflow=new Workflow();
                   newWorkflow.setContents(workflowHolder.context);
                   newWorkflow.setMd5sum(workflowHolder.md5);
                   newWorkflow.setWfversion(++vers);       //increment the version
                   newWorkflow.setVolume(WorkflowManager.this.volume);
                 //  System.out.println("watcher.WorkflowManager.init<>.call(): creating entry with : md5 "+workflowHolder.md5+" and version: "+vers);
                   wserv.createWorkFlow(newWorkflow);      //create the workflow in the db table
                   wfForLog=newWorkflow;      //this will be the logs new workflow
                   }
                   else{                                       //found a workflow for vol with md5 . use that
                    wfForLog=wlist.get(0);                //there should be just one such entry. Put a check for this
                //   next.setWorkflow(w);                    //the log in question now has the workflow
                   
                   }
                   
                   //one bulk update will speed this up.
                   System.out.println("middleware.dugex.WorkflowManager.watchForWorkflows(): updating "+loglist.size()+" logs with workflow entry "+wfForLog.getId());
                   lserv.bulkUpdateOnLogs(volume, wfForLog);
                   System.out.println("middleware.dugex.WorkflowManager.watchForWorkflows(): finished updating "+loglist.size()+" logs with workflow entry "+wfForLog.getId());
                   /* for(Log log:loglist){
                   // System.out.println(".call(): updating logs for "+log.getSubsurfaces()+" with workflow: "+wfForLog.getIdworkflows());
                   log.setWorkflow(wfForLog);
                   lserv.updateLogs(log.getIdLogs(), log);
                   }*/
                   
                   //   mlogwfholder.put(next, workflowHolder);
                   
                 //  lserv.updateLogs(next.getIdLogs(), next);       //update all these logs . all logs now have a workflow assigned*/   //UNOMMENT THIS
          //   }
                return null;
             }
                
         }).get();
        }
            
        
        } catch (InterruptedException ex) {
            Logger.getLogger(WorkflowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(WorkflowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
