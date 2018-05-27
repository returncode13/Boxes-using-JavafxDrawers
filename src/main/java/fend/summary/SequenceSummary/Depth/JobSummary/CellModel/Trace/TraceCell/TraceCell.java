/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace.TraceCell;

import db.model.DoubtType;
import db.model.Job;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace.TraceCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace.TraceCellView;

import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel;
import fend.summary.SequenceSummary.SequenceSummary;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
   // String type=DoubtTypeModel.TRACES;
    DoubtType type;
    final ContextMenu contextMenu=new ContextMenu();
    private BooleanProperty setupProperty=new SimpleBooleanProperty(true);

    public TraceCell(int depthId, Job jobkey,DoubtType traceType) {
        
       this.depthId=depthId;
       this.job=jobkey;
       model=new TraceCellModel();
       model.setCellDoubtType(traceType);
       type=traceType;
       //view=new TraceCellView(model);
       setupProperty.addListener((observable, oldValue, newValue) -> {
                //if(newValue){
                    int index=getIndex();
                    TraceCellModel tcm=getTreeTableView().getSelectionModel().getModelItem(index).getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(job).getTraceCellModel();
                  // InsightCellModel tcm=getTreeTableRow().getItem().getDepth(Long.valueOf(this.depthId+"")).getJobSummaryModel(job).getInsightCellModel();
       
                    JobSummaryModel jsm=tcm.getJobSummaryModel();
                    if(model!=tcm){
                        model=tcm;
                        model.setJobSummaryModel(jsm);
                        model.setCellDoubtType(type);
                        view=new TraceCellView(model);

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
        if(empty||t==null){
            setGraphic(null);
        }else{
            /*
            }
            if(!empty){*/
           
            /* int index=getIndex();
            TraceCellModel tcm=getTreeTableView().getSelectionModel().getModelItem(index).getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(job).getTraceCellModel();
            // TraceCellModel tcm=getTreeTableView().getTreeItem(index).getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(job).getTraceCellModel();
            JobSummaryModel jsm=tcm.getJobSummaryModel();
            
            model=tcm;
            model.setJobSummaryModel(jsm);
            model.setCellDoubtType(type);
            view.getController().setModel(model);*/
             
            
             setupProperty.set(!setupProperty.get());
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
            
           /*    final ContextMenu contextMenu=new ContextMenu();
           if(model.cellHasFailedDependency()&& model.getJobSummaryModel().getSubsurface()!=null){     //only enabled for subsurfaces and NOT for sequences.
           final MenuItem overrideMenuItem=new MenuItem("Manage Doubt");
           overrideMenuItem.setOnAction(e->{
           System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace.TraceCell.updateItem(): Fetching doubt information for Subsurface: "+model.getJobSummaryModel().getSubsurface().getSubsurface()+" job: "+model.getJobSummaryModel().getJob().getNameJobStep());
           
           
           
           model.setShowOverride(true);
           
           
           });
           contextMenu.getItems().add(overrideMenuItem);
           }
           setContextMenu(contextMenu);*/
            
            setGraphic(view);
            setStyle("-fx-padding: 0 0 0 0;");
        }
    }
    
}
