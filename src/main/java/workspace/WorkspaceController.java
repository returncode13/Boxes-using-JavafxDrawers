/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workspace;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import job.job1.JobType1Model;
import job.job1.JobType1View;
import job.definitions.JobDefinitionsModel;
import job.definitions.JobDefinitionsView;
import job.definitions.volume.VolumeListModel;
import job.definitions.volume.VolumeListView;
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
public class WorkspaceController extends Stage {
    
    
    
    private WorkspaceModel model;
    private WorkspaceView node;
    private BooleanProperty changeProperty=new SimpleBooleanProperty(false);
    
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
        JobType1Model job=new JobType1Model(this.model);
        changeProperty.bind(job.getChangeProperty());
        JobType1View jobview=new JobType1View(job,interactivePane);
        interactivePane.getChildren().add(jobview);
        
        
        
        /*JobDefinitionsModel model=new JobDefinitionsModel();
        JobDefinitionsView bdv=new JobDefinitionsView(model);
        interactivePane.getChildren().add(bdv);*/
        
        /*VolumeListModel vol=new VolumeListModel();
        VolumeListView vollistview=new VolumeListView(vol);
        interactivePane.getChildren().add(vollistview);*/
    }
    
    
    
    
    void setModel(WorkspaceModel item) {
        splitpane.prefWidthProperty().bind(baseWindow.widthProperty());
        splitpane.prefHeightProperty().bind(baseWindow.heightProperty());
        scrollpane.prefWidthProperty().bind(splitpane.widthProperty());
        scrollpane.prefHeightProperty().bind(splitpane.heightProperty());
        interactivePane.prefWidthProperty().bind(scrollpane.widthProperty());
        interactivePane.prefHeightProperty().bind(scrollpane.heightProperty());
        
        model=item;
        
        changeProperty.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               
               
                    if(model.DEBUG)System.out.println("Saving session");
               
                
            }
            
        });
      }

    void setView(WorkspaceView aThis) {
        /*node=aThis;
        baseWindow.getChildren().add(node);*/
        this.setTitle("MCVE for Dots");
         this.setScene(new Scene(baseWindow));
         this.showAndWait();
    }
    
}
