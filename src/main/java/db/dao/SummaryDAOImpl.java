/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import app.properties.AppProperties;
import db.model.Job;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.Summary;
import db.model.Workspace;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
    public void createBulkSummaries(List<Summary> summaries) {
        System.out.println("db.dao.SummaryDAOImpl.createBulkSummaries()");
        if(summaries.isEmpty()){
            return;
        }
        int batchsize=Math.min(summaries.size(), AppProperties.BULK_TRANSACTION_BATCH_SIZE);
         Session session = HibernateUtil.getSessionFactory().openSession();
         Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            for(int ii=0;ii<summaries.size();ii++){
                session.saveOrUpdate(summaries.get(ii));
                if(ii%batchsize ==0 ){
                    session.flush();
                    session.clear();
                    
                }
                
            }
            
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
            summary.setAll(false);
            summary.setFailedTimeDependency(newSummary.hasFailedTimeDependency());
            summary.setOverridenTimeFail(newSummary.hasOverridenTimeFail());
            summary.setInheritedTimeFail(newSummary.hasInheritedTimeFail());
            summary.setInheritedTimeOverride(newSummary.hasInheritedTimeOverride());
            summary.setWarningForTime(newSummary.hasWarningForTime());
            
            summary.setFailedTraceDependency(newSummary.hasFailedTraceDependency());
            summary.setOverridenTraceFail(newSummary.hasOverridenTraceFail());
            summary.setInheritedTraceFail(newSummary.hasInheritedTraceFail());
            summary.setInheritedTraceOverride(newSummary.hasInheritedTraceOverride());
            summary.setWarningForTrace(newSummary.hasWarningForTrace());
            
            
            summary.setFailedQcDependency(newSummary.hasFailedQcDependency());
            summary.setOverridenQcFail(newSummary.hasOverridenQcFail());
            summary.setInheritedQcFail(newSummary.hasInheritedQcFail());
            summary.setInheritedQcOverride(newSummary.hasInheritedQcOverride());
            summary.setWarningForQc(newSummary.hasWarningForQc());
            
           
            summary.setFailedInsightDependency(newSummary.hasFailedInsightDependency());
            summary.setOverridenInsightFail(newSummary.hasOverridenInsightFail());
            summary.setInheritedInsightFail(newSummary.hasInheritedInsightFail());
            summary.setInheritedInsightOverride(newSummary.hasInheritedInsightOverride());
            summary.setWarningForInsight(newSummary.hasWarningForInsight());
            
            summary.setFailedIoDependency(newSummary.hasFailedIoDependency());
            summary.setOverridenIoFail(newSummary.hasOverridenIoFail());
            summary.setInheritedIoFail(newSummary.hasInheritedIoFail());
            summary.setInheritedIoOverride(newSummary.hasInheritedIoOverride());
            summary.setWarningForIo(newSummary.hasWarningForIo());
            
             summary.setFailedWorkflowDependency(newSummary.hasFailedWorkflowDependency());
            summary.setOverridenWorkflowFail(newSummary.hasOverridenWorkflowFail());
            summary.setInheritedWorkflowFail(newSummary.hasInheritedWorkflowFail());
            summary.setInheritedWorkflowOverride(newSummary.hasInheritedWorkflowOverride());
            summary.setWarningForWorkflow(newSummary.hasWarningForWorkflow());
            
            
            summary.setJob(newSummary.getJob());
            summary.setSequence(newSummary.getSequence());
            summary.setSubsurface(newSummary.getSubsurface());
            session.update(summary);
            
            transaction.commit();
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           session.close();
       }
    }
    
     @Override
    public void udpateBulkSummaries(List<Summary> summariesToBeUpdated) {
         System.out.println("db.dao.SummaryDAOImpl.udpateBulkSummaries()");
        if(summariesToBeUpdated.isEmpty()) {
            return;
        }
          int batchsize=Math.min(summariesToBeUpdated.size(), AppProperties.BULK_TRANSACTION_BATCH_SIZE);
         Session session = HibernateUtil.getSessionFactory().openSession();
         Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            for(int ii=0;ii<summariesToBeUpdated.size();ii++){
                session.update(summariesToBeUpdated.get(ii));
                if(ii%batchsize ==0 ){
                    session.flush();
                    session.clear();
                    
                }
                
            }
            
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

    @Override
    public List<Summary> getSummariesForJobSeq(Job job, Sequence seq) {
        Session session=HibernateUtil.getSessionFactory().openSession();
         Transaction transaction=null;
         List<Summary> result=null;
         
         try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Summary.class);
            
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

    @Override
    public Summary getSummaryFor(Subsurface subsurface, Job job) {
         Session session=HibernateUtil.getSessionFactory().openSession();
         Transaction transaction=null;
         List<Summary> result=null;
         String hql="Select s from Summary s where s.subsurface =:s and s.job =:j";
         try{
            transaction=session.beginTransaction();
            /*Criteria criteria=session.createCriteria(Summary.class);
            criteria.add(Restrictions.eq("subsurface", subsurface));
            criteria.add(Restrictions.eq("job", job));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);*/
                
                Query query=session.createQuery(hql);
                query.setParameter("s", subsurface);
                query.setParameter("j", job);
                result=query.list();
           // result=criteria.list();
            
             System.out.println("db.dao.SummaryDAOImpl.getSummaryFor(): For job: "+job.getNameJobStep()+ " and subsurface: "+subsurface.getSubsurface()+" No of existing Summaries found : "+result.size());
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
    public List<Summary> getSummariesForJobSub(Job job, Subsurface sub, Workspace W) {
        Session session=HibernateUtil.getSessionFactory().openSession();
         Transaction transaction=null;
         List<Summary> result=null;
         
         try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Summary.class);
            criteria.add(Restrictions.eq("workspace", W));
            criteria.add(Restrictions.eq("subsurface", sub));
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

    @Override
    public List<Summary> getSummariesFor(Workspace W) {
        Session session=HibernateUtil.getSessionFactory().openSession();
         Transaction transaction=null;
         List<Summary> result=null;
         String hql="select s from Summary s INNER JOIN s.job sj WHERE sj.workspace =:w";
         try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("w", W);
            result=query.list();
            
             System.out.println("db.dao.SummaryDAOImpl.getSummariesFor(): returning summaries of size : "+result.size());
            
            transaction.commit();
         }catch(Exception e){
             e.printStackTrace();
         }finally{
             session.close();
         }
         return result;
    }

    @Override
    public void deleteAllSummaries(Workspace dbWorkspace) {
        Session session=HibernateUtil.getSessionFactory().openSession();
         Transaction transaction=null;
        
       //  String hqlSelect="select s.id from Summary s INNER JOIN s.job sj WHERE sj.workspace =:w";
         String hqlDelete="delete from Summary s where s.workspace =:w";
         try{
            transaction=session.beginTransaction();
           // Query query=session.createQuery(hqlSelect);
            //query.setParameter("w", dbWorkspace);
           // List<Long> ids=query.list();
            
            Query delQuery=session.createQuery(hqlDelete);
            //delQuery.setParameter("ids", ids);
            delQuery.setParameter("w", dbWorkspace);
            int delete=delQuery.executeUpdate();
            
            transaction.commit();
         }catch(Exception e){
             e.printStackTrace();
         }finally{
             session.close();
         }
         
    }

    @Override
    public void deleteAllSummariesForJob(Job job) {
         Session session=HibernateUtil.getSessionFactory().openSession();
         Transaction transaction=null;
        
       //  String hqlSelect="select s.id from Summary s INNER JOIN s.job sj WHERE sj.workspace =:w";
         String hqlDelete="delete from Summary s where s.job =:j";
         try{
            transaction=session.beginTransaction();
           // Query query=session.createQuery(hqlSelect);
            //query.setParameter("w", dbWorkspace);
           // List<Long> ids=query.list();
            
            Query delQuery=session.createQuery(hqlDelete);
            //delQuery.setParameter("ids", ids);
            delQuery.setParameter("j", job);
            int delete=delQuery.executeUpdate();
            
            transaction.commit();
         }catch(Exception e){
             e.printStackTrace();
         }finally{
             session.close();
         }
    }

    @Override
    public List<Summary> getSummariesFor(Subsurface sub) {
       Session session=HibernateUtil.getSessionFactory().openSession();
       
         Transaction transaction=null;
         List<Summary> result=null;
         String hql="select s from Summary s where s.subsurface =:sub";
         try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("sub", sub);
            result=query.list();
            
             System.out.println("db.dao.SummaryDAOImpl.getSummariesFor(): returning summaries of size : "+result.size());
            
            transaction.commit();
         }catch(Exception e){
             e.printStackTrace();
         }finally{
             session.close();
         }
         return result;
    }

    

   
    
}
