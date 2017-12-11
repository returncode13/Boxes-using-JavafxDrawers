/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.edge.parentchildedge;

import fend.dot.DotModel;
import fend.dot.DotView;
import fend.dot.anchor.AnchorModel;
import fend.dot.anchor.AnchorNode;
import fend.edge.edge.EdgeController;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import javafx.fxml.FXML;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;
import fend.job.job0.JobType0Model;
import fend.job.job0.JobType0View;
import fend.job.job1.JobType1Controller;
import fend.job.job1.JobType1View;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ParentChildEdgeController implements EdgeController{
    private AnchorPane interactivePane;
    private JobType0View jobView;
    private ParentChildEdgeModel model;
    private ParentChildEdgeView node;
    
    private AnchorModel childAnchorModel;
    private AnchorNode childAnchor;
    private Long type;
    
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

    

    void setModel(ParentChildEdgeModel item) {
        model=item;
        childAnchorModel=model.getChildAnchorModel();
        
    }

    void setView(ParentChildEdgeView nd, JobType0View parentJob, AnchorPane interactivePane) {
        node=nd;
        this.jobView=parentJob;
        this.interactivePane=interactivePane;
        
        childAnchor=new AnchorNode(childAnchorModel, this.interactivePane);
        
        curve=createStartingCurve();
        JobType0Model job=this.jobView.getController().getModel();
        type=job.getType();
        if(type.equals(1L)) {
        curve.startXProperty().bind(((JobType1View)this.jobView).layoutXProperty());
        curve.startYProperty().bind(((JobType1View)this.jobView).layoutYProperty());
        }
        curve.endXProperty().bind(childAnchor.centerXProperty());
        curve.endYProperty().bind(childAnchor.centerYProperty());
        curve.setMouseTransparent(true);
        constraintCurve();
        overrideAnchorBehaviour();
        
        
        
        node.getChildren().add(0,curve);
        node.getChildren().add(0,childAnchor);
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
        curve.setStroke(Color.FORESTGREEN);
        curve.setStrokeWidth(4);
        curve.setStrokeLineCap(StrokeLineCap.ROUND);
        curve.setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.6));
        return curve;
    }

    public ParentChildEdgeModel getModel() {
        return this.model;
    }
    
    
    public DotView setChildJobView(JobType0View childJobView,DotView dotnode){
       
        if(dotnode==null){              //i.e. create a new dot in the center of the parent and the child
            
       
        
        DotModel dotmodel=model.getDotModel();
        dotnode=new DotView(dotmodel, ParentChildEdgeController.this.interactivePane);
            System.out.println("fend.edge.parentchildedge.ParentChildEdgeController.setChildJobView(): created a new dotnode: "+dotnode.getId());
        node.getChildren().add(dotnode);
        
        dotnode.centerXProperty().bind(Bindings.divide((Bindings.add(curve.startXProperty(), curve.endXProperty())),2.0));
        dotnode.centerYProperty().bind(Bindings.divide((Bindings.add(curve.startYProperty(), curve.endYProperty())),2.0));
        
        
        JobType0Model job=childJobView.getController().getModel();
        model.setChildJob(job);
        node.setDropReceived(true);
        type=job.getType();
         if(type.equals(JobType0Model.PROCESS_2D)) {
             /*curve.endXProperty().bind(((JobType1View)childJobView).layoutXProperty());
             curve.endYProperty().bind(((JobType1View)childJobView).layoutYProperty());*/
        childAnchor.centerXProperty().bind(((JobType1View)childJobView).layoutXProperty());
        childAnchor.centerYProperty().bind(((JobType1View)childJobView).layoutYProperty());
       
        }
          
          }
        else{                                    //this is when the dot exists. and when it needs to be "joined" to.
            System.out.println("fend.edge.parentchildedge.ParentChildEdgeController.setChildJobView(): binding to dotnode "+dotnode.getId());
            childAnchor.centerXProperty().bind(dotnode.centerXProperty());
            childAnchor.centerYProperty().bind(dotnode.centerYProperty());
            
        }
        node.toBack();
        return dotnode;
    };
    
    /**
     * We do this so that the Dots position lies on the midpoint of the curves start and end x,y coords
     * The alternative is to find the curves points of inflection. and bind the Dots position to it.
     * https://pomax.github.com/bezierinfo/
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
        
        childAnchor.setOnMouseDragged(e->{
           
            node.toBack();              ///overriden statement
            //System.out.println("anchor.ParentChildEdgeController.setView() Mouse Dragged");
            double newX=e.getX()+dragDelta.x;
            if(newX>0 && newX<ParentChildEdgeController.this.interactivePane.getScene().getWidth()){
                childAnchor.setCenterX(newX);
            }
        double newY=e.getY()+dragDelta.y;
            if(newY>0 && newY<ParentChildEdgeController.this.interactivePane.getScene().getHeight()){
                childAnchor.setCenterY(newY);
            }
            
           e.consume();
        
        });
        
        
        childAnchor.setOnMouseReleased(e->{
           if(!node.getDropReceived()){
               node.toFront();
               node.requestFocus();
           }
           
        });
        
    }

    public CubicCurve getCurve() {
        return this.curve;
    }
    
      private class Delta{
        double x,y;
    }
}
