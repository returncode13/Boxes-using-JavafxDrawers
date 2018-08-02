/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.Job;
import db.model.Comment;
import db.model.CommentType;
import db.model.Sequence;
import db.model.Subsurface;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sharath.nair@polarcus.com
 */
public class CommentDAOImpl implements CommentDAO{

    @Override
    public void createComment(Comment qcComment) {
        System.out.println("db.dao.QcCommentDAOImpl.createQCComment()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        try{
            transaction=session.beginTransaction();
            session.saveOrUpdate(qcComment);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
    }

    @Override
    public Comment getComment(Long id) {
        Session session=HibernateUtil.getSessionFactory().openSession();
        Comment qcc=null;
        try{
            qcc=(Comment) session.get(Comment.class, id);
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        return qcc;
    }

    @Override
    public void updateComment(Comment comment) {
        System.out.println("db.dao.CommentDAOImpl.updateQcComment()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
         String updateSql="update Comment  set comments =:n where id =:m";
         
        try{
                                    transaction=session.beginTransaction();
                                  Query query=session.createQuery(updateSql);
                                  query.setParameter("m", comment.getId());
                                  query.setParameter("n", comment.getComments());
                                  int result=query.executeUpdate();
                                  transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void deleteComment(Comment qcComment) {
        System.out.println("db.dao.QcCommentDAOImpl.deleteQcComment()");
             Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction=session.beginTransaction();
           // QcComment h= (QcComment) session.get(QcComment.class, qcComment.getId());
            session.delete(qcComment);
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void getCommentsFor(Job j,String type,Map<Sequence, List<Comment>> sequenceCommentsMap, Map<Subsurface, List<Comment>> subsurfaceCommentsMap) {
     
        System.out.println("db.dao.QcCommentDAOImpl.getQcCommentsFor()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        
        List<Comment> results=null;
        String seqcommentsHql="select qcc from Comment qcc "
                + "                             INNER JOIN qcc.job jb"
                + "                             INNER JOIN qcc.commentType ctype"
                + "                              where jb =:jb AND ctype.type =:t AND qcc.comments is not empty";
        
        try{
            transaction=session.beginTransaction();
            Query query=session.createQuery(seqcommentsHql);
            query.setParameter("jb", j);
            query.setParameter("t", type);
            
            results=query.list();
            transaction.commit();
            System.out.println("db.dao.QcCommentDAOImpl.getQcCommentsFor(): returning results.size(): "+results.size());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        for(Comment q:results){
            if(q.getSubsurface()==null){
                if(!sequenceCommentsMap.containsKey(q.getSequence())){
                    sequenceCommentsMap.put(q.getSequence(), new ArrayList<>());
                    sequenceCommentsMap.get(q.getSequence()).add(q);
                }else{
                    sequenceCommentsMap.get(q.getSequence()).add(q);
                }
            }else{
                if(!subsurfaceCommentsMap.containsKey(q.getSubsurface())){
                    subsurfaceCommentsMap.put(q.getSubsurface(), new ArrayList<>());
                    subsurfaceCommentsMap.get(q.getSubsurface()).add(q);
                }else{
                    subsurfaceCommentsMap.get(q.getSubsurface()).add(q);
                }
            }
            
        }
        
        
    }

    @Override
    public void addToCommentStackFor(Sequence sequence,Subsurface sub,Job job, String comment,String type) {
        
             System.out.println("db.dao.CommentDAOImpl.addToCommentStackFor()");
            Session session=HibernateUtil.getSessionFactory().openSession();
            Transaction transaction=null;
            String selectSubComment="select q.id from Comment q "
                    + "                             INNER JOIN q.subsurface sb "
                    + "                             INNER JOIN q.job jb "
                    + "                             INNER JOIN q.commentType c where c.type =:t AND jb =:j AND sb =:sub ";
            
            
            
             String selectSeqComment="select q.id from Comment q "
                    + "                             INNER JOIN q.sequence sq"
                    + "                             INNER JOIN q.job jb "
                    + "                             INNER JOIN q.commentType c where c.type =:t AND jb =:j AND q.subsurface is null and sq =:seq ";
            
            
            
            
            
            
            String updateSql="update Comment set comments =:n where id in (:ids)";   
            
          if(sub!=null){
                    try{
                          transaction=session.beginTransaction();

                          Query query=session.createQuery(selectSubComment);
                          query.setParameter("sub", sub);
                          query.setParameter("j", job);
                          query.setParameter("t",type);
                             List<Long> qids=query.list();
                             if(!qids.isEmpty()){
                                 Query query1=session.createQuery(updateSql);
                                  query1.setParameterList("ids", qids);
                                  query1.setParameter("n", comment);
                                  int result=query1.executeUpdate();
                             }

                          transaction.commit();

                          }catch(Exception e){
                              e.printStackTrace();
                          }finally{
                              session.close();
                  }
          }else{
              
                    try{
                          transaction=session.beginTransaction();

                          Query query=session.createQuery(selectSeqComment);
                          query.setParameter("seq", sequence);
                          query.setParameter("j", job);
                          query.setParameter("t",type);
                             List<Long> qids=query.list();
                             if(!qids.isEmpty()){
                                 Query query1=session.createQuery(updateSql);
                                  query1.setParameterList("ids", qids);
                                  query1.setParameter("n", comment);
                                  int result=query1.executeUpdate();
                             }

                          transaction.commit();

                          }catch(Exception e){
                              e.printStackTrace();
                          }finally{
                              session.close();
                  }
              
              
              
          }    
        
        
        
        
        
    }

    @Override
    public Comment getCommentFor(String TYPE, Job job, Sequence sequence, Subsurface subsurface) throws Exception{
        System.out.println("db.dao.CommentDAOImpl.getCommentFor()");
            Session session=HibernateUtil.getSessionFactory().openSession();
            Transaction transaction=null;
            String selectSeqComment="Select c from Comment c INNER JOIN c.job j"
                    + "                                      INNER JOIN c.sequence s"
                    + "                                      INNER JOIN c.commentType ctype"
                    + "                                      WHERE j=:job AND s=:seq AND ctype.type=:ty AND c.subsurface is null";     
           
            String selectSubComment="Select c from Comment c INNER JOIN c.job j"
                    + "                                      INNER JOIN c.sequence s"
                    + "                                      INNER JOIN c.subsurface su"
                    + "                                      INNER JOIN c.commentType ctype"
                    + "                                      WHERE j=:job AND s=:seq AND ctype.type=:ty AND su=:sub";     
            List<Comment> results=null;
            
            
            if(subsurface==null){
                        try{
                    transaction=session.beginTransaction();

                    Query query=session.createQuery(selectSeqComment);
                    query.setParameter("job", job);
                    query.setParameter("seq", sequence);
                    query.setParameter("ty", TYPE);
                    
                    results=query.list();
                    

                    transaction.commit();

                    }catch(Exception e){
                        e.printStackTrace();
                    }finally{
                        session.close();
                    }
                        
                        
            }else{
                
                try{
                   transaction=session.beginTransaction();

                    Query query=session.createQuery(selectSubComment);
                    query.setParameter("job", job);
                    query.setParameter("seq", sequence);
                    query.setParameter("sub", subsurface);
                    query.setParameter("ty", TYPE);
                    
                    results=query.list();
                    

                    transaction.commit();

                    }catch(Exception e){
                        e.printStackTrace();
                    }finally{
                        session.close();
                    }
            }
            
            if(results.isEmpty()) return null;
            if(results.size()>1) throw new Exception("more than one comments found for ");
            else
                return results.get(0);
        
    }
    
}
