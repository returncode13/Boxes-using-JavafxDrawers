/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.QcMatrixRow;
import db.model.QcType;
import db.model.Job;
import db.model.Volume;
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
public class QcMatrixRowDAOImpl implements QcMatrixRowDAO{

    @Override
    public void createQcMatrixRow(QcMatrixRow qcmatrix) {
        System.out.println("db.dao.QcMatrixRowDAOImpl.createQcMatrixRow()");
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
    public void updateQcMatrixRow(Long qid, QcMatrixRow newq) {
        System.out.println("db.dao.QcMatrixRowDAOImpl.updateQcMatrixRow()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            QcMatrixRow h= (QcMatrixRow) session.get(QcMatrixRow.class, qid);
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
    public QcMatrixRow getQcMatrixRow(Long qid) {
        System.out.println("db.dao.QcMatrixRowDAOImpl.getQcMatrixRow()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            QcMatrixRow h= (QcMatrixRow) session.get(QcMatrixRow.class, qid);
            return h;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null; 
    }

    @Override
    public void deleteQcMatrixRow(Long qid) {
        System.out.println("db.dao.QcMatrixRowDAOImpl.deleteQcMatrixRow()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            QcMatrixRow h= (QcMatrixRow) session.get(QcMatrixRow.class, qid);
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
    Criteria criteria=session.createCriteria(QcMatrixRow.class);
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
    public List<QcMatrixRow> getQcMatrixForJob(Job sd) {    //return sd.getQcMatrices() ???
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<QcMatrixRow> result=null;
        String hql="from QcMatrixRow q where q.job =:j";
        try{
        transaction=session.beginTransaction();
        /*Criteria criteria=session.createCriteria(QcMatrixRow.class);
        criteria.add(Restrictions.eq("job", sd));
        //criteria.add(Restrictions.eq("present", true));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        result=criteria.list();*/
            Query query=session.createQuery(hql);
            query.setParameter("j", sd);
            result=query.list();
        transaction.commit();
        }catch(Exception e){
        e.printStackTrace();
        }finally{
        session.close();
        }
        return result; 
    }

    @Override
    public QcMatrixRow getQcMatrixRowFor(Job sd, QcType qctype) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<QcMatrixRow> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(QcMatrixRow.class);
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
    public List<QcMatrixRow> getQcMatrixForJob(Job sessDetails, boolean b) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<QcMatrixRow> result=null;
        try{
        transaction=session.beginTransaction();
        Criteria criteria=session.createCriteria(QcMatrixRow.class);
        criteria.add(Restrictions.eq("job", sessDetails));
        criteria.add(Restrictions.eq("present",b));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        result=criteria.list();
            System.out.println("db.dao.QcMatrixRowDAOImpl.getQcMatrixForJob(): for job "+sessDetails.getNameJobStep()+" size of qcMatrix : "+result.size());
        transaction.commit();
        }catch(Exception e){
        e.printStackTrace();
        }finally{
        session.close();
        }
        return result; 
    }

    @Override
    public void updatePresent(Long id,Boolean val) {
         Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String sql=" update QcMatrixRow set present =:n where id = :id";
              
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(sql);
            query.setParameter("n", val);
            query.setParameter("id", id);
            
            int result=query.executeUpdate();
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
    
}
