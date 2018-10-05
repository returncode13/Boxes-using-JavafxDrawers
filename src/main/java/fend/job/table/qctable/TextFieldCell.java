/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable;

import app.properties.AppProperties;
import db.model.Comment;
import db.model.CommentType;
import db.model.Job;
import db.model.User;
import db.services.CommentService;
import fend.comments.CommentTypeModel;
import fend.job.table.qctable.seq.QcTableSequence;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeTablePosition;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import fend.job.table.qctable.comment.CommentStackModel;
import fend.job.table.qctable.comment.CommentStackView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableCell;

/**
 *
 * @author sharath.nair@polarcus.com
 */
public class TextFieldCell extends TreeTableCell<QcTableSequence, String>{
    
   TextField textfield=new TextField();
   QcTableSequence selectedItem;
   CommentType qcCommentType;
   User currentUser;
   CommentService commentService;
   Job job;
   boolean deleted=false;
   boolean commit=true;
    public TextFieldCell(Job j,User u,CommentType ct,CommentService cs) {
        
        
        job=j;
        currentUser=u;
        qcCommentType=ct;
        commentService=cs;
        
        textfield.textProperty().addListener((obs,o,n)->{
            
            System.out.println("fend.job.table.qctable.TextFieldCell.<init>(): changed from "+o+"  -- > "+n);
            if(n.equals(QcTableSequence.DELETED)){
                deleted=true;
            }
            
            if(o.trim().equals(n.trim())){
                commit=false;
            }else{
                commit=true;
            }
            
            if(!o.trim().isEmpty() && n.trim().isEmpty()){
                deleted=true;
             }else{
                deleted=false;
            }
            
        });
        
        
        textfield.focusedProperty().addListener((obs,o,n)->{
            if(!n){
                if(commit){
                    if(!deleted && !textfield.getText().trim().isEmpty()){
                     System.out.println("fend.job.table.qctable.TextFieldCell.<init>(): will commit : "+textfield.getText() );
                     commitEdit(textfield.getText());
                    }
                    if(deleted){
                         System.out.println("fend.job.table.qctable.TextFieldCell.<init>(): will commit : "+textfield.getText() );
                         commitEdit(textfield.getText());
                    }
                }
                
               
            }
            
        });
    }
   
   
   
   
    @Override
    public void cancelEdit() {
        super.cancelEdit(); //To change body of generated methods, choose Tools | Templates.
        System.out.println("fend.job.table.qctable.TextFieldCell.cancelEdit()");
    }

    @Override
    public void commitEdit(String newValue) {
        super.commitEdit(newValue); //To change body of generated methods, choose Tools | Templates.
        go(newValue);
        
        
    }

    @Override
    public void startEdit() {
        super.startEdit(); //To change body of generated methods, choose Tools | Templates.
        System.out.println("fend.job.table.qctable.TextFieldCell.startEdit()");
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty); 
        if(empty){
            setText(null);
            setGraphic(null);
        }else{
            int sel=getTreeTableRow().getIndex();
            selectedItem=getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
           
            
            if(deleted){
                    textfield.setStyle("-fx-text-inner-color: gray;");
                    textfield.setPromptText(QcTableSequence.DELETED);
                    textfield.setText("");
                }else{
                    textfield.setStyle("-fx-text-inner-color: black;");
                    textfield.setText(selectedItem.getComment());
                }
            setGraphic(textfield);
        }
    }

    private void go(String com) {
          
          
            /* if(e.getNewValue().isEmpty()){
            return;
            }*/
             currentUser=AppProperties.getCurrentUser();
            String userTimeStamp=AppProperties.timeNow()+" > "+currentUser.getInitials()+" > ";
            QcTableSequence qseq=selectedItem;
            System.out.println("fend.job.table.qctable.QcTableController.setModel(): "+qseq.getSequence().getSequenceno()+" "+com);
            
           // qseq.addToComment(e.getNewValue());
                        if(!qseq.isChild()){
                            try {
                                System.out.println("end.job.table.qctable.QcTableController.setModel().changed(): will commit: "+com+" for "+qseq.getSequence().getSequenceno());
                                Comment seqcomment=null;
                                    if((seqcomment=commentService.getCommentFor(CommentTypeModel.TYPE_QC,job,qseq.getSequence(),null)) == null){
                                        seqcomment=new Comment();
                                        seqcomment.setSequence(qseq.getSequence());
                                        seqcomment.setSubsurface(null);
                                        seqcomment.setCommentType(qcCommentType);
                                        seqcomment.setJob(job);
                                        seqcomment.setComments(userTimeStamp+com);

                                        commentService.createComment(seqcomment);
                                        qseq.setLatestComment(seqcomment);
                                    }else{
                                        String oldComments=seqcomment.getComments();
                                        String newComments=userTimeStamp+com+"\n"+oldComments;
                                        seqcomment.setComments(newComments);
                                        commentService.updateComment(seqcomment);
                                        qseq.setLatestComment(seqcomment);
                                        //commentService.addToCommentStackFor(qseq.getSequence(),null, model.getParentJob(),newComments, CommentTypeModel.TYPE_QC);
                                    }
                                } catch (Exception ex) {
                                    Logger.getLogger(QcTableController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                        else {
                            System.out.println("end.job.table.qctable.QcTableController.setModel().changed(): will commit: "+com+" for "+qseq.getSequence().getSequenceno()+" "+qseq.getSubsurface().getSubsurface());
                            try {
                            Comment subcomment=null;
                                    if((subcomment=commentService.getCommentFor(CommentTypeModel.TYPE_QC,job,qseq.getSequence(),qseq.getSubsurface())) == null){
                                        subcomment=new Comment();
                                        subcomment.setSequence(qseq.getSequence());
                                        subcomment.setSubsurface(qseq.getSubsurface());
                                        subcomment.setCommentType(qcCommentType);
                                        subcomment.setJob(job);
                                        subcomment.setComments(userTimeStamp+com);

                                        commentService.createComment(subcomment);
                                        qseq.setLatestComment(subcomment);
                                    }else{
                                        String oldComments=subcomment.getComments();
                                        String newComments=userTimeStamp+com+"\n"+oldComments;
                                        subcomment.setComments(newComments);
                                        commentService.updateComment(subcomment);
                                        qseq.setLatestComment(subcomment);
                                        //commentService.addToCommentStackFor(qseq.getSequence(),qseq.getSubsurface(), model.getParentJob(),newComments, CommentTypeModel.TYPE_QC);
                                    }
                                } catch (Exception ex) {
                                    Logger.getLogger(QcTableController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
        
        
        
    }
   
    
    
}
