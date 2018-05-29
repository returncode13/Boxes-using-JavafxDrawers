/*
 * To change this license header, choose License Header in Project Properties.
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
@Table(name="subsurface",schema="public")

public class Subsurface implements Serializable {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="subsurface")
    private String subsurface;
    
    @ManyToOne
    @JoinColumn(name="seq_fk")
    private Sequence sequence;
    
    @OneToMany(mappedBy = "subsurfaceFK")
    private Set<Acquisition> acquisition;
    
    @OneToMany(mappedBy = "subsurface")
    private Set<Summary> summaries;

    
    
    
    @OneToMany(mappedBy = "subsurface")
    private Set<Header> headers;
    
    @OneToMany(mappedBy = "subsurface")
    private Set<Pheader> pheaders;
    
    @OneToMany(mappedBy = "subsurface")
    private Set<Theader> theaders;
    
    @OneToMany(mappedBy = "subsurface")
    private Set<Fheader> fheaders;
    
    @OneToMany(mappedBy = "subsurface")
    private Set<QcTable> qctables;
    
    @OneToMany(mappedBy = "subsurface")
    private Set<Doubt> doubts;
    
    @OneToMany(mappedBy = "subsurface")
    private Set<Log> logs ;
    
    @OneToMany(mappedBy ="pk.subsurface")
    private Set<SubsurfaceJob> subsurfaceJobs=new HashSet<>();
    
    public Subsurface() {
    }

    public Set<Doubt> getDoubts() {
        return doubts;
    }

    public void setDoubts(Set<Doubt> doubts) {
        this.doubts = doubts;
    }
    
    public Set<Acquisition> getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(Set<Acquisition> acquisition) {
        this.acquisition = acquisition;
    }

    public Set<Header> getHeaders() {
    return headers;
    }
    
    public void setHeaders(Set<Header> headers) {
    this.headers = headers;
    }

    public Set<Pheader> getPheaders() {
        return pheaders;
    }

    public void setPheaders(Set<Pheader> pheaders) {
        this.pheaders = pheaders;
    }
    
    
    
    
    public String getSubsurface() {
        return subsurface;
    }

    public void setSubsurface(String subsurface) {
        this.subsurface = subsurface;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Long getId() {
        return id;
    }

   

    public Set<QcTable> getQctables() {
        return qctables;
    }

    public void setQctables(Set<QcTable> qctables) {
        this.qctables = qctables;
    }

    public Set<Log> getLogs() {
        return logs;
    }

    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }

    public Set<SubsurfaceJob> getSubsurfaceJobs() {
        return subsurfaceJobs;
    }

    public void setSubsurfaceJobs(Set<SubsurfaceJob> subsurfaceJobs) {
        this.subsurfaceJobs = subsurfaceJobs;
    }

    public Set<Summary> getSummaries() {
        return summaries;
    }

    public void setSummaries(Set<Summary> summaries) {
        this.summaries = summaries;
    }
    
    
    
    /*
    @Override
    public int hashCode() {
    int hash = 7;
    hash = 71 * hash + Objects.hashCode(this.id);
    hash = 71 * hash + Objects.hashCode(this.subsurface);
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
    final Subsurface other = (Subsurface) obj;
    if (!Objects.equals(this.subsurface, other.subsurface)) {
    return false;
    }
    if (!Objects.equals(this.id, other.id)) {
    return false;
    }
    return true;
    }*/

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final Subsurface other = (Subsurface) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
   
    
    
    
    
}
