/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.Job;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.SubsurfaceJob;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SubsurfaceJobDAOImpl implements SubsurfaceJobDAO{

    @Override
    public void createSubsurfaceJob(SubsurfaceJob subsurfaceJob) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.save(subsurfaceJob);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public SubsurfaceJob getSubsurfaceJob(Long id) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        
        try{
            
            SubsurfaceJob subsurfaceJob=(SubsurfaceJob) session.get(SubsurfaceJob.class, id);
            return subsurfaceJob;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateSubsurfaceJob(Long id, SubsurfaceJob nsj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteSubsurfaceJob(Long id) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SubsurfaceJob getSubsurfaceJobFor(Job job, Subsurface subsurface) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<SubsurfaceJob> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(SubsurfaceJob.class);
            criteria.add(Restrictions.eq("pk.job", job));
            criteria.add(Restrictions.eq("pk.subsurface",subsurface));
            
            result=criteria.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        if(result.size()>1){
            return null;
        }if(result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

   

   
}
