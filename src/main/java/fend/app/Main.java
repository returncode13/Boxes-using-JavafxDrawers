/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app;

import app.connections.hibernate.Connections;
import app.connections.hibernate.HibernateUtil;
import app.connections.manager.ssh.SSHManager;
import app.properties.AppProperties;
import app.settings.database.DataBaseSettingsController;
import app.settings.ssh.SShSettings;
import app.settings.ssh.SShSettingsController;
import app.settings.ssh.SShSettingsNode;
import fend.app.AppModel;
import fend.app.AppView;
import fend.app.mode.ModeModel;
import fend.app.mode.ModeView;
import fend.project.ProjectModel;
import fend.project.ProjectView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class Main extends Application{
    
    private static SSHManager instance;
    private String connectionIP;
    private String userName;
    private String password;
    private static int dbPortOnRemote=AppProperties.APPLICATION_PORT_ON_REMOTE;
    
    
    final private String URL_TEMPLATE=AppProperties.URLTEMPLATE_FOR_DATABASE_LISTING;
    private Stage primaryStage;
    private ModeView modeView;
    
    private String dbUser=AppProperties.DATABASE_USER;
    private String dbPassword="";
    
    private final String STATEMENT="SELECT datname FROM pg_database WHERE datistemplate=false;"; 
    private ArrayList<String> dbList=new ArrayList<>();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        this.primaryStage=primaryStage;
        
        ModeModel modeModel=new ModeModel();
        modeModel.chooseModeProperty().addListener(MODE_LISTENER);
        modeView=new ModeView(modeModel);
       
       
    }

    private int connectToServer() {
            
        File sFile=new File(System.getProperty("user.home"),Connections.sshSettingXml);
         
         JAXBContext contextObj;
        try {
         contextObj = JAXBContext.newInstance(SShSettings.class);
         Unmarshaller unm=contextObj.createUnmarshaller();
         SShSettings sett=(SShSettings) unm.unmarshal(sFile);
         
         if(!sett.isPopulated()){
             System.err.println("Warning!: SSH Settings not Found! under "+sFile);
             return 0;
         }
         else{
             connectionIP=sett.getSshHost();
             userName=sett.getSshUser();
             password=sett.getSshPassword();
         }
        }catch (JAXBException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        instance = new SSHManager(userName, password, connectionIP, "");
    String errorMessage = instance.connect();
       
        
        try{
           
              if(errorMessage != null)
                {
                 System.out.println("Failed to connect via SSH instance :"+errorMessage);
                 return 0;
       // fail();
                }else
                   {
         String errorMessage1=instance.setPortForwarding(AppProperties.APPLICATION_PORT_ON_LOCAL, dbPortOnRemote);
         System.out.println(errorMessage1);
         
     }
    }
        catch(Throwable ex){
           
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
        
        
       
        //instance.close();
        return 1;
        

    }

    private void openSshSettingsView() {
        
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
                
                int connectStatus=connectToServer();
                if(connectStatus==0){
                    openSshSettingsView();
                }else{
                    listDatabasesAndConnect();
                }
                
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

    private void listDatabasesAndConnect() {
         try {
            Class.forName("org.postgresql.Driver").newInstance();
            Connection connection=DriverManager.getConnection(URL_TEMPLATE,dbUser,null);
           
            PreparedStatement ps=connection.prepareStatement(STATEMENT);
            ResultSet resultSet=ps.executeQuery();
            dbList.clear();
            while(resultSet.next()){
                dbList.add(resultSet.getString(1));
                System.out.println("landing.settings.database.DataBaseSettingsController.lookUpDatabases(): "+resultSet.getString(1));
                
            }
            
            
            
            resultSet.close();
            connection.close();
            
            
            ProjectModel projectModel=new ProjectModel();
            projectModel.setAvailableDatabases(dbList);
            projectModel.databaseSelectedProperty().addListener(DATABASE_SELECTION_LISTENER);
            ProjectView projectView=new ProjectView(projectModel);
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DataBaseSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DataBaseSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void stop(){
        System.out.println("fend.app.Main.stop(): stage is closing");
    }
    
    private ChangeListener<Boolean> DATABASE_SELECTION_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            System.out.println(".changed(): opening the application");
                AppModel appmodel=new AppModel();
                AppView appview=new AppView(appmodel);
                Scene scene=appview.getController().getAppScene();
                String title=appview.getController().getTitle();
                primaryStage.setTitle(title);
                primaryStage.setScene(scene);
                primaryStage.show();
                primaryStage.setOnCloseRequest(e->{
                    appview.getController().onClose();
                    System.out.println(".changed(): closing primaryStage");
                    primaryStage.close();
                    System.out.println(".changed(): calling exit on Platform");
                    Platform.exit();
                });
        }
    };
    
    private ChangeListener<Boolean> MODE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue) {
                //Main.this.modeView.close();
                int connectStatus = connectToServer();
                if (connectStatus == 0) {            //no shh connection established
                    openSshSettingsView();      //open ssh setting panel

                } else {                          //successful ssh tunnel
                    listDatabasesAndConnect();    //list databases
                }

            }
        }
    };
}
