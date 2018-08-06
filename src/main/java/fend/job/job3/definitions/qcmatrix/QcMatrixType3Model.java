/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job3.definitions.qcmatrix;

 
//import fend.job.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModel;
import fend.job.job0.JobType0Model;
import fend.job.job0.definitions.qcmatrix.Qint;
import fend.job.job3.definitions.qcmatrix.qcmatrixrow.QcMatrixRowType3Model;
import fend.job.job3.definitions.qcmatrix.selected.SelectedQcType3Model;


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
public class QcMatrixType3Model implements Qint{
    List<QcMatrixRowType3Model> qcMatrixRows;
    ObservableList<QcMatrixRowType3Model> observableQcMatrixRows;
    JobType0Model parentjob;
    List<BooleanProperty> changePropertyList;
    
     SelectedQcType3Model selectedModel;
     BooleanProperty reloadProperty=new SimpleBooleanProperty(false);
    
    
    public BooleanProperty reloadProperty(){
        return reloadProperty;
    }
    
    public void reload(){
        boolean val=reloadProperty.get();
        reloadProperty.set(!val);
    }
    
    
    public QcMatrixType3Model(JobType0Model parentjob,SelectedQcType3Model sm) {
        this.parentjob=parentjob;
        this.parentjob.setQcMatrixModel(this);
        qcMatrixRows=new ArrayList<>();
        observableQcMatrixRows=FXCollections.observableArrayList(qcMatrixRows);
        observableQcMatrixRows.addListener(qcmatrixChangeListener);
        selectedModel=sm;
       
    }

    public SelectedQcType3Model getSelectedModel() {
        return selectedModel;
    }

    public void setSelectedModel(SelectedQcType3Model selectedModel) {
        this.selectedModel = selectedModel;
    }
    
    
    
    @Override
    public int listSize() {
        return observableQcMatrixRows.size();
    }

    public ObservableList<QcMatrixRowType3Model> getMatrixRows() {
        return observableQcMatrixRows;
    }

    public void setQcMatrixRows(List<QcMatrixRowType3Model> qcMatrixRows) {
        this.observableQcMatrixRows=FXCollections.observableArrayList(qcMatrixRows);
    }
    
    public void addQcMatrixRow(QcMatrixRowType3Model qcmatrixrowmodel){
        this.observableQcMatrixRows.add(qcmatrixrowmodel);
        BooleanProperty changed=qcmatrixrowmodel.checkedProperty();
        //changed.addListener();
    }
    
    
    /**
     * Listeners
     *
     */
    final private ListChangeListener<QcMatrixRowType3Model> qcmatrixChangeListener=new ListChangeListener<QcMatrixRowType3Model>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends QcMatrixRowType3Model> c) {
            while(c.next()){
                for(QcMatrixRowType3Model q:c.getAddedSubList()){
                     System.out.println("fend.job.definitions.qcmatrix.QcMatrixModel.qcmatrixChangedListener.added() "+q.getName().get()+" selected? "+q.getCheckedByUser());
                     addToParentJob(q);
                }
                for(QcMatrixRowType3Model q:c.getRemoved()){
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
    
     private void addToParentJob(QcMatrixRowType3Model qcmr) {
         parentjob.addQcMatrixRow(qcmr);
     }
     
      private void removeFromParentJob(QcMatrixRowType3Model q) {
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
