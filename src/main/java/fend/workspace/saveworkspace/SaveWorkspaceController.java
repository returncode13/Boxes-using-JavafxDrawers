/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.workspace.saveworkspace;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author sharath
 */
public class SaveWorkspaceController extends Stage{
    
    SaveWorkspaceModel ssm=new SaveWorkspaceModel();
    SaveWorkSpaceView ssnode;
   
    
    @FXML
    private Label messageLabel;
    
    @FXML
    private TextField saveText;

    @FXML
    private HBox actionParent;

    @FXML
    private Button saveButton;

    @FXML
    private HBox okParent;

    @FXML
    private Button cancelButton;

    @FXML
    void handleCancelButton(ActionEvent event) {
       close();
    }

    
    
    @FXML
    void handleSaveButtonClicked(ActionEvent event) {
            System.out.println("Save Clicked : "+saveText.getText());
            String sessname=saveText.getText();
            ssm.setName(sessname);
            close();
            
    }

    
    
    
    

    void setModel(SaveWorkspaceModel sm) {
        this.ssm=sm;
    }

    void setView(SaveWorkSpaceView aThis) {
        this.ssnode=aThis;
        this.setScene(new Scene(ssnode));
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
    }

   
    
    
    
    
}
