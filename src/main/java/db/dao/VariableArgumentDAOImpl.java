/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.VariableArgument;
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
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            VariableArgument l= (VariableArgument) session.get(VariableArgument.class, vaid);
            session.delete(l);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
    
}
