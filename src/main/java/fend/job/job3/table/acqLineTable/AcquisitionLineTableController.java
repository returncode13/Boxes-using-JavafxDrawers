/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job3.table.acqLineTable;

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
import javafx.scene.control.TreeTableView;
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
public class AcquisitionLineTableController extends Stage{
    private AcqLineTableModel model;
    private AcqLineTableView view;
    private SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();
    private JobService jobService=new JobServiceImpl();
    private HeaderService headerService=new HeaderServiceImpl();
            
    
     @FXML
    private TreeTableView<AcquisitionSequenceHeaders> treetableView;

    void setModel(AcqLineTableModel item) {
        model=item;
    }

    void setView(AcqLineTableView vw) {
        view=vw;
        /*
        treetableView.setRowFactory(ttv->{
        ContextMenu contextMenu = new ContextMenu();
        //ContextMenu contextMenuOverride = new ContextMenu();
        MenuItem showLogsMenuItem=new MenuItem("option1-Impl");
        MenuItem showWorkFlowVersion=new MenuItem("option2-Impl");
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
        
        setContextMenu(contextMenu);
        }
        
        }
        };
        
        chooseThisHeader.setOnAction(evt->{
        Long id=row.getItem().getId();
        
        Header h=headerService.getHeader(id);
        h.setChosen(true);
        headerService.updateHeader(h.getHeaderId(), h);
        
        });
        
        
        return row;
        });
        */
        
        
        TreeTableColumn<AcquisitionSequenceHeaders,Long>  sequenceNumber= new TreeTableColumn<>("Sequence");
        TreeTableColumn<AcquisitionSequenceHeaders,String> subsurfaceName= new TreeTableColumn<>("Sail Line");
        subsurfaceName.setMinWidth(100);
        TreeTableColumn<AcquisitionSequenceHeaders,String>  cable=new TreeTableColumn<>("cable");
        TreeTableColumn<AcquisitionSequenceHeaders,Long>  firstChannel=new TreeTableColumn<>("first Channel");
        TreeTableColumn<AcquisitionSequenceHeaders,Long>  lastChannel=new TreeTableColumn<>("last Channel");
        TreeTableColumn<AcquisitionSequenceHeaders,Long>  gun=new TreeTableColumn<>("gun");
        TreeTableColumn<AcquisitionSequenceHeaders,Long>  firstFFID=new TreeTableColumn<>("first FFID");
        TreeTableColumn<AcquisitionSequenceHeaders,Long>  lastFFID=new TreeTableColumn<>("last FFID");
        TreeTableColumn<AcquisitionSequenceHeaders,Long>  firstShot =new TreeTableColumn<>("first Shot");
        TreeTableColumn<AcquisitionSequenceHeaders,Long>  lastShot =new TreeTableColumn<>("last Shot");
        TreeTableColumn<AcquisitionSequenceHeaders,Long>  firstGoodFFID=new TreeTableColumn<>("first Good FFID");
        TreeTableColumn<AcquisitionSequenceHeaders,Long>  lastGoodFFID=new TreeTableColumn<>("last Good FFID");
        TreeTableColumn<AcquisitionSequenceHeaders,Long>  fgsp=new TreeTableColumn<>("fgsp");
        TreeTableColumn<AcquisitionSequenceHeaders,Long>  lgsp=new TreeTableColumn<>("lgsp");
        
        
        //sequenceNumber.setCellValueFactory(new TreeItemPropertyValueFactory<>("sequenceNumber"));
        sequenceNumber.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AcquisitionSequenceHeaders, Long>, ObservableValue<Long>>() {
            @Override
            public ObservableValue<Long> call(TreeTableColumn.CellDataFeatures<AcquisitionSequenceHeaders, Long> param) {
                return new SimpleLongProperty(param.getValue().getValue().getSequence().getSequenceno()).asObject();
            }
        });
        //subsurfaceName.setCellValueFactory(new TreeItemPropertyValueFactory<>("subsurface"));
        subsurfaceName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AcquisitionSequenceHeaders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AcquisitionSequenceHeaders, String> param) {
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
       
        
        
        
        
        
        treetableView.getColumns().addAll(sequenceNumber,subsurfaceName,fgsp,lgsp,firstGoodFFID,lastGoodFFID,
                                            firstChannel,lastChannel,gun,cable,firstShot,lastShot,firstFFID,
                                            lastFFID);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        List<TreeItem<AcquisitionSequenceHeaders>> treeSeq=new ArrayList<>();
        for(SequenceHeaders seq:model.getSequenceHeaders()){
            TreeItem<AcquisitionSequenceHeaders> seqroot=new TreeItem((AcquisitionSequenceHeaders)seq);
            for(AcquisitionSubsurfaceHeaders sub:((AcquisitionSequenceHeaders)seq).getAcquisitionSubsurfaceHeaders()){
                TreeItem<AcquisitionSequenceHeaders> subitem=new TreeItem<>((AcquisitionSubsurfaceHeaders)sub);
                seqroot.getChildren().add(subitem);
            }
            treeSeq.add(seqroot);
        }
        
     
        
        
        
        
        
        
        
        
        
     TreeItem<AcquisitionSequenceHeaders> rootOfAllseq=new TreeItem<>();
     
     rootOfAllseq.getChildren().addAll(treeSeq);
     
     treetableView.setRoot(rootOfAllseq);
     treetableView.setShowRoot(false);
     treetableView.requestFocus();
        
     
     this.setTitle("Headers for "+model.getJob().getNameproperty().get());
        this.setScene(new Scene(this.view));
        this.show();
    }
}
