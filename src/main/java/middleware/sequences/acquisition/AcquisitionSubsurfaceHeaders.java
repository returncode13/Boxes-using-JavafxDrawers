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
   
    
    private Long cable;
    
    
    private Long firstChannel;
    
  
    private Long lastChannel;
    
    
    private Long gun;
    
    
    private Long firstFFID;
    
   
    private Long lastFFID;
    
    
    private Long firstShot;
    
    
    private Long lastShot;
    
    
    private Long firstGoodFFID;
    
    
    private Long lastGoodFFID;
    
    
    private Long fgsp;
    
   
    private Long lgsp;
    
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
        return cable;
    }

     @Override
    public void setCable(Long cable) {
        this.cable = cable;
    }

     @Override
    public Long getFirstChannel() {
        return firstChannel;
    }

     @Override
    public void setFirstChannel(Long firstChannel) {
        this.firstChannel = firstChannel;
    }

     @Override
    public Long getLastChannel() {
        return lastChannel;
    }

     @Override
    public void setLastChannel(Long lastChannel) {
        this.lastChannel = lastChannel;
    }

     @Override
    public Long getGun() {
        return gun;
    }

     @Override
    public void setGun(Long gun) {
        this.gun = gun;
    }

     @Override
    public Long getFirstFFID() {
        return firstFFID;
    }

     @Override
    public void setFirstFFID(Long firstFFID) {
        this.firstFFID = firstFFID;
    }

     @Override
    public Long getLastFFID() {
        return lastFFID;
    }

     @Override
    public void setLastFFID(Long lastFFID) {
        this.lastFFID = lastFFID;
    }

     @Override
    public Long getFirstShot() {
        return firstShot;
    }

     @Override
    public void setFirstShot(Long firstShot) {
        this.firstShot = firstShot;
    }

     @Override
    public Long getLastShot() {
        return lastShot;
    }

     @Override
    public void setLastShot(Long lastShot) {
        this.lastShot = lastShot;
    }

     @Override
    public Long getFirstGoodFFID() {
        return firstGoodFFID;
    }

     @Override
    public void setFirstGoodFFID(Long firstGoodFFID) {
        this.firstGoodFFID = firstGoodFFID;
    }

     @Override
    public Long getLastGoodFFID() {
        return lastGoodFFID;
    }

     @Override
    public void setLastGoodFFID(Long lastGoodFFID) {
        this.lastGoodFFID = lastGoodFFID;
    }

     @Override
    public Long getFgsp() {
        return fgsp;
    }

     @Override
    public void setFgsp(Long fgsp) {
        this.fgsp = fgsp;
    }

     @Override
    public Long getLgsp() {
        return lgsp;
    }

     @Override
    public void setLgsp(Long lgsp) {
        this.lgsp = lgsp;
    }
}
