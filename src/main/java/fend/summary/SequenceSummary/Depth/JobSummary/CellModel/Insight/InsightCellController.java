/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Insight;

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
import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryImages;
import fend.summary.SequenceSummary.colors.SequenceSummaryColors;
import fend.summary.override.OverrideModel;
import fend.summary.override.OverrideView;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import middleware.doubt.DoubtStatusModel;
import middleware.doubt.DoubtTypeModel;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class InsightCellController {
    InsightCellModel model;
    InsightCellView view;
    DoubtStatusService doubtStatusService=new DoubtStatusServiceImpl();
    DoubtTypeService doubtTypeService=new DoubtTypeServiceImpl();
    DoubtService doubtService=new DoubtServiceImpl();
    DoubtType insightDoubtType;
    
    
     private JobSummaryImages jobSummaryImages;
     private Image doubtImage;
     private Image inheritedDoubtImage;
     private Image overridenDoubtImage;
     private Image inheritedOverridenDoubtImage;
     private Image warningImage;
     private Image goodImage;
     private Image noSeqPresImage;
    
     /* @FXML
     private Label insightLabel;*/
      @FXML
    private ImageView insightCellImage;

    @FXML
    void insightClicked(MouseEvent event) {
        if(model.getJobSummaryModel().getSubsurface()!=null){
            
            System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Insight.InsightCellController.insightClicked(): time clicked for  "+model.getJobSummaryModel().getJob().getNameJobStep());
            System.out.println("active  : "+model.isActive());
            /*System.out.println("doubt   : "+model.cellHasDoubt());
            System.out.println("state   : "+model.getState());
            System.out.println("inherit : "+model.isInheritance());
            System.out.println("override: "+model.isOverride());
            System.out.println("query   : "+model.isQuery());
            System.out.println("showOver: "+model.isShowOverride());*/
            System.out.println("failedDependency:     "+model.cellHasFailedDependency());
            System.out.println("hasInheritedFail:     "+model.cellHasInheritedFail());
            System.out.println("hasInheritedOVerride: "+model.cellHasInheritedOverride());
            System.out.println("hasOverridenFail:     "+model.cellHasOverridenFail());
            System.out.println("hasWarning:           "+model.cellHasWarning());
         
            
            
            
        }
            
            
    }

    
    public void setModel(InsightCellModel item) {
        model=item;
        insightDoubtType=model.getCellDoubtType();
        
        if(model.isActive()){
           // insightLabel.setDisable(false);
           insightCellImage.setDisable(false);
            // model.getJobSummaryModel().toggleQuery();
        }else{
            /* insightLabel.setStyle("-fx-background-color: "+JobSummaryColors.INSIGHT_NO_SEQ_PRESENT);
            insightLabel.setDisable(true);*/
            insightCellImage.setStyle("-fx-background-color: "+JobSummaryColors.INSIGHT_NO_SEQ_PRESENT);
             insightCellImage.setDisable(true);
        }
        try{
            jobSummaryImages=model.getJobSummaryModel().getSummaryModel().getJobSummaryImages();
        }catch(NullPointerException npe){
            jobSummaryImages=new JobSummaryImages();
        }
        doubtImage = jobSummaryImages.getINSIGHT_DOUBT();
        inheritedDoubtImage = jobSummaryImages.getINSIGHT_INHERITED_DOUBT();
        overridenDoubtImage = jobSummaryImages.getINSIGHT_OVERRIDE();
        inheritedOverridenDoubtImage = jobSummaryImages.getINSIGHT_INHERITED_OVERRIDE();
        warningImage = jobSummaryImages.getINSIGHT_WARNING();
        goodImage = jobSummaryImages.getINSIGHT_GOOD();
        noSeqPresImage = jobSummaryImages.getINSIGHT_NO_SEQ_PRESENT();
        
          //  applyColor();
        labelColorForSub();
       
       
        model.activeProperty().addListener(ACTIVE_LISTENER);
        model.queryProperty().addListener(QUERY_LISTENER);
        model.showOverrideProperty().addListener(SHOW_OVERRIDE_CHANGE_LISTENER);
    }

    void setView(InsightCellView vw) {
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
           
          //  applyColor();
          labelColorForSub();
          
        }
    };
    
    
    /**
     * Set the inheritance on and off.
     * The background color changes if the inheritance is set.
     **/
    private ChangeListener<Boolean> TRACE_INHERITANCE_LISTENER=new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            
            
           // applyColor();
            labelColorForSub();
        }
        
    };
    
    
     /**
     * Set the override on and off.
     * The background color changes if the override is set. At this point model.isTime()==True
     **/
    private ChangeListener<Boolean> TRACE_OVERRIDE_LISTENER=new ChangeListener<Boolean>(){
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
           
            
          //  applyColor();
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
                //insightLabel.setDisable(false);
                insightCellImage.setDisable(false);                
               // applyColor();
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
                /* insightLabel.setStyle("-fx-background-color: "+JobSummaryColors.TRACES_NO_SEQ_PRESENT);
                insightLabel.setDisable(true);*/
                insightCellImage.setStyle("-fx-background-color: "+JobSummaryColors.TRACES_NO_SEQ_PRESENT);
                insightCellImage.setDisable(true);
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
             /* if(model.getJobSummaryModel().getSubsurface()!=null)
             System.out.println(".changed(): Insight Query listener called on "+model.
             getJobSummaryModel().
             getSubsurface().
             getSubsurface()+" for "+
             
             model.
             getJobSummaryModel().
             getJob().
             getNameJobStep());
             */
             /*
             model.cellProperty().removeListener(TRACE_DOUBT_LISTENER);
             model.inheritanceProperty().removeListener(TRACE_INHERITANCE_LISTENER);
             model.overrideProperty().removeListener(TRACE_OVERRIDE_LISTENER);
             */
             //does a doubt exist for the current model params (job,sub,doubttype) ?
             /* Doubt doubt=doubtService.getDoubtFor(model.getJobSummaryModel().getSubsurface(), model.getJobSummaryModel().getJob(),insightDoubtType);
             if(doubt!=null){   //if yes then set the isTime()=true boolean on the model.
             model.setCellProperty(true);
             //if there is a doubt, then fetch the status .i.e is the doubt overriden?
             //DoubtStatus ds=new ArrayList<>(doubt.getDoubtStatuses()).get(0);
             DoubtStatus ds=doubtStatusService.getDoubtStatusForDoubt(doubt).get(0);
             if(ds.getStatus().equals(DoubtStatusModel.OVERRIDE)){
             model.setOverride(true);
             
             }else{
             model.setOverride(false);
             }
             }else{           //else isTime()=false
             model.setCellProperty(false);
             }*/
            //if Time()==false. next check if the model has any inherited any doubts. Ie. (job,sub,inhdbtype)==null?
            //is not null then there;s an inherited doubt. set model.inheritance = true
            //if null then there is no inherited doubt. set model.inheritance = false;
            //if there is an inherited doubt.
            //find cause.
            //find status and state of cause.
            //set model.inherited=true.
            //if cause.status=override. then use color TIME_INH_OVER
            //else use TIME_INH_DOUBT
            //addressed in applyColor() //labelColor()
            
           //  applyColor();
           if(model.getJobSummaryModel().isChild()){
              labelColorForSub();
          }else{
              labelColorForSeq();
          }
           
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
                   List<Doubt> doubts = doubtService.getDoubtFor(model.getJobSummaryModel().getSubsurface(), model.getJobSummaryModel().getJob(), insightDoubtType);
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

                        ovrModel.setTypeText(insightDoubtType.getName());
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
     
     
     /**
      * figure out color
      **/
     
     
     
     
       private void labelColorForSub(){
           
        
           
           /*
           String color=new String();
           
           if(model.isActive()){
           model.calculateCellState();
           if(model.getCellState()== CellState.FAILED) color= JobSummaryColors.INSIGHT_DOUBT;
           else if(model.getCellState() == CellState.INHERITED_FAIL) color= JobSummaryColors.INSIGHT_INHERITED_DOUBT;
           else if(model.getCellState() == CellState.OVERRIDE) color= JobSummaryColors.INSIGHT_OVERRRIDE;
           else if(model.getCellState() == CellState.INHERITED_OVERRIDE) color= JobSummaryColors.INSIGHT_INHERITED_OVERRRIDE;
           else if(model.getCellState() == CellState.WARNING) color= JobSummaryColors.INSIGHT_WARNING;
           else if(model.getCellState() == CellState.GOOD) color= JobSummaryColors.INSIGHT_GOOD;
           }else{
           color=JobSummaryColors.INSIGHT_NO_SEQ_PRESENT;
           }
           insightLabel.setStyle("-fx-background-color: "+color);*/
             Image image=noSeqPresImage;
           String color=new String();
           if(model.isActive()){
               
               
               
               
               if(model.getCellState() == CellState.FAILED) image=doubtImage;
               else if(model.getCellState() == CellState.INHERITED_FAIL) image=inheritedDoubtImage;
               else if(model.getCellState() == CellState.OVERRIDE) image=overridenDoubtImage;
               else if(model.getCellState() == CellState.INHERITED_OVERRIDE) image=inheritedOverridenDoubtImage;
               else if(model.getCellState() == CellState.WARNING) image=warningImage;
               else if(model.getCellState() == CellState.GOOD) image=goodImage;
           }else{
           image=noSeqPresImage;
           }
          //insightLabel.setGraphic(new ImageView(image));
          insightCellImage.setImage(image);
          /*
          try{
          if(model.getJobSummaryModel().isParent()){
          color=SequenceSummaryColors.SEQUENCE;
          }
          else{
          color=SequenceSummaryColors.SUBSURFACE;
          }
          }catch(NullPointerException npe){
          
          }
          */
          // insightLabel.setStyle("-fx-background-color: "+color);
          //insightLabel.setStyle("-fx-background-color: transparent;");
          insightCellImage.setStyle("-fx-background-color: transparent;");
            
     
        }
       
       private void labelColorForSeq(){
           
           /*String color=new String();
           
           if(model.isActive()){
           
           if(model.getCellState()== CellState.FAILED) color= JobSummaryColors.INSIGHT_DOUBT;
           else if(model.getCellState() == CellState.INHERITED_FAIL) color= JobSummaryColors.INSIGHT_INHERITED_DOUBT;
           else if(model.getCellState() == CellState.OVERRIDE) color= JobSummaryColors.INSIGHT_OVERRRIDE;
           else if(model.getCellState() == CellState.INHERITED_OVERRIDE) color= JobSummaryColors.INSIGHT_INHERITED_OVERRRIDE;
           else if(model.getCellState() == CellState.WARNING) color= JobSummaryColors.INSIGHT_WARNING;
           else if(model.getCellState() == CellState.GOOD) color= JobSummaryColors.INSIGHT_GOOD;
           }else{
           color=JobSummaryColors.INSIGHT_NO_SEQ_PRESENT;
           }
           insightLabel.setStyle("-fx-background-color: "+color);
           
           }*/
           
            Image image=noSeqPresImage;
           String color=new String();
           if(model.isActive()){
               if(model.getCellState() == CellState.FAILED) image=doubtImage;
               else if(model.getCellState() == CellState.INHERITED_FAIL) image=inheritedDoubtImage;
               else if(model.getCellState() == CellState.OVERRIDE) image=overridenDoubtImage;
               else if(model.getCellState() == CellState.INHERITED_OVERRIDE) image=inheritedOverridenDoubtImage;
               else if(model.getCellState() == CellState.WARNING) image=warningImage;
               else if(model.getCellState() == CellState.GOOD) image=goodImage;
           }else{
           image=noSeqPresImage;
           }
          //insightLabel.setGraphic(new ImageView(image));
          insightCellImage.setImage(image);
          /*
          try{
          if(model.getJobSummaryModel().isParent()){
          color=SequenceSummaryColors.SEQUENCE;
          }
          else{
          color=SequenceSummaryColors.SUBSURFACE;
          }
          }catch(NullPointerException npe){
          
          }
          */
          // insightLabel.setStyle("-fx-background-color: "+color);
          //insightLabel.setStyle("-fx-background-color: transparent;");
          insightCellImage.setStyle("-fx-background-color: transparent;"); 
            
       }

   
    
}
