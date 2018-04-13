/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.Objects;
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
public class Summary implements Serializable {
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
    
    @Column(name="time_fail")
    private Boolean failedTimeDependency=false;
    
    @Column(name="trace_fail")
    private Boolean failedTraceDependency=false;
    
    @Column(name="qc_fail")
    private Boolean failedQcSummary=false;
    
    @Column(name="insight_fail")
    private Boolean failedInsightSummary=false;
    
    @Column(name="time_fail_inherited")
    private Boolean inheritedTimeFail=false;
    
    @Column(name="trace_fail_inherited")
    private Boolean inheritedTraceFail=false;
    
    @Column(name="qc_fail_inherited")
    private Boolean inheritedQcFail=false;
    
    
    @Column(name="time_fail_override")
    private Boolean overridenTimeFail=false;
    
    @Column(name="trace_fail_override")
    private Boolean overridenTraceFail=false;
    
    @Column(name="qc_fail_override")
    private Boolean overridenQcFail=false;
    
    @Column(name="insight_fail_override")
    private Boolean overridenInsightFail=false;
    
    
    @Column(name="time_fail_warning")
    private Boolean warningForTime=false;
    
    @Column(name="trace_fail_warning")
    private Boolean warningForTrace=false;
    
    @Column(name="qc_fail_warning")
    private Boolean warningForQc=false;
    
    @Column(name="insight_fail_warning")
    private Boolean warningForInsight=false;
    
    /*@Column(name="insight_inheritance_summary")
    private Boolean insightInheritanceSummary=false;
    */
    

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

  

    public Boolean hasFailedTimeDependency() {
        return failedTimeDependency;
    }

    public void setFailedTimeDependency(Boolean failedTimeDependency) {
        this.failedTimeDependency = failedTimeDependency;
    }

    public Boolean hasFailedTraceDependency() {
        return failedTraceDependency;
    }

    public void setFailedTraceDependency(Boolean failedTraceDependency) {
        this.failedTraceDependency = failedTraceDependency;
    }

    public Boolean hasFailedQcSummary() {
        return failedQcSummary;
    }

    public void setFailedQcSummary(Boolean failedQcSummary) {
        this.failedQcSummary = failedQcSummary;
    }

    public Boolean hasFailedInsightSummary() {
        return failedInsightSummary;
    }

    public void setFailedInsightSummary(Boolean failedInsightSummary) {
        this.failedInsightSummary = failedInsightSummary;
    }

    public Boolean hasInheritedTimeFail() {
        return inheritedTimeFail;
    }

    public void setInheritedTimeFail(Boolean inheritedTimeFail) {
        this.inheritedTimeFail = inheritedTimeFail;
    }

    public Boolean hasInheritedTraceFail() {
        return inheritedTraceFail;
    }

    public void setInheritedTraceFail(Boolean inheritedTraceFail) {
        this.inheritedTraceFail = inheritedTraceFail;
    }

    public Boolean hasInheritedQcFail() {
        return inheritedQcFail;
    }

    public void setInheritedQcFail(Boolean inheritedQcFail) {
        this.inheritedQcFail = inheritedQcFail;
    }

    public Boolean hasOverridenTimeFail() {
        return overridenTimeFail;
    }

    public void setOverridenTimeFail(Boolean overridenTimeFail) {
        this.overridenTimeFail = overridenTimeFail;
    }

    public Boolean hasOverridenTraceFail() {
        return overridenTraceFail;
    }

    public void setOverridenTraceFail(Boolean overridenTraceFail) {
        this.overridenTraceFail = overridenTraceFail;
    }

    public Boolean hasOverridenQcFail() {
        return overridenQcFail;
    }

    public void setOverridenQcFail(Boolean overridenQcFail) {
        this.overridenQcFail = overridenQcFail;
    }

    public Boolean hasOverridenInsightFail() {
        return overridenInsightFail;
    }

    public void setOverridenInsightFail(Boolean overridenInsightFail) {
        this.overridenInsightFail = overridenInsightFail;
    }

    public Boolean hasWarningForTime() {
        return warningForTime;
    }

    public void setWarningForTime(Boolean warningForTime) {
        this.warningForTime = warningForTime;
    }

    public Boolean hasWarningForTrace() {
        return warningForTrace;
    }

    public void setWarningForTrace(Boolean warningForTrace) {
        this.warningForTrace = warningForTrace;
    }

    public Boolean hasWarningForQc() {
        return warningForQc;
    }

    public void setWarningForQc(Boolean warningForQc) {
        this.warningForQc = warningForQc;
    }

    public Boolean hasWarningForInsight() {
        return warningForInsight;
    }

    public void setWarningForInsight(Boolean warningForInsight) {
        this.warningForInsight = warningForInsight;
    }
    
    
    

    /*public Boolean getInsightInheritanceSummary() {
    return insightInheritanceSummary;
    }
    
    public void setInsightInheritanceSummary(Boolean insightInheritanceSummary) {
    this.insightInheritanceSummary = insightInheritanceSummary;
    }*/
    
    

    public Subsurface getSubsurface() {
        return subsurface;
    }

    public void setSubsurface(Subsurface subsurface) {
        this.subsurface = subsurface;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final Summary other = (Summary) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
