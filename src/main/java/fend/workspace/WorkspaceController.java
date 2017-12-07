/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.workspace;

import fend.edge.edge.EdgeView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import fend.job.job1.JobType1Model;
import fend.job.job1.JobType1View;
import fend.job.definitions.JobDefinitionsModel;
import fend.job.definitions.JobDefinitionsView;
import fend.job.definitions.volume.VolumeListModel;
import fend.job.definitions.volume.VolumeListView;
import fend.job.job0.JobType0Model;
import fend.job.job0.JobType0View;
import fend.workspace.saveworkspace.SaveWorkSpaceView;
import fend.workspace.saveworkspace.SaveWorkspaceModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkspaceController  {
    
    
    
    private WorkspaceModel model;
    private WorkspaceView node;
    
    
    List<BooleanProperty> changePropertyList=new ArrayList<>();
    
    @FXML
    private AnchorPane baseWindow;              //depth =0 
    
    
    @FXML
    private SplitPane splitpane;                  //depth =1 

    
    @FXML   
    private ScrollPane scrollpane;              //depth =2 
    @FXML
    private AnchorPane interactivePane;         //depth =3 
    
    @FXML
    private Button add;

    @FXML
    void addBox(ActionEvent event) {
        JobType1Model job=new JobType1Model(this.model);
        BooleanProperty changeProperty=new SimpleBooleanProperty(false);
        changeProperty.bind(job.getChangeProperty());
        changeProperty.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               
               
                    if(model.DEBUG)System.out.println("Saving session");
                    saveWorkspace();
                
            }

          
            
        });
        
        changePropertyList.add(changeProperty);
        JobType1View jobview=new JobType1View(job,interactivePane);
        interactivePane.getChildren().add(jobview);
        System.out.println("workspace.WorkspaceController.addBox(): "+job.getId()%100);
        
        
        
    }
    
    
    
    
    void setModel(WorkspaceModel item) {
        splitpane.prefWidthProperty().bind(baseWindow.widthProperty());
        splitpane.prefHeightProperty().bind(baseWindow.heightProperty());
        scrollpane.prefWidthProperty().bind(splitpane.widthProperty());
        scrollpane.prefHeightProperty().bind(splitpane.heightProperty());
        interactivePane.prefWidthProperty().bind(scrollpane.widthProperty());
        interactivePane.prefHeightProperty().bind(scrollpane.heightProperty());
        interactivePane.getChildren().addListener(jobLinkChangeListener);
        model=item;
        
        
      }

    void setView(WorkspaceView vq) {
        /*node=aThis;
        baseWindow.getChildren().add(node);*/
        node=vq;
    }
    
     private void saveWorkspace() {
         String name=model.getName().get();
         
        
        final BooleanProperty readyToSave=new SimpleBooleanProperty(false);
         if(model.getName().get().isEmpty()){
             SaveWorkspaceModel sm=new SaveWorkspaceModel();
             Platform.runLater(new Runnable() {
                 @Override
                 public void run() {
                     SaveWorkSpaceView sv=new SaveWorkSpaceView(sm);
                 }
             });
             
             sm.getName().addListener((obs,old,newname)->{
                 if(newname.length()>0)
                 model.setName(newname);
                 readyToSave.set(true);
             });
             
             
         }
         
         
         if(readyToSave.get()){
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace() Name: "+name);
             
             List<JobType0Model> jobsInWorkSpace=model.getObservableJobs();
             
             
             
             
             
             
             
             
             
             
          for(JobType0Model job:jobsInWorkSpace){
              System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): List of ancestors for job: "+job.getId()%1000);
              Set<JobType0Model> ancestors=(Set<JobType0Model>) job.getAncestors();
              for(JobType0Model ancestor:ancestors){
                  System.out.println(job.getId()%1000+" -A- "+ancestor.getId()%1000);
              }
              
               System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): List of descendants for job: "+job.getId()%1000);
              Set<JobType0Model> descendants=(Set<JobType0Model>) job.getDescendants();
              for(JobType0Model descendant:descendants){
                  System.out.println(job.getId()%1000+" -D- "+descendant.getId()%1000);
              }
          }
         }
         else{
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Workspace is missing a name. Unsaved!");
         }
         
          
          
      }
      
      ListChangeListener<Node> jobLinkChangeListener=new ListChangeListener<Node>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Node> c) {
            while(c.next()){
                for(Node node:c.getAddedSubList()){
                    if(node instanceof JobType0View){
                        System.out.println(".onChanged(): new job added to workspace: ");
                        model.getObservableJobs().add(((JobType0View) node).getController().getModel());
                    }
                    
                    if(node instanceof EdgeView){
                        System.out.println(".onChanged() new edge was added to the workspace");
                        model.getObservableEdges().add(((EdgeView) node).getController().getModel());
                    }
                }
            }
        }
    };
    
}
