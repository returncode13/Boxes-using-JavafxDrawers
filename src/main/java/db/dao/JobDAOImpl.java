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
import org.hibernate.Session;
import org.hibernate.Transaction;

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
            oldJs.setNameJobStep(newJs.getNameJobStep());
            oldJs.setAlert(newJs.isAlert());
            oldJs.setJobVolumeMap(newJs.getJobVolumeMap());
            oldJs.setInsightVersions(newJs.getInsightVersions());
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

    
    
    
    
}
