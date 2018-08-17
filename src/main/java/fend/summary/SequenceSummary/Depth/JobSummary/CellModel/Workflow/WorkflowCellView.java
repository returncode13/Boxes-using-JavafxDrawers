/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Workflow;



import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.StackPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkflowCellView extends StackPane{
     private  WorkflowCellModel model;
    private WorkflowCellController  controller;
    
     private FXMLLoader fXMLLoader;
    private final URL location;
    
    
    public WorkflowCellView(WorkflowCellModel item){
        
        this.location=getClass().getClassLoader().getResource("fxml/summary/jobSummary/cellModel/workflow/workflowCellView.fxml"); 
       
          
           fXMLLoader=new FXMLLoader();
              
            fXMLLoader.setLocation(location);
             
            fXMLLoader.setRoot(this);
            fXMLLoader.setBuilderFactory(new JavaFXBuilderFactory());
           
            try{
                fXMLLoader.load(location.openStream());
           
                controller=(WorkflowCellController)fXMLLoader.getController();
             
               // setId(UUID.randomUUID().toString());
                //setId((new UID()).toString());
                 controller.setModel(item);
                    controller.setView(this) ;
               
                
            }catch(IOException e){
                throw new RuntimeException(e);
            }
    }

    public WorkflowCellController getController() {
        return controller;
    }
    
    

}
