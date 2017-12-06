/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edge.parentchildedge;

import dot.anchor.AnchorModel;

import dot.DotModel;
import job.job0.JobType0Model;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ParentChildEdgeModel {
    JobType0Model parentJob;
    DotModel dotModel;
    AnchorModel childAnchorModel;               //Anchor dropped on the child box
    JobType0Model childJob;

    public JobType0Model getParentJob() {
        return parentJob;
    }

    public void setParentJob(JobType0Model parentJob) {
        this.parentJob = parentJob;
    }

    public DotModel getDotModel() {
        if(dotModel==null){
            dotModel=new DotModel();
            dotModel.addToParents(parentJob);
        }
        return dotModel;
    }

    public void setDotModel(DotModel dotModel) {
        this.dotModel = dotModel;
        this.dotModel.addToParents(parentJob);
    }

    public AnchorModel getChildAnchorModel() {
        if(childAnchorModel==null){
            childAnchorModel=new AnchorModel();
            
        }
        return childAnchorModel;
    }

    public void setChildAnchorModel(AnchorModel childAnchorModel) {
        this.childAnchorModel = childAnchorModel;
    }

    public JobType0Model getChildJob() {
        return childJob;
    }

    public void setChildJob(JobType0Model childJob) {
        this.childJob = childJob;
        this.dotModel.addToChildren(this.childJob);
    }
    
    
    
}
