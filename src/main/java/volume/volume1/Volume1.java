/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package volume.volume1;

import java.io.File;
import java.util.Objects;
import java.util.UUID;
import javafx.beans.property.StringProperty;
import javafx.scene.shape.Box;
import volume.volume0.Volume0;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * Type 1 Volumes. logs under ../200../logs
 */
public class Volume1 implements Volume0{
    private final Long type=1L;
    private Long id;
    private StringProperty name;
    private File volume;
    private Box parentBox;

    public Volume1(Box parentBox) {
        id=UUID.randomUUID().getMostSignificantBits();
        this.parentBox = parentBox;
    }
    
    
    @Override
    public Long getType() {
        return this.type;
    }

    @Override
    public StringProperty getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

  

    @Override
    public File getVolume() {
        return this.volume;
    }
    
    @Override
    public void setVolume(File f) {
       this.volume=f;
    }

    @Override
    public Box getParentBox() {
        return this.parentBox;
    }

  
    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }

     /**
     * equals() and hashCode()
     */
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.volume);
        hash = 97 * hash + Objects.hashCode(this.parentBox);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Volume1 other = (Volume1) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.volume, other.volume)) {
            return false;
        }
        if (!Objects.equals(this.parentBox, other.parentBox)) {
            return false;
        }
        return true;
    }
    
    
    
   
    
    
}
