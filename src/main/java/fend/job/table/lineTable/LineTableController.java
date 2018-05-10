/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.lineTable;

import db.model.Header;
import db.model.Job;
import db.model.Log;
import db.model.Subsurface;
import db.model.Volume;
import db.model.Workflow;
import db.services.HeaderService;
import db.services.HeaderServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.LogService;
import db.services.LogServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import db.services.WorkflowService;
import db.services.WorkflowServiceImpl;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import middleware.sequences.SequenceHeaders;
import middleware.sequences.SubsurfaceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class LineTableController extends Stage{
    private final String colorisNotChosen="#f49542";
    private LineTableModel model;
    private LineTableView view;
    private SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();
    private JobService jobService=new JobServiceImpl();
    private HeaderService headerService=new HeaderServiceImpl();
    private Executor exec;  
    private WorkflowService workflowService=new WorkflowServiceImpl();
    private LogService logservice=new LogServiceImpl();
    private boolean multipleSubsPresent=false;
     @FXML
    private TreeTableView<SequenceHeaders> treetableView;

    
        TreeTableColumn<SequenceHeaders,Long>  sequenceNumber= new TreeTableColumn<>("SEQUENCE");
        TreeTableColumn<SequenceHeaders,String> subsurfaceName= new TreeTableColumn<>("SAILLINE");
        TreeTableColumn<SequenceHeaders,String>  timeStamp=new TreeTableColumn<>("TIMESTAMP");
        TreeTableColumn<SequenceHeaders,Long>  tracecount=new TreeTableColumn<>("Traces");
        TreeTableColumn<SequenceHeaders,Long>  inlineMax=new TreeTableColumn<>("inlineMax");
        TreeTableColumn<SequenceHeaders,Long>  inlineMin=new TreeTableColumn<>("inlineMin");
        TreeTableColumn<SequenceHeaders,Long>  inlineInc=new TreeTableColumn<>("inlineInc");
        TreeTableColumn<SequenceHeaders,Long>  xlineMax=new TreeTableColumn<>("xlineMax");
        TreeTableColumn<SequenceHeaders,Long>  xlineMin =new TreeTableColumn<>("xlineMin");
        TreeTableColumn<SequenceHeaders,Long>  xlineInc =new TreeTableColumn<>("xlineInc");
        TreeTableColumn<SequenceHeaders,Long>  dugShotMax=new TreeTableColumn<>("dugShotMax");
        TreeTableColumn<SequenceHeaders,Long>  dugShotMin=new TreeTableColumn<>("dugShotMin");
        TreeTableColumn<SequenceHeaders,Long>  dugShotInc=new TreeTableColumn<>("dugShotInc");
        TreeTableColumn<SequenceHeaders,Long>  dugChannelMax=new TreeTableColumn<>("dugChannelMax");
        TreeTableColumn<SequenceHeaders,Long>  dugChannelMin=new TreeTableColumn<>("dugChannelMin");
        TreeTableColumn<SequenceHeaders,Long>  dugChannelInc=new TreeTableColumn<>("dugChannelInc");
        TreeTableColumn<SequenceHeaders,Long>  offsetMax=new TreeTableColumn<>("offsetMax");
        TreeTableColumn<SequenceHeaders,Long>  offsetMin=new TreeTableColumn<>("offsetMin");
        TreeTableColumn<SequenceHeaders,Long>  offsetInc=new TreeTableColumn<>("offsetInc");
        TreeTableColumn<SequenceHeaders,Long>  cmpMax=new TreeTableColumn<>("cmpMax");
        TreeTableColumn<SequenceHeaders,Long>  cmpMin=new TreeTableColumn<>("cmpMin");
        TreeTableColumn<SequenceHeaders,Long>  cmpInc=new TreeTableColumn<>("cmpInc");
        TreeTableColumn<SequenceHeaders,String>  insightVersion=new TreeTableColumn<>("insightVersion");
        TreeTableColumn<SequenceHeaders,Long>  numberOfRuns=new TreeTableColumn<>("numberOfRuns");
        TreeTableColumn<SequenceHeaders,Long> workflowVersion=new TreeTableColumn<>("workflowVersion");
        TreeTableColumn<SequenceHeaders,Boolean>  multiple=new TreeTableColumn<>("multiple");
        TreeTableColumn<SequenceHeaders,Boolean>  chosen=new TreeTableColumn<>("chosen");
        TreeTableColumn<SequenceHeaders,Boolean>  deleted=new TreeTableColumn<>("deleted");
        TreeTableColumn<SequenceHeaders,String> volume=new TreeTableColumn<>("volume");
     
     
     
    void setModel(LineTableModel item) {
        
        model=item;
        model.reloadTableProperty().addListener(RELOAD_TABLE_LISTENER);
        exec=Executors.newCachedThreadPool(r->{
            Thread t=new Thread(r);
            t.setDaemon(true);
            return t;
        });
    }

    void setView(LineTableView vw) {
        view=vw;
        
        treetableView.setRowFactory(ttv->{
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
                    /*if(contextMenu.getItems().contains(chooseThisHeader)){
                    contextMenu.getItems().remove(chooseThisHeader);
                    }*/
                    setContextMenu(contextMenu);
                }
                
             }
         };
         
         chooseThisHeader.setOnAction(evt->{
             Long id=row.getItem().getId();
             Header h=headerService.getHeader(id);
             h.setChosen(true);
             headerService.updateHeader(h.getHeaderId(), h);
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
             Header h=headerService.getHeader(id);
             h.setChosen(false);
             headerService.updateHeader(h.getHeaderId(), h);
             
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
                        Header h=headerService.getHeader(id);
                        //Set<Log> logs=h.getLogs();
                        Set<Log> logs=new HashSet<>(logservice.getLogsFor(h));

                        for(Log l:logs){
                           VersionLogsModel vlm=new VersionLogsModel(l.getVersion(), l.getTimestamp(), l.getLogpath());
                           versionModels.add(vlm);
                        }
                   
                        return "Finished extracting logs for : "+h.getHeaderId();
                    
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
                        Header h=headerService.getHeader(id);
                        Volume v=h.getVolume();
                        List<Workflow> workflows=workflowService.getWorkFlowsFor(v);
                        System.out.println(".call(): got "+workflows.size()+" for headerID: "+h.getHeaderId()+" vol: "+v.getId());
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
                   
                        return "Finished extracting logs for : "+h.getHeaderId();
                    
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
        });
        
        
        
        
        //sequenceNumber.setCellValueFactory(new TreeItemPropertyValueFactory<>("sequenceNumber"));
        sequenceNumber.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SequenceHeaders, Long>, ObservableValue<Long>>() {
            @Override
            public ObservableValue<Long> call(TreeTableColumn.CellDataFeatures<SequenceHeaders, Long> param) {
                return new SimpleLongProperty(param.getValue().getValue().getSequence().getSequenceno()).asObject();
            }
        });
        //subsurfaceName.setCellValueFactory(new TreeItemPropertyValueFactory<>("subsurface"));
        subsurfaceName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SequenceHeaders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SequenceHeaders, String> param) {
                return new SimpleStringProperty(param.getValue().getValue().getSubsurfaceName());
            }
        });
        timeStamp.setCellValueFactory(new TreeItemPropertyValueFactory<>("timeStamp"));
        tracecount.setCellValueFactory(new TreeItemPropertyValueFactory<>("traceCount"));
        inlineMax.setCellValueFactory(new TreeItemPropertyValueFactory<>("inlineMax"));
        inlineMin.setCellValueFactory(new TreeItemPropertyValueFactory<>("inlineMin"));
        inlineInc.setCellValueFactory(new TreeItemPropertyValueFactory<>("inlineInc"));
        xlineMax.setCellValueFactory(new TreeItemPropertyValueFactory<>("xlineMax"));
        xlineMin.setCellValueFactory(new TreeItemPropertyValueFactory<>("xlineMin"));
        xlineInc.setCellValueFactory(new TreeItemPropertyValueFactory<>("xlineInc"));
        dugShotMax.setCellValueFactory(new TreeItemPropertyValueFactory<>("dugShotMax"));
        dugShotMin.setCellValueFactory(new TreeItemPropertyValueFactory<>("dugShotMin"));
        dugShotInc.setCellValueFactory(new TreeItemPropertyValueFactory<>("dugShotInc"));
        dugChannelMax.setCellValueFactory(new TreeItemPropertyValueFactory<>("dugChannelMax"));
        dugChannelMin.setCellValueFactory(new TreeItemPropertyValueFactory<>("dugChannelMin"));
        dugChannelInc.setCellValueFactory(new TreeItemPropertyValueFactory<>("dugChannelInc"));
        offsetMax.setCellValueFactory(new TreeItemPropertyValueFactory<>("offsetMax"));
        offsetMin.setCellValueFactory(new TreeItemPropertyValueFactory<>("offsetMin"));
        offsetInc.setCellValueFactory(new TreeItemPropertyValueFactory<>("offsetInc"));
        cmpMax.setCellValueFactory(new TreeItemPropertyValueFactory<>("cmpMax"));
        cmpMin.setCellValueFactory(new TreeItemPropertyValueFactory<>("cmpMin"));
        cmpInc.setCellValueFactory(new TreeItemPropertyValueFactory<>("cmpInc"));
        insightVersion.setCellValueFactory(new TreeItemPropertyValueFactory<>("insight"));
        numberOfRuns.setCellValueFactory(new TreeItemPropertyValueFactory<>("numberOfRuns"));
        workflowVersion.setCellValueFactory(new TreeItemPropertyValueFactory<>("workflow"));
        multiple.setCellValueFactory(new TreeItemPropertyValueFactory<>("multiple"));
        chosen.setCellValueFactory(new TreeItemPropertyValueFactory<>("chosen"));
        deleted.setCellValueFactory(new TreeItemPropertyValueFactory<>("deleted"));
        
        chosen.setCellFactory((TreeTableColumn<SequenceHeaders,Boolean> p)->{
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
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        List<TreeItem<SequenceHeaders>> treeSeq=new ArrayList<>();
        for(SequenceHeaders seq:model.getSequenceHeaders()){
            TreeItem<SequenceHeaders> seqroot=new TreeItem<>(seq);
            for(SubsurfaceHeaders sub:seq.getSubsurfaceHeaders()){
                TreeItem<SequenceHeaders> subitem=new TreeItem<>(sub);
                    if(!sub.getChosen()) {
                        multipleSubsPresent=true;
                    }
                seqroot.getChildren().add(subitem);
            }
            treeSeq.add(seqroot);
        }
        
        if(!multipleSubsPresent){
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
        
        
        
        
        
        
        
        
        
     TreeItem<SequenceHeaders> rootOfAllseq=new TreeItem<>();
     
     rootOfAllseq.getChildren().addAll(treeSeq);
     
     treetableView.setRoot(rootOfAllseq);
     treetableView.setShowRoot(false);
     treetableView.requestFocus();
        
     
     this.setOnCloseRequest(e->{
         
         
         model.getJob().exitedLineTable();
     }
     );
     
     this.setTitle("Headers for "+model.getJob().getNameproperty().get());
        this.setScene(new Scene(this.view));
        this.show();
    }
    
    
    private void rebuild(){
          
        List<TreeItem<SequenceHeaders>> treeSeq=new ArrayList<>();
        for(SequenceHeaders seq:model.getSequenceHeaders()){
            TreeItem<SequenceHeaders> seqroot=new TreeItem<>(seq);
            for(SubsurfaceHeaders sub:seq.getSubsurfaceHeaders()){
                TreeItem<SequenceHeaders> subitem=new TreeItem<>(sub);
                    if(!sub.getChosen()) {
                        multipleSubsPresent=true;
                    }
                seqroot.getChildren().add(subitem);
            }
            treeSeq.add(seqroot);
        }
        treetableView.getColumns().clear();
        if(!multipleSubsPresent){
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
        
        
        
        
        
        
        
        
        
     TreeItem<SequenceHeaders> rootOfAllseq=new TreeItem<>();
     
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
