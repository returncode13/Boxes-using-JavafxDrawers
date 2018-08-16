/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.context.log;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class HeaderLogView extends AnchorPane {
    private FXMLLoader fXMLLoader;
    private final URL location;
    private HeaderLogController controller;
    
    public HeaderLogView(HeaderLogModel lsm)
    {
       
        //this.location=LandingController.class.getResource("landingView/LandingView.fxml"); 
        this.location=getClass().getClassLoader().getResource("fxml/job/table/context/log/logView.fxml"); 
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                controller=(HeaderLogController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
               
               // sc.setId(Long.valueOf(getId()));
                
                controller.setModel(lsm);
                controller.setView(this) ;
                
               
               
                System.out.println("fend.session.node.headers.HeadersNode.<init>()");
                
            }catch(IOException e){
                throw new RuntimeException(e);
            } 
}

    public HeaderLogController getController() {
        return controller;
    }

    
}
