/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;



import db.model.User;
import app.connections.hibernate.HibernateUtil;
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
public class UserDAOImpl implements UserDAO{

    @Override
    public void createUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateUser(Long uid, User newUser) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            User ll=(User) session.get(User.class,uid);
            //ll.setWorkspaces(newUser.getWorkspaces());
            ll.setQctables(newUser.getQctables());
            ll.setDoubtStatuses(newUser.getDoubtStatuses());
            ll.setFullName(newUser.getFullName());
            ll.setInitials(newUser.getInitials());
           // ll.setOwnedWorkspaces(newUser.getOwnedWorkspaces());
            session.update(ll);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public User getUser(Long uid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            User l= (User) session.get(User.class, uid);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void deleteUser(Long uid) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            User h= (User) session.get(User.class, uid);
            session.delete(h);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    /*@Override
    public User getUserWithInitials(String ini) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<User> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(User.class);
    criteria.add(Restrictions.eq("initials", ini));
    criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    
    result=criteria.list();
    transaction.commit();
    System.out.println("db.dao.UserDAOImpl.getUserWithInitials(): got "+result.size()+" users with initials "+ini);
    if(result==null || result.size()==0)
    return null;
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    return result.get(0);
    }*/

    
    
    @Override
    public User getUserWithInitials(String ini) {
         Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String sql=" from  User where initials = :ini";
        List<User> result=null;      
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(sql);
            query.setParameter("ini", ini);
            
            
          result=query.list();
          transaction.commit();
          System.out.println("db.dao.UserDAOImpl.getUserWithInitials(): got "+result.size()+" users with initials "+ini);
            if(result.isEmpty()){
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result.get(0);
    }

    @Override
    public List<User> getUsersInWorkspace(Workspace w) {
         Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String sql=" Select u from User u INNER JOIN u.userWorkspaceEntries uw"
                + "                       WHERE uw.pk.workspace =:w";
        List<User> result=null;      
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(sql);
            query.setParameter("w", w);
            
            
          result=query.list();
          transaction.commit();
            System.out.println("db.dao.UserDAOImpl.getUsersInWorkspace(): returning "+result.size()+" users for workspace " +w.getName()+" ("+w.getId()+")");
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

    
    
 
}
