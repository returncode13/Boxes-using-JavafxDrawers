/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.PheaderDAO;
import db.dao.PheaderDAOImpl;

import db.model.Job;
import db.model.Pheader;
import db.model.Subsurface;
import db.model.Volume;
import db.model.Workspace;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class PheaderServiceImpl implements PheaderService {
    PheaderDAO phDao=new PheaderDAOImpl();
    
    @Override
    public void createHeader(Pheader h) {
       phDao.createHeader(h);
    }
    
    @Override
    public void createBulkHeaders(List<Pheader> headers) {
        phDao.createBulkHeaders(headers);
    }

    @Override
    public Pheader getHeader(Long hid) {
        return phDao.getHeader(hid);
    }

    @Override
    public void updateHeader(Long hid, Pheader newH) {
        phDao.updateHeader(hid, newH);
    }

    @Override
    public void deleteHeader(Long hid) {
        phDao.deleteHeader(hid);
    }

    @Override
    public List<Pheader> getHeadersFor(Volume v) {
        return phDao.getHeadersFor(v);
    }
    
    @Override
    public List<Subsurface> getSubsurfacesToBeSummarized(Volume next) {
        return phDao.getSubsurfacesToBeSummarized(next);
    }

    @Override
    public List<Pheader> getHeadersFor(Job job) {
        return phDao.getHeadersFor(job);
    }

    @Override
    public void deleteHeadersFor(Volume v) {
        phDao.deleteHeadersFor(v);
    }

    @Override
    public void deleteHeadersFor(Job job) {
        phDao.deleteHeadersFor(job);
    }
    
     @Override
    public Pheader getHeadersFor(Volume dbvol, Subsurface dbsub, String timestamp) {
        return phDao.getHeadersFor(dbvol,dbsub,timestamp);
    }

    @Override
    public Set<Pheader> getMultipleInstances(Job job, Subsurface sub) {
        
        return phDao.getMultipleInstances(job, sub);
    }

    @Override
    public Pheader getChosenHeaderFor(Job job, Subsurface sub) throws Exception{
        return phDao.getChosenHeaderFor(job,sub);
    }

    @Override
    public String getLatestTimeStampFor(Volume volume) {
        return phDao.getLatestTimeStampFor(volume);
    }

    @Override
    public List<Pheader> getChosenHeadersForWorkspace(Workspace W) {
        return phDao.getChosenHeadersForWorkspace(W);
    }
}
