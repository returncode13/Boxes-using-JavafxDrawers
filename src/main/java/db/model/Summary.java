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
import org.hibernate.annotations.Formula;

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
    
    @Formula("(select j.depth from obpmanager.job j where j.job_id=job_fk) ")
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
    
    @Column(name="io_fail")
    private Boolean failedIoSummary=false;
    
    @Column(name="workflow_fail")
    private Boolean failedWorkflowSummary=false;
    
    @Column(name="time_fail_inherited")
    private Boolean inheritedTimeFail=false;
    
    @Column(name="trace_fail_inherited")
    private Boolean inheritedTraceFail=false;
    
    @Column(name="qc_fail_inherited")
    private Boolean inheritedQcFail=false;
    
    @Column(name="insight_fail_inherited")
    private Boolean inheritedInsightFail=false;
    
    @Column(name="io_fail_inherited")
    private Boolean inheritedIoFail=false;
    
    @Column(name="workflow_fail_inherited")
    private Boolean inheritedWorkflowFail=false;
    
    @Column(name="time_override_inherited")
    private Boolean inheritedTimeOverride=false;
    
    @Column(name="trace_override_inherited")
    private Boolean inheritedTraceOverride=false;
    
    @Column(name="qc_override_inherited")
    private Boolean inheritedQcOverride=false;
    
    @Column(name="insight_override_inherited")
    private Boolean inheritedInsightOverride=false;
    
    @Column(name="io_override_inherited")
    private Boolean inheritedIoOverride=false;
    
    @Column(name="workflow_override_inherited")
    private Boolean inheritedWorkflowOverride=false;
    
    @Column(name="time_fail_override")
    private Boolean overridenTimeFail=false;
    
    @Column(name="trace_fail_override")
    private Boolean overridenTraceFail=false;
    
    @Column(name="qc_fail_override")
    private Boolean overridenQcFail=false;
    
    @Column(name="insight_fail_override")
    private Boolean overridenInsightFail=false;
    
    @Column(name="io_fail_override")
    private Boolean overridenIoFail=false;
    
    @Column(name="workflow_fail_override")
    private Boolean overridenWorkflowFail=false;
    
    @Column(name="time_fail_warning")
    private Boolean warningForTime=false;
    
    @Column(name="trace_fail_warning")
    private Boolean warningForTrace=false;
    
    @Column(name="qc_fail_warning")
    private Boolean warningForQc=false;
    
    @Column(name="insight_fail_warning")
    private Boolean warningForInsight=false;
    
    
    @Column(name="io_fail_warning")
    private Boolean warningForIo=false;
    
    
    @Column(name="workflow_fail_warning")
    private Boolean warningForWorkflow=false;
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
       //this.depth=job.getDepth();
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
        this.failedTimeDependency = this.failedTimeDependency || failedTimeDependency;
    }

    public Boolean hasFailedTraceDependency() {
        return failedTraceDependency;
    }

    public void setFailedTraceDependency(Boolean failedTraceDependency) {
        this.failedTraceDependency = this.failedTraceDependency || failedTraceDependency;
    }

    public Boolean hasFailedQcDependency() {
        return failedQcSummary;
    }

    public void setFailedQcDependency(Boolean failedQcSummary) {
        this.failedQcSummary = this.failedQcSummary || failedQcSummary;
    }

    public Boolean hasFailedInsightDependency() {
        return failedInsightSummary;
    }

    public void setFailedInsightDependency(Boolean failedInsightSummary) {
        this.failedInsightSummary = this.failedInsightSummary || failedInsightSummary;
    }
    
    
    public Boolean hasFailedIoDependency(){
        return failedIoSummary;
    }

    public void setFailedIoDependency(Boolean failedIoSummary){
        this.failedIoSummary=this.failedIoSummary || failedIoSummary;
    }
    
    
    public void setFailedWorkflowDependency(boolean b) {
        this.failedWorkflowSummary=this.failedWorkflowSummary || b;
    }
    
    public boolean hasFailedWorkflowDependency() {
        return failedWorkflowSummary;
    }

    
    public Boolean hasInheritedTimeFail() {
        return inheritedTimeFail;
    }

    public void setInheritedTimeFail(Boolean inheritedTimeFail) {
        this.inheritedTimeFail = this.inheritedTimeFail || inheritedTimeFail;
    }

    public Boolean hasInheritedTraceFail() {
        return inheritedTraceFail;
    }

    public void setInheritedTraceFail(Boolean inheritedTraceFail) {
        this.inheritedTraceFail = this.inheritedTraceFail || inheritedTraceFail;
    }

    public Boolean hasInheritedQcFail() {
        return inheritedQcFail;
    }

    public void setInheritedQcFail(Boolean inheritedQcFail) {
        this.inheritedQcFail = this.inheritedQcFail || inheritedQcFail;
    }
    
     public Boolean hasInheritedInsightFail() {
        return inheritedInsightFail;
    }

    public void setInheritedInsightFail(Boolean inheritedInsightFail) {
        this.inheritedInsightFail = this.inheritedInsightFail || inheritedInsightFail;
    }

    public Boolean hasInheritedIoFail(){
        return inheritedIoFail;
    }
    
    public void setInheritedIoFail(Boolean inheritedIoFail){
        this.inheritedIoFail=this.inheritedIoFail || inheritedIoFail;
    }
    
     public void setInheritedWorkflowFail(boolean b) {
        this.inheritedWorkflowFail=this.inheritedWorkflowFail || b;
    }

    public boolean hasInheritedWorkflowFail() {
        return inheritedWorkflowFail;
    } 
     
    public Boolean hasOverridenTimeFail() {
        return overridenTimeFail;
    }

    public void setOverridenTimeFail(Boolean overridenTimeFail) {
        this.overridenTimeFail = this.overridenTimeFail || overridenTimeFail;
    }

    public Boolean hasOverridenTraceFail() {
        return overridenTraceFail;
    }

    public void setOverridenTraceFail(Boolean overridenTraceFail) {
        this.overridenTraceFail = this.overridenTraceFail || overridenTraceFail;
    }

    public Boolean hasOverridenQcFail() {
        return overridenQcFail;
    }

    public void setOverridenQcFail(Boolean overridenQcFail) {
        this.overridenQcFail = this.overridenQcFail || overridenQcFail;
    }

    public Boolean hasOverridenInsightFail() {
        return overridenInsightFail;
    }

    public void setOverridenInsightFail(Boolean overridenInsightFail) {
        this.overridenInsightFail = this.overridenInsightFail || overridenInsightFail;
    }
    
    public Boolean hasOverridenIoFail(){
        return this.overridenIoFail;
    }
    
    public void setOverridenIoFail(Boolean overridenIoFail){
        this.overridenIoFail=this.overridenIoFail || overridenIoFail;
    }
    
    public void setOverridenWorkflowFail(boolean b) {
        this.overridenWorkflowFail=this.overridenWorkflowFail || b;
    }
    
     public boolean hasOverridenWorkflowFail() {
        return overridenWorkflowFail;
    }
    
    public Boolean hasWarningForTime() {
        return warningForTime;
    }

    public void setWarningForTime(Boolean warningForTime) {
        this.warningForTime = this.warningForTime || warningForTime;
    }

    public Boolean hasWarningForTrace() {
        return warningForTrace;
    }

    public void setWarningForTrace(Boolean warningForTrace) {
        this.warningForTrace = this.warningForTrace || warningForTrace;
    }

    public Boolean hasWarningForQc() {
        return warningForQc;
    }

    public void setWarningForQc(Boolean warningForQc) {
        this.warningForQc = this.warningForQc || warningForQc;
    }

    public Boolean hasWarningForInsight() {
        return warningForInsight;
    }

    public void setWarningForInsight(Boolean warningForInsight) {
        this.warningForInsight = this.warningForInsight || warningForInsight;
    }

    public Boolean hasWarningForIo(){
        return warningForIo;
    }
   
    public void setWarningForIo(Boolean warningForIo){
        this.warningForIo=this.warningForIo || warningForIo;
    }
    
    public void setWarningForWorkflow(boolean b) {
        this.warningForWorkflow=this.warningForWorkflow || b;
    }
    
     public boolean hasWarningForWorkflow() {
        return warningForWorkflow;
    }
    
    
    public Boolean hasInheritedTimeOverride() {
        return inheritedTimeOverride;
    }

    public void setInheritedTimeOverride(Boolean inheritedTimeOverride) {
        this.inheritedTimeOverride = this.inheritedTimeOverride || inheritedTimeOverride;
    }

    public Boolean hasInheritedTraceOverride() {
        return inheritedTraceOverride;
    }

    public void setInheritedTraceOverride(Boolean inheritedTraceOverride) {
        this.inheritedTraceOverride = this.inheritedTraceOverride || inheritedTraceOverride;
    }

    public Boolean hasInheritedQcOverride() {
        return inheritedQcOverride;
    }

    public void setInheritedQcOverride(Boolean inheritedQcOverride) {
        this.inheritedQcOverride = this.inheritedQcOverride || inheritedQcOverride;
    }

    public Boolean hasInheritedInsightOverride() {
        return inheritedInsightOverride;
    }

    public void setInheritedInsightOverride(Boolean inheritedInsightOverride) {
        this.inheritedInsightOverride = this.inheritedInsightOverride || inheritedInsightOverride;
    }
    
    public Boolean hasInheritedIoOverride(){
        return inheritedIoOverride;
    }
    
    public void  setInheritedIoOverride(Boolean inheritedIoOverride){
        this.inheritedIoOverride=this.inheritedIoOverride ||inheritedIoOverride;
    }
    
    
    public void setInheritedWorkflowOverride(boolean b) {
        this.inheritedWorkflowOverride=this.inheritedWorkflowOverride || b;
    }
    
    public boolean hasInheritedWorkflowOverride() {
        return inheritedWorkflowOverride;
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

    public void setAll(boolean b) {
        failedTimeDependency=failedTraceDependency=failedQcSummary=failedInsightSummary=failedIoSummary=failedWorkflowSummary=b;
        inheritedTimeFail=inheritedTraceFail=inheritedQcFail=inheritedInsightFail=inheritedIoFail=inheritedWorkflowFail=b;
        inheritedTimeOverride=inheritedTraceOverride=inheritedQcOverride=inheritedInsightOverride=inheritedIoOverride=inheritedWorkflowOverride=b;
        overridenTimeFail=overridenTraceFail=overridenQcFail=overridenInsightFail=overridenIoFail=overridenWorkflowFail=b;
        warningForTime=warningForTrace=warningForQc=warningForInsight=warningForIo=warningForWorkflow=b;
        
    }

   

   

  
  

    
    

   

   
    

    

 
    
    
    
    
}
