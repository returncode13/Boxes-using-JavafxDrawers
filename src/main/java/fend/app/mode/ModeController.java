/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app.mode;

import app.properties.AppProperties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ModeController extends Stage{
    ModeView view;
    ModeModel model;
    Scene scene;
    
    @FXML
    private Button vesselButton;

    @FXML
    private Button officeBtn;

    @FXML
    private Button developerBtn;

    @FXML
    void developerBtnClicked(ActionEvent event) {
            AppProperties.setMODE(AppProperties.DEVELOPER_MODE);
            close();
            model.setModeChosen(true);
           
    }

    @FXML
    void officeBtnClicked(ActionEvent event) {
            AppProperties.setMODE(AppProperties.OFFICE_MODE);
            close();
            model.setModeChosen(true);
    }

    @FXML
    void vesselButtonClicked(ActionEvent event) {
            AppProperties.setMODE(AppProperties.PRODUCTION_MODE);
            close();
            model.setModeChosen(true);
    }
    
    
    
    void setModel(ModeModel mod) {
       model=mod;
       Image vesselImage=new Image(getClass().getResourceAsStream("/icons/vessel.png"));
       vesselButton.setGraphic(new ImageView(vesselImage));
       
       Image officeImage=new Image(getClass().getResourceAsStream("/icons/office.png"));
       officeBtn.setGraphic(new ImageView(officeImage));
       
       Image devImage=new Image(getClass().getResourceAsStream("/icons/dev.png"));
       developerBtn.setGraphic(new ImageView(devImage));
       
    }

    void setView(ModeView vw) {
        view=vw;
        scene=new Scene(view);
        this.setScene(scene);
        
            scene.addEventHandler(KeyEvent.KEY_PRESSED, e->{
                if(e.getCode() == KeyCode.ESCAPE){
                    close();
                }
            });
        
        this.initStyle(StageStyle.TRANSPARENT);
        this.setTitle("Choose your team");
        this.show();
        
        
    }
    
}
