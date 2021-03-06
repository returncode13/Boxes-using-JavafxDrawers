/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Dot;
import db.model.Doubt;
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
public interface DoubtService {
    public void createDoubt(Doubt ds);
    public void updateDoubt(Long dsid,Doubt newds);
    public Doubt getDoubt(Long dsid);
    public void deleteDoubt(Long dsid);
    
    /* public List<Doubt> getDoubtListForJobInSession(SessionDetails sd);
    public List<Doubt> getDoubtListForJobInSession(SessionDetails sd,DoubtType dt);
    
    public List<Doubt> getDoubtListForJobInSession(SessionDetails parentsd,Long childsdId, DoubtType dt, Headers hd);
    public List<Doubt> getDoubtListForJobInSession(Headers hd);*/

    // public List<Doubt> getDoubtsForLink(Link  link);    
    public Doubt getDoubtFor(Subsurface sub,Job job,Dot dot,DoubtType doubtType) ;
    public Doubt getDoubtFor(Subsurface sub,Job job,Doubt cause,DoubtType doubtType) ;
    public List<Doubt> getDoubtFor(Subsurface sub,Job job,DoubtType doubtType) ;
    public List<Doubt> getDoubtFor(Subsurface s, Job jchild, Dot dot);
    public List<Doubt> getDoubtFor(Sequence seq, Job job);
    public List<Doubt> getDoubtFor(Sequence seq, Job job,DoubtType doubtType);
    public Doubt getCauseOfInheritedDoubtForType(Subsurface sub, Job job,DoubtType doubtType);             //return the doubt of type doubttype which resulted in an inherited doubt in job
    public List<Doubt> getDoubtFor(Subsurface sub, Job job);
    public List<Doubt> getAllDoubtsExceptInheritanceFor(Workspace w);

    public void createBulkDoubts(List<Doubt> causes);

    public void updateBulkDoubts(List<Doubt> doubtsToBeUpdated);

    public void deleteBulkDoubts(List<Long> doubtsToBeDeleted);
    public List<Doubt> getAllDoubtsJobsAndSubsurfacesFor(Workspace W,DoubtType type);

    public List<Doubt> getInheritedDoubtsForCause(Doubt doubt);

    public void deleteAllInheritedDoubts(Workspace dbWorkspace);

    public void deleteAllDoubtsRelatedTo(Job job);                    //delete all causes (l.child=job or l.parent=job) and all inherited doubts from these causes

    public void deleteAllDoubtsRelatedTo(Dot dbDot);

    public void deleteAllDoubtsRelatedTo(Link link);                // used in the cases where the doubt exists on the parent and not the child. in such cases the dot belonges to the link originating from the parent , but the dot belongs to the link that ends on the parent

    public List<Doubt> getDoubtsFor(Subsurface sub);

    public List<Doubt> getCausalDoubtsFor(Subsurface sub, Job job);

    public List<Doubt> getInheritedDoubtsOn(Subsurface sub, Job job);
    

    
}
