/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.definitions.qcmatrix;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListCell;
import fend.job.definitions.qcmatrix.qctype.QcMatrixRow;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import fend.volume.volume0.Volume0;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixListCell extends JFXListCell<QcMatrixRow>{
    HBox hbox=new HBox();
   
    Pane pane=new Pane();
    JFXCheckBox  checkbox=new JFXCheckBox();
    
    
    public QcMatrixListCell(){
       // super();
        checkbox.getStyleClass().add("-fx-background-color: #5264AE ");
        checkbox.getStyleClass().add("-jfx-button-type: RAISED");
        hbox.getChildren().addAll(checkbox);
        //HBox.setHgrow(pane, Priority.ALWAYS);
        //button.getStyleClass().add("checkbox-raised");
       
        
        
        //this.getStyleClass().add("-fx-background-color: #5264AE ");
    }

   
    
    @Override
    protected void updateItem(QcMatrixRow qctype,boolean empty){
        super.updateItem(qctype, empty);
        if(qctype==null||empty){
            setText(null);
            setGraphic(null);
        }else{
            setText(qctype.getName().get());
            setGraphic(hbox);
        }
    }
    
  
}
