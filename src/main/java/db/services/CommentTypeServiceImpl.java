/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import app.connections.hibernate.HibernateUtil;
import db.dao.CommentTypeDAO;
import db.dao.CommentTypeDAOImpl;
import db.model.CommentType;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sharath.nair@polarcus.com
 */
public class CommentTypeServiceImpl implements CommentTypeService{

    CommentTypeDAO cDao=new CommentTypeDAOImpl();
    
    
    @Override
    public CommentType getCommentTypeByName(String type) {
        return cDao.getCommentTypeByName(type);
    }

    @Override
    public void createCommentType(CommentType c) {
        cDao.createCommentType(c);
    }
    
}
