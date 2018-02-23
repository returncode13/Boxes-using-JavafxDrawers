/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary;

import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCellView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobSummaryController extends Stage{
    JobSummaryModel model;
    JobSummaryView view;
           
    
  //  @FXML
  //  private AnchorPane anchorPane;
    
    void setModel(JobSummaryModel item) {
            model=item;
            if(model.isActive()){
              //  anchorPane.setDisable(false);
            }else{
               // anchorPane.setDisable(true);
            }
            model.activeProperty().addListener(ACTIVE_LISTENER);
            model.queryProperty().addListener(QUERY_LISTENER);
    }

    void setView(JobSummaryView vw) {
        System.out.println("fend.summary.SequenceSummary.Depth.JobSummary_new.JobSummaryController.setView()");
            view=vw;
    }
    
    
    private ChangeListener<Boolean> ACTIVE_LISTENER=new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                if(model.getSubsurface()!=null){
                    System.err.println("fend.summary.SequenceSummary.Depth.JobSummary_new.JobSummaryController. setting active to true for "+model.getSubsurface().getSubsurface()+" "+model.getJob().getNameJobStep());
                }
                
             //   anchorPane.setDisable(false);
                model.getTimeCellModel().setActive(true);
               
        }else{
                 System.err.println("fend.summary.SequenceSummary.Depth.JobSummary_new.JobSummaryController. setting active to false;");
              //  anchorPane.setDisable(true);
                model.getTimeCellModel().setActive(false);
            
        }
        }
        
    };
    
     private ChangeListener<Boolean> QUERY_LISTENER=new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            System.out.println("fend.summary.SequenceSummary.Depth.JobSummary_new.JobSummaryController.: toggling QUERY_LISTENER for the time cell in "+model.getSubsurface().getSubsurface()+" "+model.getJob().getNameJobStep());
           model.getTimeCellModel().setQuery(!model.getTimeCellModel().isQuery());
        }
        
    };
    
   
}
