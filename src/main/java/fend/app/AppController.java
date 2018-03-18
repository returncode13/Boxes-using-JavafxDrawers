/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app;

import app.connections.hibernate.Connections;
import app.properties.AppProperties;
import app.settings.database.DataBaseSettings;
import app.settings.database.DataBaseSettingsController;
import app.settings.database.DataBaseSettingsNode;
import app.settings.ssh.SShSettings;
import app.settings.ssh.SShSettingsController;
import app.settings.ssh.SShSettingsNode;
import com.jfoenix.controls.JFXTextArea;
import db.model.NodeProperty;
import db.model.NodeType;
import db.model.PropertyType;
import db.model.User;
import db.model.UserWorkspace;
import db.model.Workspace;
import db.services.NodePropertyService;
import db.services.NodePropertyServiceImpl;
import db.services.NodeTypeService;
import db.services.NodeTypeServiceImpl;
import db.services.PropertyTypeService;
import db.services.PropertyTypeServiceImpl;
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
import db.services.UserWorkspaceService;
import db.services.UserWorkspaceServiceImpl;
import fend.job.job0.JobType0Model;
import fend.job.job0.property.properties.JobType0Properties;
import fend.job.job1.properties.JobType1Properties;
import fend.job.job2.properties.JobType2Properties;
import fend.job.job3.properties.JobType3Properties;
import fend.job.job4.properties.JobType4Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
    private NodeTypeService nodeTypeService=new NodeTypeServiceImpl();
    private NodePropertyService nodePropertyService=new NodePropertyServiceImpl();
    private PropertyTypeService propertyTypeService=new PropertyTypeServiceImpl();
    private UserWorkspaceService userWorkspaceService=new UserWorkspaceServiceImpl();
    private Scene appScene;
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
    private JFXTextArea smallerLog;

    
    private String titleHeader = "PQMan: "+AppProperties.VERSION;

    
      @FXML
    void openLargerLog(MouseEvent event) {
          System.out.println("fend.app.AppController.openLargerLog(): Opening the console");       
    }
    
    
    
    @FXML
    void about(ActionEvent event) {

    }

    @FXML
    void dbsettings(ActionEvent event) {
        /*DataBaseSettings dataBaseSettings=new DataBaseSettings();
        DataBaseSettingsNode dataBaseSettingsNode=new DataBaseSettingsNode(dataBaseSettings);
        */
        if(currentWorkspace!=null){
            System.out.println("fend.app.AppController.dbsettings(): Cannot change database while an active workspace remains");
            
            return;
        }
        
        
        InputStream is=null;
        try {
          DataBaseSettings  databaseSettingsModel=new DataBaseSettings();
            //    System.out.println("landing.LandingController.dbsettings(): looking for "+getClass().getClassLoader().getResource(dbSettingXml).getFile());
            //File dbFile=new File(dbSettingXml);
            /*ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
            InputStream is=classLoader.getResourceAsStream(dbSettingXml);*/
            // File dbFile=new File(getClass().getClassLoader().getResource(dbSettingXml).getFile());
            // File dbFile=
            System.out.println("fend.app.AppController.dbsettings()");
            System.out.println("fend.app.AppController.dbsettings() looking for "+System.getProperty("user.home")+ "  file: "+Connections.dbSettingXml);
         //   logger.info("looking for  file: "+dbSettingXml+" under "+ System.getProperty("user.home"));
            File dbFile=new File(System.getProperty("user.home"),Connections.dbSettingXml);
            is = new FileInputStream(dbFile);
            try {
                JAXBContext contextObj = JAXBContext.newInstance(DataBaseSettings.class);
                
                //try unmarshalling the file. if the fields are not null. populate settingsmodel
                
                Unmarshaller unm=contextObj.createUnmarshaller();
                // DataBaseSettings dbsett=(DataBaseSettings) unm.unmarshal(dbFile);
                DataBaseSettings dbsett=(DataBaseSettings) unm.unmarshal(is);
                System.out.println("fend.app.AppController.dbsettings():   unmarshalled: "+dbsett.getChosenDatabase());
                //logger.info("unmarshalled: "+dbsett.getChosenDatabase());
                String parts[]=dbsett.getChosenDatabase().split("/");
                AppProperties.setProject(parts[parts.length-1]);
                databaseSettingsModel.setDbUser(dbsett.getDbUser());
                databaseSettingsModel.setDbPassword(dbsett.getDbPassword());
                databaseSettingsModel.setChosenDatabase(dbsett.getChosenDatabase());
                //logger.info("chosen db : " + dbsett.getChosenDatabase()+" user: "+dbsett.getDbUser()+" pass: "+dbsett.getDbPassword());
                DataBaseSettingsNode dbnode=new DataBaseSettingsNode(databaseSettingsModel);
                DataBaseSettingsController dcontrl=dbnode.getDataBaseSettingsController();
                
                Marshaller marshallerObj = contextObj.createMarshaller();
                marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshallerObj.marshal(databaseSettingsModel, new File(System.getProperty("user.home"),Connections.dbSettingXml));
                
            } catch (JAXBException ex) {
                //Logger.getLogger(LandingController.class.getName()).log(Level.SEVERE, null, ex);
                //logger.log(Level.SEVERE, null, ex);
                //logger.severe("JAXBException: "+ex.getMessage());
            }
            
        } catch (FileNotFoundException ex) {
            //logger.log(Level.SEVERE, "File not found!: {0}", ex.getMessage());
          //  logger.severe("File not found");
            //logger.log(Level.SEVERE, null, ex);
            //Exceptions.printStackTrace(ex);'
            ex.printStackTrace();
            
            
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
               // logger.severe("Couldn't close file");
               // Exceptions.printStackTrace(ex);
               ex.printStackTrace();
            }
        }
    }
    
    @FXML
    void settings(ActionEvent event) throws URISyntaxException {
        
        InputStream is=null;
        try {
           SShSettings settingsModel=new SShSettings();
            /*URL sshLocationURL=getClass().getClassLoader().getResource(sshSettingXml);
            File sFile=new File(sshSettingXml);*/
            //System.out.println("landing.LandingController.settings(): looking for "+sshLocationURL.getFile());
            //File sFile=new File(sshLocationURL.getFile());
            
            /* ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
            InputStream is=classLoader.getResourceAsStream(sshSettingXml);*/
            System.out.println("fend.app.AppController.settings() looking for "+System.getProperty("user.home")+ "  file: "+Connections.sshSettingXml);
           // logger.info("looking for "+System.getProperty("user.home")+ "  file: "+sshSettingXml);
            File sFile=new File(System.getProperty("user.home"),Connections.sshSettingXml);
            is = new FileInputStream(sFile);
            try {
                JAXBContext contextObj = JAXBContext.newInstance(SShSettings.class);
                System.out.println("fend.app.AppController.settings()");
                //try unmarshalling the file. if the fields are not null. populate settingsmodel
                
                Unmarshaller unm=contextObj.createUnmarshaller();
                // SShSettings sett=(SShSettings) unm.unmarshal(sFile);
                SShSettings sett=(SShSettings) unm.unmarshal(is);
                System.out.println("fend.app.AppController.settings():  unmarshalled: "+sett.getSshHost() );
               // logger.info("unmarshalled: "+sett.getSshHost());
                
                
                if(sett.isPopulated()){
                    settingsModel.setDbPassword(sett.getDbPassword());
                    settingsModel.setDbUser(sett.getDbUser());
                    settingsModel.setId(sett.getId());
                    settingsModel.setSshHost(sett.getSshHost());
                    settingsModel.setSshPassword(sett.getSshPassword());
                    settingsModel.setSshUser(sett.getSshUser());
                  //  AppProperties.setIrdbHost(settingsModel.getSshHost());
                }
                
                SShSettingsNode setnode=new SShSettingsNode(settingsModel);
                SShSettingsController sc=new SShSettingsController();
                
                //save the xml
                
                
                Marshaller marshallerObj = contextObj.createMarshaller();
                marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshallerObj.marshal(settingsModel, new File(System.getProperty("user.home"),Connections.sshSettingXml));
                
                
            } catch (JAXBException ex) {
               // Logger.getLogger(LandingController.class.getName()).log(Level.SEVERE, null, ex);
                //logger.severe( ex.getMessage());
            }
            
           
        } catch (FileNotFoundException ex) {
            //logger.severe("file not found! : "+ ex.getMessage());
           // Exceptions.printStackTrace(ex);
           ex.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
            //    logger.severe("Can't close file : "+ ex.getMessage());
               //Exceptions.printStackTrace(ex);
               ex.printStackTrace();
            }
        }
        
    }

    @FXML
    void exitTheProgram(ActionEvent event) {
        if(currentUser!=null) previousUser=currentUser;
        logout();
        currentWorkspace=null;
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
        frontEndWorkspaceModel.setWorkspace(workspaceToBeLoaded);
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

    /*@FXML
    void settings(ActionEvent event) {
    
    
    
    }*/

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
                 System.out.println("fend.app.AppController.startNewWorkspace(): nameEntered: "+newname);
 if(!AppProperties.INSTALL) System.out.println("fend.app.AppController.startNewWorkspace(): owner: "+currentUser.getInitials());
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
                      // u=userService.getUser(currentUser.getId());
                      u=currentUser;
                       System.out.println("fend.app.AppController.startNewWorkspace(): creating a new Workspace for user "+u.getInitials());
                   }
                    
                   
                    
                     if(!AppProperties.INSTALL){
                            dbWorkspace.setOwner(u);
                            //Set<User> us=dbWorkspace.getUsers();
                            Set<User> us=new HashSet<>();
                           // us.add(u);
                          //  dbWorkspace.setUsers(us);     //Doubtful
                           // u.addToWorkspaces(dbWorkspace);
                          
                           
                            workspaceService.createWorkspace(dbWorkspace);
                            UserWorkspace userWorkspace=new UserWorkspace();
                            userWorkspace.setUser(u);
                            userWorkspace.setWorkspace(dbWorkspace);

                            System.out.println("fend.app.AppController.startNewWorkspace(): Creating an entry for user,workspace:  "+u.getId()+","+dbWorkspace.getId());
                            userWorkspaceService.createUserWorkspace(userWorkspace);
                            //workspaceService.updateWorkspace(dbWorkspace.getId(), dbWorkspace);
                    
                     }
                    
                    WorkspaceModel model=new WorkspaceModel();
                    model.setId(dbWorkspace.getId());
                    model.setName(dbWorkspace.getName());
                    model.setWorkspace(dbWorkspace);
                    WorkspaceView node=new WorkspaceView(model);
                    basePane.getChildren().add(node);
                    
                    
                    /*currentUser.addToOwnedWorkspaces(dbWorkspace);
                    currentUser.addToWorkspaces(dbWorkspace);
                    userService.updateUser(currentUser.getUser_id(), currentUser);*/
                    
                    if(!AppProperties.INSTALL){
                       // currentWorkspace=workspaceService.getWorkspace(dbWorkspace.getId());
                       currentWorkspace=dbWorkspace;
                   
//                    System.out.println("fend.app.AppController.startNewWorkspace(): "+currentWorkspace.getName()+" has "+currentWorkspace.getUsers().size()+" users");
                    AppController.this.setTitle(AppController.this.titleHeader+" : "+currentWorkspace.getName()+" owner: "+currentWorkspace.getOwner().getInitials());
                    }
                    
                    
                    
                    checkForJobPropertiesForJobType(JobType0Model.PROCESS_2D);
                    checkForJobPropertiesForJobType(JobType0Model.SEGD_LOAD);
                    checkForJobPropertiesForJobType(JobType0Model.ACQUISITION);
                    checkForJobPropertiesForJobType(JobType0Model.TEXT);
                    
                    
                    
               
        
                    
                    
                    
                }else{
                    return;
                }
            }
            
            
            /**
             * Check if the job types and properties (x,y) for each job type exists in the database.
             * If they dont exist then create
             **/
            private void checkForJobPropertiesForJobType(Long type) {
              //  if(type.equals(JobType0Model.TEXT)){
                         // Set up properties for each jobtype .
                    //if defined then skip
                    // else create                    
                    //check if a type JobType0Model.Type exists in the nodeType table 
                    //if not then create it
                    if(nodeTypeService.getNodeTypeObjForType(type)==null){
                        NodeType n=new NodeType();
                        n.setActualnodeid(type);
                        if(type.equals(JobType0Model.ACQUISITION)) n.setName("Acquisition");
                        else if(type.equals(JobType0Model.PROCESS_2D)) n.setName("2DProcess");
                        else if(type.equals(JobType0Model.SEGD_LOAD))n.setName("SEGD_LOAD");
                        else if(type.equals(JobType0Model.TEXT))n.setName("Text");
                        
                        nodeTypeService.createNodeType(n);
                    }
                    
                    //check if property type exists for JobType0Model.Type. defined in class JobType<TYPE>Properties();
                    JobType0Properties jstmp=null;
                    if(type.equals(JobType0Model.ACQUISITION)){
                        jstmp=new JobType3Properties();
                    }
                        else if(type.equals(JobType0Model.PROCESS_2D)){
                           jstmp=new JobType1Properties();
                        }
                        else if(type.equals(JobType0Model.SEGD_LOAD)){
                            jstmp=new JobType2Properties();
                        }
                        else if(type.equals(JobType0Model.TEXT)){
                           jstmp=new JobType4Properties();
                        }
                   
                    List<String> jobProperties=jstmp.getProperties();
                    for (Iterator<String> iterator = jobProperties.iterator(); iterator.hasNext();) {
                        String jprop = iterator.next();
                        if(propertyTypeService.getPropertyTypeObjForName(jprop)==null){
                            PropertyType prop=new PropertyType();
                            prop.setName(jprop);
                            propertyTypeService.createPropertyType(prop);
                        }
                    }
                    
                     //check if the nodepropertydefinitions table entries exist. i.e. check if entries like 
                    /// type 4 job has the property "to"
                    //type 4 job has the property "from" in the database
                    //if not create
                    //
                    NodeType nodeType=nodeTypeService.getNodeTypeObjForType(type);
                    if(nodePropertyService.getPropertyTypesFor(nodeType).isEmpty()){

                        for (Iterator<String> iterator = jobProperties.iterator(); iterator.hasNext();) {
                             NodeProperty nodeProperty=new NodeProperty();
                            nodeProperty.setNodeType(nodeType);
                            String j4prop = iterator.next();
                            PropertyType prop=propertyTypeService.getPropertyTypeObjForName(j4prop);
                            nodeProperty.setPropertyType(prop);

                            nodePropertyService.createNodeProperty(nodeProperty);
                        }
                    }
              //  }
            }
           
       });
       
       
        
    }
    
    
    
    
    
    
    void setModel(AppModel model) {
        this.model=model;
    }

    void setView(AppView view) {
        this.view=view;
        appScene=new Scene(this.view);
         //this.setTitle(titleHeader);
         //this.setScene(new Scene(view));
        // this.initModality(Modality.APPLICATION_MODAL);
         //this.showAndWait();
         //this.show();
    }

    public Scene getAppScene() {
        return appScene;
    }

    public String getTitleHeader() {
        return titleHeader;
    }
    
    
    
    
    private void logUser(){
        System.out.println("fend.app.AppController.logUser(): starting a usermodel");
        UserModel userModel=new UserModel();
        UserView userView=new UserView(userModel);
        if(userModel.getLoginSucceeded()){
            System.out.println("fend.app.AppController.logUser(): Getting user with Initials: "+userModel.getIntials());
      //  currentUser=userService.getUserWithInitials(userModel.getIntials());
        currentUser=userModel.getUser();
        userBtn.setText(currentUser.getInitials());
            System.out.println("fend.app.AppController.logUser(): finished retrieving User ");
        
        }else{
        if(currentUser!=null)System.out.println("fend.app.AppController.logUser(): retaining the old user: "+currentUser.getInitials());
        }
        
        
    }
    
    
    /**
     * the function returns 0 if no users are accessing the workspace.
     * else it elevates a guest to an owner and returns 1
     */
    private int elevation(){
        Workspace w=null;
        if(currentWorkspace!=null){
           // w=workspaceService.getWorkspace(currentWorkspace.getId());
           w=currentWorkspace;
        }
        
        List<User> usersInWorkspace=userService.getUsersInWorkspace(currentWorkspace);
        if(usersInWorkspace.isEmpty()) {
            System.out.println("fend.app.AppController.elevation(): no more users to elevate");
            return 0;
        }
        else{
           
            
           // List<User> usersInWorkspace=new ArrayList<>(w.getUsers());
            User elevatedGuest=usersInWorkspace.get(0);
            System.out.println("fend.app.AppController.elevation(): changing ownership of workspace "+w.getName()+" to "+elevatedGuest.getInitials());
            w.setOwner(elevatedGuest);
            workspaceService.updateWorkspace(w.getId(), w);
            return 1;
        }
    }

    private void login() {
        
        //User u=userService.getUser(currentUser.getId());
        User u=currentUser;
        Workspace w=null;
        if(currentWorkspace!=null){
          //  w=workspaceService.getWorkspace(currentWorkspace.getId());
          w=currentWorkspace;
        }
        
        if(w!=null && w.getOwner()==null){
            w.setOwner(u);
           
        }
        if(w==null) return;
        System.out.println("fend.app.AppController.login(): Users present in the workspace currently: ");
         //List<User> usersInWorkspace=new ArrayList<>(w.getUsers());
         List<User> usersInWorkspace=userService.getUsersInWorkspace(currentWorkspace);
        //for(int i=0;i<w.getUsers().size();i++){
         for(int i=0;i<usersInWorkspace.size();i++){
            //usersInWorkspace.get(i)
                    System.out.println(usersInWorkspace.get(i).getInitials());
        }
        System.out.println("fend.app.AppController.login(): adding user: "+u.getInitials()+" to currentWorkspace "+w.getName()+""
                + " sizeofUserList: "+usersInWorkspace.size());
        //w.addToUsers(u);
        UserWorkspace userWorkspace=new UserWorkspace();
        userWorkspace.setUser(u);
        userWorkspace.setWorkspace(w);
        
        System.out.println("fend.app.AppController.login(): Creating an entry for user,workspace:  "+u.getId()+","+w.getId());
        userWorkspaceService.createUserWorkspace(userWorkspace);
        System.out.println("fend.app.AppController.login(): after addition sizeofUserList: "+usersInWorkspace.size());
        List<Workspace> workspacesForUser=workspaceService.getWorkspacesForUser(u);
        System.out.println("fend.app.AppController.login(): adding workspace "+w.getName()+" to users list: "+u.getInitials()+""
                + " sizeOfWorkspaceList: "+workspacesForUser.size());
       // u.addToWorkspaces(w);
      //  System.out.println("fend.app.AppController.login(): after addition sizeOfWorkspaceList "+u.getWorkspaces().size());
     //   workspaceService.updateWorkspace(w.getId(), w);
        currentWorkspace=w;
        this.setTitle(titleHeader+" : "+currentWorkspace.getName()+" owner: "+currentWorkspace.getOwner().getInitials());
        AppProperties.setCurrentUser(u);
    }

    private void logout() {
        if(previousUser==null) return;
        
        //User u=userService.getUser(previousUser.getId());
        User u=previousUser;
        
        Workspace w=null;
        if(currentWorkspace!=null){
           // w=workspaceService.getWorkspace(currentWorkspace.getId());\
           w=currentWorkspace;
        }
        if(w==null) return;
               
     
      //  w.removeUser(u);
       // u.removeFromWorkspaces(w);
        
       System.out.println("fend.app.AppController.logout(): removing user: "+u.getInitials()+" from workspace: "+w.getName());
          //remove all entries in the user-workspace table where u=previousUser and workspace=W;
        userWorkspaceService.remove(u,w);
        
        
        /* workspaceService.updateWorkspace(w.getId(), w);
        userService.updateUser(u.getId(), u);*/
        currentWorkspace=w;
        if(u.equals(w.getOwner())){
            
            System.out.println("fend.app.AppController.logout(): elevating a guest to an owner");
          //  workspaceService.updateWorkspace(w.getId(), w);
            int i=elevation();
         //   w=workspaceService.getWorkspace(w.getId());
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
          
         List<User> usersInWorkspace=userService.getUsersInWorkspace(w);
         List<Workspace> workspacesForUser=workspaceService.getWorkspacesForUser(u);
        System.out.println("fend.app.AppController.logout(): workspace "+w.getName()+" has "+usersInWorkspace.size()+" users");
        System.out.println("fend.app.AppController.logout(): user: "+
                u.getInitials()+" is logged into "
                +workspacesForUser.size()+" workspaces");
        
    }
}
