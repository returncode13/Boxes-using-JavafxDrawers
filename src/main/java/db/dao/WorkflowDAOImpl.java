/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Volume;
import db.model.Workflow;
import app.connections.hibernate.HibernateUtil;
import db.model.Job;
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
                System.out.println("db.dao.WorkflowDAOImpl.deleteWorkFlowsFor(job): deleting "+idsToDelete.size()+" workflows belonging to volume: "+job.getNameJobStep()+" ("+job.getId()+")");
                int result=delQuery.executeUpdate();
            }
            
            
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
    
}
