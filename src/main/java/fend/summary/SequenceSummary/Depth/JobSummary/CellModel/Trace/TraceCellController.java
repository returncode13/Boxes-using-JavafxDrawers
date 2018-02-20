/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace;

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
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import middleware.doubt.DoubtStatusModel;
import middleware.doubt.DoubtTypeModel;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class TraceCellController {
    TraceCellModel model;
    TraceCellView view;
    DoubtStatusService doubtStatusService=new DoubtStatusServiceImpl();
    DoubtTypeService doubtTypeService=new DoubtTypeServiceImpl();
    DoubtService doubtService=new DoubtServiceImpl();
    DoubtType traceDoubtType;
    
    @FXML
    private Label traceLabel;

    @FXML
    void traceClicked(MouseEvent event) {
        if(model.getJobSummaryModel().getSubsurface()!=null){
            
            System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TraceCellController.timeClicked(): time clicked for  "+model.getJobSummaryModel().getJob().getNameJobStep());
            System.out.println("active  : "+model.isActive());
            System.out.println("doubt   : "+model.cellHasDoubt());
            System.out.println("state   : "+model.getState());
            System.out.println("inherit : "+model.isInheritance());
            System.out.println("override: "+model.isOverride());
            System.out.println("query   : "+model.isQuery());
            System.out.println("showOver: "+model.isShowOverride());
            
            
            
        }
            
            
    }

    
    public void setModel(TraceCellModel item) {
        model=item;
        
        
        if(model.isActive()){
            traceLabel.setDisable(false);
        }else{
             traceLabel.setStyle("-fx-background-color: "+JobSummaryColors.TRACES_NO_SEQ_PRESENT);
            traceLabel.setDisable(true);
        }
        
            applyColor();
       
        model.activeProperty().addListener(ACTIVE_LISTENER);
        traceDoubtType=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TRACES);
        
        model.cellProperty().addListener(TRACE_DOUBT_LISTENER);
        
        model.inheritanceProperty().addListener(TRACE_INHERITANCE_LISTENER);
        
        model.overrideProperty().addListener(TRACE_OVERRIDE_LISTENER);
        
        
        
        model.queryProperty().addListener(QUERY_LISTENER);
        model.showOverrideProperty().addListener(SHOW_OVERRIDE_CHANGE_LISTENER);
    }

    void setView(TraceCellView vw) {
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
    private ChangeListener<Boolean> TRACE_DOUBT_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
           
            applyColor();
        }
    };
    
    
    /**
     * Set the inheritance on and off.
     * The background color changes if the inheritance is set.
     **/
    private ChangeListener<Boolean> TRACE_INHERITANCE_LISTENER=new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            
            
            applyColor();
        }
        
    };
    
    
     /**
     * Set the override on and off.
     * The background color changes if the override is set. At this point model.isTime()==True
     **/
    private ChangeListener<Boolean> TRACE_OVERRIDE_LISTENER=new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
           
            
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
                
                traceLabel.setDisable(false);
                applyColor();
                
            }if(!newValue){
                 if(model.getJobSummaryModel().getSubsurface()!=null){
                     System.out.println(".changed(): calling activeProperty Listener on "+model.getJobSummaryModel().getJob().getNameJobStep()+" for "+model.getJobSummaryModel().getSubsurface().getSubsurface()+" active: "+!newValue);
                 }
                traceLabel.setStyle("-fx-background-color: "+JobSummaryColors.TRACES_NO_SEQ_PRESENT);
                traceLabel.setDisable(true);
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
             if(model.getJobSummaryModel().getSubsurface()!=null)
             System.out.println(".changed(): Query listener called on "+model.
                     getJobSummaryModel().
                     getSubsurface().
                     getSubsurface()+" for "+
                             
                             model.
                             getJobSummaryModel().
                             getJob().
                             getNameJobStep());
           
             
             model.cellProperty().removeListener(TRACE_DOUBT_LISTENER);
             model.inheritanceProperty().removeListener(TRACE_INHERITANCE_LISTENER);
             model.overrideProperty().removeListener(TRACE_OVERRIDE_LISTENER);
             
             //does a doubt exist for the current model params (job,sub,doubttype) ?
             Doubt doubt=doubtService.getDoubtFor(model.getJobSummaryModel().getSubsurface(), model.getJobSummaryModel().getJob(),traceDoubtType);
             if(doubt!=null){   //if yes then set the isTime()=true boolean on the model.
                 model.setCellProperty(true);
                                //if there is a doubt, then fetch the status .i.e is the doubt overriden?
                 DoubtStatus ds=new ArrayList<>(doubt.getDoubtStatuses()).get(0);
                 if(ds.getStatus().equals(DoubtStatusModel.OVERRIDE)){
                     model.setOverride(true);
                     
                 }else{
                     model.setOverride(false);
                 }
             }else{           //else isTime()=false
                 model.setCellProperty(false);
             }
            //if Time()==false. next check if the model has any inherited any doubts. Ie. (job,sub,inhdbtype)==null?
            //is not null then there;s an inherited doubt. set model.inheritance = true
            //if null then there is no inherited doubt. set model.inheritance = false;
            //if there is an inherited doubt.
            //find cause.
            //find status and state of cause.
            //set model.inherited=true.
            //if cause.status=override. then use color TIME_INH_OVER
            //else use TIME_INH_DOUBT
            //addressed in applyColor()
            
             applyColor();
            
             
             model.cellProperty().addListener(TRACE_DOUBT_LISTENER);
             model.inheritanceProperty().addListener(TRACE_INHERITANCE_LISTENER);
             model.overrideProperty().addListener(TRACE_OVERRIDE_LISTENER);
         
             
            
        }
     };
    
     
     private ChangeListener<Boolean> SHOW_OVERRIDE_CHANGE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                System.out.println(".changed(): show overrride dialog");
              // DoubtType modelDoubtType=doubtTypeService.getDoubtTypeByName(model.getContextAskedForDoubtType());
                Doubt doubt=doubtService.getDoubtFor(model.getJobSummaryModel().getSubsurface(), model.getJobSummaryModel().getJob(),traceDoubtType);
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
                    
                    ovrModel.setTypeText(traceDoubtType.getName());
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
             
             if(model.cellHasDoubt()){
                 if(model.isOverride()){
                 //    System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TraceCellController.applyColor(): Setting OVERRIDE");
                     traceLabel.setStyle("-fx-background-color: "+JobSummaryColors.TRACES_OVERRRIDE);
                 }else{
                     if(model.getState().equals(DoubtStatusModel.ERROR)){
                   //      System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TraceCellController.applyColor(): Setting ERROR");
                         traceLabel.setStyle("-fx-background-color: "+JobSummaryColors.TRACES_DOUBT);
                     }
                     if(model.getState().equals(DoubtStatusModel.WARNING)){
                   //      System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TraceCellController.applyColor(): Setting WARNING");
                         traceLabel.setStyle("-fx-background-color: "+JobSummaryColors.TRACES_WARNING);
                     }
                     
                     
                 }
                 
             }else{
                 Doubt cause=doubtService.getCauseOfInheritedDoubtForType(model.getJobSummaryModel().getSubsurface(), model.getJobSummaryModel().getJob(), traceDoubtType);
                 if(cause!=null){
                      if(model.getJobSummaryModel().getSubsurface()!=null){
                          System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace.TraceCellController.applyColor(): setting inheritance in "+model.
                     getJobSummaryModel().
                     getSubsurface().
                     getSubsurface()+" for "+
                             
                             model.
                             getJobSummaryModel().
                             getJob().
                             getNameJobStep());
                      
                          //System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace.TraceCellController.applyColor() cause found is "+cause.getId());
                      }
                     model.setInheritance(true);
                 }else{
                     if(model.getJobSummaryModel().getSubsurface()!=null){
                          System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace.TraceCellController.applyColor(): No cause for inherited doubt found in  "+model.
                     getJobSummaryModel().
                     getSubsurface().
                     getSubsurface()+" for "+
                             
                             model.
                             getJobSummaryModel().
                             getJob().
                             getNameJobStep());
                      }
                     model.setInheritance(false);
                 }
                         
                 
                 if(model.isInheritance()){
                     
                   DoubtStatus ds=new ArrayList<>(cause.getDoubtStatuses()).get(0);
                   if(ds.getStatus().equals(DoubtStatusModel.OVERRIDE)){
                      /// System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TraceCellController.applyColor(): Setting INHERITED_OVERRIDE");
                       traceLabel.setStyle("-fx-background-color: "+JobSummaryColors.TRACES_INHERITED_OVERRRIDE);
                   }else{
                      // System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TraceCellController.applyColor(): Setting INHERITED_DOUBT");
                       traceLabel.setStyle("-fx-background-color: "+JobSummaryColors.TRACES_INHERITED_DOUBT);
                   }
                     
                     
                 }else{
                     //System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TraceCellController.applyColor(): Setting GOOD");
                     traceLabel.setStyle("-fx-background-color: "+JobSummaryColors.TRACES_GOOD);
                             
                 }
             }
             
             
         }else{
            // System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TraceCellController.applyColor(): Setting NO_SEQ_PRESENT");
             traceLabel.setStyle("-fx-background-color: "+JobSummaryColors.TRACES_NO_SEQ_PRESENT);
         }
     }
    
}
