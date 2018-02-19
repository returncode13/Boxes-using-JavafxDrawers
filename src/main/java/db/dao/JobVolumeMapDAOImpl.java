/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil_back;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import db.model.Job;
import db.model.JobVolumeMap;
import db.model.Volume;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class JobVolumeMapDAOImpl implements JobVolumeMapDAO{

    @Override
    public void createJobVolumeMap(JobVolumeMap jvd) {
         Session session = HibernateUtil_back.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.save(jvd);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public JobVolumeMap getJobVolumeMap(Long jvdId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateJobVolumeMap(Long jvdId, JobVolumeMap newJvd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteJobVolumeMap(Long jvdId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<JobVolumeMap> getJobVolumeMap(Job js) {
        Session session = HibernateUtil_back.getSessionFactory().openSession();
        Transaction transaction = null;
        List<JobVolumeMap> result=null;
        
        try{
            transaction=session.beginTransaction();
            Criteria criteria = session.createCriteria(JobVolumeMap.class);
            criteria.add(Restrictions.eq("job", js));
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
    public List<JobVolumeMap> getJobVolumeMap(Volume v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Job> getJobsContaining(Volume v) {
         Session session = HibernateUtil_back.getSessionFactory().openSession();
        Transaction transaction = null;
        List<JobVolumeMap> result=null;
        List<Job> jobStepList=new ArrayList<>();
        /* try{
        transaction=session.beginTransaction();
        Criteria criteria = session.createCriteria(JobVolumeMap.class);
        criteria.add(Restrictions.eq("volume", v));
        result=criteria.list();
        transaction.commit();
        }catch(Exception e){
        e.printStackTrace();
        }finally{
        session.close();
        }
        
        for (Iterator<JobVolumeMap> iterator = result.iterator(); iterator.hasNext();) {
        JobVolumeMap jvd = iterator.next();
        jobStepList.add(jvd.getJob());
        }*/
        
        return jobStepList;
    }

    @Override
    public List<Volume> getVolumesFor(Job js) {
       Session session = HibernateUtil_back.getSessionFactory().openSession();
        Transaction transaction = null;
        List<JobVolumeMap> result=null;
        List<Volume> volumeList=new ArrayList<>();
        /* try{
        transaction=session.beginTransaction();
        Criteria criteria = session.createCriteria(JobVolumeMap.class);
        criteria.add(Restrictions.eq("job", js));
        result=criteria.list();
        transaction.commit();
        }catch(Exception e){
        e.printStackTrace();
        }finally{
        session.close();
        }
        
        for (Iterator<JobVolumeMap> iterator = result.iterator(); iterator.hasNext();) {
        JobVolumeMap jvd = iterator.next();
        volumeList.add(jvd.getVolume());
        }*/
        
        return volumeList;
    }

    @Override
    public JobVolumeMap getJobVolumeMap(Job js, Volume v) {
        
        
          Session session = HibernateUtil_back.getSessionFactory().openSession();
        Transaction transaction = null;
        List<JobVolumeMap> result=null;
        List<Volume> volumeList=new ArrayList<>();
        try{
            transaction=session.beginTransaction();
            Criteria criteria = session.createCriteria(JobVolumeMap.class);
            criteria.add(Restrictions.eq("job", js));
            criteria.add(Restrictions.eq("volume", v));
            result=criteria.list();
            transaction.commit();
            
            if(result.size()>0) return result.get(0);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        
        return null;
        
    }
    
    
}
