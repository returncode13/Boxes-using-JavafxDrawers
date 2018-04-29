/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.Dot;
import db.model.Job;
import db.model.Link;
import db.model.Subsurface;
import db.model.Workspace;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
        System.out.println("db.dao.LinkDAOImpl.createLink()");
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
        System.out.println("db.dao.LinkDAOImpl.updateLink()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
             Link l= (Link) session.get(Link.class, id);
             l.setChild(newLink.getChild());
             l.setParent(newLink.getParent());
             l.setDot(newLink.getDot());
         
             
            session.update(l);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }
    
    @Override
    public void clearLinksforJob(Job job,Dot dot) {
        System.out.println("db.dao.LinkDAOImpl.clearLinksforJob()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            
        Criteria criteria=session.createCriteria(Link.class);
        Criterion rest0=Restrictions.eq("dot",dot);
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

    @Override
    public List<Link> getLinkBetweenParentAndChild(Job parent, Job child, Dot dot) {
        System.out.println("db.dao.LinkDAOImpl.getLinkBetweenParentAndChild()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction= null;
         List result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria = session.createCriteria(Link.class);
            criteria.add(Restrictions.eq("parent", parent));
            criteria.add(Restrictions.eq("child", child));
            criteria.add(Restrictions.eq("dot", dot));
           result=criteria.list();
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

    @Override
    public List<Link> getSummaryLinksForSubsurfaceInWorkspace(Workspace W, Subsurface sub) {
        System.out.println("db.dao.LinkDAOImpl.getSummaryLinksForSubsurfaceInWorkspace()");
         Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction= null;
         List<Link> result=null;
         String hql="SELECT l from Link l INNER JOIN l.parent lp INNER JOIN lp.subsurfaceJobs lpsjs"
                 + "                      INNER JOIN l.child  lc INNER JOIN lc.subsurfaceJobs lcsjs"
                 + "                      INNER JOIN l.dot d"
                 + "                      WHERE lpsjs.pk.subsurface=lcsjs.pk.subsurface "                                                       // all child and parent jobs who contain the same sub
                 + "                                     AND"
                 + "                            ( lpsjs.updateTime > lpsjs.summaryTime OR lcsjs.updateTime > lcsjs.summaryTime)"                //job-sub combinations where update > summary  
                 + "                                     AND"
                 + "                            d.workspace =:wrkq"                                                                             //current Workspace
                 + "                                     AND"
                 + "                            lpsjs.pk.subsurface =:subq";                                                                    //for current sub
         
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("wrkq", W);
            query.setParameter("subq", sub);
            
            result=query.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

    @Override
    public List<Object[]> getSubsurfaceAndLinksForSummary(Workspace W) {
        System.out.println("db.dao.LinkDAOImpl.getSubsurfaceAndLinksForSummary()");
         Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction= null;
         List<Object[]> result=null;
         String hql="SELECT l,lpsjs,lcsjs from Link l INNER JOIN l.parent lp INNER JOIN lp.subsurfaceJobs lpsjs"
                 + "                      INNER JOIN l.child  lc INNER JOIN lc.subsurfaceJobs lcsjs"
                 + "                      INNER JOIN l.dot d"
                 + "                      WHERE lpsjs.pk.subsurface=lcsjs.pk.subsurface "                                                       // all child and parent jobs who contain the same sub
                 + "                                     AND"
                 + "                            ( lpsjs.updateTime > lpsjs.summaryTime OR lcsjs.updateTime > lcsjs.summaryTime)"                //job-sub combinations where update > summary  
                 + "                                     AND"
                 + "                            d.workspace =:wrkq" ;                                                                            //current Workspace
                 /*+ "                                     AND"
                 + "                            lpsjs.pk.subsurface =:subq";                                                                    //for current sub*/
         
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("wrkq", W);
            //query.setParameter("subq", sub);
            
            result=query.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

    @Override
    public List<Link> getLinksForDot(Dot dbDot) {
        System.out.println("db.dao.LinkDAOImpl.getLinksForDot()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Link> result=null;
        String hql="from Link l where l.dot = :d";
         try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("d", dbDot);
            //query.setParameter("subq", sub);
            
            result=query.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

    @Override
    public List<Link> getDotJobListForWorkspace(Workspace dbWorkspace) {
        System.out.println("db.dao.LinkDAOImpl.getLinksForDot()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Link> result=null;
        String hql="SELECT l from Link l INNER JOIN l.child j where j.workspace =:w ";
         try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("w", dbWorkspace);
            //query.setParameter("subq", sub);
            
            result=query.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

    @Override
    public void deleteLinksForJob(Job job) {
        System.out.println("db.dao.LinkDAOImpl.deleteLinksForJob()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        
        String hql="DELETE FROM Link l WHERE l.parent =:j OR l.child =:j";
         try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("j", job);
            //query.setParameter("subq", sub);
            
            int result=query.executeUpdate();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public List<Dot> getDotsForJob(Job job) {
         System.out.println("db.dao.LinkDAOImpl.getLinksForDot()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Dot> result=null;
        String hql="SELECT distinct l.dot from Link l  WHERE l.parent =:j OR l.child =:j";
         try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            
            query.setParameter("j", job);
            //query.setParameter("subq", sub);
            
            result=query.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

    @Override
    public List<Link> getParentLinksFor(Job job) {
        System.out.println("db.dao.LinkDAOImpl.getParentLinksFor()");
      
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Link> result=new ArrayList<>();
        String hql="SELECT l from Link l  WHERE l.parent =:j";
         try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            
            query.setParameter("j", job);
            //query.setParameter("subq", sub);
            
            result=query.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

    @Override
    public List<Link> getChildLinksForJob(Job job) {
        System.out.println("db.dao.LinkDAOImpl.getChildLinksFor()");
      
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Link> result=new ArrayList<>();
        String hql="SELECT l from Link l  WHERE l.child =:j";
         try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            
            query.setParameter("j", job);
            //query.setParameter("subq", sub);
            
            result=query.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

   
}
