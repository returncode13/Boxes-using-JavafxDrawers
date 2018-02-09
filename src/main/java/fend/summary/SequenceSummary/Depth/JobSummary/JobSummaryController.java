/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary;

import db.model.Summary;
import db.services.SummaryService;
import db.services.SummaryServiceImpl;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobSummaryController {
    private JobSummaryModel model;
    private JobSummaryView view;
    private SummaryService summaryService=new SummaryServiceImpl();
            
    
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
    void inheritClicked(ActionEvent event) {

    }

    @FXML
    void insightClicked(ActionEvent event) {

    }

    @FXML
    void qcClicked(ActionEvent event) {

    }

    @FXML
    void timeClicked(ActionEvent event) {

    }

    @FXML
    void traceClicked(ActionEvent event) {

    }
    
    void setModel(JobSummaryModel item) {
        this.model=item;
        
        List<Summary> summariesForJobsAtDepth=summaryService.getSummariesForJobSeq(model.getJob(),model.getSequence());
        if(summariesForJobsAtDepth.isEmpty()) model.setActive(false);
        else{
            model.setActive(true);
        }
        /*if(!this.model.isActive()){
        timeBtn.setDisable(true);
        traceBtn.setDisable(true);
        qcBtn.setDisable(true);
        insightBtn.setDisable(true);
        inhBtn.setDisable(true);
        }*/
        
        model.activeProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println(".changed() from "+oldValue+" to "+newValue);
                if(newValue){
                    timeBtn.setDisable(false);
                    traceBtn.setDisable(false);
                    qcBtn.setDisable(false);
                    insightBtn.setDisable(false);
                    inhBtn.setDisable(false);
                }else{
                    timeBtn.setDisable(true);
                    traceBtn.setDisable(true);
                    qcBtn.setDisable(true);
                    insightBtn.setDisable(true);
                    inhBtn.setDisable(true);
                }
            }
        });
        
    }

    void setView(JobSummaryView vw) {
        this.view=vw;
    }
    
}
