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
import db.model.NodeProperty;
import db.model.NodePropertyValue;
import db.model.NodeType;
import db.model.PropertyType;
import db.model.QcMatrixRow;
import db.model.QcTable;
import db.model.Subsurface;
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
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import db.services.SummaryService;
import db.services.SummaryServiceImpl;
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
import fend.job.job0.definitions.JobDefinitionsModel;
import fend.job.job0.definitions.JobDefinitionsView;
import fend.job.job0.definitions.qcmatrix.QcMatrixModel;
import fend.job.job0.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModelParent;
import fend.job.job0.definitions.volume.VolumeListModel;
import fend.job.job0.definitions.volume.VolumeListView;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    private List<BooleanProperty> changePropertyList = new ArrayList<>();

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
        Long typeOfJob = JobType0Model.PROCESS_2D;
        NodeType nodetype = nodeTypeService.getNodeTypeObjForType(typeOfJob);
        dbjob.setNodetype(nodetype);
        dbjob.setWorkspace(dbWorkspace);

        dbjob.setDepth(JobType0Model.INITIAL_DEPTH);
        jobService.createJob(dbjob);

        
        
        
        
        JobType1Model job = new JobType1Model(this.model);
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
        JobType1View jobview = new JobType1View(job, interactivePane);
        interactivePane.getChildren().add(jobview);
//        System.out.println("workspace.WorkspaceController.addBox(): "+g1Child.getId()%100);

            
            

    }
    
    @FXML
    void addSEGD(ActionEvent event) {
        Job dbjob = new Job();
        Long typeOfJob = JobType0Model.SEGD_LOAD;
        NodeType nodetype = nodeTypeService.getNodeTypeObjForType(typeOfJob);
        dbjob.setNodetype(nodetype);
        dbjob.setWorkspace(dbWorkspace);

        dbjob.setDepth(JobType0Model.INITIAL_DEPTH);
        jobService.createJob(dbjob);

        JobType2Model job = new JobType2Model(this.model);
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
        JobType2View jobview = new JobType2View(job, interactivePane);
        interactivePane.getChildren().add(jobview);
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
            
        
                SummaryModel summaryModel=new SummaryModel(this);
                SummaryView summaryView=new SummaryView(summaryModel);
        
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
                Summary summary;
                if ((summary = summaryService.getSummaryFor(subb.getSequence(), l.getParent())) == null) {            //create an entry for parent summary
                    summary = new Summary();
                    summary.setSequence(subb.getSequence());
                    summary.setJob(l.getParent());
                    summaryService.createSummary(summary);
                }
                if ((summary = summaryService.getSummaryFor(subb.getSequence(), l.getChild())) == null) {             //create an entry for child summary 
                    summary = new Summary();
                    summary.setSequence(subb.getSequence());
                    summary.setJob(l.getChild());
                    summaryService.createSummary(summary);
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

                            summary.setTraceSummary(true);
                        } else {
                            doubt = doubtService.getDoubtFor(subb, l.getChild(), dot, doubtTypeTraces);
                            summary.setTraceSummary(true);
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
                                summary.setTraceSummary(true);

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
                                summary.setTraceSummary(true);
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

                        summary.setQcSummary(true);
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
                        summary.setQcSummary(true);
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
                        summary.setTimeSummary(true);
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
                        summary.setTimeSummary(true);
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
                summary.setInsightSummary(true);
                
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
                summary.setInsightSummary(true);
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
                            summary.setInheritanceSummary(true);
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

                summaryService.updateSummary(summary.getId(), summary);
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
        dbWorkspace = workspaceService.getWorkspace(model.getId());
        File insightLocation = new File(AppProperties.INSIGHT_LOCATION);
        File[] insights = insightLocation.listFiles(insightFilter);
        List<String> insightVersionStrings = new ArrayList<>();
        System.out.println("fend.workspace.WorkspaceController.setModel(): No of insights found: "+insightVersionStrings.size());
        for (File insight : insights) {
            System.out.println("fend.workspace.WorkspaceController.setModel(): insightVersions Found: " + insight.getName());
            insightVersionStrings.add(insight.getName());
        }
        model.setInsightVersions(insightVersionStrings);

    }

    public WorkspaceModel getModel() {
        return model;
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
        String name = model.getName().get();

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

            workspaceService.createWorkspace(dbWorkspace);

            for (JobType0Model fejob : jobsInWorkSpace) {
                Job dbjob;
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
                    
                    List<NodePropertyValue> npValues=nodePropertyValueService.getNodePropertyValuesFor(dbjob);
                   
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
                        
                    }

                }

                /**
                 * *
                 * Volumes in the job
                 */
                List<Volume0> fevolsinFejob = fejob.getVolumes();
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

                dbjob.setVolumes(dbVolumesForCurrentJob);

                /**
                 * *
                 * QcMatrix for the job. QcMatrix are commited/updated straight
                 * to the db based on UI
              *
                 */
                List<QcMatrixRow> qcmatrixForJob = qcMatrixRowService.getQcMatrixForJob(dbjob);
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

                    Job anc = jobService.getJob(feAncestorJob.getId());
                    Ancestor ancestor = new Ancestor();
                    ancestor.setJob(dbjob);
                    ancestor.setAncestor(anc);
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

            for (Job dbjob : dbjobs) {
                System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): updating job " + dbjob.getId());

                jobService.updateJob(dbjob.getId(), dbjob);
            }
            for (Volume dbVol : dbVolumes) {
                volumeService.updateVolume(dbVol.getId(), dbVol);
            }

            workspaceService.updateWorkspace(dbWorkspace.getId(), dbWorkspace);
        } else {
            System.out.println("fend.workspace.WorkspaceController.saveWorkspace(): Workspace is missing a name. Unsaved!");
        }

    }

    private void loadSession() {
        Workspace dbWorkspace = workspaceService.getWorkspace(model.getId());

        Set<Job> jobsInDb = dbWorkspace.getJobs();
        List<JobType0Model> frontEndJobModels = new ArrayList<>();

        Map<Long, JobType0Model> idFrontEndJobMap = new HashMap<>();              //used to link the nodes later. alook up map

        for (Job dbj : jobsInDb) {
            JobType0Model fejob = null;

            Long type = dbj.getNodetype().getActualnodeid();
            if (type.equals(JobType0Model.PROCESS_2D)) {
                fejob = new JobType1Model(model);
                fejob.setId(dbj.getId());
                fejob.setNameproperty(dbj.getNameJobStep());
                fejob.setDepth(dbj.getDepth());

                System.out.println("fend.workspace.WorkspaceController.loadSession(): Added job: " + dbj.getNameJobStep());

            }
            if (type.equals(JobType0Model.SEGD_LOAD)) {
                fejob = new JobType2Model(model);
                fejob.setId(dbj.getId());
                fejob.setNameproperty(dbj.getNameJobStep());
                fejob.setDepth(dbj.getDepth());

                System.out.println("fend.workspace.WorkspaceController.loadSession(): Added job: " + dbj.getNameJobStep());

            }
            if (type.equals(JobType0Model.ACQUISITION)) {
                fejob = new JobType3Model(model);
                fejob.setId(dbj.getId());
                fejob.setNameproperty(dbj.getNameJobStep());
                fejob.setDepth(dbj.getDepth());

                System.out.println("fend.workspace.WorkspaceController.loadSession(): Added job: " + dbj.getNameJobStep());

            }
             if (type.equals(JobType0Model.TEXT)) {
                fejob = new JobType4Model(model);
                fejob.setId(dbj.getId());
                fejob.setNameproperty(dbj.getNameJobStep());
                fejob.setDepth(dbj.getDepth());

                System.out.println("fend.workspace.WorkspaceController.loadSession(): Added job: " + dbj.getNameJobStep());

            }

            Set<Volume> dbvols = dbj.getVolumes();
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

        Set<Dot> dots = dbWorkspace.getDots();
        System.out.println("fend.workspace.WorkspaceController.loadSession(): the size of  dots retrieved : " + dots.size());
        List<DotModel> frontEndDotModels = new ArrayList<>();

        for (Dot dot : dots) {
            DotModel fedot = new DotModel(model);
            Set<Link> links = dot.getLinks();
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

    public void summarize() throws Exception{
        
        
         ExecutorService executorService= Executors.newFixedThreadPool(1);
                executorService.submit(new Callable<Void>() {
                     @Override
                public Void call() throws Exception {
        
        
        
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
                Summary summary;
                //if ((summary = summaryService.getSummaryFor(subb.getSequence(), l.getParent())) == null) {            //create an entry for parent summary
                if ((summary = summaryService.getSummaryFor(subb, l.getParent())) == null) {            //create an entry for parent summary    
                    summary = new Summary();
                    summary.setSequence(subb.getSequence());
                    summary.setSubsurface(subb);
                    summary.setJob(l.getParent());
                    summary.setWorkspace(dbWorkspace);
                    summaryService.createSummary(summary);
                }
                //if ((summary = summaryService.getSummaryFor(subb.getSequence(), l.getChild())) == null) {             //create an entry for child summary 
                if ((summary = summaryService.getSummaryFor(subb, l.getChild())) == null) {             //create an entry for child summary 
                    summary = new Summary();
                    summary.setSequence(subb.getSequence());
                    summary.setSubsurface(subb);
                    summary.setJob(l.getChild());
                    summary.setWorkspace(dbWorkspace);
                    summaryService.createSummary(summary);
                }

                //dependency
                
               List<Descendant> descendantsThatContainSub=descendantService.getDescendantsForJobContainingSub(l.getChild(), subb);
               
                System.out.println(".call(): size of descendants of job: id: "+l.getChild().getId()+" name: "+l.getChild().getNameJobStep()+" that contain: "+subb.getSubsurface()+" == "+descendantsThatContainSub.size());
                Dot dot = l.getDot();                     //the dot to which the link belongs\\
               
                    checkForDependencyDoubts(l,dot,mapForVariableSetting,variableSet,argumentSet,subb,summary,descendantsThatContainSub);
                
                
                /*
                DoubtType doubtTypeTraces = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TRACES);
                System.out.println("fend.workspace.WorkspaceController.getSummary(): inside Link : " + l.getId() + " " + l.getParent().getNameJobStep() + "--->" + l.getChild().getNameJobStep());
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
                            doubt.setLink(l);
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

                            summary.setTraceSummary(true);
                        } else {
                            doubt = doubtService.getDoubtFor(subb, l.getChild(), dot, doubtTypeTraces);
                            summary.setTraceSummary(true);
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
                                doubt.setLink(l);
                                doubt.setDoubtType(doubtTypeTraces);
                                //doubt.setUser(user);
                                doubtService.createDoubt(doubt);
                                summary.setTraceSummary(true);

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
                                summary.setTraceSummary(true);
                                for (DoubtStatus ds : doubtStatuses) {
                                    System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: " + doubt.getId() + " " + ds.getStatus() + " comment: " + ds.getComment() + " time: " + ds.getTimeStamp());
                                }

                            }
                        }
                    }

                }
                */
                //dependency end    <--put inside a function. 
                //qc start  <--put inside a function. 
                checkForQcDoubts(l,dot,subb,summary,descendantsThatContainSub);
                /*DoubtType doubtTypeQc = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.QC);
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
                doubt.setLink(l);
                doubt.setSubsurface(subb);
                doubt.setSequence(subb.getSequence());
                doubt.setDoubtType(doubtTypeQc);
                //doubt.setUser(user)
                doubtService.createDoubt(doubt);
                
                summary.setQcSummary(true);
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
                summary.setQcSummary(true);
                for (DoubtStatus d : doubtStatuses) {
                System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: " + doubt.getId() + " " + d.getStatus() + " comment: " + d.getComment() + " time: " + d.getTimeStamp());
                }
                }
                }*/
                //qc end <--put inside a function
                //time start
                checkForTimeDoubts(l, dot, subb, summary,descendantsThatContainSub);
                /*DoubtType doubtTypeTime = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TIME);
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
                doubt.setLink(l);
                doubt.setSubsurface(subb);
                doubt.setSequence(subb.getSequence());
                doubt.setDoubtType(doubtTypeTime);
                //doubt.setUser(user);
                doubtService.createDoubt(doubt);
                summary.setTimeSummary(true);
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
                summary.setTimeSummary(true);
                for (DoubtStatus d : doubtStatuses) {
                System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: " + doubt.getId() + " " + d.getStatus() + " comment: " + d.getComment() + " time: " + d.getTimeStamp());
                }
                }
                
                } else {
                //do nothing
                }*/
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
                summary.setInsightSummary(true);
                
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
                summary.setInsightSummary(true);
                for(DoubtStatus d:doubtStatuses){
                System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: "+doubt.getId()+" "+d.getStatus()+" comment: "+d.getComment()+" time: "+d.getTimeStamp());
                }
                }
                }
                //insight-end <--put inside a function
                
                 */
                //inheritance   <--called at the very end. Put all doubts on top
                /*Job inhParent = l.getParent();
                Job inhChild = l.getChild();
                System.out.println("fend.workspace.WorkspaceController.summarize(): Checking for inheritance. "+inhParent.getNameJobStep()+" <----> "+inhChild.getNameJobStep());
                DoubtType doubtTypeInherit = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.INHERIT);
                List<Doubt> doubtsInParent = doubtService.getDoubtFor(subb, inhParent, dot);
                
                if (doubtsInParent != null) {
                for (Doubt cause : doubtsInParent) {
                Set<Doubt> inherited = cause.getInheritedDoubts();
                System.err.println(" inherited DoubtSet . size: "+inherited.size());
                if (inherited != null && inherited.size() > 0) {
                
                continue;                   //do nothing
                } else {
                Doubt inhDoubt = new Doubt();
                inhDoubt.setDoubtType(doubtTypeInherit);
                inhDoubt.setChildJob(inhChild);
                inhDoubt.setDot(dot);
                inhDoubt.setLink(l);
                inhDoubt.setDoubtCause(cause);
                inhDoubt.setSubsurface(subb);
                inhDoubt.setSequence(subb.getSequence());
                //inhDoubt.setUser(user);
                doubtService.createDoubt(inhDoubt);
                summary.setInheritanceSummary(true);
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
                else{
                System.out.println("fend.workspace.WorkspaceController.summarize(): doubtListFromParent g1Child "+inhParent.getNameJobStep()+" is null.. implies no doubts to be inherited");
                }*/
                //inheritance-end <--put inside a function
                
                summaryService.updateSummary(summary.getId(), summary);
            }
        }

        /*
        }catch(Exception e){
        System.out.println("fend.workspace.WorkspaceController.getSummary(): Exception "+e.getLocalizedMessage());
        }*/
        
        return null;
                }}).get();
    }

    private void checkForTimeDoubts(Link l,Dot dot,Subsurface subb,Summary summary,List<Descendant> descendantsThatContainSub) throws Exception {
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
                        doubt.setLink(l);
                        doubt.setSubsurface(subb);
                        doubt.setSequence(subb.getSequence());
                        doubt.setDoubtType(doubtTypeTime);
                        //doubt.setUser(user);
                        doubtService.createDoubt(doubt);
                        summary.setTimeSummary(true);
                        DoubtStatus doubtStatus = new DoubtStatus();
                        doubtStatus.setReason(DoubtStatusModel.getNewDoubtTimeMessage(hparent.getNameJobStep(), new String(hpt + ""), hchild.getNameJobStep(), new String(hct + ""), subb.getSubsurface(), doubtTypeTime.getName()));
                        doubtStatus.setDoubt(doubt);
                        doubtStatus.setStatus(DoubtStatusModel.YES);
                        doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                        //doubtStatus.setUser(user);
                        doubtStatusService.createDoubtStatus(doubtStatus);
                        doubt.addToDoubtStatuses(doubtStatus);
                        doubtService.updateDoubt(doubt.getId(), doubt);
                        inherit(l,dot,subb,doubt,descendantsThatContainSub);
                    } else {
                        Set<DoubtStatus> doubtStatuses = doubt.getDoubtStatuses();
                        summary.setTimeSummary(true);
                        for (DoubtStatus d : doubtStatuses) {
                            System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: " + doubt.getId() + " " + d.getStatus() + " comment: " + d.getReason() + " time: " + d.getTimeStamp());
                        }
                    }

                } else {
                    //do nothing
                    System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts()");
                    System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts() "+l.getParent().getNameJobStep()+ "<----->"+l.getChild().getNameJobStep()+" has passed the check for time. Checking for any existing doubt entries..so as to clear them");
                    Doubt existingDoubt;
                    if((existingDoubt=doubtService.getDoubtFor(subb, l.getChild(), dot, doubtTypeTime))==null){
                        //do nothing ..as no doubt exists
                        System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): No doubts exist for job: "+l.getChild().getNameJobStep()+" sub: "+subb.getSubsurface()+" for type: "+doubtTypeTime.getName());
                    }else{
                        //get all doubtStatus for the existing doubt
                        System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): found an existing doubt to a previous failed condition: id. "+existingDoubt.getId());
                        Set<DoubtStatus> doubtStatus=existingDoubt.getDoubtStatuses();
                        for(DoubtStatus doubtStat:doubtStatus){
                            System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): deleting doubtstatus: "+doubtStat.getId() );
                            doubtStatusService.deleteDoubtStatus(doubtStat.getId());
                        }
                        System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): all doubtstatus messages related to "+existingDoubt.getId()+" have now been deleted.");
                        System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): Checking to see if there were any inherited doubts related to doubt id: "+existingDoubt.getId());
                        
                        Set<Doubt> inheritedDoubts=existingDoubt.getInheritedDoubts();
                        for(Doubt inheritedDoubt:inheritedDoubts){
                            System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): found inherited doubt id: "+inheritedDoubt.getId()+" with existingCause Id: "+existingDoubt.getId());
                            
                            Set<DoubtStatus> inheritedDoubtStatus=inheritedDoubt.getDoubtStatuses();
                                for(DoubtStatus inhdbt:inheritedDoubtStatus){
                                    System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): found doubtstatus id: "+inhdbt.getId()+" related to the inherited doubt :"+inheritedDoubt.getId()+""
                                            + "\nDeleting doubtstatus: "+inhdbt.getId());
                                    doubtStatusService.deleteDoubtStatus(inhdbt.getId());
                                }
                                System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): all doubtStatus related to "+inheritedDoubt.getId()+" have now been deleted");
                                System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): Deleting inherited id: "+inheritedDoubt.getId());
                            doubtService.deleteDoubt(inheritedDoubt.getId());
                            
                        }
                        System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): all inherited doubts cleared for id: "+existingDoubt.getId()+"\nDeleting id: "+existingDoubt.getId());
                        doubtService.deleteDoubt(existingDoubt.getId());
                    }
                    System.out.println("fend.workspace.WorkspaceController.checkForTimeDoubts(): Setting summary to false for job: "+l.getChild().getNameJobStep()+" sub: "+subb.getSubsurface());
                    summary.setTimeSummary(false);
                    
                    
                }
    }

    private void checkForDependencyDoubts(Link l, Dot dot, Map<String, Double> mapForVariableSetting, Set<String> variableSet, Set<Job> argumentSet, Subsurface subb, Summary summary,List<Descendant> descendantsThatContainSub) throws Exception {
        
        
        
        DoubtType doubtTypeTraces = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TRACES);
                System.out.println("fend.workspace.WorkspaceController.getSummary(): inside Link : " + l.getId() + " " + l.getParent().getNameJobStep() + "--->" + l.getChild().getNameJobStep());
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
                    //check for any existing doubt.
                    //clear tracedependency flag.
                    //clear all doubtstatus messages related to doubt
                    //remove all inherited doubts ( and associated doubt status messages) if present
                    
                    //change summary.trace= false
                   
                    System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts() "+l.getParent().getNameJobStep()+ "<----->"+l.getChild().getNameJobStep()+" has passed dependency. Checking for any existing doubt entries..so as to clear them");
                    Doubt existingDoubt;
                    if((existingDoubt=doubtService.getDoubtFor(subb, l.getChild(), dot, doubtTypeTraces))==null){
                        //do nothing ..as no doubt exists
                        System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): No doubts exist for job: "+l.getChild().getNameJobStep()+" sub: "+subb.getSubsurface()+" for type: "+doubtTypeTraces.getName());
                    }else{
                        //get all doubtStatus for the existing doubt
                        System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): found an existing doubt to a previous failed condition: id. "+existingDoubt.getId());
                        Set<DoubtStatus> doubtStatus=existingDoubt.getDoubtStatuses();
                        for(DoubtStatus doubtStat:doubtStatus){
                            System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): deleting doubtstatus: "+doubtStat.getId() );
                            doubtStatusService.deleteDoubtStatus(doubtStat.getId());
                        }
                        System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): all doubtstatus messages related to "+existingDoubt.getId()+" have now been deleted.");
                        System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): Checking to see if there were any inherited doubts related to doubt id: "+existingDoubt.getId());
                        
                        Set<Doubt> inheritedDoubts=existingDoubt.getInheritedDoubts();
                        for(Doubt inheritedDoubt:inheritedDoubts){
                            System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): found inherited doubt id: "+inheritedDoubt.getId()+" with existingCause Id: "+existingDoubt.getId());
                            
                            Set<DoubtStatus> inheritedDoubtStatus=inheritedDoubt.getDoubtStatuses();
                                for(DoubtStatus inhdbt:inheritedDoubtStatus){
                                    System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): found doubtstatus id: "+inhdbt.getId()+" related to the inherited doubt :"+inheritedDoubt.getId()+""
                                            + "\nDeleting doubtstatus: "+inhdbt.getId());
                                    doubtStatusService.deleteDoubtStatus(inhdbt.getId());
                                }
                                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): all doubtStatus related to "+inheritedDoubt.getId()+" have now been deleted");
                                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): Deleting inherited id: "+inheritedDoubt.getId());
                            doubtService.deleteDoubt(inheritedDoubt.getId());
                            
                        }
                        System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): all inherited doubts cleared for id: "+existingDoubt.getId()+"\nDeleting id: "+existingDoubt.getId());
                        doubtService.deleteDoubt(existingDoubt.getId());
                    }
                    System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): Setting summary to false for job: "+l.getChild().getNameJobStep()+" sub: "+subb.getSubsurface());
                    summary.setTraceSummary(false);
                    
                    
                    
                } else if ((evaluated <= error && evaluated > tolerance) || (evaluated > error)) {
                    System.out.println("fend.workspace.WorkspaceController.getSummary(): creating doubt");
                    String dotState = dot.getStatus();
                    /*
                    
                    Use this redFlag to set summary to either null or true . Summary=null will show up red on the summary table. Summary=true will show up as a warning(milder) color
                    
                    Boolean redFlag;
                    if(evaluated > error){
                    redFlag=true;
                    }else{
                    redFlag=false;
                    }*/
                    Doubt doubt;

                    if (dotState.equals(DotModel.JOIN)) {
                        if ((doubtService.getDoubtFor(subb, l.getChild(), dot, doubtTypeTraces)) == null) {
                            System.out.println("fend.workspace.WorkspaceController.getSummary(): creating doubt entry for " + subb.getSubsurface() + " job: " + l.getChild().getId() + " : " + l.getChild().getNameJobStep());
                            doubt = new Doubt();
                            doubt.setChildJob(l.getChild());
                            doubt.setSubsurface(subb);
                            doubt.setSequence(subb.getSequence());
                            doubt.setDot(dot);
                            doubt.setLink(l);
                            doubt.setDoubtType(doubtTypeTraces);
                            //doubt.setUser(user);
                            doubtService.createDoubt(doubt);

                            DoubtStatus doubtStatus = new DoubtStatus();
                            doubtStatus.setReason(DoubtStatusModel.getNewDoubtTraceMessage(function, tolerance, error, evaluated, y, doubtTypeTraces.getName()));
                            doubtStatus.setDoubt(doubt);
                            doubtStatus.setStatus(DoubtStatusModel.YES);
                            doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                            //doubtStatus.setUser(user);
                            doubtStatusService.createDoubtStatus(doubtStatus);
                            doubt.addToDoubtStatuses(doubtStatus);
                            doubtService.updateDoubt(doubt.getId(), doubt);

                            summary.setTraceSummary(true);
                            inherit(l,dot,subb,doubt,descendantsThatContainSub);
                        } else {
                            doubt = doubtService.getDoubtFor(subb, l.getChild(), dot, doubtTypeTraces);
                            summary.setTraceSummary(true);
                            Set<DoubtStatus> doubtStatuses = doubt.getDoubtStatuses();
                            for (DoubtStatus ds : doubtStatuses) {
                                System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses assosciated with Doubt: " + doubt.getId() + " " + ds.getStatus() + " comment: " + ds.getReason() + " time: " + ds.getTimeStamp());
                            }
                        }
                    } else {
                        for (Job child : argumentSet) {                     //In states SPLIT and NJS , the argument set comprises of only children. since P=f(C1,C2,..Cn);
                            if ((doubtService.getDoubtFor(subb, child, dot, doubtTypeTraces)) == null) {
                                System.out.println("fend.workspace.WorkspaceController.getSummary(): creating doubt entry for " + subb.getSubsurface() + " job: " + child.getId() + " : " + child.getNameJobStep());
                                doubt = new Doubt();
                                doubt.setChildJob(child);
                                doubt.setSubsurface(subb);
                                doubt.setSequence(subb.getSequence());
                                doubt.setDot(dot);
                                doubt.setLink(l);
                                doubt.setDoubtType(doubtTypeTraces);
                                //doubt.setUser(user);
                                doubtService.createDoubt(doubt);
                                summary.setTraceSummary(true);

                                DoubtStatus doubtStatus = new DoubtStatus();
                                doubtStatus.setReason(DoubtStatusModel.getNewDoubtTraceMessage(function, tolerance, error, evaluated, y, doubtTypeTraces.getName()));
                                doubtStatus.setDoubt(doubt);
                                doubtStatus.setStatus(DoubtStatusModel.YES);
                                doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                                //doubtStatus.setUser(user);
                                doubtStatusService.createDoubtStatus(doubtStatus);
                                doubt.addToDoubtStatuses(doubtStatus);
                                doubtService.updateDoubt(doubt.getId(), doubt);
                                summary.setTraceSummary(true);
                                System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): Setting Trace doubt inheritance on the descendants");
                                inherit(l,dot,subb,doubt,descendantsThatContainSub);
                            } else {
                                doubt = doubtService.getDoubtFor(subb, child, dot, doubtTypeTraces);
                                Set<DoubtStatus> doubtStatuses = doubt.getDoubtStatuses();
                                summary.setTraceSummary(true);
                                for (DoubtStatus ds : doubtStatuses) {
                                    System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: " + doubt.getId() + " " + ds.getStatus() + " comment: " + ds.getReason() + " time: " + ds.getTimeStamp());
                                }

                            }
                        }
                    }

                }
    }

    private void checkForQcDoubts(Link l, Dot dot, Subsurface subb, Summary summary,List<Descendant> descendantsThatContainSub) throws Exception {
        
        System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): for  "+l.getParent().getNameJobStep()+"<----->"+l.getChild().getNameJobStep());
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
                            doubt.setLink(l);
                        doubt.setSubsurface(subb);
                        doubt.setSequence(subb.getSequence());
                        doubt.setDoubtType(doubtTypeQc);
                        //doubt.setUser(user)
                        doubtService.createDoubt(doubt);

                        summary.setQcSummary(true);
                        DoubtStatus doubtStatus = new DoubtStatus();
                        doubtStatus.setReason(DoubtStatusModel.getNewDoubtQCcessage(lparent.getNameJobStep(), jchild.getNameJobStep(), subb.getSubsurface(), doubtTypeQc.getName()));
                        doubtStatus.setDoubt(doubt);
                        doubtStatus.setStatus(DoubtStatusModel.YES);
                        doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                        //doubtStatus.setUser(user);
                        doubtStatusService.createDoubtStatus(doubtStatus);
                        doubt.addToDoubtStatuses(doubtStatus);
                        doubtService.updateDoubt(doubt.getId(), doubt);
                        inherit(l, dot, subb,doubt, descendantsThatContainSub);

                    } else {
                        Set<DoubtStatus> doubtStatuses = doubt.getDoubtStatuses();
                        summary.setQcSummary(true);
                        for (DoubtStatus d : doubtStatuses) {
                            System.out.println("fend.workspace.WorkspaceController.getSummary(): doubstatuses associated with Doubt: " + doubt.getId() + " " + d.getStatus() + " comment: " + d.getReason() + " time: " + d.getTimeStamp());
                        }
                    }
                }else{
                    System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): "+lparent.getNameJobStep()+ " has passed all QC. Checking for any existing doubt entries..so as to clear them");
                    Doubt existingDoubt;
                    if((existingDoubt=doubtService.getDoubtFor(subb, jchild, dot, doubtTypeQc))==null){
                        //do nothing ..as no doubt exists
                        System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): No doubts exist for job: "+jchild.getNameJobStep()+" sub: "+subb.getSubsurface()+" for type: "+doubtTypeQc.getName());
                    }else{
                        //get all doubtStatus for the existing doubt
                        System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): found an existing doubt to a previous failed condition: id. "+existingDoubt.getId());
                        Set<DoubtStatus> doubtStatus=existingDoubt.getDoubtStatuses();
                        for(DoubtStatus doubtStat:doubtStatus){
                            System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): deleting doubtstatus: "+doubtStat.getId() );
                            doubtStatusService.deleteDoubtStatus(doubtStat.getId());
                        }
                        System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): all doubtstatus messages related to "+existingDoubt.getId()+" have now been deleted.");
                        System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): Checking to see if there were any inherited doubts related to doubt id: "+existingDoubt.getId());
                        
                        Set<Doubt> inheritedDoubts=existingDoubt.getInheritedDoubts();
                        for(Doubt inheritedDoubt:inheritedDoubts){
                            System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): found inherited doubt id: "+inheritedDoubt.getId()+" with existingCause Id: "+existingDoubt.getId());
                            
                            Set<DoubtStatus> inheritedDoubtStatus=inheritedDoubt.getDoubtStatuses();
                                for(DoubtStatus inhdbt:inheritedDoubtStatus){
                                    System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): found doubtstatus id: "+inhdbt.getId()+" related to the inherited doubt :"+inheritedDoubt.getId()+""
                                            + "\nDeleting doubtstatus: "+inhdbt.getId());
                                    doubtStatusService.deleteDoubtStatus(inhdbt.getId());
                                }
                                System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): all doubtStatus related to "+inheritedDoubt.getId()+" have now been deleted");
                                System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): Deleting inherited id: "+inheritedDoubt.getId());
                            doubtService.deleteDoubt(inheritedDoubt.getId());
                            
                        }
                        System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): all inherited doubts cleared for id: "+existingDoubt.getId()+"\nDeleting id: "+existingDoubt.getId());
                        doubtService.deleteDoubt(existingDoubt.getId());
                    }
                    System.out.println("fend.workspace.WorkspaceController.checkForQcDoubts(): Setting summary to false for job: "+jchild.getNameJobStep()+" sub: "+subb.getSubsurface());
                    summary.setQcSummary(false);
                
                }

    }

    private void inherit(Link l, Dot dot, Subsurface subb, Doubt cause,List<Descendant> descendantsThatContainSub) {
        
        
                Job inhParent = l.getParent();
                Job inhChild = l.getChild();
                System.out.println("fend.workspace.WorkspaceController.summarize(): Checking for inheritance. "+inhParent.getNameJobStep()+" <----> "+inhChild.getNameJobStep());
                DoubtType doubtTypeInherit = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.INHERIT);
                Set<Job> inhJob=new HashSet<>();
                for(Descendant desc:descendantsThatContainSub){
                    Job job=desc.getDescendant();
                    inhJob.add(job);
                }
                
             //   for(Descendant desc:descendantsThatContainSub){
                 for(Job job:inhJob){
                 
                     Summary summary;
                            if((summary=summaryService.getSummaryFor(subb, job))==null){
                                 summary = new Summary();
                                summary.setSequence(subb.getSequence());
                                summary.setSubsurface(subb);
                                summary.setJob(job);
                                summary.setWorkspace(dbWorkspace);
                                summaryService.createSummary(summary);
                                
                            }
                     
                     
                    System.out.println("fend.workspace.WorkspaceController.inherit(): checking for Desc: "+job.getNameJobStep()+" <---- Descendant of ---"+l.getChild().getNameJobStep());
                    Doubt d;
                   if((d=doubtService.getDoubtFor(subb, job, dot, cause, doubtTypeInherit))==null){
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
                                doubtService.createDoubt(d);

                                System.out.println("fend.workspace.WorkspaceController.inherit(): Setting the inheritance flag in summaryEntry# "+summary.getId());
                                summary.setInheritanceSummary(true);
                                DoubtStatus doubtStatus=new DoubtStatus();
                                doubtStatus.setReason(DoubtStatusModel.getNewDoubtInheritanceMessage(inhChild.getNameJobStep(),subb.getSubsurface(), job.getNameJobStep(), doubtTypeInherit.getName()));
                                doubtStatus.setDoubt(d);
                                doubtStatus.setStatus(DoubtStatusModel.YES);
                                doubtStatus.setTimeStamp(DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT));
                                //doubtStatus.setUser(user);
                                doubtStatusService.createDoubtStatus(doubtStatus);
                                d.addToDoubtStatuses(doubtStatus);
                                doubtService.updateDoubt(d.getId(), d);

                                cause.addToInheritedDoubts(d);
                                doubtService.updateDoubt(cause.getId(), cause);
                                summaryService.updateSummary(summary.getId(), summary);
                         }else{
                                System.out.println("fend.workspace.WorkspaceController.inherit(): Existing entry for INHERITANCE doubt found for "+job.getNameJobStep()+" sub: "+subb.getSubsurface()+" cause: "+cause.getChildJob().getNameJobStep()+ " with id: "+d.getId());
                                System.out.println("fend.workspace.WorkspaceController.inherit():  "+d.getDoubtType().getName()+" on "+d.getChildJob().getNameJobStep()+" cause: "+d.getDoubtCause().getChildJob().getNameJobStep()+" message: ");
                                System.out.println("fend.workspace.WorkspaceController.inherit(): Setting the inheritance flag in summaryEntry# "+summary.getId());
                                summary.setInheritanceSummary(true);
                                summaryService.updateSummary(summary.getId(), summary);
                      
                            for(DoubtStatus dsmess:d.getDoubtStatuses()){
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
        dbWorkspace = workspaceService.getWorkspace(dbWorkspace.getId());
        for (Subsurface subb : subsurfaceList) {
            System.out.println("fend.workspace.WorkspaceController.getSummary(): Loop for subsurface : " + subb.getSubsurface());
            Set<Link> linkNodesContainingSub = linkService.getLinksContainingSubsurface(subb, dbWorkspace);                  //all links where both the parent and the child contains sub subb
            
            GLink root=graphOfLinks(linkNodesContainingSub);
            
            
            
            for (Link l : linkNodesContainingSub) {
                Summary summary;
                //if ((summary = summaryService.getSummaryFor(subb.getSequence(), l.getParent())) == null) {            //create an entry for parent summary
                if ((summary = summaryService.getSummaryFor(subb, l.getParent())) == null) {            //create an entry for parent summary    
                    summary = new Summary();
                    summary.setSequence(subb.getSequence());
                    summary.setSubsurface(subb);
                    summary.setJob(l.getParent());
                    summary.setWorkspace(dbWorkspace);
                    summaryService.createSummary(summary);
                }
                //if ((summary = summaryService.getSummaryFor(subb.getSequence(), l.getChild())) == null) {             //create an entry for child summary 
                if ((summary = summaryService.getSummaryFor(subb, l.getChild())) == null) {             //create an entry for child summary 
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
}
