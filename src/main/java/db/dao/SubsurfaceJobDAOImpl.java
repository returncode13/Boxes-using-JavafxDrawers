/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

//import app.connections.hibernate.HibernateUtil;
import app.connections.hibernate.HibernateUtil;
import app.properties.AppProperties;
import db.model.Job;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.SubsurfaceJob;
import db.model.SubsurfaceJobId;
import db.model.Workspace;
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
    public void createBulkSubsurfaceJob(List<SubsurfaceJob> subsurfaceJobs) {
        int batchsize=Math.min(subsurfaceJobs.size(), AppProperties.BULK_TRANSACTION_BATCH_SIZE);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
           for(int ii=0;ii<subsurfaceJobs.size();ii++){
               session.save(subsurfaceJobs.get(ii));
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
    public SubsurfaceJob getSubsurfaceJob(SubsurfaceJobId id) {
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
    public void updateBulkSubsurfaceJobs(List<SubsurfaceJob> subsurfaceJobsToBeUpdated) {
        int batchsize=Math.min(subsurfaceJobsToBeUpdated.size(), AppProperties.BULK_TRANSACTION_BATCH_SIZE);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
           for(int ii=0;ii<subsurfaceJobsToBeUpdated.size();ii++){
               session.update(subsurfaceJobsToBeUpdated.get(ii));
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
    public void updateSubsurfaceJob(SubsurfaceJob nsj) {
         Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<SubsurfaceJob> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(SubsurfaceJob.class);
            criteria.add(Restrictions.eq("pk.job", nsj.getJob()));
            criteria.add(Restrictions.eq("pk.subsurface",nsj.getSubsurface()));
            
            result=criteria.list();
            transaction.commit();
            
             if(result!=null && result.size()==1){
                    transaction=null;
                    SubsurfaceJob sj=result.get(0);
                    sj.setUpdateTime(nsj.getUpdateTime());
                    sj.setSummaryTime(nsj.getSummaryTime());
                    transaction=session.beginTransaction();
                    session.update(sj);
                    transaction.commit();
            
            }else{
                 throw new Exception("db.dao.SubsurfaceJobDAOImpl.updateSubsurfaceJob(): result.size()>1 found for job "+nsj.getJob()+" sub: "+nsj.getSubsurface()+" size found: "+result.size());
             }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
            
        }
   }

    @Override
    public void deleteSubsurfaceJob(SubsurfaceJobId id) {
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

    @Override
    public List<SubsurfaceJob> getSubsurfaceJobsForSummary() {
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<SubsurfaceJob> result=null;
        String sql="update_time > summary_time";
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(SubsurfaceJob.class);
            criteria.add(Restrictions.sqlRestriction(sql));
            
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
    public void updateTimeWhereJobEquals(Job job, String updateTime) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        int result=13;
        String sql="update SubsurfaceJob set updateTime = :up where job_id =:j";
        try{
            transaction=session.beginTransaction();
            /*Criteria criteria=session.createCriteria(SubsurfaceJob.class);
            criteria.add(Restrictions.sqlRestriction(hql));*/
            Query query= session.createQuery(sql);
            query.setParameter("j", job);
            query.setParameter("up", updateTime);
            result=query.executeUpdate();
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public String getLatestSummaryTime() {
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<String> result=null;
        String sql="select MAX(summaryTime) from SubsurfaceJob";
        try{
            transaction=session.beginTransaction();
            Query query= session.createQuery(sql);
            
            
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
    public List<Subsurface> getSubsurfacesForJob(Job job) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Subsurface> result=null;
        String hql="Select s.pk.subsurface from SubsurfaceJob s where s.pk.job =:j";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("j", job);
            
            result=query.list();
            System.out.println("db.dao.SubsurfaceJobDAOImpl.getSubsurfacesForJob(): size of results: "+result.size());
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

    @Override
    public List<SubsurfaceJob> getSubsurfaceJobFor(Workspace dbWorkspace) {
         Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<SubsurfaceJob> result=null;
        String hql="SELECT sj from SubsurfaceJob sj INNER JOIN sj.pk.job jj WHERE jj.workspace =:w";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("w", dbWorkspace);
            
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
    public void updateTimeWhere(Job job, Subsurface sub, String updateTime) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        int result=13;
        System.out.println("db.dao.SubsurfaceJobDAOImpl.updateTimeWhere()");
        String sql="update SubsurfaceJob set updateTime = :up where pk.job =:j and pk.subsurface =:i";
        try{
            transaction=session.beginTransaction();
            /*Criteria criteria=session.createCriteria(SubsurfaceJob.class);
            criteria.add(Restrictions.sqlRestriction(hql));*/
            Query query= session.createQuery(sql);
            query.setParameter("j", job);
            query.setParameter("i",sub);
            query.setParameter("up", updateTime);
            result=query.executeUpdate();
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public void updateTimeWhere(Job job, Sequence seq, String updateTime) {
         Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        int result=13;
        System.out.println("db.dao.SubsurfaceJobDAOImpl.updateTimeWhere()");
        String subselect="select sub.id from Subsurface sub where sub.sequence=:i";
        String sql="update SubsurfaceJob set updateTime = :up  where pk.job =:j and pk.subsurface.id in ("+subselect+")";
        try{
            transaction=session.beginTransaction();
            /*Criteria criteria=session.createCriteria(SubsurfaceJob.class);
            criteria.add(Restrictions.sqlRestriction(hql));*/
            Query query= session.createQuery(sql);
            query.setParameter("j", job);
            query.setParameter("i",seq);
            query.setParameter("up", updateTime);
            result=query.executeUpdate();
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

  
   
    @Override
    public List<SubsurfaceJob> getSubsurfaceJobForSummary(Workspace dbWorkspace) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<SubsurfaceJob> result=null;
        String hql="SELECT sj from SubsurfaceJob sj INNER JOIN sj.pk.job jj WHERE jj.workspace =:w and sj.updateTime>sj.summaryTime";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("w", dbWorkspace);
            
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
    public List<Object[]> getAllSubsurfaceJobsFor(Workspace workspace) {
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Object[]> result=null;
        String hql="SELECT sj.pk.job,sj.pk.subsurface from SubsurfaceJob sj where sj.pk.job.workspace =:w";
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(hql);
            query.setParameter("w", workspace);
            
            result=query.list();
            System.out.println("db.dao.SubsurfaceJobDAOImpl.getAllSubsurfaceJobsFor(): returning "+result.size()+" job,subsurface combinations" );
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

   
}
