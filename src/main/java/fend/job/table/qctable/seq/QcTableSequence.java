/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable.seq;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.model.QcMatrixRow;
import db.model.Sequence;
import db.model.Subsurface;
import fend.job.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModel;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTableSequence  {
    private Sequence sequence;
    private Subsurface subsurface;
    List<QcMatrixRowModel> qcmatrix;
    ObservableList<QcMatrixRowModel> observableQcMatrix;
    ObservableList<QcTableSequence> children;
    private QcTableSequence parent;
    
    private Boolean isParent=true;                          //used to update the checkbox states
    private String updateTime=new String();
    public boolean updateChildren=false;
    public boolean updateParent=false;
    
    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Subsurface getSubsurface() {
        if(subsurface==null) return new Subsurface();
        return subsurface;
    }

    public void setSubsurface(Subsurface subsurface) {
        this.subsurface = subsurface;
    }

    public ObservableList<QcMatrixRowModel> getQcmatrix() {
        return observableQcMatrix;
    }

    public void setQcmatrix(List<QcMatrixRowModel> qcmatrix) {
        this.observableQcMatrix=FXCollections.observableArrayList(qcmatrix);
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime=updateTime;
    }

    public Boolean isParent () {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public ObservableList<QcTableSequence> getChildren() {
        if(children==null) return children=FXCollections.observableArrayList();
        return children;
    }

    public void setChildren(ObservableList<QcTableSequence> children) {
        this.children = children;
    }

    public void setParent(QcTableSequence seqtreeroot) {
        this.parent=seqtreeroot;
    }

    public QcTableSequence getParent() {
        return parent;
    }

    
    
    
    
    
    
}
