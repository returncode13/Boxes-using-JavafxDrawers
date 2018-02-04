/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app;

import app.properties.AppProperties;
import db.model.User;
import db.model.Workspace;
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
import java.util.Set;
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
import db.services.UserService;

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
    private User previousUser=null;
    
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
    private String titleHeader = "PQMan: "+AppProperties.VERSION;

    
    
    @FXML
    void about(ActionEvent event) {

    }

    @FXML
    void dbsettings(ActionEvent event) {

    }

    @FXML
    void exitTheProgram(ActionEvent event) {
        if(currentUser!=null) previousUser=currentUser;
        logout();
        close();
    }

    @FXML
    void handleBugReport(ActionEvent event) {

    }

    @FXML
    void loginAsUser(ActionEvent event) {
       if(currentUser!=null){
           System.out.println("fend.app.AppController.loginAsUser():  changing user "+currentUser.getInitials());
           previousUser=currentUser;
       }
       logUser();
        //changeInWorkspaceOrUser();  //take care of exiting user
        logout();
        login();
        
        
        
        
        /* if(currentWorkspace!=null){
        currentWorkspace.addToUsers(currentUser);
        currentUser.addToWorkspaces(currentWorkspace);
        if(currentWorkspace.getOwner()==null){
        currentWorkspace.setOwner(currentUser);
        currentUser.addToOwnedWorkspaces(currentWorkspace);
        }
        
        workspaceService.updateWorkspace(currentWorkspace.getId(), currentWorkspace);
        //            userService.updateUser(currentUser.getUser_id(), currentUser);
        }*/
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
       
        /*
        if(currentWorkspace!=null){  //take care of the exiting workspace
        System.out .println("fend.app.AppController.loadSession(): about to exit "+currentWorkspace.getName()+ ""
        + " with current user :"+currentUser.getInitials()+" and owner: "+currentWorkspace.getOwner().getInitials());
        // changeInWorkspaceOrUser();
        }
        
        currentWorkspace=workspaceToBeLoaded;
        System.out.println("fend.app.AppController.loadSession(): about to load "+currentWorkspace.getName());
        // if(currentWorkspace!=null){
        if(currentWorkspace.getOwner()==null){
        System.out.println("fend.app.AppController.loadSession(): workspace "+currentWorkspace.getName()+" has no owner");
        currentWorkspace.setOwner(currentUser);
        currentWorkspace.addToUsers(currentUser);
        currentUser.addToOwnedWorkspaces(currentWorkspace);
        currentUser.addToWorkspaces(currentWorkspace);
        
        workspaceService.updateWorkspace(currentWorkspace.getId(), currentWorkspace);
        userService.updateUser(currentUser.getUser_id(), currentUser);
        }else{
        System.out.println("fend.app.AppController.loadSession(): workspace "+currentWorkspace.getName()+" has owner "+currentWorkspace.getOwner().getInitials());
        
        if(currentUser==currentWorkspace.getOwner()){
        System.out.println("fend.app.AppController.loadSession(): User : "+currentUser.getInitials()+" is already logged into as owner to workspace: "+currentWorkspace.getName());
        return;
        }
        else{
        System.out.println("fend.app.AppController.loadSession(): Adding currentUser "+currentUser.getInitials()+""
        + " to the set of users for "+currentWorkspace.getName()+" number of users Before: "+currentWorkspace.getUsers().size()+" number of Workspaces assigned to user "+currentUser.getInitials()
        +" is Before: "+currentUser.getWorkspaces().size());
        currentWorkspace.addToUsers(currentUser);
        System.out.println("fend.app.AppController.loadSession(): Adding currentUser "+currentUser.getInitials()+""
        + " to the set of users for "+currentWorkspace.getName()+" number of users After: "+currentWorkspace.getUsers().size()+" number of Workspaces assigned to user "+currentUser.getInitials()
        +" is After: "+currentUser.getWorkspaces().size());
        // currentUser.addToWorkspaces(currentWorkspace);
        workspaceService.updateWorkspace(currentWorkspace.getId(), currentWorkspace);
        // userService.updateUser(currentUser.getUser_id(), currentUser);
        
        
        //Restrictions pending
        }
        }*/
       // }
        
        basePane.getChildren().add(frontEndWorkspaceView);
        previousUser=currentUser;
        logout();
        currentWorkspace=workspaceToBeLoaded;
        login();
        this.setTitle(titleHeader+" : "+currentWorkspace.getName()+" owner: "+currentWorkspace.getOwner().getInitials());
        
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
        if(!AppProperties.INSTALL){
            
       
         if(currentUser==null){
        logUser();
        return;
        }
        
        if(currentWorkspace!=null){         //starting a new workspace from an existing one. currentWorkspace=oldWorkspace
        previousUser=currentUser;
        logout();
        //       changeInWorkspaceOrUser();
        
        
        }
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
 if(!AppProperties.INSTALL) System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): owner: "+currentUser.getInitials());
                 if(newname.length()>0){
                     
                 
                 dbWorkspace.setName(newname);
                 nameEntered.set(true);
                 }
             });
        
       nameEntered.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    User u=null;
                   if(!AppProperties.INSTALL) {
                       u=userService.getUser(currentUser.getId());
                       System.out.println("fend.app.AppController.startNewWorkspace(): creating a new Workspace for user "+u.getInitials());
                   }
                    
                    workspaceService.createWorkspace(dbWorkspace);
                    
                     if(!AppProperties.INSTALL){
                            dbWorkspace.setOwner(u);
                            Set<User> us=dbWorkspace.getUsers();
                            us.add(u);
                            dbWorkspace.setUsers(us);
                            u.addToWorkspaces(dbWorkspace);
                            workspaceService.updateWorkspace(dbWorkspace.getId(), dbWorkspace);
                    
                     }
                    
                    WorkspaceModel model=new WorkspaceModel();
                    model.setId(dbWorkspace.getId());
                    model.setName(dbWorkspace.getName());
                    WorkspaceView node=new WorkspaceView(model);
                    basePane.getChildren().add(node);
                    
                    
                    /*currentUser.addToOwnedWorkspaces(dbWorkspace);
                    currentUser.addToWorkspaces(dbWorkspace);
                    userService.updateUser(currentUser.getUser_id(), currentUser);*/
                    
                    if(!AppProperties.INSTALL){
                        currentWorkspace=workspaceService.getWorkspace(dbWorkspace.getId());
                   
                    System.out.println("fend.app.AppController.startNewWorkspace(): "+currentWorkspace.getName()+" has "+currentWorkspace.getUsers().size()+" users");
                    AppController.this.setTitle(AppController.this.titleHeader+" : "+currentWorkspace.getName()+" owner: "+currentWorkspace.getOwner().getInitials());
                    }
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
        
         this.setTitle(titleHeader);
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
     * the function returns 0 if no users are accessing the workspace.
     * else it elevates a guest to an owner
     */
    private int elevation(){
        Workspace w=null;
        if(currentWorkspace!=null){
            w=workspaceService.getWorkspace(currentWorkspace.getId());
        }
        
        
        if(w.getUsers().isEmpty()) {
            System.out.println("fend.app.AppController.elevation(): no more users to elevate");
            return 0;
        }
        else{
           
            
            List<User> usersInWorkspace=new ArrayList<>(w.getUsers());
            User elevatedGuest=usersInWorkspace.get(0);
            System.out.println("fend.app.AppController.elevation(): changing ownership of workspace "+w.getName()+" to "+elevatedGuest.getInitials());
            w.setOwner(elevatedGuest);
            workspaceService.updateWorkspace(w.getId(), w);
            return 1;
        }
    }

    private void login() {
        
        User u=userService.getUser(currentUser.getId());
        Workspace w=null;
        if(currentWorkspace!=null){
            w=workspaceService.getWorkspace(currentWorkspace.getId());
        }
        
        if(w!=null && w.getOwner()==null){
            w.setOwner(u);
           
        }
        if(w==null) return;
        System.out.println("fend.app.AppController.login(): Users present in the workspace currently: ");
         List<User> usersInWorkspace=new ArrayList<>(w.getUsers());
        for(int i=0;i<w.getUsers().size();i++){
            //usersInWorkspace.get(i)
                    System.err.println(usersInWorkspace.get(i).getInitials());
        }
        System.out.println("fend.app.AppController.login(): adding user: "+u.getInitials()+" to currentWorkspace "+w.getName()+""
                + " sizeofUserList: "+w.getUsers().size());
        w.addToUsers(u);
        System.out.println("fend.app.AppController.login(): after addition sizeofUserList: "+w.getUsers().size());
        System.out.println("fend.app.AppController.login(): adding workspace "+w.getName()+" to users list: "+u.getInitials()+""
                + " sizeOfWorkspaceList: "+u.getWorkspaces().size());
        u.addToWorkspaces(w);
        System.out.println("fend.app.AppController.login(): after addition sizeOfWorkspaceList "+u.getWorkspaces().size());
        workspaceService.updateWorkspace(w.getId(), w);
        currentWorkspace=w;
    }

    private void logout() {
        if(previousUser==null) return;
        
        User u=userService.getUser(previousUser.getId());
        
        Workspace w=null;
        if(currentWorkspace!=null){
            w=workspaceService.getWorkspace(currentWorkspace.getId());
        }
        if(w==null) return;
               
        System.out.println("fend.app.AppController.logout(): removing user: "+u.getInitials()+" from workspace: "+w.getName());
        w.removeUser(u);
        u.removeFromWorkspaces(w);
        workspaceService.updateWorkspace(w.getId(), w);
        currentWorkspace=w;
        if(u.equals(w.getOwner())){
            
            System.out.println("fend.app.AppController.logout(): elevating a guest to an owner");
            workspaceService.updateWorkspace(w.getId(), w);
            int i=elevation();
            w=workspaceService.getWorkspace(w.getId());
            if(i==0){
                w.setOwner(null);
                workspaceService.updateWorkspace(w.getId(), w);
                currentWorkspace=w;
                this.setTitle(titleHeader+" : "+currentWorkspace.getName()+" No more owners");
            }else{
                currentWorkspace=w;
                this.setTitle(titleHeader+" : "+currentWorkspace.getName()+" owner: "+currentWorkspace.getOwner().getInitials());
            }
                
        }
        
        System.out.println("fend.app.AppController.logout(): workspace "+w.getName()+" has "+w.getUsers().size()+" users");
        System.out.println("fend.app.AppController.logout(): user: "+u.getInitials()+" is logged into "+u.getWorkspaces().size()+" workspaces");
        
    }
}
