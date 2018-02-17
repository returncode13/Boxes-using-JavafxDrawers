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

    
    /**
     * Status variables. set only when there's a doubt. 
     * Two status states: DoubtStatusModel.OVERRIDE and DoubtStatusModel.YES
     */
    private String timeStatus;
    private String tracesStatus;
    private String qcStatus;
    private String insightStatus;
    //private String inheritanceStatus;                       //hopefully never have to use this!
    
    
    
    /***
     * State variables: 
     * 3 states to a each type.
     * GOOD.     (when no doubt exists. isT()==false  for type T)
     * WARNING.  (when doubt exists isT()==true for type T)
     * ERROR.    (when doubt exists isT()==true for type T)
     **/
    
    private String timeState;
    private String tracesState;
    private String qcState;
    private String insightState;
    
    
    
    /**
     * Status getters/setters
     **/
    

    public String getTimeStatus() {
        return timeStatus;
    }

    public void setTimeStatus(String timeStatus) {
        this.timeStatus = timeStatus;
    }

    public String getTracesStatus() {
        return tracesStatus;
    }

    public void setTracesStatus(String tracesStatus) {
        this.tracesStatus = tracesStatus;
    }

    public String getQcStatus() {
        return qcStatus;
    }

    public void setQcStatus(String qcStatus) {
        this.qcStatus = qcStatus;
    }

    public String getInsightStatus() {
        return insightStatus;
    }

    public void setInsightStatus(String insightStatus) {
        this.insightStatus = insightStatus;
    }

    /* public String getInheritanceStatus() {
    return inheritanceStatus;
    }
    
    public void setInheritanceStatus(String inheritanceStatus) {
    this.inheritanceStatus = inheritanceStatus;
    }
    */
    public String getTimeState() {
        return timeState;
    }

    public void setTimeState(String timeState) {
        this.timeState = timeState;
    }

    public String getTracesState() {
        return tracesState;
    }

    public void setTracesState(String tracesState) {
        this.tracesState = tracesState;
    }

    public String getQcState() {
        return qcState;
    }

    public void setQcState(String qcState) {
        this.qcState = qcState;
    }

    public String getInsightState() {
        return insightState;
    }

    /***
     * State getters/setters
     **/
    public void setInsightState(String insightState) {
        this.insightState = insightState;
    }

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
