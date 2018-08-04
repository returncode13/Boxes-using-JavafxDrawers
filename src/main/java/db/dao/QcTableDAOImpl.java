/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Header;
import db.model.QcMatrixRow;
import db.model.QcTable;
import db.model.QcType;
import db.model.Subsurface;
import db.model.Volume;
import app.connections.hibernate.HibernateUtil;
import db.model.Job;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTableDAOImpl implements QcTableDAO{

    @Override
    public void createQcTable(QcTable qcm) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(qcm);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public QcTable getQcTable(Long qid) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            QcTable h= (QcTable) session.get(QcTable.class, qid);
            return h;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateQcTable(Long qid, QcTable newQ) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            QcTable h= (QcTable) session.get(QcTable.class, qid);
           // h.setVolume(newQ.getVolume());
            h.setTime(newQ.getTime());
            /*h.setSequenceNumber(newQ.getSequenceNumber());
            h.setSubsurface(newQ.getSubsurface());*/
            //h.setHeaders(newQ.getHeaders());
            h.setSubsurface(newQ.getSubsurface());
            h.setResult(newQ.getResult());
          //  h.setQctype(newQ.getQctype());
          h.setQcMatrixRow(newQ.getQcMatrixRow());
            h.setComment(newQ.getComment());
            h.setUpdateTime(newQ.getUpdateTime());
            h.setSummaryTime(newQ.getSummaryTime());
            h.setUser(newQ.getUser());
            session.update(h);
            
            
          
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void deleteQcTable(Long qid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            QcTable h= (QcTable) session.get(QcTable.class, qid);
            session.delete(h);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
    /*
    @Override
    public List<QcTable> getQcTableFor(Volume v) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<QcTable> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(QcTable.class);
    criteria.add(Restrictions.eq("volume", v));
    result=criteria.list();
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    return result;
    }
    
    @Override
    public List<QcTable> getQcTableFor(Volume v, QcType qctype) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<QcTable> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(QcTable.class);
    criteria.add(Restrictions.eq("volume", v));
    criteria.add(Restrictions.eq("qctype", qctype));
    result=criteria.list();
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    return result;
    }*/

    @Override
    public List<QcTable> getQcTableFor(QcMatrixRow qmx) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<QcTable> result=null;
        try{
        transaction=session.beginTransaction();
        Criteria criteria=session.createCriteria(QcTable.class);
        criteria.add(Restrictions.eq("qcMatrixRow", qmx));
        result=criteria.list();
        transaction.commit();
        }catch(Exception e){
        e.printStackTrace();
        }finally{
        session.close();
        }
        return result;
    }

    /* @Override
    public List<QcTable> getQcTableFor(Header h) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<QcTable> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(QcTable.class);
    criteria.add(Restrictions.eq("headers", h));
    result=criteria.list();
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    return result;
    }*/
    /* @Override
    public QcTable getQcTableFor(QcMatrixRow qmx, Header h) throws Exception{
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<QcTable> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(QcTable.class);
    criteria.add(Restrictions.eq("qcmatrix", qmx));
    criteria.add(Restrictions.eq("headers", h));
    result=criteria.list();
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    if(result.isEmpty()){
    return null;
    }else if(result.size()>1){
    throw new Exception("More than one qcTable entries encountered for qcmatrix :  id "+qmx.getId()+" and subsurface id: "+qmx.get);
    }else{
    return result.get(0);
    }
    
    }*/
    @Override
    public List<QcTable> getQcTableFor(Subsurface s) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<QcTable> result=null;
        try{
        transaction=session.beginTransaction();
        Criteria criteria=session.createCriteria(QcTable.class);
        criteria.add(Restrictions.eq("subsurface", s));
        result=criteria.list();
        transaction.commit();
        }catch(Exception e){
        e.printStackTrace();
        }finally{
        session.close();
        }
        return result;
    }

    @Override
    public QcTable getQcTableFor(QcMatrixRow qmx, Subsurface s) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Set<QcTable> result=null;
        try{
        transaction=session.beginTransaction();
        Criteria criteria=session.createCriteria(QcTable.class);
        criteria.add(Restrictions.eq("qcMatrixRow", qmx));
        criteria.add(Restrictions.eq("subsurface", s));
        result=new LinkedHashSet<>(criteria.list());
        transaction.commit();
        }catch(Exception e){
        e.printStackTrace();
        }finally{
        session.close();
        }
        if(result.isEmpty()){
            return null;
        }else if(result.size()>1){
            System.out.println("db.dao.QcTableDAOImpl.getQcTableFor(): number of qctable entries encountered "+result.size());
            for(QcTable qct:result){
                System.err.println("id: "+qct.getId()+" sub: "+qct.getSubsurface().getId());
            }
            throw new Exception("More than one qcTable entries encountered for qcmatrix :  id "+qmx.getId()+" and subsurface id: "+s.getId()+" name: "+s.getSubsurface());
        }else{
            List<QcTable> resultSet=new ArrayList<>(result);
            return resultSet.get(0);
        }
    }

    @Override
    public QcTable getQcTableFor(Long qcmatrixRowId, Subsurface s) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<QcTable> result=null;
        try{
        transaction=session.beginTransaction();
        Criteria criteria=session.createCriteria(QcTable.class);
        criteria.add(Restrictions.eq("qcMatrixRow.id", qcmatrixRowId));
        criteria.add(Restrictions.eq("subsurface", s));
         criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        result=criteria.list();
        transaction.commit();
        }catch(Exception e){
        e.printStackTrace();
        }finally{
        session.close();
        }
        if(result.isEmpty()){
            return null;
        }else if(result.size()>1){
            throw new Exception("More than one qcTable entries encountered for qcmatrix :  id "+qcmatrixRowId+" and subsurface id: "+s.getId()+" name: "+s.getSubsurface());
        }else{
            return result.get(0);
        }
    }

    @Override
    public void deleteAllQcTablesForJob(Job job) {
        System.out.println("db.dao.QcTableDAOImpl.deleteAllQcTablesForJob()");
       Session session= HibernateUtil.getSessionFactory().openSession();
       Transaction transaction=null;
       String hqlSelect = "Select q.id from  QcTable q INNER JOIN q.qcMatrixRow qmr where qmr.job =:j";
       String hqlDelete = "Delete from QcTable q where q.id in (:ids)";
       
       try{
           transaction=session.beginTransaction();
           Query selectQuery=session.createQuery(hqlSelect);
           selectQuery.setParameter("j", job);
           List<Long> idsToDelete=selectQuery.list();
           
           
           if(idsToDelete.isEmpty()){
               transaction.commit();
               System.out.println("db.dao.QcTableDAOImpl.deleteAllQcTablesForJob(): no qctable entries found for job: "+job.getNameJobStep());
           }else{
               System.out.println("db.dao.QcTableDAOImpl.deleteAllQcTablesForJob(): deleting "+idsToDelete.size()+" qctable entries for job: "+job.getNameJobStep());
               Query deleteQuery=session.createQuery(hqlDelete);
               deleteQuery.setParameterList("ids", idsToDelete);
               int del=deleteQuery.executeUpdate();
               transaction.commit();
               
           }
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           session.close();
       }
    }

    @Override
    public void getQcTablesFor(Job j, Map<Long, Map<Subsurface, QcTable>> qcmatrixRowSubQcTableMap) {
        System.out.println("db.dao.QcTableDAOImpl.getQcTablesFor()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<QcTable> results=null;
        String hql="Select q from  QcTable q INNER JOIN q.qcMatrixRow qmr where qmr.job =:j";
        
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("j", j);
            results=query.list();
            
            transaction.commit();
            
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        for(QcTable qct:results){
            if(qcmatrixRowSubQcTableMap.containsKey(qct.getQcMatrixRow().getId())){
                qcmatrixRowSubQcTableMap.get(qct.getQcMatrixRow().getId()).put(qct.getSubsurface(), qct);
            }else{
                qcmatrixRowSubQcTableMap.put(qct.getQcMatrixRow().getId(), new HashMap<>());
                qcmatrixRowSubQcTableMap.get(qct.getQcMatrixRow().getId()).put(qct.getSubsurface(), qct);
            }
        }
    }
    
    
    
}
