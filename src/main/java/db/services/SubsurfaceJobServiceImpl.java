/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.SubsurfaceJobDAO;
import db.dao.SubsurfaceJobDAOImpl;
import db.model.Job;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.SubsurfaceJob;
import db.model.SubsurfaceJobId;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SubsurfaceJobServiceImpl implements SubsurfaceJobService{

    private SubsurfaceJobDAO subsurfaceJobDAO=new SubsurfaceJobDAOImpl();

    @Override
    public void createSubsurfaceJob(SubsurfaceJob subsurfaceJob) {
        subsurfaceJobDAO.createSubsurfaceJob(subsurfaceJob);
    }

    @Override
    public SubsurfaceJob getSubsurface(SubsurfaceJobId id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateSubsurfaceJob(SubsurfaceJob nsj) {
        subsurfaceJobDAO.updateSubsurfaceJob(nsj);
    }

    @Override
    public void deleteSubsurfaceJob(SubsurfaceJobId id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SubsurfaceJob getSubsurfaceJobFor(Job job, Subsurface subsurface) {
        return subsurfaceJobDAO.getSubsurfaceJobFor(job, subsurface);
    }
    
   
    
}
