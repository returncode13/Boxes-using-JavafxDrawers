/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.Volume;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sharath nair
 */
public class VolumeDAOImpl implements VolumeDAO {

    @Override
    public void createVolume(Volume v) {
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Volume v = (Volume) session.get(Volume.class, volid);
            return v;
            
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

    
    
}
