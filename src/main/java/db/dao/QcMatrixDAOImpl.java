/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.QcMatrix;
import db.model.QcType;
import db.model.Job;
import db.model.Volume;
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
public class QcMatrixDAOImpl implements QcMatrixDAO{

    @Override
    public void createQcMatrix(QcMatrix qcmatrix) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(qcmatrix);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        } 
    }

    @Override
    public void updateQcMatrix(Long qid, QcMatrix newq) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            QcMatrix h= (QcMatrix) session.get(QcMatrix.class, qid);
        //    h.setVolume(newq.getVolume());
            h.setJob(newq.getJob());
            h.setQctype(newq.getQctype());
            h.setPresent(newq.getPresent());
            session.update(h);
            
            
          
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public QcMatrix getQcMatrix(Long qid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            QcMatrix h= (QcMatrix) session.get(QcMatrix.class, qid);
            return h;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null; 
    }

    @Override
    public void deleteQcMatrix(Long qid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            QcMatrix h= (QcMatrix) session.get(QcMatrix.class, qid);
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
    public List<QcMatrix> getQcMatrixForVolume(Volume v) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<QcMatrix> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(QcMatrix.class);
    criteria.add(Restrictions.eq("volume", v));
    criteria.add(Restrictions.eq("present", true));
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
    public List<QcMatrix> getQcMatrixForJob(Job sd) {    //return sd.getQcMatrices() ???
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<QcMatrix> result=null;
        try{
        transaction=session.beginTransaction();
        Criteria criteria=session.createCriteria(QcMatrix.class);
        criteria.add(Restrictions.eq("job", sd));
        //criteria.add(Restrictions.eq("present", true));
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
    public QcMatrix getQcMatrixFor(Job sd, QcType qctype) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<QcMatrix> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(QcMatrix.class);
            criteria.add(Restrictions.eq("job", sd));
            criteria.add(Restrictions.eq("qctype", qctype));
            result=criteria.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        if(result.size()>1) throw new Exception("More than one entries encountered for "+sd.getNameJobStep()+" : ssd id : "+sd.getId()+"    and qctype: "+qctype.getName()+" :qctypeid: "+qctype.getId() );
        if(result.isEmpty()){
            return null;
        }
        else{
            return result.get(0);
        } 
    }

    @Override
    public List<QcMatrix> getQcMatrixForJob(Job sessDetails, boolean b) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<QcMatrix> result=null;
        try{
        transaction=session.beginTransaction();
        Criteria criteria=session.createCriteria(QcMatrix.class);
        criteria.add(Restrictions.eq("job", sessDetails));
        criteria.add(Restrictions.eq("present",b));
        result=criteria.list();
        transaction.commit();
        }catch(Exception e){
        e.printStackTrace();
        }finally{
        session.close();
        }
        return result; 
    }
    
}
