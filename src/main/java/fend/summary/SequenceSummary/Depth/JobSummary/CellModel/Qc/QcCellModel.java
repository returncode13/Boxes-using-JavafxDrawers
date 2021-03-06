/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Qc;

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
public class QcCellModel implements CellModel{
    
    private final BooleanProperty active = new SimpleBooleanProperty();    //if sequence is present in the job, then the active flag is set, unset otherwise
    private final BooleanProperty query = new SimpleBooleanProperty();      //toggling this flag will trigger a query in the db which in turn will set the values for qc,time,trace,insight,inheritance 
    
    /*
    private final BooleanProperty qcProperty = new SimpleBooleanProperty(false);                                  //is there a doubt on this node? qcProperty=TRUE if doubtExistsFor(model.sub,model.job.timedoubtType)
    private final StringProperty stateProperty = new SimpleStringProperty(DoubtStatusModel.GOOD);                  //status = GOOD. (no doubt).
    //status = WARNING (no doubt).
    //status = DOUBT (doubt)
    private final BooleanProperty inheritance = new SimpleBooleanProperty(false);                                   //inheritance=true  => inherited DOUBT  . inheritance=false ==> inherited OVERRIDE
    private final BooleanProperty override = new SimpleBooleanProperty(false);                                      //is this cell overriden?*/
    
    private JobSummaryModel jobSummaryModel;
    private final BooleanProperty showOverride = new SimpleBooleanProperty();                                       //used to show the override dialog in the TimeCellController
    
    
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

    @Override
    public DoubtType getCellDoubtType() {
        return this.cellDoubtType;
    }

    @Override
    public void setCellDoubtType(DoubtType type) {
        cellDoubtType=type;
    }

    
    
    private BooleanProperty failedQcDependency=new SimpleBooleanProperty(false);
    private BooleanProperty inheritedQcFail=new SimpleBooleanProperty(false);
    private BooleanProperty inheritedQcOverride=new SimpleBooleanProperty(false);
    private BooleanProperty overridenQcFail=new SimpleBooleanProperty(false);
    private BooleanProperty warningForQc=new SimpleBooleanProperty(false);

    public Boolean hasFailedQcDependency() {
        return failedQcDependency.get();
    }

    public BooleanProperty failedQcDependency(){
        return failedQcDependency;
    }
    
    public void setFailedQcDependency(Boolean b) {
        this.failedQcDependency.set(b);
    }

    public BooleanProperty inheritedQcFail() {
        return inheritedQcFail;
    }
    
    public Boolean hasInheritedQcFail(){
        return inheritedQcFail.get();
    }
    
    public void setInheritedQcFail(Boolean n) {
        this.inheritedQcFail.set(n);
    }

    public BooleanProperty inheritedQcOverride() {
        return inheritedQcOverride;
    }
    
    public Boolean hasInheritedQcOverride(){
        return inheritedQcOverride.get();
    }
    
    public void setInheritedQcOverride(Boolean i) {
        this.inheritedQcOverride.set(i);
    }

    public BooleanProperty overridenQcFail() {
        return overridenQcFail;
    }
    
    public Boolean hasOverridenQcFail(){
        return overridenQcFail.get();
    }
    
    public void setOverridenQcFail(Boolean o) {
        this.overridenQcFail.set(o);
    }

    public BooleanProperty warningForQc() {
        return warningForQc;
    }

    public Boolean hasWarningForQc(){
        return warningForQc.get();
    }
    
    public void setWarningForQc(Boolean w) {
        this.warningForQc.set(w);
    }
    
    
    
    
    
    @Override
    public boolean cellHasFailedDependency() {
        return hasFailedQcDependency();
    }

    @Override
    public boolean cellHasInheritedFail() {
        return hasInheritedQcFail();
    }

    @Override
    public boolean cellHasInheritedOverride() {
        return hasInheritedQcOverride();
    }

    @Override
    public boolean cellHasOverridenFail() {
        return hasOverridenQcFail();
    }

    @Override
    public boolean cellHasWarning() {
        return hasWarningForQc();
    }

    @Override
    public void setOverride(boolean b) {
        setOverridenQcFail(b);
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
        setFailedQcDependency(b);
    }

    @Override
    public void setCellHasInheritedFail(boolean b) {
        setInheritedQcFail(b);
    }

    @Override
    public void setCellHasInheritedOverride(boolean b) {
        setInheritedQcOverride(b);
    }

    @Override
    public void setCellHasOverridenFail(boolean b) {
        setOverridenQcFail(b);
    }

    @Override
    public void setCellHasWarning(boolean b) {
        setWarningForQc(b);
    }
}
