/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.fullLineTable;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class FullHeaderLineTableView extends AnchorPane{
    private  FullHeaderLineTableModel model;
    private FullHeaderLineTableController  controller;
   
     private FXMLLoader fXMLLoader;
    private final URL location;
    
    
    public FullHeaderLineTableView(FullHeaderLineTableModel item){
        
        this.location=getClass().getClassLoader().getResource("fxml/job/table/fullHeaderLineTable/fullHeaderLineTable.fxml"); 
       
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                controller=(FullHeaderLineTableController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
                
                
                controller.setModel(item);
                controller.setView(this) ;
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }
}
