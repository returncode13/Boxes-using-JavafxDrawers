/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.Job;
import db.model.Link;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class LinkDAOImpl implements LinkDAO{

    @Override
    public void createLink(Link l) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(l);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Link getLink(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Link l= (Link) session.get(Link.class, id);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void deleteLink(Long id) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
             Link l= (Link) session.get(Link.class, id);
            session.delete(l);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateLink(Long id, Link newLink) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
             Link l= (Link) session.get(Link.class, id);
             l.setChild(newLink.getChild());
             l.setParent(newLink.getParent());
             l.setDot(newLink.getDot());
             l.setDoubts(newLink.getDoubts());
             
            session.update(l);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }
    
    @Override
    public void clearLinksforJob(Job job) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            
        Criteria criteria=session.createCriteria(Link.class);
        Criterion rest1=Restrictions.eq("parent", job);
        Criterion rest2=Restrictions.eq("child", job);
        criteria.add(Restrictions.or(rest1,rest2));
        List results=criteria.list();
     
        
            if(results.size()>0){
             
         Transaction transaction=session.beginTransaction();
            for (Iterator iterator = results.iterator(); iterator.hasNext();) {
               
                    Link next = (Link) iterator.next();
                     System.out.println("db.dao.LinkDAOImpl.clearLinksforJob(): deleting links with id: "+next.getId()+ " with parent: "+next.getParent().getNameJobStep()+" and child: "+next.getChild().getNameJobStep());
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
