/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job3.definitions.qcmatrix;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListCell;
import fend.job.job3.definitions.qcmatrix.qcmatrixrow.QcMatrixRowType3Model;



import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixListType3Cell extends JFXListCell<QcMatrixRowType3Model>{
    HBox hbox=new HBox();
   
    Pane pane=new Pane();
    JFXCheckBox  checkbox=new JFXCheckBox();
    
    
    public QcMatrixListType3Cell(){
       // super();
        checkbox.getStyleClass().add("-fx-background-color: #5264AE ");
        checkbox.getStyleClass().add("-jfx-button-type: RAISED");
        hbox.getChildren().addAll(checkbox);
        //HBox.setHgrow(pane, Priority.ALWAYS);
        //button.getStyleClass().add("checkbox-raised");
       
        checkbox.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                getItem().setCheckedByUser(newValue);
                
            }
            
        });
        
        //this.getStyleClass().add("-fx-background-color: #5264AE ");
    }

   
    
    @Override
    protected void updateItem(QcMatrixRowType3Model qctype,boolean empty){
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
