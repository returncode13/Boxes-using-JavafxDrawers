/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.dot.variableArgument;

import db.model.Job;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class VariableArgumentModel {
   private Set<Job> lhsArgs=new HashSet<>();
   private Set<Job> rhsArgs=new HashSet<>();
   private Set<String> lhsVariables=new HashSet<>();            //strings of form y0->y99999..
   private Set<String> rhsVariables=new HashSet<>();            //strings of form x0->x99999..
   private ObservableSet<Job> observableLhsArgs=FXCollections.observableSet(lhsArgs);
   private ObservableSet<Job> observableRhsArgs=FXCollections.observableSet(rhsArgs);
   private Map<String,Job> variableArgumentMap=new TreeMap<>((o1, o2) -> {
       return o1.contains("x") && o2.contains("x") ? o1.compareTo(o2) : o2.compareTo(o1);     //need the terms in the order y -> x0 -> x1 -> x2
   });
   private String info=new String(); 
    
    
    public ObservableSet<Job> getObservableLhsArgs() {
        if(observableLhsArgs==null){
            return observableLhsArgs=FXCollections.observableSet();
        }
        return observableLhsArgs;
    }

    public void setObservableLhsArgs(ObservableSet<Job> observableLhsArgs) {
        this.observableLhsArgs = observableLhsArgs;
    }

    public ObservableSet<Job> getObservableRhsArgs() {
        if(observableRhsArgs==null){
            return observableRhsArgs=FXCollections.observableSet();
        }
        return observableRhsArgs;
    }

    public void setObservableRhsArgs(ObservableSet<Job> observableRhsArgs) {
        this.observableRhsArgs = observableRhsArgs;
    }
    
    
    public String getInfo() {
        info=new String();
        
        for (Map.Entry<String, Job> entry : variableArgumentMap.entrySet()) {
            String variable = entry.getKey();
            Job argument = entry.getValue();
            String space=" ";
            if(!variable.equals("y")){
                info+=variable+" = "+argument.getNameJobStep()+"\n";
            }else{
                info+=space+variable+" = "+argument.getNameJobStep()+"\n";
            }
            
        }
        return info;
    }

    public void setVariableArgumentMap(Map<String, Job> variableArgumentMap) {
        this.variableArgumentMap = variableArgumentMap;
    }
    
    

    public Map<String, Job> getVariableArgumentMap() {
        if(variableArgumentMap==null){
            return variableArgumentMap=new HashMap<>();
        }
        return variableArgumentMap;
    }

    

}
