/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job5.definitions.qcmatrix.selected;

import com.jfoenix.controls.JFXDrawersStack;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SelectedQcType5View extends JFXDrawersStack {
    
    private SelectedQcType5Model model; 
    private SelectedQcType5Controller controller;
    
    private FXMLLoader fXMLLoader;
    private final URL location;

    public SelectedQcType5View( SelectedQcType5Model model) {
        
        this.model = model;
        this.location=getClass().getClassLoader().getResource("fxml/job5/definitions/qcmatrix/selected/selectedQc.fxml");
        
        fXMLLoader=new FXMLLoader();
        fXMLLoader.setLocation(location);
        fXMLLoader.setRoot(this);
        fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
        
        try{
            fXMLLoader.load(location.openStream());
            controller=(SelectedQcType5Controller)fXMLLoader.getController();
            controller.setModel(this.model);
            controller.setView(this);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    } 
    
    
    
    
}
