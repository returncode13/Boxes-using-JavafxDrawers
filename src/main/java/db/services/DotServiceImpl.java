/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.DotDAO;
import db.dao.DotDAOImpl;
import db.model.Dot;
import db.model.Workspace;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DotServiceImpl implements DotService{
    
    DotDAO dotDao=new DotDAOImpl();
    
    @Override
    public void createDot(Dot dot) {
        dotDao.createDot(dot);
    }

    @Override
    public Dot getDot(Long id) {
        return dotDao.getDot(id);
    }

    @Override
    public void updateDot(Long id, Dot newDot) {
        dotDao.updateDot(id, newDot);
    }
    
    @Override
    public void deleteDot(Long id) {
        dotDao.deleteDot(id);
    }

    @Override
    public void clearUnattachedDots(Workspace ws) {
        dotDao.clearUnattachedDots(ws);
    }
    
}
