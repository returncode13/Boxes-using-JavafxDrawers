/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Doubt;
import db.model.DoubtType;
import app.connections.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DoubtTypeDAOImpl implements DoubtTypeDAO {

    @Override
    public void createDoubtType(DoubtType dt) {
        System.out.println("db.dao.DoubtTypeDAOImpl.createDoubtType()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(dt);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateDoubtType(Long dtid, DoubtType newdt) {
        System.out.println("db.dao.DoubtTypeDAOImpl.updateDoubtType()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            DoubtType ll=(DoubtType) session.get(DoubtType.class,dtid);
            ll.setName(newdt.getName());
            session.update(ll);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public DoubtType getDoubtType(Long dtid) {
        System.out.println("db.dao.DoubtTypeDAOImpl.getDoubtType()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            DoubtType ll=(DoubtType) session.get(DoubtType.class,dtid);
            return ll;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void deleteDoubtType(Long dtid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            DoubtType ll=(DoubtType) session.get(DoubtType.class,dtid);
            session.delete(ll);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public DoubtType getDoubtTypeByName(String doubtName) {
        System.out.println("db.dao.DoubtTypeDAOImpl.getDoubtTypeByName()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<DoubtType> result=null;
        String hql ="Select dt from DoubtType dt where dt.name =:n";
        try{
            transaction=session.beginTransaction();
            /* Criteria criteria=session.createCriteria(DoubtType.class);
            
            criteria.add(Restrictions.eq("name", doubtName));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);*/
            Query query=session.createQuery(hql);
            query.setParameter("n", doubtName);
            
            result=query.list();
            //result=criteria.list();
            transaction.commit();
         //   System.out.println("db.dao.DoubtTypeDAOImpl.getDoubtTypeByName(): returning doubttype of size: "+result.size()+" for name "+doubtName);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        if(result.isEmpty()) return null;
        else
        return result.get(0);
    }
    
    
}
