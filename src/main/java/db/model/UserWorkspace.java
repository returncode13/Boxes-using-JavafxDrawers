/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
@Entity
@Table(name="workspace_user",schema="obpmanager")
@AssociationOverrides({
    @AssociationOverride(name="pk.user",joinColumns= @JoinColumn(name="user_id")),
    @AssociationOverride(name="pk.workspace",joinColumns= @JoinColumn(name="workspace_id"))
})
public class UserWorkspace implements Serializable {
    @EmbeddedId
    private UserWorkspaceId pk=new UserWorkspaceId();
    
    @Transient
   // @Column(name="user")
    private User user;
    
    @Transient
    //@Column(name="workspace")
    private Workspace workspace;

    public UserWorkspace() {
    }

    public UserWorkspaceId getPk() {
        return pk;
    }

    /* public void setPk(UserWorkspaceId pk) {
    this.pk = pk;
    }*/

    public User getUser() {
        return pk.getUser();
    }

    public void setUser(User user) {
        pk.setUser(user);
    }

    public Workspace getWorkspace() {
        return pk.getWorkspace();
    }

    public void setWorkspace(Workspace workspace) {
        pk.setWorkspace(workspace);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.pk);
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
        final UserWorkspace other = (UserWorkspace) obj;
        if (!Objects.equals(this.pk, other.pk)) {
            return false;
        }
        return true;
    }
    
    
}
