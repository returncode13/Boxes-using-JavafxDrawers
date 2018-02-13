/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import db.model.Job;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.Summary;
import db.model.Workspace;
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
import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel;
import fend.summary.SequenceSummary.Depth.JobSummaryCell;
import fend.summary.SequenceSummary.SequenceSummary;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
    // @FXML
   // private TableView<SequenceSummary> table;
    
     @FXML
    private TreeTableView<SequenceSummary> treetable;
    
     /*
    void setModel(SummaryModel item) {
        this.model=item;
        try {//if workspace.lastUpdateTime > workspace.lastSummaryTime. then execute Summary
            this.model.getWorkspaceController().summarize();
        } catch (Exception ex) {
            Logger.getLogger(SummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("fend.summary.SummaryController.setModel(): building summary table");
        
        
        List<SequenceSummary> sequenceSummaries=model.getSequenceSummaries();
        Workspace dbWorkspace=workspaceService.getWorkspace(model.getWorkspaceController().getModel().getId());
        List<Long> depths=jobService.getDepthOfGraph(dbWorkspace);
        Set<Job> allJobsInWorkspace=new HashSet<>(jobService.listJobs(dbWorkspace));
        Map<Long,List<JobSummaryModel>> depthJobMap=new HashMap<>();
        for(Job job:allJobsInWorkspace){
            if(depthJobMap.containsKey(job.getDepth())){
                JobSummaryModel jsm=new JobSummaryModel();
                jsm.setActive(false);
                jsm.setJob(job);
                depthJobMap.get(job.getDepth()).add(jsm);
            }else{
                depthJobMap.put(job.getDepth(), new ArrayList<>());
                JobSummaryModel jsm=new JobSummaryModel();
                jsm.setActive(false);
                jsm.setJob(job);
                depthJobMap.get(job.getDepth()).add(jsm);
            }
            
        }
        List<Depth> depthForColumns=new ArrayList<>();
        for(Long depth:depths){
            Depth sumDepth=new Depth();
            sumDepth.setDepth(depth);
            sumDepth.setJobSummaries(depthJobMap.get(depth));
            depthForColumns.add(sumDepth);
            
        }
        System.out.println("fend.summary.SummaryController.setModel(): No of columns for the table: "+depthForColumns.size());
          
        List<Sequence> sequences=sequenceService.getSequenceList();
        for(Sequence seq:sequences){                                                            //for each seq
            SequenceSummary seqSummary=new SequenceSummary();
            seqSummary.setSequence(seq);
            seqSummary.setDepths(depthForColumns);
            List<Depth> depthFromSeq=seqSummary.getDepths();
            
            List<Depth> depth2FromSeq=new ArrayList<>();
            // Unable to understand why I need a second list. The iterator should be changing the list entries..
            List<SequenceSummary> children=new ArrayList<>();
            List<Subsurface> subsurfacesInSeq=subsurfaceService.getSubsurfaceForSequence(seq);
            for(Subsurface sub:subsurfacesInSeq){
                SequenceSummary subsurfaceSummary=new SequenceSummary();
            
            
                        for(Depth depth:depthFromSeq){                                                  //for each depth
                        List<JobSummaryModel> jobSummaryModels=depth.getJobSummaries();             //job summary for a job that contains Seq seq and is at depth = depth

                         List<JobSummaryModel> jobSummaries=new ArrayList<>();
                        for (Iterator<JobSummaryModel> it = jobSummaryModels.iterator(); it.hasNext();) {



                                JobSummaryModel jsm = it.next();
                                JobSummaryModel nSubjsm=new JobSummaryModel();
                                nSubjsm.setJob(jsm.getJob());
                                nSubjsm.setSequence(seq);

                                List<Summary> summariesForJobSub=summaryService.getSummariesForJobSub(jsm.getJob(), sub, dbWorkspace);
                                System.out.println("fend.summary.SummaryController.setModel(): for seq: "+seq.getSequenceno()+" sub: "+sub.getSubsurface()+" job: "+jsm.getJob().getNameJobStep()+" summary Size : "+summariesForJobSub.size());
                                if(summariesForJobSub.size()>1) {
                                   // System.out.println("fend.summary.SummaryController.setModel(): MORE THAN ONE SUMMARY FOR A JOB/SEQ PAIR ENCOUNTERED!! ?: job: "+jsm.getJob().getId()+"  seq: "+seq.getSequenceno() );
                                }       if(summariesForJobSub.isEmpty()) {
                                    //do nothing. jsm stays inactive
                                    //System.out.println("fend.summary.SummaryController.setModel(): setting jsm to FALSE for seq: "+seq.getSequenceno()+" job: "+jsm.getJob().getNameJobStep());
                                    nSubjsm.setActive(false);
                                    nSubjsm.setSequence(seq);
                                }else{
                                    Summary sj=summariesForJobSub.get(0);
                                    //System.out.println("fend.summary.SummaryController.setModel(): setting jsm to TRUE for seq: "+seq.getSequenceno()+" job: "+jsm.getJob().getNameJobStep());
                                    nSubjsm.setActive(true);
                                    nSubjsm.setTime(sj.getTimeSummary());
                                    nSubjsm.setTrace(sj.getTraceSummary());
                                    nSubjsm.setQc(sj.getQcSummary());
                                    nSubjsm.setInsight(sj.getInsightSummary());
                                    nSubjsm.setInheritance(sj.getInheritanceSummary());
                                    nSubjsm.setSequence(seq);

                                } 
                                //nSeqjsm.setChildren(children);

                                jobSummaries.add(nSubjsm);





                            }
                        Depth depth2=new Depth();
                        depth2.setDepth(depth.getDepth());
                        depth2.setJobSummaries(jobSummaries);


                        //depth.setJobSummaries(jobSummaries);
                        depth2FromSeq.add(depth2);
                        }
                        subsurfaceSummary.setDepths(depth2FromSeq);
                        children.add(subsurfaceSummary);
            }
            seqSummary.setChildren(children);
          //  seqSummary.setDepths(depth2FromSeq);
            
          
            
            
            
            
            
                   
            sequenceSummaries.add(seqSummary);
        }
        
        /* for(SequenceSummary ssum:sequenceSummaries){
        for(Depth depth:ssum.getDepths()){
        for(JobSummaryModel jssm:depth.getJobSummaries()){
        System.err.println("seq: "+ssum.getSequence().getSequenceno()+" depth: "+depth.getDepth()+" job: "+jssm.getJob().getNameJobStep()+" active: "+jssm.isActive());
        }
        }
        }
        */
        /*
        
        List<TreeTableColumn<SequenceSummary,Depth>> depthColumns=new ArrayList<>();
        for(Depth depth: depthForColumns){
            TreeTableColumn<SequenceSummary,Depth> depthColumn = new TreeTableColumn<>("Depth: "+depth.getDepth()+"");
            for(int jobId=0;jobId<depth.getJobSummaries().size();jobId++){
            //for(JobSummaryModel jobSummaryModel:depth.getJobSummaries()){
            JobSummaryModel jobSummaryModel=depth.getJobSummaries().get(jobId);
            final int fjobId=jobId;
            final int depthId=Integer.valueOf(depth.getDepth()+"");
            
                TreeTableColumn<SequenceSummary,Boolean> jobcolumn=new TreeTableColumn<>("Job: "+jobSummaryModel.getJob().getNameJobStep());
                /*jobcolumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SequenceSummary, JobSummaryModel>, ObservableValue<JobSummaryModel>>() {
                @Overridey
                public ObservableValue<JobSummaryModel> call(TableColumn.CellDataFeatures<SequenceSummary, JobSummaryModel> param) {
                Job job=param.getValue().getDepths().get(Integer.valueOf(depthId)).getJobSummaries().get(fjobId).getJob();
                Boolean isActive=param.getValue().getDepths().get(Integer.valueOf(depthId)).getJobSummaries().get(fjobId).isActive();
                Sequence seq=param.getValue().getSequence();
                System.out.println(".call(): seq: "+seq.getSequenceno()+" job: "+job.getNameJobStep()+" isActive: "+isActive);
                return new SimpleObjectProperty<JobSummaryModel>(param.getValue().getDepths().get(Integer.valueOf(depthId)).getJobSummaries().get(fjobId));
                }
                });*/
              /*  jobcolumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean>, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean> param) {
                   // System.out.println(".call(): returning : seq: "+param.getValue().getSequence().getSequenceno()+" active?: "+param.getValue().getDepths().get(depthId).getJobSummaries().get(fjobId).isActive());
                   
                return new SimpleBooleanProperty(param.getValue().getValue().getDepths().get(depthId).getJobSummaries().get(fjobId).isActive());
                }
                });
                jobcolumn.setCellFactory(param->new JobSummaryCell(depthId,fjobId));
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
        
        
        List<TreeItem<SequenceSummary>> treeSeq=new ArrayList<>();
        for(SequenceSummary seq:sequenceSummaries){
            TreeItem<SequenceSummary> seqItem=new TreeItem<>(seq);
            for(SequenceSummary sub:seq.getChildren()){
                TreeItem<SequenceSummary> subItem=new TreeItem<>(sub);
                seqItem.getChildren().add(subItem);
            }
            treeSeq.add(seqItem);
        }
        
        ObservableList<SequenceSummary> tableList=FXCollections.observableArrayList(sequenceSummaries);
        
        treetable.getColumns().add(seqTableColumn);
        treetable.getColumns().addAll(depthColumns);
        TreeItem<SequenceSummary> root=new TreeItem<>();
        root.getChildren().addAll(treeSeq);
        
        treetable.setRoot(root);
        treetable.setShowRoot(false);
        treetable.getSelectionModel().setCellSelectionEnabled(true);
        
    }
*/
     
     void setModel(SummaryModel model){
         this.model=model;
         Map<Sequence,SequenceSummary> seqSummaryMap=new HashMap<>();
         //first get a list of all the subsurfaces.
         
         try {//if workspace.lastUpdateTime > workspace.lastSummaryTime. then execute Summary
            this.model.getWorkspaceController().summarize();
        } catch (Exception ex) {
            Logger.getLogger(SummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                    System.out.println("fend.summary.SummaryController.setModel(): building summary table");


                   
                    Workspace dbWorkspace=workspaceService.getWorkspace(model.getWorkspaceController().getModel().getId());
                    List<Long> depths=jobService.getDepthOfGraph(dbWorkspace);
                    Set<Job> allJobsInWorkspace=new HashSet<>(jobService.listJobs(dbWorkspace));
                    Map<Long,List<JobSummaryModel>> depthJobMap=new HashMap<>();
                    for(Job job:allJobsInWorkspace){
                        if(depthJobMap.containsKey(job.getDepth())){
                            JobSummaryModel jsm=new JobSummaryModel();
                            jsm.setActive(false);
                            jsm.setJob(job);
                            depthJobMap.get(job.getDepth()).add(jsm);
                        }else{
                            depthJobMap.put(job.getDepth(), new ArrayList<>());
                            JobSummaryModel jsm=new JobSummaryModel();
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
                                    JobSummaryModel newValue=new JobSummaryModel();
                                    newValue.setActive(value.isActive());
                                    newValue.setJob(value.getJob());
                                    newValue.setTime(value.isTime());
                                    newValue.setTrace(value.isTrace());
                                    newValue.setQc(value.isQc());
                                    newValue.setInsight(value.isInsight());
                                    newValue.setInheritance(value.isInheritance());
                                    newValue.setSequence(sub.getSequence());
                                    newValue.setSubsurface(sub);
                                    
                                    
                                    JobSummaryModel newSeqValue=new JobSummaryModel();
                                    newSeqValue.setActive(value.isActive());
                                    newSeqValue.setJob(value.getJob());
                                    newSeqValue.setTime(value.isTime());
                                    newSeqValue.setTrace(value.isTrace());
                                    newSeqValue.setQc(value.isQc());
                                    newSeqValue.setInsight(value.isInsight());
                                    newSeqValue.setInheritance(value.isInheritance());
                                    newSeqValue.setSequence(sub.getSequence());
                                    //newSeqValue.setSubsurface(sub);
                                    
                                    
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
                    System.out.println("fend.summary.SummaryController.setModel(): size of SummariesFromWorkspace: "+summariesInWorkspace.size());
                    for(Summary x:summariesInWorkspace){
                        Long depth=x.getDepth();
                        Sequence seq=x.getSequence();
                        Subsurface sub=x.getSubsurface();
                        Job job=x.getJob();
                        System.out.println("fend.summary.SummaryController.setModel(): from summaries :");
                        System.out.println("Seq: "+seq.getSequenceno());
                        System.out.println("Sub: "+sub.getSubsurface());
                        System.out.println("Dep: "+depth);
                        System.out.println("Job: "+job.getNameJobStep());
                        System.out.println("fend.summary.SummaryController.setModel(): size  of SeqsummaryMap.get("+seq.getSequenceno()+"): "+
                                seqSummaryMap.get(seq).
                                        getChildren().size());
                        JobSummaryModel seqJsm=seqSummaryMap.get(seq).getDepth(depth).getJobSummaryModel(job);
                        seqJsm.setActive(true);
                        seqJsm.setSubsurface(null);
                        seqJsm.setTime(seqJsm.isTime()||x.getTimeSummary());
                        seqJsm.setTrace(seqJsm.isTrace()||x.getTraceSummary());
                        seqJsm.setQc(seqJsm.isQc()||x.getQcSummary());
                        seqJsm.setInsight(seqJsm.isInsight()||x.getInsightSummary());
                        seqJsm.setInheritance(seqJsm.isInheritance()||x.getInheritanceSummary());
                        
                        
                        
                        JobSummaryModel jsm=seqSummaryMap.get(seq).
                                getChild(sub).
                                getDepth(depth).
                                getJobSummaryModel(job);
                        jsm.setActive(true);
                        jsm.setSubsurface(sub);
                        jsm.setTime(x.getTimeSummary());
                        jsm.setTrace(x.getTraceSummary());
                        jsm.setQc(x.getQcSummary());
                        jsm.setInsight(x.getInsightSummary());
                        jsm.setInheritance(x.getInheritanceSummary());
                        
                        System.out.println("fend.summary.SummaryController.setModel(): Changed flags on the jobsummary model for depth: "+depth+" job: "+job.getNameJobStep()+" seq: "+seq.getSequenceno()+" sub: "+sub.getSubsurface());
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
                
                
                 jobcolumn.setCellFactory(param->new JobSummaryCell(depthId,jobkey));
                depthColumn.getColumns().add(jobcolumn);
                
            }
            /* for(JobSummaryModel jobSummaryModel:depth.getJobSummaries()){
            JobSummaryModel jobSummaryModel=depth.getJobSummaries().get(jobId);
            final int fjobId=jobId;
            final int depthId=Integer.valueOf(depth.getDepth()+"");
            
            TreeTableColumn<SequenceSummary,Boolean> jobcolumn=new TreeTableColumn<>("Job: "+jobSummaryModel.getJob().getNameJobStep());*/
                /*jobcolumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SequenceSummary, JobSummaryModel>, ObservableValue<JobSummaryModel>>() {
                @Overridey
                public ObservableValue<JobSummaryModel> call(TableColumn.CellDataFeatures<SequenceSummary, JobSummaryModel> param) {
                Job job=param.getValue().getDepths().get(Integer.valueOf(depthId)).getJobSummaries().get(fjobId).getJob();
                Boolean isActive=param.getValue().getDepths().get(Integer.valueOf(depthId)).getJobSummaries().get(fjobId).isActive();
                Sequence seq=param.getValue().getSequence();
                System.out.println(".call(): seq: "+seq.getSequenceno()+" job: "+job.getNameJobStep()+" isActive: "+isActive);
                return new SimpleObjectProperty<JobSummaryModel>(param.getValue().getDepths().get(Integer.valueOf(depthId)).getJobSummaries().get(fjobId));
                }
                });*/
                /*jobcolumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean>, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<SequenceSummary, Boolean> param) {
                // System.out.println(".call(): returning : seq: "+param.getValue().getSequence().getSequenceno()+" active?: "+param.getValue().getDepths().get(depthId).getJobSummaries().get(fjobId).isActive());
                
                return new SimpleBooleanProperty(param.getValue().getValue().getDepths().get(depthId).getJobSummaries().get(fjobId).isActive());
                }
                });
                jobcolumn.setCellFactory(param->new JobSummaryCell(depthId,fjobId));
                depthColumn.getColumns().add(jobcolumn);
                }*/
            
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
    
}
