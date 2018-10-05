/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Header;
import db.model.Job;
import db.model.QcMatrixRow;
import db.model.QcTable;
import db.model.QcType;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.User;
import db.model.Volume;
import db.model.Workspace;
import java.util.List;
import java.util.Map;

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
    public QcTable getQcTableFor(Long qcmatrixRowId,Subsurface s) throws Exception;

    public void deleteAllQcTablesForJob(Job dbjob);

    public int getQcTablesFor(Job parentJob, Map<Long, Map<Subsurface, QcTable>> qcmatrixRowSubQcTableMap,Boolean present);

    public int update(Long qcmrId, Subsurface childsub, Boolean resForDb, String updateTime, User currentUser);
    public void setAllqcTableValuesFor(Sequence seq,Job j,Long qcmatrixId,Boolean result,String updateTime, User currentUser);
    public void createBulkQcTables(List<QcTable> qctables);
    public  Map<Job,Map<Subsurface,List<QcTable>>> getQcTablesOnLeafJobsFor(Workspace w);
     public  Map<Job,Map<Subsurface,List<QcTable>>> getUpdatedQcTablesFor(Workspace w);  //all tables for subs where update>summary
     public Map<Job, Map<Subsurface, List<QcTable>>> getAllQcTablesFor(Workspace w); //all tables for workspace;
}
