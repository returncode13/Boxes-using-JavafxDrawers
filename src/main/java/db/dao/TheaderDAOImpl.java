/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import app.properties.AppProperties;
import db.model.Job;
import db.model.Theader;
import db.model.Volume;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class TheaderDAOImpl implements TheaderDAO{

    @Override
    public void createTheader(Theader t) {
        System.out.println("db.dao.TheaderDAOImpl.createTheader()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(t);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Theader getTheader(Long id) {
       Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Theader h= (Theader) session.get(Theader.class, id);
            return h;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    
    @Override
    public void deleteTheader(Theader t) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Theader h= (Theader) session.get(Theader.class, t.getTHeaderId());
            session.delete(h);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void createBulkTheader(List<Theader> list) {
        System.out.println("db.dao.TheaderDAOImpl.createBulkTheader()");
        
        int batchsize=Math.min(list.size(), AppProperties.BULK_TRANSACTION_BATCH_SIZE);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
           for(int ii=0;ii<list.size();ii++){
               session.save(list.get(ii));
               if(ii%batchsize ==0){
                   session.flush();
                   session.clear();
               }
           }
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateTheader(Theader n) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Theader h= (Theader) session.get(Theader.class, n.getTHeaderId());
            h.setJob(n.getJob());
            h.setMd5(n.getMd5());
            h.setModified(n.getModified());
            h.setDeleted(n.getDeleted());
            h.setNumberOfRuns(n.getNumberOfRuns());
            h.setSubsurface(n.getSubsurface());
            h.setSubsurfaceJob(n.getSubsurfaceJob());
            h.setSequence(n.getSequence());
            h.setVolume(n.getVolume());
            h.setSummaryTime(n.getSummaryTime());
            h.setUpdateTime(n.getUpdateTime());
            h.setTimeStamp(n.getTimeStamp());
            h.setHistory(n.getHistory());
            h.setTextFile(n.getTextFile());
            session.update(h);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public List<Theader> getTheadersFor(Job job) {
        System.out.println("db.dao.TheaderDAOImpl.getTheadersFor()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Theader> results=null;
        String hql="Select distinct th from Theader th where th.job=:j";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("j", job);
            results=query.list();
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return results;
    }

    @Override
    public String getLatestTimeStampFor(Volume vol) {
         System.out.println("db.dao.PheaderDAOImpl.getLatestTimeStampFor()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<String> result=null;
        String sql="select MAX(timeStamp) from Theader where volume =:v";
        try{
            transaction=session.beginTransaction();
            Query query= session.createQuery(sql);
            query.setParameter("v", vol);
            
            result=query.list();
            transaction.commit();
            if(result.get(0)==null){
                return new String("0");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result.get(0);
    }
    
}
