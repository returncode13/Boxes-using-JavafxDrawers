/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.Descendant;
import db.model.Job;
import db.model.Subsurface;
import db.model.Workspace;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
        System.out.println("db.dao.DescendantDAOImpl.getDescendantsFor()");
        Transaction transaction=null;
        List<Descendant> results=null;
        String hql="from Descendant d where d.job =:job";
        try{
            transaction=session.beginTransaction();
            /*Criteria criteria=session.createCriteria(Descendant.class);
            criteria.add(Restrictions.eq("job", job));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            results=criteria.list();*/
            Query query=session.createQuery(hql);
            query.setParameter("job", job);
            results=query.list();
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
        System.out.println("db.dao.DescendantDAOImpl.getDescendantFor()");
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
        }finally{
            sess.close();
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

    @Override
    public List<Descendant> getDescendantsForJobContainingSubsurface(Job job, Subsurface sub) {
         Session session = HibernateUtil.getSessionFactory().openSession();
         System.out.println("db.dao.DescendantDAOImpl.getDescendantsForJobContainingSubsurface()");
        List<Descendant> result=null;
        Transaction transaction=null;
        //String hql="from Descendant as d INNER JOIN Job as j INNER JOIN SubsurfaceJob as sj WHERE  sj.job_id =:jobAsked AND sj.id =:subsurfaceAsked";
        String hql="select d from Descendant  d INNER JOIN d.job  dj INNER JOIN dj.subsurfaceJobs  djsj WHERE  djsj.pk.job =:jobAsked AND djsj.pk.subsurface =:subsurfaceAsked";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("jobAsked", job);
            query.setParameter("subsurfaceAsked", sub);
            result=query.list();
            transaction.commit();
            System.out.println("db.dao.DescendantDAOImpl.getDescendantsForJobContainingSubsurface(): returning result of size "+result.size());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    } 

    @Override
    public List<Object[]> getDescendantsSubsurfaceJobsForSummary(Workspace W) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> result=null;
        Transaction transaction=null;
          System.out.println("db.dao.DescendantDAOImpl.getDescendantsSubsurfaceJobsForSummary()");
        
        String hql="select d,ddsj,dj from Descendant  d INNER JOIN d.descendant dd INNER JOIN dd.subsurfaceJobs ddsj"
                     + "                           INNER JOIN d.job dj      INNER JOIN dj.subsurfaceJobs djss"
                
                     + "                           WHERE ddsj.pk.subsurface=djss.pk.subsurface"
                     + "                                     AND"
                     + "                                 dj.workspace =:w";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("w", W);
            
            result=query.list();
            transaction.commit();
           
            System.out.println("db.dao.DescendantDAOImpl.getDescendantsSubsurfaceJobsForSummary(): returning "+result.size());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
        
    }

    @Override
    public void removeAllDescendantEntriesFor(Workspace workspace) {
          Session session = HibernateUtil.getSessionFactory().openSession();
        
        Transaction transaction=null;
        System.out.println("db.dao.DescendantDAOImpl.removeAllDescendantEntriesFor()");
       
        String hqlSelectIds="Select D.id from Descendant D INNER JOIN D.job dj WHERE dj.workspace =:w";
        String hqlDelete="DELETE From Descendant D where D.id in (:ids)";
        List<Long> idsToDelete=new ArrayList<>();
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hqlSelectIds);
            query.setParameter("w", workspace);
            idsToDelete=query.list();
            
            if(!idsToDelete.isEmpty()){
                Query delQuery= session.createQuery(hqlDelete);
                delQuery.setParameterList("ids", idsToDelete);
                int del=delQuery.executeUpdate();
                System.out.println("db.dao.DescendantDAOImpl.removeAllDescendantEntriesFor(): deleted "+idsToDelete.size()+" descendants");
                for(Long id:idsToDelete){
                    System.out.println("db.dao.DescendantDAOImpl.removeAllDescendantEntriesFor(): deleted: "+id);
                }
            }
            
            transaction.commit();
           
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
    
}
