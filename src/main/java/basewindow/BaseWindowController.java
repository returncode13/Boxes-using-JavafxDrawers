/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basewindow;

import box.BoxModel;
import box.BoxView;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class BaseWindowController extends Stage {
    
    
    
    private BaseWindowModel model;
    private BaseWindowNode node;
    
    @FXML
    private AnchorPane baseWindow;              //depth =0 
    
    
    @FXML
    private SplitPane splitpane;                  //depth =1 

    
    @FXML   
    private ScrollPane scrollpane;              //depth =2 
    @FXML
    private AnchorPane interactivePane;         //depth =3 
    
    @FXML
    private Button add;

    @FXML
    void addBox(ActionEvent event) {
        BoxModel box=new BoxModel();
        BoxView boxnode=new BoxView(box,interactivePane);
        //box.setBaseWindow(model);
    //    model.getObservableBoxes().add(box);
        interactivePane.getChildren().add(boxnode);
        
    }
    
    
    
    
    void setModel(BaseWindowModel item) {
        splitpane.prefWidthProperty().bind(baseWindow.widthProperty());
        splitpane.prefHeightProperty().bind(baseWindow.heightProperty());
        scrollpane.prefWidthProperty().bind(splitpane.widthProperty());
        scrollpane.prefHeightProperty().bind(splitpane.heightProperty());
        interactivePane.prefWidthProperty().bind(scrollpane.widthProperty());
        interactivePane.prefHeightProperty().bind(scrollpane.heightProperty());
        
        model=item;
        /*model.getObservableBoxes().addListener(new ListChangeListener<BoxModel>(){
        @Override
        public void onChanged(ListChangeListener.Change<? extends BoxModel> c) {
        while(c.next()){
        if(c.wasAdded()){
        System.out.println("BaseWindowController.setModel().ListChanger.onChanged() added "+c.getAddedSize()+" ");
        for(BoxModel b:c.getAddedSubList()){
        System.out.println("Added "+b.getId());
        }
        }
        if(c.wasRemoved()) {
        System.out.println("BaseWindowController.setModel().ListChanger.onChanged() removed"+c.getRemovedSize()+" ");
        for(BoxModel b:c.getRemoved()){
        System.out.println("Removed "+b.getId());
        }
        
        }
        }
        
        }
        
        });*/
        
        
        
    }

    void setView(BaseWindowNode aThis) {
        /*node=aThis;
        baseWindow.getChildren().add(node);*/
        this.setTitle("MCVE for Dots");
         this.setScene(new Scene(baseWindow));
         this.showAndWait();
    }
    
}
