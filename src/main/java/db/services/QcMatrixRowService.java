/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Job;
import db.model.QcMatrixRow;
import db.model.QcType;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface QcMatrixRowService {
    public void createQcMatrixRow(QcMatrixRow qcmatrix);
    public void updateQcMatrixRow(Long qid,QcMatrixRow newq);
    public QcMatrixRow getQcMatrixRow(Long qid);
    public void deleteQcMatrixRow(Long qid);
    
   // public List<QcMatrix> getQcMatrixForVolume(Volume v);    //get all the fields for volume v where present="true"
    
    public List<QcMatrixRow> getQcMatrixForJob(Job sd);
    public QcMatrixRow getQcMatrixRowFor(Job sd,QcType qctype) throws Exception;

    public List<QcMatrixRow> getQcMatrixForJob(Job job, boolean b);

    public void updatePresent(Long id,Boolean val);

    public void deleteAllQcMatrixRowsForJob(Job dbjob);

    public List<String> getQcMatrixRowNamesForJob(Job job);
}
