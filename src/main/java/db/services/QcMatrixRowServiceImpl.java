/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.QcMatrixRowDAOImpl;
import db.model.Job;
import db.model.QcMatrixRow;
import db.model.QcType;

import java.util.List;
import db.dao.QcMatrixRowDAO;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixRowServiceImpl implements QcMatrixRowService{
    QcMatrixRowDAO qcmDao=new QcMatrixRowDAOImpl();
    
    @Override
    public void createQcMatrixRow(QcMatrixRow qcmatrix) {
        qcmDao.createQcMatrixRow(qcmatrix);
    }

    @Override
    public void updateQcMatrixRow(Long qid, QcMatrixRow newq) {
        qcmDao.updateQcMatrixRow(qid, newq);
    }

    @Override
    public QcMatrixRow getQcMatrixRow(Long qid) {
        return qcmDao.getQcMatrixRow(qid);
    }

    @Override
    public void deleteQcMatrixRow(Long qid) {
        qcmDao.deleteQcMatrixRow(qid);
    }

    /*@Override
    public List<QcMatrix> getQcMatrixForVolume(Volume v) {
    return qcmDao.getQcMatrixForVolume(v);
    }*/

    

    @Override
    public List<QcMatrixRow> getQcMatrixForJob(Job sd) {
        return qcmDao.getQcMatrixForJob(sd);
    }

    @Override
    public QcMatrixRow getQcMatrixRowFor(Job sd, QcType qctype) throws Exception {
        return qcmDao.getQcMatrixRowFor(sd, qctype);
    }

    @Override
    public List<QcMatrixRow> getQcMatrixForJob(Job job, boolean b) {
        return qcmDao.getQcMatrixForJob(job, b);
    }

    @Override
    public void updatePresent(Long id, Boolean val) {
        qcmDao.updatePresent(id,val);
    }

    @Override
    public void deleteAllQcMatrixRowsForJob(Job job) {
        qcmDao.deleteAllQcMatrixRowsForJob(job);
    }
    
}
