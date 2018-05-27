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
public class QcCell  extends TreeTableCell<SequenceSummary, Boolean>{
    QcCellView view;
    QcCellModel  model;
    int depthId;
    Job job;
    //String type=DoubtTypeModel.QC;
    DoubtType type;
    final ContextMenu contextMenu=new ContextMenu();
    private BooleanProperty setupProperty=new SimpleBooleanProperty(true);
    
    public QcCell(int depthId, Job jobkey,DoubtType qcType) {
        
       this.depthId=depthId;
       this.job=jobkey;
       model=new QcCellModel();
       model.setCellDoubtType(qcType);
       type=qcType;
       //view=new QcCellView(model);
       setupProperty.addListener((observable, oldValue, newValue) -> {
                //if(newValue){
                    int index=getIndex();
            
                    QcCellModel tcm=getTreeTableView().getTreeItem(index).getValue().getDepth(Long.valueOf(this.depthId+"")).getJobSummaryModel(job).getQcCellModel();
                  // InsightCellModel tcm=getTreeTableRow().getItem().getDepth(Long.valueOf(this.depthId+"")).getJobSummaryModel(job).getInsightCellModel();
       
                    JobSummaryModel jsm=tcm.getJobSummaryModel();
                    if(model!=tcm){
                        model=tcm;
                        model.setJobSummaryModel(jsm);
                        model.setCellDoubtType(type);
                        view=new QcCellView(model);

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
           
            /* int index=getIndex();
            QcCellModel tcm=getTreeTableView().getTreeItem(index).getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(job).getQcCellModel();
            JobSummaryModel jsm=tcm.getJobSummaryModel();
            
            //model=new QcCellModel();
            model=tcm;
            model.setJobSummaryModel(jsm);
            model.setCellDoubtType(type);
            view.getController().setModel(model);*/
            
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
            //jsm.setFeModelQcCellModel(model);
            
            /*
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
            */
            setGraphic(view);
            setStyle("-fx-padding: 0 0 0 0;");
        }
    }
    
}
