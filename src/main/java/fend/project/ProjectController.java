/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.project;

import app.connections.hibernate.Connections;
import app.connections.hibernate.HibernateUtil;
import app.properties.AppProperties;
import app.settings.database.DataBaseSettings;
import app.settings.database.DataBaseSettingsController;
import app.settings.database.DataBaseSettingsNode;
import app.settings.ssh.SShSettings;
import app.settings.ssh.SShSettingsController;
import app.settings.ssh.SShSettingsNode;
import fend.app.AppModel;
import fend.app.AppView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author sharath
 */
public class ProjectController extends Stage{
    private ProjectModel model;
    private ProjectView view;
    
    
    @FXML
    private ComboBox<String> databaseComboBox;

    @FXML
    private Button ok;

    @FXML
    private Button exit;

    
    @FXML
    private Button dbsettings;

   
    
    
    @FXML
    void exit(ActionEvent event) {
        close();
    }

    @FXML
    void ok(ActionEvent event) {
        String chosenDatabase=databaseComboBox.getValue();
        System.out.println("fend.project.ProjectController.ok(): chosenDb: "+chosenDatabase);
        
        String url=AppProperties.PROJECT_URL;
        System.out.println("fend.project.ProjectController.ok(): url: "+url+chosenDatabase);
        
        String database=url+chosenDatabase;
         Map<String,String> persistenceMap=new HashMap<>();
            persistenceMap.put("javax.persistence.jdbc.url",database);
            
            EntityManagerFactory emfactory=Persistence.createEntityManagerFactory("PQMan",persistenceMap);
            HibernateUtil.setEntityManagerFactory(emfactory);
        System.out.println("fend.project.ProjectController.ok(): model.getDBProperty "+model.getDatabaseSelected());
        
        AppProperties.setProject(chosenDatabase);
        
        model.setDatabaseSelected(!model.getDatabaseSelected());
        
            /* AppModel appmodel=new AppModel();
            AppView appview=new AppView(appmodel);*/
        close();
    }
    
    
    
  
    
    
    @FXML
    void dbsettings(ActionEvent event) {
       
        InputStream is=null;
        try {
          DataBaseSettings  databaseSettingsModel=new DataBaseSettings();
            
           
            System.out.println("fend.landing.LandingController.dbsettings() looking for "+System.getProperty("user.home")+ "  file: "+Connections.dbSettingXml);
         //   logger.info("looking for  file: "+dbSettingXml+" under "+ System.getProperty("user.home"));
            File dbFile=new File(System.getProperty("user.home"),Connections.dbSettingXml);
            is = new FileInputStream(dbFile);
            try {
                JAXBContext contextObj = JAXBContext.newInstance(DataBaseSettings.class);
                
                //try unmarshalling the file. if the fields are not null. populate settingsmodel
                
                Unmarshaller unm=contextObj.createUnmarshaller();
                // DataBaseSettings dbsett=(DataBaseSettings) unm.unmarshal(dbFile);
                DataBaseSettings dbsett=(DataBaseSettings) unm.unmarshal(is);
                System.out.println("fend.landing.LandingController.dbsettings():   unmarshalled: "+dbsett.getChosenDatabase());
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
              
               ex.printStackTrace();
            }
        }
    }

    void setModel(ProjectModel mo) {
        model=mo;
        databaseComboBox.getItems().addAll(model.getAvailableDatabases());
        databaseComboBox.setValue(model.getSelectedDatabase());
        
    }

    void setView(ProjectView vw) {
        view=vw;
        this.setScene(new Scene(view));
       this.show();
    }
    
}
