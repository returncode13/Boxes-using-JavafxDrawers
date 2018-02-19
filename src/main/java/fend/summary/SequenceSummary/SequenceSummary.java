/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary;

import db.model.Sequence;
import db.model.Subsurface;
import fend.summary.SequenceSummary.Depth.Depth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SequenceSummary {
    Sequence sequence;        //sequence that this summary row will represent
    Subsurface subsurface;
    //private List<Depth> depths=new ArrayList<>();         //the table column is ordered by depths.
    ///private List<SequenceSummary> children=new ArrayList<>();
    private Map<Long,Depth> depthMap=new HashMap<>();
    private Map<Subsurface,SequenceSummary> children=new HashMap<>();
    
    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Map<Long, Depth> getDepthMap() {
        return depthMap;
    }

    public void setDepthMap(Map<Long, Depth> depthMap) {
        this.depthMap = depthMap;
    }

    public Map<Subsurface, SequenceSummary> getChildren() {
        return children;
    }

    public void setChildren(Map<Subsurface, SequenceSummary> children) {
        this.children = children;
    }

    public Depth getDepth(Long depth){
        return depthMap.get(depth);
    }
    
    public void addToDepth(Depth depth){
        
        this.depthMap.put(depth.getDepth(), depth);
    }
    
    public SequenceSummary getChild(Subsurface sub){
        return children.get(sub);
    }
    
    public void addToChildren(SequenceSummary sequenceSummary){
        this.children.put(sequenceSummary.getSubsurface(), sequenceSummary);
    }
    
    /* public List<Depth> getDepths() {
    return depths;
    }
    
    public void setDepths(List<Depth> depths) {
    this.depths = depths;
    }*/
    public Subsurface getSubsurface() {
        return subsurface;
    }

    public void setSubsurface(Subsurface subsurface) {
        this.subsurface = subsurface;
    }

    /* public List<SequenceSummary> getChildren() {
    return children;
    }
    
    public void setChildren(List<SequenceSummary> children) {
    this.children = children;
    }
    */

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.subsurface);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SequenceSummary other = (SequenceSummary) obj;
        if (!Objects.equals(this.subsurface, other.subsurface)) {
            return false;
        }
        return true;
    }
    
    
    
}
