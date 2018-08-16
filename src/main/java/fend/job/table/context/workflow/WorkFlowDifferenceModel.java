/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.context.workflow;

import db.model.Header;
import db.model.Pheader;
import db.model.Workflow;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkFlowDifferenceModel {
    Set<Long> lhsVersions=new HashSet<>();
    ObservableList<Long> lhsObs=FXCollections.observableArrayList(lhsVersions);
    Set<Long> rhsVersions=new HashSet<>();
    ObservableList<Long> rhsObs=FXCollections.observableArrayList(rhsVersions);
    /* List<Workflow> workflows;*/
    StringProperty differenceText=new SimpleStringProperty();
    Workflow lhsWorkflow;
    Map<Long,Workflow> mapOfVersionsVersusWorkflows;
    Header chosenHdr;
    Pheader chosenPHdr;
    Boolean ptype=false;
    Boolean htype=false;
    
    public Header getChosenHdr() {
        return chosenHdr;
    }

    public void setChosenHdr(Header chosenHdr) {
        this.chosenHdr = chosenHdr;
        htype=true;
    }

    public Pheader getChosenPHdr() {
        return chosenPHdr;
    }

    public void setChosenPHdr(Pheader chosenPHdr) {
        this.chosenPHdr = chosenPHdr;
        ptype=true;
    }
    
    
    
    
    
    public Set<Long> getLhsVersions() {
        return lhsVersions;
    }

    public void setLhsVersions(Set<Long> lhsVersions) {
        this.lhsVersions = lhsVersions;
    }

    public Set<Long> getRhsVersions() {
        return rhsVersions;
    }

    public void setRhsVersions(Set<Long> rhsVersions) {
        this.rhsVersions = rhsVersions;
    }

    public String getDifferenceText() {
        return differenceText.get();
    }

    public void setDifferenceText(String differenceText) {
        this.differenceText.set(differenceText);
    }
    
    public StringProperty differenceTextProperty(){
        return differenceText;
    }

    public ObservableList<Long> getLhsObs() {
        return lhsObs;
    }

    public void setLhsObs(List<Long> lhsObs) {
        this.lhsObs =FXCollections.observableArrayList(lhsObs);
    }

    public ObservableList<Long> getRhsObs() {
        return rhsObs;
    }

   public void setRhsObs(List<Long> rhsObs) {
        this.rhsObs =FXCollections.observableArrayList(rhsObs);
    }

   /* public List<Workflow> getWorkflows() {
   return workflows;
   }
   
   public void setWorkflows(List<Workflow> workflows) {
   this.workflows = workflows;
   }*/

    public Workflow getLhsWorkflow() {
        return lhsWorkflow;
    }

    public void setLhsWorkflow(Workflow lhsWorkflow) {
        this.lhsWorkflow = lhsWorkflow;
    }

    public Map<Long, Workflow> getMapOfVersionsVersusWorkflows() {
        return mapOfVersionsVersusWorkflows;
    }

    public void setMapOfVersionsVersusWorkflows(Map<Long, Workflow> mapOfVersionsVersusWorkflows) {
        this.mapOfVersionsVersusWorkflows = mapOfVersionsVersusWorkflows;
    }
    
   
    
    
    
    
    
}
