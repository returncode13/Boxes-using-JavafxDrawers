/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.workspace;

import db.model.Ancestor;
import db.model.Descendant;
import db.model.Dot;
import db.model.Job;
import db.model.Link;
import db.model.NodeType;
import db.model.Volume;
import db.model.Workspace;
import db.services.AncestorService;
import db.services.AncestorServiceImpl;
import db.services.DescendantService;
import db.services.DescendantServiceImpl;
import db.services.DotService;
import db.services.DotServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.JobVolumeMapService;
import db.services.JobVolumeMapServiceImpl;
import db.services.LinkService;
import db.services.LinkServiceImpl;
import db.services.NodeTypeService;
import db.services.NodeTypeServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import db.services.WorkspaceService;
import db.services.WorkspaceServiceImpl;
import fend.dot.DotModel;
import fend.dot.DotView;
import fend.dot.LinkModel;
import fend.edge.edge.EdgeModel;
import fend.edge.edge.EdgeView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import fend.job.job1.JobType1Model;
import fend.job.job1.JobType1View;
import fend.job.definitions.JobDefinitionsModel;
import fend.job.definitions.JobDefinitionsView;
import fend.job.definitions.volume.VolumeListModel;
import fend.job.definitions.volume.VolumeListView;
import fend.job.job0.JobType0Model;
import fend.job.job0.JobType0View;
import fend.volume.volume0.Volume0;
import fend.workspace.saveworkspace.SaveWorkSpaceView;
import fend.workspace.saveworkspace.SaveWorkspaceModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jdk.internal.dynalink.linker.LinkerServices;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkspaceController  {
    
    
    
    private WorkspaceModel model;
    private WorkspaceView node;
    
    private WorkspaceService workspaceService=new WorkspaceServiceImpl();
    private JobService jobService=new JobServiceImpl();
    private VolumeService volumeService=new VolumeServiceImpl();
    private JobVolumeMapService jobVolumeMapService=new JobVolumeMapServiceImpl();
    private NodeTypeService nodeTypeService=new NodeTypeServiceImpl();
    private AncestorService ancestorService=new AncestorServiceImpl();
    private DescendantService descendantService=new DescendantServiceImpl();
    private LinkService linkService=new LinkServiceImpl();
    private DotService dotService = new DotServiceImpl();
    
    
    List<BooleanProperty> changePropertyList=new ArrayList<>();
    
    @FXML
    private AnchorPane baseWindow;              //depth =0 
    
    
    @FXML
    private SplitPane splitpane;                  //depth =1 

    
    @FXML   
    private ScrollPane scrollpane;              //depth =2 
    @FXML
    private AnchorPane interactivePane;         //depth =3 
    
    @FXML
    private Button add;

    @FXML
    void addBox(ActionEvent event) {
        JobType1Model job=new JobType1Model(this.model);
        BooleanProperty changeProperty=new SimpleBooleanProperty(false);
        changeProperty.bind(job.getChangeProperty());
        changeProperty.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
               
               
                    if(model.DEBUG)System.out.println("Saving session");
                    saveWorkspace();
                
            }

          
            
        });
        
        changePropertyList.add(changeProperty);
        JobType1View jobview=new JobType1View(job,interactivePane);
        interactivePane.getChildren().add(jobview);
//        System.out.println("workspace.WorkspaceController.addBox(): "+job.getId()%100);
        
        
        
    }
    
    
    
    
    void setModel(WorkspaceModel item) {
        splitpane.prefWidthProperty().bind(baseWindow.widthProperty());
        splitpane.prefHeightProperty().bind(baseWindow.heightProperty());
        scrollpane.prefWidthProperty().bind(splitpane.widthProperty());
        scrollpane.prefHeightProperty().bind(splitpane.heightProperty());
        interactivePane.prefWidthProperty().bind(scrollpane.widthProperty());
        interactivePane.prefHeightProperty().bind(scrollpane.heightProperty());
        interactivePane.getChildren().addListener(jobLinkChangeListener);
        model=item;
        
        
      }

    void setView(WorkspaceView vq) {
        /*node=aThis;
        baseWindow.getChildren().add(node);*/
        node=vq;
    }
    
     
     /**
      * Listeners:
      **/
     
     
      ListChangeListener<Node> jobLinkChangeListener=new ListChangeListener<Node>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Node> c) {
            while(c.next()){
                for(Node node:c.getAddedSubList()){
                    if(node instanceof JobType0View){
                        System.out.println(".onChanged(): new job added to workspace: ");
                        model.getObservableJobs().add(((JobType0View) node).getController().getModel());
                    }
                    
                    if(node instanceof EdgeView){
                        model.getObservableEdges().add(((EdgeView) node).getController().getModel());
                        System.out.println(".onChanged() new edge was added to the workspace "+((EdgeView) node).getController().getModel().getId()%1000+" size of set: "+model.getObservableEdges().size());
                        
                    }
                    
                    if(node instanceof DotView){
                        System.out.println(".onChanged() new Dot was added to the workspace");
                    }
                }
            }
        }
    };
    
    
    
    /**
     * private Implementation
     */
     private void saveWorkspace() {
         String name=model.getName().get();
         
        
        final BooleanProperty readyToSave=new SimpleBooleanProperty(false);
         if(model.getName().get().isEmpty()){
             SaveWorkspaceModel sm=new SaveWorkspaceModel();
             Platform.runLater(new Runnable() {
                 @Override
                 public void run() {
                     SaveWorkSpaceView sv=new SaveWorkSpaceView(sm);
                 }
             });
             
             sm.getName().addListener((obs,old,newname)->{
                 System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): nameEntered: "+newname);
                 if(newname.length()>0)
                 model.setName(newname);
                 
                 readyToSave.set(true);
             });
             
             
         }else{
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace() continue to save session: "+model.getName().get());
             readyToSave.set(true);
         }
         
         
         if(readyToSave.get()){
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace() Name: "+name);
             Long currentWorkspaceId=model.getId();
             Workspace dbWorkspace=null;
             
             if(workspaceService.getWorkspace(currentWorkspaceId)==null){           //if there is no such entry for session in the database
                 System.out.println("fend.workspace.WorkspaceController.saveWorkspace() creating a new workspace entry");
                 dbWorkspace=new Workspace();
                 dbWorkspace.setId(currentWorkspaceId);
                 dbWorkspace.setName(model.getName().get());
             }else{
                 dbWorkspace= workspaceService.getWorkspace(currentWorkspaceId);       //refer to the existing workspace in the db
             }
                     
           
             
            // dbWorkspace.setName(model.getName().get());
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): saving the session.."+dbWorkspace.getName()+" id: "+dbWorkspace.getId());
            // workspaceService.createWorkspace(dbWorkspace);
             
             Set<JobType0Model> jobsInWorkSpace=model.getObservableJobs();
             Set<Job> dbjobs=new HashSet<>();
             Set<Volume> dbVolumes=new HashSet<>();
             Set<Dot> dbDots=new HashSet<>();
             Set<Link> dbLinks=new HashSet<>();
             
             
             for(JobType0Model fejob:jobsInWorkSpace){
                 Job dbjob;
                 Set<Volume> dbVolumesForCurrentJob=new HashSet<>();
                 Long currentJobId=fejob.getId();
                 if(jobService.getJob(currentJobId)==null){                                                                   //if it's a new node. (not saved), then create a new Job in the database and set it up.
                    dbjob=new Job();
                    dbjob.setId(currentJobId);
                    Long typeOfJob=fejob.getType();
                     //nodetype is set up during install.
                    NodeType nodetype=nodeTypeService.getNodeTypeObjForType(typeOfJob);                   
                    dbjob.setNodetype(nodetype);
                    dbjob.setWorkspace(dbWorkspace);
                    dbjob.setNameJobStep(fejob.getNameproperty().get());
                    
                 }else{
                     dbjob=jobService.getJob(currentJobId);                                               // else get the instance of the previously saved job
                 }
                 
                    
                
                 
                 
                 List<Volume0> fevolsinFejob=fejob.getVolumes();
                 for(Volume0 vol:fevolsinFejob){
                     Volume dbVol;
                     Long currentVolumeId=vol.getId();
                     if(volumeService.getVolume(currentVolumeId)==null){
                        dbVol=new Volume();
                        dbVol.setId(currentVolumeId);
                        dbVol.setVolumeType(vol.getType());
                        dbVol.setNameVolume(vol.getName().get());
                        dbVol.setPathOfVolume(vol.getVolume().getAbsolutePath());
                        dbVol.setJob(dbjob);
                     }else{
                        dbVol=volumeService.getVolume(vol.getId());
                     }
                     
                     dbVolumesForCurrentJob.add(dbVol);
                     
                 }
                
                 dbVolumes.addAll(dbVolumesForCurrentJob);
                 
                dbjob.setVolumes(dbVolumesForCurrentJob);
                dbjobs.add(dbjob);
                 
             }
             
             dbWorkspace.setJobs(dbjobs);
             
             workspaceService.createWorkspace(dbWorkspace);
             for(Job dbjob:dbjobs) jobService.createJob(dbjob);
             for(Volume dbVol:dbVolumes) volumeService.createVolume(dbVol);
             
             
             
             
             /**
              * Setting up Links
              */
             Set<EdgeModel> edges=model.getObservableEdges();
             Set<DotModel> dots=new HashSet<>();
             
             for(EdgeModel edge:edges){
                 DotModel dot=edge.getDotModel();
                 dots.add(dot);
                 Long currrentDotId=dot.getId();
                 Dot dbDot;
                 if(dotService.getDot(currrentDotId)==null){
                     dbDot=new Dot();
                     dbDot.setId(currrentDotId);
                     dbDot.setWorkspace(dbWorkspace);
                 }else{
                     dbDot=dotService.getDot(currrentDotId);
                 }
                 
                 Set<LinkModel> linksSharingThisDot=dot.getSetOfLinks();
                 Set<Link> dbLinksForDbDot=new HashSet<>();
                 
                 for(LinkModel lm:linksSharingThisDot){
                     Link dbLink=new Link();
                     Job child=jobService.getJob(lm.getChild().getId());
                     Job parent=jobService.getJob(lm.getParent().getId());
                      linkService.clearLinksforJob(child);                 //rebuild each save
                      linkService.clearLinksforJob(parent);                //rebuild each save     
                     dbLink.setParent(parent);
                     dbLink.setChild(child);
                     dbLink.setDot(dbDot);
                     dbLinksForDbDot.add(dbLink);
                     dbLinks.add(dbLink);
                 }
                                
                    
                 dbDot.setLinks(dbLinksForDbDot);
                 dbDots.add(dbDot);
                 
                 
                 
             }
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Number of edges: "+edges.size());
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Number of dots: "+dots.size());
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): dbLinks.size(): "+dbLinks.size());
             dotService.clearUnattachedDots(dbWorkspace);   //delete dots that have zero length set of links. i.e. unattached dots. for current session
             
             dbWorkspace.setDots(dbDots);
             
             for(Dot d:dbDots) dotService.createDot(d);   //create only attached ones.
             for(Link l:dbLinks) linkService.createLink(l); 
             
             
             
             /**
              * Setting up ancestors and descendants
              */
             
             
             
             
          for(JobType0Model job:jobsInWorkSpace){
//              System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): List of ancestors for job: "+job.getId()%1000);
              Job dbjob=jobService.getJob(job.getId());    //the job for which the ancestors are to be determined.
              
              
              
              System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Clearing the ancestor table for job: "+dbjob.getNameJobStep()+" id: "+dbjob.getId());
              ancestorService.clearTableForJob(dbjob);     //the table is rebuilt each time. so clear all ancestors belonging to this job
              Set<Ancestor> dbAncestors=new HashSet<>();
              Set<JobType0Model> feAncestors=(Set<JobType0Model>) job.getAncestors();
              for(JobType0Model feAncestorJob:feAncestors){
//                  System.out.println(job.getId()%1000+" -A- "+feAncestorJob.getId()%1000);
                  
                  Job anc=jobService.getJob(feAncestorJob.getId());
                  Ancestor ancestor=new Ancestor();
                  ancestor.setJob(dbjob);
                  ancestor.setAncestor(anc);
                  dbAncestors.add(ancestor);
                  
                  
              }
              dbjob.setAncestors(dbAncestors);             //add to the list of ancestors
              
              
              Set<Descendant> dbDescendants=new HashSet<>();
//              System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): List of descendants for job: "+job.getId()%1000);
              Set<JobType0Model> feDescendants=(Set<JobType0Model>) job.getDescendants();
              descendantService.clearTableForJob(dbjob);    //the table is rebuilt each time. so clear all feDescendants belonging to this job
              for(JobType0Model feDescendantJob:feDescendants){
//                  System.out.println(job.getId()%1000+" -D- "+feDescendantJob.getId()%1000);
                  Job des=jobService.getJob(feDescendantJob.getId());
                  Descendant descendant=new Descendant();
                  descendant.setJob(dbjob);
                  descendant.setDescendant(des);
                  dbDescendants.add(descendant);
              }
              dbjob.setDescendants(dbDescendants);          //add to the list of descendants
              
              for(Ancestor a:dbAncestors) ancestorService.addAncestor(a);
              for(Descendant d:dbDescendants) descendantService.addDescendant(d);
           
              
          }
         }
         else{
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Workspace is missing a name. Unsaved!");
         }
         
          
          
      }
      
     
     private void loadSession(){
         
     }
     
     
     
     
     
     
    
}
