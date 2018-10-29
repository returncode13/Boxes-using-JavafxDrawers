/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary;

import db.model.Summary;
import db.services.SummaryService;
import db.services.SummaryServiceImpl;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCellView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * 
 * This class is never called.   --OBSOLETE
 */
public class JobSummaryController extends Stage{
    JobSummaryModel model;
    JobSummaryView view;
    SummaryService summaryService=new SummaryServiceImpl();
    
  //  @FXML
  //  private AnchorPane anchorPane;
    
   public  void setModel(JobSummaryModel item) {
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
            System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.: toggling QUERY_LISTENER for the time cell in "+model.getSubsurface().getSubsurface()+" "+model.getJob().getNameJobStep());
           //model.getTimeCellModel().setQuery(!model.getTimeCellModel().isQuery());
           
           Summary x=summaryService.getSummaryFor(model.getSubsurface(), model.getJob());
           //time
            
                        model.getTimeCellModel().setFailedTimeDependency(x.hasFailedTimeDependency());
                        model.getTimeCellModel().setInheritedTimeFail(x.hasInheritedTimeFail());
                        model.getTimeCellModel().setInheritedTimeOverride(x.hasInheritedTimeOverride());
                        model.getTimeCellModel().setOverridenTimeFail(x.hasOverridenTimeFail());
                        model.getTimeCellModel().setWarningForTime(x.hasWarningForTime());
                        model.getTimeCellModel().setQuery(!model.getTimeCellModel().isQuery());                    //trigger the labelColors Call in the cells controller
            
           //trace
                        model.getTraceCellModel().setFailedTraceDependency(x.hasFailedTraceDependency());
                        model.getTraceCellModel().setInheritedTraceFail(x.hasInheritedTraceFail());
                        model.getTraceCellModel().setInheritedTraceOverride(x.hasInheritedTraceOverride());
                        model.getTraceCellModel().setOverridenTraceFail(x.hasOverridenTraceFail());
                        model.getTraceCellModel().setWarningForTrace(x.hasWarningForTrace());
                        model.getTraceCellModel().setQuery(!model.getTraceCellModel().isQuery());                 //trigger the labelColors Call in the cells controller
           
           //qc
                        model.getQcCellModel().setFailedQcDependency(x.hasFailedQcDependency());
                        model.getQcCellModel().setInheritedQcFail(x.hasInheritedQcFail());
                        model.getQcCellModel().setInheritedQcOverride(x.hasInheritedQcOverride());
                        model.getQcCellModel().setOverridenQcFail(x.hasOverridenQcFail());
                        model.getQcCellModel().setWarningForQc(x.hasWarningForQc());
                        model.getQcCellModel().setQuery(!model.getQcCellModel().isQuery());                      //trigger the labelColors Call in the cells controller
           //insight
                        model.getInsightCellModel().setFailedInsightDependency(x.hasFailedInsightDependency());
                        model.getInsightCellModel().setInheritedInsightFail(x.hasInheritedInsightFail());
                        model.getInsightCellModel().setInheritedInsightOverride(x.hasInheritedInsightOverride());
                        model.getInsightCellModel().setOverridenInsightFail(x.hasOverridenInsightFail());
                        model.getInsightCellModel().setWarningForInsight(x.hasWarningForInsight());
                        model.getInsightCellModel().setQuery(!model.getInsightCellModel().isQuery());//trigger the labelColors Call in the cells controller
           //io
                        model.getIoCellModel().setFailedIoDependency(x.hasFailedIoDependency());
                        model.getIoCellModel().setInheritedIoFail(x.hasInheritedIoFail());
                        model.getIoCellModel().setInheritedIoOverride(x.hasInheritedIoOverride());
                        model.getIoCellModel().setOverridenIoFail(x.hasOverridenIoFail());
                        model.getIoCellModel().setWarningForIo(x.hasWarningForIo());
                        model.getIoCellModel().setQuery(!model.getIoCellModel().isQuery());//trigger the labelColors Call in the cells controller
            
        }
        
    };
    
   
}
