/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.JobVolumeMapDAO;
import db.dao.JobVolumeMapDAOImpl;
import java.util.List;
import db.model.Job;
import db.model.JobVolumeMap;
import db.model.Volume;

/**
 *
 * @author sharath nair
 */
public class JobVolumeMapServiceImpl implements JobVolumeMapService{
    
    JobVolumeMapDAO jvdDao=new JobVolumeMapDAOImpl();
    
    @Override
    public void createJobVolumeMap(JobVolumeMap jvd) {
        jvdDao.createJobVolumeMap(jvd);
    }

    @Override
    public JobVolumeMap getJobVolumeMap(Long jvdId) {
        return jvdDao.getJobVolumeMap(jvdId);
    }

    @Override
    public void updateJobVolumeMap(Long jvdId, JobVolumeMap newJvd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteJobVolumeMap(Long jvdId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<JobVolumeMap> getJobVolumeMap(Job js) {
        return jvdDao.getJobVolumeMap(js);
    }

    @Override
    public List<JobVolumeMap> getJobVolumeMap(Volume v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Volume> getVolumesFor(Job js) {
        return jvdDao.getVolumesFor(js);
    }

    @Override
    public void updateVolumesFor(Job js, List<Volume> volumes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteVolumesFor(Job js) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Job> getJobsContaining(Volume v) {
      return jvdDao.getJobsContaining(v);
    }

    @Override
    public JobVolumeMap getJobVolumeMap(Job js, Volume v) {
        return jvdDao.getJobVolumeMap(js, v);
    }
    
}
