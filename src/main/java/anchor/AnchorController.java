/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anchor;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AnchorController {
    AnchorPane interactivePane;
    AnchorModel model;
    AnchorNode node;
    final Delta dragDelta=new Delta();
    @FXML
    private Circle anchor;
    /*
    @FXML
    void anchorOnDragDetected(MouseEvent event) {
    Dragboard dragboard=node.startDragAndDrop(TransferMode.MOVE);
    ClipboardContent content=new ClipboardContent();
    
    content.putString(node.getId());
    dragboard.setContent(content);
    event.consume();
    }
    
    @FXML
    void anchorOnDragDropped(DragEvent event) {
    
    }
    
    @FXML
    void anchorOnDragOver(DragEvent event) {
    node.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
    event.consume();
    }*/

    
    void setModel(AnchorModel item) {
        model=item;
        
    }
    
    public AnchorModel getModel(){
        return this.model;
    }

    void setView(AnchorNode nd,AnchorPane interactivePane) {
        node=nd;
        node.setRadius(10.0);
        node.setCenterX(10.0);
        node.setCenterY(10.0);
        node.setFill(Color.AQUAMARINE);
        this.interactivePane=interactivePane;
        /*model.getX().bind(node.layoutXProperty());
        model.getY().bind(node.layoutYProperty());*/
        model.getX().bind(node.centerXProperty());
        model.getY().bind(node.centerYProperty());
        
       node.setOnMousePressed(e->{
        //   System.out.println("anchor.AnchorController.setView() Mouse Pressed");
            dragDelta.x=node.getCenterX()-e.getX();
            dragDelta.y=node.getCenterY()-e.getY();
           e.consume();
        });
        
       node.setOnMouseDragged(e->{
     
     //  System.out.println("anchor.AnchorController.setView() Mouse Dragged");
       double newX=e.getX()+dragDelta.x;
       if(newX>0 && newX<AnchorController.this.interactivePane.getScene().getWidth()){
       node.setCenterX(newX);
       }
       double newY=e.getY()+dragDelta.y;
       if(newY>0 && newY<AnchorController.this.interactivePane.getScene().getHeight()){
       node.setCenterY(newY);
       }
       
       e.consume();
       });
       
        
         node.setOnDragDetected(e->{
      //       System.out.println("anchor.AnchorController.setView() DragDetected");
            node.startFullDrag();
        e.consume();
        });
            
      //   node.setOnDragDone(e->{System.out.println("anchor.AnchorController.setView() DragDone");});
      //   node.setOnDragDropped(e->{System.out.println("anchor.AnchorController.setView() DragDropped");});
        }
    
   
    
    private class Delta{
        double x,y;
    }
    
    
    
}
