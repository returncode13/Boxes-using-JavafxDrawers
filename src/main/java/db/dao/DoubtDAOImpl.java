/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Doubt;
import db.model.DoubtType;
import db.model.Header;
import db.model.Job;
import db.model.Link;
import app.connections.hibernate.HibernateUtil_back;
import db.model.Dot;
import db.model.Sequence;
import db.model.Subsurface;
import java.util.List;
import java.util.Set;
import middleware.doubt.DoubtTypeModel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DoubtDAOImpl implements DoubtDAO{

    @Override
    public void createDoubt(Doubt ds) {
        Session session=HibernateUtil_back.getSessionFactory().openSession();
        Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(ds);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void updateDoubt(Long dsid, Doubt newds) {
         Session session = HibernateUtil_back.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Doubt ll=(Doubt) session.get(Doubt.class,dsid);
            ll.setDoubtType(newds.getDoubtType());
            //ll.setErrorMessage(newds.getErrorMessage());
            //ll.setHeaders(newds.getHeaders());
            //ll.setParentSessionDetails(newds.getParentSessionDetails());
            //ll.setSubsurface(newds.getSubsurface());
            //ll.setLink(newds.getLink());
            //ll.setStatus(newds.getStatus());
            ll.setChildJob(newds.getChildJob());
            ll.setDot(newds.getDot());
            ll.setLink(newds.getLink());
            ll.setSubsurface(newds.getSubsurface());
            ll.setUser(newds.getUser());
            ll.setDoubtStatuses(newds.getDoubtStatuses());
            ll.setInheritedDoubts(newds.getInheritedDoubts());
            ll.setDoubtCause(newds.getDoubtCause());
            ll.setSequence(newds.getSequence());
            
            session.update(ll);
            
            
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }    
    }

    @Override
    public Doubt getDoubt(Long dsid) {
        Session session = HibernateUtil_back.getSessionFactory().openSession();
        try{
            Doubt ll=(Doubt) session.get(Doubt.class,dsid);
            return ll;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return null;
    }

    @Override
    public void deleteDoubt(Long dsid) {
        Session session = HibernateUtil_back.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Doubt ll=(Doubt) session.get(Doubt.class,dsid);
            session.delete(ll);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    
    /*   @Override
    public List<Doubt> getDoubtsForLink(Link link) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<Doubt> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(Doubt.class);
    criteria.add(Restrictions.eq("link", link));
    
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
    public List<Doubt> getDoubtListForJobInSession(SessionDetails sd) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<Doubt> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(Doubt.class);
    criteria.add(Restrictions.eq("sessionDetails", sd));
    
    result=criteria.list();
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    return result;
    }*/
    /*
    @Override
    public List<Doubt> getDoubtListForJobInSession(SessionDetails sd, DoubtType dt) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<Doubt> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(Doubt.class);
    criteria.add(Restrictions.eq("sessionDetails", sd));
    criteria.add(Restrictions.eq("doubtType", dt));
    
    result=criteria.list();
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    return result;
    }*/

    /*@Override
    public List<Doubt> getDoubtListForJobInSession(SessionDetails sd, DoubtType dt, Header hd) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<Doubt> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(Doubt.class);
    criteria.add(Restrictions.eq("sessionDetails", sd));
    criteria.add(Restrictions.eq("doubtType", dt));
    criteria.add(Restrictions.eq("headers", hd));
    
    result=criteria.list();
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    return result;                      //the list size should be one
    }*/

    /*  @Override
    public List<Doubt> getDoubtListForJobInSession(SessionDetails parentsd, Long childsdId, DoubtType dt, Header hd) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<Doubt> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(Doubt.class);
    criteria.add(Restrictions.eq("parentSessionDetails", parentsd));
    criteria.add(Restrictions.eq("doubtType", dt));
    criteria.add(Restrictions.eq("headers", hd));
    criteria.add(Restrictions.eq("childSessionDetailsId",childsdId));
    
    result=criteria.list();
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    return result;
    }*/
    /*@Override
    public List<Doubt> getDoubtListForJobInSession(Header hd) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    List<Doubt> result=null;
    try{
    transaction=session.beginTransaction();
    Criteria criteria=session.createCriteria(Doubt.class);
    criteria.add(Restrictions.eq("headers", hd));
    
    
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
    public Doubt getDoubtFor(Subsurface sub, Job job, Dot dot, DoubtType doubtType) {
        Session session=HibernateUtil_back.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Doubt> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Doubt.class);
            criteria.add(Restrictions.eq("subsurface", sub));
            criteria.add(Restrictions.eq("childJob", job));
            criteria.add(Restrictions.eq("dot", dot));
            criteria.add(Restrictions.eq("doubtType",doubtType));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            result=criteria.list();
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        if(result.size()>1){
            return null;
        }else if(result.isEmpty()){
                return null;
                }
        else if (result.size()==1){
                return  result.get(0);
                }else{
            return null;
        }
               
        
        
    }

    @Override
    public List<Doubt> getDoubtFor(Subsurface sub, Job job, Dot dot) {
         Session session=HibernateUtil_back.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Doubt> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Doubt.class);
            criteria.add(Restrictions.eq("subsurface", sub));
            criteria.add(Restrictions.eq("childJob", job));
            criteria.add(Restrictions.eq("dot", dot));
            
           
            result=criteria.list();
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        if(result.size()>=1){
            return result;
        }else {
            return null;
        
        }
    }

    @Override
    public List<Doubt> getDoubtFor(Sequence seq, Job job) {
         Session session=HibernateUtil_back.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Doubt> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Doubt.class);
            criteria.add(Restrictions.eq("sequence", seq));
            criteria.add(Restrictions.eq("childJob", job));
           // criteria.add(Restrictions.eq("dot", dot));
           
           
            result=criteria.list();
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        if(result.size()>=1){
            return result;
        }else {
            return null;
        
        }
    }

    @Override
    public List<Doubt> getDoubtFor(Sequence seq, Job childJob, DoubtType doubtType) {
         Session session=HibernateUtil_back.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Doubt> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Doubt.class);
            criteria.add(Restrictions.eq("sequence", seq));
            criteria.add(Restrictions.eq("childJob", childJob));
            criteria.add(Restrictions.eq("doubtType", doubtType));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
           
            result=criteria.list();
            
            
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        if(result.size()>=1){
            return result;
        }else {
            return null;
        
        }
    }

    @Override
    public Doubt getDoubtFor(Subsurface sub, Job job, Dot dot, Doubt cause, DoubtType doubtType) {
         Session session=HibernateUtil_back.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Doubt> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Doubt.class);
            criteria.add(Restrictions.eq("subsurface", sub));
            criteria.add(Restrictions.eq("childJob", job));
            criteria.add(Restrictions.eq("doubtType", doubtType));
            criteria.add(Restrictions.eq("dot", dot));
            criteria.add(Restrictions.eq("doubtCause", cause));
           criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
           
            result=criteria.list();
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        if(result.size()==1){
            return result.get(0);
        }else {
            return null;
        
        }
    }

    @Override
    public Doubt getDoubtFor(Subsurface sub, Job job, DoubtType doubtType) {
          Session session=HibernateUtil_back.getSessionFactory().openSession();
        Transaction transaction=null;
        List<Doubt> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Doubt.class);
            criteria.add(Restrictions.eq("subsurface", sub));
            criteria.add(Restrictions.eq("childJob", job));
            criteria.add(Restrictions.eq("doubtType", doubtType));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
           
           
            result=criteria.list();
            System.out.println("db.dao.DoubtDAOImpl.getDoubtFor(): returning a list of doubts of size: "+result.size()+" for "+sub.getSubsurface()+" job: "+job.getNameJobStep()+" doubttype: "+doubtType.getIdDoubtType()+" name: "+doubtType.getName());
                for(Doubt d:result){
                    System.out.println("id: "+d.getId()+" sub: "+d.getSubsurface().getId()+" job: "+d.getChildJob().getId()+" dbttype: "+d.getDoubtType().getIdDoubtType()+" -- "+d.getDoubtType().getName()+" dot: "+d.getDot().getId()+
                            " link: "+d.getLink().getId());
                }
            
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        if(result.size()==1){
            return result.get(0);
        }else {
            return null;
        
        }
    }

    @Override
    public List<Doubt> getInheritedDoubtFor(Subsurface sub, Job job) {
           Session session=HibernateUtil_back.getSessionFactory().openSession();
        Transaction transaction=null;
        DoubtTypeDAO dbtypeDao=new DoubtTypeDAOImpl();
        DoubtType inheritedDoubtType=dbtypeDao.getDoubtTypeByName(DoubtTypeModel.INHERIT);
        List<Doubt> result=null;
        try{
            transaction=session.beginTransaction();
            Criteria criteria=session.createCriteria(Doubt.class);
            criteria.add(Restrictions.eq("subsurface", sub));
            criteria.add(Restrictions.eq("childJob", job));
            criteria.add(Restrictions.eq("doubtType", inheritedDoubtType));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
           
           
            result=criteria.list();
            transaction.commit();
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return result;
    }

   
    
}
