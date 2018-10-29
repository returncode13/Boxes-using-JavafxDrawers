/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.context.workflow;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkFlowDifferenceView extends AnchorPane{
     private FXMLLoader fXMLLoader;
    private final URL location;
    private WorkFlowDifferenceController controller;
    
    public WorkFlowDifferenceView(WorkFlowDifferenceModel lsm)
    {
       
        //this.location=LandingController.class.getResource("landingView/LandingView.fxml"); 
        this.location=getClass().getClassLoader().getResource("fxml/job/table/context/workflow/workflowDifference_1.fxml"); 
          System.out.println(location.toString());
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                
                fXMLLoader.load(location.openStream());
                
                
                controller=(WorkFlowDifferenceController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
               
               // sc.setId(Long.valueOf(getId()));
                
                controller.setModel(lsm);
                controller.setView(this) ;
                
               
               
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            } 
}

    public WorkFlowDifferenceController getController() {
        return controller;
    }
}
