/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edge.parentchildedge;

import boxes.BoxNode;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ParentChildEdgeView extends AnchorPane{
    private  ParentChildEdgeModel model;
    private ParentChildEdgeController  controller;
    private BoxNode parentBoxNode;
    private AnchorPane interactivePane;
     private FXMLLoader fXMLLoader;
    private final URL location;
    private boolean dropReceived;
    
    
    public ParentChildEdgeView(ParentChildEdgeModel item,BoxNode parentNode, AnchorPane interactivePane){
        this.location=getClass().getClassLoader().getResource("fxml/parentchildedge.fxml"); 
       this.parentBoxNode=parentNode;
       this.interactivePane=interactivePane;
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
         
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                controller=fXMLLoader.getController();
             
              
                controller.setModel(item);
                controller.setView(this,parentBoxNode,this.interactivePane);
               
                
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
    
}
