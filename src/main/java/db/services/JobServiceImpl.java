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
    public List<Job> listJobs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    
    
}
