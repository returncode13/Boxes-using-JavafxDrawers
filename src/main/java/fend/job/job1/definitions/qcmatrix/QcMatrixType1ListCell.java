/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job1.definitions.qcmatrix;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListCell;
import fend.job.job1.definitions.qcmatrix.qcmatrixrow.QcMatrixRowType1Model;
//import fend.job.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixType1ListCell extends JFXListCell<QcMatrixRowType1Model>{
    HBox hbox=new HBox();
   
    Pane pane=new Pane();
    JFXCheckBox  checkbox=new JFXCheckBox();
    
    
    public QcMatrixType1ListCell(){
       // super();
        checkbox.getStyleClass().add("-fx-background-color: #5264AE ");
        checkbox.getStyleClass().add("-jfx-button-type: RAISED");
        hbox.getChildren().addAll(checkbox);
        //HBox.setHgrow(pane, Priority.ALWAYS);
        //button.getStyleClass().add("checkbox-raised");
       
        checkbox.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println(".changed(): old:new "+oldValue+" "+newValue);
                
                getItem().setCheckedByUser(newValue);
                
            }
            
        });
        
        
        
        //this.getStyleClass().add("-fx-background-color: #5264AE ");
    }

   
    
    @Override
    protected void updateItem(QcMatrixRowType1Model qctype,boolean empty){
        super.updateItem(qctype, empty);
        if(qctype==null||empty){
            setText(null);
            setGraphic(null);
        }else{
            if(qctype.getCheckedByUser())
                checkbox.setSelected(true);
            else
                checkbox.setSelected(false);
            setText(qctype.getName().get());
            setGraphic(hbox);
        }
    }
    
  
}
