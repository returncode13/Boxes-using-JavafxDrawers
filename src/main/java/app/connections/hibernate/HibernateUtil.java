/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.connections.hibernate;

import app.properties.AppProperties;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import app.settings.database.DataBaseSettings;
import app.settings.ssh.SShSettings;
import org.hibernate.SessionFactory;
import app.connections.manager.ssh.SSHManager;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class HibernateUtil {
    private static  SessionFactory sessionFactory;
    private static  EntityManagerFactory emfactory;  /* Dubai Params
     private static String command = "ls";
     private static String userName = "irdb";
     private static String password = "rdV9BPk9WS";
     private static String connectionIP = "10.11.1.39";     //vm in Dubai
     
     private static SSHManager instance; 
     private static int sshLocalPort=5432;
     private static int dbPort=5432;
     String strDbUSer="postgres";  //Dubai   . CHANGE this in the file hibernate.cfg.xml
     String DBPass="";  //Dubai      CHANGE this in the file hibernate.cfg.xml
    
    ALSO change the localport:1111 to localport:5432 in the file hibernate.cfg.xml when using for Dubai
    
    
    */ //End of Dubai Params
     
     /*
     Kiba Params
     */
     private static String command = "ls";
     private static String userName = "fgeo";
     private static String password = "Polarcus123";
     private static String connectionIP = "10.105.1.38";     
     private static SSHManager instance; 
     private static int sshLocalPort=AppProperties.APPLICATION_PORT_ON_LOCAL;    //bind anduril and Kiba
     //private static int sshLocalPort=5432;    //bind local on polarcus machines
     private static int dbPort=5432;
     private static String strDbUSer="fgeo";  //Kiba   . CHANGE this in the file hibernate.cfg.xml
     private static String DBPass="";  //Kiba      CHANGE this in the file hibernate.cfg.xml
     
     private static String database="";
     
     
    /*
     End of Kiba Params
     */
    
     
     
     
     
     
     
     
    private static SessionFactory buildSessionFactory(){
       /* System.out.println("hibUtil.HibernateUtil.buildSessionFactory() : Loading the connection configurations "+Connections.sshSettingXml);
        
         File sFile=new File(System.getProperty("user.home"),Connections.sshSettingXml);
         File dbFile=new File(System.getProperty("user.home"),Connections.dbSettingXml);
         
         JAXBContext contextObj;
         JAXBContext dbcontext;
        try {
         contextObj = JAXBContext.newInstance(SShSettings.class);
         Unmarshaller unm=contextObj.createUnmarshaller();
         SShSettings sett=(SShSettings) unm.unmarshal(sFile);
         
         if(!sett.isPopulated()){
             System.err.println("Settings not Found!");
         }
         else{
             connectionIP=sett.getSshHost();
             userName=sett.getSshUser();
             password=sett.getSshPassword();
             strDbUSer=sett.getDbUser();                // this doesnt matter. since its specified in the file persistence.xml
             DBPass=sett.getDbPassword();               //ditto for this as well.
             
             
             
         }
         
         
         dbcontext = JAXBContext.newInstance(DataBaseSettings.class);
         Unmarshaller dbunm=dbcontext.createUnmarshaller();
         DataBaseSettings dbsett=(DataBaseSettings)dbunm.unmarshal(dbFile);
         
         database=dbsett.getChosenDatabase();
         String parts[]=dbsett.getChosenDatabase().split("/");
                AppProperties.setProject(parts[parts.length-1]);
            
         
        } catch (JAXBException ex) {
            Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
        
        
        
        System.out.println("HibernateUtil called using connection settings:");
        System.out.println("hibUtil.HibernateUtil.buildSessionFactory():  HOST : "+connectionIP);
        System.out.println("hibUtil.HibernateUtil.buildSessionFactory():  USER : "+userName);
        System.out.println("hibUtil.HibernateUtil.buildSessionFactory():  PASS : "+password);
        System.out.println("hibUtil.HibernateUtil.buildSessionFactory():  DBASE: "+database);
        
        
        
        
        instance = new SSHManager(userName, password, connectionIP, "");
    String errorMessage = instance.connect();
       
        
        try{
           
              if(errorMessage != null)
                {
                 System.out.println("Failed to connect via SSH instance :"+errorMessage);
       // fail();
                }else
                   {
         String errorMessage1=instance.setPortForwarding(sshLocalPort, dbPort);
         System.out.println(errorMessage1);
     }

    

              /* Map<String,String> persistenceMap=new HashMap<>();
              persistenceMap.put("javax.persistence.jdbc.url",database);
              
              
              emfactory=Persistence.createEntityManagerFactory("PQMan",persistenceMap);*/
          //  String result = instance.sendCommand(command);
         
            sessionFactory=emfactory.unwrap(SessionFactory.class);
          //  System.out.println(result);
           return sessionFactory;
           
           /*  }
           catch(Throwable ex){
           System.err.println("Initial SessionFactory Creation failed");
           ex.printStackTrace();
           throw new ExceptionInInitializerError(ex);
           }*/
    }
    
  
    
    public static SessionFactory getSessionFactory() {
        if(sessionFactory==null ){sessionFactory=buildSessionFactory();if(sessionFactory!=null) System.out.println("returning sessionFactory from getSessionFactory");}
        return sessionFactory;
    }
    
    public static void setEntityManagerFactory(EntityManagerFactory em){
        emfactory=em;
    }
    
}
