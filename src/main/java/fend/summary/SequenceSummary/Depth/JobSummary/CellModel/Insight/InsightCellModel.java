/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Insight;

import db.model.DoubtType;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.CellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import middleware.doubt.DoubtStatusModel;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class InsightCellModel implements CellModel {
    
    private final BooleanProperty active = new SimpleBooleanProperty();    //if sequence is present in the job, then the active flag is set, unset otherwise
    private final BooleanProperty query = new SimpleBooleanProperty();      //toggling this flag will trigger a query in the db which in turn will set the values for qc,time,trace,insight,inheritance 
    
   
    
    private JobSummaryModel jobSummaryModel;
    private final BooleanProperty showOverride = new SimpleBooleanProperty();                                       //used to show the override dialog in the InsightCellController
    
    private DoubtType cellDoubtType;

    public JobSummaryModel getJobSummaryModel() {
        return jobSummaryModel;
    }

    public void setJobSummaryModel(JobSummaryModel jobSummaryModel) {
        this.jobSummaryModel = jobSummaryModel;
    }
    

   
    
 
    
    public boolean isActive() {
        return active.get();
    }

    public void setActive(boolean value) {
        active.set(value);
    }

    public BooleanProperty activeProperty() {
        return active;
    }
    
    /**
     * Flag used to query db
     **/
    
    public boolean isQuery() {
        return query.get();
    }

    public void setQuery(boolean value) {
        query.set(value);
    }

    public BooleanProperty queryProperty() {
        return query;
    }
     //Used to show override menu in the JobSummaryController
    public boolean isShowOverride() {
        return showOverride.get();
    }

    public void setShowOverride(boolean value) {
        showOverride.set(value);
    }

    public BooleanProperty showOverrideProperty() {
        return showOverride;
    }
    
    
     private BooleanProperty failedInsightDependency=new SimpleBooleanProperty(false);
    private BooleanProperty inheritedInsightFail=new SimpleBooleanProperty(false);
    private BooleanProperty inheritedInsightOverride=new SimpleBooleanProperty(false);
    private BooleanProperty overridenInsightFail=new SimpleBooleanProperty(false);
    private BooleanProperty warningForInsight=new SimpleBooleanProperty(false);

    public Boolean hasFailedInsightDependency() {
        return failedInsightDependency.get();
    }

    public BooleanProperty failedInsightDependency(){
        return failedInsightDependency;
    }
    
    public void setFailedInsightDependency(Boolean b) {
        this.failedInsightDependency.set(b);
    }

    public BooleanProperty inheritedInsightFail() {
        return inheritedInsightFail;
    }
    
    public Boolean hasInheritedInsightFail(){
        return inheritedInsightFail.get();
    }
    
    public void setInheritedInsightFail(Boolean n) {
        this.inheritedInsightFail.set(n);
    }

    public BooleanProperty inheritedInsightOverride() {
        return inheritedInsightOverride;
    }
    
    public Boolean hasInheritedInsightOverride(){
        return inheritedInsightOverride.get();
    }
    
    public void setInheritedInsightOverride(Boolean i) {
        this.inheritedInsightOverride.set(i);
    }

    public BooleanProperty overridenInsightFail() {
        return overridenInsightFail;
    }
    
    public Boolean hasOverridenInsightFail(){
        return overridenInsightFail.get();
    }
    
    public void setOverridenInsightFail(Boolean o) {
        this.overridenInsightFail.set(o);
    }

    public BooleanProperty warningForInsight() {
        return warningForInsight;
    }

    public Boolean hasWarningForInsight(){
        return warningForInsight.get();
    }
    
    public void setWarningForInsight(Boolean w) {
        this.warningForInsight.set(w);
    }
    
    
    
    
    
    
     @Override
    public DoubtType getCellDoubtType() {
        return this.cellDoubtType;
    }

    @Override
    public void setCellDoubtType(DoubtType type) {
        cellDoubtType=type;
    }

    @Override
    public boolean cellHasFailedDependency() {
        return hasFailedInsightDependency();
    }

    @Override
    public boolean cellHasInheritedFail() {
        return hasInheritedInsightFail();
    }

    @Override
    public boolean cellHasInheritedOverride() {
        return hasInheritedInsightOverride();
    }

    @Override
    public boolean cellHasOverridenFail() {
        return hasOverridenInsightFail();
    }

    @Override
    public boolean cellHasWarning() {
        return hasWarningForInsight();
    }

    
    /**
     * For user override
     */
    @Override
    public void setOverride(boolean b) {
        setOverridenInsightFail(b);
    }
}
