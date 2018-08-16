/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.workflow.cell;

import db.services.WorkflowService;
import fend.job.table.qctable.PQCheckBox;
import fend.job.table.workflow.workflowmodel.WorkflowModel;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkflowCheckBoxCell extends TableCell<WorkflowModel,Boolean>{

    int index;
    final PQCheckBox checkBox;
    final Label label;
    final HBox hbox;
    WorkflowModel selectedItem;
    WorkflowService workflowService;
    TableColumn<WorkflowModel, Boolean> param;
    public WorkflowCheckBoxCell(TableColumn<WorkflowModel, Boolean> p,WorkflowService ws) {
        
        checkBox=new PQCheckBox();
        label=new Label();
        hbox=new HBox(checkBox,label);
       checkBox.setAllowIndeterminate(true);
       workflowService=ws;
                param=p;
       
        checkBox.selectedProperty().addListener((obs, ol, nv) -> {
            if (getTableRow() != null) {
                int sel = getTableRow().getIndex();
                // selectedItem=(WorkflowModel) getTableRow().getItem();
                selectedItem = param.getTableView().getItems().get(sel);
                //System.out.println("fend.job.table.workflow.cell.WorkflowCheckBoxCell.<init>(): Setting control to " + nv + " for "
                //        + selectedItem.getWorkflow().getId());
                selectedItem.setIndeterminateProperty(false);
                selectedItem.setCheckedProperty(nv);

                workflowService.updateControlFor(selectedItem.getWorkflow(), nv);
            }

        });

        checkBox.indeterminateProperty().addListener((obs, ol, nv) -> {
            if (getTableRow() != null) {
                int sel = getTableRow().getIndex();
                // selectedItem=(WorkflowModel) getTableRow().getItem();
                selectedItem = param.getTableView().getItems().get(sel);
                // selectedItem=param.getTableView().getSelectionModel().getSelectedItem();
                // selectedItem=(WorkflowModel) getTableRow().getItem();
                //System.out.println("fend.job.table.workflow.cell.WorkflowCheckBoxCell.<init>(): Setting indeterminate to " + nv + " for "
                //        + selectedItem.getWorkflow().getId());
                selectedItem.setIndeterminateProperty(nv);
                if (nv) {
                    workflowService.updateControlFor(selectedItem.getWorkflow(), null);
                }
            }

        });
       
       
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty); 
        
        if(empty){
            setGraphic(null);
        }else{
          //  Boolean val=getItem();
                if(item==null){
                    checkBox.setIndeterminate(true);
                    
                   
                }
                else{
                    checkBox.setIndeterminate(false);
                    checkBox.setSelected(item);
                 
                }
//                int sel=getTableRow().getIndex();
                 //selectedItem=param.getTableView().getSelectionModel().getSelectedItem();
               //   selectedItem=(WorkflowModel) getTableRow().getItem();
                 setGraphic(checkBox);
                 getStylesheets().add("styles/checkcustom.css");
        }
    }
    
}
