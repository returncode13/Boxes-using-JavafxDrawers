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
import db.model.User;
import db.services.QcMatrixRowService;
import db.services.QcMatrixRowServiceImpl;
import db.services.QcTableService;
import db.services.QcTableServiceImpl;
import db.services.SubsurfaceJobService;
import db.services.SubsurfaceJobServiceImpl;
import fend.job.job0.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;
import fend.job.table.qctable.seq.QcTableSequence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;

import javafx.event.EventHandler;
import javafx.scene.control.Label;

import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
//import org.openide.util.Exceptions;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class CheckBoxLabelCell extends TreeTableCell<QcTableSequence, Boolean> {
    TreeTableColumn<QcTableSequence, Boolean> param;
    private QcTableService qcTableService=new QcTableServiceImpl();
    private QcMatrixRowService qcMatrixRowService=new QcMatrixRowServiceImpl();
    private SubsurfaceJobService subsurfaceJobService=new SubsurfaceJobServiceImpl();
    QcTableSequence selectedItem;
    int index;
    final PQCheckBox checkBox;
    final Label label;
    final HBox hbox;
    Job job;
    User winner=AppProperties.getCurrentUser();
    String winTime=new String();
    Map<String,HashMap<String,User>> racemap=new HashMap<>();
       final String IND="ind";
       final String CHK="chk";
       final String UNCHK="uchk";
       final Executor exec; 
       
    CheckBoxLabelCell(TreeTableColumn<QcTableSequence, Boolean> param, int ind,Executor e,Job j,BooleanProperty bp) {
        
       this.param=param;
       checkBox=new PQCheckBox();
       label=new Label();
       hbox=new HBox(checkBox,label);
       checkBox.setAllowIndeterminate(true);
       this.exec=e;
       index=ind;
       job=j;
       
       label.setVisible(false);
       racemap.put(IND,new HashMap<>());
       racemap.put(CHK,new HashMap<>());
       racemap.put(UNCHK,new HashMap<>());
      
       bp.addListener(SHOW_LABEL_LISTENER);
       
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
          /*selectedItem.getQcmatrix().get(index).setUser(AppProperties.getCurrentUser());
          selectedItem.getQcmatrix().get(index).setLastUpdatedTime(updateTime);s*/
          //label.setText(selectedItem.getQcmatrix().get(index).getUser().getInitials()+" @ "+selectedItem.getQcmatrix().get(index).getLastUpdatedTime());
        
          
      });
      
      
       checkBox.indeterminateProperty().addListener((observable,oldValue,newValue)->{
          
          String indeterminateString=QcMatrixRowModelParent.INDETERMINATE;
          int sel=getTreeTableRow().getIndex();
          String updateTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
          selectedItem=this.param.getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
          selectedItem.setUpdateTime(updateTime);
          selectedItem.getQcmatrix().get(index).getIndeterminateProperty().set(newValue);
          /*  selectedItem.getQcmatrix().get(index).setUser(AppProperties.getCurrentUser());
          selectedItem.getQcmatrix().get(index).setLastUpdatedTime(updateTime);*/
           //label.setText(selectedItem.getQcmatrix().get(index).getUser().getInitials()+" @ "+selectedItem.getQcmatrix().get(index).getLastUpdatedTime());
          
      });
     
       
       
       checkBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
           @Override
           public void handle(MouseEvent event) {
                             String updateTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);

                                int sel=getTreeTableRow().getIndex();
                                selectedItem=CheckBoxLabelCell.this.param.getTreeTableView().getSelectionModel().getModelItem(sel).getValue();

                                 if(selectedItem.isParent()){
                                      selectedItem.updateChildren=true;
                                     for(QcTableSequence child: selectedItem.getChildren()){
                                //         System.out.println(".handle(): updating children");
                                         child.updateParent=false;
                                     }
                                     updateDownwards(updateTime);
                                     selectedItem.horizontalQc();
                                     selectedItem.getQcmatrix().get(index).setUser(AppProperties.getCurrentUser());
                                     selectedItem.getQcmatrix().get(index).setLastUpdatedTime(updateTime);
                                     selectedItem.getQcmatrix().get(index).setUser(winner==null?AppProperties.getCurrentUser():winner);
                                     selectedItem.getQcmatrix().get(index).setLastUpdatedTime(winTime);
                                 }
                                else{
                                     selectedItem.getParent().updateChildren=false;
                                     selectedItem.updateParent=true;
                                     updateUpwards(updateTime);
                                     selectedItem.horizontalQc();
                                     selectedItem.getQcmatrix().get(index).setUser(AppProperties.getCurrentUser());
                                     selectedItem.getQcmatrix().get(index).setLastUpdatedTime(updateTime);
                                     selectedItem.getParent().horizontalQc();
                                     selectedItem.getParent().getQcmatrix().get(index).setUser(winner==null?AppProperties.getCurrentUser():winner);
                                     selectedItem.getParent().getQcmatrix().get(index).setLastUpdatedTime(winTime);
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
                int sel=getTreeTableRow().getIndex();
                 selectedItem=getTreeTableView().getSelectionModel().getModelItem(sel).getValue();
                 if(selectedItem.isParent()){
                    // label.setText(" user @ time");
                     //System.out.println("fend.job.table.qctable.CheckBoxLabelCell.updateItem() Winner: "+selectedItem.getWinnerForQcMatrixFromChildren(selectedItem.getQcmatrix().get(index), winTime).getInitials()+" @ "+winTime);
                     
                     String sequser=selectedItem.getWinnerForQcMatrixFromChildren(selectedItem.getQcmatrix().get(index)).getInitials();
                     String time=selectedItem.getWinningTime();
                     //label.setText(selectedItem.getWinnerForQcMatrixFromChildren(selectedItem.getQcmatrix().get(index), winTime).getInitials()+" @ "+winTime);
                     label.setText(sequser+" @ "+time);
                 }else{
                     label.setText(selectedItem.getQcmatrix().get(index).getUser().getInitials()+" @ "+selectedItem.getQcmatrix().get(index).getLastUpdatedTime());
                 }
                 //label.setText(selectedItem.getQcmatrix().get(index).getUser().getInitials()+" @ "+selectedItem.getQcmatrix().get(index).getLastUpdatedTime());
                 
                //label.setText(label.getText());
               //setGraphic(checkBox);
               setGraphic(hbox);
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
                    
                            
                            final Boolean resfDb=resForDb;
                     
                            /**
                             **/
                            
                             Task<Void> qctableTask=new Task<Void>() {
                                 
                                 
                                        @Override
                                       protected Void call() throws Exception {
                                                        //  qctable.setDisable(true);
                                                        QcTable qctable;
                                                        try {
                                                            // qctable=qcTableService.getQcTableFor(qcmrId, childsub);
                                                            int res = qcTableService.update(qcmrId,childsub,resfDb,updateTime, AppProperties.getCurrentUser());
                                                            if (res < 0) {   //no entry for qcr,chilsub
                                                                qctable = new QcTable();
                                                                QcMatrixRow dbqcmr = qcMatrixRowService.getQcMatrixRow(qcmrId);
                                                                qctable.setQcMatrixRow(dbqcmr);
                                                                qctable.setSubsurface(childsub);
                                                                qctable.setUpdateTime(updateTime);
                                                                qctable.setResult(resfDb);
                                                                qctable.setUser(AppProperties.getCurrentUser());
                                                                qcTableService.createQcTable(qctable);
                                                            }
                                                            subsurfaceJobService.updateTimeWhere(job, childsub, AppProperties.timeNow());
                                                        } catch (Exception ex) {
                                                            //Exceptions.printStackTrace(ex);
                                                            ex.printStackTrace();
                                                        }
                                                        return null;
                                                    }
                                                };

                                             qctableTask.setOnFailed(e->{
                                            qctableTask.getException().printStackTrace();
                                               
                                            });
                                            qctableTask.setOnSucceeded(e->{

                                                  System.out.println("fend.job.table.qctable.CheckBoxLabelCell.updateUpwards(): done commiting to db id,sub,result,updateTime,user"+qcmrId+","+childsub+","+
                                                          (resfDb==null?"NULL":resfDb)+","+updateTime+","+ AppProperties.getCurrentUser());

                                            });
                                            qctableTask.setOnRunning(e->{
                                                    System.out.println("fend.job.table.qctable.CheckBoxLabelCell.updateUpwards(): commit in progress to db id,sub,result,updateTime,user"+qcmrId+","+childsub+","+
                                                          (resfDb==null?"NULL":resfDb)+","+updateTime+","+ AppProperties.getCurrentUser());
                                            });

                                            exec.execute(qctableTask);
                            
                            
                            /**
                             *
                             
                             **/
                            
                            
                            
                            /*
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
                            
                            subsurfaceJobService.updateTimeWhere(job,childsub,updateTime);*/
                       
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
                        /*   } catch (Exception ex) {
                        //Exceptions.printStackTrace(ex);
                        ex.printStackTrace();
                        }*/
                    
                    
            if(selectedItem.updateParent){
              
            List<QcTableSequence> children=selectedItem.getChildren();
             selectedItem.addToRaceMap(resForDb, qmr, AppProperties.getCurrentUser(), updateTime);
            
            
            int indeterminateCount=0;
            int selectedCount=0;
            Long lastTime=0L;
                QcTableSequence parent=children.get(0).getParent();
                for(QcTableSequence child:children){
                    indeterminateCount+=child.getQcmatrix().get(index).getIndeterminateProperty().get()?1:0;
                    selectedCount+=child.getQcmatrix().get(index).getCheckUncheckProperty().get()?1:0;
                    /* User user=child.getQcmatrix().get(index).getUser();
                    String time=child.getQcmatrix().get(index).getLastUpdatedTime();
                    Long valt=Long.valueOf(time);
                    if(valt>lastTime) lastTime=valt;
                    if(child.getQcmatrix().get(index).getIndeterminateProperty().get()){
                    racemap.get(IND).put(time,user);
                    }
                    if(child.getQcmatrix().get(index).getCheckUncheckProperty().get()){
                    racemap.get(CHK).put(time,user);
                    }else{
                    racemap.get(UNCHK).put(time,user);
                    }*/
                }
                //System.out.println(selectedItem.getSequence().getSequenceno()+" updating parent: indcount: "+indeterminateCount+" selectCount: "+selectedCount);    
                if(indeterminateCount>0) {
                    
                     parent.getQcmatrix().get(index).getIndeterminateProperty().set(true);
                     parent.getQcmatrix().get(index).setPassQc(indString);
                     parent.setUpdateTime(updateTime);
                     //winner=user with highest frequency in map.get(IND)
                     //winner=racemap.get(IND).get(lastTime+"");
                    // winTime=""+lastTime;
                     
                }
                else if(indeterminateCount==0 && selectedCount==children.size()){
                    passQcString=QcMatrixRowModelParent.SELECTED;
                    parent.getQcmatrix().get(index).getIndeterminateProperty().set(false);
                    parent.getQcmatrix().get(index).getCheckUncheckProperty().set(true);
                    parent.getQcmatrix().get(index).setPassQc(passQcString);
                    parent.setUpdateTime(updateTime);
                    // winner=racemap.get(CHK).get(lastTime+"");
                    // winTime=""+lastTime;
                }else{
                    
                    passQcString=QcMatrixRowModelParent.UNSELECTED;
                    parent.getQcmatrix().get(index).getIndeterminateProperty().set(false);
                    parent.getQcmatrix().get(index).getCheckUncheckProperty().set(false);
                    parent.getQcmatrix().get(index).setPassQc(passQcString);
                    parent.setUpdateTime(updateTime);
                   //  winner=racemap.get(UNCHK).get(lastTime+"");
                   //  winTime=""+lastTime;
                }
                
               // parent.getQcmatrix().get(index).setUser(AppProperties.getCurrentUser());
               // parent.getQcmatrix().get(index).setLastUpdatedTime(updateTime);
             //   updateParent=false;
            }
           CheckBoxLabelCell.this.param.getTreeTableView().refresh();
            
           
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
                
                /* winner=AppProperties.getCurrentUser();
                winTime=updateTime;*/
            }
             if(selectedItem.isParent() && selectedItem.updateChildren ){
                 
                 
                 Boolean resForDb=null;
                     
                 for(QcTableSequence child:children){
                   child.getQcmatrix().get(index).getCheckUncheckProperty().set(selectedItem.getQcmatrix().get(index).getCheckUncheckProperty().get());
                   child.getQcmatrix().get(index).getIndeterminateProperty().set(selectedItem.getQcmatrix().get(index).getIndeterminateProperty().get());
                   child.getQcmatrix().get(index).setPassQc(passQcString);
                   child.setUpdateTime(updateTime);
                   child.getQcmatrix().get(index).setUser(AppProperties.getCurrentUser());
                   child.getQcmatrix().get(index).setLastUpdatedTime(updateTime);
                   child.addToRaceMap(resForDb, child.getQcmatrix().get(index), AppProperties.getCurrentUser(), updateTime);
                   
                   
                   
                   
                   
                   
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
                 //   try {
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
                   // } catch (Exception ex) {
                       // Exceptions.printStackTrace(ex);
                   //    ex.printStackTrace();
                  //  }
                    
                    
                    //
                    
                    child.horizontalQc();
                    
                    
                 }
                 CheckBoxLabelCell.this.param.getTreeTableView().refresh();
                  QcMatrixRowModelParent qmr=children.get(0).getQcmatrix().get(index);
                   String result=qmr.isPassQc();
                   Long qcmrId=qmr.getId();
                   if(result.equals(QcMatrixRowModelParent.INDETERMINATE)) resForDb=null;
                            else if(result.equals(QcMatrixRowModelParent.SELECTED)) {resForDb=true;}
                            else{resForDb=false;}
                   
                   /**
                    **/
                          
                            final Boolean resfDb=resForDb;
                     
                            /**
                             **/
                            
                             Task<Void> qctableTask=new Task<Void>() {
                                 
                                 
                                        @Override
                                       protected Void call() throws Exception {
                                                        //  qctable.setDisable(true);
                                                        QcTable qctable;
                                                        try {
                                                            // qctable=qcTableService.getQcTableFor(qcmrId, childsub);
                                                            qcTableService.setAllqcTableValuesFor(children.get(0).getSequence(), job,qcmrId, resfDb, updateTime, AppProperties.getCurrentUser());
                                                            subsurfaceJobService.updateTimeWhere(job,children.get(0).getSequence(),updateTime); 
                                                        } catch (Exception ex) {
                                                            //Exceptions.printStackTrace(ex);
                                                            ex.printStackTrace();
                                                        }
                                                        return null;
                                                    }
                                                };

                                             qctableTask.setOnFailed(e->{
                                            qctableTask.getException().printStackTrace();
                                               
                                            });
                                            qctableTask.setOnSucceeded(e->{

                                                  System.out.println("fend.job.table.qctable.CheckBoxLabelCell.updateDownwards(): done commiting to db id,seqNo,result,updateTime,user"+qcmrId+","+children.get(0).getSequence().getSequenceno()+","+
                                                          (resfDb==null?"NULL":resfDb)+","+updateTime+","+ AppProperties.getCurrentUser());

                                            });
                                            qctableTask.setOnRunning(e->{
                                                    System.out.println("fend.job.table.qctable.CheckBoxLabelCell.updateDownwards(): commit in progress to db id,seqNo,result,updateTime,user"+qcmrId+","+children.get(0).getSequence().getSequenceno()+","+
                                                          (resfDb==null?"NULL":resfDb)+","+updateTime+","+ AppProperties.getCurrentUser());
                                            });

                                            exec.execute(qctableTask);
                            
                            
                   /**
                    */
                   
                   /* qcTableService.setAllqcTableValuesFor(children.get(0).getSequence(), job,qcmrId, resForDb, updateTime, AppProperties.getCurrentUser());
                   subsurfaceJobService.updateTimeWhere(job,children.get(0).getSequence(),updateTime); */
              
             }
             //CheckBoxLabelCell.this.param.getTreeTableView().refresh();
             
        }
    
     
     private ChangeListener<Boolean> SHOW_LABEL_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            //toggleLabelText();
            label.setVisible(newValue);
        }

        
    };
         
     private void toggleLabelText() {
         String orig=new String();
            if(!(label.getText().isEmpty())){
                orig=label.getText();
                label.setText("");
            }else{
                label.setText(orig);
            }
            
     }
}
