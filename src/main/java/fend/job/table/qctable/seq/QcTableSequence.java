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
import fend.job.job0.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTableSequence  {
    private Sequence sequence;
    private Subsurface subsurface;
    List<QcMatrixRowModelParent> qcmatrix=new ArrayList<>();
    ObservableList<QcMatrixRowModelParent> observableQcMatrix;
    ObservableList<QcTableSequence> children;
    List<StringProperty> changedProperty=new ArrayList<>();
    
    private Boolean isChild=false;
    private Boolean isParent=true;                          //used to update the checkbox states
    private String updateTime=new String();
    public boolean updateChildren=false;
    public boolean updateParent=false;
    
    private BooleanProperty updateParentStatusProperty=new SimpleBooleanProperty(false);
    
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

    public ObservableList<QcMatrixRowModelParent> getQcmatrix() {
        return observableQcMatrix;
    }

    public void setQcmatrix(List<QcMatrixRowModelParent> qcmatrix) {
        for(QcMatrixRowModelParent q:qcmatrix){
            QcMatrixRowModelParent nq=new QcMatrixRowModelParent();
            nq.setCheckUncheckProperty(q.getCheckUncheckProperty().get());
            nq.setIndeterminateProperty(q.getIndeterminateProperty().get());
            nq.setCheckedByUser(q.getCheckedByUser());
            nq.setName(q.getName().get());
            nq.setPassQc(q.isPassQc());
            nq.setQctype(q.getQctype());
            nq.setId(q.getId());
            StringProperty changed=new SimpleStringProperty();
            changed.bind(nq.passQcProperty());
            changed.addListener(qcTableSelectionChangedListener);
            changedProperty.add(changed);
            this.qcmatrix.add(nq);
        }
        this.observableQcMatrix=FXCollections.observableArrayList(this.qcmatrix);
        for(QcTableSequence child:children){
            child.setQcmatrix(qcmatrix);
        }
        /**
         * 
         **/
        for(QcMatrixRowModelParent qmr:qcmatrix){
            int indeterminate=0;
            int selected=0;
            int qindex=qcmatrix.indexOf(qmr);
            for(QcTableSequence child:children){
                indeterminate+=child.getQcmatrix().get(qindex).getIndeterminateProperty().get()?1:0;
                selected+=child.getQcmatrix().get(qindex).getCheckUncheckProperty().get()?1:0;
                
            }
            //System.out.println("fend.job.table.qctable.seq.QcTableSequence.setQcmatrix(): indeterminate: "+indeterminate+" selected: "+selected);
            
            if(indeterminate > 0){
                String passQC=QcMatrixRowModelParent.INDETERMINATE;
                
                this.qcmatrix.get(qindex).getIndeterminateProperty().set(true);
                this.qcmatrix.get(qindex).setPassQc(passQC);
                
            }else if(indeterminate==0 && selected==children.size()){
                String passQC=QcMatrixRowModelParent.SELECTED;
                this.qcmatrix.get(qindex).getIndeterminateProperty().set(false);
                this.qcmatrix.get(qindex).getCheckUncheckProperty().set(true);
                this.qcmatrix.get(qindex).setPassQc(passQC);
                
            }else{
                String passQC=QcMatrixRowModelParent.UNSELECTED;
                this.qcmatrix.get(qindex).getIndeterminateProperty().set(false);
                this.qcmatrix.get(qindex).getCheckUncheckProperty().set(false);
                this.qcmatrix.get(qindex).setPassQc(passQC);
                
            }
        }
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime=updateTime;
    }

    public String getUpdateTime() {
        return updateTime;
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

   

    public QcTableSequence getParent() {
        return this;
    }

    public Boolean isChild() {
        return isChild;
    }

    public Boolean isParent() {
        return isParent;
    }

    public void setParent(QcTableSequence seqtreeroot) {
    }
    
    
    /***
     * Listeners
     */
    
    private ChangeListener<String> qcTableSelectionChangedListener=new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            System.out.println(".changed(): on SequenceLevel");
        }
    };
    
    
    
}
