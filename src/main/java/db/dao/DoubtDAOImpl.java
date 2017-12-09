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
import app.connections.hibernate.HibernateUtil;
import java.util.List;
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
        Session session=HibernateUtil.getSessionFactory().openSession();
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
         Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
            Doubt ll=(Doubt) session.get(Doubt.class,dsid);
            ll.setDoubtType(newds.getDoubtType());
            ll.setErrorMessage(newds.getErrorMessage());
            //ll.setHeaders(newds.getHeaders());
            //ll.setParentSessionDetails(newds.getParentSessionDetails());
            ll.setSubsurface(newds.getSubsurface());
            ll.setLink(newds.getLink());
            ll.setStatus(newds.getStatus());
            ll.setUser(newds.getUser());
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
        Session session = HibernateUtil.getSessionFactory().openSession();
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
        Session session = HibernateUtil.getSessionFactory().openSession();
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

    
     @Override
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
    }
    
    
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

   
    
}
