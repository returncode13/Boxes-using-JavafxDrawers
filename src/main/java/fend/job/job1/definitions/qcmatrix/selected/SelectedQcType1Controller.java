/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job1.definitions.qcmatrix.selected;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXListView;
import db.model.Job;
import db.services.QcMatrixRowService;
import db.services.QcMatrixRowServiceImpl;
import db.services.QcTableService;
import fend.job.job1.JobType1Model;
import fend.job.job1.definitions.qcmatrix.QcMatrixType1Model;
import fend.job.job1.definitions.qcmatrix.QcMatrixType1View;
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
public class SelectedQcType1Controller {
    final String addQcExpand="Qc types   >";             // two spaces between s and >
    final String addQcCollapse="Qc types   <";
    JobType1Model job;
    SelectedQcType1Model model;
    SelectedQcType1View view;
    QcMatrixRowService qcMatrixRowService=new QcMatrixRowServiceImpl();
   
    
    JFXDrawer addQcDrawer=new JFXDrawer();
    
    @FXML 
    private JFXDrawersStack drawersStack;

    @FXML 
    private JFXButton openQMatrix;
    
     @FXML
    private JFXListView<String> selectedList;

    void setModel(SelectedQcType1Model model) {
        
        this.model=model;
        this.job=model.getJob();
        
       this.model.updateListProperty().addListener(UPDATE_LIST_LISTENER);
        
        
    }
   
    void setView(SelectedQcType1View vw) {
        view=vw;
        setupAddQcDrawer(addQcDrawer, openQMatrix);
    }
    
    
    private void setupAddQcDrawer(JFXDrawer drawer,JFXButton button){
         drawer.setId("QMatrix");
         /*VolumeListType1Model insmodel=new VolumeListType1Model(parentBox);
         VolumeListType1View insList=new VolumeListType1View(insmodel);*/
         
         QcMatrixType1Model qcmatmodel=new QcMatrixType1Model(job,model);
         QcMatrixType1View qcMatrixView=new QcMatrixType1View(qcmatmodel);
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
            Job job=SelectedQcType1Controller.this.job.getDatabaseJob();
            List<String> names=qcMatrixRowService.getQcMatrixRowNamesForJob(job);
            model.getSelections().setAll(names);
            
            selectedList.setItems(model.getSelections());
        }
    };
           
}
