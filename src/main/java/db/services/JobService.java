/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import java.util.List;
import java.util.Set;
import db.model.Job;
import db.model.Volume;

/**
 *
 * @author sharath nair
 */
public interface JobService {
    public void createJob(Job js);
    public Job getJob(Long jobId);
    public void updateJob(Long jobId,Job newJs);
    public void deleteJob(Long jobId);
    
    
    /* public void startAlert(Job js);
    public void stopAlert(Job js);*/
    /*public void setPending(Job js);
    public void resetPending(Job js);*/
    
    public List<Job> listJobs();
    
   
}
