/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.definitions.qcmatrix.qctype;

import db.model.QcMatrixRow;
import db.model.QcType;
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
public class QcMatrixRowModel {
    Long id=null;
    StringProperty name=new SimpleStringProperty("");
    BooleanProperty checked=new SimpleBooleanProperty(false);
    QcType qctype;
    QcMatrixRowService qcMatrixRowService=new QcMatrixRowServiceImpl();
    
    public QcMatrixRowModel() {
        
        checked.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                QcMatrixRow dbqcmatrixrow=qcMatrixRowService.getQcMatrixRow(id);
                dbqcmatrixrow.setPresent(newValue);
                qcMatrixRowService.updateQcMatrixRow(dbqcmatrixrow.getId(), dbqcmatrixrow);
            }
            
        });
    }
    
    
    
    
    public StringProperty getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public BooleanProperty checkedProperty() {
        return checked;
    }

    
    public Boolean isChecked() {
        return checked.get();
    }

    public void setChecked(Boolean checked) {
        this.checked.set(checked);
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

    
    
    
   
    
    
}
