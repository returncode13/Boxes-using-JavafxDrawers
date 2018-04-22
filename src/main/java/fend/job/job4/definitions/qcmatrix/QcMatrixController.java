/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job4.definitions.qcmatrix;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import db.model.Job;
import db.model.QcMatrixRow;
import db.model.QcType;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.QcMatrixRowServiceImpl;
import db.services.QcTypeService;
import db.services.QcTypeServiceImpl;
//import fend.job.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModel;

//import fend.job.definitions.qcmatrix.qctype.addQcType.AddQcTypeModel;
//import fend.job.definitions.qcmatrix.qctype.addQcType.AddQcTypeNode;
import fend.job.job0.JobType0Model;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import db.services.QcMatrixRowService;
import fend.job.job4.definitions.qcmatrix.qcmatrixrow.QcMatrixRowModel;
import fend.job.job4.definitions.qcmatrix.qctype.addQcType.AddQcTypeModel;
import fend.job.job4.definitions.qcmatrix.qctype.addQcType.AddQcTypeNode;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixController {
    private QcMatrixModel qcMatrixModel;
    private QcMatrixView qcMatrixView;
    private QcTypeService qcTypeService=new QcTypeServiceImpl();
    private QcMatrixRowService qcMatrixService=new QcMatrixRowServiceImpl();
    private JobService jobService=new JobServiceImpl();
    
    private Job parentJob;
     @FXML
    private JFXButton addQcType;

    @FXML
    private JFXListView<QcMatrixRowModel> qcListView;

    @FXML
    void addNewQcType(ActionEvent event) {
        AddQcTypeModel acm=new AddQcTypeModel();
        AddQcTypeNode acn=new AddQcTypeNode(acm);
        String name=acm.getQctypeName();
        if(name!=null){
            QcType qctype=new QcType();
            qctype.setName(name);
            qcTypeService.createQcType(qctype);                //commit to db
            
            QcMatrixRow dbnewQcMatrixRow=new QcMatrixRow();
            dbnewQcMatrixRow.setJob(parentJob);
            dbnewQcMatrixRow.setQctype(qctype);
            qcMatrixService.createQcMatrixRow(dbnewQcMatrixRow);
            
            QcMatrixRowModel qcMatrixRow=new QcMatrixRowModel();
            qcMatrixRow.setId(dbnewQcMatrixRow.getId());
            qcMatrixRow.setName(name);
            qcMatrixRow.setQctype(qctype);
            qcMatrixModel.addQcMatrixRow(qcMatrixRow);
            
        }
    }
    
    
    void setModel(QcMatrixModel item) {
        qcMatrixModel=item;
        
        /**
         * Load the qcmatrix from the database;
         *
         */
        
        
        
        if(qcMatrixModel.getParentjob().getId()!=null){
            parentJob=jobService.getJob(qcMatrixModel.getParentjob().getId());
            List<QcMatrixRow> qcMatrixForJob=qcMatrixService.getQcMatrixForJob(parentJob);
            for(QcMatrixRow qcmatrixRow:qcMatrixForJob){
                QcMatrixRowModel qcrw=new QcMatrixRowModel();
                qcrw.setId(qcmatrixRow.getId());
                qcrw.setName(qcmatrixRow.getQctype().getName());
                System.out.println("fend.job.job2.definitions.qcmatrix.QcMatrixController.setModel(): "+qcmatrixRow.getQctype().getName()+":"+qcmatrixRow.getPresent());
                qcrw.setCheckedByUser(qcmatrixRow.getPresent());
                
                qcMatrixModel.addQcMatrixRow(qcrw);
            }
        }
    }

    void setView(QcMatrixView view) {
        qcMatrixView=view;
        qcListView.setCellFactory(e-> new QcMatrixListCell());
        qcListView.setItems(qcMatrixModel.getMatrixRows());
    }
    
}
