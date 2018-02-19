/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

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
 * @author sharath nair <sharath.nair@polarcus.com>
 */
@Entity
@Table(name="Doubtstatus",schema="obpmanager")
public class DoubtStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="doubt_fk")
    private Doubt doubt;
    
    @Column(name="reason")
    private String reason;
    
    @Column(name="status")
    private String status;
    
    @Column(name="timestamp")
    private String timeStamp;
    
    @Column(name="comments",columnDefinition = "text" )
    private String comments;
    
    @Column(name="state")
    private String state;
    
    @ManyToOne
    @JoinColumn(name="user_fk")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doubt getDoubt() {
        return doubt;
    }

    public void setDoubt(Doubt doubt) {
        this.doubt = doubt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    
    
    
    
    
}
