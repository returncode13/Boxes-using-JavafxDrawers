/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import app.properties.AppProperties;
import db.model.Doubt;
//import db.model.DoubtStatus;
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
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.IO.IOCell.IOCell;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Insight.InsightCell.InsightCell;
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
    private DoubtType ioDoubtType;
    private DoubtType inheritanceDoubtType;
    private DoubtTypeService doubtTypeService=new DoubtTypeServiceImpl();
    private DoubtStatusService doubtStatusService=new DoubtStatusServiceImpl();
    // @FXML
   // private TableView<SequenceSummary> table;
    
     @FXML
    private TreeTableView<SequenceSummary> treetable;
    
  
     void setModel(SummaryModel model){
         treetable.onSortProperty().addListener((observable) -> {
             treetable.refresh();
         });
         
         
         
         this.model=model;
         
         timeDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TIME);
         traceDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TRACES);
         qcDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.QC);
         insightDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.INSIGHT);
         inheritanceDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.INHERIT);
         ioDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.IO);
        

         
         Map<Sequence,SequenceSummary> seqSummaryMap=new HashMap<>();
        
         model.refreshTableProperty().addListener(REFRESH_TABLE_LISTENER);
         try {
              this.model.getWorkspaceController().summarizeOne();
        } catch (Exception ex) {
            Logger.getLogger(SummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
         Workspace workspace=this.model.getWorkspaceController().getModel().getWorkspace();
        
                    System.out.println("fend.summary.SummaryController.setModel(): building summary table");

                    
        // System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" getting all doubt status for workspace");           
//         List<DoubtStatus> doubtstatusForWorkspace=doubtStatusService.getAllDoubtStatusInWorkspace(workspace);
      //   System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" fetched "+doubtstatusForWorkspace.size()+" doubtstatus entries for workspace");
        // System.out.println("fend.summary.SummaryController.setModel(): "+timeNow()+" building the doubtstatus map");
        
                   
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
                                    newValue.setSubsurface(sub);
                                    newValue.setSequence(sub.getSequence());
                                    
                                    
                                    
                                    newValue.getTimeCellModel().setFailedTimeDependency(value.getTimeCellModel().cellHasFailedDependency());            //Time
                                    newValue.getTraceCellModel().setFailedTraceDependency(value.getTraceCellModel().cellHasFailedDependency());         //Trace
                                    newValue.getQcCellModel().setFailedQcDependency(value.getQcCellModel().cellHasFailedDependency());                  //Qc
                                    newValue.getInsightCellModel().setFailedInsightDependency(value.getInsightCellModel().cellHasFailedDependency());   //Insight
                                    newValue.getIoCellModel().setFailedIoDependency(value.getIoCellModel().cellHasFailedDependency());                //IO
                                    
                                    
                                    JobSummaryModel newSeqValue=new JobSummaryModel(model);
                                    newSeqValue.setActive(value.isActive());
                                    newSeqValue.setJob(value.getJob());
                                    newSeqValue.setSequence(sub.getSequence());
                                    
                                    newSeqValue.getTimeCellModel().setFailedTimeDependency(value.getTimeCellModel().cellHasFailedDependency());            //Time 
                                    newSeqValue.getTraceCellModel().setFailedTraceDependency(value.getTraceCellModel().cellHasFailedDependency());         //Trace
                                    newSeqValue.getQcCellModel().setFailedQcDependency(value.getQcCellModel().cellHasFailedDependency());                  //Qc
                                    newSeqValue.getInsightCellModel().setFailedInsightDependency(value.getInsightCellModel().cellHasFailedDependency());   //Insight
                                    newSeqValue.getIoCellModel().setFailedIoDependency(value.getIoCellModel().cellHasFailedDependency());                //IO
                                    
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
                    
                    
                    
                    
                    List<Summary> summariesInWorkspace=summaryService.getSummariesFor(dbWorkspace);
                    for(Summary x:summariesInWorkspace){
                        Long depth=x.getDepth();
                        Sequence seq=x.getSequence();
                        Subsurface sub=x.getSubsurface();
                        Job job=x.getJob();
                        
                        JobSummaryModel seqJsm=seqSummaryMap.get(seq).getDepth(depth).getJobSummaryModel(job);
                        seqJsm.setActive(true);
                        seqJsm.setSubsurface(null);
                        seqJsm.setSequence(seq);
                        seqJsm.setIsParent(true);
                        seqJsm.setIsChild(false);
                        //<--Start Time
                        seqJsm.getTimeCellModel().setActive(true);                                                                                      
                        seqJsm.getTimeCellModel().setFailedTimeDependency(seqJsm.getTimeCellModel().cellHasFailedDependency()||x.hasFailedTimeDependency());
                        seqJsm.getTimeCellModel().setInheritedTimeFail(seqJsm.getTimeCellModel().cellHasInheritedFail()||x.hasInheritedTimeFail());
                        seqJsm.getTimeCellModel().setInheritedTimeOverride(seqJsm.getTimeCellModel().cellHasInheritedOverride()||x.hasInheritedTimeOverride());
                        seqJsm.getTimeCellModel().setOverridenTimeFail(seqJsm.getTimeCellModel().cellHasOverridenFail()||x.hasOverridenTimeFail());
                        seqJsm.getTimeCellModel().setWarningForTime(seqJsm.getTimeCellModel().cellHasWarning()||x.hasWarningForTime());
                        //<-- End Time
                        
                        //<--Start Trace
                        seqJsm.getTraceCellModel().setActive(true);
                        seqJsm.getTraceCellModel().setFailedTraceDependency(seqJsm.getTraceCellModel().cellHasFailedDependency()||x.hasFailedTraceDependency());
                        seqJsm.getTraceCellModel().setInheritedTraceFail(seqJsm.getTraceCellModel().cellHasInheritedFail()||x.hasInheritedTraceFail());
                        seqJsm.getTraceCellModel().setInheritedTraceOverride(seqJsm.getTraceCellModel().cellHasInheritedOverride()||x.hasInheritedTraceOverride());
                        seqJsm.getTraceCellModel().setOverridenTraceFail(seqJsm.getTraceCellModel().cellHasOverridenFail()||x.hasOverridenTraceFail());
                        seqJsm.getTraceCellModel().setWarningForTrace(seqJsm.getTraceCellModel().cellHasWarning()||x.hasWarningForTrace());
                        //<--End Trace
                        
                        //<--Start Qc
                        seqJsm.getQcCellModel().setActive(true);
                        seqJsm.getQcCellModel().setFailedQcDependency(seqJsm.getQcCellModel().cellHasFailedDependency() || x.hasFailedQcDependency());
                        seqJsm.getQcCellModel().setInheritedQcFail(seqJsm.getQcCellModel().cellHasInheritedFail()||x.hasInheritedQcFail());
                        seqJsm.getQcCellModel().setInheritedQcOverride(seqJsm.getQcCellModel().cellHasInheritedOverride()||x.hasInheritedQcOverride());
                        seqJsm.getQcCellModel().setOverridenQcFail(seqJsm.getQcCellModel().cellHasOverridenFail()||x.hasOverridenQcFail());
                        seqJsm.getQcCellModel().setWarningForQc(seqJsm.getQcCellModel().cellHasWarning()||x.hasWarningForQc());
                        //<--End Qc
                        
                        //<--Start Insight
                        seqJsm.getInsightCellModel().setActive(true);
                        seqJsm.getInsightCellModel().setFailedInsightDependency(seqJsm.getInsightCellModel().cellHasFailedDependency() || x.hasFailedInsightDependency());
                        seqJsm.getInsightCellModel().setInheritedInsightFail(seqJsm.getInsightCellModel().cellHasInheritedFail()||x.hasInheritedInsightFail());
                        seqJsm.getInsightCellModel().setInheritedInsightOverride(seqJsm.getInsightCellModel().cellHasInheritedOverride()||x.hasInheritedInsightOverride());
                        seqJsm.getInsightCellModel().setOverridenInsightFail(seqJsm.getInsightCellModel().cellHasOverridenFail()||x.hasOverridenInsightFail());
                        seqJsm.getInsightCellModel().setWarningForInsight(seqJsm.getInsightCellModel().cellHasWarning()||x.hasWarningForInsight());
                        //<--End Insight
                        
                        
                        //<--Start IO
                        seqJsm.getIoCellModel().setActive(true);
                        seqJsm.getIoCellModel().setFailedIoDependency(seqJsm.getIoCellModel().cellHasFailedDependency() || x.hasFailedIoDependency());
                        seqJsm.getIoCellModel().setInheritedIoFail(seqJsm.getIoCellModel().cellHasInheritedFail() || x.hasInheritedIoFail());
                        seqJsm.getIoCellModel().setInheritedIoOverride(seqJsm.getIoCellModel().cellHasInheritedOverride() || x.hasInheritedIoOverride());
                        seqJsm.getIoCellModel().setOverridenIoFail(seqJsm.getIoCellModel().cellHasOverridenFail() || x.hasOverridenIoFail());
                        seqJsm.getIoCellModel().setWarningForIo(seqJsm.getIoCellModel().cellHasWarning() || x.hasWarningForIo());
                        //<--End IO
                        
                        JobSummaryModel jsm=seqSummaryMap.get(seq).
                                getChild(sub).
                                getDepth(depth).
                                getJobSummaryModel(job);
                        jsm.setActive(true);
                        jsm.setSubsurface(sub);
                        jsm.setSequence(sub.getSequence());
                        jsm.setIsParent(false);
                        jsm.setIsChild(true);
                        //<--Start Time
                        jsm.getTimeCellModel().setActive(true);
                        jsm.getTimeCellModel().setFailedTimeDependency(x.hasFailedTimeDependency());
                        jsm.getTimeCellModel().setInheritedTimeFail(x.hasInheritedTimeFail());
                        jsm.getTimeCellModel().setInheritedTimeOverride(x.hasInheritedTimeOverride());
                        jsm.getTimeCellModel().setOverridenTimeFail(x.hasOverridenTimeFail());
                        jsm.getTimeCellModel().setWarningForTime(x.hasWarningForTime());
                        //<--End Time
                        
                        //<--Start Trace
                        jsm.getTraceCellModel().setActive(true);
                        jsm.getTraceCellModel().setFailedTraceDependency(x.hasFailedTraceDependency());
                        jsm.getTraceCellModel().setInheritedTraceFail(x.hasInheritedTraceFail());
                        jsm.getTraceCellModel().setInheritedTraceOverride(x.hasInheritedTraceOverride());
                        jsm.getTraceCellModel().setOverridenTraceFail(x.hasOverridenTraceFail());
                        jsm.getTraceCellModel().setWarningForTrace(x.hasWarningForTrace());
                        //<--End Trace
                        
                        //<--Start Qc
                        jsm.getQcCellModel().setActive(true);
                        jsm.getQcCellModel().setFailedQcDependency(x.hasFailedQcDependency());
                        jsm.getQcCellModel().setInheritedQcFail(x.hasInheritedQcFail());
                        jsm.getQcCellModel().setInheritedQcOverride(x.hasInheritedQcOverride());
                        jsm.getQcCellModel().setOverridenQcFail(x.hasOverridenQcFail());
                        jsm.getQcCellModel().setWarningForQc(x.hasWarningForQc());
                        //<--End Qc
                        
                        
                        //<--Start Insight
                        jsm.getInsightCellModel().setActive(true);
                        jsm.getInsightCellModel().setFailedInsightDependency(x.hasFailedInsightDependency());
                        jsm.getInsightCellModel().setInheritedInsightFail(x.hasInheritedInsightFail());
                        jsm.getInsightCellModel().setInheritedInsightOverride(x.hasInheritedInsightOverride());
                        jsm.getInsightCellModel().setOverridenInsightFail(x.hasOverridenInsightFail());
                        jsm.getInsightCellModel().setWarningForInsight(x.hasWarningForInsight());
                        //<--End Insight
                        
                        //<--Start IO
                        jsm.getIoCellModel().setActive(true);
                        jsm.getIoCellModel().setFailedIoDependency(x.hasFailedIoDependency());
                        jsm.getIoCellModel().setInheritedIoFail(x.hasInheritedIoFail());
                        jsm.getIoCellModel().setInheritedIoOverride(x.hasInheritedIoOverride());
                        jsm.getIoCellModel().setOverridenIoFail(x.hasOverridenIoFail());
                        jsm.getIoCellModel().setWarningForIo(x.hasWarningForIo());
                        //<--End IO
                        
                      
                        
                        }
                    
         
                    
                    System.out.println("fend.summary.SummaryController.setModel(): Building the tree");
                    
                
                 
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
                  return  new SimpleBooleanProperty(param.getValue().getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(jobkey).isActive());
                 }
                });
                
                 
                 //Beginning Time Column ==>
                TreeTableColumn<SequenceSummary,Boolean> timeColumn=new TreeTableColumn<>("Time"); 
                timeColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean>, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean> param) {
                       return  new SimpleBooleanProperty(param.getValue().getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(jobkey).getTimeCellModel().isActive());
                    }
                });
                 timeColumn.setCellFactory(param->new TimeCell(depthId,jobkey,timeDoubtType));
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
                traceColumn.setCellFactory(param->new TraceCell(depthId,jobkey,traceDoubtType));
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
                qcColumn.setCellFactory(param->new QcCell(depthId,jobkey,qcDoubtType));
                 jobcolumn.getColumns().add(qcColumn);
                //<==End of Qc column
                 
                 //<==Start of Insight column
                    TreeTableColumn<SequenceSummary,Boolean> insightColumn=new TreeTableColumn<>("Insight"); 
                    insightColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean>, ObservableValue<Boolean>>() {
                        @Override
                        public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean> param) {
                           return  new SimpleBooleanProperty(param.getValue().getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(jobkey).getInsightCellModel().isActive());
                        }
                    });
                     insightColumn.setCellFactory(param->new InsightCell(depthId,jobkey,insightDoubtType));
                     jobcolumn.getColumns().add(insightColumn);
                 //<==End of Insight column
                 
                 
                 //<==Start of IO column
                    TreeTableColumn<SequenceSummary,Boolean> ioColumn=new TreeTableColumn<>("IO"); 
                    ioColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean>, ObservableValue<Boolean>>() {
                        @Override
                        public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean> param) {
                           return  new SimpleBooleanProperty(param.getValue().getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(jobkey).getIoCellModel().isActive());
                        }
                    });
                     ioColumn.setCellFactory(param->new IOCell(depthId,jobkey,ioDoubtType));
                     jobcolumn.getColumns().add(ioColumn);
                 
                 //<==End of IO column
                 
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
        show();
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
    
  
     private String timeNow(){
        return DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
    }
}
