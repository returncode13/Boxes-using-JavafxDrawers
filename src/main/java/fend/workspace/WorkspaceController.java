/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.workspace;

import app.properties.AppProperties;
import db.model.Ancestor;
import db.model.Descendant;
import db.model.Dot;
import db.model.Doubt;
import db.model.DoubtStatus;
import db.model.DoubtType;
import db.model.Header;
import db.model.Job;
import db.model.Link;
import db.model.NodeProperty;
import db.model.NodePropertyValue;
import db.model.NodeType;
import db.model.PropertyType;
import db.model.QcMatrixRow;
import db.model.QcTable;
import db.model.Subsurface;
import db.model.SubsurfaceJob;
import db.model.Summary;
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
import db.services.NodePropertyService;
import db.services.NodePropertyServiceImpl;
import db.services.NodePropertyValueService;
import db.services.NodePropertyValueServiceImpl;
import db.services.NodeTypeService;
import db.services.NodeTypeServiceImpl;
import db.services.PropertyTypeService;
import db.services.PropertyTypeServiceImpl;
import db.services.QcMatrixRowService;
import db.services.QcMatrixRowServiceImpl;
import db.services.QcTableService;
import db.services.QcTableServiceImpl;
import db.services.SubsurfaceJobService;
import db.services.SubsurfaceJobServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import db.services.SummaryService;
import db.services.SummaryServiceImpl;
import db.services.VariableArgumentService;
import db.services.VariableArgumentServiceImpl;
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
import fend.job.job0.JobType0Model;
import fend.job.job0.JobType0View;
import fend.job.job0.property.JobModelProperty;
import fend.job.job2.JobType2Model;
import fend.job.job2.JobType2View;
import fend.job.job3.JobType3Model;
import fend.job.job3.JobType3View;
import fend.job.job4.JobType4Model;
import fend.job.job4.JobType4View;
import fend.summary.SummaryModel;
import fend.summary.SummaryView;
import fend.volume.volume0.Volume0;
import fend.volume.volume1.Volume1;
import fend.volume.volume2.Volume2;
import fend.workspace.gLink.GLink;
import java.io.File;
import java.io.FileFilter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import middleware.doubt.DoubtStatusModel;
import middleware.doubt.DoubtTypeModel;
import middleware.dugex.DugLogManager;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkspaceController {

    private WorkspaceModel model;
    private WorkspaceView node;
    private Workspace dbWorkspace;

    private BooleanProperty loadingProperty = new SimpleBooleanProperty(false);

    private WorkspaceService workspaceService = new WorkspaceServiceImpl();
    private JobService jobService = new JobServiceImpl();
    private VolumeService volumeService = new VolumeServiceImpl();
    private JobVolumeMapService jobVolumeMapService = new JobVolumeMapServiceImpl();
    private NodeTypeService nodeTypeService = new NodeTypeServiceImpl();
    private PropertyTypeService propertyTypeService=new PropertyTypeServiceImpl();
    private NodePropertyService nodePropertyService=new NodePropertyServiceImpl();
    private NodePropertyValueService nodePropertyValueService=new NodePropertyValueServiceImpl();
    private AncestorService ancestorService = new AncestorServiceImpl();
    private DescendantService descendantService = new DescendantServiceImpl();
    private LinkService linkService = new LinkServiceImpl();
    private DotService dotService = new DotServiceImpl();
    private QcMatrixRowService qcMatrixRowService = new QcMatrixRowServiceImpl();
    private HeaderService headerService = new HeaderServiceImpl();
    private SubsurfaceService subsurfaceService = new SubsurfaceServiceImpl();
    private DoubtService doubtService = new DoubtServiceImpl();
    private DoubtTypeService doubtTypeService = new DoubtTypeServiceImpl();
    private DoubtStatusService doubtStatusService = new DoubtStatusServiceImpl();
    private QcTableService qcTableService = new QcTableServiceImpl();
    private SummaryService summaryService = new SummaryServiceImpl();
    private SubsurfaceJobService subsurfaceJobService=new SubsurfaceJobServiceImpl();
    private VariableArgumentService variableArgumentService=new VariableArgumentServiceImpl();
    private List<BooleanProperty> changePropertyList = new ArrayList<>();
    
    private DoubtType doubtTypeQc;
    private DoubtType doubtTypeTraces;
    private DoubtType doubtTypeTime;
    private DoubtType doubtTypeInherit;
    
    private SummaryModel summaryModel=null;
    private SummaryView summaryView;
    private Executor exec;
    private ExecutorService execService;
    
    
    private   Map<Doubt,List<DoubtStatus>> doubtDoubtStatusMap=new HashMap<>();            //map of doubts and its associated doubt statuses
    private   Map<Doubt,List<Doubt>> causeAndAssociatedInheritanceMap=new HashMap<>();    //map between cause and its list of inherited doubts
    private   Map<InheritanceKey,Doubt> inheritanceMap=new HashMap<>();                 //unique identifier of each inherited doubt (Job, Sub, Cause)
    private   Map<Dot,List<VariableArgument>> variableArgumentMap=new HashMap<>();
    private   Map<SubsurfaceJobKey,SubsurfaceJob> subsurfaceJobSummaryTimeMap=new HashMap<>();
    private   Map<Subsurface,Set<Link>> subsurfaceLinkMap=new HashMap<>();
    private   Map<HeaderKey,Header> headerMap=new HashMap<>(); 
    private   Map<SummaryKey,Summary> summaryMap=new HashMap<>();                 //used to check if an entry exists in the database
    private   Map<DoubtKey,Doubt> causeDoubtMap=new HashMap<>();                       //used to check if an entry exists in the database (ONLY non inheritance types)
        
    private   List<Summary> newSummaries=new ArrayList<>();   //summaries that will be created in the database
    private   List<Summary> summariesToBeUpdated=new ArrayList<>();  //existing summaries that need to be updated;
    private   List<Doubt> causes=new ArrayList<>();           //doubts that will be created in the database
    private   List<Doubt> doubtsToBeUpdated=new ArrayList<>();  //doubts to be updated
    private   List<DoubtStatus> doubtStatusForCause=new ArrayList<>();    //doubtstatus that will be created in the database
    private   List<DoubtStatus> doubtStatusToBeUpdated=new ArrayList<>();    //doubtstatus to be updated
    private   List<Doubt> inheritedDoubts=new ArrayList<>();          //inherited doubts created AFTER causes
    private   List<DoubtStatus> inheritedDoubtStatus=new ArrayList<>();   //inherited doubtstatus...redundant?
    private   List<Long> idsOfCausalDoubtsToBeDeleted=new ArrayList<>();            //doubts that need to be deleted SECOND! ( used when a doubt was initially set but then the underlying condition is fixed before the next summary)
    private   List<Long> idsOfInheritedDoubtsToBeDeleted=new ArrayList<>();            //doubts that need to be deleted  FIRST( used when a doubt was initially set but then the underlying condition is fixed before the next summary)
    private   List<Long> idsOfDoubtStatusToBeDeleted=new ArrayList<>();            //doubtStatus that need to be deleted ( for deleted doubts)
    private   Map<AncestorKey,List<Ancestor>> ancestorMapForSummary=new HashMap<>();    // each key (job,sub) has a list of ancestors (jobs that contain the sub)
    private   Map<DescendantKey,List<Descendant>> descendantMapForSummary = new HashMap<>(); // each key (job,sub) has a list of descendants (jobs that contain the sub)
    private double percentageOfProcessorsUsed=AppProperties.PERCENTAGE_OF_PROCESSORS_USED;
    
    
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
    private Button segdbtn;
    
    @FXML
    private Button acqbtn;

    @FXML
    private Button textBtn;
    
    @FXML
    private Button summaryButton;

    @FXML
    void addBox(ActionEvent event) {
        Job dbjob = new Job();
        BooleanProperty changeProperty = new SimpleBooleanProperty(false);
         JobType1Model job = new JobType1Model(this.model);
         
           Task<String> task =new Task<String>(){
            @Override
            protected String call() throws Exception {
                    Long typeOfJob = JobType0Model.PROCESS_2D;
                    NodeType nodetype = nodeTypeService.getNodeTypeObjForType(typeOfJob);
                    dbjob.setNodetype(nodetype);
                    dbjob.setWorkspace(dbWorkspace);

                    dbjob.setDepth(JobType0Model.INITIAL_DEPTH);
                    jobService.createJob(dbjob);
                    job.setId(dbjob.getId());
                    job.setDatabaseJob(dbjob);
                    
                    
                    List<JobModelProperty> jobProperties=job.getJobProperties();
                    for (Iterator<JobModelProperty> iterator = jobProperties.iterator(); iterator.hasNext();) {
                        JobModelProperty jobProperty = iterator.next();
                        NodePropertyValue npv=new NodePropertyValue();
                        npv.setJob(dbjob);
                        PropertyType propertyType=propertyTypeService.getPropertyTypeObjForName(jobProperty.getPropertyName());
                        NodeProperty nodeProperty=nodePropertyService.getNodeProperty(nodetype, propertyType);
                        npv.setNodeProperty(nodeProperty);
                        npv.setValue(jobProperty.getPropertyValue());
                        nodePropertyValueService.createNodePropertyValue(npv);
                    }
                         
                    return "finished building a 2dProcess entry: "+dbjob.getId();
            }
        };
        
        task.setOnFailed(e->{
                    task.getException().printStackTrace();
      });
      
        
        task.setOnSucceeded(e->{
                    changeProperty.bind(job.getChangeProperty());
                    changeProperty.addListener(workspaceChangedListener);
                    changePropertyList.add(changeProperty);
                    JobType1View jobview = new JobType1View(job, interactivePane);
                    interactivePane.getChildren().add(jobview);
            //        System.out.println("workspace.WorkspaceController.addBox(): "+g1Child.getId()%100);

         });
      
        task.setOnRunning(e->{
            System.out.println("fend.workspace.WorkspaceController.add2D(): process is running");
        });
      
      exec.execute(task);
            
            

    }
    
    @FXML
    void addSEGD(ActionEvent event) {
        Job dbjob = new Job();
        BooleanProperty changeProperty = new SimpleBooleanProperty(false);
        JobType2Model job = new JobType2Model(WorkspaceController.this.model);
        
        
        Task<String> task =new Task<String>(){
            @Override
            protected String call() throws Exception {
                Long typeOfJob = JobType0Model.SEGD_LOAD;
                    NodeType nodetype = nodeTypeService.getNodeTypeObjForType(typeOfJob);
                    dbjob.setNodetype(nodetype);
                    dbjob.setWorkspace(dbWorkspace);

                    dbjob.setDepth(JobType0Model.INITIAL_DEPTH);
                    System.out.println("fend.workspace.WorkspaceController.addSEGD(): creating  a new job in the database");
                    jobService.createJob(dbjob);

                    System.out.println(".call(): setting the job id");
                    job.setId(dbjob.getId());
                    job.setDatabaseJob(dbjob);
                    
                    System.out.println(".call(): getting jobProperties");
                     List<JobModelProperty> jobProperties=job.getJobProperties();
                    for (Iterator<JobModelProperty> iterator = jobProperties.iterator(); iterator.hasNext();) {
                        JobModelProperty jobProperty = iterator.next();
                        System.out.println(".call(): for jobProperty "+jobProperty.getPropertyName());
                        System.out.println("");
                        System.out.println(".call(): started a new  nodepropertyvalue object");
                        NodePropertyValue npv=new NodePropertyValue();
                        System.out.println(".call(): setting npvs job= dbjob");
                        npv.setJob(dbjob);
                        System.out.println(".call(): retrieving propertype: "+jobProperty.getPropertyName());
                        PropertyType propertyType=propertyTypeService.getPropertyTypeObjForName(jobProperty.getPropertyName());
                        System.out.println(".call(): retrieving nodeProperty for nodetype: "+nodetype.getName()+" and propertytype: "+propertyType.getName());
                        NodeProperty nodeProperty=nodePropertyService.getNodeProperty(nodetype, propertyType);
                        System.out.println(".call(): setting npv.nodeproperty");
                        npv.setNodeProperty(nodeProperty);
                        System.out.println(".call(): setting npv.nodepropertyValue");
                        npv.setValue(jobProperty.getPropertyValue());
                        System.out.println(".call(): creating npv in the database");
                        nodePropertyValueService.createNodePropertyValue(npv);
                        System.out.println(".call(): done creating for this property");
            
            
        }
                    return "finished building a SEGD entry: "+dbjob.getId();
            }
        };
        
        
      //  BooleanProperty changeProperty = new SimpleBooleanProperty(false);
      
      
      task.setOnFailed(e->{
          task.getException().printStackTrace();
      });
      
      task.setOnSucceeded(e->{
          System.out.println("fend.workspace.WorkspaceController.addSEGD(): task succeeded. adding on the view.");
            changeProperty.bind(job.getChangeProperty());
            changeProperty.addListener(workspaceChangedListener);

            changePropertyList.add(changeProperty);
            JobType2View jobview = new JobType2View(job, interactivePane);
            interactivePane.getChildren().add(jobview);
      });
      
      task.setOnRunning(e->{
          System.out.println("fend.workspace.WorkspaceController.addSEGD(): process is running");
      });
      
      exec.execute(task);
        
    }

    @FXML
    void addAcq(ActionEvent event) {
        Job dbjob = new Job();
        Long typeOfJob = JobType0Model.ACQUISITION;
        NodeType nodetype = nodeTypeService.getNodeTypeObjForType(typeOfJob);
        dbjob.setNodetype(nodetype);
        dbjob.setWorkspace(dbWorkspace);

        dbjob.setDepth(JobType0Model.INITIAL_DEPTH);
        jobService.createJob(dbjob);

        
        JobType3Model job = new JobType3Model(this.model);
        job.setId(dbjob.getId());
        
         List<JobModelProperty> jobProperties=job.getJobProperties();
        for (Iterator<JobModelProperty> iterator = jobProperties.iterator(); iterator.hasNext();) {
            JobModelProperty jobProperty = iterator.next();
            NodePropertyValue npv=new NodePropertyValue();
            npv.setJob(dbjob);
            PropertyType propertyType=propertyTypeService.getPropertyTypeObjForName(jobProperty.getPropertyName());
            NodeProperty nodeProperty=nodePropertyService.getNodeProperty(nodetype, propertyType);
            npv.setNodeProperty(nodeProperty);
            npv.setValue(jobProperty.getPropertyValue());
            nodePropertyValueService.createNodePropertyValue(npv);
            
            
            
        }
        
        
        BooleanProperty changeProperty = new SimpleBooleanProperty(false);
        changeProperty.bind(job.getChangeProperty());
        changeProperty.addListener(workspaceChangedListener);

        changePropertyList.add(changeProperty);
        JobType3View jobview = new JobType3View(job, interactivePane);
        interactivePane.getChildren().add(jobview);
    }
    
    @FXML
    void addText(ActionEvent event) {
        Job dbjob = new Job();
        Long typeOfJob = JobType0Model.TEXT;
        NodeType nodetype = nodeTypeService.getNodeTypeObjForType(typeOfJob);
        dbjob.setNodetype(nodetype);
        dbjob.setWorkspace(dbWorkspace);

        dbjob.setDepth(JobType0Model.INITIAL_DEPTH);
        jobService.createJob(dbjob);

        JobType4Model job = new JobType4Model(this.model);
        job.setId(dbjob.getId());
        
         List<JobModelProperty> jobProperties=job.getJobProperties();
        for (Iterator<JobModelProperty> iterator = jobProperties.iterator(); iterator.hasNext();) {
            JobModelProperty jobProperty = iterator.next();
            NodePropertyValue npv=new NodePropertyValue();
            npv.setJob(dbjob);
            PropertyType propertyType=propertyTypeService.getPropertyTypeObjForName(jobProperty.getPropertyName());
            NodeProperty nodeProperty=nodePropertyService.getNodeProperty(nodetype, propertyType);
            npv.setNodeProperty(nodeProperty);
            npv.setValue(jobProperty.getPropertyValue());
            nodePropertyValueService.createNodePropertyValue(npv);
            
            
            
        }
        
        BooleanProperty changeProperty = new SimpleBooleanProperty(false);
        changeProperty.bind(job.getChangeProperty());
        changeProperty.addListener(workspaceChangedListener);

        changePropertyList.add(changeProperty);
        JobType4View jobview = new JobType4View(job, interactivePane);
        interactivePane.getChildren().add(jobview);

    }

    
    
    @FXML 
    void getSummary(ActionEvent event) throws Exception {
            
        Task<String> summaryTask = new Task<String>(){
            @Override
            protected String call() throws Exception {
                if(summaryModel==null) summaryModel=new SummaryModel(WorkspaceController.this);
                return "Finished summary for workspace ";
            }
        };
        
        summaryTask.setOnFailed(e->{
            summaryTask.getException().printStackTrace();
        });
        
        summaryTask.setOnRunning(e->{
            System.out.println("fend.workspace.WorkspaceController.getSummary(): Summary operation running in the background thread");
        });
                
        summaryTask.setOnSucceeded(e->{
              summaryView=new SummaryView(summaryModel);
        
        });
                exec.execute(summaryTask);
        
                //summarize();
                /*
        //  try{
        Map<String, Double> mapForVariableSetting = new HashMap<>();
        Set<String> variableSet = new HashSet<>();
        Set<Job> argumentSet = new HashSet<>();
        List<Subsurface> subsurfaceList = subsurfaceService.getSubsurfaceList();
        dbWorkspace = workspaceService.getWorkspace(dbWorkspace.getId());
        for (Subsurface subb : subsurfaceList) {
            System.out.println("fend.workspace.WorkspaceController.getSummary(): Loop for subsurface : " + subb.getSubsurface());
            Set<Link> linkNodesContainingSub = linkService.getLinksContainingSubsurface(subb, dbWorkspace);                  //all links where both the parent and the child contains sub subb
            for (Link l : linkNodesContainingSub) {
                Summary summaryforCurrentJob;
                if ((summaryforCurrentJob = summaryService.getSummaryFor(subb.getSequence(), l.getParent())) == null) {            //create an entry for parent summaryforCurrentJob
                    summaryforCurrentJob = new Summary();
                    summaryforCurrentJob.setSequence(subb.getSequence());
                    summaryforCurrentJob.setJob(l.getParent());
                    summaryService.createSummary(summaryforCurrentJob);
                }
                if ((summaryforCurrentJob = summaryService.getSummaryFor(subb.getSequence(), l.getChild())) == null) {             //create an entry for child summaryforCurrentJob 
                    summaryforCurrentJob = new Summary();
                    summaryforCurrentJob.setSequence(subb.getSequence());
                    summaryforCurrentJob.setJob(l.getChild());
                    summaryService.createSummary(summaryforCurrentJob);
                }

                //dependency
                DoubtType doubtTypeTraces = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TRACES);
                System.out.println("fend.workspace.WorkspaceController.getSummary(): inside Link : " + l.getId() + " " + l.getParent().getNameJobStep() + "--->" + l.getChild().getNameJobStep());

                Dot dot = l.getDot();                     //the dot to which the link belongs
                String function = dot.getFunction();
                Double tolerance = dot.getTolerance();
                Double error = dot.getError();
                Set<VariableArgument> variableArguments = dot.getVariableArguments();         //variable and the arguments belonging to the dot.
                mapForVariableSetting.clear();
                variableSet.clear();
                argumentSet.clear();
                for (VariableArgument va : variableArguments) {
                    String var = va.getVariable();
                    Job arg = va.getArgument();
                    Double tracesArg;
                    Header h = headerService.getChosenHeaderFor(arg, subb);
                    if (h == null) {
                        tracesArg = 0.0;
                    } else {
                        tracesArg = Double.valueOf(h.getTraceCount() + "");
                    }

                    mapForVariableSetting.put(var, tracesArg);
                    if (!var.equals("y0")) {                      //y0 is the lhs which is fixed, the rhs needs to be evaluated. Do not include the y-term
                        variableSet.add(var);
                        argumentSet.add(arg);
                    }

                }
                System.out.println("fend.workspace.WorkspaceController.getSummary(): Sub: " + subb.getSubsurface() + " linkParent: " + l.getParent().getNameJobStep() + " linkChild: " + l.getChild().getNameJobStep());
                System.out.println("fend.workspace.WorkspaceController.getSummary(): function: " + function);
                System.out.println("fend.workspace.WorkspaceController.getSummary(): setting var-args");
                for (Map.Entry<String, Double> entry : mapForVariableSetting.entrySet()) {
                    String key = entry.getKey();
                    Double value = entry.getValue();
                    System.out.println(key + " = " + value);
                }

                Expression e = new ExpressionBuilder(function)
                        .variables(variableSet)
                        .build()
                        .setVariables(mapForVariableSetting);
                Double result = e.evaluate();

                Double y = mapForVariableSetting.get("y0");
                Double evaluated = Math.abs(y - result) / y;
                System.out.println("fend.workspace.WorkspaceController.getSummary(): result = " + result + " evaluated difference/y = " + evaluated);
                if (evaluated <= tolerance) {
                    System.out.println("fend.workspace.WorkspaceController.getSummary(): no doubt");
                } else if (evaluated <= error && evaluated > tolerance) {
                    System.out.println("fend.workspace.WorkspaceController.getSummary(): creating doubt");
                    String dotState = dot.getStatus();

                    Doubt doubt;

                    if (dotState.equals(DotModel.JOIN)) {
                        if ((doubtService.getDoubtFor(subb, l.getChild(), dot, doubtTypeTraces)) == null) {
                            System.out.println("fend.workspace.WorkspaceController.getSummary(): creating doubt entry for " + subb.getSubsurface() + " g1Child: " + l.getChild().getId() + " : " + l.getChild().getNameJobStep());
                            doubt = new Doubt();
                            doubt.setChildJob(l.getChild());
                            doubt.setSubsurface(subb);
                            doubt.setSequence(subb.getSequence());
                            doubt.setDot(dot);
                            doubt.setDoubtType(doubtTypeTraces);
                            //doubt.setUser(user);
                            doubtService.createDoubt(doubt);

                            DoubtStatus doubtStatus = new DoubtStatus();
                            doubtStatus.setComment(DoubtStatusModel.getNewDoubtTraceMessage(function, tolerance, error, evaluated, y, doubtTypeTraces.getName()));
                            doubtStatus.setDoubt(doubt);
                            doubtStatus.setStatus(DoubtStatusModel.YES);
                            doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                            //doubtStatus.setUser(user);
                            doubtStatusService.createDoubtStatus(doubtStatus);
                            doubt.addToDoubtStatuses(doubtStatus);
                            doubtService.updateDoubt(doubt.getId(), doubt);

                            summaryforCurrentJob.setTraceSummary(true);
                        } else {
                            doubt = doubtService.getDoubtFor(subb, l.getChild(), dot, doubtTypeTraces);
                            summaryforCurrentJob.setTraceSummary(true);
                            Set<DoubtStatus> doubtStatuses = doubt.getDoubtStatuses();
                            for (DoubtStatus ds : doubtStatuses) {
                                System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses assosciated with Doubt: " + doubt.getId() + " " + ds.getStatus() + " comment: " + ds.getComment() + " time: " + ds.getTimeStamp());
                            }
                        }
                    } else {
                        for (Job child : argumentSet) {                     //In states SPLIT and NJS , the argument set comprises of only children. since P=f(C1,C2,..Cn);
                            if ((doubtService.getDoubtFor(subb, child, dot, doubtTypeTraces)) == null) {
                                System.out.println("fend.workspace.WorkspaceController.getSummary(): creating doubt entry for " + subb.getSubsurface() + " g1Child: " + child.getId() + " : " + child.getNameJobStep());
                                doubt = new Doubt();
                                doubt.setChildJob(child);
                                doubt.setSubsurface(subb);
                                doubt.setSequence(subb.getSequence());
                                doubt.setDot(dot);
                                doubt.setDoubtType(doubtTypeTraces);
                                //doubt.setUser(user);
                                doubtService.createDoubt(doubt);
                                summaryforCurrentJob.setTraceSummary(true);

                                DoubtStatus doubtStatus = new DoubtStatus();
                                doubtStatus.setComment(DoubtStatusModel.getNewDoubtTraceMessage(function, tolerance, error, evaluated, y, doubtTypeTraces.getName()));
                                doubtStatus.setDoubt(doubt);
                                doubtStatus.setStatus(DoubtStatusModel.YES);
                                doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                                //doubtStatus.setUser(user);
                                doubtStatusService.createDoubtStatus(doubtStatus);
                                doubt.addToDoubtStatuses(doubtStatus);
                                doubtService.updateDoubt(doubt.getId(), doubt);
                            } else {
                                doubt = doubtService.getDoubtFor(subb, child, dot, doubtTypeTraces);
                                Set<DoubtStatus> doubtStatuses = doubt.getDoubtStatuses();
                                summaryforCurrentJob.setTraceSummary(true);
                                for (DoubtStatus ds : doubtStatuses) {
                                    System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: " + doubt.getId() + " " + ds.getStatus() + " comment: " + ds.getComment() + " time: " + ds.getTimeStamp());
                                }

                            }
                        }
                    }

                }

                //dependency end    <--put inside a function. 
                //qc start  <--put inside a function. 
                DoubtType doubtTypeQc = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.QC);
                Job lparent = l.getParent();
                Job jchild = l.getChild();
                List<QcMatrixRow> parentQcMatrix = qcMatrixRowService.getQcMatrixForJob(lparent, true);
                Boolean passQc = true;
                for (QcMatrixRow qcmr : parentQcMatrix) {
                    QcTable qctableentries = qcTableService.getQcTableFor(qcmr, subb);
                    Boolean qcresult = qctableentries.getResult();
                    if (qcresult == null) {
                        qcresult = false;
                    }
                    passQc = passQc && qcresult;

                }
                if (!passQc) {  //if parent has failed a single qc. Indeterminate or unchecked. create a doubt in the child
                    Doubt doubt;
                    if ((doubt = doubtService.getDoubtFor(subb, jchild, dot, doubtTypeQc)) == null) {
                        doubt = new Doubt();
                        doubt.setChildJob(jchild);
                        doubt.setDot(dot);
                        doubt.setSubsurface(subb);
                        doubt.setSequence(subb.getSequence());
                        doubt.setDoubtType(doubtTypeQc);
                        //doubt.setUser(user)
                        doubtService.createDoubt(doubt);

                        summaryforCurrentJob.setQcSummary(true);
                        DoubtStatus doubtStatus = new DoubtStatus();
                        doubtStatus.setComment(DoubtStatusModel.getNewDoubtQCcessage(lparent.getNameJobStep(), jchild.getNameJobStep(), subb.getSubsurface(), doubtTypeQc.getName()));
                        doubtStatus.setDoubt(doubt);
                        doubtStatus.setStatus(DoubtStatusModel.YES);
                        doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                        //doubtStatus.setUser(user);
                        doubtStatusService.createDoubtStatus(doubtStatus);
                        doubt.addToDoubtStatuses(doubtStatus);
                        doubtService.updateDoubt(doubt.getId(), doubt);

                    } else {
                        Set<DoubtStatus> doubtStatuses = doubt.getDoubtStatuses();
                        summaryforCurrentJob.setQcSummary(true);
                        for (DoubtStatus d : doubtStatuses) {
                            System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: " + doubt.getId() + " " + d.getStatus() + " comment: " + d.getComment() + " time: " + d.getTimeStamp());
                        }
                    }
                }

                //qc end <--put inside a function
                //time start
                DoubtType doubtTypeTime = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TIME);
                Job hparent = l.getParent();
                Job hchild = l.getChild();
                Header hp = headerService.getChosenHeaderFor(hparent, subb);
                Header hc = headerService.getChosenHeaderFor(hchild, subb);
                Long hpt = Long.valueOf(hp.getTimeStamp());
                Long hct = Long.valueOf(hc.getTimeStamp());
                if (hpt >= hct) {             //if parent is created after the child. creat a doubt in the child
                    Doubt doubt;
                    if ((doubt = doubtService.getDoubtFor(subb, hchild, dot, doubtTypeTime)) == null) {
                        doubt = new Doubt();
                        doubt.setChildJob(hchild);
                        doubt.setDot(dot);
                        doubt.setSubsurface(subb);
                        doubt.setSequence(subb.getSequence());
                        doubt.setDoubtType(doubtTypeTime);
                        //doubt.setUser(user);
                        doubtService.createDoubt(doubt);
                        summaryforCurrentJob.setTimeSummary(true);
                        DoubtStatus doubtStatus = new DoubtStatus();
                        doubtStatus.setComment(DoubtStatusModel.getNewDoubtTimeMessage(hparent.getNameJobStep(), new String(hpt + ""), hchild.getNameJobStep(), new String(hct + ""), subb.getSubsurface(), doubtTypeTime.getName()));
                        doubtStatus.setDoubt(doubt);
                        doubtStatus.setStatus(DoubtStatusModel.YES);
                        doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                        //doubtStatus.setUser(user);
                        doubtStatusService.createDoubtStatus(doubtStatus);
                        doubt.addToDoubtStatuses(doubtStatus);
                        doubtService.updateDoubt(doubt.getId(), doubt);

                    } else {
                        Set<DoubtStatus> doubtStatuses = doubt.getDoubtStatuses();
                        summaryforCurrentJob.setTimeSummary(true);
                        for (DoubtStatus d : doubtStatuses) {
                            System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: " + doubt.getId() + " " + d.getStatus() + " comment: " + d.getComment() + " time: " + d.getTimeStamp());
                        }
                    }

                } else {
                    //do nothing
                }
                //time end <--put inside a function

                /*   <switched off for testing
                
                //insight
                DoubtType doubtTypeInsight=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.INSIGHT);
                Job iparent=l.getParent();
                Job ichild=l.getChild();
                Header hip=headerService.getChosenHeaderFor(iparent, subb);
                Header hic=headerService.getChosenHeaderFor(ichild, subb);
                String insightVersionsInParentJob= iparent.getInsightVersions();
                String insightVersionInParentHeader=hip.getInsightVersion();
                
                String parts[]=insightVersionsInParentJob.split(";");
                Boolean insightPass=false;
                for(String part:parts){
                insightPass=insightPass || (part.equals(insightVersionInParentHeader));
                }
                if(!insightPass){
                //hic.setDoubt(true)   //set doubt in the child
                Doubt doubt;
                if((doubt=doubtService.getDoubtFor(subb, ichild, dot, doubtTypeInsight))==null){
                doubt=new Doubt();
                doubt.setChildJob(ichild);
                doubt.setDot(dot);
                doubt.setSubsurface(subb);
                doubt.setSequence(subb.getSequence());
                doubt.setDoubtType(doubtTypeInsight);
                doubtService.createDoubt(doubt);
                summaryforCurrentJob.setInsightSummary(true);
                
                DoubtStatus doubtStatus=new DoubtStatus();
                doubtStatus.setComment(DoubtStatusModel.getNewDoubtInsightMessage(iparent.getNameJobStep(),subb.getSubsurface(),insightVersionsInParentJob,insightVersionInParentHeader,ichild.getNameJobStep(),doubtTypeInsight.getName()));
                doubtStatus.setDoubt(doubt);
                doubtStatus.setStatus(DoubtStatusModel.YES);
                doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                //doubtStatus.setUser(user);
                doubtStatusService.createDoubtStatus(doubtStatus);
                doubt.addToDoubtStatuses(doubtStatus);
                doubtService.updateDoubt(doubt.getId(), doubt);
                
                
                }else{
                Set<DoubtStatus> doubtStatuses=doubt.getDoubtStatuses();
                summaryforCurrentJob.setInsightSummary(true);
                for(DoubtStatus d:doubtStatuses){
                System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: "+doubt.getId()+" "+d.getStatus()+" comment: "+d.getComment()+" time: "+d.getTimeStamp());
                }
                }
                }
                //insight-end <--put inside a function
                
                 */
                //inheritance   <--called at the very end. Put all doubts on top
                
                /*
                Job inhParent = l.getParent();
                Job inhChild = l.getChild();
                DoubtType doubtTypeInherit = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.INHERIT);
                List<Doubt> doubtsInParent = doubtService.getDoubtFor(subb, inhParent, dot);

                if (doubtsInParent != null) {
                    for (Doubt cause : doubtsInParent) {
                        Set<Doubt> inherited = cause.getInheritedDoubts();

                        if (inherited != null && inherited.size() > 0) {
                            continue;                   //do nothing
                        } else {
                            Doubt inhDoubt = new Doubt();
                            inhDoubt.setDoubtType(doubtTypeInherit);
                            inhDoubt.setChildJob(inhChild);
                            inhDoubt.setDot(dot);
                            inhDoubt.setDoubtCause(cause);
                            inhDoubt.setSubsurface(subb);
                            inhDoubt.setSequence(subb.getSequence());
                            //inhDoubt.setUser(user);
                            doubtService.createDoubt(inhDoubt);
                            summaryforCurrentJob.setInheritanceSummary(true);
                            DoubtStatus doubtStatus = new DoubtStatus();
                            doubtStatus.setComment(DoubtStatusModel.getNewDoubtInheritanceMessage(inhParent.getNameJobStep(), subb.getSubsurface(), inhChild.getNameJobStep(), doubtTypeInherit.getName()));
                            doubtStatus.setDoubt(inhDoubt);
                            doubtStatus.setStatus(DoubtStatusModel.YES);
                            doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                            //doubtStatus.setUser(user);
                            doubtStatusService.createDoubtStatus(doubtStatus);
                            inhDoubt.addToDoubtStatuses(doubtStatus);
                            doubtService.updateDoubt(inhDoubt.getId(), inhDoubt);

                            cause.addToInheritedDoubts(inhDoubt);
                            doubtService.updateDoubt(cause.getId(), cause);

                        }

                    }
                }
                //inheritance-end <--put inside a function

                summaryService.updateSummary(summaryforCurrentJob.getId(), summaryforCurrentJob);
            }
        }*/

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
        model = item;
        //dbWorkspace = workspaceService.getWorkspace(model.getId());
        dbWorkspace=model.getWorkspace();
        
        doubtTypeQc = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.QC);
        doubtTypeTraces = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TRACES);
        doubtTypeTime = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TIME);
        doubtTypeInherit = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.INHERIT);
        
        
        File insightLocation = new File(AppProperties.INSIGHT_LOCATION);
        File[] insights = insightLocation.listFiles(insightFilter);
        List<String> insightVersionStrings = new ArrayList<>();
        System.out.println("fend.workspace.WorkspaceController.setModel(): No of insights found: "+insightVersionStrings.size());
        for (File insight : insights) {
            System.out.println("fend.workspace.WorkspaceController.setModel(): insightVersions Found: " + insight.getName());
            insightVersionStrings.add(insight.getName());
        }
        model.setInsightVersions(insightVersionStrings);
        
        exec=Executors.newCachedThreadPool((r)->{
            Thread t=new Thread(r);
            t.setDaemon(true);
            return t;
        });

    }

    public WorkspaceModel getModel() {
        return model;
    }

    public SummaryModel getSummaryModel() {
        return summaryModel;
    }

    public void setSummaryModel(SummaryModel summaryModel) {
        this.summaryModel = summaryModel;
    }
    
    

    void setView(WorkspaceView vq) {
        /*node=aThis;
        baseWindow.getChildren().add(node);*/
        node = vq;
    }

    /**
     * private Implementation
     */
    private void saveWorkspace() {
        /*    String name = model.getName().get();
        
        final BooleanProperty readyToSave = new SimpleBooleanProperty(false);
        if (model.getName().get().isEmpty()) {
        
        } else {
        System.out.println("fend.workspace.WorkspaceController.saveWorkspace() continue to save session: " + model.getName().get());
        readyToSave.set(true);
        }
        
        if (readyToSave.get()) {
        System.out.println("fend.workspace.WorkspaceController.saveWorkspace() Name: " + name);
        Long currentWorkspaceId = model.getId();
        Workspace dbWorkspace = null;
        
        if (currentWorkspaceId == null) {           //if there is no such entry for session in the database
        System.out.println("fend.workspace.WorkspaceController.saveWorkspace() creating a new workspace entry");
        dbWorkspace = new Workspace();
        // dbWorkspace.setId(currentWorkspaceId);
        dbWorkspace.setName(model.getName().get());
        workspaceService.createWorkspace(dbWorkspace);
        model.setId(dbWorkspace.getId());
        } else {
        dbWorkspace = workspaceService.getWorkspace(currentWorkspaceId);       //refer to the existing workspace in the db
        }
        
        // dbWorkspace.setName(model.getName().get());
        System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): saving the session.." + dbWorkspace.getName() + " id: " + dbWorkspace.getId());
        // workspaceService.createWorkspace(dbWorkspace);
        
        Set<JobType0Model> jobsInWorkSpace = model.getObservableJobs();
        Set<Job> dbjobs = new HashSet<>();
        Set<Volume> dbVolumes = new HashSet<>();
        Set<Dot> dbDots = new HashSet<>();
        Set<QcMatrixRow> dbQcMatrixRows = new HashSet<>();
        
        workspaceService.createWorkspace(dbWorkspace);*/
        //    for (JobType0Model fejob : jobsInWorkSpace) {
                /*Job dbjob;
                Set<Volume> dbVolumesForCurrentJob = new HashSet<>();
                Long currentJobId = fejob.getId();
                if (currentJobId == null) {                                                                   //if it'subb a new node. (not saved), then create a new Job in the database and set it up.
                    dbjob = new Job();
                    Long typeOfJob = fejob.getType();
                    NodeType nodetype = nodeTypeService.getNodeTypeObjForType(typeOfJob);
                    dbjob.setNodetype(nodetype);
                    dbjob.setWorkspace(dbWorkspace);
                    dbjob.setNameJobStep(fejob.getNameproperty().get());

                    jobService.createJob(dbjob);
                    System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Creating job " + dbjob.getNameJobStep() + " id: " + dbjob.getId());

                    fejob.setId(dbjob.getId());

                    //nodetype is set up during install.
                } else {
                        
                    dbjob = jobService.getJob(currentJobId);                                               // else get the instance of the previously saved g1Child
                    System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Fetched job " + dbjob.getNameJobStep() + " id: " + dbjob.getId());
                    dbjob.setNameJobStep(fejob.getNameproperty().get());
                    */
                    /*List<NodePropertyValue> npValues=nodePropertyValueService.getNodePropertyValuesFor(dbjob);
                    
                    List<JobModelProperty> jobproperties=fejob.getJobProperties();
                    for (Iterator<JobModelProperty> iterator = jobproperties.iterator(); iterator.hasNext();) {
                    JobModelProperty jobProperty = iterator.next();
                    for (Iterator<NodePropertyValue> iterator1 = npValues.iterator(); iterator1.hasNext();) {
                    NodePropertyValue npv = iterator1.next();
                    if(npv.getNodeProperty().getPropertyType().getName().equals(jobProperty.getPropertyName())){
                    System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Before updating found nodePropertyValues for type 4 with  "+npv.getIdNodePropertyValue()+" : job: "+npv.getJob().getNameJobStep()+" : nodeProperty-Node: "+npv.getNodeProperty().getNodeType().getActualnodeid()+" : nodeProperty-Property: "+npv.getNodeProperty().getPropertyType().getName()+" : value: "+npv.getValue());
                    npv.setValue(jobProperty.getPropertyValue());
                    nodePropertyValueService.updateNodePropertyValue(npv.getIdNodePropertyValue(), npv);
                    System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): After updating found nodePropertyValues for type 4 with  "+npv.getIdNodePropertyValue()+" : job: "+npv.getJob().getNameJobStep()+" : nodeProperty-Node: "+npv.getNodeProperty().getNodeType().getActualnodeid()+" : nodeProperty-Property: "+npv.getNodeProperty().getPropertyType().getName()+" : value: "+npv.getValue());
                    
                    }
                    }
                    
                    }*/
//
       //         }

                /**
                 * *
                 * Volumes in the job
                 */
                /*List<Volume0> fevolsinFejob = fejob.getVolumes();
                for (Volume0 vol : fevolsinFejob) {
                Volume dbVol;
                Long currentVolumeId = vol.getId();
                if (currentVolumeId == null) {
                dbVol = new Volume();
                dbVol.setVolumeType(vol.getType());
                dbVol.setNameVolume(vol.getName().get());
                dbVol.setPathOfVolume(vol.getVolume().getAbsolutePath());
                dbVol.setJob(dbjob);
                volumeService.createVolume(dbVol);
                System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): id is now " + dbVol.getId());
                vol.setId(dbVol.getId());
                } else {
                dbVol = volumeService.getVolume(vol.getId());
                }
                
                dbVolumesForCurrentJob.add(dbVol);
                
                }
                
                dbVolumes.addAll(dbVolumesForCurrentJob);
                
                dbjob.setVolumes(dbVolumesForCurrentJob);*/

                /**
                 * *
                 * QcMatrix for the job. QcMatrix are commited/updated straight
                 * to the db based on UI
              *
                 */
                /*  List<QcMatrixRow> qcmatrixForJob = qcMatrixRowService.getQcMatrixForJob(dbjob);
                dbjob.setQcmatrix(new HashSet<>(qcmatrixForJob));
                dbjobs.add(dbjob);
                }
                
                dbWorkspace.setJobs(dbjobs);*/

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
            
             for(Dot cause:dbDots) {
                 System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): updating dot: "+cause.getId());
                 dotService.updateDot(cause.getId(),cause);
             }   //create only attached ones.
              
             */
            /**
             * Setting up ancestors and descendants
             */
            
            /*
            for (JobType0Model job : jobsInWorkSpace) {
//              System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): List of ancestors for g1Child: "+g1Child.getId()%1000);
                Job dbjob = jobService.getJob(job.getId());    //the g1Child for which the ancestors are to be determined.

                System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Clearing the ancestor table for job: " + dbjob.getNameJobStep() + " id: " + dbjob.getId());
                ancestorService.clearTableForJob(dbjob);     //the table is rebuilt each time. so clear all ancestors belonging to this g1Child
                Set<Ancestor> dbAncestors = new HashSet<>();
                Set<JobType0Model> feAncestors = (Set<JobType0Model>) job.getAncestors();
                for (JobType0Model feAncestorJob : feAncestors) {
//                  System.out.println(g1Child.getId()%1000+" -A- "+feAncestorJob.getId()%1000);
                    System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Creating ancestor for job: " + dbjob.getNameJobStep() + " " + dbjob.getId() + " Ancestor: " + feAncestorJob.getNameproperty().get() + " " + feAncestorJob.getId());

                    Job desc = jobService.getJob(feAncestorJob.getId());
                    Ancestor ancestor = new Ancestor();
                    ancestor.setJob(dbjob);
                    ancestor.setAncestor(desc);
                    dbAncestors.add(ancestor);

                }
                dbjob.setAncestors(dbAncestors);             //add to the list of ancestors

                Set<Descendant> dbDescendants = new HashSet<>();
                System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Clearing the descendant table for job: " + dbjob.getNameJobStep() + " id: " + dbjob.getId());
//              System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): List of descendants for g1Child: "+g1Child.getId()%1000);
                Set<JobType0Model> feDescendants = (Set<JobType0Model>) job.getDescendants();
                descendantService.clearTableForJob(dbjob);    //the table is rebuilt each time. so clear all feDescendants belonging to this g1Child
                for (JobType0Model feDescendantJob : feDescendants) {
//                  System.out.println(g1Child.getId()%1000+" -D- "+feDescendantJob.getId()%1000);
                    System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Creating ancestor for job: " + dbjob.getNameJobStep() + " " + dbjob.getId() + " Ancestor: " + feDescendantJob.getNameproperty().get() + " " + feDescendantJob.getId());

                    Job des = jobService.getJob(feDescendantJob.getId());
                    Descendant descendant = new Descendant();
                    descendant.setJob(dbjob);
                    descendant.setDescendant(des);
                    dbDescendants.add(descendant);
                }
                dbjob.setDescendants(dbDescendants);          //add to the list of descendants

                for (Ancestor a : dbAncestors) {
                    ancestorService.addAncestor(a);
                }
                for (Descendant d : dbDescendants) {
                    descendantService.addDescendant(d);
                }

            }
            
            */

            /* for (Job dbjob : dbjobs) {
            System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): updating job " + dbjob.getId());
            
            jobService.updateJob(dbjob.getId(), dbjob);
            }
            for (Volume dbVol : dbVolumes) {
            volumeService.updateVolume(dbVol.getId(), dbVol);
            }
            
            workspaceService.updateWorkspace(dbWorkspace.getId(), dbWorkspace);
            */
            /*  }else {
            System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Workspace is missing a name. Unsaved!");
            }*/

    }

    private void loadSession() {
        Workspace dbWorkspace = workspaceService.getWorkspace(model.getId());

        //Set<Job> jobsInDb = dbWorkspace.getJobs();
        Set<Job> jobsInDb=new HashSet<>(jobService.getJobsInWorkspace(dbWorkspace));
        
        List<JobType0Model> frontEndJobModels = new ArrayList<>();

        Map<Long, JobType0Model> idFrontEndJobMap = new HashMap<>();              //used to link the nodes later. alook up map

        for (Job dbj : jobsInDb) {
            JobType0Model fejob = null;

            Long type = dbj.getNodetype().getActualnodeid();
            if (type.equals(JobType0Model.PROCESS_2D)) {
                fejob = new JobType1Model(model);
                fejob.setId(dbj.getId());
                fejob.setDatabaseJob(dbj);
                fejob.setNameproperty(dbj.getNameJobStep());
                fejob.setDepth(dbj.getDepth());

                System.out.println("fend.workspace.WorkspaceController.loadSession(): Added job: " + dbj.getNameJobStep());

            }
            if (type.equals(JobType0Model.SEGD_LOAD)) {
                fejob = new JobType2Model(model);
                fejob.setId(dbj.getId());
                 fejob.setDatabaseJob(dbj);
                fejob.setNameproperty(dbj.getNameJobStep());
                fejob.setDepth(dbj.getDepth());

                System.out.println("fend.workspace.WorkspaceController.loadSession(): Added job: " + dbj.getNameJobStep());

            }
            if (type.equals(JobType0Model.ACQUISITION)) {
                fejob = new JobType3Model(model);
                fejob.setId(dbj.getId());
                 fejob.setDatabaseJob(dbj);
                fejob.setNameproperty(dbj.getNameJobStep());
                fejob.setDepth(dbj.getDepth());

                System.out.println("fend.workspace.WorkspaceController.loadSession(): Added job: " + dbj.getNameJobStep());

            }
             if (type.equals(JobType0Model.TEXT)) {
                fejob = new JobType4Model(model);
                fejob.setId(dbj.getId());
                 fejob.setDatabaseJob(dbj);
                fejob.setNameproperty(dbj.getNameJobStep());
                fejob.setDepth(dbj.getDepth());

                System.out.println("fend.workspace.WorkspaceController.loadSession(): Added job: " + dbj.getNameJobStep());

            }

            Set<Volume> dbvols = new HashSet<>(volumeService.getVolumesForJob(dbj));
            
            List<Volume0> frontEndVolumeModels = new ArrayList<>();
            for (Volume dbv : dbvols) {
                Volume0 fevol = null;
                Long vtype = dbv.getVolumeType();

                if (vtype.equals(Volume0.PROCESS_2D)) {
                    fevol = new Volume1(fejob);                  //parent g1Child and id set in contructor

                    fevol.setId(dbv.getId());
                    fevol.setName(dbv.getNameVolume());
                    File volumeOnDisk = new File(dbv.getPathOfVolume());
                    fevol.setVolume(volumeOnDisk);
                    System.out.println("fend.workspace.WorkspaceController.loadSession(): Added Volume : " + dbv.getNameVolume() + " to job: " + dbj.getNameJobStep());
                }
                if (vtype.equals(Volume0.SEGD_LOAD)) {
                    fevol = new Volume2(fejob);                  //parent g1Child and id set in contructor

                    fevol.setId(dbv.getId());
                    fevol.setName(dbv.getNameVolume());
                    File volumeOnDisk = new File(dbv.getPathOfVolume());
                    fevol.setVolume(volumeOnDisk);
                    System.out.println("fend.workspace.WorkspaceController.loadSession(): Added Volume : " + dbv.getNameVolume() + " to job: " + dbj.getNameJobStep());
                }
                frontEndVolumeModels.add(fevol);

            }
            fejob.setVolumes(frontEndVolumeModels);

            idFrontEndJobMap.put(fejob.getId(), fejob);

            frontEndJobModels.add(fejob);
        }
        model.setObservableJobs(new HashSet<>(frontEndJobModels));       //front end jobs and volumes set in workspace model

        //Set<Dot> dots = dbWorkspace.getDots();
        Set<Dot> dots=new HashSet<>(dotService.getDotsInWorkspace(dbWorkspace));
        System.out.println("fend.workspace.WorkspaceController.loadSession(): the size of  dots retrieved : " + dots.size());
        List<DotModel> frontEndDotModels = new ArrayList<>();

        for (Dot dot : dots) {
            DotModel fedot = new DotModel(model);
            fedot.setDatabaseDot(dot);
            //Set<Link> links = dot.getLinks();
             //Set<Link> dbLinks=dbDot.getLinks();
            Set<Link> links=new HashSet<>(linkService.getLinksForDot(dot));
            System.out.println("fend.workspace.WorkspaceController.loadSession(): number of links sharing  dot: " + dot.getId() + " in state " + dot.getStatus() + " links.size(): " + links.size());
            fedot.setStatus(dot.getStatus());
            fedot.setId(dot.getId());
            Set<LinkModel> frontEndLinkModels = new HashSet<>();

            for (Link link : links) {

                Job parent = link.getParent();
                Job child = link.getChild();
                System.out.println("fend.workspace.WorkspaceController.loadSession(): adding parent from Link " + parent.getNameJobStep() + " id: " + parent.getId());
                System.out.println("fend.workspace.WorkspaceController.loadSession(): adding child from Link " + child.getNameJobStep() + " id: " + child.getId());
                JobType0Model feParent = idFrontEndJobMap.get(parent.getId());
                JobType0Model feChild = idFrontEndJobMap.get(child.getId());

                LinkModel felink = new LinkModel();
                felink.setParent(feParent);
                felink.setChild(feChild);
                fedot.createLink(feParent, feChild);
                frontEndLinkModels.add(felink);
            }
            fedot.setLinks(frontEndLinkModels);
            frontEndDotModels.add(fedot);
        }

        Map<Long, JobType0View> idJobViewsMap = inflateFrontEndViews();

        Set<EdgeModel> edges = new HashSet<>();
        for (DotModel fedot : frontEndDotModels) {
            System.out.println("fend.workspace.WorkspaceController.loadSession() for loop for feDot: id " + fedot.getId() + " in state " + fedot.getStatus().get());
            if (fedot.getStatus().get().equals(DotModel.NJS)) {                                     // if NJS.  then the one link will be a parentchildedge
                DotView dotview = null;
                Set<LinkModel> links = fedot.getLinks();
                for (LinkModel link : links) {

                    JobType0Model parent = link.getParent();
                    JobType0Model child = link.getChild();
                    ParentChildEdgeModel pcem = new ParentChildEdgeModel();
                    pcem.setParentJob(parent);
                    pcem.setChildJob(child);
                    pcem.setDotModel(fedot);

                    ParentChildEdgeView pcv = new ParentChildEdgeView(pcem, idJobViewsMap.get(parent.getId()), interactivePane);
                    pcv.getController().setChildJobView(idJobViewsMap.get(child.getId()), dotview);

                    edges.add(pcem);
                }
            }
            if (fedot.getStatus().get().equals(DotModel.JOIN)) {                                     // if JOIN.  Set the 1st encounter parent-child as a PCE. get the common dot(view).
                // next join to the dot as you would handle the case of a drop on the dotView.
                Set<LinkModel> links = fedot.getLinks();
                int count = 0;
                DotView dotview = null;
                for (LinkModel link : links) {
                    JobType0Model parent = link.getParent();
                    JobType0Model child = link.getChild();
                    ParentChildEdgeModel pcem = new ParentChildEdgeModel();
                    pcem.setParentJob(parent);
                    pcem.setChildJob(child);
                    pcem.setDotModel(fedot);

                    if (count == 0) {
                        System.out.println("fend.workspace.WorkspaceController.loadSession() count: " + count + " Befrore call dotview is " + (dotview == null ? "is Null" : " is new "));
                        ParentChildEdgeView pcv = new ParentChildEdgeView(pcem, idJobViewsMap.get(parent.getId()), interactivePane);
                        dotview = pcv.getController().setChildJobView(idJobViewsMap.get(child.getId()), null);     // creates the dot  and get it
                        System.out.println("fend.workspace.WorkspaceController.loadSession() count: " + count + " After call dotview is " + (dotview == null ? "is Null" : " is new "));
                    } else {
                        System.out.println("fend.workspace.WorkspaceController.loadSession() count: " + count + " Vefore call dotview is " + (dotview == null ? "is Null" : " is new "));
                        ParentChildEdgeView pcv = new ParentChildEdgeView(pcem, idJobViewsMap.get(parent.getId()), interactivePane);
                        pcv.getController().setChildJobView(idJobViewsMap.get(child.getId()), dotview);      //joins to the dot
                    }
                    count++;
                    edges.add(pcem);
                }

            }
            if (fedot.getStatus().get().equals(DotModel.SPLIT)) {                                     // if SPLIT.  then the one link is PCE the rest DJE
                Set<LinkModel> links = fedot.getLinks();
                int count = 0;
                DotView dotview = null;
                for (LinkModel link : links) {
                    JobType0Model parent = link.getParent();
                    JobType0Model child = link.getChild();
                    if (count == 0) {
                        ParentChildEdgeModel pcem = new ParentChildEdgeModel();
                        pcem.setParentJob(parent);
                        pcem.setChildJob(child);
                        pcem.setDotModel(fedot);
                        System.out.println("fend.workspace.WorkspaceController.loadSession() count: " + count + " Befrore call dotview is " + (dotview == null ? "is Null" : " is new "));
                        ParentChildEdgeView pcv = new ParentChildEdgeView(pcem, idJobViewsMap.get(parent.getId()), interactivePane);
                        dotview = pcv.getController().setChildJobView(idJobViewsMap.get(child.getId()), null);     // creates the dot  and get it
                        System.out.println("fend.workspace.WorkspaceController.loadSession() count: " + count + " After call dotview is " + (dotview == null ? "is Null" : " is new "));
                        edges.add(pcem);

                    } else {
                        DotJobEdgeModel djem = new DotJobEdgeModel();
                        djem.setDotModel(fedot);
                        djem.setChildJob(child);
                        DotJobEdgeView djev = new DotJobEdgeView(djem, dotview, interactivePane);
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

    private Map<Long, JobType0View> inflateFrontEndViews() {
        Set<JobType0Model> jobmodels = (Set<JobType0Model>) model.getObservableJobs();
        Set<EdgeModel> edgeModels = (Set<EdgeModel>) model.getObservableEdges();
        List<JobType0View> jobviews = new ArrayList<>();

        Map<Long, JobType0View> idFrontEndJobMap = new HashMap<>();              //used to link the nodes later. alook up map

        for (JobType0Model job : jobmodels) {
            if (job.getType().equals(JobType0Model.PROCESS_2D)) {
               
                JobType1View jv = new JobType1View((JobType1Model) job, interactivePane);
                   
                /**
                 * Attach Listeners to save workspace
                 */
                BooleanProperty changeProperty = new SimpleBooleanProperty(false);
                changeProperty.bind(job.getChangeProperty());
                changeProperty.addListener(workspaceChangedListener);
                changePropertyList.add(changeProperty);
                idFrontEndJobMap.put(job.getId(), jv);
                interactivePane.getChildren().add(jv);
                 System.out.println("fend.workspace.WorkspaceController.inflateFrontEndViews(): name: "+job.getNameproperty().get()+" y,x: "+jv.getHeight()+","+jv.getWidth()+jv.getBoundsInLocal().getHeight()+","+jv.getBoundsInLocal().getMaxX()+","+jv.getBoundsInParent().getMaxX()+","+jv.getLayoutBounds().getMaxX());
            }
            if (job.getType().equals(JobType0Model.SEGD_LOAD)) {
                JobType2View jv = new JobType2View((JobType2Model) job, interactivePane);
                   
                /**
                 * Attach Listeners to save workspace
                 */
                BooleanProperty changeProperty = new SimpleBooleanProperty(false);
                changeProperty.bind(job.getChangeProperty());
                changeProperty.addListener(workspaceChangedListener);
                changePropertyList.add(changeProperty);
                idFrontEndJobMap.put(job.getId(), jv);
                interactivePane.getChildren().add(jv);
                 System.out.println("fend.workspace.WorkspaceController.inflateFrontEndViews(): name: "+job.getNameproperty().get()+" y,x: "+jv.getHeight()+","+jv.getWidth()+jv.getBoundsInLocal().getHeight()+","+jv.getBoundsInLocal().getMaxX()+","+jv.getBoundsInParent().getMaxX()+","+jv.getLayoutBounds().getMaxX());
            }
            if (job.getType().equals(JobType0Model.ACQUISITION)) {
                JobType3View jv = new JobType3View((JobType3Model) job, interactivePane);
                   
                /**
                 * Attach Listeners to save workspace
                 */
                BooleanProperty changeProperty = new SimpleBooleanProperty(false);
                changeProperty.bind(job.getChangeProperty());
                changeProperty.addListener(workspaceChangedListener);
                changePropertyList.add(changeProperty);
                idFrontEndJobMap.put(job.getId(), jv);
                interactivePane.getChildren().add(jv);
                 System.out.println("fend.workspace.WorkspaceController.inflateFrontEndViews(): name: "+job.getNameproperty().get()+" y,x: "+jv.getHeight()+","+jv.getWidth()+jv.getBoundsInLocal().getHeight()+","+jv.getBoundsInLocal().getMaxX()+","+jv.getBoundsInParent().getMaxX()+","+jv.getLayoutBounds().getMaxX());
            }
             if (job.getType().equals(JobType0Model.TEXT)) {
                JobType4View jv = new JobType4View((JobType4Model) job, interactivePane);
                   
                /**
                 * Attach Listeners to save workspace
                 */
                BooleanProperty changeProperty = new SimpleBooleanProperty(false);
                changeProperty.bind(job.getChangeProperty());
                changeProperty.addListener(workspaceChangedListener);
                changePropertyList.add(changeProperty);
                idFrontEndJobMap.put(job.getId(), jv);
                interactivePane.getChildren().add(jv);
                 System.out.println("fend.workspace.WorkspaceController.inflateFrontEndViews(): name: "+job.getNameproperty().get()+" y,x: "+jv.getHeight()+","+jv.getWidth()+jv.getBoundsInLocal().getHeight()+","+jv.getBoundsInLocal().getMaxX()+","+jv.getBoundsInParent().getMaxX()+","+jv.getLayoutBounds().getMaxX());
            }
            
        }
        return idFrontEndJobMap;
    }

    public void setLoading(boolean b) {
        loadingProperty.set(b);
    }

    /**
     * *
     * Listeners
     *
     *
     */
    private ChangeListener<Boolean> workspaceChangedListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (model.DEBUG) {
                System.out.println("Saving workspace");
            }
            saveWorkspace();
        }
    };

    private ChangeListener<Boolean> loadingListener = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue) {
                loadSession();
            }
        }
    };

    private ListChangeListener<Node> jobLinkChangeListener = new ListChangeListener<Node>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends Node> c) {
            while (c.next()) {
                for (Node node : c.getAddedSubList()) {
                    if (node instanceof JobType0View) {
                        System.out.println(".onChanged(): new job added to workspace: ");
                        model.getObservableJobs().add(((JobType0View) node).getController().getModel());
                    }

                    if (node instanceof EdgeView) {
                        model.getObservableEdges().add(((EdgeView) node).getController().getModel());
                        System.out.println(".onChanged() new edge was added to the workspace " + ((EdgeView) node).getController().getModel().getId() % 1000 + " size of set: " + model.getObservableEdges().size());

                    }

                    if (node instanceof DotView) {
                        System.out.println(".onChanged() new Dot was added to the workspace");
                    }
                }
            }
        }
    };

    /**
     * *
     * Get all insight versions from the folder
     */
    final private String INSIGHT_REGEX = "\\d*.\\d*-[\\w_-]*";     //match cause.cause-xxxxxx, cause.cause-xxxxx-ABC , cause.cause-xxxxxxx-ABC_mno, cause.cause-xxxxxx_mno
    final private Pattern pattern = Pattern.compile(INSIGHT_REGEX);
    final private FileFilter insightFilter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return pattern.matcher(pathname.getName()).matches();
        }

    };

    public void summarizeInMemory() throws Exception{
       
        dbWorkspace=model.getWorkspace();
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" fetching existing summaries for workspace : "+dbWorkspace.getId());
        List<Summary> existingSummariesForWorkspace=summaryService.getSummariesFor(dbWorkspace);
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" retrieved "+existingSummariesForWorkspace.size()+ " summaries");
        
        
        
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" fetching existing doubts for workspace : "+dbWorkspace.getId());
        
        List<Doubt> existingDoubtsExceptInheritanceInWorkspace=doubtService.getAllDoubtsExceptInheritanceFor(dbWorkspace);
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" retrieved "+existingDoubtsExceptInheritanceInWorkspace.size()+ " doubts");
        
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" fetching existing inheritance doubts for workspace : "+dbWorkspace.getId());
        
        List<Doubt> inheritanceDoubtsInWorkspace=doubtService.getAllDoubtsJobsAndSubsurfacesFor(dbWorkspace, doubtTypeInherit);
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" retrieved "+inheritanceDoubtsInWorkspace.size()+ " inheritance doubts");
        
        doubtDoubtStatusMap.clear();
        causeAndAssociatedInheritanceMap.clear();
        inheritanceMap.clear(); 
        summaryMap.clear(); 
        causeDoubtMap.clear();
        newSummaries.clear();
        summariesToBeUpdated.clear();
        causes.clear();
        doubtsToBeUpdated.clear();
        doubtStatusForCause.clear();
        doubtStatusToBeUpdated.clear();
        inheritedDoubts.clear();
        inheritedDoubtStatus.clear();
        subsurfaceLinkMap.clear();
        headerMap.clear();
        subsurfaceJobSummaryTimeMap.clear();
        idsOfCausalDoubtsToBeDeleted.clear();
        idsOfDoubtStatusToBeDeleted.clear();
        idsOfInheritedDoubtsToBeDeleted.clear();
        
        ancestorMapForSummary.clear();
        descendantMapForSummary.clear();
        variableArgumentMap.clear();
        
        
        for (Summary summary : existingSummariesForWorkspace) {
          SummaryKey key=new SummaryKey();
          key.subsurface=summary.getSubsurface();
          key.job=summary.getJob();
          
          summaryMap.put(key, summary);
        }
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory(): built "+summaryMap.size()+" summary Map elements");
        
        for (Doubt doubt : existingDoubtsExceptInheritanceInWorkspace) {
            DoubtKey key=new DoubtKey();
            key.subsurface=doubt.getSubsurface();
            key.job=doubt.getChildJob();
            key.dot=doubt.getDot();
            key.doubtType=doubt.getDoubtType();
            
            causeDoubtMap.put(key, doubt);
        }
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory(): built "+causeDoubtMap.size()+" cause Doubt Map elements");
        
        for(Doubt inheritedDoubt : inheritanceDoubtsInWorkspace){
            InheritanceKey key=new InheritanceKey();
            key.job=inheritedDoubt.getChildJob();
            key.subsurface=inheritedDoubt.getSubsurface();
            key.cause=inheritedDoubt.getDoubtCause();
            
            inheritanceMap.put(key, inheritedDoubt);
            Doubt cause=inheritedDoubt.getDoubtCause();
            if(!causeAndAssociatedInheritanceMap.containsKey(cause)){
                causeAndAssociatedInheritanceMap.put(cause, new ArrayList<Doubt>());
                causeAndAssociatedInheritanceMap.get(cause).add(inheritedDoubt);
            }else{
                causeAndAssociatedInheritanceMap.get(cause).add(inheritedDoubt);
            }
        }
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Retrieving all subs to summarize");
        List<Object[]> elementsToSummarize=linkService.getSubsurfaceAndLinksForSummary(dbWorkspace);
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Retrieved "+elementsToSummarize.size()+" elements to summarize");
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Building the elements map");
        
        for(Object[] element: elementsToSummarize){
            SubsurfaceJob sj=(SubsurfaceJob) element[1];
            Link value=(Link) element[0];
            Subsurface key=sj.getSubsurface(); 
            if(!subsurfaceLinkMap.containsKey(key)){
                subsurfaceLinkMap.put(key, new HashSet<>());
                subsurfaceLinkMap.get(key).add(value);
            }else{
                subsurfaceLinkMap.get(key).add(value);
            }
            subsurfaceJobSummaryTimeMap.put(generateSubsurfaceJobKey(sj.getJob(), sj.getSubsurface()), sj);
        }
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Retrieving all doubtstatus for this workspace");
        List<DoubtStatus> doubstatusForWorkspace = doubtStatusService.getAllDoubtStatusInWorkspace(dbWorkspace);
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Retrieved "+doubstatusForWorkspace.size()+" doubtstatus for this workspace");
        
        for(DoubtStatus doubtStatus:doubstatusForWorkspace){
            Doubt key=doubtStatus.getDoubt();
            if(!doubtDoubtStatusMap.containsKey(key)){
                doubtDoubtStatusMap.put(key, new ArrayList<DoubtStatus>());
                doubtDoubtStatusMap.get(key).add(doubtStatus);
            }else{
                doubtDoubtStatusMap.get(key).add(doubtStatus);
            }
        }
        
        
        
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Finished Building the map of elements of size: "+subsurfaceLinkMap.values().size());
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Retrieving all headers in workspace");
        List<Header> headersInWorkspace=headerService.getChosenHeadersForWorkspace(dbWorkspace);
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Retrieved "+headersInWorkspace.size()+" headers for workspace");
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Building the header map");
        for(Header h:headersInWorkspace){
            HeaderKey headerKey=new HeaderKey();
            headerKey.job=h.getJob();
            headerKey.subsurface=h.getSubsurface();
            
            headerMap.put(headerKey, h);
        }
         System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Finished Building the map of subs of size: "+headerMap.size());
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory(): size of the subsurfaceLinkMap: "+subsurfaceLinkMap.values().size());
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory(): size of the headerMap        : "+headerMap.size());
        
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Fetching the list of ancestors for summary" );
        List<Object[]> ancestorsForSummary=ancestorService.getAncestorsSubsurfaceJobsForSummary(dbWorkspace);
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Fetched "+ancestorsForSummary.size()+" ancestors for summary");
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Fetching the list of descendants for summary" );
        List<Object[]> descendantsForSummary=descendantService.getDescendantsSubsurfaceJobsForSummary(dbWorkspace);
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Fetched "+descendantsForSummary.size()+" descendants for summary");
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Populating the ancestor Map");
        for(Object[] ancestorForSummary : ancestorsForSummary){
            SubsurfaceJob sj = (SubsurfaceJob) ancestorForSummary[1];
            Job childJob=(Job) ancestorForSummary[2];
            Ancestor anc=(Ancestor) ancestorForSummary[0];
            AncestorKey key = generateAncestorKey(childJob, sj.getSubsurface());
            if(!ancestorMapForSummary.containsKey(key)){
                ancestorMapForSummary.put(key, new ArrayList<Ancestor>());
                ancestorMapForSummary.get(key).add(anc);
            }else{
                ancestorMapForSummary.get(key).add(anc);
            }
        }
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" populated the ancestor Map");
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Populating the Descendant Map");
        for(Object[] descendantForSummary  : descendantsForSummary){
            SubsurfaceJob sj = (SubsurfaceJob) descendantForSummary[1];
            Job parentJob=(Job)descendantForSummary[2];
            Descendant desc=(Descendant) descendantForSummary[0];
            DescendantKey key = generateDescendantKey(parentJob, sj.getSubsurface());
            if(!descendantMapForSummary.containsKey(key)){
                descendantMapForSummary.put(key, new ArrayList<Descendant>());
                descendantMapForSummary.get(key).add(desc);
            }else{
                descendantMapForSummary.get(key).add(desc);
            }
        }
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" Populated the Descendant Map with "+descendantMapForSummary.values().size()+" entries");
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" fetching all the variable - arguments for this workspace" );
        List<VariableArgument> variableArgumentsForWorkspace=variableArgumentService.getVariableArgumentsForWorkspace(dbWorkspace);
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" fetched "+variableArgumentsForWorkspace.size()+" variable - arguments for this workspace" );
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : "+timeNow()+" building the variableArguments Map");
        for(VariableArgument va: variableArgumentsForWorkspace){
            if(!variableArgumentMap.containsKey(va.getDot())){
                variableArgumentMap.put(va.getDot(), new ArrayList<VariableArgument>());
                variableArgumentMap.get(va.getDot()).add(va);
            }else{
                variableArgumentMap.get(va.getDot()).add(va);
            }
                
            
        }
        
       
       // List<Subsurface> subsurfaceList = subsurfaceService.getSubsurfaceList();
      
      
        List<Callable<String>> tasks=new ArrayList<>();
        
        execService=Executors.newFixedThreadPool(processorsUsed());
        
        
         String latestSummaryTime=subsurfaceJobService.getLatestSummaryTime();
         for (Map.Entry<Subsurface, Set<Link>> entry : subsurfaceLinkMap.entrySet()) {
            Subsurface subb = entry.getKey();
            Set<Link> links = entry.getValue();
            
             Callable<String> summaryTask=new Callable<String>(){
                @Override
                public String call() throws Exception {

             Map<String, Double> mapForVariableSetting = new HashMap<>();
        Set<String> variableSet = new HashSet<>();
        Set<Job> argumentSet = new HashSet<>();
            
            for(Link l:links)
            
              {
                System.out.println("fend.workspace.WorkspaceController.summarize(): inside Link  "+l.getId()+" for sub: "+subb.getSubsurface());
                Summary summary;
                //if ((summaryforCurrentJob = summaryService.getSummaryFor(subb.getSequence(), l.getParent())) == null) {            //create an entry for parent summaryforCurrentJob
                //if ((summary = summaryService.getSummaryFor(subb, l.getParent())) == null) {            //create an entry for parent summaryforCurrentJob    
                             SummaryKey summaryKey=generateSummaryKey(subb, l.getParent());
                                    if(!summaryMap.containsKey(summaryKey)){
                                        summary = new Summary();
                                        summary.setSequence(subb.getSequence());
                                        summary.setSubsurface(subb);
                                        summary.setJob(l.getParent());
                                        summary.setWorkspace(dbWorkspace);
                                        System.out.println("fend.workspace.WorkspaceController.summarize(): "+timeNow()+"    Creating summary for "+summary.getId());
                                        //summaryService.createSummary(summary);
                                        newSummaries.add(summary);
                                        System.out.println("fend.workspace.WorkspaceController.summarize(): "+timeNow()+"    Created summary for "+summary.getId());
                                    }
                //if ((summaryforCurrentJob = summaryService.getSummaryFor(subb.getSequence(), l.getChild())) == null) {             //create an entry for child summaryforCurrentJob 
                //if ((summary = summaryService.getSummaryFor(subb, l.getChild())) == null) {             //create an entry for child summaryforCurrentJob 
                            summaryKey=generateSummaryKey(subb, l.getChild());
                                    if(!summaryMap.containsKey(summaryKey)){
                                        summary = new Summary();
                                        summary.setSequence(subb.getSequence());
                                        summary.setSubsurface(subb);
                                        summary.setJob(l.getChild());
                                        summary.setWorkspace(dbWorkspace);
                                         System.out.println("fend.workspace.WorkspaceController.summarize(): "+timeNow()+"    Creating summary for "+summary.getId());
                                        //summaryService.createSummary(summary);
                                        newSummaries.add(summary);
                                        System.out.println("fend.workspace.WorkspaceController.summarize(): "+timeNow()+"    Created summary for "+summary.getId());
                                    }else{
                                        summary=summaryMap.get(summaryKey);
                                    }

               /**
                * Inherit all of the ancestors doubts if this is a new Link which was never summarized earlier
                */
                BigInteger creationTime=new BigInteger(l.getCreationTime());
                BigInteger latestSummaryT=new BigInteger(latestSummaryTime);
                if(creationTime.compareTo(latestSummaryT)==1){     //creation time is greater than the epoch summaryTime
                   // List<Ancestor> ancestorsThatContainSub=ancestorService.getAncestorsForJobContainingSub(l.getChild(), subb);
                    AncestorKey ancestorKey=generateAncestorKey(l.getChild(), subb);
                     List<Ancestor> ancestorsThatContainSub=new ArrayList<>();
                    if(ancestorMapForSummary.containsKey(ancestorKey)){
                       ancestorsThatContainSub=ancestorMapForSummary.get(ancestorKey);
                        
                    }
                            inheritDoubtFromAncestors(l,subb, ancestorsThatContainSub);
                    
                    
                }
                
                //dependency
                System.out.println("fend.workspace.WorkspaceController.summarize(): "+timeNow()+"   Getting descendants from the map");
               //List<Descendant> descendantsThatContainSub=descendantService.getDescendantsForJobContainingSub(l.getChild(), subb);
               
               DescendantKey descendantKey=generateDescendantKey(l.getChild(), subb);
                List<Descendant> descendantsThatContainSub=new ArrayList<>();
                if(descendantMapForSummary.containsKey(descendantKey)){
                    descendantsThatContainSub=descendantMapForSummary.get(descendantKey);
                }
                
//                System.out.println("fend.workspace.WorkspaceController.summarize(): "+timeNow()+"   Got descendants");
                System.out.println(".call(): size of descendants of job: id: "+l.getChild().getId()+" name: "+l.getChild().getNameJobStep()+" that contain: "+subb.getSubsurface()+" == "+descendantsThatContainSub.size());
                Dot dot = l.getDot();                     //the dot to which the link belongs\\
               
                    checkForDependencyDoubts(l,dot,mapForVariableSetting,variableSet,argumentSet,subb,summary,descendantsThatContainSub);
                   // checkForQcDoubts(l,dot,subb,summary,descendantsThatContainSub);
                //    checkForTimeDoubts(l, dot, subb, summary,descendantsThatContainSub);
                    
                //summaryService.updateSummary(summary.getId(), summary);
                summariesToBeUpdated.add(summary);
                
                
                
            }
            
                return "Finished Summarizing sub : "+subb.getSubsurface()+" @ "+timeNow();
                }
            };
            System.out.println("fend.workspace.WorkspaceController.summarizeInMemory(): Task made for "+subb.getSubsurface());
            tasks.add(summaryTask);
            
            
            
        }
        
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory(): waiting on threads to finish");
        
        
        try {
                     List<Future<String>> futures=execService.invokeAll(tasks);
                     for(Future<String> future:futures){
                         System.out.println("future.get: "+future.get());
                     }
                     execService.shutdown();

                 } catch (InterruptedException ex) {
                     Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (ExecutionException ex) {
                     Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
                 }
        
        
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory(): "+timeNow()+"ENDED in memory operations. moving to datbase commits");
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory(): size of lists: ");
                System.out.println("        summaries to be created: "+newSummaries.size());
                System.out.println("        summaries to be updated: "+summariesToBeUpdated.size());
                System.out.println("        doubts    to be created: "+causes.size());
                System.out.println("inherited  doubts to be created: "+inheritedDoubts.size());
                System.out.println("           doubts to be updated: "+doubtsToBeUpdated.size());
                System.out.println("       doubstatus to be created: "+doubtStatusForCause.size());
                System.out.println("       doubstatus to be updated: "+doubtStatusToBeUpdated.size()); 
                System.out.println("       doubstatus to be deleted: "+idsOfDoubtStatusToBeDeleted.size()); 
                System.out.println("           doubts to be deleted: "+idsOfCausalDoubtsToBeDeleted.size());
                System.out.println("inherited  doubts to be deleted: "+idsOfInheritedDoubtsToBeDeleted.size());
        
                //to be performed in background thread
                
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Creating "+newSummaries.size()+" summaries");
                summaryService.createBulkSummaries(newSummaries);
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Created "+newSummaries.size()+" summaries");
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Updating "+summariesToBeUpdated.size()+" summaries");
                summaryService.updateBulkSummaries(summariesToBeUpdated);
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Updated "+summariesToBeUpdated.size()+" summaries");
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Creating "+causes.size()+" doubts");
                doubtService.createBulkDoubts(causes);
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Created "+causes.size()+" doubts");
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Creating "+inheritedDoubts.size()+" Inherited doubts");
                doubtService.createBulkDoubts(inheritedDoubts);
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Created "+inheritedDoubts.size()+" Inherited doubts");
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Updating "+doubtsToBeUpdated.size()+"  doubts");
                doubtService.updateBulkDoubts(doubtsToBeUpdated);
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Updated "+doubtsToBeUpdated.size()+"  doubts");
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Creating "+doubtStatusForCause.size()+"  doubt status for cause");
                doubtStatusService.createBulkDoubtStatus(doubtStatusForCause);
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Created "+doubtStatusForCause.size()+"  doubt status for cause");
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Creating "+inheritedDoubtStatus.size()+"  doubt status for inheritance");
                doubtStatusService.createBulkDoubtStatus(inheritedDoubtStatus);
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Created "+inheritedDoubtStatus.size()+"  doubt status for inheritance");
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Updating "+doubtStatusToBeUpdated.size()+"  doubt statuses");
                doubtStatusService.updateBulkDoubtStatus(doubtStatusToBeUpdated);
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Updated "+doubtStatusToBeUpdated.size()+"  doubt statuses");
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Deleting "+idsOfDoubtStatusToBeDeleted.size()+"  doubt statuses belonging to cured conditions");
                doubtStatusService.deleteBulkDoubtStatus(idsOfDoubtStatusToBeDeleted);
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Deleted "+idsOfDoubtStatusToBeDeleted.size()+"  doubt statuses belonging to cured conditions");
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Deleting "+idsOfInheritedDoubtsToBeDeleted.size()+" inherited doubts belonging to absent causes");
                doubtService.deleteBulkDoubts(idsOfInheritedDoubtsToBeDeleted);
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Deleted "+idsOfInheritedDoubtsToBeDeleted.size()+" inherited doubts belonging to absent causes");
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Deleting "+idsOfCausalDoubtsToBeDeleted.size()+" absent doubts");
                doubtService.deleteBulkDoubts(idsOfCausalDoubtsToBeDeleted);
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Deleted "+idsOfCausalDoubtsToBeDeleted.size()+" absent doubts");
                
                List<SubsurfaceJob> subsurfaceJobsToBeUpdated=new ArrayList<SubsurfaceJob>(subsurfaceJobSummaryTimeMap.values());
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Updating the summary times for the "+subsurfaceJobsToBeUpdated.size()+" subsurfaces that were queried");
                
                subsurfaceJobService.updateBulkSubsurfaceJobs(subsurfaceJobsToBeUpdated);
                System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() "+timeNow()+" Updated the summary times for "+subsurfaceJobsToBeUpdated.size()+" entries that were queried");
                
                
    };
    
    
    
    public void summarize() throws Exception{
        
        
      
        Map<String, Double> mapForVariableSetting = new HashMap<>();
        Set<String> variableSet = new HashSet<>();
        Set<Job> argumentSet = new HashSet<>();
        List<Subsurface> subsurfaceList = subsurfaceService.getSubsurfaceList();
       // dbWorkspace = workspaceService.getWorkspace(dbWorkspace.getId());
        dbWorkspace=model.getWorkspace();
         Map<Job,List<Subsurface>> mapForSummary=subsurfaceJobService.getSubsurfaceJobsForSummary();   //all entries where updateTime>summaryTime
         String latestSummaryTime=subsurfaceJobService.getLatestSummaryTime();
         
         
         
         
         
         
        for (Subsurface subb : subsurfaceList) {
            System.out.println("fend.workspace.WorkspaceController.getSummary(): Loop for subsurface : " + subb.getSubsurface());
            //Set<Link> linkNodesContainingSub = linkService.getLinksContainingSubsurface(subb, dbWorkspace,mapForSummary);                  //all links where both the parent and the child contains sub subb
             List<Link> linkNodesContainingSub = linkService.getSummaryLinksForSubsurfaceInWorkspace(dbWorkspace, subb);
            for (Link l : linkNodesContainingSub) {
                System.out.println("fend.workspace.WorkspaceController.summarize(): inside Link  "+l.getId()+" for sub: "+subb.getSubsurface());
                Summary summary;
                //if ((summaryforCurrentJob = summaryService.getSummaryFor(subb.getSequence(), l.getParent())) == null) {            //create an entry for parent summaryforCurrentJob
                if ((summary = summaryService.getSummaryFor(subb, l.getParent())) == null) {            //create an entry for parent summaryforCurrentJob    
                    summary = new Summary();
                    summary.setSequence(subb.getSequence());
                    summary.setSubsurface(subb);
                    summary.setJob(l.getParent());
                    summary.setWorkspace(dbWorkspace);
                    System.out.println("fend.workspace.WorkspaceController.summarize(): "+timeNow()+"    Creating summary for "+summary.getId());
                    summaryService.createSummary(summary);
                    System.out.println("fend.workspace.WorkspaceController.summarize(): "+timeNow()+"    Created summary for "+summary.getId());
                }
                //if ((summaryforCurrentJob = summaryService.getSummaryFor(subb.getSequence(), l.getChild())) == null) {             //create an entry for child summaryforCurrentJob 
                if ((summary = summaryService.getSummaryFor(subb, l.getChild())) == null) {             //create an entry for child summaryforCurrentJob 
                    summary = new Summary();
                    summary.setSequence(subb.getSequence());
                    summary.setSubsurface(subb);
                    summary.setJob(l.getChild());
                    summary.setWorkspace(dbWorkspace);
                     System.out.println("fend.workspace.WorkspaceController.summarize(): "+timeNow()+"    Creating summary for "+summary.getId());
                    summaryService.createSummary(summary);
                    System.out.println("fend.workspace.WorkspaceController.summarize(): "+timeNow()+"    Created summary for "+summary.getId());
                }

               /**
                * Inherit all of the ancestors doubts if this is a new Link which was never summarized earlier
                */
                BigInteger creationTime=new BigInteger(l.getCreationTime());
                BigInteger latestSummaryT=new BigInteger(latestSummaryTime);
                if(creationTime.compareTo(latestSummaryT)==1){     //creation time is greater than the epoch summaryTime
                    List<Ancestor> ancestorsThatContainSub=ancestorService.getAncestorsForJobContainingSub(l.getChild(), subb);
                            
                    inheritDoubtFromAncestors(l,subb, ancestorsThatContainSub);
                }
                
                //dependency
                System.out.println("fend.workspace.WorkspaceController.summarize(): "+timeNow()+"   Getting descendants");
               List<Descendant> descendantsThatContainSub=descendantService.getDescendantsForJobContainingSub(l.getChild(), subb);
                System.out.println("fend.workspace.WorkspaceController.summarize(): "+timeNow()+"   Got descendants");
                System.out.println(".call(): size of descendants of job: id: "+l.getChild().getId()+" name: "+l.getChild().getNameJobStep()+" that contain: "+subb.getSubsurface()+" == "+descendantsThatContainSub.size());
                Dot dot = l.getDot();                     //the dot to which the link belongs\\
               
                    checkForDependencyDoubts(l,dot,mapForVariableSetting,variableSet,argumentSet,subb,summary,descendantsThatContainSub);
                    //checkForQcDoubts(l,dot,subb,summary,descendantsThatContainSub);
                    checkForTimeDoubts(l, dot, subb, summary,descendantsThatContainSub);
                    
                summaryService.updateSummary(summary.getId(), summary);
            }
        }

        
    }

    private void checkForTimeDoubts(Link l,Dot dot,Subsurface subb,Summary summary,List<Descendant> descendantsThatContainSub) throws Exception {
        
        
                System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): for  "+l.getParent().getNameJobStep()+"<----->"+l.getChild().getNameJobStep()+" for sub: "+subb.getSubsurface());
        
                Job hparent = l.getParent();
                Job hchild = l.getChild();
                HeaderKey parentKey=generateHeaderKey(hparent, subb);
                HeaderKey childKey=generateHeaderKey(hchild, subb);
                /*Header hp = headerService.getChosenHeaderFor(hparent, subb);
                Header hc = headerService.getChosenHeaderFor(hchild, subb);*/
                Header hp=headerMap.get(parentKey);
                Header hc=headerMap.get(childKey);
                Long hpt = Long.valueOf(hp.getTimeStamp());
                Long hct = Long.valueOf(hc.getTimeStamp());
                if (hpt >= hct) {             //if parent is created after the child. creat a doubt in the child
                    Doubt doubt;
                    DoubtKey key=generateDoubtKey(subb, hchild, dot, doubtTypeTime);
                    //if ((doubt = doubtService.getDoubtFor(subb, hchild, dot, doubtTypeTime)) == null) {
                    if(!causeDoubtMap.containsKey(key)){
                        System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): doubtkey NOT found for "+subb.getId()+" job: "+hchild.getId()+" dot: "+dot.getId()+ " type: "+doubtTypeTime.getName());
                        doubt = new Doubt();
                        doubt.setChildJob(hchild);
                        doubt.setDot(dot);
                        doubt.setLink(l);
                        doubt.setSubsurface(subb);
                        doubt.setSequence(subb.getSequence());
                        doubt.setDoubtType(doubtTypeTime);
                        //doubt.setUser(user);
                      //  doubtService.createDoubt(doubt);
                        causes.add(doubt);
                        summary.setTimeSummary(true);
                        DoubtStatus doubtStatus = new DoubtStatus();
                        doubtStatus.setReason(DoubtStatusModel.getNewDoubtTimeMessage(hparent.getNameJobStep(), new String(hpt + ""), hchild.getNameJobStep(), new String(hct + ""), subb.getSubsurface(), doubtTypeTime.getName()));
                        doubtStatus.setDoubt(doubt);
                        doubtStatus.setStatus(DoubtStatusModel.YES);
                        doubtStatus.setState(DoubtStatusModel.ERROR);
                        doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                        //doubtStatus.setUser(user);
                       // doubtStatusService.createDoubtStatus(doubtStatus);
                       doubtStatusForCause.add(doubtStatus);
                        doubt.addToDoubtStatuses(doubtStatus);
                       // doubtService.updateDoubt(doubt.getId(), doubt);
                       //doubtsToBeUpdated.add(doubt);
                        passingDoubtToDescendants(l,dot,subb,doubt,descendantsThatContainSub);
                      //  summariesToBeUpdated.add(summary);
                    } else {
                        System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): doubtkey FOUND for "+subb.getId()+" job: "+hchild.getId()+" dot: "+dot.getId()+ " type: "+doubtTypeTime.getName());
                        doubt=causeDoubtMap.get(key);
                        passingDoubtToDescendants(l,dot,subb,doubt,descendantsThatContainSub);
                        //Set<DoubtStatus> doubtStatuses = doubt.getDoubtStatuses();
                        //Set<DoubtStatus> doubtStatuses=new HashSet<>(doubtStatusService.getDoubtStatusForDoubt(doubt));
                        Set<DoubtStatus> doubtStatuses=new HashSet<>();
                            if(doubtDoubtStatusMap.containsKey(doubt)){
                                doubtStatuses=new HashSet<>(doubtDoubtStatusMap.get(doubt));
                            }
                        summary.setTimeSummary(true);
                        for (DoubtStatus d : doubtStatuses) {
                            System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: " + doubt.getId() + " " + d.getStatus() + " comment: " + d.getReason() + " time: " + d.getTimeStamp());
                        }
                      //  summariesToBeUpdated.add(summary);
                    }

                } else {
                    //do nothing
                    System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts()");
                    System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts() "+l.getParent().getNameJobStep()+ "<----->"+l.getChild().getNameJobStep()+" has passed the check for time. Checking for any existing doubt entries..so as to clear them");
                    Doubt existingDoubt;
                    DoubtKey key=generateDoubtKey(subb, hchild, dot, doubtTypeTime);
                    //if((existingDoubt=doubtService.getDoubtFor(subb, l.getChild(), dot, doubtTypeTime))==null){
                    if(!causeDoubtMap.containsKey(key)){
                        //do nothing ..as no doubt exists
                        System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): No doubts exist for job: "+l.getChild().getNameJobStep()+" sub: "+subb.getSubsurface()+" for type: "+doubtTypeTime.getName());
                    }else{
                        //get all doubtStatus for the existing doubt
                        existingDoubt=causeDoubtMap.get(key);
                        System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): found an existing doubt to a previous failed condition: id. "+existingDoubt.getId());
                        //Set<DoubtStatus> doubtStatus=existingDoubt.getDoubtStatuses();                                                   //level O-2
                       // Set<DoubtStatus> doubtStatus=new HashSet<>(doubtStatusService.getDoubtStatusForDoubt(existingDoubt));
                       Set<DoubtStatus> doubtStatus=new HashSet<>();
                            if(doubtDoubtStatusMap.containsKey(existingDoubt)){
                                doubtStatus=new HashSet<>(doubtDoubtStatusMap.get(existingDoubt));
                            }
                        for(DoubtStatus doubtStat:doubtStatus){
                            System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): deleting doubtstatus: "+doubtStat.getId() );
                         //   doubtStatusService.deleteDoubtStatus(doubtStat.getId());
                         idsOfDoubtStatusToBeDeleted.add(doubtStat.getId());
                        }
                        System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): all doubtstatus messages related to "+existingDoubt.getId()+" have now been deleted.");
                        System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): Checking to see if there were any inherited doubts related to doubt id: "+existingDoubt.getId());
                        
                       // Set<Doubt> inheritedDoubts=existingDoubt.getInheritedDoubts();
                       List<Doubt> inheritedDoubts=new ArrayList<>();
                       if(causeAndAssociatedInheritanceMap.containsKey(existingDoubt)){
                           inheritedDoubts=causeAndAssociatedInheritanceMap.get(existingDoubt);
                       }
                              
                        for(Doubt inheritedDoubt:inheritedDoubts){
                            System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): found inherited doubt id: "+inheritedDoubt.getId()+" with existingCause Id: "+existingDoubt.getId());
                            
                            //Set<DoubtStatus> doubtStatuses=inheritedDoubt.getDoubtStatuses();
                            //Set<DoubtStatus> doubtStatuses=new HashSet<>(doubtStatusService.getDoubtStatusForDoubt(inheritedDoubt));
                            Set<DoubtStatus> inheritedDoubtStatus=new HashSet<>();
                            if(doubtDoubtStatusMap.containsKey(inheritedDoubt)){
                                inheritedDoubtStatus=new HashSet<>(doubtDoubtStatusMap.get(inheritedDoubt));
                            }
                                for(DoubtStatus inhdbt:inheritedDoubtStatus){
                                    System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): found doubtstatus id: "+inhdbt.getId()+" related to the inherited doubt :"+inheritedDoubt.getId()+""
                                            + "\nDeleting doubtstatus: "+inhdbt.getId());
                                  //  doubtStatusService.deleteDoubtStatus(inhdbt.getId());
                                  idsOfDoubtStatusToBeDeleted.add(inhdbt.getId());
                                }
                                System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): all doubtStatus related to "+inheritedDoubt.getId()+" have now been deleted");
                                 Summary inhsummary;
                                 SummaryKey summaryKey=generateSummaryKey(subb, inheritedDoubt.getChildJob());
                               //if((inhsummary=summaryService.getSummaryFor(subb, inheritedDoubt.getChildJob()))!=null){
                               if(summaryMap.containsKey(summaryKey)){
                                   inhsummary=summaryMap.get(summaryKey);
                                   System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): Summary entry for inherited(Time) summary id: "+inhsummary.getId()+" set to "+false);
                                   inhsummary.setTimeInheritanceSummary(false);   //if yes then set the inheritance to false
                              //     summariesToBeUpdated.add(inhsummary);
                               }
                                 System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): Deleting inherited id: "+inheritedDoubt.getId());
                           // doubtService.deleteDoubt(inheritedDoubt.getId());
                           idsOfInheritedDoubtsToBeDeleted.add(inheritedDoubt.getId());
                            
                        }
                        System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): all inherited doubts cleared for id: "+existingDoubt.getId()+"\nDeleting id: "+existingDoubt.getId());
                       // doubtService.deleteDoubt(existingDoubt.getId());
                       idsOfCausalDoubtsToBeDeleted.add(existingDoubt.getId());
                    }
                    System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): Setting summary to false for job: "+l.getChild().getNameJobStep()+" sub: "+subb.getSubsurface());
                    summary.setTimeSummary(false);
                   // summariesToBeUpdated.add(summary);
                    
                    
                }
                
                
                 String summaryTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);    
                 /*SubsurfaceJob dbSubChildjob=subsurfaceJobService.getSubsurfaceJobFor(l.getChild(), subb);
                 dbSubChildjob.setSummaryTime(summaryTime);
                 subsurfaceJobService.updateSubsurfaceJob(dbSubChildjob);    */
                            SubsurfaceJobKey sjkey=generateSubsurfaceJobKey(l.getChild(), subb);
                if(subsurfaceJobSummaryTimeMap.containsKey(sjkey)){
                subsurfaceJobSummaryTimeMap.get(sjkey).setSummaryTime(summaryTime);
            }   
                            /*  SubsurfaceJob dbSubParentjob=subsurfaceJobService.getSubsurfaceJobFor(l.getParent(), subb);
                            dbSubParentjob.setSummaryTime(summaryTime);
                            subsurfaceJobService.updateSubsurfaceJob(dbSubParentjob);         */       
                            
    }

    private void checkForDependencyDoubts(Link l, Dot dot, Map<String, Double> mapForVariableSetting, Set<String> variableSet, Set<Job> argumentSet, Subsurface subb, Summary summary,List<Descendant> descendantsThatContainSub) throws Exception {
        
        
        
                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts()");
                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): inside Link : " + l.getId() + " " + l.getParent().getNameJobStep() + "--->" + l.getChild().getNameJobStep()+" sub: "+subb.getSubsurface());
                String function = dot.getFunction();
                Double tolerance = dot.getTolerance();
                Double error = dot.getError();
                //Set<VariableArgument> variableArguments = dot.getVariableArguments();         //variable and the arguments belonging to the dot.
                Set<VariableArgument> variableArguments = new HashSet<>(variableArgumentMap.get(dot));
                mapForVariableSetting.clear();
                variableSet.clear();
                argumentSet.clear();
                for (VariableArgument va : variableArguments) {
                    String var = va.getVariable();
                    Job arg = va.getArgument();
                    Double tracesArg;
                    HeaderKey hkey=generateHeaderKey(arg, subb);
                    /*Header h = headerService.getChosenHeaderFor(arg, subb);          //O-2*/
                    Header h=headerMap.get(hkey);
                    if (h == null) {
                        tracesArg = 0.0;
                    } else {
                        tracesArg = Double.valueOf(h.getTraceCount() + "");
                    }

                    mapForVariableSetting.put(var, tracesArg);
                    if (!var.equals("y0")) {                      //y0 is the lhs which is fixed, the rhs needs to be evaluated. Do not include the y-term
                        variableSet.add(var);
                        argumentSet.add(arg);
                    }

                }
                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): Sub: " + subb.getSubsurface() + " linkParent: " + l.getParent().getNameJobStep() + " linkChild: " + l.getChild().getNameJobStep());
                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): function: " + function);
                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): setting var-args");
                for (Map.Entry<String, Double> entry : mapForVariableSetting.entrySet()) {
                    String key = entry.getKey();
                    Double value = entry.getValue();
                    System.out.println(key + " = " + value);
                }

                Expression e = new ExpressionBuilder(function)
                        .variables(variableSet)
                        .build()
                        .setVariables(mapForVariableSetting);
                Double result = e.evaluate();

                Double y = mapForVariableSetting.get("y0");
                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): check for y = "+y+
                                    "evaluated.result = "+result);
                Double evaluated = Math.abs(y - result) / y;
                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): result = " + result + " evaluated difference/y = " + evaluated);
                if (evaluated <= tolerance) {
                    System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): no doubt");
                    //check for any existing doubt.
                    //clear tracedependency flag.
                    //clear all doubtstatus messages related to doubt
                    //remove all inherited doubts ( and associated doubt status messages) if present
                    
                    //change summaryforCurrentJob.trace= false
                   
                    System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts() "+l.getParent().getNameJobStep()+ "<----->"+l.getChild().getNameJobStep()+" has passed dependency. Checking for any existing doubt entries..so as to clear them");
                    Doubt existingDoubt;
                 //   if((existingDoubt=doubtService.getDoubtFor(subb, l.getChild(), dot, doubtTypeTraces))==null){
                 DoubtKey key=generateDoubtKey(subb, l.getChild(), dot, doubtTypeTraces);
                 if(!causeDoubtMap.containsKey(key)){
                        //do nothing ..as no doubt exists
                        System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): No doubts exist for job: "+l.getChild().getNameJobStep()+" sub: "+subb.getSubsurface()+" for type: "+doubtTypeTraces.getName());
                    }else{
                        //get all doubtStatus for the existing doubt
                        existingDoubt=causeDoubtMap.get(key);
                        System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): found an existing doubt to a previous failed condition: id. "+existingDoubt.getId());
                       // Set<DoubtStatus> doubtStatus=existingDoubt.getDoubtStatuses();
                      //  Set<DoubtStatus> doubtStatus=new HashSet<>(doubtStatusService.getDoubtStatusForDoubt(existingDoubt));
                      Set<DoubtStatus> doubtStatus=new HashSet<>();
                            if(doubtDoubtStatusMap.containsKey(existingDoubt)){
                                doubtStatus=new HashSet<>(doubtDoubtStatusMap.get(existingDoubt));
                            }
                        for(DoubtStatus doubtStat:doubtStatus){
                            System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): deleting doubtstatus: "+doubtStat.getId() );
                            //doubtStatusService.deleteDoubtStatus(doubtStat.getId());
                            idsOfDoubtStatusToBeDeleted.add(doubtStat.getId());
                        }
                        System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): all doubtstatus messages related to "+existingDoubt.getId()+" have now been deleted.");
                        System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): Checking to see if there were any inherited doubts related to doubt id: "+existingDoubt.getId());
                        
                       // Set<Doubt> inheritedDoubts=existingDoubt.getInheritedDoubts();
                        List<Doubt> inheritedDoubts=new ArrayList<>();
                       if(causeAndAssociatedInheritanceMap.containsKey(existingDoubt)){
                           inheritedDoubts=causeAndAssociatedInheritanceMap.get(existingDoubt);
                       }
                        for(Doubt inheritedDoubt:inheritedDoubts){
                            System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): found inherited doubt id: "+inheritedDoubt.getId()+" with existingCause Id: "+existingDoubt.getId());
                            
                            //Set<DoubtStatus> doubtStatuses=inheritedDoubt.getDoubtStatuses();
                            //Set<DoubtStatus> doubtStatuses=new HashSet<>(doubtStatusService.getDoubtStatusForDoubt(inheritedDoubt));
                            Set<DoubtStatus> inheritedDoubtStatus=new HashSet<>();
                            if(doubtDoubtStatusMap.containsKey(inheritedDoubt)){
                                inheritedDoubtStatus=new HashSet<>(doubtDoubtStatusMap.get(inheritedDoubt));
                            }
                                for(DoubtStatus inhdbt:inheritedDoubtStatus){
                                    System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): found doubtstatus id: "+inhdbt.getId()+" related to the inherited doubt :"+inheritedDoubt.getId()+""
                                            + "\nDeleting doubtstatus: "+inhdbt.getId());
                                   // doubtStatusService.deleteDoubtStatus(inhdbt.getId());
                                   idsOfDoubtStatusToBeDeleted.add(inhdbt.getId());
                                }
                                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): all doubtStatus related to "+inheritedDoubt.getId()+" have now been deleted");
                                 Summary inhsummary;
                                 
                                 SummaryKey summaryKey=generateSummaryKey(subb, inheritedDoubt.getChildJob());
                             //  if((inhsummary=summaryService.getSummaryFor(subb, inheritedDoubt.getChildJob()))!=null){
                             if(summaryMap.containsKey(summaryKey)){
                                   inhsummary=summaryMap.get(summaryKey);
                                   System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): Summary entry for inherited(Trace) summary id: "+inhsummary.getId()+" set to "+false);
                                   inhsummary.setTraceInheritanceSummary(false);   //if yes then set the inheritance to false
                                  // summariesToBeUpdated.add(inhsummary);
                               }
                                 System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): Deleting inherited id: "+inheritedDoubt.getId());
                                
                                
                          //  doubtService.deleteDoubt(inheritedDoubt.getId());
                          idsOfInheritedDoubtsToBeDeleted.add(inheritedDoubt.getId());
                            
                            
                            
                        }
                        System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): all inherited doubts cleared for id: "+existingDoubt.getId()+"\nDeleting id: "+existingDoubt.getId());
                        //doubtService.deleteDoubt(existingDoubt.getId());
                        idsOfCausalDoubtsToBeDeleted.add(existingDoubt.getId());
                    }
                    System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): Setting summary to false for job: "+l.getChild().getNameJobStep()+" sub: "+subb.getSubsurface());
                    summary.setTraceSummary(false);
                   // summariesToBeUpdated.add(summary);
                    
                    
                } else if ((evaluated <= error && evaluated > tolerance) || (evaluated > error)) {
                    System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): creating doubt");
                    String dotState = dot.getStatus();
                    
                    
                    //Use this redFlag to set summaryforCurrentJob to either null or true . Summary=null will show up red on the summaryforCurrentJob table. Summary=true will show up as a warning(milder) color
                    
                    Boolean redFlag;
                    if(evaluated > error){
                    redFlag=true;
                    }else{
                    redFlag=false;
                    }
                    Doubt doubt;

                    if (dotState.equals(DotModel.JOIN)) {
                        
                         DoubtKey key=generateDoubtKey(subb, l.getChild(), dot, doubtTypeTraces);
                        if(!causeDoubtMap.containsKey(key)){
                       // if ((doubtService.getDoubtFor(subb, l.getChild(), dot, doubtTypeTraces)) == null) {
                            System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): creating doubt entry for " + subb.getSubsurface() + " job: " + l.getChild().getId() + " : " + l.getChild().getNameJobStep());
                            doubt = new Doubt();
                            doubt.setChildJob(l.getChild());
                            doubt.setSubsurface(subb);
                            doubt.setSequence(subb.getSequence());
                            doubt.setDot(dot);
                            doubt.setLink(l);
                            doubt.setDoubtType(doubtTypeTraces);
                            //doubt.setUser(user);
                           // doubtService.createDoubt(doubt);
                           causes.add(doubt);
                           causeDoubtMap.put(key, doubt);
                           
                            DoubtStatus doubtStatus = new DoubtStatus();
                            doubtStatus.setReason(DoubtStatusModel.getNewDoubtTraceMessage(function, tolerance, error, evaluated, y, doubtTypeTraces.getName()));
                            doubtStatus.setDoubt(doubt);
                            doubtStatus.setStatus(DoubtStatusModel.YES);
                            if(redFlag){
                                doubtStatus.setState(DoubtStatusModel.ERROR);
                            }else{
                                doubtStatus.setState(DoubtStatusModel.WARNING);
                            }
                            doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                            //doubtStatus.setUser(user);
                          //  doubtStatusService.createDoubtStatus(doubtStatus);
                            doubtStatusForCause.add(doubtStatus);
                           // doubt.addToDoubtStatuses(doubtStatus);
                           // doubtService.updateDoubt(doubt.getId(), doubt);
                           //doubtsToBeUpdated.add(doubt);

                            summary.setTraceSummary(true);
                         //   summariesToBeUpdated.add(summary);
                            if(redFlag){                                                //inherit only for errors
                                passingDoubtToDescendants(l,dot,subb,doubt,descendantsThatContainSub);
                            }
                        } else {
                           // doubt = doubtService.getDoubtFor(subb, l.getChild(), dot, doubtTypeTraces);
                           doubt=causeDoubtMap.get(key);
                            if(redFlag){
                                    passingDoubtToDescendants(l, dot, subb,doubt, descendantsThatContainSub);
                                }
                            summary.setTraceSummary(true);
                          //  summariesToBeUpdated.add(summary);
                            String updateT=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
                            //Set<DoubtStatus> doubtStatuses = doubt.getDoubtStatuses();
                            //Set<DoubtStatus> doubtStatuses=new HashSet<>(doubtStatusService.getDoubtStatusForDoubt(doubt));
                            
                            Set<DoubtStatus> doubtStatuses=new HashSet<>();
                            if(doubtDoubtStatusMap.containsKey(doubt)){
                                doubtStatuses=new HashSet<>(doubtDoubtStatusMap.get(doubt));
                            }
                            for (DoubtStatus ds : doubtStatuses) {
                                 if(redFlag){
                                        DoubtStatus dsds=doubtStatusService.getDoubtStatus(ds.getId());
                                        dsds.setState(DoubtStatusModel.ERROR);
                                        dsds.setReason(DoubtStatusModel.getNewDoubtTraceMessage(function, tolerance, error, evaluated, y, doubtTypeTraces.getName()));
                                        dsds.setTimeStamp(updateT);
                                        //doubtStatusService.updateDoubtStatus(dsds.getId(),dsds);asd
                                        doubtStatusToBeUpdated.add(dsds);
                                    }else{
                                        DoubtStatus dsds=doubtStatusService.getDoubtStatus(ds.getId());
                                        dsds.setState(DoubtStatusModel.WARNING);
                                        dsds.setReason(DoubtStatusModel.getNewDoubtTraceMessage(function, tolerance, error, evaluated, y, doubtTypeTraces.getName()));
                                        dsds.setTimeStamp(updateT);
                                       // doubtStatusService.updateDoubtStatus(dsds.getId(),dsds);
                                       doubtStatusToBeUpdated.add(dsds);
                                    }
                                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): doubstatuses assosciated with Doubt: " + doubt.getId() + " " + ds.getStatus() + " comment: " + ds.getReason() + " time: " + ds.getTimeStamp());
                            }
                        }
                    } else {
                        for (Job child : argumentSet) {                     //In states SPLIT and NJS , the argument set comprises of only children. since P=f(C1,C2,..Cn);
                            DoubtKey key=generateDoubtKey(subb, child, dot, doubtTypeTraces);
                             if(!causeDoubtMap.containsKey(key)){ 
                           //if ((doubtService.getDoubtFor(subb, child, dot, doubtTypeTraces)) == null) {
                                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): creating doubt entry for " + subb.getSubsurface() + " job: " + child.getId() + " : " + child.getNameJobStep());
                                doubt = new Doubt();
                                doubt.setChildJob(child);
                                doubt.setSubsurface(subb);
                                doubt.setSequence(subb.getSequence());
                                doubt.setDot(dot);
                                doubt.setLink(l);
                                doubt.setDoubtType(doubtTypeTraces);
                                //doubt.setUser(user);
                               // doubtService.createDoubt(doubt);
                                causes.add(doubt);
                                //summary.setTraceSummary(true);
                                
                                
                                DoubtStatus doubtStatus = new DoubtStatus();
                                doubtStatus.setReason(DoubtStatusModel.getNewDoubtTraceMessage(function, tolerance, error, evaluated, y, doubtTypeTraces.getName()));
                                doubtStatus.setDoubt(doubt);
                                doubtStatus.setStatus(DoubtStatusModel.YES);
                                if(redFlag){
                                    doubtStatus.setState(DoubtStatusModel.ERROR);
                                }else{
                                    doubtStatus.setState(DoubtStatusModel.WARNING);
                                }
                                doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                                //doubtStatus.setUser(user);
                                //doubtStatusService.createDoubtStatus(doubtStatus);
                                doubtStatusForCause.add(doubtStatus);
                                doubt.addToDoubtStatuses(doubtStatus);
                                ///doubtService.updateDoubt(doubt.getId(), doubt);
                              //  doubtsToBeUpdated.add(doubt);
                                summary.setTraceSummary(true);
                            //    summariesToBeUpdated.add(summary);
                                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): Setting Trace doubt inheritance on the descendants");
                                if(redFlag){                                        //inherit only for errors
                                    passingDoubtToDescendants(l,dot,subb,doubt,descendantsThatContainSub);
                                }
                            } else {
                                //doubt = doubtService.getDoubtFor(subb, child, dot, doubtTypeTraces);
                                 doubt=causeDoubtMap.get(key);
                                if(redFlag){
                                    passingDoubtToDescendants(l, dot, subb,doubt, descendantsThatContainSub);
                                }
                               // Set<DoubtStatus> doubtStatuses = doubt.getDoubtStatuses();
                                //Set<DoubtStatus> doubtStatuses=new HashSet<>(doubtStatusService.getDoubtStatusForDoubt(doubt));
                                Set<DoubtStatus> doubtStatuses=new HashSet<>();
                            if(doubtDoubtStatusMap.containsKey(doubt)){
                                doubtStatuses=new HashSet<>(doubtDoubtStatusMap.get(doubt));
                            }
                                summary.setTraceSummary(true);
                             //   summariesToBeUpdated.add(summary);
                                String updateT=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
                                for (DoubtStatus ds : doubtStatuses) {
                                    if(redFlag){
                                        DoubtStatus dsds=doubtStatusService.getDoubtStatus(ds.getId());
                                        dsds.setState(DoubtStatusModel.ERROR);
                                        dsds.setReason(DoubtStatusModel.getNewDoubtTraceMessage(function, tolerance, error, evaluated, y, doubtTypeTraces.getName()));
                                        dsds.setTimeStamp(updateT);
                                        dsds.setStatus(DoubtStatusModel.YES);
                                        //doubtStatusService.updateDoubtStatus(dsds.getId(),dsds);
                                         doubtStatusToBeUpdated.add(dsds);
                                    }else{
                                        DoubtStatus dsds=doubtStatusService.getDoubtStatus(ds.getId());
                                        dsds.setState(DoubtStatusModel.WARNING);
                                        dsds.setReason(DoubtStatusModel.getNewDoubtTraceMessage(function, tolerance, error, evaluated, y, doubtTypeTraces.getName()));
                                        dsds.setTimeStamp(updateT);
                                        dsds.setStatus(DoubtStatusModel.YES);
                                       //doubtStatusService.updateDoubtStatus(dsds.getId(),dsds);
                                        doubtStatusToBeUpdated.add(dsds);
                                    }
                                    System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): doubstatuses associated with Doubt: " + doubt.getId() + " " + ds.getStatus() + " comment: " + ds.getReason() + " time: " + ds.getTimeStamp());
                                }

                            }
                        }
                    }

                    
                    //if (redFlag==false) i.e. there's a Warning then check for any inheritance and remove them.
                    
                    if(!redFlag){
                        System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): redFlag is false..dependency is in the WARNING state. Removing any previous inheritance");
                        
                        
                       
                    //check for any existing doubt.
                    //clear tracedependency flag.
                    //update all doubtstatus messages related to doubt (state: ERROR --> WARNING)
                    //remove all inherited doubts ( and associated doubt status messages) if present
                    // the doubt is still present. just not inherited!. Therefore the existing doubt is not deleted. the doubtstatus.state is changed from ERROR --> WARNING
                    //keep summaryforCurrentJob.trace= true
                    
                             Doubt previousErroneousDoubt;
                            DoubtKey key=generateDoubtKey(subb, l.getChild(), dot, doubtTypeTraces);
                             if(!causeDoubtMap.containsKey(key)){ 
                           
                           // if((previousErroneousDoubt=doubtService.getDoubtFor(subb, l.getChild(), dot, doubtTypeTraces))==null){
                                //do nothing ..as no doubt exists
                                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): No doubts exist for job: "+l.getChild().getNameJobStep()+" sub: "+subb.getSubsurface()+" for type: "+doubtTypeTraces.getName());
                            }else{
                                //get all doubtStatus for the existing doubt
                                previousErroneousDoubt=causeDoubtMap.get(key);
                                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): found an existing doubt to a previous failed condition: id. "+previousErroneousDoubt.getId());
                                //Set<DoubtStatus> doubtStatus=previousErroneousDoubt.getDoubtStatuses();
                               // Set<DoubtStatus> doubtStatus=new HashSet<>(doubtStatusService.getDoubtStatusForDoubt(previousErroneousDoubt));
                               Set<DoubtStatus> doubtStatus=new HashSet<>();
                            if(doubtDoubtStatusMap.containsKey(previousErroneousDoubt)){
                                doubtStatus=new HashSet<>(doubtDoubtStatusMap.get(previousErroneousDoubt));
                            }
                                for(DoubtStatus doubtStat:doubtStatus){
                                    System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): updating doubtstatus: "+doubtStat.getId() );
                                   // doubtStatusService.deleteDoubtStatus(doubtStat.getId());
                                   doubtStat.setState(DoubtStatusModel.WARNING);
                                   //doubtStatusService.updateDoubtStatus(doubtStat.getId(), doubtStat);
                                   doubtStatusToBeUpdated.add(doubtStat);
                                }
                                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): all doubtstatus messages related to "+previousErroneousDoubt.getId()+" have now been deleted.");
                                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): Checking to see if there were any inherited doubts related to doubt id: "+previousErroneousDoubt.getId());

                               // Set<Doubt> inheritedDoubts=previousErroneousDoubt.getInheritedDoubts();
                                List<Doubt> inheritedDoubts=new ArrayList<>();
                                    if(causeAndAssociatedInheritanceMap.containsKey(previousErroneousDoubt)){
                                        inheritedDoubts=causeAndAssociatedInheritanceMap.get(previousErroneousDoubt);
                                    }
                                for(Doubt inheritedDoubt:inheritedDoubts){
                                    System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): found inherited doubt id: "+inheritedDoubt.getId()+" with existingCause Id: "+previousErroneousDoubt.getId());

                                    //Set<DoubtStatus> doubtStatuses=inheritedDoubt.getDoubtStatuses();
                                    //Set<DoubtStatus> doubtStatuses=new HashSet<>(doubtStatusService.getDoubtStatusForDoubt(inheritedDoubt));
                                    Set<DoubtStatus> inheritedDoubtStatus=new HashSet<>();
                                            if(doubtDoubtStatusMap.containsKey(inheritedDoubt)){
                                                inheritedDoubtStatus=new HashSet<>(doubtDoubtStatusMap.get(inheritedDoubt));
                                            }
                                        for(DoubtStatus inhdbt:inheritedDoubtStatus){
                                            System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): found doubtstatus id: "+inhdbt.getId()+" related to the inherited doubt :"+inheritedDoubt.getId()+""
                                                    + "\nDeleting doubtstatus: "+inhdbt.getId());
                                            //doubtStatusService.deleteDoubtStatus(inhdbt.getId());
                                            idsOfDoubtStatusToBeDeleted.add(inhdbt.getId());
                                        }
                                        System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): all doubtStatus related to "+inheritedDoubt.getId()+" have now been deleted");
                                         Summary inhsummary;
                                      // if((inhsummary=summaryService.getSummaryFor(subb, inheritedDoubt.getChildJob()))!=null){SummaryKey summaryKey=generateSummaryKey(subb, inheritedDoubt.getChildJob());
                             //  if((inhsummary=summaryService.getSummaryFor(subb, inheritedDoubt.getChildJob()))!=null){
                                    SummaryKey summaryKey=generateSummaryKey(subb, inheritedDoubt.getChildJob());
                                    if(summaryMap.containsKey(summaryKey)){
                                            inhsummary=summaryMap.get(summaryKey);
                                           System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): Summary entry for inherited(Trace) summary id: "+inhsummary.getId()+" set to "+false);
                                           inhsummary.setTraceInheritanceSummary(false);   //if yes then set the inheritance to false
                                      //     summariesToBeUpdated.add(inhsummary);
                                       }
                                         System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): Deleting inherited id: "+inheritedDoubt.getId());


                                    //doubtService.deleteDoubt(inheritedDoubt.getId());
                                    idsOfInheritedDoubtsToBeDeleted.add(inheritedDoubt.getId());



                                }
                                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): all inherited doubts cleared for id: "+previousErroneousDoubt.getId()+"\nDeleting id: "+previousErroneousDoubt.getId());
                               
                            }
                           summary.setTraceSummary(true);
                       //    summariesToBeUpdated.add(summary);


                            }
                    
                }
                
            //  System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): "+timeNow()+"updating subsurface_job table: "+l.getChild().getNameJobStep()+" , "+subb.getSubsurface());
            String summaryTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);    
            
            SubsurfaceJobKey sjkey=generateSubsurfaceJobKey(l.getChild(), subb);
            if(subsurfaceJobSummaryTimeMap.containsKey(sjkey)){
                subsurfaceJobSummaryTimeMap.get(sjkey).setSummaryTime(summaryTime);
            }    
            
            
            /*SubsurfaceJob dbSubChildjob=subsurfaceJobService.getSubsurfaceJobFor(l.getChild(), subb);
            dbSubChildjob.setSummaryTime(summaryTime);
            subsurfaceJobService.updateSubsurfaceJob(dbSubChildjob);    */
//                            subsurfaceJobService.updateSummaryTimeFor(l.getChild(),subb,summaryTime);
           // System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): "+timeNow()+"updating subsurface_job table: "+l.getChild().getNameJobStep()+" , "+subb.getSubsurface());
            /*System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): "+timeNow()+"updating subsurface_job table: "+l.getChild().getNameJobStep()+" , "+subb.getSubsurface());
            SubsurfaceJob dbSubParentjob=subsurfaceJobService.getSubsurfaceJobFor(l.getParent(), subb);
            dbSubParentjob.setSummaryTime(summaryTime);
            subsurfaceJobService.updateSubsurfaceJob(dbSubParentjob);
            System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): "+timeNow()+"updating subsurface_job table: "+l.getChild().getNameJobStep()+" , "+subb.getSubsurface());    */        
    }

    private void checkForQcDoubts(Link l, Dot dot, Subsurface subb, Summary summary,List<Descendant> descendantsThatContainSub) throws Exception {
        
        System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): for  "+l.getParent().getNameJobStep()+"<----->"+l.getChild().getNameJobStep()+" for sub: "+subb.getSubsurface());
       
                Job lparent = l.getParent();
                Job jchild = l.getChild();
                List<QcMatrixRow> parentQcMatrix = qcMatrixRowService.getQcMatrixForJob(lparent, true);
                Boolean passQc = true;
                for (QcMatrixRow qcmr : parentQcMatrix) {
                    QcTable qctableentries = qcTableService.getQcTableFor(qcmr, subb);
                    Boolean qcresult = qctableentries.getResult();
                    if (qcresult == null) {
                        qcresult = false;
                    }
                    passQc = passQc && qcresult;

                }
                if (!passQc) {  //if parent has failed a single qc. Indeterminate or unchecked. create a doubt in the child
                    Doubt doubt;
                    //if ((doubt = doubtService.getDoubtFor(subb, jchild, dot, doubtTypeQc)) == null) {
                      DoubtKey key=generateDoubtKey(subb, jchild, dot, doubtTypeQc);
                        if(!causeDoubtMap.containsKey(key)){
                        doubt = new Doubt();
                        doubt.setChildJob(jchild);
                        doubt.setDot(dot);
                            doubt.setLink(l);
                        doubt.setSubsurface(subb);
                        doubt.setSequence(subb.getSequence());
                        doubt.setDoubtType(doubtTypeQc);
                        //doubt.setUser(user)
                        //doubtService.createDoubt(doubt);
                        causes.add(doubt);
                            
                        summary.setQcSummary(true);
                    //    summariesToBeUpdated.add(summary);
                        DoubtStatus doubtStatus = new DoubtStatus();
                        doubtStatus.setReason(DoubtStatusModel.getNewDoubtQCcessage(lparent.getNameJobStep(), jchild.getNameJobStep(), subb.getSubsurface(), doubtTypeQc.getName()));
                        doubtStatus.setDoubt(doubt);
                        doubtStatus.setStatus(DoubtStatusModel.YES);
                        doubtStatus.setState(DoubtStatusModel.ERROR);
                        doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                        //doubtStatus.setUser(user);
                       // doubtStatusService.createDoubtStatus(doubtStatus);
                       doubtStatusForCause.add(doubtStatus);
                        doubt.addToDoubtStatuses(doubtStatus);
                    //    doubtService.updateDoubt(doubt.getId(), doubt);
                    //doubtsToBeUpdated.add(doubt);
                        passingDoubtToDescendants(l, dot, subb,doubt, descendantsThatContainSub);

                    } else {
                            doubt=causeDoubtMap.get(key);
                        passingDoubtToDescendants(l, dot, subb,doubt, descendantsThatContainSub);
                        //Set<DoubtStatus> doubtStatuses = doubt.getDoubtStatuses();
                        //Set<DoubtStatus> doubtStatuses = new HashSet<>(doubtStatusService.getDoubtStatusForDoubt(doubt));
                        Set<DoubtStatus> doubtStatuses=new HashSet<>();
                            if(doubtDoubtStatusMap.containsKey(doubt)){
                                doubtStatuses=new HashSet<>(doubtDoubtStatusMap.get(doubt));
                            }
                        summary.setQcSummary(true);
                        for (DoubtStatus d : doubtStatuses) {
                            System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: " + doubt.getId() + " " + d.getStatus() + " comment: " + d.getReason() + " time: " + d.getTimeStamp());
                        }
                    }
                }else{
                    System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): "+lparent.getNameJobStep()+ " has passed all QC. Checking for any existing doubt entries..so as to clear them");
                    Doubt existingDoubt;
                    //if((existingDoubt=doubtService.getDoubtFor(subb, jchild, dot, doubtTypeQc))==null){
                    DoubtKey key=generateDoubtKey(subb, jchild, dot, doubtTypeQc);
                        if(!causeDoubtMap.containsKey(key)){
                        //do nothing ..as no doubt exists
                        System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): No doubts exist for job: "+jchild.getNameJobStep()+" sub: "+subb.getSubsurface()+" for type: "+doubtTypeQc.getName());
                    }else{
                        //get all doubtStatus for the existing doubt
                        existingDoubt=causeDoubtMap.get(key);
                        System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): found an existing doubt to a previous failed condition: id. "+existingDoubt.getId());
                       // Set<DoubtStatus> doubtStatus=existingDoubt.getDoubtStatuses();
                        //Set<DoubtStatus> doubtStatus=new HashSet<>(doubtStatusService.getDoubtStatusForDoubt(existingDoubt));
                        Set<DoubtStatus> doubtStatus=new HashSet<>();
                            if(doubtDoubtStatusMap.containsKey(existingDoubt)){
                                doubtStatus=new HashSet<>(doubtDoubtStatusMap.get(existingDoubt));
                            }
                        for(DoubtStatus doubtStat:doubtStatus){
                            System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): deleting doubtstatus: "+doubtStat.getId() );
                            //doubtStatusService.deleteDoubtStatus(doubtStat.getId());
                            idsOfDoubtStatusToBeDeleted.add(doubtStat.getId());
                        }
                        System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): all doubtstatus messages related to "+existingDoubt.getId()+" have now been deleted.");
                        System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): Checking to see if there were any inherited doubts related to doubt id: "+existingDoubt.getId());
                        
                       // Set<Doubt> inheritedDoubts=existingDoubt.getInheritedDoubts();
                        List<Doubt> inheritedDoubts=new ArrayList<>();
                       if(causeAndAssociatedInheritanceMap.containsKey(existingDoubt)){
                           inheritedDoubts=causeAndAssociatedInheritanceMap.get(existingDoubt);
                       }
                        for(Doubt inheritedDoubt:inheritedDoubts){
                            System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): found inherited doubt id: "+inheritedDoubt.getId()+" with existingCause Id: "+existingDoubt.getId());
                            
                            //Set<DoubtStatus> doubtStatuses=inheritedDoubt.getDoubtStatuses();
                           // Set<DoubtStatus> doubtStatuses=new HashSet<>(doubtStatusService.getDoubtStatusForDoubt(inheritedDoubt));
                            Set<DoubtStatus> inheritedDoubtStatus=new HashSet<>();
                            if(doubtDoubtStatusMap.containsKey(inheritedDoubt)){
                                inheritedDoubtStatus=new HashSet<>(doubtDoubtStatusMap.get(inheritedDoubt));
                            }
                                for(DoubtStatus inhdbt:inheritedDoubtStatus){
                                    System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): found doubtstatus id: "+inhdbt.getId()+" related to the inherited doubt :"+inheritedDoubt.getId()+""
                                            + "\nDeleting doubtstatus: "+inhdbt.getId());
                                    //doubtStatusService.deleteDoubtStatus(inhdbt.getId());
                                    idsOfDoubtStatusToBeDeleted.add(inhdbt.getId());
                                }
                                System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): all doubtStatus related to "+inheritedDoubt.getId()+" have now been deleted");
                               
                                
                                    //check if there's a summaryforCurrentJob entry for the inherited job./sub
                                Summary inhsummary;
                                 SummaryKey summaryKey=generateSummaryKey(subb, inheritedDoubt.getChildJob());
                                    if(summaryMap.containsKey(summaryKey)){
                              // if((inhsummary=summaryService.getSummaryFor(subb, inheritedDoubt.getChildJob()))!=null){
                                   inhsummary=summaryMap.get(summaryKey);
                                   System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): Summary entry for inherited(QC) summary id: "+inhsummary.getId()+" set to "+false);
                                   inhsummary.setQcInheritanceSummary(false);   //if yes then set the inheritance to false
                               }
                                 System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): Deleting inherited id: "+inheritedDoubt.getId());
                            //doubtService.deleteDoubt(inheritedDoubt.getId());
                            idsOfInheritedDoubtsToBeDeleted.add(inheritedDoubt.getId());
                            
                            
                            
                        }
                        System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): all inherited doubts cleared for id: "+existingDoubt.getId()+"\nDeleting id: "+existingDoubt.getId());
                       // doubtService.deleteDoubt(existingDoubt.getId());
                       idsOfCausalDoubtsToBeDeleted.add(existingDoubt.getId());
                    }
                    System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): Setting summary to false for job: "+jchild.getNameJobStep()+" sub: "+subb.getSubsurface());
                    summary.setQcSummary(false);
                  //  summariesToBeUpdated.add(summary);
                
                }
                
                
                String summaryTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);    
                
                SubsurfaceJobKey sjkey=generateSubsurfaceJobKey(l.getChild(), subb);
                if(subsurfaceJobSummaryTimeMap.containsKey(sjkey)){
                subsurfaceJobSummaryTimeMap.get(sjkey).setSummaryTime(summaryTime);
            }   
                
                /*
                SubsurfaceJob dbSubChildjob=subsurfaceJobService.getSubsurfaceJobFor(l.getChild(), subb);
                dbSubChildjob.setSummaryTime(summaryTime);
                subsurfaceJobService.updateSubsurfaceJob(dbSubChildjob);
                
                SubsurfaceJob dbSubParentjob=subsurfaceJobService.getSubsurfaceJobFor(l.getParent(), subb);
                dbSubParentjob.setSummaryTime(summaryTime);
                subsurfaceJobService.updateSubsurfaceJob(dbSubParentjob);    */

    }

    private void passingDoubtToDescendants(Link l, Dot dot, Subsurface subb, Doubt cause,List<Descendant> descendantsThatContainSub) {
        
        
                Job inhParent = l.getParent();
                Job inhChild = l.getChild();
                System.out.println("fend.workspace.WorkspaceController.summarize(): Checking for inheritance. "+inhParent.getNameJobStep()+" <----> "+inhChild.getNameJobStep()+" TYPE: "+cause.getDoubtType().getName()+" size of Descendants: "+descendantsThatContainSub.size());
                
                Set<Job> inhJob=new HashSet<>();
                for(Descendant desc:descendantsThatContainSub){
                    Job job=desc.getDescendant();
                    inhJob.add(job);
                }
                
             //   for(Descendant desc:descendantsThatContainSub){
                 for(Job job:inhJob){
                 
                     Summary summary;
                     
                      SummaryKey summaryKey=generateSummaryKey(subb, job);
                                    if(!summaryMap.containsKey(summaryKey)){
                         //   if((summary=summaryService.getSummaryFor(subb, job))==null){
                                 summary = new Summary();
                                summary.setSequence(subb.getSequence());
                                summary.setSubsurface(subb);
                                summary.setJob(job);
                                summary.setWorkspace(dbWorkspace);
                              //  summaryService.createSummary(summary);
                              newSummaries.add(summary);
                                
                            }else{
                                        summary=summaryMap.get(summaryKey);
                                    }
                     
                     
                    System.out.println("fend.workspace.WorkspaceController.inherit(): checking for Desc: "+job.getNameJobStep()+" <---- Descendant of ---"+l.getChild().getNameJobStep());
                    Doubt d;
                  // if((d=doubtService.getDoubtFor(subb, job, cause, doubtTypeInherit))==null){
                 //  DoubtKey key=generateDoubtKey(subb, job, dot, doubtTypeInherit);
                 InheritanceKey key=generateInheritanceKey(job, subb, cause);
                 
                       if(!inheritanceMap.containsKey(key)){
                                System.out.println("fend.workspace.WorkspaceController.inherit(): Creating a new INHERITANCE DOUBT for "+job.getNameJobStep()+" sub: "+subb.getSubsurface()+" cause: "+cause.getChildJob().getNameJobStep());
                                d=new Doubt();
                                d.setDoubtType(doubtTypeInherit);
                                d.setChildJob(job);
                                d.setDoubtCause(cause);
                                d.setSubsurface(subb);
                                d.setSequence(subb.getSequence());
                                /*d.setDot(dot);
                                d.setLink(l);*/
                                /*d.setDoubtCause(cause);
                                d.setSubsurface(subb);
                                d.setSequence(subb.getSequence());+-*/
                               // doubtService.createDoubt(d);
                               inheritedDoubts.add(d);
                               inheritanceMap.put(key, d);
                                System.out.println("fend.workspace.WorkspaceController.inherit(): Setting the inheritance flag in summaryEntry# "+summary.getId());
                                
                                if(cause.getDoubtType().equals(doubtTypeTime)){
                                     summary.setTimeInheritanceSummary(true);
                                }
                                if(cause.getDoubtType().equals(doubtTypeTraces)){
                                     summary.setTraceInheritanceSummary(true);
                                }
                                if(cause.getDoubtType().equals(doubtTypeQc)){
                                     summary.setQcInheritanceSummary(true);
                                }
                           //     summariesToBeUpdated.add(summary);
                                
                                DoubtStatus doubtStatus=new DoubtStatus();
                                doubtStatus.setReason(DoubtStatusModel.getNewDoubtInheritanceMessage(inhChild.getNameJobStep(),subb.getSubsurface(), job.getNameJobStep(), doubtTypeInherit.getName()));
                                doubtStatus.setDoubt(d);
                                doubtStatus.setStatus(DoubtStatusModel.YES);
                                doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                                //doubtStatus.setUser(user);
                               // doubtStatusService.createDoubtStatus(doubtStatus);
                              // doubtStatusForCause.add(doubtStatus);
                              inheritedDoubtStatus.add(doubtStatus);
                                //d.addToDoubtStatuses(doubtStatus);
                               // doubtService.updateDoubt(d.getId(), d);
                               //doubtsToBeUpdated.add(d);

                               // cause.addToInheritedDoubts(d);
                                //doubtService.updateDoubt(cause.getId(), cause);
                             //   doubtsToBeUpdated.add(cause);
                                //summaryService.updateSummary(summary.getId(), summary);
                             //   summariesToBeUpdated.add(summary);
                         }else{
                                d=causeDoubtMap.get(key);
                                System.out.println("fend.workspace.WorkspaceController.inherit(): Existing entry for INHERITANCE doubt found for "+job.getNameJobStep()+" sub: "+subb.getSubsurface()+" cause: "+cause.getChildJob().getNameJobStep()+ " with id: "+d.getId());
                                System.out.println("fend.workspace.WorkspaceController.inherit():  "+d.getDoubtType().getName()+" on "+d.getChildJob().getNameJobStep()+" cause: "+d.getDoubtCause().getChildJob().getNameJobStep()+" message: ");
                                System.out.println("fend.workspace.WorkspaceController.inherit(): Setting the inheritance flag in summaryEntry# "+summary.getId());
                                if(cause.getDoubtType().equals(doubtTypeTime)){
                                     summary.setTimeInheritanceSummary(true);
                                }
                                if(cause.getDoubtType().equals(doubtTypeTraces)){
                                     summary.setTraceInheritanceSummary(true);
                                }
                                if(cause.getDoubtType().equals(doubtTypeQc)){
                                     summary.setQcInheritanceSummary(true);
                                }
                               // summaryService.updateSummary(summary.getId(), summary);
                             //  summariesToBeUpdated.add(summary);
                      
                           // List<DoubtStatus> dsMessages=doubtStatusService.getDoubtStatusForDoubt(d);
                           Set<DoubtStatus> dsMessages=new HashSet<>();
                            if(doubtDoubtStatusMap.containsKey(d)){
                                dsMessages=new HashSet<>(doubtDoubtStatusMap.get(d));
                            }
                            for(DoubtStatus dsmess:dsMessages){
                                System.out.println("STATUS "+dsmess.getStatus()+" MSG: "+dsmess.getReason());
                            }
                        }
                }
        
    }
    
    /***
     * Unused methods
    **/
        
    public void summarize2(){
         Map<String, Double> mapForVariableSetting = new HashMap<>();
        Set<String> variableSet = new HashSet<>();
        Set<Job> argumentSet = new HashSet<>();
        List<Subsurface> subsurfaceList = subsurfaceService.getSubsurfaceList();
       // dbWorkspace = workspaceService.getWorkspace(dbWorkspace.getId());
       dbWorkspace=model.getWorkspace();
      //  Map<Job,List<Subsurface>> mapForSummary=subsurfaceJobService.getSubsurfaceJobsForSummary();   //all entries where updateTime>summaryTime
        for (Subsurface subb : subsurfaceList) {
            System.out.println("fend.workspace.WorkspaceController.getSummary(): Loop for subsurface : " + subb.getSubsurface());
           // Set<Link> linkNodesContainingSub = linkService.getLinksContainingSubsurface(subb, dbWorkspace,mapForSummary);                  //all links where both the parent and the child contains sub subb
           List<Link> linkNodesContainingSub=linkService.getSummaryLinksForSubsurfaceInWorkspace(dbWorkspace, subb);
            
            GLink root=graphOfLinks(new HashSet<>(linkNodesContainingSub));
            
            
            
            for (Link l : linkNodesContainingSub) {
                Summary summary;
                //if ((summaryforCurrentJob = summaryService.getSummaryFor(subb.getSequence(), l.getParent())) == null) {            //create an entry for parent summaryforCurrentJob
                if ((summary = summaryService.getSummaryFor(subb, l.getParent())) == null) {            //create an entry for parent summaryforCurrentJob    
                    summary = new Summary();
                    summary.setSequence(subb.getSequence());
                    summary.setSubsurface(subb);
                    summary.setJob(l.getParent());
                    summary.setWorkspace(dbWorkspace);
                    summaryService.createSummary(summary);
                }
                //if ((summaryforCurrentJob = summaryService.getSummaryFor(subb.getSequence(), l.getChild())) == null) {             //create an entry for child summaryforCurrentJob 
                if ((summary = summaryService.getSummaryFor(subb, l.getChild())) == null) {             //create an entry for child summaryforCurrentJob 
                    summary = new Summary();
                    summary.setSequence(subb.getSequence());
                    summary.setSubsurface(subb);
                    summary.setJob(l.getChild());
                    summary.setWorkspace(dbWorkspace);
                    summaryService.createSummary(summary);
                }

                //dependency
                
               

                Dot dot = l.getDot();                     //the dot to which the link belongs\\
            
                
            
            }
        }
    }

    /**
     * returns a GLink node. which is the root of a graph which has the Links from the argument set as its nodes.
     *      
     *    GLink G2 is the child of GLink G1 
     *      if G2.Link.parent is a descendant of G1.Link.child 
     **/
    private GLink graphOfLinks(Set<Link> linkNodesContainingSub) {
        GLink root=new GLink();
        Set<GLink> glinkSet=new HashSet<>();
            for(Link l:linkNodesContainingSub){
                GLink gl=new GLink();
                gl.setLink(l);
                glinkSet.add(gl);
            }
            
        
            for (Iterator<GLink> iterator = glinkSet.iterator(); iterator.hasNext();) {
            GLink g1 = iterator.next();
            Job g1Child=g1.getLink().getChild();
           
            
            List<Descendant> descendantsForG1Child=descendantService.getDescendantsFor(g1Child);
           
            
                for (Iterator<GLink> iterator1 = glinkSet.iterator(); iterator1.hasNext();) {
                    GLink g2 = iterator1.next();
                    Job g2Parent=g2.getLink().getParent();
                    
                }
            
        }
            
        return root;
    }

    
    
    
     //if the link is newly created. i.e. link.creationTime > link(parent,sub).SummaryTime 
                //                                                     OR 
                //                                   link.creationTime > link(child,sub)).SummaryTime
                /**
                 *    Get all ancestors containing Subsurface 'sub' : Set: A.
                 *      for a : A
                 *          D = List of Doubts for (a,sub)   
                 *              for( cause : D)
                 *                  if(cause.doubtstatus.state!= WARNING && cause.type!=INHERITED)            //warning doubt types are not inherited. Inherited doubt types are not inherited         
                 *                    if(inherited doubt does not exist for (cause,sub) in job=l.child)
                 *                        then create doubt.type=INH
                 *                                    doubt.cause=cause;
                 *                                    doubt.child_job=l.child
                 *                                    create doubt
                 *                                       Add doubt to cause.setOfInheritedDoubts();
                 *                                       update cause
                 *                                    create doubtstatus
                 *              
                 *                           
                 **/
    
    private void inheritDoubtFromAncestors(Link l,Subsurface sub, List<Ancestor> ancestorsThatContainSub) {
        System.out.println("fend.workspace.WorkspaceController.inheritDoubtFromAncestors()  "+l.getId()+"  for sub "+sub.getSubsurface()+
                " Number of ancestors: "+ancestorsThatContainSub.size());
        for(Ancestor anc:ancestorsThatContainSub){
            List<Doubt> doubtsInAncestor=doubtService.getDoubtFor(sub, anc.getAncestor());
            if(doubtsInAncestor==null) continue;    //no doubt in current desc for current sub. check next desc
             SummaryKey summaryKey=generateSummaryKey(sub, anc.getAncestor());
             Summary summaryforCurrentJob;
                                    if(!summaryMap.containsKey(summaryKey)){
            
                                        /* if((summaryforCurrentJob=summaryService.getSummaryFor(sub,l.getChild()))==null){*/
                                 summaryforCurrentJob = new Summary();
                                summaryforCurrentJob.setSequence(sub.getSequence());
                                summaryforCurrentJob.setSubsurface(sub);
                                summaryforCurrentJob.setJob(l.getChild());
                                summaryforCurrentJob.setWorkspace(dbWorkspace);
                                //summaryService.createSummary(summaryforCurrentJob);
                                newSummaries.add(summaryforCurrentJob);
                                
                            }else{
                                        summaryforCurrentJob=summaryMap.get(summaryKey);
                                    }
            
            
            for(Doubt cause:doubtsInAncestor){
                Doubt inheritedDoubtInCurrentJob;
                //if cause.getDoubtStatus.getState!=Warning AND cause.getDoubtType!=Inherited then proceed to creating an inherited doubt
                if(cause.getDoubtType().equals(doubtTypeInherit)) {
                    System.out.println("fend.workspace.WorkspaceController.inheritDoubtFromAncestors() Skipping cause :"+cause.getId()+" of type: "+cause.getDoubtType().getName() );
                    continue;
                }
                //DoubtStatus causeDs=new ArrayList<>(cause.getDoubtStatuses()).get(0);
                List<DoubtStatus> dx=new ArrayList<>();
                            if(doubtDoubtStatusMap.containsKey(cause)){
                                dx=doubtDoubtStatusMap.get(cause);
                            }
                //DoubtStatus causeDs=doubtStatusService.getDoubtStatusForDoubt(cause).get(0);
                DoubtStatus causeDs=dx.get(0);
                if(causeDs.getState().equals(DoubtStatusModel.WARNING))
                {
                    System.out.println("fend.workspace.WorkspaceController.inheritDoubtFromAncestors() Skipping cause :"+cause.getId()+" of type "+cause.getDoubtType().getName() +" in state : "+causeDs.getState());
                    continue;
                }
                
                InheritanceKey key=generateInheritanceKey(l.getChild(), sub, cause);
                if(!inheritanceMap.containsKey(key)){
                //if((inheritedDoubtInCurrentJob=doubtService.getDoubtFor(sub, l.getChild(),cause, doubtTypeInherit))==null){
                    inheritedDoubtInCurrentJob=new Doubt();
                    inheritedDoubtInCurrentJob.setChildJob(l.getChild());
                    inheritedDoubtInCurrentJob.setDoubtCause(cause);
                    inheritedDoubtInCurrentJob.setDoubtType(doubtTypeInherit);
                    inheritedDoubtInCurrentJob.setSequence(sub.getSequence());
                    inheritedDoubtInCurrentJob.setSubsurface(sub);
                    
                   // doubtService.createDoubt(inheritedDoubtInCurrentJob);
                   inheritedDoubts.add(inheritedDoubtInCurrentJob);
                   inheritanceMap.put(key, inheritedDoubtInCurrentJob);
                     if(cause.getDoubtType().equals(doubtTypeTime)){
                                     summaryforCurrentJob.setTimeInheritanceSummary(true);
                                }
                                if(cause.getDoubtType().equals(doubtTypeTraces)){
                                     summaryforCurrentJob.setTraceInheritanceSummary(true);
                                }
                                if(cause.getDoubtType().equals(doubtTypeQc)){
                                     summaryforCurrentJob.setQcInheritanceSummary(true);
                                }
                                
                                
                                DoubtStatus doubtStatus=new DoubtStatus();
                                doubtStatus.setReason(DoubtStatusModel.getNewDoubtInheritanceMessage(anc.getAncestor().getNameJobStep(),sub.getSubsurface(), l.getChild().getNameJobStep(), doubtTypeInherit.getName()));
                                doubtStatus.setDoubt(inheritedDoubtInCurrentJob);
                                doubtStatus.setStatus(DoubtStatusModel.YES);
                                doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                                //doubtStatus.setUser(user);
                               // doubtStatusService.createDoubtStatus(doubtStatus);
                               inheritedDoubtStatus.add(doubtStatus);
                               // inheritedDoubtInCurrentJob.addToDoubtStatuses(doubtStatus);
                                //doubtService.updateDoubt(inheritedDoubtInCurrentJob.getId(), inheritedDoubtInCurrentJob);
                               // doubtsToBeUpdated.add(inheritedDoubtInCurrentJob);

                              //  cause.addToInheritedDoubts(inheritedDoubtInCurrentJob);
                                //doubtService.updateDoubt(cause.getId(), cause);
                              //  doubtsToBeUpdated.add(cause);
                               // summaryService.updateSummary(summaryforCurrentJob.getId(), summaryforCurrentJob);
                          //     summariesToBeUpdated.add(summaryforCurrentJob);
                }else{
                    inheritedDoubtInCurrentJob=inheritanceMap.get(key);
                    System.out.println("fend.workspace.WorkspaceController.inheritDoubtFromAncestors(): inherited doubt already present inside job:"
                            + " "+inheritedDoubtInCurrentJob.getChildJob().getNameJobStep()+" for sub: "+inheritedDoubtInCurrentJob.getSubsurface().getSubsurface()+" "
                                    + " INH_id: "+inheritedDoubtInCurrentJob.getId() +" with cause id: "+inheritedDoubtInCurrentJob.getDoubtCause().getId());
                    if(cause.getDoubtType().equals(doubtTypeTime)){
                                     summaryforCurrentJob.setTimeInheritanceSummary(true);
                                }
                                if(cause.getDoubtType().equals(doubtTypeTraces)){
                                     summaryforCurrentJob.setTraceInheritanceSummary(true);
                                }
                                if(cause.getDoubtType().equals(doubtTypeQc)){
                                     summaryforCurrentJob.setQcInheritanceSummary(true);
                                }
                             //   summariesToBeUpdated.add(summaryforCurrentJob);
                    
                   // summaryService.updateSummary(summaryforCurrentJob.getId(), summaryforCurrentJob);
                    
                }
            }
            
            
        }
        
        
        /* String summaryTime=DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
        SubsurfaceJob dbSubChildjob=subsurfaceJobService.getSubsurfaceJobFor(l.getChild(), sub);
        dbSubChildjob.setSummaryTime(summaryTime);
        subsurfaceJobService.updateSubsurfaceJob(dbSubChildjob);    */
                            
                            /*SubsurfaceJob dbSubParentjob=subsurfaceJobService.getSubsurfaceJobFor(l.getParent(), sub);
                            dbSubParentjob.setSummaryTime(summaryTime);
                            subsurfaceJobService.updateSubsurfaceJob(dbSubParentjob);    */
    }
    
    
     private String timeNow(){
        return DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
    }

   
     /**
      * 
      * Keys for summary Map (summarizeInMemory())
      **/
     
     private class SummaryKey{
         Subsurface subsurface;
         Job job;

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 11 * hash + Objects.hashCode(this.subsurface);
            hash = 11 * hash + Objects.hashCode(this.job);
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
            final SummaryKey other = (SummaryKey) obj;
            if (!Objects.equals(this.subsurface, other.subsurface)) {
                return false;
            }
            if (!Objects.equals(this.job, other.job)) {
                return false;
            }
            return true;
        }
         
         
     }
     
     
     
     /**
      * 
      *  Keys for Doubt Map (summarizeInMemory())
      **/
     
     
     private class DoubtKey{
         Subsurface subsurface;
         Job job;
         DoubtType doubtType;
         Dot dot;

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 19 * hash + Objects.hashCode(this.subsurface);
            hash = 19 * hash + Objects.hashCode(this.job);
            hash = 19 * hash + Objects.hashCode(this.doubtType);
            hash = 19 * hash + Objects.hashCode(this.dot);
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
            final DoubtKey other = (DoubtKey) obj;
            if (!Objects.equals(this.subsurface, other.subsurface)) {
                return false;
            }
            if (!Objects.equals(this.job, other.job)) {
                return false;
            }
            if (!Objects.equals(this.doubtType, other.doubtType)) {
                return false;
            }
            if (!Objects.equals(this.dot, other.dot)) {
                return false;
            }
            return true;
        }
         
         
         
     }
     
    private  class HeaderKey {
        Subsurface subsurface;
        Job job;

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 13 * hash + Objects.hashCode(this.subsurface);
            hash = 13 * hash + Objects.hashCode(this.job);
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
            final HeaderKey other = (HeaderKey) obj;
            if (!Objects.equals(this.subsurface, other.subsurface)) {
                return false;
            }
            if (!Objects.equals(this.job, other.job)) {
                return false;
            }
            return true;
        }
        
        
   
    }
     
    
    private class SubsurfaceJobKey{
        Subsurface subsurface;
        Job job;

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 29 * hash + Objects.hashCode(this.subsurface);
            hash = 29 * hash + Objects.hashCode(this.job);
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
            final SubsurfaceJobKey other = (SubsurfaceJobKey) obj;
            if (!Objects.equals(this.subsurface, other.subsurface)) {
                return false;
            }
            if (!Objects.equals(this.job, other.job)) {
                return false;
            }
            return true;
        }
        
        
    }
    
    
    
    private class AncestorKey{
        Job job;
        Subsurface subsurface;

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 23 * hash + Objects.hashCode(this.job);
            hash = 23 * hash + Objects.hashCode(this.subsurface);
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
            final AncestorKey other = (AncestorKey) obj;
            if (!Objects.equals(this.job, other.job)) {
                return false;
            }
            if (!Objects.equals(this.subsurface, other.subsurface)) {
                return false;
            }
            return true;
        }
        
        
    }
    
    
    private class DescendantKey{
         Job job;
        Subsurface subsurface;

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + Objects.hashCode(this.job);
            hash = 71 * hash + Objects.hashCode(this.subsurface);
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
            final DescendantKey other = (DescendantKey) obj;
            if (!Objects.equals(this.job, other.job)) {
                return false;
            }
            if (!Objects.equals(this.subsurface, other.subsurface)) {
                return false;
            }
            return true;
        }

       
        
    }
     
     private class InheritanceKey{
         Job job;
         Subsurface subsurface;
         Doubt cause;

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + Objects.hashCode(this.job);
            hash = 71 * hash + Objects.hashCode(this.subsurface);
            hash = 71 * hash + Objects.hashCode(this.cause);
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
            final InheritanceKey other = (InheritanceKey) obj;
            if (!Objects.equals(this.job, other.job)) {
                return false;
            }
            if (!Objects.equals(this.subsurface, other.subsurface)) {
                return false;
            }
            if (!Objects.equals(this.cause, other.cause)) {
                return false;
            }
            return true;
        }
         
         
     }
     private SummaryKey generateSummaryKey(Subsurface sub,Job job){
         SummaryKey key=new SummaryKey();
         key.subsurface=sub;
         key.job=job;
         
         return key;
     }
     
     private DoubtKey generateDoubtKey(Subsurface sub,Job job,Dot dot,DoubtType doubtType){
         DoubtKey key=new DoubtKey();
            key.subsurface=sub;
            key.job=job;
            key.dot=dot;
            key.doubtType=doubtType;
            
            return key;
     }
        
     private HeaderKey generateHeaderKey(Job job,Subsurface sub){
         HeaderKey key=new HeaderKey();
         key.job=job;
         key.subsurface=sub;
         
         return key;
     }
     
     private SubsurfaceJobKey generateSubsurfaceJobKey(Job job,Subsurface sub){
         SubsurfaceJobKey key=new SubsurfaceJobKey();
         key.subsurface=sub;
         key.job=job;
         
         return  key;
     }
     
     
     private AncestorKey generateAncestorKey(Job job,Subsurface sub){
         AncestorKey key = new AncestorKey();
         key.job=job;
         key.subsurface=sub;
         
         return key;
                
     }
     
     private DescendantKey generateDescendantKey(Job job, Subsurface sub){
         DescendantKey key = new DescendantKey();
         key.job=job;
         key.subsurface=sub;
         
         return key;
     }
     
     
     private InheritanceKey generateInheritanceKey(Job job,Subsurface sub,Doubt cause){
         InheritanceKey key =new InheritanceKey();
         key.job=job;
         key.subsurface=sub;
         key.cause=cause;
         
         return key;
                 
     }
    
     
     private int processorsUsed() throws Exception{
        int procsUsed=(int) (Runtime.getRuntime().availableProcessors()*percentageOfProcessorsUsed);
               if(procsUsed <= 1){
                   
                   System.out.println("fend.workspace.WorkspaceController.processorsUsed(): Not enough resources . PR-TCount: "+procsUsed);
                   throw new Exception("Not enough resources . PR-TCount: "+procsUsed);
               }
        
               return procsUsed;
    }
    
}
