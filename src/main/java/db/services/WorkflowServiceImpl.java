/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.WorkflowDAO;
import db.dao.WorkflowDAOImpl;
import db.model.Job;
import db.model.Volume;
import db.model.Workflow;
import java.util.List;

/**
 *
 * @author sharath nair
 */
public class WorkflowServiceImpl implements WorkflowService{
    WorkflowDAO wdao=new WorkflowDAOImpl();
    
    
    @Override
    public void createWorkFlow(Workflow W) {
        wdao.createWorkFlow(W);
    }

    @Override
    public Workflow getWorkflow(Long wid) {
        return wdao.getWorkflow(wid);
    }

    @Override
    public void updateWorkFlow(Long wid, Workflow newW) {
        wdao.updateWorkFlow(wid, newW);
    }

    @Override
    public void deleteWorkFlow(Long wid) {
        wdao.deleteWorkFlow(wid);
    }

    @Override
    public List<Workflow> getWorkFlowsFor(Volume v) {
       return wdao.getWorkFlowsFor(v);
    }

    @Override
    public Workflow getWorkFlowWith(String md5, Volume vol) {
        return wdao.getWorkFlowWith(md5, vol);
    }

    @Override
    public Workflow getWorkFlowVersionFor(Volume v) {
        return wdao.getWorkFlowVersionFor(v);
    }

    @Override
    public Workflow getWorkflowRunBeforeTime(String time, Volume vol) {
        return wdao.getWorkflowRunBeforeTime(time, vol);
    }

    @Override
    public Long getHighestWorkFlowVersionFor(Volume v) {
        return wdao.getHighestWorkFlowVersionFor(v);
    }

    @Override
    public void deleteWorkFlowsFor(Volume vol) {
        wdao.deleteWorkFlowsFor(vol) ;
    }

    @Override
    public void deleteWorkFlowsFor(Job job) {
        wdao.deleteWorkFlowsFor(job);
    }

    @Override
    public Long getHighestWorkFlowVersionFor(Job job) {
       return wdao.getHighestWorkFlowVersionFor(job);
    }

    @Override
    public List<Workflow> getWorkFlowsFor(Job job) {
       return wdao.getWorkFlowsFor(job);
    }

    @Override
    public void updateControlFor(Workflow workflow, Boolean control) {
        wdao.updateControlFor(workflow, control);
    }

    @Override
    public void updateCurrentVersionsFor(Job job) {
        wdao.updateCurrentVersionsFor(job);
    }
    
}
