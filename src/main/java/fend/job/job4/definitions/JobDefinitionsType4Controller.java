/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job4.definitions;

import fend.job.job1.JobType1Model;
//import fend.job.definitions.volume.VolumeListType4Model;
//import fend.job.definitions.volume.VolumeListType4View;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
//import fend.job.definitions.insight.InsightListModel;
//import fend.job.definitions.insight.InsightListView;
//import fend.job.definitions.qcmatrix.QcMatrixModel;
//import fend.job.definitions.qcmatrix.QcMatrixView;
import fend.job.job0.JobType0Model;
import fend.job.job4.JobType4Model;
import fend.job.job4.definitions.qcmatrix.QcMatrixType4Model;
import fend.job.job4.definitions.qcmatrix.QcMatrixType4View;
import fend.job.job4.definitions.qcmatrix.selected.SelectedQcType4Model;
import fend.job.job4.definitions.qcmatrix.selected.SelectedQcType4View;
import fend.job.job4.definitions.volume.VolumeListType4Model;
import fend.job.job4.definitions.volume.VolumeListType4View;




import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobDefinitionsType4Controller {
    final String volumeExpand="Volumes >";
    final String volumeCollapse="Volumes <";
    final String qmatrixExpand="QMatrix >";
    final String qmatrixCollapse="QMatrix <";
    final String insightExpand="Insight >";
    final String insightCollapse="Insight <";
    
    JobType4Model parentBox;
    JobDefinitionsType4Model model;
    JobDefinitionsType4View view;
    JFXDrawer qmatrixDrawer=new JFXDrawer();
    JFXDrawer volumeDrawer=new JFXDrawer();
    JFXDrawer selectedQcMatrixDrawer=new JFXDrawer();
   // JFXDrawer insightDrawer=new JFXDrawer();
    
    @FXML
    private JFXDrawersStack drawersStack;

    @FXML
    private JFXButton openVolumeDrawer;

    /* @FXML
    private JFXButton openInsightDrawer;*/
    @FXML
    private JFXButton openQMatrixDrawer;



    void setModel(JobDefinitionsType4Model item,JobType4Model parentBox) {
        model=item;
        this.parentBox=parentBox;
    }

    void setView(JobDefinitionsType4View vw) {
        view=vw;
        
        setupVolumeDrawer(volumeDrawer,openVolumeDrawer);
        setupSelectedQcMatrixDrawer(selectedQcMatrixDrawer, openQMatrixDrawer);
       // setupQMatrixDrawer(qmatrixDrawer,openQMatrixDrawer);
//        setupInsightDrawer(insightDrawer,openInsightDrawer);
        
        
        
    }

    private void setupVolumeDrawer(JFXDrawer drawer,JFXButton button) {
        drawer.setId("Volume");
        VolumeListType4Model vol=new VolumeListType4Model(parentBox);
        VolumeListType4View vollistview=new VolumeListType4View(vol);
        drawer.setSidePane(vollistview);
        drawer.setDirection(JFXDrawer.DrawerDirection.LEFT);
        drawer.setDefaultDrawerSize(vollistview.computeAreaInScreen());
        drawer.setOverLayVisible(false);
        drawer.setResizableOnDrag(true);
        drawer.setTranslateX(180);
        drawer.setTranslateY(0);
        
        
        
        
        button.setOnMousePressed(e->{drawersStack.toggle(drawer);});
        drawer.setOnDrawerClosed(e->{
            drawer.setVisible(false);
            button.setText(this.volumeExpand);
            e.consume();                //prevent further collapse of the nested (previous) drawers
        });
        drawer.setOnDrawerOpening(e->{
           drawer.setVisible(true);
            button.setText(this.volumeCollapse);
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

    private void setupSelectedQcMatrixDrawer(JFXDrawer drawer, JFXButton button) {
       
         drawer.setId("QMatrix");
         /*VolumeListType1Model insmodel=new VolumeListType1Model(parentBox);
         VolumeListType1View insList=new VolumeListType1View(insmodel);*/
         
        SelectedQcType4Model smodel=new SelectedQcType4Model(parentBox);
        SelectedQcType4View sview=new SelectedQcType4View(smodel); 
        drawer.setSidePane(sview);
        drawer.setDirection(JFXDrawer.DrawerDirection.LEFT);
        drawer.setDefaultDrawerSize(sview.computeAreaInScreen());
        drawer.setOverLayVisible(false);
        drawer.setResizableOnDrag(true);
        drawer.setTranslateX(122);
        drawer.setTranslateY(0);
        
        
        
        
        button.setOnMousePressed(e->{drawersStack.toggle(drawer);});
        drawer.setOnDrawerClosed(e->{
            drawer.setVisible(false);
            button.setText(this.qmatrixExpand);
            e.consume();                //prevent further collapse of the nested (previous) drawers
        });
        drawer.setOnDrawerOpening(e->{
           
           drawer.setVisible(true);
            button.setText(this.qmatrixCollapse);
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
    
    
    
    /*private void setupQMatrixDrawer(JFXDrawer drawer,JFXButton button) {
    
    
    drawer.setId("QMatrix");
    
    QcMatrixType4Model qcmatmodel=new QcMatrixType4Model(parentBox);
    QcMatrixType4View qcMatrixView=new QcMatrixType4View(qcmatmodel);
    drawer.setSidePane(qcMatrixView);
    drawer.setDirection(JFXDrawer.DrawerDirection.LEFT);
    drawer.setDefaultDrawerSize(qcMatrixView.computeAreaInScreen());
    drawer.setOverLayVisible(false);
    drawer.setResizableOnDrag(true);
    drawer.setTranslateX(180);
    drawer.setTranslateY(0);
    
    
    
    
    button.setOnMousePressed(e->{drawersStack.toggle(drawer);});
    drawer.setOnDrawerClosed(e->{
    drawer.setVisible(false);
    button.setText(this.qmatrixExpand);
    e.consume();                //prevent further collapse of the nested (previous) drawers
    });
    drawer.setOnDrawerOpening(e->{
    drawer.setVisible(true);
    button.setText(this.qmatrixCollapse);
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
    }*/
/*
    private void setupInsightDrawer(JFXDrawer drawer,JFXButton button) {
         
         drawer.setId("Insight");
         InsightListModel insmodel=new InsightListModel(parentBox);
         InsightListView insList=new InsightListView(insmodel);
        drawer.setSidePane(insList);
        drawer.setDirection(JFXDrawer.DrawerDirection.LEFT);
        drawer.setDefaultDrawerSize(insList.computeAreaInScreen());
        drawer.setOverLayVisible(false);
        drawer.setResizableOnDrag(true);
        drawer.setTranslateX(180);
        drawer.setTranslateY(0);
        
        
        
        
        button.setOnMousePressed(e->{drawersStack.toggle(drawer);});
        drawer.setOnDrawerClosed(e->{
            drawer.setVisible(false);
            button.setText(this.insightExpand);
            e.consume();                //prevent further collapse of the nested (previous) drawers
        });
        drawer.setOnDrawerOpening(e->{
           drawer.setVisible(true);
            button.setText(this.insightCollapse);
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
    */
    
}
