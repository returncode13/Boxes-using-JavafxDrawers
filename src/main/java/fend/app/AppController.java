/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app;

import app.properties.AppProperties;
import db.model.User;
import db.model.Workspace;
import db.services.UserService;
import db.services.UserServiceImpl;
import db.services.WorkspaceService;
import db.services.WorkspaceServiceImpl;
import fend.app.loading.LoadWorkspaceModel;
import fend.app.loading.LoadWorkspaceNode;
import fend.user.UserModel;
import fend.user.UserView;
import fend.workspace.WorkspaceModel;
import fend.workspace.WorkspaceView;
import fend.workspace.saveworkspace.SaveWorkSpaceView;
import fend.workspace.saveworkspace.SaveWorkspaceModel;
import java.util.ArrayList;
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
    private UserService userService=new UserServiceImpl();
    private Workspace currentWorkspace=null;
    private User currentUser=null;
    
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
    private Button userBtn;
    
    
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
    void loginAsUser(ActionEvent event) {
        changeInWorkspaceOrUser();  //take care of exiting user
        logUser();
        
        if(currentWorkspace!=null){
            currentWorkspace.addToUsers(currentUser);
            currentUser.addToWorkspaces(currentWorkspace);
            if(currentWorkspace.getOwner()==null){
                currentWorkspace.setOwner(currentUser);
                currentUser.addToOwnedWorkspaces(currentWorkspace);
            }
            
            workspaceService.updateWorkspace(currentWorkspace.getId(), currentWorkspace);
//            userService.updateUser(currentUser.getUser_id(), currentUser);
        }
    }
    
    @FXML
    void loadSession(ActionEvent event) {
        if(currentUser==null){
            logUser();
            return;
        }
        
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
        
        
        if(currentWorkspace!=null)changeInWorkspaceOrUser();              //take care of the exiting workspace
        
        currentWorkspace=workspaceToBeLoaded;
       // if(currentWorkspace!=null){
            if(currentWorkspace.getOwner()==null){
                currentWorkspace.setOwner(currentUser);
                currentWorkspace.addToUsers(currentUser);
                currentUser.addToOwnedWorkspaces(currentWorkspace);
                currentUser.addToWorkspaces(currentWorkspace);
                
                workspaceService.updateWorkspace(currentWorkspace.getId(), currentWorkspace);
                userService.updateUser(currentUser.getUser_id(), currentUser);
            }else{
                if(currentUser==currentWorkspace.getOwner()){
                    System.out.println("fend.app.AppController.loadSession(): User : "+currentUser.getInitials()+" is already logged into as owner to workspace: "+currentWorkspace.getName());
                    return;
                }
                else{
                    currentWorkspace.addToUsers(currentUser);
                    currentUser.addToWorkspaces(currentWorkspace);
                    workspaceService.updateWorkspace(currentWorkspace.getId(), currentWorkspace);
                    userService.updateUser(currentUser.getUser_id(), currentUser);
                    
                    
                    //Restrictions pending
                }
            }
       // }
        
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
        
        if(currentUser==null){
            logUser();
            return;
        }
        
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
                 System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): owner: "+currentUser.getInitials());
                 if(newname.length()>0){
                     
                 
                 dbWorkspace.setName(newname);
                 dbWorkspace.setOwner(AppController.this.currentUser);
                 dbWorkspace.addToUsers(currentUser);
                 
                 
                 nameEntered.set(true);
                 }
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
                    
                    
                    currentUser.addToOwnedWorkspaces(dbWorkspace);
                    currentUser.addToWorkspaces(dbWorkspace);
                    userService.updateUser(currentUser.getUser_id(), currentUser);
                    
                    currentWorkspace=dbWorkspace;
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
        
         this.setTitle("PQMan: "+AppProperties.VERSION);
         this.setScene(new Scene(view));
         this.initModality(Modality.APPLICATION_MODAL);
         this.showAndWait();
    }
    
    private void logUser(){
        UserModel userModel=new UserModel();
        UserView userView=new UserView(userModel);
        if(userModel.getLoginSucceeded()){
            currentUser=userService.getUserWithInitials(userModel.getIntials());
            userBtn.setText(currentUser.getInitials());
            
            
        }else{
            if(currentUser!=null)System.out.println("fend.app.AppController.logUser(): retaining the old user: "+currentUser.getInitials());
        }
        
        
    }
    /**
     * This exit process is called in the following cases
     * 1. when a user logs out. 
     * 2. a new user logs in.
     * 
     * 
     */
    private void changeInWorkspaceOrUser(){
        
        if(currentUser==null) return;
        System.out.println("fend.app.AppController.changeInWorkspaceOrUser(): current User: "+currentUser.getInitials()+" currentWorkspace: "+currentWorkspace.getName()+
                " currentUser id: "+currentUser.getUser_id()+" owner Id: "+currentWorkspace.getOwner().getUser_id() +" NoOfUsers: "+currentWorkspace.getUsers().size());
        if(currentUser.equals(currentWorkspace.getOwner())){
            int elevStatus=elevation();
            if(elevStatus==0){       //no more users accessing the workspace (guests or owners)
                currentWorkspace.setOwner(null);
                workspaceService.updateWorkspace(currentWorkspace.getId(), currentWorkspace);
            }else{
                //do nothing
            }
        }else{      //currentuser is not the owner of the currentWorkspace but a guest
                currentWorkspace.removeUser(currentUser);
                currentUser.removeFromWorkspaces(currentWorkspace);
                workspaceService.updateWorkspace(currentWorkspace.getId(), currentWorkspace);
                userService.updateUser(currentUser.getUser_id(), currentUser);
             }
        
    }
    
    /**
     * the function returns 0 if no users are accessing the workspace.
     * else it elevates a guest to an owner
     */
    private int elevation(){
        if(currentWorkspace.getUsers().isEmpty()) return 0;
        else{
            currentUser.removeFromWorkspaces(currentWorkspace);
            currentUser.removeFromOwnedWorkspaces(currentWorkspace);
            currentWorkspace.removeUser(currentUser);
            userService.updateUser(currentUser.getUser_id(), currentUser);
            
            List<User> usersInWorkspace=new ArrayList<>(currentWorkspace.getUsers());
            User elevatedGuest=usersInWorkspace.get(0);
            System.out.println("fend.app.AppController.elevation(): Workspace "+currentWorkspace.getName()+" ownership changed from "+currentUser.getInitials()+" to "+elevatedGuest.getInitials());
            currentWorkspace.setOwner(elevatedGuest);
            elevatedGuest.addToWorkspaces(currentWorkspace);
            elevatedGuest.addToOwnedWorkspaces(currentWorkspace);
            workspaceService.updateWorkspace(currentWorkspace.getId(), currentWorkspace);
            userService.updateUser(elevatedGuest.getUser_id(), elevatedGuest);
            
            return 1;
        }
    }
}
