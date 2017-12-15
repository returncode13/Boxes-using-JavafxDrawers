/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.sequences;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.model.Sequence;
import db.model.Subsurface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SequenceHeaders extends RecursiveTreeObject<SequenceHeaders>{
    
    private String updateTime=new String();
    private String summaryTime=new String();
    
    List<SubsurfaceHeaders> subsurfaceHeaders=new ArrayList<>();
    /*private LongProperty sequenceNumber = new SimpleLongProperty();
    private StringProperty subsurfaceName=new SimpleStringProperty();
    private StringProperty timeStamp=new SimpleStringProperty();
    private LongProperty traceCount= new SimpleLongProperty(0L);
    private LongProperty inlineMax= new SimpleLongProperty(0L);
    private LongProperty inlineMin= new SimpleLongProperty(0L);
    private LongProperty inlineInc= new SimpleLongProperty(0L);
    private LongProperty xlineMax= new SimpleLongProperty(0L);
    private LongProperty xlineMin= new SimpleLongProperty(0L);
    private LongProperty xlineInc= new SimpleLongProperty(0L);
    private LongProperty dugShotMax= new SimpleLongProperty(0L);
    private LongProperty dugShotMin= new SimpleLongProperty(0L);
    private LongProperty dugShotInc= new SimpleLongProperty(0L);
    private LongProperty dugChannelMax= new SimpleLongProperty(0L);
    private LongProperty dugChannelMin= new SimpleLongProperty(0L);
    private LongProperty dugChannelInc= new SimpleLongProperty(0L);
    private LongProperty offsetMax= new SimpleLongProperty(0L);
    private LongProperty offsetMin= new SimpleLongProperty(0L);
    private LongProperty offsetInc= new SimpleLongProperty(0L);
    private LongProperty cmpMax= new SimpleLongProperty(0L);
    private LongProperty cmpMin= new SimpleLongProperty(0L);
    private LongProperty cmpInc= new SimpleLongProperty(0L);
    private StringProperty insight=new SimpleStringProperty();
    private StringProperty workflow=new SimpleStringProperty();
    private LongProperty numberOfRuns=new SimpleLongProperty();*/
    
    private Long id;
    private Long sequenceNumber=0L;
    private String subsurfaceName=new String();
    private String timeStamp=new String();
    private Long traceCount=0L;
    private Long inlineMax=0L;
    private Long inlineMin=0L;
    private Long inlineInc=0L;
    private Long xlineMax=0L;
    private Long xlineMin=0L;
    private Long xlineInc=0L;
    private Long dugShotMax=0L;
    private Long dugShotMin=0L;
    private Long dugShotInc=0L;
    private Long dugChannelMax=0L;
    private Long dugChannelMin=0L;
    private Long dugChannelInc=0L;
    private Long offsetMax=0L;
    private Long offsetMin=0L;
    private Long offsetInc=0L;
    private Long cmpMax=0L;
    private Long cmpMin=0L;
    private Long cmpInc=0L;
    private String insight=new String();
    private String workflow=new String();
    private Long numberOfRuns=0L;
    private Boolean chosen=true;
    private Boolean multiple=false;
    
    
    
    private Sequence sequence;
    private Subsurface subsurface;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public List<SubsurfaceHeaders> getSubsurfaceHeaders() {
        return subsurfaceHeaders;
    }

    public void setSubsurfaceHeaders(List<SubsurfaceHeaders> subsurfaceHeaders) {
        this.subsurfaceHeaders = subsurfaceHeaders;
        int count=0;
        Map<String,Long> gunShotMap=new HashMap<>();
        for(SubsurfaceHeaders sub:subsurfaceHeaders){
            
             String subname=sub.getSubsurface().getSubsurface();
             String gun=subname.substring(subname.lastIndexOf("_")+1,subname.length());
             
             
             
             Long shots=(sub.getDugShotMax()-sub.getDugShotMin()+sub.getDugShotInc())/sub.getDugShotInc();
           //  System.out.println("fend.session.node.headers.setRowFactory():  shots = "+shots+" for gun: "+gun);
             if(gunShotMap.containsKey(gun)&& gunShotMap.get(gun)<=shots){
                 gunShotMap.put(gun, shots);
             }else{
                 if(!gunShotMap.containsKey(gun)){
                    gunShotMap.put(gun, shots); 
                 }
                 
             }
             
             if(count==0){
             insight=sub.getInsight();
             workflow=sub.getWorkflow();
             }
             else{
                 if(!insight.equals(sub.getInsight())){
                     insight="Multiple";
                 }
                 
                 if(!workflow.equals(sub.getWorkflow())){
                     workflow="Multiple";
                 }
             }
            traceCount+=sub.getTraceCount();
            inlineMax=Math.max(inlineMax, sub.getInlineMax());
            inlineMin=Math.min(inlineMin, sub.getInlineMin());
            inlineInc=Math.min(inlineInc, sub.getInlineInc());
            xlineMax=Math.max(xlineMax, sub.getXlineMax());
            xlineMin=Math.min(xlineMin,sub.getXlineMin());
            dugShotMax=Math.max(dugShotMax, sub.getDugShotMax());
            dugShotMin=Math.min(dugShotMin,sub.getDugShotMin());
            dugShotInc=Math.max(dugShotInc, sub.getDugChannelInc());
            dugChannelMax=Math.max(dugChannelMax,sub.getDugChannelMax());
            dugChannelMin=Math.min(dugChannelMin, sub.getDugChannelMin());
            offsetMax=Math.max(offsetMax,sub.getOffsetMax());
            offsetMin=Math.min(offsetMin, sub.getOffsetMin());
            cmpMax=Math.max(cmpMax,sub.getCmpMax());
            cmpMin=Math.min(cmpMin,sub.getCmpMin());
            cmpInc=Math.max(cmpInc,sub.getCmpInc());
            numberOfRuns=Math.max(numberOfRuns, sub.getNumberOfRuns());
            
            /*sum(traceCount,sub.getTraceCount());
            max(inlineMax,sub.getInlineMax());
            min(inlineMin,sub.getInlineMin());
            max(inlineInc,sub.getInlineInc());
            max(xlineMax,sub.getXlineMax());
            min(xlineMin,sub.getXlineMin());
            max(xlineInc,sub.getXlineInc());
            max(dugShotMax,sub.getDugShotMax());
            min(dugShotMin,sub.getDugChannelMin());
            max(dugShotInc,sub.getDugShotInc());
            max(dugChannelMax,sub.getDugChannelMax());
            min(dugChannelMin,sub.getDugChannelMin());
            max(dugChannelInc,sub.getDugChannelInc());
            max(offsetMax,sub.getOffsetMax());
            min(offsetMin,sub.getOffsetMin());
            max(offsetInc,sub.getOffsetInc());
            max(cmpMax,sub.getCmpMax());
            min(cmpMin,sub.getCmpMin());
            max(cmpInc,sub.getCmpInc());
            max(numberOfRuns,sub.getNumberOfRuns());*/
           
           
            count++;
            
        }
         Long totalShots=0L;
         for (Map.Entry<String, Long> entrySet : gunShotMap.entrySet()) {
                String key = entrySet.getKey();
                Long value = entrySet.getValue();
                totalShots+=value;
         }
         dugShotMax=totalShots;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getSummaryTime() {
        return summaryTime;
    }

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public String getSubsurfaceName() {
        return subsurfaceName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public Long getTraceCount() {
        return traceCount;
    }

    public Long getInlineMax() {
        return inlineMax;
    }

    public Long getInlineMin() {
        return inlineMin;
    }

    public Long getInlineInc() {
        return inlineInc;
    }

    public Long getXlineMax() {
        return xlineMax;
    }

    public Long getXlineMin() {
        return xlineMin;
    }

    public Long getXlineInc() {
        return xlineInc;
    }

    public Long getDugShotMax() {
        return dugShotMax;
    }

    public Long getDugShotMin() {
        return dugShotMin;
    }

    public Long getDugShotInc() {
        return dugShotInc;
    }

    public Long getDugChannelMax() {
        return dugChannelMax;
    }

    public Long getDugChannelMin() {
        return dugChannelMin;
    }

    public Long getDugChannelInc() {
        return dugChannelInc;
    }

    public Long getOffsetMax() {
        return offsetMax;
    }

    public Long getOffsetMin() {
        return offsetMin;
    }

    public Long getOffsetInc() {
        return offsetInc;
    }

    public Long getCmpMax() {
        return cmpMax;
    }

    public Long getCmpMin() {
        return cmpMin;
    }

    public Long getCmpInc() {
        return cmpInc;
    }

    public String getInsight() {
        return insight;
    }

    public String getWorkflow() {
        return workflow;
    }

    public Long getNumberOfRuns() {
        return numberOfRuns;
    }

    public Boolean getChosen() {
        return chosen;
    }

    public Boolean getMultiple() {
        return multiple;
    }

    public Subsurface getSubsurface() {
        return subsurface;
    }
    
    
    
    
    private void sum(Long a,Long b){
        a=a+b;
    }
    private void min(Long a,Long b){
        Long min=a;
        if(min>b){
            min=b;
        }
        a=min;
        
    }
    private void max(Long a,Long b){
        Long max=a;
        if(max<b){
            max=b;
        }
        a=max;
        
    }
    
    
    
    
}
