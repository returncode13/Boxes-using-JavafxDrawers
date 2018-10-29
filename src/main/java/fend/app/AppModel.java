/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AppModel {
    private Long id;
    private BooleanProperty reloadProperty=new SimpleBooleanProperty(false);
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BooleanProperty reloadProperty() {
        return reloadProperty;
    }

    public void reload() {
        Boolean val=reloadProperty.get();
        this.reloadProperty.set(!val);
    }

    BooleanProperty blockProperty=new SimpleBooleanProperty(false);

    public BooleanProperty blockProperty() {
        return blockProperty;
    }
    
    
    public void block() {
       blockProperty.set(true);
    }
    
    public void unblock(){
        blockProperty.set(false);
    }

    BooleanProperty summaryBlockProperty=new SimpleBooleanProperty(false);

    public BooleanProperty summaryBlockProperty() {
        return summaryBlockProperty;
    }
    
    public void blockSummary() {
        summaryBlockProperty.set(true);
    }

    public void unblockSummary() {
        summaryBlockProperty.set(false);
    }
}
