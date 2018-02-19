/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.override;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import db.model.Doubt;
import db.model.DoubtStatus;
import db.services.DoubtService;
import db.services.DoubtStatusService;
import db.services.DoubtStatusServiceImpl;
import fend.summary.SequenceSummary.Depth.JobSummary_new.JobSummaryModel;
import fend.summary.override.confirmation.OverrideConfirmationModel;
import fend.summary.override.confirmation.OverrideConfirmationView;
import java.util.Iterator;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import middleware.doubt.DoubtStatusModel;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class OverrideController extends Stage{
    
    private OverrideModel model;
    private OverrideView view;
    private DoubtStatusService doubtStatusService=new DoubtStatusServiceImpl();

     @FXML
    private JFXTextArea userCommentTextArea;

    @FXML
    private JFXTextArea doubtReasonTextArea;

    @FXML
    private JFXTextField doubttypeTextField;

    @FXML
    private JFXTextField subsurfaceTextField;

    @FXML
    private JFXTextField linkTextField;

    @FXML
    private JFXTextField parentJobTextField;

    @FXML
    private JFXComboBox<String> statusComboBox;

    @FXML
    private Button proceedBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void onCancel(ActionEvent event) {
        close();
    }

    @FXML
    void proceedClicked(ActionEvent event) {
            OverrideConfirmationModel ovcmodel=new OverrideConfirmationModel(model);
            OverrideConfirmationView ovcview=new OverrideConfirmationView(ovcmodel);
    }
    
    
    
    
    void setModel(OverrideModel item) {
        this.model=item;
        model.confirmationProperty().addListener(CONFIRMATION_LISTENER);
        
        
        doubttypeTextField.setText(model.getTypeText());
        subsurfaceTextField.setText(model.getSubsurfaceName());
        linkTextField.setText(model.getLinkDescription());
        parentJobTextField.setText(model.getParentJobName());
        doubtReasonTextArea.setText(model.getDoubtStatusComment());
        userCommentTextArea.setText(model.getUserCommentStack());
        
        
        statusComboBox.getItems().addAll(DoubtStatusModel.OVERRIDE,DoubtStatusModel.YES);
        statusComboBox.setValue(model.getStatus());
        
        statusComboBox.valueProperty().addListener(STATUS_CHANGE_LISTENER);
       
    }

    void setView(OverrideView vw) {
        this.view=vw;
        this.setTitle("managing doubt for sub: "+model.getSubsurfaceName()+" in  job: "+model.getDoubt().getLink().getChild().getNameJobStep());
        this.setScene(new Scene(this.view));
        this.initModality(Modality.APPLICATION_MODAL);
        this.showAndWait();
        
    }
    
    
    
    
    /**
     * Listeners
     */
    
    private ChangeListener<Boolean> CONFIRMATION_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                Boolean statusChanged=false;
                System.out.println("fend.summary.override.OverrideController.setModel(): Override Confirmed...updating database for doubtstatus id: "+model.getDoubtStatus().getId());
                DoubtStatus ds=model.getDoubtStatus();
                String status=model.getStatus();
                String userComment=model.getUserCommentStack();
                if(!ds.getStatus().equals(status)){
                    statusChanged=true;
                }
                ds.setStatus(status);
                
                ds.setComments(userComment);
                doubtStatusService.updateDoubtStatus(ds.getId(), ds);
                
                Doubt doubt=ds.getDoubt();
                Set<Doubt> inheritedDoubts=doubt.getInheritedDoubts();
                for (Iterator<Doubt> iterator = inheritedDoubts.iterator(); iterator.hasNext();) {
                    Doubt inhDoubt = iterator.next();
                    
                    if(statusChanged){
                        
                        if(model.getStatus().equals(DoubtStatusModel.OVERRIDE)){
                                model.getCellModel().setOverride(true);
                        }else{
                            model.getCellModel().setOverride(false);
                        }
                        
                            Set<DoubtStatus> inhDoubtStatus=inhDoubt.getDoubtStatuses();
                                for (DoubtStatus inhDoubtStat : inhDoubtStatus) {
                                        System.out.println(".changed(): updating the status of inherited doubt "+inhDoubt.getId()+" on child "+inhDoubt.getChildJob().getNameJobStep());
                                        inhDoubtStat.setStatus(status);
                                        doubtStatusService.updateDoubtStatus(inhDoubt.getId(), inhDoubtStat);
                                }
                    }
                            
                    
                    
                    
                    
                    System.out.println(".changed(): inherited doubt: "+inhDoubt.getChildJob().getNameJobStep()+" will be requeried. for subsurface "+inhDoubt.getSubsurface().getSubsurface());
                    
                    JobSummaryModel inhjsm=model.getCellModel().getJobSummaryModel()
                            .getSummaryModel()
                            .getSequenceSummaryMap()
                            .get(inhDoubt.getSubsurface().getSequence())
                            .getChild(inhDoubt.getSubsurface())
                            .getDepth(inhDoubt.getChildJob().getDepth())
                            .getJobSummaryModel(inhDoubt.getChildJob());
                    System.out.println(".changed() : will toggle flag under: "+inhjsm.getJob().getNameJobStep());
                    System.out.println(".changed() : current Query flag: "+inhjsm.getTimeCellModel().isQuery()+" changing to --> "+!inhjsm.getTimeCellModel().isQuery());
                    System.out.println(".changed() : current Query flag: "+inhjsm.getFeModeltimeCellModel().isQuery()+" changing to --> "+!inhjsm.getFeModeltimeCellModel().isQuery());
                  //  inhjsm.setQuery(!inhjsm.isQuery());
                    inhjsm.getFeModeltimeCellModel().setQuery(!inhjsm.getFeModeltimeCellModel().isQuery());
                }
                
                
                System.out.println(".changed(): closing the override box..");
                close();
                
            }
        }
    };
    
    
    private ChangeListener<String> STATUS_CHANGE_LISTENER=new ChangeListener<String>(){
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldStatus, String newStatus) {
            System.out.println(".changed(): changing status in model from "+model.getStatus()+" to "+newStatus);
            model.setStatus(newStatus);
            
        }
        
    };
    
}
