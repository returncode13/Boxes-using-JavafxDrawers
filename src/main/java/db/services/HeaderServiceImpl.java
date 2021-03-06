/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.HeaderDAO;
import db.dao.HeaderDAOImpl;
import java.util.List;
import java.util.Set;
import db.model.Header;
import db.model.Job;
import db.model.Subsurface;
import db.model.Volume;
import db.model.Workspace;

/**
 *
 * @author sharath nair
 */
public class HeaderServiceImpl implements HeaderService{

    HeaderDAO hDao=new HeaderDAOImpl();
    
    @Override
    public void createHeader(Header h) {
       hDao.createHeader(h);
    }
    
    @Override
    public void createBulkHeaders(List<Header> headers) {
        hDao.createBulkHeaders(headers);
    }

    @Override
    public Header getHeader(Long hid) {
        return hDao.getHeader(hid);
    }

    @Override
    public void updateHeader(Long hid, Header newH) {
        hDao.updateHeader(hid, newH);
    }

    @Override
    public void deleteHeader(Long hid) {
        hDao.deleteHeader(hid);
    }

    @Override
    public List<Header> getHeadersFor(Volume v) {
        return hDao.getHeadersFor(v);
    }
    
    @Override
    public List<Subsurface> getSubsurfacesToBeSummarized(Volume next) {
        return hDao.getSubsurfacesToBeSummarized(next);
    }

    @Override
    public List<Header> getHeadersFor(Job job) {
        return hDao.getHeadersFor(job);
    }

    @Override
    public void deleteHeadersFor(Volume v) {
        hDao.deleteHeadersFor(v);
    }

    @Override
    public void deleteHeadersFor(Job job) {
        hDao.deleteHeadersFor(job);
    }
    
   
    @Override
    public Header getHeadersFor(Volume dbvol, Subsurface dbsub, String timestamp) {
        return hDao.getHeadersFor(dbvol,dbsub,timestamp);
    }

    @Override
    public Set<Header> getMultipleInstances(Job job, Subsurface sub) {
        
        return hDao.getMultipleInstances(job, sub);
    }

    @Override
    public Header getChosenHeaderFor(Job job, Subsurface sub) throws Exception{
        return hDao.getChosenHeaderFor(job,sub);
    }

    @Override
    public String getLatestTimeStampFor(Volume volume) {
        return hDao.getLatestTimeStampFor(volume);
    }

    @Override
    public List<Header> getChosenHeadersForWorkspace(Workspace W) {
        return hDao.getChosenHeadersForWorkspace(W);
    }

    @Override
    public void checkForMultipleSubsurfacesInHeadersForJob(Job job) {
        hDao.checkForMultipleSubsurfacesInHeadersForJob(job);
    }

    @Override
    public void setChosenToFalseForConflictingSubs(Subsurface conflictedSub, Job job, Volume volumeToBeExcluded) {
        hDao.setChosenToFalseForConflictingSubs(conflictedSub,job,volumeToBeExcluded);
    }

    @Override
    public void updateDeleteFlagsFor(Volume vol, List<String> subsurfacesOnDisk) {
        hDao.updateDeleteFlagsFor(vol,  subsurfacesOnDisk);
    }

    @Override
    public void updateRunInsightWorkflowVariables(Job dbjob, Volume dbvol) {
        hDao.updateRunInsightWorkflowVariables(dbjob, dbvol);
    }

    
    
    
}
