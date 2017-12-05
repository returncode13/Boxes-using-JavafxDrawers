/*/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package job.job1;


import job.definitions.JobDefinitionsModel;
import job.definitions.JobDefinitionsView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXTextField;
import java.util.List;
import java.util.Set;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.util.Duration;
import job.job0.JobType0Controller;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class JobType1Controller implements JobType0Controller{
    final String expand=">";
    final String collapse="<";
    
    JobType1Model model;
    JobType1View node;
    JFXDrawer drawer=new JFXDrawer();
    
    @FXML
    private JFXTextField name;
    
    
    @FXML
    private JFXDrawersStack drawersStack;
    
    @FXML
    private JFXButton q;

    @FXML
    private JFXButton t;

    @FXML
    private JFXButton h;

    
    @FXML
    private JFXButton openDrawer;

    void setModel(JobType1Model item) {
        model=item;
        
    }

    void setView(JobType1View vw) {
        node=vw;
        
        drawer.setId("LEFT");
        JobDefinitionsModel bdmodel=new JobDefinitionsModel();
        JobDefinitionsView bdview=new JobDefinitionsView(bdmodel,this.model);
        drawer.setSidePane(bdview);
        drawer.setDirection(JFXDrawer.DrawerDirection.LEFT);
        drawer.setDefaultDrawerSize(bdview.computeAreaInScreen());
        drawer.setOverLayVisible(false);
        drawer.setResizableOnDrag(true);
        drawer.setTranslateX(150);
        drawer.setTranslateY(0);
        
        openDrawer.setOnMousePressed(e->{
         drawersStack.toggle(drawer);
        });
        
        drawer.setOnDrawerOpened(e->{
        //openDrawer.setText(this.collapse);
        });
        
        drawer.setOnDrawerOpening(e->{
            drawer.setVisible(true);
            openDrawer.setText(this.collapse);
            FadeTransition ft=new FadeTransition(Duration.millis(500),drawer);
            ft.setFromValue(0.7);
            ft.setToValue(1.0);
            ft.setAutoReverse(true);
            ft.setCycleCount(1);
            ft.play();
        });
        
        drawer.setOnDrawerClosed(e->{
            drawer.setVisible(false);
            openDrawer.setText(this.expand);
            model.toggleChange();
            
        });
        drawer.setOnDrawerClosing(e->{
            FadeTransition ft=new FadeTransition(Duration.millis(200),drawer);
            ft.setFromValue(1.0);
            ft.setToValue(0.7);
            ft.setAutoReverse(true);
            ft.setCycleCount(1);
            ft.play();
             
        });
        
         node.setOnMouseDragged(event->{
            node.relocateToPoint(new Point2D(event.getSceneX(),event.getSceneY()));
         });
         
         name.setOnKeyPressed(e->{
             if(e.KEY_PRESSED.equals(KeyCode.ENTER)){
                 String text=name.getText();
                 model.setNameproperty(text);
            }
             if(e.KEY_PRESSED.equals(KeyCode.TAB)){
                 String text=name.getText();
                 model.setNameproperty(text);
            }
        });
         
         name.setOnKeyReleased(e->{
             
         });
    }
}