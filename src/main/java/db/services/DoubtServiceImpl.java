/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.DoubtDAO;
import db.dao.DoubtDAOImpl;
import db.model.Doubt;

import db.model.DoubtType;

import db.model.Link;

import java.util.List;

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

    @Override
    public List<Doubt> getDoubtsForLink(Link link) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
