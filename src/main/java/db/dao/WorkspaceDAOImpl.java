/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil_back;
import java.util.List;
import db.model.Workspace;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sharath nair
 */
public class WorkspaceDAOImpl implements WorkspaceDAO {

    @Override
    public void createWorkspace(Workspace s) {
      Session session = HibernateUtil_back.getSessionFactory().openSession();
      Transaction transaction=null;
      
      try{
          transaction=session.beginTransaction();
          session.saveOrUpdate(s);
          transaction.commit();
          
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          session.close();
      }
    }

    @Override
    public Workspace getWorkspace(Long sessionId) {
        Session session = HibernateUtil_back.getSessionFactory().openSession();
            
      try{
          
          Workspace s=(Workspace) session.get(Workspace.class,sessionId);
         // System.out.println("SessDAOIMPL: checking for id "+sessionId+" and found "+(s==null?" NULL":s.getIdWorkspace()));
          return s;
          
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          session.close();
      }
      return null;
    }

    @Override
    public void updateWorkspace(Long sessionId, Workspace newSession) {
        Session session = HibernateUtil_back.getSessionFactory().openSession();
      Transaction transaction=null;
      
      try{
          transaction=session.beginTransaction();
          Workspace s=(Workspace) session.get(Workspace.class, sessionId);
          s.setName(newSession.getName());
          s.setJobs(newSession.getJobs());
          s.setDots(newSession.getDots());
         // System.out.println("db.dao.WorkspaceDAOImpl.updateWorkspace(): "+newSession.getName()+" has "+newSession.getUsers().size()+" users");
          s.setUsers(newSession.getUsers());
          s.setOwner(newSession.getOwner());
          session.update(s);
          transaction.commit();
          
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          session.close();
      }
    }

    @Override
    public void deleteWorkspace(Long sessionId) {
        Session session = HibernateUtil_back.getSessionFactory().openSession();
      Transaction transaction=null;
      
      try{
          transaction=session.beginTransaction();
          Workspace s=(Workspace) session.get(Workspace.class, sessionId);
          session.delete(s);
          transaction.commit();
          
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          session.close();
      }
    }

    @Override
    public List<Workspace> listWorkspaces() {
        Session hibsession = HibernateUtil_back.getSessionFactory().openSession();
        List<Workspace> sessList=new ArrayList<>();
      Transaction transaction=null;
      
      try{
          transaction=hibsession.beginTransaction();
          sessList=hibsession.createCriteria(Workspace.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
          System.out.println("db.dao.WorkspaceDAOImpl.listWorkspaces(): no of workspaces returned : "+sessList.size());
          transaction.commit();
          
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          hibsession.close();
      }
      return sessList;
    }
    
    
}
