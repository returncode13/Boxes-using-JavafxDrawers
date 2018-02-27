/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.log;

import java.io.File;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */

 public class VersionLogsModel {
    
   Long version;
   String timestamp;
   File logfile;

    public VersionLogsModel(Long version, String timestamp, String logfile) {
        this.version = version;
        this.timestamp = timestamp;
        this.logfile = new File(logfile);
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
   
    
   
   
   
}

