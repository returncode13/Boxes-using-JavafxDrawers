/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.context.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class HeaderLogController extends Stage{
     Logger logger=Logger.getLogger(HeaderLogController.class.getName());
      private HeaderLogModel headerLogModel;
    private HeaderLogView headerLogView;
    ObservableList<Long> versionsObsList;
    
    
    @FXML
    private TabPane tabPane;

    void setModel(HeaderLogModel lsm) {
        
        try{
        headerLogModel=lsm;
        List<VersionLogsModel> tabContents=headerLogModel.getLogsmodel();
        
        for (Iterator<VersionLogsModel> iterator = tabContents.iterator(); iterator.hasNext();) {
            VersionLogsModel versionsTab = iterator.next();
            File logfile=versionsTab.getLogfile();
            System.out.println("fend.job.table.log.HeaderLogController.setModel():  reading the logfile  "+logfile.getAbsolutePath());
            logger.info("fend.job.table.log.HeaderLogController.setModel() reading logfile "+logfile.getAbsolutePath());
            FileReader fr=null;
            BufferedReader br= null;
            int max=100;
            List<String> contentsList=new ArrayList<>();
            String contents=new String();
            try {
              //  br = new BufferedReader(new BufferedReader(new FileReader(logfile),8056));
              br=new BufferedReader(new InputStreamReader(new FileInputStream(logfile)));
                
                String lines;
                int count=0;
                while((lines=br.readLine())!=null){
                    contentsList.add(lines+"\n");
                    //contents+=lines+"\n";
                    /*if(count==max){
                        contentsList.add(contents);
                        contents=new String();
                    }*/
                };
                
                System.out.println("fend.job.table.log.HeaderLogController.setModel()");
                System.out.println("fend.job.table.log.HeaderLogController.setModel(): contents.size(): "+contents.length());
            } catch (FileNotFoundException ex) {
                logger.severe(ex.getMessage());
                Logger.getLogger(HeaderLogController.class.getName()).log(Level.SEVERE, null, ex);
                
            } catch (IOException ex) {
                logger.severe(ex.getMessage());
                Logger.getLogger(HeaderLogController.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try{
                    if(br!=null)br.close();
                    if(fr!=null)fr.close();
                    
                } catch (IOException ex) {
                    logger.severe(ex.getMessage());
                    Logger.getLogger(HeaderLogController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            Tab tab= new Tab();
            tab.setText(" log ver:"+versionsTab.getVersion().toString()+" workflow version:"+versionsTab.getWorkflowVersion());
            HBox hbox=new HBox();
            //hbox.getChildren().add(new Label(""+contents));
            TextArea ta=new TextArea();
            ListView<String> lview=new ListView<>(FXCollections.observableList(contentsList));
            //ta.wrapTextProperty().setValue(Boolean.TRUE);
           // String ss=new String();
           // System.out.println("fend.session.node.headers.logger.LogsController.setModel(): about to enter the for loop to set string");
           /* for(String s:contentsList){
                //if(s.matches("\\d{4}-\\d{2}-\\d{2}"))ss+="\n";
                ss+=s;
            }*/
            System.out.println("fend.job.table.log.HeaderLogController.setModel(): about to set textArea contents: ");
           // ta.setText(contentsList.toString());
           // ta.setEditable(Boolean.FALSE);
          //  ta.prefHeightProperty().bind(hbox.heightProperty());
           // ta.prefWidthProperty().bind(hbox.widthProperty());
           // hbox.getChildren().add(ta);
           hbox.getChildren().add(lview);
           lview.prefHeightProperty().bind(hbox.heightProperty());
           lview.prefWidthProperty().bind(hbox.widthProperty());
          //  ta.prefHeightProperty().bind(hbox.heightProperty());
            hbox.setAlignment(Pos.CENTER);
            tab.setContent(hbox);
            tabPane.getTabs().add(tab);
        }
        }catch(Exception ex){
            logger.severe(ex.getMessage());
        }
        
    }

    void setView(HeaderLogView aThis) {
        try{
       headerLogView=aThis;
       this.setScene(new Scene(headerLogView));
       this.setTitle("log for "+headerLogModel.getLogsmodel().get(0).getLinename());
       this.showAndWait();
        }catch(Exception ex){
            logger.severe(ex.getMessage());
        }
    }
}
