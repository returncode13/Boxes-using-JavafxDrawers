/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.edge.dotjobedge;

import fend.dot.anchor.AnchorModel;
import fend.dot.DotModel;
import fend.edge.edge.EdgeModel;
import fend.job.job0.JobType0Model;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DotJobEdgeModel implements EdgeModel{
    Long id=UUID.randomUUID().getMostSignificantBits();
   
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

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final DotJobEdgeModel other = (DotJobEdgeModel) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
   
    
}
