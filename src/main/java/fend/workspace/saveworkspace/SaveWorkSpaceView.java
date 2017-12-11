/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.workspace.saveworkspace;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 *
 * @author sharath
 */
public class SaveWorkSpaceView extends GridPane{
    private FXMLLoader fXMLLoader;
    private final URL location;
    private SaveWorkspaceController sc;
    
    public SaveWorkSpaceView(SaveWorkspaceModel sm)
    {
       
        //this.location=LandingController.class.getResource("landingView/LandingView.fxml"); 
        this.location=getClass().getClassLoader().getResource("fxml/workspace/saveSession.fxml"); 
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                sc=(SaveWorkspaceController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
               
               // sc.setId(Long.valueOf(getId()));
                sc.setModel(sm);
                sc.setView(this) ;
                
               
               
                System.out.println("landing.SaveSessionNode.<init>()");
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }

    public SaveWorkspaceController getSaveSessionController() {
        return sc;
    }
    
    
}
