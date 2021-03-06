/*/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fend.job.job3;


import app.properties.AppProperties;
import fend.dot.anchor.AnchorView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import db.model.Ancestor;
import db.model.Descendant;
import db.model.Dot;
import db.model.Job;
import db.model.Link;
import db.services.AncestorService;
import db.services.AncestorServiceImpl;
import db.services.CommentService;
import db.services.CommentServiceImpl;
import db.services.DescendantService;
import db.services.DescendantServiceImpl;
import db.services.DotService;
import db.services.DotServiceImpl;
import db.services.DoubtService;
import db.services.DoubtServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.LinkService;
import db.services.LinkServiceImpl;
import db.services.NodePropertyValueService;
import db.services.NodePropertyValueServiceImpl;
import db.services.QcMatrixRowService;
import db.services.QcMatrixRowServiceImpl;
import db.services.QcTableService;
import db.services.QcTableServiceImpl;
import db.services.QcTypeService;
import db.services.QcTypeServiceImpl;
import db.services.SubsurfaceJobService;
import db.services.SubsurfaceJobServiceImpl;
import db.services.SummaryService;
import db.services.SummaryServiceImpl;
import db.services.UserPreferenceService;
import db.services.UserPreferenceServiceImpl;
import db.services.VariableArgumentService;
import db.services.VariableArgumentServiceImpl;
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
import fend.job.job3.definitions.JobDefinitionsType3Model;
import fend.job.job3.definitions.JobDefinitionsType3View;
import fend.job.job3.table.acqLineTable.AcqLineTableModel;
import fend.job.job3.table.acqLineTable.AcqLineTableView;
import fend.job.table.qctable.QcTableModel;
import fend.job.table.qctable.QcTableView;
import fend.volume.volume0.Volume0;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import middleware.dugex.HeaderExtractor;
import middleware.dugex.HeaderLoader;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobType3Controller implements JobType0Controller{
    final String expand=">";
    final String collapse="<";
    private AnchorPane interactivePane;
    private JobType3Model model;
    private JobType3View node;
    private Job dbjob;
    private JobService jobService=new JobServiceImpl();
    JFXDrawer drawer=new JFXDrawer();
    private final ContextMenu menu=new ContextMenu();
    private AncestorService ancestorService=new AncestorServiceImpl();
    private DescendantService descendantService=new DescendantServiceImpl();
    
    private BooleanProperty checkForHeaders;
    private HeaderExtractor headerExtractor=null;
    private Executor exec;
    private QcTableModel  qcTableModel;
    
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

     @FXML
    private JFXProgressBar progressBar;

    @FXML
    private Label message;
QcTypeService qcTypeService=new QcTypeServiceImpl();
 
 
 
    void setModel(JobType3Model item) {
        model=item;
        //dbjob=jobService.getJob(model.getId());
         dbjob=model.getDatabaseJob();
//checkForHeaders=new SimpleBooleanProperty(false);
        //checkForHeaders.addListener(headerExtractionListener);
        numberOfTypes=qcTypeService.getAllQcTypes().size();
        model.getHeadersCommited().addListener(headerExtractionListener);
        
        model.getListenToDepthChangeProperty().addListener(DEPTH_CHANGE_LISTENER);
      //  model.getDepth().addListener(depthChangeListener);
      model.updateProperty().addListener(DATABASE_JOB_UPDATE_LISTENER);
      model.deleteProperty().addListener(CURRENT_JOB_DELETE_LISTENER);
      model.qcChangedProperty().addListener(QC_CHANGED_LISTENER);
       model.exitQcTableProperty().addListener(QC_TABLE_EXITED_LISTENER);
      //qcTableModel.qcSelectionChangedProperty().addListener(QC_CHANGED_LISTENER);
      
      exec=Executors.newCachedThreadPool(runnable->{
          Thread t=new Thread(runnable);
          t.setDaemon(true);
          return t;
      });
      model.blockProperty.addListener(BLOCK_UNBLOCK_LISTENER);
        
    }

    void setView(JobType3View vw,AnchorPane interactivePane) {
        node=vw;
        this.interactivePane=interactivePane;
        drawer.setId("LEFT");
        JobDefinitionsType3Model bdmodel=new JobDefinitionsType3Model();
        JobDefinitionsType3View bdview=new JobDefinitionsType3View(bdmodel,this.model);
        drawer.setSidePane(bdview);
        drawer.setDirection(JFXDrawer.DrawerDirection.LEFT);
        drawer.setDefaultDrawerSize(bdview.computeAreaInScreen());
        drawer.setOverLayVisible(false);
        drawer.setResizableOnDrag(true);
        drawer.setTranslateX(165);
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
            double x=event.getSceneX();
            double y=event.getSceneY();
            nodePropertyValueService.updateCoordinateXforJob(dbjob,x);
            nodePropertyValueService.updateCoordinateYforJob(dbjob,y);
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
                    DotView dotnode=new DotView(dotmodel, JobType3Controller.this.interactivePane);
                    dotmodel.createLink(parent, model); // create a link between parent and child .add child to parent's list of children and parent to child's list of parents
                    
                    
                    setupAncestorsAndDescendants(parent);
                    
                   
                    //parentChildEdgeNode.getChildren().add(0,dotnode);
                    parentChildEdgeNode.add(dotnode);
                   

                    CubicCurve curve=parentChildEdgeNode.getController().getCurve();  //the curve in the node
                    dotnode.centerXProperty().bind(Bindings.divide((Bindings.add(curve.startXProperty(), curve.endXProperty())),2.0));
                    dotnode.centerYProperty().bind(Bindings.divide((Bindings.add(curve.startYProperty(), curve.endYProperty())),2.0));

                    
                    parentChildEdgeModel.setChildJob(model);
                    parentChildEdgeNode.setDropReceived(true);
                    
                    droppedAnchor.centerXProperty().bind(Bindings.add(node.layoutXProperty(),node.getBoundsInLocal().getMaxX()/2.0));
                    droppedAnchor.centerYProperty().bind(Bindings.add(node.layoutYProperty(),node.getBoundsInLocal().getMinY()));
                    model.toggleDepthChange();
                    subsurfaceJobService.updateTimeWhereJobEquals(dbjob, now());
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
                        
                         setupAncestorsAndDescendants(parent);
                        
                  
                        parentModel.getDotModel().createLink(parent, model);       //create a new link in the dot. add child to parent's list of children and parent to child's list of parents
                        
                    }
                    
                    parentNode.setDropReceived(true);
                    parentModel.setChildJob(model);
                    /*droppedAnchor.centerXProperty().bind(node.layoutXProperty());
                    droppedAnchor.centerYProperty().bind(node.layoutYProperty());*/
                    droppedAnchor.centerXProperty().bind(Bindings.add(node.layoutXProperty(),node.getBoundsInLocal().getMaxX()/2.0));
                    droppedAnchor.centerYProperty().bind(Bindings.add(node.layoutYProperty(),node.getBoundsInLocal().getMinY()));
                    model.toggleDepthChange();
                    subsurfaceJobService.updateTimeWhereJobEquals(dbjob, now());
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
            
           // model.extractLogs();
            
                  if(headerExtractor==null){
                  Task<Void> headerExtractionTask=new Task<Void>() {
                      @Override
                      protected Void call() throws Exception {
                          headerExtractor=new HeaderExtractor(model);
                          headerExtractor.progressProperty().addListener((obs,o,n)->{
                              //System.out.println("JobType1Controller.checkLogsListener.call(): progress is : "+n.doubleValue());
                              updateProgress(n.doubleValue(), 1);
                          });
                          headerExtractor.messageProperty().addListener((obs,o,n)->{
                              //System.out.println("JobType1Controller.checkLogsListener.call(): message is : "+n);
                              updateMessage(n);
                          });
                          headerExtractor.work();
                          return null;
                      }
                  };
                  
                  headerExtractionTask.setOnFailed(e->{
                      headerExtractor=null;
                      headerButton.setDisable(false);
                       showTable.setDisable(false);
                       qctable.setDisable(false);
                       //model.setFinishedCheckingLogs(false);
                       progressBar.progressProperty().unbind();
                      progressBar.setProgress(0);
                      message.textProperty().unbind();
                      message.setText("");
                       headerExtractionTask.getException().printStackTrace();
                  });
                  
                  headerExtractionTask.setOnSucceeded(e->{
                      headerExtractor=null;
                      headerButton.setDisable(false);
                      qctable.setDisable(false);
                      showTable.setDisable(false);
                      progressBar.progressProperty().unbind();
                      progressBar.setProgress(0);
                      message.textProperty().unbind();
                      message.setText("");
                     // model.setFinishedCheckingLogs(false);
                  });
                  
                  headerExtractionTask.setOnRunning(e->{
                      showTable.setDisable(true);
                      qctable.setDisable(true);
                  });
                  
                    progressBar.progressProperty().unbind();
                    progressBar.progressProperty().bind(headerExtractionTask.progressProperty()); 
                    message.textProperty().unbind();
                    message.textProperty().bind(headerExtractionTask.messageProperty());
                  exec.execute(headerExtractionTask);
              }
            
            
    }
    
    @FXML
    void checkMultiples(ActionEvent event) {
        model.checkMultiples();
    }
    
    
    @FXML
    void showTable(ActionEvent event) {
            AcqLineTableModel lineTableModel=new AcqLineTableModel(model);
            AcqLineTableView lineTableView=new AcqLineTableView(lineTableModel);
    }
    
    
    private QcTableView qcTableView;
    
     @FXML
    void showQctable(ActionEvent event) {
        
                Task<Void> qctableTask=new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                //  qctable.setDisable(true);
                if(qcTableModel==null){
                     qcTableModel=new QcTableModel(model);
                     
                }


                    return null;
                    }
                };

                qctableTask.setOnFailed(e->{
                qctableTask.getException().printStackTrace();
                qctable.setDisable(false);
                });
                qctableTask.setOnSucceeded(e->{
                qcTableView=new QcTableView(qcTableModel);
                qctable.setDisable(false);

                });
                qctableTask.setOnRunning(e->{
                System.out.println("fend.job.job1.JobType3Controller.showQctable()...loading the qctable");
                qctable.setDisable(true);
                });

                exec.execute(qctableTask);
        
            
            
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
            //
             Task<Void> task=new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    /*dbjob=jobService.getJob(model.getId());
                    dbjob.setNameJobStep(model.getNameproperty().get());
                    jobService.updateJob(dbjob.getId(), dbjob);*/
                    jobService.updateName(dbjob,model.getNameproperty().get());
                    return null;
                }
            };
            
            exec.execute(task);
           
        
            //
           
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
    
    
    final private ChangeListener<Boolean> DEPTH_CHANGE_LISTENER=new ChangeListener<Boolean>() {
       

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
             System.out.println("JobType1Controller.depth.changed(): "+model.getNameproperty().get()+" from "+oldValue+" -> "+newValue);
           
            
          
            model.toggleUpdateProperty();
           long depth=depth(dbjob);
            System.out.println("JobType1Controller.depth.changed(): "+dbjob.getNameJobStep()+" is now at depth : "+depth);
           
           jobService.updateDepth(dbjob, depth);
           model.setDepth(depth);
           
           
           Set<JobType0Model> descendants=model.getDescendants();
           for(JobType0Model desc:descendants){
            //System.out.println("depth will change for Descendants: "+desc.getNameproperty().get()+" from "+desc.getDepth().get()+" --> "+(desc.getDepth().get()+1));
                 desc.toggleDepthChange();
           }
          
        }
    };
    
     private long depth(Job job) {
           if(job.isRoot()){
               return 0;
           }
           
           else{
               List<Link> linksWithJobAsChild=linkService.getChildLinksForJob(job);
               long currentJobDepth=0;
               for(Link l:linksWithJobAsChild){
                   Job parent=l.getParent();
                   long val=1+depth(parent);
                   if(currentJobDepth < val) {
                        currentJobDepth=val;
                   }
                   
               }
               
               return currentJobDepth;
           }
    }
    
    
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
                ParentChildEdgeView pcdn=new ParentChildEdgeView(pcdm, node, JobType3Controller.this.interactivePane);
                
                
                
                
            }
        });
        deleteThisJob.setOnAction(e->{
            model.toggleDeleteProperty();
        });
        menu.getItems().addAll(addAChildJob,deleteThisJob);
    }
    
    
      /**
     * Function called for split and njs operation.
     * Sets up the ancestors descendants table.
     * 
     * Ap=parent.getAncestors;          Dp=parent.getDescendants()
     * Ac=job.getAncestors;             Dc=job.getDescendants()
     * 
     * Ac=Ac+{parent,Ap}               Dp=Dp+{job,Dc}
     * Ap=Ap;                          Dc=Dc
     * 
     * 
     * for(each of jobs Descendants d : Dc)
     * {
     *  Ancestor_d=Ancestor_d+Ac;
     * }
     * 
     * for(each of parents Ancestors a : Ap)
     * {
     *  Descendant_a=Descendant_a+Dp;
     * }
     **/
    private void setupAncestorsAndDescendants(JobType0Model parent) {
         dbjob=jobService.getJob(dbjob.getId());
                    Job dbParent=jobService.getJob(parent.getId());
                    
                    /*Set<Ancestor>   Ap=dbParent.getAncestors();
                    Set<Ancestor>   Ac=dbjob.getAncestors();
                    Set<Descendant> Dp=dbParent.getDescendants();
                    Set<Descendant> Dc=dbjob.getDescendants();*/
                    
                    Set<Ancestor> Ap=new LinkedHashSet<>(ancestorService.getAncestorFor(dbParent));
                    Set<Ancestor> Ac=new LinkedHashSet<>(ancestorService.getAncestorFor(dbjob));
                    Set<Descendant> Dp=new LinkedHashSet<>(descendantService.getDescendantsFor(dbParent));
                    Set<Descendant> Dc=new LinkedHashSet<>(descendantService.getDescendantsFor(dbjob));
                    System.out.println("fend.job.job1.JobType3Controller.setView(): Size of The parent job "+ dbParent.getId()+" "+dbParent.getNameJobStep() + "  Ancestors: "+Ap.size());
                    System.out.println("fend.job.job1.JobType3Controller.setView(): Size of The child job "+ dbjob.getId()+" "+dbjob.getNameJobStep() + "  Ancestors: "+Ac.size());
                    System.out.println("fend.job.job1.JobType3Controller.setView(): Size of The parent job "+ dbParent.getId()+" "+dbParent.getNameJobStep() + "  Descendants: "+Dp.size());
                    System.out.println("fend.job.job1.JobType3Controller.setView(): Size of The childs job "+ dbjob.getId()+" "+dbjob.getNameJobStep() + "  Descendants: "+Dc.size());
                    
                    
                    Ancestor parentIsAnAncestor;
                    if((parentIsAnAncestor=ancestorService.getAncestorFor(dbjob, dbParent))==null){
                        parentIsAnAncestor=new Ancestor();
                        parentIsAnAncestor.setJob(dbjob);
                        parentIsAnAncestor.setAncestor(dbParent);

                        ancestorService.addAncestor(parentIsAnAncestor);
                        System.out.println("fend.job.job1.JobType3Controller.setView() Created an new Ancestor for job "+dbjob.getNameJobStep()+" and ancestor  "+dbParent.getNameJobStep());
                        Ac.add(parentIsAnAncestor);
                        System.out.println("fend.job.job1.JobType3Controller.setView(): Added ");
                    }else{
                         System.out.println("fend.job.job1.JobType3Controller.setView() Found an  Ancestor Entry existing for job "+dbjob.getNameJobStep()+" and ancestor  "+dbParent.getNameJobStep());
                            
                    }
                    /*dbjob.setAncestors(Ac);
                    for(Ancestor cc:Ac){
                    ancestorService.updateAncestor(cc.getId(), cc);
                    }
                    jobService.updateJob(dbParent.getId(), dbParent);
                    jobService.updateJob(dbjob.getId(), dbjob);
                    */
                    
                    
                    for(Ancestor ap:Ap){                        //add all the ancestors of parents to the jobs list of ancestors
                        Ancestor jobAncestor;
                        Job ancestorToBeAdded=ap.getAncestor();
                        if((jobAncestor=ancestorService.getAncestorFor(dbjob, ancestorToBeAdded))==null){
                            
                            jobAncestor=new Ancestor();
                            jobAncestor.setJob(dbjob);
                            jobAncestor.setAncestor(ancestorToBeAdded);
                            ancestorService.addAncestor(jobAncestor);
                            System.out.println("fend.job.job1.JobType3Controller.setView() creating new Ancestor for job "+dbjob.getNameJobStep()+" new Ancestor added: "+ancestorToBeAdded.getNameJobStep());
                            Ac.add(jobAncestor);
                        }else{
                            System.out.println("fend.job.job1.JobType3Controller.setView() Found an  Ancestor Entry existing for job "+dbjob.getNameJobStep()+" and ancestor "+ancestorToBeAdded.getNameJobStep());
                            
                        }
                        //jobService.updateJob(ancestorToBeAdded.getId(), ancestorToBeAdded);
                    }
                    
                    //dbjob.setAncestors(Ac);
                    /* for(Ancestor cc:Ac){
                    ancestorService.updateAncestor(cc.getId(), cc);
                    }*/
                    
                    //jobService.updateJob(dbjob.getId(), dbjob);
                    
                    
                    Descendant currentJobIsADescendant;
                    if((currentJobIsADescendant=descendantService.getDescendantFor(dbParent, dbjob))==null){
                        currentJobIsADescendant=new Descendant();
                        currentJobIsADescendant.setJob(dbParent);
                        currentJobIsADescendant.setDescendant(dbjob);
                        descendantService.addDescendant(currentJobIsADescendant);
                         System.out.println("fend.job.job1.JobType3Controller.setView() creating new Descendant for job "+dbParent.getNameJobStep()+" new Descendant added: "+dbjob.getNameJobStep());
                         Dp.add(currentJobIsADescendant);
                                
                    }
                    /*dbParent.setDescendants(Dp);
                    jobService.updateJob(dbParent.getId(), dbParent);
                    jobService.updateJob(dbjob.getId(), dbjob);*/
                    
                    
                    for(Descendant dj:Dc){          //add all of the jobs descendants to the parents list of Descendants
                        Descendant currentJobsDescendant;   
                        Job descendantToBeAdded=dj.getDescendant();
                        if((currentJobsDescendant=descendantService.getDescendantFor(dbParent, descendantToBeAdded))==null){
                            currentJobsDescendant=new Descendant();
                            currentJobsDescendant.setJob(dbParent);
                            currentJobsDescendant.setDescendant(descendantToBeAdded);
                            descendantService.addDescendant(currentJobsDescendant);
                            System.out.println("fend.job.job1.JobType3Controller.setView(): creating new Descendant for job "+dbParent.getNameJobStep()+" new Descendant added: "+dbjob.getNameJobStep());
                            Dp.add(currentJobsDescendant);              //add current jobs descendant to the list of parents descendants
                        }
                    //   jobService.updateJob(descendantToBeAdded.getId(), descendantToBeAdded);
                    }
                    /* dbParent.setDescendants(Dp);
                    jobService.updateJob(dbParent.getId(), dbParent);*/
                    
                    
                    
                    
                    //update the ancestor list for the jobs descendants
                    for(Descendant jobsDescendantEntry:Dc){
                        Job jobsDescendant=jobsDescendantEntry.getDescendant();              //jobs descendant
                       // Set<Ancestor> ancestorsInJobsDescendant=jobsDescendant.getAncestors();
                       Set<Ancestor> ancestorsInJobsDescendant=new HashSet<>(ancestorService.getAncestorFor(jobsDescendant));
                        
                            for(Ancestor anc:Ac){
                                Job ancestorJobToBeAdded=anc.getAncestor();        //this ancestor is the current jobs ancestor which is now been added to its descendant
                                Ancestor jobAncestor;
                                if((jobAncestor=ancestorService.getAncestorFor(jobsDescendant, ancestorJobToBeAdded))==null){
                                    
                                    jobAncestor=new Ancestor();
                                    jobAncestor.setJob(jobsDescendant);
                                    jobAncestor.setAncestor(ancestorJobToBeAdded);
                                    ancestorService.addAncestor(jobAncestor);
                                    System.out.println("fend.job.job1.JobType3Controller.setView() creating new Ancestor for job "+jobsDescendant.getNameJobStep()+" new Ancestor added: "+ancestorJobToBeAdded.getNameJobStep());
                                    ancestorsInJobsDescendant.add(jobAncestor);
                                }else{
                                     System.out.println("fend.job.job1.JobType3Controller.setView() Found an  Ancestor Entry existing for job "+jobsDescendant.getNameJobStep()+" and ancestor "+ancestorJobToBeAdded.getNameJobStep());
                                }
                            }
                            
                            /* jobsDescendant.setAncestors(ancestorsInJobsDescendant);
                            
                            jobService.updateJob(jobsDescendant.getId(), jobsDescendant);*/
                    }
                            
                    
                    
                    //update the descendant list for the parents ancestors
                    for(Ancestor parentAncestorEntry:Ap){
                        Job parentsAncestor=parentAncestorEntry.getAncestor();  //parents Ancestor
                        //Set<Descendant> descendantsInParentsAncestor=parentsAncestor.getDescendants();
                        Set<Descendant> descendantsInParentsAncestor=new HashSet<>(descendantService.getDescendantsFor(parentsAncestor));
                        
                        for(Descendant desc:Dp){                    //add all of the parents descendants to the parents ancestors
                            Job descendantToBeAdded=desc.getDescendant();       //descendant to be added to the parents ancestor's list of descendants
                            Descendant parentDescendant;
                            
                            if((parentDescendant=descendantService.getDescendantFor(parentsAncestor,descendantToBeAdded))==null){
                                parentDescendant=new Descendant();
                                parentDescendant.setJob(parentsAncestor);
                                parentDescendant.setDescendant(descendantToBeAdded);
                                descendantService.addDescendant(parentDescendant);
                                System.out.println("fend.job.job1.JobType3Controller.setView(): creating new Descendant for job "+parentsAncestor.getNameJobStep()+" new Descendant added: "+descendantToBeAdded.getNameJobStep());
                                descendantsInParentsAncestor.add(parentDescendant);
                            }else{
                                System.out.println("fend.job.job1.JobType3Controller.setView(): found a Descendant Entry existing for job "+parentsAncestor.getNameJobStep()+" and descendant "+descendantToBeAdded.getNameJobStep());
                                
                            }
                        }
                        
                        /*parentsAncestor.setDescendants(descendantsInParentsAncestor);
                        jobService.updateJob(parentsAncestor.getId(), parentsAncestor);*/
                    }
                     model.toggleUpdateProperty();
                     parent.toggleUpdateProperty();
                    
    }
     private ChangeListener<Boolean> DATABASE_JOB_UPDATE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            dbjob=jobService.getJob(dbjob.getId());
            model.setDatabaseJob(dbjob);
        }
    };
    
  
    private LinkService linkService=new LinkServiceImpl();
    private VariableArgumentService variableArgumentService=new VariableArgumentServiceImpl();
    private DotService dotService=new DotServiceImpl();
    private DoubtService doubtService=new DoubtServiceImpl();
    private SummaryService summaryService=new SummaryServiceImpl();
    private QcTableService qcTableService=new QcTableServiceImpl();
    private QcMatrixRowService qcMatrixRowService=new QcMatrixRowServiceImpl();
    private SubsurfaceJobService subsurfaceJobService=new SubsurfaceJobServiceImpl();
    
     private String now() {
        return DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
    }
    
      private void deleteLinksBelongingtoCurrentJob() {
           List<Dot> dotsForJob=linkService.getDotsForJob(dbjob);            //list of dots where link.parent=job OR link.child=job
          System.out.println("fend.job.job1.JobType1Controller.deleteLinksBelongingtoCurrentJob(): deleting the variable arguments ");
          for(Dot dot:dotsForJob){
          variableArgumentService.deleteVariableArgumentFor(dot);
          }
          
          List<Link> links1=linkService.getChildLinksForJob(dbjob);       //get links where job is child and update the links parents
            for(Link l:links1){
                subsurfaceJobService.updateTimeWhereJobEquals(l.getParent(), now());
            }
            
            List<Link> links2=linkService.getParentLinksFor(dbjob);         //get links where job is parent and update the links children
            for(Link l:links2){
                subsurfaceJobService.updateTimeWhereJobEquals(l.getChild(), now());
            }
          
          
            linkService.deleteLinksForJob(dbjob);
              for(Dot dot:dotsForJob){
            dot=dotService.getDot(dot.getId());
            if(dot.canBeDeleted()){ //if dot no longer has any links then its candidate for delete
                 doubtService.deleteAllDoubtsRelatedTo(dot);
            System.out.println("fend.job.job1.JobType1Controller.deleteLinksBelongingtoCurrentJob(): deleting dot "+dot.getId());
            dotService.deleteDot(dot.getId());
            }else{
            System.out.println("fend.job.job1.JobType1Controller.deleteLinksBelongingtoCurrentJob(): NO DELETION for dot "+dot.getId()+" which has links existing. ");
            dot.setFunction("");
            dotService.updateFunction(dot);
            //rebuildVariableArguments(dot);     // This is done during the reload of the session
            }
            }
            
        }

       private void rebuildVariableArguments(Dot dot) {
           //figure out the mode of the dot(SPLIT,JOIN,NJS)    . Done during workspace loading
           throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    
      private void rebuildAncestorDescendants() {
           model.getWorkspaceModel().rebuildGraph();              //workspace controller rebuilds the graph
      }
    
     
       private void deleteAllVolumesInCurrentJob() {
            //toggleDelete On each volume
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
     
       private void deleteAllDoubtsRelatedToJob() {
            doubtService.deleteAllDoubtsRelatedTo(dbjob);
        }
     
       private void deleteAllSummariesRelatedToJob() {
            summaryService.deleteAllSummariesForJob(dbjob);
        }
       
        private void reloadWorkspace() {
            model.getWorkspaceModel().reload();
        }

         private void deleteAllQcsRelatedToJob() {
             System.out.println("fend.job.job1.JobType1Controller.deleteAllQcsRelatedToJob(): deleting all qc table entries related to job "+dbjob.getNameJobStep());
             qcTableService.deleteAllQcTablesForJob(dbjob);
             System.out.println("fend.job.job1.JobType1Controller.deleteAllQcsRelatedToJob(): deleting all the qc definitions related to this job");
             qcMatrixRowService.deleteAllQcMatrixRowsForJob(dbjob);
        }

          private void deleteAllCommentsRelatedToJob() {
           commentService.deleteAllCommentsRelatedToJob(dbjob);
        }
         private void deleteAllUserPreferencesRelatedToJob(){
             userPreferenceService.deleteAllUserPreferencesFor(dbjob);
         }

    private NodePropertyValueService nodePropertyValueService=new NodePropertyValueServiceImpl();
    private CommentService commentService=new CommentServiceImpl();
    private UserPreferenceService userPreferenceService=new UserPreferenceServiceImpl();

    private ChangeListener<Boolean> CURRENT_JOB_DELETE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            model.getWorkspaceModel().block();
            
            System.out.println("fend.job.job1.JobType1Controller.CURRENT_JOB_DELETE_LISTENER: deleting  all qc comments related to this job");
            deleteAllCommentsRelatedToJob();
            System.out.println("fend.job.job1.JobType1Controller.CURRENT_JOB_DELETE_LISTENER: deleting  all user preferences related to this job");
            deleteAllUserPreferencesRelatedToJob();
            System.out.println("fend.job.job1.JobType1Controller.CURRENT_JOB_DELETE_LISTENER: deleting all qcs related to this job");
            deleteAllQcsRelatedToJob();
            System.out.println("fend.job.job1.JobType1Controller.CURRENT_JOB_DELETE_LISTENER: deleting doubts related to this job");
            deleteAllDoubtsRelatedToJob();
            deleteLinksBelongingtoCurrentJob();
            
            
            /*deleteAllVolumesInCurrentJob();
            deleteAllDoubtsRelatedToJob();
            deleteAllSummariesRelatedToJob();*/
            nodePropertyValueService.removeAllNodePropertyValuesFor(dbjob);
            model.getWorkspaceModel().prepareToRebuild();                 //clear all ancestors before deleting
            
            
             Task<Void> jobDeletionTask=new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                /* List<Volume0> volsInJobDc=new ArrayList<>();
                for(Volume0 v:model.getVolumes()){
                volsInJobDc.add(v);
                }
                
                System.out.println("fend.job.job1.JobType1Controller.CURRENT_JOB_DELETE_LISTENER: no of volumes in the job: "+volsInJobDc.size());
                for(Volume0 vol:volsInJobDc){
                System.out.println("fend.job.job1.JobType1Controller.CURRENT_JOB_DELETE_LISTENER: deleting volume "+vol.getName().get()+" id: "+vol.getId());
                vol.delete(true);
                model.removeVolume(vol);
                }*/
                   
                    
                    
                    System.out.println("fend.job.job1.JobType1Controller.CURRENT_JOB_DELETE_LISTENER: deleting summaries related to this job");
                    deleteAllSummariesRelatedToJob();
                    
                    System.out.println("fend.job.job1.JobType1Controller.CURRENT_JOB_DELETE_LISTENER: deleting "+dbjob.getNameJobStep() );
                    jobService.deleteJob(dbjob.getId());  //replace by soft delete
                    
                       return null;
                    }
                    };
                     
           jobDeletionTask.setOnRunning(e->{
                System.out.println("deletion in process...");
            });
            
            jobDeletionTask.setOnSucceeded(e->{
                
                    System.out.println("fend.job.job1.JobType1Controller.CURRENT_JOB_DELETE_LISTENER: Rebuilding ancestors and descendants");
                     rebuildAncestorDescendants();
                     reloadWorkspace();
                     model.getWorkspaceModel().unblock();
            });
            exec.execute(jobDeletionTask);
           
        }

       
       
      
       
 
    };
int numberOfTypes;
     private ChangeListener<Boolean> QC_CHANGED_LISTENER=new ChangeListener<Boolean>() {
         @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            System.out.println("fend.job.job1.JobType1Controller.QC_CHANGED_LISTENER: will reload qcs");
            numberOfTypes=qcTypeService.getAllQcTypes().size();
            if(qcTableModel!=null && qcTableView!=null){
                //reload only if the number of items in the 
                
                if(model.getQcMatrixModel().listSize()==numberOfTypes){
                    System.out.println("fend.job.job1.JobType1Controller.QC_CHANGED_LISTENER: will reload the qctable");
                    model.block();
                    qcTableModel.reloadSequences();
                    subsurfaceJobService.updateTimeWhereJobEquals(dbjob, AppProperties.timeNow());
                }else{
                    System.out.println("fend.job.job1.JobType1Controller.QC_CHANGED_LISTENER: mismatch of size: model.getQcMatrixModel().listSize() != numberOfTypes : "+ model.getQcMatrixModel().listSize()+"!="+numberOfTypes);
                }
                
            }
            //qcTableModel=null;
            
                if(model.getQcMatrixModel().listSize()==numberOfTypes){
                    subsurfaceJobService.updateTimeWhereJobEquals(dbjob, AppProperties.timeNow());
                }
        }
    };
     
     
     
     
    private ChangeListener<Boolean> BLOCK_UNBLOCK_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                model.getWorkspaceModel().block();
            }else{
                model.getWorkspaceModel().unblock();
            }
        }
    };
    
     private ChangeListener<Boolean> QC_TABLE_EXITED_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            qcTableView=null;
            qcTableModel=null;
        }
    };   
}