/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.definitions;

import fend.job.job1.JobType1Model;
import com.jfoenix.controls.JFXDrawersStack;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.StackPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobDefinitionsView extends JFXDrawersStack {
    private JobType1Model parentBox;
    private  JobDefinitionsModel model;
    private JobDefinitionsController  controller;
   
     private FXMLLoader fXMLLoader;
    private final URL location;
    
    
    public JobDefinitionsView(JobDefinitionsModel item,JobType1Model parentBox){
        this.model=item;
        this.parentBox=parentBox;
        this.location=getClass().getClassLoader().getResource("fxml/jobdefinitions.fxml"); 
       
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                controller=(JobDefinitionsController)fXMLLoader.getController();
                controller.setModel(item,this.parentBox);
                controller.setView(this) ;
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }
    
}
