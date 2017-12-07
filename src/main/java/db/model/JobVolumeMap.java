/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author sharath nair
 */
@Entity
@Table(name = "JobVolumeMap",schema = "obpmanager")
public class JobVolumeMap implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name= "job_fk",nullable = false)
    private Job job;
    
    @ManyToOne
    @JoinColumn(name = "volume_fk",nullable = false)
    private Volume volume;

    public JobVolumeMap() {
    }

    public JobVolumeMap(Job jobStep, Volume volume) {
        this.job = jobStep;
        this.volume = volume;
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    
    
    
    
    
    
}
