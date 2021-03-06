/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;


import db.model.NodeType;
import app.connections.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class NodeTypeDAOImpl implements NodeTypeDAO{

    @Override
    public void createNodeType(NodeType n) {
        System.out.println("db.dao.NodeTypeDAOImpl.createNodeType()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(n);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public NodeType getNodeType(Long nid) {
        System.out.println("db.dao.NodeTypeDAOImpl.getNodeType()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            NodeType ll=(NodeType) session.get(NodeType.class,nid);
            return ll;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateNodeType(Long nid, NodeType newNodeType) {
        System.out.println("db.dao.NodeTypeDAOImpl.updateNodeType()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            NodeType ll=(NodeType) session.get(NodeType.class,nid);
            ll.setActualnodeid(newNodeType.getActualnodeid());
            ll.setName(newNodeType.getName());
            
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
    public void deleteNodeType(Long nid) {
        System.out.println("db.dao.NodeTypeDAOImpl.deleteNodeType()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            NodeType ll=(NodeType) session.get(NodeType.class,nid);
            session.delete(ll);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public NodeType getNodeTypeObjForType(Long type) {
        System.out.println("db.dao.NodeTypeDAOImpl.getNodeTypeObjForType()");
        Session sess = HibernateUtil.getSessionFactory().openSession();
        List<NodeType> result=null;
        Transaction transaction=null;
        try{
            transaction=sess.beginTransaction();
            Criteria criteria= sess.createCriteria(NodeType.class);
            criteria.add(Restrictions.eq("actualnodeid", type));
        
            result=criteria.list();
            transaction.commit();
           
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            sess.close();
        }
        
        if(result.size()==1){
               return result.get(0);
           }else{
               return null;
           }
    }
    
    
}
