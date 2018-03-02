/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import db.model.Job;
/*import db.model.SessionDetails;
import db.model.Sessions;*/
import db.model.Volume;
import db.model.Workspace;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class JobDAOImpl implements JobDAO{

    public JobDAOImpl() {
    }

    
    
    @Override
    public void createJob(Job js) {
        
             System.out.println("db.dao.JobDAOImpl.createJob()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(js);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public Job getJob(Long jobId) {
        System.out.println("db.dao.JobDAOImpl.getJob()");
         Session session = HibernateUtil.getSessionFactory().openSession();
        
       
        try{
            Job js = (Job) session.get(Job.class, jobId);
           // System.out.println("JobDAOIMPL: checking for id "+jobId+" and found "+(js==null?" NULL":js.getIdJob()));
            return js;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateJob(Long jobId,Job newJs) {
      Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        System.out.println("db.dao.JobDAOImpl.updateJob()");
        try{
            transaction=session.beginTransaction();
            
            Job oldJs=(Job) session.get(Job.class, jobId);
            oldJs.setWorkspace(newJs.getWorkspace());
           // oldJs.setVolumes(newJs.getVolumes());
            oldJs.setNameJobStep(newJs.getNameJobStep());
            oldJs.setAlert(newJs.isAlert());
           // oldJs.setJobVolumeMap(newJs.getJobVolumeMap());
            oldJs.setInsightVersions(newJs.getInsightVersions());
            
          //  oldJs.setHeaders(newJs.getHeaders());
          //  oldJs.setDescendants(newJs.getDescendants());
         //   oldJs.setAncestors(newJs.getAncestors());
         //   oldJs.setLinksWithJobAsChild(newJs.getLinksWithJobAsChild());
        //    oldJs.setLinksWithJobAsParent(newJs.getLinksWithJobAsParent());
        //    oldJs.setLogs(newJs.getLogs());
            oldJs.setNodetype(newJs.getNodetype());
        //    oldJs.setQcmatrix(newJs.getQcmatrix());
       // //    oldJs.setVariableArguments(newJs.getVariableArguments());
        //    oldJs.setDoubts(newJs.getDoubts());
       //     oldJs.setSummaries(newJs.getSummaries());
            //oldJs.setSequences(newJs.getSequences());
        //    oldJs.setSubsurfaceJobs(newJs.getSubsurfaceJobs());
            //oldJs.setSubsurfaces(newJs.getSubsurfaces());
            oldJs.setDepth(newJs.getDepth());
            
            session.update(oldJs);
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void deleteJob(Long jobId) {
        System.out.println("db.dao.JobDAOImpl.deleteJob()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction =  null;
        
        try{
            transaction= session.beginTransaction();
            
            Job js = (Job) session.get(Job.class, jobId);  //try using criteria instead
            System.out.println((js==null)?"db.dao.JobDAOImpl.deleteJob: NULL found!":"Deleting job:  "+js.getNameJobStep()+" id: "+js.getId());
            session.delete(js);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

   

    /*
    @Override
    public void setPending(Job njs) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    
    try{
    transaction=session.beginTransaction();
    
    Job oldJs=(Job) session.get(Job.class, njs.getIdJob());
    oldJs.setPending(Boolean.TRUE);
    session.update(oldJs);
    
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    }
    
    @Override
    public void resetPending(Job njs) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    
    try{
    transaction=session.beginTransaction();
    
    Job oldJs=(Job) session.get(Job.class, njs.getIdJob());
    oldJs.setPending(Boolean.FALSE);
    session.update(oldJs);
    
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    }*/

    @Override
    public List<Long> getDepthOfGraph(Workspace W) {
        System.out.println("db.dao.JobDAOImpl.getDepthOfGraph()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Long> depths=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Job.class);
            criteria.add(Restrictions.eq("workspace", W));
            ProjectionList pList=Projections.projectionList();
            pList.add(Projections.property("depth"));
            criteria.setProjection(Projections.distinct(pList));
            depths=criteria.list();
            transaction.commit();
            
            System.out.println("db.dao.JobDAOImpl.getDepthOfGraph(): returning "+depths.size()+" depths");
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return depths;
    }

    @Override
    public List<Job> listJobs(Workspace W) {
        System.out.println("db.dao.JobDAOImpl.listJobs()");
        Session session= HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Job> result=null;
        String hql="from Job j where j.workspace= :w";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("w", W);
            /* Criteria criteria=session.createCriteria(Job.class);
            criteria.add(Restrictions.eq("workspace", W));*/
          //  result=criteria.list();
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
    public void updateName(Job dbjob, String name) {
        System.out.println("db.dao.JobDAOImpl.updateName()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String sql=" update Job set name =:n where id = :id";
              
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(sql);
            query.setParameter("n", name);
            query.setParameter("id", dbjob.getId());
            
            int result=query.executeUpdate();
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateDepth(Job dbjob, Long depth) {
        System.out.println("db.dao.JobDAOImpl.updateDepth()");
         Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String sql=" update Job set depth =:n where id = :id";
              
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(sql);
            query.setParameter("n", depth);
            query.setParameter("id", dbjob.getId());
            
            int result=query.executeUpdate();
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateInsightVersionInJob(Job job) {
        System.out.println("db.dao.JobDAOImpl.updateInsightVersionInJob()");
     Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String sql=" update Job set insightVersions =:n where id = :id";
              
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(sql);
            query.setParameter("n", job.getInsightVersions());
            query.setParameter("id", job.getId());
            
            int result=query.executeUpdate();
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public List<Job> getJobsInWorkspace(Workspace W) {
        System.out.println("db.dao.JobDAOImpl.getJobsInWorkspace()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String sql=" from Job j where j.workspace =:w";
              List<Job> results=null;
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(sql);
            query.setParameter("w", W);
            
            results=query.list();
            transaction.commit();
            System.out.println("db.dao.JobDAOImpl.getJobsInWorkspace(): returning "+results.size()+" jobs for the workspace");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return results;
    }




    
    
    
    
}
