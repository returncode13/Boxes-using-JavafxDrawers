package fend.app.console;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sharath
 */
public class ConsoleModel {
    private String log="Nothing logged";

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
    
    public void addToLog(String s){
        log+=s;
    }
    
    
}
