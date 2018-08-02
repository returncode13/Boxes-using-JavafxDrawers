/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job3.definitions.qcmatrix.selected;



import fend.job.job3.JobType3Model;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SelectedQcType3Model {
    JobType3Model job;
    private List<String> selectedQcs=new ArrayList<>();
    private ObservableList<String> selections=FXCollections.observableArrayList(selectedQcs);
    private BooleanProperty updateListProperty=new SimpleBooleanProperty(false);
    
    public BooleanProperty updateListProperty(){
        return updateListProperty;
    }
    
    
    public void updateList(){
        boolean val=updateListProperty.get();
        updateListProperty.set(!val);
    }
    
    
    public SelectedQcType3Model(JobType3Model job) {
        this.job = job;
    }

    
    
    public JobType3Model getJob() {
        return job;
    }

    public void setJob(JobType3Model job) {
        this.job = job;
    }

    
    
    
    public ObservableList<String> getSelections() {
        return selections;
    }

    public void setSelections(List<String> selections) {
        this.selections=FXCollections.observableArrayList(selections);
    }
    
    
    
}
