/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job2.definitions;

import fend.job.job1.JobType1Model;
import com.jfoenix.controls.JFXDrawersStack;
import fend.job.job0.JobType0Model;
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
    private JobType0Model parentBox;
    private  JobDefinitionsModel model;
    private JobDefinitionsController  controller;
   
     private FXMLLoader fXMLLoader;
    private final URL location;
    
    
    public JobDefinitionsView(JobDefinitionsModel item,JobType0Model parentBox){
        this.model=item;
        this.parentBox=parentBox;
        this.location=getClass().getClassLoader().getResource("fxml/job2/definitions/jobdefinitions.fxml"); 
       
          
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
