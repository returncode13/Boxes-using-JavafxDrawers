/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.CommentType;

/**
 *
 * @author sharath.nair@polarcus.com
 */
public interface CommentTypeService {
    public CommentType getCommentTypeByName(String type);

    public void createCommentType(CommentType qccommentType);
}
