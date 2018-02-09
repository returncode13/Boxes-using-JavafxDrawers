/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import db.model.Job;
import db.model.Sequence;
import db.model.Summary;
import db.model.Workspace;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.SequenceService;
import db.services.SequenceServiceImpl;
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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    
     @FXML
    private TableView<SequenceSummary> table;
    
    
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
        List<Job> allJobsInWorkspace=jobService.listJobs(dbWorkspace);
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
            
                for(Depth depth:depthFromSeq){                                                  //for each depth
                    List<JobSummaryModel> jobSummaryModels=depth.getJobSummaries();             //job summary for a job that contains Seq seq and is at depth = depth
                    
                    
                    
                    for(JobSummaryModel jsm:jobSummaryModels){
                        List<Summary> summariesForJobsAtDepth=summaryService.getSummariesForJobSeq(jsm.getJob(), seq, dbWorkspace);
                        System.out.println("fend.summary.SummaryController.setModel(): for seq: "+seq.getSequenceno()+" job: "+jsm.getJob().getNameJobStep()+" summary Size : "+summariesForJobsAtDepth.size());
                        if(summariesForJobsAtDepth.size()>1) {
                            System.out.println("fend.summary.SummaryController.setModel(): MORE THAN ONE SUMMARY FOR A JOB/SEQ PAIR ENCOUNTERED!! ?: job: "+jsm.getJob().getId()+"  seq: "+seq.getSequenceno() );
                        }
                        if(summariesForJobsAtDepth.isEmpty()) {
                            //do nothing. jsm stays inactive
                            jsm.setActive(false);
                        }else{
                            Summary sj=summariesForJobsAtDepth.get(0);
                            jsm.setActive(true);
                            jsm.setTime(sj.getTimeSummary());
                            jsm.setTrace(sj.getTraceSummary());
                            jsm.setQc(sj.getQcSummary());
                            jsm.setInsight(sj.getInsightSummary());
                            jsm.setInheritance(sj.getInheritanceSummary());
                            
                        }
                    }
                    
                    
                    
                    /*if(summariesForJobsAtDepth.isEmpty()){              //seq hasnt got a summary here. possible case where the sequence is absent at this depth
                    JobSummaryModel jobSummaryModel=new JobSummaryModel();
                    jobSummaryModel.setActive(false);
                    //jobSummaryModels.add(jobSummaryModel);
                    }else{
                    for(Summary sj:summariesForJobsAtDepth){
                    JobSummaryModel jobSummaryModel=new JobSummaryModel();
                    jobSummaryModel.setActive(true);
                    jobSummaryModel.setJob(sj.getJob());
                    jobSummaryModel.setQc(sj.getQcSummary());
                    jobSummaryModel.setTime(sj.getTimeSummary());
                    jobSummaryModel.setTrace(sj.getTraceSummary());
                    jobSummaryModel.setInsight(sj.getInsightSummary());
                    jobSummaryModel.setInheritance(sj.getInheritanceSummary());
                    jobSummaryModels.add(jobSummaryModel);
                    }
                    
                    }*/
                    depth.setJobSummaries(jobSummaryModels);
                                        
                }
            seqSummary.setDepths(depthFromSeq);
            sequenceSummaries.add(seqSummary);
        }
        
        
        List<TableColumn<SequenceSummary,Depth>> depthColumns=new ArrayList<>();
        for(Depth depth: depthForColumns){
            TableColumn<SequenceSummary,Depth> depthColumn = new TableColumn<>("Depth: "+depth.getDepth()+"");
            
            for(JobSummaryModel jobSummaryModel:depth.getJobSummaries()){
                TableColumn<SequenceSummary,JobSummaryModel> jobcolumn=new TableColumn<>("Job: "+jobSummaryModel.getJob().getNameJobStep());
                jobcolumn.setCellFactory(param->new JobSummaryCell(jobSummaryModel));
                depthColumn.getColumns().add(jobcolumn);
            }
            
            depthColumns.add(depthColumn);
        }
        
        
        
        TableColumn<SequenceSummary,Long> seqTableColumn=new TableColumn<>("seq");
        seqTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SequenceSummary, Long>, ObservableValue<Long>>() {
            @Override
            public ObservableValue<Long> call(TableColumn.CellDataFeatures<SequenceSummary, Long> param) {
                return new SimpleLongProperty(param.getValue().getSequence().getSequenceno()).asObject();
            }
        });
        
        
        
        
        ObservableList<SequenceSummary> tableList=FXCollections.observableArrayList(sequenceSummaries);
        table.getColumns().add(seqTableColumn);
        table.getColumns().addAll(depthColumns);
        table.setItems(tableList);
        
    }

    void setView(SummaryView vw) {
        this.view=vw;
        
        
        
        this.setScene(new Scene(this.view));
        showAndWait();
    }
    
}
