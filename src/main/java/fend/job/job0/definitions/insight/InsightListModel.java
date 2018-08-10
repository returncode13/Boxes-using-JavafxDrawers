/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job0.definitions.insight;

import db.model.Job;
import db.services.JobService;
import db.services.JobServiceImpl;
import fend.job.job0.JobType0Model;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class InsightListModel {
    private List<InsightVersion> insightVersionList;
    private ObservableList<InsightVersion> observableInsightList;
    private JobType0Model parentJob;

    public InsightListModel(JobType0Model parentJob) {
        this.parentJob = parentJob;
        insightVersionList=new ArrayList<>();
        observableInsightList=FXCollections.observableArrayList(insightVersionList);
        
    }

    public JobType0Model getParentJob() {
        return parentJob;
    }

    public ObservableList<InsightVersion> getInsightVersionList() {
        return observableInsightList;
    }

    public void setInsightVersionList(List<InsightVersion> insightVersionList) {
        this.observableInsightList=FXCollections.observableArrayList(insightVersionList);
    }
    
    public void addToInsightVersionList(InsightVersion ins){
        this.observableInsightList.add(ins);
    }
    
    public void removeFromInsightVersionList(InsightVersion ins){
        this.observableInsightList.remove(ins);
    }

   
    
    
    
}
