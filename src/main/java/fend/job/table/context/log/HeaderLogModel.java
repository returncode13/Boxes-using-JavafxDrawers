/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.context.log;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class HeaderLogModel {
      private List<VersionLogsModel> logsmodel=new ArrayList<>();

    

    public List<VersionLogsModel> getLogsmodel() {
        return logsmodel;
    }

    public void setLogsmodel(List<VersionLogsModel> logsmodel) {
        this.logsmodel = logsmodel;
    }
    
}
