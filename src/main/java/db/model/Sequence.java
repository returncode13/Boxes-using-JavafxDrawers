/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
@Entity
@Table(name="sequence",schema="public")
public class Sequence implements Serializable {
@Id
@GeneratedValue
private Long id;

@Column(name="sequenceno")
private Long sequenceno;

@Column(name="status")
private String status;

@Column(name="real_line_name")
private String realLineName;


 
@OneToMany(mappedBy = "sequence")
private Set<Subsurface> subsurfaces;

@OneToMany(mappedBy = "sequence")
private Set<Acquisition> acquisition;


@OneToMany(mappedBy = "sequence")
private Set<Summary> summaries;

@OneToMany(mappedBy = "sequence")
private Set<Doubt> doubts;

    public Set<Acquisition> getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(Set<Acquisition> acquisition) {
        this.acquisition = acquisition;
    }


    public Set<Subsurface> getSubsurfaces() {
        return subsurfaces;
    }

    public void setSubsurfaces(Set<Subsurface> subsurfaces) {
        this.subsurfaces = subsurfaces;
    }

    public Long getSequenceno() {
        return sequenceno;
    }

    public void setSequenceno(Long sequenceno) {
        this.sequenceno = sequenceno;
    }
   
    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRealLineName() {
        return realLineName;
    }

    public void setRealLineName(String realLineName) {
        this.realLineName = realLineName;
    }

    public Set<Summary> getSummaries() {
        return summaries;
    }

    public void setSummaries(Set<Summary> summaries) {
        this.summaries = summaries;
    }

    public Set<Doubt> getDoubts() {
        return doubts;
    }

    public void setDoubts(Set<Doubt> doubts) {
        this.doubts = doubts;
    }

    
    
    
    
    

    public Sequence() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Objects.hashCode(this.sequenceno);
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
        final Sequence other = (Sequence) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.sequenceno, other.sequenceno)) {
            return false;
        }
        return true;
    }


    
    

}
