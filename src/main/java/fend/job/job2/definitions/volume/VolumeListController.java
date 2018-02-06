/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job2.definitions.volume;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import db.model.Job;
import db.model.Volume;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import fend.job.job0.JobType0Model;
import fend.volume.volume0.Volume0;
import fend.volume.volume1.Volume4;
import fend.volume.volume2.Volume2;
import java.util.Set;


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
            System.out.println("fend.job.job1.definitions.volume.VolumeListController.addNewVolume(): The chosen directory doesn't seem to be a dugio volume");
            return;
        }
        
        
        Volume vol=new Volume();
        vol.setNameVolume(f.getName());
        vol.setPathOfVolume(f.getAbsolutePath());
        vol.setVolumeType(type);
        vol.setJob(dbjob);
        volumeService.createVolume(vol);
        
        //type=1L;  <--for demo
        if(type.equals(JobType0Model.PROCESS_2D)){
            Volume4 volume1=new Volume4(parentjob);
            volume1.setId(vol.getId());
            volume1.setName(f.getName());
            volume1.setVolume(f);
           
            model.addToVolumeList(volume1);
        }
        /* if(type.equals(JobType0Model.SEGD_LOAD)){
        Volume2 volume2=new Volume2(parentjob);
        volume2.setId(vol.getId());
        volume2.setName(f.getName());
        volume2.setVolume(f);
        
        model.addToVolumeList(volume2);
        }
        if(type.equals(JobType0Model.ACQUISITION)){
        //TO DO.
        
        
        /*Volume2 volume2=new Volume2(parentjob);
        volume2.setId(vol.getId());
        volume2.setName(f.getName());
        volume2.setVolume(f);
        
        model.addToVolumeList(volume2);*/
   // }
    /*if(type.equals(JobType0Model.TEXT)){
        //TO DO.
        
        
        /*Volume2 volume2=new Volume2(parentjob);
        volume2.setId(vol.getId());
        volume2.setName(f.getName());
        volume2.setVolume(f);
        
        model.addToVolumeList(volume2);*/
   /* }*/
    }
    
     
     
     
     
    void setModel(VolumeListModel item) {
        model=item;
        parentjob=model.getParentJob();
        type=parentjob.getType();
        dbjob=jobService.getJob(parentjob.getId());
        
        
        Set<Volume> dbVolumesInJob=dbjob.getVolumes();
        for(Volume dbVol:dbVolumesInJob){
            Volume0 fevol;
            /*if(type.equals(JobType0Model.PROCESS_2D)){
            fevol=new Volume4(parentjob);
            fevol.setId(dbVol.getId());
            fevol.setName(dbVol.getNameVolume());
            File volumeOnDisk=new File(dbVol.getPathOfVolume());
            fevol.setVolume(volumeOnDisk);
            model.addToVolumeList(fevol);
            }*/
             if(type.equals(JobType0Model.SEGD_LOAD)){
            fevol=new Volume2(parentjob);
            fevol.setId(dbVol.getId());
            fevol.setName(dbVol.getNameVolume());
            File volumeOnDisk=new File(dbVol.getPathOfVolume());
            fevol.setVolume(volumeOnDisk);
            model.addToVolumeList(fevol);
            }
             /*
            if(type.equals(JobType0Model.ACQUISITION)){
            //TO DO
            
            /*fevol=new Volume2(parentjob);
            fevol.setId(dbVol.getId());
            fevol.setName(dbVol.getNameVolume());
            File volumeOnDisk=new File(dbVol.getPathOfVolume());
            fevol.setVolume(volumeOnDisk);
            model.addToVolumeList(fevol);*/
       // }
      //  if(type.equals(JobType0Model.TEXT)){
            //TO DO
            
            /*fevol=new Volume2(parentjob);
            fevol.setId(dbVol.getId());
            fevol.setName(dbVol.getNameVolume());
            File volumeOnDisk=new File(dbVol.getPathOfVolume());
            fevol.setVolume(volumeOnDisk);
            model.addToVolumeList(fevol);*/
     //   }*/
        }
        
    }

    void setView(VolumeListView view) {
        this.view=view;
       
        volumeListView.setCellFactory(e->new VolumeListCell());
        volumeListView.setItems(model.getObservableListOfVolumes());
    }
    
}
