/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job3.table.lineTable;

import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import db.model.Header;
import db.model.Job;
import db.model.Subsurface;
import db.services.HeaderService;
import db.services.HeaderServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import middleware.sequences.SequenceHeaders;
import middleware.sequences.SubsurfaceHeaders;
import middleware.sequences.acquisition.AcquisitionSequenceHeaders;
import middleware.sequences.acquisition.AcquisitionSubsurfaceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class LineTableController extends Stage{
    private LineTableModel model;
    private LineTableView view;
    private SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();
    private JobService jobService=new JobServiceImpl();
    private HeaderService headerService=new HeaderServiceImpl();
            
    
     @FXML
    private JFXTreeTableView<SequenceHeaders> treetableView;

    void setModel(LineTableModel item) {
        model=item;
    }

    void setView(LineTableView vw) {
        view=vw;
        
        treetableView.setRowFactory(ttv->{
            ContextMenu contextMenu = new ContextMenu();
         //ContextMenu contextMenuOverride = new ContextMenu();
         MenuItem showLogsMenuItem=new MenuItem("Logs");
         MenuItem showWorkFlowVersion=new MenuItem("Workflow Versions");
         MenuItem chooseThisHeader=new MenuItem("Choose This Subsurface");
         MenuItem showOverride=new MenuItem("Override Doubt");
         contextMenu.getItems().add(showLogsMenuItem);
         contextMenu.getItems().add(showWorkFlowVersion); 
         
         JFXTreeTableRow<SequenceHeaders> row=new JFXTreeTableRow<SequenceHeaders>(){
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
                }else if(item!=null && item.getChosen()){
                    /*if(contextMenu.getItems().contains(chooseThisHeader)){
                    contextMenu.getItems().remove(chooseThisHeader);
                    }*/
                    setContextMenu(contextMenu);
                }
                
             }
         };
         
         chooseThisHeader.setOnAction(evt->{
             Long id=row.getItem().getId();
             /*Subsurface s=row.getItem().getSubsurface();
             Long jobId=model.getJob().getId();
             Job job=jobService.getJob(jobId);
             Subsurface sub=subsurfaceService.getSubsurface(id);*/
             Header h=headerService.getHeader(id);
             h.setChosen(true);
             headerService.updateHeader(h.getHeaderId(), h);
             
         });
         
         
         return row;
        });
        
        
        
        TreeTableColumn<SequenceHeaders,Long>  sequenceNumber= new TreeTableColumn<>("SEQUENCE");
        TreeTableColumn<SequenceHeaders,String> subsurfaceName= new TreeTableColumn<>("SAILLINE");
        TreeTableColumn<SequenceHeaders,String>  cable=new TreeTableColumn<>("cable");
        TreeTableColumn<SequenceHeaders,Long>  firstChannel=new TreeTableColumn<>("firstChannel");
        TreeTableColumn<SequenceHeaders,Long>  lastChannel=new TreeTableColumn<>("lastChannel");
        TreeTableColumn<SequenceHeaders,Long>  gun=new TreeTableColumn<>("gun");
        TreeTableColumn<SequenceHeaders,Long>  firstFFID=new TreeTableColumn<>("firstFFID");
        TreeTableColumn<SequenceHeaders,Long>  lastFFID=new TreeTableColumn<>("lastFFID");
        TreeTableColumn<SequenceHeaders,Long>  firstShot =new TreeTableColumn<>("firstShot");
        TreeTableColumn<SequenceHeaders,Long>  lastShot =new TreeTableColumn<>("lastShot");
        TreeTableColumn<SequenceHeaders,Long>  firstGoodFFID=new TreeTableColumn<>("firstGoodFFID");
        TreeTableColumn<SequenceHeaders,Long>  lastGoodFFID=new TreeTableColumn<>("lastGoodFFID");
        TreeTableColumn<SequenceHeaders,Long>  fgsp=new TreeTableColumn<>("fgsp");
        TreeTableColumn<SequenceHeaders,Long>  lgsp=new TreeTableColumn<>("lgsp");
        /*TreeTableColumn<SequenceHeaders,Long>  dugChannelMin=new TreeTableColumn<>("dugChannelMin");
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
        */
        
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
        cable.setCellValueFactory(new TreeItemPropertyValueFactory<>("cable"));
        firstChannel.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstChannel"));
        lastChannel.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastChannel"));
        gun.setCellValueFactory(new TreeItemPropertyValueFactory<>("gun"));
        firstFFID.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstFFID"));
        lastFFID.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastFFID"));
        firstShot.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstShot"));
        lastShot.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastShot"));
        firstGoodFFID.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstGoodFFID"));
        lastGoodFFID.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastGoodFFID"));
        fgsp.setCellValueFactory(new TreeItemPropertyValueFactory<>("fgsp"));
        lgsp.setCellValueFactory(new TreeItemPropertyValueFactory<>("lgsp"));
        /*dugChannelMin.setCellValueFactory(new TreeItemPropertyValueFactory<>("dugChannelMin"));
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
        
        
        multiple.setCellFactory((TreeTableColumn<SequenceHeaders,Boolean> p)->{
        TreeTableCell cell=new TreeTableCell<SequenceHeaders,Boolean>(){
        
        @Override
        protected void updateItem(Boolean item, boolean empty){
        super.updateItem(item, empty);
        TreeTableRow<SequenceHeaders> seqTreeRow=getTreeTableRow();
        if(item==null || empty ){
        setText(null);
        seqTreeRow.setStyle("");
        setStyle("");
        }else{
        seqTreeRow.setStyle(item ? "-fx-background-color:orange":"");
        setText(item.toString());
        setStyle(item? "-fx-background-color:red":"");
        }
        }
        };
        return cell;
        });
        */
        
        
        
        
        
        treetableView.getColumns().addAll(sequenceNumber,subsurfaceName,fgsp,lgsp,firstGoodFFID,lastGoodFFID,
                                            firstChannel,lastChannel,gun,cable,firstShot,lastShot,firstFFID,
                                            lastFFID);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        List<TreeItem<SequenceHeaders>> treeSeq=new ArrayList<>();
        for(SequenceHeaders seq:model.getSequenceHeaders()){
            TreeItem<SequenceHeaders> seqroot=new TreeItem((AcquisitionSequenceHeaders)seq);
            for(AcquisitionSubsurfaceHeaders sub:((AcquisitionSequenceHeaders)seq).getAcquisitionSubsurfaceHeaders()){
                TreeItem<SequenceHeaders> subitem=new TreeItem<>((AcquisitionSubsurfaceHeaders)sub);
                seqroot.getChildren().add(subitem);
            }
            treeSeq.add(seqroot);
        }
        
     
        
        
        
        
        
        
        
        
        
     TreeItem<SequenceHeaders> rootOfAllseq=new TreeItem<>();
     
     rootOfAllseq.getChildren().addAll(treeSeq);
     
     treetableView.setRoot(rootOfAllseq);
     treetableView.setShowRoot(false);
     treetableView.requestFocus();
        
     
     this.setTitle("Headers for "+model.getJob().getNameproperty().get());
        this.setScene(new Scene(this.view));
        this.showAndWait();
    }
}
