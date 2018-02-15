/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth;

import db.model.Job;
import fend.summary.SequenceSummary.Depth.Depth;
import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel;
import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryView;
import fend.summary.SequenceSummary.SequenceSummary;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeTableCell;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobSummaryCell extends TreeTableCell<SequenceSummary, Boolean>{
    JobSummaryView view;
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
    
    
    public JobSummaryCell(int depthId, Job job) {
        this.depthId=depthId;
       // this.jobId=jobId;
       this.job=job;
//       this.itemProperty().addListener(ITEM_PROPERTY_CHANGE_LISTENER);
       
        model=new JobSummaryModel();
        
        view=new JobSummaryView(model);
        
         
    }

    
                                            
    protected void updateItem(Boolean t,boolean empty){
        super.updateItem(t, empty);
        if(!empty){
           
              int index=getIndex();
            JobSummaryModel jsm=getTreeTableView().getTreeItem(index).getValue().getDepth(Long.valueOf(depthId+"")).getJobSummaryModel(job);
             //JobSummaryModel jsm= getTreeTableView().getTreeItem(index).getValue().getDepths().get(depthId).getJobSummaries().get(jobId);
           //JobSummaryModel jsm=getTableView().getItems().get(index).getDepths().get(depthId).getJobSummaries().get(jobId);
            System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.updateItem(): Setting sequence to "+jsm.getSequence().getSequenceno());
          //  System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.updateItem(): Setting sequence to "+jsm.getSubsurface().getSubsurface());
            if(jsm.getSubsurface()==null){//this means the selected item is the parent sequence.
            //jsm=getTreeTableView().getTreeItem(index).getValue().get
         //   getTreeTableView().getTreeItem(index).getValue().get;
                System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.updateItem(): Sequence "+jsm.getSequence().getSequenceno()+" clicked. No subsurface info");
            }else{
                System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.updateItem(): Setting subsurface to "+jsm.getSubsurface().getSubsurface());
            }
            model.setSequence(jsm.getSequence());
            model.setJob(jsm.getJob());
            model.setInheritance(jsm.isInheritance());
            model.setTime(jsm.isTime());
            model.setTrace(jsm.isTrace());
            model.setQc(jsm.isQc());
            model.setInsight(jsm.isInsight());
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
            //this.model=new JobSummaryModel();
             final ContextMenu contextMenu=new ContextMenu();
                if(model.isInDoubt() && model.getSubsurface()!=null){     //only enabled for subsurfaces and NOT for sequences. 
                    final MenuItem overrideMenuItem=new MenuItem("Manage Doubt"); 
                    overrideMenuItem.setOnAction(e->{
                        System.out.println("Fetching doubt information for Subsurface: "+model.getSubsurface().getSubsurface()+" job: "
                                + ""+getTreeTableRow().getItem().getDepth(Long.valueOf(depthId)).getJobSummaryModel(job).getJob().getNameJobStep()+" for doubtType: "+model.getContextAskedForDoubtType());
                    });
                    contextMenu.getItems().add(overrideMenuItem);
                }
                setContextMenu(contextMenu);
            /*SequenceSummary item=(SequenceSummary) this.getTableRow().getItem();
            model=item.getDepths().get(depthId).getJobSummaries().get(jobId);
            model.setSequence(item.getSequence());*/
            
            //System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.<init>() seq "+model.getSequence().getSequenceno()+"  job "+model.getJob().getNameJobStep()+" is Active: "+model.isActive());
            
            setGraphic(view);
        }
    }
    
    
    
    
    /*  private ChangeListener<Boolean> ITEM_PROPERTY_CHANGE_LISTENER=new ChangeListener<Boolean>() {
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    if(newValue!=null)
    if(newValue){
    final ContextMenu contextMenu=new ContextMenu();
    if(model.isInDoubt()){
    final MenuItem overrideMenuItem=new MenuItem("Manage Doubt");
    overrideMenuItem.setOnAction(e->{
    System.out.println("Fetching doubt information for Subsurface: "+getTreeTableRow().getItem().getSubsurface().getSubsurface()+" job: "
    + ""+getTreeTableRow().getItem().getDepth(Long.valueOf(depthId)).getJobSummaryModel(job).getJob().getNameJobStep());
    });
    contextMenu.getItems().add(overrideMenuItem);
    }
    setContextMenu(contextMenu);
    }
    }
    };*/
    
    
}
