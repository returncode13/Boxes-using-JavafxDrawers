/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Header;
import db.model.Log;
import db.model.Volume;
import db.model.Workflow;
import app.connections.hibernate.HibernateUtil;
import db.model.Job;
import db.model.Pheader;
import db.model.Subsurface;
import db.model.Workspace;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author sharath nair  
 * sharath.nair@polarcus.com
 */
public class LogDAOImpl implements LogDAO{

    @Override
    public void createLogs(Log l) {
        System.out.println("db.dao.LogDAOImpl.createLogs()");
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(l);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public Log getLogs(Long lid) {
        System.out.println("db.dao.LogDAOImpl.getLogs()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Log l= (Log) session.get(Log.class, lid);
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateLogs(Long lid, Log newL) {
        System.out.println("db.dao.LogDAOImpl.updateLogs()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Log ll=(Log) session.get(Log.class,lid);
            ll.setHeader(newL.getHeader());
            //ll.setSubsurfaces(newL.getSubsurfaces());
            ll.setSubsurface(newL.getSubsurface());
            ll.setInsightVersion(newL.getInsightVersion());
            ll.setLogpath(newL.getLogpath());
            ll.setTimestamp(newL.getTimestamp());
            ll.setVolume(newL.getVolume());
            ll.setCompletedsuccessfully(newL.getCompletedsuccessfully());
            ll.setErrored(newL.getErrored());
            ll.setRunning(newL.getRunning());
            ll.setCancelled(newL.getCancelled());
            ll.setWorkflow(newL.getWorkflow());
            ll.setUpdateTime(newL.getUpdateTime());
            ll.setSummaryTime(newL.getSummaryTime());
            ll.setJob(newL.getJob());
            ll.setVersion(newL.getVersion());
            session.update(ll);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public void deleteLogs(Long lid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Log h= (Log) session.get(Log.class, lid);
            session.delete(h);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public List<Log> getLogsFor(Header h) {
        System.out.println("db.dao.LogDAOImpl.getLogsFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Log> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Log.class);
            criteria.add(Restrictions.eq("header", h));
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
    public List<Log> getLogsFor(Volume v) {
        System.out.println("db.dao.LogDAOImpl.getLogsFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Log> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Log.class);
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
    public List<Log> getLogsFor(Volume v, Subsurface subline) {
        System.out.println("db.dao.LogDAOImpl.getLogsFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Log> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Log.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.add(Restrictions.eq("subsurface", subline));
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
    public Log getLatestLogFor(Volume v, Subsurface subline) {
        System.out.println("db.dao.LogDAOImpl.getLatestLogFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Log> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Log.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.add(Restrictions.eq("subsurface", subline));
            criteria.addOrder(Order.desc("timestamp"));
            result=criteria.list();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        if(result.isEmpty())return null;
        else{
        return result.get(0);
        }
    }

    @Override
    public List<Log> getLogsFor(Volume v, Boolean completed, Boolean running, Boolean errored, Boolean cancelled) {
        System.out.println("db.dao.LogDAOImpl.getLogsFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Log> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Log.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.add(Restrictions.eq("completedsuccessfully", completed));
            criteria.add(Restrictions.eq("running", running));
            criteria.add(Restrictions.eq("errored", errored));
            criteria.add(Restrictions.eq("cancelled", cancelled));
            
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
    public List<Log> getLogsFor(Volume v, Subsurface subline, Boolean completed, Boolean running, Boolean errored, Boolean cancelled) {
        System.out.println("db.dao.LogDAOImpl.getLogsFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Log> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Log.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.add(Restrictions.eq("subsurface", subline));
            criteria.add(Restrictions.eq("completedsuccessfully", completed));
            criteria.add(Restrictions.eq("running", running));
            criteria.add(Restrictions.eq("errored", errored));
            criteria.add(Restrictions.eq("cancelled", cancelled));
            
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
    public List<Log> getLogsFor(Volume v, Workflow workflow) {
        System.out.println("db.dao.LogDAOImpl.getLogsFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Log> result=null;
        List<Workflow> reswf=null;
        try{
            transaction=session.beginTransaction();
           
                Criteria criteria=session.createCriteria(Log.class);
            criteria.add(Restrictions.eq("volume", v));
            //criteria.add(Restrictions.eq("workflow", workflow));
            criteria.add(Restrictions.or(Restrictions.isNull("workflow"),Restrictions.eq("workflow", workflow)));
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
    public List<Log> getLogsFor(Volume v, Long seq) {
        System.out.println("db.dao.LogDAOImpl.getLogsFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Log> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Log.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.add(Restrictions.eq("sequence", seq));
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
    public List<Log> getSequencesFor(Volume v) {
        System.out.println("db.dao.LogDAOImpl.getSequencesFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Log> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Log.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("sequence"),"sequence")));
            criteria.setResultTransformer(Transformers.aliasToBean(Log.class));
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
    public List<Log> getSubsurfacesFor(Volume v, Long seq) {
        System.out.println("db.dao.LogDAOImpl.getSubsurfacesFor()");
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Log> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Log.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.add(Restrictions.eq("sequence", seq));
            criteria.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("subsurfaces"),"subsurfaces")));
            criteria.setResultTransformer(Transformers.aliasToBean(Log.class));
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
    public Log getLogsFor(Volume volume, Subsurface linename, String timestamp, String filename) throws Exception{
        System.out.println("db.dao.LogDAOImpl.getLogsFor()");
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Log> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Log.class);
            criteria.add(Restrictions.eq("volume", volume));
            criteria.add(Restrictions.eq("subsurface", linename));
            criteria.add(Restrictions.eq("logpath", filename));
            criteria.add(Restrictions.eq("timestamp", timestamp));
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
            throw new Exception("More than one results encountered for log: "+filename+" timestamp: "+timestamp+" volume: "+volume.getId()+" line: "+linename);
        }else{
            return result.get(0);
        }
            
        
    }

    @Override
    public List<Log> getLogsFor(Job dbJob) {
        System.out.println("db.dao.LogDAOImpl.getLogsFor()");
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Log> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Log.class);
            criteria.add(Restrictions.eq("job", dbJob));
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
    public List<Log> getLogsByTimeFor(Job dbJob) {
        System.out.println("db.dao.LogDAOImpl.getLogsByTimeFor()");
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Log> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Log.class);
            criteria.add(Restrictions.eq("job", dbJob));
            criteria.addOrder(Order.asc("timestamp"));
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
    public List<Log> getLogsByTimeFor(Job dbJob, Subsurface sub) {
        System.out.println("db.dao.LogDAOImpl.getLogsByTimeFor()");
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Log> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Log.class);
            criteria.add(Restrictions.eq("job", dbJob));
            criteria.add(Restrictions.eq("subsurface", sub));
            criteria.addOrder(Order.asc("timestamp"));
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
    public void bulkUpdateOnLogs(Volume v, Workflow w) {
        System.out.println("db.dao.LogDAOImpl.bulkUpdateOnLogs()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        int result=13;
        String sql="update Log set workflow = :wk where volume =:v and workflow is null ";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(sql);
            query.setParameter("v", v);
            query.setParameter("wk", w);
            result=query.executeUpdate();
            
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
                
    }

    @Override
    public void bulkUpdateOnLogs(Volume volume, Header hdr,Subsurface sub) {
        System.out.println("db.dao.LogDAOImpl.bulkUpdateOnLogs()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        int result=13;
        String sql="update Log set header = :hd where volume =:v and subsurface =:sub and header is null ";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(sql);
            query.setParameter("v", volume);
            query.setParameter("hd", hdr);
            query.setParameter("sub", sub);
            result=query.executeUpdate();
            
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public String getLatestLogTimeFor(Volume dbVol) {
        System.out.println("db.dao.LogDAOImpl.getLatestLogTimeFor()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<String> result=null;
        String sql="select MAX(timestamp) from Log where volume = :v";
        try{
            transaction=session.beginTransaction();
            Query query= session.createQuery(sql);
            query.setParameter("v", dbVol);
            
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

    @Override
    public void deleteLogsFor(Volume vol) {
        System.out.println("db.dao.LogDAOImpl.deleteLogsFor()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        
        String hqlSelect="Select l.id from Log l where l.volume =:v";
        String hqlDelete="Delete from Log l where l.idLogs in (:ids)";
        try{
            transaction=session.beginTransaction();
            Query selectQuery= session.createQuery(hqlSelect);
            selectQuery.setParameter("v", vol);
            List<Long> idsToDelete=selectQuery.list();
            
            if(!idsToDelete.isEmpty()){
                Query delQuery= session.createQuery(hqlDelete);
                delQuery.setParameterList("ids", idsToDelete);
                System.out.println("db.dao.LogDAOImpl.deleteLogsFor(): deleting "+idsToDelete.size()+" logs belonging to volume: "+vol.getNameVolume()+" ("+vol.getId()+")");
                int result=delQuery.executeUpdate();
            }
            
            
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public List<Log> getLogsWithInputVolumes(Workspace workspace) {
        System.out.println("db.dao.LogDAOImpl.getLogsWithInputVolumes()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Log> result=null;
        String hql="Select l from Log l where (l.job,l.subsurface,l.version) in "
                + "("
                + " Select ll.job,ll.subsurface,max(ll.version) from Log ll Inner join ll.job j"
                + " where j.workspace =:w "
                + " group by ll.job,ll.subsurface"
                + ")";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("w", workspace);
            result=query.list();
            System.out.println("db.dao.LogDAOImpl.getLogsWithInputVolumes(): retrieved  "+result.size()+" logs for the workspace: "+workspace.getName() );
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
       return result;
    }

    @Override
    public void bulkUpdateOnLogs(Volume volume, Pheader phdr, Subsurface sub) {
    System.out.println("db.dao.LogDAOImpl.bulkUpdateOnLogs()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        int result=13;
        String sql="update Log set pheader = :hd where volume =:v and subsurface =:sub and pheader is null ";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(sql);
            query.setParameter("v", volume);
            query.setParameter("hd", phdr);
            query.setParameter("sub", sub);
            result=query.executeUpdate();
            
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

   
    

    
    
}
