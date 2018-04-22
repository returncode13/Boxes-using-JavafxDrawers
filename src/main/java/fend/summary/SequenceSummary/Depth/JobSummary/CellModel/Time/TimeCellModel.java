/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time;

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
public class TimeCellModel implements CellModel{
    
    private final BooleanProperty active = new SimpleBooleanProperty();    //if sequence is present in the job, then the active flag is set, unset otherwise
    private final BooleanProperty query = new SimpleBooleanProperty();      //toggling this flag will trigger a query in the db which in turn will set the values for qc,time,trace,insight,inheritance 
    
    
    /* private final BooleanProperty timeProperty = new SimpleBooleanProperty(false);                                  //is there a doubt on this node? timeProperty=TRUE if doubtExistsFor(model.sub,model.job.timedoubtType)
    private final StringProperty stateProperty = new SimpleStringProperty(DoubtStatusModel.GOOD);                  //status = GOOD. (no doubt).
    //status = WARNING (no doubt).
    //status = DOUBT (doubt)
    private final BooleanProperty inheritance = new SimpleBooleanProperty(false);                                   //inheritance=true  => inherited DOUBT  . inheritance=false ==> inherited OVERRIDE
    private final BooleanProperty override = new SimpleBooleanProperty(false);                                      //is this cell overriden?*/
    
    private DoubtType cellDoubtType;
    
    private JobSummaryModel jobSummaryModel;
    private final BooleanProperty showOverride = new SimpleBooleanProperty();                                       //used to show the override dialog in the TimeCellController

    public JobSummaryModel getJobSummaryModel() {
        return jobSummaryModel;
    }

    public void setJobSummaryModel(JobSummaryModel jobSummaryModel) {
        this.jobSummaryModel = jobSummaryModel;
    }
    
    
    private BooleanProperty failedTimeDependency=new SimpleBooleanProperty(false);
    private BooleanProperty inheritedTimeFail=new SimpleBooleanProperty(false);
    private BooleanProperty inheritedTimeOverride=new SimpleBooleanProperty(false);
    private BooleanProperty overridenTimeFail=new SimpleBooleanProperty(false);
    private BooleanProperty warningForTime=new SimpleBooleanProperty(false);

    public Boolean hasFailedTimeDependency() {
        return failedTimeDependency.get();
    }

    public BooleanProperty failedTimeDependency(){
        return failedTimeDependency;
    }
    
    public void setFailedTimeDependency(Boolean b) {
        this.failedTimeDependency.set(b);
    }

    public BooleanProperty inheritedTimeFail() {
        return inheritedTimeFail;
    }
    
    public Boolean hasInheritedTimeFail(){
        return inheritedTimeFail.get();
    }
    
    public void setInheritedTimeFail(Boolean n) {
        this.inheritedTimeFail.set(n);
    }

    public BooleanProperty inheritedTimeOverride() {
        return inheritedTimeOverride;
    }
    
    public Boolean hasInheritedTimeOverride(){
        return inheritedTimeOverride.get();
    }
    
    public void setInheritedTimeOverride(Boolean i) {
        this.inheritedTimeOverride.set(i);
    }

    public BooleanProperty overridenTimeFail() {
        return overridenTimeFail;
    }
    
    public Boolean hasOverridenTimeFail(){
        return overridenTimeFail.get();
    }
    
    public void setOverridenTimeFail(Boolean o) {
        this.overridenTimeFail.set(o);
    }

    public BooleanProperty warningForTime() {
        return warningForTime;
    }

    public Boolean hasWarningForTime(){
        return warningForTime.get();
    }
    
    public void setWarningForTime(Boolean w) {
        this.warningForTime.set(w);
    }
    
   
    
    
    
    
    
    /*
    
    public boolean isInheritance() {
    return inheritance.get();
    }
    
    public void setInheritance(boolean value) {
    inheritance.set(value);
    }
    
    public BooleanProperty inheritanceProperty() {
    return inheritance;
    }
    
    
    
    
    
    
    public boolean isOverride() {
    return override.get();
    }
    
    public void setOverride(boolean value) {
    override.set(value);
    }
    
    public BooleanProperty overrideProperty() {
    return override;
    }
    
    
    
    
    
    
    
    public String getState() {
    return stateProperty.get();
    }
    
    public void setState(String value) {
    stateProperty.set(value);
    }
    
    public StringProperty statePropertyProperty() {
    return stateProperty;
    }
    
    */
    
    
    @Override
    public boolean cellHasFailedDependency(){
        return hasFailedTimeDependency();
    }
    
    public boolean cellHasInheritedFail(){
        return hasInheritedTimeFail();
    }
    
    public boolean cellHasInheritedOverride(){
        return hasInheritedTimeOverride();
    }
    
    public boolean cellHasOverridenFail(){
        return hasOverridenTimeFail();
    }
    
    public boolean cellHasWarning(){
        return hasWarningForTime();
    }
    
    /* public boolean cellHasDoubt() {
    return timeProperty.get();
    }
    
    public void setCellProperty(boolean value) {
    timeProperty.set(value);
    }
    
    public BooleanProperty cellProperty() {
    return timeProperty;
    }*/
   
    
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
    
     @Override
    public DoubtType getCellDoubtType() {
        return this.cellDoubtType;
    }

    @Override
    public void setCellDoubtType(DoubtType type) {
        cellDoubtType=type;
    }

    @Override
    public void setOverride(boolean b) {
        setOverridenTimeFail(b);
    }
}
