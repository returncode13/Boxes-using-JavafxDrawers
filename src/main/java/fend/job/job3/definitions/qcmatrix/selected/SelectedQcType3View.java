/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job3.definitions.qcmatrix.selected;

import com.jfoenix.controls.JFXDrawersStack;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SelectedQcType3View extends JFXDrawersStack {
    
    private SelectedQcType3Model model; 
    private SelectedQcType3Controller controller;
    
    private FXMLLoader fXMLLoader;
    private final URL location;

    public SelectedQcType3View( SelectedQcType3Model model) {
        
        this.model = model;
        this.location=getClass().getClassLoader().getResource("fxml/job3/definitions/qcmatrix/selected/selectedQc.fxml");
        
        fXMLLoader=new FXMLLoader();
        fXMLLoader.setLocation(location);
        fXMLLoader.setRoot(this);
        fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
        
        try{
            fXMLLoader.load(location.openStream());
            controller=(SelectedQcType3Controller)fXMLLoader.getController();
            controller.setModel(this.model);
            controller.setView(this);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    } 
    
    
    
    
}
