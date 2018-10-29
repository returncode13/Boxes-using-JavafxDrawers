/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app.loading;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;


/**
 *
 * @author sharath
 */

public class LoadWorkspaceNode extends AnchorPane {

    private FXMLLoader fXMLLoader;
    private final URL location;
    private LoadWorkspaceController lsc;
    
    public LoadWorkspaceNode(LoadWorkspaceModel lsm)
    {
       
        //this.location=LandingController.class.getResource("landingView/LandingView.fxml"); 
        this.location=getClass().getClassLoader().getResource("fxml/app/loading/loadSession.fxml"); 
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                lsc=(LoadWorkspaceController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
               
               // sc.setId(Long.valueOf(getId()));
                
                lsc.setModel(lsm);
                lsc.setView(this) ;
                
               
               
                
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }



    public LoadWorkspaceController getLoadSessionController() {
        return lsc;
    }
    
}
