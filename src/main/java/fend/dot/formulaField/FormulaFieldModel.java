/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.dot.formulaField;

import db.model.Dot;
import db.model.Job;
import db.model.Link;
import db.model.VariableArgument;
import db.model.Workspace;
import db.services.DotService;
import db.services.DotServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.VariableArgumentService;
import db.services.VariableArgumentServiceImpl;
import db.services.WorkspaceService;
import db.services.WorkspaceServiceImpl;
import fend.dot.DotModel;
import fend.dot.LinkModel;
import fend.job.job0.JobType0Model;
import fend.workspace.WorkspaceModel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;


/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class FormulaFieldModel {
   DotModel dot; 
   
   
   String info=new String();
   StringProperty function=new SimpleStringProperty("");                         //any valid mathematical function
   DoubleProperty rhsResult=new SimpleDoubleProperty(0.0);                                //result of the function
   DoubleProperty tolerance=new SimpleDoubleProperty(0.0);                               //in %
   DoubleProperty error=new SimpleDoubleProperty(0.0);                                    //in %
   /**
    * If abs(rhsResult)< tolerance  . set doubt=none
    * if tolerance<abs(rhsResult)<error.  set doubt=warning 
    * if error<abs(rhsResult). set doubt=error    
    **/
  
   
    
    Integer ilhs=0;
    private DotService dotService=new DotServiceImpl();
    private VariableArgumentService variableArgumentService=new VariableArgumentServiceImpl();

    private Dot dbDot;
    private Set<VariableArgument> dbVariableArguments;
    private Map<String,Job> variableArgumentMap=new HashMap<>();
    
    public FormulaFieldModel(DotModel dot) {
        this.dot = dot;
        //dbDot=dotService.getDot(dot.getId());
        dbDot=this.dot.getDatabaseDot();
               
        function.set(dbDot.getFunction());
        tolerance.set(dbDot.getTolerance()==null?0.0:dbDot.getTolerance());
        error.set(dbDot.getError()==null?0.0:dbDot.getError());
        //dbVariableArguments=dbDot.getVariableArguments();
        dbVariableArguments=new HashSet<>(variableArgumentService.getVariableArgumentsForDot(dbDot));
        variableArgumentMap.clear();
        for(VariableArgument va:dbVariableArguments){
            variableArgumentMap.put(va.getVariable(), va.getArgument());
        }
        
        function.addListener(formulaChangeListener);
        tolerance.addListener(toleranceChangeListener);
        error.addListener(errorChangeListener);
        
          
    }

    public StringProperty getFunction() {
        return function;
    }

    public void setFunction(String formula) {
        this.function.set(formula);
    }

    public DoubleProperty getRhsResult() {
        return rhsResult;
    }

    public void setRhsResult(Double rhsResult) {
        this.rhsResult.set(rhsResult);
    }

    public DoubleProperty getTolerance() {
        return tolerance;
    }

    public void setTolerance(Double tolerance) {
        this.tolerance.set(tolerance);
    }

    public DoubleProperty getError() {
        return error;
    }

    public void setError(Double error) {
        this.error.set(error);
    }

   

    public String getInfo(){
       return dot.getVariableArgumentModel().getInfo();
    }
    
    
    
    
   
    
    
    /*void update() {
    System.out.println("fend.dot.formulaField.FormulaFieldModel.update(): updating the dot");
    
    
    
    
    }*/
    
    
    
    /**
     * Listeners
     **/
    private final ChangeListener<String> formulaChangeListener=new ChangeListener<String>() {
       @Override
       public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
           dot.setFunction(function.get());
       }
   };
    
    private final ChangeListener<Number> toleranceChangeListener=new ChangeListener<Number>() {
       @Override
       public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
          dot.setTolerance(tolerance.get());
         
       }
   };
    
    
    /* private final ChangeListener<Double> errorChangeListener=new ChangeListener<Double>() {
    @Override
    public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
    dot.setError(error.get());
    }
    };*/
    private final ChangeListener<Number> errorChangeListener=new ChangeListener<Number>() {
       @Override
       public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
          dot.setError(error.get());
         
       }
   };
}
