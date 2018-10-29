/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job3.table.acqLineTable;

import fend.job.job0.JobType0Model;
import javafx.collections.ObservableList;
import middleware.sequences.SequenceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AcqLineTableModel {
    private ObservableList<SequenceHeaders> sequenceHeaders;
    private JobType0Model job;

    public AcqLineTableModel(JobType0Model job) {
        this.job = job;
        sequenceHeaders=this.job.getSequenceHeaders();
    }

    public JobType0Model getJob() {
        return job;
    }

    
    
    public ObservableList<SequenceHeaders> getSequenceHeaders() {
        return sequenceHeaders;
    }

    public void setSequenceHeaders(ObservableList<SequenceHeaders> sequenceHeaders) {
        this.sequenceHeaders = sequenceHeaders;
    }
    
    
}
