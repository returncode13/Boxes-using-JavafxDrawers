/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * values entered during install
 */
@Entity
@Table(name="NodeProperty",schema = "obpmanager")
public class NodeProperty implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNodeProperty;
    
    @ManyToOne
    @JoinColumn(name="nodeType",nullable=false)
    private NodeType nodeType;
    
    @ManyToOne
    @JoinColumn(name="propertyType",nullable=false)
    private PropertyType propertyType;

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public Long getIdNodeProperty() {
        return idNodeProperty;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.idNodeProperty);
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
        final NodeProperty other = (NodeProperty) obj;
        if (!Objects.equals(this.idNodeProperty, other.idNodeProperty)) {
            return false;
        }
        return true;
    }
    
    
    
}
