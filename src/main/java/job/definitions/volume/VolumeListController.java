/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package job.definitions.volume;

import workspace.WorkspaceModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.File;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Box;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import job.job0.JobType0Model;
import volume.volume0.Volume0;
import volume.volume1.Volume1;


/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class VolumeListController {

    private VolumeListModel model;
    private VolumeListView view;
    private Long type;              //the type of job and volume
    private JobType0Model parentjob;
     @FXML
    private JFXListView<Volume0> volumeListView;
     
    
    @FXML
    private JFXButton addVolume;

    @FXML
    void addNewVolume(ActionEvent event) {
        DirectoryChooser dirc=new DirectoryChooser();
        File f=dirc.showDialog(addVolume.getScene().getWindow());
        if(f==null){
            return;
        }
        if(!f.getName().endsWith(".dugio")){
            System.out.println("box.definitions.volume.VolumeListController.addNewVolume(): The chosen directory doesn't seem to be a dugio volume");
            return;
        }
        //type=1L;  <--for demo
        if(type.equals(JobType0Model.PROCESS_2D)){
            Volume1 volume1=new Volume1(parentjob);
            volume1.setName(f.getName());
            volume1.setVolume(f);
            model.addToVolumeList(volume1);
        }
    }
    
     
     
     
     
    void setModel(VolumeListModel item) {
        model=item;
        parentjob=model.getParentJob();
        type=parentjob.getType();
        
    }

    void setView(VolumeListView view) {
        this.view=view;
       
        volumeListView.setCellFactory(e->new VolumeListCell());
        volumeListView.setItems(model.getObservableListOfVolumes());
    }
    
}
