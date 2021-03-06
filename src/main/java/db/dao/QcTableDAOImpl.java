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
import app.properties.AppProperties;
import db.model.Job;
import db.model.Sequence;
import db.model.User;
import db.model.Workspace;
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
    public int getQcTablesFor(Job j, Map<Long, Map<Subsurface, QcTable>> qcmatrixRowSubQcTableMap,Boolean present) {
        System.out.println("db.dao.QcTableDAOImpl.getQcTablesFor()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<QcTable> results=null;
        String hql="Select q from  QcTable q INNER JOIN q.qcMatrixRow qmr where qmr.job =:j and qmr.present=:p";
        
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("j", j);
            query.setParameter("p",present);
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
        return results.size();
    }

    @Override
    public int update(Long idOfQcMatrix, Subsurface sub, Boolean result, String updateTime, User currentUser) {
        System.out.println("db.dao.QcTableDAOImpl.update()");
        int res=0;
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String select="Select q.id from QcTable q INNER JOIN q.qcMatrixRow qmr"
                + "                             INNER JOIN q.subsurface sub"
                + "                             where qmr.id =:q AND sub =:s";
        
        String update="Update QcTable set result=:r , updateTime=:ut, user=:usr where id in (:ids) ";
        
        try{
            transaction=session.beginTransaction();
            Query sq=session.createQuery(select);
            sq.setParameter("q", idOfQcMatrix);
            sq.setParameter("s", sub);
            
                List<Long> ids=sq.list();
                if(ids.isEmpty()){
                    transaction.commit();
                    res = -13;    //return < 0 to indicate no such qctable > for idOfQcMatrix,sub
                }else{
                    Query up=session.createQuery(update);
                    up.setParameter("r", result);
                    up.setParameter("ut", updateTime);
                    up.setParameter("usr", currentUser);
                    up.setParameterList("ids", ids);
                        res=up.executeUpdate();
                    transaction.commit();
                }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return res;
    }

    @Override
    public void setAllqcTableValuesFor(Sequence seq, Job job, Long qcmatrixId,Boolean result,String updateTime, User currentUser) {
       System.out.println("db.dao.QcTableDAOImpl.update()");
        int res=0;
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String select="Select q.id from QcTable q INNER JOIN q.subsurface sub"
                + "                               INNER JOIN q.qcMatrixRow qmr"
                + "                               "
                + "                                     WHERE "
                + "                                 qmr.job=:j "
                + "                                      AND"
                + "                                 sub.sequence=:s"
                + "                                      AND"
                + "                                 qmr.id=:qid";
        
        
        String update="Update QcTable set result=:r , updateTime=:ut, user=:usr where id in (:ids) ";
        String update2="Update QcTable set result=:r , updateTime=:ut, user=:usr where id in ("+select+")";
        try{
            transaction=session.beginTransaction();
            //Query sq=session.createQuery(select);
            Query sq=session.createQuery(update2);
            sq.setParameter("r", result);
            sq.setParameter("ut", updateTime);
            sq.setParameter("usr", currentUser);
            sq.setParameter("j", job);
            sq.setParameter("s", seq);
            sq.setParameter("qid", qcmatrixId);
            
            /*List<Long> ids=sq.list();
            if(ids.isEmpty()){
            transaction.commit();
            res = -13;    //return < 0 to indicate no such qctable > for idOfQcMatrix,sub
            }else{
            Query up=session.createQuery(update);
            up.setParameter("r", result);
            up.setParameter("ut", updateTime);
            up.setParameter("usr", currentUser);
            up.setParameterList("ids", ids);*/
                        res=sq.executeUpdate();
                    transaction.commit();
         //       }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public void createBulkQcTables(List<QcTable> qctables) {
         System.out.println("db.dao.QcTableDAOImpl.createBulkQcTables()");
        if(qctables.isEmpty()){
            return;
        }
        int batchsize=Math.min(qctables.size(), AppProperties.BULK_TRANSACTION_BATCH_SIZE);
         Session session = HibernateUtil.getSessionFactory().openSession();
         Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            for(int ii=0;ii<qctables.size();ii++){
                session.saveOrUpdate(qctables.get(ii));
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

    @Override
    public Map<Job, Map<Subsurface, List<QcTable>>> getQcTablesOnLeafJobsFor(Workspace w) {
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction =null;
        int jc = 0;
        int sbc = 0;
        int qcc = 0;
        
        Map<Job, Map<Subsurface, List<QcTable>>> map=new HashMap<>();
        List<Long> leavesId=null;
        List<Object[]> jobsubQc=null;
        //String selectLeaves="select j.id from  Descendant D RIGHT JOIN D.job j where j is null and  j.workspace= :w";
        String selectLeaves="select jj.id from Job jj where jj.id not in (select j.id from  Descendant D Left JOIN D.job j) and jj.workspace=:w";
        
        String subsSelect="select sj.pk.subsurface.id from SubsurfaceJob sj where  sj.pk.job.id in (:leavesId) and sj.updateTime > sj.summaryTime";  //get all subsurfaces in leavesId that were updated
        
        String getJobSubQc="select j,sub,qct from QcTable qct INNER JOIN qct.qcMatrixRow QMR "
                + "                                           INNER JOIN QMR.job j"
                + "                                           INNER JOIN qct.subsurface sub "
                + "         where  QMR.present=true and sub.id in ("+subsSelect+") and j.id in (:leavesId)";                //all qctables for all subsurfaces for all leavesId
        
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(selectLeaves);
            query.setParameter("w", w);
            leavesId=query.list();
            System.out.println("db.dao.QcTableDAOImpl.getQcTablesOnLeafJobsFor(): returning a list of "+leavesId.size()+" leaves");
            for(Long l:leavesId){
                System.out.println("id: "+l);
            }
          
            if(!leavesId.isEmpty()){
                    Query qcquery=session.createQuery(getJobSubQc);
                    qcquery.setParameterList("leavesId", leavesId);
                    jobsubQc=qcquery.list();
                    
            }else{
                System.out.println("db.dao.QcTableDAOImpl.getQcTablesOnLeafJobsFor(): NO leaves found!");
            }
            transaction.commit();
            
            for(Object[] jsq:jobsubQc){
                Job jb=(Job) jsq[0];
                Subsurface sb=(Subsurface) jsq[1];
                QcTable qc=(QcTable) jsq[2];
                if(!map.containsKey(jb)){
                    map.put(jb,new HashMap<>());
                    jc++;
                }
                if(!map.get(jb).containsKey(sb)){
                    map.get(jb).put(sb, new ArrayList<>());
                    sbc++;
                }
                map.get(jb).get(sb).add(qc);
                qcc++;
               
            }
            
        }catch(Exception e){
            throw e;
        }finally{
            session.close();
        }
        
       
        Set<Job> keys=map.keySet();
        int size=0;
        for(Job j: keys){
            size+=map.get(j).values().size();
        }
        
         System.out.println("db.dao.QcTableDAOImpl.getQcTablesOnLeafJobsFor() :  "+jc+" jobs "+sbc+" subs "+qcc+" qcs entered: -> mapsize: "+(jc*sbc*qcc));
        return map;
    }

    @Override
    public Map<Job, Map<Subsurface, List<QcTable>>> getUpdatedQcTablesFor(Workspace w) {
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction =null;
        int jc = 0;
        int sbc = 0;
        int qcc = 0;
        
        Map<Job, Map<Subsurface, List<QcTable>>> map=new HashMap<>();
        List<Long> leavesId=null;
        List<Object[]> jobsubQc=null;
        //String selectLeaves="select j.id from  Descendant D RIGHT JOIN D.job j where j is null and  j.workspace= :w";
        //String selectLeaves="select jj.id from Job jj where jj.id not in (select j.id from  Descendant D Left JOIN D.job j) and jj.workspace=:w";
        
       
        
        String getJobSubQc="select j,sub,qct from QcTable qct INNER JOIN qct.qcMatrixRow QMR "
                + "                                           INNER JOIN QMR.job j"
                + "                                           INNER JOIN qct.subsurface sub "
                + "                                           INNER JOIN qct.subsurfaceJob subjob"
                + "                                            WHERE QMR.present=true AND subjob.updateTime > subjob.summaryTime AND j.workspace=:w";
                
        
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(getJobSubQc);
            query.setParameter("w", w);
            jobsubQc=query.list();
            System.out.println("db.dao.QcTableDAOImpl.getQcTablesOnLeafJobsFor(): returning a list of "+jobsubQc.size()+" objects");
           
            transaction.commit();
            
            for(Object[] jsq:jobsubQc){
                Job jb=(Job) jsq[0];
                Subsurface sb=(Subsurface) jsq[1];
                QcTable qc=(QcTable) jsq[2];
                if(!map.containsKey(jb)){
                    map.put(jb,new HashMap<>());
                    jc++;
                }
                if(!map.get(jb).containsKey(sb)){
                    map.get(jb).put(sb, new ArrayList<>());
                    sbc++;
                }
                map.get(jb).get(sb).add(qc);
                qcc++;
               
            }
            
        }catch(Exception e){
            throw e;
        }finally{
            session.close();
        }
        
       
        Set<Job> keys=map.keySet();
        int size=0;
        for(Job j: keys){
            size+=map.get(j).values().size();
        }
        
         System.out.println("db.dao.QcTableDAOImpl.getQcTablesOnLeafJobsFor() :  "+jc+" jobs "+sbc+" subs "+qcc+" qcs entered: -> mapsize: "+(jc*sbc*qcc));
        return map;
    }

    @Override
    public Map<Job, Map<Subsurface, List<QcTable>>> getQcTablesFor(Workspace w) {
       
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        String hql="select j,sub,qct from QcTable qct INNER JOIN qct.qcMatrixRow QMR "
                + "                                           INNER JOIN QMR.job j"
                + "                                           INNER JOIN qct.subsurface sub "
                + "                                            WHERE QMR.present=true  AND j.workspace=:w";
        
           Map<Job, Map<Subsurface, List<QcTable>>> map=new HashMap<>();
       
        List<Object[]> jobsubQc=null;
         int jc = 0;
        int sbc = 0;
        int qcc = 0;
         try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("w", w);
            jobsubQc=query.list();
            System.out.println("db.dao.QcTableDAOImpl.getQcTablesFor(): returning a list of "+jobsubQc.size()+" objects");
           
            transaction.commit();
            
            for(Object[] jsq:jobsubQc){
                Job jb=(Job) jsq[0];
                Subsurface sb=(Subsurface) jsq[1];
                QcTable qc=(QcTable) jsq[2];
                if(!map.containsKey(jb)){
                    map.put(jb,new HashMap<>());
                    jc++;
                }
                if(!map.get(jb).containsKey(sb)){
                    map.get(jb).put(sb, new ArrayList<>());
                    sbc++;
                }
                map.get(jb).get(sb).add(qc);
                qcc++;
               
            }
            
        }catch(Exception e){
            throw e;
        }finally{
            session.close();
        }
        
       
        Set<Job> keys=map.keySet();
        int size=0;
        for(Job j: keys){
            size+=map.get(j).values().size();
        }
        
         System.out.println("db.dao.QcTableDAOImpl.getQcTablesFor() :  "+jc+" jobs "+sbc+" subs "+qcc+" qcs entered: -> mapsize: "+(jc*sbc*qcc));
        return map;
    }
    
    
    
}
