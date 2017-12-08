/*/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fend.job.job1;


import fend.dot.anchor.AnchorNode;
import fend.job.definitions.JobDefinitionsModel;
import fend.job.definitions.JobDefinitionsView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXTextField;
import fend.dot.DotModel;
import fend.dot.DotView;
import fend.edge.dotjobedge.DotJobEdgeModel;
import fend.edge.dotjobedge.DotJobEdgeView;
import fend.edge.parentchildedge.ParentChildEdgeModel;
import fend.edge.parentchildedge.ParentChildEdgeView;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.util.Duration;
import fend.job.job0.JobType0Controller;
import fend.job.job0.JobType0Model;
import fend.volume.volume0.Volume0;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobType1Controller implements JobType0Controller{
    final String expand=">";
    final String collapse="<";
    private AnchorPane interactivePane;
    JobType1Model model;
    JobType1View node;
    JFXDrawer drawer=new JFXDrawer();
    private final ContextMenu menu=new ContextMenu();
    
    
    private BooleanProperty checkForHeaders;
    
    
    
    @FXML
    private JFXTextField name;
    
    
    @FXML
    private JFXDrawersStack drawersStack;
    
    @FXML
    private JFXButton q;

    @FXML
    private JFXButton t;

    @FXML
    private JFXButton headerButton;

    
    @FXML
    private JFXButton openDrawer;

    void setModel(JobType1Model item) {
        model=item;
        //checkForHeaders=new SimpleBooleanProperty(false);
        //checkForHeaders.addListener(headerExtractionListener);
        model.getHeadersCommited().addListener(headerExtractionListener);
        
        
    }

    void setView(JobType1View vw,AnchorPane interactivePane) {
        node=vw;
        this.interactivePane=interactivePane;
        drawer.setId("LEFT");
        JobDefinitionsModel bdmodel=new JobDefinitionsModel();
        JobDefinitionsView bdview=new JobDefinitionsView(bdmodel,this.model);
        drawer.setSidePane(bdview);
        drawer.setDirection(JFXDrawer.DrawerDirection.LEFT);
        drawer.setDefaultDrawerSize(bdview.computeAreaInScreen());
        drawer.setOverLayVisible(false);
        drawer.setResizableOnDrag(true);
        drawer.setTranslateX(150);
        drawer.setTranslateY(0);
        //name.setText(model.getId()%1000+"");
        name.textProperty().addListener(nameChangeListener);
        openDrawer.setOnMousePressed(e->{
         drawersStack.toggle(drawer);
        });
        
        drawer.setOnDrawerOpened(e->{
        //openDrawer.setText(this.collapse);
        });
        
        drawer.setOnDrawerOpening(e->{
            drawer.setVisible(true);
            openDrawer.setText(this.collapse);
            FadeTransition ft=new FadeTransition(Duration.millis(500),drawer);
            ft.setFromValue(0.7);
            ft.setToValue(1.0);
            ft.setAutoReverse(true);
            ft.setCycleCount(1);
            ft.play();
        });
        
        drawer.setOnDrawerClosed(e->{
            drawer.setVisible(false);
            openDrawer.setText(this.expand);
            model.toggleChange();
            
        });
        drawer.setOnDrawerClosing(e->{
            FadeTransition ft=new FadeTransition(Duration.millis(200),drawer);
            ft.setFromValue(1.0);
            ft.setToValue(0.7);
            ft.setAutoReverse(true);
            ft.setCycleCount(1);
            ft.play();
             
        });
        
         node.setOnMouseDragged(event->{
            node.relocateToPoint(new Point2D(event.getSceneX(),event.getSceneY()));
         });
         
         node.setOnMouseDragReleased(e->{
             
             
             System.out.println("job.job1.JobType1Controller.setView(): MouseDrag Released");
              AnchorNode droppedAnchor=(AnchorNode) e.getGestureSource();
             
              if(droppedAnchor.getParent() instanceof ParentChildEdgeView){
                  System.out.println("job.job1.JobType1Controller.setView(): Instance of ParentChildEdgeView");
                    ParentChildEdgeView parentChildEdgeNode=((ParentChildEdgeView)droppedAnchor.getParent());
                    ParentChildEdgeModel parentChildEdgeModel=parentChildEdgeNode.getController().getModel();
                    
////                    System.out.println("job.job1.JobType1Controller.setView(): parentLinkModel: "+parentChildEdgeModel.getParentJob().getId()%100);
                    

                    JobType0Model parent=parentChildEdgeModel.getParentJob();
                    /**
                     * add the link to the box only if this box is not an existing child to the parent
                     */
                    if(parent.getChildren().contains(model)){
                        for (Iterator<JobType0Model> iterator = parent.getChildren().iterator(); iterator.hasNext();) {
                            JobType0Model next = iterator.next();
////                            System.out.println("job.job1.JobType1Controller.setView(): child in "+parent.getId()%100+" : "+next.getId()%100);
                            
                        }
//                        System.out.println("boxes.BoxController.setView(): contains "+model.getId()%100);
                         parentChildEdgeNode.setDropReceived(false);
                         return;
                    }
                    if(parent.equals(model))
                    {
                        System.out.println("boxes.BoxController.setView(): cyclic");
                        return;
                    }
//                    System.out.println("job.job1.JobType1Controller.setView(): adding: "+parent.getId());
                    model.addParent(parent);
                    parent.addChild(model);
                    
                    DotModel dotmodel=parentChildEdgeModel.getDotModel();
                    DotView dotnode=new DotView(dotmodel, JobType1Controller.this.interactivePane);
                    parentChildEdgeNode.getChildren().add(dotnode);
                    

                    CubicCurve curve=parentChildEdgeNode.getController().getCurve();  //the curve in the node
                    dotnode.centerXProperty().bind(Bindings.divide((Bindings.add(curve.startXProperty(), curve.endXProperty())),2.0));
                    dotnode.centerYProperty().bind(Bindings.divide((Bindings.add(curve.startYProperty(), curve.endYProperty())),2.0));

                    
                    parentChildEdgeModel.setChildJob(model);
                    parentChildEdgeNode.setDropReceived(true);
                    droppedAnchor.centerXProperty().bind(node.layoutXProperty());
                    droppedAnchor.centerYProperty().bind(node.layoutYProperty());
                
                }
              
              
              
              
              if(droppedAnchor.getParent() instanceof DotJobEdgeView){
                    DotJobEdgeView parentNode=(DotJobEdgeView) droppedAnchor.getParent();
                    DotJobEdgeModel parentModel=parentNode.getController().getModel();
                    
                    Set<JobType0Model> parents=parentModel.getDotModel().getParents();          //since the drop happens from a Dot to a Box , the Box is a child of the Dots parents
                    for(JobType0Model parent:parents){
                        
                         if(parent.getChildren().contains(model)){
////                            System.out.println("boxes.BoxController.setView(): "+model.getId()%100+" already exists as a child to parent "+parent.getId()%100);
                             parentNode.setDropReceived(false);
                             return;
                        }
                         if(parent.equals(model))
                        {
                            System.out.println("boxes.BoxController.setView(): cyclic");
                            return;
                        }
                        
                        model.addParent(parent);
                        parent.addChild(model);
                    }
                    
                    parentNode.setDropReceived(true);
                    parentModel.setChildJob(model);
                    droppedAnchor.centerXProperty().bind(node.layoutXProperty());
                    droppedAnchor.centerYProperty().bind(node.layoutYProperty());
                }
             
         });
         
         
         
         
         setupMenu();
         
         name.setOnKeyPressed(e->{
         if(e.KEY_PRESSED.equals(KeyCode.ENTER)){
         String text=name.getText();
        // model.setNameproperty(text);
         }
         if(e.KEY_PRESSED.equals(KeyCode.TAB)){
         String text=name.getText();
        // model.setNameproperty(text);
         }
         });
         
         name.setOnKeyReleased(e->{
         
         });
    }
    
    
    /***
     * Extract headers for the current job
     
     */
    
     @FXML
    void extractHeadersForJob(ActionEvent event) {
            t.setDisable(true);
            model.extractLogs();
            
    }
    
    @FXML
    void checkMultiples(ActionEvent event) {
        model.checkMultiples();
    }
    
    @Override
    public JobType0Model getModel() {
        return this.model;
    }
    /**
     * Listeners
     */
    final private ChangeListener<String> nameChangeListener=new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            System.out.println(".changed(): from "+oldValue+" to "+newValue);
            model.setNameproperty(newValue);
        }
    };
    
    
    
    final private ChangeListener<Boolean> headerExtractionListener=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                t.setDisable(false);
            }
        }
    };
    
    /***
     * private Implementation
     */

    private void setupMenu() {
         /**
         * Setup linking
         */
        MenuItem addAChildJob=new MenuItem("+start a link");
        MenuItem deleteThisJob=new MenuItem("-delete this job node");
        node.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>(){
            @Override
            public void handle(ContextMenuEvent event) {
                menu.show(node, event.getScreenX(), event.getScreenY());
            }
            
        });
        addAChildJob.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            //    System.out.println(".handle(): Add a jobdotEdge here");
           
                ParentChildEdgeModel pcdm=new ParentChildEdgeModel();
                pcdm.setParentJob(model);
                ParentChildEdgeView pcdn=new ParentChildEdgeView(pcdm, node, JobType1Controller.this.interactivePane);
                
                
                
                
            }
        });
        
        menu.getItems().addAll(addAChildJob,deleteThisJob);
    }
    
    
    
}