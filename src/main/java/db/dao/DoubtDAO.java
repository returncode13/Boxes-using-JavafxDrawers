/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Dot;
import db.model.Doubt;
import db.model.DoubtType;
import db.model.Header;
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
public interface DoubtDAO {
    public void createDoubt(Doubt ds);
    public void updateDoubt(Long dsid,Doubt newds);
    public Doubt getDoubt(Long dsid);
    public void deleteDoubt(Long dsid);
    
    /*public List<Doubt> getDoubtListForJobInSession(Job sd);
    
    public List<Doubt> getDoubtListForJobInSession(Job sd, DoubtType dt);
    
    public List<Doubt> getDoubtListForJobInSession(Job parentsd, Long childsdId,DoubtType dt, Header hd);
    public List<Doubt> getDoubtListForJobInSession(Header hd);*/
    
    
   // public List<Doubt> getDoubtsForLink(Link  link);
    
    public Doubt getDoubtFor(Subsurface sub,Job job,Dot dot,DoubtType doubtType) ;
    public Doubt getDoubtFor(Subsurface sub,Job job,Doubt cause,DoubtType doubtType) ;
    public Doubt getDoubtFor(Subsurface sub,Job job,DoubtType doubtType) ;
    
    public List<Doubt> getDoubtFor(Subsurface sub, Job job, Dot dot);
    public List<Doubt> getDoubtFor(Sequence seq, Job job);
    public List<Doubt> getDoubtFor(Sequence seq, Job job,DoubtType doubtType);
    public List<Doubt> getInheritedDoubtFor(Subsurface sub, Job job);
    public List<Doubt> getDoubtFor(Subsurface sub, Job job);
    public List<Doubt> getAllDoubtsFor(Workspace w);

    public void createBulkDoubts(List<Doubt> doubts);

    public void updateBulkDoubts(List<Doubt> doubtsToBeUpdated);

    public void deleteBulkDoubts(List<Long> doubtsToBeDeleted);
    public List<Doubt> getAllDoubtsJobsAndSubsurfacesFor(Workspace W,DoubtType type);
}
