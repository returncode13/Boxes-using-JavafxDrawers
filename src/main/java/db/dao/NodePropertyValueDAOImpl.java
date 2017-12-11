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
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<NodePropertyValue> result=null;
        
        try{
            transaction=session.beginTransaction();
            Criteria criteria = session.createCriteria(NodePropertyValue.class);
            criteria.add(Restrictions.eq("job", job));
            
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<NodePropertyValue> result=null;
        
        try{
            transaction=session.beginTransaction();
            Criteria criteria = session.createCriteria(NodePropertyValue.class);
            criteria.add(Restrictions.eq("job", jobStep));
            criteria.add(Restrictions.eq("nodeProperty", nodeProperty));
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

   
    
}
