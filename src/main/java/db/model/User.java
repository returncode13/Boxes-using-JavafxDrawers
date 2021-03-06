/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sharath nair
 */
@Entity
@Table(name="PQUser",schema="obpmanager")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "user_id",nullable=false)
    private Long id;
    
    @Column(name="fullName",nullable=false,length=2048)
    private String fullName;
    
    @Column(name="initials",nullable=false,length=3)
    private String initials;
    
    @OneToMany(mappedBy = "owner")
    private Set<Workspace> ownedWorkspaces=new HashSet<>();
    
    
    @OneToMany(mappedBy = "pk.user")
    private Set<UserWorkspace> userWorkspaceEntries=new HashSet<>();
    
    /* @OneToMany(mappedBy ="user")
    private Set<Workspace> workspaces;*/
    
    /* @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
    name = "user_workspace",
    joinColumns = { @JoinColumn(name = "id") },
    inverseJoinColumns = { @JoinColumn(name = "id") }
    
    )*/
    /*@ManyToMany(mappedBy = "users",fetch = FetchType.EAGER)
    private Set<Workspace> workspaces = new HashSet<>();*/
    /* @OneToMany(mappedBy ="pk.userid",fetch=FetchType.EAGER)
    private Set<UserWorkspace> userWorkspaces=new HashSet<>();*/
    
    @OneToMany(mappedBy ="user")
    private Set<QcTable> qctables;

    @OneToMany(mappedBy = "user")
    private Set<DoubtStatus> doubtStatuses;
    
    public Long getId() {
        return id;
    }
    
    /* public Set<Workspace> getWorkspaces() {
    return workspaces;
    }
    
    public void setWorkspaces(Set<Workspace> workspaces) {
    this.workspaces = workspaces;
    }*/

    public Set<QcTable> getQctables() {
        return qctables;
    }

    public void setQctables(Set<QcTable> qctables) {
        this.qctables = qctables;
    }

    public Set<DoubtStatus> getDoubtStatuses() {
        return doubtStatuses;
    }

    public void setDoubtStatuses(Set<DoubtStatus> doubtStatuses) {
        this.doubtStatuses = doubtStatuses;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    /*  public Set<Workspace> getWorkspaces() {
    return workspaces;
    }
    
    public void setWorkspaces(Set<Workspace> workspaces) {
    this.workspaces = workspaces;
    }
    
    public void addToWorkspaces(Workspace workspace){
    this.workspaces.add(workspace);
    }
    
    public void removeFromWorkspaces(Workspace workspace){
    this.workspaces.remove(workspace);
    
    }
    */
    public Set<Workspace> getOwnedWorkspaces() {
        return ownedWorkspaces;
    }

    public void setOwnedWorkspaces(Set<Workspace> ownedWorkspaces) {
        this.ownedWorkspaces = ownedWorkspaces;
    }

    public void addToOwnedWorkspaces(Workspace workspace){
        this.ownedWorkspaces.add(workspace);
    }
    
     public void removeFromOwnedWorkspaces(Workspace workspace){
        this.ownedWorkspaces.remove(workspace);
    }

    public Set<UserWorkspace> getUserWorkspaceEntries() {
        return userWorkspaceEntries;
    }

    public void setUserWorkspaceEntries(Set<UserWorkspace> userWorkspaceEntries) {
        this.userWorkspaceEntries = userWorkspaceEntries;
    }
     
     
     
     
     
    
     /* public Set<UserWorkspace> getUserWorkspaces() {
     return userWorkspaces;
     }
     
     public void setUserWorkspaces(Set<UserWorkspace> userWorkspaces) {
     this.userWorkspaces = userWorkspaces;
     }*/

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.id);
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
        final User other = (User) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
}
