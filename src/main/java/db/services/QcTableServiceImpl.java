/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.QcTableDAOImpl;
import db.model.QcTable;

import java.util.List;
import db.dao.QcTableDAO;
import db.model.Job;
import db.model.QcMatrixRow;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.User;
import db.model.Workspace;
import java.util.Map;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTableServiceImpl implements QcTableService{
    
    QcTableDAO qctDAO=new QcTableDAOImpl();
    
    
    @Override
    public void createQcTable(QcTable qcm) {
        qctDAO.createQcTable(qcm);
    }

    @Override
    public QcTable getQcTable(Long qid) {
        return qctDAO.getQcTable(qid);
    }

    @Override
    public void updateQcTable(Long qid, QcTable newQ) {
        qctDAO.updateQcTable(qid, newQ);
        
    }

    @Override
    public void deleteQcTable(Long qid) {
        qctDAO.deleteQcTable(qid);
    }

    /*@Override
    public List<QcTable> getQcTableFor(Volume v) {
    return qctDAO.getQcTableFor(v);
    }
    
    @Override
    public List<QcTable> getQcTableFor(Volume v, QcType qctype) {
    return qctDAO.getQcTableFor(v, qctype);
    }*/

    @Override
    public List<QcTable> getQcTableFor(QcMatrixRow qmx) {
        return qctDAO.getQcTableFor(qmx);
    }

    @Override
    public List<QcTable> getQcTableFor(Subsurface h) {
        return qctDAO.getQcTableFor(h);
    }

    @Override
    public QcTable getQcTableFor(QcMatrixRow qmx, Subsurface h)  throws Exception{
        return qctDAO.getQcTableFor(qmx, h);
    }

    @Override
    public QcTable getQcTableFor(Long qcmatrixRowId, Subsurface s) throws Exception {
         return qctDAO.getQcTableFor(qcmatrixRowId, s);
    }

    @Override
    public void deleteAllQcTablesForJob(Job job) {
        qctDAO.deleteAllQcTablesForJob(job);
    }

    @Override
    public int getQcTablesFor(Job parentJob, Map<Long, Map<Subsurface, QcTable>> qcmatrixRowSubQcTableMap,Boolean present) {
        return qctDAO.getQcTablesFor(parentJob,qcmatrixRowSubQcTableMap,present);
    }

    @Override
    public int update(Long idOfQcMatrix, Subsurface sub, Boolean result, String updateTime, User currentUser) {
        return qctDAO.update(idOfQcMatrix,  sub, result,  updateTime,currentUser);
    }

    @Override
    public void setAllqcTableValuesFor(Sequence seq, Job j,Long qcmatrixId, Boolean result, String updateTime, User currentUser) {
        qctDAO.setAllqcTableValuesFor(seq, j,qcmatrixId, result, updateTime, currentUser);
    }

    @Override
    public void createBulkQcTables(List<QcTable> qctables) {
        qctDAO.createBulkQcTables(qctables);
    }

    @Override
    public Map<Job, Map<Subsurface, List<QcTable>>> getQcTablesOnLeafJobsFor(Workspace w) {
        return qctDAO.getQcTablesOnLeafJobsFor(w);
    }

    @Override
    public Map<Job, Map<Subsurface, List<QcTable>>> getUpdatedQcTablesFor(Workspace w) {
        return qctDAO.getUpdatedQcTablesFor(w);
    }

    @Override
    public Map<Job, Map<Subsurface, List<QcTable>>> getAllQcTablesFor(Workspace w) {
        return qctDAO.getQcTablesFor(w);
    }
    
    

   
    
}
