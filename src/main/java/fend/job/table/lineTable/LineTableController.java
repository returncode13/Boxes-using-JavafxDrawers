/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.lineTable;

import com.jfoenix.controls.JFXTreeTableView;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import middleware.sequences.SequenceHeaders;
import middleware.sequences.SubsurfaceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class LineTableController extends Stage{
    LineTableModel model;
    LineTableView view;
    
    
     @FXML
    private JFXTreeTableView<SequenceHeaders> treetableView;

    void setModel(LineTableModel item) {
        model=item;
    }

    void setView(LineTableView vw) {
        view=vw;
        
        
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
        
        
        sequenceNumber.setCellValueFactory(new TreeItemPropertyValueFactory<>("sequenceNumber"));
        subsurfaceName.setCellValueFactory(new TreeItemPropertyValueFactory<>("subsurface"));
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
        
        
        /*multiple.setCellFactory((TreeTableColumn<SequenceHeaders,Boolean> p)->{
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
        
        
        
        
        
        treetableView.getColumns().addAll(sequenceNumber,subsurfaceName,
                numberOfRuns,workflowVersion,chosen,multiple,timeStamp,tracecount,inlineMax,inlineMin,inlineInc,
                xlineMax,xlineMin,xlineInc,dugShotMax,dugShotMin,dugShotInc,dugChannelMax,dugChannelMin,
                dugChannelInc,offsetMax,offsetMin,offsetInc,cmpMax,cmpMin,cmpInc,insightVersion);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        List<TreeItem<SequenceHeaders>> treeSeq=new ArrayList<>();
        for(SequenceHeaders seq:model.getSequenceHeaders()){
            TreeItem<SequenceHeaders> seqroot=new TreeItem<>(seq);
            for(SubsurfaceHeaders sub:seq.getSubsurfaceHeaders()){
                TreeItem<SequenceHeaders> subitem=new TreeItem<>(sub);
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
