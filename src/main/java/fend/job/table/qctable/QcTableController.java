/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable;

import fend.job.table.qctable.seq.QcTableSequence;
import fend.job.job0.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcTableController extends Stage{
    QcTableModel model;
    QcTableView view;
    
    Executor exec;
    
    /*@FXML
    private JFXTreeTableView<QcTableSequence> treeTableView;*/
    
     @FXML
    private TreeTableView<QcTableSequence> treetableView;

    
    public void setModel(QcTableModel item){
        model=item;
        exec=Executors.newCachedThreadPool(runnable->{
                Thread t=new Thread(runnable);
                t.setDaemon(true);
                return t;
        });
        
        
        /*        //List<TreeItem<QcTableSequence>> treeSeq=new ArrayList<>();
        Task<Void> qctableLoadTask=new Task<Void>() {
        @Override
        protected Void call() throws Exception {*/
                
       
        
        
        
        ObservableList<QcTableSequence> sequences=model.getQctableSequences();
        
        System.out.println("fend.job.table.qctable.QcTableController.setModel(): starting to build the qc table");
         List<TreeItem<QcTableSequence>> treeSeq=new ArrayList<>();
        for(QcTableSequence qcseq:sequences){
            TreeItem<QcTableSequence> qseqroot=new TreeItem<>(qcseq);
            for(QcTableSequence qcsub:qcseq.getChildren()){
                TreeItem<QcTableSequence> qcsubchild=new TreeItem<>(qcsub);
                qseqroot.getChildren().add(qcsubchild);
            }
            treeSeq.add(qseqroot);
        }
        
        System.out.println("fend.job.table.qctable.QcTableController.setModel(): finished building the qc table");
        
        
        
        System.out.println("fend.job.table.qctable.QcTableController.setView(): size of sequenceList received: "+sequences.size());
        List<TreeTableColumn<QcTableSequence,Boolean>> columns=new ArrayList<>();
        ObservableList<QcMatrixRowModelParent> qcMatrixForCols=sequences.get(0).getQcmatrix();
        
        //JFXTreeTableColumn<QcTableSequence,Long> seqCol=new JFXTreeTableColumn<>();
        
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
                
                String sublinename=new String();
                try{
                    sublinename=param.getValue().getValue().getSubsurface().getSubsurface();
                }
                catch(NullPointerException npe){
                    //System.out.println("fend.job.table.qctable.QcTableController.setView(): Null Pointer encountered");
                }
                if(sublinename==null){
                    return new SimpleStringProperty(sublinename=param.getValue().getValue().getSequence().getRealLineName());
                }else{
                    return new SimpleStringProperty(sublinename);
                }
            } 
        });
        
        System.out.println("fend.job.table.qctable.QcTableController.setView(): size of qcmatrix (number of qctypes):  "+qcMatrixForCols.size());
        
        for(int i=0;i<qcMatrixForCols.size();i++){
            QcMatrixRowModelParent qcrow=qcMatrixForCols.get(i);
            final int index=i;
            TreeTableColumn<QcTableSequence,Boolean> qcCol=new TreeTableColumn<>(qcrow.getName().get());
            qcCol.setText(qcrow.getName().get());
            
            qcCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<QcTableSequence, Boolean>, ObservableValue<Boolean>>() {
                @Override 
                public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<QcTableSequence, Boolean> param) {
                    QcTableSequence qseq=param.getValue().getValue();
                   
                     qcCol.setText(qseq.getQcmatrix().get(index).getName().get());
                     SimpleBooleanProperty checkUncheck=new SimpleBooleanProperty();
                     SimpleBooleanProperty indeterminate=new SimpleBooleanProperty();
                     String name=qseq.getSubsurface().getSubsurface();
                     if(name==null){
                         name=qseq.getSequence().getRealLineName();
                     }
                     
                   //  System.out.println(".call(): Sub: "+name+" DBQCResult CHKUNCHK: " +qseq.getQcmatrix().get(index).getCheckUncheckProperty().get());
                   //  System.out.println(".call(): Sub: "+name+" DBQCResult INDETERM: " +qseq.getQcmatrix().get(index).getIndeterminateProperty().get());
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
            
          qcCol.setCellFactory((param)->{return new CheckBoxCell(param, index,exec);});
          
            columns.add(qcCol);
        }
        
        
        
        //final TreeItem<QcTableSequence> root=new RecursiveTreeItem<>(sequences,RecursiveTreeObject::getChildren);
       
        
        treetableView.getColumns().addAll(seqCol,subCol);
        treetableView.getColumns().addAll(columns);
        CheckBoxTreeItem<QcTableSequence> root=new CheckBoxTreeItem<>();
        root.getChildren().addAll(treeSeq);
        root.setIndependent(true);
        treetableView.setRoot(root);
        treetableView.setShowRoot(false);
        
        /*   return null;
        
        }
        };
        
        
        
        qctableLoadTask.setOnFailed(e->{
        qctableLoadTask.getException().printStackTrace();
        });
        
        qctableLoadTask.setOnSucceeded(e->{
        
        
        });
        
        exec.execute(qctableLoadTask);*/
        
        
    }
    
    public void setView(QcTableView vw){
         this.view=vw;
        
        this.setTitle("qcTable");
        this.setScene(new Scene(this.view));
        this.show();
        

        
            
       
        
    }
}
