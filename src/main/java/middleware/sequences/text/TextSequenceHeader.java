/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.sequences.text;

import db.model.Sequence;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class TextSequenceHeader {
     private Long id;
     private Sequence sequence;
     private StringProperty timestampProperty=new SimpleStringProperty();
     private StringProperty md5Property=new SimpleStringProperty();
     private Long numberOfRuns=0L;
     private StringProperty textFileProperty=new SimpleStringProperty();
     private BooleanProperty modifiedProperty=new SimpleBooleanProperty();
     private BooleanProperty deletedProperty=new SimpleBooleanProperty();

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }


    

    
    public StringProperty timestampProperty() {
        return timestampProperty;
    }
    
    public void setTimestamp(String time) {
        this.timestampProperty.set(time);
    }

    public StringProperty md5Property() {
        return md5Property;
    }
    
    public void setMd5(String m) {
        this.md5Property.set(m);
    }

    public Long getNumberOfRuns() {
        return numberOfRuns;
    }
    
    public void setNumberOfRuns(Long numberOfRuns) {
        this.numberOfRuns = numberOfRuns;
    }

    public StringProperty textFileProperty() {
        return textFileProperty;
    }
    
    public void setTextFile(String textFile) {
        this.textFileProperty.set(textFile);
    }

    public BooleanProperty modifiedProperty() {
        return modifiedProperty;
    }
    
    public void setModifiedProperty(Boolean m) {
        this.modifiedProperty.set(m);
    }

    public BooleanProperty deletedProperty() {
        return deletedProperty;
    }
    
    public void setDeletedProperty(Boolean d) {
        this.deletedProperty.set(d);
    }

     
     
     
    
}
