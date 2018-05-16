/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job4.definitions.qcmatrix;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.StackPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixType4View extends StackPane{
    private  QcMatrixType4Model model;
    private QcMatrixType4Controller  controller;
    
     private FXMLLoader fXMLLoader;
    private final URL location;
    
    
    public QcMatrixType4View(QcMatrixType4Model item){
       model=item;
        this.location=getClass().getClassLoader().getResource("fxml/job4/definitions/qcmatrix/qcmatrix.fxml"); 
       
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                controller=(QcMatrixType4Controller)fXMLLoader.getController();
                controller.setModel(this.model);
                controller.setView(this) ;
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }
}
