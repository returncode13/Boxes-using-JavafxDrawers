/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.LinkDAO;
import db.dao.LinkDAOImpl;
import db.model.Job;
import db.model.Link;

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
    public void clearLinksforJob(Job job) {
        linkDAO.clearLinksforJob(job);
    }
    
}
