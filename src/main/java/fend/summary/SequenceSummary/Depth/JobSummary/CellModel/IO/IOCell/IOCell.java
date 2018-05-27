/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.IO.IOCell;

import db.model.DoubtType;
import db.model.Job;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.IO.IOCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.IO.IOCellView;
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
public class IOCell extends TreeTableCell<SequenceSummary, Boolean> {
    IOCellView view;
    IOCellModel  model;
    int depthId;
    Job job;
    //String type=DoubtTypeModel.QC;
    DoubtType type;
    final ContextMenu contextMenu=new ContextMenu();
    private BooleanProperty setupProperty=new SimpleBooleanProperty(true);
    
    
    public IOCell(int depthId, Job jobkey,DoubtType ioType) {
        
       this.depthId=depthId;
       this.job=jobkey;
       model=new IOCellModel();
       model.setCellDoubtType(ioType);
       type=ioType;
       //view=new IOCellView(model);
       setupProperty.addListener((observable, oldValue, newValue) -> {
                //if(newValue){
                    int index=getIndex();
            
                    IOCellModel tcm=getTreeTableView().getTreeItem(index).getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(job).getIoCellModel();
                  // InsightCellModel tcm=getTreeTableRow().getItem().getDepth(Long.valueOf(this.depthId+"")).getJobSummaryModel(job).getInsightCellModel();
       
                    JobSummaryModel jsm=tcm.getJobSummaryModel();
                    if(model!=tcm){
                        model=tcm;
                        model.setJobSummaryModel(jsm);
                        model.setCellDoubtType(type);
                        view=new IOCellView(model);

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
           
            /*int index=getIndex();
            IOCellModel tcm=getTreeTableView().getTreeItem(index).getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(job).getIoCellModel();
            JobSummaryModel jsm=tcm.getJobSummaryModel();
            
            //model=new QcCellModel();
            model=tcm;
            model.setJobSummaryModel(jsm);
            model.setCellDoubtType(type);
            view.getController().setModel(model);
            */
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
            setContextMenu(contextMenu);*/
            
            setGraphic(view);
            setStyle("-fx-padding: 0 0 0 0;");
        }
    }
}
