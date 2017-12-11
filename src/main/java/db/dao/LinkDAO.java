/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Job;
import db.model.Link;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface LinkDAO {
    public void createLink(Link l);
    public Link getLink(Long id);
    public void deleteLink(Long id);
    public void updateLink(Long id,Link newLink);
    public void clearLinksforJob(Job job);      //clear links where either child=job or parent=job
}
