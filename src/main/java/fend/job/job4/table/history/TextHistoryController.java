/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job4.table.history;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class TextHistoryController extends Stage{
    TextHistoryModel model;
    TextHistoryView view;
    
    @FXML 
    private JFXTextArea textArea;
           
    void setModel(TextHistoryModel m) {
        model=m;
        textArea.setText(model.getHistory());
    }

    void setView(TextHistoryView a) {
        view=a;
        this.setScene(new Scene(view));
        this.show();
    }
    
}
