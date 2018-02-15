/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.DoubtStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DoubtStatusDAOImpl implements DoubtStatusDAO{

    @Override
    public void createDoubtStatus(DoubtStatus ds) {
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(ds);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public DoubtStatus getDoubtStatus(Long id) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            DoubtStatus l= (DoubtStatus) session.get(DoubtStatus.class, id);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void deleteDoubtStatus(Long id) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            DoubtStatus l= (DoubtStatus) session.get(DoubtStatus.class, id);
            session.delete(l);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public void updateDoubtStatus(Long id, DoubtStatus newds) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
             DoubtStatus l= (DoubtStatus) session.get(DoubtStatus.class, id);
             l.setReason(newds.getReason());
             l.setDoubt(newds.getDoubt());
             l.setStatus(newds.getStatus());
             l.setTimeStamp(newds.getTimeStamp());
             l.setUser(newds.getUser());
             
             
            session.update(l);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }
    
}
