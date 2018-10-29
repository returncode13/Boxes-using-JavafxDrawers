/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job3.definitions;

import fend.job.job1.JobType1Model;
import com.jfoenix.controls.JFXDrawersStack;
import fend.job.job0.JobType0Model;
import fend.job.job3.JobType3Model;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.StackPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobDefinitionsType3View extends JFXDrawersStack {
    private JobType3Model parentBox;
    private  JobDefinitionsType3Model model;
    private JobDefinitionsType3Controller  controller;
   
     private FXMLLoader fXMLLoader;
    private final URL location;
    
    
    public JobDefinitionsType3View(JobDefinitionsType3Model item,JobType3Model parentBox){
        this.model=item;
        this.parentBox=parentBox;
        this.location=getClass().getClassLoader().getResource("fxml/job3/definitions/jobdefinitions.fxml"); 
       
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                controller=(JobDefinitionsType3Controller)fXMLLoader.getController();
                controller.setModel(item,this.parentBox);
                controller.setView(this) ;
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }
    
}
