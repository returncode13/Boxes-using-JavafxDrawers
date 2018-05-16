/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job4.table;

import fend.job.job4.table.history.TextHistoryModel;
import fend.job.job4.table.history.TextHistoryView;
import java.util.List;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import middleware.sequences.text.TextSequenceHeader;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class TextLineTableController extends Stage{
    private TextLineTableModel model;
    private TextLineTableView view;
    
    @FXML
    private TableView<TextSequenceHeader> textTable;
    
    TableColumn<TextSequenceHeader,Long> sequenceNumber=new TableColumn<>("seq");
    TableColumn<TextSequenceHeader, String> file=new TableColumn<>("file");
    TableColumn<TextSequenceHeader,String> md5=new TableColumn<>("md5");
    TableColumn<TextSequenceHeader,String> timeStamp=new TableColumn<>("time stamp");
    TableColumn<TextSequenceHeader,Long> numberOfRuns=new TableColumn<>("rev");
    TableColumn<TextSequenceHeader,Boolean> modified=new TableColumn<>("modified");
    TableColumn<TextSequenceHeader,Boolean> deleted=new TableColumn<>("deleted");
    
    void setView(TextLineTableView v) {
        view=v;
     this.setOnCloseRequest(e->{
          model.getJob().exitedLineTable();
                    }
     );
     this.setScene(new Scene(this.view));
        this.show();
        
    }

    void setModel(TextLineTableModel item) {
        this.model=item;
        sequenceNumber.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TextSequenceHeader, Long>, ObservableValue<Long>>() {
            @Override
            public ObservableValue<Long> call(TableColumn.CellDataFeatures<TextSequenceHeader, Long> param) {
                return new SimpleLongProperty(param.getValue().getSequence().getSequenceno()).asObject();
            }
        });
        file.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TextSequenceHeader, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TextSequenceHeader, String> param) {
               return param.getValue().textFileProperty();
            } 
        });
        md5.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TextSequenceHeader, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TextSequenceHeader, String> param) {
                return param.getValue().md5Property();
            }
        });
        
        timeStamp.setCellValueFactory(p->{
            return p.getValue().timestampProperty();
            
        });
        numberOfRuns.setCellValueFactory(p->{
            return new SimpleLongProperty(p.getValue().getNumberOfRuns()).asObject();
        });
        modified.setCellValueFactory(p->{
            return p.getValue().modifiedProperty();
        });
        deleted.setCellValueFactory(p->{
            return p.getValue().deletedProperty();
        });
        
        textTable.getColumns().addAll(sequenceNumber,file,timeStamp,md5,numberOfRuns,modified,deleted);
        
        List<TextSequenceHeader> contents=model.getTextSequenceHeaders();
        textTable.getItems().addAll(contents);
        
        textTable.setRowFactory(r->{
            ContextMenu contextMenu=new ContextMenu();
            MenuItem history=new MenuItem("show history");
            contextMenu.getItems().add(history);
            TableRow<TextSequenceHeader> row=new TableRow<TextSequenceHeader>(){
                
                @Override
                protected void updateItem(TextSequenceHeader item,boolean empty){
                    super.updateItem(item, empty);
                    if(item==null || empty){
                        setText(null);
                        setGraphic(null);
                    }if(item!=null){
                        ContextMenu cm=new ContextMenu();
                        cm.getItems().add(history);
                        setContextMenu(cm);
                    }
                }
            };
            
            history.setOnAction(e->{
                TextSequenceHeader selected=row.getItem();
                   TextHistoryModel thm=new TextHistoryModel();
                   thm.setHistory(selected.getHistoryProperty().get());
                   TextHistoryView view=new TextHistoryView(thm);
            });
        
            return row;
        
        });
    }
    
    
    
    
}
