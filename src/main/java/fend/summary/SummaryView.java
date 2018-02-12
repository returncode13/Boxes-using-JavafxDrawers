/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SummaryView extends AnchorPane{
    private  SummaryModel model;
    private SummaryController  controller;
    
     private FXMLLoader fXMLLoader;
    private final URL location;
    
    
    public SummaryView(SummaryModel item){
        this.model=item;
        this.location=getClass().getClassLoader().getResource("fxml/summary/summaryTable_subsurface.fxml"); 
       
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                controller=(SummaryController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
                 controller.setModel(item);
                    controller.setView(this) ;
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }

    public SummaryController getController() {
        return controller;
    }
    
}
