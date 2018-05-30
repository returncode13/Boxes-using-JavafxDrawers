/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.fullLineTable;

import db.model.Fheader;
import db.model.Header;
import db.model.Job;
import db.model.Log;
import db.model.Subsurface;
import db.model.Volume;
import db.model.Workflow;
import db.services.FheaderService;
import db.services.FheaderServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.LogService;
import db.services.LogServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import db.services.WorkflowService;
import db.services.WorkflowServiceImpl;
import fend.job.job5.JobType5Model;
import fend.job.table.log.HeaderLogModel;
import fend.job.table.log.HeaderLogView;
import fend.job.table.log.VersionLogsModel;
import fend.job.table.workflow.WorkFlowDifferenceModel;
import fend.job.table.workflow.WorkFlowDifferenceView;
import fend.volume.volume0.Volume0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import middleware.sequences.fullHeaders.FullSequenceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class FullHeaderLineTableController extends Stage{
    private final String colorisNotChosen="#f49542";
    private FullHeaderLineTableModel model;
    private FullHeaderLineTableView view;
    private SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();
    private JobService jobService=new JobServiceImpl();
    private FheaderService headerService=new FheaderServiceImpl(); 
    private Executor exec;  
    private WorkflowService workflowService=new WorkflowServiceImpl();
    private LogService logservice=new LogServiceImpl();
    private boolean multipleSubsPresent=false;
     @FXML
    private TreeTableView<FullSequenceHeaders> treetableView;

     
     /**
      * generated using 
      * dugio read file=160_P_Overlapping_shot_attenuation.dugio line=696501061101_cable5_gun1 query=time:0 full_headers | durange raw=true output=bash | awk '{split($0,a,"=");print "TreeTableColumn<FullSequenceHeaders,Long> "a[1]" = new TreeTableColumn<>(\""a[1]"\");"}'
      **/
TreeTableColumn<FullSequenceHeaders,Long>  sequenceNumber= new TreeTableColumn<>("SEQUENCE");
TreeTableColumn<FullSequenceHeaders,String> subsurfaceName= new TreeTableColumn<>("SAILLINE");     
TreeTableColumn<FullSequenceHeaders,Long> totalTraces = new TreeTableColumn<>("totalTraces");
TreeTableColumn<FullSequenceHeaders,Long> minTracr = new TreeTableColumn<>("minTracr");
TreeTableColumn<FullSequenceHeaders,Long> maxTracr = new TreeTableColumn<>("maxTracr");
TreeTableColumn<FullSequenceHeaders,Long> incTracr = new TreeTableColumn<>("incTracr");
TreeTableColumn<FullSequenceHeaders,Long> firstTracr = new TreeTableColumn<>("firstTracr");
TreeTableColumn<FullSequenceHeaders,Long> lastTracr = new TreeTableColumn<>("lastTracr");
TreeTableColumn<FullSequenceHeaders,Long> minFldr = new TreeTableColumn<>("minFldr");
TreeTableColumn<FullSequenceHeaders,Long> maxFldr = new TreeTableColumn<>("maxFldr");
TreeTableColumn<FullSequenceHeaders,Long> incFldr = new TreeTableColumn<>("incFldr");
TreeTableColumn<FullSequenceHeaders,Long> firstFldr = new TreeTableColumn<>("firstFldr");
TreeTableColumn<FullSequenceHeaders,Long> lastFldr = new TreeTableColumn<>("lastFldr");
TreeTableColumn<FullSequenceHeaders,Long> minTracf = new TreeTableColumn<>("minTracf");
TreeTableColumn<FullSequenceHeaders,Long> maxTracf = new TreeTableColumn<>("maxTracf");
TreeTableColumn<FullSequenceHeaders,Long> incTracf = new TreeTableColumn<>("incTracf");
TreeTableColumn<FullSequenceHeaders,Long> firstTracf = new TreeTableColumn<>("firstTracf");
TreeTableColumn<FullSequenceHeaders,Long> lastTracf = new TreeTableColumn<>("lastTracf");
TreeTableColumn<FullSequenceHeaders,Long> minEp = new TreeTableColumn<>("minEp");
TreeTableColumn<FullSequenceHeaders,Long> maxEp = new TreeTableColumn<>("maxEp");
TreeTableColumn<FullSequenceHeaders,Long> incEp = new TreeTableColumn<>("incEp");
TreeTableColumn<FullSequenceHeaders,Long> firstEp = new TreeTableColumn<>("firstEp");
TreeTableColumn<FullSequenceHeaders,Long> lastEp = new TreeTableColumn<>("lastEp");
TreeTableColumn<FullSequenceHeaders,Long> minCdp = new TreeTableColumn<>("minCdp");
TreeTableColumn<FullSequenceHeaders,Long> maxCdp = new TreeTableColumn<>("maxCdp");
TreeTableColumn<FullSequenceHeaders,Long> incCdp = new TreeTableColumn<>("incCdp");
TreeTableColumn<FullSequenceHeaders,Long> firstCdp = new TreeTableColumn<>("firstCdp");
TreeTableColumn<FullSequenceHeaders,Long> lastCdp = new TreeTableColumn<>("lastCdp");
TreeTableColumn<FullSequenceHeaders,Long> minCdpt = new TreeTableColumn<>("minCdpt");
TreeTableColumn<FullSequenceHeaders,Long> maxCdpt = new TreeTableColumn<>("maxCdpt");
TreeTableColumn<FullSequenceHeaders,Long> incCdpt = new TreeTableColumn<>("incCdpt");
TreeTableColumn<FullSequenceHeaders,Long> firstCdpt = new TreeTableColumn<>("firstCdpt");
TreeTableColumn<FullSequenceHeaders,Long> lastCdpt = new TreeTableColumn<>("lastCdpt");
TreeTableColumn<FullSequenceHeaders,Long> minTrid = new TreeTableColumn<>("minTrid");
TreeTableColumn<FullSequenceHeaders,Long> maxTrid = new TreeTableColumn<>("maxTrid");
TreeTableColumn<FullSequenceHeaders,Long> incTrid = new TreeTableColumn<>("incTrid");
TreeTableColumn<FullSequenceHeaders,Long> firstTrid = new TreeTableColumn<>("firstTrid");
TreeTableColumn<FullSequenceHeaders,Long> lastTrid = new TreeTableColumn<>("lastTrid");
TreeTableColumn<FullSequenceHeaders,Long> minDuse = new TreeTableColumn<>("minDuse");
TreeTableColumn<FullSequenceHeaders,Long> maxDuse = new TreeTableColumn<>("maxDuse");
TreeTableColumn<FullSequenceHeaders,Long> incDuse = new TreeTableColumn<>("incDuse");
TreeTableColumn<FullSequenceHeaders,Long> firstDuse = new TreeTableColumn<>("firstDuse");
TreeTableColumn<FullSequenceHeaders,Long> lastDuse = new TreeTableColumn<>("lastDuse");
TreeTableColumn<FullSequenceHeaders,Long> duse = new TreeTableColumn<>("duse");
TreeTableColumn<FullSequenceHeaders,Long> minOffset = new TreeTableColumn<>("minOffset");
TreeTableColumn<FullSequenceHeaders,Long> maxOffset = new TreeTableColumn<>("maxOffset");
TreeTableColumn<FullSequenceHeaders,Long> incOffset = new TreeTableColumn<>("incOffset");
TreeTableColumn<FullSequenceHeaders,Long> firstOffset = new TreeTableColumn<>("firstOffset");
TreeTableColumn<FullSequenceHeaders,Long> lastOffset = new TreeTableColumn<>("lastOffset");
TreeTableColumn<FullSequenceHeaders,Long> minGelev = new TreeTableColumn<>("minGelev");
TreeTableColumn<FullSequenceHeaders,Long> maxGelev = new TreeTableColumn<>("maxGelev");
TreeTableColumn<FullSequenceHeaders,Long> incGelev = new TreeTableColumn<>("incGelev");
TreeTableColumn<FullSequenceHeaders,Long> firstGelev = new TreeTableColumn<>("firstGelev");
TreeTableColumn<FullSequenceHeaders,Long> lastGelev = new TreeTableColumn<>("lastGelev");
TreeTableColumn<FullSequenceHeaders,Long> minSelev = new TreeTableColumn<>("minSelev");
TreeTableColumn<FullSequenceHeaders,Long> maxSelev = new TreeTableColumn<>("maxSelev");
TreeTableColumn<FullSequenceHeaders,Long> incSelev = new TreeTableColumn<>("incSelev");
TreeTableColumn<FullSequenceHeaders,Long> firstSelev = new TreeTableColumn<>("firstSelev");
TreeTableColumn<FullSequenceHeaders,Long> lastSelev = new TreeTableColumn<>("lastSelev");
TreeTableColumn<FullSequenceHeaders,Long> minSdepth = new TreeTableColumn<>("minSdepth");
TreeTableColumn<FullSequenceHeaders,Long> maxSdepth = new TreeTableColumn<>("maxSdepth");
TreeTableColumn<FullSequenceHeaders,Long> incSdepth = new TreeTableColumn<>("incSdepth");
TreeTableColumn<FullSequenceHeaders,Long> firstSdepth = new TreeTableColumn<>("firstSdepth");
TreeTableColumn<FullSequenceHeaders,Long> lastSdepth = new TreeTableColumn<>("lastSdepth");
TreeTableColumn<FullSequenceHeaders,Long> minSwdep = new TreeTableColumn<>("minSwdep");
TreeTableColumn<FullSequenceHeaders,Long> maxSwdep = new TreeTableColumn<>("maxSwdep");
TreeTableColumn<FullSequenceHeaders,Long> incSwdep = new TreeTableColumn<>("incSwdep");
TreeTableColumn<FullSequenceHeaders,Long> firstSwdep = new TreeTableColumn<>("firstSwdep");
TreeTableColumn<FullSequenceHeaders,Long> lastSwdep = new TreeTableColumn<>("lastSwdep");
TreeTableColumn<FullSequenceHeaders,Long> minGwdep = new TreeTableColumn<>("minGwdep");
TreeTableColumn<FullSequenceHeaders,Long> maxGwdep = new TreeTableColumn<>("maxGwdep");
TreeTableColumn<FullSequenceHeaders,Long> incGwdep = new TreeTableColumn<>("incGwdep");
TreeTableColumn<FullSequenceHeaders,Long> firstGwdep = new TreeTableColumn<>("firstGwdep");
TreeTableColumn<FullSequenceHeaders,Long> lastGwdep = new TreeTableColumn<>("lastGwdep");
TreeTableColumn<FullSequenceHeaders,Long> minScalel = new TreeTableColumn<>("minScalel");
TreeTableColumn<FullSequenceHeaders,Long> maxScalel = new TreeTableColumn<>("maxScalel");
TreeTableColumn<FullSequenceHeaders,Long> incScalel = new TreeTableColumn<>("incScalel");
TreeTableColumn<FullSequenceHeaders,Long> firstScalel = new TreeTableColumn<>("firstScalel");
TreeTableColumn<FullSequenceHeaders,Long> lastScalel = new TreeTableColumn<>("lastScalel");
TreeTableColumn<FullSequenceHeaders,Long> scalel = new TreeTableColumn<>("scalel");
TreeTableColumn<FullSequenceHeaders,Long> minScalco = new TreeTableColumn<>("minScalco");
TreeTableColumn<FullSequenceHeaders,Long> maxScalco = new TreeTableColumn<>("maxScalco");
TreeTableColumn<FullSequenceHeaders,Long> incScalco = new TreeTableColumn<>("incScalco");
TreeTableColumn<FullSequenceHeaders,Long> firstScalco = new TreeTableColumn<>("firstScalco");
TreeTableColumn<FullSequenceHeaders,Long> lastScalco = new TreeTableColumn<>("lastScalco");
TreeTableColumn<FullSequenceHeaders,Long> scalco = new TreeTableColumn<>("scalco");
TreeTableColumn<FullSequenceHeaders,Long> minSx = new TreeTableColumn<>("minSx");
TreeTableColumn<FullSequenceHeaders,Long> maxSx = new TreeTableColumn<>("maxSx");
TreeTableColumn<FullSequenceHeaders,Long> incSx = new TreeTableColumn<>("incSx");
TreeTableColumn<FullSequenceHeaders,Long> firstSx = new TreeTableColumn<>("firstSx");
TreeTableColumn<FullSequenceHeaders,Long> lastSx = new TreeTableColumn<>("lastSx");
TreeTableColumn<FullSequenceHeaders,Long> minSy = new TreeTableColumn<>("minSy");
TreeTableColumn<FullSequenceHeaders,Long> maxSy = new TreeTableColumn<>("maxSy");
TreeTableColumn<FullSequenceHeaders,Long> incSy = new TreeTableColumn<>("incSy");
TreeTableColumn<FullSequenceHeaders,Long> firstSy = new TreeTableColumn<>("firstSy");
TreeTableColumn<FullSequenceHeaders,Long> lastSy = new TreeTableColumn<>("lastSy");
TreeTableColumn<FullSequenceHeaders,Long> minGx = new TreeTableColumn<>("minGx");
TreeTableColumn<FullSequenceHeaders,Long> maxGx = new TreeTableColumn<>("maxGx");
TreeTableColumn<FullSequenceHeaders,Long> incGx = new TreeTableColumn<>("incGx");
TreeTableColumn<FullSequenceHeaders,Long> firstGx = new TreeTableColumn<>("firstGx");
TreeTableColumn<FullSequenceHeaders,Long> lastGx = new TreeTableColumn<>("lastGx");
TreeTableColumn<FullSequenceHeaders,Long> minGy = new TreeTableColumn<>("minGy");
TreeTableColumn<FullSequenceHeaders,Long> maxGy = new TreeTableColumn<>("maxGy");
TreeTableColumn<FullSequenceHeaders,Long> incGy = new TreeTableColumn<>("incGy");
TreeTableColumn<FullSequenceHeaders,Long> firstGy = new TreeTableColumn<>("firstGy");
TreeTableColumn<FullSequenceHeaders,Long> lastGy = new TreeTableColumn<>("lastGy");
TreeTableColumn<FullSequenceHeaders,Long> minWevel = new TreeTableColumn<>("minWevel");
TreeTableColumn<FullSequenceHeaders,Long> maxWevel = new TreeTableColumn<>("maxWevel");
TreeTableColumn<FullSequenceHeaders,Long> incWevel = new TreeTableColumn<>("incWevel");
TreeTableColumn<FullSequenceHeaders,Long> firstWevel = new TreeTableColumn<>("firstWevel");
TreeTableColumn<FullSequenceHeaders,Long> lastWevel = new TreeTableColumn<>("lastWevel");
TreeTableColumn<FullSequenceHeaders,Long> wevel = new TreeTableColumn<>("wevel");
TreeTableColumn<FullSequenceHeaders,Long> minSwevel = new TreeTableColumn<>("minSwevel");
TreeTableColumn<FullSequenceHeaders,Long> maxSwevel = new TreeTableColumn<>("maxSwevel");
TreeTableColumn<FullSequenceHeaders,Long> incSwevel = new TreeTableColumn<>("incSwevel");
TreeTableColumn<FullSequenceHeaders,Long> firstSwevel = new TreeTableColumn<>("firstSwevel");
TreeTableColumn<FullSequenceHeaders,Long> lastSwevel = new TreeTableColumn<>("lastSwevel");
TreeTableColumn<FullSequenceHeaders,Long> swevel = new TreeTableColumn<>("swevel");
TreeTableColumn<FullSequenceHeaders,Long> minSut = new TreeTableColumn<>("minSut");
TreeTableColumn<FullSequenceHeaders,Long> maxSut = new TreeTableColumn<>("maxSut");
TreeTableColumn<FullSequenceHeaders,Long> incSut = new TreeTableColumn<>("incSut");
TreeTableColumn<FullSequenceHeaders,Long> firstSut = new TreeTableColumn<>("firstSut");
TreeTableColumn<FullSequenceHeaders,Long> lastSut = new TreeTableColumn<>("lastSut");
TreeTableColumn<FullSequenceHeaders,Long> minGut = new TreeTableColumn<>("minGut");
TreeTableColumn<FullSequenceHeaders,Long> maxGut = new TreeTableColumn<>("maxGut");
TreeTableColumn<FullSequenceHeaders,Long> incGut = new TreeTableColumn<>("incGut");
TreeTableColumn<FullSequenceHeaders,Long> firstGut = new TreeTableColumn<>("firstGut");
TreeTableColumn<FullSequenceHeaders,Long> lastGut = new TreeTableColumn<>("lastGut");
TreeTableColumn<FullSequenceHeaders,Long> minSstat = new TreeTableColumn<>("minSstat");
TreeTableColumn<FullSequenceHeaders,Long> maxSstat = new TreeTableColumn<>("maxSstat");
TreeTableColumn<FullSequenceHeaders,Long> incSstat = new TreeTableColumn<>("incSstat");
TreeTableColumn<FullSequenceHeaders,Long> firstSstat = new TreeTableColumn<>("firstSstat");
TreeTableColumn<FullSequenceHeaders,Long> lastSstat = new TreeTableColumn<>("lastSstat");
TreeTableColumn<FullSequenceHeaders,Long> minGstat = new TreeTableColumn<>("minGstat");
TreeTableColumn<FullSequenceHeaders,Long> maxGstat = new TreeTableColumn<>("maxGstat");
TreeTableColumn<FullSequenceHeaders,Long> incGstat = new TreeTableColumn<>("incGstat");
TreeTableColumn<FullSequenceHeaders,Long> firstGstat = new TreeTableColumn<>("firstGstat");
TreeTableColumn<FullSequenceHeaders,Long> lastGstat = new TreeTableColumn<>("lastGstat");
TreeTableColumn<FullSequenceHeaders,Long> minLagb = new TreeTableColumn<>("minLagb");
TreeTableColumn<FullSequenceHeaders,Long> maxLagb = new TreeTableColumn<>("maxLagb");
TreeTableColumn<FullSequenceHeaders,Long> incLagb = new TreeTableColumn<>("incLagb");
TreeTableColumn<FullSequenceHeaders,Long> firstLagb = new TreeTableColumn<>("firstLagb");
TreeTableColumn<FullSequenceHeaders,Long> lastLagb = new TreeTableColumn<>("lastLagb");
TreeTableColumn<FullSequenceHeaders,Long> minNs = new TreeTableColumn<>("minNs");
TreeTableColumn<FullSequenceHeaders,Long> maxNs = new TreeTableColumn<>("maxNs");
TreeTableColumn<FullSequenceHeaders,Long> incNs = new TreeTableColumn<>("incNs");
TreeTableColumn<FullSequenceHeaders,Long> firstNs = new TreeTableColumn<>("firstNs");
TreeTableColumn<FullSequenceHeaders,Long> lastNs = new TreeTableColumn<>("lastNs");
TreeTableColumn<FullSequenceHeaders,Long> ns = new TreeTableColumn<>("ns");
TreeTableColumn<FullSequenceHeaders,Long> minDt = new TreeTableColumn<>("minDt");
TreeTableColumn<FullSequenceHeaders,Long> maxDt = new TreeTableColumn<>("maxDt");
TreeTableColumn<FullSequenceHeaders,Long> incDt = new TreeTableColumn<>("incDt");
TreeTableColumn<FullSequenceHeaders,Long> firstDt = new TreeTableColumn<>("firstDt");
TreeTableColumn<FullSequenceHeaders,Long> lastDt = new TreeTableColumn<>("lastDt");
TreeTableColumn<FullSequenceHeaders,Long> dt = new TreeTableColumn<>("dt");
TreeTableColumn<FullSequenceHeaders,Long> minSfs = new TreeTableColumn<>("minSfs");
TreeTableColumn<FullSequenceHeaders,Long> maxSfs = new TreeTableColumn<>("maxSfs");
TreeTableColumn<FullSequenceHeaders,Long> incSfs = new TreeTableColumn<>("incSfs");
TreeTableColumn<FullSequenceHeaders,Long> firstSfs = new TreeTableColumn<>("firstSfs");
TreeTableColumn<FullSequenceHeaders,Long> lastSfs = new TreeTableColumn<>("lastSfs");
TreeTableColumn<FullSequenceHeaders,Long> minSfe = new TreeTableColumn<>("minSfe");
TreeTableColumn<FullSequenceHeaders,Long> maxSfe = new TreeTableColumn<>("maxSfe");
TreeTableColumn<FullSequenceHeaders,Long> incSfe = new TreeTableColumn<>("incSfe");
TreeTableColumn<FullSequenceHeaders,Long> firstSfe = new TreeTableColumn<>("firstSfe");
TreeTableColumn<FullSequenceHeaders,Long> lastSfe = new TreeTableColumn<>("lastSfe");
TreeTableColumn<FullSequenceHeaders,Long> minSlen = new TreeTableColumn<>("minSlen");
TreeTableColumn<FullSequenceHeaders,Long> maxSlen = new TreeTableColumn<>("maxSlen");
TreeTableColumn<FullSequenceHeaders,Long> incSlen = new TreeTableColumn<>("incSlen");
TreeTableColumn<FullSequenceHeaders,Long> firstSlen = new TreeTableColumn<>("firstSlen");
TreeTableColumn<FullSequenceHeaders,Long> lastSlen = new TreeTableColumn<>("lastSlen");
TreeTableColumn<FullSequenceHeaders,Long> slen = new TreeTableColumn<>("slen");
TreeTableColumn<FullSequenceHeaders,Long> minStyp = new TreeTableColumn<>("minStyp");
TreeTableColumn<FullSequenceHeaders,Long> maxStyp = new TreeTableColumn<>("maxStyp");
TreeTableColumn<FullSequenceHeaders,Long> incStyp = new TreeTableColumn<>("incStyp");
TreeTableColumn<FullSequenceHeaders,Long> firstStyp = new TreeTableColumn<>("firstStyp");
TreeTableColumn<FullSequenceHeaders,Long> lastStyp = new TreeTableColumn<>("lastStyp");
TreeTableColumn<FullSequenceHeaders,Long> minStas = new TreeTableColumn<>("minStas");
TreeTableColumn<FullSequenceHeaders,Long> maxStas = new TreeTableColumn<>("maxStas");
TreeTableColumn<FullSequenceHeaders,Long> incStas = new TreeTableColumn<>("incStas");
TreeTableColumn<FullSequenceHeaders,Long> firstStas = new TreeTableColumn<>("firstStas");
TreeTableColumn<FullSequenceHeaders,Long> lastStas = new TreeTableColumn<>("lastStas");
TreeTableColumn<FullSequenceHeaders,Long> minStae = new TreeTableColumn<>("minStae");
TreeTableColumn<FullSequenceHeaders,Long> maxStae = new TreeTableColumn<>("maxStae");
TreeTableColumn<FullSequenceHeaders,Long> incStae = new TreeTableColumn<>("incStae");
TreeTableColumn<FullSequenceHeaders,Long> firstStae = new TreeTableColumn<>("firstStae");
TreeTableColumn<FullSequenceHeaders,Long> lastStae = new TreeTableColumn<>("lastStae");
TreeTableColumn<FullSequenceHeaders,Long> stae = new TreeTableColumn<>("stae");
TreeTableColumn<FullSequenceHeaders,Long> minAfilf = new TreeTableColumn<>("minAfilf");
TreeTableColumn<FullSequenceHeaders,Long> maxAfilf = new TreeTableColumn<>("maxAfilf");
TreeTableColumn<FullSequenceHeaders,Long> incAfilf = new TreeTableColumn<>("incAfilf");
TreeTableColumn<FullSequenceHeaders,Long> firstAfilf = new TreeTableColumn<>("firstAfilf");
TreeTableColumn<FullSequenceHeaders,Long> lastAfilf = new TreeTableColumn<>("lastAfilf");
TreeTableColumn<FullSequenceHeaders,Long> afilf = new TreeTableColumn<>("afilf");
TreeTableColumn<FullSequenceHeaders,Long> minAfils = new TreeTableColumn<>("minAfils");
TreeTableColumn<FullSequenceHeaders,Long> maxAfils = new TreeTableColumn<>("maxAfils");
TreeTableColumn<FullSequenceHeaders,Long> incAfils = new TreeTableColumn<>("incAfils");
TreeTableColumn<FullSequenceHeaders,Long> firstAfils = new TreeTableColumn<>("firstAfils");
TreeTableColumn<FullSequenceHeaders,Long> lastAfils = new TreeTableColumn<>("lastAfils");
TreeTableColumn<FullSequenceHeaders,Long> afils = new TreeTableColumn<>("afils");
TreeTableColumn<FullSequenceHeaders,Long> minLcf = new TreeTableColumn<>("minLcf");
TreeTableColumn<FullSequenceHeaders,Long> maxLcf = new TreeTableColumn<>("maxLcf");
TreeTableColumn<FullSequenceHeaders,Long> incLcf = new TreeTableColumn<>("incLcf");
TreeTableColumn<FullSequenceHeaders,Long> firstLcf = new TreeTableColumn<>("firstLcf");
TreeTableColumn<FullSequenceHeaders,Long> lastLcf = new TreeTableColumn<>("lastLcf");
TreeTableColumn<FullSequenceHeaders,Long> lcf = new TreeTableColumn<>("lcf");
TreeTableColumn<FullSequenceHeaders,Long> minLcs = new TreeTableColumn<>("minLcs");
TreeTableColumn<FullSequenceHeaders,Long> maxLcs = new TreeTableColumn<>("maxLcs");
TreeTableColumn<FullSequenceHeaders,Long> incLcs = new TreeTableColumn<>("incLcs");
TreeTableColumn<FullSequenceHeaders,Long> firstLcs = new TreeTableColumn<>("firstLcs");
TreeTableColumn<FullSequenceHeaders,Long> lastLcs = new TreeTableColumn<>("lastLcs");
TreeTableColumn<FullSequenceHeaders,Long> lcs = new TreeTableColumn<>("lcs");
TreeTableColumn<FullSequenceHeaders,Long> minHcs = new TreeTableColumn<>("minHcs");
TreeTableColumn<FullSequenceHeaders,Long> maxHcs = new TreeTableColumn<>("maxHcs");
TreeTableColumn<FullSequenceHeaders,Long> incHcs = new TreeTableColumn<>("incHcs");
TreeTableColumn<FullSequenceHeaders,Long> firstHcs = new TreeTableColumn<>("firstHcs");
TreeTableColumn<FullSequenceHeaders,Long> lastHcs = new TreeTableColumn<>("lastHcs");
TreeTableColumn<FullSequenceHeaders,Long> minYear = new TreeTableColumn<>("minYear");
TreeTableColumn<FullSequenceHeaders,Long> maxYear = new TreeTableColumn<>("maxYear");
TreeTableColumn<FullSequenceHeaders,Long> incYear = new TreeTableColumn<>("incYear");
TreeTableColumn<FullSequenceHeaders,Long> firstYear = new TreeTableColumn<>("firstYear");
TreeTableColumn<FullSequenceHeaders,Long> lastYear = new TreeTableColumn<>("lastYear");
TreeTableColumn<FullSequenceHeaders,Long> year = new TreeTableColumn<>("year");
TreeTableColumn<FullSequenceHeaders,Long> minDay = new TreeTableColumn<>("minDay");
TreeTableColumn<FullSequenceHeaders,Long> maxDay = new TreeTableColumn<>("maxDay");
TreeTableColumn<FullSequenceHeaders,Long> incDay = new TreeTableColumn<>("incDay");
TreeTableColumn<FullSequenceHeaders,Long> firstDay = new TreeTableColumn<>("firstDay");
TreeTableColumn<FullSequenceHeaders,Long> lastDay = new TreeTableColumn<>("lastDay");
TreeTableColumn<FullSequenceHeaders,Long> day = new TreeTableColumn<>("day");
TreeTableColumn<FullSequenceHeaders,Long> minHour = new TreeTableColumn<>("minHour");
TreeTableColumn<FullSequenceHeaders,Long> maxHour = new TreeTableColumn<>("maxHour");
TreeTableColumn<FullSequenceHeaders,Long> incHour = new TreeTableColumn<>("incHour");
TreeTableColumn<FullSequenceHeaders,Long> firstHour = new TreeTableColumn<>("firstHour");
TreeTableColumn<FullSequenceHeaders,Long> lastHour = new TreeTableColumn<>("lastHour");
TreeTableColumn<FullSequenceHeaders,Long> minMinute = new TreeTableColumn<>("minMinute");
TreeTableColumn<FullSequenceHeaders,Long> maxMinute = new TreeTableColumn<>("maxMinute");
TreeTableColumn<FullSequenceHeaders,Long> incMinute = new TreeTableColumn<>("incMinute");
TreeTableColumn<FullSequenceHeaders,Long> firstMinute = new TreeTableColumn<>("firstMinute");
TreeTableColumn<FullSequenceHeaders,Long> lastMinute = new TreeTableColumn<>("lastMinute");
TreeTableColumn<FullSequenceHeaders,Long> minSec = new TreeTableColumn<>("minSec");
TreeTableColumn<FullSequenceHeaders,Long> maxSec = new TreeTableColumn<>("maxSec");
TreeTableColumn<FullSequenceHeaders,Long> incSec = new TreeTableColumn<>("incSec");
TreeTableColumn<FullSequenceHeaders,Long> firstSec = new TreeTableColumn<>("firstSec");
TreeTableColumn<FullSequenceHeaders,Long> lastSec = new TreeTableColumn<>("lastSec");
TreeTableColumn<FullSequenceHeaders,Long> minGrnors = new TreeTableColumn<>("minGrnors");
TreeTableColumn<FullSequenceHeaders,Long> maxGrnors = new TreeTableColumn<>("maxGrnors");
TreeTableColumn<FullSequenceHeaders,Long> incGrnors = new TreeTableColumn<>("incGrnors");
TreeTableColumn<FullSequenceHeaders,Long> firstGrnors = new TreeTableColumn<>("firstGrnors");
TreeTableColumn<FullSequenceHeaders,Long> lastGrnors = new TreeTableColumn<>("lastGrnors");
TreeTableColumn<FullSequenceHeaders,Long> grnors = new TreeTableColumn<>("grnors");
TreeTableColumn<FullSequenceHeaders,Long> minGrnofr = new TreeTableColumn<>("minGrnofr");
TreeTableColumn<FullSequenceHeaders,Long> maxGrnofr = new TreeTableColumn<>("maxGrnofr");
TreeTableColumn<FullSequenceHeaders,Long> incGrnofr = new TreeTableColumn<>("incGrnofr");
TreeTableColumn<FullSequenceHeaders,Long> firstGrnofr = new TreeTableColumn<>("firstGrnofr");
TreeTableColumn<FullSequenceHeaders,Long> lastGrnofr = new TreeTableColumn<>("lastGrnofr");
TreeTableColumn<FullSequenceHeaders,Long> grnofr = new TreeTableColumn<>("grnofr");
TreeTableColumn<FullSequenceHeaders,Long> minGaps = new TreeTableColumn<>("minGaps");
TreeTableColumn<FullSequenceHeaders,Long> maxGaps = new TreeTableColumn<>("maxGaps");
TreeTableColumn<FullSequenceHeaders,Long> incGaps = new TreeTableColumn<>("incGaps");
TreeTableColumn<FullSequenceHeaders,Long> firstGaps = new TreeTableColumn<>("firstGaps");
TreeTableColumn<FullSequenceHeaders,Long> lastGaps = new TreeTableColumn<>("lastGaps");
TreeTableColumn<FullSequenceHeaders,Long> minOtrav = new TreeTableColumn<>("minOtrav");
TreeTableColumn<FullSequenceHeaders,Long> maxOtrav = new TreeTableColumn<>("maxOtrav");
TreeTableColumn<FullSequenceHeaders,Long> incOtrav = new TreeTableColumn<>("incOtrav");
TreeTableColumn<FullSequenceHeaders,Long> firstOtrav = new TreeTableColumn<>("firstOtrav");
TreeTableColumn<FullSequenceHeaders,Long> lastOtrav = new TreeTableColumn<>("lastOtrav");
TreeTableColumn<FullSequenceHeaders,Long> minCdpx = new TreeTableColumn<>("minCdpx");
TreeTableColumn<FullSequenceHeaders,Long> maxCdpx = new TreeTableColumn<>("maxCdpx");
TreeTableColumn<FullSequenceHeaders,Long> incCdpx = new TreeTableColumn<>("incCdpx");
TreeTableColumn<FullSequenceHeaders,Long> firstCdpx = new TreeTableColumn<>("firstCdpx");
TreeTableColumn<FullSequenceHeaders,Long> lastCdpx = new TreeTableColumn<>("lastCdpx");
TreeTableColumn<FullSequenceHeaders,Long> minCdpy = new TreeTableColumn<>("minCdpy");
TreeTableColumn<FullSequenceHeaders,Long> maxCdpy = new TreeTableColumn<>("maxCdpy");
TreeTableColumn<FullSequenceHeaders,Long> incCdpy = new TreeTableColumn<>("incCdpy");
TreeTableColumn<FullSequenceHeaders,Long> firstCdpy = new TreeTableColumn<>("firstCdpy");
TreeTableColumn<FullSequenceHeaders,Long> lastCdpy = new TreeTableColumn<>("lastCdpy");
TreeTableColumn<FullSequenceHeaders,Long> minInline = new TreeTableColumn<>("minInline");
TreeTableColumn<FullSequenceHeaders,Long> maxInline = new TreeTableColumn<>("maxInline");
TreeTableColumn<FullSequenceHeaders,Long> incInline = new TreeTableColumn<>("incInline");
TreeTableColumn<FullSequenceHeaders,Long> firstInline = new TreeTableColumn<>("firstInline");
TreeTableColumn<FullSequenceHeaders,Long> lastInline = new TreeTableColumn<>("lastInline");
TreeTableColumn<FullSequenceHeaders,Long> minCrossline = new TreeTableColumn<>("minCrossline");
TreeTableColumn<FullSequenceHeaders,Long> maxCrossline = new TreeTableColumn<>("maxCrossline");
TreeTableColumn<FullSequenceHeaders,Long> incCrossline = new TreeTableColumn<>("incCrossline");
TreeTableColumn<FullSequenceHeaders,Long> firstCrossline = new TreeTableColumn<>("firstCrossline");
TreeTableColumn<FullSequenceHeaders,Long> lastCrossline = new TreeTableColumn<>("lastCrossline");
TreeTableColumn<FullSequenceHeaders,Long> minShotpoint = new TreeTableColumn<>("minShotpoint");
TreeTableColumn<FullSequenceHeaders,Long> maxShotpoint = new TreeTableColumn<>("maxShotpoint");
TreeTableColumn<FullSequenceHeaders,Long> incShotpoint = new TreeTableColumn<>("incShotpoint");
TreeTableColumn<FullSequenceHeaders,Long> firstShotpoint = new TreeTableColumn<>("firstShotpoint");
TreeTableColumn<FullSequenceHeaders,Long> lastShotpoint = new TreeTableColumn<>("lastShotpoint");
TreeTableColumn<FullSequenceHeaders,Long> minScalsp = new TreeTableColumn<>("minScalsp");
TreeTableColumn<FullSequenceHeaders,Long> maxScalsp = new TreeTableColumn<>("maxScalsp");
TreeTableColumn<FullSequenceHeaders,Long> incScalsp = new TreeTableColumn<>("incScalsp");
TreeTableColumn<FullSequenceHeaders,Long> firstScalsp = new TreeTableColumn<>("firstScalsp");
TreeTableColumn<FullSequenceHeaders,Long> lastScalsp = new TreeTableColumn<>("lastScalsp");
TreeTableColumn<FullSequenceHeaders,Long> minTcm = new TreeTableColumn<>("minTcm");
TreeTableColumn<FullSequenceHeaders,Long> maxTcm = new TreeTableColumn<>("maxTcm");
TreeTableColumn<FullSequenceHeaders,Long> incTcm = new TreeTableColumn<>("incTcm");
TreeTableColumn<FullSequenceHeaders,Long> firstTcm = new TreeTableColumn<>("firstTcm");
TreeTableColumn<FullSequenceHeaders,Long> lastTcm = new TreeTableColumn<>("lastTcm");
TreeTableColumn<FullSequenceHeaders,Long> minTid = new TreeTableColumn<>("minTid");
TreeTableColumn<FullSequenceHeaders,Long> maxTid = new TreeTableColumn<>("maxTid");
TreeTableColumn<FullSequenceHeaders,Long> incTid = new TreeTableColumn<>("incTid");
TreeTableColumn<FullSequenceHeaders,Long> firstTid = new TreeTableColumn<>("firstTid");
TreeTableColumn<FullSequenceHeaders,Long> lastTid = new TreeTableColumn<>("lastTid");
TreeTableColumn<FullSequenceHeaders,Long> tid = new TreeTableColumn<>("tid");
TreeTableColumn<FullSequenceHeaders,Long> minSedm = new TreeTableColumn<>("minSedm");
TreeTableColumn<FullSequenceHeaders,Long> maxSedm = new TreeTableColumn<>("maxSedm");
TreeTableColumn<FullSequenceHeaders,Long> incSedm = new TreeTableColumn<>("incSedm");
TreeTableColumn<FullSequenceHeaders,Long> firstSedm = new TreeTableColumn<>("firstSedm");
TreeTableColumn<FullSequenceHeaders,Long> lastSedm = new TreeTableColumn<>("lastSedm");
TreeTableColumn<FullSequenceHeaders,Long> minSede = new TreeTableColumn<>("minSede");
TreeTableColumn<FullSequenceHeaders,Long> maxSede = new TreeTableColumn<>("maxSede");
TreeTableColumn<FullSequenceHeaders,Long> incSede = new TreeTableColumn<>("incSede");
TreeTableColumn<FullSequenceHeaders,Long> firstSede = new TreeTableColumn<>("firstSede");
TreeTableColumn<FullSequenceHeaders,Long> lastSede = new TreeTableColumn<>("lastSede");
TreeTableColumn<FullSequenceHeaders,Long> sede = new TreeTableColumn<>("sede");
TreeTableColumn<FullSequenceHeaders,Long> minSmm = new TreeTableColumn<>("minSmm");
TreeTableColumn<FullSequenceHeaders,Long> maxSmm = new TreeTableColumn<>("maxSmm");
TreeTableColumn<FullSequenceHeaders,Long> incSmm = new TreeTableColumn<>("incSmm");
TreeTableColumn<FullSequenceHeaders,Long> firstSmm = new TreeTableColumn<>("firstSmm");
TreeTableColumn<FullSequenceHeaders,Long> lastSmm = new TreeTableColumn<>("lastSmm");
TreeTableColumn<FullSequenceHeaders,Long> minSme = new TreeTableColumn<>("minSme");
TreeTableColumn<FullSequenceHeaders,Long> maxSme = new TreeTableColumn<>("maxSme");
TreeTableColumn<FullSequenceHeaders,Long> incSme = new TreeTableColumn<>("incSme");
TreeTableColumn<FullSequenceHeaders,Long> firstSme = new TreeTableColumn<>("firstSme");
TreeTableColumn<FullSequenceHeaders,Long> lastSme = new TreeTableColumn<>("lastSme");
TreeTableColumn<FullSequenceHeaders,Long> minSmu = new TreeTableColumn<>("minSmu");
TreeTableColumn<FullSequenceHeaders,Long> maxSmu = new TreeTableColumn<>("maxSmu");
TreeTableColumn<FullSequenceHeaders,Long> incSmu = new TreeTableColumn<>("incSmu");
TreeTableColumn<FullSequenceHeaders,Long> firstSmu = new TreeTableColumn<>("firstSmu");
TreeTableColumn<FullSequenceHeaders,Long> lastSmu = new TreeTableColumn<>("lastSmu");
TreeTableColumn<FullSequenceHeaders,Long> minUi1 = new TreeTableColumn<>("minUi1");
TreeTableColumn<FullSequenceHeaders,Long> maxUi1 = new TreeTableColumn<>("maxUi1");
TreeTableColumn<FullSequenceHeaders,Long> incUi1 = new TreeTableColumn<>("incUi1");
TreeTableColumn<FullSequenceHeaders,Long> firstUi1 = new TreeTableColumn<>("firstUi1");
TreeTableColumn<FullSequenceHeaders,Long> lastUi1 = new TreeTableColumn<>("lastUi1");
TreeTableColumn<FullSequenceHeaders,Long> minUi2 = new TreeTableColumn<>("minUi2");
TreeTableColumn<FullSequenceHeaders,Long> maxUi2 = new TreeTableColumn<>("maxUi2");
TreeTableColumn<FullSequenceHeaders,Long> incUi2 = new TreeTableColumn<>("incUi2");
TreeTableColumn<FullSequenceHeaders,Long> firstUi2 = new TreeTableColumn<>("firstUi2");
TreeTableColumn<FullSequenceHeaders,Long> lastUi2 = new TreeTableColumn<>("lastUi2");
TreeTableColumn<FullSequenceHeaders,Long> ui2 = new TreeTableColumn<>("ui2");
TreeTableColumn<FullSequenceHeaders,Long> minVer = new TreeTableColumn<>("minVer");
TreeTableColumn<FullSequenceHeaders,Long> maxVer = new TreeTableColumn<>("maxVer");
TreeTableColumn<FullSequenceHeaders,Long> incVer = new TreeTableColumn<>("incVer");
TreeTableColumn<FullSequenceHeaders,Long> sampleRate = new TreeTableColumn<>("sampleRate");
TreeTableColumn<FullSequenceHeaders,Long> recordLength = new TreeTableColumn<>("recordLength");
TreeTableColumn<FullSequenceHeaders,Long> unitVer = new TreeTableColumn<>("unitVer");
     
     
    void setModel(FullHeaderLineTableModel item) {
        
        model=item;
        model.reloadTableProperty().addListener(RELOAD_TABLE_LISTENER);
        exec=Executors.newCachedThreadPool(r->{
            Thread t=new Thread(r);
            t.setDaemon(true);
            return t;
        });
    }

    void setView(FullHeaderLineTableView vw) {
        view=vw;
        
     /*   treetableView.setRowFactory(ttv->{
            ContextMenu contextMenu = new ContextMenu();
         //ContextMenu contextMenuOverride = new ContextMenu();
         MenuItem showLogsMenuItem=new MenuItem("Logs");
         MenuItem showWorkFlowVersion=new MenuItem("Workflow Versions");
         MenuItem chooseThisHeader=new MenuItem("Choose this subsurface");
         MenuItem deselectThisHeader=new MenuItem("Deselect this subsurface");
         MenuItem showOverride=new MenuItem("Override Doubt");
         contextMenu.getItems().add(showLogsMenuItem);
         contextMenu.getItems().add(showWorkFlowVersion); 
         
         TreeTableRow<SequenceHeaders> row=new TreeTableRow<SequenceHeaders>(){
             
             
             
             @Override
             protected void updateItem(SequenceHeaders item,boolean empty){
                 super.updateItem(item,empty);
                  if(item==null || empty){
                    setText(null);
                    setStyle("");
                    setContextMenu(null);
                }if(item!=null&& !item.getChosen()){
                    ContextMenu cm=new ContextMenu();
                    cm.getItems().add(showLogsMenuItem);
                    cm.getItems().add(showWorkFlowVersion);
                    cm.getItems().add(chooseThisHeader);
                    setContextMenu(cm);
                }if(item!=null&& item.getChosen() && item.getMultiple()){
                    ContextMenu cm=new ContextMenu();
                    cm.getItems().add(showLogsMenuItem);
                    cm.getItems().add(showWorkFlowVersion);
                    cm.getItems().add(deselectThisHeader);
                    setContextMenu(cm);
                }
                else if(item!=null && item.getChosen()){
                    
                    setContextMenu(contextMenu);
                }
                
             }
         };
         
         chooseThisHeader.setOnAction(evt->{
             Long id=row.getItem().getId();
             Fheader h=headerService.getHeader(id);
             h.setChosen(true);
             headerService.updateHeader(h.getfHeaderId(), h);
             Subsurface conflictedSub=h.getSubsurface();
             Job currentJob=h.getJob();
             Volume volumeSelected=h.getVolume();
             
             headerService.setChosenToFalseForConflictingSubs(conflictedSub,currentJob,volumeSelected);             //all conflicted except the one selected will have chosen=false and multiple=true;
             //run the headerloader once more. model.reloadSequenceHeaders();
             //set multipleSubsurfacesPresent=false;
             //rebuild table
             model.getJob().reLoadSequenceHeaders();
             multipleSubsPresent=false;
             
         });
         
         deselectThisHeader.setOnAction(evt->{
              Long id=row.getItem().getId();
             Fheader h=headerService.getHeader(id);
             h.setChosen(false);
             headerService.updateHeader(h.getfHeaderId(), h);
             
             //run the headerloader once more. model.reloadSequenceHeaders();
             //set multipleSubsurfacesPresent=false;
             //rebuild table
             model.getJob().reLoadSequenceHeaders();
             multipleSubsPresent=false;
         });
         
         
         showLogsMenuItem.setOnAction(e->{
                Long id=row.getItem().getId();
          
              Set<VersionLogsModel> versionModels=new HashSet<>();
             Task<String> loghTask=new Task<String>(){
                    @Override
                    protected String call() throws Exception {
                        Fheader h=headerService.getHeader(id);
                        //Set<Log> logs=h.getLogs();
                        Set<Log> logs=new HashSet<>(logservice.getLogsFor(h));

                        for(Log l:logs){
                           VersionLogsModel vlm=new VersionLogsModel(l.getVersion(), l.getTimestamp(), l.getLogpath());
                           versionModels.add(vlm);
                        }
                   
                        return "Finished extracting logs for : "+h.getfHeaderId();
                    
                    }
                 
             };
             
             loghTask.setOnSucceeded(ee->{
             
                    HeaderLogModel headerLogModel=new HeaderLogModel();
                    headerLogModel.setLogsmodel(new ArrayList<>(versionModels));
                    HeaderLogView headerLogView=new HeaderLogView(headerLogModel);
             
             });
             
             loghTask.setOnRunning(ee->{});
             loghTask.setOnFailed(ee->{
                 loghTask.getException().printStackTrace();
             });
                exec.execute(loghTask);
                
                
                
         });
         
         showWorkFlowVersion.setOnAction(e->{
              Long id=row.getItem().getId();
            
           final  WorkFlowDifferenceModel workFlowDifferenceModel=new WorkFlowDifferenceModel();
             
             Task<String> workflowTask=new Task<String>(){
                    @Override
                    protected String call() throws Exception {
                        Fheader h=headerService.getHeader(id);
                        Volume v=h.getVolume();
                        List<Workflow> workflows=workflowService.getWorkFlowsFor(v);
                        System.out.println(".call(): got "+workflows.size()+" for headerID: "+h.getfHeaderId()+" vol: "+v.getId());
                        Map<Long,Workflow> mapversionWorkflow=new HashMap<>();
                        List<Long> versions=new ArrayList<>();
                        for(Workflow w: workflows){
                            System.out.println(".call(): map.put:( "+w.getWfversion()+","+w.getId()+")");
                            mapversionWorkflow.put(w.getWfversion(), w);
                            versions.add(w.getWfversion());
                        }
                        Workflow hdrWorkflw=mapversionWorkflow.get(h.getWorkflowVersion());
                        System.out.println(".call(): current headers workflow id: "+hdrWorkflw.getId());
                        workFlowDifferenceModel.setMapOfVersionsVersusWorkflows(mapversionWorkflow);
                        workFlowDifferenceModel.setLhsObs(versions);
                        workFlowDifferenceModel.setRhsObs(versions);
                        workFlowDifferenceModel.setLhsWorkflow(hdrWorkflw);
                        workFlowDifferenceModel.setChosenHdr(h);
                   
                        return "Finished extracting logs for : "+h.getfHeaderId();
                    
                    }
                 
             };
             
             
             workflowTask.setOnSucceeded(ee->{
             
                   WorkFlowDifferenceView workFlowDifferenceView=new WorkFlowDifferenceView(workFlowDifferenceModel);
             
             });
             
             workflowTask.setOnRunning(ee->{});
             workflowTask.setOnFailed(ee->{
                 workflowTask.getException().printStackTrace();
             });
                exec.execute(workflowTask);
         });
         
         
         
         
         return row;
        });*/
        
        
        
        
        //sequenceNumber.setCellValueFactory(new TreeItemPropertyValueFactory<>("sequenceNumber"));
        sequenceNumber.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FullSequenceHeaders, Long>, ObservableValue<Long>>() {
            @Override
            public ObservableValue<Long> call(TreeTableColumn.CellDataFeatures<FullSequenceHeaders, Long> param) {
                return new SimpleLongProperty(param.getValue().getValue().getSequence().getSequenceno()).asObject();
            }
        });
        //subsurfaceName.setCellValueFactory(new TreeItemPropertyValueFactory<>("subsurface"));
        subsurfaceName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FullSequenceHeaders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FullSequenceHeaders, String> param) {
                
                String sub=new String();
                FullSequenceHeaders val=param.getValue().getValue();
                if(val.isSequence()){
                    sub=val.getSequence().getRealLineName();
                }else{
                    sub=val.getSubsurfaceName();
                }
                return new SimpleStringProperty(sub);
            }
        });
        
        /**
         * generated using 
         * dugio read file=160_P_Overlapping_shot_attenuation.dugio line=696501061101_cable5_gun1 query=time:0 full_headers | durange raw=true output=bash | awk '{split($0,a,"=");print a[1]".setCellValueFactory(new TreeItemPropertyValueFactory<>(\""a[1]"\"));"}'
         **/
        totalTraces.setCellValueFactory(new TreeItemPropertyValueFactory<>("totalTraces"));
        minTracr.setCellValueFactory(new TreeItemPropertyValueFactory<>("minTracr"));
        maxTracr.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxTracr"));
        incTracr.setCellValueFactory(new TreeItemPropertyValueFactory<>("incTracr"));
        firstTracr.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstTracr"));
        lastTracr.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastTracr"));
        minFldr.setCellValueFactory(new TreeItemPropertyValueFactory<>("minFldr"));
        maxFldr.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxFldr"));
        incFldr.setCellValueFactory(new TreeItemPropertyValueFactory<>("incFldr"));
        firstFldr.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstFldr"));
        lastFldr.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastFldr"));
        minTracf.setCellValueFactory(new TreeItemPropertyValueFactory<>("minTracf"));
        maxTracf.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxTracf"));
        incTracf.setCellValueFactory(new TreeItemPropertyValueFactory<>("incTracf"));
        firstTracf.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstTracf"));
        lastTracf.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastTracf"));
        minEp.setCellValueFactory(new TreeItemPropertyValueFactory<>("minEp"));
        maxEp.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxEp"));
        incEp.setCellValueFactory(new TreeItemPropertyValueFactory<>("incEp"));
        firstEp.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstEp"));
        lastEp.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastEp"));
        minCdp.setCellValueFactory(new TreeItemPropertyValueFactory<>("minCdp"));
        maxCdp.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxCdp"));
        incCdp.setCellValueFactory(new TreeItemPropertyValueFactory<>("incCdp"));
        firstCdp.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstCdp"));
        lastCdp.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastCdp"));
        minCdpt.setCellValueFactory(new TreeItemPropertyValueFactory<>("minCdpt"));
        maxCdpt.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxCdpt"));
        incCdpt.setCellValueFactory(new TreeItemPropertyValueFactory<>("incCdpt"));
        firstCdpt.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstCdpt"));
        lastCdpt.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastCdpt"));
        minTrid.setCellValueFactory(new TreeItemPropertyValueFactory<>("minTrid"));
        maxTrid.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxTrid"));
        incTrid.setCellValueFactory(new TreeItemPropertyValueFactory<>("incTrid"));
        firstTrid.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstTrid"));
        lastTrid.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastTrid"));
        minDuse.setCellValueFactory(new TreeItemPropertyValueFactory<>("minDuse"));
        maxDuse.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxDuse"));
        incDuse.setCellValueFactory(new TreeItemPropertyValueFactory<>("incDuse"));
        firstDuse.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstDuse"));
        lastDuse.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastDuse"));
        duse.setCellValueFactory(new TreeItemPropertyValueFactory<>("duse"));
        minOffset.setCellValueFactory(new TreeItemPropertyValueFactory<>("minOffset"));
        maxOffset.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxOffset"));
        incOffset.setCellValueFactory(new TreeItemPropertyValueFactory<>("incOffset"));
        firstOffset.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstOffset"));
        lastOffset.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastOffset"));
        minGelev.setCellValueFactory(new TreeItemPropertyValueFactory<>("minGelev"));
        maxGelev.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxGelev"));
        incGelev.setCellValueFactory(new TreeItemPropertyValueFactory<>("incGelev"));
        firstGelev.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstGelev"));
        lastGelev.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastGelev"));
        minSelev.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSelev"));
        maxSelev.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSelev"));
        incSelev.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSelev"));
        firstSelev.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSelev"));
        lastSelev.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSelev"));
        minSdepth.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSdepth"));
        maxSdepth.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSdepth"));
        incSdepth.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSdepth"));
        firstSdepth.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSdepth"));
        lastSdepth.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSdepth"));
        minSwdep.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSwdep"));
        maxSwdep.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSwdep"));
        incSwdep.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSwdep"));
        firstSwdep.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSwdep"));
        lastSwdep.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSwdep"));
        minGwdep.setCellValueFactory(new TreeItemPropertyValueFactory<>("minGwdep"));
        maxGwdep.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxGwdep"));
        incGwdep.setCellValueFactory(new TreeItemPropertyValueFactory<>("incGwdep"));
        firstGwdep.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstGwdep"));
        lastGwdep.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastGwdep"));
        minScalel.setCellValueFactory(new TreeItemPropertyValueFactory<>("minScalel"));
        maxScalel.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxScalel"));
        incScalel.setCellValueFactory(new TreeItemPropertyValueFactory<>("incScalel"));
        firstScalel.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstScalel"));
        lastScalel.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastScalel"));
        scalel.setCellValueFactory(new TreeItemPropertyValueFactory<>("scalel"));
        minScalco.setCellValueFactory(new TreeItemPropertyValueFactory<>("minScalco"));
        maxScalco.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxScalco"));
        incScalco.setCellValueFactory(new TreeItemPropertyValueFactory<>("incScalco"));
        firstScalco.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstScalco"));
        lastScalco.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastScalco"));
        scalco.setCellValueFactory(new TreeItemPropertyValueFactory<>("scalco"));
        minSx.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSx"));
        maxSx.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSx"));
        incSx.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSx"));
        firstSx.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSx"));
        lastSx.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSx"));
        minSy.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSy"));
        maxSy.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSy"));
        incSy.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSy"));
        firstSy.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSy"));
        lastSy.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSy"));
        minGx.setCellValueFactory(new TreeItemPropertyValueFactory<>("minGx"));
        maxGx.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxGx"));
        incGx.setCellValueFactory(new TreeItemPropertyValueFactory<>("incGx"));
        firstGx.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstGx"));
        lastGx.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastGx"));
        minGy.setCellValueFactory(new TreeItemPropertyValueFactory<>("minGy"));
        maxGy.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxGy"));
        incGy.setCellValueFactory(new TreeItemPropertyValueFactory<>("incGy"));
        firstGy.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstGy"));
        lastGy.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastGy"));
        minWevel.setCellValueFactory(new TreeItemPropertyValueFactory<>("minWevel"));
        maxWevel.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxWevel"));
        incWevel.setCellValueFactory(new TreeItemPropertyValueFactory<>("incWevel"));
        firstWevel.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstWevel"));
        lastWevel.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastWevel"));
        wevel.setCellValueFactory(new TreeItemPropertyValueFactory<>("wevel"));
        minSwevel.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSwevel"));
        maxSwevel.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSwevel"));
        incSwevel.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSwevel"));
        firstSwevel.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSwevel"));
        lastSwevel.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSwevel"));
        swevel.setCellValueFactory(new TreeItemPropertyValueFactory<>("swevel"));
        minSut.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSut"));
        maxSut.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSut"));
        incSut.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSut"));
        firstSut.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSut"));
        lastSut.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSut"));
        minGut.setCellValueFactory(new TreeItemPropertyValueFactory<>("minGut"));
        maxGut.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxGut"));
        incGut.setCellValueFactory(new TreeItemPropertyValueFactory<>("incGut"));
        firstGut.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstGut"));
        lastGut.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastGut"));
        minSstat.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSstat"));
        maxSstat.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSstat"));
        incSstat.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSstat"));
        firstSstat.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSstat"));
        lastSstat.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSstat"));
        minGstat.setCellValueFactory(new TreeItemPropertyValueFactory<>("minGstat"));
        maxGstat.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxGstat"));
        incGstat.setCellValueFactory(new TreeItemPropertyValueFactory<>("incGstat"));
        firstGstat.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstGstat"));
        lastGstat.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastGstat"));
        minLagb.setCellValueFactory(new TreeItemPropertyValueFactory<>("minLagb"));
        maxLagb.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxLagb"));
        incLagb.setCellValueFactory(new TreeItemPropertyValueFactory<>("incLagb"));
        firstLagb.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstLagb"));
        lastLagb.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastLagb"));
        minNs.setCellValueFactory(new TreeItemPropertyValueFactory<>("minNs"));
        maxNs.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxNs"));
        incNs.setCellValueFactory(new TreeItemPropertyValueFactory<>("incNs"));
        firstNs.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstNs"));
        lastNs.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastNs"));
        ns.setCellValueFactory(new TreeItemPropertyValueFactory<>("ns"));
        minDt.setCellValueFactory(new TreeItemPropertyValueFactory<>("minDt"));
        maxDt.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxDt"));
        incDt.setCellValueFactory(new TreeItemPropertyValueFactory<>("incDt"));
        firstDt.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstDt"));
        lastDt.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastDt"));
        dt.setCellValueFactory(new TreeItemPropertyValueFactory<>("dt"));
        minSfs.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSfs"));
        maxSfs.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSfs"));
        incSfs.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSfs"));
        firstSfs.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSfs"));
        lastSfs.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSfs"));
        minSfe.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSfe"));
        maxSfe.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSfe"));
        incSfe.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSfe"));
        firstSfe.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSfe"));
        lastSfe.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSfe"));
        minSlen.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSlen"));
        maxSlen.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSlen"));
        incSlen.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSlen"));
        firstSlen.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSlen"));
        lastSlen.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSlen"));
        slen.setCellValueFactory(new TreeItemPropertyValueFactory<>("slen"));
        minStyp.setCellValueFactory(new TreeItemPropertyValueFactory<>("minStyp"));
        maxStyp.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxStyp"));
        incStyp.setCellValueFactory(new TreeItemPropertyValueFactory<>("incStyp"));
        firstStyp.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstStyp"));
        lastStyp.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastStyp"));
        minStas.setCellValueFactory(new TreeItemPropertyValueFactory<>("minStas"));
        maxStas.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxStas"));
        incStas.setCellValueFactory(new TreeItemPropertyValueFactory<>("incStas"));
        firstStas.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstStas"));
        lastStas.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastStas"));
        minStae.setCellValueFactory(new TreeItemPropertyValueFactory<>("minStae"));
        maxStae.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxStae"));
        incStae.setCellValueFactory(new TreeItemPropertyValueFactory<>("incStae"));
        firstStae.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstStae"));
        lastStae.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastStae"));
        stae.setCellValueFactory(new TreeItemPropertyValueFactory<>("stae"));
        minAfilf.setCellValueFactory(new TreeItemPropertyValueFactory<>("minAfilf"));
        maxAfilf.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxAfilf"));
        incAfilf.setCellValueFactory(new TreeItemPropertyValueFactory<>("incAfilf"));
        firstAfilf.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstAfilf"));
        lastAfilf.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastAfilf"));
        afilf.setCellValueFactory(new TreeItemPropertyValueFactory<>("afilf"));
        minAfils.setCellValueFactory(new TreeItemPropertyValueFactory<>("minAfils"));
        maxAfils.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxAfils"));
        incAfils.setCellValueFactory(new TreeItemPropertyValueFactory<>("incAfils"));
        firstAfils.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstAfils"));
        lastAfils.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastAfils"));
        afils.setCellValueFactory(new TreeItemPropertyValueFactory<>("afils"));
        minLcf.setCellValueFactory(new TreeItemPropertyValueFactory<>("minLcf"));
        maxLcf.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxLcf"));
        incLcf.setCellValueFactory(new TreeItemPropertyValueFactory<>("incLcf"));
        firstLcf.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstLcf"));
        lastLcf.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastLcf"));
        lcf.setCellValueFactory(new TreeItemPropertyValueFactory<>("lcf"));
        minLcs.setCellValueFactory(new TreeItemPropertyValueFactory<>("minLcs"));
        maxLcs.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxLcs"));
        incLcs.setCellValueFactory(new TreeItemPropertyValueFactory<>("incLcs"));
        firstLcs.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstLcs"));
        lastLcs.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastLcs"));
        lcs.setCellValueFactory(new TreeItemPropertyValueFactory<>("lcs"));
        minHcs.setCellValueFactory(new TreeItemPropertyValueFactory<>("minHcs"));
        maxHcs.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxHcs"));
        incHcs.setCellValueFactory(new TreeItemPropertyValueFactory<>("incHcs"));
        firstHcs.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstHcs"));
        lastHcs.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastHcs"));
        minYear.setCellValueFactory(new TreeItemPropertyValueFactory<>("minYear"));
        maxYear.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxYear"));
        incYear.setCellValueFactory(new TreeItemPropertyValueFactory<>("incYear"));
        firstYear.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstYear"));
        lastYear.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastYear"));
        year.setCellValueFactory(new TreeItemPropertyValueFactory<>("year"));
        minDay.setCellValueFactory(new TreeItemPropertyValueFactory<>("minDay"));
        maxDay.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxDay"));
        incDay.setCellValueFactory(new TreeItemPropertyValueFactory<>("incDay"));
        firstDay.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstDay"));
        lastDay.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastDay"));
        day.setCellValueFactory(new TreeItemPropertyValueFactory<>("day"));
        minHour.setCellValueFactory(new TreeItemPropertyValueFactory<>("minHour"));
        maxHour.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxHour"));
        incHour.setCellValueFactory(new TreeItemPropertyValueFactory<>("incHour"));
        firstHour.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstHour"));
        lastHour.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastHour"));
        minMinute.setCellValueFactory(new TreeItemPropertyValueFactory<>("minMinute"));
        maxMinute.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxMinute"));
        incMinute.setCellValueFactory(new TreeItemPropertyValueFactory<>("incMinute"));
        firstMinute.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstMinute"));
        lastMinute.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastMinute"));
        minSec.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSec"));
        maxSec.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSec"));
        incSec.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSec"));
        firstSec.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSec"));
        lastSec.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSec"));
        minGrnors.setCellValueFactory(new TreeItemPropertyValueFactory<>("minGrnors"));
        maxGrnors.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxGrnors"));
        incGrnors.setCellValueFactory(new TreeItemPropertyValueFactory<>("incGrnors"));
        firstGrnors.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstGrnors"));
        lastGrnors.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastGrnors"));
        grnors.setCellValueFactory(new TreeItemPropertyValueFactory<>("grnors"));
        minGrnofr.setCellValueFactory(new TreeItemPropertyValueFactory<>("minGrnofr"));
        maxGrnofr.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxGrnofr"));
        incGrnofr.setCellValueFactory(new TreeItemPropertyValueFactory<>("incGrnofr"));
        firstGrnofr.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstGrnofr"));
        lastGrnofr.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastGrnofr"));
        grnofr.setCellValueFactory(new TreeItemPropertyValueFactory<>("grnofr"));
        minGaps.setCellValueFactory(new TreeItemPropertyValueFactory<>("minGaps"));
        maxGaps.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxGaps"));
        incGaps.setCellValueFactory(new TreeItemPropertyValueFactory<>("incGaps"));
        firstGaps.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstGaps"));
        lastGaps.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastGaps"));
        minOtrav.setCellValueFactory(new TreeItemPropertyValueFactory<>("minOtrav"));
        maxOtrav.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxOtrav"));
        incOtrav.setCellValueFactory(new TreeItemPropertyValueFactory<>("incOtrav"));
        firstOtrav.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstOtrav"));
        lastOtrav.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastOtrav"));
        minCdpx.setCellValueFactory(new TreeItemPropertyValueFactory<>("minCdpx"));
        maxCdpx.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxCdpx"));
        incCdpx.setCellValueFactory(new TreeItemPropertyValueFactory<>("incCdpx"));
        firstCdpx.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstCdpx"));
        lastCdpx.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastCdpx"));
        minCdpy.setCellValueFactory(new TreeItemPropertyValueFactory<>("minCdpy"));
        maxCdpy.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxCdpy"));
        incCdpy.setCellValueFactory(new TreeItemPropertyValueFactory<>("incCdpy"));
        firstCdpy.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstCdpy"));
        lastCdpy.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastCdpy"));
        minInline.setCellValueFactory(new TreeItemPropertyValueFactory<>("minInline"));
        maxInline.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxInline"));
        incInline.setCellValueFactory(new TreeItemPropertyValueFactory<>("incInline"));
        firstInline.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstInline"));
        lastInline.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastInline"));
        minCrossline.setCellValueFactory(new TreeItemPropertyValueFactory<>("minCrossline"));
        maxCrossline.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxCrossline"));
        incCrossline.setCellValueFactory(new TreeItemPropertyValueFactory<>("incCrossline"));
        firstCrossline.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstCrossline"));
        lastCrossline.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastCrossline"));
        minShotpoint.setCellValueFactory(new TreeItemPropertyValueFactory<>("minShotpoint"));
        maxShotpoint.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxShotpoint"));
        incShotpoint.setCellValueFactory(new TreeItemPropertyValueFactory<>("incShotpoint"));
        firstShotpoint.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstShotpoint"));
        lastShotpoint.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastShotpoint"));
        minScalsp.setCellValueFactory(new TreeItemPropertyValueFactory<>("minScalsp"));
        maxScalsp.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxScalsp"));
        incScalsp.setCellValueFactory(new TreeItemPropertyValueFactory<>("incScalsp"));
        firstScalsp.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstScalsp"));
        lastScalsp.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastScalsp"));
        minTcm.setCellValueFactory(new TreeItemPropertyValueFactory<>("minTcm"));
        maxTcm.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxTcm"));
        incTcm.setCellValueFactory(new TreeItemPropertyValueFactory<>("incTcm"));
        firstTcm.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstTcm"));
        lastTcm.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastTcm"));
        minTid.setCellValueFactory(new TreeItemPropertyValueFactory<>("minTid"));
        maxTid.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxTid"));
        incTid.setCellValueFactory(new TreeItemPropertyValueFactory<>("incTid"));
        firstTid.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstTid"));
        lastTid.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastTid"));
        tid.setCellValueFactory(new TreeItemPropertyValueFactory<>("tid"));
        minSedm.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSedm"));
        maxSedm.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSedm"));
        incSedm.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSedm"));
        firstSedm.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSedm"));
        lastSedm.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSedm"));
        minSede.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSede"));
        maxSede.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSede"));
        incSede.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSede"));
        firstSede.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSede"));
        lastSede.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSede"));
        sede.setCellValueFactory(new TreeItemPropertyValueFactory<>("sede"));
        minSmm.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSmm"));
        maxSmm.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSmm"));
        incSmm.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSmm"));
        firstSmm.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSmm"));
        lastSmm.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSmm"));
        minSme.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSme"));
        maxSme.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSme"));
        incSme.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSme"));
        firstSme.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSme"));
        lastSme.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSme"));
        minSmu.setCellValueFactory(new TreeItemPropertyValueFactory<>("minSmu"));
        maxSmu.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxSmu"));
        incSmu.setCellValueFactory(new TreeItemPropertyValueFactory<>("incSmu"));
        firstSmu.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstSmu"));
        lastSmu.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastSmu"));
        minUi1.setCellValueFactory(new TreeItemPropertyValueFactory<>("minUi1"));
        maxUi1.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxUi1"));
        incUi1.setCellValueFactory(new TreeItemPropertyValueFactory<>("incUi1"));
        firstUi1.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstUi1"));
        lastUi1.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastUi1"));
        minUi2.setCellValueFactory(new TreeItemPropertyValueFactory<>("minUi2"));
        maxUi2.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxUi2"));
        incUi2.setCellValueFactory(new TreeItemPropertyValueFactory<>("incUi2"));
        firstUi2.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstUi2"));
        lastUi2.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastUi2"));
        ui2.setCellValueFactory(new TreeItemPropertyValueFactory<>("ui2"));
        minVer.setCellValueFactory(new TreeItemPropertyValueFactory<>("minVer"));
        maxVer.setCellValueFactory(new TreeItemPropertyValueFactory<>("maxVer"));
        incVer.setCellValueFactory(new TreeItemPropertyValueFactory<>("incVer"));
        sampleRate.setCellValueFactory(new TreeItemPropertyValueFactory<>("sampleRate"));
        recordLength.setCellValueFactory(new TreeItemPropertyValueFactory<>("recordLength"));
        unitVer.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitVer"));

        /*chosen.setCellFactory((TreeTableColumn<SequenceHeaders,Boolean> p)->{
        TreeTableCell cell=new TreeTableCell<SequenceHeaders,Boolean>(){
        @Override
        protected void updateItem(Boolean isChosen,boolean empty){
        super.updateItem(isChosen, empty);
        TreeTableRow<SequenceHeaders> seqTreeRow = getTreeTableRow();
        
        if(isChosen==null||empty){
        setText(null);
        seqTreeRow.setStyle("");
        }else if(isChosen){
        setText(isChosen.toString());
        }else if(!isChosen){
        seqTreeRow.setStyle("-fx-background-color: "+colorisNotChosen);
        setText(isChosen.toString());
        }
        
        
        }
        };
        return cell;
        });
        
        
        volume.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SequenceHeaders, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SequenceHeaders, String> param) {
        SequenceHeaders seq=param.getValue().getValue();
        Volume0 v=seq.getVolume();
        if(v==null) return new SimpleStringProperty("Multiple volumes");
        return v.getName();
        }
        });
        
        */
        
        
        
        
        
        
        
        
        
        
        
        
        sequenceNumber.setMinWidth(100);
        subsurfaceName.setMinWidth(250);
        
        
        
        List<TreeItem<FullSequenceHeaders>> treeSeq=new ArrayList<>();
        for(FullSequenceHeaders seq:model.getFullSequenceHeaders()){
            TreeItem<FullSequenceHeaders> seqroot=new TreeItem<>(seq);
            for(FullSequenceHeaders sub:seq.getSubsurfaceHeaders()){
                TreeItem<FullSequenceHeaders> subitem=new TreeItem<>(sub);
                    if(!sub.getChosen()) {
                        multipleSubsPresent=true;
                    }
                seqroot.getChildren().add(subitem);
            }
            treeSeq.add(seqroot);
        }
        
        treeSeq.sort((o1, o2) -> {
           
            
            return ((FullSequenceHeaders)o1.getValue()).getSequence().getSequenceno().compareTo(((FullSequenceHeaders)o2.getValue()).getSequence().getSequenceno());
        });
        
        
       // if(!multipleSubsPresent){
                treetableView.getColumns().addAll(sequenceNumber,subsurfaceName,totalTraces,minTracr,maxTracr,incTracr,firstTracr,lastTracr,minFldr,maxFldr,incFldr,firstFldr,lastFldr,minTracf,maxTracf,incTracf,firstTracf,lastTracf,minEp,maxEp,incEp,firstEp,lastEp,minCdp,maxCdp,incCdp,firstCdp,lastCdp,minCdpt,maxCdpt,incCdpt,firstCdpt,lastCdpt,minTrid,maxTrid,incTrid,firstTrid,lastTrid,minDuse,maxDuse,incDuse,firstDuse,lastDuse,duse,minOffset,maxOffset,incOffset,firstOffset,lastOffset,minGelev,maxGelev,incGelev,firstGelev,lastGelev,minSelev,maxSelev,incSelev,firstSelev,lastSelev,minSdepth,maxSdepth,incSdepth,firstSdepth,lastSdepth,minSwdep,maxSwdep,incSwdep,firstSwdep,lastSwdep,minGwdep,maxGwdep,incGwdep,firstGwdep,lastGwdep,minScalel,maxScalel,incScalel,firstScalel,lastScalel,scalel,minScalco,maxScalco,incScalco,firstScalco,lastScalco,scalco,minSx,maxSx,incSx,firstSx,lastSx,minSy,maxSy,incSy,firstSy,lastSy,minGx,maxGx,incGx,firstGx,lastGx,minGy,maxGy,incGy,firstGy,lastGy,minWevel,maxWevel,incWevel,firstWevel,lastWevel,wevel,minSwevel,maxSwevel,incSwevel,firstSwevel,lastSwevel,swevel,minSut,maxSut,incSut,firstSut,lastSut,minGut,maxGut,incGut,firstGut,lastGut,minSstat,maxSstat,incSstat,firstSstat,lastSstat,minGstat,maxGstat,incGstat,firstGstat,lastGstat,minLagb,maxLagb,incLagb,firstLagb,lastLagb,minNs,maxNs,incNs,firstNs,lastNs,ns,minDt,maxDt,incDt,firstDt,lastDt,dt,minSfs,maxSfs,incSfs,firstSfs,lastSfs,minSfe,maxSfe,incSfe,firstSfe,lastSfe,minSlen,maxSlen,incSlen,firstSlen,lastSlen,slen,minStyp,maxStyp,incStyp,firstStyp,lastStyp,minStas,maxStas,incStas,firstStas,lastStas,minStae,maxStae,incStae,firstStae,lastStae,stae,minAfilf,maxAfilf,incAfilf,firstAfilf,lastAfilf,afilf,minAfils,maxAfils,incAfils,firstAfils,lastAfils,afils,minLcf,maxLcf,incLcf,firstLcf,lastLcf,lcf,minLcs,maxLcs,incLcs,firstLcs,lastLcs,lcs,minHcs,maxHcs,incHcs,firstHcs,lastHcs,minYear,maxYear,incYear,firstYear,lastYear,year,minDay,maxDay,incDay,firstDay,lastDay,day,minHour,maxHour,incHour,firstHour,lastHour,minMinute,maxMinute,incMinute,firstMinute,lastMinute,minSec,maxSec,incSec,firstSec,lastSec,minGrnors,maxGrnors,incGrnors,firstGrnors,lastGrnors,grnors,minGrnofr,maxGrnofr,incGrnofr,firstGrnofr,lastGrnofr,grnofr,minGaps,maxGaps,incGaps,firstGaps,lastGaps,minOtrav,maxOtrav,incOtrav,firstOtrav,lastOtrav,minCdpx,maxCdpx,incCdpx,firstCdpx,lastCdpx,minCdpy,maxCdpy,incCdpy,firstCdpy,lastCdpy,minInline,maxInline,incInline,firstInline,lastInline,minCrossline,maxCrossline,incCrossline,firstCrossline,lastCrossline,minShotpoint,maxShotpoint,incShotpoint,firstShotpoint,lastShotpoint,minScalsp,maxScalsp,incScalsp,firstScalsp,lastScalsp,minTcm,maxTcm,incTcm,firstTcm,lastTcm,minTid,maxTid,incTid,firstTid,lastTid,tid,minSedm,maxSedm,incSedm,firstSedm,lastSedm,minSede,maxSede,incSede,firstSede,lastSede,sede,minSmm,maxSmm,incSmm,firstSmm,lastSmm,minSme,maxSme,incSme,firstSme,lastSme,minSmu,maxSmu,incSmu,firstSmu,lastSmu,minUi1,maxUi1,incUi1,firstUi1,lastUi1,minUi2,maxUi2,incUi2,firstUi2,lastUi2,ui2,minVer,maxVer,incVer,sampleRate,recordLength,unitVer);
                
                /* }
                else{
                treetableView.getColumns().addAll(sequenceNumber,subsurfaceName,);
                
                }*/
        
        
        
        
        
        
        
        
        
     TreeItem<FullSequenceHeaders> rootOfAllseq=new TreeItem<>();
     
     rootOfAllseq.getChildren().addAll(treeSeq);
     
     treetableView.setRoot(rootOfAllseq);
     treetableView.setShowRoot(false);
     treetableView.requestFocus();
        
     
     this.setOnCloseRequest(e->{
         
         
         ((JobType5Model)model.getJob()).exitedFullHeaderLineTable();
     }
     );
     
     this.setTitle("Headers for "+model.getJob().getNameproperty().get());
        this.setScene(new Scene(this.view));
        this.show();
    }
    
    
    private void rebuild(){
          
        List<TreeItem<FullSequenceHeaders>> treeSeq=new ArrayList<>();
        for(FullSequenceHeaders seq:model.getFullSequenceHeaders()){
            TreeItem<FullSequenceHeaders> seqroot=new TreeItem<>(seq);
            for(FullSequenceHeaders sub:seq.getSubsurfaceHeaders()){
                TreeItem<FullSequenceHeaders> subitem=new TreeItem<>(sub);
                    if(!sub.getChosen()) {
                        multipleSubsPresent=true;
                    }
                seqroot.getChildren().add(subitem);
            }
            treeSeq.add(seqroot);
        }
        treetableView.getColumns().clear();
        /*if(!multipleSubsPresent){
                treetableView.getColumns().addAll(sequenceNumber,subsurfaceName,
                numberOfRuns,workflowVersion,chosen,multiple,timeStamp,tracecount,inlineMax,inlineMin,inlineInc,
                xlineMax,xlineMin,xlineInc,dugShotMax,dugShotMin,dugShotInc,dugChannelMax,dugChannelMin,
                dugChannelInc,offsetMax,offsetMin,offsetInc,cmpMax,cmpMin,cmpInc,insightVersion,deleted);
        }
        else{
            treetableView.getColumns().addAll(sequenceNumber,subsurfaceName,
                numberOfRuns,workflowVersion,chosen,multiple,timeStamp,tracecount,inlineMax,inlineMin,inlineInc,
                xlineMax,xlineMin,xlineInc,dugShotMax,dugShotMin,dugShotInc,dugChannelMax,dugChannelMin,
                dugChannelInc,offsetMax,offsetMin,offsetInc,cmpMax,cmpMin,cmpInc,insightVersion,volume,deleted);
        }
        */
        
        treetableView.getColumns().addAll(sequenceNumber,subsurfaceName,totalTraces,minTracr,maxTracr,incTracr,firstTracr,lastTracr,minFldr,maxFldr,incFldr,firstFldr,lastFldr,minTracf,maxTracf,incTracf,firstTracf,lastTracf,minEp,maxEp,incEp,firstEp,lastEp,minCdp,maxCdp,incCdp,firstCdp,lastCdp,minCdpt,maxCdpt,incCdpt,firstCdpt,lastCdpt,minTrid,maxTrid,incTrid,firstTrid,lastTrid,minDuse,maxDuse,incDuse,firstDuse,lastDuse,duse,minOffset,maxOffset,incOffset,firstOffset,lastOffset,minGelev,maxGelev,incGelev,firstGelev,lastGelev,minSelev,maxSelev,incSelev,firstSelev,lastSelev,minSdepth,maxSdepth,incSdepth,firstSdepth,lastSdepth,minSwdep,maxSwdep,incSwdep,firstSwdep,lastSwdep,minGwdep,maxGwdep,incGwdep,firstGwdep,lastGwdep,minScalel,maxScalel,incScalel,firstScalel,lastScalel,scalel,minScalco,maxScalco,incScalco,firstScalco,lastScalco,scalco,minSx,maxSx,incSx,firstSx,lastSx,minSy,maxSy,incSy,firstSy,lastSy,minGx,maxGx,incGx,firstGx,lastGx,minGy,maxGy,incGy,firstGy,lastGy,minWevel,maxWevel,incWevel,firstWevel,lastWevel,wevel,minSwevel,maxSwevel,incSwevel,firstSwevel,lastSwevel,swevel,minSut,maxSut,incSut,firstSut,lastSut,minGut,maxGut,incGut,firstGut,lastGut,minSstat,maxSstat,incSstat,firstSstat,lastSstat,minGstat,maxGstat,incGstat,firstGstat,lastGstat,minLagb,maxLagb,incLagb,firstLagb,lastLagb,minNs,maxNs,incNs,firstNs,lastNs,ns,minDt,maxDt,incDt,firstDt,lastDt,dt,minSfs,maxSfs,incSfs,firstSfs,lastSfs,minSfe,maxSfe,incSfe,firstSfe,lastSfe,minSlen,maxSlen,incSlen,firstSlen,lastSlen,slen,minStyp,maxStyp,incStyp,firstStyp,lastStyp,minStas,maxStas,incStas,firstStas,lastStas,minStae,maxStae,incStae,firstStae,lastStae,stae,minAfilf,maxAfilf,incAfilf,firstAfilf,lastAfilf,afilf,minAfils,maxAfils,incAfils,firstAfils,lastAfils,afils,minLcf,maxLcf,incLcf,firstLcf,lastLcf,lcf,minLcs,maxLcs,incLcs,firstLcs,lastLcs,lcs,minHcs,maxHcs,incHcs,firstHcs,lastHcs,minYear,maxYear,incYear,firstYear,lastYear,year,minDay,maxDay,incDay,firstDay,lastDay,day,minHour,maxHour,incHour,firstHour,lastHour,minMinute,maxMinute,incMinute,firstMinute,lastMinute,minSec,maxSec,incSec,firstSec,lastSec,minGrnors,maxGrnors,incGrnors,firstGrnors,lastGrnors,grnors,minGrnofr,maxGrnofr,incGrnofr,firstGrnofr,lastGrnofr,grnofr,minGaps,maxGaps,incGaps,firstGaps,lastGaps,minOtrav,maxOtrav,incOtrav,firstOtrav,lastOtrav,minCdpx,maxCdpx,incCdpx,firstCdpx,lastCdpx,minCdpy,maxCdpy,incCdpy,firstCdpy,lastCdpy,minInline,maxInline,incInline,firstInline,lastInline,minCrossline,maxCrossline,incCrossline,firstCrossline,lastCrossline,minShotpoint,maxShotpoint,incShotpoint,firstShotpoint,lastShotpoint,minScalsp,maxScalsp,incScalsp,firstScalsp,lastScalsp,minTcm,maxTcm,incTcm,firstTcm,lastTcm,minTid,maxTid,incTid,firstTid,lastTid,tid,minSedm,maxSedm,incSedm,firstSedm,lastSedm,minSede,maxSede,incSede,firstSede,lastSede,sede,minSmm,maxSmm,incSmm,firstSmm,lastSmm,minSme,maxSme,incSme,firstSme,lastSme,minSmu,maxSmu,incSmu,firstSmu,lastSmu,minUi1,maxUi1,incUi1,firstUi1,lastUi1,minUi2,maxUi2,incUi2,firstUi2,lastUi2,ui2,minVer,maxVer,incVer,sampleRate,recordLength,unitVer);
                
        
        
        
        
        
        
     TreeItem<FullSequenceHeaders> rootOfAllseq=new TreeItem<>();
     
     rootOfAllseq.getChildren().addAll(treeSeq);
     
     treetableView.setRoot(rootOfAllseq);
     treetableView.setShowRoot(false);
     treetableView.requestFocus();
     
    }
    
    private ChangeListener<Boolean> RELOAD_TABLE_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            rebuild();
        }
    };
    
    
    
}
