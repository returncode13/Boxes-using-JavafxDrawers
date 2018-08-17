/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel.Workflow;

import db.model.DoubtType;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.CellModel;
import fend.summary.SequenceSummary.Depth.JobSummary.CellModel.CellState;
import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkflowCellModel implements CellModel{
    private final BooleanProperty active = new SimpleBooleanProperty();    //if sequence is present in the job, then the active flag is set, unset otherwise
    private final BooleanProperty query = new SimpleBooleanProperty();      //toggling this flag will trigger a query in the db which in turn will set the values for qc,time,trace,insight,inheritance 
    
   
    
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

    
    
    private BooleanProperty failedDependency=new SimpleBooleanProperty(false);
    private BooleanProperty inheritedFail=new SimpleBooleanProperty(false);
    private BooleanProperty inheritedOverride=new SimpleBooleanProperty(false);
    private BooleanProperty overridenFail=new SimpleBooleanProperty(false);
    private BooleanProperty warning=new SimpleBooleanProperty(false);

    public Boolean hasFailedDependency() {
        return failedDependency.get();
    }

    public BooleanProperty failedDependency(){
        return failedDependency;
    }
    
    public void setFailedDependency(Boolean b) {
        this.failedDependency.set(b);
    }

    public BooleanProperty inheritedFail() {
        return inheritedFail;
    }
    
    public Boolean hasInheritedFail(){
        return inheritedFail.get();
    }
    
    public void setInheritedFail(Boolean n) {
        this.inheritedFail.set(n);
    }

    public BooleanProperty inheritedOverride() {
        return inheritedOverride;
    }
    
    public Boolean hasInheritedOverride(){
        return inheritedOverride.get();
    }
    
    public void setInheritedOverride(Boolean i) {
        this.inheritedOverride.set(i);
    }

    public BooleanProperty overridenFail() {
        return overridenFail;
    }
    
    public Boolean hasOverridenFail(){
        return overridenFail.get();
    }
    
    public void setOverridenFail(Boolean o) {
        this.overridenFail.set(o);
    }

    public BooleanProperty warning() {
        return warning;
    }

    public Boolean hasWarning(){
        return warning.get();
    }
    
    public void setWarning(Boolean w) {
        this.warning.set(w);
    }
    
    
    
    
    
    @Override
    public boolean cellHasFailedDependency() {
        return hasFailedDependency();
    }

    @Override
    public boolean cellHasInheritedFail() {
        return hasInheritedFail();
    }

    @Override
    public boolean cellHasInheritedOverride() {
        return hasInheritedOverride();
    }

    @Override
    public boolean cellHasOverridenFail() {
        return hasOverridenFail();
    }

    @Override
    public boolean cellHasWarning() {
        return hasWarning();
    }

    @Override
    public void setOverride(boolean b) {
        setOverridenFail(b);
    }
    
    /**
     * Start @ 0 and split when a new number is encountered to follow the logic on paper.
     **/
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
        setFailedDependency(b);
    }

    @Override
    public void setCellHasInheritedFail(boolean b) {
        setInheritedFail(b);
    }

    @Override
    public void setCellHasInheritedOverride(boolean b) {
        setInheritedOverride(b);
    }

    @Override
    public void setCellHasOverridenFail(boolean b) {
        setOverridenFail(b);
    }

    @Override
    public void setCellHasWarning(boolean b) {
        setWarning(b);
    }
    
}
