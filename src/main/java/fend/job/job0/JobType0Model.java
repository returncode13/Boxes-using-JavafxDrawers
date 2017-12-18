/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job0;


import fend.job.definitions.qcmatrix.QcMatrixModel;
import fend.job.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModel;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import fend.volume.volume0.Volume0;
import fend.workspace.WorkspaceModel;
import java.util.List;
import javafx.beans.property.LongProperty;
import middleware.sequences.SequenceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface JobType0Model {
    public final static Long INITIAL_DEPTH=0L;
    public final static Long PROCESS_2D=1L;
    public Long getId();
    public void setId(Long id);
    public void setDepth(Long depth);
    public LongProperty getDepth();
    public Long getType();
    public StringProperty getNameproperty();
    public void setNameproperty(String name);
    public ObservableList<Volume0> getVolumes();
    public void setVolumes(List<Volume0> observableVolumes);
    public void addVolume(Volume0 vol);
    public void removeVolume(Volume0 vol);
    
    public ObservableList<QcMatrixRowModel> getQcMatrix();                   //shot <selected>,  stack <unselected>
    public void setQcMatrix(List<QcMatrixRowModel> observableQcMatrixRows);
    public void addQcMatrixRow(QcMatrixRowModel qcmrow);                     // shot<selected>
    public void removeQcMatrixRow(QcMatrixRowModel qcmrow);
    
    public BooleanProperty  getListenToDepthChangeProperty();
    public void setListenToDepthChange(Boolean listenToDepthChange);
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
   
    public ObservableList<SequenceHeaders> getSequenceHeaders();  
    
    
    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();

    public WorkspaceModel getWorkspaceModel();
    
}
