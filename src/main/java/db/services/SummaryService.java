/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

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
public interface SummaryService {
 public void createSummary(Summary summary);
 public Summary getSummary(Long id);
 public void deleteSummary(Long id);
 public void updateSummary(Long id,Summary newSummary);

 public Summary getSummaryFor(Sequence sequence,Job job);
  public Summary getSummaryFor(Subsurface subsurface,Job job);
  public List<Long> getDepthsInSummary(Workspace W);
  public List<Summary> getSummariesForJobSeq(Job job,Sequence seq,Workspace W);
  public List<Summary> getSummariesForJobSeq(Job job,Sequence seq);
}
