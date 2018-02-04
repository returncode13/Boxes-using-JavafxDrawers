/*/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fend.job.job2;


import fend.dot.anchor.AnchorView;
import fend.job.definitions.JobDefinitionsModel;
import fend.job.definitions.JobDefinitionsView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXTextField;
import db.model.Job;
import db.services.JobService;
import db.services.JobServiceImpl;
import fend.dot.DotModel;
import fend.dot.DotView;
import fend.dot.LinkModel;
import fend.edge.dotjobedge.DotJobEdgeModel;
import fend.edge.dotjobedge.DotJobEdgeView;
import fend.edge.parentchildedge.ParentChildEdgeModel;
import fend.edge.parentchildedge.ParentChildEdgeView;
import java.util.Iterator;
import java.util.Set;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurve;
import javafx.util.Duration;
import fend.job.job0.JobType0Controller;
import fend.job.job0.JobType0Model;
import fend.job.table.lineTable.LineTableModel;
import fend.job.table.lineTable.LineTableView;
import fend.job.table.qctable.QcTableModel;
import fend.job.table.qctable.QcTableView;
import java.util.HashSet;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobType2Controller implements JobType0Controller{
    final String expand=">";
    final String collapse="<";
    private AnchorPane interactivePane;
    private JobType2Model model;
    private JobType2View node;
    private Job dbjob;
    private JobService jobService=new JobServiceImpl();
    JFXDrawer drawer=new JFXDrawer();
    private final ContextMenu menu=new ContextMenu();
    
    
    private BooleanProperty checkForHeaders;
    
    
    
    @FXML
    private JFXTextField name;
    
    
    @FXML
    private JFXDrawersStack drawersStack;
    
     @FXML
    private JFXButton qctable;

    @FXML
    private JFXButton showTable;

    @FXML
    private JFXButton headerButton;

    
    @FXML
    private JFXButton openDrawer;

    void setModel(JobType2Model item) {
        model=item;
        dbjob=jobService.getJob(model.getId());
//checkForHeaders=new SimpleBooleanProperty(false);
        //checkForHeaders.addListener(headerExtractionListener);
        model.getHeadersCommited().addListener(headerExtractionListener);
        model.getListenToDepthChangeProperty().addListener(listenToDepthChange);
      //  model.getDepth().addListener(depthChangeListener);
        
    }

    void setView(JobType2View vw,AnchorPane interactivePane) {
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
        name.setText(model.getNameproperty().get());
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
             
             
             System.out.println("job.job2.JobType2Controller.setView(): MouseDrag Released");
              AnchorView droppedAnchor=(AnchorView) e.getGestureSource();
             
              if(droppedAnchor.getParent() instanceof ParentChildEdgeView){
                  System.out.println("job.job2.JobType2Controller.setView(): Instance of ParentChildEdgeView");
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
/*model.addParent(parent);
parent.addChild(model);*/
                    
                    DotModel dotmodel=parentChildEdgeModel.getDotModel();
                    /*dotmodel.addToParents(parent);
                    dotmodel.addToChildren(model);*/
                    DotView dotnode=new DotView(dotmodel, JobType2Controller.this.interactivePane);
                    dotmodel.createLink(parent, model); // create a link between parent and child .add child to parent's list of children and parent to child's list of parents
                    
                    Long parentDepth=parent.getDepth().get();
                    if(model.getDepth().get()<(parentDepth+1)){
                        model.setListenToDepthChange(true);
                        model.setDepth(parentDepth+1);
                    }
                    parentChildEdgeNode.getChildren().add(0,dotnode);
                   

                    CubicCurve curve=parentChildEdgeNode.getController().getCurve();  //the curve in the node
                    dotnode.centerXProperty().bind(Bindings.divide((Bindings.add(curve.startXProperty(), curve.endXProperty())),2.0));
                    dotnode.centerYProperty().bind(Bindings.divide((Bindings.add(curve.startYProperty(), curve.endYProperty())),2.0));

                    
                    parentChildEdgeModel.setChildJob(model);
                    parentChildEdgeNode.setDropReceived(true);
                    /* droppedAnchor.centerXProperty().bind(node.layoutXProperty());
                    droppedAnchor.centerYProperty().bind(node.layoutYProperty());*/
                    droppedAnchor.centerXProperty().bind(Bindings.add(node.layoutXProperty(),node.getBoundsInLocal().getMaxX()/2.0));
                    droppedAnchor.centerYProperty().bind(Bindings.add(node.layoutYProperty(),node.getBoundsInLocal().getMinY()));
                
                }
              
              
              
              
              if(droppedAnchor.getParent() instanceof DotJobEdgeView){         //split
                    DotJobEdgeView parentNode=(DotJobEdgeView) droppedAnchor.getParent();
                    DotJobEdgeModel parentModel=parentNode.getController().getModel();
                    
                    Set<LinkModel> linksFromDot=parentModel.getDotModel().getLinks();
                    
                    /*Set<JobType0Model> parents=parentModel.getDotModel().getParents();          //since the drop happens from a Dot to a Box , the Box is a child of the Dots parents*/
                    Set<JobType0Model> parents=new HashSet<>();
                    
                    for(LinkModel link:linksFromDot){
                        parents.add(link.getParent());
                    }
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
                        
                         Long parentDepth=parent.getDepth().get();
                    if(model.getDepth().get()<(parentDepth+1)){
                        model.setDepth(parentDepth+1);
                    } 
                         
                    /*model.addParent(parent);
                    parent.addChild(model);*/
                        /*parentModel.getDotModel().addToChildren(model);            //add to the shared Dots children*/
                        parentModel.getDotModel().createLink(parent, model);       //create a new link in the dot. add child to parent's list of children and parent to child's list of parents
                        
                    }
                    
                    parentNode.setDropReceived(true);
                    parentModel.setChildJob(model);
                    /*droppedAnchor.centerXProperty().bind(node.layoutXProperty());
                    droppedAnchor.centerYProperty().bind(node.layoutYProperty());*/
                    droppedAnchor.centerXProperty().bind(Bindings.add(node.layoutXProperty(),node.getBoundsInLocal().getMaxX()/2.0));
                    droppedAnchor.centerYProperty().bind(Bindings.add(node.layoutYProperty(),node.getBoundsInLocal().getMinY()));
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
            showTable.setDisable(true);
            model.extractLogs();
            
    }
    
    @FXML
    void checkMultiples(ActionEvent event) {
        model.checkMultiples();
    }
    
    
    @FXML
    void showTable(ActionEvent event) {
            LineTableModel lineTableModel=new LineTableModel(model);
            LineTableView lineTableView=new LineTableView(lineTableModel);
    }
    
     @FXML
    void showQctable(ActionEvent event) {
            QcTableModel qcTableModel=new QcTableModel(model);
            QcTableView qcTableView=new QcTableView(qcTableModel);
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
            System.out.println("workspace.WorkspaceController.NameChangeListener.changed(): from "+oldValue+" to "+newValue);
            model.setNameproperty(newValue);
           
        }
    };
    
    
    
    final private ChangeListener<Boolean> headerExtractionListener=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                showTable.setDisable(false);
            }
        }
    };
    
    
    final private ChangeListener<Number> depthChangeListener=new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            System.out.println("JobType1Controller.depth.changed(): "+model.getNameproperty().get()+" from "+oldValue+" -> "+newValue);
           dbjob.setDepth((Long) newValue);
           jobService.updateJob(dbjob.getId(), dbjob);
           /*Set<Descendant> descendants=dbjob.getDescendants();    //the descendants aren't truly reflected till the session is saved
           for(Descendant d:descendants){
           d.getDescendant().setDepth(d.getDescendant().getDepth()+1);
           }*/
           Set<JobType0Model> descendants=model.getDescendants();
           for(JobType0Model desc:descendants){
            System.out.println("depth will change for Descendants: "+desc.getNameproperty().get()+" from "+desc.getDepth().get()+" --> "+(desc.getDepth().get()+1));
            Job d=jobService.getJob(desc.getId());
            d.setDepth(desc.getDepth().get()+1);
            jobService.updateJob(d.getId(), d);
            desc.setListenToDepthChange(false);
            desc.setDepth(desc.getDepth().get()+1);
            desc.setListenToDepthChange(true);
           }
           /*Set<JobType0Model> children=model.getChildren();
           for(JobType0Model child:children){
           child.setDepth(child.getDepth().get()+1);
           }*/
        }
    };
    
    
    final private ChangeListener<Boolean> listenToDepthChange=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                model.getDepth().addListener(depthChangeListener);
            }else{
                model.getDepth().removeListener(depthChangeListener);
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
                ParentChildEdgeView pcdn=new ParentChildEdgeView(pcdm, node, JobType2Controller.this.interactivePane);
                
                
                
                
            }
        });
        
        menu.getItems().addAll(addAChildJob,deleteThisJob);
    }
    
    
    
}