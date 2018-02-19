/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth;

import db.model.Job;
import fend.summary.SequenceSummary.Depth.JobSummary_new.JobSummaryModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class Depth {
    private Long  depth; //depth of this column
    //private List<JobSummaryModel> jobSummaries=new ArrayList<>();
    private Map<Job,JobSummaryModel> jobSummaryMap=new HashMap<>();
    
    
    
    public Long getDepth() {
        return depth;
    }

    public void setDepth(Long depth) {
        this.depth = depth;
    }
    /*
    public List<JobSummaryModel> getJobSummaries() {
    return jobSummaries;
    }
    
    public void setJobSummaries(List<JobSummaryModel> jobSummaries) {
    this.jobSummaries = jobSummaries;
    }
    */

    public Map<Job, JobSummaryModel> getJobSummaryMap() {
        return jobSummaryMap;
    }

    public void setJobSummaryMap(Map<Job, JobSummaryModel> jobSummaryMap) {
        this.jobSummaryMap=jobSummaryMap;
        
    }
    
    public JobSummaryModel getJobSummaryModel(Job job){
        return jobSummaryMap.get(job);
    }
    
    public void addToJobSummaryMap(JobSummaryModel jobSummaryModel){
        this.jobSummaryMap.put(jobSummaryModel.getJob(), jobSummaryModel);
    }
    
    
}
