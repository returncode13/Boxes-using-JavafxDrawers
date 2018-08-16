/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.dot;


import db.model.Dot;
import db.model.Job;
import fend.dot.formulaField.FormulaFieldModel;
import fend.dot.variableArgument.VariableArgumentModel;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import fend.job.job0.JobType0Model;
import fend.workspace.WorkspaceModel;
import java.util.Map;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DotModel {
    public static final String NJS="N";
    public static final String JOIN="J";
    public static final String SPLIT="S";
    
    private Long id;
    private Color color;
    private DoubleProperty x=new SimpleDoubleProperty();
    private DoubleProperty y=new SimpleDoubleProperty();
    /**
     * ParentSet and ChildSet are used to trip the state of the dot. (join,split,njs)
     */
    private Set<JobType0Model> parentSet;
    private Set<JobType0Model> childSet;
    private Set<LinkModel> setOfLinks;
    private WorkspaceModel workspaceModel;
    
    private ObservableList<LinkModel> observableLinkList;
    
    
    boolean split=false;       //1 Parent --> m Children
    boolean join=false;        //m Parents --> 1 Child
    boolean njs=false;         //1 Parent --> 1 Child
    boolean enableFurtherLinks=false;  
    
    private StringProperty status;     //display the status of the Dot on it
    private BooleanProperty delete;     //delete this node;
    private BooleanProperty linkWasCreated;
    private StringProperty function=new SimpleStringProperty("");
    private DoubleProperty error=new SimpleDoubleProperty(0.0);
    private DoubleProperty tolerance=new SimpleDoubleProperty(0.0);
    private VariableArgumentModel variableArgumentModel=new VariableArgumentModel();
    private Dot databaseDot;
    private BooleanProperty dotclickedProperty=new SimpleBooleanProperty(false);
    
    
    
    
    public BooleanProperty dotClickedProperty(){
        return dotclickedProperty;
    }
    
    
    public void clickDot(){
        boolean val =dotclickedProperty.get();
        dotclickedProperty.set(!val);
    }
    
    
    public DotModel(WorkspaceModel workspaceM){
//        id=UUID.randomUUID().getMostSignificantBits();
        id=null;
        status=new SimpleStringProperty("");
        delete=new SimpleBooleanProperty(false);
        setOfLinks=new HashSet<>();
        observableLinkList=FXCollections.observableArrayList(setOfLinks);
        linkWasCreated=new SimpleBooleanProperty(false);
        parentSet=new HashSet<>();
        childSet=new HashSet<>();
        linkWasCreated.addListener(statusChangeListener);
        this.workspaceModel=workspaceM;
       
        
    }   

    public Dot getDatabaseDot() {
        return databaseDot;
    }

    public void setDatabaseDot(Dot databaseDot) {
        this.databaseDot = databaseDot;
        
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
    public DoubleProperty getX() {
      return x;
    }

    public void setX(DoubleProperty x) {
       this.x = x;
    }

    public DoubleProperty getY() {
        return y;
    }

    public void setY(DoubleProperty y) {
       this.y = y;
    }

    public StringProperty getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public BooleanProperty getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete.set(delete);
    }

    public Set<LinkModel> getLinks() {
        /* updateLinks();*/
        System.out.println("fend.dot.DotModel.getSetOfLinks(): returning linksetsize "+setOfLinks.size()+" for id: "+id);
        return setOfLinks;
    }

    public void setLinks(Set<LinkModel> setOfLinks) {
        this.setOfLinks = setOfLinks;
    }
    
    
    
    
   

    public boolean isSplit() {
        return split;
    }

    public boolean isJoin() {
        return join;
    }

    public boolean isNjs() {
        return njs;
    }

    public boolean enableFurtherLinks() {
        return enableFurtherLinks;
    }

    public WorkspaceModel getWorkspaceModel() {
        return workspaceModel;
    }

    public void setWorkspaceModel(WorkspaceModel workspaceModel) {
        this.workspaceModel = workspaceModel;
    }
    
    
    
   

   public void createLink(JobType0Model parent, JobType0Model child) {
        LinkModel lm=new LinkModel();
        lm.setChild(child);
        lm.setParent(parent);
        setOfLinks.add(lm);
        parentSet.add(parent);
        childSet.add(child);
        parent.addChild(child);
        child.addParent(parent);
        workspaceModel=parent.getWorkspaceModel();
        System.out.println("fend.dot.DotModel.createLink(): link was created");
        linkWasCreated.set(!linkWasCreated.get());
    }
    
   
   private void updateStatus() {
       
       if((parentSet.size()==1) && (childSet.size()==1)){
            System.out.println("dot.DotModel.updateConnectionStatus():   setting njs=true ParentSize: "+parentSet.size()+" ChildrenSize: "+childSet.size());
            njs=true;
            split=false;
            join=false;
            enableFurtherLinks=true;
            status.set(this.NJS);
            

            }
            else if(parentSet.size()==1 && childSet.size()>1){
            System.out.println("dot.DotModel.updateConnectionStatus():  setting split=true ParentSize: "+parentSet.size()+" ChildrenSize: "+childSet.size());
            njs=false;
            split=true;
            join=false;
            enableFurtherLinks=true;
            status.set(this.SPLIT);

            }
            else if(parentSet.size()>1&& childSet.size()==1){
            System.out.println("dot.DotModel.updateConnectionStatus():  setting join=true ParentSize: "+parentSet.size()+" ChildrenSize: "+childSet.size());
            njs=false;
            split=false;
            join=true;
            enableFurtherLinks=true;
            status.set(this.JOIN);




            }else{
            System.out.println("dot.DotModel.updateConnectionStatus(): Disabling further links ParentSize: "+parentSet.size()+" ChildrenSize: "+childSet.size());
            enableFurtherLinks=false;
            }
   }
   
   
   
 
    
    

    public StringProperty getFunction() {
        return function;
    }

    public void setFunction(String formula) {
        this.function.set(formula);
    }

    public DoubleProperty getError() {
        return error;
    }

    public void setError(Double error) {
        this.error.set(error);
    }

    public DoubleProperty getTolerance() {
        return tolerance;
    }

    public void setTolerance(Double tolerance) {
        this.tolerance.set(tolerance);
    }

   
    
    
     
   final private ChangeListener<Boolean> statusChangeListener=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            System.out.println("DotModel.changed(): link changed Property "+newValue);
            updateStatus();
        }

        
    };

    public BooleanProperty getLinkWasCreated() {
        return linkWasCreated;
    }

    public VariableArgumentModel getVariableArgumentModel() {
        return variableArgumentModel;
    }

    void toggleLinkWasCreated() {
        linkWasCreated.set(!linkWasCreated.get());
    }

    private BooleanProperty exitedFormulaFieldProperty=new SimpleBooleanProperty(false);
    
    public BooleanProperty exitedFormulaFieldProperty(){
        return this.exitedFormulaFieldProperty;
    }
    
    public void exitedFormulaField() {
        this.exitedFormulaFieldProperty.set(!exitedFormulaFieldProperty.get());
        
    }

    
    BooleanProperty warnUser=new SimpleBooleanProperty(false);

    public BooleanProperty warnUserProperty() {
        return warnUser;
    }
    
    
    
    public void removeUserWarning() {
        warnUser.set(false);
    }

    public void warnUser() {
        warnUser.set(true);
    }

   
  
   
}
