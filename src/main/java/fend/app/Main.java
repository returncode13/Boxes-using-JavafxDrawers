/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app;

import fend.app.AppModel;
import fend.app.AppView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class Main extends Application{
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppModel appmodel=new AppModel();
        AppView appview=new AppView(appmodel);
        Scene scene=appview.getController().getAppScene();
        String title=appview.getController().getTitle();
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        /*  WorkspaceModel model=new WorkspaceModel();
        WorkspaceView node=new WorkspaceView(model);*/
       
    }
}
