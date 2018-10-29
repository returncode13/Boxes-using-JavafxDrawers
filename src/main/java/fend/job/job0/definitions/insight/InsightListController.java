/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.job0.definitions.insight;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import db.model.Job;
import db.services.JobService;
import db.services.JobServiceImpl;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class InsightListController {
    private InsightListModel model;
    private InsightListView view;
    private JobService jobService=new JobServiceImpl();
    
    @FXML
    private JFXListView<InsightVersion> insightList;
    
    
    
    
    void setModel(InsightListModel item) {
       model=item;
       Job job=jobService.getJob(model.getParentJob().getId());
       String insightsPresent=job.getInsightVersions();
       String[] parts;
       if(insightsPresent!=null){
           parts=insightsPresent.split(";");
       }else{
           parts=new String[0];
       }
       /**
        * build the insight list using the list built during the load of the workspace 
        **/
           List<String> ins=model.getParentJob().getWorkspaceModel().getInsightVersions();
           System.out.println("fend.job.definitions.insight.InsightListController.setModel(): InsightList from workspace size: "+ins.size());
           List<InsightVersion> insVersionList=new ArrayList<>();
           for(String i:ins){
               InsightVersion is=new InsightVersion();
               is.setInsightVersion(i);
               is.setParentJob(model.getParentJob());
               if(insightsPresent!=null){
                   for(String part:parts){
                       if(part.equals(i)){
                           is.setCheckedByUser(true);
                       }
                   }
                   
               }else{
                   is.setCheckedByUser(false);
               }
               //System.out.println("fend.job.definitions.insight.InsightListController.setModel(): adding "+is.getInsightVersion());
               insVersionList.add(is);
           }
           model.setInsightVersionList(insVersionList);
       
           
    }

    void setView(InsightListView vi) {
        view=vi;
        insightList.setCellFactory(e->new InsightListCell());
       
        insightList.setItems(model.getInsightVersionList());
    }
    
}
