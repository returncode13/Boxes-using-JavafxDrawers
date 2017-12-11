/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import java.util.List;
import db.model.Job;
import db.model.JobVolumeMap;
import db.model.Volume;

/**
 *
 * @author sharath nair
 */
public interface JobVolumeMapService {
     public void createJobVolumeMap(JobVolumeMap jvd);
    public JobVolumeMap getJobVolumeMap(Long jvdId);
    public void updateJobVolumeMap(Long jvdId,JobVolumeMap newJvd);
    public void deleteJobVolumeMap(Long jvdId);
    
    public List<JobVolumeMap> getJobVolumeMap(Job js);
    public List<JobVolumeMap> getJobVolumeMap(Volume v);
    
    public List<Volume> getVolumesFor(Job js);              //convenience function to get volume records from the Volume table where foreign key =js
    public void updateVolumesFor(Job js,List<Volume> volumes);//update existing volume records in the Volume table where foreign key =js
    public void deleteVolumesFor(Job js);                      //delete all volume records in the volume table where foreign key=js
    public List<Job> getJobsContaining(Volume v);                  //convenience function to return the foreign key for the pk=v.getPK();
    public JobVolumeMap getJobVolumeMap(Job js, Volume v);
}
