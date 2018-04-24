/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Qc;

import db.model.Doubt;
import db.model.DoubtStatus;
import db.model.DoubtType;
import db.model.Link;
import db.services.DoubtService;
import db.services.DoubtServiceImpl;
import db.services.DoubtStatusService;
import db.services.DoubtStatusServiceImpl;
import db.services.DoubtTypeService;
import db.services.DoubtTypeServiceImpl;
import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryColors;
import fend.summary.override.OverrideModel;
import fend.summary.override.OverrideView;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import middleware.doubt.DoubtStatusModel;
import middleware.doubt.DoubtTypeModel;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcCellController {
    QcCellModel model;
    QcCellView view;
    DoubtStatusService doubtStatusService=new DoubtStatusServiceImpl();
    DoubtTypeService doubtTypeService=new DoubtTypeServiceImpl();
    DoubtService doubtService=new DoubtServiceImpl();
    DoubtType qcDoubtType;
    
    @FXML
    private Label qcLabel;

    @FXML
    void qcClicked(MouseEvent event) {
        if(model.getJobSummaryModel().getSubsurface()!=null){
            System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCellController.qcClicked(): time clicked for  "+model.getJobSummaryModel().getJob().getNameJobStep());
            System.out.println("active  : "+model.isActive());
            System.out.println("failedDependency:     "+model.cellHasFailedDependency());
            System.out.println("hasInheritedFail:     "+model.cellHasInheritedFail());
            System.out.println("hasInheritedOVerride: "+model.cellHasInheritedOverride());
            System.out.println("hasOverridenFail:     "+model.cellHasOverridenFail());
            System.out.println("hasWarning:           "+model.cellHasWarning());
            
            
            
        }
            
            
    }

    
    public void setModel(QcCellModel item) {
        //qcDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.QC );
        model=item;
        qcDoubtType=model.getCellDoubtType();
       
       
         if(model.isActive()){
        qcLabel.setDisable(false);
        }else{
        qcLabel.setStyle("-fx-background-color: "+JobSummaryColors.QC_NO_SEQ_PRESENT);
        qcLabel.setDisable(true);
        }
        
        //    applyColor();
        labelColor();
       
        
        model.activeProperty().addListener(ACTIVE_LISTENER);
        
        
        /*  model.cellProperty().addListener(QC_DOUBT_LISTENER);
        
        model.inheritanceProperty().addListener(QC_INHERITANCE_LISTENER);
        
        model.overrideProperty().addListener(QC_OVERRIDE_LISTENER); */
        
        
        
        model.queryProperty().addListener(QUERY_LISTENER);
        model.showOverrideProperty().addListener(SHOW_OVERRIDE_CHANGE_LISTENER);
       
    }

    void setView(QcCellView vw) {
        view=vw;
        
    }
    
     @FXML
    void onContextMenuRequested(ContextMenuEvent event) {
         final ContextMenu contextMenu=new ContextMenu();
            if( model.getJobSummaryModel().getSubsurface()!=null){     //only enabled for subsurfaces and NOT for sequences.
            final MenuItem overrideMenuItem=new MenuItem("Manage Doubt");
            overrideMenuItem.setOnAction(e->{
            System.out.println("Fetching doubt information for Subsurface: "+model.getJobSummaryModel().getSubsurface().getSubsurface()+" job: "+model.getJobSummaryModel().getJob().getNameJobStep());
            
            
            
            model.setShowOverride(true);
            
            });
            contextMenu.getItems().add(overrideMenuItem);
            }
            
            
    }
    
    
    
    /**
     * Set the background based on whether the doubt flag is set.
     */
    private ChangeListener<Boolean> QC_DOUBT_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
           
          //  applyColor();
          labelColor();
        }
    };
    
    
    /**
     * Set the inheritance on and off.
     * The background color changes if the inheritance is set.
     **/
    private ChangeListener<Boolean> QC_INHERITANCE_LISTENER=new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
           
         //   applyColor();
         labelColor();
        }
        
    };
    
    
     /**
     * Set the override on and off.
     * The background color changes if the override is set. At this point model.isTime()==True
     **/
    private ChangeListener<Boolean> QC_OVERRIDE_LISTENER=new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            
         //   applyColor();
         labelColor();
        }
        
    };
    
    /**
     * true if seq is present in summary table
     * false otherwise. 
     * all colors set to JobSummaryColors.NO_SEQ
     **/
     private ChangeListener<Boolean> ACTIVE_LISTENER=new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                /*if(model.getJobSummaryModel().getSubsurface()!=null){
                System.out.println(".changed(): calling activeProperty Listener on "+model.getJobSummaryModel()
                .getJob()
                .getNameJobStep()+" for "+
                model.getJobSummaryModel()
                .getSubsurface()
                .getSubsurface()+
                " active: "+newValue);
                }
                */
                qcLabel.setDisable(false);
              //  applyColor();
              labelColor();
                
            }if(!newValue){
                /* if(model.getJobSummaryModel().getSubsurface()!=null){
                System.out.println(".changed(): calling activeProperty Listener on "+model.getJobSummaryModel().getJob().getNameJobStep()+" for "+model.getJobSummaryModel().getSubsurface().getSubsurface()+" active: "+!newValue);
                }*/
                qcLabel.setStyle("-fx-background-color: "+JobSummaryColors.QC_NO_SEQ_PRESENT);
                qcLabel.setDisable(true);
            }
        }
     };
     
     
     
     
     /**
      * toggled to indicate a change in the database table
      * 
      **/
     private ChangeListener<Boolean> QUERY_LISTENER=new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            
            
            /*  model.cellProperty().removeListener(QC_DOUBT_LISTENER);
            model.inheritanceProperty().removeListener(QC_INHERITANCE_LISTENER);
            model.overrideProperty().removeListener(QC_OVERRIDE_LISTENER);
            
            //does a doubt exist for the current model params (job,sub,doubttype) ?
            Doubt doubt=doubtService.getDoubtFor(model.getJobSummaryModel().getSubsurface(), model.getJobSummaryModel().getJob(),qcDoubtType);
            if(doubt!=null){   //if yes then set the isTime()=true boolean on the model.
            model.setCellProperty(true);
            // model.getJobSummaryModel().getFeModelQcCellModel().setCellProperty(true);
            
            //if there is a doubt, then fetch the status .i.e is the doubt overriden?
            //DoubtStatus ds=new ArrayList<>(doubt.getDoubtStatuses()).get(0);
            DoubtStatus ds=doubtStatusService.getDoubtStatusForDoubt(doubt).get(0);
            if(ds.getStatus().equals(DoubtStatusModel.OVERRIDE)){
            model.setOverride(true);
            }else{
            model.setOverride(false);
            }
            }else{           //else isTime()=false
            model.setCellProperty(false);*/
               // model.getJobSummaryModel().getFeModelQcCellModel().setCellProperty(false);
                 
           //  }
            //if Time()==false. next check if the model has any inherited any doubts. Ie. (job,sub,inhdbtype)==null?
            //is not null then there;s an inherited doubt. set model.inheritance = true
            //if null then there is no inherited doubt. set model.inheritance = false;
            //if there is an inherited doubt.
            //find cause.
            //find status and state of cause.
            //set model.inherited=true.
            //if cause.status=override. then use color TIME_INH_OVER
            //else use TIME_INH_DOUBT
            //addressed in applyColor() / labelColor()
            
           //  applyColor();
           labelColor();
            
             
           /*   model.cellProperty().addListener(QC_DOUBT_LISTENER);
           model.inheritanceProperty().addListener(QC_INHERITANCE_LISTENER);
           model.overrideProperty().addListener(QC_OVERRIDE_LISTENER);*/
         
             
            
        }
     };
    
     
     private ChangeListener<Boolean> SHOW_OVERRIDE_CHANGE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                  if(model.getJobSummaryModel().getSubsurface()!=null){
                   System.out.println(".changed(): show overrride dialog for " + model.
                              getJobSummaryModel().
                              getSubsurface().
                              getSubsurface()
                              + " job: "
                              + model.
                                      getJobSummaryModel().
                                      getJob().
                                      getNameJobStep());

                      // DoubtType modelDoubtType=doubtTypeService.getDoubtTypeByName(model.getContextAskedForDoubtType());
                      List<Doubt> doubts = doubtService.getDoubtFor(model.getJobSummaryModel().getSubsurface(), model.getJobSummaryModel().getJob(), qcDoubtType);
                    //DoubtStatus ds=new ArrayList<>(doubt.getDoubtStatuses()).get(0);
                    // DoubtStatus ds=doubtStatusService.getDoubtStatusForDoubt(doubt).get(0);
                    
                    for(Doubt doubt:doubts){
                      OverrideModel ovrModel = new OverrideModel(model);

                      Link l = doubt.getLink();
                      String linkDesc = l.getParent().getNameJobStep() + " <---> " + l.getChild().getNameJobStep();
                      String parentJobName = l.getParent().getNameJobStep();
                      String doubtStatusReason = doubt.getReason();
                      String stat = doubt.getStatus();
                      String userComment = doubt.getComments();
                      String earlierStat = doubt.getStatus();

                      ovrModel.setDoubt(doubt);
                      //   ovrModel.setDoubtStatus(ds);

                      ovrModel.setTypeText(qcDoubtType.getName());
                      ovrModel.setSubsurfaceName(model.getJobSummaryModel().getSubsurface().getSubsurface());
                      ovrModel.setLinkDescription(linkDesc);
                      ovrModel.setParentJobName(parentJobName);
                      ovrModel.setDoubtStatusComment(doubt.getReason());
                      ovrModel.setStatus(stat);
                      ovrModel.setUserCommentStack(userComment);
                      ovrModel.setEarlierStatus(earlierStat);

                      OverrideView ovrView = new OverrideView(ovrModel);
                      
                    }
                    model.setShowOverride(false);
                }
            }
        }
    };
     

     
     private void labelColor(){
        
        
         String color=new String();
           if (model.isActive()) {

               if (model.hasFailedQcDependency()) {
                   color = JobSummaryColors.QC_DOUBT;
               } else if (model.hasInheritedQcFail()) {
                   color = JobSummaryColors.QC_INHERITED_DOUBT;
               } else if (model.hasOverridenQcFail()) {
                   color = JobSummaryColors.QC_OVERRRIDE;
               } else if (model.hasInheritedQcOverride()) {
                   color = JobSummaryColors.QC_INHERITED_OVERRRIDE;
               } else if (model.hasWarningForQc()) {
                   color = JobSummaryColors.QC_WARNING;
               } else {
                   color = JobSummaryColors.QC_GOOD;
               }

           }else{
               color=JobSummaryColors.QC_NO_SEQ_PRESENT;
           }
        
         
     
     qcLabel.setStyle("-fx-background-color: "+color);
     
     }
    
}
