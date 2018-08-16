/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.CommentDAOImpl;
import db.model.Job;
import db.model.Comment;
import db.model.Sequence;
import db.model.Subsurface;
import java.util.List;
import java.util.Map;
import db.dao.CommentDAO;
import db.model.CommentType;
import db.model.Workflow;

/**
 *
 * @author sharath.nair@polarcus.com
 */
public class CommentServiceImpl implements CommentService {
    private CommentDAO qccDao=new CommentDAOImpl();
    
    @Override
    public void createComment(Comment qcComment) {
            qccDao.createComment(qcComment);
    }

    @Override
    public Comment getComment(Long id) {
        return qccDao.getComment(id);
    }

    @Override
    public void updateComment(Comment qcComment) {
        qccDao.updateComment(qcComment);
    }
    
    
    @Override
    public void deleteComment(Comment qcComment) {
            qccDao.deleteComment(qcComment);
    }

    @Override
    public void getCommentsFor(Job job,String type, Map<Sequence, List<Comment>> sequenceCommentsMap, Map<Subsurface, List<Comment>> subsurfaceCommentsMap) {
            qccDao.getCommentsFor(job,type, sequenceCommentsMap, subsurfaceCommentsMap);
    }

   

    @Override
    public Comment getCommentFor(String TYPE, Job job, Sequence sequence, Subsurface subsurface) throws Exception {
       return  qccDao.getCommentFor(TYPE,job, sequence,subsurface);
    }

    @Override
    public void addToCommentStackFor(Sequence sequence, Subsurface sub, Job job, String comment, String type) {
        qccDao.addToCommentStackFor(sequence,sub, job, comment, type);
    }

    @Override
    public void deleteAllCommentsRelatedToJob(Job job) {
        qccDao.deleteAllCommentsRelatedToJob(job);
    }

    @Override
    public void getCommentsFor(Job job, String TYPE, Map<Workflow, List<Comment>> map) {
        qccDao.getCommentFor(job,TYPE,map);
    }

    @Override
    public Comment getCommentFor(String TYPE_WORKFLOW, Job job, Workflow workflow) {
        return qccDao.getCommentFor(TYPE_WORKFLOW, job, workflow);
    }

   
    
}
