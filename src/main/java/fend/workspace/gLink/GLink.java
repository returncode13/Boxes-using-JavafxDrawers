/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.workspace.gLink;

import db.model.Link;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * Class holds creates a graph with links as nodes.
 */
public class GLink {
    Link link;
    List<GLink> parents=new ArrayList<>();
    List<GLink> children=new ArrayList<>();

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public List<GLink> getParents() {
        return parents;
    }

    public void setParents(List<GLink> parents) {
        this.parents = parents;
    }

    public List<GLink> getChildren() {
        return children;
    }

    public void setChildren(List<GLink> children) {
        this.children = children;
    }
    
    public void addToChildren(GLink child){
        this.children.add(child);
    }
    
    public void addToParents(GLink parent){
        this.parents.add(parent);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.link);
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
        final GLink other = (GLink) obj;
        if (!Objects.equals(this.link, other.link)) {
            return false;
        }
        return true;
    }
    
    
}
