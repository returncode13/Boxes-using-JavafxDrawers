/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job5.definitions;

import com.jfoenix.controls.JFXDrawersStack;
import fend.job.job5.JobType5Model;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobDefinitionsType5View extends JFXDrawersStack {
    private JobType5Model parentBox;
    private  JobDefinitionsType5Model model;
    private JobDefinitionsType5Controller  controller;
   
     private FXMLLoader fXMLLoader;
    private final URL location;
    
    
    public JobDefinitionsType5View(JobDefinitionsType5Model item,JobType5Model parentBox){
        this.model=item;
        this.parentBox=parentBox;
        this.location=getClass().getClassLoader().getResource("fxml/job5/definitions/jobdefinitions.fxml"); 
       
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                controller=(JobDefinitionsType5Controller)fXMLLoader.getController();
                controller.setModel(item,this.parentBox);
                controller.setView(this) ;
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }
    
}
