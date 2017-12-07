/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.sequences;

import java.util.ArrayList;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SequenceHeaders {
    
    private String updateTime=new String();
    private String summaryTime=new String();
    
    ArrayList<SubsurfaceHeaders> subsurfaces=new ArrayList<>();
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
    
    
    
}
