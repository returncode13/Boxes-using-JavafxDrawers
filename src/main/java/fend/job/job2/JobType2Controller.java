/*/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fend.job.job2;


import fend.dot.anchor.AnchorView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXTextField;
import db.model.Ancestor;
import db.model.Descendant;
import db.model.Job;
import db.services.AncestorService;
import db.services.AncestorServiceImpl;
import db.services.DescendantService;
import db.services.DescendantServiceImpl;
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
import fend.job.job2.definitions.JobDefinitionsModel;
import fend.job.job2.definitions.JobDefinitionsView;
import fend.job.table.lineTable.LineTableModel;
import fend.job.table.lineTable.LineTableView;
import fend.job.table.qctable.QcTableModel;
import fend.job.table.qctable.QcTableView;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import middleware.dugex.DugLogManager;
import middleware.dugex.HeaderExtractor;
import middleware.dugex.HeaderLoader;

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
    private AncestorService ancestorService=new AncestorServiceImpl();
    private DescendantService descendantService=new DescendantServiceImpl();
    
    
    
    private DugLogManager dugLogManager=null;
    private HeaderExtractor headerExtractor=null;
    private Executor exec;
    
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
        
        //dbjob=jobService.getJob(model.getId());
         dbjob=model.getDatabaseJob();
//checkForHeaders=new SimpleBooleanProperty(false);
        //checkForHeaders.addListener(headerExtractionListener);
        model.getHeadersCommited().addListener(headerExtractionListener);
        model.getListenToDepthChangeProperty().addListener(listenToDepthChange);
      //  model.getDepth().addListener(depthChangeListener);
         model.finishedCheckingLogs().addListener(checkLogsListener);
      
      exec=Executors.newCachedThreadPool(runnable->{
          Thread t=new Thread(runnable);
          t.setDaemon(true);
          return t;
      });
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
                    
                    setupAncestorsAndDescendants(parent);
                    
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
                        
                         setupAncestorsAndDescendants(parent);
                         
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
              if(dugLogManager==null){
                headerButton.setDisable(true);
                 showTable.setDisable(true);
                 qctable.setDisable(true);
                 
                Task<Void> logExtraction=new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                       dugLogManager=new DugLogManager(model);
                       return null;
                    }
                };
                
                logExtraction.setOnFailed(e->{
                        logExtraction.getException().printStackTrace();
                        headerButton.setDisable(false);
                         showTable.setDisable(false);
                        model.setFinishedCheckingLogs(false);
                        dugLogManager=null;
                });
                
                logExtraction.setOnSucceeded(e->{
                   // headerButton.setDisable(false);               this has to be enabled  AFTER the header extraction takes place. See Listener checkLogsListener
                    model.setFinishedCheckingLogs(true);
                    dugLogManager=null;
                });
                
                exec.execute(logExtraction);
            }
            
           //model.extractLogs();
            
    }
    
    @FXML
    void checkMultiples(ActionEvent event) {
        model.checkMultiples();
    }
    
    
    @FXML
    void showTable(ActionEvent event) {
            final HeaderLoader headerloader=new HeaderLoader(model);
            Task<String> headerLoaderTask=new Task<String>(){
                @Override
                protected String call() throws Exception {
                    headerloader.retrieveHeaders();
                    
                    return "Finished loading of headers for "+model.getNameproperty().get();
                }
                
            };
            headerLoaderTask.setOnSucceeded(e->{
                    model.setSequenceHeaders(headerloader.getSequenceHeaders());
                    LineTableModel lineTableModel=new LineTableModel(model);
                    LineTableView lineTableView=new LineTableView(lineTableModel);
            });
         
        
            headerLoaderTask.setOnRunning(e->{});
            headerLoaderTask.setOnFailed(e->{
                headerLoaderTask.getException().printStackTrace();
            });
        
            exec.execute(headerLoaderTask);
           
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
            /* dbjob=jobService.getJob(model.getId());
            dbjob.setDepth((Long) newValue);
            jobService.updateJob(dbjob.getId(), dbjob);*/
           jobService.updateDepth(dbjob,newValue.longValue());
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
    
    
    
    
    /**
  * Used to extract headers after the logs are extracted.
  **/
    
    private  ChangeListener<Boolean> checkLogsListener=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
          //  if(newValue){
          //      new HeaderExtractor(model);
          if(newValue){
              if(headerExtractor==null){
                  Task<Void> headerExtractionTask=new Task<Void>() {
                      @Override
                      protected Void call() throws Exception {
                          headerExtractor=new HeaderExtractor(model);
                          return null;
                      }
                  };
                  
                  headerExtractionTask.setOnFailed(e->{
                      headerExtractor=null;
                      headerButton.setDisable(false);
                       showTable.setDisable(false);
                       qctable.setDisable(false);
                       model.setFinishedCheckingLogs(false);
                  });
                  
                  headerExtractionTask.setOnSucceeded(e->{
                      headerExtractor=null;
                      headerButton.setDisable(false);
                      qctable.setDisable(false);
                      showTable.setDisable(false);
                      model.setFinishedCheckingLogs(false);
                  });
                  
                  exec.execute(headerExtractionTask);
              }
          }
           // }
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
        // dbjob=jobService.getJob(dbjob.getId());
                    //Job dbParent=jobService.getJob(parent.getId());
                    Job dbParent=parent.getDatabaseJob();
                    /*Set<Ancestor>   Ap=dbParent.getAncestors();
                    Set<Ancestor>   Ac=dbjob.getAncestors();
                    Set<Descendant> Dp=dbParent.getDescendants();
                    Set<Descendant> Dc=dbjob.getDescendants();*/
                    
                    Set<Ancestor> Ap=new LinkedHashSet<>(ancestorService.getAncestorFor(dbParent));
                    Set<Ancestor> Ac=new LinkedHashSet<>(ancestorService.getAncestorFor(dbjob));
                    Set<Descendant> Dp=new LinkedHashSet<>(descendantService.getDescendantsFor(dbParent));
                    Set<Descendant> Dc=new LinkedHashSet<>(descendantService.getDescendantsFor(dbjob));
                    System.out.println("fend.job.job1.JobType2Controller.setView(): Size of The parent job "+ dbParent.getId()+" "+dbParent.getNameJobStep() + "  Ancestors: "+Ap.size());
                    System.out.println("fend.job.job1.JobType2Controller.setView(): Size of The child job "+ dbjob.getId()+" "+dbjob.getNameJobStep() + "  Ancestors: "+Ac.size());
                    System.out.println("fend.job.job1.JobType2Controller.setView(): Size of The parent job "+ dbParent.getId()+" "+dbParent.getNameJobStep() + "  Descendants: "+Dp.size());
                    System.out.println("fend.job.job1.JobType2Controller.setView(): Size of The childs job "+ dbjob.getId()+" "+dbjob.getNameJobStep() + "  Descendants: "+Dc.size());
                    
                    
                    Ancestor parentIsAnAncestor;
                    if((parentIsAnAncestor=ancestorService.getAncestorFor(dbjob, dbParent))==null){
                        parentIsAnAncestor=new Ancestor();
                        parentIsAnAncestor.setJob(dbjob);
                        parentIsAnAncestor.setAncestor(dbParent);

                        ancestorService.addAncestor(parentIsAnAncestor);
                        System.out.println("fend.job.job1.JobType2Controller.setView() Created an new Ancestor for job "+dbjob.getNameJobStep()+" and ancestor  "+dbParent.getNameJobStep());
                        Ac.add(parentIsAnAncestor);
                        System.out.println("fend.job.job1.JobType2Controller.setView(): Added ");
                    }else{
                         System.out.println("fend.job.job1.JobType2Controller.setView() Found an  Ancestor Entry existing for job "+dbjob.getNameJobStep()+" and ancestor  "+dbParent.getNameJobStep());
                            
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
                            System.out.println("fend.job.job1.JobType2Controller.setView() creating new Ancestor for job "+dbjob.getNameJobStep()+" new Ancestor added: "+ancestorToBeAdded.getNameJobStep());
                            Ac.add(jobAncestor);
                        }else{
                            System.out.println("fend.job.job1.JobType2Controller.setView() Found an  Ancestor Entry existing for job "+dbjob.getNameJobStep()+" and ancestor "+ancestorToBeAdded.getNameJobStep());
                            
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
                         System.out.println("fend.job.job1.JobType2Controller.setView() creating new Descendant for job "+dbParent.getNameJobStep()+" new Descendant added: "+dbjob.getNameJobStep());
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
                            System.out.println("fend.job.job1.JobType2Controller.setView(): creating new Descendant for job "+dbParent.getNameJobStep()+" new Descendant added: "+dbjob.getNameJobStep());
                            Dp.add(currentJobsDescendant);              //add current jobs descendant to the list of parents descendants
                        }
                    //   jobService.updateJob(descendantToBeAdded.getId(), descendantToBeAdded);
                    }
                    /* dbParent.setDescendants(Dp);
                    jobService.updateJob(dbParent.getId(), dbParent);*/
                    
                    
                    
                    
                    //update the ancestor list for the jobs descendants
                    for(Descendant jobsDescendantEntry:Dc){
                        Job jobsDescendant=jobsDescendantEntry.getDescendant();              //jobs descendant
                        Set<Ancestor> ancestorsInJobsDescendant=jobsDescendant.getAncestors();
                        
                            for(Ancestor anc:Ac){
                                Job ancestorJobToBeAdded=anc.getAncestor();        //this ancestor is the current jobs ancestor which is now been added to its descendant
                                Ancestor jobAncestor;
                                if((jobAncestor=ancestorService.getAncestorFor(jobsDescendant, ancestorJobToBeAdded))==null){
                                    
                                    jobAncestor=new Ancestor();
                                    jobAncestor.setJob(jobsDescendant);
                                    jobAncestor.setAncestor(ancestorJobToBeAdded);
                                    ancestorService.addAncestor(jobAncestor);
                                    System.out.println("fend.job.job1.JobType2Controller.setView() creating new Ancestor for job "+jobsDescendant.getNameJobStep()+" new Ancestor added: "+ancestorJobToBeAdded.getNameJobStep());
                                    ancestorsInJobsDescendant.add(jobAncestor);
                                }else{
                                     System.out.println("fend.job.job1.JobType2Controller.setView() Found an  Ancestor Entry existing for job "+jobsDescendant.getNameJobStep()+" and ancestor "+ancestorJobToBeAdded.getNameJobStep());
                                }
                            }
                            
                            /* jobsDescendant.setAncestors(ancestorsInJobsDescendant);
                            
                            jobService.updateJob(jobsDescendant.getId(), jobsDescendant);*/
                    }
                            
                    
                    
                    //update the descendant list for the parents ancestors
                    for(Ancestor parentAncestorEntry:Ap){
                        Job parentsAncestor=parentAncestorEntry.getAncestor();  //parents Ancestor
                        Set<Descendant> descendantsInParentsAncestor=parentsAncestor.getDescendants();
                        
                        for(Descendant desc:Dp){                    //add all of the parents descendants to the parents ancestors
                            Job descendantToBeAdded=desc.getDescendant();       //descendant to be added to the parents ancestor's list of descendants
                            Descendant parentDescendant;
                            
                            if((parentDescendant=descendantService.getDescendantFor(parentsAncestor,descendantToBeAdded))==null){
                                parentDescendant=new Descendant();
                                parentDescendant.setJob(parentsAncestor);
                                parentDescendant.setDescendant(descendantToBeAdded);
                                descendantService.addDescendant(parentDescendant);
                                System.out.println("fend.job.job1.JobType2Controller.setView(): creating new Descendant for job "+parentsAncestor.getNameJobStep()+" new Descendant added: "+descendantToBeAdded.getNameJobStep());
                                descendantsInParentsAncestor.add(parentDescendant);
                            }else{
                                System.out.println("fend.job.job1.JobType2Controller.setView(): found a Descendant Entry existing for job "+parentsAncestor.getNameJobStep()+" and descendant "+descendantToBeAdded.getNameJobStep());
                                
                            }
                        }
                        
                        /*parentsAncestor.setDescendants(descendantsInParentsAncestor);
                        jobService.updateJob(parentsAncestor.getId(), parentsAncestor);*/
                    }
                    
    }
    
}