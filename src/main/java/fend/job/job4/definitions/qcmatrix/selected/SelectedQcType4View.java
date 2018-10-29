/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job4.definitions.qcmatrix.selected;

import com.jfoenix.controls.JFXDrawersStack;
import fend.job.job4.definitions.qcmatrix.selected.SelectedQcType4Model;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SelectedQcType4View extends JFXDrawersStack {
    
    private SelectedQcType4Model model; 
    private SelectedQcType4Controller controller;
    
    private FXMLLoader fXMLLoader;
    private final URL location; 

    public SelectedQcType4View( SelectedQcType4Model model) {
        
        this.model = model;
        this.location=getClass().getClassLoader().getResource("fxml/job4/definitions/qcmatrix/selected/selectedQc.fxml");
        
        fXMLLoader=new FXMLLoader();
        fXMLLoader.setLocation(location);
        fXMLLoader.setRoot(this);
        fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
        
        try{
            fXMLLoader.load(location.openStream());
            controller=(SelectedQcType4Controller)fXMLLoader.getController();
            controller.setModel(this.model);
            controller.setView(this);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    } 
    
    
    
    
}
