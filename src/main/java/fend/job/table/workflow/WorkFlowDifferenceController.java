/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.workflow;

import db.model.Workflow;
import db.services.WorkflowService;
import db.services.WorkflowServiceImpl;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import middleware.dugex.DugioScripts;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkFlowDifferenceController extends Stage{
    WorkFlowDifferenceModel model;
    WorkFlowDifferenceView view;
    Map<Long,Workflow> mapOfVersionsVersusWorkflows;
    DugioScripts dugioScripts=new DugioScripts();
    Long lhs;
    Long rhs;
    Executor exec;
    String contents;
    
    @FXML
    private Label currentHeaderWfVersionLabel;

    @FXML
    private ComboBox<Long> lhsVersionComboBox;

    @FXML
    private ComboBox<Long> rhsVersionComboBox;

    @FXML
    private TextArea differenceTextArea;
    
    
    
    void setModel(WorkFlowDifferenceModel item) {
        model=item;
      //  List<Workflow> workflows=model.getWorkflows();
        mapOfVersionsVersusWorkflows=model.getMapOfVersionsVersusWorkflows();
        ObservableList<Long> lhsvalues=model.getLhsObs();
        lhsVersionComboBox.getItems().addAll(lhsvalues);
        
        ObservableList<Long> rhsvalues=model.getRhsObs();
        rhsVersionComboBox.getItems().addAll(rhsvalues);
        
        Workflow currentHdrWf=model.getLhsWorkflow();
        String currentVHT="version used for "+model.
                getChosenHdr().
                getSubsurface().
                getSubsurface()+
                " is : "+
                currentHdrWf.
                        getWfversion();
        currentHeaderWfVersionLabel.setText(currentVHT);
        
        
        lhsVersionComboBox.setValue(currentHdrWf.getWfversion());
        lhsVersionComboBox.valueProperty().addListener(LHS_DIFFERENCE_LISTENER);
        rhsVersionComboBox.valueProperty().addListener(RHS_DIFFERENCE_LISTENER);
        
        exec=Executors.newCachedThreadPool(r->{
            Thread t=new Thread(r);
            t.setDaemon(true);
            return t;
        });
        
    }

    void setView(WorkFlowDifferenceView vw) {
        view=vw;
        this.setScene(new Scene(this.view));
        showAndWait();
    }
    
    
    /**
     * difference listener
     **/
    
    private ChangeListener<Number> LHS_DIFFERENCE_LISTENER=new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            lhs=newValue.longValue();
            
            Task<Void> ltask=new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    contents=calculateDiff(lhs, rhs);
                    return null; 
                }
            };
            ltask.setOnSucceeded(e->{
                differenceTextArea.setText(contents);
            });
            
            ltask.setOnRunning(e->{
                differenceTextArea.setText("..hang on...");
            });
            
            ltask.setOnFailed(e->{
                ltask.getException().printStackTrace();
            });
            
            exec.execute(ltask);
        }

        
    };
    
    
    private ChangeListener<Number> RHS_DIFFERENCE_LISTENER=new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            rhs=newValue.longValue();
             Task<Void> ltask=new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    contents=calculateDiff(lhs, rhs);
                    return null; 
                }
            };
            ltask.setOnSucceeded(e->{
                differenceTextArea.setText(contents);
            });
            
            ltask.setOnRunning(e->{
                differenceTextArea.setText("..hang on...");
            });
         
            ltask.setOnFailed(e->{
                ltask.getException().printStackTrace();
            });
            
            exec.execute(ltask);
        }
    };
    
    
    private String calculateDiff(Long lhs, Long rhs) throws IOException {
       File lhsWk=File.createTempFile("lhsWk", ".sh");
       Workflow lhsWorkflow=mapOfVersionsVersusWorkflows.get(lhs);
       BufferedWriter bw = new BufferedWriter(new FileWriter(lhsWk));
       bw.write(lhsWorkflow.getContents());
       bw.close();
       
       
       File rhsWk=File.createTempFile("rhsWk", ".sh");
       Workflow rhsWorkflow=mapOfVersionsVersusWorkflows.get(rhs);
       BufferedWriter brw = new BufferedWriter(new FileWriter(rhsWk));
       brw.write(rhsWorkflow.getContents());
       brw.close();
       
       
       
       
        String contents=new String("");
            Process process=new ProcessBuilder(dugioScripts.getWorkflowDifference().getAbsolutePath(),lhsWk.getAbsolutePath(),rhsWk.getAbsolutePath()).start();
            InputStream is = process.getInputStream();
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader br=new BufferedReader(isr);
            while((contents=br.readLine())!=null){};
            
            System.out.println("fend.job.table.workflow.WorkFlowDifferenceController.calculateDiff() contents "+contents);
                
            lhsWk.deleteOnExit();
            rhsWk.deleteOnExit();
        return contents;
    }
}
