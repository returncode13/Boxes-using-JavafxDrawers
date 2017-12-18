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
import java.util.Set;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class LinkServiceImpl implements LinkService {

    LinkDAO linkDAO=new LinkDAOImpl();
           
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

    @Override
    public Set<Link> getLinksContainingSubsurface(Subsurface s, Workspace w) {
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
                        links.add(l);
                    }
                    System.out.println("db.dao.LinkServiceImpl.getLinksContainingSubsurface()");
                }
            }
        }
        
        return links;
        
    }
    

    
}
