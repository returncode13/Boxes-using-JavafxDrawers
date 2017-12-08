/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import db.model.Ancestor;
import db.model.Job;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class AncestorDAOImpl implements AncestorDAO{

    @Override
    public void addAncestor(Ancestor anc) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.save(anc);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public Ancestor getAncestor(Long aid) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Ancestor anc= (Ancestor) session.get(Ancestor.class, aid);
            return anc;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateAncestor(Long aid, Ancestor newAncestor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Ancestor anc= (Ancestor) session.get(Ancestor.class, aid);
            anc.setAncestor(newAncestor.getAncestor());
            anc.setJob(newAncestor.getJob());
            session.update(anc);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void deleteAncestor(Long aid) {
          Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Ancestor anc= (Ancestor) session.get(Ancestor.class, aid);
            session.delete(anc);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Ancestor getAncestorFor(Job fkid, Long ancestor) {
        Session sess = HibernateUtil.getSessionFactory().openSession();
        List<Ancestor> result=null;
        Transaction transaction=null;
        try{
            transaction=sess.beginTransaction();
            Criteria criteria= sess.createCriteria(Ancestor.class);
            criteria.add(Restrictions.eq("job", fkid));
            criteria.add(Restrictions.eq("ancestor",ancestor));
            result=criteria.list();
            transaction.commit();
            if(result.size()==1) return result.get(0);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return null;
    }

    @Override
    public void clearTableForJob(Job dbjob) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            
        Criteria criteria=session.createCriteria(Ancestor.class);
        criteria.add(Restrictions.eq("job", dbjob));
        List results=criteria.list();
     
        
            if(results.size()>0){
             
         Transaction transaction=session.beginTransaction();
            for (Iterator iterator = results.iterator(); iterator.hasNext();) {
                    Ancestor next = (Ancestor) iterator.next();
                    session.delete(next);
                    
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