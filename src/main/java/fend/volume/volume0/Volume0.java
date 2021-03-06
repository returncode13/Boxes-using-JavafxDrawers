/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.volume.volume0;

import db.model.Volume;
import middleware.sequences.SubsurfaceHeaders;
import java.io.File;
import java.util.List;
import javafx.beans.property.StringProperty;
import fend.job.job0.JobType0Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface Volume0 {

    public static Long PROCESS_2D=1L;
    public static Long SEGD_LOAD=2L;
    public static Long ACQUISITION=3L;
    public static Long TEXT=4L;
    public static Long SEGY=5L;
    public Long getType();
    public Long getId();
    /**
     * Use the setId method only while loading a session
     */
    public void setId(Long id);
    public StringProperty getName();
    public void setName(String s);
    public File getVolume();
    public void setVolume(File f);
    public JobType0Model getParentJob();
    
    public List<SubsurfaceHeaders> getSubsurfaces();
    public File getLogFolder();
     
    public BooleanProperty deleteProperty();
    public void delete(boolean b);
    
    public void setDbVolume(Volume v);
    public Volume getDbVolume();
    
   
    
   
}
