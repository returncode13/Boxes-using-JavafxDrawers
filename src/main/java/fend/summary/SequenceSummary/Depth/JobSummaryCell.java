/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth;

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
public class JobSummaryCell extends TableCell<SequenceSummary, JobSummaryModel>{
    JobSummaryView view;
    JobSummaryModel model;

    public JobSummaryCell() {
        
    }

    public JobSummaryCell(TableColumn<SequenceSummary, JobSummaryModel> param) {
        //param.get
                
    }

    
                                            
    protected void updateItem(JobSummaryModel t,boolean empty){
        super.updateItem(t, true);
        if(!empty){
            SequenceSummary item=(SequenceSummary) this.getTableRow().getItem();
            this.model = model;
            model.setSequence(item.getSequence());
            System.out.println("fend.summary.SequenceSummary.Depth.JobSummaryCell.<init>() job "+model.isActive());
            view=new JobSummaryView(model);
            setGraphic(view);
        }
    }
    
    
}
