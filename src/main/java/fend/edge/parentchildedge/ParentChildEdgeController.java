/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.edge.parentchildedge;

import db.model.Job;
import db.services.JobService;
import db.services.JobServiceImpl;
import fend.dot.DotModel;
import fend.dot.DotView;
import fend.dot.LinkModel;
import fend.dot.anchor.AnchorModel;
import fend.dot.anchor.AnchorView;
import fend.edge.edge.EdgeController;
import fend.edge.edge.arrow.Arrow;

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
import fend.job.job2.JobType2View;
import fend.job.job3.JobType3View;
import fend.job.job4.JobType4View;
import java.util.Set;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

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
    private AnchorView childAnchor;
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
     
    
    private Arrow arrowEnd;
    private Arrow arrowStart;
    private Arrow arrowBeforeDot;
    private Arrow arrowAfterDot;
    
    private DotView dotview;

    void setModel(ParentChildEdgeModel item) {
        model=item;
        childAnchorModel=model.getChildAnchorModel();
        //model.dropSuccessFulProperty().addListener(DROP_SUCCESSFUL_LISTENER);
        
    }

    void setView(ParentChildEdgeView nd, JobType0View parentJob, AnchorPane interactivePane) {
        node=nd;
        this.jobView=parentJob;
        this.interactivePane=interactivePane;
        
        childAnchor=new AnchorView(childAnchorModel, this.interactivePane);
        childAnchor.centerXProperty().addListener(UPDATE_ARROW_LISTENER);
        childAnchor.centerYProperty().addListener(UPDATE_ARROW_LISTENER);
        curve=createStartingCurve();
        
        JobType0Model job=this.jobView.getController().getModel();
        type=job.getType();
        if(type.equals(JobType0Model.PROCESS_2D)) {
                        curve.startXProperty().bind(Bindings.add(((JobType1View)this.jobView).layoutXProperty(),71)); //handcoding is awful!. 142 is the width, 74 the height
                        curve.startYProperty().bind(Bindings.add(((JobType1View)this.jobView).layoutYProperty(),100));
        }
        if(type.equals(JobType0Model.SEGD_LOAD)) {
                        curve.startXProperty().bind(Bindings.add(((JobType2View)this.jobView).layoutXProperty(),71)); //handcoding is awful!. 142 is the width, 74 the height
                        curve.startYProperty().bind(Bindings.add(((JobType2View)this.jobView).layoutYProperty(),100));
        }
        if(type.equals(JobType0Model.ACQUISITION)) {
           
                        curve.startXProperty().bind(Bindings.add(((JobType3View)this.jobView).layoutXProperty(),71)); //handcoding is awful!. 142 is the width, 74 the height
                        curve.startYProperty().bind(Bindings.add(((JobType3View)this.jobView).layoutYProperty(),100));
        }
        if(type.equals(JobType0Model.TEXT)) {
           
                        curve.startXProperty().bind(Bindings.add(((JobType4View)this.jobView).layoutXProperty(),71)); //handcoding is awful!. 142 is the width, 74 the height
                        curve.startYProperty().bind(Bindings.add(((JobType4View)this.jobView).layoutYProperty(),100));
        }
        
        curve.endXProperty().bind(childAnchor.centerXProperty());
        curve.endYProperty().bind(childAnchor.centerYProperty());
        curve.setMouseTransparent(true);
        constraintCurve();
        overrideAnchorBehaviour();
        
        double[] arrowShape=new double[]{0,0,7,13,-7,13};
        arrowEnd=new Arrow(curve, 0.90f, arrowShape);
        arrowBeforeDot=new Arrow(curve,0.40f,arrowShape);
        arrowAfterDot=new Arrow(curve,0.60f,arrowShape);
        arrowStart=new Arrow(curve, 0.20f, arrowShape);
        curve.startXProperty().addListener(UPDATE_ARROW_LISTENER);
        curve.startYProperty().addListener(UPDATE_ARROW_LISTENER);
        
        node.getChildren().add(0,arrowEnd);
        node.getChildren().add(0,arrowBeforeDot);
        node.getChildren().add(0,arrowAfterDot);
        node.getChildren().add(0,arrowStart);
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
        curve.setStroke(Color.BLACK);
        curve.setStrokeWidth(2);
        curve.setStrokeLineCap(StrokeLineCap.ROUND);
        //curve.setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.6));
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
            this.dotview=dotnode;
        node.getChildren().add(this.dotview);
        
       // dotnode.centerXProperty().bind(Bindings.divide((Bindings.add(curve.startXProperty(), curve.endXProperty())),2.0));
       // dotnode.centerYProperty().bind(Bindings.divide((Bindings.add(curve.startYProperty(), curve.endYProperty())),2.0));
       
       this.dotview.centerXProperty().bind(Bindings.divide((Bindings.add(curve.startXProperty(), curve.endXProperty())),2.0));
        this.dotview.centerYProperty().bind(Bindings.divide((Bindings.add(curve.startYProperty(), curve.endYProperty())),2.0));
        
        
        JobType0Model job=childJobView.getController().getModel();
        model.setChildJob(job);
        node.setDropReceived(true);
        type=job.getType();
         if(type.equals(JobType0Model.PROCESS_2D)) {
            System.out.println("fend.edge.parentchildedge.ParentChildEdgeController.setChildJobView(): Ht: " +((JobType1View)childJobView).getHeight()+" , Width: "+((JobType1View)childJobView).getWidth());
       // childAnchor.centerXProperty().bind(Bindings.add(((JobType1View)childJobView).layoutXProperty(),((JobType1View)childJobView).getBoundsInLocal().getMaxX()/2.0));
       // childAnchor.centerYProperty().bind(Bindings.add(((JobType1View)childJobView).layoutYProperty(),((JobType1View)childJobView).getBoundsInLocal().getMaxY()));
       childAnchor.centerXProperty().bind(Bindings.add(((JobType1View)childJobView).layoutXProperty(),71));   //handcoding is awful!. 142 is the width, 74 the height
        childAnchor.centerYProperty().bind(Bindings.add(((JobType1View)childJobView).layoutYProperty(),0));
        childAnchor.setRadius(5);
        }
         if(type.equals(JobType0Model.SEGD_LOAD)) {
             System.out.println("fend.edge.parentchildedge.ParentChildEdgeController.setChildJobView(): Ht: " +((JobType2View)childJobView).getHeight()+" , Width: "+((JobType2View)childJobView).getWidth());
       // childAnchor.centerXProperty().bind(Bindings.add(((JobType1View)childJobView).layoutXProperty(),((JobType1View)childJobView).getBoundsInLocal().getMaxX()/2.0));
       // childAnchor.centerYProperty().bind(Bindings.add(((JobType1View)childJobView).layoutYProperty(),((JobType1View)childJobView).getBoundsInLocal().getMaxY()));
       childAnchor.centerXProperty().bind(Bindings.add(((JobType2View)childJobView).layoutXProperty(),71));   //handcoding is awful!. 142 is the width, 74 the height
        childAnchor.centerYProperty().bind(Bindings.add(((JobType2View)childJobView).layoutYProperty(),0));
        childAnchor.setRadius(5);
        }
         if(type.equals(JobType0Model.ACQUISITION)) {
             System.out.println("fend.edge.parentchildedge.ParentChildEdgeController.setChildJobView(): Ht: " +((JobType3View)childJobView).getHeight()+" , Width: "+((JobType3View)childJobView).getWidth());
       // childAnchor.centerXProperty().bind(Bindings.add(((JobType1View)childJobView).layoutXProperty(),((JobType1View)childJobView).getBoundsInLocal().getMaxX()/2.0));
       // childAnchor.centerYProperty().bind(Bindings.add(((JobType1View)childJobView).layoutYProperty(),((JobType1View)childJobView).getBoundsInLocal().getMaxY()));
       childAnchor.centerXProperty().bind(Bindings.add(((JobType3View)childJobView).layoutXProperty(),71));   //handcoding is awful!. 142 is the width, 74 the height
        childAnchor.centerYProperty().bind(Bindings.add(((JobType3View)childJobView).layoutYProperty(),0));
        childAnchor.setRadius(5);
        }
         if(type.equals(JobType0Model.TEXT)) {
             System.out.println("fend.edge.parentchildedge.ParentChildEdgeController.setChildJobView(): Ht: " +((JobType3View)childJobView).getHeight()+" , Width: "+((JobType3View)childJobView).getWidth());
       // childAnchor.centerXProperty().bind(Bindings.add(((JobType1View)childJobView).layoutXProperty(),((JobType1View)childJobView).getBoundsInLocal().getMaxX()/2.0));
       // childAnchor.centerYProperty().bind(Bindings.add(((JobType1View)childJobView).layoutYProperty(),((JobType1View)childJobView).getBoundsInLocal().getMaxY()));
       childAnchor.centerXProperty().bind(Bindings.add(((JobType4View)childJobView).layoutXProperty(),71));   //handcoding is awful!. 142 is the width, 74 the height
        childAnchor.centerYProperty().bind(Bindings.add(((JobType4View)childJobView).layoutYProperty(),0));
        childAnchor.setRadius(5);
        }
          
          }
        else{                                    //this is when the dot exists. and when it needs to be "joined" to.
            System.out.println("fend.edge.parentchildedge.ParentChildEdgeController.setChildJobView(): binding to dotnode "+dotnode.getId());
            /*childAnchor.centerXProperty().bind(dotnode.centerXProperty());
            childAnchor.centerYProperty().bind(dotnode.centerYProperty());*/
            this.dotview=dotnode;
            childAnchor.centerXProperty().bind(this.dotview.centerXProperty());
            childAnchor.centerYProperty().bind(this.dotview.centerYProperty());
            
        }
        node.toBack();
        this.dotview.toFront();
        //return dotnode;
        return this.dotview;
    };
    
    /**
     * We do this so that the Dots position lies on the midpoint of the curves start and end x,y coords
     * The alternative is to find the curves points of inflection. and bind the Dots position to it.
     * https://pomax.github.com/bezierinfo/
     */
    private void constraintCurve() {
        /**Curve Constraint Begin
         */
        
        mControloffX.set(-50.0);
        mControloffY.set(100.0);
        
        
        /* mControlDirX1.bind(new When(
        curve.startXProperty().greaterThan(curve.endXProperty()))
        .then(-1.0).otherwise(1.0)
        );
        
        
        mControlDirX2.bind(new When(
        curve.startXProperty().greaterThan(curve.endXProperty()))
        .then(1.0).otherwise(-1.0)
        );
        */
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
            
            //update Arrow
            arrowEnd.update();
            
           e.consume();
        
        });
        
        
        childAnchor.setOnMouseReleased(e->{
           if(!node.getDropReceived()){
               node.toFront();
               node.requestFocus();
           }else{
               
               /* System.out.println("fend.edge.parentchildedge.ParentChildEdgeController.setChildJobView(): Y" +node.getBoundsInLocal().getMaxY()+","+node.getBoundsInLocal().getMinY()+" , X: "+node.getBoundsInLocal().getMinX()+","+node.getBoundsInLocal().getMaxX());
               childAnchor.centerXProperty().bind(Bindings.add(node.layoutXProperty(),node.getBoundsInLocal().getMaxX()/2.0));
               childAnchor.centerYProperty().bind(Bindings.add(node.layoutYProperty(),node.getBoundsInLocal().getMinY()));*/
               //reduce the childanchors radius to zero once it's dropped on a node
               
               childAnchor.setRadius(5);
           }
           
        });
        
    }

    public CubicCurve getCurve() {
        return this.curve;
    }
    
      private class Delta{
        double x,y;
    }
      
      
    private ChangeListener<? super Number > UPDATE_ARROW_LISTENER=(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
        //System.out.println("Arrow updating "+oldValue.doubleValue()+" -> "+newValue.doubleValue());
        ParentChildEdgeController.this.arrowEnd.update();
        ParentChildEdgeController.this.arrowBeforeDot.update();
        ParentChildEdgeController.this.arrowAfterDot.update();
        ParentChildEdgeController.this.arrowStart.update();
        if(dotview!=null){
            this.dotview.toFront();
        }
    };
    
    private JobService jobService=new JobServiceImpl();
    
  private ChangeListener<Boolean> DROP_SUCCESSFUL_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                /*Job dbparent=jobService.getJob(model.getParentJob().getId());
                model.getParentJob().setDatabaseJob(dbparent);
                Job dbchild=jobService.getJob(model.getChildJob().getId());
                model.getChildJob().setDatabaseJob(dbchild);*/
                /*DotModel dotmodel=model.getDotModel();
                Set<LinkModel> links=dotmodel.getLinks();
                for(LinkModel link:links){
                JobType0Model parentj=link.getParent();
                JobType0Model childj=link.getChild();
                //Job dbChild=jobService.getJob(childj.getId());
                Job dbChild=childj.getDatabaseJob();
                //Job dbParent=jobService.getJob(parentj.getId());
                Job dbParent=parentj.getDatabaseJob();
                }*/
                System.out.println(".changed(): updating the parent");
                model.getParentJob().toggleUpdateProperty();
                model.setDropSuccessFul(false);
            }
            
        }
    };
      
}
