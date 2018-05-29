/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.FheaderDAO;
import db.dao.FheaderDAOImpl;
import db.model.Fheader;
import db.model.Job;
import db.model.Volume;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class FheaderServiceImpl implements FheaderService {

    FheaderDAO fhDAO=new FheaderDAOImpl();
    
    @Override
    public void createHeader(Fheader h) {
        fhDAO.createHeader(h);
    }

    @Override
    public void createBulkHeaders(List<Fheader> headers) {
        fhDAO.createBulkHeaders(headers);
    }

    @Override
    public Fheader getHeader(Long hid) {
       return fhDAO.getHeader(hid);
    }

    @Override
    public void updateHeader(Long hid, Fheader newH) {
        fhDAO.updateHeader(hid, newH);
    }

    @Override
    public void deleteHeader(Long hid) {
        fhDAO.deleteHeader(hid);
    }

    @Override
    public List<Fheader> getHeadersFor(Job job) {
        return  fhDAO.getHeadersFor(job);
    }

    @Override
    public List<Fheader> getHeadersFor(Volume v) {
        return fhDAO.getHeadersFor(v);
    }

    @Override
    public void deleteHeadersFor(Volume v) {
        fhDAO.deleteHeadersFor(v);
    }

    @Override
    public void deleteHeadersFor(Job job) {
        fhDAO.deleteHeadersFor(job);
    }

    @Override
    public void updateDeleteFlagsFor(Volume dbvol, List<String> subsurfacesOnDisk) {
        fhDAO.updateDeleteFlagsFor(dbvol, subsurfacesOnDisk);
    }

    @Override
    public void checkForMultipleSubsurfacesInHeadersForJob(Job dbjob) {
        fhDAO.checkForMultipleSubsurfacesInHeadersForJob(dbjob);
    }

    @Override
    public String getLatestTimeStampFor(Volume dbvol) {
        return fhDAO.getLatestTimeStampFor(dbvol);
    }
    
}
