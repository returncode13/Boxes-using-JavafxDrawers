/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SummaryTableRow;

import fend.summary.SequenceSummary.SequenceSummary;
import fend.summary.SequenceSummary.colors.SequenceSummaryColors;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableRow;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SummaryTableRow extends TreeTableRow<SequenceSummary>{
    
    
    private final SimpleBooleanProperty active=new SimpleBooleanProperty();
    private boolean isSelected=false;
    private SequenceSummary currentItem=null;
    
    public SummaryTableRow(){
        super();
        active.addListener((observable, oldValue, newValue) -> {
            if(currentItem!=null && currentItem == getItem()){
                updateItem(getItem(), isEmpty());
            }
        });
        
        itemProperty().addListener(new ChangeListener<SequenceSummary>(){
            @Override
            public void changed(ObservableValue<? extends SequenceSummary> observable, SequenceSummary oldValue, SequenceSummary newValue) {
               active.unbind();
               if(newValue!=null){
                   active.bind(newValue.sequenceSummaryIsActiveProperty());
                   currentItem = newValue;
               }
            }
            
        });
        
        selectedProperty().addListener((obs,oldValue,newValue)->{
            if(newValue){
                
                    isSelected=true;
                    updateItem(getItem(), isEmpty());
               
            }else{
             
                    isSelected=false;
                    updateItem(getItem(), isEmpty());
               
            }
        });
    }
    
    
                    @Override
                     protected void updateItem(SequenceSummary item,boolean empty){
                         super.updateItem(item, empty);
                         if((item==null)||empty){
                             setStyle("");
                             setText("");
                             setGraphic(null);
                         }else{
                            
                            
                             boolean isSub = item.isChild();
                         
                             if (isSub) {
                                    if (!isSelected) {
                                     setStyle("-fx-background-color: " + SequenceSummaryColors.SUBSURFACE+";");
                                      setTextFill(SequenceSummaryColors.SUBSURFACE_TEXT);
                                    }
                                    else{
                                         setStyle("-fx-background-color: " + SequenceSummaryColors.SUBSURFACE_SELECTED+";");
                                        setTextFill(SequenceSummaryColors.SUBSURFACE_TEXT_SELECTED);
                                    }
                                

                             } else {
                                 if(!isSelected){
                                 setStyle("-fx-background-color: " + SequenceSummaryColors.SEQUENCE+";");
                                 setTextFill(SequenceSummaryColors.SEQUENCE_TEXT);
                                 }else{
                                     setStyle("-fx-background-color: " + SequenceSummaryColors.SEQUENCE_SELECTED+";");
                                 setTextFill(SequenceSummaryColors.SEQUENCE_TEXT_SELECTED);
                                 }
                             }
                             
                         }
                     }
    
}
