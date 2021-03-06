/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.dot.formulaField;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import db.model.Job;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

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
    Set<String> allowedVariables=new HashSet<>();
    
    void setModel(FormulaFieldModel lsm) {
        model=lsm;
       // model.getFunction().addListener(FUNCTION_IS_NOT_DEFINED_LISTENER);
        //function.textProperty().addListener(FUNCTION_IS_NOT_DEFINED_LISTENER);
        function.setText(model.getFunction().get());
        
        tolerance.setText(model.getTolerance().get()+"");
        error.setText(model.getError().get()+"");
        textArea.setText(model.getInfo());
        Map<String,Job> vamap=model.getDot().getVariableArgumentModel().getVariableArgumentMap();
        for (Map.Entry<String, Job> entry : vamap.entrySet()) {
            String var = entry.getKey();
            Job arg = entry.getValue();
            if(!var.equals("y")){
                allowedVariables.add(var);
            }
            
            
        }
        
        model.functionValidityProperty().addListener(FUNCTION_VALIDITY_LISTENER);
        model.limitsValidityProperty().addListener(LIMITS_VALIDITY_LISTENER);
       
        /*tolerance.textProperty().addListener(toleranceListener);
        error.textProperty().addListener(errorListener);
        function.textProperty().addListener(functionListener);*/
    }

    void setView(FormulaFieldView nd) {
         node=nd;
         this.setOnCloseRequest(e->{
            model.getDot().exitedFormulaField();
         });
         
        this.setScene(new Scene(node));
        this.show();
         
    }
    
    
     private ChangeListener<String> FUNCTION_IS_NOT_DEFINED_LISTENER=new ChangeListener<String>(){
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            
            System.out.println("fend.dot.formulaField.FUNCTION_IS_NOT_DEFINED: changed(): "+oldValue+"-->"+newValue);
            if(newValue.trim().isEmpty()){
                
                System.out.println(".changed(): warn user");
            }else{
                
                 System.out.println(".changed(): remove Warning");
            }
        }
       
     };
   
    @FXML
    void okClicked(ActionEvent event) {
        model.setFunction(function.getText());
        model.setTolerance(Double.valueOf(tolerance.getText()));
        model.setError(Double.valueOf(error.getText()));
        
        close();
        model.getDot().exitedFormulaField();
        if(model.getDot().warnUserProperty().get()){
            model.removeUserWarning();
        }
    }
    
    
     @FXML
    void onFunctionKeyReleased(KeyEvent event) {
         //System.out.println("fend.dot.formulaField.FormulaFieldController.onKeyReleased(): error checking...");
         String text=function.getText();
         try{
             if(text.length()>0){
             boolean pass=checkFunction(function.getText());
             model.setFunctionValidity(pass);
         }
         }catch(NullPointerException npe){
             function.setPromptText("start typing ");
         }
    }
    
    
    
     @FXML
    void onErrorKeyReleased(KeyEvent event) {
          String error=this.error.getText();
          if(error.length()>0){
              boolean pass=checkErrorTolerance();
              model.setLimitsValidity(pass);
          }else{
              model.setLimitsValidity(false);
          }
    }
    
     @FXML
    void onToleranceKeyReleased(KeyEvent event) {
           String tolerance=this.tolerance.getText();
          if(tolerance.length()>0){
              boolean pass=checkErrorTolerance();
              model.setLimitsValidity(pass);
          }else{
              model.setLimitsValidity(false);
          }
    }
    
    
    @FXML
    void cancel(ActionEvent event) {
        close();
        model.getDot().exitedFormulaField();
    }

    private boolean checkFunction(String text)  {
        boolean pass=false;
        Expression exp;
        ValidationResult res;
        try{
            exp= new ExpressionBuilder(text)
                            .variables(allowedVariables)
                            .build();
            res=exp.validate(false);
            pass=res.isValid();
        }catch(UnknownFunctionOrVariableException un){
            pass=false;
        }catch(IllegalArgumentException | EmptyStackException ia){
            pass=false;
        }
       
        return pass;
        
    }  
    
    private boolean checkErrorTolerance() {
        String error=this.error.getText();
        String tolerance=this.tolerance.getText();
        
        boolean pass=false;
        try{
            Double err=Double.valueOf(error);
            Double tol=Double.valueOf(tolerance);
             if(err>=tol){
                 pass=true;
             }else{
                 pass=false;
             }
        }catch(NumberFormatException nfe){
            pass=false;
        }
        
        return pass;
    }
    
    /**
     * Listeners
     **/
    
    private ChangeListener<Boolean> FUNCTION_VALIDITY_LISTENER = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(!newValue){
                function.setStyle("-fx-text-fill: red;");
                okButton.setDisable(true);
            }else{
                function.setStyle("-fx-text-fill: green;");
                okButton.setDisable(false);
            }
        }
    };
    
    
    private ChangeListener<Boolean> LIMITS_VALIDITY_LISTENER=new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(!newValue){
                error.setStyle("-fx-text-fill: red;");
                tolerance.setStyle("-fx-text-fill: red;");
                okButton.setDisable(true);
            }else{
                error.setStyle("-fx-text-fill: green;");
                tolerance.setStyle("-fx-text-fill: green;");
                okButton.setDisable(false);
            }
        }
        
    };
    
    
    

   
}
