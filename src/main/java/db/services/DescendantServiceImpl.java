/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.DescendantDAO;
import db.dao.DescendantDAOImpl;

import app.connections.hibernate.HibernateUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import db.model.Descendant;
import db.model.Job;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class DescendantServiceImpl implements DescendantService{
    DescendantDAO descDao=new DescendantDAOImpl();
    
            

    @Override
    public void addDescendant(Descendant d) {
        descDao.addDescendant(d);
    }

    @Override
    public Descendant getDescendant(Long did) {
        return descDao.getDescendant(did);
    }

    @Override
    public void updateDescendant(Long did, Descendant newD) {
        descDao.updateDescendant(did, newD);
    }

    @Override
    public void deleteDescendant(Long did) {
        descDao.deleteDescendant(did);
    }
    
    @Override
    public void clearTableForJob(Job dbjob) {
        descDao.clearTableForJob(dbjob);
    }
    
    /* @Override
    public void makeDescendantTableFor(Job job, Set<Job> listOfDescendant) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    
    try{
    
    Criteria criteria=session.createCriteria(Descendant.class);
    criteria.add(Restrictions.eq("job", job));
    List results=criteria.list();
    
    
    if(results.size()>0){
    
    Transaction transaction=session.beginTransaction();
    for (Iterator iterator = results.iterator(); iterator.hasNext();) {
    Descendant next = (Descendant) iterator.next();
    session.delete(next);
    
    }
    transaction.commit();
    
    
    }
    
    Transaction transaction=session.beginTransaction();
    for (Iterator<Job> iterator = listOfDescendant.iterator(); iterator.hasNext();) {
    Job next = iterator.next();
    Descendant desc=new Descendant();
    desc.setJob(job);
    desc.setDescendant(next);
    
    }
    transaction.commit();
    
    
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    
    }*/
    

   

    
    
}
