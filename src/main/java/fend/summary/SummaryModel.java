/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import db.model.Sequence;
import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryImages;
import fend.summary.SequenceSummary.SequenceSummary;
import fend.workspace.WorkspaceController;
import fend.workspace.WorkspaceModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SummaryModel {
    private WorkspaceController workspaceController;
  // private List<SequenceSummary> sequenceSummaries=new ArrayList<>();
    private Map<Sequence,SequenceSummary> sequenceSummaryMap=new HashMap<>();
    private BooleanProperty refreshTable=new SimpleBooleanProperty(false);
    
    final private JobSummaryImages jobSummaryImages=new JobSummaryImages();
    
    
    public SummaryModel(WorkspaceController workspaceController) {
        this.workspaceController = workspaceController;
    }


    
    
    public Map<Sequence, SequenceSummary> getSequenceSummaryMap() {    
        return sequenceSummaryMap;
    }

    /*  public List<SequenceSummary> getSequenceSummaries() {
    return sequenceSummaries;
    }
    public void setSequenceSummaries(List<SequenceSummary> sequenceSummaries) {
    this.sequenceSummaries = sequenceSummaries;
    }*/
    public void setSequenceSummaryMap(Map<Sequence, SequenceSummary> sequenceSummaryMap) {
        this.sequenceSummaryMap = sequenceSummaryMap;
    }
    
    
    public SequenceSummary getSequenceSummary(Sequence seq){
        return sequenceSummaryMap.get(seq);
    }

    public void addToSequenceSummaryMap(SequenceSummary sequenceSummary){
        this.sequenceSummaryMap.put(sequenceSummary.getSequence(), sequenceSummary);
    }
    
    public WorkspaceController getWorkspaceController() {
        return workspaceController;
    }

    public BooleanProperty refreshTableProperty() {
        return refreshTable;
    }

    public void setRefreshTable(Boolean refresh) {
        this.refreshTable.set(refresh);
    }

    public JobSummaryImages getJobSummaryImages() {
        return jobSummaryImages;
    }
    
    
    
    
}