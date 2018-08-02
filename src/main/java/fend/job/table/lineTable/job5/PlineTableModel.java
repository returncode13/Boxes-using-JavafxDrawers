/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.lineTable.job5;

import fend.job.job0.JobType0Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import middleware.sequences.SequenceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class PlineTableModel {
    private ObservableList<SequenceHeaders> sequenceHeaders;
    private JobType0Model job;
    private BooleanProperty reloadTableProperty=new SimpleBooleanProperty(true);
    
    public PlineTableModel(JobType0Model job) {
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
    
    public BooleanProperty reloadTableProperty(){
        return reloadTableProperty;
    }
    
    public void reloadTable() {
        boolean val=reloadTableProperty.get();
        reloadTableProperty.set(!val);
    }
    
    
}
