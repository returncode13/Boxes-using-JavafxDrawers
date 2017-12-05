/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package job.job1;


import workspace.WorkspaceModel;
import fend.sequences.Subsurface;
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
import job.job0.JobType0Model;
import volume.volume0.Volume0;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobType1Model implements JobType0Model {
    final boolean DEBUG=WorkspaceModel.DEBUG;
    final private Long id=1L;
    
    private StringProperty nameproperty;
    private List<Volume0> volumes;
    private ObservableList<Volume0> observableVolumes;
    private List<Subsurface> subsurfacesInJob;
    private Set<Subsurface> duplicatesInJob;
    private BooleanProperty changeProperty;
    private WorkspaceModel workspaceModel;
    private Set<JobType0Model> parents;
    private Set<JobType0Model> children;
    private ObservableSet<JobType0Model> observableParents;
    private ObservableSet<JobType0Model> observableChildren;
    
    
    
            
    public JobType1Model(WorkspaceModel workspaceModel) {
        nameproperty=new SimpleStringProperty();
        children=new HashSet<>();
        children.add(this);                                 //born as a root. 
        parents=new HashSet<>();
        parents.add(this);                                  //born as a root.
        this.workspaceModel=workspaceModel;
        changeProperty=new SimpleBooleanProperty(false);
        duplicatesInJob=new HashSet<>();
        subsurfacesInJob=new ArrayList<>();
        volumes=new ArrayList<>();
        
        observableVolumes=FXCollections.observableArrayList(volumes);
        observableVolumes.addListener(volumeListChangeListener);
        
        observableParents=FXCollections.observableSet(parents);
        observableParents.addListener(parentsChangeListener);
        
        observableChildren=FXCollections.observableSet(children);
        observableChildren.addListener(childrenChangeListener);
        
        nameproperty.addListener(nameChangeListener);
        
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
        return this.id;
    }
    
    @Override
    public void setVolumes(ObservableList<Volume0> observableVolumes) {
        this.observableVolumes = observableVolumes;
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
        if(parent!=this){
            observableParents.remove(this);
            observableParents.add(parent);
        }
        
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
        if(child!=this){
            observableChildren.remove(this);
            observableChildren.add(child);
        }
    }
    
    @Override
    public void removeChild(JobType0Model child){
        observableChildren.remove(child);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
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
                               
                    List<Subsurface> subsurfacesInVol=vol.getSubsurfaces();
                    if(WorkspaceModel.DEBUG) System.out.println("job.job1.JobType1Model.volumeListChangeListener() : add extract subs from vol "+vol+" and add to job:  size()"+subsurfacesInVol.size());
                    for(Subsurface s:subsurfacesInVol){
                        subsurfacesInJob.add(s);
                        
                      
                    }
        
                }
                
                
                for(Volume0 vol: c.getRemoved()){
                    if(WorkspaceModel.DEBUG) System.out.println("job.job1.JobType1Model.volumeListChangeListener() : remove  subs in vol "+vol+" from job: ");
                    List<Subsurface> subsurfacesInVol=vol.getSubsurfaces();
                    for(Subsurface s:subsurfacesInVol){
                    //    System.out.println("Remove: "+s.getSubsurfaceName()+" "+s.getTimeStamp()+" from job");
                        subsurfacesInJob.remove(s);
                       
                    }
        
                }
            }
            
            updateDuplicatesInJob();
           // toggleChange();
            
           
            if(DEBUG)System.out.println(".onChanged() duplicates");
               for(Subsurface s:duplicatesInJob){
                   System.out.println(".duplicates: vol :"+s.getVolume().getName()+" time: "+s.getTimeStamp()+" name: "+s.getSubsurfaceName());
               }
              
            
        }
 
    };
    
    /**
     * Listen to changes. 
     */
    final private SetChangeListener<JobType0Model> parentsChangeListener=new SetChangeListener<JobType0Model>() {
        @Override
        public void onChanged(SetChangeListener.Change<? extends JobType0Model> change) {
          //  toggleChange();
            
        }

        
    };
    /**
     * Listen to changes
     */
    final private SetChangeListener<JobType0Model> childrenChangeListener=new SetChangeListener<JobType0Model>() {
        @Override
        public void onChanged(SetChangeListener.Change<? extends JobType0Model> change) {
          //  toggleChange();
        }
    };
    
    
    final private ChangeListener<String> nameChangeListener=new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            System.out.println(".changed(): Name from "+oldValue+ " to "+newValue);
            toggleChange();
        }
    };
    
    
    /***
     * Private Implementation
     */
    
     
    private void updateDuplicatesInJob() {
            
            duplicatesInJob.clear();
            Map<Subsurface,String> lookupMap=new HashMap<>();
            for(Subsurface s:subsurfacesInJob){
                String name=s.getSubsurfaceName().get();
                if(lookupMap.containsValue(name)){
                    duplicatesInJob.add(s);                                                 //add the duplicate
                    Map<Subsurface,String> t=new HashMap<>(lookupMap);
                    for (Map.Entry<Subsurface, String> entry : t.entrySet()) {
                        Subsurface key = entry.getKey();
                        String value = entry.getValue();
                        if(value.equals(name)){
                            duplicatesInJob.add(key);                                      //add the existing subsurface
                            
                        }
                        
                    }
                }
                else{
                    lookupMap.put(s,s.getSubsurfaceName().get());
                }
            }
            
            
        }
    
    void toggleChange() {
        changeProperty.set(!changeProperty.get());
    }
    
}
