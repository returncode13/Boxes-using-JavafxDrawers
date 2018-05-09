/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Time;

import db.model.DoubtType;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.CellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.CellState;
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
    
     @Override
    public void calculateCellState() {
        CellState cellstate=CellState.FAILED;
                
        if(cellHasFailedDependency()){ //1
            
            if(cellHasOverridenFail()){  //1.1
                
                if(cellHasInheritedFail()){  //1.1.1
                    cellstate=CellState.INHERITED_FAIL;
                }else{    //1.1.2
                    cellstate=CellState.OVERRIDE;
                }
                
            }else{ //1.2
                cellstate=CellState.FAILED;
            }
                
        }else{//2
            if(cellHasInheritedFail()){ //2.1
                cellstate=CellState.INHERITED_FAIL;
            }else{  //2.2
                if(cellHasInheritedOverride()){  //2.2.1
                    cellstate=CellState.INHERITED_OVERRIDE;
                }else{  //2.2.2
                    if(cellHasWarning()){  //2.2.2.1
                        cellstate=CellState.WARNING;
                    }else{      //2.2.2.2
                        cellstate=CellState.GOOD;
                    }
                }
            }
        }
        this.cellstate=cellstate;
        
       //return this.cellstate;
        
        
    }
private CellState cellstate;
    
    @Override
    public void setCellState(CellState cellstate) {
        this.cellstate=cellstate;
    }
    
    
    @Override
    public CellState getCellState() {
        return cellstate;
    }

    @Override
    public void setCellHasFailedDependency(boolean b) {
        setFailedTimeDependency(b);
    }

    @Override
    public void setCellHasInheritedFail(boolean b) {
        setInheritedTimeFail(b);
    }

    @Override
    public void setCellHasInheritedOverride(boolean b) {
        setInheritedTimeOverride(b);
    }

    @Override
    public void setCellHasOverridenFail(boolean b) {
        setOverridenTimeFail(b);
    }

    @Override
    public void setCellHasWarning(boolean b) {
        setWarningForTime(b);
    }
}
