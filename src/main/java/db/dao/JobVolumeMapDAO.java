/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import java.util.List;
import db.model.Job;
import db.model.JobVolumeMap;
import db.model.Volume;

/**
 *
 * @author sharath nair
 */
public interface JobVolumeMapDAO {
    public void createJobVolumeMap(JobVolumeMap jvd);
    public JobVolumeMap getJobVolumeMap(Long jvdId);
    public void updateJobVolumeMap(Long jvdId,JobVolumeMap newJvd);
    public void deleteJobVolumeMap(Long jvdId);
    
    public List<JobVolumeMap> getJobVolumeMap(Job js);
    public List<JobVolumeMap> getJobVolumeMap(Volume v);
    public JobVolumeMap getJobVolumeMap(Job js, Volume v);

    public List<Job> getJobsContaining(Volume v);        //convenience function to return the foreign key for the pk=v.getPK();
    public List<Volume> getVolumesFor(Job js);              
    
}
