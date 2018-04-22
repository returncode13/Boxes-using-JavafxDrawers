/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app.mode;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ModeView extends AnchorPane{
     private FXMLLoader fXMLLoader;
    private final URL location;
    private ModeController controller;
    
    public ModeView(ModeModel mod)
    {
       
        //this.location=LandingController.class.getResource("landingView/LandingView.fxml"); 
        this.location=getClass().getClassLoader().getResource("fxml/app/mode/mode.fxml");
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                controller=(ModeController)fXMLLoader.getController();
             
                controller.setModel(mod);
                controller.setView(this) ;
                
               
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }

    public ModeController getController() {
        return controller;
    }
    
    public void close(){
        this.close();
    }
    
}
