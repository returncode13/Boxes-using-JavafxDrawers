/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.CommentType;
import db.model.Job;
import db.model.UserPreference;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class UserPreferenceDAOImpl implements UserPreferenceDAO{

    @Override
    public void createUserPreference(UserPreference up) {
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(up);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        
    }

    @Override
    public UserPreference getUserPreference(Long id) {
       Session session=HibernateUtil.getSessionFactory().openSession();
       UserPreference up=null;
       try{
           up=(UserPreference) session.get(UserPreference.class,id);
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           session.close();
       }
       return up;
    }

    @Override
    public void updateUserPreference(UserPreference up) {
          
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String updateSql="update UserPreference  set jsonProperty =:n where id =:m";
         
        try{
                                    transaction=session.beginTransaction();
                                  Query query=session.createQuery(updateSql);
                                  query.setParameter("m", up.getId());
                                  query.setParameter("n", up.getJsonProperty());
                                  int result=query.executeUpdate();
                                  transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void deleteUserPreference(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserPreference getUserPreferenceFor(Job j,CommentType type) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<UserPreference> userPreferences=null;
        String hql="Select up from UserPreference up INNER JOIN up.job j "
                + "                                  INNER JOIN up.commentType t"
                + "                             WHERE  j =:j"
                + "                                 AND"
                + "                                    t =:t";
        try{
            
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("j", j);
            query.setParameter("t", type);
            userPreferences=query.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        if(userPreferences.isEmpty()) return null;
        else
        return userPreferences.get(0);
    }

    
    @Override
    public void deleteAllUserPreferencesFor(Job j) {
        System.out.println("db.dao.UserPreferenceDAOImpl.deleteAllUserPreferencesFor()");
       Session session= HibernateUtil.getSessionFactory().openSession();
       Transaction transaction=null;
       String hqlSelect = "Select q.id from  UserPreference q INNER JOIN q.job j where j =:j";
       String hqlDelete = "Delete from UserPreference q where q.id in (:ids)";
       
       try{
           transaction=session.beginTransaction();
           Query selectQuery=session.createQuery(hqlSelect);
           selectQuery.setParameter("j", j);
           List<Long> idsToDelete=selectQuery.list();
           
           
           if(idsToDelete.isEmpty()){
               transaction.commit();
               System.out.println("db.dao.UserPreferenceDAOImpl.deleteAllUserPreferencesFor(): no qctable entries found for job: "+j.getNameJobStep());
           }else{
               System.out.println("db.dao.UserPreferenceDAOImpl.deleteAllUserPreferencesFor(): deleting "+idsToDelete.size()+" qctable entries for job: "+j.getNameJobStep());
               Query deleteQuery=session.createQuery(hqlDelete);
               deleteQuery.setParameterList("ids", idsToDelete);
               int del=deleteQuery.executeUpdate();
               transaction.commit();
               
           }
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           session.close();
       }
    }
    
}
