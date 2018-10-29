/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.HashSet;
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
import org.hibernate.annotations.Formula;


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
    private String insightVersions=new String();
    
    
    @Column(name = "alert",nullable = true)
    private Boolean alert;
    
  
    @Column(name="depth",nullable=false)
    private Long depth;
   
    @Formula("(select count(*) from obpmanager.descendant d where d.job_fk=job_id)")
   // @Column(name="is_Leaf")
    private Integer isLeaf;
    
    @Formula("(select count(*) from obpmanager.ancestor a where a.job_fk=job_id)")
   // @Column(name="is_Root")
    private Integer isRoot;
    
    @ManyToOne
    @JoinColumn(name="nodetype_fk",nullable=false)
    private NodeType nodetype;
    
    @ManyToOne
    @JoinColumn(name="workspace_fk",nullable=false)
    private Workspace workspace;
    
    
 
     @OneToMany(mappedBy = "job")                             //create a member named "job" in the JobVolumeMap class definition
    private Set<Volume> volumes=new HashSet<>();
    
    
    @OneToMany(mappedBy = "job")
    private Set<Ancestor> currentJobInAncestor;                         //The ancestor table is of the form  Job(currentjob)-->Job(ancestor)
    
    @OneToMany(mappedBy = "ancestor")
    private Set<Ancestor> ancestors=new HashSet<>();                    
    
    @OneToMany(mappedBy ="job")
    private Set<Descendant> currentJobInDescendant;
    
    @OneToMany(mappedBy ="descendant")
    private Set<Descendant> descendants=new HashSet<>();
    
    @OneToMany(mappedBy = "job")
    private Set<QcMatrixRow> qcmatrix=new HashSet<>();
    
    @OneToMany(mappedBy = "job")
    private Set<Log> logs;
    
        
    @OneToMany(mappedBy = "job")                                    //2D headers . metas
    private Set<Header> headers=new HashSet<>();
    
    @OneToMany(mappedBy = "job")                                   //p-headers. obpd headers .metas
    private Set<Pheader> pheaders=new HashSet<>();
    
    @OneToMany(mappedBy = "job")                                   //t-headers. text headers. md5. timestamp
    private Set<Theader> theaders=new HashSet<>();
    
    @OneToMany(mappedBy = "job")                                    //full-headers.
    private Set<Theader> fheaders=new HashSet<>();
    
    @OneToMany(mappedBy = "parent")
    private Set<Link> linksWithJobAsParent;                 //links where this job is parent...So all the children of this parent job are on the opposite end of the links
    
    @OneToMany(mappedBy = "child")
    private Set<Link> linksWithJobAsChild;                  //links where this job is child. So all the parents of this job are on the opposite end of the link
    
    @OneToMany(mappedBy = "argument")
    private Set<VariableArgument> variableArguments;
    
     @ManyToMany()
    @JoinTable(name="subsurface_job",schema = "obpmanager",joinColumns ={ @JoinColumn(name="job_id")},inverseJoinColumns ={ @JoinColumn(name="id")})    //unidirectional Many-to-Many relationship . 1 job->several subs.
    private Set<Subsurface> subsurfaces=new HashSet<>();
   
    @OneToMany(mappedBy ="pk.job")
    private Set<SubsurfaceJob> subsurfaceJobs =new HashSet<>();
    
    @OneToMany(mappedBy = "job")
    private Set<Summary> summaries;
    
    @OneToMany(mappedBy = "job")
    private Set<Comment> qcComments;
    
    
    @OneToMany(mappedBy = "childJob")
    private Set<Doubt> doubts;
    
  
    public Job() {
    }
    

    
    
    public boolean isLeaf(){
        return isLeaf==null ? true : isLeaf == 0;
    }
    
    public boolean isRoot(){
        return isRoot==null ? true : isRoot == 0;
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

 

    public String getInsightVersions() {
        return insightVersions;
    }

    public void setInsightVersions(String insightVersions) {
        this.insightVersions = insightVersions;
    }

  

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

    /* public Set<QcMatrixRow> getQcmatrix() {
    return qcmatrix;
    }
    
    public void setQcmatrix(Set<QcMatrixRow> qcmatrix) {
    this.qcmatrix = qcmatrix;
    }*/

    /* public Set<Link> getLinksWithJobAsParent() {
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
    }*/

    /*  public Set<Header> getHeaders() {
    return headers;
    }
    
    public void setHeaders(Set<Header> headers) {
    this.headers = headers;
    }*/

    /* public Set<Ancestor> getAncestors() {
    return ancestors;
    }
    
    public void setAncestors(Set<Ancestor> ancestors) {
    this.ancestors = ancestors;
    }*/

    /* public Set<Descendant> getDescendants() {
    return descendants;
    }
    
    public void setDescendants(Set<Descendant> descendants) {
    this.descendants = descendants;
    }*/

    /* public Set<Volume> getVolumes() {
    return volumes;
    }
    
    public void setVolumes(Set<Volume> volumes) {
    this.volumes = volumes;
    }*/

    /*public Set<Log> getLogs() {
    return logs;
    }
    
    public void setLogs(Set<Log> logs) {
    this.logs = logs;
    }*/

    /*
    public Set<Subsurface> getSubsurfaces() {
    return subsurfaces;
    }
    
    public void setSubsurfaces(Set<Subsurface> subsurfaces) {
    this.subsurfaces = subsurfaces;
    }*/

    /* public Set<VariableArgument> getVariableArguments() {
    return variableArguments;
    }
    
    public void setVariableArguments(Set<VariableArgument> variableArguments) {
    this.variableArguments = variableArguments;
    }*/

    /*public Set<Doubt> getDoubts() {
    return doubts;
    }
    
    public void setDoubts(Set<Doubt> doubts) {
    this.doubts = doubts;
    }*/

    public Long getDepth() {
        return depth;
    }

    public void setDepth(Long depth) {
        this.depth = depth;
    }
    
    /*
    public Set<Summary> getSummaries() {
    return summaries;
    }
    
    public void setSummaries(Set<Summary> summaries) {
    this.summaries = summaries;
    }*/

   
    /*public Set<SubsurfaceJob> getSubsurfaceJobs() {
    return subsurfaceJobs;
    }
    
    public void setSubsurfaceJobs(Set<SubsurfaceJob> subsurfaceJobs) {
    this.subsurfaceJobs = subsurfaceJobs;
    }*/
    
    
    
    
    
    
    
    
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
