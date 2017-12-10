/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.definitions.qcmatrix;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import db.model.QcType;
import db.services.QcTypeService;
import db.services.QcTypeServiceImpl;
import fend.job.definitions.qcmatrix.qctype.QcMatrixRow;

import fend.job.definitions.qcmatrix.qctype.addQcType.AddQcTypeModel;
import fend.job.definitions.qcmatrix.qctype.addQcType.AddQcTypeNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class QcMatrixController {
    private QcMatrixModel qcMatrixModel;
    private QcMatrixView qcMatrixView;
    private QcTypeService qcTypeService=new QcTypeServiceImpl();
    
     @FXML
    private JFXButton addQcType;

    @FXML
    private JFXListView<QcMatrixRow> qcListView;

    @FXML
    void addNewQcType(ActionEvent event) {
        AddQcTypeModel acm=new AddQcTypeModel();
        AddQcTypeNode acn=new AddQcTypeNode(acm);
        String name=acm.getQctypeName();
        if(name!=null){
            QcType qctype=new QcType();
            qctype.setName(name);
            qcTypeService.createQcType(qctype);                //commit to db
            
            QcMatrixRow qcMatrixRow=new QcMatrixRow();
            qcMatrixRow.setName(name);
            
            qcMatrixModel.addQcMatrixRow(qcMatrixRow);
            
        }
    }
    
    
    void setModel(QcMatrixModel item) {
        qcMatrixModel=item;
    }

    void setView(QcMatrixView view) {
        qcMatrixView=view;
        qcListView.setCellFactory(e-> new QcMatrixListCell());
        qcListView.setItems(qcMatrixModel.getMatrixRows());
    }
    
}
