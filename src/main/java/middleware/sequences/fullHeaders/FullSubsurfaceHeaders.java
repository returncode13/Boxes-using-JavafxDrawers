/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.sequences.fullHeaders;

import db.model.Sequence;
import db.model.Subsurface;
import db.model.Volume;
import fend.volume.volume0.Volume0;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class FullSubsurfaceHeaders extends FullSequenceHeaders{

    

   
    private Subsurface subsurface;
    private Volume0 volume;
    
    public FullSubsurfaceHeaders(Volume0 volume) {
        super();
        isSequence=false;
        isSubsurface=true;
        this.volume=volume;
    }

  

    public Subsurface getSubsurface() {
        return subsurface;
    }

    public void setSubsurface(Subsurface subsurface) {
        this.subsurface = subsurface;
    }

    public Volume0 getVolume() {
        return volume;
    }
    
    
    
}
