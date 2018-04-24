/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.edge.parentchildedge;

import fend.dot.anchor.AnchorModel;

import fend.dot.DotModel;
import fend.edge.edge.EdgeModel;
import fend.job.job0.JobType0Model;
import java.util.Objects;
import java.util.UUID;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ParentChildEdgeModel implements EdgeModel {
    Long id=UUID.randomUUID().getMostSignificantBits();
    JobType0Model parentJob;
    DotModel dotModel;
    AnchorModel childAnchorModel;               //Anchor dropped on the child box
    JobType0Model childJob;
    BooleanProperty dropSuccessFul=new SimpleBooleanProperty(false);

    public BooleanProperty dropSuccessFulProperty() {
        return dropSuccessFul;
    }

    public void setDropSuccessFul(Boolean drop) {
        this.dropSuccessFul.set(drop);
        
    }
    
    
    
    public JobType0Model getParentJob() {
        return parentJob;
    }

    public void setParentJob(JobType0Model parentJob) {
        this.parentJob = parentJob;
    }

    public DotModel getDotModel() {
        if(dotModel==null){
            dotModel=new DotModel(this.parentJob.getWorkspaceModel());
          //  dotModel.addToParents(parentJob);
        }
        return dotModel;
    }

    public void setDotModel(DotModel dotModel) {
        this.dotModel = dotModel;
     //   this.dotModel.addToParents(parentJob);
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
//        this.dotModel.addToChildren(this.childJob);
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final ParentChildEdgeModel other = (ParentChildEdgeModel) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
