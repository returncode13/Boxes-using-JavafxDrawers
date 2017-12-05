/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package job.job0;

import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import volume.volume0.Volume0;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface JobType0Model {
    public final static Long PROCESS_2D=1L;
    public Long getType();
    public StringProperty getNameproperty();
    public void setNameproperty(String name);
    public ObservableList<Volume0> getVolumes();
    public void setVolumes(ObservableList<Volume0> observableVolumes);
    public void addVolume(Volume0 vol);
    public void removeVolume(Volume0 vol);
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
}
