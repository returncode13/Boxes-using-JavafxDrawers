/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
@Entity
@Table(name="summary",schema = "obpmanager")
public class Summary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "job_fk",nullable=false)
    private Job job;
    
    @ManyToOne
    @JoinColumn(name = "seq_fk",nullable=false)
    private Sequence sequence;
    
    @ManyToOne
    @JoinColumn(name = "subsurface_fk",nullable=false)
    private Subsurface subsurface;
    
    @Column(name="depth")
    private Long depth;
    
    @Column(name="time_summary")
    private Boolean timeSummary=false;
    
    @Column(name="trace_summary")
    private Boolean traceSummary=false;
    
    @Column(name="qc_summary")
    private Boolean qcSummary=false;
    
    @Column(name="insight_summary")
    private Boolean insightSummary=false;
    
    @Column(name="inheritance_summary")
    private Boolean inheritanceSummary=false;

    @ManyToOne
    @JoinColumn(name="workspace",nullable=false)
    private Workspace workspace;
    
    public Summary() {
    }

    
    
    
    
    
    public Long getId() {
        return id;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
        this.depth=job.getDepth();
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Long getDepth() {
        return this.depth;
    }

  

    public Boolean getTimeSummary() {
        return timeSummary;
    }

    public void setTimeSummary(Boolean timeSummary) {
        this.timeSummary = timeSummary;
    }

    public Boolean getTraceSummary() {
        return traceSummary;
    }

    public void setTraceSummary(Boolean traceSummary) {
        this.traceSummary = traceSummary;
    }

    public Boolean getQcSummary() {
        return qcSummary;
    }

    public void setQcSummary(Boolean qcSummary) {
        this.qcSummary = qcSummary;
    }

    public Boolean getInsightSummary() {
        return insightSummary;
    }

    public void setInsightSummary(Boolean insightSummary) {
        this.insightSummary = insightSummary;
    }

    public Boolean getInheritanceSummary() {
        return inheritanceSummary;
    }

    public void setInheritanceSummary(Boolean inheritanceSummary) {
        this.inheritanceSummary = inheritanceSummary;
    }

    public Subsurface getSubsurface() {
        return subsurface;
    }

    public void setSubsurface(Subsurface subsurface) {
        this.subsurface = subsurface;
    }
    
    
    
    
    
}
