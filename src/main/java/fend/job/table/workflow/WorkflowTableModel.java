/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.workflow;

import db.model.Comment;
import db.model.Job;
import db.model.Workflow;
import db.services.CommentService;
import db.services.CommentServiceImpl;
import db.services.WorkflowService;
import db.services.WorkflowServiceImpl;
import fend.comments.CommentTypeModel;
import fend.job.table.workflow.workflowmodel.WorkflowModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkflowTableModel {
    
    Job job;
    List<WorkflowModel> workflows=new ArrayList<>();
    private CommentService commentService=new CommentServiceImpl();
    private WorkflowService workflowService=new WorkflowServiceImpl();
    Map<Workflow,List<Comment>> workflowCommentMap=new HashMap<>(); 
    List<WorkflowModel> currentWorkflows=new ArrayList<>();
    Map<Long,Workflow> mapversionWorkflow=new HashMap<>();
    List<Long> versions=new ArrayList<>(); 
    
    public List<WorkflowModel> getCurrentWorkflows() {
        return currentWorkflows;
    }
    
    
    
    
    public List<WorkflowModel> getWorkflows() {
        return workflows;
    }

    public void setWorkflows(List<WorkflowModel> workflows) {
        this.workflows = workflows;
    }

    
    
    
    public WorkflowTableModel(Job j) {
        job=j;
        //get all workflows belonging to this job
        //get all comments belonging to each of the workflows
        commentService.getCommentsFor(job,CommentTypeModel.TYPE_WORKFLOW,workflowCommentMap);
        List<Workflow> workflowsInJob=workflowService.getWorkFlowsFor(job);
        System.out.println("fend.job.table.workflow.WorkflowTableModel.<init>(): "+workflowsInJob.size()+" workflows found for job "+job.getNameJobStep());
        for(Workflow w:workflowsInJob){
            WorkflowModel wfm=new WorkflowModel();
            wfm.setVersion(w.getWfversion());
            versions.add(w.getWfversion());
            
            
            String lastComment;
            if(workflowCommentMap.containsKey(w)){
                System.out.println("fend.job.table.workflow.WorkflowTableModel.<init>(): key found!: "+w.getId());
                if(!workflowCommentMap.get(w).isEmpty()){
                   
                    lastComment=workflowCommentMap.get(w).get(0).getComments();
                     System.out.println("fend.job.table.workflow.WorkflowTableModel.<init>(): comments present "+lastComment);
                }else{
                     System.out.println("fend.job.table.workflow.WorkflowTableModel.<init>(): NO comments present ");
                    lastComment="";
                }
            }else{
                lastComment="";
            }
            wfm.setCommentStack(lastComment);
            wfm.setControl(w.getControl());
            wfm.setWorkflow(w);
            wfm.setCurrentVersionProperty(w.isCurrentVersion());
            if(w.isCurrentVersion()){
                currentWorkflows.add(wfm);
            }
            workflows.add(wfm);
            mapversionWorkflow.put(w.getWfversion(), w);
        }
        
        
        
    }

    public Job getJob() {
        return job;
    }

    public Map<Long, Workflow> getMapversionWorkflow() {
        return mapversionWorkflow;
    }

    public List<Long> getVersions() {
        return versions;
    }
    
    
    
    
    
}
