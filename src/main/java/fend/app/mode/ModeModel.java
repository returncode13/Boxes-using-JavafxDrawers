/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app.mode;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ModeModel {
    String mode;
    BooleanProperty chooseMode=new SimpleBooleanProperty(false);
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setModeChosen(boolean b) {
        chooseMode.set(b);
    }

    public BooleanProperty chooseModeProperty() {
        return chooseMode;
    }
    
    
}
