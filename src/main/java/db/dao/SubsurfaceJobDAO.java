/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.SubsurfaceJob;
import db.model.Job;
import db.model.Subsurface;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface SubsurfaceJobDAO {
    public void createSubsurfaceJob(SubsurfaceJob subsurfaceJob);
    public SubsurfaceJob getSubsurfaceJob(Long id);
    public void updateSubsurfaceJob(Long id,SubsurfaceJob nsj);
    public void deleteSubsurfaceJob(Long id);
    public SubsurfaceJob getSubsurfaceJobFor(Job job,Subsurface subsurface);
            
}
