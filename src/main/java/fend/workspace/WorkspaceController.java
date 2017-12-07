/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.workspace;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import fend.job.job1.JobType1Model;
import fend.job.job1.JobType1View;
import fend.job.definitions.JobDefinitionsModel;
import fend.job.definitions.JobDefinitionsView;
import fend.job.definitions.volume.VolumeListModel;
import fend.job.definitions.volume.VolumeListView;
import java.util.ArrayList;
import java.util.List;
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
public class WorkspaceController  {
    
    
    
    private WorkspaceModel model;
    private WorkspaceView node;
   
    List<BooleanProperty> changePropertyList=new ArrayList<>();
    
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
        BooleanProperty changeProperty=new SimpleBooleanProperty(false);
        changeProperty.bind(job.getChangeProperty());
        changeProperty.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               
               
                    if(model.DEBUG)System.out.println("Saving session");
                    saveWorkspace();
                
            }

          
            
        });
        
        changePropertyList.add(changeProperty);
        JobType1View jobview=new JobType1View(job,interactivePane);
        interactivePane.getChildren().add(jobview);
        System.out.println("workspace.WorkspaceController.addBox(): "+job.getId()%100);
        
        
        
    }
    
    
    
    
    void setModel(WorkspaceModel item) {
        splitpane.prefWidthProperty().bind(baseWindow.widthProperty());
        splitpane.prefHeightProperty().bind(baseWindow.heightProperty());
        scrollpane.prefWidthProperty().bind(splitpane.widthProperty());
        scrollpane.prefHeightProperty().bind(splitpane.heightProperty());
        interactivePane.prefWidthProperty().bind(scrollpane.widthProperty());
        interactivePane.prefHeightProperty().bind(scrollpane.heightProperty());
        
        model=item;
        
        
      }

    void setView(WorkspaceView vq) {
        /*node=aThis;
        baseWindow.getChildren().add(node);*/
        node=vq;
    }
    
      private void saveWorkspace() {
          System.out.println("fend.workspace.WorkspaceController.saveWorkspace()");
      }
    
}
