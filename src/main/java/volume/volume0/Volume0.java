/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package volume.volume0;

import fend.sequences.Subsurface;
import java.io.File;
import java.util.List;
import javafx.beans.property.StringProperty;
import job.job0.JobType0Model;


/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface Volume0 {
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

    public List<Subsurface> getSubsurfaces();
    
    
   
}
