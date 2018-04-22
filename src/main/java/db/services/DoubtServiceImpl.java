/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.DoubtDAO;
import db.dao.DoubtDAOImpl;
import db.model.Dot;
import db.model.Doubt;

import db.model.DoubtType;
import db.model.Job;

import db.model.Link;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.Workspace;

import java.util.List;
import java.util.Set;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DoubtServiceImpl implements DoubtService{

    DoubtDAO dsDAO=new DoubtDAOImpl();
            
    
    @Override
    public void createDoubt(Doubt ds) {
        dsDAO.createDoubt(ds);
    }

    @Override
    public void updateDoubt(Long dsid, Doubt newds) {
        dsDAO.updateDoubt(dsid, newds);
    }

    @Override
    public Doubt getDoubt(Long dsid) {
        return dsDAO.getDoubt(dsid);
    }

    @Override
    public void deleteDoubt(Long dsid) {
        dsDAO.deleteDoubt(dsid);
    }

    /* @Override
    public List<Doubt> getDoubtListForJobInSession(SessionDetails sd) {
    return dsDAO.getDoubtListForJobInSession(sd);
    }
    
    @Override
    public List<Doubt> getDoubtListForJobInSession(SessionDetails sd, DoubtType dt) {
    return dsDAO.getDoubtListForJobInSession(sd,dt);
    }
    
    
    
    @Override
    public List<Doubt> getDoubtListForJobInSession(SessionDetails parentsd, Long childsdId, DoubtType dt, Headers hd) {
    return dsDAO.getDoubtListForJobInSession(parentsd, childsdId, dt, hd);
    }
    
    @Override
    public List<Doubt> getDoubtListForJobInSession(Headers hd) {
    return dsDAO.getDoubtListForJobInSession(hd);
    }*/

    /*  @Override
    public List<Doubt> getDoubtsForLink(Link link) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    @Override
    public Doubt getDoubtFor(Subsurface sub, Job job, Dot dot, DoubtType doubtType) {
        return dsDAO.getDoubtFor(sub, job, dot, doubtType);
    }

    @Override
    public List<Doubt> getDoubtFor(Subsurface sub, Job job, Dot dot) {
        return dsDAO.getDoubtFor(sub, job, dot);
    }

    @Override
    public List<Doubt> getDoubtFor(Sequence seq, Job job) {
        return dsDAO.getDoubtFor(seq, job);
    }

    @Override
    public List<Doubt> getDoubtFor(Sequence seq, Job job, DoubtType doubtType) {
        return dsDAO.getDoubtFor(seq, job,doubtType);
    }

    @Override
    public Doubt getDoubtFor(Subsurface sub, Job job, Doubt cause, DoubtType doubtType) {
        return dsDAO.getDoubtFor(sub, job, cause, doubtType);
    }

    @Override
    public Doubt getDoubtFor(Subsurface sub, Job job, DoubtType doubtType) {
        return dsDAO.getDoubtFor(sub, job, doubtType);
    }

    @Override
    public Doubt getCauseOfInheritedDoubtForType(Subsurface sub, Job job, DoubtType doubtType) {
        List<Doubt> inheritedDoubtsInSubJob=dsDAO.getInheritedDoubtFor(sub, job);
        for(Doubt inh:inheritedDoubtsInSubJob){
            Doubt cause=inh.getDoubtCause();
            if(cause.getDoubtType().equals(doubtType)){
                return cause;
            }
        }
        return null;
    }

    @Override
    public List<Doubt> getDoubtFor(Subsurface sub, Job job) {
        return dsDAO.getDoubtFor(sub, job);
    }

    @Override
    public List<Doubt> getAllDoubtsExceptInheritanceFor(Workspace w) {
        return dsDAO.getAllDoubtsExceptInheritanceFor(w);
    }

    @Override
    public void createBulkDoubts(List<Doubt> doubts) {
        dsDAO.createBulkDoubts(doubts);
    }

    @Override
    public void updateBulkDoubts(List<Doubt> doubtsToBeUpdated) {
        dsDAO.updateBulkDoubts(doubtsToBeUpdated);
     }

    @Override
    public void deleteBulkDoubts(List<Long> doubtsToBeDeleted) {
        dsDAO.deleteBulkDoubts(doubtsToBeDeleted);
    }

    @Override
    public List<Doubt> getAllDoubtsJobsAndSubsurfacesFor(Workspace W, DoubtType type) {
        return dsDAO.getAllDoubtsJobsAndSubsurfacesFor(W, type);
    }

    @Override
    public List<Doubt> getInheritedDoubtsForCause(Doubt cause) {
        return dsDAO.getInheritedDoubtsForCause(cause);
    }

    
    
}
