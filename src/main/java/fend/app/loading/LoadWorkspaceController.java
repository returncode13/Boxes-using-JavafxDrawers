/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.app.loading;

import db.model.Workspace;
import java.util.Iterator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author sharath nair
 * sharath.nair@polarcus.com
 */
public class LoadWorkspaceController extends Stage{

     private  LoadWorkspaceModel lsmodel;
    private LoadWorkspaceNode lsnode;
    private ObservableList<Workspace> workspaceList;
    private Workspace selectedWorkspace;
    
    @FXML
    private Button loadButton;
    
    @FXML
    private ListView<Workspace> listView=new ListView<>();
    
    @FXML
    void handleLoadButton(ActionEvent event) {
        
        lsmodel.setWorkspaceToBeLoaded(selectedWorkspace);
        
        System.out.println("fend.app.loading.LoadSessionController.handleLoadButton() will load : "+selectedWorkspace.getName());
        
        close();
    }
    
    
   
    
    void setModel(LoadWorkspaceModel lsm) {
        this.lsmodel=lsm;
        workspaceList=lsmodel.getList();
        listView.setItems(workspaceList);
       listView.setCellFactory(lv -> {
           ListCell<Workspace> cell = new ListCell<Workspace>(){
               @Override
               protected void updateItem(Workspace item, boolean empty) {
                   super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                   if(empty){
                       setText(null);
                   }else{
                       setText(item.getName());
                   }
                   
                   
               }
               
           };
           
           cell.setOnMouseClicked(e->{
               if(cell.getItem()!=null){
                   System.out.println(cell.getItem().getName()+ " :id: "+cell.getItem().getId());
                   selectedWorkspace=cell.getItem();
               }
           });
            return cell;
           
       });
       /* for (Iterator<String> iterator = workspaceList.iterator(); iterator.hasNext();) {
            String next = iterator.next();
            System.out.println(next);
            
        }*/
        
        
       
    }

    void setView(LoadWorkspaceNode aThis) {
       
        this.lsnode=aThis;
        this.setScene(new Scene(lsnode));
        this.showAndWait();
    }
    
}
