/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.Job;
import db.model.Volume;
import db.model.Workspace;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class VolumeDAOImpl implements VolumeDAO {

    @Override
    public void createVolume(Volume v) {
        System.out.println("db.dao.VolumeDAOImpl.createVolume()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(v);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
            
        }
    }

    @Override
    public Volume getVolume(Long volid) {
        System.out.println("db.dao.VolumeDAOImpl.getVolume()");
        Session session = HibernateUtil.getSessionFactory().openSession();
         Transaction transaction=null;
         String hql="from Volume where id =:volid";
        List<Volume> results=null;
        try{
            transaction=session.beginTransaction();
             Query query=session.createQuery(hql);
            query.setParameter("volid", volid);
            //Volume v = (Volume) session.get(Volume.class, volid);
            results=query.list();
            transaction.commit();
            System.out.println("db.dao.VolumeDAOImpl.getVolume(): returning volume : "+results.get(0).getId());
            return results.get(0);
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    

    @Override
    public void deleteVolume(Long volid) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
       
        try{
            transaction=session.beginTransaction();
           
            
            
            
            
           Volume v = (Volume) session.get(Volume.class, volid);
            session.delete(v);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void startAlert(Volume nv) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
           Volume v = (Volume) session.get(Volume.class, nv.getId());
           
            
               v.setAlert(Boolean.TRUE);
                session.update(v);
            
            
          
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void stopAlert(Volume nv) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
           Volume v = (Volume) session.get(Volume.class, nv.getId());
           
            
               v.setAlert(Boolean.FALSE);
                session.update(v);
            
            
          
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void setHeaderExtractionFlag(Volume nv) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
           Volume v = (Volume) session.get(Volume.class, nv.getId());
           
            
               v.setHeaderExtracted(Boolean.TRUE);
                session.update(v);
            
            
          
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void resetHeaderExtractionFlag(Volume nv) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
           Volume v = (Volume) session.get(Volume.class, nv.getId());
           
            
               v.setHeaderExtracted(Boolean.FALSE);
                session.update(v);
            
            
          
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateVolume(Long volid, Volume newVol) {
        System.out.println("db.dao.VolumeDAOImpl.updateVolume()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        try{
            transaction=session.beginTransaction();
            
            Volume ov=(Volume) session.get(Volume.class,newVol.getId());
            ov.setJob(newVol.getJob());
            ov.setNameVolume(newVol.getNameVolume());
            ov.setPathOfVolume(newVol.getPathOfVolume());
            ov.setVolumeType(newVol.getVolumeType());
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public List<Volume> getVolumesForJob(Job job) {
        System.out.println("db.dao.VolumeDAOImpl.getVolumesForJob()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Volume> results=null;
        String hql="from Volume v where v.job = :j";
        try{
            transaction=session.beginTransaction();
            
            /*Criteria criteria=session.createCriteria(Volume.class);
            criteria.add(Restrictions.eq("job", job));*/
            Query query=session.createQuery(hql);
            query.setParameter("j", job);
            results=query.list();
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        return results;
    }

    @Override
    public List<Volume> getAllVolumesIn(Workspace workspace) {
         System.out.println("db.dao.VolumeDAOImpl.getVolumesForJob()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Volume> results=null;
        String hql="select v from Volume v inner join v.job j where j.workspace =:w";
        try{
            transaction=session.beginTransaction();
           
            Query query=session.createQuery(hql);
            query.setParameter("w", workspace);
            results=query.list();
            transaction.commit();
            System.out.println("db.dao.VolumeDAOImpl.getAllVolumesIn(): returning  "+results.size()+ " volumes belonging to workspace "+workspace.getName());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        return results;
    }

    
    
}
