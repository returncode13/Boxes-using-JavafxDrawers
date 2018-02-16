/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary;

import db.model.Job;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.Summary;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobSummaryModel {
   // private Summary summary;           
    private Sequence sequence;                                              //the sequence whos status this cell represents
    private Subsurface subsurface;
    private Job job;                                                        //the job that this cell will represent
    private final BooleanProperty qc = new SimpleBooleanProperty();         //qc is true implies no doubt.
    private final BooleanProperty time = new SimpleBooleanProperty();       //time is true implies no doubt.
    private final BooleanProperty trace = new SimpleBooleanProperty();      //trace is true implies no doubt.
    private final BooleanProperty insight = new SimpleBooleanProperty();    //insight is true implies no doubt.
    private final BooleanProperty inheritance = new SimpleBooleanProperty();//inheritance is true implies no doubt.
    private final BooleanProperty active = new SimpleBooleanProperty();    //if sequence is present in the job, then the active flag is set, unset otherwise

    private String contextAskedForDoubtType=null;
    private final BooleanProperty showOverride = new SimpleBooleanProperty();

  
    
    
    
    
    
    //Used to show override menu in the JobSummaryController
    public boolean isShowOverride() {
        return showOverride.get();
    }

    public void setShowOverride(boolean value) {
        showOverride.set(value);
    }

    public BooleanProperty showOverrideProperty() {
        return showOverride;
    }
    
    
    
    public boolean isActive() {
        return active.get();
    }

    public void setActive(boolean value) {
        active.set(value);
    }

    public BooleanProperty activeProperty() {
        return active;
    }
    
    
    
    
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    /*public Summary getSummary() {
    return summary;
    }
    
    public void setSummary(Summary summary) {
    this.summary = summary;
    }*/
    
    
    
    
    //Qc getter and setter
    public boolean isQc() {
        return qc.get();
    }

    public void setQc(boolean value) {
        qc.set(value);
    }

    public BooleanProperty qcProperty() {
        return qc;
    }
    
    
    //Time getter and setter
     public boolean isTime() {
        return time.get();
    }

    public void setTime(boolean value) {
        time.set(value);
    }

    public BooleanProperty timeProperty() {
        return time;
    }
    
    //Trace getter and setter
    public boolean isTrace() {
        return trace.get();
    }

    public void setTrace(boolean value) {
        trace.set(value);
    }

    public BooleanProperty traceProperty() {
        return trace;
    }
    
    
    //Insight getter and setter
    public boolean isInsight() {
        return insight.get();
    }

    public void setInsight(boolean value) {
        insight.set(value);
    }

    public BooleanProperty insightProperty() {
        return insight;
    }
    
    
    
    //Inheritance getter and setter
    public boolean isInheritance() {
        return inheritance.get();
    }

    public void setInheritance(boolean value) {
        inheritance.set(value);
    }

    public BooleanProperty inheritanceProperty() {
        return inheritance;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Subsurface getSubsurface() {
        return subsurface;
    }

    public void setSubsurface(Subsurface subsurface) {
        this.subsurface = subsurface;
    }

    public Boolean isInDoubt(){
        
        return this.isTime()||this.isTrace()||this.isQc()||this.isInsight()||this.isInheritance();
    }

    public String getContextAskedForDoubtType() {
        return contextAskedForDoubtType;
    }

    public void setContextAskedForDoubtType(String contextAskedForDoubtType) {
        this.contextAskedForDoubtType = contextAskedForDoubtType;
    }
    
    
    
}
