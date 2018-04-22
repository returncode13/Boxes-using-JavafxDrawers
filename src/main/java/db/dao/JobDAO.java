/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import java.util.List;
import java.util.Set;
import db.model.Job;
import db.model.Volume;
import db.model.Workspace;

/**
 *
 * @author sharath nair
 */
public interface JobDAO {
    
    // Crud ops
    public void createJob(Job js);
    public Job getJob(Long jobId);
    public void updateJob(Long jobId,Job newJs);
    public void deleteJob(Long jobId);
    public List<Long> getDepthOfGraph(Workspace W);
    /* public void startAlert(Job js);
    public void stopAlert(Job js);*/
    /*public void setPending(Job js);
    public void resetPending(Job js);*/

    public List<Job> listJobs(Workspace W);

    public void updateName(Job dbjob, String name);

    public void updateDepth(Job dbjob, Long newValue);

    public void updateInsightVersionInJob(Job job);

    public List<Job> getJobsInWorkspace(Workspace W);
    
}
