/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary;

import db.model.Sequence;
import fend.summary.SequenceSummary.Depth.Depth;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SequenceSummary {
    Sequence sequence;        //sequence that this summary row will represent
    private List<Depth> depths=new ArrayList<>();         //the table column is ordered by depths.

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public List<Depth> getDepths() {
        return depths;
    }

    public void setDepths(List<Depth> depths) {
        this.depths = depths;
    }
    
    
    
    
}
