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
import db.model.Workspace;
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
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateCoordinateXforJob(Job job, double xcord) {
        System.out.println("db.dao.NodePropertyValueDAOImpl.updateCoordinateXforJob()");
         
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction =null;
        String hqlSelect="Select npv.idNodePropertyValue from NodePropertyValue npv INNER JOIN npv.nodeProperty np "
                + "                                                                 INNER JOIN np.propertyType pt "
                + "                                                                 WHERE pt.name =:xx"
                + "                                                                     AND"
                + "                                                                 npv.job =:j   ";
        
        String hqlUpdate="Update NodePropertyValue npv set npv.value =:val where npv.idNodePropertyValue in (:ids)";
        try{
            transaction =session.beginTransaction();
            Query querySelect=session.createQuery(hqlSelect);
            querySelect.setParameter("j", job);
            querySelect.setParameter("xx", "x");                                //looking for the x-coordinate (x)
            List<Long> xid=querySelect.list();
            if(xid.isEmpty()){
                transaction.commit();
            }else{
                Query queryUpdate=session.createQuery(hqlUpdate);
                String valX=""+xcord;
                queryUpdate.setParameter("val", valX);
                queryUpdate.setParameterList("ids", xid);
                int upres=queryUpdate.executeUpdate();
                transaction.commit();
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
    
    @Override
    public void updateCoordinateYforJob(Job job, double ycord) {
        System.out.println("db.dao.NodePropertyValueDAOImpl.updateCoordinateXforJob()");
         
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction =null;
        String hqlSelect="Select npv.idNodePropertyValue from NodePropertyValue npv INNER JOIN npv.nodeProperty np "
                + "                                                                 INNER JOIN np.propertyType pt "
                + "                                                                 WHERE pt.name =:xx"
                + "                                                                     AND"
                + "                                                                 npv.job =:j   ";
        
        String hqlUpdate="Update NodePropertyValue npv set npv.value =:val where npv.idNodePropertyValue in (:ids)";
        try{
            transaction =session.beginTransaction();
            Query querySelect=session.createQuery(hqlSelect);
            querySelect.setParameter("j", job);
            querySelect.setParameter("xx", "y");                                //looking for the y-coordinate (y)
            List<Long> xid=querySelect.list();
            if(xid.isEmpty()){
                transaction.commit();
            }else{
                Query queryUpdate=session.createQuery(hqlUpdate);
                String valY=""+ycord;
                queryUpdate.setParameter("val", valY);
                queryUpdate.setParameterList("ids", xid);
                int upres=queryUpdate.executeUpdate();
                transaction.commit();
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public List<NodePropertyValue> getNodePropertyXYvaluesForWorkspace(Workspace workspace) {
        System.out.println("db.dao.NodePropertyValueDAOImpl.getNodePropertyValuesForWorkspace()");
        System.out.println("db.dao.NodePropertyValueDAOImpl.removeAllNodePropertyValuesFor()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction =null;
        List<NodePropertyValue> results=null;
        //String hql="select npv from NodePropertyValue npv inner join npv.job j where j.workspace =:w";
        String hql="select npv from NodePropertyValue npv            INNER JOIN npv.nodeProperty np "
                + "                                                  INNER JOIN np.propertyType pt "
                + "                                                  INNER JOIN npv.job j"
                + "                                                  WHERE (pt.name =:xx OR pt.name =:yy)"
                + "                                                         AND"
                + "                                                  j.workspace =:w"
                + "";
        try{
            transaction =session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("w", workspace);
            query.setParameter("xx", "x");
            query.setParameter("yy", "y");
            results=query.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return results;
    }

   
    
}
