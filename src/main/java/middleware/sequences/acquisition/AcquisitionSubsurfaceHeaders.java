/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.sequences.acquisition;

import db.model.Sequence;
import db.model.Subsurface;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AcquisitionSubsurfaceHeaders extends AcquisitionSequenceHeaders{
     private Long id;
    
    
    private Sequence sequence;
    
    
    private Subsurface subsurfaceFK;
   
    
    private Long cable=0L;
    
    
    private Long firstChannel=0L;
    
  
    private Long lastChannel=0L;
    
    
    private Long gun=0L;
    
    
    private Long firstFFID=0L;
    
   
    private Long lastFFID=0L;
    
    
    private Long firstShot=0L;
    
    
    private Long lastShot=0L;
    
    
    private Long firstGoodFFID=0L;
    
    
    private Long lastGoodFFID=0L;
    
    
    private Long fgsp=0L;
    
   
    private Long lgsp=0L;
    
    private String subsurfaceName;

    public String getSubsurfaceName() {
        return subsurfaceName;
    }

    public void setSubsurfaceName(String subsurfaceName) {
        this.subsurfaceName = subsurfaceName;
    }
    
    
           
     @Override
    public Long getId() {
        return id;
    }
    
    
     @Override
    public Sequence getSequence() {
        return sequence;
    }

     @Override
    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

     @Override
    public Subsurface getSubsurfaceFK() {
        return subsurfaceFK;
    }

     @Override
    public void setSubsurfaceFK(Subsurface subsurfaceFK) {
        this.subsurfaceFK = subsurfaceFK;
    }

     @Override
    public Long getCable() {
        if(cable==null) cable=-1L;
        return cable;
    }

     @Override
    public void setCable(Long cable) {
        this.cable = cable;
    }

     @Override
    public Long getFirstChannel() {
        if(firstChannel==null) firstChannel=-1L;
        return firstChannel;
    }

     @Override
    public void setFirstChannel(Long firstChannel) {
        this.firstChannel = firstChannel;
    }

     @Override
    public Long getLastChannel() {
        if(lastChannel==null) lastChannel=-1L;
        return lastChannel;
    }

     @Override
    public void setLastChannel(Long lastChannel) {
        this.lastChannel = lastChannel;
    }

     @Override
    public Long getGun() {
        if(gun==null) gun=-1L;
        return gun;
    }

     @Override
    public void setGun(Long gun) {
        this.gun = gun;
    }

     @Override
    public Long getFirstFFID() {
        if(firstFFID==null) firstFFID=-1L;
        return firstFFID;
    }

     @Override
    public void setFirstFFID(Long firstFFID) {
        this.firstFFID = firstFFID;
    }

     @Override
    public Long getLastFFID() {
        if(lastFFID==null) lastFFID=-1L;
        return lastFFID;
    }

     @Override
    public void setLastFFID(Long lastFFID) {
        this.lastFFID = lastFFID;
    }

     @Override
    public Long getFirstShot() {
        if(firstShot==null) firstShot=-1L;
        return firstShot;
    }

     @Override
    public void setFirstShot(Long firstShot) {
        this.firstShot = firstShot;
    }

     @Override
    public Long getLastShot() {
        if(lastShot==null) lastShot=-1L;
        return lastShot;
    }

     @Override
    public void setLastShot(Long lastShot) {
        this.lastShot = lastShot;
    }

     @Override
    public Long getFirstGoodFFID() {
        // System.out.println("middleware.sequences.acquisition.AcquisitionSubsurfaceHeaders.getFirstGoodFFID(): returning "+firstGoodFFID+" sub: "+subsurfaceName);
        if(firstGoodFFID==null) firstGoodFFID=-1L; 
        return firstGoodFFID;
    }

     @Override
    public void setFirstGoodFFID(Long firstGoodFFID) {
        this.firstGoodFFID = firstGoodFFID;
    }

     @Override
    public Long getLastGoodFFID() {
        if(lastGoodFFID==null) lastGoodFFID=-1L;
        return lastGoodFFID;
    }

     @Override
    public void setLastGoodFFID(Long lastGoodFFID) {
        this.lastGoodFFID = lastGoodFFID;
    }

     @Override
    public Long getFgsp() {
        if(fgsp==null) fgsp=-1L;
        return fgsp;
    }

     @Override
    public void setFgsp(Long fgsp) {
        this.fgsp = fgsp;
    }

     @Override
    public Long getLgsp() {
        if(lgsp==null) lgsp=-1L;
        return lgsp;
    }

     @Override
    public void setLgsp(Long lgsp) {
        this.lgsp = lgsp;
    }
}
