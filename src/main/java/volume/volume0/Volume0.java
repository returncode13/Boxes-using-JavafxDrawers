/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package volume.volume0;

import java.io.File;
import javafx.beans.property.StringProperty;
import javafx.scene.shape.Box;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface Volume0 {
    public Long getType();
    public Long getId();
    public void setId(Long id);
    public StringProperty getName();
    public void setName(String s);
    public File getVolume();
    public void setVolume(File f);
    public Box getParentBox();
    
   
}
