/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "Dot",schema = "obpmanager")
public class Dot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="workspace_fk",nullable=false)
    private Workspace workspace;
    
    @OneToMany(mappedBy = "dot",fetch = FetchType.EAGER)
    private Set<VariableArgument> variableArguments;
    
    @OneToMany(mappedBy = "dot",fetch = FetchType.EAGER)
    private Set<Doubt> doubts;
    
    @Column(name="status")
    private String status;
    
    @Column(name="function")
    private String function;
    
    @Column(name="tolerance")
    private Double tolerance=0.0;
    
    @Column(name="error")
    private Double error=0.0;
    
    public Dot() {
    }

    /*public void setId(Long id) {
    this.id = id;
    }
    */
     
    public Long getId() {
        return id;
    }
    
    @OneToMany(mappedBy = "dot",fetch=FetchType.EAGER)
    private Set<Link> links;

    public Set<Link> getLinks() {
        return links;
    }

    public void setLinks(Set<Link> links) {
        this.links = links;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Dot other = (Dot) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<VariableArgument> getVariableArguments() {
        return variableArguments;
    }

    public void setVariableArguments(Set<VariableArgument> variableArguments) {
        this.variableArguments = variableArguments;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Double getTolerance() {
        return tolerance;
    }

    public void setTolerance(Double tolerance) {
        this.tolerance = tolerance;
    }

    public Double getError() {
        return error;
    }

    public void setError(Double error) {
        this.error = error;
    }

    public Set<Doubt> getDoubts() {
        return doubts;
    }

    public void setDoubts(Set<Doubt> doubts) {
        this.doubts = doubts;
    }

    
    
    
    
    
    
}
