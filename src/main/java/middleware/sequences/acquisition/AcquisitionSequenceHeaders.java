/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.sequences.acquisition;

import db.model.Sequence;
import db.model.Subsurface;
import java.util.ArrayList;
import java.util.List;
import middleware.sequences.SequenceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AcquisitionSequenceHeaders extends SequenceHeaders{
    
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
    
    private List<AcquisitionSubsurfaceHeaders> acquisitionSubsurfaceHeaders=new ArrayList<>();
    
    public Long getId() {
        return id;
    }
    
    
    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Subsurface getSubsurfaceFK() {
        return subsurfaceFK;
    }

    public void setSubsurfaceFK(Subsurface subsurfaceFK) {
        this.subsurfaceFK = subsurfaceFK;
    }

    public Long getCable() {
        return cable;
    }

    public void setCable(Long cable) {
        this.cable = cable;
    }

    public Long getFirstChannel() {
        return firstChannel;
    }

    public void setFirstChannel(Long firstChannel) {
        this.firstChannel = firstChannel;
    }

    public Long getLastChannel() {
        return lastChannel;
    }

    public void setLastChannel(Long lastChannel) {
        this.lastChannel = lastChannel;
    }

    public Long getGun() {
        return gun;
    }

    public void setGun(Long gun) {
        this.gun = gun;
    }

    public Long getFirstFFID() {
        return firstFFID;
    }

    public void setFirstFFID(Long firstFFID) {
        this.firstFFID = firstFFID;
    }

    public Long getLastFFID() {
        return lastFFID;
    }

    public void setLastFFID(Long lastFFID) {
        this.lastFFID = lastFFID;
    }

    public Long getFirstShot() {
        return firstShot;
    }

    public void setFirstShot(Long firstShot) {
        this.firstShot = firstShot;
    }

    public Long getLastShot() {
        return lastShot;
    }

    public void setLastShot(Long lastShot) {
        this.lastShot = lastShot;
    }

    public Long getFirstGoodFFID() {
        return firstGoodFFID;
    }

    public void setFirstGoodFFID(Long firstGoodFFID) {
        this.firstGoodFFID = firstGoodFFID;
    }

    public Long getLastGoodFFID() {
        return lastGoodFFID;
    }

    public void setLastGoodFFID(Long lastGoodFFID) {
        this.lastGoodFFID = lastGoodFFID;
    }

    public Long getFgsp() {
        return fgsp;
    }

    public void setFgsp(Long fgsp) {
        this.fgsp = fgsp;
    }

    public Long getLgsp() {
        return lgsp;
    }

    public void setLgsp(Long lgsp) {
        this.lgsp = lgsp;
    }

    public List<AcquisitionSubsurfaceHeaders> getAcquisitionSubsurfaceHeaders() {
        return acquisitionSubsurfaceHeaders;
    }

    public void setAcquisitionSubsurfaceHeaders(List<AcquisitionSubsurfaceHeaders> acquisitionSubsurfaceHeaders) {
        this.acquisitionSubsurfaceHeaders = acquisitionSubsurfaceHeaders;
    }
    
    
}
