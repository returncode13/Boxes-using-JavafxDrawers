
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.Objects;
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
@Table(name = "Ancestor",schema = "obpmanager")
public class Ancestor implements Serializable{
    @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name ="job_fk")       
    private Job job;
    
    @ManyToOne
    @JoinColumn(name="ancestor")
    private Job ancestor;  

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.job);
        hash = 71 * hash + Objects.hashCode(this.ancestor);
        return hash;
    }

    

    @Override
    public boolean equals(Object obj) {
        Ancestor a=(Ancestor) obj;
        
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ancestor other = (Ancestor) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.job, other.job)) {
            return false;
        }
        if (!Objects.equals(this.ancestor, other.ancestor)) {
            return false;
        }
        return true;
    }

    
    
   
    public Ancestor() {
    }

  
    
    
    public Long getId() {
        return id;
    }

    public Job getAncestor() {
        return ancestor;
    }

    public void setAncestor(Job ancestor) {
        this.ancestor = ancestor;
    }

    
    
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
    
    
}
