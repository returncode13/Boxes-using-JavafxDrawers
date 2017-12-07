/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.dot.anchor;

import java.io.IOException;
import java.net.URL;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AnchorNode extends Circle{
   private  AnchorModel model;
    private AnchorController  controller;
    private  AnchorPane interactivePane;
    
     private FXMLLoader fXMLLoader;
    private final URL location;
   
    
    
    public AnchorNode(AnchorModel item,AnchorPane interactivePane){
        this.location=getClass().getClassLoader().getResource("fxml/anchor.fxml"); 
       this.interactivePane=interactivePane;
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
           // fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                controller=(AnchorController)fXMLLoader.getController();
             
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
    public void relocateToPoint(Point2D xx){
        Point2D coords=getParent().sceneToLocal(xx);
        
        relocate(
                (int)(coords.getX() -(getBoundsInLocal().getWidth()/2)),
                (int)(coords.getY() -(getBoundsInLocal().getHeight()/2))
                );
        
    }

    public AnchorController getController() {
        return controller;
    }

    
    
    
    

}
