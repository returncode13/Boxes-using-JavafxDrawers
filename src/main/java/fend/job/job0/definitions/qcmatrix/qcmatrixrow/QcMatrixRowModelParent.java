/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job0.definitions.qcmatrix.qcmatrixrow;

import app.properties.AppProperties;
import db.model.QcMatrixRow;
import db.model.QcType;
import db.model.User;
import db.services.QcMatrixRowService;
import db.services.QcMatrixRowServiceImpl;
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
public class QcMatrixRowModelParent {
    public static final String INDETERMINATE="indeterminate";
    public static final String SELECTED="selected";
    public static final String UNSELECTED="unselected";
    
    
    User user;
    Long id=null;
    StringProperty name=new SimpleStringProperty("");
    BooleanProperty checkedByUser=new SimpleBooleanProperty(false);
    QcType qctype;
    QcMatrixRowService qcMatrixRowService=new QcMatrixRowServiceImpl();
    
    BooleanProperty checkUncheckProperty=new SimpleBooleanProperty();
    BooleanProperty indeterminateProperty=new SimpleBooleanProperty();
    
    String lastUpdatedTime=new String("EPOCH");
    
    public QcMatrixRowModelParent() {
        
        checkedByUser.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                QcMatrixRow dbqcmatrixrow=qcMatrixRowService.getQcMatrixRow(id);
                dbqcmatrixrow.setPresent(newValue);
                qcMatrixRowService.updateQcMatrixRow(dbqcmatrixrow.getId(), dbqcmatrixrow);
            }
            
        });
    }

    public User getUser() {
        return user==null?AppProperties.getCurrentUser():user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
    
    
    
    
    public StringProperty getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public BooleanProperty checkedProperty() {
        return checkedByUser;
    }

    
    public Boolean getCheckedByUser() {
        return checkedByUser.get();
    }

    public void setCheckedByUser(Boolean checked) {
        if(checked==null)checked=false;
        this.checkedByUser.set(checked);
    }

   

    
    
    
    
    @Override
    public String toString() {
        return name.get();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QcType getQctype() {
        return qctype;
    }

    public void setQctype(QcType qctype) {
        this.qctype = qctype;
    }

    public BooleanProperty getCheckUncheckProperty() {
        return checkUncheckProperty;
    }

    public void setCheckUncheckProperty(Boolean checkUncheck) {
        this.checkUncheckProperty.set(checkUncheck);
    }

    public BooleanProperty getIndeterminateProperty() {
        return indeterminateProperty;
    }

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
        final QcMatrixRowModelParent other = (QcMatrixRowModelParent) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
     
    private final StringProperty passQc = new SimpleStringProperty();
    
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
    QcMatrixRowModelParent.this.passQc.set(INDETERMINATE);
    }
    }
    };
    
    ChangeListener<Boolean> checkUncheckChangeListener=new ChangeListener<Boolean>() {
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    if(newValue){
    QcMatrixRowModelParent.this.passQc.set(INDETERMINATE);
    }
    }
    };*/
    
}
