/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth;

import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class Depth {
    private Long  depth; //depth of this column
    private List<JobSummaryModel> jobSummaries=new ArrayList<>();

    public Long getDepth() {
        return depth;
    }

    public void setDepth(Long depth) {
        this.depth = depth;
    }

    public List<JobSummaryModel> getJobSummaries() {
        return jobSummaries;
    }

    public void setJobSummaries(List<JobSummaryModel> jobSummaries) {
        this.jobSummaries = jobSummaries;
    }
    
    
    
}
