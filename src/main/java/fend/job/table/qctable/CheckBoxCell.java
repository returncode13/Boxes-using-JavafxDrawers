/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable;


import app.properties.AppProperties;
import db.model.Job;
import db.model.QcMatrixRow;
import db.model.QcTable;
import db.model.Subsurface;
import db.model.SubsurfaceJob;
import db.services.QcMatrixRowService;
import db.services.QcMatrixRowServiceImpl;
import db.services.QcTableService;
import db.services.QcTableServiceImpl;
import db.services.SubsurfaceJobService;
import db.services.SubsurfaceJobServiceImpl;
import fend.job.job0.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;
import fend.job.table.qctable.seq.QcTableSequence;
import java.util.List;
import java.util.concurrent.Executor;
import javafx.concurrent.Task;

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
    private SubsurfaceJobService subsurfaceJobService=new SubsurfaceJobServiceImpl();
    QcTableSequence selectedItem;
    int index;
    final CheckBox checkBox;
    Job job;

    CheckBoxCell(TreeTableColumn<QcTableSequence, Boolean> param, int ind,Executor exec,Job j) {
        
       this.param=param;
       checkBox=new CheckBox();
       checkBox.setAllowIndeterminate(true);
       index=ind;
       job=j;
      
      
      checkBox.selectedProperty().addListener((observable,oldValue,newValue)->{
          
          String passQcString=new String();
          if(newValue){
              passQcString=QcMatrixRowModelParent.SELECTED;
          }else{
              passQcString=QcMatrixRowModelParent.UNSELECTED;
          }
          
          String indString=QcMatrixRowModelParent.INDETERMINATE;
          
          int sel=getTreeTableRow().getIndex();
          String updateTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
          
          selectedItem=this.param.getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
          selectedItem.getQcmatrix().get(index).getIndeterminateProperty().set(false);
          selectedItem.getQcmatrix().get(index).getCheckUncheckProperty().set(newValue);
          
          
          selectedItem.setUpdateTime(updateTime);
          selectedItem.getQcmatrix().get(index).setPassQc(passQcString);
         
          
        
          
      });
      
      
       checkBox.indeterminateProperty().addListener((observable,oldValue,newValue)->{
          
          String indeterminateString=QcMatrixRowModelParent.INDETERMINATE;
          int sel=getTreeTableRow().getIndex();
          String updateTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
          selectedItem=this.param.getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
          selectedItem.setUpdateTime(updateTime);
          selectedItem.getQcmatrix().get(index).getIndeterminateProperty().set(newValue);
          
         
      });
     
       
       
       checkBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
           @Override
           public void handle(MouseEvent event) {
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
                                     selectedItem.horizontalQc();
                                 }
                                else{
                                     selectedItem.getParent().updateChildren=false;
                                     selectedItem.updateParent=true;
                                     updateUpwards(updateTime);
                                     selectedItem.horizontalQc();
                                     selectedItem.getParent().horizontalQc();
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
             String indString=QcMatrixRowModelParent.INDETERMINATE;
             String passQcString=new String();
                    QcMatrixRowModelParent qmr=selectedItem.getQcmatrix().get(index);
                   String result=qmr.isPassQc();
                   Long qcmrId=qmr.getId();
                     Subsurface childsub=selectedItem.getSubsurface();
                  //   System.out.println("updating dbentry for "+childsub.getSubsurface()+" : QM "+qcmrId+"  "+" "+qmr.getName().get()+" "+qmr.isPassQc());
                     
                     Boolean resForDb=null;
                            if(result.equals(QcMatrixRowModelParent.INDETERMINATE)) resForDb=null;
                            else if(result.equals(QcMatrixRowModelParent.SELECTED)) {resForDb=true;}
                            else{resForDb=false;}
                     
                     
                     QcTable qctable;
                     try {
                       // qctable=qcTableService.getQcTableFor(qcmrId, childsub);
                        int res=qcTableService.update(qcmrId,childsub,resForDb,updateTime,AppProperties.getCurrentUser());
                        if(res<0){   //no entry for qcr,chilsub
                            qctable=new QcTable();
                            QcMatrixRow dbqcmr=qcMatrixRowService.getQcMatrixRow(qcmrId);
                            qctable.setQcMatrixRow(dbqcmr);
                            qctable.setSubsurface(childsub);
                            qctable.setUpdateTime(updateTime);
                            qctable.setResult(resForDb);
                            qctable.setUser(AppProperties.getCurrentUser());
                            qcTableService.createQcTable(qctable);
                        }
                        
                       subsurfaceJobService.updateTimeWhere(job,childsub,updateTime);
                        
                        /* if(qctable!=null){                                              //update existing qctable entry
                        
                        qctable.setResult(resForDb);
                        qctable.setUpdateTime(updateTime);
                        qctable.setUser(AppProperties.getCurrentUser());
                        qcTableService.updateQcTable(qctable.getId(), qctable);
                        qcTableService.update(qctable);
                        QcMatrixRow dbqcmr=qcMatrixRowService.getQcMatrixRow(qcmrId);
                        SubsurfaceJob dbSubjob=subsurfaceJobService.getSubsurfaceJobFor(dbqcmr.getJob(), childsub);   //fetch from map
                        dbSubjob.setUpdateTime(updateTime);
                        subsurfaceJobService.updateSubsurfaceJob(dbSubjob);
                        }else{                                                      //create  a new qctable entry
                        qctable=new QcTable();
                        QcMatrixRow dbqcmr=qcMatrixRowService.getQcMatrixRow(qcmrId);
                        qctable.setQcMatrixRow(dbqcmr);
                        qctable.setSubsurface(childsub);
                        qctable.setUpdateTime(updateTime);
                        qctable.setResult(resForDb);
                        qctable.setUser(AppProperties.getCurrentUser());
                        qcTableService.createQcTable(qctable);
                        SubsurfaceJob dbSubjob=subsurfaceJobService.getSubsurfaceJobFor(dbqcmr.getJob(), childsub);
                        dbSubjob.setUpdateTime(updateTime);
                        subsurfaceJobService.updateSubsurfaceJob(dbSubjob);
                        }*/
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
                    selectedCount+=child.getQcmatrix().get(index).getCheckUncheckProperty().get()?1:0;
                }
                //System.out.println(selectedItem.getSequence().getSequenceno()+" updating parent: indcount: "+indeterminateCount+" selectCount: "+selectedCount);    
                if(indeterminateCount>0) {
                    
                     parent.getQcmatrix().get(index).getIndeterminateProperty().set(true);
                     parent.getQcmatrix().get(index).setPassQc(indString);
                     parent.setUpdateTime(updateTime);
                }
                else if(indeterminateCount==0 && selectedCount==children.size()){
                    passQcString=QcMatrixRowModelParent.SELECTED;
                    parent.getQcmatrix().get(index).getIndeterminateProperty().set(false);
                    parent.getQcmatrix().get(index).getCheckUncheckProperty().set(true);
                    parent.getQcmatrix().get(index).setPassQc(passQcString);
                    parent.setUpdateTime(updateTime);
                }else{
                    
                    passQcString=QcMatrixRowModelParent.UNSELECTED;
                    parent.getQcmatrix().get(index).getIndeterminateProperty().set(false);
                    parent.getQcmatrix().get(index).getCheckUncheckProperty().set(false);
                    parent.getQcmatrix().get(index).setPassQc(passQcString);
                    parent.setUpdateTime(updateTime);
                }
             //   updateParent=false;
            }
          //  CheckBoxCell.this.param.getTreeTableView().refresh();
            
           
        }
     
     private void updateDownwards(String updateTime){
            List<QcTableSequence> children=selectedItem.getChildren();
            String passQcString=new String();
            if(!selectedItem.getQcmatrix().get(index).getIndeterminateProperty().get()){
                    if(selectedItem.getQcmatrix().get(index).getCheckUncheckProperty().get()){
                        passQcString=QcMatrixRowModelParent.SELECTED;
                    }else{
                        passQcString=QcMatrixRowModelParent.UNSELECTED;
                    }
                }else{
                passQcString=QcMatrixRowModelParent.INDETERMINATE;
            }
             if(selectedItem.isParent() && selectedItem.updateChildren ){
                 
                 
                 Boolean resForDb=null;
                     
                 for(QcTableSequence child:children){
                   child.getQcmatrix().get(index).getCheckUncheckProperty().set(selectedItem.getQcmatrix().get(index).getCheckUncheckProperty().get());
                   child.getQcmatrix().get(index).getIndeterminateProperty().set(selectedItem.getQcmatrix().get(index).getIndeterminateProperty().get());
                   child.getQcmatrix().get(index).setPassQc(passQcString);
                   child.setUpdateTime(updateTime);
                  // QcMatrixRowModelParent qmr=child.getQcmatrix().get(index);
                  // String result=qmr.isPassQc();
                 //  Long qcmrId=qmr.getId();
                  //   Subsurface childsub=child.getSubsurface();
                   // System.out.println("updating dbentry for "+childsub.getSubsurface()+" : QM "+qcmrId+"  "+" "+qmr.getName().get()+" "+qmr.isPassQc());
                    
                    
                    
                    //
                    /* Boolean resForDb=null;
                            if(result.equals(QcMatrixRowModelParent.INDETERMINATE)) resForDb=null;
                            else if(result.equals(QcMatrixRowModelParent.SELECTED)) {resForDb=true;}
                            else{resForDb=false;}*/
                     
                     
                 //   QcTable qctable;
                    try {
                        /*int res=qcTableService.update(qcmrId,childsub,resForDb,updateTime,AppProperties.getCurrentUser());
                        if(res<0){   //no entry for qcr,chilsub
                        qctable=new QcTable();
                        QcMatrixRow dbqcmr=qcMatrixRowService.getQcMatrixRow(qcmrId);
                        qctable.setQcMatrixRow(dbqcmr);
                        qctable.setSubsurface(childsub);
                        qctable.setUpdateTime(updateTime);
                        qctable.setResult(resForDb);
                        qctable.setUser(AppProperties.getCurrentUser());
                        qcTableService.createQcTable(qctable);
                        }
                        */
                     //  subsurfaceJobService.updateTimeWhere(job,childsub,updateTime);
                         /*
                        qctable=qcTableService.getQcTableFor(qcmrId, childsub);
                        if(qctable!=null){                                              //update existing qctable entry
                            
                            qctable.setResult(resForDb);
                            qctable.setUpdateTime(updateTime);
                            qctable.setUser(AppProperties.getCurrentUser());
                            qcTableService.updateQcTable(qctable.getId(), qctable);
                            QcMatrixRow dbqcmr=qcMatrixRowService.getQcMatrixRow(qcmrId);           
                            SubsurfaceJob dbSubjob=subsurfaceJobService.getSubsurfaceJobFor(dbqcmr.getJob(), childsub);
                            dbSubjob.setUpdateTime(updateTime);
                            subsurfaceJobService.updateSubsurfaceJob(dbSubjob);
                        }else{                                                      //create  a new qctable entry
                            qctable=new QcTable();
                            QcMatrixRow dbqcmr=qcMatrixRowService.getQcMatrixRow(qcmrId);
                            qctable.setQcMatrixRow(dbqcmr);
                            qctable.setSubsurface(childsub);
                            qctable.setUpdateTime(updateTime);
                            qctable.setUser(AppProperties.getCurrentUser());
                            qctable.setResult(resForDb);
                            qcTableService.createQcTable(qctable);
                            SubsurfaceJob dbSubjob=subsurfaceJobService.getSubsurfaceJobFor(dbqcmr.getJob(), childsub);
                            dbSubjob.setUpdateTime(updateTime);
                            subsurfaceJobService.updateSubsurfaceJob(dbSubjob);
                        }*/
                    } catch (Exception ex) {
                       // Exceptions.printStackTrace(ex);
                       ex.printStackTrace();
                    }
                    
                    
                    //
                    
                    child.horizontalQc();
                    
                    
                 }
                  QcMatrixRowModelParent qmr=children.get(0).getQcmatrix().get(index);
                   String result=qmr.isPassQc();
                   Long qcmrId=qmr.getId();
                   if(result.equals(QcMatrixRowModelParent.INDETERMINATE)) resForDb=null;
                            else if(result.equals(QcMatrixRowModelParent.SELECTED)) {resForDb=true;}
                            else{resForDb=false;}
                qcTableService.setAllqcTableValuesFor(children.get(0).getSequence(), job,qcmrId, resForDb, updateTime, AppProperties.getCurrentUser());
                 subsurfaceJobService.updateTimeWhere(job,children.get(0).getSequence(),updateTime); 
              
             }
            // CheckBoxCell.this.param.getTreeTableView().refresh();
             
        }
    
}
