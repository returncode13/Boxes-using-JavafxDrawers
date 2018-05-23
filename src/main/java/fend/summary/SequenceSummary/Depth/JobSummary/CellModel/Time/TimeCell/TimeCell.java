/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCell;

import db.model.DoubtType;
import db.model.Job;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCellView;
import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel;
import fend.summary.SequenceSummary.SequenceSummary;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeTableCell;
import middleware.doubt.DoubtTypeModel;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class TimeCell  extends TreeTableCell<SequenceSummary, Boolean>{
    TimeCellView view;
    TimeCellModel  model;
    int depthId;
    Job job;
    //String type=DoubtTypeModel.TIME;
     DoubtType type;
     
    public TimeCell(int depthId, Job jobkey,DoubtType timeDoubtType) {
        
       this.depthId=depthId;
       this.job=jobkey;
       model=new TimeCellModel();
       model.setCellDoubtType(timeDoubtType);
       type=timeDoubtType;
       view=new TimeCellView(model);
        
    }
    
    protected void updateItem(Boolean t,boolean empty){
        super.updateItem(t, empty);
        if(!empty){
           
            int index=getIndex();
            TimeCellModel tcm=getTreeTableView().getTreeItem(index).getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(job).getTimeCellModel();
            JobSummaryModel jsm=tcm.getJobSummaryModel();
            
            //model=new TimeCellModel();
            model=tcm;
            model.setJobSummaryModel(jsm);
            model.setCellDoubtType(type);
            view.getController().setModel(model);
            
            if(!t){
               // model.setActive(true);
            model.setActive(false);
            //  System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.updateItem(): index is : "+index+" item is "+getTableView().getItems().get(index).getSequence().getSequenceno());
            
            }
            else{
           // model.setActive(false);
            model.setActive(true);
            //  System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.updateItem(): index is : "+index+" item is "+getTableView().getItems().get(index).getSequence().getSequenceno());
            
            }
            //jsm.setFeModelTimeCellModel(model);
            
            
            final ContextMenu contextMenu=new ContextMenu();
            if(model.cellHasFailedDependency()&& model.getJobSummaryModel().getSubsurface()!=null){     //only enabled for subsurfaces and NOT for sequences.
            final MenuItem overrideMenuItem=new MenuItem("Manage Doubt");
            overrideMenuItem.setOnAction(e->{
            System.out.println("Fetching doubt information for Subsurface: "+model.getJobSummaryModel().getSubsurface().getSubsurface()+" job: ");
            
            
            
            model.setShowOverride(true);
            
            });
            contextMenu.getItems().add(overrideMenuItem);
            }
            setContextMenu(contextMenu);
            
            setGraphic(view);
            setStyle("-fx-padding: 0 0 0 0;");
        }
    }
    
}
