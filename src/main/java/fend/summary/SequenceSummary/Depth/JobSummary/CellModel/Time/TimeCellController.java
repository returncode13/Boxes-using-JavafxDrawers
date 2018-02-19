/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time;

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
public class TimeCellController {
    TimeCellModel model;
    TimeCellView view;
    DoubtStatusService doubtStatusService=new DoubtStatusServiceImpl();
    DoubtTypeService doubtTypeService=new DoubtTypeServiceImpl();
    DoubtService doubtService=new DoubtServiceImpl();
    DoubtType timeDoubtType;
    
    @FXML
    private Label timeLabel;

    @FXML
    void timeClicked(MouseEvent event) {
        if(model.getJobSummaryModel().getSubsurface()!=null){
            System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCellController.timeClicked(): time clicked for  "+model.getJobSummaryModel().getJob().getNameJobStep());
            System.out.println("active  : "+model.isActive());
            System.out.println("doubt   : "+model.isTimeProperty());
            System.out.println("state   : "+model.getState());
            System.out.println("inherit : "+model.isInheritance());
            System.out.println("override: "+model.isOverride());
            System.out.println("query   : "+model.isQuery());
            System.out.println("showOver: "+model.isShowOverride());
            
            
            
        }
            
            
    }

    
    void setModel(TimeCellModel item) {
        model=item;
        if(model.isActive()){
            timeLabel.setDisable(false);
        }else{
             timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_NO_SEQ_PRESENT);
            timeLabel.setDisable(true);
        }
        if(model.isActive()){
            /* if(model.isTimeProperty()){
            timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_DOUBT);
            }else{
            if(model.getJobSummaryModel().getSubsurface()!=null){
            if(model.getState().equals(DoubtStatusModel.GOOD)){
            timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_GOOD);
            }
            if(model.getState().equals(DoubtStatusModel.WARNING)){
            timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_WARNING);
            }
            }
            }*/
            applyColor();
        }
       
        
        timeDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TIME);
        
        model.timePropertyProperty().addListener(TIME_DOUBT_LISTENER);
        
        model.inheritanceProperty().addListener(TIME_INHERITANCE_LISTENER);
        
        model.overrideProperty().addListener(TIME_OVERRIDE_LISTENER);
        
        
        model.activeProperty().addListener(ACTIVE_LISTENER);
        model.queryProperty().addListener(QUERY_LISTENER);
        model.showOverrideProperty().addListener(SHOW_OVERRIDE_CHANGE_LISTENER);
    }

    void setView(TimeCellView vw) {
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
    private ChangeListener<Boolean> TIME_DOUBT_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(model.isActive()){
            
                if(newValue){   //is in doubt
                    timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_DOUBT);
                }
                if(!newValue){  //is not in doubt
                    //not in doubt. so decide between the two states. GOOD/WARNING
                    if(model.getJobSummaryModel().getSubsurface()!=null){
                        
                    
                        if(model.getState().equals(DoubtStatusModel.GOOD)){
                            timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_GOOD);
                        }
                         if(model.getState().equals(DoubtStatusModel.WARNING)){
                            timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_WARNING);
                        }
                     }

                }
            
        }
        }
    };
    
    
    /**
     * Set the inheritance on and off.
     * The background color changes if the inheritance is set.
     **/
    private ChangeListener<Boolean> TIME_INHERITANCE_LISTENER=new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            
            if(model.isActive()){
            
            if(!model.isTimeProperty()){        //if the cell has no doubt of its own. proceed to deal with the inheritance
                if(newValue){                   // inheritance=true implies the inheritance is NOT OVERRIDEN. 
                    timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_INHERITED_DOUBT);
                }
                if(!newValue){                           // inheritance=false implies the inheritance is OVERRIDEN. 
                    timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_INHERITED_OVERRRIDE);
                    
                }
            }
        }
        }
        
    };
    
    
     /**
     * Set the override on and off.
     * The background color changes if the override is set. At this point model.isTime()==True
     **/
    private ChangeListener<Boolean> TIME_OVERRIDE_LISTENER=new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            /*if(model.isActive()){
            
            if(model.isTimeProperty()){                 //called only when there's a doubt
            if(newValue){    //override called on this cells doubt
            timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_OVERRRIDE);
            }
            if(!newValue){   //either set back to DOUBTFUL STATE
            timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_DOUBT);
            }
            }
            
            }*/
            
            applyColor();
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
                if(model.getJobSummaryModel().getSubsurface()!=null){
                    System.out.println(".changed(): calling activeProperty Listener on "+model.getJobSummaryModel()
                        .getJob()
                        .getNameJobStep()+" for "+
                        model.getJobSummaryModel()
                                .getSubsurface()
                                .getSubsurface()+
                        " active: "+newValue);
                }
                
                timeLabel.setDisable(false);
                applyColor();
                
            }if(!newValue){
                 if(model.getJobSummaryModel().getSubsurface()!=null){
                     System.out.println(".changed(): calling activeProperty Listener on "+model.getJobSummaryModel().getJob().getNameJobStep()+" for "+model.getJobSummaryModel().getSubsurface().getSubsurface()+" active: "+!newValue);
                 }
                timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_NO_SEQ_PRESENT);
                timeLabel.setDisable(true);
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
            
             //String typeToBeQueried=model.getContextAskedForDoubtType();
             
             Doubt indDoubt=doubtService.getDoubtFor(model.getJobSummaryModel().getSubsurface(), model.getJobSummaryModel().getJob(),timeDoubtType);
             Doubt cause=indDoubt.getDoubtCause();
             DoubtStatus causeDoubtStatus=new ArrayList<>(cause.getDoubtStatuses()).get(0);
             
            
        }
     };
    
     
     private ChangeListener<Boolean> SHOW_OVERRIDE_CHANGE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                System.out.println(".changed(): show overrride dialog");
              // DoubtType modelDoubtType=doubtTypeService.getDoubtTypeByName(model.getContextAskedForDoubtType());
                Doubt doubt=doubtService.getDoubtFor(model.getJobSummaryModel().getSubsurface(), model.getJobSummaryModel().getJob(),timeDoubtType);
                DoubtStatus ds=new ArrayList<>(doubt.getDoubtStatuses()).get(0);
                OverrideModel ovrModel=new OverrideModel(model);
                
                Link l=doubt.getLink();
                String linkDesc=l.getParent().getNameJobStep()+" <---> "+l.getChild().getNameJobStep();
                String parentJobName=l.getParent().getNameJobStep();
                String doubtStatusReason=ds.getReason();
                String stat=ds.getStatus();
                String userComment=ds.getComments();
                String earlierStat=ds.getStatus();
                
                    
                    ovrModel.setDoubt(doubt);
                    ovrModel.setDoubtStatus(ds);
                    
                    ovrModel.setTypeText(timeDoubtType.getName());
                    ovrModel.setSubsurfaceName(model.getJobSummaryModel().getSubsurface().getSubsurface());
                    ovrModel.setLinkDescription(linkDesc);
                    ovrModel.setParentJobName(parentJobName);                   
                    ovrModel.setDoubtStatusComment(ds.getReason());
                    ovrModel.setStatus(stat);
                    ovrModel.setUserCommentStack(userComment);
                    ovrModel.setEarlierStatus(earlierStat);
                    
                OverrideView ovrView=new OverrideView(ovrModel);
                model.setShowOverride(false);
            }
        }
    };
     
     
     /**
      * figure out color
      **/
     private void applyColor(){
         if(model.isActive()){
             
             if(model.isTimeProperty()){
                 if(model.isOverride()){
                     timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_OVERRRIDE);
                 }else{
                     if(model.getState().equals(DoubtStatusModel.ERROR)){
                         timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_DOUBT);
                     }
                     if(model.getState().equals(DoubtStatusModel.WARNING)){
                         timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_WARNING);
                     }
                     
                     
                 }
                 
             }else{
                 Doubt cause=doubtService.getCauseOfInheritedDoubtForType(model.getJobSummaryModel().getSubsurface(), model.getJobSummaryModel().getJob(), timeDoubtType);
                 if(cause!=null){
                     model.setInheritance(true);
                 }else{
                     model.setInheritance(false);
                 }
                         
                 
                 if(model.isInheritance()){
                     
                   DoubtStatus ds=new ArrayList<>(cause.getDoubtStatuses()).get(0);
                   if(ds.getStatus().equals(DoubtStatusModel.OVERRIDE)){
                       timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_INHERITED_OVERRRIDE);
                   }else{
                       timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_INHERITED_DOUBT);
                   }
                     
                     
                 }else{
                     timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_GOOD);
                             
                 }
             }
             
             
         }else{
             timeLabel.setStyle("-fx-background-color: "+JobSummaryColors.TIME_NO_SEQ_PRESENT);
         }
     }
    
}
