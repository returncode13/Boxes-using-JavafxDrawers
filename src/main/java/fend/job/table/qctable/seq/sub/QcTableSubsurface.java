/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable.seq.sub;

import db.model.Sequence;
import db.model.Subsurface;
import fend.job.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModel;
import fend.job.table.qctable.seq.QcTableSequence;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTableSubsurface extends QcTableSequence{
     private Sequence sequence;
    private Subsurface subsurface;
    List<QcMatrixRowModel> qcmatrix=new ArrayList<>();
    ObservableList<QcMatrixRowModel> observableQcMatrix;
    QcTableSequence parent;
    private String updateTime=new String();
    
    private Boolean isChild=true;
    private Boolean isParent=false;                          //used to update the checkbox states
   
    public boolean updateChildren=false;
    public boolean updateParent=false;
    
     @Override
    public Sequence getSequence() {
        return sequence;
    }

     @Override
    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

     @Override
    public Subsurface getSubsurface() {
        return subsurface;
    }

     @Override
    public void setSubsurface(Subsurface subsurface) {
        this.subsurface = subsurface;
    }

     @Override
    public ObservableList<QcMatrixRowModel> getQcmatrix() {
        return observableQcMatrix;
    }

     @Override
    public void setQcmatrix(List<QcMatrixRowModel> qcmatrix) {
         for(QcMatrixRowModel q:qcmatrix){
            QcMatrixRowModel nq=new QcMatrixRowModel();
            nq.setCheckUncheckProperty(q.getCheckUncheckProperty().get());
            nq.setIndeterminateProperty(q.getIndeterminateProperty().get());
            nq.setCheckedByUser(q.getCheckedByUser());
            nq.setName(q.getName().get());
            nq.setPassQc(q.isPassQc());
            nq.setQctype(q.getQctype());
            nq.setId(q.getId());
            this.qcmatrix.add(nq);
        }
        this.observableQcMatrix=FXCollections.observableArrayList(this.qcmatrix);
        //this.observableQcMatrix=FXCollections.observableArrayList(this.qcmatrix);
    }

     @Override
    public void setUpdateTime(String updateTime) {
        this.updateTime=updateTime;
    }

     @Override
    public Boolean isParent () {
        return isParent;
    }

     @Override
    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public void setParent(QcTableSequence parent) {
        this.parent = parent;
    }

     @Override
    public QcTableSequence getParent() {
        return parent;
    }
    
     public ObservableList<QcTableSequence> getChildren() {
        return parent.getChildren();
    }
}
