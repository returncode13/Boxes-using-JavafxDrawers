/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Job;
import db.model.Volume;
import db.model.Workflow;
import db.model.Workspace;
import java.util.List;

/**
 *
 * @author adira0150
 */
public interface WorkflowDAO {
    public void createWorkFlow(Workflow W );
    public Workflow getWorkflow(Long wid);
    public void updateWorkFlow(Long wid,Workflow newW);
    public void deleteWorkFlow(Long wid);
    
    public List<Workflow> getWorkFlowsFor(Volume v);
    public List<Workflow> getWorkFlowsFor(Job job);
    public Workflow getWorkFlowWith(String md5,Volume vol);          //return workflow having the md5sumchecks=md5 for volume vol
    public Workflow getWorkFlowVersionFor(Volume v);            //highest workflow version for the given volume
    public Workflow getWorkflowRunBeforeTime(String time,Volume vol);    //Used for type 2 Volumes. The latest workflow before time=time is the workflow that needs to be assigned to the subsurface
    public Long getHighestWorkFlowVersionFor(Volume v);            //highest workflow version for the given volume
    public Long getHighestWorkFlowVersionFor(Job job);            //highest workflow version for the given job

    public void deleteWorkFlowsFor(Volume vol);

    public void deleteWorkFlowsFor(Job job);

    public void updateControlFor(Workflow workflow, Boolean control);
    public void updateCurrentVersionsFor(Job job);

    public List<Object[]> getCurrentWorkflowsIn(Workspace workspace);

    public void updateSubsurfacesForJobWithWorkflow(Job job, Workflow workflow);

    
}
