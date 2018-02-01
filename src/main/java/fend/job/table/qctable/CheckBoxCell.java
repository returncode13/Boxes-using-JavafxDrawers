/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable;


import app.properties.AppProperties;
import db.model.QcMatrixRow;
import db.model.QcTable;
import db.model.Subsurface;
import db.services.QcMatrixRowService;
import db.services.QcMatrixRowServiceImpl;
import db.services.QcTableService;
import db.services.QcTableServiceImpl;
import fend.job.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModel;
import fend.job.table.qctable.seq.QcTableSequence;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;

import javafx.scene.input.MouseEvent;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
//import org.openide.util.Exceptions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class CheckBoxCell extends TreeTableCell<QcTableSequence, Boolean> {
    TreeTableColumn<QcTableSequence, Boolean> param;
    private QcTableService qcTableService=new QcTableServiceImpl();
    private QcMatrixRowService qcMatrixRowService=new QcMatrixRowServiceImpl();
    QcTableSequence selectedItem;
    int index;
    final CheckBox checkBox;
    

    CheckBoxCell(TreeTableColumn<QcTableSequence, Boolean> param, int ind) {
        
       this.param=param;
       checkBox=new CheckBox();
       checkBox.setAllowIndeterminate(true);
       index=ind;
       
      
      
      checkBox.selectedProperty().addListener((observable,oldValue,newValue)->{
          
          String passQcString=new String();
          if(newValue){
              passQcString=QcMatrixRowModel.SELECTED;
          }else{
              passQcString=QcMatrixRowModel.UNSELECTED;
          }
          
          String indString=QcMatrixRowModel.INDETERMINATE;
          
          int sel=getTreeTableRow().getIndex();
          String updateTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
          
          selectedItem=this.param.getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
          selectedItem.getQcmatrix().get(index).getIndeterminateProperty().set(false);
          selectedItem.getQcmatrix().get(index).getCheckUncheckProperty().set(newValue);
          /*selectedItem.getQctypes().get(index).getIndeterminate().set(false);
          selectedItem.getQctypes().get(index).getCheckUncheck().set(newValue);*/
          
          selectedItem.setUpdateTime(updateTime);
          selectedItem.getQcmatrix().get(index).setPassQc(passQcString);
         
          
        
          
      });
      
      
       checkBox.indeterminateProperty().addListener((observable,oldValue,newValue)->{
           //System.out.println("fend.session.node.qcTable.CheckBoxCell.<init>() indeterminate: old: "+oldValue+" new: "+newValue);
          String indeterminateString=QcMatrixRowModel.INDETERMINATE;
          int sel=getTreeTableRow().getIndex();
          String updateTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
          selectedItem=this.param.getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
          selectedItem.setUpdateTime(updateTime);
         // System.out.println("fend.session.node.qcTable.CheckBoxCell.<init>(): indeterminateProperty(): "+newValue+" for "+selectedItem.getSequence().getSequenceno()+" : "+selectedItem.getSubsurface().getSubsurface());
          selectedItem.getQcmatrix().get(index).getIndeterminateProperty().set(newValue);
          
         
      });
     
       
       
       checkBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
           @Override
           public void handle(MouseEvent event) {
            //   System.out.println(".handle(): MouseClicked");
             
               String updateTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
               
               int sel=getTreeTableRow().getIndex();
               selectedItem=CheckBoxCell.this.param.getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
                
                if(selectedItem.isParent()){
                     selectedItem.updateChildren=true;
                    for(QcTableSequence child: selectedItem.getChildren()){
               //         System.out.println(".handle(): updating children");
                        child.updateParent=false;
                    }
                    updateDownwards(updateTime);
                }
               else{
                    selectedItem.getParent().updateChildren=false;
                    selectedItem.updateParent=true;
                    updateUpwards(updateTime);
                }
                
                
           }
           
       });
       
       checkBox.setOnMousePressed(new EventHandler<MouseEvent>(){
           @Override
           public void handle(MouseEvent event) {
               //System.out.println(".handle(): MousePressed");
           }
           
       });
       
       checkBox.setOnMouseReleased(new EventHandler<MouseEvent>(){
           @Override
           public void handle(MouseEvent event) {
              // System.out.println(".handle(): Released");
           }
       });
    }

    
    
    
    @Override
    protected void updateItem(Boolean qcstatus,boolean empty){
       super.updateItem(qcstatus, empty);
            
            if(empty){
                setGraphic(null);
            }else{
              
             Boolean value=getItem();
                
                if(qcstatus==null){
                    checkBox.setIndeterminate(true);
                   
                }
                else{
                    checkBox.setIndeterminate(false);
                    checkBox.setSelected(qcstatus);
                 
                }
               setGraphic(checkBox);
                getStylesheets().add("styles/checkcustom.css");
            }
    }
    
     private void updateUpwards(String updateTime){
             String indString=QcMatrixRowModel.INDETERMINATE;
             String passQcString=new String();
                    QcMatrixRowModel qmr=selectedItem.getQcmatrix().get(index);
                   String result=qmr.isPassQc();
                   Long qcmrId=qmr.getId();
                     Subsurface childsub=selectedItem.getSubsurface();
                  //   System.out.println("updating dbentry for "+childsub.getSubsurface()+" : QM "+qcmrId+"  "+" "+qmr.getName().get()+" "+qmr.isPassQc());
                     
                     Boolean resForDb=null;
                            if(result.equals(QcMatrixRowModel.INDETERMINATE)) resForDb=null;
                            else if(result.equals(QcMatrixRowModel.SELECTED)) {resForDb=true;}
                            else{resForDb=false;}
                     
                     
                     QcTable qctable;
                     try {
                        qctable=qcTableService.getQcTableFor(qcmrId, childsub);
                        if(qctable!=null){                                              //update existing qctable entry
                            
                            qctable.setResult(resForDb);
                            qctable.setUpdateTime(updateTime);
                            qcTableService.updateQcTable(qctable.getId(), qctable);
                        }else{                                                      //create  a new qctable entry
                            qctable=new QcTable();
                            QcMatrixRow dbqcmr=qcMatrixRowService.getQcMatrixRow(qcmrId);
                            qctable.setQcMatrixRow(dbqcmr);
                            qctable.setSubsurface(childsub);
                            qctable.setUpdateTime(updateTime);
                            qctable.setResult(resForDb);
                            qcTableService.createQcTable(qctable);
                        }
                    } catch (Exception ex) {
                        //Exceptions.printStackTrace(ex);
                        ex.printStackTrace();
                    }
                    
                    
            if(selectedItem.updateParent){
              
            List<QcTableSequence> children=selectedItem.getChildren();
            int indeterminateCount=0;
            int selectedCount=0;
           
                QcTableSequence parent=children.get(0).getParent();
                for(QcTableSequence child:children){
                    indeterminateCount+=child.getQcmatrix().get(index).getIndeterminateProperty().get()?1:0;
                    
                }
                //System.out.println(selectedItem.getSequence().getSequenceno()+" updating parent: indcount: "+indeterminateCount+" selectCount: "+selectedCount);    
                if(indeterminateCount>0) {
                    
                     parent.getQcmatrix().get(index).getIndeterminateProperty().set(true);
                     parent.getQcmatrix().get(index).setPassQc(indString);
                     parent.setUpdateTime(updateTime);
                }
                else if(indeterminateCount==0 && selectedCount==children.size()){
                    passQcString=QcMatrixRowModel.SELECTED;
                    parent.getQcmatrix().get(index).getIndeterminateProperty().set(false);
                    parent.getQcmatrix().get(index).getCheckUncheckProperty().set(true);
                    parent.getQcmatrix().get(index).setPassQc(passQcString);
                    parent.setUpdateTime(updateTime);
                }else{
                    passQcString=QcMatrixRowModel.UNSELECTED;
                    parent.getQcmatrix().get(index).getIndeterminateProperty().set(false);
                    parent.getQcmatrix().get(index).getCheckUncheckProperty().set(false);
                    parent.getQcmatrix().get(index).setPassQc(passQcString);
                    parent.setUpdateTime(updateTime);
                }
             //   updateParent=false;
            }
            CheckBoxCell.this.param.getTreeTableView().refresh();
            
           
        }
     
     private void updateDownwards(String updateTime){
            List<QcTableSequence> children=selectedItem.getChildren();
            String passQcString=new String();
            if(!selectedItem.getQcmatrix().get(index).getIndeterminateProperty().get()){
                    if(selectedItem.getQcmatrix().get(index).getCheckUncheckProperty().get()){
                        passQcString=QcMatrixRowModel.SELECTED;
                    }else{
                        passQcString=QcMatrixRowModel.UNSELECTED;
                    }
                }else{
                passQcString=QcMatrixRowModel.INDETERMINATE;
            }
             if(selectedItem.isParent() && selectedItem.updateChildren ){
                     
                 for(QcTableSequence child:children){
                   child.getQcmatrix().get(index).getCheckUncheckProperty().set(selectedItem.getQcmatrix().get(index).getCheckUncheckProperty().get());
                   child.getQcmatrix().get(index).getIndeterminateProperty().set(selectedItem.getQcmatrix().get(index).getIndeterminateProperty().get());
                   child.getQcmatrix().get(index).setPassQc(passQcString);
                   child.setUpdateTime(updateTime);
                   QcMatrixRowModel qmr=child.getQcmatrix().get(index);
                   String result=qmr.isPassQc();
                   Long qcmrId=qmr.getId();
                     Subsurface childsub=child.getSubsurface();
                   // System.out.println("updating dbentry for "+childsub.getSubsurface()+" : QM "+qcmrId+"  "+" "+qmr.getName().get()+" "+qmr.isPassQc());
                    
                    
                    
                    //
                     Boolean resForDb=null;
                            if(result.equals(QcMatrixRowModel.INDETERMINATE)) resForDb=null;
                            else if(result.equals(QcMatrixRowModel.SELECTED)) {resForDb=true;}
                            else{resForDb=false;}
                     
                     
                     QcTable qctable;
                     try {
                        qctable=qcTableService.getQcTableFor(qcmrId, childsub);
                        if(qctable!=null){                                              //update existing qctable entry
                            
                            qctable.setResult(resForDb);
                            qctable.setUpdateTime(updateTime);
                            qcTableService.updateQcTable(qctable.getId(), qctable);
                        }else{                                                      //create  a new qctable entry
                            qctable=new QcTable();
                            QcMatrixRow dbqcmr=qcMatrixRowService.getQcMatrixRow(qcmrId);
                            qctable.setQcMatrixRow(dbqcmr);
                            qctable.setSubsurface(childsub);
                            qctable.setUpdateTime(updateTime);
                            qctable.setResult(resForDb);
                            qcTableService.createQcTable(qctable);
                        }
                    } catch (Exception ex) {
                       // Exceptions.printStackTrace(ex);
                       ex.printStackTrace();
                    }
                    
                    
                    //
                    
                    
                    
                    
                 }
                
              
             }
             CheckBoxCell.this.param.getTreeTableView().refresh();
             
        }
    
}
