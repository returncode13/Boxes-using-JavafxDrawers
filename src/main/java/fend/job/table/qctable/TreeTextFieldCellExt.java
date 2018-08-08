/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable;

import fend.job.table.qctable.comment.CommentStackModel;
import fend.job.table.qctable.comment.CommentStackView;
import fend.job.table.qctable.seq.QcTableSequence;
import javafx.scene.control.cell.TextFieldTreeTableCell;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class TreeTextFieldCellExt extends TextFieldTreeTableCell<QcTableSequence,String> {

    
    QcTableSequence selectedItem;
    public TreeTextFieldCellExt() {
         
        setOnContextMenuRequested(e->{
              selectedItem= getTreeTableView().getSelectionModel().getModelItem(getTreeTableRow().getIndex()).getValue();
            String cm=selectedItem.getComment();
            CommentStackModel cms=new CommentStackModel();
            cms.setCommentStack(cm);
            if(selectedItem.isParent()){
                cms.setTitle(selectedItem.getSequence().getSequenceno()+"");
            }else{
                cms.setTitle(selectedItem.getSequence().getSequenceno()+" : "+selectedItem.getSubsurface().getSubsurface());
            }
           
            
            CommentStackView cmv=new CommentStackView(cms);
        });
        
    }
    
}
