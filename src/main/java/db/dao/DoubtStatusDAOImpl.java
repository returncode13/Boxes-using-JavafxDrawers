/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import app.properties.AppProperties;
import db.model.DoubtStatus;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DoubtStatusDAOImpl implements DoubtStatusDAO{

    @Override
    public void createDoubtStatus(DoubtStatus ds) {
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(ds);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

     @Override
    public void createBulkDoubtStatus(List<DoubtStatus> doubtStatuses) {
        int batchsize=Math.min(doubtStatuses.size(), AppProperties.BULK_TRANSACTION_BATCH_SIZE);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
           for(int ii=0;ii<doubtStatuses.size();ii++){
               session.save(doubtStatuses.get(ii));
               if(ii%batchsize ==0){
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
    public DoubtStatus getDoubtStatus(Long id) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            DoubtStatus l= (DoubtStatus) session.get(DoubtStatus.class, id);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void deleteDoubtStatus(Long id) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            DoubtStatus l= (DoubtStatus) session.get(DoubtStatus.class, id);
            session.delete(l);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public void updateDoubtStatus(Long id, DoubtStatus newds) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
             DoubtStatus l= (DoubtStatus) session.get(DoubtStatus.class, id);
             l.setReason(newds.getReason());
             l.setDoubt(newds.getDoubt());
             l.setStatus(newds.getStatus());
             l.setState(newds.getState());
             l.setTimeStamp(newds.getTimeStamp());
             l.setUser(newds.getUser());
             l.setComments(newds.getComments());
             
             
            session.update(l);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

   

    @Override
    public void updateBulkDoubtStatus(List<DoubtStatus> doubtStatusToBeUpdated) {
        int batchsize=Math.min(doubtStatusToBeUpdated.size(), AppProperties.BULK_TRANSACTION_BATCH_SIZE);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
           for(int ii=0;ii<doubtStatusToBeUpdated.size();ii++){
               session.update(doubtStatusToBeUpdated.get(ii));
               if(ii%batchsize ==0){
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
    public void deleteBulkDoubtStatus(List<Long> idsOfDoubtStatusToBeDeleted) {
        //int batchsize=Math.min(doubtStatusToBeDeleted.size(), AppProperties.BULK_TRANSACTION_BATCH_SIZE);
        if(idsOfDoubtStatusToBeDeleted.isEmpty()) return;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql="DELETE  DoubtStatus d WHERE d.id IN (:ids)";
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            /*for(int ii=0;ii<doubtsToBeDeleted.size();ii++){
            session.delete(doubtsToBeDeleted.get(ii));
            if(ii%batchsize ==0){
            session.flush();
            session.clear();
            }
            }
            */
            
            Query query=session.createQuery(hql);
            query.setParameterList("ids", idsOfDoubtStatusToBeDeleted);
            int result=query.executeUpdate();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
    
}
