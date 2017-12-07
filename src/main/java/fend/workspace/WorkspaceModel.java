/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.workspace;

import fend.job.job1.JobType1Model;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkspaceModel {

    public final static boolean DEBUG=true;
   private  List<JobType1Model> boxes=new ArrayList<>(); 
    private ObservableList<JobType1Model> observableBoxes=FXCollections.observableList(boxes);
    
   
    
    
    
    public List<JobType1Model> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<JobType1Model> boxes) {
        this.boxes = boxes;
    }

    public ObservableList<JobType1Model> getObservableBoxes() {
        return observableBoxes;
    }
    
    
}
