/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary;

import db.model.Dot;
import db.model.Doubt;
import db.model.DoubtStatus;
import db.model.Link;
import db.model.Summary;
import db.services.DoubtService;
import db.services.DoubtServiceImpl;
import db.services.SummaryService;
import db.services.SummaryServiceImpl;
import java.util.List;
import java.util.Set;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobSummaryController {
    private JobSummaryModel model;
    private JobSummaryView view;
    private SummaryService summaryService=new SummaryServiceImpl();
    private DoubtService doubtService=new DoubtServiceImpl();
    
    @FXML
    private Button timeBtn;

    @FXML
    private Button traceBtn;

    @FXML
    private Button qcBtn;

    @FXML
    private Button insightBtn;

    @FXML
    private Button inhBtn;

    
    @FXML
    private Label timeLabel;

    @FXML
    private Label traceLabel;

    @FXML
    private Label qcLabel;

    @FXML
    private Label insightLabel;

    @FXML
    private Label inheritLabel;
    

    /*
    @FXML
    void inheritClicked(ActionEvent event) {
    System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.inheritClicked()");
    }
    
    @FXML
    void insightClicked(ActionEvent event) {
    System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.insightClicked()");
    
    }
    
    @FXML
    void qcClicked(ActionEvent event) {
    System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.qcClicked()");
    
    }
    
    @FXML
    void timeClicked(ActionEvent event) {
    System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.timeClicked()");
    
    }
    
    @FXML
    void traceClicked(ActionEvent event) {
    System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.traceClicked()");
    
    }*/
    
    @FXML
    void inheritClicked(MouseEvent event) {
        System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.inheritClicked()");
    }

    @FXML
    void insightClicked(MouseEvent event) {
        System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.insightClicked()");

    }

    @FXML
    void qcClicked(MouseEvent event) {
        System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.qcClicked()");
    }

    @FXML
    void timeClicked(MouseEvent event) {
        if(model.isTime()) {  //time doubt exists
            List<Doubt> doubtsForTime=doubtService.getDoubtFor(model.getSequence(), model.getJob());
            for(Doubt d:doubtsForTime){
                Set<DoubtStatus> dsList=d.getDoubtStatuses();
                Dot dot=d.getDot();
                Set<Link> links=dot.getLinks();
                for(DoubtStatus ds:dsList){
                     System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.timeClicked(): Doubt id: "+d.getId()+" "+ds.getComment());
                }
               
            }
        }
            
    }

    @FXML
    void traceClicked(MouseEvent event) {
        System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.traceClicked()");
    }
    
    void setModel(JobSummaryModel item) {
        this.model=item;
        
        /*List<Summary> summariesForJobsAtDepth=summaryService.getSummariesForJobSeq(model.getJob(),model.getSequence());
        if(summariesForJobsAtDepth.isEmpty()) model.setActive(false);
        else{
        model.setActive(true);
        }*/
        if(!this.model.isActive()){
            timeLabel.setDisable(true);
            traceLabel.setDisable(true);
            qcLabel.setDisable(true);
            insightLabel.setDisable(true);
            inheritLabel.setDisable(true);
            /* timeBtn.setDisable(true);
            traceBtn.setDisable(true);
            qcBtn.setDisable(true);
            insightBtn.setDisable(true);
            inhBtn.setDisable(true);*/
        }else{
            timeLabel.setDisable(false);
            traceLabel.setDisable(false);
            qcLabel.setDisable(false);
            insightLabel.setDisable(false);
            inheritLabel.setDisable(false);
            /*timeBtn.setDisable(false);
            traceBtn.setDisable(false);
            qcBtn.setDisable(false);
            insightBtn.setDisable(false);
            inhBtn.setDisable(false);*/
        }
        
        model.activeProperty().addListener(new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        System.out.println(".changed() from "+oldValue+" to "+newValue);
        if(newValue){
            timeLabel.setDisable(false);
            traceLabel.setDisable(false);
            qcLabel.setDisable(false);
            insightLabel.setDisable(false);
            inheritLabel.setDisable(false);
            /*timeBtn.setDisable(false);
            traceBtn.setDisable(false);
            qcBtn.setDisable(false);
            insightBtn.setDisable(false);
            inhBtn.setDisable(false);*/
        }else{
            
            timeLabel.setDisable(true);
            traceLabel.setDisable(true);
            qcLabel.setDisable(true);
            insightLabel.setDisable(true);
            inheritLabel.setDisable(true);
            /*timeBtn.setDisable(true);
            traceBtn.setDisable(true);
            qcBtn.setDisable(true);
            insightBtn.setDisable(true);
            inhBtn.setDisable(true);*/
        }
        }
        });
        
        
        model.timeProperty().addListener(TIME_SUMMARY_CHANGE_LISTENER);
        
    }

    void setView(JobSummaryView vw) {
        this.view=vw;
    }
    
    /**
     * Listeners
     */
    
    private ChangeListener<Boolean> TIME_SUMMARY_CHANGE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                timeLabel.setStyle("-fx-background-color: #14C6EF");
            }
        }
    };
}
