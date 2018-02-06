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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author sharath nair
 */
@Entity
@Table(name = "Workspace",schema = "obpmanager")

public class Workspace implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workspace_id",nullable=false)
    private Long id;
    
    @Column(name = "name",length = 1025)
    private String name;
    
    @ManyToOne
    @JoinColumn(name="owner")
    private User owner;
    /* @Column(name = "hashSessions",length = 1025)
    private String hashSessions;*/
    
    @OneToMany(mappedBy="workspace",fetch = FetchType.EAGER)
    private Set<Job> jobs;
    
     @OneToMany(mappedBy="workspace",fetch = FetchType.EAGER)
    private Set<Dot> dots;
  
    // @ManyToMany(mappedBy = "workspaces",fetch = FetchType.EAGER)
      @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(
        name = "workspace_user", 
        joinColumns = { @JoinColumn(name = "workspace_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "user_id") }
        
    )
     private Set<User> users=new HashSet<>();
     
     
      /* @OneToMany(mappedBy = "pk.workspace",fetch = FetchType.EAGER)
      private Set<UserWorkspace> userWorkspaces=new HashSet<>();*/
     /*@ManyToOne
     @JoinColumn(name="user_fk")
     private User user;*/
   
   /*@OneToMany(mappedBy = "sessions",cascade = CascadeType.ALL,orphanRemoval = true)
   private Set<QcType> qcTypes;*/

    /* @OneToMany(mappedBy = "sessions",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<User> users;
    */
   /*@OneToMany(mappedBy = "sessions",cascade = CascadeType.ALL,orphanRemoval = true)
   private Set<ObpManagerLog> obpmanagerLogs;
   */
   
    /* public Workspace(String nameSessions, String hashSessions) {
    this.name = nameSessions;
    /*this.hashSessions = hashSessions;
    }*/

    public Workspace() {
    }

   
   
    public Long getId() {
        return id;
    }

    /*public void setId(Long id) {
    this.id = id;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }
    
    
    /*
    public String getHashSessions() {
    return hashSessions;
    }
    
    public void setHashSessions(String hashSessions) {
    this.hashSessions = hashSessions;
    }*/
    

    @Override
    public String toString() {
        return "Sessions{" + "idSessions=" + id + ", nameSessions=" + name + '}';
    }

    /*public Set<QcType> getQcTypes() {
    return qcTypes;
    }
    
    public void setQcTypes(Set<QcType> qcTypes) {
    this.qcTypes = qcTypes;
    }*/

    /* public User getUser() {
    return user;
    }
    
    public void setUser(User user) {
    this.user = user;
    }*/

    public Set<Dot> getDots() {
        return dots;
    }

    public void setDots(Set<Dot> dots) {
        this.dots = dots;
    }

    public void addToDots(Dot dbDot) {
        this.dots.add(dbDot);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    
    public void addToUsers(User user) {
        this.users.add(user);
    }
    
    public void removeUser(User user){
        this.users.remove(user);
    }
    
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    /* public Set<UserWorkspace> getUserWorkspaces() {
    return userWorkspaces;
    }
    
    public void setUserWorkspaces(Set<UserWorkspace> userWorkspaces) {
    this.userWorkspaces = userWorkspaces;
    }*/

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Workspace other = (Workspace) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
   
   
   
    
}
