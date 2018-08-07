/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable;

import app.properties.AppProperties;
import db.model.Comment;
import db.model.CommentType;
import db.model.User;
import db.model.UserPreference;
import db.services.CommentService;
import db.services.CommentServiceImpl;
import db.services.CommentTypeService;
import db.services.CommentTypeServiceImpl;
import db.services.QcTableService;
import db.services.QcTableServiceImpl;
import db.services.UserPreferenceService;
import db.services.UserPreferenceServiceImpl;
import fend.comments.CommentTypeModel;
import fend.job.table.qctable.seq.QcTableSequence;
import fend.job.job0.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.text.normalizer.UBiDiProps;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTableController extends Stage{
    private final String JSON_ORDER="order";
    final private String COMMENTS="Comments";
    final private String SEQUENCE="Sequence";
    final private String SUBSURFACE="Subsurface";
    
    
    QcTableModel model;
    QcTableView view;
    QcTableService qcTableService=new QcTableServiceImpl();
    CommentService commentService=new CommentServiceImpl();
    CommentTypeService commentTypeService=new CommentTypeServiceImpl();
    UserPreferenceService userPreferenceService=new UserPreferenceServiceImpl();
    CommentType qcCommentType;
    User currentUser;
    Executor exec;
    Map<String,TreeTableColumn> userPrefColumnArrangement;
    UserPreference up=null;
    /*@FXML
    private JFXTreeTableView<QcTableSequence> treeTableView;*/
    List<TreeTableColumn<QcTableSequence,?>> allcols;
    List<TreeItem<QcTableSequence>> treeSeq;
    BooleanProperty showUserTime=new SimpleBooleanProperty(false);
    List<TreeTableColumn<QcTableSequence,?>> columns;
    
     @FXML
    private Button fairAndJustButton;
    
    
     @FXML
    private TreeTableView<QcTableSequence> treetableView;

    public void init(){
         qcCommentType=commentTypeService.getCommentTypeByName(CommentTypeModel.TYPE_QC);
       
        
        exec=Executors.newCachedThreadPool(runnable->{
                Thread t=new Thread(runnable);
                t.setDaemon(true);
                return t;
        });
        
        
    } 
     
     
     
    
    public void setModel(QcTableModel item){
        model=item;
       
        userPrefColumnArrangement=new HashMap<>();
        model.reloadSequencesProperty().addListener(REFRESH_TABLE_LISTENER);
        model.showUserTimeStampProperty().addListener(EXPAND_TABLE_COLUMN_LISTENER);
        ObservableList<QcTableSequence> sequences=model.getQctableSequences();
        
        System.out.println("fend.job.table.qctable.QcTableController.setModel(): starting to build the qc table");
        treeSeq=new ArrayList<>();
        for(QcTableSequence qcseq:sequences){
           // qcseq.refreshTableProperty().addListener(REFRESH_TABLE_LISTENER);
            TreeItem<QcTableSequence> qseqroot=new TreeItem<>(qcseq);
            for(QcTableSequence qcsub:qcseq.getChildren()){
             //   qcsub.refreshTableProperty().addListener(REFRESH_TABLE_LISTENER);
                TreeItem<QcTableSequence> qcsubchild=new TreeItem<>(qcsub);
                qseqroot.getChildren().add(qcsubchild);
            }
            treeSeq.add(qseqroot);
        }
        
        System.out.println("fend.job.table.qctable.QcTableController.setModel(): finished building the qc table");
        
        
        
        System.out.println("fend.job.table.qctable.QcTableController.setView(): size of sequenceList received: "+sequences.size());
        columns=new ArrayList<>();
        ObservableList<QcMatrixRowModelParent> qcMatrixForCols;
        if(!sequences.isEmpty()){
            qcMatrixForCols=sequences.get(0).getQcmatrix();
        }
        else{
            qcMatrixForCols=FXCollections.observableArrayList();
        }
        
        
        
        TreeTableColumn<QcTableSequence,Long> seqCol=new TreeTableColumn<>();
        seqCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<QcTableSequence, Long>, ObservableValue<Long>>() {
            @Override
            public ObservableValue<Long> call(TreeTableColumn.CellDataFeatures<QcTableSequence, Long> param) {
              return new SimpleObjectProperty<>(param.getValue().getValue().getSequence().getSequenceno());
            } 
        });
        //JFXTreeTableColumn<QcTableSequence,String> subCol=new JFXTreeTableColumn<>();
        TreeTableColumn<QcTableSequence,String> subCol=new TreeTableColumn<>();
       
        
        subCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<QcTableSequence, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<QcTableSequence, String> param) {
                return param.getValue().getValue().labelProperty();
            }
        });
        
        subCol.setCellFactory(p->{return new LabelCell(p);});
        
        System.out.println("fend.job.table.qctable.QcTableController.setView(): size of qcmatrix (number of qctypes):  "+qcMatrixForCols.size());
        
        int totalColumnCount=0;
        List<String> namesOfcols=new ArrayList<>();
        
        
        for(int i=0;i<qcMatrixForCols.size();i++){
            QcMatrixRowModelParent qcrow=qcMatrixForCols.get(i);
            final int index=i;
            //TreeTableColumn<QcTableSequence,Boolean> qcCol=new TreeTableColumn<>(qcrow.getName().get());
            //TreeTableColumn<QcTableSequence,Boolean> qcCol=new TreeTableColumn<>();
            TreeTableColumn<QcTableSequence,Boolean> qcCol=new TreeTableColumn<>();
           // qcCol.setText(qcrow.getName().get());
            
            qcCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<QcTableSequence, Boolean>, ObservableValue<Boolean>>() {
                @Override 
                public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<QcTableSequence, Boolean> param) {
                    QcTableSequence qseq=param.getValue().getValue();
                        
                    
                     //qcCol.setText(qseq.getQcmatrix().get(index).getName().get());
                     SimpleBooleanProperty checkUncheck=new SimpleBooleanProperty();
                     SimpleBooleanProperty indeterminate=new SimpleBooleanProperty();
                     String name=qseq.getSubsurface().getSubsurface();
                     if(name==null){
                         name=qseq.getSequence().getRealLineName();
                     }
                 
                     checkUncheck.bindBidirectional(qseq.getQcmatrix().get(index).getCheckUncheckProperty());
                     indeterminate.bindBidirectional(qseq.getQcmatrix().get(index).getIndeterminateProperty());
                     
                     
                     checkUncheck.addListener(new ChangeListener<Boolean>(){
                         @Override
                         public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                             qseq.getQcmatrix().get(index).getIndeterminateProperty().set(false);
                             qseq.getQcmatrix().get(index).getCheckUncheckProperty().set(newValue);
                         }
                         
                     });
                     
                     
                     indeterminate.addListener(new ChangeListener<Boolean>(){
                         @Override
                         public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            
                                 qseq.getQcmatrix().get(index).getIndeterminateProperty().set(newValue);
                                 
                         }
                         
                     });
                 
                 if(indeterminate.get()){
                        return null;
                    }else{
                        return checkUncheck;
                    }    
                     
                     
                }
            });
            
         // qcCol.setCellFactory((param)->{return new CheckBoxCell(param, index,exec,model.getDbJob());});
          qcCol.setCellFactory((param)->{return new CheckBoxLabelCell(param, index,exec,model.getDbJob(),showUserTime);});
          //Label qcL=new Label(qseq.getQcmatrix().get(index).getName().get());
          
          
          
             Label qcL=new Label(qcrow.getName().get());
                    VBox qcvbx=new VBox(qcL);
                    qcvbx.setRotate(-90);
                    qcvbx.setPadding(new Insets(5,5,5,5));
                    Group qcvgrp=new Group(qcvbx);
                    qcvgrp.setAccessibleText(qcL.getText());
                    qcCol.setGraphic(qcvgrp);
                    
                    ++totalColumnCount;
                    namesOfcols.add(qcL.getText());
                    userPrefColumnArrangement.put(qcL.getText(), qcCol);
                    
                    
                    //who done it columns
                    /*TreeTableColumn<QcTableSequence,String> usertimestamp=new TreeTableColumn<>("fnj");
                    usertimestamp.setCellValueFactory(p->{return new SimpleStringProperty(p.getValue().getValue().getUpdateTime());});
                    qcCol.getColumns().add(usertimestamp);*/
                    
            
                    /*
                    TreeTableColumn<QcTableSequence,String> userTimeCol=new TreeTableColumn<>();
                    userTimeCol.setCellValueFactory(p->{
                    return new SimpleStringProperty(p.getValue().getValue().getQcmatrix().get(index).getUser().getInitials()+" @ "+p.getValue().getValue().getQcmatrix().get(index).getLastUpdatedTime());
                    
                    });
                    userTimeCol.setCellFactory();
                    TreeTableColumn<QcTableSequence,?> com=new TreeTableColumn<>();
                    com.getColumns().addAll(qcCol,userTimeCol);*/
                    qcCol.setPrefWidth(30);
            columns.add(qcCol);
           // columns.add(com);
        }
        
        
        //Comment column
        
        TreeTableColumn<QcTableSequence,String> commentCol=new TreeTableColumn<>(COMMENTS);
        namesOfcols.add(COMMENTS);
        userPrefColumnArrangement.put(COMMENTS, commentCol);
       commentCol.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        //commentCol.setCellFactory(TextFieldCell.forTreeTableColumn());
        commentCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<QcTableSequence, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<QcTableSequence, String> param) {
                QcTableSequence qseq=param.getValue().getValue();
                return qseq.commentProperty();
            }
        });
        
        
        
        commentCol.setOnEditCommit(e->{
            if(e.getOldValue().equals(e.getNewValue())){
                return;
            }
            
            if(e.getNewValue().isEmpty()){
               return; 
            }
             currentUser=AppProperties.getCurrentUser();
            String userTimeStamp=timeNow()+" > "+currentUser.getInitials()+" > ";
            
            System.out.println("fend.job.table.qctable.QcTableController.setModel(): "+e.getRowValue().getValue().getSequence()+" "+e.getNewValue());
            QcTableSequence qseq=e.getRowValue().getValue();
           // qseq.addToComment(e.getNewValue());
                        if(!qseq.isChild()){
                            try {
                                System.out.println("end.job.table.qctable.QcTableController.setModel().changed(): will commit: "+e.getNewValue()+" for "+qseq.getSequence().getSequenceno());
                                Comment seqcomment=null;
                                    if((seqcomment=commentService.getCommentFor(CommentTypeModel.TYPE_QC,model.getDbJob(),qseq.getSequence(),null)) == null){
                                        seqcomment=new Comment();
                                        seqcomment.setSequence(qseq.getSequence());
                                        seqcomment.setSubsurface(null);
                                        seqcomment.setCommentType(qcCommentType);
                                        seqcomment.setJob(model.getDbJob());
                                        seqcomment.setComments(userTimeStamp+e.getNewValue());

                                        commentService.createComment(seqcomment);
                                        qseq.setQcComment(seqcomment);
                                    }else{
                                        String oldComments=seqcomment.getComments();
                                        String newComments=userTimeStamp+e.getNewValue()+"\n"+oldComments;
                                        seqcomment.setComments(newComments);
                                        commentService.updateComment(seqcomment);
                                        qseq.setQcComment(seqcomment);
                                        //commentService.addToCommentStackFor(qseq.getSequence(),null, model.getParentJob(),newComments, CommentTypeModel.TYPE_QC);
                                    }
                                } catch (Exception ex) {
                                    Logger.getLogger(QcTableController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                        else {
                            System.out.println("end.job.table.qctable.QcTableController.setModel().changed(): will commit: "+e.getNewValue()+" for "+qseq.getSequence().getSequenceno()+" "+qseq.getSubsurface().getSubsurface());
                            try {
                            Comment subcomment=null;
                                    if((subcomment=commentService.getCommentFor(CommentTypeModel.TYPE_QC,model.getDbJob(),qseq.getSequence(),qseq.getSubsurface())) == null){
                                        subcomment=new Comment();
                                        subcomment.setSequence(qseq.getSequence());
                                        subcomment.setSubsurface(qseq.getSubsurface());
                                        subcomment.setCommentType(qcCommentType);
                                        subcomment.setJob(model.getDbJob());
                                        subcomment.setComments(userTimeStamp+e.getNewValue());

                                        commentService.createComment(subcomment);
                                        qseq.setQcComment(subcomment);
                                    }else{
                                        String oldComments=subcomment.getComments();
                                        String newComments=userTimeStamp+e.getNewValue()+"\n"+oldComments;
                                        subcomment.setComments(newComments);
                                        commentService.updateComment(subcomment);
                                        qseq.setQcComment(subcomment);
                                        //commentService.addToCommentStackFor(qseq.getSequence(),qseq.getSubsurface(), model.getParentJob(),newComments, CommentTypeModel.TYPE_QC);
                                    }
                                } catch (Exception ex) {
                                    Logger.getLogger(QcTableController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
        
        
        
        });
        commentCol.setEditable(true);
         
        seqCol.setText(SEQUENCE); 
            namesOfcols.add(SEQUENCE);
            userPrefColumnArrangement.put(SEQUENCE, seqCol);
        subCol.setText(SUBSURFACE);
            namesOfcols.add(SUBSURFACE);
            userPrefColumnArrangement.put(SUBSURFACE, subCol);
        
       
        totalColumnCount+=3;                    //three cols in addition to the qc matrix (seq,sub,comment);
        //loading user preferences
        allcols=new ArrayList<>();
        
        JSONObject jsnLoad=null;
        
        
         
      if((up=userPreferenceService.getUserPreferenceFor(model.getDbJob(), qcCommentType))==null){
          up=new UserPreference();
          up.setJob(model.getDbJob());
          up.setCommentType(qcCommentType);
          userPreferenceService.createUserPreference(up);
          
            for (Map.Entry<String, TreeTableColumn> entry : userPrefColumnArrangement.entrySet()) {
              String name = entry.getKey();
              TreeTableColumn qcol = entry.getValue();
              allcols.add(qcol);
              
          }
          
      }else{
          String jsonContents=up.getJsonProperty();
          try{
              jsnLoad=new JSONObject(jsonContents);
              
               for(int ii=0;ii<totalColumnCount;ii++){                           // the order is always 0,1,2,3...,
                    if(jsnLoad.has(""+ii)){
                        if(namesOfcols.contains(jsnLoad.get(""+ii))){
                            allcols.add(userPrefColumnArrangement.get(jsnLoad.get(""+ii)));
                            namesOfcols.remove(jsnLoad.get(""+ii));
                        }
                    }
                    
                    
                }
                
                if(!namesOfcols.isEmpty()){          //any new qctypes NOT common to the json file  
                        for(String s:namesOfcols){
                            allcols.add(userPrefColumnArrangement.get(s));      //the new ones are appended to the end and sorted by default
                        }
                }
            
              
              
          }catch(JSONException e){
              
              
              System.out.println("fend.job.table.qctable.QcTableController.setModel(): JSON EXCEPTION: could not read the json object: contents: "+jsonContents);
              for (Map.Entry<String, TreeTableColumn> entry : userPrefColumnArrangement.entrySet()) {
                  String key = entry.getKey();
                  TreeTableColumn value = entry.getValue();
                  allcols.add(value);
              }
          }
           
          
      }
       
        
        
       
        /*  treetableView.getColumns().addAll(seqCol,subCol);
        treetableView.getColumns().addAll(columns);
        treetableView.getColumns().addAll(commentCol);*/
        
       
        
        
        
        
        
    }
    
    
   private void buildTreeView(){
        
        treetableView.getColumns().addAll(allcols);
        CheckBoxTreeItem<QcTableSequence> root=new CheckBoxTreeItem<>();
        root.getChildren().addAll(treeSeq);
        root.setIndependent(true);
        treetableView.setRoot(root);
        treetableView.setShowRoot(false);
        treetableView.setEditable(true);
        
        final List<TreeTableColumn<QcTableSequence,?>> unchangedColumns=Collections.unmodifiableList(treetableView.getColumns());
        
        JSONObject jsn=new JSONObject();
        
        setOnCloseRequest(e->{
            System.out.println("fend.job.table.qctable.QcTableController.setModel(): Save preferences now");
            
            
            ObservableList<TreeTableColumn<QcTableSequence,?>> cols=treetableView.getColumns();
            int[] columnOrder=new int[cols.size()];
            
            for(int i=0;i<cols.size();i++){
                columnOrder[i]=unchangedColumns.indexOf(cols.get(i));
                if(cols.get(i)
                         .getGraphic()!=null){
                    jsn.put(""+i,cols.get(i)
                         .getGraphic()
                         .getAccessibleText());
                }else{
                    jsn.put(""+i,cols.get(i).getText());
                }
                
                 
                
            }
            
            List<Integer> order=new ArrayList<>();
            for(int i=0;i<columnOrder.length;i++){
                System.out.print(" "+columnOrder[i]+" ");
              order.add(i);
                  
            }System.out.println("");
            jsn.put(JSON_ORDER, order);
        
          String newPref=jsn.toString();
          up.setJsonProperty(newPref);
          userPreferenceService.updateUserPreference(up);
          
          model.getJob().exitedQcTable();
            
            
        });
        
        
   }
    
    public void setView(QcTableView vw){
        buildTreeView();
       
        
        this.view=vw;
        
        this.setTitle("qcTable for "+model.getDbJob().getNameJobStep());
        this.setScene(new Scene(this.view));
        this.show();
        

        
            
       
        
    }
    
    
    public void reload(){
        this.model.reloadSequencesProperty().unbind();
        view.setDisable(true);
        treetableView.setPlaceholder(new ProgressBar(-1));
        allcols.clear();
          Task<Void> qctableTask=new Task<Void>() {
        @Override
        protected Void call() throws Exception {
                  //  qctable.setDisable(true);
                  model=new QcTableModel(model.getJob());
                  QcTableController.this.setModel(model);
                     return null;
                    }
                    };
            
             qctableTask.setOnFailed(e->{
            qctableTask.getException().printStackTrace();
             view.setDisable(false);
               model.getJob().unblock();
            });
            qctableTask.setOnSucceeded(e->{
                view.setDisable(false);
                treetableView.getColumns().clear();
                    buildTreeView();
                   treetableView.refresh();
                   model.getJob().unblock();
            
            });
            qctableTask.setOnRunning(e->{
                //model.getJob().block();
                view.setDisable(true);
                treetableView.setPlaceholder(new ProgressBar(-1.0));
                
            });
            
            exec.execute(qctableTask);
        
        
        
        
        
        
    }
    
    private ChangeListener<Boolean> REFRESH_TABLE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
           reload();
           
        }
    };
    
    
    
    private ChangeListener<Boolean> EXPAND_TABLE_COLUMN_LISTENER = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
         

        }
    };
    
    private String timeNow() {
        return DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
    }

    
    @FXML
    private Button reloadBtn;

    
    @FXML
    void reloadTable(ActionEvent event) {
        this.reload();
    }

      @FXML
    void fairAndJust(ActionEvent event) {
        showWhoDoneIt();
    }

    int colsize=3+AppProperties.TIMESTAMP_FORMAT.length()+1;
    
    private void showWhoDoneIt() {
       // model.toggleUserTimeStamp();
       showUserTime.set(!showUserTime.get());
       if(showUserTime.get()){
           colsize=180;
       }else{
           colsize=30;
       }
       for(TreeTableColumn<QcTableSequence, ?> c: columns){
           c.setPrefWidth(colsize);
       }
    }

}
