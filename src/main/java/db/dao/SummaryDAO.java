/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Job;
import db.model.Sequence;
import db.model.Summary;
import db.model.Workspace;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface SummaryDAO {
 public void createSummary(Summary summary);
 public Summary getSummary(Long id);
 public void deleteSummary(Long id);
 public void updateSummary(Long id,Summary newSummary);

 public Summary getSummaryFor(Sequence sequence,Job job);
}
