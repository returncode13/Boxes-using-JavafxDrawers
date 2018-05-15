/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Job;
import db.model.Subsurface;
import db.model.SubsurfaceJob;
import db.model.SubsurfaceJobId;
import db.model.Workspace;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface SubsurfaceJobService {
     public void createSubsurfaceJob(SubsurfaceJob subsurfaceJob);
      public void createBulkSubsurfaceJob(List<SubsurfaceJob> subsurfaceJobs);
    public SubsurfaceJob getSubsurface(SubsurfaceJobId id);
    public void updateSubsurfaceJob(SubsurfaceJob nsj);
    public void deleteSubsurfaceJob(SubsurfaceJobId id);
    public SubsurfaceJob getSubsurfaceJobFor(Job job,Subsurface subsurface);
    public Map<Job,List<Subsurface>> getSubsurfaceJobsForSummary();           //get subsurface_job records where updateTime>summaryTime;

    public void updateTimeWhereJobEquals(Job parent, String updateTime);

    public String getLatestSummaryTime();

    public void updateBulkSubsurfaceJobs(List<SubsurfaceJob> subsurfaceJobsToBeUpdated);

    public List<Subsurface> getSubsurfacesForJob(Job dbjob);

    public List<SubsurfaceJob> getSubsurfaceJobFor(Workspace dbWorkspace);
   
}
