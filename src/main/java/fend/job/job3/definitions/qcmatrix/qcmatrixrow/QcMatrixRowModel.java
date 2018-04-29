/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job3.definitions.qcmatrix.qcmatrixrow;

import db.model.QcMatrixRow;
import db.model.QcType;
import db.services.QcMatrixRowService;
import db.services.QcMatrixRowServiceImpl;
import fend.job.job0.JobType0Model;
import fend.job.job0.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixRowModel extends QcMatrixRowModelParent{
    public static final String INDETERMINATE="indeterminate";
    public static final String SELECTED="selected";
    public static final String UNSELECTED="unselected";
    
    Long id=null;
    StringProperty name=new SimpleStringProperty("");
    BooleanProperty checkedByUser=new SimpleBooleanProperty(false);
    QcType qctype;
    QcMatrixRowService qcMatrixRowService=new QcMatrixRowServiceImpl();
    
    BooleanProperty checkUncheckProperty=new SimpleBooleanProperty();
    BooleanProperty indeterminateProperty=new SimpleBooleanProperty();
    
    JobType0Model parentJob;
    
    public QcMatrixRowModel() {
        
        checkedByUser.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                /*QcMatrixRow dbqcmatrixrow=qcMatrixRowService.getQcMatrixRow(id);
                dbqcmatrixrow.setPresent(newValue);
                qcMatrixRowService.updateQcMatrixRow(dbqcmatrixrow.getId(), dbqcmatrixrow);*/
                qcMatrixRowService.updatePresent(id,newValue);
                parentJob.toggleQcChangedProperty();
            }
            
        });
    }
    
    
    public JobType0Model getParentJob() {
        return parentJob;
    }

    public void setParentJob(JobType0Model parentJob) {
        this.parentJob = parentJob;
    }
    
    @Override
    public StringProperty getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public BooleanProperty checkedProperty() {
        return checkedByUser;
    }

    
    @Override
    public Boolean getCheckedByUser() {
        return checkedByUser.get();
    }

    @Override
    public void setCheckedByUser(Boolean checked) {
        if(checked==null)checked=false;
        this.checkedByUser.set(checked);
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public QcType getQctype() {
        return qctype;
    }

    @Override
    public void setQctype(QcType qctype) {
        this.qctype = qctype;
    }

    @Override
    public BooleanProperty getCheckUncheckProperty() {
        return checkUncheckProperty;
    }

    @Override
    public void setCheckUncheckProperty(Boolean checkUncheck) {
        this.checkUncheckProperty.set(checkUncheck);
    }

    @Override
    public BooleanProperty getIndeterminateProperty() {
        return indeterminateProperty;
    }

    @Override
    public void setIndeterminateProperty(Boolean isIndeterminate) {
        this.indeterminateProperty.set(isIndeterminate);
    }

    
    
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QcMatrixRowModel other = (QcMatrixRowModel) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
     
    private final StringProperty passQc = new SimpleStringProperty();
    
    @Override
    public String isPassQc() {
     if(indeterminateProperty.get()){
         passQc.set(INDETERMINATE);
       
     }else{
         if(checkUncheckProperty.getValue()){
             passQc.set(SELECTED);
         }else{
             passQc.set(UNSELECTED);
         }
        
     }
     return passQc.get();
    }
    
    @Override
    public void setPassQc(String value) {
         if(value.equals(INDETERMINATE)){
             indeterminateProperty.set(true);
         }else{
             indeterminateProperty.set(false);
             if(value.equals(SELECTED)){
                 checkUncheckProperty.set(true);
             }else{
                 checkUncheckProperty.set(false);
             }
         }
    }
    
    @Override
    public StringProperty passQcProperty() {
        
        if(indeterminateProperty.get()){
         passQc.set(INDETERMINATE);
        
     }else{
        if(checkUncheckProperty.getValue()){
             passQc.set(SELECTED);
         }else{
             passQc.set(UNSELECTED);
         }
        
     }
        return passQc;
    }
    
    /*
    ChangeListener<Boolean> indeterminateChangeListener=new ChangeListener<Boolean>() {
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    if(newValue){
    QcMatrixRowModel.this.passQc.set(INDETERMINATE);
    }
    }
    };
    
    ChangeListener<Boolean> checkUncheckChangeListener=new ChangeListener<Boolean>() {
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    if(newValue){
    QcMatrixRowModel.this.passQc.set(INDETERMINATE);
    }
    }
    };*/
    
}
