/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dot.formulaField;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class FormulaFieldController extends Stage{
    

    @FXML
    private TextField formula;

    @FXML
    private TextField tolerance;

    @FXML
    private TextField error;

  
    
    FormulaFieldModel model;
    FormulaFieldNode node;

    void setModel(FormulaFieldModel lsm) {
        model=lsm;
    }

    void setView(FormulaFieldNode nd) {
         node=nd;
        this.setScene(new Scene(node));
        this.showAndWait();
    }
    
}
