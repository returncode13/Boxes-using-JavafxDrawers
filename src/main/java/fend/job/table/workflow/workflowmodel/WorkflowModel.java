/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.workflow.workflowmodel;

import db.model.Workflow;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkflowModel {
    Long version;
    String comment;
    Boolean control;
    String commentStack;
    Workflow workflow;
     BooleanProperty currentVersionProperty=new SimpleBooleanProperty(false);
    
    public BooleanProperty currentVersionProperty() {
    return currentVersionProperty;
    }
    
    public void setCurrentVersionProperty(Boolean c) {
    this.currentVersionProperty.set(c);
    }
    
    public boolean isCurrentVersion(){
    return this.currentVersionProperty.get();
    }
    
    
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getComment() {
        return comment;
    }
    
    public String getCommentStack(){
        return commentStack;
    }

    
    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCommentStack(String commentStack) {
        this.commentStack = commentStack;
        if(this.commentStack.isEmpty()){
            System.out.println("fend.job.table.workflow.workflowmodel.WorkflowModel.setCommentStack(): EMPTY COMMENT STACK!");
            comment="";
        }else{
            System.out.println("fend.job.table.workflow.workflowmodel.WorkflowModel.setCommentStack(): commentStack  found: "+commentStack);
            String usertimeComment=this.commentStack.split("\n")[0];
            int start=usertimeComment.indexOf(">",usertimeComment.indexOf(">")+1);
            String latestComment=usertimeComment.substring(start+1);
            System.out.println("fend.job.table.workflow.workflowmodel.WorkflowModel.setCommentStack(): Setting comment to "+latestComment);
            comment=latestComment;
        }
    }
    
    

    public Boolean getControl() {
        return control;
    }
    
    private BooleanProperty indeterminateProperty=new SimpleBooleanProperty(false);
    private BooleanProperty checkedProperty=new SimpleBooleanProperty(true);

    public BooleanProperty indeterminateProperty() {
        return indeterminateProperty;
    }

    public void setIndeterminateProperty(Boolean i) {
        this.indeterminateProperty.set(i);
    }

    public BooleanProperty checkedProperty() {
        return checkedProperty;
    }

    public void setCheckedProperty(Boolean c) {
        this.checkedProperty.set(c);
    }
    
    

    public void setControl(Boolean control) {
        this.control = control;
        if(this.control==null){
            setIndeterminateProperty(true);
        }else{
            setCheckedProperty(control);
        }
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }
    
    
}
