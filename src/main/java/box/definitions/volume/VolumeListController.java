/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package box.definitions.volume;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import volume.volume0.Volume0;


/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class VolumeListController {

    private VolumeListModel model;
    private VolumeListView view;
    
     @FXML
    private JFXListView<Volume0> volumeListView;
     
    void setModel(VolumeListModel item) {
        model=item;
    }

    void setView(VolumeListView view) {
        this.view=view;
        JFXButton jfxb=new JFXButton("+");
        jfxb.setOnMouseClicked(e->{
            System.out.println("box.definitions.volume.VolumeListController.setView(): Clicked!");});
        volumeListView.setCellFactory(param->new ListCell<Volume0>(){
            @Override
            protected void updateItem(Volume0 vol,boolean empty){
                super.updateItem(vol,empty);
                if(empty || vol==null){
                    setText(null);
                    setGraphic(jfxb);
                }else{
                    setText(vol.getName().get());
                }
                       
                
            }
        });
    }
    
}
