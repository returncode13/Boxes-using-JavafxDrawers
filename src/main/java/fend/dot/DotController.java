/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.dot;

import app.properties.AppProperties;
import db.model.Ancestor;
import db.model.Descendant;
import db.model.Dot;
import db.model.Job;
import db.model.Link;
import db.model.SubsurfaceJob;
import db.model.VariableArgument;
import db.model.Workspace;
import db.services.AncestorService;
import db.services.AncestorServiceImpl;
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
import db.services.SubsurfaceJobService;
import db.services.SubsurfaceJobServiceImpl;
import db.services.VariableArgumentService;
import db.services.VariableArgumentServiceImpl;
import db.services.WorkspaceService;
import db.services.WorkspaceServiceImpl;
import fend.dot.anchor.AnchorView;
import fend.job.job0.JobType0Model;
import fend.dot.formulaField.FormulaFieldModel;
import fend.dot.formulaField.FormulaFieldView;
import fend.dot.variableArgument.VariableArgumentModel;
import fend.edge.dotjobedge.DotJobEdgeModel;
import fend.edge.dotjobedge.DotJobEdgeView;
import fend.edge.parentchildedge.ParentChildEdgeModel;
import fend.edge.parentchildedge.ParentChildEdgeView;
import fend.workspace.WorkspaceModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.concurrent.Task;
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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DotController extends Stage{
    private DotModel model;
    private DotView node;
    private Dot dbDot;;
    private AnchorPane interactivePane;
    private DotService dotService=new DotServiceImpl();
    private WorkspaceService workspaceService=new WorkspaceServiceImpl();
    private LinkService linkService=new LinkServiceImpl();
    private JobService jobService=new JobServiceImpl();
    private VariableArgumentService variableArgumentService=new VariableArgumentServiceImpl();
    final Delta dragDelta=new Delta();
    private Workspace dbWorkspace;
    private final ContextMenu menu=new ContextMenu();
    private Map<String,Job> variableArgumentMap;
    private ObservableSet<Job> observableLhsArgs;
    private ObservableSet<Job> observableRhsArgs;
    private Set<VariableArgument> dbVariableArguments;
    private AncestorService ancestorService=new AncestorServiceImpl();
    private DescendantService descendantService=new DescendantServiceImpl();
    private SubsurfaceJobService subsurfaceJobService=new SubsurfaceJobServiceImpl();
    
    
    
    
    @FXML
    private Circle circle;
    
     
    void setModel(DotModel mod){
        model=mod;
        WorkspaceModel workspaceModel=model.getWorkspaceModel();
        //dbWorkspace=workspaceService.getWorkspace(workspaceModel.getId());
        dbWorkspace=workspaceModel.getWorkspace();
        
            String creationTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
            if(mod.getId()==null){                      //Commit the dot as soon as its created
            dbDot=new Dot();    
            dbDot.setWorkspace(dbWorkspace);
            dbDot.setStatus(model.getStatus().get());
            dbDot.setCreationTime(creationTime);
            dotService.createDot(dbDot);
            model.setId(dbDot.getId());
            model.setDatabaseDot(dbDot);
          //  dbWorkspace.addToDots(dbDot);
            }
            else{
                /*dbDot=dotService.getDot(mod.getId());       //else get a reference to the existing dot
                dbDot.setStatus(model.getStatus().get());
                dotService.updateDot(dbDot.getId(), dbDot);    //update */
                dbDot=model.getDatabaseDot();
            }
            
          
            
         //   workspaceService.updateWorkspace(dbWorkspace.getId(), dbWorkspace);//Commented for troubleshooting. Positively to be uncommented.            
           
    //   model.dotClickedProperty().addListener(DOT_CLICKED_LISTENER);
       model.exitedFormulaFieldProperty().addListener(FORMULA_FIELD_EXITED);
        model.getDelete().addListener(DOT_DELETE_LISTENER);
        
        /*model.getDelete().addListener(new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if(newValue){
        
        deleteDotAndLinks();
        }
        }
        
        
        });*/
        
        
        
        
        exec=Executors.newCachedThreadPool(runnable->{
          Thread t=new Thread(runnable);
          t.setDaemon(true);
          return t;
      });
        
    }
    
    private Executor exec;
    
    public DotModel getModel(){
        return model;
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
           AnchorView droppedAnchor=(AnchorView) e.getGestureSource();
            if(droppedAnchor.getParent() instanceof ParentChildEdgeView){
            System.out.println("dot.DotController.setView() NJS: "+model.isNjs()+" Join: "+model.isJoin()+" Split: "+model.isSplit()+" Enabled: "+model.enableFurtherLinks());
            if(model.enableFurtherLinks() && (model.isNjs()||model.isJoin())){
               
               
               
                
                if(droppedAnchor.getParent() instanceof ParentChildEdgeView){
                    ParentChildEdgeView parentNode=((ParentChildEdgeView)droppedAnchor.getParent());
                    
                    ParentChildEdgeModel parentModel=parentNode.getController().getModel();
                    
                    JobType0Model childFromDot=(new ArrayList<>(model.getLinks())).get(0).getChild();    //the ONLY child associated with the circle model
                    parentModel.setChildJob(childFromDot);
                    
                    JobType0Model parentConnectingToDot=parentModel.getParentJob();   //get the parent job connecting to this Dot
                    
                    setupAncestorsAndDescendants(parentConnectingToDot, childFromDot);
                    parentConnectingToDot.addChild(childFromDot);
                    childFromDot.addParent(parentConnectingToDot);
                   // model.addToParents(parentConnectingToDot);
                    model.createLink(parentConnectingToDot,childFromDot);
                    parentModel.setDotModel(model);                      //Share this circle. 
                    parentNode.setDropReceived(true);
                    parentModel.setDropSuccessFul(true);
                    droppedAnchor.centerXProperty().bind(node.centerXProperty());
                    droppedAnchor.centerYProperty().bind(node.centerYProperty());
                    childFromDot.toggleDepthChange();
                    subsurfaceJobService.updateTimeWhereJobEquals(childFromDot.getDatabaseJob(), now());
                }
            }else{
                System.out.println("dot.DotController.setView(): Join Operation disallowed");
            }
            
            
        }
        });
        
        
        
        node.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (e.getClickCount() == 2) {
                    if (model.enableFurtherLinks()) {
                                        if(formulaModel == null){
                                          formulaModel=new FormulaFieldModel(model);
                                          formulaFieldNode=new FormulaFieldView(formulaModel);
                                        }else{
                                          //formulaFieldNode.requestFocus();
                                        }
                    } else {
                        System.out.println("fend.dot.DotController.setView(): Nope! this dot has its enableFutherLinks param set to false. Do links exist?");
                    }

                }
            }
        });
        
        
        
        
        
        
         model.getLinkWasCreated().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               System.out.println("dot.DotController.addListener.modelstatus: Link was created in the model.. changed from "+oldValue+" to "+newValue);
               updateColor();
               updateDatabaseAndFormulaFieldinModel(); 
            }

           
        });
         
          
         
         model.toggleLinkWasCreated();          //force a toggle during load
         
         model.getFunction().addListener(functionChangeListener);
         model.warnUserProperty().addListener(FUNCTION_IS_NOT_DEFINED_LISTENER);
         model.getTolerance().addListener(toleranceChangeListener);
         model.getError().addListener(errorChangeListener);
         
         
         
          if(dbDot.getFunction()==null || dbDot.getFunction().isEmpty()){
                model.warnUser();
            }
    }
    
    private FormulaFieldModel formulaModel=null;
    private FormulaFieldView formulaFieldNode;

    /**
     * UNUSED method. kept because it still exists in the fxml file
     */
     @FXML
    void dotClicked(MouseEvent e) {
        /*if(e.getButton().equals(MouseButton.PRIMARY)){
        if(e.getClickCount()==2){
        if(model.enableFurtherLinks()){
        if(formulaModel == null){
        formulaModel=new FormulaFieldModel(model);
        formulaFieldNode=new FormulaFieldView(formulaModel);
        }else{
        formulaFieldNode.requestFocus();
        }
        
        }else{
        System.out.println("fend.dot.DotController.setView(): Nope! this dot has its enableFutherLinks param set to false. Do links exist?");
        }
        
        }
        }*/
    }

    @FXML
    void onContextMenuRequested(ContextMenuEvent event) {
            menu.show(node, event.getScreenX(), event.getScreenY());
    }

    
    
    
    private void updateColor() {
        System.out.println("fend.dot.DotController.updateColor(): model status is : "+model.getStatus().get());
     
            if(model.getStatus().get().equals(DotModel.NJS))node.setFill(Color.DIMGRAY);
            if(model.getStatus().get().equals(DotModel.JOIN))node.setFill(Color.DARKORCHID);
            if(model.getStatus().get().equals(DotModel.SPLIT))node.setFill(Color.NAVY);
            
               
    }
    
    
    private void deleteDotAndLinks() {
         /*   Set<JobType0Model> parents=model.getParents();
         Set<JobType0Model> children=model.getChildren();*/
         System.out.println("fend.dot.DotController.deleteDotAndLinks(): calling block on workspace: "+model.getWorkspaceModel().getName().get()+" ("+model.getWorkspaceModel().getId()+")");
        // model.getWorkspaceModel().block();
         deleteDoubtsRelatedToThisDot();
         List<Link> linksBelongingToDot=linkService.getLinksForDot(dbDot);
         System.out.println("fend.dot.DotController.deleteNodeAndLinks(): deleting  "+linksBelongingToDot.size()+" link(s) belonging to the dot: "+dbDot.getId());
         for(Link l:linksBelongingToDot){
             doubtService.deleteAllDoubtsRelatedTo(l);
             subsurfaceJobService.updateTimeWhereJobEquals(l.getParent(), now());
             subsurfaceJobService.updateTimeWhereJobEquals(l.getChild(), now());
             linkService.deleteLink(l.getId());
         }
         System.out.println("fend.dot.DotController.deleteNodeAndLinks(): deleting variable and arguments related to the dot "+dbDot.getId());
         variableArgumentService.deleteVariableArgumentFor(dbDot);
         System.out.println("fend.dot.DotController.deleteNodeAndLinks(): deleting the dot: "+dbDot.getId());
         dotService.deleteDot(dbDot.getId());
         
        
         System.out.println("fend.dot.DotController.deleteDotAndLinks(): calling unblock on workspace: "+model.getWorkspaceModel().getName().get()+" ("+model.getWorkspaceModel().getId()+")");
        // model.getWorkspaceModel().unblock();
         
            
    }
     
    private DoubtService doubtService=new DoubtServiceImpl();
    
    private void deleteDoubtsRelatedToThisDot(){
        
        doubtService.deleteAllDoubtsRelatedTo(dbDot);
         
    }
     
    private void updateDatabaseAndFormulaFieldinModel() {
        
        System.out.println("fend.dot.DotController.updateDatabaseAndFormulaFieldinModel(): updating the dot.");
       
         //     dbDot=dotService.getDot(model.getId());
              dbDot.setStatus(model.getStatus().get());
         
          Set<LinkModel> linksSharingThisDot=model.getLinks();
          Set<Link> dbLinks=new HashSet<>();
          for(LinkModel lm:linksSharingThisDot){
             
                    
                     //Job child=jobService.getJob(lm.getChild().getId());
                     Job child=lm.getChild().getDatabaseJob();
                     //Job parent=jobService.getJob(lm.getParent().getId());
                     Job parent=lm.getParent().getDatabaseJob();
                     List<Link> linksContainingParentChildThroughDot=linkService.getLinkBetweenParentAndChild(parent, child, dbDot);
                      //linkService.clearLinksforJob(child,dbDot);                 //rebuild each save
                      //linkService.clearLinksforJob(parent,dbDot);                //rebuild each save     
                     
                      if(linksContainingParentChildThroughDot.isEmpty()){
                            Link dbLink=new Link();
                            dbLink.setParent(parent);
                            dbLink.setChild(child);
                            dbLink.setDot(dbDot);
                            dbLinks.add(dbLink);
                      }
                      
                      
                      
          }
         // dbDot.setLinks(dbLinks);
          String creationTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
          for(Link l:dbLinks){
              l.setCreationTime(creationTime);
              linkService.createLink(l);
          }
          //dotService.updateDot(dbDot.getId(), dbDot);
          dotService.updateStatus(model.getId(),model.getStatus().get());
          model.setDatabaseDot(dbDot);
          
          
          /**
           * Clear all the variableargument entries existing in the database belonging to this dot.
           * 
           **/
          //Set<VariableArgument> variableArguments=dbDot.getVariableArguments();
          
          Set<VariableArgument> variableArguments=new HashSet<>(variableArgumentService.getVariableArgumentsForDot(dbDot));
          
          
          for(VariableArgument va:variableArguments){
              variableArgumentService.deleteVariableArgument(va.getId());
          }
          
          
          /**
           * ready the models formula field
           */
          updateVariableArgumentsInDB();   
          
          
    }

    
    
    
    
     private void setVariableArgumentMap() {
        System.out.println("fend.dot.DotController.setVariableArgumentMap(): building the variableArgumentMap");
        variableArgumentMap.clear();
        Set<LinkModel> feLinks=model.getLinks();
        String state=model.getStatus().get();
        
        if(state.equals(DotModel.NJS)){         //lhs=parent.; rhs=child
            for(LinkModel felink:feLinks){
                JobType0Model parentj=felink.getParent();
                JobType0Model childj=felink.getChild();
                //Job dbChild=jobService.getJob(childj.getId());
                Job dbChild=childj.getDatabaseJob();
                //Job dbParent=jobService.getJob(parentj.getId());
                Job dbParent=parentj.getDatabaseJob();
                observableLhsArgs.add(dbParent);
                observableRhsArgs.add(dbChild);
            }
            
        }
        if(state.equals(DotModel.JOIN)){        //lhs=child; rhs=parents
             observableLhsArgs.clear();
            observableRhsArgs.clear();
            for(LinkModel felink:feLinks){
                JobType0Model parentj=felink.getParent();
                JobType0Model childj=felink.getChild();
                //Job dbChild=jobService.getJob(childj.getId());
                Job dbChild=childj.getDatabaseJob();
                //Job dbParent=jobService.getJob(parentj.getId());
                Job dbParent=parentj.getDatabaseJob();
                observableLhsArgs.add(dbChild);
                observableRhsArgs.add(dbParent);
            }
            
        }
        if(state.equals(DotModel.SPLIT)){       //lhs=parent; rhs=children
            observableLhsArgs.clear();
            observableRhsArgs.clear();
            for(LinkModel felink:feLinks){
                JobType0Model parentj=felink.getParent();
                JobType0Model childj=felink.getChild();
                //Job dbChild=jobService.getJob(childj.getId());
                Job dbChild=childj.getDatabaseJob();
                //Job dbParent=jobService.getJob(parentj.getId());
                Job dbParent=parentj.getDatabaseJob();
                observableLhsArgs.add(dbParent);
                observableRhsArgs.add(dbChild);
            }
        }
        
        Integer lhsIndex=0;
        String lhsVariable="y";
        for(Job lhs:observableLhsArgs){ //of size 1 
           String lhsVariableM=lhsVariable;//+lhsIndex.toString();    //y0,y1...yn   . May 25th disabled terms like y0. keeping just the term 'y'
            System.out.println("LHS "+lhsVariableM+" = "+lhs.getNameJobStep());
           variableArgumentMap.put(lhsVariableM, lhs);
           lhsIndex++;
        }
        
        Integer rhsIndex=0;
        String rhsVariable="x";
        for(Job rhs:observableRhsArgs){  //size >=1
            String rhsVariableM=rhsVariable+rhsIndex.toString();  //x0,x1,x2...,xm
            System.out.println("RHS "+rhsVariableM+" = "+rhs.getNameJobStep());
            variableArgumentMap.put(rhsVariableM, rhs);
            rhsIndex++;
        }
    }

    private void updateVariableArgumentsInDB() {
        System.out.println("fend.dot.DotController.setupModelFormulaField(): creating a new formula field");
        /**
           * Set up new variableArguments based on the new connections to the dot.
           * 
           */
          
          
          variableArgumentMap=model.getVariableArgumentModel().getVariableArgumentMap();
          observableLhsArgs=model.getVariableArgumentModel().getObservableLhsArgs();
          observableRhsArgs=model.getVariableArgumentModel().getObservableRhsArgs();
          // dbVariableArguments=dbDot.getVariableArguments();
          dbVariableArguments=new HashSet<>(variableArgumentService.getVariableArgumentsForDot(dbDot));
          setVariableArgumentMap();                        
          for (Map.Entry<String, Job> entry : variableArgumentMap.entrySet()) {
                    String variable = entry.getKey();
                    Job arg = entry.getValue();
                    VariableArgument va=new VariableArgument();
                    va.setVariable(variable);
                    va.setArgument(arg);
                    va.setDot(dbDot);
                    variableArgumentService.createVariableArgument(va);
                  //  dbVariableArguments.add(va);
                }
          
        //  dbDot.setVariableArguments(dbVariableArguments);
          dotService.updateDot(dbDot.getId(), dbDot);
          model.setDatabaseDot(dbDot);
          System.out.println("fend.dot.DotController.setupModelFormulaField(): setting a new formula field to the dot"); 
        
    }
            
            
    
    
    private class Delta{
        double x,y;
    }
   
    
   final private ChangeListener<String> functionChangeListener=new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if(model.getId()!=null){
                /* dbDot=dotService.getDot(model.getId());
                
                dotService.updateDot(dbDot.getId(), dbDot);*/
                dbDot.setFunction(newValue);
                //dotService.updateFunction(dbDot);
                dotService.updateDot(dbDot.getId(), dbDot);
                model.setDatabaseDot(dbDot);
  /*
                Update the times for all the jobs connected by this dot
*/                
                //Set<Link> dbLinks=dbDot.getLinks();
                Set<Link> dbLinks=new HashSet<>(linkService.getLinksForDot(dbDot));
                String updateTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
                for(Link dbLink:dbLinks){
                    subsurfaceJobService.updateTimeWhereJobEquals(dbLink.getParent(),updateTime);
                    subsurfaceJobService.updateTimeWhereJobEquals(dbLink.getChild(), updateTime);
                }
            }
        }
    };
   
   
   final private ChangeListener<Number> toleranceChangeListener=new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
             if(model.getId()!=null){
                 /*dbDot=dotService.getDot(model.getId());
                 
                 dotService.updateDot(dbDot.getId(), dbDot);*/
                dbDot.setTolerance((Double) newValue);
                //dotService.updateTolerance(dbDot);
                dotService.updateDot(dbDot.getId(), dbDot);
                model.setDatabaseDot(dbDot);
                /*
                Update the times for all the jobs connected by this dot
*/                
                 //Set<Link> dbLinks=dbDot.getLinks();
                Set<Link> dbLinks=new HashSet<>(linkService.getLinksForDot(dbDot));
                String updateTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
                for(Link dbLink:dbLinks){
                    System.out.println(".changed(): updating times on subsurface job entries: ");
                    subsurfaceJobService.updateTimeWhereJobEquals(dbLink.getParent(),updateTime);
                    subsurfaceJobService.updateTimeWhereJobEquals(dbLink.getChild(), updateTime);
                }
            }
        }
    };
   
   final private ChangeListener<Number> errorChangeListener=new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
             if(model.getId()!=null){
                 /*dbDot=dotService.getDot(model.getId());
                 
                 dotService.updateDot(dbDot.getId(), dbDot);*/
                dbDot.setError((Double) newValue);
                //dotService.updateError(dbDot);
                dotService.updateDot(dbDot.getId(), dbDot);
                model.setDatabaseDot(dbDot);
                 /*
                Update the times for all the jobs connected by this dot
*/                
                 //Set<Link> dbLinks=dbDot.getLinks();
                Set<Link> dbLinks=new HashSet<>(linkService.getLinksForDot(dbDot));
                String updateTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
                for(Link dbLink:dbLinks){
                    subsurfaceJobService.updateTimeWhereJobEquals(dbLink.getParent(),updateTime);
                    subsurfaceJobService.updateTimeWhereJobEquals(dbLink.getChild(), updateTime);
                }
                
            }
        }
    };

   
   
   
   
   //setting up ancestors and descendants
  /**
     * Function called for a Join operation.
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
   
    private void setupAncestorsAndDescendants(JobType0Model parent,JobType0Model child) {
                    //Job  dbjob=jobService.getJob(child.getId());
                     Job  dbjob=child.getDatabaseJob();
                   // Job dbParent=jobService.getJob(parent.getId());
                   Job dbParent=parent.getDatabaseJob();
                    
                    /*Set<Ancestor>   Ap=dbParent.getAncestors();
                    Set<Ancestor>   Ac=dbjob.getAncestors();
                    Set<Descendant> Dp=dbParent.getDescendants();
                    Set<Descendant> Dc=dbjob.getDescendants();*/
                    
                    Set<Ancestor> Ap=new LinkedHashSet<>(ancestorService.getAncestorFor(dbParent));
                    Set<Ancestor> Ac=new LinkedHashSet<>(ancestorService.getAncestorFor(dbjob));
                    Set<Descendant> Dp=new LinkedHashSet<>(descendantService.getDescendantsFor(dbParent));
                    Set<Descendant> Dc=new LinkedHashSet<>(descendantService.getDescendantsFor(dbjob));
                    System.out.println("fend.dot.DotController.setupAncestorsAndDescendants() Size of The parent job "+ dbParent.getId()+" "+dbParent.getNameJobStep() + "  Ancestors: "+Ap.size());
                    System.out.println("fend.dot.DotController.setupAncestorsAndDescendants() Size of The child job "+ dbjob.getId()+" "+dbjob.getNameJobStep() + "  Ancestors: "+Ac.size());
                    System.out.println("fend.dot.DotController.setupAncestorsAndDescendants() Size of The parent job "+ dbParent.getId()+" "+dbParent.getNameJobStep() + "  Descendants: "+Dp.size());
                    System.out.println("fend.dot.DotController.setupAncestorsAndDescendants() Size of The childs job "+ dbjob.getId()+" "+dbjob.getNameJobStep() + "  Descendants: "+Dc.size());
                    
                    
                    Ancestor parentIsAnAncestor;
                    if((parentIsAnAncestor=ancestorService.getAncestorFor(dbjob, dbParent))==null){
                        parentIsAnAncestor=new Ancestor();
                        parentIsAnAncestor.setJob(dbjob);
                        parentIsAnAncestor.setAncestor(dbParent);

                        ancestorService.addAncestor(parentIsAnAncestor);
                        System.out.println("fend.dot.DotController.setupAncestorsAndDescendants() Created an new Ancestor for job "+dbjob.getNameJobStep()+" and ancestor  "+dbParent.getNameJobStep());
                        Ac.add(parentIsAnAncestor);
                        System.out.println("fend.dot.DotController.setupAncestorsAndDescendants() Added ");
                    }else{
                         System.out.println("fend.dot.DotController.setupAncestorsAndDescendants() Found an  Ancestor Entry existing for job "+dbjob.getNameJobStep()+" and ancestor  "+dbParent.getNameJobStep());
                            
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
                            System.out.println("fend.dot.DotController.setupAncestorsAndDescendants() creating new Ancestor for job "+dbjob.getNameJobStep()+" new Ancestor added: "+ancestorToBeAdded.getNameJobStep());
                            Ac.add(jobAncestor);
                        }else{
                            System.out.println("fend.dot.DotController.setupAncestorsAndDescendants() Found an  Ancestor Entry existing for job "+dbjob.getNameJobStep()+" and ancestor "+ancestorToBeAdded.getNameJobStep());
                            
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
                         System.out.println("fend.dot.DotController.setupAncestorsAndDescendants() creating new Descendant for job "+dbParent.getNameJobStep()+" new Descendant added: "+dbjob.getNameJobStep());
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
                            System.out.println("fend.dot.DotController.setupAncestorsAndDescendants(): creating new Descendant for job "+dbParent.getNameJobStep()+" new Descendant added: "+dbjob.getNameJobStep());
                            Dp.add(currentJobsDescendant);              //add current jobs descendant to the list of parents descendants
                        }
                    //   jobService.updateJob(descendantToBeAdded.getId(), descendantToBeAdded);
                    }
                    /* dbParent.setDescendants(Dp);
                    jobService.updateJob(dbParent.getId(), dbParent);*/
                    
                    
                    
                    
                    //update the ancestor list for the jobs descendants
                    for(Descendant jobsDescendantEntry:Dc){
                        Job jobsDescendant=jobsDescendantEntry.getDescendant();              //jobs descendant
                        //Set<Ancestor> ancestorsInJobsDescendant=jobsDescendant.getAncestors();
                        Set<Ancestor> ancestorsInJobsDescendant=new HashSet<>(ancestorService.getAncestorFor(dbjob));
                            for(Ancestor anc:Ac){
                                Job ancestorJobToBeAdded=anc.getAncestor();        //this ancestor is the current jobs ancestor which is now been added to its descendant
                                Ancestor jobAncestor;
                                if((jobAncestor=ancestorService.getAncestorFor(jobsDescendant, ancestorJobToBeAdded))==null){
                                    
                                    jobAncestor=new Ancestor();
                                    jobAncestor.setJob(jobsDescendant);
                                    jobAncestor.setAncestor(ancestorJobToBeAdded);
                                    ancestorService.addAncestor(jobAncestor);
                                    System.out.println("fend.dot.DotController.setupAncestorsAndDescendants() creating new Ancestor for job "+jobsDescendant.getNameJobStep()+" new Ancestor added: "+ancestorJobToBeAdded.getNameJobStep());
                                    ancestorsInJobsDescendant.add(jobAncestor);
                                }else{
                                     System.out.println("fend.dot.DotController.setupAncestorsAndDescendants() Found an  Ancestor Entry existing for job "+jobsDescendant.getNameJobStep()+" and ancestor "+ancestorJobToBeAdded.getNameJobStep());
                                }
                            }
                            
                            /* jobsDescendant.setAncestors(ancestorsInJobsDescendant);
                            
                            jobService.updateJob(jobsDescendant.getId(), jobsDescendant);*/
                    }
                            
                    //System.out.println("fend.dot.DotController.setupAncestorsAndDescendants()");
                    
                    //update the descendant list for the parents ancestors
                    for(Ancestor parentAncestorEntry:Ap){
                        Job parentsAncestor=parentAncestorEntry.getAncestor();  //parents Ancestor
                      //  Set<Descendant> descendantsInParentsAncestor=parentsAncestor.getDescendants();
                        Set<Descendant> descendantsInParentsAncestor=new HashSet<>(descendantService.getDescendantsFor(parentsAncestor));
                        for(Descendant desc:Dp){                    //add all of the parents descendants to the parents ancestors
                            Job descendantToBeAdded=desc.getDescendant();       //descendant to be added to the parents ancestor's list of descendants
                            Descendant parentDescendant;
                            
                            if((parentDescendant=descendantService.getDescendantFor(parentsAncestor,descendantToBeAdded))==null){
                                parentDescendant=new Descendant();
                                parentDescendant.setJob(parentsAncestor);
                                parentDescendant.setDescendant(descendantToBeAdded);
                                descendantService.addDescendant(parentDescendant);
                                System.out.println("fend.dot.DotController.setupAncestorsAndDescendants() creating new Descendant for job "+parentsAncestor.getNameJobStep()+" new Descendant added: "+descendantToBeAdded.getNameJobStep());
                                descendantsInParentsAncestor.add(parentDescendant);
                            }else{
                                System.out.println("fend.dot.DotController.setupAncestorsAndDescendants() found a Descendant Entry existing for job "+parentsAncestor.getNameJobStep()+" and descendant "+descendantToBeAdded.getNameJobStep());
                                
                            }
                        }
                        
                        /*parentsAncestor.setDescendants(descendantsInParentsAncestor);
                        jobService.updateJob(parentsAncestor.getId(), parentsAncestor);*/
                    }
                    parent.toggleUpdateProperty();
                    child.toggleUpdateProperty();
    }
    
    /*private ChangeListener<Boolean> DOT_CLICKED_LISTENER=new ChangeListener<Boolean>() {
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    if(model.enableFurtherLinks()){
    
    FormulaFieldModel formulaModel=new FormulaFieldModel(model);
    FormulaFieldView formulaFieldNode=new FormulaFieldView(formulaModel);
    }else{
    System.out.println("fend.dot.DotController.setView(): Nope! this dot has its enableFutherLinks param set to false. Do links exist?");
    }
    }
    };*/
    
    private ChangeListener<Boolean> FORMULA_FIELD_EXITED=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            formulaModel=null;
            formulaFieldNode=null;
        }
    };
    
    private String now() {
        return DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
    }
    
    private ChangeListener<Boolean> DOT_DELETE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                model.getWorkspaceModel().block();
                Task<Void> deleteTask=new Task<Void>(){
                    @Override
                    protected Void call() throws Exception {
                        deleteDotAndLinks();
                        return null;
                    }
                    
                };
                
                deleteTask.setOnRunning(e->{System.out.println("deleting dot..");});
                deleteTask.setOnSucceeded(e->{
                     model.getWorkspaceModel().reload();
                        model.getWorkspaceModel().unblock();
                });
                
                exec.execute(deleteTask);
            }
        }
    };
 
    
    
    
    
     private ChangeListener<Boolean> FUNCTION_IS_NOT_DEFINED_LISTENER=new ChangeListener<Boolean>(){
       

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                node.setFill(Color.RED);
                model.getWorkspaceModel().blockSummary();
                
            }else{
                updateColor();
                model.getWorkspaceModel().unBlockSummary();
                
            }
        }
       
     };
    
    
    
    
}
