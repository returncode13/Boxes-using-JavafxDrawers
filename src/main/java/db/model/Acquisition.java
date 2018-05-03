/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author sharath
 */

@Entity
@Table(name="acquisition",schema = "public")
public class Acquisition implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="seq_fk",nullable=false)
    private Sequence sequence;
    
    @ManyToOne
    @JoinColumn(name="sub_fk",nullable=false)
    private Subsurface subsurfaceFK;
   
    @Column(name="cable")
    private Long cable=-1L;
    
    @Column(name="firstChannel")
    private Long firstChannel=-1L;
    
    @Column(name="lastChannel")
    private Long lastChannel=-1L;
    
    @Column(name="gun")
    private Long gun=-1L;
    
    @Column(name="firstFFID")
    private Long firstFFID=-1L;
    
    @Column(name="lastFFID")
    private Long lastFFID=-1L;
    
    @Column(name="firstShot")
    private Long firstShot=-1L;
    
    @Column(name="lastShot")
    private Long lastShot=-1L;
    
    @Column(name="FGFFID")
    private Long firstGoodFFID=-1L;
    
    @Column(name="LGFFID")
    private Long lastGoodFFID=-1L;
    
    @Column(name="fgsp")
    private Long fgsp=-1L;
    
    @Column(name="lgsp")
    private Long lgsp=-1L;
    
    
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
    
    
    
    
    
    
    
}
