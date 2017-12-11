/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.dot;

import fend.job.job0.JobType0Model;
import java.util.Objects;



/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class LinkModel {
    JobType0Model parent;
    JobType0Model child;

    public JobType0Model getParent() {
        return parent;
    }

    public void setParent(JobType0Model parent) {
        this.parent = parent;
    }

    public JobType0Model getChild() {
        return child;
    }

    public void setChild(JobType0Model child) {
        this.child = child;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.parent);
        hash = 79 * hash + Objects.hashCode(this.child);
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
        final LinkModel other = (LinkModel) obj;
        if (!Objects.equals(this.parent, other.parent)) {
            return false;
        }
        if (!Objects.equals(this.child, other.child)) {
            return false;
        }
        return true;
    }
    
    
    
}
