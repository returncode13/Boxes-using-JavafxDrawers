/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.HashSet;
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
@Table(name="Doubt",schema="obpmanager")
public class Doubt implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    /*@ManyToOne
    @JoinColumn(name="headers_fk",nullable=false)
    private Headers headers;*/
    
    /*@ManyToOne
    @JoinColumn(name="parent_sessiondetails_fk",nullable=false)
    private SessionDetails parentSessionDetails;
    */
    
    @ManyToOne
    @JoinColumn(name="subsurface_fk")
    private Subsurface subsurface;
    
    @ManyToOne
    @JoinColumn(name="doubtType_fk",nullable=false)
    private DoubtType doubtType;
    
    
    @ManyToOne
    @JoinColumn(name="user_fk")
    private User user;
    
        
     @ManyToOne
    @JoinColumn(name="link_fk")
    private Link link;                              //several doubts maybe associated to a single link.
    
    
    @ManyToOne
    @JoinColumn(name="dot_fk")                      //several  with the same dot
    private Dot dot;
    
    @ManyToOne
    @JoinColumn(name="child_job_fk")
    private Job childJob;
    
    
    
    @ManyToOne
    @JoinColumn(name="seq_fk")
    private Sequence sequence;
    
    @OneToMany(mappedBy = "doubt")
    private Set<DoubtStatus> doubtStatuses=new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "doubt_cause_id")
    private Doubt doubtCause;
    
    @OneToMany(mappedBy = "doubtCause",fetch=FetchType.EAGER)
    private Set<Doubt> inheritedDoubts=new HashSet<>();
    
    @Column(name="reason",columnDefinition = "text")
    private String reason=new String();
    
    @Column(name="status")
    private String status=new String();
    
    @Column(name="timestamp")
    private String timeStamp;
    
    @Column(name="comments",columnDefinition = "text" )
    private String comments=new String();
    
    @Column(name="state")
    private String state=new String();

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
    
    
    
    
    
    
    /*
    @ManyToOne
    @JoinColumn(name="user_fk",nullable=true)
    private User user;
    */
    
    /* @Column(name="child_sessionDetails_id",nullable=false)
    private Long childSessionDetailsId;
    */
    
    /*@Column(name="status")
    private String status;
    
    @Column(name="errorMessage")
    private String errorMessage;
    
    public String getStatus() {
    return status;
    }
    
    public void setStatus(String status) {
    this.status = status;
    }
    
    public String getErrorMessage() {
    return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    }*/

    /*public Long getChildSessionDetailsId() {
    return childSessionDetailsId;
    }
    
    public void setChildSessionDetailsId(Long childSessionDetailsId) {
    this.childSessionDetailsId = childSessionDetailsId;
    }
    */
    
    
    /*
    public User getUser() {
    return user;
    }
    
    public void setUser(User user) {
    this.user = user;
    }
    
    */
    

    public Long getId() {
        return id;
    }

    

    /*public Headers getHeaders() {
    return headers;
    }
    
    public void setHeaders(Headers headers) {
    this.headers = headers;
    }*/

    /* public SessionDetails getParentSessionDetails() {
    return parentSessionDetails;
    }
    
    public void setParentSessionDetails(SessionDetails parentSessionDetails) {
    this.parentSessionDetails = parentSessionDetails;
    }*/

    public DoubtType getDoubtType() {
        return doubtType;
    }

    public void setDoubtType(DoubtType doubtType) {
        this.doubtType = doubtType;
    }

    public Subsurface getSubsurface() {
        return subsurface;
    }

    public void setSubsurface(Subsurface subsurface) {
        this.subsurface = subsurface;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*  public Link getLink() {
    return link;
    }
    
    public void setLink(Link link) {
    this.link = link;
    }*/

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    
    
    public Dot getDot() {
        return dot;
    }

    public void setDot(Dot dot) {
        this.dot = dot;
    }

    public Job getChildJob() {
        return childJob;
    }

    public void setChildJob(Job childJob) {
        this.childJob = childJob;
    }

    public Set<DoubtStatus> getDoubtStatuses() {
        return doubtStatuses;
    }

    public void setDoubtStatuses(Set<DoubtStatus> doubtStatuses) {
        this.doubtStatuses = doubtStatuses;
    }
    
    public void addToDoubtStatuses(DoubtStatus doubtStatus) {
        if(this.doubtStatuses==null) this.doubtStatuses=new HashSet<>();
        this.doubtStatuses.add(doubtStatus);
                
    }

    public Doubt getDoubtCause() {
        return doubtCause;
    }

    public void setDoubtCause(Doubt doubtCause) {
        this.doubtCause = doubtCause;
    }

    public Set<Doubt> getInheritedDoubts() {
        return inheritedDoubts;
    }

    public void setInheritedDoubts(Set<Doubt> inheritedDoubts) {
        this.inheritedDoubts = inheritedDoubts;
    }

    public void addToInheritedDoubts(Doubt inheritedDoubt) {
        this.inheritedDoubts.add(inheritedDoubt);
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final Doubt other = (Doubt) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

   
    
    
    
    
    
}
