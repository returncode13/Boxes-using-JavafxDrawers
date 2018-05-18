/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job4;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import fend.job.job0.JobType0Controller;
import fend.job.job0.JobType0View;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobType4View extends AnchorPane implements JobType0View{
    private  JobType4Model model;
    private JobType4Controller  controller;
    private AnchorPane interactivePane;
     private FXMLLoader fXMLLoader;
    private final URL location;
    
    
    public JobType4View(JobType4Model item,AnchorPane interactivePane){
        this.interactivePane=interactivePane;
        this.location=getClass().getClassLoader().getResource("fxml/job/front/job4_1.fxml"); 
       
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                controller=(JobType4Controller)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
                 controller.setModel(item);
                controller.setView(this,this.interactivePane) ;
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }
    
    
    /*
    for drag purposes of the node
    */
    @Override
    public void relocateToPoint(Point2D xx){
        Point2D coords=getParent().sceneToLocal(xx);
        
        relocate(
                (int)(coords.getX() -(getBoundsInLocal().getWidth()/2)),
                (int)(coords.getY() -(getBoundsInLocal().getHeight()/2))
                );
        
    }

    @Override
    public JobType0Controller getController() {
        return controller;
    }
}
