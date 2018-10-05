/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable;

import fend.job.table.qctable.comment.CommentStackModel;
import fend.job.table.qctable.comment.CommentStackView;
import fend.job.table.qctable.seq.QcTableSequence;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class TreeTextFieldCellExt extends TextFieldTreeTableCell<QcTableSequence,String> {

    
    QcTableSequence selectedItem;
    public TreeTextFieldCellExt() {
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton()==MouseButton.PRIMARY && event.getClickCount()==2){
                    event.consume();
                }
            }
        });
    }
    
    
    
}
