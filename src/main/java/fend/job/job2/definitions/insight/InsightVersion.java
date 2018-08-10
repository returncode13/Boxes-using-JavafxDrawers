/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job2.definitions.insight;

import app.properties.AppProperties;
import db.model.Job;
import db.model.QcMatrixRow;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.SubsurfaceJobService;
import db.services.SubsurfaceJobServiceImpl;
import fend.job.job0.JobType0Model;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class InsightVersion {
    private String insightVersion=new String();
    private BooleanProperty checkedByUser=new SimpleBooleanProperty(false); 
    private JobService jobService=new JobServiceImpl();
    private JobType0Model parentJob;
    private SubsurfaceJobService subsurfaceJobService=new SubsurfaceJobServiceImpl();
    int count=0;
    
    public InsightVersion(){
     
        checkedByUser.addListener(new ChangeListener<Boolean>(){
            
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println("InsightVersion.checkedByUser.changed(): "+insightVersion+" from "+oldValue+" to "+newValue);
               
               if(newValue){
                   
                  //  Job job=jobService.getJob(parentJob.getId());
                   Job job=parentJob.getDatabaseJob();
                    
                    String insightVersionsPresent=job.getInsightVersions();
                    if(insightVersionsPresent==null){
                        insightVersionsPresent=new String();
                    }
                    if(insightVersionsPresent.contains(insightVersion)){
                    }
                    else{
                        insightVersionsPresent+=";"+insightVersion+";";
                    }
                    
                    System.out.println("new Insight string: "+insightVersionsPresent);
                    job.setInsightVersions(insightVersionsPresent);
                    //jobService.updateJob(job.getId(), job);
                    jobService.updateInsightVersionInJob(job);
                   //  if(count>0)subsurfaceJobService.updateTimeWhereJobEquals(job, AppProperties.timeNow());     //the listener is called once during loading. count>1==> loading completed
                    parentJob.setDatabaseJob(job);
               }else{
                    //Job job=jobService.getJob(parentJob.getId());
                    Job job=parentJob.getDatabaseJob();
                    String insightVersionsPresent=job.getInsightVersions();
                    String parts[]=insightVersionsPresent.split(";");
                    String remakeInsightVersions=new String();
                    for(String part:parts){
                        if(part.equals(insightVersion)){
                            continue;
                        }else{
                            remakeInsightVersions+=part+";";
                        }
                    }
                    System.out.println("new Insight string: "+remakeInsightVersions);
                    job.setInsightVersions(remakeInsightVersions);
                    //jobService.updateJob(job.getId(), job);
                    jobService.updateInsightVersionInJob(job);
                   ///  if(count>0)subsurfaceJobService.updateTimeWhereJobEquals(job, AppProperties.timeNow());     //the listener is called once during loading. count>1==> loading completed
                    parentJob.setDatabaseJob(job);
               }
               
               
            }
            
        });
        
            count++;       //count=1 after load.
    }
    
    public String getInsightVersion() {
        return insightVersion;
    }

    public void setInsightVersion(String insightVersion) {
        this.insightVersion = insightVersion;
    }

    public JobType0Model getParentJob() {
        return parentJob;
    }

    public void setParentJob(JobType0Model parentJob) {
        this.parentJob = parentJob;
    }
    
     public Boolean getCheckedByUser() {
        return checkedByUser.get();
    }

    public void setCheckedByUser(Boolean checked) {
        if(checked==null)checked=false;
        this.checkedByUser.set(checked);
    }
    
}
