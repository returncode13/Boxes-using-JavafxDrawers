/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app.console;

/**
 *
 * @author sharath
 */
public class ConsoleController {

    ConsoleModel model;
    ConsoleView view;
    
    
    void setModel(ConsoleModel m) {
        model=m;
    }

    void setView(ConsoleView vw) {
        view=vw;
    }
    
}
