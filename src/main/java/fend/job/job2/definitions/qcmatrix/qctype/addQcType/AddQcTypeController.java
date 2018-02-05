/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job2.definitions.qcmatrix.qctype.addQcType;


import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author adira0150
 */
public class AddQcTypeController extends Stage{
    private AddQcTypeModel model;
    private AddQcTypeNode node;
    
    
    
    @FXML
    private Button doneBtn;

    
     @FXML
    private Button cancel;

    @FXML
    private JFXTextField textf;

    @FXML
    void onCancel(ActionEvent event) {
        close();
    }
    
    
    @FXML
    void onDone(ActionEvent event) {
        String name=textf.getText();
        model.setQctypeName(name);
        close();
    }

    void setModel(AddQcTypeModel m) {
       this.model=m;
    }

    void setView(AddQcTypeNode aThis) {
        node=aThis;
        this.setScene(new Scene(node));
        this.showAndWait();
    }
    
    
   
}
