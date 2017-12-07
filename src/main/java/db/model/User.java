/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
 * @author sharath nair
 */
@Entity
@Table(name="User",schema="obpmanager")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy ="user")
    private Set<Workspace> workspaces;
    
    @OneToMany(mappedBy ="user")
    private Set<QcTable> qctables;

    @OneToMany(mappedBy = "user")
    private Set<Doubt> doubts;
    
    public Long getId() {
        return id;
    }
    
    public Set<Workspace> getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(Set<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    public Set<QcTable> getQctables() {
        return qctables;
    }

    public void setQctables(Set<QcTable> qctables) {
        this.qctables = qctables;
    }

    public Set<Doubt> getDoubts() {
        return doubts;
    }

    public void setDoubts(Set<Doubt> doubts) {
        this.doubts = doubts;
    }
    
    
    
    
}
