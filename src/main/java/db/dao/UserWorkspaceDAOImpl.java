/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.User;
import db.model.UserWorkspace;
import db.model.Workspace;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath.nair sharath.nair@polarcus.com
 */
public class UserWorkspaceDAOImpl implements UserWorkspaceDAO{

    @Override
    public void createUserWorkspace(UserWorkspace userWorkspace) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.save(userWorkspace);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public UserWorkspace getUserWorkspace(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            
            UserWorkspace uw=(UserWorkspace) session.get(UserWorkspace.class, id);
            return uw;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void deleteUserWorkspace(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            UserWorkspace uw=(UserWorkspace) session.get(UserWorkspace.class, id);
           session.delete(uw);
           transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public void updateUserWorkspace(Long id, UserWorkspace newUserWorkspace) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            UserWorkspace uw=(UserWorkspace) session.get(UserWorkspace.class, id);
            uw.setUser(newUserWorkspace.getUser());
            uw.setWorkspace(newUserWorkspace.getWorkspace());
          //  ll.setSessions(newQcType.getSessions());
            session.update(uw);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public UserWorkspace getUserWorkspaceFor(User user, Workspace workspace) {
       Session session =HibernateUtil.getSessionFactory().openSession();
       Transaction transaction=null;
       List<UserWorkspace> result=null;
       
       try{
           transaction=session.beginTransaction();
           Criteria criteria=session.createCriteria(UserWorkspace.class);
           criteria.add(Restrictions.eq("pk.user", user));
           criteria.add(Restrictions.eq("pk.workspace", workspace));
           result=criteria.list();
           transaction.commit();
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           session.close();
       }
       
         if(result.size()>1){
            return null;
        }if(result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

    @Override
    public void remove(User u, Workspace w) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String hql="Delete  UserWorkspace uw where uw.pk.user =:usr AND uw.pk.workspace = :w";
        try{
            transaction = session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("usr", u);
            query.setParameter("w", w);
            
            int result=query.executeUpdate();
        }catch(Exception e){
            
        }finally{
            session.close();
        }
        
                
    }
    
    
    
}
