/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.JobDAO;
import db.dao.JobDAOImpl;
import java.util.List;
import java.util.Set;
import db.model.Job;
import db.model.Volume;
import db.model.Workspace;

/**
 *
 * @author sharath nair
 */
public class JobServiceImpl implements JobService {
    
    JobDAO jobStepDAO= new JobDAOImpl();

    public JobServiceImpl() {
    }

        
    @Override
    public void createJob(Job js) {
        jobStepDAO.createJob(js);
    }

    @Override
    public Job getJob(Long jobId) {
        return jobStepDAO.getJob(jobId);
    }

    @Override
    public void updateJob(Long jobId, Job newJs) {
        jobStepDAO.updateJob(jobId, newJs);
    }

    @Override
    public void deleteJob(Long jobId) {
        jobStepDAO.deleteJob(jobId);
    }

    @Override
    public List<Job> listJobs(Workspace W) {
       return jobStepDAO.listJobs(W);
    }

    /*@Override
    public void startAlert(Job js) {
    jobStepDAO.startAlert(js);   }
    
    @Override
    public void stopAlert(Job js) {
    jobStepDAO.stopAlert(js);    }*/
    /*@Override
    public void setPending(Job js) {
    jobStepDAO.setPending(js);
    }
    
    @Override
    public void resetPending(Job js) {
    jobStepDAO.resetPending(js);
    }*/

    @Override
    public List<Long> getDepthOfGraph(Workspace W) {
        return jobStepDAO.getDepthOfGraph(W);
    }

    @Override
    public void updateName(Job dbjob, String name) {
        jobStepDAO.updateName(dbjob,name);
    }

    
    
}
