/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.TheaderDAO;
import db.dao.TheaderDAOImpl;
import db.model.Job;
import db.model.Theader;
import db.model.Volume;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class TheaderServiceImpl implements TheaderService{
    
    TheaderDAO thDAO=new TheaderDAOImpl();
    
    @Override
    public void createTheader(Theader t) {
        thDAO.createTheader(t);
    }

    @Override
    public void createBulkTheader(List<Theader> list) {
        thDAO.createBulkTheader(list);
    }

    @Override
    public Theader getTheader(Long id) {
        return thDAO.getTheader(id);
    }

    @Override
    public void updateTheader(Theader n) {
         thDAO.updateTheader(n);
    }

    @Override
    public void deleteTheader(Theader t) {
        thDAO.deleteTheader(t);
    }

    @Override
    public List<Theader> getTheadersFor(Job job) {
        return thDAO.getTheadersFor(job);
    }

    @Override
    public String getLatestTimeStampFor(Volume vol) {
        return thDAO.getLatestTimeStampFor(vol);
    }

    @Override
    public void deleteTheadersFor(Volume vol) {
        thDAO.deleteTheadersFor(vol);
    }
    
}
