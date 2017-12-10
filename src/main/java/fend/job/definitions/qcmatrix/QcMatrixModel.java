/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.definitions.qcmatrix;

 
import fend.job.definitions.qcmatrix.qctype.QcMatrixRow;
import fend.job.job0.JobType0Model;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixModel {
    List<QcMatrixRow> qcMatrixRows;
    ObservableList<QcMatrixRow> observableQcMatrixRows;
    JobType0Model parentjob;
    
    public QcMatrixModel(JobType0Model parentjob) {
        this.parentjob=parentjob;
        qcMatrixRows=new ArrayList<>();
        observableQcMatrixRows=FXCollections.observableArrayList(qcMatrixRows);
       
    }

    public ObservableList<QcMatrixRow> getMatrixRows() {
        return observableQcMatrixRows;
    }

    public void setQcMatrixRows(List<QcMatrixRow> qcMatrixRows) {
        this.observableQcMatrixRows=FXCollections.observableArrayList(qcMatrixRows);
    }
    
    public void addQcMatrixRow(QcMatrixRow qctype){
        this.observableQcMatrixRows.add(qctype);
    }
    
    
    /**
     * Listeners
     *
     */
    final private ListChangeListener<QcMatrixRow> qcmatrixChangeListener=new ListChangeListener<QcMatrixRow>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends QcMatrixRow> c) {
            while(c.next()){
                for(QcMatrixRow q:c.getAddedSubList()){
                     System.out.println("fend.job.definitions.qcmatrix.QcMatrixModel.qcmatrixChangedListener.added() "+q.getName().get()+" selected? "+q.isChecked());
                     addToParentJob(q);
                }
                for(QcMatrixRow q:c.getRemoved()){
                     System.out.println("fend.job.definitions.qcmatrix.QcMatrixModel.qcmatrixChangedListener.removed() "+q.getName().get()+" selected? "+q.isChecked());
                     removeFromParentJob(q);
                }
            }
        }

       

       
    };
    
    
    /***
     * private implementations
     */
    
     private void addToParentJob(QcMatrixRow qcmr) {
         parentjob.addQcMatrixRow(qcmr);
     }
     
      private void removeFromParentJob(QcMatrixRow q) {
          parentjob.removeQcMatrixRow(q);
      }
}
