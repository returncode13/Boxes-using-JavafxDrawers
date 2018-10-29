/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Volume;
import db.model.Workflow;
import app.connections.hibernate.HibernateUtil;
import app.properties.AppProperties;
import db.model.Job;
import db.model.Workspace;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class WorkflowDAOImpl implements WorkflowDAO {

    @Override
    public void createWorkFlow(Workflow W) {
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(W);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Workflow getWorkflow(Long wid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Workflow l= (Workflow) session.get(Workflow.class, wid);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateWorkFlow(Long wid, Workflow newW) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteWorkFlow(Long wid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Workflow> getWorkFlowsFor(Volume v) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Workflow> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Workflow.class);
            criteria.add(Restrictions.eq("volume", v));
            result=criteria.list();
            transaction.commit();
            if(result==null || result.size()==0)
                return null;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

    @Override
    public Workflow getWorkFlowWith(String md5,Volume vol) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Workflow> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Workflow.class);
            criteria.add(Restrictions.eq("volume", vol));
            criteria.add(Restrictions.eq("md5sum", md5));
            result=criteria.list();
            System.out.println("db.dao.WorkflowDAOImpl.getWorkFlowWith(): returning "+result.size()+ " workflows for md5: "+md5+" in volume: "+vol.getId());
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        if(result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

    @Override
    public Workflow getWorkFlowVersionFor(Volume v) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Workflow> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Workflow.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.addOrder(Order.desc("wfversion"));
            result=criteria.list();
            transaction.commit();
            if(result==null || result.size()==0)
                return null;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result.get(0);
    }

    @Override
    public Workflow getWorkflowRunBeforeTime(String time, Volume vol) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Workflow> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Workflow.class);
            criteria.add(Restrictions.eq("volume", vol));
            criteria.add(Restrictions.le("time", time));
            criteria.addOrder(Order.desc("time"));
            result=criteria.list();
            transaction.commit();
            if(result==null || result.size()==0)
                return null;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result.get(0);
    }

    @Override
    public Long getHighestWorkFlowVersionFor(Volume v) {
       Session session=HibernateUtil.getSessionFactory().openSession();
       Transaction transaction=null;
       String sql="select MAX(wfversion) from Workflow where volume = :v";
       List<Long> result=null;
       try{
           transaction =session.beginTransaction();
           Query query=session.createQuery(sql);
           query.setParameter("v", v);
           result=query.list();
            
            transaction.commit();
            if(result.get(0)==null){
                return 0L;
            }
       }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result.get(0);
    }

    @Override
    public void deleteWorkFlowsFor(Volume vol) {
        System.out.println("db.dao.WorkflowDAOImpl.deleteWorkFlowsFor()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        
        String hqlSelect="Select w.id from Workflow w where w.volume =:v";
        String hqlDelete="Delete from Workflow w where w.id in (:ids)";
        try{
            transaction=session.beginTransaction();
            Query selectQuery= session.createQuery(hqlSelect);
            selectQuery.setParameter("v", vol);
            List<Long> idsToDelete=selectQuery.list();
            
            if(!idsToDelete.isEmpty()){
                Query delQuery= session.createQuery(hqlDelete);
                delQuery.setParameterList("ids", idsToDelete);
                System.out.println("db.dao.WorkflowDAOImpl.deleteWorkFlowsFor(): deleting "+idsToDelete.size()+" workflows belonging to volume: "+vol.getNameVolume()+" ("+vol.getId()+")");
                int result=delQuery.executeUpdate();
            }
            
            
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
    
    
     @Override
    public void deleteWorkFlowsFor(Job job) {
         System.out.println("db.dao.WorkflowDAOImpl.deleteWorkFlowsFor()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        
        String hqlSelect="Select w.id from Workflow w inner join w.volume v inner join v.job j where j =:jj";
        String hqlDelete="Delete from Workflow w where w.id in (:ids)";
        try{
            transaction=session.beginTransaction();
            Query selectQuery= session.createQuery(hqlSelect);
            selectQuery.setParameter("jj", job);
            List<Long> idsToDelete=selectQuery.list();
            
            if(!idsToDelete.isEmpty()){
                Query delQuery= session.createQuery(hqlDelete);
                delQuery.setParameterList("ids", idsToDelete);
                System.out.println("db.dao.WorkflowDAOImpl.deleteWorkFlowsFor(): deleting "+idsToDelete.size()+" workflows belonging to volume: "+job.getNameJobStep()+" ("+job.getId()+")");
                int result=delQuery.executeUpdate();
            }
            
            
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Long getHighestWorkFlowVersionFor(Job job) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        Long highest=-1L;
        String hqlSelect="Select w.id from Workflow w inner join w.volume v inner join v.job j where j =:jj";
        String sql="select MAX(wfversion) from Workflow where  id in (:ids)";
         try{
            transaction=session.beginTransaction();
            Query selectQuery= session.createQuery(hqlSelect);
            selectQuery.setParameter("jj", job);
            List<Long> idsBelongingToJob=selectQuery.list();
            if(idsBelongingToJob.isEmpty()){
               highest=-1L;
            }else{
                List<Long> result=null;
                Query query=session.createQuery(sql);
                query.setParameterList("ids", idsBelongingToJob);
                result=query.list();
                highest=result.get(0);
            }
               transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return highest;
    }

    @Override
    public List<Workflow> getWorkFlowsFor(Job job) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Workflow> results=null;
        String hqlSelect="Select w from Workflow w inner join w.volume v inner join v.job j where j =:jj";
        try{
            transaction=session.beginTransaction();
            Query selectQuery= session.createQuery(hqlSelect);
            selectQuery.setParameter("jj", job);
            results=selectQuery.list();
             transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return results;
    }

    @Override
    public void updateControlFor(Workflow workflow, Boolean control) {
       Session session=HibernateUtil.getSessionFactory().openSession();
       Transaction transaction=null;
       String update="update Workflow w set w.control=:c where w.id =:i";
       try{
           transaction=session.beginTransaction();
           Query query=session.createQuery(update);
           query.setParameter("i", workflow.getId());
           query.setParameter("c", control);
           int res=query.executeUpdate();
           transaction.commit();
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           session.close();
       }
       
    }

    @Override
    public void updateCurrentVersionsFor(Job job) {
        System.out.println("db.dao.WorkflowDAOImpl.updateCurrentVersionsFor(job)");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String selectCurrentWorkflows="Select  distinct w.id from Log L INNER JOIN L.job j"
                + "                                                  INNER JOIN L.workflow w"
                + "                                                  WHERE j=:jj AND L.isMaxVersion=true";
        String setCurrentWorkflowsToFalse="update Workflow set isCurrentVersion=false where id in ("
                + "                                                                                 Select  distinct w.id from Log L INNER JOIN L.job j "
                + "                                                                                                                  INNER JOIN L.workflow w"
                + "                                                                                 where j=:jj"
                + "                                                                               )";
        String updateCurrentWorkflows="update  Workflow  set isCurrentVersion=true where id in (:ids)";
        
        try{
            transaction=session.beginTransaction();
            Query falseQuery=session.createQuery(setCurrentWorkflowsToFalse);
            falseQuery.setParameter("jj", job);
            int f=falseQuery.executeUpdate();
            System.out.println("db.dao.WorkflowDAOImpl.updateCurrentVersionsFor(): Set all current versions to false. returned value: "+f);
            
            
            Query query=session.createQuery(selectCurrentWorkflows);
            query.setParameter("jj", job);
            List<Long>idsToupdate=query.list();
            if(!idsToupdate.isEmpty()){
                
                Query updateQ=session.createQuery(updateCurrentWorkflows);
                updateQ.setParameterList("ids", idsToupdate);
                int res=updateQ.executeUpdate();
                System.out.println("db.dao.WorkflowDAOImpl.updateCurrentVersionsFor(): updating "+idsToupdate.size()+" current workflows ");
                
            }else{
                System.out.println("db.dao.WorkflowDAOImpl.updateCurrentVersionsFor(): NO Current Workflows found for job: "+job.getNameJobStep());
            }
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        
        
    }

    @Override
    public List<Object[]> getCurrentWorkflowsIn(Workspace workspace) {
        System.out.println("db.dao.WorkflowDAOImpl.getCurrentWorkflowsIn(Workspace)");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Object[]> results=null;
        String hql="Select j,sub,w from Log lg INNER JOIN lg.job j"
                + "                            INNER JOIN j.workspace wks"
                + "                            INNER JOIN lg.subsurface sub"
                + "                            INNER JOIN lg.workflow w"
                + "                            WHERE lg.isMaxVersion=true and wks=:wksp";
         try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("wksp", workspace);
            results=query.list();
             System.out.println("db.dao.WorkflowDAOImpl.getCurrentWorkflowsIn(): returning "+results.size()+" workflows");

             transaction.commit();
        }catch(Exception e){
            throw e;
        }finally{
             session.close();
         }
     return results;   
    }

    @Override
    public void updateSubsurfacesForJobWithWorkflow(Job job, Workflow workflow) {
        System.out.println("db.dao.WorkflowDAOImpl.updateSubsurfacesForJobWithWorkflow()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        /*String getSubs="Select sub.id from Log l INNER JOIN l.job job"
        + "                           INNER JOIN l.subsurface sub"
        + "                           INNER JOIN l.workflow w "
        + "                             WHERE "
        + "                           l.isMaxVersion=true"
        + "                             AND"
        + "                           job=:jj"
        + "                             AND"
        + "                           w=:wkf";*/
         String getSubs="Select l.subsurface.id from Log l "
                + "                                 WHERE "
                + "                           l.isMaxVersion=true"
                + "                             AND"
                + "                           l.job=:jj"
                + "                             AND"
                + "                           l.workflow=:wkf";
        String updateSubs="update SubsurfaceJob set updateTime = :up where pk.job =:jj and pk.subsurface.id in (:subids)";
        
        try{
            transaction=session.beginTransaction();
            Query getSubQuery=session.createQuery(getSubs);
            getSubQuery.setParameter("jj", job);
            getSubQuery.setParameter("wkf", workflow);
            List<Long> subids=getSubQuery.list();
                if(subids.isEmpty()){
                    System.out.println("db.dao.WorkflowDAOImpl.updateSubsurfacesForJobWithWorkflow(): no subsurfaces found for "+job.getNameJobStep()+" workflow: id: "+workflow.getId()+" version: "+workflow.getWfversion());
                }else{
                    Query updateQuery=session.createQuery(updateSubs);
                    updateQuery.setParameter("jj",job);
                    updateQuery.setParameter("up",AppProperties.timeNow());
                    updateQuery.setParameterList("subids", subids);
                    int res=updateQuery.executeUpdate();
                    System.out.println("db.dao.WorkflowDAOImpl.updateSubsurfacesForJobWithWorkflow(): updating "+subids.size()+" subsurfaces ");
                }
            transaction.commit();
                
        }catch(Exception e){
            throw e;
        }finally{
            session.close();
        }
        
        
        
    }
}
