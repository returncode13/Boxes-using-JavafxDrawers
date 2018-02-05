/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable.seq.sub;

import db.model.QcMatrixRow;
import db.model.QcTable;
import db.model.Sequence;
import db.model.Subsurface;
import db.services.QcTableService;
import db.services.QcTableServiceImpl;
import fend.job.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;
import fend.job.table.qctable.seq.QcTableSequence;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTableSubsurface extends QcTableSequence{
     private Sequence sequence;
    private Subsurface subsurface;
    List<QcMatrixRowModelParent> qcmatrix=new ArrayList<>();
    ObservableList<QcMatrixRowModelParent> observableQcMatrix;
    QcTableSequence parent;
    private QcTableService qcTableService=new QcTableServiceImpl();
    private String updateTime=new String();
    ObservableList<StringProperty> changedList=FXCollections.observableArrayList();
    ListProperty<StringProperty> changedListProperty=new SimpleListProperty<>(changedList);
    
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
    public ObservableList<QcMatrixRowModelParent> getQcmatrix() {
        return observableQcMatrix;
    }

     @Override
    public void setQcmatrix(List<QcMatrixRowModelParent> qcmatrix) {
        changedList.clear();
        changedListProperty.clear();
        changedListProperty.unbind();
        
         for(QcMatrixRowModelParent q:qcmatrix){
            QcMatrixRowModelParent nq=new QcMatrixRowModelParent();
            nq.setId(q.getId());
             //System.out.println("loading result for QMid: "+nq.getId()+" Subid: "+subsurface.getId()+" - "+subsurface.getSubsurface());
             try{
                 QcTable qcTableFromDb=qcTableService.getQcTableFor(nq.getId(), subsurface);
                 
                 if(qcTableFromDb==null){
                     nq.setPassQc(q.isPassQc());
                 }else{
                        Boolean result=qcTableFromDb.getResult();
                        if(result==null){
                            nq.setPassQc(QcMatrixRowModelParent.INDETERMINATE);
                        }else if(result){
                            nq.setPassQc(QcMatrixRowModelParent.SELECTED);
                        }else{
                            nq.setPassQc(QcMatrixRowModelParent.UNSELECTED);
                        }
                 }
                 
                 
             }catch(Exception e){
               nq.setPassQc(q.isPassQc());
             }
             /*nq.setCheckUncheckProperty(q.getCheckUncheckProperty().get());
             nq.setIndeterminateProperty(q.getIndeterminateProperty().get());*/
            nq.setCheckedByUser(q.getCheckedByUser());
            nq.setName(q.getName().get());
            //nq.setPassQc(q.isPassQc());
            nq.setQctype(q.getQctype());
            
            StringProperty changed=new SimpleStringProperty();
            changed.bind(nq.passQcProperty());
            changed.addListener(qcTableSelectionChangedListener);
            changedList.add(changed);
            this.qcmatrix.add(nq);
        }
        this.observableQcMatrix=FXCollections.observableArrayList(this.qcmatrix);
        changedListProperty=new SimpleListProperty<>(changedList);
        changedListProperty.addListener(listHasChanged);
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

    public String getUpdateTime() {
        return updateTime;
    }
     
    private ChangeListener<String> qcTableSelectionChangedListener=new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            System.out.println(".changed(): from "+oldValue+" to "+newValue);
        }
    };
    
     private ListChangeListener<StringProperty> listHasChanged=new ListChangeListener<StringProperty>() {
         @Override
         public void onChanged(ListChangeListener.Change<? extends StringProperty> c) {
             while(c.next()){
                 if(c.wasUpdated()){
                //     System.out.println(".onChanged(): from "+c.getFrom()+" to "+c.getTo()+" was updated");
                 }
             }
         }
     };
     
}
