/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job2.definitions.volume;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import fend.volume.volume0.Volume0;
import javafx.scene.control.Button;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class VolumeListCell extends JFXListCell<Volume0>{
    HBox hbox=new HBox();
   
    Pane pane=new Pane();
    Button  button=new Button("X");
    
    public VolumeListCell(){
       // super();
       button.getStylesheets().add("styles/bootstrap3.css");
        //button.getStyleClass().add("-fx-background-color: #5264AE ");
        button.getStyleClass().add("danger");
        hbox.getChildren().addAll(button);
        //HBox.setHgrow(pane, Priority.ALWAYS);
        //button.getStyleClass().add("button-raised");
       
        
        button.setOnAction(e->{
               getItem().delete(true);
               getListView().
                       getItems().
                       remove(getItem());
               
        });
        //this.getStyleClass().add("-fx-background-color: #5264AE ");
    }

   
    
    @Override
    protected void updateItem(Volume0 vol,boolean empty){
        super.updateItem(vol, empty);
        if(vol==null||empty){
            setText(null);
            setGraphic(null);
        }else{
            setText(vol.getName().get());
            setGraphic(hbox);
        }
    }
    
  
}
