/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.workspace.saveworkspace;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sharath
 */
public class SaveWorkspaceModel {
    
    private  StringProperty name=new SimpleStringProperty();

    public StringProperty getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
    
    
}
