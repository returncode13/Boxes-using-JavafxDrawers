/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.SequenceSummary.Depth.JobSummary.CellModel;

import db.model.DoubtType;
import fend.summary.SequenceSummary.Depth.JobSummary.JobSummaryModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface CellModel {

    public JobSummaryModel getJobSummaryModel();
   
    public void setJobSummaryModel(JobSummaryModel jobSummaryModel);
    
    
    /**
     * cell has interitance=true is the isCellProperty()=false and there are doubts present for jobSummaryModel.sub,job,cellType
     */
    public boolean isInheritance();
    public void setInheritance(boolean value) ;
    public BooleanProperty inheritanceProperty();
    
    public DoubtType getCellDoubtType();
    public void setCellDoubtType(DoubtType type);
    
    public boolean isOverride();    
    public void setOverride(boolean value); 
    public BooleanProperty overrideProperty();
    
    
    
    
    
    

    public String getState();   
    public void setState(String value);
    public StringProperty statePropertyProperty();
    
    
    
    
    public boolean cellHasDoubt();       //true is doubt is set on the cell. time trace qc insight io
    public void setCellProperty(boolean value);
    public BooleanProperty cellProperty();
   
    
    public boolean isActive();
    public void setActive(boolean value);
    public BooleanProperty activeProperty();
    
    /**
     * Flag used to query db
     **/
    
    public boolean isQuery();
    public void setQuery(boolean value);
    public BooleanProperty queryProperty();
    
    
     //Used to show override menu in the JobSummaryController
    public boolean isShowOverride();
    public void setShowOverride(boolean value);
    public BooleanProperty showOverrideProperty();
}
