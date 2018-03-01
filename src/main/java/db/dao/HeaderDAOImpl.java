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
            h.setNumberOfRuns(newH.getNumberOfRuns());
            h.setModified(newH.getModified());
            h.setWorkflowVersion(newH.getWorkflowVersion());
            h.setUpdateTime(newH.getUpdateTime());
            h.setSummaryTime(newH.getSummaryTime());
            h.setTextfilepath(newH.getTextfilepath());
            h.setMultipleInstances(newH.getModified());
            h.setChosen(newH.getChosen());
            h.setLogs(newH.getLogs());
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Header> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Header.class);
            criteria.add(Restrictions.eq("job", job));
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
    public void deleteHeadersFor(Volume v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteHeadersFor(Job job) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     @Override
    public List<Subsurface> getSubsurfacesToBeSummarized(Volume v) {
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
   
    
    
    /*@Override
    public void updateHeader(Volume v, List<Headers> headers) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<Headers> result=null;
    List<Headers> toBedeleted=new ArrayList<>();
    List<Headers> toBeUpdated=new ArrayList<>();
    List<Headers> toBeAdded=new ArrayList<>();
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(Header.class);
    criteria.add(Restrictions.eq("volume", v));
    result=criteria.list();
    
    //the volume at this point has either more number of subsurfaces for update or less
    
    if(result.size()>headers.size())                                            //more subsurface lines in the extracted entries as compared to the the latest header extract
    {
    for (Iterator<Headers> iterator = result.iterator(); iterator.hasNext();) {
    Header next = iterator.next();
    for (Iterator<Headers> iterator1 = headers.iterator(); iterator1.hasNext();) {
    Header n = iterator1.next();
    if(n.getSubsurface().equals(next.getSubsurface()))                  //since subsurface is what interests us.
    {
    n.setId(next.getId());
    toBeUpdated.add(n);
    if(toBedeleted.contains(n))toBedeleted.remove(n);
    session.update(n);
    }else
    {
    toBedeleted.add(n);
    
    }
    
    
    
    }
    
    
    
    
    }
    
    
    for (Iterator<Headers> iterator1 = toBedeleted.iterator(); iterator1.hasNext();) {
    Header nn = iterator1.next();
    for (Iterator<Headers> r = result.iterator(); r.hasNext();) {
    Header next = r.next();
    if(next.getSubsurface().equals(nn.getSubsurface()))
    {
    nn.setIdHeaders(next.getIdHeaders());
    }
    
    }
    
    for (Iterator<Headers> iterator = toBedeleted.iterator(); iterator.hasNext();) {
    Header next = iterator.next();
    this.deleteHeader(next.getIdHeaders());
    
    }
    
    
    
    }
    
    }
    else{
    if(result.size()<=headers.size()){
    for (Iterator<Headers> iterator = headers.iterator(); iterator.hasNext();) {
    Header next = iterator.next();
    for (Iterator<Headers> iterator1 = result.iterator(); iterator1.hasNext();) {
    Header n = iterator1.next();
    if(n.getSubsurface().equals(next.getSubsurface()))                  //since subsurface is what interests us.
    {
    next.setIdHeaders(n.getIdHeaders());
    toBeUpdated.add(next);
    if(toBeAdded.contains(next)){toBeAdded.remove(next);}
    session.update(n);
    }else
    {
    toBeAdded.add(next);
    
    }
    
    
    
    }
    
    
    
    
    }
    
    for(Iterator<Headers> iterator1=toBeAdded.iterator();iterator1.hasNext();){
    Header nn=iterator1.next();
    
    this.createHeader(nn);
    }
    }
    }
    
    
    
    
    
    
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    }*/
    /* @Override
    public void deleteHeadersFor(Volume v) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    /* @Override
    public Set<Volume> getVolumesContaining(String subsurface) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<Headers> result=null;
    Set<Volume> volumeSet=new HashSet<>();
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(Header.class);
    criteria.add(Restrictions.eq("subsurface", subsurface));
    
    result=criteria.list();
    if(result.size()>0){
    System.out.println("res >0 :"+result.size());
    }else
    {System.out.println("res=0");}
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    
    if(result.size()==0){
    System.out.println("result is zero");
    }
    
    
    for (Iterator<Headers> iterator = result.iterator(); iterator.hasNext();) {
    Header next = iterator.next();
    System.out.println("Adding: "+next.getVolume());
    volumeSet.add(next.getVolume());
    
    }
    
    return volumeSet;
    
    }*/
    
    /*  @Override
    public Set<Volume> getVolumesContaining(Subsurface subsurface) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<Headers> result=null;
    Set<Volume> volumeSet=new HashSet<>();
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(Header.class);
    criteria.add(Restrictions.eq("subsurface", subsurface));
    
    result=criteria.list();
    if(result.size()>0){
    System.out.println("res >0 :"+result.size());
    }else
    {System.out.println("res=0");}
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    
    if(result.size()==0){
    System.out.println("result is zero");
    }
    
    
    for (Iterator<Headers> iterator = result.iterator(); iterator.hasNext();) {
    Header next = iterator.next();
    System.out.println("Adding: "+next.getVolume());
    volumeSet.add(next.getVolume());
    
    }
    
    return volumeSet;
    
    }*/
    /*@Override
    public List<Headers> getHeadersFor(Volume v, String s) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<Headers> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(Header.class);
    criteria.add(Restrictions.eq("volume", v));
    criteria.add(Restrictions.eq("subsurface", s));
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
    public List<Headers> getHeadersFor(Volume v, Subsurface s) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<Headers> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(Header.class);
    criteria.add(Restrictions.eq("volume", v));
    criteria.add(Restrictions.eq("subsurface", s));
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
    public Header getHeadersFor(Volume dbvol, Subsurface dbsub, String timestamp) {
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
               
                
             //   Query query=session.createQuery("from obpmanager.subsurface_job where job_id = :jobid and id =:subid");
//                "select * from obpmanager.header INNER JOIN obpmanager.job on obpmanager.header.job_fk=obpmanager.job.job_id INNER JOIN public.subsurface on obpmanager.header.subsurface_fk=public.subsurface.id AND chosen=false WHERE public.subsurface.id=16062 AND obpmanager.job.job_id=91;"
/*  Query query=session.createQuery("from Header INNER JOIN Job on Header.job.id=Job.id INNER JOIN Subsurface on Header.subsurface.id=Subsurface.id  WHERE Subsurface.id =:subid AND Job.id =:jobid");
query.setParameter("jobid", job);
query.setParameter("subid", sub);
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

    

    
    
}
