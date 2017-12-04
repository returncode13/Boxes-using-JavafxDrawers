/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package box.definitions.volume;

import basewindow.BaseWindowModel;
import box.BoxModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import volume.volume0.Volume0;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * class holds a list of volumes
 */
public class VolumeListModel {
    List<Volume0> volumes;
    ObservableList<Volume0> observableListOfVolumes;
    BoxModel parentBox;                     //the job that contains this list
    
    
    public VolumeListModel(BoxModel parentBox) {
        this.parentBox=parentBox;
        volumes=new ArrayList<>();
        observableListOfVolumes=FXCollections.observableArrayList(volumes);
        
        observableListOfVolumes.addListener(new ListChangeListener<Volume0>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends Volume0> c) {
                    while(c.next()){
                        for(Volume0 vol:c.getAddedSubList()){
                            if(BaseWindowModel.DEBUG) System.out.println("box.definitions.volume.VolumeListModel.added(): "+vol.getName());
                            updateParentBox();
                        }
                        
                        for(Volume0 vol:c.getRemoved()){
                            if(BaseWindowModel.DEBUG) System.out.println("box.definitions.volume.VolumeListModel.removed(): "+vol.getName());
                            updateParentBox();
                        }
                    }
            }

           
        });
        
    }

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
        volumes.add(vol);
    }
    
    public void removeFromVolumeList(Volume0 vol){
        volumes.remove(vol);
    }
    
     private void updateParentBox() {
     }
    
}
