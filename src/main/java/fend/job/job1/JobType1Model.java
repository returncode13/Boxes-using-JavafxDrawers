/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job1;


import db.model.Job;
import db.model.Subsurface;
import db.services.HeaderService;
import db.services.HeaderServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import fend.job.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModel;
import fend.workspace.WorkspaceModel;
import middleware.sequences.SubsurfaceHeaders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import fend.job.job0.JobType0Model;
import fend.volume.volume0.Volume0;
import fend.volume.volume1.Volume1;
import java.util.logging.LogManager;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import middleware.dugex.HeaderExtractor;
import middleware.dugex.DugLogManager;
import middleware.dugex.HeaderLoader;
import middleware.sequences.SequenceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobType1Model implements JobType0Model {
    final boolean DEBUG=WorkspaceModel.DEBUG;
    final private Long type=1L;
    private Long id;
    private LongProperty depth;
    private StringProperty nameproperty;
    private List<Volume0> volumes;
    private ObservableList<Volume0> observableVolumes;
    private ObservableList<SequenceHeaders> sequenceHeaders;
    
    private List<QcMatrixRowModel> qcmatrix;
    private ObservableList<QcMatrixRowModel> observableQcMatrix;
    
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
    private BooleanProperty listenToDepthChange;
            
    public JobType1Model(WorkspaceModel workspaceModel) {
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
        observableVolumes.addListener(volumeListChangeListener);
        
        
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
        finishedCheckingLogs.addListener(checkLogsListener);
        
        listenToDepthChange=new SimpleBooleanProperty(false);
        
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
        return observableVolumes;
    }
    
    @Override
    public void addVolume(Volume0 vol){
        observableVolumes.add(vol);
    }
    
    @Override
    public void removeVolume(Volume0 vol){
        observableVolumes.remove(vol);
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
       
       observableAncestors.clear();
            for(JobType0Model parent:observableParents){
                observableAncestors.add(parent);
                observableAncestors.addAll(parent.getAncestors());
            }
        
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
        final JobType1Model other = (JobType1Model) obj;
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
    
    final private ListChangeListener<QcMatrixRowModel> qcmatrixChangeListener=new ListChangeListener<QcMatrixRowModel>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends QcMatrixRowModel> c) {
                while(c.next()){
                    for(QcMatrixRowModel q:c.getAddedSubList()){
                        System.out.println("ob.job1.JobType1Model.qcmatrixChangeListener().added() qcmatrixrow to qcmatrix: "+q.getName().get()+" is selected: "+q.getCheckedByUser());
                    }
                    for(QcMatrixRowModel q:c.getRemoved()){
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

    public void extractLogs() {
        System.out.println("fend.job.job1.JobType1Model.extractLogs(): ..Process to check logs and commit");
        new DugLogManager(this);
       finishedCheckingLogs.set(!finishedCheckingLogs.get());
    }
    
     private void extractHeaders() {
         System.out.println("fend.job.job1.JobType1Model.extractHeaders(): starting a new HeaderExtractor");
         new HeaderExtractor(this);
        
     }
            
     
     ChangeListener<Boolean> checkLogsListener=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
          //  if(newValue){
                extractHeaders();
           // }
        }
    };

    void checkMultiples() {
       
    }

   

    public ObservableList<SequenceHeaders> getSequenceHeaders() {
        retrieveHeaders();
        return sequenceHeaders;
    }
    
     void retrieveHeaders() {
        HeaderLoader headerloader=new HeaderLoader(this);
        sequenceHeaders=headerloader.getSequenceHeaders();
        
    }
    

    @Override
    public ObservableList<QcMatrixRowModel> getQcMatrix() {
        return observableQcMatrix;
    }

    @Override
    public void setQcMatrix(List<QcMatrixRowModel> qcMatrixRows) {
        this.observableQcMatrix=FXCollections.observableArrayList(qcMatrixRows);
    }

    @Override
    public void addQcMatrixRow(QcMatrixRowModel qcmrow) {
        observableQcMatrix.add(qcmrow);
    }

    @Override
    public void removeQcMatrixRow(QcMatrixRowModel qcmrow) {
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
     
    
    
    
   
}
