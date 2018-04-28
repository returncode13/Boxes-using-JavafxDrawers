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
import db.model.Job;
import db.model.NodeProperty;
import db.model.NodePropertyValue;
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
import fend.job.job0.property.JobModelProperty;
import fend.job.job0.property.properties.JobType0Properties;
import fend.job.job1.properties.JobType1Properties;
import fend.job.job2.properties.JobType2Properties;
import fend.job.job3.JobType3Model;
import fend.job.job3.JobType3View;
import fend.job.job3.properties.JobType3Properties;
import fend.job.job4.properties.JobType4Properties;
import fend.workspace.WorkspaceController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.SVGPath;
import javafx.stage.Screen;
import javafx.util.Duration;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AppController extends Stage implements Initializable{
    private AppModel model;
    private AppView view;
    private WorkspaceService workspaceService=new WorkspaceServiceImpl();
    private UserService userService=new UserServiceImpl();
    private Workspace currentWorkspace=null;
   // private WorkspaceModel currentWorkspaceModel=null;
    private WorkspaceController currentWorkspaceController=null;
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
    private Button newWorkspace;

    @FXML
    private Button loadExistingWorkspace;

    @FXML
    private Button connect;

    @FXML
    private Button databaseSettings;

    
    
    
    @FXML
    private StackPane basePane;
    
    
     @FXML
    private JFXTextArea smallerLog;

    @FXML
    private Label projectLabel;

    @FXML
    private Label workspaceLabel;

    @FXML
    private Label ownerLabel;

    @FXML
    private ListView<String> guestList;
    
    
    
    
    
    

    private   List<String> glist=new ArrayList<>();
    private   ObservableList<String> observableGuestList=FXCollections.observableArrayList(glist);

    
    public  ObservableList<String> getObservableGuestList() {
        return observableGuestList;
    }

    public  void setObservableGuestList(ObservableList<String> observableGuestList) {
        observableGuestList = observableGuestList;
    }
    
    
    
    public  void addToGuestList(User u){
        observableGuestList.add(u.getInitials());
    }
     
    private String titleHeader = "PQMan: "+AppProperties.VERSION;

    
      @FXML
    void openLargerLog(MouseEvent event) {
          System.out.println("fend.app.AppController.openLargerLog(): Opening the console");       
    }
    
    
    
    @FXML
    void about(ActionEvent event) {

    }
    
     @FXML
    private Button button2D;

    @FXML
    private Button acqButton;

    @FXML
    private Button segdButton;

    @FXML
    private Button textButton;

    @FXML
    private Button summaryButton;

    @FXML
    private Button chartButton;
    
     @FXML
    void add2DNode(ActionEvent event) {
        currentWorkspaceController.addBox(event);
    }

    @FXML
    void addAcqNode(ActionEvent event) {
        currentWorkspaceController.addAcq(event);
    }

    @FXML
    void addSegdNode(ActionEvent event) {
        currentWorkspaceController.addSEGD(event);
    }

    @FXML
    void addTextNode(ActionEvent event) {
        currentWorkspaceController.addText(event);
    }
    
    
    @FXML
    void drawCharts(ActionEvent event) {
        currentWorkspaceController.chart(event);
    }

    @FXML
    void startSummary(ActionEvent event) {
        try {
            currentWorkspaceController.getSummary(event);
        } catch (Exception ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            
            System.out.println("fend.app.AppController.dbsettings()");
            System.out.println("fend.app.AppController.dbsettings() looking for "+System.getProperty("user.home")+ "  file: "+Connections.dbSettingXml);
         
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
               
            }
            
        } catch (FileNotFoundException ex) {
            
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
        currentWorkspaceController=frontEndWorkspaceView.getController();
        frontEndWorkspaceView.getController().setLoading(true);
       
        
        
        basePane.getChildren().add(frontEndWorkspaceView);
        previousUser=currentUser;
        logout();
        currentWorkspace=workspaceToBeLoaded;
        enableButtons.set(true);
        login();
        this.setTitle(titleHeader+" : "+currentWorkspace.getName()+" owner: "+currentWorkspace.getOwner().getInitials());
        
        refreshGuestList();
        
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

    private static SVGPath createPath(String s,String fill,String hoverFill){
        SVGPath path=new SVGPath();
        
       path.getStyleClass().add("svg");
        path.setContent(s);
        path.setStyle("-fill:"+fill+";-hover-fill:"+hoverFill+";");
        return path;
    }
    
    private void createUserButton(){
        Group svg=new Group(
                /*createPath("M1,38.599c-0.553,0-1,0.447-1,1s0.447,1,1,1c1.163,0,2.08,0.375,2.803,1.146C5.914,44,5.63,48.732,5.625,48.849v12.75    c0,3.211,0.892,5.688,2.649,7.356c2.109,2.004,4.771,2.146,5.479,2.146c0.098,0,0.158-0.004,0.174-0\n" +
                ".004    c0.552-0.027,0.976-0.498,0.947-1.051c-0.029-0.554-0.508-0.98-1.051-0.947c-0.026,0.012-2.385,0.104-4.172-1.594    c-1.346-1.277-2.026-3.265-2.026-5.906L7.623,48.916c0.016-0.229,0.348-5.644-2.356-8.533c-0.279-0.298-0.581-0.561-0.901\n" +
                "-0.783    c0.32-0.224,0.622-0.484,0.901-0.784c2.704-2.893,2.372-8.305,2.358-8.466v-12.75c0-2.631,0.676-4.612,2.01-5.891    c1.767-1.694,4.158-1.614,4.193-1.61c0.551,0.034,1.017-0.396,1.046-0.947c0.027-0.552-0.396-1.021-0.947-1.051    c-0.\n" +
                "138,0.002-3.25-0.14-5.651,2.143c-1.759,1.67-2.649,4.146-2.649,7.356l0.002,12.817c0.09,1.317-0.064,5.151-1.821,7.032    C3.082,38.223,2.165,38.599,1,38.599z", "red", "darkred"),
                
                createPath("M78.752,41.153c0.555,0,1-0.444,1-0.999c0-0.554-0.445-1-1-1c-1.164,0-2.08-0.375-2.803-1.146    c-2.111-2.255-1.828-6.989-1.822-7.104v-12.75c0-3.212-0.895-5.688-2.648-7.356c-2.4-2.281-5.521-2.144-5.65-2.143    c-0.5\n" +
                "53,0.029-0.977,0.499-0.947,1.052c0.029,0.552,0.498,0.976,1.053,0.946c0.025-0.009,2.383-0.104,4.17,1.595    c1.35,1.276,2.025,3.265,2.025,5.906l0.002,12.684c-0.016,0.229-0.348,5.641,2.355,8.532c0.281,0.3,0.582,0.561,0.902,0.782    c-0.32,0\n" +
                ".227-0.621,0.484-0.902,0.784c-2.703,2.896-2.371,8.307-2.357,8.465v12.75c0,2.633-0.676,4.613-2.01,5.895    c-1.768,1.689-4.164,1.607-4.191,1.607c-0.531-0.028-1.018,0.396-1.047,0.947c-0.027,0.551,0.396,1.021,0.947,1.051    c0.018,0,0.074,0.\n" +
                "002,0.174,0.002c0.705,0,3.367-0.141,5.479-2.145c1.761-1.67,2.648-4.146,2.648-7.357l-0.003-12.813    c-0.09-1.318,0.063-5.152,1.821-7.031C76.67,41.532,77.586,41.153,78.752,41.153z", "red", "darkred")*/
                
                /*  createPath("M493.692,476.023H296.139v35.679H494.16c10.521,0,18.915-9.108,17.728-19.869" +
                "C510.883,482.732,502.849,476.023,493.692,476.023z", "red", "darkred"),
                createPath("M493.566,511.703H17.84c-9.852,0-17.84-7.987-17.84-17.84V18.137c0-9.852,7.987-17.84,17.84-17.84" +
                "s17.84,7.987,17.84,17.84v457.886h457.886c9.852,0,17.84,7.987,17.84,17.84C511.405,503.715,503.418,511.703,493.566,511.703z", "red", "darkred"),
                createPath("M493.566,100.2h-40.437v411.503h40.437c9.852,0,17.84-7.987,17.84-17.84V118.04\n" +
                "	C511.405,108.187,503.418,100.2,493.566,100.2z", "blue", "darkblue"),
                createPath("M493.566,511.703h-79.684c-9.852,0-17.84-7.987-17.84-17.84V118.04c0-9.852,7.987-17.84,17.84-17.84\n" +
                "	h79.684c9.852,0,17.84,7.987,17.84,17.84v375.823C511.405,503.715,503.418,511.703,493.566,511.703z", "yellow", "orange"),
                createPath("M335.387,225.078H294.95v286.625h40.437c9.852,0,17.84-7.987,17.84-17.84V242.918\n" +
                "	C353.226,233.065,345.239,225.078,335.387,225.078z", "red", "darkred"),
                createPath("M335.387,511.703h-79.684c-9.852,0-17.84-7.987-17.84-17.84V242.918c0-9.852,7.987-17.84,17.84-17.84\n" +
                "	h79.684c9.852,0,17.84,7.987,17.84,17.84v250.945C353.226,503.715,345.239,511.703,335.387,511.703z", "green", "darkgreen"),
                createPath("M176.019,351.145h-40.437v160.557h40.437c9.852,0,17.84-7.987,17.84-17.84V368.985\n" +
                "	C193.858,359.133,185.871,351.145,176.019,351.145z", "green", "darkgreen"),
                createPath("M176.019,511.703H96.334c-9.852,0-17.84-7.987-17.84-17.84V368.985c0-9.852,7.987-17.84,17.84-17.84\n" +
                "	h79.684c9.852,0,17.84,7.987,17.84,17.84v124.878C193.858,503.715,185.871,511.703,176.019,511.703z", "blue", "blue")*/
                
               // createPath("M20,20h60v60h-60z", "red", "darkred")
        );
        Bounds bounds=svg.getBoundsInParent();
        double scale=Math.min(20/bounds.getWidth(),20/bounds.getHeight());
        svg.setScaleX(scale);
        svg.setScaleY(scale);
        
        userBtn.setGraphic(svg);
        userBtn.setMaxSize(100, 100);
        userBtn.setMinSize(100, 100);
        userBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
    
    
    
   
    
    private void createGraphAndChartsButton(){
        
        

//<div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
        Group svg=new Group(
                
                createPath("M493.692,476.023H296.139v35.679H494.16c10.521,0,18.915-9.108,17.728-19.869" +
"C510.883,482.732,502.849,476.023,493.692,476.023z", "#808285", "#808285"),
                createPath("M493.566,511.703H17.84c-9.852,0-17.84-7.987-17.84-17.84V18.137c0-9.852,7.987-17.84,17.84-17.84" +
"s17.84,7.987,17.84,17.84v457.886h457.886c9.852,0,17.84,7.987,17.84,17.84C511.405,503.715,503.418,511.703,493.566,511.703z", "#A7A9AC", "#A7A9AC"),
                createPath("M493.566,100.2h-40.437v411.503h40.437c9.852,0,17.84-7.987,17.84-17.84V118.04\n" +
"	C511.405,108.187,503.418,100.2,493.566,100.2z", "#48A792", "#48A792"),
                createPath("M493.566,511.703h-79.684c-9.852,0-17.84-7.987-17.84-17.84V118.04c0-9.852,7.987-17.84,17.84-17.84\n" +
"	h79.684c9.852,0,17.84,7.987,17.84,17.84v375.823C511.405,503.715,503.418,511.703,493.566,511.703z", "#85EDC1", "#85EDC1"),
                createPath("M335.387,225.078H294.95v286.625h40.437c9.852,0,17.84-7.987,17.84-17.84V242.918\n" +
"	C353.226,233.065,345.239,225.078,335.387,225.078z", "#FF9900", "#FF9900"),
                createPath("M335.387,511.703h-79.684c-9.852,0-17.84-7.987-17.84-17.84V242.918c0-9.852,7.987-17.84,17.84-17.84\n" +
"	h79.684c9.852,0,17.84,7.987,17.84,17.84v250.945C353.226,503.715,345.239,511.703,335.387,511.703z", "#FFDB2D", "#FFDB2D"),
                 createPath("M176.019,351.145h-40.437v160.557h40.437c9.852,0,17.84-7.987,17.84-17.84V368.985\n" +
"	C193.858,359.133,185.871,351.145,176.019,351.145z", "#FC0023", "#FC0023"),
                createPath("M176.019,511.703H96.334c-9.852,0-17.84-7.987-17.84-17.84V368.985c0-9.852,7.987-17.84,17.84-17.84\n" +
"	h79.684c9.852,0,17.84,7.987,17.84,17.84v124.878C193.858,503.715,185.871,511.703,176.019,511.703z", "#FF4F19", "#FF4F19")
                
               // createPath("M20,20h60v60h-60z", "red", "darkred")
        );
        Bounds bounds=svg.getBoundsInParent();
        double scale=Math.min(20/bounds.getWidth(),20/bounds.getHeight());
        svg.setScaleX(scale);
        svg.setScaleY(scale);
        
        chartButton.setGraphic(svg);
        chartButton.setMaxSize(157,48);
        chartButton.setMinSize(157, 48);
        chartButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
    
    private void createSummaryButton(){
        
        //<div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
        
        
        
        Group svg=new Group(
              
                createPath("M494.345,459.034H17.655C7.904,459.034,0,451.13,0,441.379V70.621\n" +
"	c0-9.751,7.904-17.655,17.655-17.655h476.69c9.751,0,17.655,7.904,17.655,17.655v370.759\n" +
                        "	C512,451.129,504.095,459.034,494.345,459.034z", "#062751", "#062751"),
                createPath("M0,105.931h512v-35.31c0-9.751-7.904-17.655-17.655-17.655H17.655C7.904,52.966,0,60.87,0,70.621\n" +
"	V105.931z", "#C3E678", "#C3E678"),
                createPath("M61.793,220.69h-35.31c-4.875,0-8.828-3.953-8.828-8.828v-8.828c0-4.875,3.953-8.828,8.828-8.828\n" +
"	h35.31c4.875,0,8.828,3.953,8.828,8.828v8.828C70.621,216.737,66.668,220.69,61.793,220.69z", "#D7DEED", "#D7DEED"),
                new Group(
                        createPath("M203.034,220.69h-35.31c-4.875,0-8.828-3.953-8.828-8.828v-8.828c0-4.875,3.953-8.828,8.828-8.828\n" +
"		h35.31c4.875,0,8.828,3.953,8.828,8.828v8.828C211.862,216.737,207.91,220.69,203.034,220.69z", "#8F96AC", "#8F96AC"),
                        createPath("M414.897,220.69h-35.31c-4.875,0-8.828-3.953-8.828-8.828v-8.828c0-4.875,3.953-8.828,8.828-8.828\n" +
"		h35.31c4.875,0,8.828,3.953,8.828,8.828v8.828C423.724,216.737,419.772,220.69,414.897,220.69z", "#8F96AC", "#8F96AC"),
                        createPath("M344.276,220.69h-35.31c-4.875,0-8.828-3.953-8.828-8.828v-8.828c0-4.875,3.953-8.828,8.828-8.828\n" +
"		h35.31c4.875,0,8.828,3.953,8.828,8.828v8.828C353.103,216.737,349.151,220.69,344.276,220.69z", "#8F96AC", "#8F96AC"),
                        createPath("M485.517,220.69h-35.31c-4.875,0-8.828-3.953-8.828-8.828v-8.828c0-4.875,3.953-8.828,8.828-8.828\n" +
"		h35.31c4.875,0,8.828,3.953,8.828,8.828v8.828C494.345,216.737,490.392,220.69,485.517,220.69z", "#8F96AC", "#8F96AC")
                ),
                
                createPath("M61.793,308.966h-35.31c-4.875,0-8.828-3.953-8.828-8.828v-8.828c0-4.875,3.953-8.828,8.828-8.828\n" +
"	h35.31c4.875,0,8.828,3.953,8.828,8.828v8.828C70.621,305.013,66.668,308.966,61.793,308.966z", "#C3E678", "#C3E678"),
                new Group(
                                createPath("M203.034,308.966h-35.31c-4.875,0-8.828-3.953-8.828-8.828v-8.828c0-4.875,3.953-8.828,8.828-8.828\n" +
                "		h35.31c4.875,0,8.828,3.953,8.828,8.828v8.828C211.862,305.013,207.91,308.966,203.034,308.966z", "#D7DEED", "#D7DEED"),
                                createPath("M414.897,308.966h-35.31c-4.875,0-8.828-3.953-8.828-8.828v-8.828c0-4.875,3.953-8.828,8.828-8.828\n" +
"		h35.31c4.875,0,8.828,3.953,8.828,8.828v8.828C423.724,305.013,419.772,308.966,414.897,308.966z", "#D7DEED", "#D7DEED"),
                        createPath("M344.276,308.966h-35.31c-4.875,0-8.828-3.953-8.828-8.828v-8.828c0-4.875,3.953-8.828,8.828-8.828\n" +
"		h35.31c4.875,0,8.828,3.953,8.828,8.828v8.828C353.103,305.013,349.151,308.966,344.276,308.966z", "#D7DEED", "D7DEED"),
                        createPath("M485.517,308.966h-35.31c-4.875,0-8.828-3.953-8.828-8.828v-8.828c0-4.875,3.953-8.828,8.828-8.828\n" +
"		h35.31c4.875,0,8.828,3.953,8.828,8.828v8.828C494.345,305.013,490.392,308.966,485.517,308.966z", "#D7DEED", "#D7DEED"),
                        createPath("M273.655,353.103h-35.31c-4.875,0-8.828-3.953-8.828-8.828v-8.828c0-4.875,3.953-8.828,8.828-8.828\n" +
"		h35.31c4.875,0,8.828,3.953,8.828,8.828v8.828C282.483,349.151,278.53,353.103,273.655,353.103z", "#D7DEED", "#D7DEED"),
                        createPath("M132.414,353.103h-35.31c-4.875,0-8.828-3.953-8.828-8.828v-8.828c0-4.875,3.953-8.828,8.828-8.828\n" +
"		h35.31c4.875,0,8.828,3.953,8.828,8.828v8.828C141.241,349.151,137.289,353.103,132.414,353.103z", "#D7DEED", "D7DEED")
                        
                ),
                createPath("M61.793,353.103h-35.31c-4.875,0-8.828-3.953-8.828-8.828v-8.828c0-4.875,3.953-8.828,8.828-8.828\n" +
"	h35.31c4.875,0,8.828,3.953,8.828,8.828v8.828C70.621,349.151,66.668,353.103,61.793,353.103z", "#C3E678", "#C3E678")
                
               
        );
        Bounds bounds=svg.getBoundsInParent();
        double scale=Math.min(25/bounds.getWidth(),25/bounds.getHeight());
        svg.setScaleX(scale);
        svg.setScaleY(scale);
        
        summaryButton.setGraphic(svg);
        summaryButton.setMaxSize(157,48);
        summaryButton.setMinSize(157, 48);
        summaryButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
    
    private BooleanProperty enableButtons=new SimpleBooleanProperty(false);
    
    @FXML
    void startNewWorkspace(ActionEvent event) {
      //  if(!AppProperties.INSTALL){
            
       
         if(currentUser==null){
        logUser();
        return;
        }
        
        if(currentWorkspace!=null){         //starting a new workspace from an existing one. currentWorkspace=oldWorkspace
        previousUser=currentUser;
        logout();
        //       changeInWorkspaceOrUser();
        
        
        }
      //  }
        
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
 System.out.println("fend.app.AppController.startNewWorkspace(): owner: "+currentUser.getInitials());
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
               //    if(!AppProperties.INSTALL) {
                      // u=userService.getUser(currentUser.getId());
                      u=currentUser;
                       System.out.println("fend.app.AppController.startNewWorkspace(): creating a new Workspace for user "+u.getInitials());
               //    }
                    
                   
                    
                    // if(!AppProperties.INSTALL){
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
                    
                  //   }
                    
                    WorkspaceModel model=new WorkspaceModel();
                    model.setId(dbWorkspace.getId());
                    model.setName(dbWorkspace.getName());
                    model.setWorkspace(dbWorkspace);
                    WorkspaceView node=new WorkspaceView(model);
                    //currentWorkspaceModel=model;
                    currentWorkspaceController=node.getController();
                    basePane.getChildren().add(node);
                    
                    
                    /*currentUser.addToOwnedWorkspaces(dbWorkspace);
                    currentUser.addToWorkspaces(dbWorkspace);
                    userService.updateUser(currentUser.getUser_id(), currentUser);*/
                    
                   // if(!AppProperties.INSTALL){
                       // currentWorkspace=workspaceService.getWorkspace(dbWorkspace.getId());
                       currentWorkspace=dbWorkspace;
                      enableButtons.set(true);
//                    System.out.println("fend.app.AppController.startNewWorkspace(): "+currentWorkspace.getName()+" has "+currentWorkspace.getUsers().size()+" users");
                    AppController.this.setTitle(AppController.this.titleHeader+" : "+currentWorkspace.getName()+" owner: "+currentWorkspace.getOwner().getInitials());
                   // }
                    
                    refreshGuestList();
                    ownerLabel.setText(currentWorkspace.getOwner().getInitials());
                    workspaceLabel.setText(currentWorkspace.getName());
                    
                    checkForJobPropertiesForJobType(JobType0Model.PROCESS_2D);
                    checkForJobPropertiesForJobType(JobType0Model.SEGD_LOAD);
                    checkForJobPropertiesForJobType(JobType0Model.ACQUISITION);
                    checkForJobPropertiesForJobType(JobType0Model.TEXT);
                    
                    
                    
               
        
                    
                    
                    
                }else{
                    enableButtons.set(false);
                    return;
                }
            }
            
            
            /**
             * Check if the job types and properties (x,y) for each job type exists in the database.
             * If they don't exist then create
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
        enableButtons.addListener(ENABLE_BUTTONS_LISTENER);
        createGraphAndChartsButton();
        createSummaryButton();
        
        
                acqButton.setDisable(true);
                segdButton.setDisable(true);
                button2D.setDisable(true);
                textButton.setDisable(true);
                summaryButton.setDisable(true);
                chartButton.setDisable(true);
                
         projectLabel.setText(AppProperties.getProject());
         ownerLabel.setText("");
         
        //createNewWorkspaceButton();
        //createLoadWorkspaceButton();
        //createUserButton();
        
        
            guestService.setPeriod(Duration.seconds(AppProperties.TIME_FOR_GUEST_QUERY));
            
            guestService.setOnSucceeded(e->{
              //  System.out.println("fend.app.AppController.startGuestService(): updating the guest List");
                //AppController.this.setGuestChangedProperty(!AppController.this.getGuestChanged());
                guestList.getItems().clear();
                guestList.getItems().addAll(AppController.this.observableGuestList);
            });
            guestService.setOnRunning(e->{
               // System.out.println("fend.app.AppController.startGuestService(): service is up and running");
            });
            guestService.setOnCancelled(e->{
                System.out.println("fend.app.AppController.startGuestService(): cancelled");
            });
            guestService.setOnFailed(e->{
                System.out.println("fend.app.AppController.startGuestService(): failed");
            });
    }

    void setView(AppView view) {
        this.view=view;
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        appScene=new Scene(this.view,visualBounds.getWidth(),visualBounds.getHeight());
       /*Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
       this.setScene(new Scene(view,visualBounds.getWidth(),visualBounds.getHeight()));*/
         this.setTitle(titleHeader);
         
         //this.initModality(Modality.APPLICATION_MODAL);
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
        
      //  List<User> usersInWorkspace=userService.getUsersInWorkspace(currentWorkspace);
      List<User> usersInWorkspace=userWorkspaceService.getUsersInWorkspace(currentWorkspace);
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
         //List<User> usersInWorkspace=userService.getUsersInWorkspace(currentWorkspace);
          List<User> usersInWorkspace=userWorkspaceService.getUsersInWorkspace(currentWorkspace);
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
        workspaceService.updateWorkspace(w.getId(), w);
        currentWorkspace=w;
        this.setTitle(titleHeader+" : "+currentWorkspace.getName()+" owner: "+currentWorkspace.getOwner().getInitials());
        AppProperties.setCurrentUser(u);
        ownerLabel.setText(currentWorkspace.getOwner().getInitials());
        workspaceLabel.setText(currentWorkspace.getName());
        refreshGuestList();
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
      
        
       System.out.println("fend.app.AppController.logout(): removing user: "+u.getInitials()+" from workspace: "+w.getName());
          //remove all entries in the user-workspace table where u=previousUser and workspace=W;
        userWorkspaceService.remove(u,w);
        
       
        currentWorkspace=w;
        if(u.equals(w.getOwner())){
            
            System.out.println("fend.app.AppController.logout(): elevating a guest to an owner");
          
            int i=elevation();
        
            if(i==0){
                w.setOwner(null);
                workspaceService.updateWorkspace(w.getId(), w);
                currentWorkspace=w;
                this.setTitle(titleHeader+" : "+currentWorkspace.getName()+" No more owners");
                ownerLabel.setText("");
            }else{
                currentWorkspace=w;
                this.setTitle(titleHeader+" : "+currentWorkspace.getName()+" owner: "+currentWorkspace.getOwner().getInitials());
                ownerLabel.setText(currentWorkspace.getOwner().getInitials());
                //ownerLabel.setText(currentWorkspace.getOwner().getInitials());
                workspaceLabel.setText(currentWorkspace.getName());
                refreshGuestList();
            }
                
        }
          
         List<User> usersInWorkspace=userService.getUsersInWorkspace(w);
         List<Workspace> workspacesForUser=workspaceService.getWorkspacesForUser(u);
        System.out.println("fend.app.AppController.logout(): workspace "+w.getName()+" has "+usersInWorkspace.size()+" users");
        System.out.println("fend.app.AppController.logout(): user: "+
                u.getInitials()+" is logged into "
                +workspacesForUser.size()+" workspaces");
       
        refreshGuestList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    private void createLoadWorkspaceButton() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void createNewWorkspaceButton() {
        double[] points=new double[]{31,50, 33,50, 33,33, 50,33, 50,31, 33,31, 33,14, 31,14,
 31,31, 14,31, 14,33, 31,33};
        Polyline plusSign=new Polyline(points);
        
        Group svg=new Group(
                
                createPath("M31.999,64C32,64,32,64,32.001,64c8.548,0,16.584-3.33,22" +
".627-9.373c6.044-6.043,9.371-14.08,9.371-22.628    s-3.328-16.584-9.371-22.627C4" +
"8.584,3.329,40.549,0,32.001,0c0,0-0.001,0-0.002,0C23.451,0,15.415,3.329,9.372,9." +
"373    c-6.044,6.044-9.371,14.081-9.371,22.628s3.328,16.584,9.371,22.627C15.416," +
"60.671,23.451,64,31.999,64z M10.786,10.787    C16.452,5.121,23.986,2,32.001,2c0." +
"001,0,0.001,0,0.002,0c8.012,0,15.546,3.121,21.211,8.786c5.666,5.666,8.785,13.2,8" +
".785,21.213    s-3.119,15.548-8.785,21.214S40.015,62,32.001,62h-0.002c-0.001,0-0" +
".001,0-0.002,0c-8.012,0-15.546-3.121-21.211-8.786    c-5.666-5.666-8.785-13.2-8." +
"785-21.213C2.001,23.987,5.12,16.453,10.786,10.787z", "#808285", "#808285")//,
                /* createPath("M493.566,511.703H17.84c-9.852,0-17.84-7.987-17.84-17.84V18.137c0-9.852,7.987-17.84,17.84-17.84" +
                "s17.84,7.987,17.84,17.84v457.886h457.886c9.852,0,17.84,7.987,17.84,17.84C511.405,503.715,503.418,511.703,493.566,511.703z", "#A7A9AC", "#A7A9AC"),
                createPath("M493.566,100.2h-40.437v411.503h40.437c9.852,0,17.84-7.987,17.84-17.84V118.04\n" +
                "	C511.405,108.187,503.418,100.2,493.566,100.2z", "#48A792", "#48A792"),
                createPath("M493.566,511.703h-79.684c-9.852,0-17.84-7.987-17.84-17.84V118.04c0-9.852,7.987-17.84,17.84-17.84\n" +
                "	h79.684c9.852,0,17.84,7.987,17.84,17.84v375.823C511.405,503.715,503.418,511.703,493.566,511.703z", "#85EDC1", "#85EDC1"),
                createPath("M335.387,225.078H294.95v286.625h40.437c9.852,0,17.84-7.987,17.84-17.84V242.918\n" +
                "	C353.226,233.065,345.239,225.078,335.387,225.078z", "#FF9900", "#FF9900"),
                createPath("M335.387,511.703h-79.684c-9.852,0-17.84-7.987-17.84-17.84V242.918c0-9.852,7.987-17.84,17.84-17.84\n" +
                "	h79.684c9.852,0,17.84,7.987,17.84,17.84v250.945C353.226,503.715,345.239,511.703,335.387,511.703z", "#FFDB2D", "#FFDB2D"),
                createPath("M176.019,351.145h-40.437v160.557h40.437c9.852,0,17.84-7.987,17.84-17.84V368.985\n" +
                "	C193.858,359.133,185.871,351.145,176.019,351.145z", "#FC0023", "#FC0023"),
                createPath("M176.019,511.703H96.334c-9.852,0-17.84-7.987-17.84-17.84V368.985c0-9.852,7.987-17.84,17.84-17.84\n" +
        "	h79.684c9.852,0,17.84,7.987,17.84,17.84v124.878C193.858,503.715,185.871,511.703,176.019,511.703z", "#FF4F19", "#FF4F19")*/
                
               // createPath("M20,20h60v60h-60z", "red", "darkred")
        );
        Bounds bounds=svg.getBoundsInParent();
        double scale=Math.min(80/bounds.getWidth(),80/bounds.getHeight());
        svg.setScaleX(scale);
        svg.setScaleY(scale);
        
        newWorkspace.setGraphic(svg);
        /* newWorkspace.setMaxSize(157,48);
        newWorkspace.setMinSize(157, 48);*/
        newWorkspace.setStyle("-fx-background-radius : 50em;"
                + "-fx-border-radius:50em;"
                + "-fx-padding: 0px;"
                + "-fx-border-width: 1;");
        //newWorkspace.getStylesheets().add("")
        newWorkspace.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
    
    private ChangeListener<Boolean> ENABLE_BUTTONS_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                acqButton.setDisable(false);
                segdButton.setDisable(false);
                button2D.setDisable(false);
                textButton.setDisable(false);
                summaryButton.setDisable(false);
                chartButton.setDisable(false);
            }else{
                acqButton.setDisable(true);
                segdButton.setDisable(true);
                button2D.setDisable(true);
                textButton.setDisable(true);
                summaryButton.setDisable(true);
                chartButton.setDisable(true);
            }
        }
    };

    void onClose() {
        if(currentUser!=null) previousUser=currentUser;
        logout();
        currentWorkspace=null;
        close();
        
    }
    

    
    
    
   
    private ScheduledService<Void> guestService= new ScheduledService<Void>() {
                @Override
                protected Task<Void> createTask() {
                   // System.out.println("fend.app.AppController.startGuestService().createTask()..starting a task");
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            List<User> usersInWorkspace = userWorkspaceService.getUsersInWorkspace(currentWorkspace);
                            AppController.this.observableGuestList.clear();
                            User owner = null;
                            if (currentWorkspace != null) {
                                owner = currentWorkspace.getOwner();
                            }
                            for (int i = 0; i < usersInWorkspace.size(); i++) {
                                User usr = usersInWorkspace.get(i);
                                if (!usr.equals(owner)) {
                                    AppController.this.observableGuestList.add(usersInWorkspace.get(i).getInitials());
                                }
                                //System.out.println(usersInWorkspace.get(i).getInitials());
                            }

                            if (observableGuestList.isEmpty()) {
                                observableGuestList.add("None");
                            }
                            // 
                            return null;
                        }
                    };
                }
            };
    
    private Task<Void> guestTask=new Task<Void>() {
        @Override
        protected Void call() throws Exception {
             List<User> usersInWorkspace=userWorkspaceService.getUsersInWorkspace(currentWorkspace);
                  AppController.this.observableGuestList.clear();
                  User owner=null;
                  if(currentWorkspace!=null){
                      owner=currentWorkspace.getOwner();
                  }
                for (int i = 0; i < usersInWorkspace.size(); i++) {
                    User usr=usersInWorkspace.get(i);
                    if(!usr.equals(owner))
                    AppController.this.observableGuestList.add(usersInWorkspace.get(i).getInitials());
                    //System.out.println(usersInWorkspace.get(i).getInitials());
                }
                
                if(observableGuestList.isEmpty()){
                    observableGuestList.add("None");
                }
               // 
               return null;
        }
    };
    
    private void refreshGuestList(){
      
        if(!guestService.isRunning()){
            //System.out.println("fend.app.AppController.refreshGuestList(): starting");
            guestService.start();
            //startGuestService();
        }else{
            //System.out.println("fend.app.AppController.refreshGuestList(): restarting");
           // guestService.restart();
            
           // startGuestService();
        }
        
        
        
    }
    
    private ChangeListener<Boolean> GUEST_LIST_CHANGED=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println("fend.app.AppController.guestChangeListener: invoked");
                guestList.getItems().clear();
                
        }
    };
    
    
}
