/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.CommentTypeDAO;
import db.dao.CommentTypeDAOImpl;
import db.model.CommentType;

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
    
}
