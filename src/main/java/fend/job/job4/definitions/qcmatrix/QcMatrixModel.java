/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job4.definitions.qcmatrix;


//import fend.job.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModel;
import fend.job.job0.JobType0Model;
import fend.job.job4.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModel;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixModel {
    List<QcMatrixRowModel> qcMatrixRows;
    ObservableList<QcMatrixRowModel> observableQcMatrixRows;
    JobType0Model parentjob;
    List<BooleanProperty> changePropertyList;
    
    public QcMatrixModel(JobType0Model parentjob) {
        this.parentjob=parentjob;
        qcMatrixRows=new ArrayList<>();
        observableQcMatrixRows=FXCollections.observableArrayList(qcMatrixRows);
        observableQcMatrixRows.addListener(qcmatrixChangeListener);
        
       
    }

    public ObservableList<QcMatrixRowModel> getMatrixRows() {
        return observableQcMatrixRows;
    }

    public void setQcMatrixRows(List<QcMatrixRowModel> qcMatrixRows) {
        this.observableQcMatrixRows=FXCollections.observableArrayList(qcMatrixRows);
    }
    
    public void addQcMatrixRow(QcMatrixRowModel qcmatrixrowmodel){
        this.observableQcMatrixRows.add(qcmatrixrowmodel);
        BooleanProperty changed=qcmatrixrowmodel.checkedProperty();
        //changed.addListener();
    }
    
    
    /**
     * Listeners
     *
     */
    final private ListChangeListener<QcMatrixRowModel> qcmatrixChangeListener=new ListChangeListener<QcMatrixRowModel>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends QcMatrixRowModel> c) {
            while(c.next()){
                for(QcMatrixRowModel q:c.getAddedSubList()){
                     System.out.println("fend.job.definitions.qcmatrix.QcMatrixModel.qcmatrixChangedListener.added() "+q.getName().get()+" selected? "+q.getCheckedByUser());
                     addToParentJob(q);
                }
                for(QcMatrixRowModel q:c.getRemoved()){
                     System.out.println("fend.job.definitions.qcmatrix.QcMatrixModel.qcmatrixChangedListener.removed() "+q.getName().get()+" selected? "+q.getCheckedByUser());
                     removeFromParentJob(q);
                }
                if(c.wasUpdated()){
                    System.out.println("fend.job.definitions.qcmatrix.QcMatrixModel.qcmatrixChangedListener.updated() "+c.getList().get(c.getFrom()).getName().get()+" selected? "+c.getList().get(c.getFrom()).getCheckedByUser());
                    
                }
            }
        }

       

       
    };
    
    
    /***
     * private implementations
     */
    
     private void addToParentJob(QcMatrixRowModel qcmr) {
         parentjob.addQcMatrixRow(qcmr);
     }
     
      private void removeFromParentJob(QcMatrixRowModel q) {
          parentjob.removeQcMatrixRow(q);
      }

    public JobType0Model getParentjob() {
        return parentjob;
    }
      
      /**
       * Listeners
       **/
    
    final private ChangeListener<Boolean> qcmrowselectionListener=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
           // updateParentJob()
        }
    };
}
