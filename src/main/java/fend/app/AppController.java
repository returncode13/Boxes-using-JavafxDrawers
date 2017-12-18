/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app;

import db.model.Workspace;
import db.services.WorkspaceService;
import db.services.WorkspaceServiceImpl;
import fend.app.loading.LoadWorkspaceModel;
import fend.app.loading.LoadWorkspaceNode;
import fend.workspace.WorkspaceModel;
import fend.workspace.WorkspaceView;
import fend.workspace.saveworkspace.SaveWorkSpaceView;
import fend.workspace.saveworkspace.SaveWorkspaceModel;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AppController extends Stage{
    private AppModel model;
    private AppView view;
    private WorkspaceService workspaceService=new WorkspaceServiceImpl();
    
     @FXML
    private MenuBar menubar;

    
    @FXML
    private MenuItem startWorkspace;

    @FXML
    private MenuItem saveCurrentWorkspace;

    @FXML
    private MenuItem saveSessionAs;

    @FXML
    private MenuItem loadWorkspace;


    @FXML
    private MenuItem exit;

    @FXML
    private MenuItem settings;

    @FXML
    private MenuItem dbsettings;

    @FXML
    private MenuItem idAbout;

    @FXML
    private Button bugReport;

    @FXML
    private StackPane basePane;

    @FXML
    void about(ActionEvent event) {

    }

    @FXML
    void dbsettings(ActionEvent event) {

    }

    @FXML
    void exitTheProgram(ActionEvent event) {

    }

    @FXML
    void handleBugReport(ActionEvent event) {

    }

    @FXML
    void loadSession(ActionEvent event) {
        List<Workspace> list=workspaceService.listWorkspaces();
        ObservableList<Workspace> observableList=FXCollections.observableArrayList(list);
        LoadWorkspaceModel lwm=new LoadWorkspaceModel(observableList);
        LoadWorkspaceNode lwn=new LoadWorkspaceNode(lwm);
        
        Workspace workspaceToBeLoaded=lwm.getWorkspaceToBeLoaded();
        if(workspaceToBeLoaded==null) return;
        WorkspaceModel frontEndWorkspaceModel=new WorkspaceModel();
        frontEndWorkspaceModel.setId(workspaceToBeLoaded.getId());
        frontEndWorkspaceModel.setName(workspaceToBeLoaded.getName());
        WorkspaceView frontEndWorkspaceView=new WorkspaceView(frontEndWorkspaceModel);
        frontEndWorkspaceView.getController().setLoading(true);
        basePane.getChildren().add(frontEndWorkspaceView);
    }

    @FXML
    void saveCurrentSession(ActionEvent event) {

    }

    @FXML
    void saveSessionAs(ActionEvent event) {

    }

    @FXML
    void settings(ActionEvent event) {

    }

    @FXML
    void startNewWorkspace(ActionEvent event) {
        Workspace dbWorkspace= new Workspace();
        final BooleanProperty nameEntered=new SimpleBooleanProperty(false);
         SaveWorkspaceModel sm=new SaveWorkspaceModel();
             Platform.runLater(new Runnable() {
                 @Override
                 public void run() {
                     SaveWorkSpaceView sv=new SaveWorkSpaceView(sm);
                 }
             });
             
             sm.getName().addListener((obs,old,newname)->{
                 System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): nameEntered: "+newname);
                 if(newname.length()>0)
                 dbWorkspace.setName(newname);
                 nameEntered.set(true);
             });
        
       nameEntered.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    System.out.println("fend.app.AppController.startNewWorkspace(): creating a new Workspace");
                    workspaceService.createWorkspace(dbWorkspace);
                    WorkspaceModel model=new WorkspaceModel();
                    model.setId(dbWorkspace.getId());
                    model.setName(dbWorkspace.getName());
                    WorkspaceView node=new WorkspaceView(model);
                    basePane.getChildren().add(node);
                }else{
                    return;
                }
            }
           
       });
        
    }
    
    
    
    
    
    
    void setModel(AppModel model) {
        
        this.model=model;
    }

    void setView(AppView view) {
        this.view=view;
         this.setTitle("MCVE for Dots");
         this.setScene(new Scene(view));
         this.initModality(Modality.APPLICATION_MODAL);
         this.showAndWait();
    }
    
}
