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
        
        try{
            transaction=session.beginTransaction();
            
            Job oldJs=(Job) session.get(Job.class, jobId);
            oldJs.setWorkspace(newJs.getWorkspace());
            oldJs.setVolumes(newJs.getVolumes());
            oldJs.setNameJobStep(newJs.getNameJobStep());
            oldJs.setAlert(newJs.isAlert());
           // oldJs.setJobVolumeMap(newJs.getJobVolumeMap());
            oldJs.setInsightVersions(newJs.getInsightVersions());
            
            oldJs.setHeaders(newJs.getHeaders());
            oldJs.setDescendants(newJs.getDescendants());
            oldJs.setAncestors(newJs.getAncestors());
            oldJs.setLinksWithJobAsChild(newJs.getLinksWithJobAsChild());
            oldJs.setLinksWithJobAsParent(newJs.getLinksWithJobAsParent());
            oldJs.setLogs(newJs.getLogs());
            oldJs.setNodetype(newJs.getNodetype());
            oldJs.setQcmatrix(newJs.getQcmatrix());
            oldJs.setVariableArguments(newJs.getVariableArguments());
            oldJs.setDoubts(newJs.getDoubts());
            oldJs.setSummaries(newJs.getSummaries());
            //oldJs.setSequences(newJs.getSequences());
            oldJs.setSubsurfaceJobs(newJs.getSubsurfaceJobs());
            oldJs.setSubsurfaces(newJs.getSubsurfaces());
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
        Session session= HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Job> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Job.class);
            criteria.add(Restrictions.eq("workspace", W));
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
