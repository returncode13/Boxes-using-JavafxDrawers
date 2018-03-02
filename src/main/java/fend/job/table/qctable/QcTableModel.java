/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable;

import db.model.Job;
import db.model.QcMatrixRow;
import db.model.Sequence;
import db.model.Subsurface;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.QcMatrixRowService;
import db.services.QcMatrixRowServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
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
    
    
    
    private Job parentJob;
    private JobType0Model fejob;
     private ObservableList<QcTableSequence> qctableSequences;
    private JobService jobService=new JobServiceImpl();
    private QcMatrixRowService qcMatrixRowService=new QcMatrixRowServiceImpl();
    private SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();

    public QcTableModel(JobType0Model fejob) {
        this.fejob = fejob;
        parentJob=jobService.getJob(this.fejob.getId());
        qctableSequences=FXCollections.observableArrayList();
        List<QcMatrixRow> qcmatrixForJob=qcMatrixRowService.getQcMatrixForJob(parentJob, true);
       // System.out.println("fend.job.table.qctable.QcTableModel.<init>(): size of qcmatrix for job: "+parentJob.getId()+" is "+qcmatrixForJob.size());
        List<QcMatrixRowModelParent> feqcmr=new ArrayList<>();
        
       
        Map<Sequence,List<QcTableSequence>> lookupmap=new HashMap<>();  //all subsurfaces grouped under the sequence key
        
        for(QcMatrixRow qcmrow:qcmatrixForJob){
            
            QcMatrixRowModelParent femod=new QcMatrixRowModelParent();
            femod.setId(qcmrow.getId());
            femod.setName(qcmrow.getQctype().getName());
            femod.setQctype(qcmrow.getQctype());
            feqcmr.add(femod);
         //   System.out.println("fend.job.table.qctable.QcTableModel.<init>() created and added new QcMatrixRowModelParent with id: "+femod.getId()+" name: "+femod.getName().get());
        }
        
        //Set<Subsurface> subsinJob=parentJob.getSubsurfaces();
        Set<Subsurface> subsinJob=new HashSet<>(subsurfaceService.getSubsurfacesPresentInJob(parentJob));
     //   System.out.println("fend.job.table.qctable.QcTableModel.<init>(): size of subs from job: "+parentJob.getId()+" size: "+subsinJob.size());
        for(Subsurface s:subsinJob){
            Sequence seq=s.getSequence();
            
          //  System.out.println("fend.job.table.qctable.QcTableModel.<init>(): found seq: "+seq.getSequenceno());
            
            QcTableSubsurface qctableSubsurface=new QcTableSubsurface();
            
            qctableSubsurface.setSequence(seq);
            qctableSubsurface.setSubsurface(s);
           
            qctableSubsurface.setQcmatrix(feqcmr);
           // qctableSubsurface.setIsParent(false);
            
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
        
        for (Map.Entry<Sequence, List<QcTableSequence>> entry : lookupmap.entrySet()) {
            Sequence seq = entry.getKey();
            List<QcTableSequence> subs = entry.getValue();
            ObservableList<QcTableSequence> obssubs=FXCollections.observableArrayList(subs);
           // System.out.println("fend.job.table.qctable.QcTableModel.<init>(): new Sequence root added: ");
            QcTableSequence seqtreeroot=new QcTableSequence();
            seqtreeroot.setChildren(obssubs);
            seqtreeroot.setQcmatrix(feqcmr);
            seqtreeroot.setSequence(seq);
            
           // seqtreeroot.setIsParent(true);
            for(QcTableSequence sub:obssubs){
                sub.setParent(seqtreeroot);
            }
            
            qctableSequences.add(seqtreeroot);      //each entry can now be rendered as seq.Long   sub.string   qcmatrix[1].check/Uncheck/Indeterminate  qcmatrix[2].check/Uncheck/Indeterminate... qcmatrix[n].check/uncheck/indeteminate
        }
        
      //  System.out.println("fend.job.table.qctable.QcTableModel.<init>() qctableSequences is of size(): "+qctableSequences.size());
    }
    
    
    
    
    
    
    
   

    public ObservableList<QcTableSequence> getQctableSequences() {
        return qctableSequences;
    }

    public void setQctableSequences(List<QcTableSequence> qctableSequences) {
        this.qctableSequences=FXCollections.observableArrayList(qctableSequences);
    }
    
    
    
    
    
    
            
    
}
