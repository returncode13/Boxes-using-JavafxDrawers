/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.Dot;
import db.model.VariableArgument;
import db.model.Workspace;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class VariableArgumentDAOImpl implements VariableArgumentDAO{

    @Override
    public void createVariableArgument(VariableArgument va) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(va);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public VariableArgument getVariableArgument(Long vaid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            VariableArgument l= (VariableArgument) session.get(VariableArgument.class, vaid);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateVariableArgument(Long vaid, VariableArgument newVa) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
             VariableArgument l= (VariableArgument) session.get(VariableArgument.class, vaid);
             l.setVariable(newVa.getVariable());
             l.setArgument(newVa.getArgument());
             l.setDot(newVa.getDot());
            
             session.update(l);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public void deleteVariableArgument(Long vaid) {
        System.out.println("db.dao.VariableArgumentDAOImpl.deleteVariableArgument()");
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            VariableArgument l= (VariableArgument) session.get(VariableArgument.class, vaid);
            session.delete(l);
            transaction.commit();
            System.out.println("db.dao.VariableArgumentDAOImpl.deleteVariableArgument():..done deleting "+vaid);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public List<VariableArgument> getVariableArgumentsForDot(Dot dbDot) {
        System.out.println("db.dao.VariableArgumentDAOImpl.getVariableArgumentsForDot()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String hql="from VariableArgument v where v.dot =:d";
        List<VariableArgument> results=null;
        try{
            transaction = session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("d", dbDot);
            results=query.list();
            
            transaction.commit();
            System.out.println("db.dao.VariableArgumentDAOImpl.getVariableArgumentsForDot(): returning "+results.size()+" variable Arguments for dot "+dbDot.getId());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return results;
    }

    @Override
    public List<VariableArgument> getVariableArgumentsForWorkspace(Workspace w) {
         System.out.println("db.dao.VariableArgumentDAOImpl.getVariableArgumentsForWorkspace()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String hql="select v from VariableArgument v INNER JOIN v.dot d WHERE d.workspace =:w";
        List<VariableArgument> results=null;
        try{
            transaction = session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("w",w);
            results=query.list();
            
            transaction.commit();
            System.out.println("db.dao.VariableArgumentDAOImpl.getVariableArgumentsForDot(): returning "+results.size()+" variable Arguments for workspace "+w.getId());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return results;
    }
    
}
