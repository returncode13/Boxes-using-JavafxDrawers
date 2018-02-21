/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.Dot;
import db.model.Workspace;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DotDAOImpl implements DotDAO{

    @Override
    public void createDot(Dot dot) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(dot);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Dot getDot(Long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Dot l= (Dot) session.get(Dot.class, id);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateDot(Long id, Dot newDot) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
             Dot l= (Dot) session.get(Dot.class, id);
             l.setStatus(newDot.getStatus());
             l.setLinks(newDot.getLinks());
             l.setVariableArguments(newDot.getVariableArguments());
             l.setFunction(newDot.getFunction());
             l.setTolerance(newDot.getTolerance());
             l.setError(newDot.getError());
             l.setDoubts(newDot.getDoubts());
             l.setWorkspace(newDot.getWorkspace());
             
            session.update(l);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public void deleteDot(Long id) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Dot l= (Dot) session.get(Dot.class, id);
            session.delete(l);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void clearUnattachedDots(Workspace ws) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            
        Criteria criteria=session.createCriteria(Dot.class);
        criteria.add(Restrictions.eq("workspace",ws));
        
        List results=criteria.list();
     
        
            if(results.size()>0){
             
         Transaction transaction=session.beginTransaction();
            for (Iterator iterator = results.iterator(); iterator.hasNext();) {
                    Dot dot = (Dot) iterator.next();
                    if(dot.getLinks()==null||dot.getLinks().isEmpty()){
                        session.delete(dot);
                    }
                    
                }
        transaction.commit();
        
        
        }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
    
}
