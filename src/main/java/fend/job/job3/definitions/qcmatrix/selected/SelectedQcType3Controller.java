/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job3.definitions.qcmatrix.selected;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXListView;
import db.model.Job;
import db.services.QcMatrixRowService;
import db.services.QcMatrixRowServiceImpl;
import fend.job.job3.JobType3Model;
import fend.job.job3.definitions.qcmatrix.QcMatrixType3Model;
import fend.job.job3.definitions.qcmatrix.QcMatrixType3View;


import java.util.List;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.util.Duration;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class SelectedQcType3Controller {
    final String addQcExpand="Qc types   >";             // two spaces between s and >
    final String addQcCollapse="Qc types   <";
    JobType3Model job;
    SelectedQcType3Model model;
    SelectedQcType3View view;
    QcMatrixRowService qcMatrixRowService=new QcMatrixRowServiceImpl();
   
    
    JFXDrawer addQcDrawer=new JFXDrawer();
    
    @FXML 
    private JFXDrawersStack drawersStack;

    @FXML 
    private JFXButton openQMatrix;
    
     @FXML
    private JFXListView<String> selectedList;

    void setModel(SelectedQcType3Model model) {
        
        this.model=model;
        this.job=model.getJob();
        
       this.model.updateListProperty().addListener(UPDATE_LIST_LISTENER);
        
        
    }
   
    void setView(SelectedQcType3View vw) {
        view=vw;
        setupAddQcDrawer(addQcDrawer, openQMatrix);
    }
    
    
    private void setupAddQcDrawer(JFXDrawer drawer,JFXButton button){
         drawer.setId("QMatrix");
         /*VolumeListType1Model insmodel=new VolumeListType1Model(parentBox);
         VolumeListType1View insList=new VolumeListType1View(insmodel);*/
         
         QcMatrixType3Model qcmatmodel=new QcMatrixType3Model(job,model);
         QcMatrixType3View qcMatrixView=new QcMatrixType3View(qcmatmodel);
        drawer.setSidePane(qcMatrixView);
        drawer.setDirection(JFXDrawer.DrawerDirection.LEFT);
        drawer.setDefaultDrawerSize(qcMatrixView.computeAreaInScreen());
        drawer.setOverLayVisible(false);
        drawer.setResizableOnDrag(true);
        drawer.setTranslateX(202);
        drawer.setTranslateY(0);
        
        
        
        
        button.setOnMousePressed(e->{drawersStack.toggle(drawer);});
        drawer.setOnDrawerClosed(e->{
            drawer.setVisible(false);
            button.setText(this.addQcExpand);
            e.consume();                //prevent further collapse of the nested (previous) drawers
        });
        drawer.setOnDrawerOpening(e->{
            qcmatmodel.reload();       //reload to fetch latest list. replace by a better way
           drawer.setVisible(true);
            button.setText(this.addQcCollapse);
            FadeTransition ft=new FadeTransition(Duration.millis(500),drawer);
            ft.setFromValue(0.7);
            ft.setToValue(1.0);
            ft.setAutoReverse(true);
            ft.setCycleCount(1);
            ft.play();
        });
        
        drawer.setOnDrawerClosing(e->{
            FadeTransition ft=new FadeTransition(Duration.millis(200),drawer);
            ft.setFromValue(1.0);
            ft.setToValue(0.7);
            ft.setAutoReverse(true);
            ft.setCycleCount(1);
            ft.play();
             
        });
    }

    private ChangeListener<Boolean> UPDATE_LIST_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            model.getSelections().clear();
            Job job=SelectedQcType3Controller.this.job.getDatabaseJob();
            List<String> names=qcMatrixRowService.getQcMatrixRowNamesForJob(job);
            model.getSelections().setAll(names);
            
            selectedList.setItems(model.getSelections());
        }
    };
           
}
