/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;


import db.model.Job;
import db.model.Theader;
import db.model.Volume;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface TheaderDAO {
    public void createTheader(Theader t);
    public void createBulkTheader(List<Theader> list); 
    public Theader getTheader(Long id);
    public void updateTheader(Theader n);
    public void deleteTheader(Theader t);

    public List<Theader> getTheadersFor(Job job);

    public String getLatestTimeStampFor(Volume vol);

    public void deleteTheadersFor(Volume vol);
}
