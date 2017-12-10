/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app.loading;

import db.model.Workspace;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath
 */
public class LoadWorkspaceModel {
    
    private ObservableList<Workspace> listOfWorkspace;
    private Workspace workspaceToBeLoaded;
    
    
    public LoadWorkspaceModel(ObservableList<Workspace> listOfWorkspace) {
        this.listOfWorkspace = listOfWorkspace;
    }

    public Workspace getWorkspaceToBeLoaded() {
        return workspaceToBeLoaded;
    }

    public void setWorkspaceToBeLoaded(Workspace workspaceToBeLoaded) {
        this.workspaceToBeLoaded = workspaceToBeLoaded;
    }
    
    
    

    ObservableList<Workspace> getList() {
        return listOfWorkspace;
    }

    
    
    
    
    
    
}
