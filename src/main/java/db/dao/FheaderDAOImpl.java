/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import app.properties.AppProperties;
import db.model.Fheader;
import db.model.Job;
import db.model.Subsurface;
import db.model.Volume;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class FheaderDAOImpl implements FheaderDAO{

    @Override
    public void createHeader(Fheader h) {
        System.out.println("db.dao.FheaderDAOImpl.createHeader()");
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
    public void createBulkHeaders(List<Fheader> headers) {
        System.out.println("db.dao.FheaderDAOImpl.createBulkHeaders()");
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
    public Fheader getHeader(Long hid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Fheader h= (Fheader) session.get(Fheader.class, hid);
            return h;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void updateHeader(Long hid, Fheader newH) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteHeader(Long hid) {
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Fheader h= (Fheader) session.get(Fheader.class, hid);
            session.delete(h);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public List<Fheader> getHeadersFor(Job job) {
        System.out.println("db.dao.FheaderDAOImpl.getHeadersFor(Job)");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Fheader> result=null;
        String hql="from Fheader h where h.job =:j";
        try{
            transaction=session.beginTransaction();
            
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
    public List<Fheader> getHeadersFor(Volume v) {
       System.out.println("db.dao.FheaderDAOImpl.getHeadersFor(Volume)");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Fheader> result=null;
        String hql="from Fheader h where h.volume =:v";
        try{
            transaction=session.beginTransaction();
            
                Query query=session.createQuery(hql);
                query.setParameter("v", v);
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
        System.out.println("db.dao.FheaderDAOImpl.deleteHeadersFor(Volume)");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        
        String hqlSelect="Select h.fHeaderId from Fheader h where h.volume =:v";
        String hqlDelete="Delete from Fheader h where h.fHeaderId in (:ids)";
        try{
            transaction=session.beginTransaction();
            Query selectQuery= session.createQuery(hqlSelect);
            selectQuery.setParameter("v", v);
            List<Long> idsToDelete=selectQuery.list();
            
            if(!idsToDelete.isEmpty()){
                Query delQuery= session.createQuery(hqlDelete);
                delQuery.setParameterList("ids", idsToDelete);
                System.out.println("db.dao.FheaderDAOImpl.deleteHeadersFor(): deleting "+idsToDelete.size()+" headers belonging to volume: "+v.getNameVolume()+" ("+v.getId()+")");
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
          System.out.println("db.dao.FheaderDAOImpl.deleteHeadersFor(Job)");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        
        String hqlSelect="Select h.fHeaderId from Fheader h where h.job =:j";
        String hqlDelete="Delete from Fheader h where h.fHeaderId in (:ids)";
        try{
            transaction=session.beginTransaction();
            Query selectQuery= session.createQuery(hqlSelect);
            selectQuery.setParameter("j", job);
            List<Long> idsToDelete=selectQuery.list();
            
            if(!idsToDelete.isEmpty()){
                Query delQuery= session.createQuery(hqlDelete);
                delQuery.setParameterList("ids", idsToDelete);
                System.out.println("db.dao.FheaderDAOImpl.deleteHeadersFor(Job): deleting "+idsToDelete.size()+" p-headers belonging to job: "+job.getNameJobStep()+" ("+job.getId()+")");
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
    public void updateDeleteFlagsFor(Volume vol, List<String> subsurfacesOnDisk) {
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String hqlSelectSubsNotInDiskVolume="select s from Subsurface s where s.subsurface not in (:names)";
        String hqlSelectHeadersThatContainDeletedSubs="select h.fHeaderId from Fheader h where h.volume =:v and h.subsurface in (:subs)";
        String hqlUpdateDeleteFalse="update Fheader h set h.deleted=false where h.volume =:v";
        String hqlUpdateDeleteTrue="update Fheader h set h.deleted=true where h.fHeaderId in (:ids)";
        try{
            transaction =session.beginTransaction();
            Query selectSubsNotInDisk=session.createQuery(hqlSelectSubsNotInDiskVolume);
            selectSubsNotInDisk.setParameterList("names", subsurfacesOnDisk);
            List<Subsurface> deletedSubs=selectSubsNotInDisk.list();
            
            if(deletedSubs.isEmpty()){
                System.out.println("db.dao.FheaderDAOImpl.updateDeleteFlagsFor(): all subs in disk volume : "+vol.getPathOfVolume()+" are present in the database volume : "+vol.getNameVolume()+" ("+vol.getId()+")");
                /*Query delFalse=session.createQuery(hqlUpdateDeleteFalse);
                delFalse.setParameter("v", vol);
                int delF=delFalse.executeUpdate();*/
                transaction.commit();
            }else{
                 System.out.println("db.dao.FheaderDAOImpl.updateDeleteFlagsFor(): "+deletedSubs.size()+" in disk volume : "+vol.getPathOfVolume()+" are ABSENT in the database volume : "+vol.getNameVolume()+" ("+vol.getId()+")");
                 Query selectHeaders=session.createQuery(hqlSelectHeadersThatContainDeletedSubs);
                 selectHeaders.setParameter("v", vol);
                 selectHeaders.setParameterList("subs", deletedSubs);
                 List<Long> headersToUdpate=selectHeaders.list();
                 
                    if(headersToUdpate.isEmpty()){
                        System.out.println("db.dao.FheaderDAOImpl.updateDeleteFlagsFor(): no previously existing subsurfaces were deleted");
                        Query delFalse=session.createQuery(hqlUpdateDeleteFalse);
                        delFalse.setParameter("v", vol);
                        int delF=delFalse.executeUpdate();
                        transaction.commit();
                    }else{
                        System.out.println("db.dao.FheaderDAOImpl.updateDeleteFlagsFor(): set delete=true on "+headersToUdpate.size()+" headers for vol: "+vol.getNameVolume()+" ("+vol.getId()+")");
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
    public void checkForMultipleSubsurfacesInHeadersForJob(Job job) {
        System.out.println("db.dao.FheaderDAOImpl.checkForMultipleSubsurfacesInHeadersForJob()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        
       
        String hqlDetermineCommonSubs="Select h.subsurface.id from Fheader h    where h.job =:j "
                + "                                                         group by h.subsurface.id "
                + "                                                         having count(*) > 1";
        String hqlSelectIds="Select h.fHeaderId from Fheader h where h.subsurface.id in (:subs) and h.job =:j";
        String hqlUpdateChooseFalse="update Fheader h Set h.chosen = false,h.multipleInstances = true where h.fHeaderId in (:ids)";
        String hqlUpdateChooseTrue="update Fheader h Set h.chosen = true,h.multipleInstances = false where h.job =:j";
        try{
            transaction=session.beginTransaction();
            Query subsurfaceQuery= session.createQuery(hqlDetermineCommonSubs);
            subsurfaceQuery.setParameter("j", job );
            List<Long> subIds=subsurfaceQuery.list();
                
            if(subIds.isEmpty()){
                System.out.println("db.dao.FheaderDAOImpl.checkForMultipleSubsurfacesInHeadersForJob(): no subs repeated for job: "+job.getNameJobStep());
                Query chooseTrueQuery=session.createQuery(hqlUpdateChooseTrue);
                chooseTrueQuery.setParameter("j", job);
                int qes=chooseTrueQuery.executeUpdate();
                transaction.commit();
            }else{
                System.out.println("db.dao.FheaderDAOImpl.checkForMultipleSubsurfacesInHeadersForJob(): Found "+subIds.size()+" repeated subs for job: "+job.getNameJobStep());
                    Query selectHeaderIds=session.createQuery(hqlSelectIds);
                    selectHeaderIds.setParameterList("subs", subIds);
                    selectHeaderIds.setParameter("j",job);
                    List<Long> repeatedHeaderIds=selectHeaderIds.list();
                    
                        if(repeatedHeaderIds.isEmpty()){
                            System.out.println("db.dao.FheaderDAOImpl.checkForMultipleSubsurfacesInHeadersForJob(): no headers found for job: "+job.getNameJobStep());
                            transaction.commit();
                        }else{
                            
                            System.out.println("db.dao.FheaderDAOImpl.checkForMultipleSubsurfacesInHeadersForJob(): updating the chosen headers for "+repeatedHeaderIds.size()+" headers for job: "+job.getNameJobStep()+"\n"
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
    public String getLatestTimeStampFor(Volume volume) {
        System.out.println("db.dao.HeaderDAOImpl.getLatestTimeStampFor()");
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<String> result=null;
        String sql="select MAX(timeStamp) from Fheader where volume =:v";
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

}
