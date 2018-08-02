/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.fullLineTable;

import fend.job.job0.JobType0Model;
import fend.job.job5.JobType5Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import middleware.sequences.SequenceHeaders;
import middleware.sequences.fullHeaders.FullSequenceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class FullHeaderLineTableModel {
    private ObservableList<FullSequenceHeaders> fullSequenceHeaders;
    private JobType5Model job;
    private BooleanProperty reloadTableProperty=new SimpleBooleanProperty(true);
    
    public FullHeaderLineTableModel(JobType5Model job) {
        this.job = job;
        fullSequenceHeaders=this.job.getFullSequenceHeaders();
    }

    public JobType0Model getJob() {
        return job;
    }

    
    
    public ObservableList<FullSequenceHeaders> getFullSequenceHeaders() {
        return fullSequenceHeaders;
    }

    public void setFullSequenceHeaders(ObservableList<FullSequenceHeaders> fullSequenceHeaders) {
        this.fullSequenceHeaders = fullSequenceHeaders;
    }
    
    public BooleanProperty reloadTableProperty(){
        return reloadTableProperty;
    }
    
    public void reloadTable() {
        boolean val=reloadTableProperty.get();
        reloadTableProperty.set(!val);
    }
    
    
}
