/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.workspace;

import db.model.Workspace;
import fend.edge.edge.EdgeModel;
import fend.job.job0.JobType0Model;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkspaceModel {

    public final static boolean DEBUG=true;
    private Long id;
    private StringProperty name=new SimpleStringProperty("");
    private  Set<JobType0Model> jobs=new HashSet<>(); 
    private  Set<EdgeModel> edges=new HashSet<>();
    private ObservableSet<JobType0Model> observableJobs=FXCollections.observableSet(jobs);
    private ObservableSet<EdgeModel> observableEdges=FXCollections.observableSet(edges);
    
   
    public WorkspaceModel(){
  //      id=UUID.randomUUID().getMostSignificantBits();
          id=null;
    }

    
    
    
    
   public void addJob(JobType0Model job) {
        this.observableJobs.add(job);
    }

    public ObservableSet<JobType0Model> getObservableJobs() {
        return observableJobs;
    }

    public void setObservableJobs(Set<JobType0Model> observableJobs) {
        this.observableJobs = FXCollections.observableSet(observableJobs);
    }

    
   
    public void addEdge(EdgeModel edge) {
        this.observableEdges.add(edge);
    }

    public ObservableSet<EdgeModel> getObservableEdges() {
        return observableEdges;
    }

    public void setObservableEdges(Set<EdgeModel> observableEdges) {
        this.observableEdges=FXCollections.observableSet(observableEdges);
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
}
