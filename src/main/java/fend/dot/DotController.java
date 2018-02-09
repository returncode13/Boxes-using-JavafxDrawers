/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.dot;

import db.model.Dot;
import db.model.Job;
import db.model.Link;
import db.model.VariableArgument;
import db.model.Workspace;
import db.services.DotService;
import db.services.DotServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.LinkService;
import db.services.LinkServiceImpl;
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
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
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
   
    
    
    
    
    
    @FXML
    private Circle circle;
    
     
    void setModel(DotModel mod){
        model=mod;
        WorkspaceModel workspaceModel=model.getWorkspaceModel();
        dbWorkspace=workspaceService.getWorkspace(workspaceModel.getId());
            
            if(mod.getId()==null){                      //Commit the dot as soon as its created
            dbDot=new Dot();    
            dbDot.setWorkspace(dbWorkspace);
            dbDot.setStatus(model.getStatus().get());
            dotService.createDot(dbDot);
            model.setId(dbDot.getId());
            dbWorkspace.addToDots(dbDot);
            }
            else{
                dbDot=dotService.getDot(mod.getId());       //else get a reference to the existing dot
                dbDot.setStatus(model.getStatus().get());
                dotService.updateDot(dbDot.getId(), dbDot);    //update 
            }
            
            
            
            workspaceService.updateWorkspace(dbWorkspace.getId(), dbWorkspace);
            
            
       
        
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
                    
                    
                    parentConnectingToDot.addChild(childFromDot);
                    childFromDot.addParent(parentConnectingToDot);
                   // model.addToParents(parentConnectingToDot);
                    model.createLink(parentConnectingToDot,childFromDot);
                    parentModel.setDotModel(model);                      //Share this circle. 
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
               if(model.enableFurtherLinks()){
                   
                   FormulaFieldModel formulaModel=new FormulaFieldModel(model);
                   FormulaFieldView formulaFieldNode=new FormulaFieldView(formulaModel);
               }else{
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
         model.getTolerance().addListener(toleranceChangeListener);
         model.getError().addListener(errorChangeListener);
    }

    
    private void updateColor() {
        System.out.println("fend.dot.DotController.updateColor(): model status is : "+model.getStatus().get());
            if(model.getStatus().get().equals(DotModel.NJS))node.setFill(Color.DIMGRAY);
            if(model.getStatus().get().equals(DotModel.JOIN))node.setFill(Color.DARKORCHID);
            if(model.getStatus().get().equals(DotModel.SPLIT))node.setFill(Color.NAVY);
    }
    
     private void deleteNodeAndLinks() {
         /*   Set<JobType0Model> parents=model.getParents();
         Set<JobType0Model> children=model.getChildren();*/
            
    }
     
    private void updateDatabaseAndFormulaFieldinModel() {
        
        System.out.println("fend.dot.DotController.updateDatabaseAndFormulaFieldinModel(): updating the dot. deleting old links..creating new ones. ");
       
              dbDot=dotService.getDot(model.getId());
              dbDot.setStatus(model.getStatus().get());
         
          Set<LinkModel> linksSharingThisDot=model.getLinks();
          Set<Link> dbLinks=new HashSet<>();
          for(LinkModel lm:linksSharingThisDot){
             
                    
                     Job child=jobService.getJob(lm.getChild().getId());
                     Job parent=jobService.getJob(lm.getParent().getId());
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
          dbDot.setLinks(dbLinks);
          
          for(Link l:dbLinks){
              
              linkService.createLink(l);
          }
          dotService.updateDot(dbDot.getId(), dbDot);
          
          
          /**
           * Clear all the variableargument entries existing in the database belonging to this dot.
           * 
           **/
          Set<VariableArgument> variableArguments=dbDot.getVariableArguments();
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
                Job dbChild=jobService.getJob(childj.getId());
                Job dbParent=jobService.getJob(parentj.getId());
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
                Job dbChild=jobService.getJob(childj.getId());
                Job dbParent=jobService.getJob(parentj.getId());
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
                Job dbChild=jobService.getJob(childj.getId());
                Job dbParent=jobService.getJob(parentj.getId());
                observableLhsArgs.add(dbParent);
                observableRhsArgs.add(dbChild);
            }
        }
        
        Integer lhsIndex=0;
        String lhsVariable="y";
        for(Job lhs:observableLhsArgs){ //of size 1 
           String lhsVariableM=lhsVariable+lhsIndex.toString();    //y0,y1...yn
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
           dbVariableArguments=dbDot.getVariableArguments();
          setVariableArgumentMap();                        
          for (Map.Entry<String, Job> entry : variableArgumentMap.entrySet()) {
                    String variable = entry.getKey();
                    Job arg = entry.getValue();
                    VariableArgument va=new VariableArgument();
                    va.setVariable(variable);
                    va.setArgument(arg);
                    va.setDot(dbDot);
                    variableArgumentService.createVariableArgument(va);
                    dbVariableArguments.add(va);
                }
          
          dbDot.setVariableArguments(dbVariableArguments);
          dotService.updateDot(dbDot.getId(), dbDot);
          System.out.println("fend.dot.DotController.setupModelFormulaField(): setting a new formula field to the dot"); 
        
    }
            
            
    
    
    private class Delta{
        double x,y;
    }
   
    
   final private ChangeListener<String> functionChangeListener=new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if(model.getId()!=null){
                dbDot=dotService.getDot(model.getId());
                dbDot.setFunction(newValue);
                dotService.updateDot(dbDot.getId(), dbDot);
            }
        }
    };
   
   
   final private ChangeListener<Number> toleranceChangeListener=new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
             if(model.getId()!=null){
                dbDot=dotService.getDot(model.getId());
                dbDot.setTolerance((Double) newValue);
                dotService.updateDot(dbDot.getId(), dbDot);
            }
        }
    };
   
   final private ChangeListener<Number> errorChangeListener=new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
             if(model.getId()!=null){
                dbDot=dotService.getDot(model.getId());
                dbDot.setError((Double) newValue);
                dotService.updateDot(dbDot.getId(), dbDot);
            }
        }
    };

   
}
