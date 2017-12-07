/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.sequences;

import java.util.Objects;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import fend.volume.volume0.Volume0;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SubsurfaceHeaders extends SequenceHeaders{
    
    
    private Volume0 volume;
    private String updateTime=new String();
    private String summaryTime=new String();
    
    private LongProperty sequenceNumber = new SimpleLongProperty();   
    private StringProperty subsurfaceName=new SimpleStringProperty();
    private StringProperty timeStamp=new SimpleStringProperty();
    private LongProperty traceCount= new SimpleLongProperty();  
    private LongProperty inlineMax= new SimpleLongProperty();  
    private LongProperty inlineMin= new SimpleLongProperty();  
    private LongProperty inlineInc= new SimpleLongProperty();  
    private LongProperty xlineMax= new SimpleLongProperty();  
    private LongProperty xlineMin= new SimpleLongProperty();  
    private LongProperty xlineInc= new SimpleLongProperty();  
    private LongProperty dugShotMax= new SimpleLongProperty();  
    private LongProperty dugShotMin= new SimpleLongProperty();  
    private LongProperty dugShotInc= new SimpleLongProperty();  
    private LongProperty dugChannelMax= new SimpleLongProperty();  
    private LongProperty dugChannelMin= new SimpleLongProperty();  
    private LongProperty dugChannelInc= new SimpleLongProperty();  
    private LongProperty offsetMax= new SimpleLongProperty();  
    private LongProperty offsetMin= new SimpleLongProperty();  
    private LongProperty offsetInc= new SimpleLongProperty();  
    private LongProperty cmpMax= new SimpleLongProperty();  
    private LongProperty cmpMin= new SimpleLongProperty();  
    private LongProperty cmpInc= new SimpleLongProperty(); 
    
    private SequenceHeaders sequence;

    public SubsurfaceHeaders(Volume0 volume) {
        this.volume = volume;
    }
    
    
    private SubsurfaceHeaders(){}
    
    public StringProperty getSubsurfaceName() {
        return subsurfaceName;
    }

    public void setSubsurfaceName(String subsurfaceName) {
        this.subsurfaceName.set(subsurfaceName);
    }

    public StringProperty getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp.set(timeStamp);
    }

    public Volume0 getVolume() {
        return volume;
    }

    public void setVolume(Volume0 volume) {
        this.volume = volume;
    }

    
      
    
    /**
     * equals and hashcode()
     */
    
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.volume);
        hash = 71 * hash + Objects.hashCode(this.sequenceNumber);
        hash = 71 * hash + Objects.hashCode(this.subsurfaceName);
        hash = 71 * hash + Objects.hashCode(this.timeStamp);
        hash = 71 * hash + Objects.hashCode(this.sequence);
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
        final SubsurfaceHeaders other = (SubsurfaceHeaders) obj;
        if (!Objects.equals(this.volume, other.volume)) {
            return false;
        }
        if (!Objects.equals(this.sequenceNumber, other.sequenceNumber)) {
            return false;
        }
        if (!Objects.equals(this.subsurfaceName, other.subsurfaceName)) {
            return false;
        }
        if (!Objects.equals(this.timeStamp, other.timeStamp)) {
            return false;
        }
        if (!Objects.equals(this.sequence, other.sequence)) {
            return false;
        }
        return true;
    }
    
    
  
    
    
    
    
}
