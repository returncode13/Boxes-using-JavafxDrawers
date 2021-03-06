/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job5;


import db.model.Job;
import db.model.Log;
import db.model.Subsurface;
import fend.job.job0.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;
import fend.workspace.WorkspaceModel;
import middleware.sequences.SubsurfaceHeaders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import fend.job.job0.JobType0Model;
import fend.job.job0.definitions.insight.InsightListParentModel;
import fend.job.job0.definitions.qcmatrix.QcMatrixModel;
import fend.job.job0.definitions.qcmatrix.Qint;
import fend.job.job0.property.JobModelProperty;
import fend.job.job5.properties.JobType5Properties;

import fend.volume.volume0.Volume0;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import middleware.sequences.SequenceHeaders;
import middleware.sequences.fullHeaders.FullSequenceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * SEGY node
 */
public class JobType5Model implements JobType0Model {
    final boolean DEBUG=WorkspaceModel.DEBUG;
    final private Long type=JobType0Model.SEGY;
    private Long id;
    private LongProperty depth;
    private StringProperty nameproperty;
    private List<Volume0> volumes;
    private ObservableList<Volume0> observableVolumes;
    private ObservableList<SequenceHeaders> sequenceHeaders;
    
    private List<QcMatrixRowModelParent> qcmatrix;
    private ObservableList<QcMatrixRowModelParent> observableQcMatrix;
    
    private List<SubsurfaceHeaders> subsurfacesInJob;
    private Set<SubsurfaceHeaders> duplicatesInJob;
    private BooleanProperty changeProperty;
    private WorkspaceModel workspaceModel;
    private Set<JobType0Model> parents;
    private Set<JobType0Model> children;
    private Set<JobType0Model> ancestors;
    private Set<JobType0Model> descendants;
    private Boolean lineageChanged;
    private ObservableSet<JobType0Model> observableParents;
    private ObservableSet<JobType0Model> observableChildren;
    private ObservableSet<JobType0Model> observableAncestors;
    private ObservableSet<JobType0Model> observableDescendants;
    private BooleanProperty finishedCheckingLogs;
    private BooleanProperty headersCommited;
    private BooleanProperty listenToDepthChange=new SimpleBooleanProperty(false);
    private List<JobModelProperty> jobProperties;
    private Map<Subsurface,Log> mapOfLatestLogForSubsurface=new HashMap<>();
    private BooleanProperty updateProperty=new SimpleBooleanProperty(false);
    private BooleanProperty deleteProperty=new SimpleBooleanProperty(false);
    private BooleanProperty qcChangedProperty=new SimpleBooleanProperty(false);
    private BooleanProperty reloadSequenceHeaders=new SimpleBooleanProperty(false);
    private BooleanProperty exitedLineTableProperty=new SimpleBooleanProperty(false);
    private BooleanProperty reloadFullSequenceHeadersProperty=new SimpleBooleanProperty(false);
    
    BooleanProperty blockProperty=new SimpleBooleanProperty(false);
    
    private BooleanProperty exitedQcTableProperty=new SimpleBooleanProperty(false);
    
    private Qint qcMatrixModel;
    private InsightListParentModel insightListModel;

    public InsightListParentModel getInsightListModel() {
        return insightListModel;
    }

    public void setInsightListModel(InsightListParentModel insightListModel) {
        this.insightListModel = insightListModel;
    }
    
    
    private BooleanProperty insightChangedProperty=new SimpleBooleanProperty(false);
    public BooleanProperty insightChangedProperty() {
        return insightChangedProperty;
    }

    public void insightSelectionHasChanged() {
        this.insightChangedProperty.set(!this.insightChangedProperty().get());
    }
    
    
    @Override
    public void setQcMatrixModel(Qint qmm){
        qcMatrixModel=qmm;
    }
    
    
    @Override
    public Qint getQcMatrixModel(){
        return qcMatrixModel;
        
    }
    
     @Override
    public BooleanProperty exitQcTableProperty() {
        return exitedQcTableProperty;
    }

    @Override
    public void exitedQcTable() {
        exitedQcTableProperty.set(!exitedQcTableProperty.get());
    }
    
    public void reloadFullHeaders(){
        reloadFullSequenceHeadersProperty.set(!reloadFullSequenceHeadersProperty.get());
    }
    
    public BooleanProperty reloadFullHeadersProperty(){
        return this.reloadFullSequenceHeadersProperty;
    }

    public BooleanProperty blockProperty() {
        return blockProperty;
    }
    
    
    @Override
    public void block() {
       blockProperty.set(true);
    }
    
    @Override
    public void unblock(){
        blockProperty.set(false);
    }
    
    
    @Override
    public BooleanProperty exitLineTableProperty(){
        return exitedLineTableProperty;
    };
    
    @Override
    public void exitedLineTable(){
        boolean val=exitedLineTableProperty.get();
        exitedLineTableProperty.set(!val);
    };
    
    @Override
    public BooleanProperty reloadSequenceHeadersProperty(){
        return reloadSequenceHeaders;
    };
    
    @Override
    public void reLoadSequenceHeaders(){
        boolean val=reloadSequenceHeaders.get();
        reloadSequenceHeaders.set(!val);
    };
    
    public BooleanProperty qcChangedProperty(){
        return qcChangedProperty;
    }
    
    public void setQcChanged(boolean v){
        qcChangedProperty.set(v);
    }
    
    public void toggleQcChangedProperty(){
        boolean val=qcChangedProperty.get();
        qcChangedProperty.set(!val);
    }
    
   
    @Override
    public BooleanProperty deleteProperty() {
        return deleteProperty;
    }

    @Override
    public void toggleDeleteProperty() {
        boolean val=deleteProperty.get();
        deleteProperty.set(!val);
    }
    
    
    @Override
    public Boolean getUpdate() {
        return updateProperty.get();
    }

    @Override
    public void toggleUpdateProperty() {
        updateProperty.set(!getUpdate());
    }
     
    @Override
    public BooleanProperty updateProperty(){
        return updateProperty;
    }
    
    
    private Job databaseJob; 

    public Job getDatabaseJob() {
        return databaseJob;
    }

    public void setDatabaseJob(Job databaseJob) {
        this.databaseJob = databaseJob;
    }
    
    
    
    public JobType5Model(WorkspaceModel workspaceModel) {
        //id=UUID.randomUUID().getMostSignificantBits();
        id=null;
        depth=new SimpleLongProperty();
        depth.set(0L);                                               // at birth depth=0;
        finishedCheckingLogs=new SimpleBooleanProperty(false);
        headersCommited=new SimpleBooleanProperty(false);
        nameproperty=new SimpleStringProperty();
        children=new HashSet<>();
        //children.add(this);                                 //born as a root. 
        parents=new HashSet<>();
        //parents.add(this);                                  //born as a root.
        
        ancestors=new HashSet<>();
        descendants=new HashSet<>();
        lineageChanged=new Boolean(false);
        
        this.workspaceModel=workspaceModel;
        changeProperty=new SimpleBooleanProperty(false);
        duplicatesInJob=new HashSet<>();
        subsurfacesInJob=new ArrayList<>();
        volumes=new ArrayList<>();
        
        observableVolumes=FXCollections.observableArrayList(volumes);
      //  observableVolumes.addListener(volumeListChangeListener);
        
        
        qcmatrix =new ArrayList<>();
        
        observableQcMatrix=FXCollections.observableArrayList(qcmatrix);
        observableQcMatrix.addListener(qcmatrixChangeListener);
        
        
        observableParents=FXCollections.observableSet(parents);
        observableParents.addListener(parentsChangeListener);
        
        observableChildren=FXCollections.observableSet(children);
        observableChildren.addListener(childrenChangeListener);
        
        observableAncestors=FXCollections.observableSet(ancestors);
        observableDescendants=FXCollections.observableSet(descendants);
        
       
                
        
        nameproperty.addListener(nameChangeListener);
        //finishedCheckingLogs.addListener(checkLogsListener);
        
        listenToDepthChange=new SimpleBooleanProperty(false);
        
        jobProperties=new ArrayList<>();
        
        JobType5Properties namesOfProperties=new JobType5Properties();
        List<String> names=namesOfProperties.getProperties();
        for(String name:names){
            JobModelProperty jobModelProperty=new JobModelProperty();
            jobModelProperty.setPropertyName(name);
            this.jobProperties.add(jobModelProperty);
        }
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    
    
    @Override
    public StringProperty getNameproperty() {
        return nameproperty;
    }

    @Override
    public void setNameproperty(String name) {
        this.nameproperty.set(name);
    }

    @Override
    public BooleanProperty getChangeProperty() {
        return changeProperty;
    }

    @Override
    public void setChangeProperty(Boolean change) {
        this.changeProperty.set(change);
    }

    @Override
    public Long getType() {
        return this.type;
    }
    
    @Override
    public void setVolumes(List<Volume0> volumes) {
        this.observableVolumes =FXCollections.observableArrayList(volumes);
    }    
    
    @Override
    public ObservableList<Volume0> getVolumes() {
        System.out.println("fend.job.job5.JobType5Model.getVolumes(): returning "+observableVolumes.size()+" volumes for job: "+this.id);
        return observableVolumes;
    }
    
    @Override
    public void addVolume(Volume0 vol){
        observableVolumes.add(vol);
    }
    
    @Override
    public void removeVolume(Volume0 vol){
        System.out.println("fend.job.job5.JobType5Model.removeVolume(): removing volume "+vol.getId()+"from job: "+this.databaseJob.getId());
        observableVolumes.remove(vol);
        System.out.println("fend.job.job5.JobType5Model.removeVolume(): Number of volumes: "+observableVolumes.size());
    }
   

    @Override
    public ObservableSet<JobType0Model> getParents() {
        return observableParents;
    }

    @Override
    public void setParents(Set<JobType0Model> parents) {
        this.observableParents=FXCollections.observableSet(parents);
    }

    @Override
    public ObservableSet<JobType0Model> getChildren() {
        return observableChildren;
    }

    @Override
    public void setChildren(Set<JobType0Model> children) {
        this.observableChildren=FXCollections.observableSet(children);
    }
    
    @Override
    public void addParent(JobType0Model parent){
      //  if(parent!=this){
        //    observableParents.remove(this);
            observableParents.add(parent);
       // }
        
    }

    public List<SubsurfaceHeaders> getSubsurfacesInJob() {
        return subsurfacesInJob;
    }

    public void setSubsurfacesInJob(List<SubsurfaceHeaders> subsurfacesInJob) {
        this.subsurfacesInJob = subsurfacesInJob;
    }
    
    

    public ObservableSet<JobType0Model> getAncestors() {
        System.out.println("fend.job.job1.JobType1Model.getAncestors(): Inside "+this.getNameproperty().get());
       observableAncestors.clear();
            for(JobType0Model parent:observableParents){
                System.err.println("Adding parent: "+parent.getNameproperty().get());
                observableAncestors.add(parent);
                observableAncestors.addAll(parent.getAncestors());
            }
        System.out.println("fend.job.job1.JobType1Model.getAncestors(): returning from : "+this.getNameproperty().get()); 
        return observableAncestors;
    }

    public ObservableSet<JobType0Model> getDescendants() {
       
        observableDescendants.clear();
            for(JobType0Model child:observableChildren){
                observableDescendants.add(child);
                observableDescendants.addAll(child.getDescendants());
                
                
            }
            return observableDescendants;
    }

    public Boolean getLineageChanged() {
        return lineageChanged;
    }

   
    
    
    
    
    /**
     *
     * @param parent
     */
    @Override
    public void removeParent(JobType0Model parent){
        observableParents.remove(parent);
    }
    
    @Override
    public void addChild(JobType0Model child){
          observableChildren.add(child);
       
    }
    
    @Override
    public void removeChild(JobType0Model child){
        observableChildren.remove(child);
    }

    public BooleanProperty getHeadersCommited() {
        return headersCommited;
    }

    public void setHeadersCommited(Boolean headersCommited) {
        this.headersCommited.set(headersCommited);
    }

    
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final JobType5Model other = (JobType5Model) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    
    /***
     * Listeners
     */
    
      /***
     * Listen to changes. 
     * figure our duplicates at the end of each new volume addition
     *  
     */
    
    final private ListChangeListener<Volume0> volumeListChangeListener=new ListChangeListener<Volume0>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Volume0> c) {
            while(c.next()){
                for(Volume0 vol: c.getAddedSubList()){
                               
                    List<SubsurfaceHeaders> subsurfacesInVol=vol.getSubsurfaces();
                    if(WorkspaceModel.DEBUG) System.out.println("job.job1.JobType1Model.volumeListChangeListener() : add extract subs from vol "+vol+" and add to job:  size()"+subsurfacesInVol.size());
                    for(SubsurfaceHeaders s:subsurfacesInVol){
                        subsurfacesInJob.add(s);
                        
                      
                    }
        
                }
                
                
                for(Volume0 vol: c.getRemoved()){
                    if(WorkspaceModel.DEBUG) System.out.println("job.job1.JobType1Model.volumeListChangeListener() : remove  subs in vol "+vol+" from job: ");
                    List<SubsurfaceHeaders> subsurfacesInVol=vol.getSubsurfaces();
                    for(SubsurfaceHeaders s:subsurfacesInVol){
                    //    System.out.println("Remove: "+s.getSubsurfaceName()+" "+s.getTimeStamp()+" from job");
                        subsurfacesInJob.remove(s);
                       
                    }
        
                }
            }
            
            updateDuplicatesInJob();
           // toggleChange();
            
           
            if(DEBUG)System.out.println(".onChanged() duplicates");
               for(SubsurfaceHeaders s:duplicatesInJob){
                   System.out.println(".duplicates: vol :"+s.getVolume().getName()+" time: "+s.getTimeStamp()+" name: "+s.getSubsurfaceName());
               }
              
            
        }
 
    };
    
    final private ListChangeListener<QcMatrixRowModelParent> qcmatrixChangeListener=new ListChangeListener<QcMatrixRowModelParent>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends QcMatrixRowModelParent> c) {
                while(c.next()){
                    for(QcMatrixRowModelParent q:c.getAddedSubList()){
                        System.out.println("ob.job1.JobType1Model.qcmatrixChangeListener().added() qcmatrixrow to qcmatrix: "+q.getName().get()+" is selected: "+q.getCheckedByUser());
                    }
                    for(QcMatrixRowModelParent q:c.getRemoved()){
                        System.out.println("ob.job1.JobType1Model.qcmatrixChangeListener().removed() qcmatrixrow to qcmatrix: "+q.getName().get()+" is selected: "+q.getCheckedByUser());
                    }
                }
        }
    };
    
    /**
     * Listen to changes. 
     */
    final private SetChangeListener<JobType0Model> parentsChangeListener=new SetChangeListener<JobType0Model>() {
        @Override
        public void onChanged(SetChangeListener.Change<? extends JobType0Model> change) {
           
            //  toggleChange();*/
            
            /*
            //             System.out.println(".onChanged(): Ancestors for job: "+id%1000);
            for(JobType0Model anc:getAncestors()){
            //                System.out.println(anc.getId()%1000+" -A- "+id%1000);
            }
            
            //     System.out.println(".onChanged(): Descendants for job: "+id%1000);
            for(JobType0Model des:getDescendants()){
            //                System.out.println(des.getId()%1000+" -D- "+id%1000);
            }*/
            
        }

        

        
    };
    /**
     * Listen to changes
     */
    final private SetChangeListener<JobType0Model> childrenChangeListener=new SetChangeListener<JobType0Model>() {
        @Override
        public void onChanged(SetChangeListener.Change<? extends JobType0Model> change) {
           
            //  toggleChange();*/
          //  System.out.println(".onChanged(): Ancestors for job: "+id%1000);
          /* for(JobType0Model anc:getAncestors()){
          //                System.out.println(anc.getId()%1000+" -A- "+id%1000);
          }
          
          //System.out.println(".onChanged(): Descendants for job: "+id%1000);
          for(JobType0Model des:getDescendants()){
          //                System.out.println(des.getId()%1000+" -D- "+id%1000);
          }*/
        }

    };
    
    
    final private ChangeListener<String> nameChangeListener=new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            System.out.println("fend.job.job1.JobType1Model.namechangeListener.changed(): Name from "+oldValue+ " to "+newValue);
            //toggleChange();
            
        }
    };
    
    
    
    
    
    /***
     * Private Implementation
     */
    
     
    private void updateDuplicatesInJob() {
            
            duplicatesInJob.clear();
            Map<SubsurfaceHeaders,String> lookupMap=new HashMap<>();
            for(SubsurfaceHeaders s:subsurfacesInJob){
                String name=s.getSubsurfaceName();
                if(lookupMap.containsValue(name)){
                    duplicatesInJob.add(s);                                                 //add the duplicate
                    Map<SubsurfaceHeaders,String> t=new HashMap<>(lookupMap);
                    for (Map.Entry<SubsurfaceHeaders, String> entry : t.entrySet()) {
                        SubsurfaceHeaders key = entry.getKey();
                        String value = entry.getValue();
                        if(value.equals(name)){
                            duplicatesInJob.add(key);                                      //add the existing subsurface
                            
                        }
                        
                    }
                }
                else{
                    lookupMap.put(s,s.getSubsurfaceName());
                }
            }
            
            
        }
    
    void toggleChange() {
       
        changeProperty.set(!changeProperty.get());
    }
    
    private void buildAncestors() {
            observableAncestors.clear();
          
    }

    
    
    
    
    private void buildDescendants() {
        observableDescendants.clear();
        
    }

    /*public void extractLogs() {
    System.out.println("fend.job.job1.JobType1Model.extractLogs(): ..Process to check logs and commit");
    new DugLogManager(this);
    finishedCheckingLogs.set(!finishedCheckingLogs.get());
    }*/
    
    /*private void extractHeaders() {
    System.out.println("fend.job.job1.JobType1Model.extractHeaders(): starting a new HeaderExtractor");
    new HeaderExtractor(this);
    
    }*/
            
     
    /*ChangeListener<Boolean> checkLogsListener=new ChangeListener<Boolean>() {
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    //  if(newValue){
    extractHeaders();
    // }
    }
    };*/

    void checkMultiples() {
       
    }

    public void setSequenceHeaders(ObservableList<SequenceHeaders> sequenceHeaders) {
        this.sequenceHeaders = sequenceHeaders;
    }

   
    
    
    
    public ObservableList<SequenceHeaders> getSequenceHeaders() {
        //retrieveHeaders();
        return sequenceHeaders;
    }
    
    /*void retrieveHeaders() {
    HeaderLoader headerloader=new HeaderLoader(this);
    sequenceHeaders=headerloader.getSequenceHeaders();
    
    }*/
    

    @Override
    public ObservableList<QcMatrixRowModelParent> getQcMatrix() {
        return observableQcMatrix;
    }

    @Override
    public void setQcMatrix(List<QcMatrixRowModelParent> qcMatrixRows) {
        this.observableQcMatrix=FXCollections.observableArrayList(qcMatrixRows);
    }

    @Override
    public void addQcMatrixRow(QcMatrixRowModelParent qcmrow) {
        observableQcMatrix.add(qcmrow);
    }

    @Override
    public void removeQcMatrixRow(QcMatrixRowModelParent qcmrow) {
        observableQcMatrix.remove(qcmrow);
    }

    @Override
    public WorkspaceModel getWorkspaceModel() {
        return workspaceModel;
    }

    @Override
    public void setDepth(Long depth) {
        this.depth.set(depth);
    }

    @Override
    public LongProperty getDepth() {
        return this.depth;
    }

    @Override
    public BooleanProperty getListenToDepthChangeProperty() {
        return this.listenToDepthChange;
    }

    public BooleanProperty getListenToDepthChange() {
        return listenToDepthChange;
    }

    public void setListenToDepthChange(Boolean listenToDepthChange) {
        this.listenToDepthChange.set(listenToDepthChange);
    }
    
    @Override
    public void toggleDepthChange() {
        boolean val=listenToDepthChange.get();
        listenToDepthChange.set(!val);
        
    }


    @Override
    public List<JobModelProperty> getJobProperties() {
        return jobProperties;
                
    }

    @Override
    public void setJobProperties(List<JobModelProperty> jobModelProperties) {
        this.jobProperties=jobModelProperties;
    }

    public BooleanProperty finishedCheckingLogs() {
        return finishedCheckingLogs;
    }

    public void setFinishedCheckingLogs(Boolean finished) {
        this.finishedCheckingLogs.set(finished);
    }

    @Override
    public Map<Subsurface, Log> getLatestLogForSubsurfaceMap() {
        return mapOfLatestLogForSubsurface;
    }

    @Override
    public void setLatestLogForSubsurfaceMap(Map<Subsurface, Log> mapOfLatestLogForSubsurface) {
        this.mapOfLatestLogForSubsurface=mapOfLatestLogForSubsurface;
    }

    private BooleanProperty extractFullHeaderProperty=new SimpleBooleanProperty(true);

    public BooleanProperty extractFullHeaderProperty() {
        return extractFullHeaderProperty;
    }
    
    
    void extractFullHeaders() {
        extractFullHeaderProperty.set(!extractFullHeaderProperty.get());
    }

    
    private ObservableList<FullSequenceHeaders> fullSequenceHeaders=FXCollections.observableArrayList();
    void setFullSequenceHeaders(ObservableList<FullSequenceHeaders> fullSequenceHeaders) {
        this.fullSequenceHeaders=fullSequenceHeaders;
    }

    public ObservableList<FullSequenceHeaders> getFullSequenceHeaders() {
        return fullSequenceHeaders;
    }

    
    private BooleanProperty exitFullHeaderLineTableProperty=new SimpleBooleanProperty(true);
    
    public BooleanProperty exitFullHeaderLineTableProperty() {
        return exitFullHeaderLineTableProperty;
    }

    
    public void exitedFullHeaderLineTable(){
        exitFullHeaderLineTableProperty.set(!exitFullHeaderLineTableProperty.get());
    }

    
    
    
    
   
}
