/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.SubsurfaceJob;
import db.model.Job;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.SubsurfaceJobId;
import db.model.Workspace;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface SubsurfaceJobDAO {
    public void createSubsurfaceJob(SubsurfaceJob subsurfaceJob);
    public void createBulkSubsurfaceJob(List<SubsurfaceJob> subsurfaceJobs);
    public SubsurfaceJob getSubsurfaceJob(SubsurfaceJobId id);
    public void updateSubsurfaceJob(SubsurfaceJob nsj);
    public void deleteSubsurfaceJob(SubsurfaceJobId id);
    public SubsurfaceJob getSubsurfaceJobFor(Job job,Subsurface subsurface);
    public List<SubsurfaceJob> getSubsurfaceJobsForSummary();           //get subsurface_job records where updateTime>summaryTime;

    public void updateTimeWhereJobEquals(Job parent, String updateTime);

    public String getLatestSummaryTime();

    public void updateBulkSubsurfaceJobs(List<SubsurfaceJob> subsurfaceJobsToBeUpdated);

    public List<Subsurface> getSubsurfacesForJob(Job j);

    public List<SubsurfaceJob> getSubsurfaceJobFor(Workspace dbWorkspace);

    public void updateTimeWhere(Job job, Subsurface sub, String updateTime);
    public void updateTimeWhere(Job job, Sequence seq,String updateTime);
          
}
