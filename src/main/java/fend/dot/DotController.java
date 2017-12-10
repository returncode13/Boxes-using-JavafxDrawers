/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.dot;

import fend.dot.anchor.AnchorNode;
import fend.job.job0.JobType0Model;
import fend.dot.formulaField.FormulaFieldModel;
import fend.dot.formulaField.FormulaFieldNode;
import fend.edge.dotjobedge.DotJobEdgeModel;
import fend.edge.dotjobedge.DotJobEdgeView;
import fend.edge.parentchildedge.ParentChildEdgeModel;
import fend.edge.parentchildedge.ParentChildEdgeView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DotController extends Stage{
    private DotModel model;
    private DotView node;
    private AnchorPane interactivePane;
    
    final Delta dragDelta=new Delta();
     private final ContextMenu menu=new ContextMenu();
    
    
    @FXML
    private Circle dot;
    
     
    void setModel(DotModel mod){
        model=mod;
        model.getStatus().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               updateColor();
            }

            
        });
        
        model.getDelete().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    deleteNodeAndLinks();
                }
            }

           
        });
        
    }

    void setView(DotView nd,AnchorPane interactivePane) {
        this.interactivePane=interactivePane;
        node=nd;
        node.setRadius(10);
        node.setCenterX(10.0);
        node.setCenterY(10.0);
        
        model.getX().bind(node.centerXProperty());
        model.getY().bind(node.centerYProperty());
        
         MenuItem addAChildJob=new MenuItem("+link to a job");
        MenuItem deleteThisJob=new MenuItem("-delete this dot");
        node.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>(){
            @Override
            public void handle(ContextMenuEvent event) {
                 menu.show(node, event.getScreenX(), event.getScreenY());
            }
        });
        addAChildJob.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(model.enableFurtherLinks() && (model.isNjs()||model.isSplit())){
                    DotJobEdgeModel djm=new DotJobEdgeModel();
                djm.setDotModel(model);
                DotJobEdgeView djn=new DotJobEdgeView(djm,node,DotController.this.interactivePane);
                }else{
                    System.out.println(".handle(): Split Operation disallowed ");
                }
                
            }
        });
        deleteThisJob.setOnAction(e->{
            model.setDelete(true);
        });
        menu.getItems().addAll(addAChildJob,deleteThisJob);
     
        /**
         * Allow a join only if join==true
         * 
         */
        node.setOnMouseDragReleased(e->{
           AnchorNode droppedAnchor=(AnchorNode) e.getGestureSource();
            if(droppedAnchor.getParent() instanceof ParentChildEdgeView){
            System.out.println("dot.DotController.setView() NJS: "+model.isNjs()+" Join: "+model.isJoin()+" Split: "+model.isSplit()+" Enabled: "+model.enableFurtherLinks());
            if(model.enableFurtherLinks() && (model.isNjs()||model.isJoin())){
               
               
               
                
                if(droppedAnchor.getParent() instanceof ParentChildEdgeView){
                    ParentChildEdgeView parentNode=((ParentChildEdgeView)droppedAnchor.getParent());
                    
                    ParentChildEdgeModel parentModel=parentNode.getController().getModel();
                    
                    JobType0Model childFromDot=(new ArrayList<>(model.getLinks())).get(0).getChild();    //the ONLY child associated with the dot model
                    parentModel.setChildJob(childFromDot);
                    
                    JobType0Model parentConnectingToDot=parentModel.getParentJob();   //get the parent job connecting to this Dot
                    //model.addToParents(parentConnectingToDot);                      //add it to the Dots list of parents. (which will now be >1)
                    
                    parentConnectingToDot.addChild(childFromDot);
                    childFromDot.addParent(parentConnectingToDot);
                   // model.addToParents(parentConnectingToDot);
                    model.createLink(parentConnectingToDot,childFromDot);
                    parentModel.setDotModel(model);                      //Share this dot. 
                    parentNode.setDropReceived(true);
                    droppedAnchor.centerXProperty().bind(node.centerXProperty());
                    droppedAnchor.centerYProperty().bind(node.centerYProperty());
                }
            }else{
                System.out.println("dot.DotController.setView(): Join Operation disallowed");
            }
            
            
        }
        });
        
        
        
        node.setOnMouseClicked(e->{
        if(e.getButton().equals(MouseButton.PRIMARY)){
            if(e.getClickCount()==2){
                FormulaFieldModel formulaModel=new FormulaFieldModel();
                FormulaFieldNode formulaFieldNode=new FormulaFieldNode(formulaModel);
            }
        }
        });
    }

    
    private void updateColor() {
            if(model.getStatus().get().equals(DotModel.NJS))node.setFill(Color.DIMGRAY);
            if(model.getStatus().get().equals(DotModel.JOIN))node.setFill(Color.DARKORCHID);
            if(model.getStatus().get().equals(DotModel.SPLIT))node.setFill(Color.NAVY);
    }
    
     private void deleteNodeAndLinks() {
         /*   Set<JobType0Model> parents=model.getParents();
         Set<JobType0Model> children=model.getChildren();*/
            
    }
            
    
    
    private class Delta{
        double x,y;
    }
    
}
