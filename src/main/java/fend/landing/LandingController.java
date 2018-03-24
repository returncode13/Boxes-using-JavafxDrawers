/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.landing;

import app.connections.hibernate.Connections;
import app.properties.AppProperties;
import app.settings.database.DataBaseSettings;
import app.settings.database.DataBaseSettingsController;
import app.settings.database.DataBaseSettingsNode;
import app.settings.ssh.SShSettings;
import app.settings.ssh.SShSettingsController;
import app.settings.ssh.SShSettingsNode;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author sharath
 */
public class LandingController {
    
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
    
}
