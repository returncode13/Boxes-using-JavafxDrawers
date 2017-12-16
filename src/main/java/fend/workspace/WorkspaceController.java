/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.workspace;

import app.properties.AppProperties;
import com.jfoenix.controls.JFXButton;
import db.model.Ancestor;
import db.model.Descendant;
import db.model.Dot;
import db.model.Doubt;
import db.model.DoubtStatus;
import db.model.DoubtType;
import db.model.Header;
import db.model.Job;
import db.model.Link;
import db.model.NodeType;
import db.model.QcMatrixRow;
import db.model.QcTable;
import db.model.Subsurface;
import db.model.VariableArgument;
import db.model.Volume;
import db.model.Workspace;
import db.services.AncestorService;
import db.services.AncestorServiceImpl;
import db.services.DescendantService;
import db.services.DescendantServiceImpl;
import db.services.DotService;
import db.services.DotServiceImpl;
import db.services.DoubtService;
import db.services.DoubtServiceImpl;
import db.services.DoubtStatusService;
import db.services.DoubtStatusServiceImpl;
import db.services.DoubtTypeService;
import db.services.DoubtTypeServiceImpl;
import db.services.HeaderService;
import db.services.HeaderServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.JobVolumeMapService;
import db.services.JobVolumeMapServiceImpl;
import db.services.LinkService;
import db.services.LinkServiceImpl;
import db.services.NodeTypeService;
import db.services.NodeTypeServiceImpl;
import db.services.QcMatrixRowService;
import db.services.QcMatrixRowServiceImpl;
import db.services.QcTableService;
import db.services.QcTableServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import db.services.WorkspaceService;
import db.services.WorkspaceServiceImpl;
import fend.dot.DotModel;
import fend.dot.DotView;
import fend.dot.LinkModel;
import fend.edge.dotjobedge.DotJobEdgeModel;
import fend.edge.dotjobedge.DotJobEdgeView;
import fend.edge.edge.EdgeModel;
import fend.edge.edge.EdgeView;
import fend.edge.parentchildedge.ParentChildEdgeModel;
import fend.edge.parentchildedge.ParentChildEdgeView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import fend.job.job1.JobType1Model;
import fend.job.job1.JobType1View;
import fend.job.definitions.JobDefinitionsModel;
import fend.job.definitions.JobDefinitionsView;
import fend.job.definitions.qcmatrix.QcMatrixModel;
import fend.job.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModel;
import fend.job.definitions.volume.VolumeListModel;
import fend.job.definitions.volume.VolumeListView;
import fend.job.job0.JobType0Model;
import fend.job.job0.JobType0View;
import fend.volume.volume0.Volume0;
import fend.volume.volume1.Volume1;
import fend.workspace.saveworkspace.SaveWorkSpaceView;
import fend.workspace.saveworkspace.SaveWorkspaceModel;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
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
import middleware.doubt.DoubtStatusModel;
import middleware.doubt.DoubtTypeModel;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkspaceController  {
    
    
    
    private WorkspaceModel model;
    private WorkspaceView node;
    private Workspace dbWorkspace;
    
    
    private BooleanProperty loadingProperty=new SimpleBooleanProperty(false);
    
    private WorkspaceService workspaceService=new WorkspaceServiceImpl();
    private JobService jobService=new JobServiceImpl();
    private VolumeService volumeService=new VolumeServiceImpl();
    private JobVolumeMapService jobVolumeMapService=new JobVolumeMapServiceImpl();
    private NodeTypeService nodeTypeService=new NodeTypeServiceImpl();
    private AncestorService ancestorService=new AncestorServiceImpl();
    private DescendantService descendantService=new DescendantServiceImpl();
    private LinkService linkService=new LinkServiceImpl();
    private DotService dotService = new DotServiceImpl();
    private QcMatrixRowService qcMatrixRowService=new QcMatrixRowServiceImpl();
    private HeaderService headerService=new HeaderServiceImpl();
    private SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();
    private DoubtService doubtService=new DoubtServiceImpl();
    private DoubtTypeService doubtTypeService=new DoubtTypeServiceImpl();
    private DoubtStatusService doubtStatusService=new DoubtStatusServiceImpl();
    private QcTableService qcTableService=new QcTableServiceImpl();
    
    private List<BooleanProperty> changePropertyList=new ArrayList<>();
    
    @FXML
    private AnchorPane baseWindow;              //depth =0 
    
    
    @FXML
    private SplitPane splitpane;                //depth =1 

    
    @FXML   
    private ScrollPane scrollpane;              //depth =2 
    @FXML
    private AnchorPane interactivePane;         //depth =3 
    
    @FXML
    private Button add;

   
     @FXML
    private JFXButton summaryButton;
    
    
    @FXML
    void addBox(ActionEvent event) {
        Job dbjob=new Job();
        Long typeOfJob=JobType0Model.PROCESS_2D;
        NodeType nodetype=nodeTypeService.getNodeTypeObjForType(typeOfJob);                   
        dbjob.setNodetype(nodetype);
        dbjob.setWorkspace(dbWorkspace);
        jobService.createJob(dbjob);
        
        JobType1Model job=new JobType1Model(this.model);
        job.setId(dbjob.getId());
        BooleanProperty changeProperty=new SimpleBooleanProperty(false);
        changeProperty.bind(job.getChangeProperty());
        changeProperty.addListener(workspaceChangedListener);
        
        changePropertyList.add(changeProperty);
        JobType1View jobview=new JobType1View(job,interactivePane);
        interactivePane.getChildren().add(jobview);
//        System.out.println("workspace.WorkspaceController.addBox(): "+job.getId()%100);
        
        
        
    }
    
     @FXML
    void getSummary(ActionEvent event) throws Exception {
        
      //  try{
            
       
        Map<String,Double> mapForVariableSetting=new HashMap<>();
        Set<String> variableSet=new HashSet<>();
        Set<Job> argumentSet=new HashSet<>();
        List<Subsurface> subsurfaceList=subsurfaceService.getSubsurfaceList();
        dbWorkspace=workspaceService.getWorkspace(dbWorkspace.getId());
        for(Subsurface s:subsurfaceList){
            System.out.println("fend.workspace.WorkspaceController.getSummary(): Loop for subsurface : "+s.getSubsurface());
            Set<Link> linkNodesContainingSub=linkService.getLinksContainingSubsurface(s, dbWorkspace);                  //all links where both the parent and the child contains sub s
            for(Link l:linkNodesContainingSub){
                
                
                //dependency
                DoubtType doubtTypeTraces=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TRACES);
                System.out.println("fend.workspace.WorkspaceController.getSummary(): inside Link : "+l.getId()+" "+l.getParent().getNameJobStep()+"--->"+l.getChild().getNameJobStep()); 
                
                Dot dot=l.getDot();                     //the dot to which the link belongs
                String function=dot.getFunction();
                Double tolerance=dot.getTolerance();
                Double error=dot.getError();
                Set<VariableArgument> variableArguments=dot.getVariableArguments();         //variable and the arguments belonging to the dot.
                mapForVariableSetting.clear();
                variableSet.clear();
                argumentSet.clear();
                for(VariableArgument va:variableArguments){
                    String var=va.getVariable();
                    Job arg=va.getArgument();
                    Double tracesArg;
                    Header h=headerService.getChosenHeaderFor(arg, s);
                    if(h==null){
                        tracesArg=0.0;
                    }else{
                        tracesArg=Double.valueOf(h.getTraceCount()+"");
                    }
                    
                    mapForVariableSetting.put(var, tracesArg);
                    if(!var.equals("y0")){                      //y0 is the lhs which is fixed, the rhs needs to be evaluated. Do not include the y-term
                        variableSet.add(var);
                        argumentSet.add(arg);
                    }                            
                    
                }
                System.out.println("fend.workspace.WorkspaceController.getSummary(): Sub: "+s.getSubsurface()+" linkParent: "+l.getParent().getNameJobStep()+" linkChild: "+l.getChild().getNameJobStep());
                System.out.println("fend.workspace.WorkspaceController.getSummary(): function: "+function);
                System.out.println("fend.workspace.WorkspaceController.getSummary(): setting var-args");
                for (Map.Entry<String, Double> entry : mapForVariableSetting.entrySet()) {
                    String key = entry.getKey();
                    Double value = entry.getValue();
                    System.out.println(key+" = "+value);
                }
                
                Expression e=new ExpressionBuilder(function)
                            .variables(variableSet)
                            .build()
                            .setVariables(mapForVariableSetting);
                Double result=e.evaluate();
               
                Double y=mapForVariableSetting.get("y0");
                Double evaluated=Math.abs(y-result)/y;
                 System.out.println("fend.workspace.WorkspaceController.getSummary(): result = "+result+" evaluated difference/y = "+evaluated);
                if(evaluated<=tolerance){
                    System.out.println("fend.workspace.WorkspaceController.getSummary(): no doubt");
                }else if(evaluated<=error && evaluated>tolerance){
                    System.out.println("fend.workspace.WorkspaceController.getSummary(): creating doubt");
                    String dotState=dot.getStatus();
                                 
                    Doubt doubt;
                    
                    if(dotState.equals(DotModel.JOIN)){
                        if((doubtService.getDoubtFor(s, l.getChild(), dot, doubtTypeTraces))==null){
                            System.out.println("fend.workspace.WorkspaceController.getSummary(): creating doubt entry for "+s.getSubsurface()+" job: "+l.getChild().getId()+" : "+l.getChild().getNameJobStep());
                            doubt=new Doubt();
                            doubt.setChildJob(l.getChild());
                            doubt.setSubsurface(s);
                            doubt.setDot(dot);
                            doubt.setDoubtType(doubtTypeTraces);
                            //doubt.setUser(user);
                            doubtService.createDoubt(doubt);
                            
                           
                            DoubtStatus doubtStatus=new DoubtStatus();
                            doubtStatus.setComment(DoubtStatusModel.getNewDoubtTraceMessage(function, tolerance, error, evaluated, y, doubtTypeTraces.getName()));
                            doubtStatus.setDoubt(doubt);
                            doubtStatus.setStatus(DoubtStatusModel.YES);
                            doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                            //doubtStatus.setUser(user);
                            doubtStatusService.createDoubtStatus(doubtStatus);
                            doubt.addToDoubtStatuses(doubtStatus);
                            doubtService.updateDoubt(doubt.getId(), doubt);
                            
                        }else{
                            doubt=doubtService.getDoubtFor(s, l.getChild(), dot, doubtTypeTraces);
                            Set<DoubtStatus> doubtStatuses=doubt.getDoubtStatuses();
                            for(DoubtStatus ds:doubtStatuses){
                                System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses assosciated with Doubt: "+doubt.getId()+" "+ds.getStatus()+" comment: "+ds.getComment()+" time: "+ds.getTimeStamp());
                            }
                        }
                    }else{
                        for(Job child:argumentSet){                     //In states SPLIT and NJS , the argument set comprises of only children. since P=f(C1,C2,..Cn);
                            if((doubtService.getDoubtFor(s, child, dot, doubtTypeTraces))==null){
                                System.out.println("fend.workspace.WorkspaceController.getSummary(): creating doubt entry for "+s.getSubsurface()+" job: "+child.getId()+" : "+child.getNameJobStep());
                            doubt=new Doubt();
                            doubt.setChildJob(child);
                            doubt.setSubsurface(s);
                            doubt.setDot(dot);
                            doubt.setDoubtType(doubtTypeTraces);
                            //doubt.setUser(user);
                            doubtService.createDoubt(doubt);
                            
                            DoubtStatus doubtStatus=new DoubtStatus();
                            doubtStatus.setComment(DoubtStatusModel.getNewDoubtTraceMessage(function, tolerance, error, evaluated, y, doubtTypeTraces.getName()));
                            doubtStatus.setDoubt(doubt);
                            doubtStatus.setStatus(DoubtStatusModel.YES);
                            doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                            //doubtStatus.setUser(user);
                            doubtStatusService.createDoubtStatus(doubtStatus);
                            doubt.addToDoubtStatuses(doubtStatus);
                            doubtService.updateDoubt(doubt.getId(), doubt);
                        }else{
                                doubt=doubtService.getDoubtFor(s, child, dot, doubtTypeTraces);
                                Set<DoubtStatus> doubtStatuses=doubt.getDoubtStatuses();
                            for(DoubtStatus ds:doubtStatuses){
                                System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: "+doubt.getId()+" "+ds.getStatus()+" comment: "+ds.getComment()+" time: "+ds.getTimeStamp());
                            }
                                
                            }
                        }
                    }
                             
                    
                }
                
                //dependency end    <--put inside a function. 
                
                
                //qc start  <--put inside a function. 
                DoubtType doubtTypeQc=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.QC);
                Job lparent=l.getParent();
                Job jchild =l.getChild();
                List<QcMatrixRow> parentQcMatrix=qcMatrixRowService.getQcMatrixForJob(lparent, true);
                Boolean passQc=true;
                for(QcMatrixRow qcmr:parentQcMatrix){
                    QcTable qctableentries=qcTableService.getQcTableFor(qcmr,s);
                    Boolean qcresult=qctableentries.getResult();
                    if(qcresult==null){
                        qcresult=false;
                    }
                    passQc=passQc && qcresult;
                    
                }
                if(!passQc){  //if parent has failed a single qc. Indeterminate or unchecked. create a doubt in the child
                    Doubt doubt;
                    if((doubt=doubtService.getDoubtFor(s, jchild, dot, doubtTypeQc))==null){
                         doubt=new Doubt();
                        doubt.setChildJob(jchild);
                        doubt.setDot(dot);
                        doubt.setSubsurface(s);
                        doubt.setDoubtType(doubtTypeQc);
                        //doubt.setUser(user)
                        doubtService.createDoubt(doubt);
                         DoubtStatus doubtStatus=new DoubtStatus();
                            doubtStatus.setComment(DoubtStatusModel.getNewDoubtQCcessage(lparent.getNameJobStep(), jchild.getNameJobStep(),s.getSubsurface(),doubtTypeQc.getName()));
                            doubtStatus.setDoubt(doubt);
                            doubtStatus.setStatus(DoubtStatusModel.YES);
                            doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                            //doubtStatus.setUser(user);
                            doubtStatusService.createDoubtStatus(doubtStatus);
                            doubt.addToDoubtStatuses(doubtStatus);
                            doubtService.updateDoubt(doubt.getId(), doubt);
                        
                    }else{
                       Set<DoubtStatus> doubtStatuses=doubt.getDoubtStatuses();
                       for(DoubtStatus d:doubtStatuses){
                         System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: "+doubt.getId()+" "+d.getStatus()+" comment: "+d.getComment()+" time: "+d.getTimeStamp());
                            }
                    }
                }
                
                //qc end <--put inside a function
                
                
                
                //time start
                DoubtType doubtTypeTime=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TIME);
                Job hparent=l.getParent();
                Job hchild=l.getChild();
                Header hp=headerService.getChosenHeaderFor(hparent, s);
                Header hc=headerService.getChosenHeaderFor(hchild, s);
                Long hpt=Long.valueOf(hp.getTimeStamp());
                Long hct=Long.valueOf(hc.getTimeStamp());
                if(hpt >= hct){             //if parent is created after the child. creat a doubt in the child
                    Doubt doubt;
                   if((doubt=doubtService.getDoubtFor(s, hchild, dot, doubtTypeTime))==null){
                       doubt=new Doubt();
                       doubt.setChildJob(hchild);
                       doubt.setDot(dot);
                       doubt.setSubsurface(s);
                       doubt.setDoubtType(doubtTypeTime);
                       //doubt.setUser(user);
                       doubtService.createDoubt(doubt);
                       
                            DoubtStatus doubtStatus=new DoubtStatus();
                            doubtStatus.setComment(DoubtStatusModel.getNewDoubtTimeMessage(hparent.getNameJobStep(),new String(hpt+""), hchild.getNameJobStep(), new String(hct+""), s.getSubsurface(), doubtTypeTime.getName()));
                            doubtStatus.setDoubt(doubt);
                            doubtStatus.setStatus(DoubtStatusModel.YES);
                            doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                            //doubtStatus.setUser(user);
                            doubtStatusService.createDoubtStatus(doubtStatus);
                            doubt.addToDoubtStatuses(doubtStatus);
                            doubtService.updateDoubt(doubt.getId(), doubt);
                       
                   }else{
                        Set<DoubtStatus> doubtStatuses=doubt.getDoubtStatuses();
                       for(DoubtStatus d:doubtStatuses){
                         System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: "+doubt.getId()+" "+d.getStatus()+" comment: "+d.getComment()+" time: "+d.getTimeStamp());
                            }
                    }
                       
                   }
                else{
                    //do nothing
                }
                //time end <--put inside a function
                
                
                
                
                //insight
                
                //insight-end <--put inside a function
                
            }   
        }
        
        
        /*
        }catch(Exception e){
        System.out.println("fend.workspace.WorkspaceController.getSummary(): Exception "+e.getLocalizedMessage());
        }*/

    }
     
    
    
    
    
    void setModel(WorkspaceModel item) {
        splitpane.prefWidthProperty().bind(baseWindow.widthProperty());
        splitpane.prefHeightProperty().bind(baseWindow.heightProperty());
        scrollpane.prefWidthProperty().bind(splitpane.widthProperty());
        scrollpane.prefHeightProperty().bind(splitpane.heightProperty());
        interactivePane.prefWidthProperty().bind(scrollpane.widthProperty());
        interactivePane.prefHeightProperty().bind(scrollpane.heightProperty());
        interactivePane.getChildren().addListener(jobLinkChangeListener);
        loadingProperty.addListener(loadingListener);
        model=item;
        dbWorkspace=workspaceService.getWorkspace(model.getId());
        File insightLocation=new File(AppProperties.INSIGHT_LOCATION);
        File[] insights=insightLocation.listFiles(insightFilter);
        List<String> insightVersionStrings=new ArrayList<>();
        for(File insight:insights){
            System.out.println("fend.workspace.WorkspaceController.setModel(): insightVersions Found: "+insight.getName());
            insightVersionStrings.add(insight.getName());
        }
        model.setInsightVersions(insightVersionStrings);
        
      }

    void setView(WorkspaceView vq) {
        /*node=aThis;
        baseWindow.getChildren().add(node);*/
        node=vq;
    }
    
    
    
   
    
    
    
    /**
     * private Implementation
     */
     private void saveWorkspace() {
         String name=model.getName().get();
         
        
        final BooleanProperty readyToSave=new SimpleBooleanProperty(false);
         if(model.getName().get().isEmpty()){
            
             
             
         }else{
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace() continue to save session: "+model.getName().get());
             readyToSave.set(true);
         }
         
         
         if(readyToSave.get()){
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace() Name: "+name);
             Long currentWorkspaceId=model.getId();
             Workspace dbWorkspace=null;
             
             if(currentWorkspaceId==null){           //if there is no such entry for session in the database
                 System.out.println("fend.workspace.WorkspaceController.saveWorkspace() creating a new workspace entry");
                 dbWorkspace=new Workspace();
                // dbWorkspace.setId(currentWorkspaceId);
                 dbWorkspace.setName(model.getName().get());
                 workspaceService.createWorkspace(dbWorkspace);
                 model.setId(dbWorkspace.getId());
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
             Set<QcMatrixRow> dbQcMatrixRows=new HashSet<>();
             
             workspaceService.createWorkspace(dbWorkspace);
             
             for(JobType0Model fejob:jobsInWorkSpace){
                 Job dbjob;
                 Set<Volume> dbVolumesForCurrentJob=new HashSet<>();
                 Long currentJobId=fejob.getId();
                 if(currentJobId==null){                                                                   //if it's a new node. (not saved), then create a new Job in the database and set it up.
                    dbjob=new Job();
                    Long typeOfJob=fejob.getType();
                    NodeType nodetype=nodeTypeService.getNodeTypeObjForType(typeOfJob);                   
                    dbjob.setNodetype(nodetype);
                    dbjob.setWorkspace(dbWorkspace);
                    dbjob.setNameJobStep(fejob.getNameproperty().get());
                     
                    jobService.createJob(dbjob);
                    System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Creating job "+dbjob.getNameJobStep()+" id: "+dbjob.getId());
                    
                    fejob.setId(dbjob.getId());
                    
                     //nodetype is set up during install.
                    
                    
                    
                 }else{
                     
                     dbjob=jobService.getJob(currentJobId);                                               // else get the instance of the previously saved job
                     System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Fetched job "+dbjob.getNameJobStep()+" id: "+dbjob.getId());
                     dbjob.setNameJobStep(fejob.getNameproperty().get());
                     
                 }
                 
                    
                /***
                 * Volumes in the job
                 */
                 
                 
                 List<Volume0> fevolsinFejob=fejob.getVolumes();
                 for(Volume0 vol:fevolsinFejob){
                     Volume dbVol;
                     Long currentVolumeId=vol.getId();
                     if(currentVolumeId==null){
                        dbVol=new Volume();
                        dbVol.setVolumeType(vol.getType());
                        dbVol.setNameVolume(vol.getName().get());
                        dbVol.setPathOfVolume(vol.getVolume().getAbsolutePath());
                        dbVol.setJob(dbjob);
                        volumeService.createVolume(dbVol);
                         System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): id is now "+dbVol.getId());
                        vol.setId(dbVol.getId());
                     }else{
                        dbVol=volumeService.getVolume(vol.getId());
                     }
                     
                     dbVolumesForCurrentJob.add(dbVol);
                     
                 }
                
                 dbVolumes.addAll(dbVolumesForCurrentJob);
                 
                dbjob.setVolumes(dbVolumesForCurrentJob);
                
                
                
                
                
                
             /***
              * QcMatrix for the job. QcMatrix are commited/updated straight to the db based on UI
              **/
            
                List<QcMatrixRow> qcmatrixForJob=qcMatrixRowService.getQcMatrixForJob(dbjob);
                dbjob.setQcmatrix(new HashSet<>(qcmatrixForJob));
                dbjobs.add(dbjob);
             }
             
             
             
             
             
            
             
             dbWorkspace.setJobs(dbjobs);
             
             
            
             
             
             
             /**
              * Setting up Links
              */
             /*
             Set<EdgeModel> edges=model.getObservableEdges();
             Set<DotModel> dots=new HashSet<>();
             Set<Link> dbLinks=new HashSet<>();
             
             dotService.clearUnattachedDots(dbWorkspace);   //delete dots that have zero length set of links. i.e. unattached dots. for current session
             for(EdgeModel edge:edges){
                 DotModel dot=edge.getDotModel();
                 dots.add(dot);
                 Long currentDotId=dot.getId();
                 Dot dbDot;
                 /*if(currentDotId==null){
                 dbDot=new Dot();
                 dbDot.setWorkspace(dbWorkspace);
                 dbDot.setStatus(dot.getStatus().get());
                 
                 dotService.createDot(dbDot);
                 System.out.println("fend.workspace.WorkspaceController.saveWorkspace()..creating dot with id: "+dbDot.getId()+ " currentId: "+currentDotId+" status: "+dbDot.getStatus() );
                 dot.setId(dbDot.getId());
                 }else{*/
                     
                  /*   dbDot=dotService.getDot(currentDotId);
                     
                     System.out.println("fend.workspace.WorkspaceController.saveWorkspace()..fetched dot with id: "+dbDot.getId()+ " currentId: "+currentDotId+" initial status: "+dbDot.getStatus() );
                     dbDot.setStatus(dot.getStatus().get());
                     System.out.println("fend.workspace.WorkspaceController.saveWorkspace()..fetched dot with id: "+dbDot.getId()+ " currentId: "+currentDotId+" final status: "+dbDot.getStatus() );
                // }
                 
                 Set<LinkModel> linksSharingThisDot=dot.getLinks();
                 /*  Set<Link> dbLinksForDbDot=new HashSet<>();*/
                 
              /*   for(LinkModel lm:linksSharingThisDot){
                     Link dbLink=new Link();
                     Job child=jobService.getJob(lm.getChild().getId());
                     Job parent=jobService.getJob(lm.getParent().getId());
                      linkService.clearLinksforJob(child);                 //rebuild each save
                      linkService.clearLinksforJob(parent);                //rebuild each save     
                     dbLink.setParent(parent);
                     dbLink.setChild(child);
                     dbLink.setDot(dbDot);
                 //    dbLinksForDbDot.add(dbLink);
                     dbLinks.add(dbLink);
                 }
                                
                    
                 dbDot.setLinks(dbLinks);
                 dbDots.add(dbDot);
                 
                 
                 
             }
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Number of edges: "+edges.size());
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Number of dots: "+dots.size());
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): dbLinks.size(): "+dbLinks.size());
             
             
             dbWorkspace.setDots(dbDots);
             
             for(Link l:dbLinks) {
             
             linkService.createLink(l);
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): creating link: "+l.getId()+" with dot: "+l.getDot().getId()+" parent: "+l.getParent().getNameJobStep()+"("+l.getParent().getId()+") child:"+l.getChild().getNameJobStep()+"("+l.getChild().getId()+")");
             }
            
             for(Dot d:dbDots) {
                 System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): updating dot: "+d.getId());
                 dotService.updateDot(d.getId(),d);
             }   //create only attached ones.
              
             */
             
             
             
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
          
          
           for(Job dbjob:dbjobs) jobService.updateJob(dbjob.getId(),dbjob);
            for(Volume dbVol:dbVolumes) volumeService.updateVolume(dbVol.getId(),dbVol);
          
            
            
             workspaceService.updateWorkspace(dbWorkspace.getId(), dbWorkspace);
         }
         else{
             System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Workspace is missing a name. Unsaved!");
         }
         
          
          
      }
      
     
     private  void loadSession(){
         Workspace  dbWorkspace=workspaceService.getWorkspace(model.getId());
        
         Set<Job> jobsInDb=dbWorkspace.getJobs();
         List<JobType0Model> frontEndJobModels=new ArrayList<>();
         
         Map<Long,JobType0Model> idFrontEndJobMap=new HashMap<>();              //used to link the nodes later. alook up map
         
         
         
         for(Job dbj:jobsInDb){
             JobType0Model fejob=null;
             
             Long type=dbj.getNodetype().getActualnodeid();
             if(type.equals(JobType0Model.PROCESS_2D)){
                 fejob=new JobType1Model(model);
                 fejob.setId(dbj.getId());   
                 fejob.setNameproperty(dbj.getNameJobStep());
                 System.out.println("fend.workspace.WorkspaceController.loadSession(): Added job: "+dbj.getNameJobStep());
                 
             }
             
             Set<Volume> dbvols=dbj.getVolumes();
             List<Volume0> frontEndVolumeModels=new ArrayList<>();
             for(Volume dbv:dbvols){
                 Volume0 fevol=null;
                 Long vtype=dbv.getVolumeType();
                 
                 if(vtype.equals(Volume0.PROCESS_2D)){
                     fevol=new Volume1(fejob);                  //parent job and id set in contructor
                     
                     fevol.setId(dbv.getId());
                     fevol.setName(dbv.getNameVolume());
                     File volumeOnDisk=new File(dbv.getPathOfVolume());
                     fevol.setVolume(volumeOnDisk);
                     System.out.println("fend.workspace.WorkspaceController.loadSession(): Added Volume : "+dbv.getNameVolume()+" to job: "+dbj.getNameJobStep() );
                 }
                 frontEndVolumeModels.add(fevol);
                 
             } 
             fejob.setVolumes(frontEndVolumeModels);
             
             idFrontEndJobMap.put(fejob.getId(), fejob);
             
          frontEndJobModels.add(fejob);   
         }
         model.setObservableJobs(new HashSet<>(frontEndJobModels));       //front end jobs and volumes set in workspace model
         
         
         
         Set<Dot> dots=dbWorkspace.getDots();
         System.out.println("fend.workspace.WorkspaceController.loadSession(): the size of  dots retrieved : "+dots.size() );
         List<DotModel> frontEndDotModels=new ArrayList<>();
         
         for(Dot dot:dots){
             DotModel fedot=new DotModel(model);
             Set<Link> links=dot.getLinks();
             System.out.println("fend.workspace.WorkspaceController.loadSession(): number of links sharing  dot: "+dot.getId()+" in state "+dot.getStatus()+" links.size(): "+links.size());
             fedot.setStatus(dot.getStatus());
             fedot.setId(dot.getId());
             Set<LinkModel> frontEndLinkModels=new HashSet<>();
             
             for(Link link:links){
                
                 Job parent=link.getParent();
                 Job child=link.getChild();
                 System.out.println("fend.workspace.WorkspaceController.loadSession(): adding parent from Link "+parent.getNameJobStep()+" id: "+parent.getId());
                 System.out.println("fend.workspace.WorkspaceController.loadSession(): adding child from Link "+child.getNameJobStep()+" id: "+child.getId());
                 JobType0Model feParent=idFrontEndJobMap.get(parent.getId());
                 JobType0Model feChild=idFrontEndJobMap.get(child.getId());
                 
                 LinkModel felink=new LinkModel();
                 felink.setParent(feParent);
                 felink.setChild(feChild);
                 fedot.createLink(feParent, feChild);
                frontEndLinkModels.add(felink);
             }
             fedot.setLinks(frontEndLinkModels);
             frontEndDotModels.add(fedot);
         }
         
         
         Map<Long,JobType0View> idJobViewsMap=inflateFrontEndViews();
         
         
         
         
        Set<EdgeModel> edges=new HashSet<>();
        for(DotModel fedot:frontEndDotModels){
            System.out.println("fend.workspace.WorkspaceController.loadSession() for loop for feDot: id "+fedot.getId()+" in state "+fedot.getStatus().get());
            if(fedot.getStatus().get().equals(DotModel.NJS)){                                     // if NJS.  then the one link will be a parentchildedge
               DotView dotview=null;
                Set<LinkModel> links=fedot.getLinks();
                for (LinkModel link : links) {
                    
                    JobType0Model parent=link.getParent();
                    JobType0Model child=link.getChild();
                    ParentChildEdgeModel pcem=new ParentChildEdgeModel();
                    pcem.setParentJob(parent);
                    pcem.setChildJob(child);
                    pcem.setDotModel(fedot);
                    
                    ParentChildEdgeView pcv=new ParentChildEdgeView(pcem, idJobViewsMap.get(parent.getId()), interactivePane);
                    pcv.getController().setChildJobView(idJobViewsMap.get(child.getId()),dotview);
                    
                    edges.add(pcem);
                }
             }
            if(fedot.getStatus().get().equals(DotModel.JOIN)){                                     // if JOIN.  Set the 1st encounter parent-child as a PCE. get the common dot(view).
                                                                                                   // next join to the dot as you would handle the case of a drop on the dotView.
               Set<LinkModel> links=fedot.getLinks();
               int count=0;
               DotView dotview=null;
                for (LinkModel link : links) {
                    JobType0Model parent=link.getParent();
                    JobType0Model child=link.getChild();
                    ParentChildEdgeModel pcem=new ParentChildEdgeModel();
                    pcem.setParentJob(parent);
                    pcem.setChildJob(child);
                    pcem.setDotModel(fedot);
                    
                        if(count==0){                                                                   
                            System.out.println("fend.workspace.WorkspaceController.loadSession() count: "+count+" Befrore call dotview is "+(dotview==null?"is Null":" is new "));
                            ParentChildEdgeView pcv=new ParentChildEdgeView(pcem, idJobViewsMap.get(parent.getId()), interactivePane);
                            dotview=pcv.getController().setChildJobView(idJobViewsMap.get(child.getId()),null);     // creates the dot  and get it
                            System.out.println("fend.workspace.WorkspaceController.loadSession() count: "+count+" After call dotview is "+(dotview==null?"is Null":" is new "));
                        }else{
                            System.out.println("fend.workspace.WorkspaceController.loadSession() count: "+count+" Vefore call dotview is "+(dotview==null?"is Null":" is new "));
                            ParentChildEdgeView pcv=new ParentChildEdgeView(pcem, idJobViewsMap.get(parent.getId()), interactivePane);
                            pcv.getController().setChildJobView(idJobViewsMap.get(child.getId()),dotview);      //joins to the dot
                        }
                    count++;
                    edges.add(pcem);
                }
                
            }
            if(fedot.getStatus().get().equals(DotModel.SPLIT)){                                     // if SPLIT.  then the one link is PCE the rest DJE
               Set<LinkModel> links=fedot.getLinks();
               int count=0;
               DotView dotview=null;
               for(LinkModel link:links){
                   JobType0Model parent=link.getParent();
                   JobType0Model child=link.getChild();
                   if(count==0){
                        ParentChildEdgeModel pcem=new ParentChildEdgeModel();
                        pcem.setParentJob(parent);
                        pcem.setChildJob(child);
                        pcem.setDotModel(fedot);
                        System.out.println("fend.workspace.WorkspaceController.loadSession() count: "+count+" Befrore call dotview is "+(dotview==null?"is Null":" is new "));
                        ParentChildEdgeView pcv=new ParentChildEdgeView(pcem, idJobViewsMap.get(parent.getId()), interactivePane);
                        dotview=pcv.getController().setChildJobView(idJobViewsMap.get(child.getId()),null);     // creates the dot  and get it
                        System.out.println("fend.workspace.WorkspaceController.loadSession() count: "+count+" After call dotview is "+(dotview==null?"is Null":" is new "));
                        edges.add(pcem);
                        
                   }else{
                       DotJobEdgeModel djem=new DotJobEdgeModel();
                       djem.setDotModel(fedot);
                       djem.setChildJob(child);
                       DotJobEdgeView djev=new DotJobEdgeView(djem, dotview, interactivePane);
                       djev.getController().setChildJobView(idJobViewsMap.get(child.getId()));
                       edges.add(djem);
                   }
                   count++;
                   
               }
            }
            
        }
        
        model.setObservableEdges(edges);
         
        
         
       //  
       //  model.setObservableEdges(new HashSet<>(frontEnd));
     }

    private Map<Long,JobType0View> inflateFrontEndViews() {
        Set<JobType0Model> jobmodels=(Set<JobType0Model>) model.getObservableJobs();
        Set<EdgeModel> edgeModels=(Set<EdgeModel>) model.getObservableEdges();
        List<JobType0View> jobviews=new ArrayList<>();
        
        Map<Long,JobType0View> idFrontEndJobMap=new HashMap<>();              //used to link the nodes later. alook up map
        
        for(JobType0Model job:jobmodels){
            if(job.getType().equals(JobType0Model.PROCESS_2D)){
                JobType1View jv=new JobType1View((JobType1Model) job, interactivePane);
                
                /**
                 * Attach Listeners to save workspace
                 */
                BooleanProperty changeProperty=new SimpleBooleanProperty(false);
                changeProperty.bind(job.getChangeProperty());
                changeProperty.addListener(workspaceChangedListener);
                changePropertyList.add(changeProperty);
                idFrontEndJobMap.put(job.getId(),jv);
                interactivePane.getChildren().add(jv);
            }
        }
        return idFrontEndJobMap;
    }

    public void setLoading(boolean b) {
        loadingProperty.set(b);
    }
     
     
     
     /***
      * Listeners
      
      **/
     private ChangeListener<Boolean> workspaceChangedListener=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
              if(model.DEBUG)System.out.println("Saving workspace");
            saveWorkspace();
        }
    };
     
     private ChangeListener<Boolean> loadingListener=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                loadSession();
            }
        }
    };
      
     private ListChangeListener<Node> jobLinkChangeListener=new ListChangeListener<Node>() {
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
    
     
     
     /***
      * Get all insight versions from the folder
      */
     
     final private String INSIGHT_REGEX="\\d*.\\d*-[\\w_-]*";     //match d.d-xxxxxx, d.d-xxxxx-ABC , d.d-xxxxxxx-ABC_mno, d.d-xxxxxx_mno
     final private Pattern pattern=Pattern.compile(INSIGHT_REGEX);
     final private FileFilter insightFilter=new FileFilter(){
        @Override
        public boolean accept(File pathname) {
            return pattern.matcher(pathname.getName()).matches();
        }
         
     };
             
             
    
}
