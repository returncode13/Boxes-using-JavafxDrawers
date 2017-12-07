/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.workspace;

import fend.edge.edge.EdgeModel;
import fend.job.job0.JobType0Model;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkspaceModel {

    public final static boolean DEBUG=true;
    private StringProperty name=new SimpleStringProperty("");
    private  List<JobType0Model> jobs=new ArrayList<>(); 
    private  List<EdgeModel> edges=new ArrayList<>();
    private ObservableList<JobType0Model> observableJobs=FXCollections.observableList(jobs);
    private ObservableList<EdgeModel> observableEdges=FXCollections.observableArrayList(edges);
    
   
    
    
    
   public void addJob(JobType0Model job) {
        this.observableJobs.add(job);
    }

    public ObservableList<JobType0Model> getObservableJobs() {
        return observableJobs;
    }

    public void setObservableJobs(List<JobType0Model> observableJobs) {
        this.observableJobs = FXCollections.observableArrayList(observableJobs);
    }

    
   
    public void addEdge(EdgeModel edge) {
        this.observableEdges.add(edge);
    }

    public ObservableList<EdgeModel> getObservableEdges() {
        return observableEdges;
    }

    public void setObservableEdges(List<EdgeModel> observableEdges) {
        this.observableEdges=FXCollections.observableArrayList(observableEdges);
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
    
    
    
}
