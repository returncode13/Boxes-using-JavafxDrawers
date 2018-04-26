/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Dot;
import db.model.Job;
import db.model.Link;
import db.model.Subsurface;
import db.model.Workspace;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface LinkService {
    public void createLink(Link l);
    public Link getLink(Long id);
    public void deleteLink(Long id);
    public void updateLink(Long id,Link newLink);
    public void clearLinksforJob(Job job,Dot dot);      //clear links where either child=job or parent=job
 //   public Set<Link> getLinksContainingSubsurface(Subsurface s,Workspace w,Map<Job,List<Subsurface>> mapForSummary); //return links where s belongs to both parent.getSub() AND child.getSub(); in workspace w
    public List<Link> getLinkBetweenParentAndChild(Job parent,Job Child,Dot dot);
    public List<Link> getSummaryLinksForSubsurfaceInWorkspace(Workspace W,Subsurface sub);
    public List<Object[]> getSubsurfaceAndLinksForSummary(Workspace W);

    public List<Link> getLinksForDot(Dot dbDot);

    public List<Link> getDotJobListForWorkspace(Workspace dbWorkspace);

    public void deleteLinksForJob(Job dbjob);

    public List<Dot> getDotsForJob(Job dbjob);
}
