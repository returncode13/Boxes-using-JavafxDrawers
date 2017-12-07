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
import db.model.QcMatrix;
import db.model.Subsurface;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTableServiceImpl implements QcTableService{
    
    QcTableDAO qcmDAO=new QcTableDAOImpl();
    
    @Override
    public void createQcTable(QcTable qcm) {
        qcmDAO.createQcTable(qcm);
    }

    @Override
    public QcTable getQcTable(Long qid) {
        return qcmDAO.getQcTable(qid);
    }

    @Override
    public void updateQcTable(Long qid, QcTable newQ) {
        qcmDAO.updateQcTable(qid, newQ);
    }

    @Override
    public void deleteQcTable(Long qid) {
        qcmDAO.deleteQcTable(qid);
    }

    /*@Override
    public List<QcTable> getQcTableFor(Volume v) {
    return qcmDAO.getQcTableFor(v);
    }
    
    @Override
    public List<QcTable> getQcTableFor(Volume v, QcType qctype) {
    return qcmDAO.getQcTableFor(v, qctype);
    }*/

    @Override
    public List<QcTable> getQcTableFor(QcMatrix qmx) {
        return qcmDAO.getQcTableFor(qmx);
    }

    @Override
    public List<QcTable> getQcTableFor(Subsurface h) {
        return qcmDAO.getQcTableFor(h);
    }

    @Override
    public QcTable getQcTableFor(QcMatrix qmx, Subsurface h)  throws Exception{
        return qcmDAO.getQcTableFor(qmx, h);
    }
    
    

   
    
}
