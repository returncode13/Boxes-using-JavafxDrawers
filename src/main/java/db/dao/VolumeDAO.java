/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Job;
import db.model.Volume;
import db.model.Workspace;
import java.util.List;

/**
 *
 * @author sharath nair
 */
public interface VolumeDAO {
    public void createVolume(Volume v);
    public Volume getVolume(Long volid);
    public void updateVolume(Long volid,Volume newVol);
    public void deleteVolume(Long volid);

    public void startAlert(Volume v);
    public void stopAlert(Volume v);
    public void setHeaderExtractionFlag(Volume v);
    public void resetHeaderExtractionFlag(Volume v);
    public List<Volume> getVolumesForJob(Job job);

    public List<Volume> getAllVolumesIn(Workspace workspace);

   
    
    
}
