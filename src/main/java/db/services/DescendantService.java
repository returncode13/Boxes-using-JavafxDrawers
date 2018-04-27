/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import java.util.Set;
import db.model.Descendant;
import db.model.Job;
import db.model.Subsurface;
import db.model.Workspace;
import java.util.List;

/**
 *
 * @author sharath nair
 */
public interface DescendantService {
    public void addDescendant(Descendant d);
    public Descendant getDescendant(Long did);
    public void updateDescendant(Long did,Descendant newD);
    public void deleteDescendant(Long did);
    public void clearTableForJob(Job dbjob);
    public List<Descendant> getDescendantsFor(Job job);
    public List<Descendant> getDescendantsForJobContainingSub(Job job,Subsurface sub);
    public Descendant getDescendantFor(Job job, Job descendant);
    public List<Object[]> getDescendantsSubsurfaceJobsForSummary(Workspace W);// Return all descendants that contain the same subsurface as the job

    public void removeAllDescendantEntriesFor(Workspace dbWorkspace);
}
