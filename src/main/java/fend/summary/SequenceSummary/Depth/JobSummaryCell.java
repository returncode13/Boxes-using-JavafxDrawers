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
public class JobSummaryCell extends TableCell<SequenceSummary, Boolean>{
    JobSummaryView view;
    JobSummaryModel model;
    int depthId;
    int jobId;
    
    public JobSummaryCell() {
        
    }

    public JobSummaryCell(TableColumn<SequenceSummary, JobSummaryModel> param) {
        //param.get
       // param.getCellData(0)
                
    }

    public JobSummaryCell(int depthId, int jobId) {
        this.depthId=depthId;
        this.jobId=jobId;
        model=new JobSummaryModel();
         
    }

    
                                            
    protected void updateItem(Boolean t,boolean empty){
        super.updateItem(t, empty);
        if(!empty){
            int index=getIndex();
           model=getTableView().getItems().get(index).getDepths().get(depthId).getJobSummaries().get(jobId);
            
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
            view=new JobSummaryView(model);
            setGraphic(view);
        }
    }
    
    
}
