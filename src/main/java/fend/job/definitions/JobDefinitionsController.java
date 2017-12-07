/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.definitions;

import fend.job.job1.JobType1Model;
import fend.job.definitions.volume.VolumeListModel;
import fend.job.definitions.volume.VolumeListView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobDefinitionsController {
    final String volumeExpand="Volumes >";
    final String volumeCollapse="Volumes <";
    final String qmatrixExpand="QMatrix >";
    final String qmatrixCollapse="QMatrix <";
    final String insightExpand="Insight >";
    final String insightCollapse="Insight <";
    
    JobType1Model parentBox;
    JobDefinitionsModel model;
    JobDefinitionsView view;
    JFXDrawer qmatrixDrawer=new JFXDrawer();
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



    void setModel(JobDefinitionsModel item,JobType1Model parentBox) {
        model=item;
        this.parentBox=parentBox;
    }

    void setView(JobDefinitionsView vw) {
        view=vw;
        
        setupVolumeDrawer(volumeDrawer,openVolumeDrawer);
        setupQMatrixDrawer(qmatrixDrawer,openQMatrixDrawer);
        setupInsightDrawer(insightDrawer,openInsightDrawer);
        
        
        
    }

    private void setupVolumeDrawer(JFXDrawer drawer,JFXButton button) {
        drawer.setId("Volume");
        VolumeListModel vol=new VolumeListModel(parentBox);
        VolumeListView vollistview=new VolumeListView(vol);
        drawer.setSidePane(vollistview);
        drawer.setDirection(JFXDrawer.DrawerDirection.LEFT);
        drawer.setDefaultDrawerSize(vollistview.computeAreaInScreen());
        drawer.setOverLayVisible(false);
        drawer.setResizableOnDrag(true);
        drawer.setTranslateX(200);
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

    private void setupQMatrixDrawer(JFXDrawer drawer,JFXButton button) {
        
        
         drawer.setId("QMatrix");
         VolumeListModel vol=new VolumeListModel(parentBox);
         VolumeListView vollistview=new VolumeListView(vol);
        drawer.setSidePane(vollistview);
        drawer.setDirection(JFXDrawer.DrawerDirection.LEFT);
        drawer.setDefaultDrawerSize(vollistview.computeAreaInScreen());
        drawer.setOverLayVisible(false);
        drawer.setResizableOnDrag(true);
        drawer.setTranslateX(200);
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

    private void setupInsightDrawer(JFXDrawer drawer,JFXButton button) {
         
         drawer.setId("Insight");
         VolumeListModel vol=new VolumeListModel(parentBox);
         VolumeListView vollistview=new VolumeListView(vol);
        drawer.setSidePane(vollistview);
        drawer.setDirection(JFXDrawer.DrawerDirection.LEFT);
        drawer.setDefaultDrawerSize(vollistview.computeAreaInScreen());
        drawer.setOverLayVisible(false);
        drawer.setResizableOnDrag(true);
        drawer.setTranslateX(200);
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
    
}
