/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.workflow;

import app.properties.AppProperties;
import db.model.Comment;
import db.model.CommentType;
import db.model.User;
import fend.job.table.workflow.cell.WorkflowCheckBoxCell;
import db.model.Workflow;
import db.services.CommentService;
import db.services.CommentServiceImpl;
import db.services.CommentTypeService;
import db.services.CommentTypeServiceImpl;
import db.services.WorkflowService;
import db.services.WorkflowServiceImpl;
import fend.comments.CommentTypeModel;
import fend.job.table.workflow.workflowmodel.WorkflowModel;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class WorkflowTableController extends Stage{
    WorkflowTableModel model;
    WorkflowTableView view;
    WorkflowService workflowService=new WorkflowServiceImpl();
    CommentType workflowCommentType;
    User currentUser;
    CommentTypeService commentTypeService=new CommentTypeServiceImpl();
    CommentService commentService=new CommentServiceImpl();
    BooleanProperty showCurrent=new SimpleBooleanProperty(false);
    
    
    @FXML
    private TableView<WorkflowModel> workflowTable;

    @FXML
    private Button filterButton;
    
     @FXML
    void filter(ActionEvent event) {
        showCurrent.set(!showCurrent.get());
       
            
    }
    
    void setModel(WorkflowTableModel item) {
       model=item;
       List<WorkflowModel> workflowsForJob=model.getWorkflows();
        workflowCommentType=commentTypeService.getCommentTypeByName(CommentTypeModel.TYPE_WORKFLOW);
       TableColumn<WorkflowModel,Long> workflowVersionCol=new TableColumn<>("worflowVersion");
       TableColumn<WorkflowModel,String> workflowComments=new TableColumn<>("comments");
       TableColumn<WorkflowModel,Boolean> workflowControl=new TableColumn<>("control");
       workflowVersionCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<WorkflowModel, Long>, ObservableValue<Long>>() {
           @Override
           public ObservableValue<Long> call(TableColumn.CellDataFeatures<WorkflowModel, Long> param) {
               return new SimpleLongProperty(param.getValue().getVersion()).asObject();
           }
       });
       
       workflowComments.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<WorkflowModel, String>, ObservableValue<String>>() {
           @Override
           public ObservableValue<String> call(TableColumn.CellDataFeatures<WorkflowModel, String> param) {
              return new SimpleStringProperty(param.getValue().getComment());
           }
       });
       workflowComments.setCellFactory(TextFieldTableCell.forTableColumn());
       workflowComments.setOnEditCommit(e->{
           if(e.getOldValue().equals(e.getNewValue())){
               return;
           }
           currentUser= AppProperties.getCurrentUser();
          String userTimeStamp=AppProperties.timeNow()+" > "+currentUser.getInitials()+" > ";
           WorkflowModel wm=e.getRowValue();
           
            Comment comment=null;
            try{
                if((comment=commentService.getCommentFor(CommentTypeModel.TYPE_WORKFLOW,model.getJob(),wm.getWorkflow()))==null){
                    comment=new Comment();
                    comment.setCommentType(workflowCommentType);
                    comment.setJob(model.getJob());
                    comment.setSequence(null);
                    comment.setSubsurface(null);
                    comment.setWorkflow(wm.getWorkflow());
                    comment.setComments(userTimeStamp+e.getNewValue());
                    commentService.createComment(comment);
                    wm.setCommentStack(comment.getComments());
                }else{
                     String oldComments = comment.getComments();
                    String newComments = userTimeStamp + e.getNewValue() + "\n" + oldComments;
                    comment.setComments(newComments);
                    commentService.updateComment(comment);
                    wm.setCommentStack(comment.getComments());
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
       });
        workflowControl.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<WorkflowModel, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<WorkflowModel, Boolean> param) {

                WorkflowModel wf = param.getValue();
                if (wf.indeterminateProperty().get()) {
                    return null;
                } else {
                    return wf.checkedProperty();
                }

            }
        });
       
       
        workflowControl.setCellFactory(p->{return new WorkflowCheckBoxCell(p,model.getJob(),workflowService);});
      
       filterButton.setText("show current workflows");
       workflowTable.getColumns().addAll(workflowVersionCol,workflowControl,workflowComments);
       ObservableList<WorkflowModel> obs=FXCollections.observableList(model.getWorkflows());
       workflowTable.getItems().addAll(obs);
       workflowTable.setEditable(true);
       
       
       showCurrent.addListener(FILTER_WORKFLOW_LISTENER);
    }

    void setView(WorkflowTableView vw) {
       view=vw;
       this.setTitle("All workflows used in job: "+model.getJob().getNameJobStep());
        this.setScene(new Scene(this.view));
        show();
    }
    
            ChangeListener<Boolean> FILTER_WORKFLOW_LISTENER=new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                
                        workflowTable.getItems().clear();
                            {
                                ObservableList<WorkflowModel> obs = FXCollections.observableList(model.getCurrentWorkflows());
                                workflowTable.getItems().addAll(obs);
                            }
                        workflowTable.refresh();
                        filterButton.setText("show all workflows    ");
                        WorkflowTableController.this.setTitle("Current workflows used in job: "+model.getJob().getNameJobStep());
            }else{
                        workflowTable.getItems().clear();
                            {
                                ObservableList<WorkflowModel> obs = FXCollections.observableList(model.getWorkflows());
                                workflowTable.getItems().addAll(obs);
                            }
                        workflowTable.refresh();
                        filterButton.setText("show current workflows");
                        WorkflowTableController.this.setTitle("All workflows used in job: "+model.getJob().getNameJobStep());
            }
        }
    };
    
    
    
    
}
