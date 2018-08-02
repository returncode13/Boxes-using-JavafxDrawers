/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job2.definitions.qcmatrix.selected;


import fend.job.job2.JobType2Model;
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
public class SelectedQcType2Model {
    JobType2Model job;
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
    
    
    public SelectedQcType2Model(JobType2Model job) {
        this.job = job;
    }

    
    
    public JobType2Model getJob() {
        return job;
    }

    public void setJob(JobType2Model job) {
        this.job = job;
    }

    
    
    
    public ObservableList<String> getSelections() {
        return selections;
    }

    public void setSelections(List<String> selections) {
        this.selections=FXCollections.observableArrayList(selections);
    }
    
    
    
}
