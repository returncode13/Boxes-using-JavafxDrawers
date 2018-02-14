/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary;

import db.model.Dot;
import db.model.Doubt;
import db.model.DoubtStatus;
import db.model.DoubtType;
import db.model.Link;
import db.model.Summary;
import db.services.DoubtService;
import db.services.DoubtServiceImpl;
import db.services.DoubtTypeService;
import db.services.DoubtTypeServiceImpl;
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
import middleware.doubt.DoubtTypeModel;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobSummaryController {
    private final String TIME_IS_SET="#2B00FF";
    private final String TIME_IS_UNSET="#14C6EF";
    private final String TRACES_ARE_SET="#01594D";
    private final String TRACES_ARE_UNSET="#14EFD1";
    private final String QC_IS_SET="#00521F";
    private final String QC_IS_UNSET="#14EF67";
    private final String INSIGHT_IS_SET="#0B5500";
    private final String INSIGHT_IS_UNSET="#2FEF14";
    private final String INHERITANCE_IS_SET="#93B400";
    private final String INHERITANCE_IS_UNSET="#C8EF14";
    private DoubtType timeDoubtType;
    private DoubtType traceDoubtType;
    private DoubtType qcDoubtType;
    private DoubtType insightDoubtType;
    private DoubtType inheritanceDoubtType;
    
    private JobSummaryModel model;
    private JobSummaryView view;
    private SummaryService summaryService=new SummaryServiceImpl();
    private DoubtService doubtService=new DoubtServiceImpl();
    private DoubtTypeService doubtTypeService=new DoubtTypeServiceImpl();
    
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
        if(model.isInheritance()) {  //inheritance doubt exists
            System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.inheritClicked(): model.sequence "+model.getSequence().getSequenceno()+" "
                    + "job: "+model.getJob().getNameJobStep());
            List<Doubt> doubtsForInheritance=doubtService.getDoubtFor(model.getSequence(), model.getJob(),inheritanceDoubtType);
            for(Doubt d:doubtsForInheritance){
                
                Doubt cause=d.getDoubtCause();
                
                Set<DoubtStatus> dsList=cause.getDoubtStatuses();
                Dot dot=d.getDot();
                
                for(DoubtStatus ds:dsList){
                System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.inheritClicked(): Doubt id: "+d.getId()+"  INHERITED FROM "+cause.getLink().getParent().getNameJobStep()
                +" <--Failed Link--> "+cause.getLink().getChild().getNameJobStep()+" type: "+cause.getDoubtType().getName()+" status: "+ds.getStatus()+" message: "+ds.getComment());
                }
               
            }
        }
    }

    @FXML
    void insightClicked(MouseEvent event) {
        System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.insightClicked()");

    }

    @FXML
    void qcClicked(MouseEvent event) {
        System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.qcClicked()");
        if(model.isQc()){
            System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.qcClicked(): model.sequence "+model.getSequence().getSequenceno()+" "
                    + "job: "+model.getJob().getNameJobStep());
            List<Doubt> doubtsForQC=doubtService.getDoubtFor(model.getSequence(), model.getJob(),qcDoubtType);
            for(Doubt d:doubtsForQC){
                Set<DoubtStatus> dsList=d.getDoubtStatuses();
                Dot dot=d.getDot();
               
                for(DoubtStatus ds:dsList){
                     System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.QcClicked(): Doubt id: "+d.getId()+"  "+d.getLink().getParent().getNameJobStep()
                             +" <--Failed Link--> "+d.getLink().getChild().getNameJobStep()+" type: "+d.getDoubtType().getName()+" status: "+ds.getStatus()+" message: "+ds.getComment());
                }
               
            }
        }
    }

    @FXML
    void timeClicked(MouseEvent event) {
        if(model.isTime()) {  //time doubt exists
            System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.timeClicked(): model.sequence "+model.getSequence().getSequenceno()+" "
                    + "job: "+model.getJob().getNameJobStep());
            List<Doubt> doubtsForTime=doubtService.getDoubtFor(model.getSequence(), model.getJob(),timeDoubtType);
            for(Doubt d:doubtsForTime){
                Set<DoubtStatus> dsList=d.getDoubtStatuses();
                Dot dot=d.getDot();
               
                for(DoubtStatus ds:dsList){
                     System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.timeClicked(): Doubt id: "+d.getId()+"  "+d.getLink().getParent().getNameJobStep()
                             +" <--Failed Link--> "+d.getLink().getChild().getNameJobStep()+" type: "+d.getDoubtType().getName()+" status: "+ds.getStatus()+" message: "+ds.getComment());
                }
               
            }
        }
            
    }

    @FXML
    void traceClicked(MouseEvent event) {
        System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.traceClicked()");
        if(model.isTrace()){
            System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.traceClicked(): model.sequence "+model.getSequence().getSequenceno()+" "
                    + "job: "+model.getJob().getNameJobStep());
            List<Doubt> doubtsForTrace=doubtService.getDoubtFor(model.getSequence(), model.getJob(),traceDoubtType);
            for(Doubt d:doubtsForTrace){
                Set<DoubtStatus> dsList=d.getDoubtStatuses();
                Dot dot=d.getDot();
               
                for(DoubtStatus ds:dsList){
                     System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryController.traceClicked(): Doubt id: "+d.getId()+"  "+d.getLink().getParent().getNameJobStep()
                             +" <--Failed Link--> "+d.getLink().getChild().getNameJobStep()+" type: "+d.getDoubtType().getName()+" status: "+ds.getStatus()+" message: "+ds.getComment());
                }
               
            }
        }
    }
    
    void setModel(JobSummaryModel item) {
        this.model=item;
        timeDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TIME);
        traceDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TRACES);
        qcDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.QC);
        insightDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.INSIGHT);
        inheritanceDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.INHERIT);
        
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
        if(model.isTime()){
            timeLabel.setStyle("-fx-background-color: "+TIME_IS_SET);
        }else{
            timeLabel.setStyle("-fx-background-color: "+TIME_IS_UNSET);
        }
        
        model.traceProperty().addListener(TRACE_SUMMARY_CHANGE_LISTENER);
        if(model.isTrace()){
            traceLabel.setStyle("-fx-background-color: "+TRACES_ARE_SET);
        }else{
            traceLabel.setStyle("-fx-background-color: "+TRACES_ARE_UNSET);
        }
        
        model.qcProperty().addListener(QC_SUMMARY_CHANGE_LISTENER);
        if(model.isQc()){
            qcLabel.setStyle("-fx-background-color: "+QC_IS_SET );
        }else{
            qcLabel.setStyle("-fx-background-color: "+QC_IS_UNSET );
        }
        
        model.inheritanceProperty().addListener(INHERITANCE_SUMMARY_CHANGE_LISTENER);
        if(model.isInheritance()){
            inheritLabel.setStyle("-fx-background-color: "+INHERITANCE_IS_SET );
        }else{
            inheritLabel.setStyle("-fx-background-color: "+INHERITANCE_IS_UNSET );
        }
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
                timeLabel.setStyle("-fx-background-color: "+TIME_IS_SET);
            }else{
                timeLabel.setStyle("-fx-background-color: "+TIME_IS_UNSET);
            }
        }
    };
    
    
    private ChangeListener<Boolean> TRACE_SUMMARY_CHANGE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                traceLabel.setStyle("-fx-background-color: "+TRACES_ARE_SET);
            }else{
                traceLabel.setStyle("-fx-background-color: "+TRACES_ARE_UNSET);
            }
        }
    };
    
    
    private ChangeListener<Boolean> QC_SUMMARY_CHANGE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                qcLabel.setStyle("-fx-background-color: "+QC_IS_SET);
            }else{
                qcLabel.setStyle("-fx-background-color: "+QC_IS_UNSET);
            }
        }
    };
    
    private ChangeListener<Boolean> INHERITANCE_SUMMARY_CHANGE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                inheritLabel.setStyle("-fx-background-color: "+INHERITANCE_IS_SET);
            }else{
                inheritLabel.setStyle("-fx-background-color: "+INHERITANCE_IS_UNSET);
            }
        }
    };
}
