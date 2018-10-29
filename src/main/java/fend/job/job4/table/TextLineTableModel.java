/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job4.table;

import fend.job.job0.JobType0Model;
import java.util.ArrayList;
import java.util.List;
import middleware.sequences.text.TextSequenceHeader;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class TextLineTableModel {
    List<TextSequenceHeader> textSequenceHeaders=new ArrayList<>();
    JobType0Model job;

    public TextLineTableModel(JobType0Model job) {
        this.job = job;
    }

    public List<TextSequenceHeader> getTextSequenceHeaders() {
        return textSequenceHeaders;
    }

    public void setTextSequenceHeaders(List<TextSequenceHeader> textSequenceHeaders) {
        this.textSequenceHeaders = textSequenceHeaders;
    }

    public JobType0Model getJob() {
        return job;
    }
    
    
    
}
