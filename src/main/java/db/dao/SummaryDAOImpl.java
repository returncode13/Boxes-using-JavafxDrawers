/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.Job;
import db.model.Sequence;
import db.model.Summary;
import db.model.Workspace;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SummaryDAOImpl implements  SummaryDAO{

    @Override
    public void createSummary(Summary summary) {
        Session session = HibernateUtil.getSessionFactory().openSession();
         Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(summary);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public Summary getSummary(Long id) {
       Session session=HibernateUtil.getSessionFactory().openSession();
       try{
           Summary summary=(Summary) session.get(Summary.class,id);
           return summary;
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           session.close();
       }
       return null;
    }

    @Override
    public void deleteSummary(Long id) {
       Session session=HibernateUtil.getSessionFactory().openSession();
       Transaction transaction=null;
       
       try{
            transaction=session.beginTransaction();
            Summary summary=(Summary) session.get(Summary.class, id);
            session.delete(summary);
            transaction.commit();
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           session.close();
       }
       
    }

    @Override
    public void updateSummary(Long id, Summary newSummary) {
         Session session=HibernateUtil.getSessionFactory().openSession();
       Transaction transaction=null;
       
       try{
            transaction=session.beginTransaction();
            Summary summary=(Summary) session.get(Summary.class, id);
            summary.setInheritanceSummary(newSummary.getInheritanceSummary());
            summary.setInsightSummary(newSummary.getInsightSummary());
            summary.setQcSummary(newSummary.getQcSummary());
            summary.setTimeSummary(newSummary.getTimeSummary());
            summary.setTraceSummary(newSummary.getTraceSummary());
            summary.setJob(newSummary.getJob());
            summary.setSequence(newSummary.getSequence());
            session.update(summary);
            
            transaction.commit();
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           session.close();
       }
    }

    @Override
    public Summary getSummaryFor(Sequence sequence,Job job ) {
         Session session=HibernateUtil.getSessionFactory().openSession();
         Transaction transaction=null;
         List<Summary> result=null;
         
         try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Summary.class);
            criteria.add(Restrictions.eq("sequence", sequence));
            criteria.add(Restrictions.eq("job", job));
            result=criteria.list();
            transaction.commit();
         }catch(Exception e){
             e.printStackTrace();
         }finally{
             session.close();
         }
         if(result.size()>1){
             return null;
         }else if(result.size()==0){
             return null;
         }else{
             return result.get(0);
         }
         
         
         
    }

    @Override
    public List<Long> getDepthsInSummary(Workspace W) {
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Long> depths=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Summary.class);
            criteria.add(Restrictions.eq("workspace", W));
            ProjectionList pList=Projections.projectionList();
            pList.add(Projections.property("depth"));
            criteria.setProjection(Projections.distinct(pList));
            depths=criteria.list();
            transaction.commit();
            System.out.println("db.dao.SummaryDAOImpl.getDepthsInSummary(): returning "+depths.size()+" depths");
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return depths;
    }

    @Override
    public List<Summary> getSummariesForJobSeq(Job job,Sequence seq,Workspace W) {
        Session session=HibernateUtil.getSessionFactory().openSession();
         Transaction transaction=null;
         List<Summary> result=null;
         
         try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Summary.class);
            criteria.add(Restrictions.eq("workspace", W));
            criteria.add(Restrictions.eq("sequence", seq));
            criteria.add(Restrictions.eq("job", job));
            result=criteria.list();
            transaction.commit();
         }catch(Exception e){
             e.printStackTrace();
         }finally{
             session.close();
         }
         return result;
    }
    
}
