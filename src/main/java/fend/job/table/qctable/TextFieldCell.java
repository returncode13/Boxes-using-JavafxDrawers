/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable;

import fend.job.table.qctable.seq.QcTableSequence;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeTablePosition;
import javafx.scene.control.cell.TextFieldTreeTableCell;

/**
 *
 * @author sharath.nair@polarcus.com
 */
public class TextFieldCell extends TextFieldTreeTableCell<QcTableSequence, String>{
    
    private TextField textField;
    private TreeTablePosition<QcTableSequence,String> tablePosition=null;
    private Tooltip tooltip=new Tooltip();
    
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        if(item==null){
            setTooltip(null);
            setText(null);
        }else{
            int sel=getTreeTableRow().getIndex();
            
            QcTableSequence qseq=getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
            tooltip.setText(qseq.getQcComment().getComments());
            setTooltip(tooltip);
            
            
        }
    }
    
    
    
}
