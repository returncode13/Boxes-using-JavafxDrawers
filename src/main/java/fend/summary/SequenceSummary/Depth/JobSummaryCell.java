/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth;

import db.model.Job;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time.TimeCellView;
import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel;
import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryView;
import fend.summary.SequenceSummary.SequenceSummary;
import fend.summary.SummaryModel;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeTableCell;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobSummaryCell extends TreeTableCell<SequenceSummary, Boolean>{
    SummaryModel summaryModel;
    JobSummaryView view;
    //AnchorPane view=new AnchorPane();
    JobSummaryModel model;
    int depthId;
    //int jobId;
    Job job;
    
    
    public JobSummaryCell() {
        
    }

    public JobSummaryCell(TableColumn<SequenceSummary, JobSummaryModel> param) {
        //param.get
       // param.getCellData(0)
                
    }

    /*   public JobSummaryCell(int depthId, int jobId) {
    this.depthId=depthId;
    this.jobId=jobId;
    model=new JobSummaryModel();
    
    view=new JobSummaryView(model);
    
    
    }*/
    
    
    public JobSummaryCell(int depthId, Job job,SummaryModel summodel) {
        this.summaryModel=summodel;
        this.depthId=depthId;
       // this.jobId=jobId;
       this.job=job;
//       this.itemProperty().addListener(ITEM_PROPERTY_CHANGE_LISTENER);
       
        model=new JobSummaryModel(summaryModel);
        /*TimeCellModel tcm=model.getTimeCellModel();
        TimeCellView tcview=new TimeCellView(tcm);
        */
        view=new JobSummaryView(model);
        //view.getChildren().add(tcview);
        
        
         
    }

    
                                            
    protected void updateItem(Boolean t,boolean empty){
        super.updateItem(t, empty);
        if(!empty){
           
              int index=getIndex();
            JobSummaryModel jsm=getTreeTableView().getTreeItem(index).getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(job);
           
            if(jsm.getSubsurface()==null){//this means the selected item is the parent sequence.
          }else{
                System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.updateItem(): Setting subsurface to "+jsm.getSubsurface().getSubsurface());
            }
            /*model.setSequence(jsm.getSequence());
            model.setJob(jsm.getJob());
            model.setInheritance(jsm.isInheritance());
            model.setTime(jsm.isTime());
            model.setTrace(jsm.isTrace());
            model.setQc(jsm.isQc());
            model.setInsight(jsm.isInsight());*/
            //model.getTimeCellModel().setTimeProperty(jsm.getTimeCellModel().isTimeProperty());
            if(jsm.getSubsurface()!=null){
                model.setSubsurface(jsm.getSubsurface());
            }
            
            if(!t){
                model.setActive(false);
               //  System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.updateItem(): index is : "+index+" item is "+getTableView().getItems().get(index).getSequence().getSequenceno());
            
            }
            else{
                model.setActive(true);
               //  System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.updateItem(): index is : "+index+" item is "+getTableView().getItems().get(index).getSequence().getSequenceno());
            
            }
            /* final ContextMenu contextMenu=new ContextMenu();
            if(model.isInDoubt() && model.getSubsurface()!=null){     //only enabled for subsurfaces and NOT for sequences.
            final MenuItem overrideMenuItem=new MenuItem("Manage Doubt");
            overrideMenuItem.setOnAction(e->{
            System.out.println("Fetching doubt information for Subsurface: "+model.getSubsurface().getSubsurface()+" job: "
            + ""+getTreeTableRow().getItem().getDepth(Long.valueOf(depthId)).getJobSummaryModel(job).getJob().getNameJobStep()+" for doubtType: "+model.getContextAskedForDoubtType());
            
            
            model.setShowOverride(true);
            
            });
            contextMenu.getItems().add(overrideMenuItem);
            }
            setContextMenu(contextMenu);
            */
            
            
            
            
            
            /*SequenceSummary item=(SequenceSummary) this.getTableRow().getItem();
            model=item.getDepths().get(depthId).getJobSummaries().get(jobId);
            model.setSequence(item.getSequence());*/
            
            //System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.<init>() seq "+model.getSequence().getSequenceno()+"  job "+model.getJob().getNameJobStep()+" is Active: "+model.isActive());
            
            setGraphic(view);
        }
    }
    
    

    
}
