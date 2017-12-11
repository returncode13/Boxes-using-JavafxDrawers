/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Header;
import db.model.QcMatrixRow;
import db.model.QcTable;
import db.model.QcType;
import db.model.Subsurface;
import db.model.Volume;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface QcTableService {
    public void createQcTable(QcTable qcm);
    public QcTable getQcTable(Long qid);
    public void updateQcTable(Long qid,QcTable newQ);
    public void deleteQcTable(Long qid);
    
    /*public List<QcTable> getQcTableFor(Volume v);
    public List<QcTable> getQcTableFor(Volume v,QcType qctype);               //for column wise retrieval*/
     public List<QcTable> getQcTableFor(QcMatrixRow qmx);
    public List<QcTable> getQcTableFor(Subsurface s);
    public QcTable getQcTableFor(QcMatrixRow qmx,Subsurface s) throws Exception;
}
