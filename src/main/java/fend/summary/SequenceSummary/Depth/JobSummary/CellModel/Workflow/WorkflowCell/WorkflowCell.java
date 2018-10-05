/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Workflow.WorkflowCell;

import db.model.DoubtType;
import db.model.Job;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Workflow.WorkflowCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Workflow.WorkflowCellView;

import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel;
import fend.summary.SequenceSummary.SequenceSummary;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeTableCell;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkflowCell extends TreeTableCell<SequenceSummary, Boolean>{
    WorkflowCellView view;
    WorkflowCellModel  model;
    int depthId;
    Job job;
    DoubtType type;
    final ContextMenu contextMenu=new ContextMenu();
    private BooleanProperty setupProperty=new SimpleBooleanProperty(true);
    
    public WorkflowCell(int depthId, Job jobkey,DoubtType workflowType) {
        
       this.depthId=depthId;
       this.job=jobkey;
       model=new WorkflowCellModel();
       model.setCellDoubtType(workflowType);
       type=workflowType;
       //view=new QcCellView(model);
       setupProperty.addListener((observable, oldValue, newValue) -> {
                //if(newValue){
                    int index=getIndex();
            
                    WorkflowCellModel tcm=getTreeTableView().getTreeItem(index).getValue().getDepth(Long.valueOf(this.depthId+"")).getJobSummaryModel(job).getWorkflowCellModel();
                    JobSummaryModel jsm=tcm.getJobSummaryModel();
                    if(model!=tcm){
                        model=tcm;
                        model.setJobSummaryModel(jsm);
                        model.setCellDoubtType(type);
                        view=new WorkflowCellView(model);

                         if(model.cellHasFailedDependency()&& model.getJobSummaryModel().isChild()){     //only enabled for subsurfaces and NOT for sequences.
                                final MenuItem overrideMenuItem=new MenuItem("Manage Doubt");
                                overrideMenuItem.setOnAction(e->{
                                System.out.println("Fetching doubt information for Subsurface: "+model.getJobSummaryModel().getSubsurface().getSubsurface()+" job: ");



                                model.setShowOverride(true);

                                });
                                contextMenu.getItems().add(overrideMenuItem);
                        }
                        setContextMenu(contextMenu);
                    }

                    
               // }
            });
        
    }
    
    protected void updateItem(Boolean t,boolean empty){
        super.updateItem(t, empty);
        if(!empty){
           
            setupProperty.set(!setupProperty.get());
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
            
            setGraphic(view);
            setStyle("-fx-padding: 0 0 0 0;");
        }
    }
}
