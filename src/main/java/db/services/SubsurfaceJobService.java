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
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface SubsurfaceJobService {
     public void createSubsurfaceJob(SubsurfaceJob subsurfaceJob);
    public SubsurfaceJob getSubsurface(SubsurfaceJobId id);
    public void updateSubsurfaceJob(SubsurfaceJob nsj);
    public void deleteSubsurfaceJob(SubsurfaceJobId id);
    public SubsurfaceJob getSubsurfaceJobFor(Job job,Subsurface subsurface);
}
