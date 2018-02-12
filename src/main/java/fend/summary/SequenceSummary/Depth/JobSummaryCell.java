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
            /*if(jsm.getSubsurface()==null){
            jsm=getTreeTableView().getTreeItem(index).getValue().get
            }*/
            model.setSequence(jsm.getSequence());
            model.setJob(jsm.getJob());
            model.setInheritance(jsm.isInheritance());
            model.setTime(jsm.isTime());
            model.setTrace(jsm.isTrace());
            model.setQc(jsm.isQc());
            model.setInsight(jsm.isInsight());
            if(!t){
                model.setActive(false);
               //  System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.updateItem(): index is : "+index+" item is "+getTableView().getItems().get(index).getSequence().getSequenceno());
            
            }
            else{
                model.setActive(true);
               //  System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.updateItem(): index is : "+index+" item is "+getTableView().getItems().get(index).getSequence().getSequenceno());
            
            }
            //this.model=new JobSummaryModel();
             
            /*SequenceSummary item=(SequenceSummary) this.getTableRow().getItem();
            model=item.getDepths().get(depthId).getJobSummaries().get(jobId);
            model.setSequence(item.getSequence());*/
            
            //System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.<init>() seq "+model.getSequence().getSequenceno()+"  job "+model.getJob().getNameJobStep()+" is Active: "+model.isActive());
            
            setGraphic(view);
        }
    }
    
    
}
