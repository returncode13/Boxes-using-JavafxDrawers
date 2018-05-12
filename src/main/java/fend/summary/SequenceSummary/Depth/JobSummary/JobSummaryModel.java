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
import db.services.SummaryService;
import db.services.SummaryServiceImpl;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.CellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.CellState;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.IO.IOCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Insight.InsightCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Qc.QcCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace.TraceCellModel;
import fend.summary.SummaryModel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobSummaryModel {
    
    private Sequence sequence;
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
    boolean isParent=false;
    boolean isChild=false;
    private Executor exec;
    
    /* private TimeCellModel feModelTimeCellModel;                             //these are front end models. these are the ones that are updated for the UI. (colors) etc whenever the db is updated. Find a way to get rid of these.
    private TraceCellModel feModelTraceCellModel;
    
    */
    
    SummaryService summaryService=new SummaryServiceImpl();                     //** This view corresponding to this model is NEVER CAlled and therefore cannot attach a listener to it in the usual manner (i.e. Controller)
    private List<JobSummaryModel> children=new ArrayList<>();
    private JobSummaryModel parent;
    
    
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
        
        exec=Executors.newCachedThreadPool(r->{
        Thread t=new Thread(r);
        t.setDaemon(true);
        return t;
        });
    }

    public List<JobSummaryModel> getChildren() {
        return children;
    }

    public void setChildren(List<JobSummaryModel> children) {
        this.children = children;
    }

    public JobSummaryModel getParent() {
        return parent;
    }

    public void setParent(JobSummaryModel parent) {
        this.parent = parent;
    }
    
    public void addToChildren(JobSummaryModel child){
        children.add(child);
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
    
    public boolean isParent(){
        return isParent;
    }
    
    public void setIsParent(boolean p){
        isParent=p;
    }
    
    public boolean isChild(){
        return isChild;
    }
    
    public void setIsChild(boolean v){
        isChild=v;
    }
    
    
    
    
    
    private ChangeListener<Boolean> QUERY_LISTENER=new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            //
           //model.getTimeCellModel().setQuery(!model.getTimeCellModel().isQuery());
            if (JobSummaryModel.this.subsurface != null) {        //for subsurfaces
                //System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel.: toggling QUERY_LISTENER for the time cell in "+JobSummaryModel.this.getSubsurface().getSubsurface()+" "+JobSummaryModel.this.getJob().getNameJobStep());
                Summary x = summaryService.getSummaryFor(JobSummaryModel.this.getSubsurface(), JobSummaryModel.this.getJob());
                //time

                JobSummaryModel.this.getTimeCellModel().setFailedTimeDependency(x.hasFailedTimeDependency());
                JobSummaryModel.this.getTimeCellModel().setInheritedTimeFail(x.hasInheritedTimeFail());
                JobSummaryModel.this.getTimeCellModel().setInheritedTimeOverride(x.hasInheritedTimeOverride());
                JobSummaryModel.this.getTimeCellModel().setOverridenTimeFail(x.hasOverridenTimeFail());
                JobSummaryModel.this.getTimeCellModel().setWarningForTime(x.hasWarningForTime());
                timeCellModel.calculateCellState();
                JobSummaryModel.this.getTimeCellModel().setQuery(!JobSummaryModel.this.getTimeCellModel().isQuery());                    //trigger the labelColors Call in the cells controller

                //trace
                JobSummaryModel.this.getTraceCellModel().setFailedTraceDependency(x.hasFailedTraceDependency());
                JobSummaryModel.this.getTraceCellModel().setInheritedTraceFail(x.hasInheritedTraceFail());
                JobSummaryModel.this.getTraceCellModel().setInheritedTraceOverride(x.hasInheritedTraceOverride());
                JobSummaryModel.this.getTraceCellModel().setOverridenTraceFail(x.hasOverridenTraceFail());
                JobSummaryModel.this.getTraceCellModel().setWarningForTrace(x.hasWarningForTrace());
                traceCellModel.calculateCellState();
                JobSummaryModel.this.getTraceCellModel().setQuery(!JobSummaryModel.this.getTraceCellModel().isQuery());                 //trigger the labelColors Call in the cells controller

                //qc
                JobSummaryModel.this.getQcCellModel().setFailedQcDependency(x.hasFailedQcDependency());
                JobSummaryModel.this.getQcCellModel().setInheritedQcFail(x.hasInheritedQcFail());
                JobSummaryModel.this.getQcCellModel().setInheritedQcOverride(x.hasInheritedQcOverride());
                JobSummaryModel.this.getQcCellModel().setOverridenQcFail(x.hasOverridenQcFail());
                JobSummaryModel.this.getQcCellModel().setWarningForQc(x.hasWarningForQc());
                qcCellModel.calculateCellState();
                JobSummaryModel.this.getQcCellModel().setQuery(!JobSummaryModel.this.getQcCellModel().isQuery());                      //trigger the labelColors Call in the cells controller
                //insight
                JobSummaryModel.this.getInsightCellModel().setFailedInsightDependency(x.hasFailedInsightDependency());
                JobSummaryModel.this.getInsightCellModel().setInheritedInsightFail(x.hasInheritedInsightFail());
                JobSummaryModel.this.getInsightCellModel().setInheritedInsightOverride(x.hasInheritedInsightOverride());
                JobSummaryModel.this.getInsightCellModel().setOverridenInsightFail(x.hasOverridenInsightFail());
                JobSummaryModel.this.getInsightCellModel().setWarningForInsight(x.hasWarningForInsight());
                insightCellModel.calculateCellState();
                JobSummaryModel.this.getInsightCellModel().setQuery(!JobSummaryModel.this.getInsightCellModel().isQuery());        //trigger the labelColors Call in the cells controller
                
                //io
                JobSummaryModel.this.getIoCellModel().setFailedIoDependency(x.hasFailedIoDependency());
                JobSummaryModel.this.getIoCellModel().setInheritedIoFail(x.hasInheritedIoFail());
                JobSummaryModel.this.getIoCellModel().setInheritedIoOverride(x.hasInheritedIoOverride());
                JobSummaryModel.this.getIoCellModel().setOverridenIoFail(x.hasOverridenIoFail());
                JobSummaryModel.this.getIoCellModel().setWarningForIo(x.hasWarningForIo());
                ioCellModell.calculateCellState();
                JobSummaryModel.this.getIoCellModel().setQuery(!JobSummaryModel.this.getIoCellModel().isQuery());//trigger the labelColors Call in the cells controller

            }else{                                      //for sequences.
                
                    setParentCellState();
              //  System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel.: toggling QUERY_LISTENER for the cells  in job:  "+job.getNameJobStep()+" for seq :"+sequence.getSequenceno());
             //   List<Summary> xs = summaryService.getSummariesForJobSeq(job, sequence);
                
               
                
             /*
             boolean timeFail=false;
             boolean timeInhFail=false;
             boolean timeOver=false;
             boolean timeInhOver=false;
             boolean timeWarn=false;
             
             boolean traceFail=false;
             boolean traceInhFail=false;
             boolean traceOver=false;
             boolean traceInhOver=false;
             boolean traceWarn=false;
             
             
             boolean qcFail=false;
             boolean qcInhFail=false;
             boolean qcOver=false;
             boolean qcInhOver=false;
             boolean qcWarn=false;
             
             boolean insightFail=false;
             boolean insightInhFail=false;
             boolean insightOver=false;
             boolean insightInhOver=false;
             boolean insightWarn=false;
             
             boolean ioFail=false;
             boolean ioInhFail=false;
             boolean ioOver=false;
             boolean ioInhOver=false;
             boolean ioWarn=false;
             
             //   for(Summary x:xs){
             for(JobSummaryModel sub:children){
             
             //  System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel.: summary sub : "+x.getSubsurface().getSubsurface());
             // CellState subTimecellState=figureCellState(timeCellModel, x);
             CellState subTimecellState=sub.getTimeCellModel().getCellState();
             timeFail=timeFail || (subTimecellState == CellState.FAILED);
             timeInhFail=timeInhFail || (subTimecellState == CellState.INHERITED_FAIL);
             timeOver=timeOver || (subTimecellState == CellState.OVERRIDE);
             timeInhOver=timeInhOver || (subTimecellState == CellState.INHERITED_OVERRIDE);
             timeWarn=timeWarn || (subTimecellState == CellState.WARNING);
             
             
             //CellState subTracecellState=figureCellState(traceCellModel,x);
             CellState subTracecellState=sub.getTraceCellModel().getCellState();
             traceFail=traceFail || (subTracecellState == CellState.FAILED);
             traceInhFail=traceInhFail ||(subTracecellState == CellState.INHERITED_FAIL);
             traceOver=traceOver || (subTracecellState == CellState.OVERRIDE);
             traceInhOver = traceInhOver || (subTracecellState == CellState.INHERITED_OVERRIDE);
             traceWarn=traceWarn || (subTracecellState == CellState.WARNING);
             
             
             //CellState subQcCellState=figureCellState(qcCellModel, x);
             CellState subQcCellState=sub.getQcCellModel().getCellState();
             
             qcFail=qcFail || (subQcCellState == CellState.FAILED);
             qcInhFail=qcInhFail || (subQcCellState == CellState.INHERITED_FAIL);
             qcOver = qcOver || (subQcCellState == CellState.OVERRIDE);
             qcInhOver = qcInhOver || (subQcCellState == CellState.INHERITED_OVERRIDE);
             qcWarn = qcWarn || (subQcCellState == CellState.WARNING);
             
             
             //CellState subInsightCellState=figureCellState(insightCellModel, x);
             CellState subInsightCellState=sub.getInsightCellModel().getCellState();
             
             insightFail=insightFail || (subInsightCellState == CellState.FAILED);
             insightInhFail = insightInhFail || (subInsightCellState == CellState.INHERITED_FAIL);
             insightOver = insightOver || (subInsightCellState == CellState.OVERRIDE);
             insightInhOver = insightInhOver || (subInsightCellState == CellState.INHERITED_OVERRIDE);
             insightWarn = insightWarn || (subInsightCellState == CellState.WARNING);
             
             //CellState subIoCellState=figureCellState(ioCellModell,x);
             CellState subIoCellState=sub.getIoCellModel().getCellState();
             
             ioFail= ioFail ||(subIoCellState == CellState.FAILED);
             ioInhFail = ioInhFail || (subIoCellState == CellState.INHERITED_FAIL);
             ioOver = ioOver || (subIoCellState == CellState.OVERRIDE);
             ioInhOver = ioInhOver || (subIoCellState == CellState.INHERITED_OVERRIDE);
             ioWarn = ioWarn || (subIoCellState == CellState.WARNING);
             
             /* System.out.println("Values for seq "+sequence.getSequenceno()+" current sub: "+x.getSubsurface().getSubsurface());
             System.out.println("Time: ");
             System.out.println("    fail    : "+timeFail);
             System.out.println("    inhFail : "+timeInhFail);
             System.out.println("    override: "+timeOver);
             System.out.println("    inhOver : "+timeInhOver);
             System.out.println("    warn    : "+timeWarn);
             
             System.out.println("Trace: ");
             System.out.println("    fail    : "+traceFail);
             System.out.println("    inhFail : "+traceInhFail);
             System.out.println("    override: "+traceOver);
             System.out.println("    inhOver : "+traceInhOver);
             System.out.println("    warn    : "+traceWarn);
             
             System.out.println("QC: ");
             System.out.println("    fail    : "+qcFail);
             System.out.println("    inhFail : "+qcInhFail);
             System.out.println("    override: "+qcOver);
             System.out.println("    inhOver : "+qcInhOver);
             System.out.println("    warn    : "+qcWarn);
             
             System.out.println("Insight: ");
             System.out.println("    fail    : "+insightFail);
             System.out.println("    inhFail : "+insightInhFail);
             System.out.println("    override: "+insightOver);
             System.out.println("    inhOver : "+insightInhOver);
             System.out.println("    warn    : "+insightWarn);
             
             System.out.println("IO: ");
             System.out.println("    fail    : "+ioFail);
             System.out.println("    inhFail : "+ioInhFail);
             System.out.println("    override: "+ioOver);
             System.out.println("    inhOver : "+ioInhOver);
             System.out.println("    warn    : "+ioWarn);*/
             
            }
            
          /*  
            setCellStateFor(timeCellModel,timeFail,timeInhFail,timeOver,timeInhOver,timeWarn);
            setCellStateFor(traceCellModel, traceFail, traceInhFail, traceOver, traceInhOver, traceWarn);
            setCellStateFor(qcCellModel, qcFail, qcInhFail, qcOver, qcInhOver, qcWarn);
            setCellStateFor(insightCellModel, insightFail, insightInhFail, insightOver, insightInhOver, insightWarn);
            setCellStateFor(ioCellModell, ioFail, ioInhFail, ioOver, ioInhOver, ioWarn);
            /*
            System.out.println(".changed(): cell states for seq: "+sequence.getSequenceno());
            System.out.println("time    : "+timeCellModel.getCellState().name());
            System.out.println("trace   : "+traceCellModel.getCellState().name());
            System.out.println("qc      : "+qcCellModel.getCellState().name());
            System.out.println("insight : "+insightCellModel.getCellState().name());
            System.out.println("io      : "+ioCellModell.getCellState().name());*/
            /*  System.out.println("failedDependency  :     "+ioCellModell.cellHasFailedDependency());
            System.out.println("hasInheritedIOFail:     "+ioCellModell.cellHasInheritedFail());
            System.out.println("hasIOoVerride     :     "+ioCellModell.cellHasInheritedOverride());
            System.out.println("hasOverridenIOFail:     "+ioCellModell.cellHasOverridenFail());
            System.out.println("hasIOWarning      :     "+ioCellModell.cellHasWarning());*/
            
           /* timeCellModel.setQuery(!timeCellModel.isQuery());
            traceCellModel.setQuery(!traceCellModel.isQuery());
            qcCellModel.setQuery(!qcCellModel.isQuery());
            insightCellModel.setQuery(!insightCellModel.isQuery());
            ioCellModell.setQuery(!ioCellModell.isQuery());
            }*/
        }

       
        
        
        
    };
    
    
     private void setCellStateFor(CellModel cellModel, boolean fail, boolean inhFail, boolean over, boolean inhOver, boolean warn) {
            if(fail){          //1
                cellModel.setCellState(CellState.FAILED);
            }else{             //2
                if(inhFail){   //2.1
                    cellModel.setCellState(CellState.INHERITED_FAIL);
                }else{        //2.2
                    
                    if(over){ //2.2.1
                        cellModel.setCellState(CellState.OVERRIDE);
                    }else{   //2.2.2
                        
                        if(inhOver){  //2.2.2.1
                            cellModel.setCellState(CellState.INHERITED_OVERRIDE);
                        }else{       //2.2.2.2
                            
                            if(warn){//2.2.2.2.1
                                cellModel.setCellState(CellState.WARNING);
                            }else{   //2.2.2.2.2
                                cellModel.setCellState(CellState.GOOD);
                            }
                        }
                    }
                    
                }
            }
            CellState cs=cellModel.getCellState();
            if(cs == CellState.FAILED){
                cellModel.setCellHasFailedDependency(true);
                cellModel.setCellHasOverridenFail(false);
            }
            else if(cs == CellState.INHERITED_FAIL){
                cellModel.setCellHasFailedDependency(false);
                cellModel.setCellHasInheritedFail(true);
            }
            else if(cs == CellState.OVERRIDE){
                cellModel.setCellHasFailedDependency(true);
                cellModel.setCellHasOverridenFail(true);
                cellModel.setCellHasInheritedFail(false);
            }
            else if(cs == CellState.INHERITED_OVERRIDE){
                cellModel.setCellHasFailedDependency(false);
                cellModel.setCellHasInheritedFail(false);
                cellModel.setCellHasInheritedOverride(true);
            }
            else if(cs == CellState.WARNING){
                cellModel.setCellHasFailedDependency(false);
                cellModel.setCellHasInheritedFail(false);
                cellModel.setCellHasInheritedOverride(false);
                cellModel.setCellHasWarning(true);
            }
            else if(cs == CellState.GOOD){
                cellModel.setCellHasFailedDependency(false);
                cellModel.setCellHasInheritedFail(false);
                cellModel.setCellHasInheritedOverride(false);
                cellModel.setCellHasWarning(false);
            }
        }

    public void setSequence(Sequence sequence) {
        this.sequence=sequence;
    }

    public Sequence getSequence() {
        return sequence;
    }
    
    private CellState figureCellState(CellModel cm,Summary x){
       CellState cellstate=CellState.FAILED;
       if(cm.equals(timeCellModel)){
           if(x.hasFailedTimeDependency() && x.hasOverridenTimeFail() && x.hasInheritedTimeFail()) cellstate=CellState.INHERITED_FAIL;   //1.1.1
           if(x.hasFailedTimeDependency() && x.hasOverridenTimeFail() && !x.hasInheritedTimeFail()) cellstate=CellState.OVERRIDE;  //1.1.2
           if(x.hasFailedTimeDependency() && !x.hasOverridenTimeFail()) cellstate=CellState.FAILED; //1.2
           if(!x.hasFailedTimeDependency() && x.hasInheritedTimeFail()) cellstate=CellState.INHERITED_FAIL;  //2.1
           if(!x.hasFailedTimeDependency() && !x.hasInheritedTimeFail() && x.hasInheritedTimeOverride()) cellstate=CellState.INHERITED_OVERRIDE; //2.2.1
           if(!x.hasFailedTimeDependency() && !x.hasInheritedTimeFail() && !x.hasInheritedTimeOverride() && x.hasWarningForTime()) cellstate=CellState.WARNING;   //2.2.2.1
           if(!x.hasFailedTimeDependency() && !x.hasInheritedTimeFail() && !x.hasInheritedTimeOverride() && !x.hasWarningForTime()) cellstate=CellState.GOOD;   //2.2.2.2
       }
       
       if(cm.equals(traceCellModel)){
           if(x.hasFailedTraceDependency() && x.hasOverridenTraceFail() && x.hasInheritedTraceFail()) cellstate=CellState.INHERITED_FAIL;   //1.1.1
           if(x.hasFailedTraceDependency() && x.hasOverridenTraceFail() && !x.hasInheritedTraceFail()) cellstate=CellState.OVERRIDE;  //1.1.2
           if(x.hasFailedTraceDependency() && !x.hasOverridenTraceFail()) cellstate=CellState.FAILED; //1.2
           if(!x.hasFailedTraceDependency() && x.hasInheritedTraceFail()) cellstate=CellState.INHERITED_FAIL;  //2.1
           if(!x.hasFailedTraceDependency() && !x.hasInheritedTraceFail() && x.hasInheritedTraceOverride()) cellstate=CellState.INHERITED_OVERRIDE; //2.2.1
           if(!x.hasFailedTraceDependency() && !x.hasInheritedTraceFail() && !x.hasInheritedTraceOverride() && x.hasWarningForTrace()) cellstate=CellState.WARNING;   //2.2.2.1
           if(!x.hasFailedTraceDependency() && !x.hasInheritedTraceFail() && !x.hasInheritedTraceOverride() && !x.hasWarningForTrace()) cellstate=CellState.GOOD;   //2.2.2.2
       }
       
       if(cm.equals(qcCellModel)){
           if(x.hasFailedQcDependency() && x.hasOverridenQcFail() && x.hasInheritedQcFail()) cellstate=CellState.INHERITED_FAIL;   //1.1.1
           if(x.hasFailedQcDependency() && x.hasOverridenQcFail() && !x.hasInheritedQcFail()) cellstate=CellState.OVERRIDE;  //1.1.2
           if(x.hasFailedQcDependency() && !x.hasOverridenQcFail()) cellstate=CellState.FAILED; //1.2
           if(!x.hasFailedQcDependency() && x.hasInheritedQcFail()) cellstate=CellState.INHERITED_FAIL;  //2.1
           if(!x.hasFailedQcDependency() && !x.hasInheritedQcFail() && x.hasInheritedQcOverride()) cellstate=CellState.INHERITED_OVERRIDE; //2.2.1
           if(!x.hasFailedQcDependency() && !x.hasInheritedQcFail() && !x.hasInheritedQcOverride() && x.hasWarningForQc()) cellstate=CellState.WARNING;   //2.2.2.1
           if(!x.hasFailedQcDependency() && !x.hasInheritedQcFail() && !x.hasInheritedQcOverride() && !x.hasWarningForQc()) cellstate=CellState.GOOD;   //2.2.2.2
       }
       
       if(cm.equals(insightCellModel)){
           if(x.hasFailedInsightDependency() && x.hasOverridenInsightFail() && x.hasInheritedInsightFail()) cellstate=CellState.INHERITED_FAIL;   //1.1.1
           if(x.hasFailedInsightDependency() && x.hasOverridenInsightFail() && !x.hasInheritedInsightFail()) cellstate=CellState.OVERRIDE;  //1.1.2
           if(x.hasFailedInsightDependency() && !x.hasOverridenInsightFail()) cellstate=CellState.FAILED; //1.2
           if(!x.hasFailedInsightDependency() && x.hasInheritedInsightFail()) cellstate=CellState.INHERITED_FAIL;  //2.1
           if(!x.hasFailedInsightDependency() && !x.hasInheritedInsightFail() && x.hasInheritedInsightOverride()) cellstate=CellState.INHERITED_OVERRIDE; //2.2.1
           if(!x.hasFailedInsightDependency() && !x.hasInheritedInsightFail() && !x.hasInheritedInsightOverride() && x.hasWarningForInsight()) cellstate=CellState.WARNING;   //2.2.2.1
           if(!x.hasFailedInsightDependency() && !x.hasInheritedInsightFail() && !x.hasInheritedInsightOverride() && !x.hasWarningForInsight()) cellstate=CellState.GOOD;   //2.2.2.2
       }
       
       
       if(cm.equals(ioCellModell)){
           if(x.hasFailedIoDependency() && x.hasOverridenIoFail() && x.hasInheritedIoFail()) cellstate=CellState.INHERITED_FAIL;   //1.1.1
           if(x.hasFailedIoDependency() && x.hasOverridenIoFail() && !x.hasInheritedIoFail()) cellstate=CellState.OVERRIDE;  //1.1.2
           if(x.hasFailedIoDependency() && !x.hasOverridenIoFail()) cellstate=CellState.FAILED; //1.2
           if(!x.hasFailedIoDependency() && x.hasInheritedIoFail()) cellstate=CellState.INHERITED_FAIL;  //2.1
           if(!x.hasFailedIoDependency() && !x.hasInheritedIoFail() && x.hasInheritedIoOverride()) cellstate=CellState.INHERITED_OVERRIDE; //2.2.1
           if(!x.hasFailedIoDependency() && !x.hasInheritedIoFail() && !x.hasInheritedIoOverride() && x.hasWarningForIo()) cellstate=CellState.WARNING;   //2.2.2.1
           if(!x.hasFailedIoDependency() && !x.hasInheritedIoFail() && !x.hasInheritedIoOverride() && !x.hasWarningForIo()) cellstate=CellState.GOOD;   //2.2.2.2
       }
       
        return cellstate;
    }
    
    
    public void setParentCellState(){
         
                boolean timeFail=false;
                boolean timeInhFail=false;
                boolean timeOver=false;
                boolean timeInhOver=false;
                boolean timeWarn=false;
                
                boolean traceFail=false;
                boolean traceInhFail=false;
                boolean traceOver=false;
                boolean traceInhOver=false;
                boolean traceWarn=false;
                
                
                boolean qcFail=false;
                boolean qcInhFail=false;
                boolean qcOver=false;
                boolean qcInhOver=false;
                boolean qcWarn=false;
                
                boolean insightFail=false;
                boolean insightInhFail=false;
                boolean insightOver=false;
                boolean insightInhOver=false;
                boolean insightWarn=false;
                
                boolean ioFail=false;
                boolean ioInhFail=false;
                boolean ioOver=false;
                boolean ioInhOver=false;
                boolean ioWarn=false;
               
             //   for(Summary x:xs){
                 for(JobSummaryModel sub:children){
                     
                  //  System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel.: summary sub : "+x.getSubsurface().getSubsurface());
                   // CellState subTimecellState=figureCellState(timeCellModel, x);
                     CellState subTimecellState=sub.getTimeCellModel().getCellState();
                        timeFail=timeFail || (subTimecellState == CellState.FAILED);
                        timeInhFail=timeInhFail || (subTimecellState == CellState.INHERITED_FAIL);
                        timeOver=timeOver || (subTimecellState == CellState.OVERRIDE);
                        timeInhOver=timeInhOver || (subTimecellState == CellState.INHERITED_OVERRIDE);
                        timeWarn=timeWarn || (subTimecellState == CellState.WARNING);
                    
                    
                    //CellState subTracecellState=figureCellState(traceCellModel,x);
                    CellState subTracecellState=sub.getTraceCellModel().getCellState();
                        traceFail=traceFail || (subTracecellState == CellState.FAILED);
                        traceInhFail=traceInhFail ||(subTracecellState == CellState.INHERITED_FAIL);
                        traceOver=traceOver || (subTracecellState == CellState.OVERRIDE);
                        traceInhOver = traceInhOver || (subTracecellState == CellState.INHERITED_OVERRIDE);
                        traceWarn=traceWarn || (subTracecellState == CellState.WARNING);
                        
                        
                    //CellState subQcCellState=figureCellState(qcCellModel, x);
                    CellState subQcCellState=sub.getQcCellModel().getCellState();
                        
                        qcFail=qcFail || (subQcCellState == CellState.FAILED);
                        qcInhFail=qcInhFail || (subQcCellState == CellState.INHERITED_FAIL);
                        qcOver = qcOver || (subQcCellState == CellState.OVERRIDE);
                        qcInhOver = qcInhOver || (subQcCellState == CellState.INHERITED_OVERRIDE);
                        qcWarn = qcWarn || (subQcCellState == CellState.WARNING);
                        
                        
                    //CellState subInsightCellState=figureCellState(insightCellModel, x);
                    CellState subInsightCellState=sub.getInsightCellModel().getCellState();
                    
                        insightFail=insightFail || (subInsightCellState == CellState.FAILED);
                        insightInhFail = insightInhFail || (subInsightCellState == CellState.INHERITED_FAIL);
                        insightOver = insightOver || (subInsightCellState == CellState.OVERRIDE);
                        insightInhOver = insightInhOver || (subInsightCellState == CellState.INHERITED_OVERRIDE);
                        insightWarn = insightWarn || (subInsightCellState == CellState.WARNING);
                        
                    //CellState subIoCellState=figureCellState(ioCellModell,x);
                    CellState subIoCellState=sub.getIoCellModel().getCellState();
                        
                        ioFail= ioFail ||(subIoCellState == CellState.FAILED);
                        ioInhFail = ioInhFail || (subIoCellState == CellState.INHERITED_FAIL);
                        ioOver = ioOver || (subIoCellState == CellState.OVERRIDE);
                        ioInhOver = ioInhOver || (subIoCellState == CellState.INHERITED_OVERRIDE);
                        ioWarn = ioWarn || (subIoCellState == CellState.WARNING);
                        
                        /* System.out.println("Values for seq "+sequence.getSequenceno()+" current sub: "+x.getSubsurface().getSubsurface());
                        System.out.println("Time: ");
                        System.out.println("    fail    : "+timeFail);
                        System.out.println("    inhFail : "+timeInhFail);
                        System.out.println("    override: "+timeOver);
                        System.out.println("    inhOver : "+timeInhOver);
                        System.out.println("    warn    : "+timeWarn);
                        
                        System.out.println("Trace: ");
                        System.out.println("    fail    : "+traceFail);
                        System.out.println("    inhFail : "+traceInhFail);
                        System.out.println("    override: "+traceOver);
                        System.out.println("    inhOver : "+traceInhOver);
                        System.out.println("    warn    : "+traceWarn);
                        
                        System.out.println("QC: ");
                        System.out.println("    fail    : "+qcFail);
                        System.out.println("    inhFail : "+qcInhFail);
                        System.out.println("    override: "+qcOver);
                        System.out.println("    inhOver : "+qcInhOver);
                        System.out.println("    warn    : "+qcWarn);
                        
                        System.out.println("Insight: ");
                        System.out.println("    fail    : "+insightFail);
                        System.out.println("    inhFail : "+insightInhFail);
                        System.out.println("    override: "+insightOver);
                        System.out.println("    inhOver : "+insightInhOver);
                        System.out.println("    warn    : "+insightWarn);
                        
                        System.out.println("IO: ");
                        System.out.println("    fail    : "+ioFail);
                        System.out.println("    inhFail : "+ioInhFail);
                        System.out.println("    override: "+ioOver);
                        System.out.println("    inhOver : "+ioInhOver);
                        System.out.println("    warn    : "+ioWarn);*/
                     
                }
                
                
                setCellStateFor(timeCellModel,timeFail,timeInhFail,timeOver,timeInhOver,timeWarn);
                setCellStateFor(traceCellModel, traceFail, traceInhFail, traceOver, traceInhOver, traceWarn);
                setCellStateFor(qcCellModel, qcFail, qcInhFail, qcOver, qcInhOver, qcWarn);
                setCellStateFor(insightCellModel, insightFail, insightInhFail, insightOver, insightInhOver, insightWarn);
                setCellStateFor(ioCellModell, ioFail, ioInhFail, ioOver, ioInhOver, ioWarn);
                /*
                System.out.println(".changed(): cell states for seq: "+sequence.getSequenceno());
                System.out.println("time    : "+timeCellModel.getCellState().name());
                System.out.println("trace   : "+traceCellModel.getCellState().name());
                System.out.println("qc      : "+qcCellModel.getCellState().name());
                System.out.println("insight : "+insightCellModel.getCellState().name());
                System.out.println("io      : "+ioCellModell.getCellState().name());*/
                /*  System.out.println("failedDependency  :     "+ioCellModell.cellHasFailedDependency());
                System.out.println("hasInheritedIOFail:     "+ioCellModell.cellHasInheritedFail());
                System.out.println("hasIOoVerride     :     "+ioCellModell.cellHasInheritedOverride());
                System.out.println("hasOverridenIOFail:     "+ioCellModell.cellHasOverridenFail());
                System.out.println("hasIOWarning      :     "+ioCellModell.cellHasWarning());*/
                
                timeCellModel.setQuery(!timeCellModel.isQuery());
                traceCellModel.setQuery(!traceCellModel.isQuery());
                qcCellModel.setQuery(!qcCellModel.isQuery());
                insightCellModel.setQuery(!insightCellModel.isQuery());
                ioCellModell.setQuery(!ioCellModell.isQuery());
            
    }
}
