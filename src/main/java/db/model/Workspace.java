/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author sharath nair
 */
@Entity
@Table(name = "Workspace",schema = "obpmanager")

public class Workspace implements Serializable{
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name",length = 1025)
    private String name;
    
    /* @Column(name = "hashSessions",length = 1025)
    private String hashSessions;*/
    
    @OneToMany(mappedBy="workspace")
    private Set<Job> jobs;
  
    @ManyToOne
    @JoinColumn(name="user_fk")
    private User user;
   
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

    public void setId(Long id) {
        this.id = id;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
   
   
   
    
}
