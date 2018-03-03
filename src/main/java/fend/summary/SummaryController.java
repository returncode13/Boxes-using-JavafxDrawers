/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import app.properties.AppProperties;
import db.model.Doubt;
import db.model.DoubtStatus;
import db.model.DoubtType;
import db.model.Job;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.Summary;
import db.model.Workspace;
import db.services.DoubtService;
import db.services.DoubtServiceImpl;
import db.services.DoubtStatusService;
import db.services.DoubtStatusServiceImpl;
import db.services.DoubtTypeService;
import db.services.DoubtTypeServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.SequenceService;
import db.services.SequenceServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import db.services.SummaryService;
import db.services.SummaryServiceImpl;
import db.services.WorkspaceService;
import db.services.WorkspaceServiceImpl;
import fend.summary.SequenceSummary.Depth.Depth;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Qc.QcCell.QcCell;
import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel;
import fend.summary.SequenceSummary.Depth.JobSummaryCell;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCell.TimeCell;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace.TraceCell.TraceCell;
import fend.summary.SequenceSummary.SequenceSummary;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import middleware.doubt.DoubtStatusModel;
import middleware.doubt.DoubtTypeModel;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SummaryController extends Stage{
    private SummaryModel model;
    private SummaryView view;
    private SummaryService summaryService=new SummaryServiceImpl();
    private SequenceService sequenceService=new SequenceServiceImpl();
    private WorkspaceService workspaceService=new WorkspaceServiceImpl();
    private JobService jobService=new JobServiceImpl();
    private SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();
    private DoubtService doubtService =new DoubtServiceImpl();
    private DoubtType timeDoubtType;
    private DoubtType traceDoubtType;
    private DoubtType qcDoubtType;
    private DoubtType insightDoubtType;
    private DoubtType inheritanceDoubtType;
    private DoubtTypeService doubtTypeService=new DoubtTypeServiceImpl();
    private DoubtStatusService doubtStatusService=new DoubtStatusServiceImpl();
    // @FXML
   // private TableView<SequenceSummary> table;
    
     @FXML
    private TreeTableView<SequenceSummary> treetable;
    
  
     void setModel(SummaryModel model){
         this.model=model;
         
         timeDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TIME);
         traceDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TRACES);
         qcDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.QC);
         insightDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.INSIGHT);
         inheritanceDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.INHERIT);
         Map<TimeJobSubKey,Doubt> timeDoubtMap=new HashMap<>();
         Map<TraceJobSubKey,Doubt> traceDoubtMap=new HashMap<>();
         Map<QcJobSubKey,Doubt> qcDoubtMap=new HashMap<>();
         
         
         Map<Doubt,DoubtStatus> doubtStatusMap=new HashMap<>();
         
         Map<Sequence,SequenceSummary> seqSummaryMap=new HashMap<>();
         //first get a list of all the subsurfaces.
         model.refreshTableProperty().addListener(REFRESH_TABLE_LISTENER);
         try {//if workspace.lastUpdateTime > workspace.lastSummaryTime. then execute Summary
           // this.model.getWorkspaceController().summarize();
           this.model.getWorkspaceController().summarizeInMemory();
        } catch (Exception ex) {
            Logger.getLogger(SummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
         Workspace workspace=this.model.getWorkspaceController().getModel().getWorkspace();
         System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" getting all Time doubts for workspace : "+workspace.getId());
         List<Doubt> timeDoubtsInWorkspace=doubtService.getAllDoubtsJobsAndSubsurfacesFor(workspace, timeDoubtType);
         System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" got "+timeDoubtsInWorkspace.size()+"time related doubts");
         System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" getting all Trace doubts for workspace : "+workspace.getId());
         List<Doubt> traceDoubtsInWorkspace=doubtService.getAllDoubtsJobsAndSubsurfacesFor(workspace, traceDoubtType);
         System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" got "+traceDoubtsInWorkspace.size()+"trace related doubts");
         System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" getting all Qc doubts for workspace : "+workspace.getId());
         List<Doubt> qcDoubtsInWorkspace=doubtService.getAllDoubtsJobsAndSubsurfacesFor(workspace, qcDoubtType);
         System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" got "+qcDoubtsInWorkspace.size()+"qc related doubts");
         System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" building the time Map");
            for(Doubt timeDoubt:timeDoubtsInWorkspace){
                TimeJobSubKey timeKey=generateTimeJobSubKey(timeDoubt.getChildJob(), timeDoubt.getSubsurface());
                timeDoubtMap.put(timeKey, timeDoubt);
            }
         System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" built the time Map with "+timeDoubtMap.size());
         
         System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" building the trace Map");
            for(Doubt traceDoubt:traceDoubtsInWorkspace){
                TraceJobSubKey traceKey=generateTraceJobSubKey(traceDoubt.getChildJob(), traceDoubt.getSubsurface());
                traceDoubtMap.put(traceKey, traceDoubt);
            }
         System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" built the trace Map with "+traceDoubtMap.size());
         
         
         System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" building the qc Map");
            for(Doubt qcDoubt:qcDoubtsInWorkspace){
                QcJobSubKey qcKey=generateQcJobSubKey(qcDoubt.getChildJob(), qcDoubt.getSubsurface());
                qcDoubtMap.put(qcKey, qcDoubt);
            }
         System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" built the qc Map with "+qcDoubtMap.size());
                    System.out.println("fend.summary.SummaryController.setModel(): building summary table");

                    
         System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" getting all doubt status for workspace");           
         List<DoubtStatus> doubtstatusForWorkspace=doubtStatusService.getAllDoubtStatusInWorkspace(workspace);
         System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" fetched "+doubtstatusForWorkspace.size()+" doubtstatus entries for workspace");
         System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" building the doubtstatus map");
         for (DoubtStatus doubtStatus : doubtstatusForWorkspace) {
             doubtStatusMap.put(doubtStatus.getDoubt(), doubtStatus);
         }
                   
                    Workspace dbWorkspace=workspaceService.getWorkspace(model.getWorkspaceController().getModel().getId());
                    List<Long> depths=jobService.getDepthOfGraph(dbWorkspace);
                    Set<Job> allJobsInWorkspace=new HashSet<>(jobService.listJobs(dbWorkspace));
                    Map<Long,List<JobSummaryModel>> depthJobMap=new HashMap<>();
                    for(Job job:allJobsInWorkspace){
                        if(depthJobMap.containsKey(job.getDepth())){
                            JobSummaryModel jsm=new JobSummaryModel(this.model);
                           
                            jsm.setActive(false);
                            jsm.setJob(job);
                            depthJobMap.get(job.getDepth()).add(jsm);
                        }else{
                            depthJobMap.put(job.getDepth(), new ArrayList<>());
                            JobSummaryModel jsm=new JobSummaryModel(this.model);
                            jsm.setActive(false);
                            jsm.setJob(job);
                            depthJobMap.get(job.getDepth()).add(jsm);
                        }

                    }
                    List<Depth> depthForColumns=new ArrayList<>();
                    for(Long depth:depths){
                        Depth sumDepth=new Depth();
                        sumDepth.setDepth(depth);
                        //sumDepth.setJobSummaries(depthJobMap.get(depth));
                        List<JobSummaryModel> jobSummariesAtDepth=depthJobMap.get(depth);
                        for(JobSummaryModel jsm:jobSummariesAtDepth){
                            System.out.println("fend.summary.SummaryController.setModel(): adding jobSummaryModel for job: "+jsm.getJob().getNameJobStep()+" to depth :"+depth);
                            sumDepth.addToJobSummaryMap(jsm);
                        }
                        
                        depthForColumns.add(sumDepth);

                    }
                    
                   
                    
                    List<Subsurface> subsurfacesInSurvey=subsurfaceService.getSubsurfaceList();
                    for(Subsurface sub:subsurfacesInSurvey){
                        SequenceSummary subSeqSummary=new SequenceSummary();
                        subSeqSummary.setSequence(sub.getSequence());
                       
                        Map<Long,Depth> depthMapForSequenceRoot=new HashMap<>();
                        
                            for(Depth depth:depthForColumns){
                                Depth d1=new Depth();
                                Depth d2ForSeq=new Depth();
                                d1.setDepth(depth.getDepth());
                                d2ForSeq.setDepth(depth.getDepth());
                                
                                Map<Job,JobSummaryModel> newJobJobSummaryModel=depth.getJobSummaryMap();
                                    for (Map.Entry<Job, JobSummaryModel> entry : newJobJobSummaryModel.entrySet()) {
                                    Job key = entry.getKey();
                                    JobSummaryModel value = entry.getValue();
                                    JobSummaryModel newValue=new JobSummaryModel(model);
                                    newValue.setActive(value.isActive());
                                    newValue.setJob(value.getJob());
                                    /*newValue.setTime(value.isTime());
                                    newValue.setTrace(value.isTrace());
                                    newValue.setQc(value.isQc());
                                    newValue.setInsight(value.isInsight());
                                    newValue.setInheritance(value.isInheritance());
                                    newValue.setSequence(sub.getSequence()); */
                                    newValue.setSubsurface(sub);
                                    newValue.getTimeCellModel().setCellProperty(value.getTimeCellModel().cellHasDoubt());
                                    newValue.getTraceCellModel().setCellProperty(value.getTraceCellModel().cellHasDoubt());
                                    newValue.getQcCellModel().setCellProperty(value.getQcCellModel().cellHasDoubt());
                                    
                                    JobSummaryModel newSeqValue=new JobSummaryModel(model);
                                    newSeqValue.setActive(value.isActive());
                                    newSeqValue.setJob(value.getJob());
                                    /*newSeqValue.setTime(value.isTime());
                                    newSeqValue.setTrace(value.isTrace());
                                    newSeqValue.setQc(value.isQc());
                                    newSeqValue.setInsight(value.isInsight());
                                    newSeqValue.setInheritance(value.isInheritance());
                                    newSeqValue.setSequence(sub.getSequence());*/
                                    //newSeqValue.setSubsurface(sub);
                                    newSeqValue.getTimeCellModel().setCellProperty(value.getTimeCellModel().cellHasDoubt());
                                    newSeqValue.getTraceCellModel().setCellProperty(value.getTraceCellModel().cellHasDoubt());
                                    newSeqValue.getQcCellModel().setCellProperty(value.getQcCellModel().cellHasDoubt());
                                    
                                    d1.addToJobSummaryMap(newValue);
                                    d2ForSeq.addToJobSummaryMap(newSeqValue);
                                    
                                }
                                subSeqSummary.addToDepth(d1);
                                depthMapForSequenceRoot.put(d2ForSeq.getDepth(), d2ForSeq);
                            }
                            
                            if(seqSummaryMap.containsKey(sub.getSequence())){
                                subSeqSummary.setSubsurface(sub);
                                seqSummaryMap.get(sub.getSequence()).addToChildren(subSeqSummary);
                                System.out.println("fend.summary.SummaryController.setModel(): added child subsurface: "+sub.getSubsurface());
                            }else{
                                System.out.println("fend.summary.SummaryController.setModel(): added root sequence:    "+sub.getSequence().getSequenceno());
                                SequenceSummary seqRootSummary=new SequenceSummary();
                                seqRootSummary.setSequence(sub.getSequence());
                                seqRootSummary.setDepthMap(depthMapForSequenceRoot);
                                        
                            seqSummaryMap.put(sub.getSequence(), seqRootSummary);
                            subSeqSummary.setSubsurface(sub);
                            seqSummaryMap.get(sub.getSequence()).addToChildren(subSeqSummary);
                                System.out.println("fend.summary.SummaryController.setModel(): added child subsurface: "+sub.getSubsurface());
                            }
                    }
                    
                    System.out.println("fend.summary.SummaryController.setModel(): Listing the map contents for Seq: sub relations ");
                    for (Map.Entry<Sequence, SequenceSummary> entry : seqSummaryMap.entrySet()) {
                        Sequence seq = entry.getKey();
                        SequenceSummary seqSummary = entry.getValue();
                        
                            Map<Subsurface,SequenceSummary> subChildren=seqSummary.getChildren();
                            for (Map.Entry<Subsurface, SequenceSummary> entry1 : subChildren.entrySet()) {
                            Subsurface sub = entry1.getKey();
                            SequenceSummary subSummary = entry1.getValue();
                            
                                System.out.println("fend.summary.SummaryController.setModel(): FOUND Seq: "+seqSummary.getSequence().getSequenceno()+" sub: "+subSummary.getSubsurface().getSubsurface());
                            
                        }

                    }
                    
                    
                    List<Summary> summariesInWorkspace=summaryService.getSummariesFor(dbWorkspace);
                   //  Set<Summary> summariesInWorkspace=new LinkedHashSet<>(summaryService.getSummariesFor(dbWorkspace));
                   // System.out.println("fend.summary.SummaryController.setModel(): size of SummariesFromWorkspace: "+summariesInWorkspace.size());
                    for(Summary x:summariesInWorkspace){
                        Long depth=x.getDepth();
                        Sequence seq=x.getSequence();
                        Subsurface sub=x.getSubsurface();
                        Job job=x.getJob();
                        /*System.out.println("fend.summary.SummaryController.setModel(): from summaries :");
                        System.out.println("Seq: "+seq.getSequenceno());
                        System.out.println("Sub: "+sub.getSubsurface());
                        System.out.println("Dep: "+depth);
                        System.out.println("Job: "+job.getNameJobStep());
                        System.out.println("fend.summary.SummaryController.setModel(): size  of SeqsummaryMap.get("+seq.getSequenceno()+"): "+
                        seqSummaryMap.get(seq).
                        getChildren().size());*/
                        JobSummaryModel seqJsm=seqSummaryMap.get(seq).getDepth(depth).getJobSummaryModel(job);
                        seqJsm.setActive(true);
                        seqJsm.getTimeCellModel().setActive(true);
                        seqJsm.setSubsurface(null);
                        /*seqJsm.setTime(seqJsm.isTime()||x.getTimeSummary());
                        seqJsm.setTrace(seqJsm.isTrace()||x.getTraceSummary());
                        seqJsm.setQc(seqJsm.isQc()||x.getQcSummary());
                        seqJsm.setInsight(seqJsm.isInsight()||x.getInsightSummary());
                        seqJsm.setInheritance(seqJsm.isInheritance()||x.getInheritanceSummary());*/
                        seqJsm.getTimeCellModel().setCellProperty(seqJsm.getTimeCellModel().cellHasDoubt()||x.getTimeSummary());
                        seqJsm.getTraceCellModel().setActive(true);
                        seqJsm.getTraceCellModel().setCellProperty(seqJsm.getTraceCellModel().cellHasDoubt()||x.getTraceSummary());
                        
                        
                        
                        JobSummaryModel jsm=seqSummaryMap.get(seq).
                                getChild(sub).
                                getDepth(depth).
                                getJobSummaryModel(job);
                        jsm.setActive(true);
                        jsm.setSubsurface(sub);
                        jsm.getTimeCellModel().setActive(true);
                        jsm.getTimeCellModel().setCellProperty(x.getTimeSummary());
                        if(x.getTimeSummary()){
                           // Doubt d=doubtService.getDoubtFor(sub, job, timeDoubtType);
                           // DoubtStatus ds=new ArrayList<>(d.getDoubtStatuses()).get(0);
                           TimeJobSubKey timeKey=generateTimeJobSubKey(job, sub);
                           Doubt d=timeDoubtMap.get(timeKey);
                           
                          // DoubtStatus ds=doubtStatusService.getDoubtStatusForDoubt(d).get(0);
                          DoubtStatus ds=doubtStatusMap.get(d);
                            String state=ds.getState();
                             jsm.getTimeCellModel().setState(state);
                            String status=ds.getStatus();
                            if(status.equals(DoubtStatusModel.OVERRIDE)){
                                jsm.getTimeCellModel().setOverride(true);
                            }else{
                                jsm.getTimeCellModel().setOverride(false);
                            }
                        }
                        jsm.getTraceCellModel().setActive(true);
                        jsm.getTraceCellModel().setCellProperty(x.getTraceSummary());
                        if(x.getTraceSummary()){
                          //  Doubt d=doubtService.getDoubtFor(sub, job, traceDoubtType);
                          TraceJobSubKey traceKey=generateTraceJobSubKey(job, sub);
                          Doubt d=traceDoubtMap.get(traceKey);
                           // DoubtStatus ds=new ArrayList<>(d.getDoubtStatuses()).get(0);
                          // DoubtStatus ds=doubtStatusService.getDoubtStatusForDoubt(d).get(0);
                          DoubtStatus ds=doubtStatusMap.get(d);
                            String state=ds.getState();
                            jsm.getTraceCellModel().setState(state);
                            String status=ds.getStatus();
                            if(status.equals(DoubtStatusModel.OVERRIDE)){
                                jsm.getTraceCellModel().setOverride(true);
                            }else{
                                jsm.getTraceCellModel().setOverride(false);
                            }
                        }else{                  //is there is  no doubt (summary is false) then check for inherited
                           // Doubt cause=doubtService.getCauseOfInheritedDoubtForType(sub, job, traceDoubtType);
                        }
                        jsm.getQcCellModel().setActive(true);
                        jsm.getQcCellModel().setCellProperty(x.getQcSummary());
                        if(x.getQcSummary()){
                           // Doubt d=doubtService.getDoubtFor(sub, job, qcDoubtType);
                           QcJobSubKey qcKey=generateQcJobSubKey(job, sub);
                           Doubt d=qcDoubtMap.get(qcKey);
                            //DoubtStatus ds=new ArrayList<>(d.getDoubtStatuses()).get(0);
                           // DoubtStatus ds=doubtStatusService.getDoubtStatusForDoubt(d).get(0);
                           DoubtStatus ds=doubtStatusMap.get(d);
                            String state=ds.getState();
                            jsm.getQcCellModel().setState(state);
                            String status=ds.getStatus();
                            if(status.equals(DoubtStatusModel.OVERRIDE)){
                                jsm.getTraceCellModel().setOverride(true);
                            }else{
                                jsm.getTraceCellModel().setOverride(false);
                            }
                        }
                        
                        
                        /*jsm.setTime(x.getTimeSummary());
                        
                        jsm.setTrace(x.getTraceSummary());
                        
                        jsm.setQc(x.getQcSummary());
                        
                        jsm.setInsight(x.getInsightSummary());
                        
                        jsm.setInheritance(x.getInheritanceSummary());
                        */
                      //  System.out.println("fend.summary.SummaryController.setModel(): Changed flags on the jobsummary model for depth: "+depth+" job: "+job.getNameJobStep()+" seq: "+seq.getSequenceno()+" sub: "+sub.getSubsurface());
                    }
                    
         
                    
                    System.out.println("fend.summary.SummaryController.setModel(): Building the tree");
                    
                 //   TreeItem<SequenceSummary> root=new TreeItem<>();
                 
                 List<TreeTableColumn<SequenceSummary,Depth>> depthColumns=new ArrayList<>();
        for(Depth depth: depthForColumns){
            TreeTableColumn<SequenceSummary,Depth> depthColumn = new TreeTableColumn<>("Depth: "+depth.getDepth()+"");
            //for(int jobId=0;jobId<depth.getJobSummaries().size();jobId++){
            Map<Job,JobSummaryModel> jobsummaryModelMap=depth.getJobSummaryMap();
            for (Map.Entry<Job, JobSummaryModel> entry : jobsummaryModelMap.entrySet()) {
                final Job jobkey = entry.getKey();
                JobSummaryModel jsm = entry.getValue();
                final int depthId=Integer.valueOf(depth.getDepth()+"");
                TreeTableColumn<SequenceSummary,Boolean> jobcolumn=new TreeTableColumn<>("Job: "+jsm.getJob().getNameJobStep());
                 
                 jobcolumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean>, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean> param) {
                   // System.out.println(".call(): returning : seq: "+param.getValue().getSequence().getSequenceno()+" active?: "+param.getValue().getDepths().get(depthId).getJobSummaries().get(fjobId).isActive());
                  return  new SimpleBooleanProperty(param.getValue().getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(jobkey).isActive());
                //return new SimpleBooleanProperty(param.getValue().getValue().getDepths().get(depthId).getJobSummaries().get(fjobId).isActive());
                }
                });
                
                 
                 //Beginning Time Column ==>
                 
                TreeTableColumn<SequenceSummary,Boolean> timeColumn=new TreeTableColumn<>("Time"); 
                timeColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean>, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean> param) {
                        /*if(param.getValue().getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(jobkey).getSubsurface()!=null)
                        System.out.println(".call(): RETURNING:  "+param.getValue().getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(jobkey).getSubsurface().getSubsurface()+" "
                        + "JOB: "+param.getValue().getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(jobkey).getJob().getNameJobStep()+
                        "  VALUE: "+param.getValue().getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(jobkey).getTimeCellModel().isActive());*/
                       return  new SimpleBooleanProperty(param.getValue().getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(jobkey).getTimeCellModel().isActive());
                    }
                });
                
                 //jobcolumn.setCellFactory(param->new JobSummaryCell(depthId,jobkey,this.model));
                 timeColumn.setCellFactory(param->new TimeCell(depthId,jobkey));
                 jobcolumn.getColumns().add(timeColumn);
                  //<==End of Time column
                  //Beginning Trace Column ==>
                 
                 TreeTableColumn<SequenceSummary,Boolean> traceColumn=new TreeTableColumn<>("Trace"); 
                traceColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean>, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean> param) {
                       return  new SimpleBooleanProperty(param.getValue().getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(jobkey).getTraceCellModel().isActive());
                    }
                });
                traceColumn.setCellFactory(param->new TraceCell(depthId,jobkey));
                 jobcolumn.getColumns().add(traceColumn);
                 
                 
                 //<==End of Trace column
                 
                    //Beginning Qc Column ==>
                 
                 TreeTableColumn<SequenceSummary,Boolean> qcColumn=new TreeTableColumn<>("Qc"); 
                qcColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean>, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean> param) {
                       return  new SimpleBooleanProperty(param.getValue().getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(jobkey).getQcCellModel().isActive());
                    }
                });
                qcColumn.setCellFactory(param->new QcCell(depthId,jobkey));
                 jobcolumn.getColumns().add(qcColumn);
                 
                 
                 //<==End of Qc column
                 
                depthColumn.getColumns().add(jobcolumn);
                
            }
           
            depthColumns.add(depthColumn);
        }
        
        
        
        TreeTableColumn<SequenceSummary,Long> seqTableColumn=new TreeTableColumn<>("seq");
        seqTableColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SequenceSummary, Long>, ObservableValue<Long>>() {
            @Override
            public ObservableValue<Long> call(TreeTableColumn.CellDataFeatures<SequenceSummary, Long> param) {
                return new SimpleLongProperty(param.getValue().getValue().getSequence().getSequenceno()).asObject();
            }
        });
        
        TreeTableColumn<SequenceSummary,String> subsurfaceTableColumn=new TreeTableColumn<>("subsurface");
        subsurfaceTableColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SequenceSummary, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SequenceSummary, String> param) {
                if(param.getValue().getValue().getSubsurface()==null){
                   return new SimpleStringProperty(param.getValue().getValue().getSequence().getRealLineName());
                }
                return new SimpleStringProperty(param.getValue().getValue().getSubsurface().getSubsurface());
            }
        });
        
        
        List<TreeItem<SequenceSummary>> treeSeq=new ArrayList<>();
        
         for (Map.Entry<Sequence, SequenceSummary> entry : seqSummaryMap.entrySet()) {
             Sequence seq = entry.getKey();
             SequenceSummary seqSummaryRoot = entry.getValue();
             
             TreeItem<SequenceSummary> seqItem=new TreeItem<>(seqSummaryRoot);
             
                Map<Subsurface,SequenceSummary> children=seqSummaryRoot.getChildren();
                for (Map.Entry<Subsurface, SequenceSummary> entry1 : children.entrySet()) {
                 Subsurface key = entry1.getKey();
                 SequenceSummary subSummaryChild = entry1.getValue();
                 TreeItem<SequenceSummary> subItem=new TreeItem<>(subSummaryChild);
                 
                    seqItem.getChildren().add(subItem);
             }
                treeSeq.add(seqItem);
             
         }
        
        
         /*for(SequenceSummary seq:sequenceSummaries){
         TreeItem<SequenceSummary> seqItem=new TreeItem<>(seq);
         for(SequenceSummary sub:seq.getChildren()){
         TreeItem<SequenceSummary> subItem=new TreeItem<>(sub);
         seqItem.getChildren().add(subItem);
         }
         treeSeq.add(seqItem);
         }*/
        
       // ObservableList<SequenceSummary> tableList=FXCollections.observableArrayList(sequenceSummaries);
        
        this.model.setSequenceSummaryMap(seqSummaryMap);
       
       
        treetable.getColumns().add(seqTableColumn);
        treetable.getColumns().add(subsurfaceTableColumn);
        treetable.getColumns().addAll(depthColumns);
        TreeItem<SequenceSummary> root=new TreeItem<>();
        root.getChildren().addAll(treeSeq);
        
        treetable.setRoot(root);
        treetable.setShowRoot(false);
        treetable.getSelectionModel().setCellSelectionEnabled(true);
                    
     }
     
     
     
     
     
     
    void setView(SummaryView vw) {
        this.view=vw;
        
        
        
        this.setScene(new Scene(this.view));
        showAndWait();
    }
    
    
    /**
     * Listener to refresh table
     **/
    
    private ChangeListener<Boolean> REFRESH_TABLE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
           treetable.refresh();
        }
    };
    
    
    private class TimeJobSubKey{
        Job job;
        Subsurface subsurface;

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + Objects.hashCode(this.job);
            hash = 29 * hash + Objects.hashCode(this.subsurface);
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
            final TimeJobSubKey other = (TimeJobSubKey) obj;
            if (!Objects.equals(this.job, other.job)) {
                return false;
            }
            if (!Objects.equals(this.subsurface, other.subsurface)) {
                return false;
            }
            return true;
        }
        
        
              
    }
    
    
    private class TraceJobSubKey{
        Job job;
        Subsurface subsurface;

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 97 * hash + Objects.hashCode(this.job);
            hash = 97 * hash + Objects.hashCode(this.subsurface);
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
            final TraceJobSubKey other = (TraceJobSubKey) obj;
            if (!Objects.equals(this.job, other.job)) {
                return false;
            }
            if (!Objects.equals(this.subsurface, other.subsurface)) {
                return false;
            }
            return true;
        }

        
        
              
    }
    
    
    private class QcJobSubKey{
        Job job;
        Subsurface subsurface;

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 53 * hash + Objects.hashCode(this.job);
            hash = 53 * hash + Objects.hashCode(this.subsurface);
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
            final QcJobSubKey other = (QcJobSubKey) obj;
            if (!Objects.equals(this.job, other.job)) {
                return false;
            }
            if (!Objects.equals(this.subsurface, other.subsurface)) {
                return false;
            }
            return true;
        }

       
        
        
              
    }
    
    
    private TimeJobSubKey generateTimeJobSubKey(Job job,Subsurface sub){
        TimeJobSubKey key=new TimeJobSubKey();
        key.job=job;
        key.subsurface=sub;
        
        return key;
    }
    
    
    private TraceJobSubKey generateTraceJobSubKey(Job job,Subsurface sub){
        TraceJobSubKey key=new TraceJobSubKey();
        key.job=job;
        key.subsurface=sub;
        
        return key;
                
    }
    
    private QcJobSubKey generateQcJobSubKey(Job job,Subsurface sub){
        QcJobSubKey key=new QcJobSubKey();
        key.job=job;
        key.subsurface=sub;
        
        return key;
                
    }
    
     private String timeNow(){
        return DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
    }
}
