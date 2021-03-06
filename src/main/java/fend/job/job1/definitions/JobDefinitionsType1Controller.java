/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job1.definitions;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;

import fend.job.job1.JobType1Model;
import fend.job.job1.definitions.insight.InsightListModel;
import fend.job.job1.definitions.insight.InsightListView;
import fend.job.job1.definitions.qcmatrix.QcMatrixType1Model;
import fend.job.job1.definitions.qcmatrix.QcMatrixType1View;
import fend.job.job1.definitions.qcmatrix.selected.SelectedQcType1Model;
import fend.job.job1.definitions.qcmatrix.selected.SelectedQcType1View;
import fend.job.job1.definitions.volume.VolumeListType1Model;
import fend.job.job1.definitions.volume.VolumeListType1View;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.util.Duration;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobDefinitionsType1Controller {
    final String volumeExpand="Volumes >";
    final String volumeCollapse="Volumes <";
    final String qmatrixExpand="QMatrix >";
    final String qmatrixCollapse="QMatrix <";
    final String insightExpand="Insight >";
    final String insightCollapse="Insight <";
    
    JobType1Model parentBox;
    JobDefinitionsType1Model model;
    JobDefinitionsType1View view;
   // JFXDrawer qmatrixDrawer=new JFXDrawer();
    JFXDrawer selectedQcMatrixDrawer=new JFXDrawer();
    JFXDrawer volumeDrawer=new JFXDrawer();
    JFXDrawer insightDrawer=new JFXDrawer();
    
    @FXML
    private JFXDrawersStack drawersStack;

    @FXML
    private JFXButton openVolumeDrawer;

    @FXML
    private JFXButton openInsightDrawer;

    @FXML
    private JFXButton openQMatrixDrawer;



    void setModel(JobDefinitionsType1Model item,JobType1Model parentBox) {
        model=item;
        this.parentBox=parentBox;
    }

    void setView(JobDefinitionsType1View vw) {
        view=vw;
        
        setupVolumeDrawer(volumeDrawer,openVolumeDrawer);
        //setupQMatrixDrawer(qmatrixDrawer,openQMatrixDrawer);
        setupSelectedQcMatrixDrawer(selectedQcMatrixDrawer,openQMatrixDrawer);
        setupInsightDrawer(insightDrawer,openInsightDrawer);
        
        
        
    }

    private void setupVolumeDrawer(JFXDrawer drawer,JFXButton button) {
        drawer.setId("Volume");
        VolumeListType1Model vol=new VolumeListType1Model(parentBox);
        VolumeListType1View vollistview=new VolumeListType1View(vol);
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

    /*  private void setupQMatrixDrawer(JFXDrawer drawer,JFXButton button) {
    
    
    drawer.setId("QMatrix");
    
    
    QcMatrixType1Model qcmatmodel=new QcMatrixType1Model(parentBox);
    QcMatrixType1View qcMatrixView=new QcMatrixType1View(qcmatmodel);
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

    private void setupSelectedQcMatrixDrawer(JFXDrawer drawer, JFXButton button) {
       
         drawer.setId("QMatrix");
         /*VolumeListType1Model insmodel=new VolumeListType1Model(parentBox);
         VolumeListType1View insList=new VolumeListType1View(insmodel);*/
         
        SelectedQcType1Model smodel=new SelectedQcType1Model(parentBox);
        SelectedQcType1View sview=new SelectedQcType1View(smodel);
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
    
}
