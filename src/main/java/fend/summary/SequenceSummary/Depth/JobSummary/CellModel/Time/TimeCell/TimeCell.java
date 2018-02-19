/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCell;

import db.model.Job;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCellView;
import fend.summary.SequenceSummary.Depth.JobSummary_new.JobSummaryModel;
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
    String type=DoubtTypeModel.TIME;

    public TimeCell(int depthId, Job jobkey) {
        
       this.depthId=depthId;
       this.job=jobkey;
       model=new TimeCellModel();
       view=new TimeCellView(model);
        
    }
    
    protected void updateItem(Boolean t,boolean empty){
        super.updateItem(t, empty);
        if(!empty){
           
            int index=getIndex();
            TimeCellModel tcm=getTreeTableView().getTreeItem(index).getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(job).getTimeCellModel();
            JobSummaryModel jsm=tcm.getJobSummaryModel();
            model.setJobSummaryModel(jsm);
            if(jsm.getSubsurface()==null){
            
            }else{
               
                System.out.println("fend.summary.SequenceSummary.Depth.TimeCell.updateItem(): Setting subsurface to "+jsm.getSubsurface().getSubsurface());
            }
            
            model.setTimeProperty(jsm.getTimeCellModel().isTimeProperty());
           // model.setActive(true);
            model.setInheritance(jsm.getTimeCellModel().isInheritance());
            model.setOverride(jsm.getTimeCellModel().isOverride());
            model.setQuery(jsm.getTimeCellModel().isQuery());
            model.setShowOverride(jsm.getTimeCellModel().isShowOverride());
            model.setState(jsm.getTimeCellModel().getState());
            
            if(jsm.getSubsurface()!=null){
                model.getJobSummaryModel().setSubsurface(jsm.getSubsurface());
            }
            
            if(!t){
                model.setActive(false);
               //  System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.updateItem(): index is : "+index+" item is "+getTableView().getItems().get(index).getSequence().getSequenceno());
            
            }
            else{
                model.setActive(true);
               //  System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.updateItem(): index is : "+index+" item is "+getTableView().getItems().get(index).getSequence().getSequenceno());
            
            }
           jsm.setFeModeltimeCellModel(model);
            
            final ContextMenu contextMenu=new ContextMenu();
            if(model.isTimeProperty()&& model.getJobSummaryModel().getSubsurface()!=null){     //only enabled for subsurfaces and NOT for sequences.
            final MenuItem overrideMenuItem=new MenuItem("Manage Doubt");
            overrideMenuItem.setOnAction(e->{
            System.out.println("Fetching doubt information for Subsurface: "+model.getJobSummaryModel().getSubsurface().getSubsurface()+" job: ");
            
            
            
            model.setShowOverride(true);
            
            });
            contextMenu.getItems().add(overrideMenuItem);
            }
            setContextMenu(contextMenu);
            
            setGraphic(view);
        }
    }
    
}
