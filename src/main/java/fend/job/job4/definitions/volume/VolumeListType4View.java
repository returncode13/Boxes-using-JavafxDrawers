/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job4.definitions.volume;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.StackPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class VolumeListType4View extends StackPane{
    private  VolumeListType4Model model;
    private VolumeListType4Controller  controller;
    
     private FXMLLoader fXMLLoader;
    private final URL location;
    
    
    public VolumeListType4View(VolumeListType4Model item){
       
        this.location=getClass().getClassLoader().getResource("fxml/job4/definitions/volume/volumeList.fxml"); 
       
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                controller=(VolumeListType4Controller)fXMLLoader.getController();
                controller.setModel(item);
                controller.setView(this) ;
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }
}
