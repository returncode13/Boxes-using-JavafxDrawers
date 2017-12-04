/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basewindow;

import box.BoxModel;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class BaseWindowModel {

    public final static boolean DEBUG=true;
   private  List<BoxModel> boxes=new ArrayList<>(); 
    private ObservableList<BoxModel> observableBoxes=FXCollections.observableList(boxes);
    
   
    
    
    
    public List<BoxModel> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<BoxModel> boxes) {
        this.boxes = boxes;
    }

    public ObservableList<BoxModel> getObservableBoxes() {
        return observableBoxes;
    }
    
    
}
