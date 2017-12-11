/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
@Entity
@Table(name="QcMatrixRow",schema="obpmanager")
public class QcMatrixRow {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 Long id;
 
 /*@ManyToOne
 @JoinColumn(name="volume_fk",nullable=false)
 private Volume volume;*/

 @ManyToOne
 @JoinColumn(name="job_fk",nullable=false)
 private Job job;
 
 
 
 
 
@ManyToOne
@JoinColumn(name="qctype_fk",nullable=false)
private QcType qctype;

@Column(name="present")
Boolean present;

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }

    


    /*  public Volume getVolume() {
    return volume;
    }
    
    public void setVolume(Volume volume) {
    this.volume = volume;
    }*/

    public QcType getQctype() {
        return qctype;
    }

    public void setQctype(QcType qctype) {
        this.qctype = qctype;
    }

    
    public Long getId() {
        return id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
    
    
    
    


}
