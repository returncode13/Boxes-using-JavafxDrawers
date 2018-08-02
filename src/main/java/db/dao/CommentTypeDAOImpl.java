/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import app.connections.hibernate.HibernateUtil;
import db.model.CommentType;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sharath.nair@polarcus.com
 */
public class CommentTypeDAOImpl implements CommentTypeDAO {

    @Override
    public CommentType getCommentTypeByName(String type) {
        System.out.println("db.dao.CommentTypeDAOImpl.getCommentTypeByName()");
        Session session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=null;
        List<CommentType> results=null;
        String hql="Select c from CommentType c where c.type=:n";
        try{
            Query query=session.createQuery(hql);
            query.setParameter("n",type);
            results=query.list();
            
            if(results.isEmpty()) throw new Exception("no commenttype object with type "+type+" found");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return results.get(0);
    }
    
}
