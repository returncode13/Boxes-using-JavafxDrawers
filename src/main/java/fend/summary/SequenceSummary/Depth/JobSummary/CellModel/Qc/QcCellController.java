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
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.CellState;
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
         model.getJobSummaryModel().toggleQuery();
        }else{
        qcLabel.setStyle("-fx-background-color: "+JobSummaryColors.QC_NO_SEQ_PRESENT);
        qcLabel.setDisable(true);
        }
        
        //    applyColor();
               
                    labelColorForSub();
            
        
        model.activeProperty().addListener(ACTIVE_LISTENER);
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
          labelColorForSub();
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
         labelColorForSub();
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
         labelColorForSub();
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
              // model.getJobSummaryModel().toggleQuery();
                if(model.getJobSummaryModel().isChild()){
                    labelColorForSub();
                }else{
                    labelColorForSeq();
                }
                
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
            
           
            
           //  applyColor();
           if(model.getJobSummaryModel().isChild()){
              labelColorForSub();
          }else{
              labelColorForSeq();
          }
            
             
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
     

     
     private void labelColorForSub(){
        
       
         
         
         String color=new String();
           if(model.isActive()){
               model.calculateCellState();
               if(model.getCellState() == CellState.FAILED) color= JobSummaryColors.QC_DOUBT;
               if(model.getCellState() == CellState.INHERITED_FAIL) color= JobSummaryColors.QC_INHERITED_DOUBT;
               if(model.getCellState() == CellState.OVERRIDE) color= JobSummaryColors.QC_OVERRRIDE;
               if(model.getCellState() == CellState.INHERITED_OVERRIDE) color= JobSummaryColors.QC_INHERITED_OVERRRIDE;
               if(model.getCellState() == CellState.WARNING) color= JobSummaryColors.QC_WARNING;
               if(model.getCellState() == CellState.GOOD) color= JobSummaryColors.QC_GOOD;
           }else{
           color=JobSummaryColors.TRACES_NO_SEQ_PRESENT;
           }
           qcLabel.setStyle("-fx-background-color: "+color);
     
     }
     
      private void labelColorForSeq(){
        
        
        
         
         String color=new String();
           if(model.isActive()){
               
               if(model.getCellState() == CellState.FAILED) color= JobSummaryColors.QC_DOUBT;
               if(model.getCellState() == CellState.INHERITED_FAIL) color= JobSummaryColors.QC_INHERITED_DOUBT;
               if(model.getCellState() == CellState.OVERRIDE) color= JobSummaryColors.QC_OVERRRIDE;
               if(model.getCellState() == CellState.INHERITED_OVERRIDE) color= JobSummaryColors.QC_INHERITED_OVERRRIDE;
               if(model.getCellState() == CellState.WARNING) color= JobSummaryColors.QC_WARNING;
               if(model.getCellState() == CellState.GOOD) color= JobSummaryColors.QC_GOOD;
           }else{
           color=JobSummaryColors.TRACES_NO_SEQ_PRESENT;
           }
           qcLabel.setStyle("-fx-background-color: "+color);
     
     }
    
}
