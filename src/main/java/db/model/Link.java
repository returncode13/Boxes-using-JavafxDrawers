/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.util.Objects;
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
    
    
    @OneToMany(mappedBy = "link")
    private Set<Doubt> doubts;                  //all doubts associated with this link
    
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

    public Set<Doubt> getDoubts() {
        return doubts;
    }

    public void setDoubts(Set<Doubt> doubts) {
        this.doubts = doubts;
    }

    public Dot getDot() {
        return dot;
    }

    public void setDot(Dot dot) {
        this.dot = dot;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.parent);
        hash = 29 * hash + Objects.hashCode(this.child);
        hash = 29 * hash + Objects.hashCode(this.dot);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Link other = (Link) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.parent, other.parent)) {
            return false;
        }
        if (!Objects.equals(this.child, other.child)) {
            return false;
        }
        if (!Objects.equals(this.dot, other.dot)) {
            return false;
        }
        return true;
    }
    
    
    

    
}
