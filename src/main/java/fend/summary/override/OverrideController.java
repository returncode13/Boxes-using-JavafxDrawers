/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.override;

import app.properties.AppProperties;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import db.model.Doubt;
import db.model.DoubtStatus;
import db.model.Job;
import db.model.Subsurface;
import db.services.DoubtService;
import db.services.DoubtServiceImpl;
import db.services.DoubtStatusService;
import db.services.DoubtStatusServiceImpl;
import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel;
import fend.summary.override.confirmation.OverrideConfirmationModel;
import fend.summary.override.confirmation.OverrideConfirmationView;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private DoubtService doubtService=new DoubtServiceImpl();
    
    
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
                System.out.println("fend.summary.override.OverrideController.setModel(): Override Confirmed...updating database for doubt id: "+model.getDoubt().getId());
               // DoubtStatus ds=model.getDoubtStatus();
               
               Doubt d=model.getDoubt();
                String status=model.getStatus();
                String userComment=model.getUserCommentStack();
                if(!d.getStatus().equals(status)){
                    statusChanged=true;
                }
                d.setStatus(status); //change the status
                
                
                
                d.setUser(AppProperties.getCurrentUser());
                d.setComments(userComment);
                doubtService.updateDoubt(d.getId(), d);
                
                /*if(statusChanged){
                if(model.getStatus().equals(DoubtStatusModel.OVERRIDE)){
                model.getCellModel().setOverride(true);
                }else{
                model.getCellModel().setOverride(false);
                }
                }*/
                
                try {
                    //model.getCellModel().getJobSummaryModel().getSummaryModel().getWorkspaceController().summarizeOne();
                    Job origin=model.getCellModel().getJobSummaryModel().getJob();
                    Subsurface sub=model.getCellModel().getJobSummaryModel().getSubsurface();
                    //model.getCellModel().getJobSummaryModel().getSummaryModel().getWorkspaceController().resummarizeFor(origin,sub);
                    model.getCellModel().getJobSummaryModel().getSummaryModel().getWorkspaceController().summaryFor(origin, sub);
                } catch (Exception ex) {
                    Logger.getLogger(OverrideController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                model.getCellModel().getJobSummaryModel().toggleQuery();     //color the subsurface cell
                model.getCellModel().getJobSummaryModel().getSummaryModel()
                        .getSequenceSummary(d.getSequence())                //color the sequence cell
                        .getDepth(d.getChildJob().getDepth()).getJobSummaryModel(d.getChildJob()).toggleQuery();
                Doubt doubt=d;
                //Set<Doubt> inheritedDoubts=doubt.getInheritedDoubts();
                List<Doubt> inheritedDoubts=doubtService.getInheritedDoubtsForCause(doubt);
                for (Iterator<Doubt> iterator = inheritedDoubts.iterator(); iterator.hasNext();) {
                    Doubt inhDoubt = iterator.next();
                    
                    
                    
                    
                    
                    System.out.println(".changed(): inherited doubt: "+inhDoubt.getChildJob().getNameJobStep()+" will be requeried. for subsurface "+inhDoubt.getSubsurface().getSubsurface());
                    
                    
                    Job inhJob=inhDoubt.getChildJob();
                    Subsurface sub=inhDoubt.getSubsurface();
                    model.getCellModel().getJobSummaryModel().getSummaryModel().getWorkspaceController().summaryFor(inhJob, sub);
                    
                    JobSummaryModel inhjsm_sub=model.getCellModel().getJobSummaryModel()
                            .getSummaryModel()
                            .getSequenceSummaryMap()
                            .get(inhDoubt.getSubsurface().getSequence())
                            .getChild(inhDoubt.getSubsurface())
                            .getDepth(inhDoubt.getChildJob().getDepth())
                            .getJobSummaryModel(inhDoubt.getChildJob());
                    System.out.println(".changed() : will toggle flag under: "+inhjsm_sub.getJob().getNameJobStep());
                    System.out.print(".changed() : current Query flag: "+inhjsm_sub.isQuery()+" changing to --> ");
                    //inhjsm_sub.setQuery(!inhjsm_sub.isQuery());
                    inhjsm_sub.toggleQuery();
                    System.out.println(" "+inhjsm_sub.isQuery());
                    
                    JobSummaryModel inhjsm_seq=model.getCellModel().getJobSummaryModel()
                                                    .getSummaryModel()
                                                    .getSequenceSummary(inhDoubt.getSubsurface().getSequence())
                                                    .getDepth(inhDoubt.getChildJob().getDepth())
                                                    .getJobSummaryModel(inhDoubt.getChildJob());
                                                        
                    
                    inhjsm_seq.toggleQuery();
                    
                    
                    
                    //System.out.println(".changed(): refreshing table");
                    
                    /* Boolean ref=model.getCellModel().getJobSummaryModel().getSummaryModel().refreshTableProperty().get();
                    model.getCellModel().getJobSummaryModel().getSummaryModel().setRefreshTable(!ref);*/
                
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
