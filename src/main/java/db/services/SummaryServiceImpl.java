/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.SummaryDAO;
import db.dao.SummaryDAOImpl;
import db.model.Job;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.Summary;
import db.model.Workspace;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SummaryServiceImpl implements SummaryService{
    
    SummaryDAO summaryDao=new SummaryDAOImpl();
    
    @Override
    public void createSummary(Summary summary) {
        summaryDao.createSummary(summary);
    }

    @Override
    public Summary getSummary(Long id) {
        return summaryDao.getSummary(id);
    }

    @Override
    public void deleteSummary(Long id) {
        summaryDao.deleteSummary(id);
                
    }

    @Override
    public void updateSummary(Long id, Summary newSummary) {
        summaryDao.updateSummary(id, newSummary);
    }

    @Override
    public Summary getSummaryFor(Sequence sequence,Job job) {
        return summaryDao.getSummaryFor(sequence,job);
    }

    @Override
    public List<Long> getDepthsInSummary(Workspace W) {
        return summaryDao.getDepthsInSummary(W);
    }

    @Override
    public List<Summary> getSummariesForJobSeq(Job job,Sequence seq,Workspace W) {
        return summaryDao.getSummariesForJobSeq(job, seq, W);
    }

    @Override
    public List<Summary> getSummariesForJobSeq(Job job, Sequence seq) {
        return summaryDao.getSummariesForJobSeq(job, seq);
    }

    @Override
    public Summary getSummaryFor(Subsurface subsurface, Job job) {
        return summaryDao.getSummaryFor(subsurface, job);
    }

    @Override
    public List<Summary> getSummariesForJobSub(Job job, Subsurface sub, Workspace W) {
        return summaryDao.getSummariesForJobSub(job, sub, W);
    }

    @Override
    public List<Summary> getSummariesFor(Workspace W) {
        return summaryDao.getSummariesFor(W);
    }

    @Override
    public void createBulkSummaries(List<Summary> summaries) {
        summaryDao.createBulkSummaries(summaries);
    }

    @Override
    public void updateBulkSummaries(List<Summary> summariesToBeUpdated) {
        summaryDao.udpateBulkSummaries(summariesToBeUpdated);
    }

    @Override
    public void deleteAllSummaries(Workspace dbWorkspace) {
        summaryDao.deleteAllSummaries(dbWorkspace);
    }
    
}
