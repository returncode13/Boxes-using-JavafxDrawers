/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable;

import app.properties.AppProperties;
import db.model.Comment;
import db.model.Job;
import db.model.QcMatrixRow;
import db.model.QcTable;
import db.model.Sequence;
import db.model.Subsurface;
import db.services.CommentService;
import db.services.CommentServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.QcMatrixRowService;
import db.services.QcMatrixRowServiceImpl;
import db.services.QcTableService;
import db.services.QcTableServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import fend.comments.CommentTypeModel;
import fend.job.job0.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;
import fend.job.job0.JobType0Model;
import fend.job.table.qctable.seq.QcTableSequence;
import fend.job.table.qctable.seq.sub.QcTableSubsurface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * 
 * 
 * build a list of QctableSequence for supply to the Qctable
 */
public class QcTableModel {
    
    
    
    private Job dbJob;
    private JobType0Model fejob;
     private ObservableList<QcTableSequence> qctableSequences;
    private JobService jobService=new JobServiceImpl();
    private QcMatrixRowService qcMatrixRowService=new QcMatrixRowServiceImpl();
    private SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();
    private CommentService commentService=new CommentServiceImpl();
    private BooleanProperty qcSelectionChanged=new SimpleBooleanProperty(false);
    private Map<Sequence,List<Comment>> sequenceComments=new HashMap<>();
    private Map<Subsurface,List<Comment>> subsurfaceComments=new HashMap<>();
    private Map<Long,Map<Subsurface,QcTable>> qcmatrixRowSubQcTableMap=new HashMap<>();
    private QcTableService qcTableService=new QcTableServiceImpl();
    private BooleanProperty reloadSequencesProperty=new SimpleBooleanProperty(false);

    public BooleanProperty reloadSequencesProperty() {
        return reloadSequencesProperty;
    }
    
    
    
    
    public void reloadSequences(){
        reloadSequencesProperty.set(!reloadSequencesProperty.get());
    }
    /*public BooleanProperty qcSelectionChangedProperty(){
    return qcSelectionChanged;
    }
    
    public void userHasChangedQcSelection(){
    
    Boolean val=qcSelectionChanged.get();
    qcSelectionChanged.set(!val);
    }*/
    
    public QcTableModel(JobType0Model fejob) {
        this.fejob = fejob;
        dbJob=jobService.getJob(this.fejob.getId());
        qctableSequences=FXCollections.observableArrayList();
        List<QcMatrixRow> qcmatrixForJob=qcMatrixRowService.getQcMatrixForJob(dbJob, true);
       // System.out.println("fend.job.table.qctable.QcTableModel.<init>(): size of qcmatrix for job: "+dbJob.getId()+" is "+qcmatrixForJob.size());
        List<QcMatrixRowModelParent> feqcmr=new ArrayList<>();
        
        System.out.println("fend.job.table.qctable.QcTableModel.<init>(): Loading all comments for the job: "+dbJob.getNameJobStep());
        commentService.getCommentsFor(dbJob,CommentTypeModel.TYPE_QC,sequenceComments, subsurfaceComments);
        Map<Sequence,List<QcTableSequence>> lookupmap=new HashMap<>();  //all subsurfaces grouped under the sequence key
        
         Set<Subsurface> subsinJob=new HashSet<>(subsurfaceService.getSubsurfacesPresentInJob(dbJob));
         qcmatrixRowSubQcTableMap.clear();
         System.out.println("fend.job.table.qctable.QcTableModel.<init>(): loading the qctable for the job");
        int existingSize=qcTableService.getQcTablesFor(dbJob,qcmatrixRowSubQcTableMap,true);
        
        
         Map<Long,QcMatrixRow> qcmrlookup=new HashMap<>();
        
        for(QcMatrixRow qcmrow:qcmatrixForJob){
            
            QcMatrixRowModelParent femod=new QcMatrixRowModelParent();
            femod.setId(qcmrow.getId());
            femod.setName(qcmrow.getQctype().getName());
            femod.setQctype(qcmrow.getQctype());
            feqcmr.add(femod);
            qcmrlookup.put(qcmrow.getId(),qcmrow);
         //   System.out.println("fend.job.table.qctable.QcTableModel.<init>() created and added new QcMatrixRowModelParent with id: "+femod.getId()+" name: "+femod.getName().get());
        }
        
       
        
       List<QcTable> qctablesToCreate=new ArrayList<>();
       Map<QcMatrixRow,Set<Subsurface>> tempMapForqcTables=new HashMap<>();
       //int existingsize=((HashMap<?,?>)(qcmatrixRowSubQcTableMap.values())).values().size();
       
       
       
        if(qcmatrixForJob.size()*subsinJob.size() == existingSize){    //i.e all the qcmatrices have subs (i.e. qctable entries in the database 
            System.out.println("fend.job.table.qctable.QcTableModel.<init>():  all the qcmatrices have subs qcmatrixForJob.size()*subsinJob.size() = qcmatrixRowSubQcTableMap.values().size() ==> "+qcmatrixForJob.size()+"*"+subsinJob.size() +
                    "="+existingSize);
        }else{
            System.out.println("fend.job.table.qctable.QcTableModel.<init>(): Size mismatch qcmatrixForJob.size()*subsinJob.size() = qcmatrixRowSubQcTableMap.values().size() ==> "+qcmatrixForJob.size()+"*"+subsinJob.size() +
                    "="+existingSize);
           
             for (Map.Entry<Long, Map<Subsurface, QcTable>> entry : qcmatrixRowSubQcTableMap.entrySet()) {
                Long key = entry.getKey();

                if(qcmrlookup.containsKey(key)){
                    Set<Subsurface> subsNotPresentInTable=new HashSet<>(subsinJob);
                    Map<Subsurface, QcTable> value = entry.getValue();
                    subsNotPresentInTable.removeAll(value.keySet());
                    System.out.println("fend.job.table.qctable.QcTableModel.<init>(): "
                    +subsNotPresentInTable
                    .size()+""
                    + " subs are missing qctable entries for qcmatrix: "+
                    qcmrlookup
                    .get(key)
                    .getQctype()
                    .getName());
                    tempMapForqcTables.put(qcmrlookup.get(key), subsNotPresentInTable);


                    for(Subsurface s:subsNotPresentInTable){
                        QcTable qct=new QcTable();
                        qct.setQcMatrixRow(qcmrlookup.get(key));
                        qct.setSubsurface(s);
                        qct.setUpdateTime(AppProperties.timeNow());
                        qct.setResult(Boolean.FALSE);
                        qct.setUser(AppProperties.getCurrentUser());
                        qctablesToCreate.add(qct);
                    }
                    
                    qcmrlookup.remove(key);
                }
                
                
            
            }
             //have all the keys been accounted for
                if(qcmrlookup.isEmpty()){
                    System.out.println("fend.job.table.qctable.QcTableModel.<init>(): all qcmatrices accounted for");
                }else{
                    for (Map.Entry<Long, QcMatrixRow> entry1 : qcmrlookup.entrySet()) {
                        Long newKey = entry1.getKey();
                        QcMatrixRow value = entry1.getValue();
                        System.out.println("fend.job.table.qctable.QcTableModel.<init>(): "+value.getQctype().getName()+" has  no qctable entries");
                        for(Subsurface s: subsinJob){
                            QcTable qct=new QcTable();
                            qct.setQcMatrixRow(qcmrlookup.get(newKey));
                            qct.setSubsurface(s);
                            qct.setUpdateTime(AppProperties.timeNow());
                            qct.setResult(Boolean.FALSE);
                            qct.setUser(AppProperties.getCurrentUser());
                            qctablesToCreate.add(qct);
                        }
                        
                    }
                }
            
            System.out.println("fend.job.table.qctable.QcTableModel.<init>(): Creating "+qctablesToCreate.size()+" qctables");
            qcTableService.createBulkQcTables(qctablesToCreate);
            
            
             
        }
        
       
        
        
        //Set<Subsurface> subsinJob=dbJob.getSubsurfaces();
       
        System.out.println("fend.job.table.qctable.QcTableModel.<init>(): starting to build the lookup map");
     //   System.out.println("fend.job.table.qctable.QcTableModel.<init>(): size of subs from job: "+dbJob.getId()+" size: "+subsinJob.size());
        for(Subsurface s:subsinJob){
            Sequence seq=s.getSequence();
            
          //  System.out.println("fend.job.table.qctable.QcTableModel.<init>(): found seq: "+seq.getSequenceno());
            
            QcTableSubsurface qctableSubsurface=new QcTableSubsurface();
            
            qctableSubsurface.setSequence(seq);
            qctableSubsurface.setSubsurface(s);
            if(subsurfaceComments.containsKey(s)){
                qctableSubsurface.setQcComment(subsurfaceComments.get(s).get(0));
            }
           
            
            if(!lookupmap.containsKey(seq)){
                List<QcTableSequence> ql=new ArrayList<>();
             //   System.out.println("fend.job.table.qctable.QcTableModel.<init>(): was NULL and creating a new list  to the lookup map "+qctableSubsurface.getSubsurface().getSubsurface());
                ql.add(qctableSubsurface);
                lookupmap.put(seq, ql);
            }else{
              //  System.out.println("fend.job.table.qctable.QcTableModel.<init>(): adding a new entry to the lookup map "+qctableSubsurface.getSubsurface().getSubsurface());
                lookupmap.get(seq).add(qctableSubsurface);
            }
        }
        
        
        
        
        
        System.out.println("fend.job.table.qctable.QcTableModel.<init>(): finished building the lookup map");
        for (Map.Entry<Sequence, List<QcTableSequence>> entry : lookupmap.entrySet()) {
            Sequence seq = entry.getKey();
            List<QcTableSequence> subs = entry.getValue();
            ObservableList<QcTableSequence> obssubs=FXCollections.observableArrayList(subs);
           // System.out.println("fend.job.table.qctable.QcTableModel.<init>(): new Sequence root added: ");
            QcTableSequence seqtreeroot=new QcTableSequence();
            seqtreeroot.setChildren(obssubs);
            seqtreeroot.setQcmatrix(feqcmr,qcmatrixRowSubQcTableMap);      //the qc matrix are set for the subs in this call
            seqtreeroot.setSequence(seq);
            if(sequenceComments.containsKey(seq)){
                seqtreeroot.setQcComment(sequenceComments.get(seq).get(0));
            }
            //System.out.println("fend.job.table.qctable.QcTableModel.<init>(): starting to build for seq: "+seq.getSequenceno());
           // seqtreeroot.setIsParent(true);
            for(QcTableSequence sub:obssubs){
                sub.setParent(seqtreeroot);
            }
           // System.out.println("fend.job.table.qctable.QcTableModel.<init>(): finished building for seq: "+seq.getSequenceno());
            
            qctableSequences.add(seqtreeroot);      //each entry can now be rendered as seq.Long   sub.string   qcmatrix[1].check/Uncheck/Indeterminate  qcmatrix[2].check/Uncheck/Indeterminate... qcmatrix[n].check/uncheck/indeteminate
        }
        System.out.println("fend.job.table.qctable.QcTableModel.<init>(): added the seqs to the qctableSequences List");
      //  System.out.println("fend.job.table.qctable.QcTableModel.<init>() qctableSequences is of size(): "+qctableSequences.size());
    }
    
    
    
    
    
    
    
   

    public ObservableList<QcTableSequence> getQctableSequences() {
        return qctableSequences;
    }

    public void setQctableSequences(List<QcTableSequence> qctableSequences) {
        this.qctableSequences=FXCollections.observableArrayList(qctableSequences);
    }

    public Job getDbJob() {
        return dbJob;
    }

    public JobType0Model getJob() {
        return fejob;
    }
    
    
    
    
    
    
            
    
}
