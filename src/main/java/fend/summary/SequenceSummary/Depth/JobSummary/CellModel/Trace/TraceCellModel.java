/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace;

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
public class TraceCellModel implements CellModel {
    
    private final BooleanProperty active = new SimpleBooleanProperty();    //if sequence is present in the job, then the active flag is set, unset otherwise
    private final BooleanProperty query = new SimpleBooleanProperty();      //toggling this flag will trigger a query in the db which in turn will set the values for qc,time,trace,insight,inheritance 
    
  

    
    private JobSummaryModel jobSummaryModel;
    private final BooleanProperty showOverride = new SimpleBooleanProperty(false);                                       //used to show the override dialog in the TimeCellController
    
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
        System.out.println("fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Trace.TraceCellModel.setShowOverride(): called for "+jobSummaryModel.getSubsurface().getSubsurface());
        showOverride.set(value);
    }

    public BooleanProperty showOverrideProperty() {
        return showOverride;
    }
    
    
     private BooleanProperty failedTraceDependency=new SimpleBooleanProperty(false);
    private BooleanProperty inheritedTraceFail=new SimpleBooleanProperty(false);
    private BooleanProperty inheritedTraceOverride=new SimpleBooleanProperty(false);
    private BooleanProperty overridenTraceFail=new SimpleBooleanProperty(false);
    private BooleanProperty warningForTrace=new SimpleBooleanProperty(false);

    public Boolean hasFailedTraceDependency() {
        return failedTraceDependency.get();
    }

    public BooleanProperty failedTraceDependency(){
        return failedTraceDependency;
    }
    
    public void setFailedTraceDependency(Boolean b) {
        this.failedTraceDependency.set(b);
    }

    public BooleanProperty inheritedTraceFail() {
        return inheritedTraceFail;
    }
    
    public Boolean hasInheritedTraceFail(){
        return inheritedTraceFail.get();
    }
    
    public void setInheritedTraceFail(Boolean n) {
        this.inheritedTraceFail.set(n);
    }

    public BooleanProperty inheritedTraceOverride() {
        return inheritedTraceOverride;
    }
    
    public Boolean hasInheritedTraceOverride(){
        return inheritedTraceOverride.get();
    }
    
    public void setInheritedTraceOverride(Boolean i) {
        this.inheritedTraceOverride.set(i);
    }

    public BooleanProperty overridenTraceFail() {
        return overridenTraceFail;
    }
    
    public Boolean hasOverridenTraceFail(){
        return overridenTraceFail.get();
    }
    
    public void setOverridenTraceFail(Boolean o) {
        this.overridenTraceFail.set(o);
    }

    public BooleanProperty warningForTrace() {
        return warningForTrace;
    }

    public Boolean hasWarningForTrace(){
        return warningForTrace.get();
    }
    
    public void setWarningForTrace(Boolean w) {
        this.warningForTrace.set(w);
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
        return hasFailedTraceDependency();
    }

    @Override
    public boolean cellHasInheritedFail() {
        return hasInheritedTraceFail();
    }

    @Override
    public boolean cellHasInheritedOverride() {
        return hasInheritedTraceOverride();
    }

    @Override
    public boolean cellHasOverridenFail() {
        return hasOverridenTraceFail();
    }

    @Override
    public boolean cellHasWarning() {
        return hasWarningForTrace();
    }

    
    /**
     * For user override
     */
    @Override
    public void setOverride(boolean b) {
        setOverridenTraceFail(b);
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
        
      // return this.cellstate;
        
        
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
        setFailedTraceDependency(b);
    }

    @Override
    public void setCellHasInheritedFail(boolean b) {
        setInheritedTraceFail(b);
    }

    @Override
    public void setCellHasInheritedOverride(boolean b) {
        setInheritedTraceOverride(b);
    }

    @Override
    public void setCellHasOverridenFail(boolean b) {
        setOverridenTraceFail(b);
    }

    @Override
    public void setCellHasWarning(boolean b) {
        setWarningForTrace(b);
    }
}
