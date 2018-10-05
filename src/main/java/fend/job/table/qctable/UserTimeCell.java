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
public class UserTimeCell extends TreeTableCell<QcTableSequence, String>{
    TreeTableColumn<QcTableSequence, String> param;
    QcTableSequence selectedItem;
    int index;
    
    UserTimeCell(TreeTableColumn<QcTableSequence, String> param,int ind){
        this.param=param;
        this.index=ind;
    }
    
    
    @Override
    protected void updateItem(String seq,boolean empty){
        super.updateItem(seq, empty);
        if(seq == null||empty){
            setStyle("");
            setText("");
            setGraphic(null);
        }else{
           
            int sel=getTreeTableRow().getIndex();
            selectedItem=getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
            String userTime=new String();
             boolean isSub=selectedItem.isChild();
            if(selectedItem.isParent()){
              //  userTime=selectedItem.getQcmatrix().get(index).;
            }else{
                userTime=selectedItem.getSubsurface().getSubsurface();
            }
           
            if(seq.equals(QcMatrixRowModelParent.INDETERMINATE)){
                if(isSub){
                    setStyle("-fx-background-color: #ffa07a");
                }else{
                    setStyle("-fx-background-color: red");
                }
                setText(userTime);
            }else if(seq.equals(QcMatrixRowModelParent.SELECTED)){
                 if(isSub){
                    setStyle("-fx-background-color: #cdff82");
                }else{
                    setStyle("-fx-background-color: greenyellow");
                }
                
                setText(userTime);
            }else if(seq.equals(QcMatrixRowModelParent.UNSELECTED)){
                if(isSub){
                    setStyle("-fx-background-color: #ffb732");
                }else{
                    setStyle("-fx-background-color: orange");
                }
                setText(userTime);
            } 
          
        }
        
    }
}
