/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.override;

import db.model.Doubt;
import db.model.DoubtStatus;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.CellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class OverrideModel {
   
    //private JobSummaryModel jobSummaryModel;
    private CellModel cellModel;
    private Doubt doubt;
    private DoubtStatus doubtStatus;
    
    private String typeText;
    private String subsurfaceName;
    private String linkDescription;
    private String parentJobName;
    private String doubtStatusComment;
    private String status;
    private String userCommentStack;
    private String earlierStatus;
    private BooleanProperty confirmation;

    public OverrideModel(JobSummaryModel jbsm) {
     //   jobSummaryModel=jbsm;
        confirmation=new SimpleBooleanProperty(false);
        
    }
     public OverrideModel(CellModel cellm) {
     //   jobSummaryModel=jbsm;
     cellModel=cellm;
        confirmation=new SimpleBooleanProperty(false);
        
    }

    /*public JobSummaryModel getJobSummaryModel() {
    return jobSummaryModel;
    }
    
    public void setJobSummaryModel(JobSummaryModel jobSummaryModel) {
    this.jobSummaryModel = jobSummaryModel;
    }*/
    
    
    
    public Boolean getConfirmation() {
        return confirmation.get();
    }

    public void setConfirmation(Boolean confirm) {
        this.confirmation.set(confirm);
    }
    
    public BooleanProperty confirmationProperty(){
        return confirmation;
    }
    
    
    
    
    public Doubt getDoubt() {
        return doubt;
    }

    public void setDoubt(Doubt doubt) {
        this.doubt = doubt;
    }

    public DoubtStatus getDoubtStatus() {
        return doubtStatus;
    }

    public void setDoubtStatus(DoubtStatus doubtStatus) {
        this.doubtStatus = doubtStatus;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public String getSubsurfaceName() {
        return subsurfaceName;
    }

    public void setSubsurfaceName(String subsurfaceName) {
        this.subsurfaceName = subsurfaceName;
    }

    public String getLinkDescription() {
        return linkDescription;
    }

    public void setLinkDescription(String linkDescription) {
        this.linkDescription = linkDescription;
    }

    public String getParentJobName() {
        return parentJobName;
    }

    public void setParentJobName(String parentJobName) {
        this.parentJobName = parentJobName;
    }

    public String getDoubtStatusComment() {
        return doubtStatusComment;
    }

    public void setDoubtStatusComment(String doubtStatusComment) {
        this.doubtStatusComment = doubtStatusComment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        earlierStatus=this.status;
        this.status = status;
    }

    public String getUserCommentStack() {
        return userCommentStack;
    }

    public void setUserCommentStack(String userCommentStack) {
        this.userCommentStack = userCommentStack;
    }
    
    public void addToUserCommentStack(String userCommentStack) {
         
         this.userCommentStack += "@User: @Time @From "+earlierStatus+" --> "+this.status+" "+ userCommentStack+"\n";
    }

    public String getEarlierStatus() {
        return earlierStatus;
    }

    public void setEarlierStatus(String earlierStatus) {
        this.earlierStatus = earlierStatus;
    }

    public CellModel getCellModel() {
        return cellModel;
    }

    public void setCellModel(CellModel cellModel) {
        this.cellModel = cellModel;
    }
    
    
    
           
    
}
