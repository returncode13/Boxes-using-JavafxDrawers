/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary;

import db.model.Job;
import db.model.Subsurface;
import db.model.Summary;
import db.services.SummaryService;
import db.services.SummaryServiceImpl;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.IO.IOCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Insight.InsightCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Qc.QcCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace.TraceCellModel;
import fend.summary.SummaryModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobSummaryModel {
    
    private Subsurface subsurface;
    private Job job;
    private TimeCellModel timeCellModel;
    private TraceCellModel traceCellModel;
    private QcCellModel qcCellModel;
    private InsightCellModel insightCellModel;
    private IOCellModel ioCellModell;
    private final BooleanProperty active = new SimpleBooleanProperty();    //if sequence is present in the job, then the active flag is set, unset otherwise
    private final BooleanProperty query = new SimpleBooleanProperty(false);      //toggling this flag will trigger a query in the db which in turn will set the values for qc,time,trace,insight,inheritance 
    private final BooleanProperty showOverride = new SimpleBooleanProperty();
    
    /* private TimeCellModel feModelTimeCellModel;                             //these are front end models. these are the ones that are updated for the UI. (colors) etc whenever the db is updated. Find a way to get rid of these.
    private TraceCellModel feModelTraceCellModel;
    
    */
    
    SummaryService summaryService=new SummaryServiceImpl();                     //** This view corresponding to this model is NEVER CAlled and therefore cannot attach a listener to it in the usual manner (i.e. Controller)
    
    
    final private SummaryModel summaryModel;

    public JobSummaryModel(SummaryModel summaryModel) {
        this.summaryModel = summaryModel;
        timeCellModel=new TimeCellModel();
        timeCellModel.setJobSummaryModel(this);
        
        traceCellModel=new TraceCellModel();
        traceCellModel.setJobSummaryModel(this);
        
        qcCellModel=new QcCellModel();
        qcCellModel.setJobSummaryModel(this);
        
        insightCellModel=new InsightCellModel();
        insightCellModel.setJobSummaryModel(this);
        
        ioCellModell=new IOCellModel();
        ioCellModell.setJobSummaryModel(this);
        this.query.addListener(QUERY_LISTENER);
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

    public QcCellModel getQcCellModel() {
        return qcCellModel;
    }

    public void setQcCellModel(QcCellModel qcCellModel) {
        this.qcCellModel = qcCellModel;
    }

    public InsightCellModel getInsightCellModel() {
        return insightCellModel;
    }

    public void setInsightCellModel(InsightCellModel insightCellModel) {
        this.insightCellModel = insightCellModel;
    }

    public IOCellModel getIoCellModel() {
        return ioCellModell;
    }

    public void setIoCellModell(IOCellModel ioCellModell) {
        this.ioCellModell = ioCellModell;
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
    
    public void toggleQuery(){
        query.set(!query.get());
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
    
    private ChangeListener<Boolean> QUERY_LISTENER=new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.: toggling QUERY_LISTENER for the time cell in "+JobSummaryModel.this.getSubsurface().getSubsurface()+" "+JobSummaryModel.this.getJob().getNameJobStep());
           //model.getTimeCellModel().setQuery(!model.getTimeCellModel().isQuery());
           
           Summary x=summaryService.getSummaryFor(JobSummaryModel.this.getSubsurface(), JobSummaryModel.this.getJob());
           //time
            
                        JobSummaryModel.this.getTimeCellModel().setFailedTimeDependency(x.hasFailedTimeDependency());
                        JobSummaryModel.this.getTimeCellModel().setInheritedTimeFail(x.hasInheritedTimeFail());
                        JobSummaryModel.this.getTimeCellModel().setInheritedTimeOverride(x.hasInheritedTimeOverride());
                        JobSummaryModel.this.getTimeCellModel().setOverridenTimeFail(x.hasOverridenTimeFail());
                        JobSummaryModel.this.getTimeCellModel().setWarningForTime(x.hasWarningForTime());
                        JobSummaryModel.this.getTimeCellModel().setQuery(!JobSummaryModel.this.getTimeCellModel().isQuery());                    //trigger the labelColors Call in the cells controller
            
           //trace
                        JobSummaryModel.this.getTraceCellModel().setFailedTraceDependency(x.hasFailedTraceDependency());
                        JobSummaryModel.this.getTraceCellModel().setInheritedTraceFail(x.hasInheritedTraceFail());
                        JobSummaryModel.this.getTraceCellModel().setInheritedTraceOverride(x.hasInheritedTraceOverride());
                        JobSummaryModel.this.getTraceCellModel().setOverridenTraceFail(x.hasOverridenTraceFail());
                        JobSummaryModel.this.getTraceCellModel().setWarningForTrace(x.hasWarningForTrace());
                        JobSummaryModel.this.getTraceCellModel().setQuery(!JobSummaryModel.this.getTraceCellModel().isQuery());                 //trigger the labelColors Call in the cells controller
           
           //qc
                        JobSummaryModel.this.getQcCellModel().setFailedQcDependency(x.hasFailedQcDependency());
                        JobSummaryModel.this.getQcCellModel().setInheritedQcFail(x.hasInheritedQcFail());
                        JobSummaryModel.this.getQcCellModel().setInheritedQcOverride(x.hasInheritedQcOverride());
                        JobSummaryModel.this.getQcCellModel().setOverridenQcFail(x.hasOverridenQcFail());
                        JobSummaryModel.this.getQcCellModel().setWarningForQc(x.hasWarningForQc());
                        JobSummaryModel.this.getQcCellModel().setQuery(!JobSummaryModel.this.getQcCellModel().isQuery());                      //trigger the labelColors Call in the cells controller
           //insight
                        JobSummaryModel.this.getInsightCellModel().setFailedInsightDependency(x.hasFailedInsightDependency());
                        JobSummaryModel.this.getInsightCellModel().setInheritedInsightFail(x.hasInheritedInsightFail());
                        JobSummaryModel.this.getInsightCellModel().setInheritedInsightOverride(x.hasInheritedInsightOverride());
                        JobSummaryModel.this.getInsightCellModel().setOverridenInsightFail(x.hasOverridenInsightFail());
                        JobSummaryModel.this.getInsightCellModel().setWarningForInsight(x.hasWarningForInsight());
                        JobSummaryModel.this.getInsightCellModel().setQuery(!JobSummaryModel.this.getInsightCellModel().isQuery());//trigger the labelColors Call in the cells controller
           //io
                        JobSummaryModel.this.getIoCellModel().setFailedIoDependency(x.hasFailedIoDependency());
                        JobSummaryModel.this.getIoCellModel().setInheritedIoFail(x.hasInheritedIoFail());
                        JobSummaryModel.this.getIoCellModel().setInheritedIoOverride(x.hasInheritedIoOverride());
                        JobSummaryModel.this.getIoCellModel().setOverridenIoFail(x.hasOverridenIoFail());
                        JobSummaryModel.this.getIoCellModel().setWarningForIo(x.hasWarningForIo());
                        JobSummaryModel.this.getIoCellModel().setQuery(!JobSummaryModel.this.getIoCellModel().isQuery());//trigger the labelColors Call in the cells controller
            
        }
        
    };
}
