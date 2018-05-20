/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.SubsurfaceCell;

import fend.summary.SequenceSummary.SequenceSummary;
import fend.summary.SequenceSummary.colors.SequenceSummaryColors;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SubsurfaceCell extends TreeTableCell<SequenceSummary,String>{
    TreeTableColumn<SequenceSummary,String> param;
    SequenceSummary selectedItem;
    
    public SubsurfaceCell(TreeTableColumn<SequenceSummary, String> p) {
        param=p;
    }
    
    @Override
    protected void updateItem(String subname,boolean empty){
        super.updateItem(subname, empty);
        if(subname==null||empty){
            setStyle("");
            setText("");
            setGraphic(null);
        }else{
            int sel=getTreeTableRow().getIndex();
            selectedItem=getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
            boolean isSub=selectedItem.isChild();
            String subOrSail=new String();
            if(selectedItem.isParent()){
                subOrSail=selectedItem.getSequence().getRealLineName();
            }else{
                subOrSail=selectedItem.getSubsurface().getSubsurface();
            }
            if(isSub){
                setStyle("-fx-background-color: "+SequenceSummaryColors.SUBSURFACE);
                setText(subOrSail);
            }else{
                setStyle("-fx-background-color: "+SequenceSummaryColors.SEQUENCE);
                setText(subOrSail);
            }
        }
    }
}
