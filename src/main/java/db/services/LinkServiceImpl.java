/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.LinkDAO;
import db.dao.LinkDAOImpl;
import db.model.Dot;
import db.model.Job;
import db.model.Link;
import db.model.Subsurface;
import db.model.SubsurfaceJob;
import db.model.Workspace;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class LinkServiceImpl implements LinkService {
    

    LinkDAO linkDAO=new LinkDAOImpl();
    SubsurfaceJobService subsurfaceJobService=new SubsurfaceJobServiceImpl();
    
    
    @Override
    public void createLink(Link l) {
        linkDAO.createLink(l);
    }

    @Override
    public Link getLink(Long id) {
        return linkDAO.getLink(id);
    }

    @Override
    public void deleteLink(Long id) {
        linkDAO.deleteLink(id);
    }

    @Override
    public void updateLink(Long id, Link newLink) {
        linkDAO.updateLink(id, newLink);
    }

    @Override
    public void clearLinksforJob(Job job,Dot dot) {
        linkDAO.clearLinksforJob(job,dot);
    }

    /* @Override
    public Set<Link> getLinksContainingSubsurface(Subsurface s, Workspace w,Map<Job,List<Subsurface>> mapForSummary) {
    //return links which whose parent and child contain Subsurface S
    Set<Dot> dotsInWorkspace=w.getDots();
    Set<Link> links=new HashSet<>();
    
    
    
    
    for(Dot dot:dotsInWorkspace){
    Set<Link> linksinDot=dot.getLinks();
    for(Link l:linksinDot){
    Set<Subsurface> parentSubsurfaces=l.getParent().getSubsurfaces();
    //Set<SubsurfaceJob> parentSubsurfaces=l.getParent().getSubsurfaceJobs();
    if(!parentSubsurfaces.contains(s)){
    //if(!parentSubsurfaces.){
    continue;
    }else{
    if(l.getChild().getSubsurfaces().contains(s)){
    
    if((mapForSummary.containsKey(l.getChild()) && mapForSummary.get(l.getChild()).contains(s) )||( mapForSummary.containsKey(l.getParent()) && mapForSummary.get(l.getParent()).contains(s) )){
    System.out.println("db.services.LinkServiceImpl.getLinksContainingSubsurface(): Will summarize the link "+l.getParent().getNameJobStep()+" ----> "+l.getChild().getNameJobStep()+" for subsurface : "+s.getSubsurface());
    links.add(l);
    }
    }
    // System.out.println("db.dao.LinkServiceImpl.getLinksContainingSubsurface()");
    }
    }
    }
    
    return links;
    
    }*/

    @Override
    public List<Link> getLinkBetweenParentAndChild(Job parent, Job Child, Dot dot) {
        return linkDAO.getLinkBetweenParentAndChild(parent, Child, dot);
    }

    @Override
    public List<Link> getSummaryLinksForSubsurfaceInWorkspace(Workspace W, Subsurface sub) {
        return linkDAO.getSummaryLinksForSubsurfaceInWorkspace(W, sub);
    }

    @Override
    public List<Object[]> getSubsurfaceAndLinksForSummary(Workspace W) {
        return linkDAO.getSubsurfaceAndLinksForSummary(W);
    }

    @Override
    public List<Link> getLinksForDot(Dot dbDot) {
        return linkDAO.getLinksForDot(dbDot);
    }

    @Override
    public List<Link> getDotJobListForWorkspace(Workspace dbWorkspace) {
        return linkDAO.getDotJobListForWorkspace(dbWorkspace);
    }

    @Override
    public void deleteLinksForJob(Job job) {
       linkDAO.deleteLinksForJob(job);
    }

    @Override
    public List<Dot> getDotsForJob(Job job) {
       return linkDAO.getDotsForJob(job);
    }

    @Override
    public List<Link> getParentLinksFor(Job job) {
        return linkDAO.getParentLinksFor(job);
    }

    

   

    
}
