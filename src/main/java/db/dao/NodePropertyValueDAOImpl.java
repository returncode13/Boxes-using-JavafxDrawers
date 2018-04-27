/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;


import db.model.Job;
import db.model.NodeProperty;

import db.model.NodePropertyValue;


import app.connections.hibernate.HibernateUtil;
import java.util.ArrayList;
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
public class NodePropertyValueDAOImpl implements NodePropertyValueDAO {

    @Override
    public void createNodePropertyValue(NodePropertyValue npv) {
        System.out.println("db.dao.NodePropertyValueDAOImpl.createNodePropertyValue()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(npv);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public NodePropertyValue getNodePropertyValue(Long npvid) {
        System.out.println("db.dao.NodePropertyValueDAOImpl.getNodePropertyValue()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            NodePropertyValue ll=(NodePropertyValue) session.get(NodePropertyValue.class,npvid);
            return ll;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateNodePropertyValue(Long npvid, NodePropertyValue newNpv) {
        System.out.println("db.dao.NodePropertyValueDAOImpl.updateNodePropertyValue()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            NodePropertyValue ll=(NodePropertyValue) session.get(NodePropertyValue.class,npvid);
            ll.setJob(newNpv.getJob());
            ll.setNodeProperty(newNpv.getNodeProperty());
            ll.setValue(newNpv.getValue());
          //  ll.setSessions(newQcType.getSessions());
            session.update(ll);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public void deleteNodePropertyValue(Long npvid) {
        System.out.println("db.dao.NodePropertyValueDAOImpl.deleteNodePropertyValue()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            NodePropertyValue ll=(NodePropertyValue) session.get(NodePropertyValue.class,npvid);
            session.delete(ll);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public List<NodePropertyValue> getNodePropertyValuesFor(Job job) {
        System.out.println("db.dao.NodePropertyValueDAOImpl.getNodePropertyValuesFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<NodePropertyValue> result=null;
        
        try{
            transaction=session.beginTransaction();
            Criteria criteria = session.createCriteria(NodePropertyValue.class);
            criteria.add(Restrictions.eq("job", job));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        //result=criteria.list();
            
            result=criteria.list();
            transaction.commit();
            
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        
        return null;
    }

    @Override
    public NodePropertyValue getNodePropertyValuesFor(Job jobStep, NodeProperty nodeProperty) {
        System.out.println("db.dao.NodePropertyValueDAOImpl.getNodePropertyValuesFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<NodePropertyValue> result=null;
        
        try{
            transaction=session.beginTransaction();
            Criteria criteria = session.createCriteria(NodePropertyValue.class);
            criteria.add(Restrictions.eq("job", jobStep));
            criteria.add(Restrictions.eq("nodeProperty", nodeProperty));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            result=criteria.list();
            transaction.commit();
            if(result.size()==1){
                return result.get(0);
            }if(result.size()>1){
                //cry about it
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        
        return null;
    }

    @Override
    public void removeAllNodePropertyValuesFor(Job job) {
        System.out.println("db.dao.NodePropertyValueDAOImpl.removeAllNodePropertyValuesFor()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction =null;
        String hql="delete  from NodePropertyValue npv where npv.job =:j ";
        try{
            transaction =session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("j", job);
            int result=query.executeUpdate();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

   
    
}
