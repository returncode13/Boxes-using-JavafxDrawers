/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
@Entity
@Table(name="Link",schema = "obpmanager")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="parent_fk")
    private Job parent;
    
    @ManyToOne
    @JoinColumn(name="child_fk")
    private Job child;

    @ManyToOne
    @JoinColumn(name="dot_fk")
    private Dot dot;
    
    
  
    
    public Link() {
    }
    
    
    public Long getId() {
        return id;
    }

    public Job getParent() {
        return parent;
    }

    public void setParent(Job parent) {
        this.parent = parent;
    }

    public Job getChild() {
        return child;
    }

    public void setChild(Job child) {
        this.child = child;
    }

  

    public Dot getDot() {
        return dot;
    }

    public void setDot(Dot dot) {
        this.dot = dot;
    }
    
    
    

    
}
