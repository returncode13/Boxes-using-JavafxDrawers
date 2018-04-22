/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Qc.QcCell;

import db.model.DoubtType;
import db.model.Job;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Qc.QcCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Qc.QcCellView;

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
public class QcCell  extends TreeTableCell<SequenceSummary, Boolean>{
    QcCellView view;
    QcCellModel  model;
    int depthId;
    Job job;
    //String type=DoubtTypeModel.QC;
    DoubtType type;
    public QcCell(int depthId, Job jobkey,DoubtType qcType) {
        
       this.depthId=depthId;
       this.job=jobkey;
       model=new QcCellModel();
       model.setCellDoubtType(qcType);
       type=qcType;
       view=new QcCellView(model);
        
    }
    
    protected void updateItem(Boolean t,boolean empty){
        super.updateItem(t, empty);
        if(!empty){
           
            int index=getIndex();
            QcCellModel tcm=getTreeTableView().getTreeItem(index).getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(job).getQcCellModel();
            JobSummaryModel jsm=tcm.getJobSummaryModel();
            model.setJobSummaryModel(jsm);
            //model=new QcCellModel();
            model=tcm;
            model.setCellDoubtType(type);
            view.getController().setModel(model);
            /*if(jsm.getSubsurface()==null){
            
            }else{
            
            System.out.println("fend.summary.SequenceSummary.Depth.TimeCell.updateItem(): Setting subsurface to "+jsm.getSubsurface().getSubsurface()+" with tcm.active= "+tcm.isActive() +" T: "+t +" TJ:"+jsm.isActive());
            }*/
            
            /* model.setCellProperty(jsm.getQcCellModel().cellHasDoubt());
            // model.setActive(true);
            model.setInheritance(jsm.getQcCellModel().isInheritance());
            model.setOverride(jsm.getQcCellModel().isOverride());
            model.setQuery(jsm.getQcCellModel().isQuery());
            model.setShowOverride(jsm.getQcCellModel().isShowOverride());
            model.setState(jsm.getQcCellModel().getState());
            
            if(jsm.getSubsurface()!=null){
            model.getJobSummaryModel().setSubsurface(jsm.getSubsurface());
            }
            */
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
            //jsm.setFeModelQcCellModel(model);
            
            
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
        }
    }
    
}
