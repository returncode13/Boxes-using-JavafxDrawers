/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.DoubtStatusDAO;
import db.dao.DoubtStatusDAOImpl;
import db.model.DoubtStatus;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DoubtStatusServiceImpl implements DoubtStatusService{
    
    DoubtStatusDAO dsDao=new DoubtStatusDAOImpl();
    
    
    @Override
    public void createDoubtStatus(DoubtStatus ds) {
        dsDao.createDoubtStatus(ds);
    }

    @Override
    public DoubtStatus getDoubtStatus(Long id) {
        return dsDao.getDoubtStatus(id);
    }

    @Override
    public void deleteDoubtStatus(Long id) {
        dsDao.deleteDoubtStatus(id);
    }

    @Override
    public void updateDoubtStatus(Long id, DoubtStatus newds) {
        dsDao.updateDoubtStatus(id, newds);
    }
    
}
