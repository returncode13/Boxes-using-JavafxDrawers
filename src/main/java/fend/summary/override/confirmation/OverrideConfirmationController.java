/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.override.confirmation;

import com.jfoenix.controls.JFXTextArea;
import db.model.DoubtStatus;
import db.model.DoubtType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import middleware.doubt.DoubtStatusModel;
import middleware.doubt.DoubtTypeModel;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class OverrideConfirmationController extends Stage{

    
    private OverrideConfirmationModel model;
    private OverrideConfirmationView view;
    
    
    
     @FXML
    private Label confirmationLabel;

    @FXML
    private JFXTextArea commentTextArea;
    
     @FXML
    private Label commentHeader;

    @FXML
    private Button overrideBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void cancelClicked(ActionEvent event) {
        close();
    }

    @FXML
    void overrideClicked(ActionEvent event) {
        String userComment=commentTextArea.getText();
        model.getOverrideModel().addToUserCommentStack(userComment);
        
        model.getOverrideModel().setConfirmation(true);
       
        close();
        
    }
    
    void setModel(OverrideConfirmationModel item) {
        this.model=item;
        String earlierStatus=this.model.getOverrideModel().getEarlierStatus();
        String newStatus=this.model.getOverrideModel().getStatus();
        if(!earlierStatus.equals(newStatus)){
            confirmationLabel.setText("Changing status from "+earlierStatus+" to "+newStatus);
            if(newStatus.equals(DoubtStatusModel.OVERRIDE)){
                commentHeader.setText("Please enter a comment for your override.");
            }else{
                commentHeader.setText("Please enter a comment for the undoing of the override");
            }
            
        }else{
            confirmationLabel.setText("No Change in status: Adding to the comment stack");
            
                commentHeader.setText("Please add to the comment stack ");
            
        }
        
    }

    void setView(OverrideConfirmationView vw) {
        this.view=vw;
        this.setTitle("Commit");
        this.setScene(new Scene(this.view));
       // this.initModality(Modality.APPLICATION_MODAL);
        this.show();
    }
    
}
