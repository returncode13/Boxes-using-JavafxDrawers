/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job4.definitions.volume;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import db.model.Job;
import db.model.Volume;
import db.services.HeaderService;
import db.services.HeaderServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.LogService;
import db.services.LogServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import db.services.WorkflowService;
import db.services.WorkflowServiceImpl;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import fend.job.job0.JobType0Model;
import fend.job.job4.definitions.volume.listFiles.ListFilesModel;
import fend.job.job4.definitions.volume.listFiles.ListFilesNode;
import fend.volume.volume0.Volume0;
import fend.volume.volume1.Volume1;
import fend.volume.volume2.Volume2;
import fend.volume.volume4.Volume4;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class VolumeListController {

    private VolumeListModel model;
    private VolumeListView view;
    private Long type;              //the type of job and volume
    private JobType0Model parentjob;
    private Job dbjob;
    private VolumeService volumeService=new VolumeServiceImpl();
    private JobService jobService=new JobServiceImpl();
    private ListFilesModel listFilesModel;
    private HeaderService headerService=new HeaderServiceImpl();
    private LogService logService=new LogServiceImpl();
    private WorkflowService workflowService=new WorkflowServiceImpl();
    
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
        
        List<String> filenames=new ArrayList<>();
        File[] fileL=f.listFiles();
                for(int i=0;i<fileL.length;i++){
                    filenames.add(fileL[i].getName());
                }
        
               
                
                
        Volume vol=new Volume();
        vol.setNameVolume(f.getName());
        vol.setPathOfVolume(f.getAbsolutePath());
        vol.setVolumeType(type);
        vol.setJob(dbjob);
        volumeService.createVolume(vol);
        
        
        
        
        
        if(type.equals(JobType0Model.TEXT)){
        Volume4 volume4=new Volume4(parentjob);
        volume4.setId(vol.getId());
        volume4.setName(f.getName());
        volume4.setDbVolume(vol);
        volume4.deleteProperty().addListener(VOLUME_DELETE_LISTENER);
        volume4.setVolume(f);
        
        model.addToVolumeList(volume4);
        
            listFilesModel=new ListFilesModel(filenames,volume4);
            ListFilesNode lfnode=new ListFilesNode(listFilesModel);
        }
        
        
    }
    
     
     
     
     
    void setModel(VolumeListModel item) {
        model=item;
        parentjob=model.getParentJob();
        type=parentjob.getType();
        dbjob=jobService.getJob(parentjob.getId());
        
        
         //Set<Volume> dbVolumesInJob=dbjob.getVolumes();
        Set<Volume> dbVolumesInJob= new HashSet<>(volumeService.getVolumesForJob(dbjob));
        for(Volume dbVol:dbVolumesInJob){
            Volume0 fevol;
            
            
            fevol=new Volume1(parentjob);
            fevol.setId(dbVol.getId());
            fevol.setName(dbVol.getNameVolume());
            fevol.setDbVolume(dbVol);
            fevol.deleteProperty().addListener(VOLUME_DELETE_LISTENER);
            File volumeOnDisk=new File(dbVol.getPathOfVolume());
            fevol.setVolume(volumeOnDisk);
            model.addToVolumeList(fevol);
     
        }
        
    }

    void setView(VolumeListView view) {
        this.view=view;
       
        volumeListView.setCellFactory(e->new VolumeListCell());
        volumeListView.setItems(model.getObservableListOfVolumes());
    }
    
    private ChangeListener<Boolean> VOLUME_DELETE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            
            for(Volume0 vols:model.observableListOfVolumes){
                boolean volIsToBeDeleted=vols.deleteProperty().get();
                if(volIsToBeDeleted){
                    System.out.println(" "+vols.getId()+" : "+vols.getName()+" will be deleted from the irdb database");
                    
                    /**
                     * delete headers
                     * delete logs
                     * delete workflows
                     **/
                    Volume dbVol=vols.getDbVolume();
                    
                    
                    System.out.println("deleting associated logs");
                    logService.deleteLogsFor(dbVol);
                    
                    System.out.println("deleting associated headers");
                    headerService.deleteHeadersFor(dbVol);
                    
                    
                    System.out.println("deleting associated workflows");
                    workflowService.deleteWorkFlowsFor(dbVol);
                    
                    System.out.println("deleting volume  from the irdb database");
                    volumeService.deleteVolume(dbVol.getId());
                    
                    
                    parentjob.removeVolume(vols); 
                }
            }
        }
    };
}
