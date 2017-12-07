/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author sharath nair
 */
@Entity
@Table(name = "Descendant",schema = "obpmanager")
public class Descendant implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "job_fk")       
    private Job job;
    
    
    @ManyToOne
    @JoinColumn(name="descendant")
    private Job descendant;  

    public Job getDescendant() {
        return descendant;
    }

    public void setDescendant(Job descendant) {
        this.descendant = descendant;
    }
    

     
    
    public Descendant() {
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
