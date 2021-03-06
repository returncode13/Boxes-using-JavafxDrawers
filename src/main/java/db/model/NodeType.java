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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * values entered during install
 */
@Entity
@Table(name="NodeType",schema="obpmanager")
public class NodeType implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idForNodeTypeTable")
    private Long idNodeType;
    
    
    @Column(name="actualnodeid")
    private Long actualnodeid;
    
    @Column(name="Name")
    private String name;
    
    @OneToMany(mappedBy = "nodetype",cascade = CascadeType.ALL,orphanRemoval = true)
private Set<Job> jobsteps;
    
    
    
   
    
    @OneToMany(mappedBy = "nodeType",cascade = CascadeType.ALL,orphanRemoval = true)                              //create a member named "jobStep" in the JobVolumeDetails class definition
    private Set<NodeProperty> nodePropertyType;

    public Long getIdNodeType() {
        return idNodeType;
    }

   
    public Long getActualnodeid() {
        return actualnodeid;
    }

    public void setActualnodeid(Long actualnodeid) {
        this.actualnodeid = actualnodeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.idNodeType);
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
        final NodeType other = (NodeType) obj;
        if (!Objects.equals(this.idNodeType, other.idNodeType)) {
            return false;
        }
        return true;
    }
    
    
}
