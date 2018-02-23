/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.SubsurfaceJobDAO;
import db.dao.SubsurfaceJobDAOImpl;
import db.model.Job;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.SubsurfaceJob;
import db.model.SubsurfaceJobId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SubsurfaceJobServiceImpl implements SubsurfaceJobService{

    private SubsurfaceJobDAO subsurfaceJobDAO=new SubsurfaceJobDAOImpl();

    @Override
    public void createSubsurfaceJob(SubsurfaceJob subsurfaceJob) {
        subsurfaceJobDAO.createSubsurfaceJob(subsurfaceJob);
    }

    @Override
    public SubsurfaceJob getSubsurface(SubsurfaceJobId id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateSubsurfaceJob(SubsurfaceJob nsj) {
        subsurfaceJobDAO.updateSubsurfaceJob(nsj);
    }

    @Override
    public void deleteSubsurfaceJob(SubsurfaceJobId id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SubsurfaceJob getSubsurfaceJobFor(Job job, Subsurface subsurface) {
        return subsurfaceJobDAO.getSubsurfaceJobFor(job, subsurface);
    }

    @Override
    public Map<Job, List<Subsurface>> getSubsurfaceJobsForSummary() {
        List<SubsurfaceJob> subsurfaceJobsForSummary=subsurfaceJobDAO.getSubsurfaceJobsForSummary();
        Map<Job,List<Subsurface>> result=new HashMap<>();
        
        for(SubsurfaceJob subj:subsurfaceJobsForSummary){
            if(!result.containsKey(subj.getJob())){
                result.put(subj.getJob(), new ArrayList<>());
                result.get(subj.getJob()).add(subj.getSubsurface());
            }else{
                result.get(subj.getJob()).add(subj.getSubsurface());
            }
        }
        return result;
      
    }

    @Override
    public void updateTimeWhereJobEquals(Job parent, String updateTime) {
        subsurfaceJobDAO.updateTimeWhereJobEquals(parent,updateTime);
    }

    @Override
    public String getLatestSummaryTime() {
        return subsurfaceJobDAO.getLatestSummaryTime();
    }
    
   
    
}
