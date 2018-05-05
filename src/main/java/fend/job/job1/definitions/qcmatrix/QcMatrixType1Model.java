/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job1.definitions.qcmatrix;

 
//import fend.job.definitions.qcmatrix.qcmatrixrow.QcMatrixRowType1Model;
import fend.job.job0.JobType0Model;
import fend.job.job1.JobType1Model;
import fend.job.job1.definitions.qcmatrix.qcmatrixrow.QcMatrixRowType1Model;
import fend.job.job1.definitions.qcmatrix.selected.SelectedQcType1Model;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixType1Model {
    List<QcMatrixRowType1Model> qcMatrixRows;
    ObservableList<QcMatrixRowType1Model> observableQcMatrixRows;
    JobType0Model parentjob;
    List<BooleanProperty> changePropertyList;
    SelectedQcType1Model selectedModel;
    BooleanProperty reloadProperty=new SimpleBooleanProperty(false);
    
    
    public BooleanProperty reloadProperty(){
        return reloadProperty;
    }
    
    public void reload(){
        boolean val=reloadProperty.get();
        reloadProperty.set(!val);
    }
    
    public QcMatrixType1Model(JobType0Model parentjob,SelectedQcType1Model sm) {
        this.parentjob=parentjob;
        qcMatrixRows=new ArrayList<>();
        observableQcMatrixRows=FXCollections.observableArrayList(qcMatrixRows);
        observableQcMatrixRows.addListener(qcmatrixChangeListener);
        selectedModel=sm;
       
    }

    public SelectedQcType1Model getSelectedModel() {
        return selectedModel;
    }

    public void setSelectedModel(SelectedQcType1Model selectedModel) {
        this.selectedModel = selectedModel;
    }

   

    public ObservableList<QcMatrixRowType1Model> getMatrixRows() {
        return observableQcMatrixRows;
    }

    public void setQcMatrixRows(List<QcMatrixRowType1Model> qcMatrixRows) {
        this.observableQcMatrixRows=FXCollections.observableArrayList(qcMatrixRows);
    }
    
    public void addQcMatrixRow(QcMatrixRowType1Model qcmatrixrowmodel){
        this.observableQcMatrixRows.add(qcmatrixrowmodel);
        BooleanProperty changed=qcmatrixrowmodel.checkedProperty();
        //changed.addListener();
    }
    
    
    /**
     * Listeners
     *
     */
    final private ListChangeListener<QcMatrixRowType1Model> qcmatrixChangeListener=new ListChangeListener<QcMatrixRowType1Model>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends QcMatrixRowType1Model> c) {
            while(c.next()){
                for(QcMatrixRowType1Model q:c.getAddedSubList()){
                     System.out.println("fend.job.definitions.qcmatrix.QcMatrixModel.qcmatrixChangedListener.added() "+q.getName().get()+" selected? "+q.getCheckedByUser());
                     addToParentJob(q);
                }
                for(QcMatrixRowType1Model q:c.getRemoved()){
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
    
     private void addToParentJob(QcMatrixRowType1Model qcmr) {
         parentjob.addQcMatrixRow(qcmr);
         selectedModel.updateList();
     }
     
      private void removeFromParentJob(QcMatrixRowType1Model q) {
          parentjob.removeQcMatrixRow(q);
          selectedModel.updateList();
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
