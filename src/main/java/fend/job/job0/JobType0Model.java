/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job0;


import db.model.Job;
import db.model.Log;
import db.model.Subsurface;
import fend.job.job0.definitions.qcmatrix.QcMatrixModel;
//import fend.job.job1.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;
import fend.job.job0.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;
import fend.job.job0.property.JobModelProperty;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import fend.volume.volume0.Volume0;
import fend.workspace.WorkspaceModel;
import java.util.List;
import java.util.Map;
import javafx.beans.property.LongProperty;
import middleware.sequences.SequenceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface JobType0Model {
    public final static Long INITIAL_DEPTH=0L;
    public final static Long PROCESS_2D=1L;
    public final static Long SEGD_LOAD=2L;
    public final static Long ACQUISITION=3L;
    public final static Long TEXT=4L;
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
    
    public ObservableList<QcMatrixRowModelParent> getQcMatrix();                   //shot <selected>,  stack <unselected>
    public void setQcMatrix(List<QcMatrixRowModelParent> observableQcMatrixRows);
    public void addQcMatrixRow(QcMatrixRowModelParent qcmrow);                     // shot<selected>
    public void removeQcMatrixRow(QcMatrixRowModelParent qcmrow);
    
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
    public List<JobModelProperty> getJobProperties();
    public void setJobProperties(List<JobModelProperty> jobProperties);
    
    public Job getDatabaseJob();
    public void setDatabaseJob(Job job);
    public Map<Subsurface,Log> getLatestLogForSubsurfaceMap();
    public void setLatestLogForSubsurfaceMap(Map<Subsurface,Log> mapOfLatestLogForSubsurface);
    
    
    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();

    public WorkspaceModel getWorkspaceModel();
    
}
