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
import db.model.Subsurface;
import db.model.Workspace;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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
    public List<Ancestor> getAncestorFor(Job job) {
        Session sess = HibernateUtil.getSessionFactory().openSession();
        List<Ancestor> result=null;
        System.out.println("db.dao.AncestorDAOImpl.getAncestorFor()");
        Transaction transaction=null;
        String hql="from Ancestor a where a.job = :job";
        try{
            transaction=sess.beginTransaction();
            /*Criteria criteria= sess.createCriteria(Ancestor.class);
            criteria.add(Restrictions.eq("job", job));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            result=criteria.list();*/
            Query query=sess.createQuery(hql);
            query.setParameter("job", job);
            result=query.list();
                    
            transaction.commit();
            System.out.println("db.dao.AncestorDAOImpl.getAncestorFor(): returning ancestor list of size "+result.size()+" for job: "+job.getNameJobStep());
           
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sess.close();
        }
        
        
        return result;
    }

    @Override
    public void clearTableForJob(Job dbjob) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            
        Criteria criteria=session.createCriteria(Ancestor.class);
        criteria.add(Restrictions.eq("job", dbjob));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List results=criteria.list();
        Set<Ancestor> set=new LinkedHashSet<>(results);
        
            if(set.size()>0){
             
         Transaction transaction=session.beginTransaction();
            for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                    Ancestor next = (Ancestor) iterator.next();
                    System.out.println("db.dao.AncestorDAOImpl.clearTableForJob(): deleting ancestor table entry: "+next.getId()+ " for "+dbjob.getNameJobStep());
                    session.delete(next);
                    
                }
        transaction.commit();
                System.out.println("db.dao.AncestorDAOImpl.clearTableForJob(): transaction completed. deleted all ancestors");
        
        }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    
    }

    @Override
    public Ancestor getAncestorFor(Job job, Job ancestor) {
       Session sess = HibernateUtil.getSessionFactory().openSession();
        List<Ancestor> result=null;
        System.out.println("db.dao.AncestorDAOImpl.getAncestorFor()");
        Transaction transaction=null;
        try{
            transaction=sess.beginTransaction();
            Criteria criteria= sess.createCriteria(Ancestor.class);
            criteria.add(Restrictions.eq("job", job));
            criteria.add(Restrictions.eq("ancestor",ancestor));
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
               throw new Exception("More than one ancestor entry for job: "+job.getNameJobStep()+"("+job.getId()+")"+" ancestor "+ancestor.getNameJobStep()+" ("+ancestor.getId()+")");
           } catch (Exception ex) {
               Logger.getLogger(AncestorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
           }
        }if(result.size()==1){
            return result.get(0);
        }
        System.out.println("db.dao.AncestorDAOImpl.getAncestorFor(): No ancestors found for "+job.getNameJobStep()+"("+job.getId()+")"+" ancestor "+ancestor.getNameJobStep()+" ("+ancestor.getId()+")");
        return null;
      
    }
         
    
     @Override
    public List<Ancestor> getAncestorsForJobContainingSubsurface(Job job, Subsurface sub) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        List<Ancestor> result=null;
        Transaction transaction=null;
         System.out.println("db.dao.AncestorDAOImpl.getAncestorsForJobContainingSubsurface()");
        //String hql="from Descendant as d INNER JOIN Job as j INNER JOIN SubsurfaceJob as sj WHERE  sj.job_id =:jobAsked AND sj.id =:subsurfaceAsked";
        String hql="select d from Ancestor  d INNER JOIN d.job  dj INNER JOIN dj.subsurfaceJobs  djsj WHERE  djsj.pk.job =:jobAsked AND djsj.pk.subsurface =:subsurfaceAsked";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("jobAsked", job);
            query.setParameter("subsurfaceAsked", sub);
            result=query.list();
            transaction.commit();
            System.out.println("db.dao.AncestorDAOImpl.getAncestorsForJobContainingSubsurface(): returning "+result.size()+" ancestors for job "+job.getId()+" containing sub : "+sub.getId());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    } 
    
    
    /**
     * Return all ancestors that contain the same subsurface as the job
     */
     public List<Object[]> getAncestorsSubsurfaceJobsForSummary(Workspace W) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> result=null;
        Transaction transaction=null;
         System.out.println("db.dao.AncestorDAOImpl.getAncestorsForJobContainingSubsurface()");
        //String hql="from Descendant as d INNER JOIN Job as j INNER JOIN SubsurfaceJob as sj WHERE  sj.job_id =:jobAsked AND sj.id =:subsurfaceAsked";
        String hql="select a,aasj,aj from Ancestor  a INNER JOIN a.ancestor aa INNER JOIN aa.subsurfaceJobs aasj"
                     + "                           INNER JOIN a.job aj      INNER JOIN aj.subsurfaceJobs ajss"
                
                     + "                           WHERE aasj.pk.subsurface=ajss.pk.subsurface"
                     + "                                     AND"
                     + "                                 aj.workspace =:w";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("w", W);
            
            result=query.list();
            transaction.commit();
            System.out.println("db.dao.AncestorDAOImpl.getAncestorsForJobContainingSubsurface(): returning "+result.size());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    } 

    @Override
    public void removeAllAncestorEntriesFor(Workspace workspace) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        
        Transaction transaction=null;
        System.out.println("db.dao.AncestorDAOImpl.removeAllAncestorEntriesFor()");
        //String hql="from Descendant as d INNER JOIN Job as j INNER JOIN SubsurfaceJob as sj WHERE  sj.job_id =:jobAsked AND sj.id =:subsurfaceAsked";
        String hqlSelectIds="Select A.id from Ancestor A INNER JOIN A.job aj WHERE aj.workspace =:w";
        String hqlDelete="DELETE From Ancestor A where A.id in (:ids)";
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
                System.out.println("db.dao.AncestorDAOImpl.removeAllAncestorEntriesFor(): deleted "+idsToDelete.size()+" ancestors");
                for(Long id:idsToDelete){
                    System.out.println("db.dao.AncestorDAOImpl.removeAllAncestorEntriesFor(): deleted: "+id);
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