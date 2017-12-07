/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.edge.dotjobedge;

import fend.dot.anchor.AnchorModel;
import fend.dot.DotModel;
import fend.job.job0.JobType0Model;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DotJobEdgeModel {
    
   
    DotModel dotModel;      //the dotmodel from which the link originates
    JobType0Model childJob;        //the child box on which the anchor will be dropped
    AnchorModel anchorModel;    //the anchor to be dropped

    public DotModel getDotModel() {
        return dotModel;
    }

    public void setDotModel(DotModel dotModel) {
        this.dotModel = dotModel;
    }

    public AnchorModel getAnchorModel() {
        if(anchorModel==null){
            anchorModel=new AnchorModel();
        }
        return anchorModel;
    }

    public void setAnchorModel(AnchorModel anchorModel) {
        this.anchorModel = anchorModel;
    }

    public JobType0Model getChildJob() {
        return childJob;
    }

    public void setChildJob(JobType0Model childBox) {
        this.childJob = childBox;
        this.dotModel.addToChildren(this.childJob);
    }
    
    
    

   
    
}
