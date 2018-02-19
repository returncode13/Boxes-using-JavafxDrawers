/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.AncestorDAOImpl;
import app.connections.hibernate.HibernateUtil_back;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import db.model.Ancestor;

import db.model.Job;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import db.dao.AncestorDAO;

/**
 *
 * @author sharath nair
 */
public class AncestorServiceImpl implements AncestorService{

    AncestorDAO ancDao = new AncestorDAOImpl();
   
    
    @Override
    public void addAncestor(Ancestor A) {
        ancDao.addAncestor(A);
    }

    @Override
    public Ancestor getAncestor(Long aid) {
        return ancDao.getAncestor(aid);
    }

    @Override
    public void updateAncestor(Long aid, Ancestor newAncestor) {
        ancDao.updateAncestor(aid, newAncestor);
    }

    @Override
    public void deleteAncestor(Long aid) {
        ancDao.deleteAncestor(aid);
    }
    
    @Override
    public void clearTableForJob(Job dbjob) {
        ancDao.clearTableForJob(dbjob);
    }
    
    @Override
    public Ancestor getAncestorFor(Job job, Job ancestor) {
        return ancDao.getAncestorFor(job, ancestor);
                
    }
    
    /*@Override
    public void getInitialAncestorListFor(SessionDetails s,Set<Long> listOfAncestor){
    System.out.println("db.services.AncestorServiceImpl.getInitialAncestorListFor: Asking me to find: "+s.getIdSessionDetails()+" which contains: "+s.getJobStep().getNameJobStep()+" inside session: "+s.getSessions().getNameSessions());
    List<Parent> parentList=getParentList();
    getInitialAncestorListFor(s,listOfAncestor,parentList);
    }
    */
    
    /* private List<Parent> getParentList(){
    Session sess = HibernateUtil.getSessionFactory().openSession();
    List<Parent> results=null;
    Transaction transaction=null;
    try{
    transaction=sess.beginTransaction();
    
    Criteria criteria=sess.createCriteria(Parent.class);
    System.out.println("db.services.AncestorServiceImpl.getParentList(): about to extract the whole list");
    results=criteria.list();                           //get all the Parent entries;
    for (Iterator<Parent> iterator = results.iterator(); iterator.hasNext();) {
    Parent next = iterator.next();
    System.out.println("db.services.AncestorServiceImpl.getParentList(): found Parent: id: "+next.getIdParent()+" SessionsDetails ID: "+next.getSessionDetails().getIdSessionDetails()+" ParentId: "+next.getParent());
    }
    transaction.commit();
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    sess.close();
    }
    return results;
    }
    
    
    
    private void getInitialAncestorListFor(SessionDetails s,Set<Long> listOfAncestor,List<Parent> parentList){
    List<Parent> shortList=new ArrayList<>();
    
    for (Iterator<Parent> iterator = parentList.iterator(); iterator.hasNext();) {
    Parent parent = iterator.next();
    if(parent.getSessionDetails().getIdSessionDetails().equals(s.getIdSessionDetails())){
    shortList.add(parent);                         //shortlist will now contain the list of all parents where FK=s .i.e list of all jobs belonging to the same session
    
    }
    }
    
    
    for (Parent ancs:shortList) {
    SessionDetails sdAnc=ssdServ.getSessionDetails(ancs.getParent());
    if(Objects.equals(s.getIdSessionDetails(), sdAnc.getIdSessionDetails()))
    {
    //System.out.println(s.getJobStep().getNameJobStep()+ ": root Or a standalone node...eitherways a root");
    System.out.println("db.services.AncestorServiceImpl.getInitialAncestorListFor()"+s.getJobStep().getNameJobStep()+ ": root Or a standalone node...eitherways a root");
    listOfAncestor.add(s.getIdSessionDetails());
    
    return;
    }
    listOfAncestor.add(s.getIdSessionDetails());
    getInitialAncestorListFor(sdAnc,listOfAncestor,parentList);
    }
    
    
    }
    */
    
    /*
    private void  getInitialAncestorListFor(SessionDetails s,Set<Long> listOfAncestor,Session session){
    
    if(session!=null && session.isOpen()){
    
    session.getTransaction().commit();
    session.close();
    
    
    }
    Session sess = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction=sess.beginTransaction();
    try{
    //Criteria criteria=sess.createCriteria(Ancestor.class);
    Criteria criteria=sess.createCriteria(Parent.class);
    criteria.add(Restrictions.eq("sessionDetails", s));                    //in the Parent select all records where FK=s; i.e list of all jobs belonging to the same session
    List<Parent> result=criteria.list();
    
    System.out.println("AncServ: resultSize: "+result.size());
    
    
    
    for(Parent ancs:result){
    // System.out.println("AncServ: About to ask for the Sessiondetails for ancestor id "+ancs.getAncestor());
    SessionDetails sdAnc=ssdServ.getSessionDetails(ancs.getParent());                           //remember that the "ancestor" column in the Ancestor Table is the Long id of a session detail as well. Both columns
    //are really session details in this table. Just the sessiondetails column truly refers to a SessionDetails object (for foreign key)
    //while the ancestor column holds the id of the immediate parent SessionDetails of the first columns sessionDetail object.
    
    //so if 1<--2 then the table will contain an entry where the sessionDetails object =1 and its ancestor =2.id
    
    //  System.out.println("AncServ: inloop sdAnc:=Id: "+sdAnc.getIdSessionDetails());
    if(Objects.equals(s.getIdSessionDetails(), sdAnc.getIdSessionDetails()))
    {
    System.out.println("root Or a standalone node...eitherways a root");
    listOfAncestor.add(s.getIdSessionDetails());
    
    return;
    }
    listOfAncestor.add(s.getIdSessionDetails());
    
    getInitialAncestorListFor(sdAnc,listOfAncestor,sess);
    
    
    }
    if(sess.isOpen() && sess.isConnected())
    transaction.commit();
    
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    if(sess.isOpen())sess.close();
    }
    //list.add(s);
    
    }*/

    /*  @Override
    public void makeAncestorTableFor(Job job,Set<Job> listOfAncestor)
    {
    Session session = HibernateUtil.getSessionFactory().openSession();
    
    try{
    
    Criteria criteria=session.createCriteria(Ancestor.class);
    criteria.add(Restrictions.eq("job", job));
    List results=criteria.list();
    
    
    if(results.size()>0){
    
    Transaction transaction=session.beginTransaction();
    for (Iterator iterator = results.iterator(); iterator.hasNext();) {
    Ancestor next = (Ancestor) iterator.next();
    session.delete(next);
    
    }
    transaction.commit();
    
    
    }
    
    Transaction transaction=session.beginTransaction();
    
    for (Iterator<Job> iterator = listOfAncestor.iterator(); iterator.hasNext();) {
    Job next = iterator.next();
    Ancestor anc=new Ancestor();
    anc.setJob(job);
    anc.setAncestor(next);
    
    session.save(anc);
    }
    
    transaction.commit();
    
    
    }catch(Exception e){
    e.printStackTrace();
    }finally{
    session.close();
    }
    
    }*/
      
      
  
      /*
      @Override
      public void getAncestorFor(SessionDetails fkid, Set<Long> listOfAncestor) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }
      
      @Override
      public Ancestor getAncestorFor(SessionDetails fkid, Long ancestor) {
      return ancDao.getAncestorFor(fkid, ancestor);
      }*/

    @Override
    public List<Ancestor> getAncestorFor(Job job) {
        return ancDao.getAncestorFor(job);
    }

    

    
   
    
}
