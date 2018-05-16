/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job4.table.history;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class TextHistoryView extends AnchorPane{
     private  TextHistoryModel model;
    private TextHistoryController  controller;
   
     private FXMLLoader fXMLLoader;
    private final URL location;
    
    
    public TextHistoryView(TextHistoryModel item){
        this.model=item;
        this.location=getClass().getClassLoader().getResource("fxml/job4/table/history/history.fxml"); 
       
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                controller=(TextHistoryController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
                
                
                controller.setModel(model);
                controller.setView(this) ;
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }
}
