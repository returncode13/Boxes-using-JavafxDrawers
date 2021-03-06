/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.edge.parentchildedge;


import fend.dot.DotView;
import fend.edge.edge.EdgeView;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import fend.job.job0.JobType0Model;
import fend.job.job0.JobType0View;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ParentChildEdgeView extends AnchorPane implements EdgeView{
    private  ParentChildEdgeModel model;
    private ParentChildEdgeController  controller;
    private JobType0View parentJob;
    private AnchorPane interactivePane;
     private FXMLLoader fXMLLoader;
    private final URL location;
    private boolean dropReceived;
    
    
    public ParentChildEdgeView(ParentChildEdgeModel item,JobType0View parentJob, AnchorPane interactivePane){
        this.location=getClass().getClassLoader().getResource("fxml/parentchildedge_1.fxml"); 
       this.parentJob=parentJob;
       this.interactivePane=interactivePane;
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
         
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                controller=fXMLLoader.getController();
             
              
                controller.setModel(item);
                controller.setView(this,parentJob,this.interactivePane);
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }
    
    
     /*
    for drag purposes of the node
    */
    public void relocateToPoint(Point2D xx){
        Point2D coords=getParent().sceneToLocal(xx);
        
        relocate(
                (int)(coords.getX() -(getBoundsInLocal().getWidth()/2)),
                (int)(coords.getY() -(getBoundsInLocal().getHeight()/2))
                );
        
    }

    public ParentChildEdgeController getController() {
        return controller;
    }

    public void setDropReceived(boolean b) {
        this.dropReceived = b;
    }
    
    public boolean getDropReceived(){
        return this.dropReceived;
    }
    
    public void add(DotView dot){
        controller.add(dot);
    }
    
}
