/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Doubt;
import db.model.Doubt;
import db.model.DoubtType;

import db.model.Link;

import java.util.List;

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

     public List<Doubt> getDoubtsForLink(Link  link);    
}
