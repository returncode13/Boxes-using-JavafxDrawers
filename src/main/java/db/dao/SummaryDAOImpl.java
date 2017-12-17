/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.Summary;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
    
}
