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
import db.model.Pheader;
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
import db.services.PheaderService;
import db.services.PheaderServiceImpl;
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
import fend.job.job5.JobType5Model;
import fend.job.job5.JobType5View;
import fend.summary.SummaryModel;
import fend.summary.SummaryView;
import fend.volume.volume0.Volume0;
import fend.volume.volume1.Volume1;
import fend.volume.volume2.Volume2;
import fend.volume.volume4.Volume4;
import fend.volume.volume5.Volume5;
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
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
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
    
    private NodeTypeService nodeTypeService = new NodeTypeServiceImpl();
    private PropertyTypeService propertyTypeService = new PropertyTypeServiceImpl();
    private NodePropertyService nodePropertyService = new NodePropertyServiceImpl();
    private NodePropertyValueService nodePropertyValueService = new NodePropertyValueServiceImpl();
    private AncestorService ancestorService = new AncestorServiceImpl();
    private DescendantService descendantService = new DescendantServiceImpl();
    private LinkService linkService = new LinkServiceImpl();
    private DotService dotService = new DotServiceImpl();
    private QcMatrixRowService qcMatrixRowService = new QcMatrixRowServiceImpl();
    private HeaderService headerService = new HeaderServiceImpl();
    private SubsurfaceService subsurfaceService = new SubsurfaceServiceImpl();
    private DoubtService doubtService = new DoubtServiceImpl();
    private DoubtTypeService doubtTypeService = new DoubtTypeServiceImpl();
    
    private QcTableService qcTableService = new QcTableServiceImpl();
    private SummaryService summaryService = new SummaryServiceImpl();
    private SubsurfaceJobService subsurfaceJobService = new SubsurfaceJobServiceImpl();
    private VariableArgumentService variableArgumentService = new VariableArgumentServiceImpl();
    private List<BooleanProperty> changePropertyList = new ArrayList<>();

    private DoubtType doubtTypeQc;
    private DoubtType doubtTypeTraces;
    private DoubtType doubtTypeTime;
    private DoubtType doubtTypeInsight;
    private DoubtType doubtTypeInherit;
    
    private NodeType node2D;
    private NodeType nodeSegd;
    private NodeType nodeText;
    private NodeType nodeAcq;
    private NodeType nodeSegy;
    
    
    
    private SummaryModel summaryModel = null;
    private SummaryView summaryView;
    private Executor exec;
    private ExecutorService execService;

    private Map<DoubtKey, List<DoubtStatus>> causeDoubtStatusMap = new HashMap<>();            //map of doubts and its associated doubt statuses. This includes all types of doubts excluding INHERITANCE
    private Map<InheritanceKey, List<DoubtStatus>> inheritanceDoubtStatusMap = new HashMap<>(); //map of inherited doubts and their doubt statuses.
    private Map<Doubt, List<Doubt>> causeAndAssociatedInheritanceMap = new HashMap<>();    //map between doubt and its list of inherited doubts
    private Map<InheritanceKey, Doubt> inheritanceMap = new HashMap<>();                 //unique identifier of each inherited doubt (Job, Sub, Cause)
    private Map<Dot, List<VariableArgument>> variableArgumentMap = new HashMap<>();
    private Map<SubsurfaceJobKey, SubsurfaceJob> subsurfaceJobSummaryTimeMap = new HashMap<>();
    private Map<Subsurface, Set<Link>> subsurfaceLinkMap = new HashMap<>();
    private Map<HeaderKey, Header> headerMap = new HashMap<>();
    private Map<PheaderKey, Pheader> pheaderMap = new HashMap<>();
    private Map<SummaryKey, Summary> summaryMap = new HashMap<>();                 //used to check if an entry exists in the database
    

    private List<Summary> newSummaries = new ArrayList<>();   //summaries that will be created in the database
     
    private Map<AncestorKey, List<Ancestor>> ancestorMapForSummary = new HashMap<>();    // each pkey (job,sub) has a list of ancestors (jobs that contain the sub)
    private Map<DescendantKey, List<Descendant>> descendantMapForSummary = new HashMap<>(); // each pkey (job,sub) has a list of descendants (jobs that contain the sub)
    private double percentageOfProcessorsUsed = AppProperties.PERCENTAGE_OF_PROCESSORS_USED;
    private double dividerWidth = 0.90;

    @FXML
    private AnchorPane baseWindow;              //depth =0 

    @FXML
    private HBox hbox;
    /*@FXML
    private SplitPane splitpane;                //depth =1 */

    @FXML
    private ScrollPane scrollpane;              //depth =2 

    private ScrollBar vscrollBar;

    @FXML
    private AnchorPane anchorPane2;

    @FXML
    private AnchorPane interactivePane;         //depth =3 

    /*  @FXML
    private Button chartButton;
    
    
    @FXML
    private Button add;
    
    @FXML
    private Button segdbtn;
    
    @FXML
    private Button acqbtn;
    
    @FXML
    private Button textBtn;
    
    @FXML
    private Button summaryButton;*/
    // @FXML
    public void chart(ActionEvent event) {
        System.out.println("fend.workspace.WorkspaceController.chart(): Pending implementation for charts");
    }

    //  @FXML
    public void addBox(ActionEvent event) {
        Job dbjob = new Job();
        BooleanProperty changeProperty = new SimpleBooleanProperty(false);
        JobType1Model job = new JobType1Model(this.model);

        Task<String> task = new Task<String>() {
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

                List<JobModelProperty> jobProperties = job.getJobProperties();
                for (Iterator<JobModelProperty> iterator = jobProperties.iterator(); iterator.hasNext();) {
                    JobModelProperty jobProperty = iterator.next();
                    NodePropertyValue npv = new NodePropertyValue();
                    npv.setJob(dbjob);
                    PropertyType propertyType = propertyTypeService.getPropertyTypeObjForName(jobProperty.getPropertyName());
                    NodeProperty nodeProperty = nodePropertyService.getNodeProperty(nodetype, propertyType);
                    npv.setNodeProperty(nodeProperty);
                    npv.setValue(jobProperty.getPropertyValue());
                    nodePropertyValueService.createNodePropertyValue(npv);
                }

                return "finished building a 2dProcess entry: " + dbjob.getId();
            }
        };

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
        });

        task.setOnSucceeded(e -> {
            changeProperty.bind(job.getChangeProperty());
            changeProperty.addListener(workspaceChangedListener);
            changePropertyList.add(changeProperty);
            JobType1View jobview = new JobType1View(job, interactivePane);
            interactivePane.getChildren().add(jobview);
            //        System.out.println("workspace.WorkspaceController.addBox(): "+g1Child.getId()%100);

        });

        task.setOnRunning(e -> {
            System.out.println("fend.workspace.WorkspaceController.add2D(): process is running");
        });

        exec.execute(task);

    }

    // @FXML
    public void addSEGD(ActionEvent event) {
        Job dbjob = new Job();
        BooleanProperty changeProperty = new SimpleBooleanProperty(false);
        JobType2Model job = new JobType2Model(WorkspaceController.this.model);

        Task<String> task = new Task<String>() {
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
                List<JobModelProperty> jobProperties = job.getJobProperties();
                for (Iterator<JobModelProperty> iterator = jobProperties.iterator(); iterator.hasNext();) {
                    JobModelProperty jobProperty = iterator.next();
                    System.out.println(".call(): for jobProperty " + jobProperty.getPropertyName());
                    System.out.println("");
                    System.out.println(".call(): started a new  nodepropertyvalue object");
                    NodePropertyValue npv = new NodePropertyValue();
                    System.out.println(".call(): setting npvs job= dbjob");
                    npv.setJob(dbjob);
                    System.out.println(".call(): retrieving propertype: " + jobProperty.getPropertyName());
                    PropertyType propertyType = propertyTypeService.getPropertyTypeObjForName(jobProperty.getPropertyName());
                    System.out.println(".call(): retrieving nodeProperty for nodetype: " + nodetype.getName() + " and propertytype: " + propertyType.getName());
                    NodeProperty nodeProperty = nodePropertyService.getNodeProperty(nodetype, propertyType);
                    System.out.println(".call(): setting npv.nodeproperty");
                    npv.setNodeProperty(nodeProperty);
                    System.out.println(".call(): setting npv.nodepropertyValue");
                    npv.setValue(jobProperty.getPropertyValue());
                    System.out.println(".call(): creating npv in the database");
                    nodePropertyValueService.createNodePropertyValue(npv);
                    System.out.println(".call(): done creating for this property");

                }
                return "finished building a SEGD entry: " + dbjob.getId();
            }
        };

        //  BooleanProperty changeProperty = new SimpleBooleanProperty(false);
        task.setOnFailed(e -> {
            task.getException().printStackTrace();
        });

        task.setOnSucceeded(e -> {
            System.out.println("fend.workspace.WorkspaceController.addSEGD(): task succeeded. adding on the view.");
            changeProperty.bind(job.getChangeProperty());
            changeProperty.addListener(workspaceChangedListener);

            changePropertyList.add(changeProperty);
            JobType2View jobview = new JobType2View(job, interactivePane);
            interactivePane.getChildren().add(jobview);
        });

        task.setOnRunning(e -> {
            System.out.println("fend.workspace.WorkspaceController.addSEGD(): process is running");
        });

        exec.execute(task);

    }

    // @FXML
    public void addAcq(ActionEvent event) {
        

        Job dbjob = new Job();
        BooleanProperty changeProperty = new SimpleBooleanProperty(false);
        JobType3Model job = new JobType3Model(WorkspaceController.this.model);

        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                Long typeOfJob = JobType0Model.ACQUISITION;
                NodeType nodetype = nodeTypeService.getNodeTypeObjForType(typeOfJob);
                dbjob.setNodetype(nodetype);
                dbjob.setWorkspace(dbWorkspace);

                dbjob.setDepth(JobType0Model.INITIAL_DEPTH);
                System.out.println("fend.workspace.WorkspaceController.addAcq(): creating  a new job in the database");
                jobService.createJob(dbjob);

                System.out.println(".call(): setting the job id");
                job.setId(dbjob.getId());
                job.setDatabaseJob(dbjob);

                System.out.println(".call(): getting jobProperties");
                List<JobModelProperty> jobProperties = job.getJobProperties();
                for (Iterator<JobModelProperty> iterator = jobProperties.iterator(); iterator.hasNext();) {
                    JobModelProperty jobProperty = iterator.next();
                    System.out.println(".call(): for jobProperty " + jobProperty.getPropertyName());
                    System.out.println("");
                    System.out.println(".call(): started a new  nodepropertyvalue object");
                    NodePropertyValue npv = new NodePropertyValue();
                    System.out.println(".call(): setting npvs job= dbjob");
                    npv.setJob(dbjob);
                    System.out.println(".call(): retrieving propertype: " + jobProperty.getPropertyName());
                    PropertyType propertyType = propertyTypeService.getPropertyTypeObjForName(jobProperty.getPropertyName());
                    System.out.println(".call(): retrieving nodeProperty for nodetype: " + nodetype.getName() + " and propertytype: " + propertyType.getName());
                    NodeProperty nodeProperty = nodePropertyService.getNodeProperty(nodetype, propertyType);
                    System.out.println(".call(): setting npv.nodeproperty");
                    npv.setNodeProperty(nodeProperty);
                    System.out.println(".call(): setting npv.nodepropertyValue");
                    npv.setValue(jobProperty.getPropertyValue());
                    System.out.println(".call(): creating npv in the database");
                    nodePropertyValueService.createNodePropertyValue(npv);
                    System.out.println(".call(): done creating for this property");

                }
                return "finished building an Acq entry: " + dbjob.getId();
            }
        };

        //  BooleanProperty changeProperty = new SimpleBooleanProperty(false);
        task.setOnFailed(e -> {
            task.getException().printStackTrace();
        });

        task.setOnSucceeded(e -> {
            System.out.println("fend.workspace.WorkspaceController.addAcq(): task succeeded. adding on the view.");
            changeProperty.bind(job.getChangeProperty());
            changeProperty.addListener(workspaceChangedListener);

            changePropertyList.add(changeProperty);
            JobType3View jobview = new JobType3View(job, interactivePane);
            interactivePane.getChildren().add(jobview);
        });

        task.setOnRunning(e -> {
            System.out.println("fend.workspace.WorkspaceController.addAcq(): process is running");
        });

        exec.execute(task);

    }

    //@FXML
    public void addText(ActionEvent event) {

        Job dbjob = new Job();
        BooleanProperty changeProperty = new SimpleBooleanProperty(false);
        JobType4Model job = new JobType4Model(this.model);

        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                Long typeOfJob = JobType0Model.TEXT;
                NodeType nodetype = nodeTypeService.getNodeTypeObjForType(typeOfJob);
                dbjob.setNodetype(nodetype);
                dbjob.setWorkspace(dbWorkspace);

                dbjob.setDepth(JobType0Model.INITIAL_DEPTH);
                jobService.createJob(dbjob);
                job.setId(dbjob.getId());
                job.setDatabaseJob(dbjob);

                List<JobModelProperty> jobProperties = job.getJobProperties();
                for (Iterator<JobModelProperty> iterator = jobProperties.iterator(); iterator.hasNext();) {
                    JobModelProperty jobProperty = iterator.next();
                    NodePropertyValue npv = new NodePropertyValue();
                    npv.setJob(dbjob);
                    PropertyType propertyType = propertyTypeService.getPropertyTypeObjForName(jobProperty.getPropertyName());
                    NodeProperty nodeProperty = nodePropertyService.getNodeProperty(nodetype, propertyType);
                    npv.setNodeProperty(nodeProperty);
                    npv.setValue(jobProperty.getPropertyValue());
                    nodePropertyValueService.createNodePropertyValue(npv);
                }

                return "finished building a Text entry: " + dbjob.getId();
            }
        };

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
        });

        task.setOnSucceeded(e -> {
            changeProperty.bind(job.getChangeProperty());
            changeProperty.addListener(workspaceChangedListener);
            changePropertyList.add(changeProperty);
            JobType4View jobview = new JobType4View(job, interactivePane);
            interactivePane.getChildren().add(jobview);
            //        System.out.println("workspace.WorkspaceController.addBox(): "+g1Child.getId()%100);

        });

        task.setOnRunning(e -> {
            System.out.println("fend.workspace.WorkspaceController.addText(): process is running");
        });

        exec.execute(task);

    }
    
    
     public void addSegy(ActionEvent event) {
        Job dbjob = new Job();
        BooleanProperty changeProperty = new SimpleBooleanProperty(false);
        JobType5Model job = new JobType5Model(this.model);

        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                Long typeOfJob = JobType0Model.SEGY;
                NodeType nodetype = nodeTypeService.getNodeTypeObjForType(typeOfJob);
                dbjob.setNodetype(nodetype);
                dbjob.setWorkspace(dbWorkspace);

                dbjob.setDepth(JobType0Model.INITIAL_DEPTH);
                jobService.createJob(dbjob);
                job.setId(dbjob.getId());
                job.setDatabaseJob(dbjob);

                List<JobModelProperty> jobProperties = job.getJobProperties();
                for (Iterator<JobModelProperty> iterator = jobProperties.iterator(); iterator.hasNext();) {
                    JobModelProperty jobProperty = iterator.next();
                    NodePropertyValue npv = new NodePropertyValue();
                    npv.setJob(dbjob);
                    PropertyType propertyType = propertyTypeService.getPropertyTypeObjForName(jobProperty.getPropertyName());
                    NodeProperty nodeProperty = nodePropertyService.getNodeProperty(nodetype, propertyType);
                    npv.setNodeProperty(nodeProperty);
                    npv.setValue(jobProperty.getPropertyValue());
                    nodePropertyValueService.createNodePropertyValue(npv);
                }

                return "finished building a SEGY entry: " + dbjob.getId();
            }
        };

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
        });

        task.setOnSucceeded(e -> {
            changeProperty.bind(job.getChangeProperty());
            changeProperty.addListener(workspaceChangedListener);
            changePropertyList.add(changeProperty);
            JobType5View jobview = new JobType5View(job, interactivePane); 
            interactivePane.getChildren().add(jobview);
            //        System.out.println("workspace.WorkspaceController.addBox(): "+g1Child.getId()%100);

        });

        task.setOnRunning(e -> {
            System.out.println("fend.workspace.WorkspaceController.addText(): process is running");
        });

        exec.execute(task);
    }

    //@FXML 
    public void getSummary(ActionEvent event) throws Exception {

        Task<String> summaryTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                if (summaryModel == null) {
                    summaryModel = new SummaryModel(WorkspaceController.this);
                }
                return "Finished summary for workspace ";
            }
        };

        summaryTask.setOnFailed(e -> {
            summaryTask.getException().printStackTrace();
        });

        summaryTask.setOnRunning(e -> {
            System.out.println("fend.workspace.WorkspaceController.getSummary(): Summary operation running in the background thread");
        });

        summaryTask.setOnSucceeded(e -> {
            summaryView = new SummaryView(summaryModel);

        });
        exec.execute(summaryTask);

        
    }
   
    void setModel(WorkspaceModel item) {
//        splitpane.prefWidthProperty().bind(baseWindow.widthProperty());
        //      splitpane.prefHeightProperty().bind(baseWindow.heightProperty());
        //     scrollpane.prefWidthProperty().bind(splitpane.widthProperty());
        //     scrollpane.prefHeightProperty().bind(splitpane.heightProperty());

        //    fixDivider();
        hbox.prefWidthProperty().bind(baseWindow.widthProperty());
        hbox.prefHeightProperty().bind(baseWindow.heightProperty());
        scrollpane.prefWidthProperty().bind(hbox.widthProperty());
        scrollpane.prefHeightProperty().bind(hbox.heightProperty());
        interactivePane.prefWidthProperty().bind(scrollpane.widthProperty());
        interactivePane.prefHeightProperty().bind(scrollpane.heightProperty());
        interactivePane.getChildren().addListener(jobLinkChangeListener);
        loadingProperty.addListener(loadingListener);
        //   createGraphAndChartsButton();
        //   createSummaryButton();
        // moveScrollPanesToTheLeft();

        model = item;
        //dbWorkspace = workspaceService.getWorkspace(model.getId());
        dbWorkspace = model.getWorkspace();

        doubtTypeQc = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.QC);
        doubtTypeTraces = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TRACES);
        doubtTypeTime = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.TIME);
        doubtTypeInsight=doubtTypeService.getDoubtTypeByName(DoubtTypeModel.INSIGHT);
        doubtTypeInherit = doubtTypeService.getDoubtTypeByName(DoubtTypeModel.INHERIT);
        
        node2D=nodeTypeService.getNodeTypeObjForType(JobType0Model.PROCESS_2D);
        nodeSegd=nodeTypeService.getNodeTypeObjForType(JobType0Model.SEGD_LOAD);
        nodeAcq=nodeTypeService.getNodeTypeObjForType(JobType0Model.ACQUISITION);
        nodeText=nodeTypeService.getNodeTypeObjForType(JobType0Model.TEXT);
        nodeSegy=nodeTypeService.getNodeTypeObjForType(JobType0Model.SEGY);
        
        
        
        
        File insightLocation = new File(AppProperties.getINSIGHT_LOCATION());
        File[] insights = insightLocation.listFiles(insightFilter);
        List<String> insightVersionStrings = new ArrayList<>();
        System.out.println("fend.workspace.WorkspaceController.setModel(): No of insights found: " + insightVersionStrings.size());
        for (File insight : insights) {
            System.out.println("fend.workspace.WorkspaceController.setModel(): insightVersions Found: " + insight.getName());
            insightVersionStrings.add(insight.getName());
        }
        model.setInsightVersions(insightVersionStrings);
        model.rebuildGraphOrderProperty().addListener(REBUILD_GRAPH_LISTENER);
        model.prepareToRebuildProperty().addListener(CLEAR_ANCESTOR_LISTENER);
        model.reloadProperty().addListener(RELOAD_LISTENER);
       // model.clearDescendantsProperty().addListener(CLEAR_DESCENDANT_LISTENER);
        exec = Executors.newCachedThreadPool((r) -> {
            Thread t = new Thread(r);
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
    }

    private void loadSession() {
        Workspace dbWorkspace = workspaceService.getWorkspace(model.getId());

        //Set<Job> jobsInDb = dbWorkspace.getJobs();
        Set<Job> jobsInDb = new HashSet<>(jobService.getJobsInWorkspace(dbWorkspace));

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
            if (type.equals(JobType0Model.SEGY)) {
                fejob = new JobType5Model(model);
                fejob.setId(dbj.getId());
                fejob.setDatabaseJob(dbj);
                fejob.setNameproperty(dbj.getNameJobStep());
                fejob.setDepth(dbj.getDepth());

                System.out.println("fend.workspace.WorkspaceController.loadSession(): Added job: " + dbj.getNameJobStep());

            }

            /*Set<Volume> dbvols = new HashSet<>(volumeService.getVolumesForJob(dbj));
            
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
                
                 // Skip the process for the Acq node
                 
                if (vtype.equals(Volume0.TEXT)) {
                    fevol = new Volume4(fejob);                  //parent g1Child and id set in contructor

                    fevol.setId(dbv.getId());
                    fevol.setName(dbv.getNameVolume());
                    File volumeOnDisk = new File(dbv.getPathOfVolume());
                    fevol.setVolume(volumeOnDisk);
                    System.out.println("fend.workspace.WorkspaceController.loadSession(): Added Volume : " + dbv.getNameVolume() + " to job: " + dbj.getNameJobStep());
                }
                if (vtype.equals(Volume0.SEGY)) {
                    fevol = new Volume5(fejob);                  //parent g1Child and id set in contructor

                    fevol.setId(dbv.getId());
                    fevol.setName(dbv.getNameVolume());
                    File volumeOnDisk = new File(dbv.getPathOfVolume());
                    fevol.setVolume(volumeOnDisk);
                    System.out.println("fend.workspace.WorkspaceController.loadSession(): Added Volume : " + dbv.getNameVolume() + " to job: " + dbj.getNameJobStep());
                }
                frontEndVolumeModels.add(fevol);

            }
            fejob.setVolumes(frontEndVolumeModels);
            */

            idFrontEndJobMap.put(fejob.getId(), fejob);

            frontEndJobModels.add(fejob);
        }
        model.setObservableJobs(new HashSet<>(frontEndJobModels));       //front end jobs and volumes set in workspace model

        //Set<Dot> dots = dbWorkspace.getDots();
        Set<Dot> dots = new HashSet<>(dotService.getDotsInWorkspace(dbWorkspace));
        System.out.println("fend.workspace.WorkspaceController.loadSession(): the size of  dots retrieved : " + dots.size());
        List<DotModel> frontEndDotModels = new ArrayList<>();

        for (Dot dot : dots) {
            DotModel fedot = new DotModel(model);
            fedot.setDatabaseDot(dot);
            //Set<Link> links = dot.getLinks();
            //Set<Link> dbLinks=dbDot.getLinks();
            Set<Link> links = new HashSet<>(linkService.getLinksForDot(dot));
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
                System.out.println("fend.workspace.WorkspaceController.inflateFrontEndViews(): name: " + job.getNameproperty().get() + " y,x: " + jv.getHeight() + "," + jv.getWidth() + jv.getBoundsInLocal().getHeight() + "," + jv.getBoundsInLocal().getMaxX() + "," + jv.getBoundsInParent().getMaxX() + "," + jv.getLayoutBounds().getMaxX());
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
                System.out.println("fend.workspace.WorkspaceController.inflateFrontEndViews(): name: " + job.getNameproperty().get() + " y,x: " + jv.getHeight() + "," + jv.getWidth() + jv.getBoundsInLocal().getHeight() + "," + jv.getBoundsInLocal().getMaxX() + "," + jv.getBoundsInParent().getMaxX() + "," + jv.getLayoutBounds().getMaxX());
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
                System.out.println("fend.workspace.WorkspaceController.inflateFrontEndViews(): name: " + job.getNameproperty().get() + " y,x: " + jv.getHeight() + "," + jv.getWidth() + jv.getBoundsInLocal().getHeight() + "," + jv.getBoundsInLocal().getMaxX() + "," + jv.getBoundsInParent().getMaxX() + "," + jv.getLayoutBounds().getMaxX());
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
                System.out.println("fend.workspace.WorkspaceController.inflateFrontEndViews(): name: " + job.getNameproperty().get() + " y,x: " + jv.getHeight() + "," + jv.getWidth() + jv.getBoundsInLocal().getHeight() + "," + jv.getBoundsInLocal().getMaxX() + "," + jv.getBoundsInParent().getMaxX() + "," + jv.getLayoutBounds().getMaxX());
            }
            if (job.getType().equals(JobType0Model.SEGY)) {
                JobType5View jv = new JobType5View((JobType5Model) job, interactivePane);

                /**
                 * Attach Listeners to save workspace
                 */
                BooleanProperty changeProperty = new SimpleBooleanProperty(false);
                changeProperty.bind(job.getChangeProperty());
                changeProperty.addListener(workspaceChangedListener);
                changePropertyList.add(changeProperty);
                idFrontEndJobMap.put(job.getId(), jv);
                interactivePane.getChildren().add(jv);
                System.out.println("fend.workspace.WorkspaceController.inflateFrontEndViews(): name: " + job.getNameproperty().get() + " y,x: " + jv.getHeight() + "," + jv.getWidth() + jv.getBoundsInLocal().getHeight() + "," + jv.getBoundsInLocal().getMaxX() + "," + jv.getBoundsInParent().getMaxX() + "," + jv.getLayoutBounds().getMaxX());
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
    final private String INSIGHT_REGEX = "\\d*.\\d*-[\\w_-]*";     //match doubt.doubt-xxxxxx, doubt.doubt-xxxxx-ABC , doubt.doubt-xxxxxxx-ABC_mno, doubt.doubt-xxxxxx_mno
    final private Pattern pattern = Pattern.compile(INSIGHT_REGEX);
    final private FileFilter insightFilter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return pattern.matcher(pathname.getName()).matches();
        }

    };

  
    
   
    /**
     * Summary new approach
     *
     */
    private Map<SummaryKey, SummaryHolder> sMap = new HashMap<>();
    private Map<DoubtKey, DoubtHolder> dMap = new HashMap<>();
    private Map<Doubt,List<Long>> iMap=new HashMap<>();
    private Map<Job,List<Dot>> djMap=new HashMap<>();
    
    //private Map<Doubt,DoubtStatus>

    /**
     * clear all containers before sum
     */
    private void clearAllMaps() {
        sMap.clear();
        dMap.clear();
        subsurfaceLinkMap.clear();
        subsurfaceJobSummaryTimeMap.clear();
        headerMap.clear();
        variableArgumentMap.clear();
        descendantMapForSummary.clear();
        ancestorMapForSummary.clear();
        //causeAndAssociatedInheritanceMap.clear();
        iMap.clear();
        djMap.clear();
        newDoubts.clear();
        updateDoubts.clear();
        deleteDoubts.clear();
        newInheritedDoubts.clear();
        inheritanceLUMap.clear();
        newSummaries.clear();
    }


    final private int DEPENDENCY_FAIL_ERROR   = -1;
    final private int DEPENDENCY_FAIL_WARNING =  0;
    final private int DEPENDENCY_PASS         =  1;
    
    private ResultHolder checkTimeDependency(Link l, Subsurface subb){
        Job hparent = l.getParent();
        Job hchild = l.getChild();
        /*boolean path1=(                                                                                     //path to take if both of them are either  2D or SEGD
        (hparent.getNodetype().equals(node2D) || hparent.getNodetype().equals(nodeSegd))
        &&
        (hchild.getNodetype().equals(node2D) || hchild.getNodetype().equals(nodeSegd)));
        
        
        boolean path2=(                                                                                     //path to take if atleast one of them is SEGY
        hparent.getNodetype().equals(nodeSegy)
        ||
        hchild.getNodetype().equals(nodeSegy)
        );*/
        
        boolean parentIsSegy=hparent.getNodetype().equals(nodeSegy);
        boolean childIsSegy=hchild.getNodetype().equals(nodeSegy);
        boolean bothAreSegy=parentIsSegy && childIsSegy;
        boolean processIsNotSegy=!parentIsSegy &&  !childIsSegy;
       
                    
        ResultHolder resultHolder=new ResultHolder();
        
        if(processIsNotSegy){
                    HeaderKey parentKey = generateHeaderKey(hparent, subb);
                    HeaderKey childKey = generateHeaderKey(hchild, subb);
                    Header hp = headerMap.get(parentKey);
                    Header hc = headerMap.get(childKey);
                    Long hpt = Long.valueOf(hp.getTimeStamp());
                    Long hct = Long.valueOf(hc.getTimeStamp());

                     
                    if(hpt >= hct) {    //parent header created not before child header
                        resultHolder.result=DEPENDENCY_FAIL_ERROR;
                        resultHolder.reason=DoubtStatusModel.getNewDoubtTimeMessage(hparent.getNameJobStep(), new String(hpt + ""), hchild.getNameJobStep(), new String(hct + ""), subb.getSubsurface(), doubtTypeTime.getName());
                       return resultHolder;
                    }else{
                        resultHolder.result=DEPENDENCY_PASS;
                        resultHolder.reason=DoubtStatusModel.getTimeDependencyPassedMessage(hparent.getNameJobStep(), new String(hpt + ""), hchild.getNameJobStep(), new String(hct + ""), subb.getSubsurface(), doubtTypeTime.getName());
                        return resultHolder;
                    }
        }else{
                   if(bothAreSegy){
                        
                        PheaderKey parentKey = generatePheaderKey(hparent, subb);
                        PheaderKey childKey = generatePheaderKey(hchild, subb);
                        Pheader hp = pheaderMap.get(parentKey);
                        Pheader hc = pheaderMap.get(childKey);
                        Long hpt = Long.valueOf(hp.getTimeStamp());
                        Long hct = Long.valueOf(hc.getTimeStamp());


                        if(hpt >= hct) {    //parent header created not before child header
                            resultHolder.result=DEPENDENCY_FAIL_ERROR;
                            resultHolder.reason=DoubtStatusModel.getNewDoubtTimeMessage(hparent.getNameJobStep(), new String(hpt + ""), hchild.getNameJobStep(), new String(hct + ""), subb.getSubsurface(), doubtTypeTime.getName());
                           // return resultHolder;
                        }else{
                            resultHolder.result=DEPENDENCY_PASS;
                            resultHolder.reason=DoubtStatusModel.getTimeDependencyPassedMessage(hparent.getNameJobStep(), new String(hpt + ""), hchild.getNameJobStep(), new String(hct + ""), subb.getSubsurface(), doubtTypeTime.getName());
                            //return resultHolder;
                        }
                          
                   }else if(parentIsSegy){
                       
                        PheaderKey parentKey = generatePheaderKey(hparent, subb);
                        HeaderKey childKey = generateHeaderKey(hchild, subb);
                        Pheader hp = pheaderMap.get(parentKey);
                        Header hc = headerMap.get(childKey);
                        Long hpt = Long.valueOf(hp.getTimeStamp());
                        Long hct = Long.valueOf(hc.getTimeStamp());


                        if(hpt >= hct) {    //parent header created not before child header
                            resultHolder.result=DEPENDENCY_FAIL_ERROR;
                            resultHolder.reason=DoubtStatusModel.getNewDoubtTimeMessage(hparent.getNameJobStep(), new String(hpt + ""), hchild.getNameJobStep(), new String(hct + ""), subb.getSubsurface(), doubtTypeTime.getName());
                            //return resultHolder;
                        }else{
                            resultHolder.result=DEPENDENCY_PASS;
                            resultHolder.reason=DoubtStatusModel.getTimeDependencyPassedMessage(hparent.getNameJobStep(), new String(hpt + ""), hchild.getNameJobStep(), new String(hct + ""), subb.getSubsurface(), doubtTypeTime.getName());
                           // return resultHolder;
                        }
                       
                   }else if(childIsSegy){
                        HeaderKey parentKey = generateHeaderKey(hparent, subb);
                        PheaderKey childKey = generatePheaderKey(hchild, subb);
                        Header hp = headerMap.get(parentKey);
                        Pheader hc = pheaderMap.get(childKey);
                        Long hpt = Long.valueOf(hp.getTimeStamp());
                        Long hct = Long.valueOf(hc.getTimeStamp());


                        if(hpt >= hct) {    //parent header created not before child header
                            resultHolder.result=DEPENDENCY_FAIL_ERROR;
                            resultHolder.reason=DoubtStatusModel.getNewDoubtTimeMessage(hparent.getNameJobStep(), new String(hpt + ""), hchild.getNameJobStep(), new String(hct + ""), subb.getSubsurface(), doubtTypeTime.getName());
                            //return resultHolder;
                        }else{
                            resultHolder.result=DEPENDENCY_PASS;
                            resultHolder.reason=DoubtStatusModel.getTimeDependencyPassedMessage(hparent.getNameJobStep(), new String(hpt + ""), hchild.getNameJobStep(), new String(hct + ""), subb.getSubsurface(), doubtTypeTime.getName());
                            //return resultHolder;
                        }
                   }
        }
      return resultHolder;
    }
    
  
    
    
    
    private ResultHolder checkTraceDependency(Link link,Subsurface subb){
        Dot dot=link.getDot();
        String function = dot.getFunction();
        Double tolerance = dot.getTolerance();
        Double error = dot.getError();
        //Set<VariableArgument> variableArguments = dot.getVariableArguments();         //variable and the arguments belonging to the dot.
        Set<VariableArgument> variableArguments = new HashSet<>(variableArgumentMap.get(dot));
                    
        
        Map<String, Double> mapForVariableSetting = new HashMap<>();
        Set<String> variableSet = new HashSet<>();
        Set<Job> argumentSet = new HashSet<>();
        ResultHolder resultHolder=new ResultHolder();
        mapForVariableSetting.clear();
        variableSet.clear();
        argumentSet.clear();
        for (VariableArgument va : variableArguments) {
            String var = va.getVariable();
            Job arg = va.getArgument();
            Double tracesArg;
            
            /*Header ph = headerService.getChosenHeaderFor(arg, subb);          //O-2*/
             boolean argIsSegy=arg.getNodetype().equals(nodeSegy);
             if(!argIsSegy){
                 HeaderKey hkey = generateHeaderKey(arg, subb);
                 Header h = headerMap.get(hkey);
                    if (h == null) {
                        tracesArg = 0.0;
                    } else {
                        tracesArg = Double.valueOf(h.getTraceCount() + "");
                    }

             }else{
                 PheaderKey hkey=generatePheaderKey(arg, subb);
                 Pheader ph = pheaderMap.get(hkey);
                    if (ph == null) {
                        tracesArg = 0.0;
                    } else {
                        tracesArg = Double.valueOf(ph.getTraceCount() + "");
                    }

             }
                    
            mapForVariableSetting.put(var, tracesArg);
            if (!var.equals("y0")) {                      //y0 is the lhs which is fixed, the rhs needs to be evaluated. Do not include the y-term
                variableSet.add(var);
                argumentSet.add(arg);
            }

        }
        System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): Sub: " + subb.getSubsurface() + " linkParent: " + link.getParent().getNameJobStep() + " linkChild: " + link.getChild().getNameJobStep());
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
        System.out.println("fend.workspace.WorkspaceController.checkForDependencyDoubts(): check for y = " + y
                + "evaluated.result = " + result);
        Double evaluated = Math.abs(y - result) / y;
        
        
        if (evaluated <= tolerance) {
            resultHolder.result=DEPENDENCY_PASS;
            resultHolder.reason=DoubtStatusModel.getTraceDependencyPassedMessage(link.getParent().getNameJobStep(),link.getChild().getNameJobStep(),
                    evaluated.toString(),tolerance.toString(),error.toString(),subb.getSubsurface(),doubtTypeTraces.getName());
        }else if(evaluated >= error){
            resultHolder.result=DEPENDENCY_FAIL_ERROR;
            resultHolder.reason=DoubtStatusModel.getNewDoubtTraceMessage(function, tolerance, error, evaluated, y, doubtTypeTraces.getName());
        }else{
            resultHolder.result=DEPENDENCY_FAIL_WARNING;
            resultHolder.reason=DoubtStatusModel.getTraceDependencyWarningMessage(function, tolerance, error, evaluated, y, function);
        }
        
        return resultHolder;
        
    }
    
    private ResultHolder checkQcDependency(Link link,Subsurface sub){
        
        Job lparent = link.getParent();
        Job jchild = link.getChild();
        List<QcMatrixRow> parentQcMatrix = qcMatrixRowService.getQcMatrixForJob(lparent, true);    //put this in a map
        Boolean passQc = true;
        for (QcMatrixRow qcmr : parentQcMatrix) {
            try {
                QcTable qctableentries = qcTableService.getQcTableFor(qcmr, sub);                   //put this in a map
                Boolean qcresult;
                if(qctableentries==null){
                    qcresult=false;
                }else{
                    qcresult=qctableentries.getResult();
                }
                
                if (qcresult == null) {
                    qcresult = false;
                }
                passQc = passQc && qcresult;
            } catch (Exception ex) {
                Logger.getLogger(WorkspaceController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
        ResultHolder resultHolder=new ResultHolder();
        if (!passQc) {
            resultHolder.result=DEPENDENCY_FAIL_ERROR;
            resultHolder.reason=DoubtStatusModel.getNew2DoubtQCmessage(lparent.getNameJobStep(), sub.getSubsurface(), doubtTypeQc.getName());
        }else{
            resultHolder.result=DEPENDENCY_PASS;
            resultHolder.reason=DoubtStatusModel.getQcDependencyPassedMessage(lparent.getNameJobStep(), jchild.getNameJobStep(), sub.getSubsurface(), doubtTypeQc.getName());
        }
        
        return resultHolder;
    }
    
    private ResultHolder checkQcDependencyOnLeaf(Link link,Subsurface sub){
        
        Job lparent = link.getParent();
        Job jchild = link.getChild();
        List<QcMatrixRow> childQcMatrix = qcMatrixRowService.getQcMatrixForJob(jchild, true);    //put this in a map
        Boolean passQc = true;
        for (QcMatrixRow qcmr : childQcMatrix) {
            try {
                QcTable qctableentries = qcTableService.getQcTableFor(qcmr, sub);                   //put this in a map
                Boolean qcresult;
                if(qctableentries==null){
                    qcresult=false;
                }else{
                    qcresult=qctableentries.getResult();
                }
                
                if (qcresult == null) {
                    qcresult = false;
                }
                passQc = passQc && qcresult;
            } catch (Exception ex) {
                Logger.getLogger(WorkspaceController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
        ResultHolder resultHolder=new ResultHolder();
        if (!passQc) {
            resultHolder.result=DEPENDENCY_FAIL_ERROR;
            resultHolder.reason=DoubtStatusModel.getNew2DoubtQCmessage(jchild.getNameJobStep(), sub.getSubsurface(), doubtTypeQc.getName());
        }else{
            resultHolder.result=DEPENDENCY_PASS;
            resultHolder.reason=DoubtStatusModel.getQcDependencyPassedMessage(jchild.getNameJobStep(), lparent.getNameJobStep(), sub.getSubsurface(), doubtTypeQc.getName());
        }
        
        return resultHolder;
    }
    
    private ResultHolder checkInsightDependency(Link link,Subsurface sub){
        Job parent=link.getParent();
        Job child=link.getChild();
        
        ResultHolder resultHolder=new ResultHolder();
        
        boolean insightFail=false;
        
        String insightsInParent=parent.getInsightVersions();                // ins1; ins2; ins3;
            List<String> parentList=new ArrayList<>();
            String insightVersionInHeader=" **NO ENTRY FOUND** ";
            if(insightsInParent==null){
                insightFail=true;
            }
            else{
           
            
            String[] parts=insightsInParent.split(";");
            for (String s: parts){
                System.out.println("fend.workspace.WorkspaceController.checkInsightDependency(): for job : "+parent.getNameJobStep()+" found: "+s);
                parentList.add(s);
            };
        
        
        boolean parentIsSegy=parent.getNodetype().equals(nodeSegy);
        if(!parentIsSegy){
            HeaderKey key=generateHeaderKey(parent, sub);
            if(headerMap.containsKey(key)){
                Header hp=headerMap.get(key);     //get headers of the parent
                insightVersionInHeader=hp.getInsightVersion();
                System.out.println("fend.workspace.WorkspaceController.checkInsightDependency(): for sub: "+sub.getSubsurface()+" found insight: "+insightVersionInHeader);
                if(!parentList.contains(insightVersionInHeader)) {
                    insightFail=true;
                }

            }else{
                //do nothing. no header present for this key.
            }
        }else{
            PheaderKey key=generatePheaderKey(parent, sub);
             if(pheaderMap.containsKey(key)){
                Pheader ph=pheaderMap.get(key);     //get headers of the parent
                insightVersionInHeader=ph.getInsightVersion();
                System.out.println("fend.workspace.WorkspaceController.checkInsightDependency(): for sub: "+sub.getSubsurface()+" found insight: "+insightVersionInHeader);
                if(!parentList.contains(insightVersionInHeader)) {
                    insightFail=true;
                }

            }else{
                //do nothing. no header present for this key.
            }
        }
            
        
        
    }
        if(insightFail){
            resultHolder.result=DEPENDENCY_FAIL_ERROR;
            resultHolder.reason=DoubtStatusModel.getNewDoubtInsightMessage(parent.getNameJobStep(), sub.getSubsurface(), insightsInParent, insightVersionInHeader, doubtTypeInsight.getName());
        }else{
            resultHolder.result=DEPENDENCY_PASS;
            resultHolder.reason=DoubtStatusModel.getInsightDependencyPassedMessage(parent.getNameJobStep(), sub.getSubsurface(), doubtTypeInsight.getName());
        }
        
        return resultHolder;
               
    }
    
    
    private ResultHolder checkInsightDependencyOnLeaf(Link link,Subsurface sub){
       // Job parent=link.getParent();
        Job child=link.getChild();
        
        ResultHolder resultHolder=new ResultHolder();
        
        boolean insightFail=false;
        
        String insightsInChild=child.getInsightVersions();                // ins1; ins2; ins3;
            List<String> childList=new ArrayList<>();
            String insightVersionInHeader=" **NO ENTRY FOUND** ";
            if(insightsInChild==null){
                insightFail=true;
            }
            else{
           
            
            String[] parts=insightsInChild.split(";");
            for (String s: parts){
                System.out.println("fend.workspace.WorkspaceController.checkInsightDependency(): for job : "+child.getNameJobStep()+" found: "+s);
                childList.add(s);
            };
        
        
        
        boolean childIsSegy=child.getNodetype().equals(nodeSegy);
        if(!childIsSegy){
            HeaderKey key=generateHeaderKey(child, sub);
            if(headerMap.containsKey(key)){
                Header hp=headerMap.get(key);     //get headers of the parent
                insightVersionInHeader=hp.getInsightVersion();
                System.out.println("fend.workspace.WorkspaceController.checkInsightDependency(): for sub: "+sub.getSubsurface()+" found insight: "+insightVersionInHeader);
                if(!childList.contains(insightVersionInHeader)) {
                    insightFail=true;
                }

            }else{
                //do nothing. no header present for this key.
            }
        }else{
            PheaderKey key=generatePheaderKey(child, sub);
            if(pheaderMap.containsKey(key)){
                Pheader ph=pheaderMap.get(key);     //get headers of the parent
                insightVersionInHeader=ph.getInsightVersion();
                System.out.println("fend.workspace.WorkspaceController.checkInsightDependency(): for sub: "+sub.getSubsurface()+" found insight: "+insightVersionInHeader);
                if(!childList.contains(insightVersionInHeader)) {
                    insightFail=true;
                }

            }else{
                //do nothing. no header present for this key.
            }
        }
            
        
        
    }
        if(insightFail){
            resultHolder.result=DEPENDENCY_FAIL_ERROR;
            resultHolder.reason=DoubtStatusModel.getNewDoubtInsightMessage(child.getNameJobStep(), sub.getSubsurface(), insightsInChild, insightVersionInHeader, doubtTypeInsight.getName());
        }else{
            resultHolder.result=DEPENDENCY_PASS;
            resultHolder.reason=DoubtStatusModel.getInsightDependencyPassedMessage(child.getNameJobStep(), sub.getSubsurface(), doubtTypeInsight.getName());
        }
        
        return resultHolder;
               
    }
    


 /**
  * Rebuild ancestors and descendants for a job that's deleted
  * handling delete 
  **/
    
 
    
 /**
  * new summary algorithm 23rd April 2018
**/   
    public void summarizeOne() throws Exception{
        
         /*
            Clear all containers
         */
        clearAllMaps();
        
        /*
            load all necessary maps
        */
        loadAllMaps();
        
        
            // Consider only those subsurfaces that have changed.
        
            List<Callable<String>> tasks = new ArrayList<>();

        execService = Executors.newFixedThreadPool(processorsUsed());

        String latestSummaryTime = subsurfaceJobService.getLatestSummaryTime();
        for (Map.Entry<Subsurface, Set<Link>> entry : subsurfaceLinkMap.entrySet()) {
            Subsurface subb = entry.getKey();
            Set<Link> links = entry.getValue();

            Callable<String> summaryTask = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    
                    for(Link link : links){
                        /**
                         * the dot that the link belongs to 
                         */
                        Dot dot = link.getDot();                     
                         
                        
                        
                        /**
                         * checkXXXDependency(args..) function returns -1 (ERROR) ,0 (WARNING) or 1 (GOOD) 
                         */
                        
                        /**
                         * Based on the type of jobs at the end of each link , the procedure has to be customized
                         * 
                         */
                        /*boolean acquisitionType=link.getParent().getNodetype().getIdNodeType().equals(JobType0Model.ACQUISITION) || link.getChild().getNodetype().getIdNodeType().equals(JobType0Model.ACQUISITION);
                        boolean textType=link.getParent().getNodetype().getIdNodeType().equals(JobType0Model.TEXT) || link.getChild().getNodetype().getIdNodeType().equals(JobType0Model.TEXT);*/
                        boolean acquisitionType=link.getParent().getNodetype().equals(nodeAcq) || link.getChild().getNodetype().equals(nodeAcq);
                        boolean textType=link.getParent().getNodetype().equals(nodeText) || link.getChild().getNodetype().equals(nodeText) ;
                        boolean segyType=link.getParent().getNodetype().equals(nodeSegy) || link.getChild().getNodetype().equals(nodeSegy);
                        boolean segdOr2DOrSegy= !acquisitionType && !textType || segyType;
                        
                        if(acquisitionType){
                                boolean forLeaf=true;
                                ResultHolder timestatus=new ResultHolder();
                                timestatus.result=DEPENDENCY_PASS;                                                    // force good .all processing done after acquisition. 
                                setDoubt(doubtTypeTime, timestatus, dot, subb, link,!forLeaf);
                                
                                ResultHolder tracestatus=new ResultHolder();
                                tracestatus.result=DEPENDENCY_PASS;                                                    // force good . not applicable (or is it?? no of shots acquired per line to the next steps?)
                                setDoubt(doubtTypeTraces, tracestatus, dot, subb, link,!forLeaf);
                                
                                ResultHolder qcstatus=checkQcDependency(link, subb);
                                setDoubt(doubtTypeQc, qcstatus, dot, subb, link,!forLeaf);
                                
                                ResultHolder insightStatus=new ResultHolder();
                                insightStatus.result=DEPENDENCY_PASS;                                                  // force good
                                setDoubt(doubtTypeInsight,insightStatus,dot,subb,link,!forLeaf);
                                
                                if(link.getChild().isLeaf()){
                                    ResultHolder qcstatusForLeaf=checkQcDependencyOnLeaf(link, subb);
                                    setDoubt(doubtTypeQc, qcstatusForLeaf, dot, subb, link,forLeaf);
                                    
                                    ResultHolder insightStatusForLeaf=checkInsightDependencyOnLeaf(link, subb);
                                    setDoubt(doubtTypeInsight,insightStatusForLeaf,dot,subb,link,forLeaf);
                                }
                        }
                        
                        
                        if(segdOr2DOrSegy){
                                boolean forLeaf=true;
                                ResultHolder timestatus=checkTimeDependency(link, subb);
                                setDoubt(doubtTypeTime,timestatus,dot,subb,link,!forLeaf);


                                ResultHolder tracestatus=checkTraceDependency(link,subb);
                                setDoubt(doubtTypeTraces,tracestatus,dot,subb,link,!forLeaf);
                                
                                ResultHolder qcstatus=checkQcDependency(link, subb);
                                setDoubt(doubtTypeQc, qcstatus, dot, subb, link,!forLeaf);
                               
                                
                                ResultHolder insightStatus=checkInsightDependency(link, subb);
                                setDoubt(doubtTypeInsight,insightStatus,dot,subb,link,!forLeaf);
                                
                                 
                                if(link.getChild().isLeaf()){
                                    ResultHolder qcstatusForLeaf=checkQcDependencyOnLeaf(link, subb);
                                    setDoubt(doubtTypeQc, qcstatusForLeaf, dot, subb, link,forLeaf);
                                    
                                    ResultHolder insightStatusForLeaf=checkInsightDependencyOnLeaf(link, subb);
                                    setDoubt(doubtTypeInsight,insightStatusForLeaf,dot,subb,link,forLeaf);
                                }
                                
                        }
                        
                        /*   if(segyType){
                        boolean forLeaf=true;
                        ResultHolder timestatus=checkTimeDependency(link, subb);
                        setDoubt(doubtTypeTime,timestatus,dot,subb,link,!forLeaf);
                        
                        
                        ResultHolder tracestatus=checkTraceDependency(link,subb);
                        setDoubt(doubtTypeTraces,tracestatus,dot,subb,link,!forLeaf);
                        
                        ResultHolder qcstatus=checkQcDependency(link, subb);
                        setDoubt(doubtTypeQc, qcstatus, dot, subb, link,!forLeaf);
                        
                        
                        ResultHolder insightStatus=checkInsightDependency(link, subb);
                        setDoubt(doubtTypeInsight,insightStatus,dot,subb,link,!forLeaf);
                        
                        
                        if(link.getChild().isLeaf()){
                        ResultHolder qcstatusForLeaf=checkQcDependencyOnLeaf(link, subb);
                        setDoubt(doubtTypeQc, qcstatusForLeaf, dot, subb, link,forLeaf);
                        
                        ResultHolder insightStatusForLeaf=checkInsightDependencyOnLeaf(link, subb);
                        setDoubt(doubtTypeInsight,insightStatusForLeaf,dot,subb,link,forLeaf);
                        }
                        
                        }
                        */
                        
                        
                        String summaryTime = DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);

                                SubsurfaceJobKey sjkey = generateSubsurfaceJobKey(link.getChild(), subb);
                                if (subsurfaceJobSummaryTimeMap.containsKey(sjkey)) {
                                    subsurfaceJobSummaryTimeMap.get(sjkey).setSummaryTime(summaryTime);
                                }
                                SubsurfaceJobKey spjkey = generateSubsurfaceJobKey(link.getParent(), subb);
                                if (subsurfaceJobSummaryTimeMap.containsKey(spjkey)) {
                                    subsurfaceJobSummaryTimeMap.get(spjkey).setSummaryTime(summaryTime);
                                }
                        
                         
                    }
                    return "Finished Summarizing sub : " + subb.getSubsurface() + " @ " + timeNow();
                }

               
            };
                System.out.println("fend.workspace.WorkspaceController.summarizeOne(): Task made for " + subb.getSubsurface());
            tasks.add(summaryTask);

        }
        
        
         System.out.println("fend.workspace.WorkspaceController.summarizeOne(): waiting on threads to finish");

        try {
            List<Future<String>> futures = execService.invokeAll(tasks);
            for (Future<String> future : futures) {
                System.out.println("future.get: " + future.get());
            }
            execService.shutdown();

        } catch (InterruptedException ex) {
            Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(DugLogManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /**
         * pass down to descendants
         */
        passDoubtsDownwards();
        
        /**
         * Populate summaries
         */
        populateSummaries();
        
        
        /**
         * Database operations
         */
        summaryDatabaseOperations();
       
        
        
        
    }
    private PheaderService pheaderService=new PheaderServiceImpl();
  
    
    private void loadAllMaps(){
        
        
        
       
        
        /**
         * Get all inherited doubts from the database.
         * Create a map (iMap) of <<Doubt> key=cause,List<Long> idsOfinheritedFromCause>> for lookup.
         * this map is used to delete inherited doubts before commit operations to db
         * 
         **/
        List<Doubt> inheritanceDoubtsInWorkspace = doubtService.getAllDoubtsJobsAndSubsurfacesFor(dbWorkspace, doubtTypeInherit);
         for (Doubt inheritedDoubt : inheritanceDoubtsInWorkspace) {
            
            Doubt cause = inheritedDoubt.getDoubtCause();
            if (!causeAndAssociatedInheritanceMap.containsKey(cause)) {
                causeAndAssociatedInheritanceMap.put(cause, new ArrayList<Doubt>());
                causeAndAssociatedInheritanceMap.get(cause).add(inheritedDoubt);
            } else {
                causeAndAssociatedInheritanceMap.get(cause).add(inheritedDoubt);
            }
            
            if(!iMap.containsKey(cause)){
                iMap.put(cause, new ArrayList<>());
                iMap.get(cause).add(inheritedDoubt.getId());
            }else{
                iMap.get(cause).add(inheritedDoubt.getId());
            }
        }
         
         
        /**
         * get all doubts from the database 
         * Create a map of doubt<DoubtKey(dot,job,sub),DoubtHolder> and populate these entries
         * 
         * Create a "hack map" dcMap ( for summary lookup of direct causes)
         * 
         * 
         */
        List<Doubt> existingCausesInWorkspace = doubtService.getAllDoubtsExceptInheritanceFor(dbWorkspace);
        for (Doubt d : existingCausesInWorkspace){
            DoubtKey key = generateDoubtKey(d.getSubsurface(), d.getChildJob(), d.getDot(), d.getDoubtType());
            DoubtHolder dh = new DoubtHolder();
            dh.cause = d;
            dMap.put(key, dh);
        }
        
        /**
         * dMap is now one-one with the database table
         **/
        
         
         
          /**
         * get all summaries from the database 
         * Create a map of summary<SummaryKey(job,Subsurface), SummaryHolder> and populate these
         * entries
         */
          /*  List<Summary> allSummariesInWorkspace = summaryService.getSummariesFor(dbWorkspace);
          for (Summary s : allSummariesInWorkspace) {
          SummaryKey key = generateSummaryKey(s.getSubsurface(), s.getJob());
          SummaryHolder sh = new SummaryHolder();
          sh.summary = s;
          sh.update=true;
          sMap.put(key, sh);
          
          }*/

        /**
         * sMap is now one-one with the database table
         */
        /**
         * add new summary entries to sMap.
         * All subsurfaces acquired will have an entry
         * execute the for loop only if there aren't as many summaries as there are entries in the subsurface-job table
         **/
         List<SubsurfaceJob> allSubsurfacesJobsInCurrentWorkspace=subsurfaceJobService.getSubsurfaceJobFor(dbWorkspace);
     //    if(sMap.keySet().size() != allSubsurfacesJobsInCurrentWorkspace.size()){
             
         
             for (SubsurfaceJob sj : allSubsurfacesJobsInCurrentWorkspace) {
                 SummaryKey key = generateSummaryKey(sj.getSubsurface(), sj.getJob());
                 SummaryHolder sh;
                 if (!sMap.containsKey(key)) {
                     sh = new SummaryHolder();
                     sh.create = true;

                     Summary sum = new Summary();
                     sum.setSequence(sj.getSubsurface().getSequence());
                     sum.setSubsurface(sj.getSubsurface());
                     sum.setJob(sj.getJob());
                     
                     sum.setWorkspace(dbWorkspace);
                     System.out.println("fend.workspace.WorkspaceController.summarizeZero(): " + timeNow() + "    Creating summary for " + sum.getId());

                     sh.summary = sum;

                     sMap.put(key, sh);
                 }
             }
         
       //  }
         
        
        /**
         * Retrieve all links that need to be summarized
         * 
         **/
        
        List<Object[]> elementsToSummarize = linkService.getSubsurfaceAndLinksForSummary(dbWorkspace);
        System.out.println("fend.workspace.WorkspaceController.summarizeZero() : " + timeNow() + " Retrieved " + elementsToSummarize.size() + " elements to summarize");
        System.out.println("fend.workspace.WorkspaceController.summarizeZero() : " + timeNow() + " Building the elements map");

        for (Object[] element : elementsToSummarize) {
            SubsurfaceJob sjc = (SubsurfaceJob) element[2];     //child job of the link
            SubsurfaceJob sjp = (SubsurfaceJob) element[1];     //parent job of the link
            Link value = (Link) element[0];
            
            Subsurface ckey = sjc.getSubsurface();
            if (!subsurfaceLinkMap.containsKey(ckey)) {
                subsurfaceLinkMap.put(ckey, new HashSet<>());
                subsurfaceLinkMap.get(ckey).add(value);
            } else {
                subsurfaceLinkMap.get(ckey).add(value);
            }
            subsurfaceJobSummaryTimeMap.put(generateSubsurfaceJobKey(sjp.getJob(), sjp.getSubsurface()), sjp);
            subsurfaceJobSummaryTimeMap.put(generateSubsurfaceJobKey(sjc.getJob(), sjc.getSubsurface()), sjc);

        }
        
        /**
         * subsurfaceLinkMap<Subsurface,Set<Link>> is a map of the links containing the subsurface
         * subsurfaceJobSummaryTimeMap<SubsurfaceKey(job,subsurface),Job> is a map of the subsurfaceJob entries to be updated
         **/
        
        /**
         * Get chosen headers for dependency checks
         * Create a headerMap<HeaderKey(job,subsurface),Header> and populate it
         */
        
        List<Header> headersInWorkspace = headerService.getChosenHeadersForWorkspace(dbWorkspace);
        for (Header h : headersInWorkspace) {
            HeaderKey headerKey = new HeaderKey();
            headerKey.job = h.getJob();
            headerKey.subsurface = h.getSubsurface();

            headerMap.put(headerKey, h);
        }
        /**
         * headerMap is now one-one with database table
         */
        
        
        /**
         * This is for the external (public) headers
         * Get chosen headers for dependency checks
         * Create a pheaderMap<PheaderKey(job,subsurface),Header> and populate it
         */
        
        List<Pheader> pheadersInWorkspace = pheaderService.getChosenHeadersForWorkspace(dbWorkspace);
        for (Pheader h : pheadersInWorkspace) {
            PheaderKey pheaderKey = new PheaderKey();
            pheaderKey.job = h.getJob();
            pheaderKey.subsurface = h.getSubsurface();

            pheaderMap.put(pheaderKey, h);
        }
        /**
         * headerMap is now one-one with database table
         */
        
        
        /**
         * Get variable arguments for dependency checks
         * Create a variableArgumentMap<Dot,List<VariableArgument>> and populate it
         */
        
         List<VariableArgument> variableArgumentsForWorkspace = variableArgumentService.getVariableArgumentsForWorkspace(dbWorkspace);
        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : " + timeNow() + " fetched " + variableArgumentsForWorkspace.size() + " variable - arguments for this workspace");

        System.out.println("fend.workspace.WorkspaceController.summarizeInMemory() : " + timeNow() + " building the variableArguments Map");
        for (VariableArgument va : variableArgumentsForWorkspace) {
            if (!variableArgumentMap.containsKey(va.getDot())) {
                variableArgumentMap.put(va.getDot(), new ArrayList<VariableArgument>());
                variableArgumentMap.get(va.getDot()).add(va);
            } else {
                variableArgumentMap.get(va.getDot()).add(va);
            }

        }
        
        /**
         * variableArgumentMap is now one-one with database table
         **/
        
                
        /**
         * Get descendants 
         * Create a descendantMap<DescendantKey(job,subsurface),Descendant>  //all descendants for job=job that contain sub=subsurface
         *
         */
        List<Object[]> descendantsForSummary = descendantService.getDescendantsSubsurfaceJobsForSummary(dbWorkspace);
        for (Object[] descendantForSummary : descendantsForSummary){
            SubsurfaceJob sj = (SubsurfaceJob) descendantForSummary[1];
            Job parentJob = (Job) descendantForSummary[2];
            Descendant desc = (Descendant) descendantForSummary[0];
            DescendantKey key = generateDescendantKey(parentJob, sj.getSubsurface());
            if (!descendantMapForSummary.containsKey(key)) {
                descendantMapForSummary.put(key, new ArrayList<Descendant>());
                descendantMapForSummary.get(key).add(desc);
            } else {
                descendantMapForSummary.get(key).add(desc);
            }
        }
        
        /**
         * descendantMapForSummary is now one-one with database table
         **/
        
        
        /**
         * Get the dot-job map. 
         * Used to determine the correct dot when dealing with ancestor jobs. 
         * The dot and the job are together required to generate the DoubtKey
         **/
        
        List<Link> dotAncestorList=linkService.getDotJobListForWorkspace(dbWorkspace);
        for(Link lk:dotAncestorList){
            Dot d=lk.getDot();
            Job j=lk.getChild();
            boolean parentIsRoot=lk.getParent().isRoot();
            
            
            if(!djMap.containsKey(j)){
                djMap.put(j, new ArrayList<>());
                djMap.get(j).add(d);
                
                
            }else{
                djMap.get(j).add(d);
            }
            
            if(parentIsRoot){
                Job parent=lk.getParent();
                if(!djMap.containsKey(parent)){
                    djMap.put(parent, new ArrayList<>());
                    djMap.get(parent).add(d);
                }else{
                    
                }
                       
            }
        }
        
        /**
         * Get ancestors
         * Create an ancestorMap<AncestorKey(job,subsurface),Ancestor>    //all ancestors for job=job that contain sub=subsurface
         * 
         **/
        
        List<Object[]> ancestorsForSummary = ancestorService.getAncestorsSubsurfaceJobsForSummary(dbWorkspace);
        for (Object[] ancestorForSummary : ancestorsForSummary) {
            SubsurfaceJob sj = (SubsurfaceJob) ancestorForSummary[1];
            Job childJob = (Job) ancestorForSummary[2];
            Ancestor anc = (Ancestor) ancestorForSummary[0];
            AncestorKey key = generateAncestorKey(childJob, sj.getSubsurface());
            if (!ancestorMapForSummary.containsKey(key)) {
                ancestorMapForSummary.put(key, new ArrayList<Ancestor>());
                ancestorMapForSummary.get(key).add(anc);
            } else {
                ancestorMapForSummary.get(key).add(anc);
            }
        }
        
    }
    
    private void setDoubt(DoubtType doubtType, ResultHolder result, Dot dot, Subsurface sub, Link link,Boolean forLeaf) {
        if(result.result == DEPENDENCY_FAIL_ERROR){
            /**
             * if doubt exists for the key (doubtType,dot,sub,job) then update its reason 
             * else if it doesn't exist then create a new doubt for the key and add to map. 
             * update its reason 
             * set status to YES
             * set state to ERROR
             */
            Job jobWithDoubt;
            List<DoubtKey> keys=new ArrayList<>();
            
            if (doubtType.equals(doubtTypeTime)) {                               //time on child
                jobWithDoubt = link.getChild();
                DoubtKey key = generateDoubtKey(sub, jobWithDoubt, dot, doubtType);
                keys.add(key);
            } else if (doubtType.equals(doubtTypeTraces)) {                      //trace on child
                jobWithDoubt = link.getChild();
                DoubtKey key = generateDoubtKey(sub, jobWithDoubt, dot, doubtType);
                keys.add(key);
            } else if (doubtType.equals(doubtTypeQc)) {                          //qc on parent
                
                
                                if(forLeaf){
                                    jobWithDoubt = link.getChild();
                                }else{
                                    jobWithDoubt = link.getParent();
                                }
                                
                                
                              
                                if(jobWithDoubt.isRoot()){
                                    if(!djMap.containsKey(jobWithDoubt)){
                                        djMap.put(jobWithDoubt, new ArrayList<>());
                                        djMap.get(jobWithDoubt).add(dot);
                                    }
                                }
                                
                                
                                
                                if (djMap.containsKey(jobWithDoubt)) {                              //remaining cases where the parent is not a root.
                                    List<Dot> dotsParent = djMap.get(jobWithDoubt);                //dot(s) of the link(s) of which the parent is a child
                                    for (Dot dp : dotsParent) {
                                        DoubtKey key = generateDoubtKey(sub, jobWithDoubt, dp, doubtType);
                                        keys.add(key);
                                    }
                                } 
                                    
                               
                                
                                
            } else if (doubtType.equals(doubtTypeInsight)) {                     //insight on parent
               
                                if(forLeaf){
                                    jobWithDoubt = link.getChild();
                                }else{
                                    jobWithDoubt = link.getParent();
                                }
                                
                                
                              
                                if(jobWithDoubt.isRoot()){
                                    if(!djMap.containsKey(jobWithDoubt)){
                                        djMap.put(jobWithDoubt, new ArrayList<>());
                                        djMap.get(jobWithDoubt).add(dot);
                                    }
                                }
                                
                                
                                
                                if (djMap.containsKey(jobWithDoubt)) {                              //remaining cases where the parent is not a root.
                                    List<Dot> dotsParent = djMap.get(jobWithDoubt);                //dot(s) of the link(s) of which the parent is a child
                                    for (Dot dp : dotsParent) {
                                        DoubtKey key = generateDoubtKey(sub, jobWithDoubt, dp, doubtType);
                                        keys.add(key);
                                    }
                                } 
                                    
                
                
            } else {
                jobWithDoubt = link.getChild();                                  //default on child   
            }
            //DoubtKey key = generateDoubtKey(sub, jobWithDoubt, dot, doubtType);  //cycle through each key
            for(DoubtKey key:keys){
                
                DoubtHolder dh;
                if (dMap.containsKey(key)) {
                    dh = dMap.get(key);
                    dh.cause.setReason(result.reason);
                    dh.cause.setStatus(DoubtStatusModel.YES);
                    dh.cause.setState(DoubtStatusModel.ERROR);
                    dh.update = true;
                    dh.delete = false;
                } else {
                    dh = new DoubtHolder();
                    dh.cause = new Doubt();
                    //dh.cause.setChildJob(jobWithDoubt);
                    dh.cause.setChildJob(key.job);
                    dh.cause.setLink(link);
                    dh.cause.setDot(key.dot);
                    //dh.cause.setSubsurface(sub);
                    dh.cause.setSubsurface(key.subsurface);
                    //dh.cause.setSequence(sub.getSequence());
                    dh.cause.setSequence(key.subsurface.getSequence());
                    dh.cause.setReason(result.reason);
                    dh.cause.setStatus(DoubtStatusModel.YES);
                    dh.cause.setState(DoubtStatusModel.ERROR);
                    dh.cause.setTimeStamp(timeNow());
                    dh.cause.setDoubtType(doubtType);
                    dh.create = true;
                    dh.delete = false;
                    dMap.put(key, dh);
                }
                
            }
           
            
                          
        }else if(result.result == DEPENDENCY_FAIL_WARNING){     // if it's a warning
             /**
             * if doubt exists for the key (doubtType,dot,sub,job) then update its reason 
             * else if it doesn't exist then create a new doubt for the key and add to map. 
             * update its reason 
             * set state to YES
             */
             /*Job jobWithDoubt;
             if (doubtType.equals(doubtTypeTime)) {                               //time on child
             jobWithDoubt = link.getChild();
             } else if (doubtType.equals(doubtTypeTraces)) {                      //trace on child
             jobWithDoubt = link.getChild();
             } else if (doubtType.equals(doubtTypeQc)) {                          //qc on parent
             jobWithDoubt = link.getParent();
             } else if (doubtType.equals(doubtTypeInsight)) {                     //insight on parent
             jobWithDoubt = link.getParent();
             } else {
             jobWithDoubt = link.getChild();                                  //default on child
             }*/
             
            Job jobWithDoubt;
            List<DoubtKey> keys=new ArrayList<>();
            
            if (doubtType.equals(doubtTypeTime)) {                               //time on child
                jobWithDoubt = link.getChild();
                DoubtKey key = generateDoubtKey(sub, jobWithDoubt, dot, doubtType);
                keys.add(key);
            } else if (doubtType.equals(doubtTypeTraces)) {                      //trace on child
                jobWithDoubt = link.getChild();
                DoubtKey key = generateDoubtKey(sub, jobWithDoubt, dot, doubtType);
                keys.add(key);
            } else if (doubtType.equals(doubtTypeQc)) {                          //qc on parent
                
                
                                 if(forLeaf){
                                    jobWithDoubt = link.getChild();
                                }else{
                                    jobWithDoubt = link.getParent();
                                }
                                
                                
                                if(jobWithDoubt.isRoot()){
                                    /* DoubtKey key=generateDoubtKey(sub, jobWithDoubt, dot, doubtType);  //make a key with the links dot. This is unique
                                    keys.add(key);*/
                                    if(!djMap.containsKey(jobWithDoubt)){
                                        djMap.put(jobWithDoubt, new ArrayList<>());
                                        djMap.get(jobWithDoubt).add(dot);
                                    }
                                }
                                

                                if (djMap.containsKey(jobWithDoubt)) {
                                    List<Dot> dotsParent = djMap.get(jobWithDoubt);                //dot(s) of the link(s) of which the parent is a child
                                    for (Dot dp : dotsParent) {
                                        DoubtKey key = generateDoubtKey(sub, jobWithDoubt, dp, doubtType);
                                        keys.add(key);
                                    }
                                } 

                                
                                
            } else if (doubtType.equals(doubtTypeInsight)) {                     //insight on parent
                
                                if(forLeaf){
                                    jobWithDoubt = link.getChild();
                                }else{
                                    jobWithDoubt = link.getParent();
                                }
                                
                                
                              
                                if(jobWithDoubt.isRoot()){
                                    if(!djMap.containsKey(jobWithDoubt)){
                                        djMap.put(jobWithDoubt, new ArrayList<>());
                                        djMap.get(jobWithDoubt).add(dot);
                                    }
                                }
                                
                                
                                
                                if (djMap.containsKey(jobWithDoubt)) {                              //remaining cases where the parent is not a root.
                                    List<Dot> dotsParent = djMap.get(jobWithDoubt);                //dot(s) of the link(s) of which the parent is a child
                                    for (Dot dp : dotsParent) {
                                        DoubtKey key = generateDoubtKey(sub, jobWithDoubt, dp, doubtType);
                                        keys.add(key);
                                    }
                                } 
                                    
                
                
            } else {
                jobWithDoubt = link.getChild();                                  //default on child   
            }
             
             
             
            //DoubtKey key = generateDoubtKey(sub, jobWithDoubt, dot, doubtType);
             for(DoubtKey key:keys){
                 
                                DoubtHolder dh;
                                if (dMap.containsKey(key)) {
                                    dh = dMap.get(key);
                                    dh.cause.setReason(result.reason);
                                    dh.cause.setStatus(DoubtStatusModel.YES);
                                    dh.cause.setState(DoubtStatusModel.WARNING);
                                    dh.update = true;
                                    dh.delete = false;
                                } else {
                                    dh = new DoubtHolder();
                                    dh.cause = new Doubt();
                                    dh.cause.setChildJob(jobWithDoubt);
                                    dh.cause.setLink(link);
                                    //dh.cause.setDot(dot);
                                    dh.cause.setDot(key.dot);
                                    dh.cause.setSubsurface(sub);
                                    dh.cause.setSequence(sub.getSequence());
                                    dh.cause.setReason(result.reason);
                                    dh.cause.setStatus(DoubtStatusModel.YES);
                                    dh.cause.setState(DoubtStatusModel.WARNING);
                                    dh.cause.setTimeStamp(timeNow());
                                    dh.cause.setDoubtType(doubtType);
                                    dh.create = true;
                                    dh.delete = false;
                                    dMap.put(key, dh);
                                }
                                
             }
            
        }else{  //all good 
            /**
             * Delete any existing doubts
             * 
             */
           
            Job jobWithDoubt;
            List<DoubtKey> keys=new ArrayList<>();
            
            if (doubtType.equals(doubtTypeTime)) {                               //time on child
                jobWithDoubt = link.getChild();
                DoubtKey key = generateDoubtKey(sub, jobWithDoubt, dot, doubtType);
                keys.add(key);
            } else if (doubtType.equals(doubtTypeTraces)) {                      //trace on child
                jobWithDoubt = link.getChild();
                DoubtKey key = generateDoubtKey(sub, jobWithDoubt, dot, doubtType);
                keys.add(key);
            } else if (doubtType.equals(doubtTypeQc)) {                          //qc on parent
                
                
                                if(forLeaf){
                                    jobWithDoubt = link.getChild();
                                }else{
                                    jobWithDoubt = link.getParent();
                                }
                                
                                if(jobWithDoubt.isRoot()){
                                   if(!djMap.containsKey(jobWithDoubt)){
                                        djMap.put(jobWithDoubt, new ArrayList<>());
                                        djMap.get(jobWithDoubt).add(dot);
                                    }
                                }
                                if (djMap.containsKey(jobWithDoubt)) {
                                    List<Dot> dotsParent = djMap.get(jobWithDoubt);                //dot(s) of the link(s) of which the parent is a child
                                    for (Dot dp : dotsParent) {
                                        DoubtKey key = generateDoubtKey(sub, jobWithDoubt, dp, doubtType);
                                        keys.add(key);
                                    }
                                } 

                                
                                
            } else if (doubtType.equals(doubtTypeInsight)) {                     //insight on parent
                
                                if(forLeaf){
                                    jobWithDoubt = link.getChild();
                                }else{
                                    jobWithDoubt = link.getParent();
                                }
                                
                                
                              
                                if(jobWithDoubt.isRoot()){
                                    if(!djMap.containsKey(jobWithDoubt)){
                                        djMap.put(jobWithDoubt, new ArrayList<>());
                                        djMap.get(jobWithDoubt).add(dot);
                                    }
                                }
                                
                                
                                
                                if (djMap.containsKey(jobWithDoubt)) {                              //remaining cases where the parent is not a root.
                                    List<Dot> dotsParent = djMap.get(jobWithDoubt);                //dot(s) of the link(s) of which the parent is a child
                                    for (Dot dp : dotsParent) {
                                        DoubtKey key = generateDoubtKey(sub, jobWithDoubt, dp, doubtType);
                                        keys.add(key);
                                    }
                                } 
                                    
                
                
            } else {
                jobWithDoubt = link.getChild();                                  //default on child   
            }
             
             for(DoubtKey key:keys){
                 //DoubtKey key = generateDoubtKey(sub, jobWithDoubt, dot, doubtType);
                 DoubtHolder dh;
                 if (dMap.containsKey(key)) {
                     dh = dMap.get(key);
                     dh.delete = true;
                 } else {

                 }
             }
            
            
        }
    }
    
        List<Doubt> newDoubts=new ArrayList<>();
        List<Long> deleteDoubts=new ArrayList<>();
        List<Doubt> updateDoubts=new ArrayList<>();
        List<Doubt> newInheritedDoubts=new ArrayList<>();
        Map<SubsurfaceJobKey,List<Doubt>> inheritanceLUMap=new HashMap<>();
       
    
    private void passDoubtsDownwards(){
        System.out.println("fend.workspace.WorkspaceController.passDoubtsDownwards(): started @ "+timeNow());
        
        
        for(DoubtHolder dh: dMap.values()){
            Doubt cause = dh.cause;
            if (dh.create) {
                newDoubts.add(cause);
            } else if (dh.update) {
                updateDoubts.add(cause);
            } else if (dh.delete) {
                deleteDoubts.add(cause.getId());
                continue;
            }
            
            Subsurface sub=cause.getSubsurface();
            Job rootJob=cause.getChildJob();
            
            if(cause.getState().equals(DoubtStatusModel.ERROR)){
                /*
                * descend doubts into descendants of the rootJob that contain this sub
                */
                  
                        
                        DescendantKey descendantKey = generateDescendantKey(rootJob, sub);
                        List<Descendant> descendantsThatContainSub = new ArrayList<>();
                        if (descendantMapForSummary.containsKey(descendantKey)) {
                            descendantsThatContainSub = descendantMapForSummary.get(descendantKey);
                        }
                        
                        for(Descendant descendant:descendantsThatContainSub){
                            Job descendantJob=descendant.getDescendant();
                            Doubt inheritedDoubt=new Doubt();
                            inheritedDoubt.setChildJob(descendantJob);
                            inheritedDoubt.setSubsurface(sub);
                            inheritedDoubt.setSequence(sub.getSequence());
                            inheritedDoubt.setDoubtCause(cause);
                            inheritedDoubt.setDoubtType(doubtTypeInherit);
                            String reason=(DoubtStatusModel.getInheritanceMessage(descendantJob,cause));
                            inheritedDoubt.setReason(reason);
                            dh.inheritedDoubts.add(inheritedDoubt);
                            newInheritedDoubts.add(inheritedDoubt);
                            
                            SubsurfaceJobKey sjkey=generateSubsurfaceJobKey(descendantJob, sub);
                            if(!inheritanceLUMap.containsKey(sjkey)){
                                inheritanceLUMap.put(sjkey, new ArrayList<>());
                                inheritanceLUMap.get(sjkey).add(inheritedDoubt);
                            }else{
                                inheritanceLUMap.get(sjkey).add(inheritedDoubt);
                            }
                            
                        }
            }
            
        }
        System.out.println("fend.workspace.WorkspaceController.passDoubtsDownwards(): finished @ "+timeNow()+ " size of the inherited doubts : "+newInheritedDoubts.size());
        
        
    }
    
    
    private void populateSummaries(){
       
        
        for(SummaryHolder sh : sMap.values()){
            Subsurface sub = sh.summary.getSubsurface();
            Job job=sh.summary.getJob();
            Summary summary=sh.summary;
            newSummaries.add(summary);
            if(!djMap.containsKey(job)){
                System.out.println("fend.workspace.WorkspaceController.populateSummaries(): job "+job.getNameJobStep()+ " key not found. All summaries set to true");
                summary.setAll(false);
                continue;
            }
            List<Dot> dots=djMap.get(job);
            
            for(Dot dot:dots){
                    DoubtKey timeKey=generateDoubtKey(sub, job, dot, doubtTypeTime);
                    DoubtKey traceKey=generateDoubtKey(sub, job, dot, doubtTypeTraces);
                    DoubtKey qcKey=generateDoubtKey(sub, job, dot, doubtTypeQc);
                    DoubtKey insightKey=generateDoubtKey(sub, job, dot, doubtTypeInsight);
                    
                        //time Start
                        if(dMap.containsKey(timeKey)){
                            DoubtHolder dh=dMap.get(timeKey);
                            if(!dh.delete){
                                Doubt cause=dh.cause;
                                boolean error=cause.getState().equals(DoubtStatusModel.ERROR);
                                if(error){
                                    summary.setFailedTimeDependency(true);
                                    summary.setWarningForTime(false);
                                    boolean  causeIsOverriden=cause.getStatus().equals(DoubtStatusModel.OVERRIDE);
                                        if(causeIsOverriden) {
                                            summary.setOverridenTimeFail(true);
                                        }else{
                                            
                                            summary.setOverridenTimeFail(false);
                                        }
                                                
                                }else{
                                    summary.setFailedTimeDependency(false);
                                    summary.setWarningForTime(true);
                                }
                            }
                        }else{
                            summary.setFailedTimeDependency(false);
                            summary.setWarningForTime(false);
                            summary.setOverridenTimeFail(false);
                            
                        }
                        
                        //time end
                        
                        //trace start
                        if(dMap.containsKey(traceKey)){
                            DoubtHolder dh=dMap.get(traceKey);
                            if(!dh.delete){
                                Doubt cause=dh.cause;
                                boolean error=cause.getState().equals(DoubtStatusModel.ERROR);
                                if(error){
                                    summary.setFailedTraceDependency(true);
                                    summary.setWarningForTrace(false);
                                    boolean  causeIsOverriden=cause.getStatus().equals(DoubtStatusModel.OVERRIDE);
                                        if(causeIsOverriden) {
                                            summary.setOverridenTraceFail(true);
                                        }else{
                                            summary.setOverridenTraceFail( false);
                                        }
                                                
                                }else{
                                    summary.setFailedTraceDependency(false);
                                    summary.setWarningForTrace(true);
                                }
                            }
                        }else{
                            summary.setFailedTraceDependency(false);
                            summary.setWarningForTrace(false);
                            summary.setOverridenTraceFail(false);
                            
                        }
                        
                        //trace end
                        
                        //qc start
                        
                        if(dMap.containsKey(qcKey)){
                            DoubtHolder dh=dMap.get(qcKey);
                            System.out.println("fend.workspace.WorkspaceController.populateSummaries(): map contains key j= "+job.getNameJobStep()+" , s= "+sub.getSubsurface()+" d= "+dot.getId()+" dt= "+doubtTypeQc.getName() );
                            if(!dh.delete){
                                System.out.println(": to update or create");
                                Doubt cause=dh.cause;
                                boolean error=cause.getState().equals(DoubtStatusModel.ERROR);
                                if(error){
                                    System.out.println(": errored.");
                                    summary.setFailedQcDependency(true);
                                    summary.setWarningForQc(false);
                                    boolean  causeIsOverriden=cause.getStatus().equals(DoubtStatusModel.OVERRIDE);
                                        if(causeIsOverriden) {
                                            summary.setOverridenQcFail(true);
                                        }else{
                                            
                                            summary.setOverridenQcFail(false);
                                        }
                                                
                                }else{
                                    System.out.println(": warning");
                                    summary.setFailedQcDependency(false);
                                    summary.setWarningForQc(true);
                                }
                            }else{
                                System.out.println(": to delete");
                            }
                        }else{
                            
                            System.out.println("fend.workspace.WorkspaceController.populateSummaries(): map DOES NOT contain key j= "+job.getNameJobStep()+" , s= "+sub.getSubsurface()+" d= "+dot.getId()+" dt= "+doubtTypeQc.getName() );
                            summary.setFailedQcDependency(false);
                            summary.setWarningForQc(false);
                            summary.setOverridenQcFail(false);
                            
                        }
                        
                        //qc end
                        
                        //insight start
                         if(dMap.containsKey(insightKey)){
                            DoubtHolder dh=dMap.get(insightKey);
                            if(!dh.delete){
                                Doubt cause=dh.cause;
                                boolean error=cause.getState().equals(DoubtStatusModel.ERROR);
                                if(error){
                                    
                                    summary.setFailedInsightDependency(true);
                                    summary.setWarningForInsight(false);
                                    boolean  causeIsOverriden=cause.getStatus().equals(DoubtStatusModel.OVERRIDE);
                                        if(causeIsOverriden) {
                                            summary.setOverridenInsightFail(true);
                                        }else{
                                            
                                            summary.setOverridenInsightFail(false);
                                        }
                                                
                                }else{
                                    summary.setFailedInsightDependency(false);
                                    summary.setWarningForInsight(true);
                                }
                            }
                        }else{
                            summary.setFailedInsightDependency(false);
                            summary.setWarningForInsight(false);
                            summary.setOverridenInsightFail(false);
                            
                        }
                        //insight end
                        
                        
                        //are there any inherited doubts on this job,sub?
                        SubsurfaceJobKey sjkey=generateSubsurfaceJobKey(job, sub);
                        List<Doubt> inheritedDoubts=new ArrayList<>();
                        if(inheritanceLUMap.containsKey(sjkey)){                    // There is inheritance on this key
                            inheritedDoubts=inheritanceLUMap.get(sjkey);
                            for(Doubt inheritedDoubt:inheritedDoubts){
                                Doubt cause=inheritedDoubt.getDoubtCause();
                                DoubtType causeType=cause.getDoubtType();
                                boolean causeIsOverriden=cause.getStatus().equals(DoubtStatusModel.OVERRIDE);
                                
                                if(causeType.equals(doubtTypeTime)){
                                    if(causeIsOverriden){
                                        sh.inheritedTimeOverridenCause.add(cause);
                                    }else{
                                        sh.inheritedTimeCause.add(cause);
                                    }
                                }
                                if(causeType.equals(doubtTypeTraces)){
                                    if(causeIsOverriden){
                                        sh.inheritedTraceOverridenCause.add(cause);
                                    }else{
                                        sh.inheritedTraceCause.add(cause);
                                    }
                                }
                                if(causeType.equals(doubtTypeQc)){
                                    if(causeIsOverriden){
                                        sh.inheritedQcOverridenCause.add(cause);
                                    }else{
                                        sh.inheritedQcCause.add(cause);
                                    }
                                }
                                if(causeType.equals(doubtTypeInsight)){
                                    if(causeIsOverriden){
                                        sh.inheritedInsightOverridenCause.add(cause);
                                    }else{
                                        sh.inheritedInsightCause.add(cause);
                                    }
                                }
                            }
                            
                           boolean inheritedTime = !sh.inheritedTimeCause.isEmpty();
                           boolean inheritedOverridenTime = !sh.inheritedTimeOverridenCause.isEmpty();
                           
                           boolean inheritedTrace = !sh.inheritedTraceCause.isEmpty();
                           boolean inheritedOverridenTrace = !sh.inheritedTraceOverridenCause.isEmpty();
                           
                           boolean inheritedQc = !sh.inheritedQcCause.isEmpty();
                           boolean inheritedOverridenQc = !sh.inheritedQcOverridenCause.isEmpty();
                           
                           boolean inheritedInsight = !sh.inheritedInsightCause.isEmpty();
                           boolean inheritedOverridenInsight = !sh.inheritedInsightOverridenCause.isEmpty();
                           
                           summary.setInheritedTimeFail(inheritedTime);
                           summary.setInheritedTimeOverride(inheritedOverridenTime);
                           
                           summary.setInheritedTraceFail(inheritedTrace);
                           summary.setInheritedTraceOverride(inheritedOverridenTrace);
                           
                           summary.setInheritedQcFail(inheritedQc);
                           summary.setInheritedQcOverride(inheritedOverridenQc);
                           
                           summary.setInheritedInsightFail(inheritedInsight);
                           summary.setInheritedInsightOverride(inheritedOverridenInsight);
                           
                        }else{                                                      // no inheritance on this key
                            summary.setInheritedTraceFail(false);
                            summary.setInheritedTimeOverride(false);
                            
                            summary.setInheritedTraceFail(false);
                            summary.setInheritedTraceOverride(false);
                            
                            summary.setInheritedQcFail(false);
                            summary.setInheritedQcOverride(false);
                            
                            summary.setInheritedInsightFail(false);
                           summary.setInheritedInsightOverride(false);
                        }
            }
            
            System.out.println("fend.workspace.WorkspaceController.populateSummaries(): for sub: "+sub.getSubsurface()+" job: "+job.getNameJobStep());
            System.out.println("failedTimeDependency:     "+summary.hasFailedTimeDependency());
            System.out.println("hasInheritedTimeFail:     "+summary.hasInheritedTimeFail());
            System.out.println("hasInheritedTimeOVerride: "+summary.hasInheritedTimeOverride());
            System.out.println("hasOverridenTimeFail:     "+summary.hasOverridenTimeFail());
            System.out.println("hasTimeWarning:           "+summary.hasWarningForTime());
            System.out.println("");
            System.out.println("failedTraceDependency:     "+summary.hasFailedTraceDependency());
            System.out.println("hasInheritedTraceFail:     "+summary.hasInheritedTraceFail());
            System.out.println("hasInheritedTraceOVerride: "+summary.hasInheritedTraceOverride());
            System.out.println("hasOverridenTraceFail:     "+summary.hasOverridenTraceFail());
            System.out.println("hasTraceWarning:           "+summary.hasWarningForTrace());
            System.out.println("");
            System.out.println("failedQcDependency:     "+summary.hasFailedQcDependency());
            System.out.println("hasInheritedQcFail:     "+summary.hasInheritedQcFail());
            System.out.println("hasInheritedQcOVerride: "+summary.hasInheritedQcOverride());
            System.out.println("hasOverridenQcFail:     "+summary.hasOverridenQcFail());
            System.out.println("hasQcWarning:           "+summary.hasWarningForQc());
            
            System.out.println("");
            System.out.println("failedInsightDependency:     "+summary.hasFailedInsightDependency());
            System.out.println("hasInheritedInsightFail:     "+summary.hasInheritedInsightFail());
            System.out.println("hasInheritedInsightOVerride: "+summary.hasInheritedInsightOverride());
            System.out.println("hasOverridenInsightFail:     "+summary.hasOverridenInsightFail());
            System.out.println("hasInsightWarning:           "+summary.hasWarningForInsight());
        }
    }
    
    private void summaryDatabaseOperations(){
         /**
         * delete all inherited doubts in database.
         */
        System.out.println("fend.workspace.WorkspaceController.summaryDatabaseOperations(): will delete all inherited doubts. Part of the rebuild");
        
        
        doubtService.deleteAllInheritedDoubts(dbWorkspace);
        
        /**
         * delete all summaries in database
         **/
        System.out.println("fend.workspace.WorkspaceController.summaryDatabaseOperations(): deleting all summaries.");
        summaryService.deleteAllSummaries(dbWorkspace); 
        
        System.out.println("fend.workspace.WorkspaceController.summaryDatabaseOperations(): Will create: "+newDoubts.size()+" new doubts");
        System.out.println("fend.workspace.WorkspaceController.summaryDatabaseOperations(): will update: "+updateDoubts.size()+" existing doubts");
        System.out.println("fend.workspace.WorkspaceController.summaryDatabaseOperations(): will delete: "+deleteDoubts.size()+" existing doubts");
        System.out.println("fend.workspace.WorkspaceController.summaryDatabaseOperations(): will create: "+newInheritedDoubts.size()+" new inherited doubts");
        System.out.println("fend.workspace.WorkspaceController.summaryDatabaseOperations(): will create: "+newSummaries.size()+" new summaries");
        
        doubtService.deleteBulkDoubts(deleteDoubts);
        doubtService.createBulkDoubts(newDoubts);
        doubtService.updateBulkDoubts(updateDoubts);
        doubtService.createBulkDoubts(newInheritedDoubts);
        
        summaryService.createBulkSummaries(newSummaries);
        
        
        List<SubsurfaceJob> subsurfaceJobsToBeUpdated = new ArrayList<SubsurfaceJob>(subsurfaceJobSummaryTimeMap.values());
        System.out.println("fend.workspace.WorkspaceController.summaryDatabaseOperations() " + timeNow() + " Updating the summary times for the " + subsurfaceJobsToBeUpdated.size() + " subsurfaces that were queried");

        subsurfaceJobService.updateBulkSubsurfaceJobs(subsurfaceJobsToBeUpdated);
        
    }

   
    private class DoubtHolder {

        Doubt cause;
        List<Doubt> inheritedDoubts=new ArrayList<>();
        boolean create;
        boolean update;
        boolean delete;
    }

    private class SummaryHolder {

        Summary summary;
        boolean create;
        boolean update;
        boolean delete;
       
        
        
        //Doubt directTimeCause=null;                                             //time_fail = false if directTimeCause=null
        //Doubt overridenTimeCause=null;                                          //time_fail_override=false if overridenTimeCause=null
        List<Doubt> inheritedTimeCause=new ArrayList<>();                       //time_fail_inherited=false if inheritedTimeCause.isEmpty()                  since no more cause for inheritance for this sum
        List<Doubt> inheritedTimeOverridenCause=new ArrayList<>();              //time_override_inherited=false if inheritedTimeOverridenCause.isEmpty()     since no more overriden cause for inheritance for this sum
        //Doubt timeWarningCause=null;                                            //time_warning=false if timeWarningCause=null
        
       // Doubt directTraceCause=null;
       // Doubt overridenTraceCause=null;
        List<Doubt> inheritedTraceCause=new ArrayList<>();
        List<Doubt> inheritedTraceOverridenCause=new ArrayList<>();
       // Doubt traceWarningCause=null;
        
        
       // Doubt directQcCause=null;
       // Doubt overridenQcCause=null;
        List<Doubt> inheritedQcCause=new ArrayList<>();
        List<Doubt> inheritedQcOverridenCause=new ArrayList<>();
       // Doubt qcWarningCause=null;
        
        List<Doubt> inheritedInsightCause=new ArrayList<>();
        List<Doubt> inheritedInsightOverridenCause=new ArrayList<>();
        
        
    }
    
    private class ResultHolder{
        int result;
        String reason;
    }

    /**
     * *
     * Unused methods
    *
     */

    public void summarize2() {
        Map<String, Double> mapForVariableSetting = new HashMap<>();
        Set<String> variableSet = new HashSet<>();
        Set<Job> argumentSet = new HashSet<>();
        List<Subsurface> subsurfaceList = subsurfaceService.getSubsurfaceList();
        // dbWorkspace = workspaceService.getWorkspace(dbWorkspace.getId());
        dbWorkspace = model.getWorkspace();
        //  Map<Job,List<Subsurface>> mapForSummary=subsurfaceJobService.getSubsurfaceJobsForSummary();   //all entries where updateTime>summaryTime
        for (Subsurface subb : subsurfaceList) {
            System.out.println("fend.workspace.WorkspaceController.getSummary(): Loop for subsurface : " + subb.getSubsurface());
            // Set<Link> linkNodesContainingSub = linkService.getLinksContainingSubsurface(subb, dbWorkspace,mapForSummary);                  //all links where both the parent and the child contains sub subb
            List<Link> linkNodesContainingSub = linkService.getSummaryLinksForSubsurfaceInWorkspace(dbWorkspace, subb);

            GLink root = graphOfLinks(new HashSet<>(linkNodesContainingSub));

            for (Link l : linkNodesContainingSub) {
                Summary summary;
                //if ((summaryforCurrentJob = summaryService.getSummaryFor(subb.getSequence(), link.getParent())) == null) {            //create an entry for parent summaryforCurrentJob
                if ((summary = summaryService.getSummaryFor(subb, l.getParent())) == null) {            //create an entry for parent summaryforCurrentJob    
                    summary = new Summary();
                    summary.setSequence(subb.getSequence());
                    summary.setSubsurface(subb);
                    summary.setJob(l.getParent());
                    summary.setWorkspace(dbWorkspace);
                    summaryService.createSummary(summary);
                }
                //if ((summaryforCurrentJob = summaryService.getSummaryFor(subb.getSequence(), link.getChild())) == null) {             //create an entry for child summaryforCurrentJob 
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
     * returns a GLink node. which is the root of a graph which has the Links
     * from the argument set as its nodes.
     *
     * GLink G2 is the child of GLink G1 if G2.Link.parent is a descendant of
     * G1.Link.child 
     *
     */
    private GLink graphOfLinks(Set<Link> linkNodesContainingSub) {
        GLink root = new GLink();
        Set<GLink> glinkSet = new HashSet<>();
        for (Link l : linkNodesContainingSub) {
            GLink gl = new GLink();
            gl.setLink(l);
            glinkSet.add(gl);
        }

        for (Iterator<GLink> iterator = glinkSet.iterator(); iterator.hasNext();) {
            GLink g1 = iterator.next();
            Job g1Child = g1.getLink().getChild();

            List<Descendant> descendantsForG1Child = descendantService.getDescendantsFor(g1Child);

            for (Iterator<GLink> iterator1 = glinkSet.iterator(); iterator1.hasNext();) {
                GLink g2 = iterator1.next();
                Job g2Parent = g2.getLink().getParent();

            }

        }

        return root;
    }

    //if the link is newly created. i.e. link.creationTime > link(parent,sub).SummaryTime 
    //                                                     OR 
    //                                   link.creationTime > link(child,sub)).SummaryTime
    /**
     * Get all ancestors containing Subsurface 'sub' : Set: A. for a : A D =
     * List of Doubts for (a,sub) for( doubt : D) if(doubt.doubtstatus.state!=
     * WARNING && doubt.type!=INHERITED) //warning doubt types are not
     * inherited. Inherited doubt types are not inherited if(inherited doubt
     * does not exist for (doubt,sub) in job=link.child) then create
     * doubt.type=INH doubt.doubt=doubt; doubt.child_job=link.child create doubt
     * Add doubt to doubt.setOfInheritedDoubts(); update doubt create
     * doubtstatus
     *
     *
     *
     */
  
    private String timeNow() {
        return DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
    }

    private void moveScrollPanesToTheLeft() {
        vscrollBar = new ScrollBar();
        vscrollBar.setOrientation(Orientation.VERTICAL);
        vscrollBar.minProperty().bind(scrollpane.vminProperty());
        vscrollBar.maxProperty().bind(scrollpane.vmaxProperty());
        vscrollBar.visibleAmountProperty().bind(scrollpane.heightProperty().divide(interactivePane.heightProperty()));
        scrollpane.vvalueProperty().bindBidirectional(vscrollBar.valueProperty());
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        anchorPane2.getChildren().addAll(vscrollBar);

    }

    /* private void fixDivider() {
     splitpane.setDividerPositions(dividerWidth);
     SplitPane.Divider divider=splitpane.getDividers().get(0);
     divider.positionProperty().addListener(new ChangeListener<Number>(){
     @Override
     public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
     divider.setPosition(dividerWidth);
     }
     
     });
     }*/
    /**
     *
     * Keys for sum Map (())
      *
     */
    private class SummaryKey {

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
     * Keys for Doubt Map (summarizeInMemory())
      *
     */
    private class DoubtKey {

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

    private class HeaderKey {

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
    
      private class PheaderKey {

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
            final PheaderKey other = (PheaderKey) obj;
            if (!Objects.equals(this.subsurface, other.subsurface)) {
                return false;
            }
            if (!Objects.equals(this.job, other.job)) {
                return false;
            }
            return true;
        }

    }

    private class SubsurfaceJobKey {

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

    private class AncestorKey {

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

    private class DescendantKey {

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

    private class InheritanceKey {

        Job job;
        Subsurface subsurface;
        DoubtType causeDoubtType;

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 19 * hash + Objects.hashCode(this.job);
            hash = 19 * hash + Objects.hashCode(this.subsurface);
            hash = 19 * hash + Objects.hashCode(this.causeDoubtType);
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
            if (!Objects.equals(this.causeDoubtType, other.causeDoubtType)) {
                return false;
            }
            return true;
        }

    }

    private SummaryKey generateSummaryKey(Subsurface sub, Job job) {
        SummaryKey key = new SummaryKey();
        key.subsurface = sub;
        key.job = job;

        return key;
    }

    private DoubtKey generateDoubtKey(Subsurface sub, Job job, Dot dot, DoubtType doubtType) {
        DoubtKey key = new DoubtKey();
        key.subsurface = sub;
        key.job = job;
        key.dot = dot;
        key.doubtType = doubtType;

        return key;
    }

    private HeaderKey generateHeaderKey(Job job, Subsurface sub) {
        HeaderKey key = new HeaderKey();
        key.job = job;
        key.subsurface = sub;

        return key;
    }
    
    private PheaderKey generatePheaderKey(Job job, Subsurface sub) {
        PheaderKey key = new PheaderKey();
        key.job = job;
        key.subsurface = sub;

        return key;
    }

    private SubsurfaceJobKey generateSubsurfaceJobKey(Job job, Subsurface sub) {
        SubsurfaceJobKey key = new SubsurfaceJobKey();
        key.subsurface = sub;
        key.job = job;

        return key;
    }

    private AncestorKey generateAncestorKey(Job job, Subsurface sub) {
        AncestorKey key = new AncestorKey();
        key.job = job;
        key.subsurface = sub;

        return key;

    }

    private DescendantKey generateDescendantKey(Job job, Subsurface sub) {
        DescendantKey key = new DescendantKey();
        key.job = job;
        key.subsurface = sub;

        return key;
    }

    private InheritanceKey generateInheritanceKey(Job job, Subsurface sub, DoubtType causeDoubtType) {
        InheritanceKey key = new InheritanceKey();
        key.job = job;
        key.subsurface = sub;
        key.causeDoubtType = causeDoubtType;

        return key;

    }

    private int processorsUsed() throws Exception {
        int procsUsed = (int) (Runtime.getRuntime().availableProcessors() * percentageOfProcessorsUsed);
        if (procsUsed <= 1) {

            System.out.println("fend.workspace.WorkspaceController.processorsUsed(): Not enough resources . PR-TCount: " + procsUsed);
            throw new Exception("Not enough resources . PR-TCount: " + procsUsed);
        }

        return procsUsed;
    }

    //createGraphAndChartsButton();
    private static SVGPath createPath(String s, String fill, String hoverFill) {
        SVGPath path = new SVGPath();

        path.getStyleClass().add("svg");
        path.setContent(s);
        path.setStyle("-fill:" + fill + ";-hover-fill:" + hoverFill + ";");
        return path;
    }
    
    List<Job> roots=new ArrayList<>();
   private ChangeListener<Boolean> CLEAR_ANCESTOR_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            System.out.println("Removing all ancestors in workspace as part of rebuild "+dbWorkspace.getName());
            roots=jobService.getRootsInWorkspace(dbWorkspace);
            
            ancestorService.removeAllAncestorEntriesFor(dbWorkspace);
            descendantService.removeAllDescendantEntriesFor(dbWorkspace);
            
        }
    };
   
   private ChangeListener<Boolean> CLEAR_DESCENDANT_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            System.out.println("Removing all ancestors in workspace as part of rebuild "+dbWorkspace.getName());
            descendantService.removeAllDescendantEntriesFor(dbWorkspace);
        }
    };

   private Map<Job,Set<Ancestor>> ancestorLUM=new HashMap<>();
   private Map<Job,Set<Descendant>> descendantLUM=new HashMap<>();
   
   private ChangeListener<Boolean> REBUILD_GRAPH_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
           
            List<Job> jobsinWorkspace=jobService.listJobs(dbWorkspace);
            ancestorLUM.clear();
            descendantLUM.clear();
            adLum.clear();
            for(Job j:jobsinWorkspace){
                AncestorDescendantHolder adh=new AncestorDescendantHolder();
                adh.job=j;
                adLum.put(j, adh);
            }
            for(Job root:roots){
                if(!jobsinWorkspace.contains(root)){     //if the root was the one marked to be deleted
                   continue;
                }
                List<Link> links=linkService.getParentLinksFor(root); 
                for(Link l:links){
                    System.out.println("checking link "+l.getId()+" "+l.getParent().getNameJobStep()+" --> "+l.getChild().getNameJobStep());
                    rebuildGraph(l);
                }
                
                
                
            }
            
           
            
            for (Map.Entry<Job, AncestorDescendantHolder> entry : adLum.entrySet()) {
                Job job = entry.getKey();
                AncestorDescendantHolder value = entry.getValue();
                Set<Job> ac=value.ancestors;
                System.out.print(""+job.getNameJobStep()+" anc = {");
                for(Job a:ac){
                    Ancestor anc=new Ancestor();
                    anc.setJob(job);
                    anc.setAncestor(a);
                    //ancestorsToBeCommited.add(anc);
                    ancestorService.addAncestor(anc);
                    System.out.print(""+a.getNameJobStep()+",");
                }
                System.out.println("}");
                
                Set<Job> dc=value.descendants;
                System.out.print(""+job.getNameJobStep()+" des = {");
                for(Job d:dc){
                    Descendant desc=new Descendant();
                    desc.setJob(job);
                    desc.setDescendant(d);
                    //descendantsToBeCommited.add(desc);
                    descendantService.addDescendant(desc);
                    System.out.print(""+d.getNameJobStep()+",");
                }
                System.out.println("}");
                
                
            }
           
            
        }
    };
   
   
   
   private void rebuildGraph(Link l){
       
       System.out.println("fend.workspace.WorkspaceController.rebuildGraph(): checking "+l.getParent().getNameJobStep()+" --> "+l.getChild().getNameJobStep());
       
       Job parent=l.getParent();
       Job child=l.getChild();
       
       
       
       Set<Job> pa=adLum.get(parent).ancestors;
       
       for(Job a: pa){
           adLum.get(child).ancestors.add(a);
       }
      
       
       adLum.get(child).ancestors.add(parent);
       
       
       System.out.println(" "+child.getNameJobStep()+" anc = { ");
       for(Job j:adLum.get(child).ancestors){
           System.out.println(""+j.getNameJobStep()+",");
       }
       System.out.println("}");
       
       List<Link> listOfLinksWhereChildIsParent=linkService.getParentLinksFor(child);
       if(listOfLinksWhereChildIsParent.isEmpty()){
          // adLum.get(parent).descendants.add(child);
           System.out.println("fend.workspace.WorkspaceController.rebuildGraph(): "+child.getNameJobStep()+" has no more children..");
       }
       for(Link link:listOfLinksWhereChildIsParent){
           System.out.println("fend.workspace.WorkspaceController.rebuildGraph(): going into Link "+link.getParent().getNameJobStep()+" --> "+link.getChild().getNameJobStep());
           rebuildGraph(link);
       }
       
       System.out.println("fend.workspace.WorkspaceController.rebuildGraph(): back in Link checking "+l.getParent().getNameJobStep()+" --> "+l.getChild().getNameJobStep());
       
       Set<Job> cd=adLum.get(child).descendants;
       
       for(Job d:cd){
           adLum.get(parent).descendants.add(d);
       }
       
       adLum.get(parent).descendants.add(child);
       
       System.out.println(" "+parent.getNameJobStep()+" desc = { ");
       for(Job j:adLum.get(parent).descendants){
           System.out.println(""+j.getNameJobStep()+",");
       }
       System.out.println("}");
       
       System.out.println("fend.workspace.WorkspaceController.rebuildGraph(): Exiting link "+l.getParent().getNameJobStep()+" --> "+l.getChild().getNameJobStep());
       
   }
   
   private void rebuild(Link l){
      System.out.println("checking link "+l.getId()+" "+l.getParent().getNameJobStep()+" --> "+l.getChild().getNameJobStep());
      Set<Ancestor> ancestorsInParent;  
      if(!ancestorLUM.containsKey(l.getParent())){
          ancestorLUM.put(l.getParent(), new HashSet<>());
          ancestorsInParent=ancestorLUM.get(l.getParent());
      }else{
          ancestorsInParent=ancestorLUM.get(l.getParent());
      }
              
      // Add all the parents ancestors to the child
      for(Ancestor a: ancestorsInParent){
          Ancestor childAncestor=new Ancestor();
          childAncestor.setJob(l.getChild());
          childAncestor.setAncestor(a.getAncestor());
          //ancestorService.addAncestor(childAncestor);
          if(!ancestorLUM.containsKey(l.getChild())){
              ancestorLUM.put(l.getChild(),new HashSet<>());
              ancestorLUM.get(l.getChild()).add(childAncestor);
          }else{
              ancestorLUM.get(l.getChild()).add(childAncestor);
          }
      }
      //Add the parent as an ancestor
        Ancestor childAncestor=new Ancestor();
        childAncestor.setJob(l.getChild());
        childAncestor.setAncestor(l.getParent());
        if(!ancestorLUM.containsKey(l.getChild())){
              ancestorLUM.put(l.getChild(),new HashSet<>());
              ancestorLUM.get(l.getChild()).add(childAncestor);
          }else{
              ancestorLUM.get(l.getChild()).add(childAncestor);
          }
      
        /*  if(l.getChild().isLeaf()){
        Descendant parentDescendant=new Descendant();
        parentDescendant.setJob(l.getParent());
        parentDescendant.setDescendant(l.getChild());
        if(!descendantLUM.containsKey(l.getParent())){
        descendantLUM.put(l.getParent(), new HashSet<>());
        descendantLUM.get(l.getParent()).add(parentDescendant);
        }else{
        descendantLUM.get(l.getParent()).add(parentDescendant);
        }
        
        return;
        
        }*/
      
      List<Link> linksWhereChildIsParent=linkService.getParentLinksFor(l.getChild());
      if(linksWhereChildIsParent.isEmpty()){
            Descendant parentDescendant=new Descendant();
                parentDescendant.setJob(l.getParent());
                parentDescendant.setDescendant(l.getChild());
          if(!descendantLUM.containsKey(l.getParent())){
              descendantLUM.put(l.getParent(), new HashSet<>());
              descendantLUM.get(l.getParent()).add(parentDescendant);
          }else{
              descendantLUM.get(l.getParent()).add(parentDescendant);
          }
          System.out.println(" "+l.getChild().getNameJobStep()+" has no more children. ");
          System.out.println("fend.workspace.WorkspaceController.rebuild(): returning");
         // return;
      }
      for(Link link:linksWhereChildIsParent){
          System.out.println(" rebuild:  checking link "+link.getId()+" "+link.getParent().getNameJobStep()+" --> "+link.getChild().getNameJobStep());
          rebuild(link);
      }
      
      //Add all child descendants to the parent
      Set<Descendant> descendantsInChild;
      if(!descendantLUM.containsKey(l.getChild())){
          descendantLUM.put(l.getChild(),new HashSet<>());
          descendantsInChild=descendantLUM.get(l.getChild());
      }else{
          descendantsInChild=descendantLUM.get(l.getChild());
      }
      
      for(Descendant d:descendantsInChild){
          Descendant parentDescendant=new Descendant();
          parentDescendant.setJob(l.getParent());
          parentDescendant.setDescendant(d.getDescendant());
          
          if(!descendantLUM.containsKey(l.getParent())){
              descendantLUM.put(l.getParent(), new HashSet<>());
              descendantLUM.get(l.getParent()).add(parentDescendant);
          }else{
              descendantLUM.get(l.getParent()).add(parentDescendant);
          }
          
      }
      //Add the child as the descendant of the parent
        
          Descendant parentDescendant=new Descendant();
          parentDescendant.setJob(l.getParent());
          parentDescendant.setDescendant(l.getChild());
          
          if(!descendantLUM.containsKey(l.getParent())){
              descendantLUM.put(l.getParent(), new HashSet<>());
              descendantLUM.get(l.getParent()).add(parentDescendant);
          }else{
              descendantLUM.get(l.getParent()).add(parentDescendant);
          }
      
   }
   
   
   private class AncestorDescendantHolder{
       Job job;
       Set<Job> ancestors=new HashSet<>();
       Set<Job> descendants=new HashSet<>();
   }
   
   private Map<Job,AncestorDescendantHolder> adLum=new HashMap<>();
   
   private ChangeListener<Boolean> RELOAD_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println(".changed(): Auto-RELOAD of workspace implementation pending. Manually reload the workspace "+dbWorkspace.getName());
        }
    };
}
