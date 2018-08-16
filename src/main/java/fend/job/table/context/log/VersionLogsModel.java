/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.context.log;

import java.io.File;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */

 public class VersionLogsModel {
    
   Long version;
   String timestamp;
   File logfile;
   Long workflowVersion;
   String linename;
   
   
    public VersionLogsModel(Long version, String timestamp, String logfile,Long wfversion,String line) {
        this.version = version;
        this.timestamp = timestamp;
        this.logfile = new File(logfile);
        this.workflowVersion=wfversion;
        this.linename=line;
    }

    public Long getVersion() {
        return version;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public File getLogfile() {
        return logfile;
    }

    public Long getWorkflowVersion() {
        return workflowVersion;
    }

    public String getLinename() {
        return linename;
    }
   
    
    
   
   
   
}

