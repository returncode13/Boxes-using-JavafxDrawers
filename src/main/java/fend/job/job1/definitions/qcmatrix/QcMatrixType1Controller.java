/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job1.definitions.qcmatrix;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import db.model.Job;
import db.model.QcMatrixRow;
import db.model.QcType;
import db.model.Workspace;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.QcMatrixRowServiceImpl;
import db.services.QcTypeService;
import db.services.QcTypeServiceImpl;
//import fend.job.definitions.qcmatrix.qcmatrixrow.QcMatrixRowType1Model;

//import fend.job.definitions.qcmatrix.qctype.addQcType.AddQcTypeModel;
//import fend.job.definitions.qcmatrix.qctype.addQcType.AddQcTypeNode;
import fend.job.job0.JobType0Model;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import db.services.QcMatrixRowService;
import db.services.WorkspaceService;
import db.services.WorkspaceServiceImpl;
import fend.job.job1.definitions.qcmatrix.qcmatrixrow.QcMatrixRowType1Model;
import fend.job.job1.definitions.qcmatrix.qctype.addQcType.AddQcTypeModel;
import fend.job.job1.definitions.qcmatrix.qctype.addQcType.AddQcTypeNode;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixType1Controller {
    private QcMatrixType1Model qcMatrixModel;
    private QcMatrixType1View qcMatrixView;
    private QcTypeService qcTypeService=new QcTypeServiceImpl();
    private QcMatrixRowService qcMatrixService=new QcMatrixRowServiceImpl();
   // private JobService jobService=new JobServiceImpl();
    private WorkspaceService workspaceService=new WorkspaceServiceImpl();
    private JobService jobService=new JobServiceImpl();
    
    private Job parentJob;
    @FXML
    private JFXButton addQcType;

    @FXML
    private JFXListView<QcMatrixRowType1Model> qcListView;

    @FXML
    void addNewQcType(ActionEvent event) {
        AddQcTypeModel acm=new AddQcTypeModel();
        AddQcTypeNode acn=new AddQcTypeNode(acm);
        String name=acm.getQctypeName();
        if(name!=null){
            QcType qctype=new QcType();
            qctype.setName(name);
            qcTypeService.createQcType(qctype);                //commit to db
           
            //for all jobs in the current workspace
            QcMatrixRow dbnewQcMatrixRow=new QcMatrixRow();
            Workspace currentWorkspace=parentJob.getWorkspace();
            List<Job> jobsInWorkspace=jobService.getJobsInWorkspace(currentWorkspace);
            for(Job j:jobsInWorkspace){
                QcMatrixRow dbqcmr=new QcMatrixRow();
                dbqcmr.setJob(j);
                dbqcmr.setQctype(qctype);
                qcMatrixService.createQcMatrixRow(dbqcmr);
                if(parentJob.equals(j)){
                    dbnewQcMatrixRow=dbqcmr;
                }
            }
            
            
           // qcMatrixService.createQcMatrixRow(dbqcmr);
           
            
            
            
            QcMatrixRowType1Model qcMatrixRow=new QcMatrixRowType1Model();
            qcMatrixRow.setId(dbnewQcMatrixRow.getId());
            qcMatrixRow.setName(name);
            qcMatrixRow.setQctype(qctype);
            qcMatrixRow.setParentJob(qcMatrixModel.getParentjob());
            qcMatrixRow.setSelectionModel(qcMatrixModel.getSelectedModel());
            qcMatrixModel.addQcMatrixRow(qcMatrixRow);
            qcMatrixModel.getSelectedModel().updateList();
        }
        
       
    }
    
    
    void setModel(QcMatrixType1Model item) {
        qcMatrixModel=item;
        
        
        /**
         * Load the qcmatrix from the database;
         *
         */
        
        qcMatrixModel.reloadProperty().addListener(QC_MATRIX_ROWS_RELOAD_LISTENER);
        
        if(qcMatrixModel.getParentjob().getId()!=null){
            //parentJob=jobService.getJob(qcMatrixModel.getParentjob().getId());
            parentJob=qcMatrixModel.getParentjob().getDatabaseJob();
            List<QcMatrixRow> qcMatrixForJob=qcMatrixService.getQcMatrixForJob(parentJob);
            for(QcMatrixRow qcmatrixRow:qcMatrixForJob){
                QcMatrixRowType1Model qcrw=new QcMatrixRowType1Model();
                qcrw.setId(qcmatrixRow.getId());
                qcrw.setName(qcmatrixRow.getQctype().getName());
                qcrw.setParentJob(qcMatrixModel.getParentjob());
                System.out.println("fend.job.definitions.qcmatrix.QcMatrixController.setModel(): "+qcmatrixRow.getQctype().getName()+":"+qcmatrixRow.getPresent());
                qcrw.setSelectionModel(qcMatrixModel.getSelectedModel());
                qcrw.setCheckedByUser(qcmatrixRow.getPresent());
                
                qcMatrixModel.addQcMatrixRow(qcrw);
                //qcMatrixModel.getSelectedModel().getSelections().add(qcmatrixRow.getQctype().getName());
            }
            qcMatrixModel.getSelectedModel().updateList();
        }
    }

    void setView(QcMatrixType1View view) {
        qcMatrixView=view;
        qcListView.setCellFactory(e-> new QcMatrixType1ListCell());
        qcListView.setItems(qcMatrixModel.getMatrixRows());
    }
    
    
    private ChangeListener<Boolean> QC_MATRIX_ROWS_RELOAD_LISTENER=new ChangeListener<Boolean>() {
        
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            
            qcMatrixModel.observableQcMatrixRows.clear();
            
             List<QcMatrixRow> qcMatrixForJob=qcMatrixService.getQcMatrixForJob(parentJob);
            for(QcMatrixRow qcmatrixRow:qcMatrixForJob){
                QcMatrixRowType1Model qcrw=new QcMatrixRowType1Model();
                qcrw.setId(qcmatrixRow.getId());
                qcrw.setName(qcmatrixRow.getQctype().getName());
                qcrw.setParentJob(qcMatrixModel.getParentjob());
                System.out.println("fend.job.definitions.qcmatrix.QcMatrixController.LISTENER(): "+qcmatrixRow.getQctype().getName()+":"+qcmatrixRow.getPresent());
                qcrw.setSelectionModel(qcMatrixModel.getSelectedModel());
                qcrw.setCheckedByUser(qcmatrixRow.getPresent());
                
                qcMatrixModel.addQcMatrixRow(qcrw);
                //qcMatrixModel.getSelectedModel().getSelections().add(qcmatrixRow.getQctype().getName());
            }
            qcMatrixModel.getSelectedModel().updateList();
        }
    };
}
