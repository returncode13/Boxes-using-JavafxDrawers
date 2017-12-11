/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 *
 * @author sharath nair
 */

@Entity
@Table(name="job",schema = "obpmanager")

public class Job implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id",nullable = false,unique = true,length = 10)
    private Long id;
    
    @Column(name = "name",nullable = true,length = 256)
    private String nameJobStep;
    
    @Column(name = "insightVersionsUsed",nullable=true,length=2048)
    private String insightVersions;
    
    
    @Column(name = "alert",nullable = true)
    private Boolean alert;
    
    /*@Column(name = "type",nullable=false)
    private Long type;*/
    
   
    
    @ManyToOne
    @JoinColumn(name="nodetype_fk",nullable=false)
    private NodeType nodetype;
    
    @ManyToOne
    @JoinColumn(name="workspace_fk",nullable=false)
    private Workspace workspace;
    
    
    
    /*@Column(name = "pending",nullable = true)
    private Boolean pending;*/
    /* @OneToMany(mappedBy = "job")                              //create a member named "job" in the JobVolumeMap class definition
    private Set<JobVolumeMap> jobVolumeMap;*/
    
     @OneToMany(mappedBy = "job",fetch=FetchType.EAGER)                             //create a member named "job" in the JobVolumeMap class definition
    private Set<Volume> volumes;
    
    
    @OneToMany(mappedBy = "job",fetch=FetchType.EAGER)
    private Set<Ancestor> currentJobInAncestor;                         //The ancestor table is of the form  Job(currentjob)-->Job(ancestor)
    
    @OneToMany(mappedBy = "ancestor",fetch=FetchType.EAGER)
    private Set<Ancestor> ancestors;                    
    
    @OneToMany(mappedBy ="job",fetch=FetchType.EAGER)
    private Set<Descendant> currentJobInDescendant;
    
    @OneToMany(mappedBy ="descendant",fetch=FetchType.EAGER)
    private Set<Descendant> descendants;
    
    @OneToMany(mappedBy = "job",fetch=FetchType.EAGER)
    private Set<QcMatrixRow> qcmatrix;
    
    @OneToMany(mappedBy = "job",fetch=FetchType.EAGER)
    private Set<Log> logs;
    
    
    @OneToMany(mappedBy = "job",fetch=FetchType.EAGER)
    private Set<Header> headers;
    
    @OneToMany(mappedBy = "parent",fetch=FetchType.EAGER)
    private Set<Link> linksWithJobAsParent;                 //links where this job is parent...So all the children of this parent job are on the opposite end of the links
    
    @OneToMany(mappedBy = "child",fetch=FetchType.EAGER)
    private Set<Link> linksWithJobAsChild;                  //links where this job is child. So all the parents of this job are on the opposite end of the link
    
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="subsurface_job",schema = "obpmanager",joinColumns ={ @JoinColumn(name="job_id")},inverseJoinColumns ={ @JoinColumn(name="id")})    //unidirectional Many-to-Many relationship . 1 job->several subs. 
    private Set<Subsurface> subsurfaces;
     
    /*public Job(String nameJobStep, Boolean alert,String insightVersion,Long type) {
    this.nameJobStep = nameJobStep;
    this.alert = alert;
    this.insightVersions=insightVersion;
    this.type=type;
    }
    */

    /*
    public Job(String nameJobStep, Boolean alert,String insightVersion,NodeType type) {
    this.nameJobStep = nameJobStep;
    this.alert = alert;
    this.insightVersions=insightVersion;
    this.nodetype=type;
    }
    */
    public Job() {
    }
    

    
    

  
    
    
    
 
    public Long getId() {
        return id;
    }

    /*public void setId(Long id) {
    this.id = id;
    }*/
    
    public String getNameJobStep() {
        return nameJobStep;
    }

    public void setNameJobStep(String nameJobStep) {
        this.nameJobStep = nameJobStep;
    }

    public Boolean isAlert() {
        return alert;
    }

    public void setAlert(Boolean alert) {
        this.alert = alert;
    }

    /*public Set<JobVolumeMap> getJobVolumeMap() {
    return jobVolumeMap;
    }
    
    public void setJobVolumeMap(Set<JobVolumeMap> jobVolumeMap) {
    
    if(jobVolumeMap!=null)
    {
    this.jobVolumeMap.clear();
    
    for (Iterator<JobVolumeMap> iterator = jobVolumeMap.iterator(); iterator.hasNext();) {
    JobVolumeMap next = iterator.next();
    this.jobVolumeMap.add(next);
    }
    }
    //this.jobVolumeMap = jobVolumeMap;
    }*/
   

    public String getInsightVersions() {
        return insightVersions;
    }

    public void setInsightVersions(String insightVersions) {
        this.insightVersions = insightVersions;
    }

    /* public Boolean getPending() {
    return pending;
    }
    
    public void setPending(Boolean pending) {
    this.pending = pending;
    }
    */

    /* public Long getType() {
    return type;
    }
    
    public void setType(Long type) {
    this.type = type;
    }*/

    /*public NodeType getType() {
    return nodetype;
    }
    
    public void setType(NodeType type) {
    this.nodetype = type;
    }*/

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public NodeType getNodetype() {
        return nodetype;
    }

    public void setNodetype(NodeType nodetype) {
        this.nodetype = nodetype;
    }

    public Set<Ancestor> getCurrentJobInAncestor() {
        return currentJobInAncestor;
    }

    public void setCurrentJobInAncestor(Set<Ancestor> currentJobInAncestor) {
        this.currentJobInAncestor = currentJobInAncestor;
    }

    public Set<Descendant> getCurrentJobInDescendant() {
        return currentJobInDescendant;
    }

    public void setCurrentJobInDescendant(Set<Descendant> currentJobInDescendant) {
        this.currentJobInDescendant = currentJobInDescendant;
    }

    public Set<QcMatrixRow> getQcmatrix() {
        return qcmatrix;
    }

    public void setQcmatrix(Set<QcMatrixRow> qcmatrix) {
        this.qcmatrix = qcmatrix;
    }

    public Set<Link> getLinksWithJobAsParent() {
        return linksWithJobAsParent;
    }

    public void setLinksWithJobAsParent(Set<Link> linksWithJobAsParent) {
        this.linksWithJobAsParent = linksWithJobAsParent;
    }

    public Set<Link> getLinksWithJobAsChild() {
        return linksWithJobAsChild;
    }

    public void setLinksWithJobAsChild(Set<Link> linksWithJobAsChild) {
        this.linksWithJobAsChild = linksWithJobAsChild;
    }

    public Set<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(Set<Header> headers) {
        this.headers = headers;
    }

    public Set<Ancestor> getAncestors() {
        return ancestors;
    }

    public void setAncestors(Set<Ancestor> ancestors) {
        this.ancestors = ancestors;
    }

    public Set<Descendant> getDescendants() {
        return descendants;
    }

    public void setDescendants(Set<Descendant> descendants) {
        this.descendants = descendants;
    }

    public Set<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(Set<Volume> volumes) {
        this.volumes = volumes;
    }

    public Set<Log> getLogs() {
        return logs;
    }

    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }

    
    public Set<Subsurface> getSubsurfaces() {
        return subsurfaces;
    }

    public void setSubsurfaces(Set<Subsurface> subsurfaces) {
        this.subsurfaces = subsurfaces;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Job other = (Job) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
   
    
    
    
    
    
   
    
    
    
    
    

    
    
    
    
    
}
