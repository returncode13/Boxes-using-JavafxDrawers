/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Doubt;
import db.model.DoubtType;
import db.model.Header;
import db.model.Job;
import db.model.Link;

import java.util.List;

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
    
    
    public List<Doubt> getDoubtsForLink(Link  link);
    
    
}
