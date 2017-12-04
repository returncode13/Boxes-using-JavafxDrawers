/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package box.definitions;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class BoxDefinitionsController {

    BoxDefinitionsModel model;
    BoxDefinitionsView view;
    
    
    @FXML
    private StackPane leftDrawerPane;
    
     @FXML
    private JFXTextField textf;

    void setModel(BoxDefinitionsModel item) {
        model=item;
    }

    void setView(BoxDefinitionsView vw) {
        view=vw;
        
    }
    
}
