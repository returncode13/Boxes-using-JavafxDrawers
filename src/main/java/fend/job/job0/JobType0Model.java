/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job0;


import fend.job.definitions.qcmatrix.QcMatrixModel;
import fend.job.definitions.qcmatrix.qctype.QcMatrixRow;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import fend.volume.volume0.Volume0;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface JobType0Model {
    public final static Long PROCESS_2D=1L;
    public Long getId();
    public void setId(Long id);
    public Long getType();
    public StringProperty getNameproperty();
    public void setNameproperty(String name);
    public ObservableList<Volume0> getVolumes();
    public void setVolumes(List<Volume0> observableVolumes);
    public void addVolume(Volume0 vol);
    public void removeVolume(Volume0 vol);
    
    public ObservableList<QcMatrixRow> getQcMatrix();                   //shot <selected>,  stack <unselected>
    public void setQcMatrix(List<QcMatrixRow> observableQcMatrixRows);
    public void addQcMatrixRow(QcMatrixRow qcmrow);                     // shot<selected>
    public void removeQcMatrixRow(QcMatrixRow qcmrow);
    
    public BooleanProperty getChangeProperty();
    public void setChangeProperty(Boolean change);
    public ObservableSet<JobType0Model> getParents();
    public void setParents(Set<JobType0Model> parents);
    public ObservableSet<JobType0Model> getChildren();
    public void setChildren(Set<JobType0Model> children);
    public void addParent(JobType0Model parent);
    public void removeParent(JobType0Model parent);
    public void addChild(JobType0Model child);
    public void removeChild(JobType0Model child); 
    
    public ObservableSet<JobType0Model> getAncestors();
    public ObservableSet<JobType0Model> getDescendants();
   
    
    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();
    
}
