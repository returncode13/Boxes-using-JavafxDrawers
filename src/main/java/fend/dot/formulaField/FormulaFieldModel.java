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
   String formula=new String();                         //any valid mathematical formula
   Double rhsResult=0.0;                                //result of the formula
   Double tolerance=0.0;                                //in %
   Double error=0.0;                                    //in %
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
        dbDot=dotService.getDot(dot.getId());
        formula=dbDot.getFormula();
        tolerance=dbDot.getTolerance();
        error=dbDot.getError();
        dbVariableArguments=dbDot.getVariableArguments();
        variableArgumentMap.clear();
        for(VariableArgument va:dbVariableArguments){
            variableArgumentMap.put(va.getVariable(), va.getArgument());
        }
        
          
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Double getTolerance() {
        return tolerance;
    }

    public void setTolerance(Double tolerance) {
        this.tolerance = tolerance;
    }

    public Double getError() {
        return error;
    }

    public void setError(Double error) {
        this.error = error;
    }

    public String getInfo(){
       return dot.getVariableArgumentModel().getInfo();
    }
    
    
    
    
   
    
    
    void update() {
        System.out.println("fend.dot.formulaField.FormulaFieldModel.update(): updating the dot");
        dot.setFormula(formula);
        dot.setError(error);
        dot.setTolerance(tolerance);
        /* dbDot.setFormula(formula);
        dbDot.setError(error);
        dbDot.setTolerance(tolerance);
        dotService.updateDot(dbDot.getId(), dbDot);*/
    }
    
    
    
    
}
