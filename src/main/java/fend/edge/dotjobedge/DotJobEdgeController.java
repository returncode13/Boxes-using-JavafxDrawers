/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.edge.dotjobedge;

import fend.dot.anchor.AnchorModel;
import fend.dot.anchor.AnchorView;
import fend.dot.DotModel;
import fend.dot.DotView;
import fend.edge.edge.EdgeController;
import fend.edge.edge.arrow.Arrow;
import fend.edge.parentchildedge.ParentChildEdgeController;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;
import fend.job.job0.JobType0Model;
import fend.job.job0.JobType0View;
import fend.job.job1.JobType1View;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DotJobEdgeController implements EdgeController {
    private JobType0Model childJob;
    private DotJobEdgeModel model;
    private DotJobEdgeView node;
    private DotModel dotmodel;
    private DotView dotnode;
    private AnchorModel anchorModel;
    private AnchorView anchor;
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
   
    private Arrow arrowEnd;
    private Arrow arrowStart;
    
    void setModel(DotJobEdgeModel item) {
        model=item;
        dotmodel=model.getDotModel();
        anchorModel=model.getAnchorModel();
        
        
    }
    
    void setView(DotJobEdgeView nd,DotView commonDot,AnchorPane interactivePane) {
        
        node=nd;
        this.interactivePane=interactivePane;
                
     
        
        anchor=new AnchorView(anchorModel,this.interactivePane);
//        dotnode=new DotView(dotmodel);
        anchor.centerXProperty().addListener(UPDATE_ARROW_LISTENER);
        anchor.centerYProperty().addListener(UPDATE_ARROW_LISTENER);
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
      
        double[] arrowShape=new double[]{0,0,7,13,-7,13};
        arrowEnd=new Arrow(curve, 0.90f, arrowShape);
        arrowStart=new Arrow(curve, 0.20f, arrowShape);
        
        curve.startXProperty().addListener(UPDATE_ARROW_LISTENER);
        curve.startYProperty().addListener(UPDATE_ARROW_LISTENER);
        
        node.getChildren().add(0,arrowStart);
        node.getChildren().add(0,arrowEnd);
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
        curve.setStroke(Color.BLACK);
        curve.setStrokeWidth(2);
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
        
        
        /*mControlDirX1.bind(new When(
        curve.startXProperty().greaterThan(curve.endXProperty()))
        .then(-1.0).otherwise(1.0)
        );
        
        
        mControlDirX2.bind(new When(
        curve.startXProperty().greaterThan(curve.endXProperty()))
        .then(1.0).otherwise(-1.0)
        );*/
        
         mControlDirY1.bind(new When(
        curve.startYProperty().greaterThan(curve.endYProperty()))
        .then(-1.0).otherwise(1.0)
        );
        
         mControlDirY2.bind(new When(
        curve.startYProperty().greaterThan(curve.endYProperty()))
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
            
            
            //update arrow
            arrowEnd.update();
           e.consume();
        
        });
        
        
        anchor.setOnMouseReleased(e->{
           if(!node.getDropReceived()){
               node.toFront();
               node.requestFocus();
           }else{
               //reduce the anchors radius to zero once it's dropped on a node
               anchor.setRadius(0);
           }
           
        });
        
    }
    
     public void setChildJobView(JobType0View childJobView){
        
         JobType0Model job=childJobView.getController().getModel();
        Long type=job.getType();
         if(type.equals(JobType0Model.PROCESS_2D)) {
             /*anchor.centerXProperty().bind(((JobType1View)childJobView).layoutXProperty());
             anchor.centerYProperty().bind(((JobType1View)childJobView).layoutYProperty());*/
             /* anchor.centerXProperty().bind(Bindings.add(((JobType1View)childJobView).layoutXProperty(),((JobType1View)childJobView).getBoundsInLocal().getMaxX()/2.0));
             anchor.centerYProperty().bind(Bindings.add(((JobType1View)childJobView).layoutYProperty(),((JobType1View)childJobView).getBoundsInLocal().getMinY()));*/
              anchor.centerXProperty().bind(Bindings.add(((JobType1View)childJobView).layoutXProperty(),71)); //handcoding is awful!. 142 is the width, 74 the height
            anchor.centerYProperty().bind(Bindings.add(((JobType1View)childJobView).layoutYProperty(),0)); 
            anchor.setRadius(5);
        }
         this.node.toBack();
         
    };

    public DotJobEdgeModel getModel() {
        return model;
    }
    
    
    
       private class Delta{
        double x,y;
    }
       
        private ChangeListener<? super Number > UPDATE_ARROW_LISTENER=(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
        DotJobEdgeController.this.arrowEnd.update();
        DotJobEdgeController.this.arrowStart.update();
    };
}
