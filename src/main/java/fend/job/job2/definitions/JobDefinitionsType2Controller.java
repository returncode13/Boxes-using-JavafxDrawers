/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job2.definitions;

//import fend.job.definitions.volume.VolumeListType2Model;
//import fend.job.definitions.volume.VolumeListType2View;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import fend.job.job2.JobType2Model;
import fend.job.job2.definitions.insight.InsightListModel;
import fend.job.job2.definitions.insight.InsightListView;
import fend.job.job2.definitions.qcmatrix.QcMatrixType2Model;
import fend.job.job2.definitions.qcmatrix.QcMatrixType2View;
import fend.job.job2.definitions.qcmatrix.selected.SelectedQcType2View;
import fend.job.job2.definitions.volume.VolumeListType2Model;
import fend.job.job2.definitions.volume.VolumeListType2View;
import fend.job.job2.definitions.qcmatrix.selected.SelectedQcType2Model;


import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.util.Duration;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobDefinitionsType2Controller {
    final String volumeExpand="Volumes >";
    final String volumeCollapse="Volumes <";
    final String qmatrixExpand="QMatrix >";
    final String qmatrixCollapse="QMatrix <";
    final String insightExpand="Insight >";
    final String insightCollapse="Insight <";
    
    JobType2Model parentBox;
    JobDefinitionsType2Model model;
    JobDefinitionsType2View view;
    //JFXDrawer qmatrixDrawer=new JFXDrawer();
    JFXDrawer volumeDrawer=new JFXDrawer();
    JFXDrawer insightDrawer=new JFXDrawer();
    
    JFXDrawer selectedQcMatrixDrawer=new JFXDrawer();
    
    
    @FXML
    private JFXDrawersStack drawersStack;

    @FXML
    private JFXButton openVolumeDrawer;

    @FXML
    private JFXButton openInsightDrawer;

    @FXML
    private JFXButton openQMatrixDrawer;



    void setModel(JobDefinitionsType2Model item,JobType2Model parentBox) {
        model=item;
        this.parentBox=parentBox;
    }

    void setView(JobDefinitionsType2View vw) {
        view=vw;
        
        setupVolumeDrawer(volumeDrawer,openVolumeDrawer);
       // setupQMatrixDrawer(qmatrixDrawer,openQMatrixDrawer);
        setupInsightDrawer(insightDrawer,openInsightDrawer);
        setupSelectedQcMatrixDrawer(selectedQcMatrixDrawer,openQMatrixDrawer);
        
        
    }

    private void setupVolumeDrawer(JFXDrawer drawer,JFXButton button) {
        drawer.setId("Volume");
        VolumeListType2Model vol=new VolumeListType2Model(parentBox);
        VolumeListType2View vollistview=new VolumeListType2View(vol);
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

    /* private void setupQMatrixDrawer(JFXDrawer drawer,JFXButton button) {
    
    
    drawer.setId("QMatrix");
    
    QcMatrixType2Model qcmatmodel=new QcMatrixType2Model(parentBox);
    QcMatrixType2View qcMatrixView=new QcMatrixType2View(qcmatmodel);
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
         
        SelectedQcType2Model smodel=new SelectedQcType2Model(parentBox);
        SelectedQcType2View sview=new SelectedQcType2View(smodel);
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
