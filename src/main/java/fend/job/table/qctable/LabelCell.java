/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable;

import fend.job.job0.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;
import fend.job.table.qctable.seq.QcTableSequence;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class LabelCell extends TreeTableCell<QcTableSequence, String> {
    TreeTableColumn<QcTableSequence, String> param;
    QcTableSequence selectedItem;
    
    
    LabelCell(TreeTableColumn<QcTableSequence, String> param){
        this.param=param;
        
    }
    
    
    @Override
    protected void updateItem(String seq,boolean empty){
        super.updateItem(seq, empty);
        if(empty||seq == null){
            setGraphic(null);
        }else{
            int sel=getTreeTableRow().getIndex();
            selectedItem=getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
            String subOrSail=new String();
            if(selectedItem.isParent()){
                subOrSail=selectedItem.getSequence().getRealLineName();
            }else{
                subOrSail=selectedItem.getSubsurface().getSubsurface();
            }
           
            if(seq.equals(QcMatrixRowModelParent.INDETERMINATE)){
                setStyle("-fx-background-color: red");
                setText(subOrSail+" I ");
            }else if(seq.equals(QcMatrixRowModelParent.SELECTED)){
                setStyle("-fx-background-color: greenyellow");
                setText(subOrSail+" G ");
            }else if(seq.equals(QcMatrixRowModelParent.UNSELECTED)){
                setStyle("-fx-background-color: orange");
                setText(subOrSail+" U ");
            } 
            selectedItem.refreshTable();
        }
        
    }
}
