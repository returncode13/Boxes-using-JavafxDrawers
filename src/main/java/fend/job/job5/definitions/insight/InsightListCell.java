/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job5.definitions.insight;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class InsightListCell extends JFXListCell<InsightVersion> {
     HBox hbox=new HBox();
   
    Pane pane=new Pane();
    JFXCheckBox  checkbox=new JFXCheckBox();
    
    
    public InsightListCell(){
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
    protected void updateItem(InsightVersion insightVersion,boolean empty){
        super.updateItem(insightVersion, empty);
        if(insightVersion==null||empty){
            setText(null);
            setGraphic(null);
        }else{
            if(insightVersion.getCheckedByUser())
                checkbox.setSelected(true);
            else
                checkbox.setSelected(false);
            //System.out.println("fend.job.definitions.insight.InsightListCell.updateItem(): insightVersion : "+insightVersion.getInsightVersion());
            setText(insightVersion.getInsightVersion());
            setGraphic(hbox);
        }
    }
}
