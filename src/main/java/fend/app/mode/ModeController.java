/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app.mode;

import app.properties.AppProperties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    
    
    
     @FXML
    void highlightWithToolTipDev(MouseEvent event) {
            vesselButton.setEffect(null);
            officeBtn.setEffect(null);
            developerBtn.setEffect(new Bloom(0.5));
    }
    
     @FXML
    void highlightWithToolTipOffice(MouseEvent event) {
            vesselButton.setEffect(null);
            officeBtn.setEffect(new Bloom(0.5));
            developerBtn.setEffect(null);
    }
    
     @FXML
    void highlightWithToolTipVessel(MouseEvent event) {
            vesselButton.setEffect(new Bloom(0.5));
            officeBtn.setEffect(null);
            developerBtn.setEffect(null);

    }
    void setModel(ModeModel mod) {
       model=mod;
       Image vesselImage=new Image(getClass().getResourceAsStream("/icons/mode/paper_boat.png"));
       vesselButton.setGraphic(new ImageView(vesselImage));
       
       Image officeImage=new Image(getClass().getResourceAsStream("/icons/mode/burj_khalifa.png"));
       officeBtn.setGraphic(new ImageView(officeImage));
       
       Image devImage=new Image(getClass().getResourceAsStream("/icons/mode/dev_1.png"));
       developerBtn.setGraphic(new ImageView(devImage));
       
    }

     
            double xoffset=0;
            double yoffset=0;
            boolean drag=false;
            
    
    void setView(ModeView vw) {
        view=vw;
        scene=new Scene(view);
        this.setScene(scene);
        
            scene.addEventHandler(KeyEvent.KEY_PRESSED, e->{
                if(e.getCode() == KeyCode.ESCAPE){
                    close();
                }
            });
            
           scene.addEventFilter(MouseEvent.MOUSE_PRESSED, e->{
                xoffset=e.getSceneX();
               yoffset=e.getSceneY();
           });
            
          scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, e->{
              scene.setCursor(Cursor.HAND);
               this.setX(e.getScreenX()-xoffset);
               this.setY(e.getScreenY()-yoffset);
               drag=true;
               e.consume();
          });
          
          /*scene.addEventFilter(MouseEvent., eventFilter);*/
          
          scene.addEventFilter(MouseEvent.MOUSE_RELEASED, e->{
          scene.setCursor(Cursor.DEFAULT);
          if(drag){
              e.consume();
              drag=false;
          }
          
          });
          
          scene.addEventFilter(MouseEvent.MOUSE_ENTERED, e->{
              scene.setCursor(Cursor.HAND);
          });
          
          scene.addEventFilter(MouseEvent.MOUSE_CLICKED, e->{
              scene.setCursor(Cursor.HAND);
          });
           
          scene.addEventFilter(MouseEvent.MOUSE_EXITED, e->{
              scene.setCursor(Cursor.DEFAULT);
          });
          /*scene.setOnMousePressed(e->{
          xoffset=e.getSceneX();
          yoffset=e.getSceneY();
          });
          
          scene.setOnMouseDragged(e->{
          scene.setCursor(Cursor.HAND);
          this.setX(e.getScreenX()-xoffset);
          this.setY(e.getScreenY()-yoffset);
          
          });*/
           
          /*scene.setOnMouseReleased(e->{
          scene.setCursor(Cursor.DEFAULT);
          });
          */
        this.initStyle(StageStyle.TRANSPARENT);
        this.setTitle("Choose your team");
        this.show();
        
        
    }
    
}
