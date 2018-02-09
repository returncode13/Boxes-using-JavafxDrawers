/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary;

import fend.summary.SequenceSummary.SequenceSummary;
import fend.workspace.WorkspaceController;
import fend.workspace.WorkspaceModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SummaryModel {
    private WorkspaceController workspaceController;
    private List<SequenceSummary> sequenceSummaries=new ArrayList<>();

    public SummaryModel(WorkspaceController workspaceController) {
        this.workspaceController = workspaceController;
    }

    
    
    
    public List<SequenceSummary> getSequenceSummaries() {
        return sequenceSummaries;
    }

    public void setSequenceSummaries(List<SequenceSummary> sequenceSummaries) {
        this.sequenceSummaries = sequenceSummaries;
    }

    public WorkspaceController getWorkspaceController() {
        return workspaceController;
    }
    
    
}