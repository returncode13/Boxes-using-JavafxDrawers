/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.VolumeDAO;
import db.dao.VolumeDAOImpl;
import db.model.Job;
import java.util.List;

import db.model.Volume;
import db.model.Workspace;

/**
 *
 * @author sharath nair
 */
public class VolumeServiceImpl implements VolumeService{

    VolumeDAO volDao=new VolumeDAOImpl();
    
    
    @Override
    public void createVolume(Volume v) {
        volDao.createVolume(v);
    }

    @Override
    public Volume getVolume(Long volid) {
        return volDao.getVolume(volid);
    }

    @Override
    public void updateVolume(Long volid, Volume newVol) {
        volDao.updateVolume(volid, newVol);
    }

    @Override
    public void deleteVolume(Long volid) {
        volDao.deleteVolume(volid);
    }

    @Override
    public void startAlert(Volume v) {
        volDao.startAlert(v);
    }

    @Override
    public void stopAlert(Volume v) {
       volDao.stopAlert(v);
    }

    @Override
    public void setHeaderExtractionFlag(Volume v) {
        volDao.setHeaderExtractionFlag(v);
    }

    @Override
    public void resetHeaderExtractionFlag(Volume v) {
        volDao.resetHeaderExtractionFlag(v);
    }

    @Override
    public List<Volume> getVolumesForJob(Job dbjob) {
        return volDao.getVolumesForJob(dbjob);
    }

    @Override
    public List<Volume> getAllVolumesIn(Workspace workspace) {
        return volDao.getAllVolumesIn(workspace);
    }

    @Override
    public void deleteAllVolumesFor(Job job) {
        volDao.deleteAllVolumesFor(job);
    }

    
    
}
