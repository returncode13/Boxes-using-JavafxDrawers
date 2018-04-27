/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Header;
import db.model.Job;
import db.model.Log;
import db.model.Subsurface;
import db.model.Volume;
import db.model.Workflow;
import java.util.List;

/**
 *
 * @author naila0152
 */
public interface LogDAO {
    public void createLogs(Log l);
    public Log getLogs(Long lid);
    public void updateLogs(Long lid, Log newL);
    public void deleteLogs(Long lid);
    
    public List<Log> getLogsFor(Header h);  //get the logs for which the foreign key=h.id
    public List<Log> getLogsFor(Volume v);
    public List<Log> getLogsFor(Volume v,Boolean completed,Boolean running,Boolean errored,Boolean cancelled);
    public List<Log> getLogsFor(Volume v,Subsurface subline);
    public List<Log> getLogsFor(Volume v,Subsurface subline,Boolean completed,Boolean running,Boolean errored,Boolean cancelled);
    public Log getLatestLogFor(Volume v, Subsurface sub);
    public List<Log> getLogsFor(Volume v, Workflow workflow);
    public List<Log> getLogsFor(Volume v,Long seq);       //get logs for seq
    public List<Log> getSequencesFor(Volume v);           //get distinct sequences in volume
    public List<Log> getSubsurfacesFor(Volume v, Long seq); //get distinct subsurfaces for  volume,seq

    public Log getLogsFor(Volume volume, Subsurface linename, String timestamp, String filename) throws Exception;

    public List<Log> getLogsFor(Job dbJob);

    public List<Log> getLogsByTimeFor(Job dbJob);

    public List<Log> getLogsByTimeFor(Job dbJob, Subsurface sub);
    public void bulkUpdateOnLogs(Volume v,Workflow w);    //usually a lot of logs have their wk=null and this is to do a bulk update on all of them

    public void bulkUpdateOnLogs(Volume volume, Header hdr,Subsurface sub);

    public String getLatestLogTimeFor(Volume dbVol);

    public void deleteLogsFor(Volume vol);
}
