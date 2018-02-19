/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary_new;

import db.model.Job;
import db.model.Subsurface;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace.TraceCellModel;
import fend.summary.SummaryModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobSummaryModel {
    
    private Subsurface subsurface;
    private Job job;
    private TimeCellModel timeCellModel;
    private TraceCellModel traceCellModel;
    private final BooleanProperty active = new SimpleBooleanProperty();    //if sequence is present in the job, then the active flag is set, unset otherwise
    private final BooleanProperty query = new SimpleBooleanProperty(false);      //toggling this flag will trigger a query in the db which in turn will set the values for qc,time,trace,insight,inheritance 
    private final BooleanProperty showOverride = new SimpleBooleanProperty();
    
    private TimeCellModel feModelTimeCellModel;                             //these are front end models. these are the ones that are updated for the UI. (colors) etc whenever the db is updated. Find a way to get rid of these.
    private TraceCellModel feModelTraceCellModel;
    
    
    
    
    
    
    final private SummaryModel summaryModel;

    public JobSummaryModel(SummaryModel summaryModel) {
        this.summaryModel = summaryModel;
        timeCellModel=new TimeCellModel();
        timeCellModel.setJobSummaryModel(this);
        
        traceCellModel=new TraceCellModel();
        traceCellModel.setJobSummaryModel(this);
    }
    
    public SummaryModel getSummaryModel() {
        return summaryModel;
    }
    
    
    
    
    
    
    public Subsurface getSubsurface() {
        return subsurface;
    }

    public void setSubsurface(Subsurface subsurface) {
        this.subsurface = subsurface;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    
    
    
    public TimeCellModel getTimeCellModel() {
        return timeCellModel;
    }

    public void setTimeCellModel(TimeCellModel timeCellModel) {
        this.timeCellModel = timeCellModel;
    }

    public TraceCellModel getTraceCellModel() {
        return traceCellModel;
    }

    public void setTraceCellModel(TraceCellModel traceCellModel) {
        this.traceCellModel = traceCellModel;
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
    
    /**
     * Flag used to query db
     **/
    
    public boolean isQuery() {
        return query.get();
    }

    public void setQuery(boolean value) {
        query.set(value);
    }

    public BooleanProperty queryProperty() {
        return query;
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
    
    
    public TimeCellModel getFeModelTimeCellModel() {
        return feModelTimeCellModel;
    }

    public void setFeModelTimeCellModel(TimeCellModel feModelTimeCellModel) {
        this.feModelTimeCellModel = feModelTimeCellModel;
    }
    
    public TraceCellModel getFeModelTraceCellModel() {
        return feModelTraceCellModel;
    }

    public void setFeModelTraceCellModel(TraceCellModel feModelTraceCellModel) {
        this.feModelTraceCellModel = feModelTraceCellModel;
    }
    
}
