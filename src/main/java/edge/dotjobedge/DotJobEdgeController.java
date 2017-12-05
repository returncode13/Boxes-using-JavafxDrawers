/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edge.dotjobedge;

import anchor.AnchorModel;
import anchor.AnchorNode;
import dot.DotModel;
import dot.DotView;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;
import job.job0.JobType0Model;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DotJobEdgeController {
    private JobType0Model childJob;
    private DotJobEdgeModel model;
    private DotJobEdgeView node;
    private DotModel dotmodel;
    private DotView dotnode;
    private AnchorModel anchorModel;
    private AnchorNode anchor;
    private AnchorPane interactivePane;
    
    final Delta dragDelta=new Delta(); 
     
     /**
     * 
     * fields used for constraining the cubic curve
     * We do this so that the Dots position lies on the midpoint of the curves start and end x,y coords
     * The alternative is to find the curves points of inflection. and bind the Dots position to it.
     * https://pomax.github.com/bezierinfo/
    */ 
    private final DoubleProperty mControloffX=new SimpleDoubleProperty();
    private final DoubleProperty mControloffY=new SimpleDoubleProperty();
    private final DoubleProperty mControlDirX1=new SimpleDoubleProperty();
    private final DoubleProperty mControlDirY1=new SimpleDoubleProperty();
    private final DoubleProperty mControlDirX2=new SimpleDoubleProperty();
    private final DoubleProperty mControlDirY2=new SimpleDoubleProperty(); 
     
     
     
    @FXML
    private CubicCurve curve;
   
    void setModel(DotJobEdgeModel item) {
        model=item;
        dotmodel=model.getDotModel();
        anchorModel=model.getAnchorModel();
        
        
    }
    
    void setView(DotJobEdgeView nd,DotView commonDot,AnchorPane interactivePane) {
        
        node=nd;
        this.interactivePane=interactivePane;
                
     
        
        anchor=new AnchorNode(anchorModel,this.interactivePane);
//        dotnode=new DotView(dotmodel);
        this.interactivePane=interactivePane;
        dotnode=commonDot;
        
        
        
        
        curve=createStartingCurve();
       
        
        curve.startXProperty().bind(dotnode.centerXProperty());
        curve.startYProperty().bind(dotnode.centerYProperty());
        curve.endXProperty().bind(anchorModel.getX());
        curve.endYProperty().bind(anchorModel.getY());
        //curve.setMouseTransparent(true);
        constraintCurve();
        overrideAnchorBehaviour();
      
        
        node.getChildren().add(0,curve);
        node.getChildren().add(0,anchor);
        //node.getChildren().add(dotnode);
        this.interactivePane.getChildren().add(node);
       
        
    }
    
    private CubicCurve createStartingCurve() {
        //CubicCurve curve = new CubicCurve();
        curve.setStartX(50);
        curve.setStartY(200);
        curve.setControlX1(150);
        curve.setControlY1(300);
        curve.setControlX2(250);
        curve.setControlY2(50);
        curve.setEndX(350);
        curve.setEndY(150);
        curve.setStroke(Color.RED);
        curve.setStrokeWidth(4);
        curve.setStrokeLineCap(StrokeLineCap.ROUND);
       // curve.setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.6));
        return curve;
    }
    
    
       /**
     * We do this to have similar behavior as the parentchildjobedges
     */
    private void constraintCurve() {
        /**Curve Constraint Begin
         */
        
        mControloffX.set(100.0);
        mControloffY.set(50.0);
        
        
        mControlDirX1.bind(new When(
        curve.startXProperty().greaterThan(curve.endXProperty()))
        .then(-1.0).otherwise(1.0)
        );
        
        
        mControlDirX2.bind(new When(
        curve.startXProperty().greaterThan(curve.endXProperty()))
        .then(1.0).otherwise(-1.0)
        );
        
        curve.controlX1Property().bind(
                Bindings.add(
                        curve.startXProperty(), 
                        mControloffX.multiply(mControlDirX1)
                        )
        );
        
        curve.controlX2Property().bind(
                Bindings.add(
                        curve.endXProperty(), 
                        mControloffX.multiply(mControlDirX2)
                        )
        );
        
        curve.controlY1Property().bind(
                Bindings.add(
                        curve.startYProperty(), 
                        mControloffY.multiply(mControlDirY1)
                        )
        );
        
        curve.controlY2Property().bind(
                Bindings.add(
                        curve.endYProperty(), 
                        mControloffY.multiply(mControlDirY2)
                        )
        );
        
        curve.toBack();
        /**Curve Constraint End
         */
        
    }
    
      /**
     * Override the behaviour of the childanchor. 
     * The node needs to go to the top of the parent pane.(the AnchorPane interactivePane).
     * This is inorder to address the z-ordering of the boxes and the anchors.
     * The anchors will always need to be "behind" the boxes for them to be dropped on the boxes.
     * 
     */
    private void overrideAnchorBehaviour() {
        
        anchor.setOnMouseDragged(e->{
           
            node.toBack();              ///overriden statement
            //System.out.println("anchor.DotJobEdgeController.setView() Mouse Dragged");
            double newX=e.getX()+dragDelta.x;
            if(newX>0 && newX<DotJobEdgeController.this.interactivePane.getScene().getWidth()){
                anchor.setCenterX(newX);
            }
        double newY=e.getY()+dragDelta.y;
            if(newY>0 && newY<DotJobEdgeController.this.interactivePane.getScene().getHeight()){
                anchor.setCenterY(newY);
            }
            
           e.consume();
        
        });
        
        
        anchor.setOnMouseReleased(e->{
           if(!node.getDropReceived()){
               node.toFront();
               node.requestFocus();
           }
           
        });
        
    }

    public DotJobEdgeModel getModel() {
        return model;
    }
    
    
    
       private class Delta{
        double x,y;
    }
}
