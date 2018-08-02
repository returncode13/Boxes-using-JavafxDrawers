/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.CommentType;

/**
 *
 * @author sharath.nair@polarcus.com
 */
public interface CommentTypeDAO {
    public CommentType getCommentTypeByName(String type);
}
