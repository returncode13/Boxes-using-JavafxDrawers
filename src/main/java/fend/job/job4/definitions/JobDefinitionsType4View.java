/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job4.definitions;

import com.jfoenix.controls.JFXDrawersStack;
import fend.job.job4.JobType4Model;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobDefinitionsType4View extends JFXDrawersStack {
    private JobType4Model parentBox;
    private  JobDefinitionsType4Model model;
    private JobDefinitionsType4Controller  controller;
   
     private FXMLLoader fXMLLoader;
    private final URL location;
    
    
    public JobDefinitionsType4View(JobDefinitionsType4Model item,JobType4Model parentBox){
        this.model=item;
        this.parentBox=parentBox;
        this.location=getClass().getClassLoader().getResource("fxml/job4/definitions/jobdefinitions.fxml"); 
       
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                controller=(JobDefinitionsType4Controller)fXMLLoader.getController();
                controller.setModel(item,this.parentBox);
                controller.setView(this) ;
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }
    
}
