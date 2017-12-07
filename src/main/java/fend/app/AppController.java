/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app;

import fend.workspace.WorkspaceModel;
import fend.workspace.WorkspaceView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AppController extends Stage{
    AppModel model;
    AppView view;
    
     @FXML
    private MenuBar menubar;

    
    @FXML
    private MenuItem startWorkspace;

    @FXML
    private MenuItem saveCurrentWorkspace;

    @FXML
    private MenuItem saveSessionAs;

    @FXML
    private MenuItem loadWorkspace;


    @FXML
    private MenuItem exit;

    @FXML
    private MenuItem settings;

    @FXML
    private MenuItem dbsettings;

    @FXML
    private MenuItem idAbout;

    @FXML
    private Button bugReport;

    @FXML
    private StackPane basePane;

    @FXML
    void about(ActionEvent event) {

    }

    @FXML
    void dbsettings(ActionEvent event) {

    }

    @FXML
    void exitTheProgram(ActionEvent event) {

    }

    @FXML
    void handleBugReport(ActionEvent event) {

    }

    @FXML
    void loadSession(ActionEvent event) {

    }

    @FXML
    void saveCurrentSession(ActionEvent event) {

    }

    @FXML
    void saveSessionAs(ActionEvent event) {

    }

    @FXML
    void settings(ActionEvent event) {

    }

    @FXML
    void startNewWorkspace(ActionEvent event) {
         WorkspaceModel model=new WorkspaceModel();
        WorkspaceView node=new WorkspaceView(model);
        basePane.getChildren().add(node);
    }
    
    
    
    
    
    
    void setModel(AppModel model) {
        
        this.model=model;
    }

    void setView(AppView view) {
        this.view=view;
         this.setTitle("MCVE for Dots");
         this.setScene(new Scene(view));
         this.showAndWait();
    }
    
}
