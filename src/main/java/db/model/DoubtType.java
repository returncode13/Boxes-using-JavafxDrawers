/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="DoubtType",schema="obpmanager")
public class DoubtType {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long idDoubtType;

@OneToMany(mappedBy = "doubtType",cascade = CascadeType.ALL,orphanRemoval = true)
private Set<Doubt> doubtstatus;

@Column(name="name")
private String name;

    public Long getIdDoubtType() {
        return idDoubtType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Doubt> getDoubtstatus() {
        return doubtstatus;
    }

    public void setDoubtstatus(Set<Doubt> doubtstatus) {
        this.doubtstatus = doubtstatus;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.idDoubtType);
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
        final DoubtType other = (DoubtType) obj;
        if (!Objects.equals(this.idDoubtType, other.idDoubtType)) {
            return false;
        }
        return true;
    }

    
    

    
    
    
    
}
