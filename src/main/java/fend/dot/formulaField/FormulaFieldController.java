/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.dot.formulaField;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class FormulaFieldController extends Stage{
    
       
    @FXML
    private JFXTextField function;

     @FXML
    private JFXTextField tolerance;

    @FXML
    private JFXTextField error;

    @FXML
    private JFXButton okButton;
    
    @FXML
    private JFXButton cancel;

     @FXML
    private JFXTextArea textArea;
    
    FormulaFieldModel model;
    FormulaFieldView node;

    void setModel(FormulaFieldModel lsm) {
        model=lsm;
        function.setText(model.getFunction().get());
        tolerance.setText(model.getTolerance().get()+"");
        error.setText(model.getError().get()+"");
        textArea.setText(model.getInfo());
        
        /*tolerance.textProperty().addListener(toleranceListener);
        error.textProperty().addListener(errorListener);
        function.textProperty().addListener(functionListener);*/
    }

    void setView(FormulaFieldView nd) {
         node=nd;
        this.setScene(new Scene(node));
        this.showAndWait();
    }
    
    
   
    @FXML
    void okClicked(ActionEvent event) {
        model.setFunction(function.getText());
        model.setTolerance(Double.valueOf(tolerance.getText()));
        model.setError(Double.valueOf(error.getText()));
        
        close();
    }
    
    @FXML
    void cancel(ActionEvent event) {
        close();
    }
    /**
     * Listeners
     **/
    /*private ChangeListener<String> functionListener=new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    model.setFunction(newValue);
    }
    };
    
    private ChangeListener<String> errorListener=new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    try{
    model.setError(Double.valueOf(newValue));
    }catch(NumberFormatException nfe){
    model.setError(0.0);
    }
    }
    };
    
    private ChangeListener<String> toleranceListener=new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    try{
    model.setError(Double.valueOf(newValue));
    }catch(NumberFormatException nfe){
    model.setError(0.0);
    }
    }
    };*/
}
