/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.user.newUser;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath
 */
public class NewUserView extends AnchorPane{

    
        
   
    
    private FXMLLoader fXMLLoader;
    private final URL location;
    private NewUserController newUserController;
    
    public NewUserView(NewUserModel newUserModel) {
    
       
        //this.location=LandingController.class.getResource("landingView/LandingView.fxml"); 
        this.location=getClass().getClassLoader().getResource("fxml/user/newUser/newUser.fxml"); 
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                newUserController=(NewUserController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
               
               // sc.setId(Long.valueOf(getId()));
                
                newUserController.setModel(newUserModel);
                newUserController.setView(this) ;
                
               
                System.out.println("landing.reporter.ReporterNode.<init>()");
                
            }catch(IOException e){
                throw new RuntimeException(e);
            } 
        }
    
    }

