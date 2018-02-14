/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.Descendant;
import db.model.Job;
import java.util.Iterator;

import java.util.List;
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
public class DescendantDAOImpl implements DescendantDAO {

    @Override
    public void addDescendant(Descendant d) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.save(d);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Descendant getDescendant(Long did) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        Descendant anc=null;
        try{
            transaction=session.beginTransaction();
           anc = (Descendant) session.get(Descendant.class, did);
            System.out.println("retrieved "+anc.getId());
           transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return anc;
    }

    @Override
    public void updateDescendant(Long did, Descendant newD) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Descendant desc= (Descendant) session.get(Descendant.class, did);
            desc.setDescendant(newD.getDescendant());
            desc.setJob(newD.getJob());
            session.update(desc);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void deleteDescendant(Long did) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
             Descendant anc= (Descendant) session.get(Descendant.class, did);
            session.delete(anc);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Descendant getDescendantFor(Job fkid, Long descendant) {
       Session session = HibernateUtil.getSessionFactory().openSession();
        List<Descendant> results=null;
        try{
            Transaction transaction=session.beginTransaction();
        Criteria criteria=session.createCriteria(Descendant.class);
        criteria.add(Restrictions.eq("job", fkid));
        criteria.add(Restrictions.eq("descendant", descendant));
        results=criteria.list();
     
        if(results.size()>0)return results.get(0);
         
            
         transaction.commit();
         
         
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

     @Override
    public void clearTableForJob(Job dbjob) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            
        Criteria criteria=session.createCriteria(Descendant.class);
        criteria.add(Restrictions.eq("job", dbjob));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List results=criteria.list();
     
        
            if(results.size()>0){
             
         Transaction transaction=session.beginTransaction();
            for (Iterator iterator = results.iterator(); iterator.hasNext();) {
                    Descendant next = (Descendant) iterator.next();
                    System.out.println("db.dao.DescendantDAOImpl.clearTableForJob():  deleting  id: "+next.getId()+" for job: "+next.getJob().getNameJobStep());
                    session.delete(next);
                    
                }
        transaction.commit();
                System.out.println("db.dao.DescendantDAOImpl.clearTableForJob(): finished transaction..deleted all entries for descendants");
        
        }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    
    }

    @Override
    public List<Descendant> getDescendantsFor(Job job) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List results=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Descendant.class);
            criteria.add(Restrictions.eq("job", job));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            results=criteria.list();
            transaction.commit();
            System.out.println("db.dao.DescendantDAOImpl.getDescendantsFor(): for job: "+job.getNameJobStep()+" size of descendants returned: "+results.size());
        
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return results;
    }

    @Override
    public Descendant getDescendantFor(Job job, Job descendant) {
        Session sess = HibernateUtil.getSessionFactory().openSession();
        List<Descendant> result=null;
        Transaction transaction=null;
        try{
            transaction=sess.beginTransaction();
            Criteria criteria= sess.createCriteria(Descendant.class);
            criteria.add(Restrictions.eq("job", job));
            criteria.add(Restrictions.eq("descendant",descendant));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            result=criteria.list();
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        if(result.size()>1){
           try {
               throw new Exception("More than one ancestor entry for job: "+job.getNameJobStep()+"("+job.getId()+")"+" ancestor "+descendant.getNameJobStep()+" ("+descendant.getId()+")");
           } catch (Exception ex) {
               Logger.getLogger(AncestorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
           }
        }if(result.size()==1){
            return result.get(0);
        }
        System.out.println("db.dao.AncestorDAOImpl.getAncestorFor(): No ancestors found for "+job.getNameJobStep()+"("+job.getId()+")"+" ancestor "+descendant.getNameJobStep()+" ("+descendant.getId()+")");
        return null;
    }
    
}
