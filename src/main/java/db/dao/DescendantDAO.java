/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Descendant;
import db.model.Job;
import db.model.Subsurface;
import db.model.Workspace;
import java.util.List;

/**
 *
 * @author sharath nair
 */
public interface DescendantDAO  {
    public void addDescendant(Descendant d);
    public Descendant getDescendant(Long did);
    public void updateDescendant(Long did,Descendant newD);
    public void deleteDescendant(Long did);

    public Descendant getDescendantFor(Job fkid, Long descendant);
    public List<Descendant> getDescendantsFor(Job job);
    public void clearTableForJob(Job dbjob);
    public Descendant getDescendantFor(Job job, Job descendant);

    public List<Descendant> getDescendantsForJobContainingSubsurface(Job job, Subsurface sub);
    public List<Object[]> getDescendantsSubsurfaceJobsForSummary(Workspace W);// Return all descendants that contain the same subsurface as the job

    public void removeAllDescendantEntriesFor(Workspace workspace);
}
