/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.IO;

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
public class IOCellModel implements CellModel {
    
    private final BooleanProperty active = new SimpleBooleanProperty();    //if sequence is present in the job, then the active flag is set, unset otherwise
    private final BooleanProperty query = new SimpleBooleanProperty();      //toggling this flag will trigger a query in the db which in turn will set the values for qc,time,trace,insight,inheritance 
    
 
    
    private JobSummaryModel jobSummaryModel;
    private final BooleanProperty showOverride = new SimpleBooleanProperty();                                       //used to show the override dialog in the IoCellController
    
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
    
    
     private BooleanProperty failedIoDependency=new SimpleBooleanProperty(false);
    private BooleanProperty inheritedIoFail=new SimpleBooleanProperty(false);
    private BooleanProperty inheritedIoOverride=new SimpleBooleanProperty(false);
    private BooleanProperty overridenIoFail=new SimpleBooleanProperty(false);
    private BooleanProperty warningForIo=new SimpleBooleanProperty(false);

    public Boolean hasFailedIoDependency() {
        return failedIoDependency.get();
    }

    public BooleanProperty failedIoDependency(){
        return failedIoDependency;
    }
    
    public void setFailedIoDependency(Boolean b) {
        this.failedIoDependency.set(b);
    }

    public BooleanProperty inheritedIoFail() {
        return inheritedIoFail;
    }
    
    public Boolean hasInheritedIoFail(){
        return inheritedIoFail.get();
    }
    
    public void setInheritedIoFail(Boolean n) {
        this.inheritedIoFail.set(n);
    }

    public BooleanProperty inheritedIoOverride() {
        return inheritedIoOverride;
    }
    
    public Boolean hasInheritedIoOverride(){
        return inheritedIoOverride.get();
    }
    
    public void setInheritedIoOverride(Boolean i) {
        this.inheritedIoOverride.set(i);
    }

    public BooleanProperty overridenIoFail() {
        return overridenIoFail;
    }
    
    public Boolean hasOverridenIoFail(){
        return overridenIoFail.get();
    }
    
    public void setOverridenIoFail(Boolean o) {
        this.overridenIoFail.set(o);
    }

    public BooleanProperty warningForIo() {
        return warningForIo;
    }

    public Boolean hasWarningForIo(){
        return warningForIo.get();
    }
    
    public void setWarningForIo(Boolean w) {
        this.warningForIo.set(w);
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
        return hasFailedIoDependency();
    }

    @Override
    public boolean cellHasInheritedFail() {
        return hasInheritedIoFail();
    }

    @Override
    public boolean cellHasInheritedOverride() {
        return hasInheritedIoOverride();
    }

    @Override
    public boolean cellHasOverridenFail() {
        return hasOverridenIoFail();
    }

    @Override
    public boolean cellHasWarning() {
        return hasWarningForIo();
    }

    
    /**
     * For user override
     */
    @Override
    public void setOverride(boolean b) {
        setOverridenIoFail(b);
    }
}
