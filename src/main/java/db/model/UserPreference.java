/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author sharath.nair@polarcus.com
 */
@Entity
@Table(name="user_preference",schema="obpmanager")
public class UserPreference implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    
    
    @ManyToOne
    @JoinColumn(name="job_fk",nullable=false)
    private Job job;
    
    @ManyToOne
    @JoinColumn(name="comment_type_fk",nullable=false)
    private CommentType commentType;
    
    
    @Column(name="json_property_string")
    private String jsonProperty=new String("");

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getJsonProperty() {
        return jsonProperty;
    }

    public void setJsonProperty(String jsonProperty) {
        this.jsonProperty = jsonProperty;
    }

    public Long getId() {
        return id;
    }

    public CommentType getCommentType() {
        return commentType;
    }

    public void setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }

    
    
    
    public UserPreference() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final UserPreference other = (UserPreference) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
