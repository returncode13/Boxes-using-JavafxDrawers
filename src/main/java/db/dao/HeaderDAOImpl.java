/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import app.properties.AppProperties;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import db.model.Header;
import db.model.Job;
import db.model.Log;
import db.model.Subsurface;
import db.model.Volume;
import db.model.Workspace;
//import fend.session.node.headers.SubSurfaceHeaders;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class HeaderDAOImpl implements HeaderDAO{

    
    
    
    @Override
    public void createHeader(Header h) {
               System.out.println("db.dao.HeaderDAOImpl.createHeader()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(h);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }
    
    @Override
    public void createBulkHeaders(List<Header> headers) {
        System.out.println("db.dao.HeaderDAOImpl.createBulkHeaders()");
        int batchsize=Math.min(headers.size(), AppProperties.BULK_TRANSACTION_BATCH_SIZE);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
           for(int ii=0;ii<headers.size();ii++){
               session.saveOrUpdate(headers.get(ii));
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
    public Header getHeader(Long hid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Header h= (Header) session.get(Header.class, hid);
            return h;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateHeader(Long hid, Header newH) {
        System.out.println("db.dao.HeaderDAOImpl.updateHeader()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Header h= (Header) session.get(Header.class, hid);
            
          //  if(h.getSequence().getSequenceno().equals(newH.getSequence().getSequenceno()) && h.getSubsurface().getSubsurface().equals(newH.getSubsurface().getSubsurface())){
            h.setTimeStamp(newH.getTimeStamp());
            h.setCmpInc(newH.getCmpInc());
            h.setCmpMax(newH.getCmpMax());
            h.setCmpMin(newH.getCmpMin());
            
            h.setDugChannelInc(newH.getDugChannelInc());
            h.setDugChannelMax(newH.getDugChannelMax());
            h.setDugChannelMin(newH.getDugChannelMin());
            
            h.setDugShotInc(newH.getDugShotInc());
            h.setDugShotMax(newH.getDugShotMax());
            h.setDugShotMin(newH.getDugShotMin());
            
            h.setInlineInc(newH.getInlineInc());
            h.setInlineMax(newH.getInlineMax());
            h.setInlineMin(newH.getInlineMin());
            
            h.setOffsetInc(newH.getOffsetInc());
            h.setOffsetMax(newH.getOffsetMax());
            h.setOffsetMin(newH.getOffsetMin());
            
            h.setTraceCount(newH.getTraceCount());
            //h.setNumberOfRuns(newH.getNumberOfRuns());
            h.setModified(newH.getModified());
            //h.setWorkflowVersion(newH.getWorkflowVersion());
            h.setUpdateTime(newH.getUpdateTime());
            h.setSummaryTime(newH.getSummaryTime());
            h.setTextfilepath(newH.getTextfilepath());
            h.setMultipleInstances(newH.getMultipleInstances());
            h.setChosen(newH.getChosen());
            //h.setLogs(newH.getLogs());
            /*if(newH.getModified()){
            h.setModified(Boolean.FALSE);
            }*/
            session.update(h);
          //      System.out.println("db.dao.HeaderDAOImpl: updating entry for subsurface : "+newH.getSubsurface().getSubsurface()+" with id: "+newH.getIdHeaders() );
           // }
           /*else{
           throw new Exception("db.dao.HeaderDAOImpl: The id belongs to a different seq/subsurface compared to the ones that the new header value refers to!!. ");
           }*/
          
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public void deleteHeader(Long hid) {
       Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Header h= (Header) session.get(Header.class, hid);
            session.delete(h);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public List<Header> getHeadersFor(Volume v) {
        System.out.println("db.dao.HeaderDAOImpl.getHeadersFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Header> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Header.class);
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
    public List<Header> getHeadersFor(Job job) {
        System.out.println("db.dao.HeaderDAOImpl.getHeadersFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Header> result=null;
        String hql="from Header h where h.job =:j";
        try{
            transaction=session.beginTransaction();
            /*Criteria criteria=session.createCriteria(Header.class);
            criteria.add(Restrictions.eq("job", job));
            result=criteria.list();*/
                Query query=session.createQuery(hql);
                query.setParameter("j", job);
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
    public void deleteHeadersFor(Volume v) {
        System.out.println("db.dao.HeaderDAOImpl.deleteHeadersFor()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        
        String hqlSelect="Select h.headerId from Header h where h.volume =:v";
        String hqlDelete="Delete from Header h where h.headerId in (:ids)";
        try{
            transaction=session.beginTransaction();
            Query selectQuery= session.createQuery(hqlSelect);
            selectQuery.setParameter("v", v);
            List<Long> idsToDelete=selectQuery.list();
            
            if(!idsToDelete.isEmpty()){
                Query delQuery= session.createQuery(hqlDelete);
                delQuery.setParameterList("ids", idsToDelete);
                System.out.println("db.dao.HeaderDAOImpl.deleteHeadersFor(): deleting "+idsToDelete.size()+" headers belonging to volume: "+v.getNameVolume()+" ("+v.getId()+")");
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
    public void deleteHeadersFor(Job job) {
         System.out.println("db.dao.HeaderDAOImpl.deleteHeadersFor()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        
        String hqlSelect="Select h.headerId from Header h where h.job =:j";
        String hqlDelete="Delete from Header h where h.headerId in (:ids)";
        try{
            transaction=session.beginTransaction();
            Query selectQuery= session.createQuery(hqlSelect);
            selectQuery.setParameter("j", job);
            List<Long> idsToDelete=selectQuery.list();
            
            if(!idsToDelete.isEmpty()){
                Query delQuery= session.createQuery(hqlDelete);
                delQuery.setParameterList("ids", idsToDelete);
                System.out.println("db.dao.HeaderDAOImpl.deleteHeadersFor(job): deleting "+idsToDelete.size()+" headers belonging to job: "+job.getNameJobStep()+" ("+job.getId()+")");
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
    public List<Subsurface> getSubsurfacesToBeSummarized(Volume v) {
         System.out.println("db.dao.HeaderDAOImpl.getSubsurfacesToBeSummarized()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Header> result=null;
        List<Subsurface> subsurfacesThatNeedToBeSummarized=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Header.class);
            criteria.add(Restrictions.eq("volume", v));
            criteria.add(Restrictions.gtProperty("summaryTime", "updateTime"));
            result=criteria.list();
            transaction.commit();
            
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        if(result.size()>0){
            for (Iterator<Header> iterator = result.iterator(); iterator.hasNext();) {
                Header hdr = iterator.next();
                subsurfacesThatNeedToBeSummarized.add(hdr.getSubsurface());
                
            }
            return subsurfacesThatNeedToBeSummarized;
        }
        else {
            return null;
        }
    }
   
  
    @Override
    public Header getHeadersFor(Volume dbvol, Subsurface dbsub, String timestamp) {
        System.out.println("db.dao.HeaderDAOImpl.getHeadersFor()");
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = null;
            List<Header> result=null;
            try{
                transaction=session.beginTransaction();
                Criteria criteria=session.createCriteria(Header.class);
                criteria.add(Restrictions.eq("volume", dbvol));
                criteria.add(Restrictions.eq("subsurface", dbsub));
                criteria.add(Restrictions.eq("timeStamp",timestamp));
                
                result=criteria.list();
                transaction.commit();
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                session.close();
            }
            if(result.isEmpty())
            {
                return null;
            }else{
                return result.get(0);
            }
            
    }

    @Override
    public Set<Header> getMultipleInstances(Job job, Subsurface sub) {
        System.out.println("db.dao.HeaderDAOImpl.getMultipleInstances()");
        Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = null;
            Set<Header> result=null;
            try{
                transaction=session.beginTransaction();
                Criteria criteria=session.createCriteria(Header.class);
                criteria.createAlias("subsurfaceJob", "sj");
                criteria.add(Restrictions.eq("sj.pk.job", job));
                criteria.add(Restrictions.eq("sj.pk.subsurface", sub));
                //Criterion rest1=Restrictions.eq("job_id", job);
               // Criterion rest2=Restrictions.eq("id", sub);
                
                //criteria.add(Restrictions.and(rest1,rest2));
               
                
             //   Query selectQuery=session.createQuery("from obpmanager.subsurface_job where job_id = :jobid and id =:subid");
//                "select * from obpmanager.header INNER JOIN obpmanager.job on obpmanager.header.job_fk=obpmanager.job.job_id INNER JOIN public.subsurface on obpmanager.header.subsurface_fk=public.subsurface.id AND chosen=false WHERE public.subsurface.id=16062 AND obpmanager.job.job_id=91;"
/*  Query selectQuery=session.createQuery("from Header INNER JOIN Job on Header.job.id=Job.id INNER JOIN Subsurface on Header.subsurface.id=Subsurface.id  WHERE Subsurface.id =:subid AND Job.id =:jobid");
selectQuery.setParameter("jobid", job);
selectQuery.setParameter("subid", sub);
*/
                result=new LinkedHashSet(criteria.list());
                transaction.commit();
                
                if(result.size()>1){
                    System.out.println("db.dao.HeaderDAOImpl.getMultipleInstances(): result.size() for job "+job.getId()+" sub: "+sub.getId()+ " result.size(): "+result.size());
                for(Header h:result){
                    transaction=null;
                    
                    System.out.println("db.dao.HeaderDAOImpl.getMultipleInstances(): updating header "+h.getHeaderId()+" subsurface ID: "+h.getSubsurface().getId()+" job: "+h.getJob().getId());
                    h.setMultipleInstances(true);
                    h.setChosen(false);
                    transaction=session.beginTransaction();
                    
                    session.update(h);
                    transaction.commit();
                }
            }
                
                
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                session.close();
            }
            
            if(result.isEmpty())
            {
                return null;
            }else{
                return result;  //should be of size 1
            }
    }

    @Override
    public Header getChosenHeaderFor(Job job, Subsurface sub) throws Exception{
        System.out.println("db.dao.HeaderDAOImpl.getChosenHeaderFor()");
         Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = null;
            System.out.println("db.dao.HeaderDAOImpl.getChosenHeaderFor() started ");
            Set<Header> result=null;
            try{
                transaction=session.beginTransaction();
                Criteria criteria=session.createCriteria(Header.class);
                /*criteria.add(Restrictions.eq("job.id", job.getId()));
                criteria.add(Restrictions.eq("subsurface.id", sub.getId()));*/
                //criteria.add(Restrictions.eq("chosen", true));
                 Criterion rest1=Restrictions.eq("job", job);
                Criterion rest2=Restrictions.eq("subsurface", sub);
                Criterion rest3=Restrictions.eq("chosen", true);
                criteria.add(Restrictions.and(rest1,rest2,rest3));
                
                
                
                result=new LinkedHashSet(criteria.list());
                transaction.commit();
                }catch(Exception e){
                e.printStackTrace();
            }finally{
                session.close();
            }
            System.out.println("db.dao.HeaderDAOImpl.getChosenHeaderFor() returnin ");
            if(result.isEmpty())
            {
                return null;
            }
            else if(result.size()>1){
                throw new Exception("More than one chosen header found!!. Please choose a single header for "+job.getNameJobStep()+" : sub: "+sub.getSubsurface());
            }
            else if(result.size()==1)
            {
                return new ArrayList<>(result).get(0);
            }
            else{
                return null;
            }
    }

    @Override
    public String getLatestTimeStampFor(Volume volume) {
        System.out.println("db.dao.HeaderDAOImpl.getLatestTimeStampFor()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<String> result=null;
        String sql="select MAX(timeStamp) from Header where volume =:v";
        try{
            transaction=session.beginTransaction();
            Query query= session.createQuery(sql);
            query.setParameter("v", volume);
            
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
    public List<Header> getChosenHeadersForWorkspace(Workspace W) {
        System.out.println("db.dao.HeaderDAOImpl.getChosenHeadersForWorkspace()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Header> result=null;
        String hql="Select h from Header h INNER JOIN h.job j where j.workspace =:wrk and h.chosen=true ";
        try{
            transaction=session.beginTransaction();
            Query query= session.createQuery(hql);
            query.setParameter("wrk", W);
            
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
    public void checkForMultipleSubsurfacesInHeadersForJob(Job job) {
        System.out.println("db.dao.HeaderDAOImpl.checkForMultipleSubsurfacesInHeadersForJob()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        
       
        String hqlDetermineCommonSubs="Select h.subsurface.id from Header h    where h.job =:j "
                + "                                                         group by h.subsurface.id "
                + "                                                         having count(*) > 1";
        String hqlSelectIds="Select h.headerId from Header h where h.subsurface.id in (:subs) and h.job =:j";
        String hqlUpdateChooseFalse="update Header h Set h.chosen = false,h.multipleInstances = true where h.headerId in (:ids)";
        String hqlUpdateChooseTrue="update Header h Set h.chosen = true,h.multipleInstances = false where h.job =:j";
        try{
            transaction=session.beginTransaction();
            Query subsurfaceQuery= session.createQuery(hqlDetermineCommonSubs);
            subsurfaceQuery.setParameter("j", job );
            List<Long> subIds=subsurfaceQuery.list();
                
            if(subIds.isEmpty()){
                System.out.println("db.dao.HeaderDAOImpl.checkForMultipleSubsurfacesInHeadersForJob(): no subs repeated for job: "+job.getNameJobStep());
                Query chooseTrueQuery=session.createQuery(hqlUpdateChooseTrue);
                chooseTrueQuery.setParameter("j", job);
                int qes=chooseTrueQuery.executeUpdate();
                transaction.commit();
            }else{
                System.out.println("db.dao.HeaderDAOImpl.checkForMultipleSubsurfacesInHeadersForJob(): Found "+subIds.size()+" repeated subs for job: "+job.getNameJobStep());
                    Query selectHeaderIds=session.createQuery(hqlSelectIds);
                    selectHeaderIds.setParameterList("subs", subIds);
                    selectHeaderIds.setParameter("j",job);
                    List<Long> repeatedHeaderIds=selectHeaderIds.list();
                    
                        if(repeatedHeaderIds.isEmpty()){
                            System.out.println("db.dao.HeaderDAOImpl.checkForMultipleSubsurfacesInHeadersForJob(): no headers found for job: "+job.getNameJobStep());
                            transaction.commit();
                        }else{
                            
                            System.out.println("db.dao.HeaderDAOImpl.checkForMultipleSubsurfacesInHeadersForJob(): updating the chosen headers for "+repeatedHeaderIds.size()+" headers for job: "+job.getNameJobStep()+"\n"
                                    + "You will need to select the correct subsurface.\n"
                                    + "See the header table for this job ");
                            Query updateQuery=session.createQuery(hqlUpdateChooseFalse);
                            updateQuery.setParameterList("ids",repeatedHeaderIds);
                            int result=updateQuery.executeUpdate();
                            transaction.commit();
                        }
                    
                
            }
            
            
           
            
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void setChosenToFalseForConflictingSubs(Subsurface conflictedSub, Job job, Volume volumeToBeExcluded) {
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        
        String hqlSelect="Select h.headerId from Header h where  h.subsurface =:s and h.job =:j and h.volume !=:v ";
        String hqlUpdate="update Header h set h.chosen=false,h.multipleInstances=true where h.headerId in (:ids)";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hqlSelect);
            query.setParameter("j", job);
            query.setParameter("s", conflictedSub);
            query.setParameter("v", volumeToBeExcluded);
            List<Long> idsToUpdate=query.list();
            
                if(idsToUpdate.isEmpty()){
                    System.out.println("db.dao.HeaderDAOImpl.setChosenToFalseForConflictingSubs(): no headers found to update. ");
                    transaction.commit();
                }else{
                    System.out.println("db.dao.HeaderDAOImpl.setChosenToFalseForConflictingSubs(): updating "+idsToUpdate.size()+" conflicting headers");
                    for(Long l: idsToUpdate){
                        System.out.println("db.dao.HeaderDAOImpl.setChosenToFalseForConflictingSubs(): id to update: "+l);
                    }
                    Query updateQ=session.createQuery(hqlUpdate);
                    updateQ.setParameterList("ids", idsToUpdate);
                    int res=updateQ.executeUpdate();
                    transaction.commit();
                            
                }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateDeleteFlagsFor(Volume vol, List<String> subsurfacesOnDisk) {
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String hqlSelectSubsNotInDiskVolume="select s from Subsurface s where s.subsurface not in (:names)";
        String hqlSelectHeadersThatContainDeletedSubs="select h.headerId from Header h where h.volume =:v and h.subsurface in (:subs)";
        String hqlUpdateDeleteFalse="update Header h set h.deleted=false where h.volume =:v";
        String hqlUpdateDeleteTrue="update Header h set h.deleted=true where h.headerId in (:ids)";
        try{
            transaction =session.beginTransaction();
            Query selectSubsNotInDisk=session.createQuery(hqlSelectSubsNotInDiskVolume);
            selectSubsNotInDisk.setParameterList("names", subsurfacesOnDisk);
            List<Subsurface> deletedSubs=selectSubsNotInDisk.list();
            
            if(deletedSubs.isEmpty()){
                System.out.println("db.dao.HeaderDAOImpl.updateDeleteFlagsFor(): all subs in disk volume : "+vol.getPathOfVolume()+" are present in the database volume : "+vol.getNameVolume()+" ("+vol.getId()+")");
                /*Query delFalse=session.createQuery(hqlUpdateDeleteFalse);
                delFalse.setParameter("v", vol);
                int delF=delFalse.executeUpdate();*/
                transaction.commit();
            }else{
                 System.out.println("db.dao.HeaderDAOImpl.updateDeleteFlagsFor(): "+deletedSubs.size()+" in disk volume : "+vol.getPathOfVolume()+" are ABSENT in the database volume : "+vol.getNameVolume()+" ("+vol.getId()+")");
                 Query selectHeaders=session.createQuery(hqlSelectHeadersThatContainDeletedSubs);
                 selectHeaders.setParameter("v", vol);
                 selectHeaders.setParameterList("subs", deletedSubs);
                 List<Long> headersToUdpate=selectHeaders.list();
                 
                    if(headersToUdpate.isEmpty()){
                        System.out.println("db.dao.HeaderDAOImpl.updateDeleteFlagsFor(): no previously existing subsurfaces were deleted");
                        Query delFalse=session.createQuery(hqlUpdateDeleteFalse);
                        delFalse.setParameter("v", vol);
                        int delF=delFalse.executeUpdate();
                        transaction.commit();
                    }else{
                        System.out.println("db.dao.HeaderDAOImpl.updateDeleteFlagsFor(): set delete=true on "+headersToUdpate.size()+" headers for vol: "+vol.getNameVolume()+" ("+vol.getId()+")");
                        Query delTrue=session.createQuery(hqlUpdateDeleteTrue);
                        delTrue.setParameterList("ids", headersToUdpate);
                        int delT=delTrue.executeUpdate();
                        transaction.commit();
                    }
                 
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateRunInsightWorkflowVariables(Job j, Volume v) {
        System.out.println("db.dao.HeaderDAOImpl.updateRunInsightWorkflowVariables()");
       Session session=HibernateUtil.getSessionFactory().openSession();
       Transaction transaction=null;
       List<Header> headersToBeUpdated=new ArrayList<>();
       String hqlLatestLogs="Select l from Log l where l.job=:jb and l.volume=:v and l.isMaxVersion=true";
       try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hqlLatestLogs);
            query.setParameter("jb", j);
            query.setParameter("v", v);
            
            List<Log> latestLogs=query.list();
            transaction.commit();
            
            for(Log l:latestLogs){
                Header h=l.getHeader();
                if(h!=null){
                    h.setNumberOfRuns(l.getVersion()+1);
                    h.setInsightVersion(l.getInsightVersion());
                    h.setWorkflowVersion(l.getWorkflow().getWfversion());
                    headersToBeUpdated.add(h);
                }
               
            }
            
            System.out.println("db.dao.HeaderDAOImpl.updateRunInsightWorkflowVariables(): will update "+headersToBeUpdated.size());
            
            
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           session.close();
       }
       
       updateBulkHeaders(headersToBeUpdated);
    }

    private void updateBulkHeaders(List<Header> headersToBeUpdated) {
          if(headersToBeUpdated.isEmpty()) {
            System.out.println("db.dao.HeaderDAOImpl.updateBulkHeaders(): No headers to be updated!");
            return;
        }
         System.out.println("db.dao.HeaderDAOImpl.updateBulkHeaders():");
          int batchsize=Math.min(headersToBeUpdated.size(), AppProperties.BULK_TRANSACTION_BATCH_SIZE);
         Session session = HibernateUtil.getSessionFactory().openSession();
         Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            for(int ii=0;ii<headersToBeUpdated.size();ii++){
                session.update(headersToBeUpdated.get(ii));
                if(ii%batchsize ==0 ){
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

    

    
    
}
