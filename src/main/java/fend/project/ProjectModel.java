/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.project;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author sharath
 */
public class ProjectModel {
    private List<String> availableDatabases=new ArrayList<>();
    private String selectedDatabase=new String();
    private BooleanProperty databaseSelected=new SimpleBooleanProperty(false);

    public Boolean getDatabaseSelected() {
        return databaseSelected.get();
    }

    public void setDatabaseSelected(Boolean ds) {
        this.databaseSelected.set(ds);
    }
    
    public BooleanProperty databaseSelectedProperty(){
        return databaseSelected;
    }
    
    
    
    
    
    public String getSelectedDatabase() {
        return selectedDatabase;
    }

    public void setSelectedDatabase(String selectedDatabase) {
        this.selectedDatabase = selectedDatabase;
    }
    
    

    public List<String> getAvailableDatabases() {
        return availableDatabases;
    }

    public void setAvailableDatabases(List<String> availableDatabases) {
        this.availableDatabases = availableDatabases;
    }
}
