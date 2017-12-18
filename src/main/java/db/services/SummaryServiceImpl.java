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
import db.model.Summary;

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
    
}
