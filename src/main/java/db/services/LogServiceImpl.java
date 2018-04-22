/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.LogDAOImpl;
import db.model.Header;
import db.model.Log;
import db.model.Volume;
import db.model.Workflow;
import java.util.List;
import db.dao.LogDAO;
import db.model.Job;
import db.model.Subsurface;

/**
 *
 * @author sharath nair
 */
public class LogServiceImpl implements LogService{

    LogDAO ldao=new LogDAOImpl();
    @Override
    public void createLogs(Log l) {
        ldao.createLogs(l);
    }

    @Override
    public Log getLogs(Long lid) {
       return ldao.getLogs(lid);
    }

    @Override
    public void updateLogs(Long lid, Log newL) {
        ldao.updateLogs(lid, newL);
    }

    @Override
    public void deleteLogs(Long lid) {
        ldao.deleteLogs(lid);
    }

    @Override
    public List<Log> getLogsFor(Header h) {
        return ldao.getLogsFor(h);
    }

    @Override
    public List<Log> getLogsFor(Volume v) {
        return ldao.getLogsFor(v);
    }

    @Override
    public List<Log> getLogsFor(Volume v, Subsurface subline) {
        return ldao.getLogsFor(v, subline);
    }

    @Override
    public Log getLatestLogFor(Volume v, Subsurface subline) {
        return ldao.getLatestLogFor(v, subline);
    }

    @Override
    public List<Log> getLogsFor(Volume v, Boolean completed, Boolean running, Boolean errored, Boolean cancelled) {
        return ldao.getLogsFor(v, completed, running, errored, cancelled);
    }

    @Override
    public List<Log> getLogsFor(Volume v, Subsurface subline, Boolean completed, Boolean running, Boolean errored, Boolean cancelled) {
        return ldao.getLogsFor(v, subline, completed, running, errored, cancelled);
    }

    @Override
    public List<Log> getLogsFor(Volume v, Workflow workflow) {
        return ldao.getLogsFor(v, workflow);
    }

    @Override
    public List<Log> getLogsFor(Volume v, Long seq) {
        return ldao.getLogsFor(v, seq);
    }

    @Override
    public List<Log> getSequencesFor(Volume v) {
        return ldao.getSequencesFor(v);
    }

    @Override
    public List<Log> getSubsurfacesFor(Volume v, Long seq) {
        return ldao.getSubsurfacesFor(v, seq);
    }

    @Override
    public Log getLogsFor(Volume volume, Subsurface linename, String timestamp, String filename) throws Exception{
        return ldao.getLogsFor(volume,linename,timestamp,filename);
    }

    @Override
    public List<Log> getLogsFor(Job dbJob) {
        return ldao.getLogsFor(dbJob);
    }

    @Override
    public List<Log> getLogsByTimeFor(Job dbJob) {
        return ldao.getLogsByTimeFor(dbJob);
    }

    @Override
    public List<Log> getLogsByTimeFor(Job dbJob, Subsurface sub) {
        return ldao.getLogsByTimeFor(dbJob,sub);
    }

    @Override
    public void bulkUpdateOnLogs(Volume v, Workflow w) {
           ldao.bulkUpdateOnLogs(v, w);
    }

    @Override
    public void bulkUpdateOnLogs(Volume volume, Header hdr,Subsurface sub) {
        ldao.bulkUpdateOnLogs(volume, hdr,sub);
    }

    @Override
    public String getLatestLogTimeFor(Volume dbVol) {
       return ldao.getLatestLogTimeFor(dbVol);
    }

   
    
}
