/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.sequences;

import db.model.Sequence;
import db.model.Subsurface;
import java.util.Objects;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import fend.volume.volume0.Volume0;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SubsurfaceHeaders extends SequenceHeaders{
    
    
    private Volume0 volume;
    private String updateTime=new String();
    private String summaryTime=new String();
    private Long id;
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
    private StringProperty insight=new SimpleStringProperty();
    private StringProperty workflow=new SimpleStringProperty();
    private LongProperty numberOfRuns=new SimpleLongProperty();
    
    private BooleanProperty chosen=new SimpleBooleanProperty();
    private BooleanProperty multiple=new SimpleBooleanProperty();
    
    private SequenceHeaders sequenceHeader;
    private Subsurface subsurface;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    
    
    @Override
    public Sequence getSequence(){
        return subsurface.getSequence();
    }
    
    public Subsurface getSubsurface() {
        return subsurface;
    }

    public void setSubsurface(Subsurface subsurface) {
        this.subsurface = subsurface;
    }
    
    
    
    public SubsurfaceHeaders(Volume0 volume) {
        this.volume = volume;
    }
    
    
    private SubsurfaceHeaders(){}
    
    @Override
    public String getSubsurfaceName() {
        return subsurfaceName.get();
    }

    public void setSubsurfaceName(String subsurfaceName) {
        this.subsurfaceName.set(subsurfaceName);
    }

    @Override
    public String getTimeStamp() {
        return timeStamp.get();
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

    @Override
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getSummaryTime() {
        return summaryTime;
    }

    public void setSummaryTime(String summaryTime) {
        this.summaryTime = summaryTime;
    }

    @Override
    public Long getSequenceNumber() {
        return sequenceNumber.get();
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber.set(sequenceNumber);
    }

    @Override
    public Long getTraceCount() {
        return traceCount.get();
    }

    public void setTraceCount(Long traceCount) {
        this.traceCount.set(traceCount);
    }

    @Override
    public Long getInlineMax() {
        return inlineMax.get();
    }

    public void setInlineMax(Long inlineMax) {
        this.inlineMax.set(inlineMax);
    }

    @Override
    public Long getInlineMin() {
        return inlineMin.get();
    }

    public void setInlineMin(Long inlineMin) {
        this.inlineMin.set(inlineMin);
    }

    @Override
    public Long getInlineInc() {
        return inlineInc.get();
    }

    public void setInlineInc(Long inlineInc) {
        this.inlineInc.set(inlineInc);
    }

    @Override
    public Long getXlineMax() {
        return xlineMax.get();
    }

    public void setXlineMax(Long xlineMax) {
        this.xlineMax.set(xlineMax);
    }

    @Override
    public Long getXlineMin() {
        return xlineMin.get();
    }

    public void setXlineMin(Long xlineMin) {
        this.xlineMin.set(xlineMin);
    }

    @Override
    public Long getXlineInc() {
        return xlineInc.get();
    }

    public void setXlineInc(Long xlineInc) {
        this.xlineInc.set(xlineInc);
    }

    @Override
    public Long getDugShotMax() {
        return dugShotMax.get();
    }

    public void setDugShotMax(Long dugShotMax) {
        this.dugShotMax.set(dugShotMax);
    }

    @Override
    public Long getDugShotMin() {
        return dugShotMin.get();
    }

    public void setDugShotMin(Long dugShotMin) {
        this.dugShotMin.set(dugShotMin);
    }

    @Override
    public Long getDugShotInc() {
        return dugShotInc.get();
    }

    public void setDugShotInc(Long dugShotInc) {
        this.dugShotInc.set(dugShotInc);
    }

    @Override
    public Long getDugChannelMax() {
        return dugChannelMax.get();
    }

    public void setDugChannelMax(Long dugChannelMax) {
        this.dugChannelMax.set(dugChannelMax);
    }

    @Override
    public Long getDugChannelMin() {
        return dugChannelMin.get();
    }

    public void setDugChannelMin(Long dugChannelMin) {
        this.dugChannelMin.set(dugChannelMin);
    }

    @Override
    public Long getDugChannelInc() {
        return dugChannelInc.get();
    }

    public void setDugChannelInc(Long dugChannelInc) {
        this.dugChannelInc.set(dugChannelInc);
    }

    @Override
    public Long getOffsetMax() {
        return offsetMax.get();
    }

    public void setOffsetMax(Long offsetMax) {
        this.offsetMax.set(offsetMax);
    }

    @Override
    public Long getOffsetMin() {
        return offsetMin.get();
    }

    public void setOffsetMin(Long offsetMin) {
        this.offsetMin.set(offsetMin);
    }

    /**
     *
     * @return
     */
    @Override
    public Long getOffsetInc() {
        return offsetInc.get();
    }

    public void setOffsetInc(Long offsetInc) {
        this.offsetInc.set(offsetInc);
    }

    @Override
    public Long getCmpMax() {
        return cmpMax.get();
    }

    public void setCmpMax(Long cmpMax) {
        this.cmpMax.set(cmpMax);
    }

    @Override
    public Long getCmpMin() {
        return cmpMin.get();
    }

    public void setCmpMin(Long cmpMin) {
        this.cmpMin.set(cmpMin);
    }

    @Override
    public Long getCmpInc() {
        return cmpInc.get();
    }

    public void setCmpInc(Long cmpInc) {
        this.cmpInc.set(cmpInc);
    }

    public SequenceHeaders getSequenceHeader() {
        return sequenceHeader;
    }

    public void setSequenceHeader(SequenceHeaders sequenceHeader) {
        this.sequenceHeader = sequenceHeader;
    }

    @Override
    public String getInsight() {
        return insight.get();
    }

    public void setInsight(String insight) {
        this.insight.set(insight);
    }

    @Override
    public String getWorkflow() {
        return workflow.get();
    }

    public void setWorkflow(String workflow) {
        this.workflow.set(workflow);
    }

    @Override
    public Long getNumberOfRuns() {
        return numberOfRuns.get();
    }

    public void setNumberOfRuns(Long numberOfRuns) {
        this.numberOfRuns.set(numberOfRuns);
    }

    public Boolean getChosen() {
        return chosen.get();
    }

    public void setChosen(Boolean chosen) {
        this.chosen.set(chosen);
    }

    public Boolean getMultiple() {
        return multiple.get();
    }

    public void setMultiple(Boolean multiple) {
        this.multiple.set(multiple);
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
        hash = 71 * hash + Objects.hashCode(this.sequenceHeader);
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
        if (!Objects.equals(this.sequenceHeader, other.sequenceHeader)) {
            return false;
        }
        return true;
    }
    
    
  
    
    
    
    
}
