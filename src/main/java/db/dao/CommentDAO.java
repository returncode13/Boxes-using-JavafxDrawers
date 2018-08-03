/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Job;
import db.model.Comment;
import db.model.CommentType;
import db.model.Sequence;
import db.model.Subsurface;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sharath.nair@polarcus.com
 */
public interface CommentDAO {
    public void createComment(Comment qcComment);
    public Comment getComment(Long id);
    public void updateComment(Comment qcComment);
    public void deleteComment(Comment qcComment);
    
    public void getCommentsFor(Job job,String type,Map<Sequence,List<Comment>> sequenceCommentsMap,Map<Subsurface,List<Comment>> subsurfaceCommentsMap);
    public void addToCommentStackFor(Sequence sequence,Subsurface sub,Job job, String comment,String type);

    public Comment getCommentFor(String TYPE, Job job, Sequence sequence, Subsurface subsurface) throws Exception;

    public void deleteAllCommentsRelatedToJob(Job job);

}
