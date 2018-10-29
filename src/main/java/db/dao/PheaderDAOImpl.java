/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import app.properties.AppProperties;
import db.model.Job;
import db.model.Pheader;
import db.model.Subsurface;
import db.model.Volume;
import db.model.Workspace;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class PheaderDAOImpl implements PheaderDAO{
    
    @Override
    public void createHeader(Pheader h) {
               System.out.println("db.dao.PheaderDAOImpl.createHeader()");
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
    public void createBulkHeaders(List<Pheader> headers) {
        System.out.println("db.dao.PheaderDAOImpl.createBulkHeaders()");
        int batchsize=Math.min(headers.size(), AppProperties.BULK_TRANSACTION_BATCH_SIZE);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
           for(int ii=0;ii<headers.size();ii++){
               session.save(headers.get(ii));
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
    public Pheader getHeader(Long hid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Pheader h= (Pheader) session.get(Pheader.class, hid);
            return h;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateHeader(Long hid, Pheader newH) {
        System.out.println("db.dao.PheaderDAOImpl.updateHeader()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Pheader h= (Pheader) session.get(Pheader.class, hid);
            
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
            h.setNumberOfRuns(newH.getNumberOfRuns());
            h.setModified(newH.getModified());
            h.setWorkflowVersion(newH.getWorkflowVersion());
            h.setUpdateTime(newH.getUpdateTime());
            h.setSummaryTime(newH.getSummaryTime());
            h.setTextfilepath(newH.getTextfilepath());
            h.setMultipleInstances(newH.getModified());
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
            Pheader h= (Pheader) session.get(Pheader.class, hid);
            session.delete(h);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public List<Pheader> getHeadersFor(Volume v) {
        System.out.println("db.dao.PheaderDAOImpl.getHeadersFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Pheader> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Pheader.class);
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
    public List<Pheader> getHeadersFor(Job job) {
        System.out.println("db.dao.PheaderDAOImpl.getHeadersFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Pheader> result=null;
        String hql="from Pheader h where h.job =:j";
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
        System.out.println("db.dao.PheaderDAOImpl.deleteHeadersFor()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        
        String hqlSelect="Select h.pHeaderId from Pheader h where h.volume =:v";
        String hqlDelete="Delete from Pheader h where h.pHeaderId in (:ids)";
        try{
            transaction=session.beginTransaction();
            Query selectQuery= session.createQuery(hqlSelect);
            selectQuery.setParameter("v", v);
            List<Long> idsToDelete=selectQuery.list();
            
            if(!idsToDelete.isEmpty()){
                Query delQuery= session.createQuery(hqlDelete);
                delQuery.setParameterList("ids", idsToDelete);
                System.out.println("db.dao.PheaderDAOImpl.deleteHeadersFor(): deleting "+idsToDelete.size()+" headers belonging to volume: "+v.getNameVolume()+" ("+v.getId()+")");
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
          System.out.println("db.dao.PheaderDAOImpl.deleteHeadersFor()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        
        String hqlSelect="Select h.pHeaderId from Pheader h where h.job =:j";
        String hqlDelete="Delete from Pheader h where h.pHeaderId in (:ids)";
        try{
            transaction=session.beginTransaction();
            Query selectQuery= session.createQuery(hqlSelect);
            selectQuery.setParameter("j", job);
            List<Long> idsToDelete=selectQuery.list();
            
            if(!idsToDelete.isEmpty()){
                Query delQuery= session.createQuery(hqlDelete);
                delQuery.setParameterList("ids", idsToDelete);
                System.out.println("db.dao.PheaderDAOImpl.deleteHeadersFor(job): deleting "+idsToDelete.size()+" p-headers belonging to job: "+job.getNameJobStep()+" ("+job.getId()+")");
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
         System.out.println("db.dao.PheaderDAOImpl.getSubsurfacesToBeSummarized()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Pheader> result=null;
        List<Subsurface> subsurfacesThatNeedToBeSummarized=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Pheader.class);
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
            for (Iterator<Pheader> iterator = result.iterator(); iterator.hasNext();) {
                Pheader hdr = iterator.next();
                subsurfacesThatNeedToBeSummarized.add(hdr.getSubsurface());
                
            }
            return subsurfacesThatNeedToBeSummarized;
        }
        else {
            return null;
        }
    }
   
  
    @Override
    public Pheader getHeadersFor(Volume dbvol, Subsurface dbsub, String timestamp) {
        System.out.println("db.dao.PheaderDAOImpl.getHeadersFor()");
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = null;
            List<Pheader> result=null;
            try{
                transaction=session.beginTransaction();
                Criteria criteria=session.createCriteria(Pheader.class);
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
    public Set<Pheader> getMultipleInstances(Job job, Subsurface sub) {
        System.out.println("db.dao.PheaderDAOImpl.getMultipleInstances()");
        Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = null;
            Set<Pheader> result=null;
            try{
                transaction=session.beginTransaction();
                Criteria criteria=session.createCriteria(Pheader.class);
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
                    System.out.println("db.dao.PheaderDAOImpl.getMultipleInstances(): result.size() for job "+job.getId()+" sub: "+sub.getId()+ " result.size(): "+result.size());
                for(Pheader h:result){
                    transaction=null;
                    
                    System.out.println("db.dao.PheaderDAOImpl.getMultipleInstances(): updating header "+h.getpHeaderId()+" subsurface ID: "+h.getSubsurface().getId()+" job: "+h.getJob().getId());
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
    public Pheader getChosenHeaderFor(Job job, Subsurface sub) throws Exception{
        System.out.println("db.dao.PheaderDAOImpl.getChosenHeaderFor()");
         Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = null;
            System.out.println("db.dao.PheaderDAOImpl.getChosenHeaderFor() started ");
            Set<Pheader> result=null;
            try{
                transaction=session.beginTransaction();
                Criteria criteria=session.createCriteria(Pheader.class);
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
            System.out.println("db.dao.PheaderDAOImpl.getChosenHeaderFor() returnin ");
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
        System.out.println("db.dao.PheaderDAOImpl.getLatestTimeStampFor()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<String> result=null;
        String sql="select MAX(timeStamp) from Pheader where volume =:v";
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
    public List<Pheader> getChosenHeadersForWorkspace(Workspace W) {
        System.out.println("db.dao.PheaderDAOImpl.getChosenHeadersForWorkspace()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Pheader> result=null;
        String hql="Select h from Pheader h INNER JOIN h.job j where j.workspace =:wrk and h.chosen=true ";
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
         System.out.println("db.dao.PheaderDAOImpl.checkForMultipleSubsurfacesInHeadersForJob()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        
       
        String hqlDetermineCommonSubs="Select h.subsurface.id from Pheader h    where h.job =:j "
                + "                                                         group by h.subsurface.id "
                + "                                                         having count(*) > 1";
        String hqlSelectIds="Select h.pHeaderId from Pheader h where h.subsurface.id in (:subs) and h.job =:j";
        String hqlUpdateChoose="update Pheader h Set h.chosen = false,h.multipleInstances=true where h.pHeaderId in (:ids)";
        String hqlUpdateChooseTrue="update Pheader h Set h.chosen = true,h.multipleInstances = false where h.job =:j";
        try{
            transaction=session.beginTransaction();
            Query subsurfaceQuery= session.createQuery(hqlDetermineCommonSubs);
            subsurfaceQuery.setParameter("j", job );
            List<Long> subIds=subsurfaceQuery.list();
                
            if(subIds.isEmpty()){
                System.out.println("db.dao.PheaderDAOImpl.checkForMultipleSubsurfacesInHeadersForJob(): no subs repeated for job: "+job.getNameJobStep());
                Query chooseTrueQuery=session.createQuery(hqlUpdateChooseTrue);
                chooseTrueQuery.setParameter("j", job);
                transaction.commit();
            }else{
                System.out.println("db.dao.PheaderDAOImpl.checkForMultipleSubsurfacesInHeadersForJob(): Found "+subIds.size()+" repeated subs for job: "+job.getNameJobStep());
                    Query selectHeaderIds=session.createQuery(hqlSelectIds);
                    selectHeaderIds.setParameterList("subs", subIds);
                    selectHeaderIds.setParameter("j",job);
                    List<Long> repeatedHeaderIds=selectHeaderIds.list();
                    
                        if(repeatedHeaderIds.isEmpty()){
                            System.out.println("db.dao.PheaderDAOImpl.checkForMultipleSubsurfacesInHeadersForJob(): no headers found for job: "+job.getNameJobStep());
                            transaction.commit();
                        }else{
                            
                            System.out.println("db.dao.PheaderDAOImpl.checkForMultipleSubsurfacesInHeadersForJob(): updating the chosen headers for "+repeatedHeaderIds.size()+" headers for job: "+job.getNameJobStep()+"\n"
                                    + "You will need to select the correct subsurface.\n"
                                    + "See the header table for this job ");
                            Query updateQuery=session.createQuery(hqlUpdateChoose);
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
        
        String hqlSelect="Select h.headerId from Pheader h where  h.subsurface =:s and h.job =:j and h.volume !=:v ";
        String hqlUpdate="update Pheader h set h.chosen=false,h.multiple=true where h.headerId in (:ids)";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hqlSelect);
            query.setParameter("j", job);
            query.setParameter("s", conflictedSub);
            query.setParameter("v", volumeToBeExcluded);
            List<Long> idsToUpdate=query.list();
            
                if(idsToUpdate.isEmpty()){
                    System.out.println("db.dao.PheaderDAOImpl.setChosenToFalseForConflictingSubs(): no headers found to update. ");
                    transaction.commit();
                }else{
                    System.out.println("db.dao.PheaderDAOImpl.setChosenToFalseForConflictingSubs(): updating "+idsToUpdate.size()+" conflicting headers");
                    Query updateQ=session.createQuery(hqlUpdate);
                    updateQ.setParameterList("ids", idsToUpdate);
                    int res=updateQ.executeUpdate();
                    transaction.commit();
                            
                }
            
        }catch(Exception e){
            
        }finally{
            session.close();
        }
    }

    @Override
    public void updateDeleteFlagsFor(Volume vol, List<String> subsurfacesOnDisk) {
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String hqlSelectSubsNotInDiskVolume="select s from Subsurface s where s.subsurface not in (:names)";
        String hqlSelectHeadersThatContainDeletedSubs="select h.pHeaderId from Pheader h where h.volume =:v and h.subsurface in (:subs)";
        String hqlUpdateDeleteFalse="update Pheader h set h.deleted=false where h.volume =:v";
        String hqlUpdateDeleteTrue="update Pheader h set h.deleted=true where h.pHeaderId in (:ids)";
        try{
            transaction =session.beginTransaction();
            Query selectSubsNotInDisk=session.createQuery(hqlSelectSubsNotInDiskVolume);
            selectSubsNotInDisk.setParameterList("names", subsurfacesOnDisk);
            List<Subsurface> deletedSubs=selectSubsNotInDisk.list();
            
            if(deletedSubs.isEmpty()){
                System.out.println("db.dao.PheaderDAOImpl.updateDeleteFlagsFor(): all subs in disk volume : "+vol.getPathOfVolume()+" are present in the database volume : "+vol.getNameVolume()+" ("+vol.getId()+")");
                /*Query delFalse=session.createQuery(hqlUpdateDeleteFalse);
                delFalse.setParameter("v", vol);
                int delF=delFalse.executeUpdate();*/
                transaction.commit();
            }else{
                 System.out.println("db.dao.PheaderDAOImpl.updateDeleteFlagsFor(): "+deletedSubs.size()+" in disk volume : "+vol.getPathOfVolume()+" are ABSENT in the database volume : "+vol.getNameVolume()+" ("+vol.getId()+")");
                 Query selectHeaders=session.createQuery(hqlSelectHeadersThatContainDeletedSubs);
                 selectHeaders.setParameter("v", vol);
                 selectHeaders.setParameterList("subs", deletedSubs);
                 List<Long> headersToUdpate=selectHeaders.list();
                 
                    if(headersToUdpate.isEmpty()){
                        System.out.println("db.dao.PheaderDAOImpl.updateDeleteFlagsFor(): no previously existing subsurfaces were deleted");
                        Query delFalse=session.createQuery(hqlUpdateDeleteFalse);
                        delFalse.setParameter("v", vol);
                        int delF=delFalse.executeUpdate();
                        transaction.commit();
                    }else{
                        System.out.println("db.dao.PheaderDAOImpl.updateDeleteFlagsFor(): set delete=true on "+headersToUdpate.size()+" headers for vol: "+vol.getNameVolume()+" ("+vol.getId()+")");
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
    
    
    
}
