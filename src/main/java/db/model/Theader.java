/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 * 
 * Text node headers
 * 
 */
@Entity
@Table(name="t_header",schema = "obpmanager")
public class Theader implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tHeaderId;
    
    @ManyToOne
    @JoinColumn(name="volume_fk",nullable = false)
    private Volume volume;
    
    @ManyToOne
    @JoinColumn(name="job_fk",nullable = false)
    private Job job;
    
     
    
   
  
    
    @ManyToOne
    @JoinColumns({@JoinColumn(name="job_id",nullable=false),
                  @JoinColumn(name="id",nullable=false)})
    private SubsurfaceJob subsurfaceJob;
    

    
    @ManyToOne
    @JoinColumn(name="subsurface_fk",nullable = false)
    private Subsurface subsurface;
    
    
    @ManyToOne
    @JoinColumn(name="sequence_fk",nullable = false)
    private Sequence sequence;
    
    @Column(name= "TimeStamp ")
    private String timeStamp=new String();
    
    

    @Column(name="Modified")
    private Boolean modified=false;
    
    @Column(name="Deleted")
    private Boolean deleted=false; 
    
    @Column(name="NumberOfRuns")
    private Long numberOfRuns=0L;                                                                      //number of times the subsurface was run
    
    @Column(name="history",columnDefinition = "text")
    private String history=new String();
    
    
    @Column(name="UpdateTime")
    private String updateTime=new String();
    
    @Column(name="SummaryTime")
    private String summaryTime=new String();
    
    @Column(name="md5",columnDefinition = "TEXT")                                             //used for txt file types. job steptype=4
    private String md5=new String();         
   
    @Column(name="text_file",columnDefinition="TEXT")
    private String textFile=new String();
    
    public Theader() {
      
    }

    
    


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.tHeaderId);
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
        final Theader other = (Theader) obj;
        if (!Objects.equals(this.tHeaderId, other.tHeaderId)) {
            return false;
        }
        return true;
    }

    public Long getTHeaderId() {
        return tHeaderId;
    }

    public void setTHeaderId(Long tHeaderId) {
        this.tHeaderId = tHeaderId;
    }
    
  
    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

  

    
    

    public Subsurface getSubsurface() {
    return subsurface;
    }
    
    public void setSubsurface(Subsurface subsurface) {
    this.subsurface = subsurface;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
    
    public void appendToHistory(String history){
        this.history+="\n"+history;
    }

    public String getTextFile() {
        return textFile;
    }

    public void setTextFile(String textFile) {
        this.textFile = textFile;
    }
    
    
    
    
    
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Boolean getModified() {
        return modified;
    }

    public void setModified(Boolean modified) {
        this.modified = modified;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getNumberOfRuns() {
        return numberOfRuns;
    }

    public void setNumberOfRuns(Long version) {
        this.numberOfRuns = version;
    }

   

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSummaryTime() {
        return summaryTime;
    }

    public void setSummaryTime(String summaryTime) {
        this.summaryTime = summaryTime;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Job getJob() {
    return job;
    }
    
    public void setJob(Job job) {
    this.job = job;
    }

   

   
    
    public SubsurfaceJob getSubsurfaceJob() {
        return subsurfaceJob;
    }

    public void setSubsurfaceJob(SubsurfaceJob subsurfaceJob) {
        this.subsurfaceJob = subsurfaceJob;
    }
}