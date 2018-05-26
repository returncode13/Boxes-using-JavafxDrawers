/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.SequenceCell;

import fend.summary.SequenceSummary.SequenceSummary;
import fend.summary.SequenceSummary.colors.SequenceSummaryColors;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.paint.Color;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SequenceCell  extends TreeTableCell<SequenceSummary, Long> {
    TreeTableColumn<SequenceSummary,Long> param;
    SequenceSummary selectedItem;
    final BooleanProperty isSelected=new SimpleBooleanProperty(false);
    
    public SequenceCell(TreeTableColumn<SequenceSummary, Long> p) {
        param=p;
        
    }
  
    
    
    
    
    @Override
    protected void updateItem(Long seq,boolean empty){
        super.updateItem(seq, empty);
        if(seq==null||empty){
            setStyle("");
            setText("");
            setGraphic(null);
        }else{
            int sel=getTreeTableRow().getIndex();
            selectedItem=getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
            boolean isSub=selectedItem.isChild();
            
            
            if(isSub){
                
                    setStyle("-fx-background-color: "+SequenceSummaryColors.SUBSURFACE);
                    setTextFill(SequenceSummaryColors.SUBSURFACE_TEXT);
                
                
                
            }else{
                
                    setStyle("-fx-background-color: "+SequenceSummaryColors.SEQUENCE);
                    setTextFill(SequenceSummaryColors.SEQUENCE_TEXT);
               
                
            }
            
            setText(""+seq);
            //getStylesheets().add("css/treeTableCell.css");
        }
    }
}
