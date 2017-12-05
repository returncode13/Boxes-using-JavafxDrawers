/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package job.definitions.volume;

import workspace.WorkspaceModel;
import job.job1.JobType1Model;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import job.job0.JobType0Model;
import volume.volume0.Volume0;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * class holds a list of volumes
 */
public class VolumeListModel {
    List<Volume0> volumes;
    List<String> subsurfacesInVolume;
    ObservableList<Volume0> observableListOfVolumes;
    JobType0Model parentJob;                     //the job that contains this list
    
    
    public VolumeListModel(JobType0Model parentBox) {
        this.parentJob=parentBox;
        volumes=new ArrayList<>();
        observableListOfVolumes=FXCollections.observableArrayList(volumes);
        
        observableListOfVolumes.addListener(new ListChangeListener<Volume0>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends Volume0> c) {
                    while(c.next()){
                        for(Volume0 vol:c.getAddedSubList()){
                            if(WorkspaceModel.DEBUG) System.out.println("box.definitions.volume.VolumeListModel.added(): "+vol.getName());
                            addVolumeToParentJob(vol);
                        }
                        
                        for(Volume0 vol:c.getRemoved()){
                            if(WorkspaceModel.DEBUG) System.out.println("box.definitions.volume.VolumeListModel.removed(): "+vol.getName());
                            removeVolumeFromParentJob(vol);
                        }
                    }
            }

            
           
        });
        
        
        
        
        
    }

    /* public VolumeListModel() {    //Remove this constructor.
    volumes=new ArrayList<>();
    observableListOfVolumes=FXCollections.observableArrayList(volumes);
    
    observableListOfVolumes.addListener(new ListChangeListener<Volume0>(){
    @Override
    public void onChanged(ListChangeListener.Change<? extends Volume0> c) {
    while(c.next()){
    for(Volume0 vol:c.getAddedSubList()){
    if(WorkspaceModel.DEBUG) System.out.println("box.definitions.volume.VolumeListModel.added(): "+vol.getName());
    addVolumeToParentJob();
    }
    
    for(Volume0 vol:c.getRemoved()){
    if(WorkspaceModel.DEBUG) System.out.println("box.definitions.volume.VolumeListModel.removed(): "+vol.getName());
    addVolumeToParentJob();
    }
    }
    }
    
    
    });
    }*/
    
    

    public ObservableList<Volume0> getObservableListOfVolumes() {
        return observableListOfVolumes;
    }

    public List<Volume0> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume0> volumes) {
        this.volumes = volumes;
    }
    
    public void addToVolumeList(Volume0 vol){
        observableListOfVolumes.add(vol);
    }
    
    public void removeFromVolumeList(Volume0 vol){
        observableListOfVolumes.remove(vol);
    }
    
     private void addVolumeToParentJob(Volume0 vol) {
         System.out.println("box.definitions.volume.VolumeListModel.updateParentJob(): Send this update to the Parentjob");
         parentJob.addVolume(vol);
     
     }

     
     private void removeVolumeFromParentJob(Volume0 vol) {
         parentJob.removeVolume(vol);
     }

           
     
    public JobType0Model getParentJob() {
        return parentJob;
    }

    private void extractSubsurfacesInVolume() {
          
    }

     
}
