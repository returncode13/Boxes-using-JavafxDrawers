/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace.TraceCell;

import db.model.Job;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace.TraceCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace.TraceCellView;

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
public class TraceCell  extends TreeTableCell<SequenceSummary, Boolean>{
    TraceCellView view;
    TraceCellModel  model;
    int depthId;
    Job job;
    String type=DoubtTypeModel.TIME;

    public TraceCell(int depthId, Job jobkey) {
        
       this.depthId=depthId;
       this.job=jobkey;
       model=new TraceCellModel();
       view=new TraceCellView(model);
        
    }
    
    protected void updateItem(Boolean t,boolean empty){
        super.updateItem(t, empty);
        if(!empty){
           
            int index=getIndex();
            TraceCellModel tcm=getTreeTableView().getTreeItem(index).getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(job).getTraceCellModel();
            JobSummaryModel jsm=tcm.getJobSummaryModel();
            //model.setJobSummaryModel(jsm);
            model=tcm;
             view.getController().setModel(model);
             /*if(jsm.getSubsurface()==null){
             
             }else{
             
             System.out.println("fend.summary.SequenceSummary.Depth.TraceCell.updateItem(): Setting subsurface to "+jsm.getSubsurface().getSubsurface());
             }
             */
            /* model.setCellProperty(jsm.getTraceCellModel().cellHasDoubt());
            // model.setActive(true);
            model.setInheritance(jsm.getTraceCellModel().isInheritance());
            model.setOverride(jsm.getTraceCellModel().isOverride());
            model.setQuery(jsm.getTraceCellModel().isQuery());
            model.setShowOverride(jsm.getTraceCellModel().isShowOverride());
            model.setState(jsm.getTraceCellModel().getState());
            
            if(jsm.getSubsurface()!=null){
            model.getJobSummaryModel().setSubsurface(jsm.getSubsurface());
            }*/
            
             if(!t){
            model.setActive(false);
            //  System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.updateItem(): index is : "+index+" item is "+getTableView().getItems().get(index).getSequence().getSequenceno());
            
            }
            else{
                // model.setActive(false);
            model.setActive(true);
            //  System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.updateItem(): index is : "+index+" item is "+getTableView().getItems().get(index).getSequence().getSequenceno());
            
            }
           // jsm.setFeModelTraceCellModel(model);*/
            
            final ContextMenu contextMenu=new ContextMenu();
            if(model.cellHasDoubt()&& model.getJobSummaryModel().getSubsurface()!=null){     //only enabled for subsurfaces and NOT for sequences.
            final MenuItem overrideMenuItem=new MenuItem("Manage Doubt");
            overrideMenuItem.setOnAction(e->{
            System.out.println("Fetching doubt information for Subsurface: "+model.getJobSummaryModel().getSubsurface().getSubsurface()+" job: "+model.getJobSummaryModel().getJob().getNameJobStep());
            
            
            
            model.setShowOverride(true);
            
            });
            contextMenu.getItems().add(overrideMenuItem);
            }
            setContextMenu(contextMenu);
            
            setGraphic(view);
        }
    }
    
}
