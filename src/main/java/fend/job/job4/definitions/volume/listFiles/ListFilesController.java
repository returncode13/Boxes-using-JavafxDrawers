/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job4.definitions.volume.listFiles;


import db.model.Job;
import db.model.NodeProperty;
import db.model.NodePropertyValue;
import db.model.NodeType;
import db.model.PropertyType;
import db.services.NodePropertyService;
import db.services.NodePropertyServiceImpl;
import db.services.NodePropertyValueService;
import db.services.NodePropertyValueServiceImpl;
import db.services.NodeTypeService;
import db.services.NodeTypeServiceImpl;
import db.services.PropertyTypeService;
import db.services.PropertyTypeServiceImpl;
import fend.job.job0.property.JobModelProperty;
import fend.volume.volume4.Volume4;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class ListFilesController extends Stage{

   
    ListFilesModel model;
    ListFilesNode node;
    ObservableList<Text> textList;
    Integer from;
    Integer to;
    
    @FXML
    private ListView<String> fileListView;

    @FXML
    private TextFlow textFlow;

    @FXML
    private TextField seqFromTF;

    @FXML
    private TextField seqToTF;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;
    
    
    private PropertyTypeService propertyTypeService=new PropertyTypeServiceImpl();
    private NodePropertyService nodePropertyService=new NodePropertyServiceImpl();
    private NodeTypeService nodeTypeService=new NodeTypeServiceImpl();
    private NodePropertyValueService nodePropertyValueService=new NodePropertyValueServiceImpl();

    @FXML
    void handleOK(ActionEvent event) {
            model.setFrom(from);
            model.setTo(to);
           
             System.out.println("fend.job.job4.definitions.volume.listFiles.ListFilesController.handleOK(): setting values inside model of From: "+from+" : TO: "+to);
             List<JobModelProperty> jobProps=((Volume4)this.model.getVolumeModel()).getParentJob().getJobProperties();
             for (Iterator<JobModelProperty> iterator = jobProps.iterator(); iterator.hasNext();) {
            JobModelProperty jp = iterator.next();
            if(jp.getPropertyName().equals("from")){
                jp.setPropertyValue(new String(""+this.from));
            }
            if(jp.getPropertyName().equals("to")){
                jp.setPropertyValue(new String(""+this.to));
            }
        }
             NodeType textNodeType=nodeTypeService.getNodeTypeObjForType(this.model.getVolumeModel().getType());
             PropertyType fromProperty=propertyTypeService.getPropertyTypeObjForName("from");
             PropertyType toProperty=propertyTypeService.getPropertyTypeObjForName("to");
             
             NodeProperty fromNodeProperty=nodePropertyService.getNodeProperty(textNodeType, fromProperty);
             NodeProperty toNodeProperty=nodePropertyService.getNodeProperty(textNodeType, toProperty);
             Job job=this.model.getVolumeModel().getParentJob().getDatabaseJob();
             
             NodePropertyValue fromNodePropertyValue=nodePropertyValueService.getNodePropertyValueFor(job, fromNodeProperty);
             fromNodePropertyValue.setValue(""+from);
             
             NodePropertyValue toNodePropertyValue=nodePropertyValueService.getNodePropertyValueFor(job, toNodeProperty);
             toNodePropertyValue.setValue(""+to);
             
             nodePropertyValueService.updateNodePropertyValue(toNodePropertyValue.getIdNodePropertyValue(), toNodePropertyValue);
             nodePropertyValueService.updateNodePropertyValue(fromNodePropertyValue.getIdNodePropertyValue(), fromNodePropertyValue);
             
             
             
             
             
             close();
    }

    @FXML
    void onCancel(ActionEvent event) {
            close();
    }

    @FXML
    void seqFromKeyReleased(KeyEvent event) {
        try{
        from=Integer.valueOf(seqFromTF.getText());
        //from--;               //BB
        }catch(NumberFormatException nfe){
            from=null;
            List<Node> nodL=textFlow.getChildren();
            for (Node node1 : nodL) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
        }
        
        
        System.out.println("fend.session.node.volumes.type4.listFiles.ListFilesController.seqFromKeyReleased()  from: "+from);
        
        if(from!=null && to==null){
            List<Node> nodL=textFlow.getChildren();
            for (Node node1 : nodL) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
            
            ((Text)textFlow.getChildren().get(from)).setFill(Color.PURPLE);
        }
        
        if(from!=null && to!=null){
            List<Node> nodL=textFlow.getChildren();
            for (Node node1 : nodL) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
       
        
        if(to>=from){
            
            int i=from;
            while(i<=to){
                try{
                    ((Text)textFlow.getChildren().get(i++)).setFill(Color.PURPLE);
                }catch(ArrayIndexOutOfBoundsException ai){
                    List<Node> nodL2=textFlow.getChildren();
                    for (Node node1 : nodL2) {
                        if(node1 instanceof Text){
                            ((Text) node1).setFill(Color.BLACK);
                        }
                    }
                }
                
            }
                  
            
            
        }
        
        if(to<from){
            
           // ((Text)textFlow.getChildren().get(from)).setFill(Color.PURPLE);
            System.out.println("fend.session.node.volumes.type4.listFiles.ListFilesController.seqToKeyReleased(): \"to\" value cannot be less than \"from\"");
            List<Node> nodLl=textFlow.getChildren();
            for (Node node1 : nodLl) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
        }
        
         }
        
        
    }
    

    @FXML
    void seqToKeyReleased(KeyEvent event) {
        try{
        to=Integer.valueOf(seqToTF.getText());
        //to--;   //BB
        }catch(NumberFormatException nfe){
            to=null;
            List<Node> nodL=textFlow.getChildren();
            for (Node node1 : nodL) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
        }
       
        if(to!=null && from==null){
            List<Node> nodL=textFlow.getChildren();
            for (Node node1 : nodL) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
            
            ((Text)textFlow.getChildren().get(to)).setFill(Color.PURPLE);
        }
        
        
        if(to!=null && from!=null){
        System.out.println("fend.session.node.volumes.type4.listFiles.ListFilesController.seqToKeyReleased() to: "+to);
        List<Node> tnodes=new ArrayList<>();
        /*Node nodeFrom=textFlow.getChildren().get(from);
        Node nodeTo=textFlow.getChildren().get(to);*/
        
        List<Node> nodL=textFlow.getChildren();
            for (Node node1 : nodL) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
            
        
        if(to>=from){
            
            int i=from;
            while(i<=to){
                try{
                    ((Text)textFlow.getChildren().get(i++)).setFill(Color.PURPLE);
                }catch(ArrayIndexOutOfBoundsException ai){
                    List<Node> nodL2=textFlow.getChildren();
                    for (Node node1 : nodL2) {
                        if(node1 instanceof Text){
                            ((Text) node1).setFill(Color.BLACK);
                        }
                    }
                }
            }
                  
            
            
        }
        if(to<from)
        {
           // ((Text)textFlow.getChildren().get(to)).setFill(Color.PURPLE);
            List<Node> nodLl=textFlow.getChildren();
            for (Node node1 : nodLl) {
                if(node1 instanceof Text){
                    ((Text) node1).setFill(Color.BLACK);
                }
            }
            System.out.println("fend.session.node.volumes.type4.listFiles.ListFilesController.seqToKeyReleased(): \"to\" value cannot be less than \"from\"");
        }
        }

    }
    
    
    
    void setModel(ListFilesModel m) {
        this.model=m;
        fileListView.setItems(model.getFileobs());
        List<String> charsForTF=model.getCharsForTextFlow();
        textList=FXCollections.observableArrayList();
        for (String s : charsForTF) {
            textList.add(new Text(s));
            
        }
        textFlow.getChildren().addAll(textList);
        
        
    }

    void setView(ListFilesNode aThis) {
        this.node=aThis;
        this.setTitle("Determine sequence from file name");
        this.setScene(new Scene(node));
        this.showAndWait();
    }

    
    
    
    
    
}
