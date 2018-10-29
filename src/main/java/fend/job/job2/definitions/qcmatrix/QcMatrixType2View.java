/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job2.definitions.qcmatrix;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.StackPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixType2View extends StackPane{
    private  QcMatrixType2Model model;
    private QcMatrixType2Controller  controller;
    
     private FXMLLoader fXMLLoader;
    private final URL location;
    
    
    public QcMatrixType2View(QcMatrixType2Model item){
       model=item;
        this.location=getClass().getClassLoader().getResource("fxml/job2/definitions/qcmatrix/qcmatrix.fxml"); 
       
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                controller=(QcMatrixType2Controller)fXMLLoader.getController();
                controller.setModel(this.model);
                controller.setView(this) ;
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }
}
