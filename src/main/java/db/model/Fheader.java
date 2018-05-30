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
 * Used this script to generate this code
 * dugio read file=100_P-PRECONDITION.dugio line=708501151101_cable8_gun1 query=time:0-0 full_headers | durange | awk '{if (/[[:alpha:]][[:space:]][[:alpha:]]/) {print "@Column(name=\""$1"_"$2"\")\nLong "$1"_"$2"=0L;"} else {print "@Column(name=\""$1"\")\nLong "$1"=0L;"}}'
 */
@Entity
@Table(name="full_header",schema = "public")
public class Fheader implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fHeaderId;
    
    @ManyToOne
    @JoinColumn(name="volume_fk",nullable = false)
    private Volume volume;
    
    @ManyToOne
    @JoinColumn(name="job_fk",nullable = false)
    private Job job;
    
     
    
    @OneToMany(mappedBy = "fheader")
    private Set<Log> logs;
    
   
    
    @ManyToOne
    @JoinColumns({@JoinColumn(name="job_id",nullable=false),
                  @JoinColumn(name="id",nullable=false)})
    private SubsurfaceJob subsurfaceJob;
    
     
     @ManyToOne
    @JoinColumn(name="subsurface_fk",nullable = false)
    private Subsurface subsurface;
     
    @Column(name= "timeStamp ")
    private String timeStamp; 
     
@Column(name="totalTraces")
Long totalTraces=0L;

@Column(name="minTracr")
Long minTracr=0L;

@Column(name="maxTracr")
Long maxTracr=0L;

@Column(name="incTracr")
Long incTracr=0L;

@Column(name="firstTracr")
Long firstTracr=0L;

@Column(name="lastTracr")
Long lastTracr=0L;

@Column(name="minFldr")
Long minFldr=0L;

@Column(name="maxFldr")
Long maxFldr=0L;

@Column(name="incFldr")
Long incFldr=0L;

@Column(name="firstFldr")
Long firstFldr=0L;

@Column(name="lastFldr")
Long lastFldr=0L;

@Column(name="minTracf")
Long minTracf=0L;

@Column(name="maxTracf")
Long maxTracf=0L;

@Column(name="incTracf")
Long incTracf=0L;

@Column(name="firstTracf")
Long firstTracf=0L;

@Column(name="lastTracf")
Long lastTracf=0L;

@Column(name="minEp")
Long minEp=0L;

@Column(name="maxEp")
Long maxEp=0L;

@Column(name="incEp")
Long incEp=0L;

@Column(name="firstEp")
Long firstEp=0L;

@Column(name="lastEp")
Long lastEp=0L;

@Column(name="minCdp")
Long minCdp=0L;

@Column(name="maxCdp")
Long maxCdp=0L;

@Column(name="incCdp")
Long incCdp=0L;

@Column(name="firstCdp")
Long firstCdp=0L;

@Column(name="lastCdp")
Long lastCdp=0L;

@Column(name="minCdpt")
Long minCdpt=0L;

@Column(name="maxCdpt")
Long maxCdpt=0L;

@Column(name="incCdpt")
Long incCdpt=0L;

@Column(name="firstCdpt")
Long firstCdpt=0L;

@Column(name="lastCdpt")
Long lastCdpt=0L;

@Column(name="minTrid")
Long minTrid=0L;

@Column(name="maxTrid")
Long maxTrid=0L;

@Column(name="incTrid")
Long incTrid=0L;

@Column(name="firstTrid")
Long firstTrid=0L;

@Column(name="lastTrid")
Long lastTrid=0L;

@Column(name="minDuse")
Long minDuse=0L;

@Column(name="maxDuse")
Long maxDuse=0L;

@Column(name="incDuse")
Long incDuse=0L;

@Column(name="firstDuse")
Long firstDuse=0L;

@Column(name="lastDuse")
Long lastDuse=0L;

@Column(name="duse")
Long duse=0L;

@Column(name="minOffset")
Long minOffset=0L;

@Column(name="maxOffset")
Long maxOffset=0L;

@Column(name="incOffset")
Long incOffset=0L;

@Column(name="firstOffset")
Long firstOffset=0L;

@Column(name="lastOffset")
Long lastOffset=0L;

@Column(name="minGelev")
Long minGelev=0L;

@Column(name="maxGelev")
Long maxGelev=0L;

@Column(name="incGelev")
Long incGelev=0L;

@Column(name="firstGelev")
Long firstGelev=0L;

@Column(name="lastGelev")
Long lastGelev=0L;

@Column(name="minSelev")
Long minSelev=0L;

@Column(name="maxSelev")
Long maxSelev=0L;

@Column(name="incSelev")
Long incSelev=0L;

@Column(name="firstSelev")
Long firstSelev=0L;

@Column(name="lastSelev")
Long lastSelev=0L;

@Column(name="minSdepth")
Long minSdepth=0L;

@Column(name="maxSdepth")
Long maxSdepth=0L;

@Column(name="incSdepth")
Long incSdepth=0L;

@Column(name="firstSdepth")
Long firstSdepth=0L;

@Column(name="lastSdepth")
Long lastSdepth=0L;

@Column(name="minSwdep")
Long minSwdep=0L;

@Column(name="maxSwdep")
Long maxSwdep=0L;

@Column(name="incSwdep")
Long incSwdep=0L;

@Column(name="firstSwdep")
Long firstSwdep=0L;

@Column(name="lastSwdep")
Long lastSwdep=0L;

@Column(name="minGwdep")
Long minGwdep=0L;

@Column(name="maxGwdep")
Long maxGwdep=0L;

@Column(name="incGwdep")
Long incGwdep=0L;

@Column(name="firstGwdep")
Long firstGwdep=0L;

@Column(name="lastGwdep")
Long lastGwdep=0L;

@Column(name="minScalel")
Long minScalel=0L;

@Column(name="maxScalel")
Long maxScalel=0L;

@Column(name="incScalel")
Long incScalel=0L;

@Column(name="firstScalel")
Long firstScalel=0L;

@Column(name="lastScalel")
Long lastScalel=0L;

@Column(name="scalel")
Long scalel=0L;

@Column(name="minScalco")
Long minScalco=0L;

@Column(name="maxScalco")
Long maxScalco=0L;

@Column(name="incScalco")
Long incScalco=0L;

@Column(name="firstScalco")
Long firstScalco=0L;

@Column(name="lastScalco")
Long lastScalco=0L;

@Column(name="scalco")
Long scalco=0L;

@Column(name="minSx")
Long minSx=0L;

@Column(name="maxSx")
Long maxSx=0L;

@Column(name="incSx")
Long incSx=0L;

@Column(name="firstSx")
Long firstSx=0L;

@Column(name="lastSx")
Long lastSx=0L;

@Column(name="minSy")
Long minSy=0L;

@Column(name="maxSy")
Long maxSy=0L;

@Column(name="incSy")
Long incSy=0L;

@Column(name="firstSy")
Long firstSy=0L;

@Column(name="lastSy")
Long lastSy=0L;

@Column(name="minGx")
Long minGx=0L;

@Column(name="maxGx")
Long maxGx=0L;

@Column(name="incGx")
Long incGx=0L;

@Column(name="firstGx")
Long firstGx=0L;

@Column(name="lastGx")
Long lastGx=0L;

@Column(name="minGy")
Long minGy=0L;

@Column(name="maxGy")
Long maxGy=0L;

@Column(name="incGy")
Long incGy=0L;

@Column(name="firstGy")
Long firstGy=0L;

@Column(name="lastGy")
Long lastGy=0L;

@Column(name="minWevel")
Long minWevel=0L;

@Column(name="maxWevel")
Long maxWevel=0L;

@Column(name="incWevel")
Long incWevel=0L;

@Column(name="firstWevel")
Long firstWevel=0L;

@Column(name="lastWevel")
Long lastWevel=0L;

@Column(name="wevel")
Long wevel=0L;

@Column(name="minSwevel")
Long minSwevel=0L;

@Column(name="maxSwevel")
Long maxSwevel=0L;

@Column(name="incSwevel")
Long incSwevel=0L;

@Column(name="firstSwevel")
Long firstSwevel=0L;

@Column(name="lastSwevel")
Long lastSwevel=0L;

@Column(name="swevel")
Long swevel=0L;

@Column(name="minSut")
Long minSut=0L;

@Column(name="maxSut")
Long maxSut=0L;

@Column(name="incSut")
Long incSut=0L;

@Column(name="firstSut")
Long firstSut=0L;

@Column(name="lastSut")
Long lastSut=0L;

@Column(name="minGut")
Long minGut=0L;

@Column(name="maxGut")
Long maxGut=0L;

@Column(name="incGut")
Long incGut=0L;

@Column(name="firstGut")
Long firstGut=0L;

@Column(name="lastGut")
Long lastGut=0L;

@Column(name="minSstat")
Long minSstat=0L;

@Column(name="maxSstat")
Long maxSstat=0L;

@Column(name="incSstat")
Long incSstat=0L;

@Column(name="firstSstat")
Long firstSstat=0L;

@Column(name="lastSstat")
Long lastSstat=0L;

@Column(name="minGstat")
Long minGstat=0L;

@Column(name="maxGstat")
Long maxGstat=0L;

@Column(name="incGstat")
Long incGstat=0L;

@Column(name="firstGstat")
Long firstGstat=0L;

@Column(name="lastGstat")
Long lastGstat=0L;

@Column(name="minLagb")
Long minLagb=0L;

@Column(name="maxLagb")
Long maxLagb=0L;

@Column(name="incLagb")
Long incLagb=0L;

@Column(name="firstLagb")
Long firstLagb=0L;

@Column(name="lastLagb")
Long lastLagb=0L;

@Column(name="minNs")
Long minNs=0L;

@Column(name="maxNs")
Long maxNs=0L;

@Column(name="incNs")
Long incNs=0L;

@Column(name="firstNs")
Long firstNs=0L;

@Column(name="lastNs")
Long lastNs=0L;

@Column(name="ns")
Long ns=0L;

@Column(name="minDt")
Long minDt=0L;

@Column(name="maxDt")
Long maxDt=0L;

@Column(name="incDt")
Long incDt=0L;

@Column(name="firstDt")
Long firstDt=0L;

@Column(name="lastDt")
Long lastDt=0L;

@Column(name="dt")
Long dt=0L;

@Column(name="minSfs")
Long minSfs=0L;

@Column(name="maxSfs")
Long maxSfs=0L;

@Column(name="incSfs")
Long incSfs=0L;

@Column(name="firstSfs")
Long firstSfs=0L;

@Column(name="lastSfs")
Long lastSfs=0L;

@Column(name="minSfe")
Long minSfe=0L;

@Column(name="maxSfe")
Long maxSfe=0L;

@Column(name="incSfe")
Long incSfe=0L;

@Column(name="firstSfe")
Long firstSfe=0L;

@Column(name="lastSfe")
Long lastSfe=0L;

@Column(name="minSlen")
Long minSlen=0L;

@Column(name="maxSlen")
Long maxSlen=0L;

@Column(name="incSlen")
Long incSlen=0L;

@Column(name="firstSlen")
Long firstSlen=0L;

@Column(name="lastSlen")
Long lastSlen=0L;

@Column(name="slen")
Long slen=0L;

@Column(name="minStyp")
Long minStyp=0L;

@Column(name="maxStyp")
Long maxStyp=0L;

@Column(name="incStyp")
Long incStyp=0L;

@Column(name="firstStyp")
Long firstStyp=0L;

@Column(name="lastStyp")
Long lastStyp=0L;

@Column(name="minStas")
Long minStas=0L;

@Column(name="maxStas")
Long maxStas=0L;

@Column(name="incStas")
Long incStas=0L;

@Column(name="firstStas")
Long firstStas=0L;

@Column(name="lastStas")
Long lastStas=0L;

@Column(name="minStae")
Long minStae=0L;

@Column(name="maxStae")
Long maxStae=0L;

@Column(name="incStae")
Long incStae=0L;

@Column(name="firstStae")
Long firstStae=0L;

@Column(name="lastStae")
Long lastStae=0L;

@Column(name="stae")
Long stae=0L;

@Column(name="minAfilf")
Long minAfilf=0L;

@Column(name="maxAfilf")
Long maxAfilf=0L;

@Column(name="incAfilf")
Long incAfilf=0L;

@Column(name="firstAfilf")
Long firstAfilf=0L;

@Column(name="lastAfilf")
Long lastAfilf=0L;

@Column(name="afilf")
Long afilf=0L;

@Column(name="minAfils")
Long minAfils=0L;

@Column(name="maxAfils")
Long maxAfils=0L;

@Column(name="incAfils")
Long incAfils=0L;

@Column(name="firstAfils")
Long firstAfils=0L;

@Column(name="lastAfils")
Long lastAfils=0L;

@Column(name="afils")
Long afils=0L;

@Column(name="minLcf")
Long minLcf=0L;

@Column(name="maxLcf")
Long maxLcf=0L;

@Column(name="incLcf")
Long incLcf=0L;

@Column(name="firstLcf")
Long firstLcf=0L;

@Column(name="lastLcf")
Long lastLcf=0L;

@Column(name="lcf")
Long lcf=0L;

@Column(name="minLcs")
Long minLcs=0L;

@Column(name="maxLcs")
Long maxLcs=0L;

@Column(name="incLcs")
Long incLcs=0L;

@Column(name="firstLcs")
Long firstLcs=0L;

@Column(name="lastLcs")
Long lastLcs=0L;

@Column(name="lcs")
Long lcs=0L;

@Column(name="minHcs")
Long minHcs=0L;

@Column(name="maxHcs")
Long maxHcs=0L;

@Column(name="incHcs")
Long incHcs=0L;

@Column(name="firstHcs")
Long firstHcs=0L;

@Column(name="lastHcs")
Long lastHcs=0L;

@Column(name="minYear")
Long minYear=0L;

@Column(name="maxYear")
Long maxYear=0L;

@Column(name="incYear")
Long incYear=0L;

@Column(name="firstYear")
Long firstYear=0L;

@Column(name="lastYear")
Long lastYear=0L;

@Column(name="year")
Long year=0L;

@Column(name="minDay")
Long minDay=0L;

@Column(name="maxDay")
Long maxDay=0L;

@Column(name="incDay")
Long incDay=0L;

@Column(name="firstDay")
Long firstDay=0L;

@Column(name="lastDay")
Long lastDay=0L;

@Column(name="day")
Long day=0L;

@Column(name="minHour")
Long minHour=0L;

@Column(name="maxHour")
Long maxHour=0L;

@Column(name="incHour")
Long incHour=0L;

@Column(name="firstHour")
Long firstHour=0L;

@Column(name="lastHour")
Long lastHour=0L;

@Column(name="minMinute")
Long minMinute=0L;

@Column(name="maxMinute")
Long maxMinute=0L;

@Column(name="incMinute")
Long incMinute=0L;

@Column(name="firstMinute")
Long firstMinute=0L;

@Column(name="lastMinute")
Long lastMinute=0L;

@Column(name="minSec")
Long minSec=0L;

@Column(name="maxSec")
Long maxSec=0L;

@Column(name="incSec")
Long incSec=0L;

@Column(name="firstSec")
Long firstSec=0L;

@Column(name="lastSec")
Long lastSec=0L;

@Column(name="minGrnors")
Long minGrnors=0L;

@Column(name="maxGrnors")
Long maxGrnors=0L;

@Column(name="incGrnors")
Long incGrnors=0L;

@Column(name="firstGrnors")
Long firstGrnors=0L;

@Column(name="lastGrnors")
Long lastGrnors=0L;

@Column(name="grnors")
Long grnors=0L;

@Column(name="minGrnofr")
Long minGrnofr=0L;

@Column(name="maxGrnofr")
Long maxGrnofr=0L;

@Column(name="incGrnofr")
Long incGrnofr=0L;

@Column(name="firstGrnofr")
Long firstGrnofr=0L;

@Column(name="lastGrnofr")
Long lastGrnofr=0L;

@Column(name="grnofr")
Long grnofr=0L;

@Column(name="minGaps")
Long minGaps=0L;

@Column(name="maxGaps")
Long maxGaps=0L;

@Column(name="incGaps")
Long incGaps=0L;

@Column(name="firstGaps")
Long firstGaps=0L;

@Column(name="lastGaps")
Long lastGaps=0L;

@Column(name="minOtrav")
Long minOtrav=0L;

@Column(name="maxOtrav")
Long maxOtrav=0L;

@Column(name="incOtrav")
Long incOtrav=0L;

@Column(name="firstOtrav")
Long firstOtrav=0L;

@Column(name="lastOtrav")
Long lastOtrav=0L;

@Column(name="minCdpx")
Long minCdpx=0L;

@Column(name="maxCdpx")
Long maxCdpx=0L;

@Column(name="incCdpx")
Long incCdpx=0L;

@Column(name="firstCdpx")
Long firstCdpx=0L;

@Column(name="lastCdpx")
Long lastCdpx=0L;

@Column(name="minCdpy")
Long minCdpy=0L;

@Column(name="maxCdpy")
Long maxCdpy=0L;

@Column(name="incCdpy")
Long incCdpy=0L;

@Column(name="firstCdpy")
Long firstCdpy=0L;

@Column(name="lastCdpy")
Long lastCdpy=0L;

@Column(name="minInline")
Long minInline=0L;

@Column(name="maxInline")
Long maxInline=0L;

@Column(name="incInline")
Long incInline=0L;

@Column(name="firstInline")
Long firstInline=0L;

@Column(name="lastInline")
Long lastInline=0L;

@Column(name="minCrossline")
Long minCrossline=0L;

@Column(name="maxCrossline")
Long maxCrossline=0L;

@Column(name="incCrossline")
Long incCrossline=0L;

@Column(name="firstCrossline")
Long firstCrossline=0L;

@Column(name="lastCrossline")
Long lastCrossline=0L;

@Column(name="minShotpoint")
Long minShotpoint=0L;

@Column(name="maxShotpoint")
Long maxShotpoint=0L;

@Column(name="incShotpoint")
Long incShotpoint=0L;

@Column(name="firstShotpoint")
Long firstShotpoint=0L;

@Column(name="lastShotpoint")
Long lastShotpoint=0L;

@Column(name="minScalsp")
Long minScalsp=0L;

@Column(name="maxScalsp")
Long maxScalsp=0L;

@Column(name="incScalsp")
Long incScalsp=0L;

@Column(name="firstScalsp")
Long firstScalsp=0L;

@Column(name="lastScalsp")
Long lastScalsp=0L;

@Column(name="minTcm")
Long minTcm=0L;

@Column(name="maxTcm")
Long maxTcm=0L;

@Column(name="incTcm")
Long incTcm=0L;

@Column(name="firstTcm")
Long firstTcm=0L;

@Column(name="lastTcm")
Long lastTcm=0L;

@Column(name="minTid")
Long minTid=0L;

@Column(name="maxTid")
Long maxTid=0L;

@Column(name="incTid")
Long incTid=0L;

@Column(name="firstTid")
Long firstTid=0L;

@Column(name="lastTid")
Long lastTid=0L;

@Column(name="tid")
Long tid=0L;

@Column(name="minSedm")
Long minSedm=0L;

@Column(name="maxSedm")
Long maxSedm=0L;

@Column(name="incSedm")
Long incSedm=0L;

@Column(name="firstSedm")
Long firstSedm=0L;

@Column(name="lastSedm")
Long lastSedm=0L;

@Column(name="minSede")
Long minSede=0L;

@Column(name="maxSede")
Long maxSede=0L;

@Column(name="incSede")
Long incSede=0L;

@Column(name="firstSede")
Long firstSede=0L;

@Column(name="lastSede")
Long lastSede=0L;

@Column(name="sede")
Long sede=0L;

@Column(name="minSmm")
Long minSmm=0L;

@Column(name="maxSmm")
Long maxSmm=0L;

@Column(name="incSmm")
Long incSmm=0L;

@Column(name="firstSmm")
Long firstSmm=0L;

@Column(name="lastSmm")
Long lastSmm=0L;

@Column(name="minSme")
Long minSme=0L;

@Column(name="maxSme")
Long maxSme=0L;

@Column(name="incSme")
Long incSme=0L;

@Column(name="firstSme")
Long firstSme=0L;

@Column(name="lastSme")
Long lastSme=0L;

@Column(name="minSmu")
Long minSmu=0L;

@Column(name="maxSmu")
Long maxSmu=0L;

@Column(name="incSmu")
Long incSmu=0L;

@Column(name="firstSmu")
Long firstSmu=0L;

@Column(name="lastSmu")
Long lastSmu=0L;

@Column(name="minUi1")
Long minUi1=0L;

@Column(name="maxUi1")
Long maxUi1=0L;

@Column(name="incUi1")
Long incUi1=0L;

@Column(name="firstUi1")
Long firstUi1=0L;

@Column(name="lastUi1")
Long lastUi1=0L;

@Column(name="minUi2")
Long minUi2=0L;

@Column(name="maxUi2")
Long maxUi2=0L;

@Column(name="incUi2")
Long incUi2=0L;

@Column(name="firstUi2")
Long firstUi2=0L;

@Column(name="lastUi2")
Long lastUi2=0L;

@Column(name="ui2")
Long ui2=0L;

@Column(name="minVer")
Long minVer=0L;

@Column(name="maxVer")
Long maxVer=0L;

@Column(name="incVer")
Long incVer=0L;

@Column(name="sampleRate")
Long sampleRate=0L;

@Column(name="recordLength")
Long recordLength=0L;

@Column(name="unitVer")
Long unitVer=0L;

@Column(name="Deleted")
    private Boolean deleted=false; 
    
    @Column(name="NumberOfRuns")
    private Long numberOfRuns=0L;                                               //number of times the subsurface was run
    
        
    @Column(name="InsightVersion")
    private String insightVersion;                                        //version of insight as derived from the latest log.
    
    @Column(name="workflowVersion")                                       //version of workflow
    private Long workflowVersion;
    
    
    @Column(name="UpdateTime")
    private String updateTime;
    
    @Column(name="SummaryTime")
    private String summaryTime;
    
   
    
    @Column(name="multiple_instances")
    private Boolean multipleInstances=false;
    
    @Column(name="chosen")
    private Boolean chosen=true;


    public Long getfHeaderId() {
        return fHeaderId;
    }



    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Set<Log> getLogs() {
        return logs;
    }

    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }

    public SubsurfaceJob getSubsurfaceJob() {
        return subsurfaceJob;
    }

    public void setSubsurfaceJob(SubsurfaceJob subsurfaceJob) {
        this.subsurfaceJob = subsurfaceJob;
    }

    public Subsurface getSubsurface() {
        return subsurface;
    }

    public void setSubsurface(Subsurface subsurface) {
        this.subsurface = subsurface;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getTotalTraces() {
        return totalTraces;
    }

    public void setTotalTraces(Long totalTraces) {
        this.totalTraces = totalTraces;
    }

    public Long getMinTracr() {
        return minTracr;
    }

    public void setMinTracr(Long minTracr) {
        this.minTracr = minTracr;
    }

    public Long getMaxTracr() {
        return maxTracr;
    }

    public void setMaxTracr(Long maxTracr) {
        this.maxTracr = maxTracr;
    }

    public Long getIncTracr() {
        return incTracr;
    }

    public void setIncTracr(Long incTracr) {
        this.incTracr = incTracr;
    }

    public Long getFirstTracr() {
        return firstTracr;
    }

    public void setFirstTracr(Long firstTracr) {
        this.firstTracr = firstTracr;
    }

    public Long getLastTracr() {
        return lastTracr;
    }

    public void setLastTracr(Long lastTracr) {
        this.lastTracr = lastTracr;
    }

    public Long getMinFldr() {
        return minFldr;
    }

    public void setMinFldr(Long minFldr) {
        this.minFldr = minFldr;
    }

    public Long getMaxFldr() {
        return maxFldr;
    }

    public void setMaxFldr(Long maxFldr) {
        this.maxFldr = maxFldr;
    }

    public Long getIncFldr() {
        return incFldr;
    }

    public void setIncFldr(Long incFldr) {
        this.incFldr = incFldr;
    }

    public Long getFirstFldr() {
        return firstFldr;
    }

    public void setFirstFldr(Long firstFldr) {
        this.firstFldr = firstFldr;
    }

    public Long getLastFldr() {
        return lastFldr;
    }

    public void setLastFldr(Long lastFldr) {
        this.lastFldr = lastFldr;
    }

    public Long getMinTracf() {
        return minTracf;
    }

    public void setMinTracf(Long minTracf) {
        this.minTracf = minTracf;
    }

    public Long getMaxTracf() {
        return maxTracf;
    }

    public void setMaxTracf(Long maxTracf) {
        this.maxTracf = maxTracf;
    }

    public Long getIncTracf() {
        return incTracf;
    }

    public void setIncTracf(Long incTracf) {
        this.incTracf = incTracf;
    }

    public Long getFirstTracf() {
        return firstTracf;
    }

    public void setFirstTracf(Long firstTracf) {
        this.firstTracf = firstTracf;
    }

    public Long getLastTracf() {
        return lastTracf;
    }

    public void setLastTracf(Long lastTracf) {
        this.lastTracf = lastTracf;
    }

    public Long getMinEp() {
        return minEp;
    }

    public void setMinEp(Long minEp) {
        this.minEp = minEp;
    }

    public Long getMaxEp() {
        return maxEp;
    }

    public void setMaxEp(Long maxEp) {
        this.maxEp = maxEp;
    }

    public Long getIncEp() {
        return incEp;
    }

    public void setIncEp(Long incEp) {
        this.incEp = incEp;
    }

    public Long getFirstEp() {
        return firstEp;
    }

    public void setFirstEp(Long firstEp) {
        this.firstEp = firstEp;
    }

    public Long getLastEp() {
        return lastEp;
    }

    public void setLastEp(Long lastEp) {
        this.lastEp = lastEp;
    }

    public Long getMinCdp() {
        return minCdp;
    }

    public void setMinCdp(Long minCdp) {
        this.minCdp = minCdp;
    }

    public Long getMaxCdp() {
        return maxCdp;
    }

    public void setMaxCdp(Long maxCdp) {
        this.maxCdp = maxCdp;
    }

    public Long getIncCdp() {
        return incCdp;
    }

    public void setIncCdp(Long incCdp) {
        this.incCdp = incCdp;
    }

    public Long getFirstCdp() {
        return firstCdp;
    }

    public void setFirstCdp(Long firstCdp) {
        this.firstCdp = firstCdp;
    }

    public Long getLastCdp() {
        return lastCdp;
    }

    public void setLastCdp(Long lastCdp) {
        this.lastCdp = lastCdp;
    }

    public Long getMinCdpt() {
        return minCdpt;
    }

    public void setMinCdpt(Long minCdpt) {
        this.minCdpt = minCdpt;
    }

    public Long getMaxCdpt() {
        return maxCdpt;
    }

    public void setMaxCdpt(Long maxCdpt) {
        this.maxCdpt = maxCdpt;
    }

    public Long getIncCdpt() {
        return incCdpt;
    }

    public void setIncCdpt(Long incCdpt) {
        this.incCdpt = incCdpt;
    }

    public Long getFirstCdpt() {
        return firstCdpt;
    }

    public void setFirstCdpt(Long firstCdpt) {
        this.firstCdpt = firstCdpt;
    }

    public Long getLastCdpt() {
        return lastCdpt;
    }

    public void setLastCdpt(Long lastCdpt) {
        this.lastCdpt = lastCdpt;
    }

    public Long getMinTrid() {
        return minTrid;
    }

    public void setMinTrid(Long minTrid) {
        this.minTrid = minTrid;
    }

    public Long getMaxTrid() {
        return maxTrid;
    }

    public void setMaxTrid(Long maxTrid) {
        this.maxTrid = maxTrid;
    }

    public Long getIncTrid() {
        return incTrid;
    }

    public void setIncTrid(Long incTrid) {
        this.incTrid = incTrid;
    }

    public Long getFirstTrid() {
        return firstTrid;
    }

    public void setFirstTrid(Long firstTrid) {
        this.firstTrid = firstTrid;
    }

    public Long getLastTrid() {
        return lastTrid;
    }

    public void setLastTrid(Long lastTrid) {
        this.lastTrid = lastTrid;
    }

    public Long getMinDuse() {
        return minDuse;
    }

    public void setMinDuse(Long minDuse) {
        this.minDuse = minDuse;
    }

    public Long getMaxDuse() {
        return maxDuse;
    }

    public void setMaxDuse(Long maxDuse) {
        this.maxDuse = maxDuse;
    }

    public Long getIncDuse() {
        return incDuse;
    }

    public void setIncDuse(Long incDuse) {
        this.incDuse = incDuse;
    }

    public Long getFirstDuse() {
        return firstDuse;
    }

    public void setFirstDuse(Long firstDuse) {
        this.firstDuse = firstDuse;
    }

    public Long getLastDuse() {
        return lastDuse;
    }

    public void setLastDuse(Long lastDuse) {
        this.lastDuse = lastDuse;
    }

    public Long getDuse() {
        return duse;
    }

    public void setDuse(Long duse) {
        this.duse = duse;
    }

    public Long getMinOffset() {
        return minOffset;
    }

    public void setMinOffset(Long minOffset) {
        this.minOffset = minOffset;
    }

    public Long getMaxOffset() {
        return maxOffset;
    }

    public void setMaxOffset(Long maxOffset) {
        this.maxOffset = maxOffset;
    }

    public Long getIncOffset() {
        return incOffset;
    }

    public void setIncOffset(Long incOffset) {
        this.incOffset = incOffset;
    }

    public Long getFirstOffset() {
        return firstOffset;
    }

    public void setFirstOffset(Long firstOffset) {
        this.firstOffset = firstOffset;
    }

    public Long getLastOffset() {
        return lastOffset;
    }

    public void setLastOffset(Long lastOffset) {
        this.lastOffset = lastOffset;
    }

    public Long getMinGelev() {
        return minGelev;
    }

    public void setMinGelev(Long minGelev) {
        this.minGelev = minGelev;
    }

    public Long getMaxGelev() {
        return maxGelev;
    }

    public void setMaxGelev(Long maxGelev) {
        this.maxGelev = maxGelev;
    }

    public Long getIncGelev() {
        return incGelev;
    }

    public void setIncGelev(Long incGelev) {
        this.incGelev = incGelev;
    }

    public Long getFirstGelev() {
        return firstGelev;
    }

    public void setFirstGelev(Long firstGelev) {
        this.firstGelev = firstGelev;
    }

    public Long getLastGelev() {
        return lastGelev;
    }

    public void setLastGelev(Long lastGelev) {
        this.lastGelev = lastGelev;
    }

    public Long getMinSelev() {
        return minSelev;
    }

    public void setMinSelev(Long minSelev) {
        this.minSelev = minSelev;
    }

    public Long getMaxSelev() {
        return maxSelev;
    }

    public void setMaxSelev(Long maxSelev) {
        this.maxSelev = maxSelev;
    }

    public Long getIncSelev() {
        return incSelev;
    }

    public void setIncSelev(Long incSelev) {
        this.incSelev = incSelev;
    }

    public Long getFirstSelev() {
        return firstSelev;
    }

    public void setFirstSelev(Long firstSelev) {
        this.firstSelev = firstSelev;
    }

    public Long getLastSelev() {
        return lastSelev;
    }

    public void setLastSelev(Long lastSelev) {
        this.lastSelev = lastSelev;
    }

    public Long getMinSdepth() {
        return minSdepth;
    }

    public void setMinSdepth(Long minSdepth) {
        this.minSdepth = minSdepth;
    }

    public Long getMaxSdepth() {
        return maxSdepth;
    }

    public void setMaxSdepth(Long maxSdepth) {
        this.maxSdepth = maxSdepth;
    }

    public Long getIncSdepth() {
        return incSdepth;
    }

    public void setIncSdepth(Long incSdepth) {
        this.incSdepth = incSdepth;
    }

    public Long getFirstSdepth() {
        return firstSdepth;
    }

    public void setFirstSdepth(Long firstSdepth) {
        this.firstSdepth = firstSdepth;
    }

    public Long getLastSdepth() {
        return lastSdepth;
    }

    public void setLastSdepth(Long lastSdepth) {
        this.lastSdepth = lastSdepth;
    }

    public Long getMinSwdep() {
        return minSwdep;
    }

    public void setMinSwdep(Long minSwdep) {
        this.minSwdep = minSwdep;
    }

    public Long getMaxSwdep() {
        return maxSwdep;
    }

    public void setMaxSwdep(Long maxSwdep) {
        this.maxSwdep = maxSwdep;
    }

    public Long getIncSwdep() {
        return incSwdep;
    }

    public void setIncSwdep(Long incSwdep) {
        this.incSwdep = incSwdep;
    }

    public Long getFirstSwdep() {
        return firstSwdep;
    }

    public void setFirstSwdep(Long firstSwdep) {
        this.firstSwdep = firstSwdep;
    }

    public Long getLastSwdep() {
        return lastSwdep;
    }

    public void setLastSwdep(Long lastSwdep) {
        this.lastSwdep = lastSwdep;
    }

    public Long getMinGwdep() {
        return minGwdep;
    }

    public void setMinGwdep(Long minGwdep) {
        this.minGwdep = minGwdep;
    }

    public Long getMaxGwdep() {
        return maxGwdep;
    }

    public void setMaxGwdep(Long maxGwdep) {
        this.maxGwdep = maxGwdep;
    }

    public Long getIncGwdep() {
        return incGwdep;
    }

    public void setIncGwdep(Long incGwdep) {
        this.incGwdep = incGwdep;
    }

    public Long getFirstGwdep() {
        return firstGwdep;
    }

    public void setFirstGwdep(Long firstGwdep) {
        this.firstGwdep = firstGwdep;
    }

    public Long getLastGwdep() {
        return lastGwdep;
    }

    public void setLastGwdep(Long lastGwdep) {
        this.lastGwdep = lastGwdep;
    }

    public Long getMinScalel() {
        return minScalel;
    }

    public void setMinScalel(Long minScalel) {
        this.minScalel = minScalel;
    }

    public Long getMaxScalel() {
        return maxScalel;
    }

    public void setMaxScalel(Long maxScalel) {
        this.maxScalel = maxScalel;
    }

    public Long getIncScalel() {
        return incScalel;
    }

    public void setIncScalel(Long incScalel) {
        this.incScalel = incScalel;
    }

    public Long getFirstScalel() {
        return firstScalel;
    }

    public void setFirstScalel(Long firstScalel) {
        this.firstScalel = firstScalel;
    }

    public Long getLastScalel() {
        return lastScalel;
    }

    public void setLastScalel(Long lastScalel) {
        this.lastScalel = lastScalel;
    }

    public Long getScalel() {
        return scalel;
    }

    public void setScalel(Long scalel) {
        this.scalel = scalel;
    }

    public Long getMinScalco() {
        return minScalco;
    }

    public void setMinScalco(Long minScalco) {
        this.minScalco = minScalco;
    }

    public Long getMaxScalco() {
        return maxScalco;
    }

    public void setMaxScalco(Long maxScalco) {
        this.maxScalco = maxScalco;
    }

    public Long getIncScalco() {
        return incScalco;
    }

    public void setIncScalco(Long incScalco) {
        this.incScalco = incScalco;
    }

    public Long getFirstScalco() {
        return firstScalco;
    }

    public void setFirstScalco(Long firstScalco) {
        this.firstScalco = firstScalco;
    }

    public Long getLastScalco() {
        return lastScalco;
    }

    public void setLastScalco(Long lastScalco) {
        this.lastScalco = lastScalco;
    }

    public Long getScalco() {
        return scalco;
    }

    public void setScalco(Long scalco) {
        this.scalco = scalco;
    }

    public Long getMinSx() {
        return minSx;
    }

    public void setMinSx(Long minSx) {
        this.minSx = minSx;
    }

    public Long getMaxSx() {
        return maxSx;
    }

    public void setMaxSx(Long maxSx) {
        this.maxSx = maxSx;
    }

    public Long getIncSx() {
        return incSx;
    }

    public void setIncSx(Long incSx) {
        this.incSx = incSx;
    }

    public Long getFirstSx() {
        return firstSx;
    }

    public void setFirstSx(Long firstSx) {
        this.firstSx = firstSx;
    }

    public Long getLastSx() {
        return lastSx;
    }

    public void setLastSx(Long lastSx) {
        this.lastSx = lastSx;
    }

    public Long getMinSy() {
        return minSy;
    }

    public void setMinSy(Long minSy) {
        this.minSy = minSy;
    }

    public Long getMaxSy() {
        return maxSy;
    }

    public void setMaxSy(Long maxSy) {
        this.maxSy = maxSy;
    }

    public Long getIncSy() {
        return incSy;
    }

    public void setIncSy(Long incSy) {
        this.incSy = incSy;
    }

    public Long getFirstSy() {
        return firstSy;
    }

    public void setFirstSy(Long firstSy) {
        this.firstSy = firstSy;
    }

    public Long getLastSy() {
        return lastSy;
    }

    public void setLastSy(Long lastSy) {
        this.lastSy = lastSy;
    }

    public Long getMinGx() {
        return minGx;
    }

    public void setMinGx(Long minGx) {
        this.minGx = minGx;
    }

    public Long getMaxGx() {
        return maxGx;
    }

    public void setMaxGx(Long maxGx) {
        this.maxGx = maxGx;
    }

    public Long getIncGx() {
        return incGx;
    }

    public void setIncGx(Long incGx) {
        this.incGx = incGx;
    }

    public Long getFirstGx() {
        return firstGx;
    }

    public void setFirstGx(Long firstGx) {
        this.firstGx = firstGx;
    }

    public Long getLastGx() {
        return lastGx;
    }

    public void setLastGx(Long lastGx) {
        this.lastGx = lastGx;
    }

    public Long getMinGy() {
        return minGy;
    }

    public void setMinGy(Long minGy) {
        this.minGy = minGy;
    }

    public Long getMaxGy() {
        return maxGy;
    }

    public void setMaxGy(Long maxGy) {
        this.maxGy = maxGy;
    }

    public Long getIncGy() {
        return incGy;
    }

    public void setIncGy(Long incGy) {
        this.incGy = incGy;
    }

    public Long getFirstGy() {
        return firstGy;
    }

    public void setFirstGy(Long firstGy) {
        this.firstGy = firstGy;
    }

    public Long getLastGy() {
        return lastGy;
    }

    public void setLastGy(Long lastGy) {
        this.lastGy = lastGy;
    }

    public Long getMinWevel() {
        return minWevel;
    }

    public void setMinWevel(Long minWevel) {
        this.minWevel = minWevel;
    }

    public Long getMaxWevel() {
        return maxWevel;
    }

    public void setMaxWevel(Long maxWevel) {
        this.maxWevel = maxWevel;
    }

    public Long getIncWevel() {
        return incWevel;
    }

    public void setIncWevel(Long incWevel) {
        this.incWevel = incWevel;
    }

    public Long getFirstWevel() {
        return firstWevel;
    }

    public void setFirstWevel(Long firstWevel) {
        this.firstWevel = firstWevel;
    }

    public Long getLastWevel() {
        return lastWevel;
    }

    public void setLastWevel(Long lastWevel) {
        this.lastWevel = lastWevel;
    }

    public Long getWevel() {
        return wevel;
    }

    public void setWevel(Long wevel) {
        this.wevel = wevel;
    }

    public Long getMinSwevel() {
        return minSwevel;
    }

    public void setMinSwevel(Long minSwevel) {
        this.minSwevel = minSwevel;
    }

    public Long getMaxSwevel() {
        return maxSwevel;
    }

    public void setMaxSwevel(Long maxSwevel) {
        this.maxSwevel = maxSwevel;
    }

    public Long getIncSwevel() {
        return incSwevel;
    }

    public void setIncSwevel(Long incSwevel) {
        this.incSwevel = incSwevel;
    }

    public Long getFirstSwevel() {
        return firstSwevel;
    }

    public void setFirstSwevel(Long firstSwevel) {
        this.firstSwevel = firstSwevel;
    }

    public Long getLastSwevel() {
        return lastSwevel;
    }

    public void setLastSwevel(Long lastSwevel) {
        this.lastSwevel = lastSwevel;
    }

    public Long getSwevel() {
        return swevel;
    }

    public void setSwevel(Long swevel) {
        this.swevel = swevel;
    }

    public Long getMinSut() {
        return minSut;
    }

    public void setMinSut(Long minSut) {
        this.minSut = minSut;
    }

    public Long getMaxSut() {
        return maxSut;
    }

    public void setMaxSut(Long maxSut) {
        this.maxSut = maxSut;
    }

    public Long getIncSut() {
        return incSut;
    }

    public void setIncSut(Long incSut) {
        this.incSut = incSut;
    }

    public Long getFirstSut() {
        return firstSut;
    }

    public void setFirstSut(Long firstSut) {
        this.firstSut = firstSut;
    }

    public Long getLastSut() {
        return lastSut;
    }

    public void setLastSut(Long lastSut) {
        this.lastSut = lastSut;
    }

    public Long getMinGut() {
        return minGut;
    }

    public void setMinGut(Long minGut) {
        this.minGut = minGut;
    }

    public Long getMaxGut() {
        return maxGut;
    }

    public void setMaxGut(Long maxGut) {
        this.maxGut = maxGut;
    }

    public Long getIncGut() {
        return incGut;
    }

    public void setIncGut(Long incGut) {
        this.incGut = incGut;
    }

    public Long getFirstGut() {
        return firstGut;
    }

    public void setFirstGut(Long firstGut) {
        this.firstGut = firstGut;
    }

    public Long getLastGut() {
        return lastGut;
    }

    public void setLastGut(Long lastGut) {
        this.lastGut = lastGut;
    }

    public Long getMinSstat() {
        return minSstat;
    }

    public void setMinSstat(Long minSstat) {
        this.minSstat = minSstat;
    }

    public Long getMaxSstat() {
        return maxSstat;
    }

    public void setMaxSstat(Long maxSstat) {
        this.maxSstat = maxSstat;
    }

    public Long getIncSstat() {
        return incSstat;
    }

    public void setIncSstat(Long incSstat) {
        this.incSstat = incSstat;
    }

    public Long getFirstSstat() {
        return firstSstat;
    }

    public void setFirstSstat(Long firstSstat) {
        this.firstSstat = firstSstat;
    }

    public Long getLastSstat() {
        return lastSstat;
    }

    public void setLastSstat(Long lastSstat) {
        this.lastSstat = lastSstat;
    }

    public Long getMinGstat() {
        return minGstat;
    }

    public void setMinGstat(Long minGstat) {
        this.minGstat = minGstat;
    }

    public Long getMaxGstat() {
        return maxGstat;
    }

    public void setMaxGstat(Long maxGstat) {
        this.maxGstat = maxGstat;
    }

    public Long getIncGstat() {
        return incGstat;
    }

    public void setIncGstat(Long incGstat) {
        this.incGstat = incGstat;
    }

    public Long getFirstGstat() {
        return firstGstat;
    }

    public void setFirstGstat(Long firstGstat) {
        this.firstGstat = firstGstat;
    }

    public Long getLastGstat() {
        return lastGstat;
    }

    public void setLastGstat(Long lastGstat) {
        this.lastGstat = lastGstat;
    }

    public Long getMinLagb() {
        return minLagb;
    }

    public void setMinLagb(Long minLagb) {
        this.minLagb = minLagb;
    }

    public Long getMaxLagb() {
        return maxLagb;
    }

    public void setMaxLagb(Long maxLagb) {
        this.maxLagb = maxLagb;
    }

    public Long getIncLagb() {
        return incLagb;
    }

    public void setIncLagb(Long incLagb) {
        this.incLagb = incLagb;
    }

    public Long getFirstLagb() {
        return firstLagb;
    }

    public void setFirstLagb(Long firstLagb) {
        this.firstLagb = firstLagb;
    }

    public Long getLastLagb() {
        return lastLagb;
    }

    public void setLastLagb(Long lastLagb) {
        this.lastLagb = lastLagb;
    }

    public Long getMinNs() {
        return minNs;
    }

    public void setMinNs(Long minNs) {
        this.minNs = minNs;
    }

    public Long getMaxNs() {
        return maxNs;
    }

    public void setMaxNs(Long maxNs) {
        this.maxNs = maxNs;
    }

    public Long getIncNs() {
        return incNs;
    }

    public void setIncNs(Long incNs) {
        this.incNs = incNs;
    }

    public Long getFirstNs() {
        return firstNs;
    }

    public void setFirstNs(Long firstNs) {
        this.firstNs = firstNs;
    }

    public Long getLastNs() {
        return lastNs;
    }

    public void setLastNs(Long lastNs) {
        this.lastNs = lastNs;
    }

    public Long getNs() {
        return ns;
    }

    public void setNs(Long ns) {
        this.ns = ns;
    }

    public Long getMinDt() {
        return minDt;
    }

    public void setMinDt(Long minDt) {
        this.minDt = minDt;
    }

    public Long getMaxDt() {
        return maxDt;
    }

    public void setMaxDt(Long maxDt) {
        this.maxDt = maxDt;
    }

    public Long getIncDt() {
        return incDt;
    }

    public void setIncDt(Long incDt) {
        this.incDt = incDt;
    }

    public Long getFirstDt() {
        return firstDt;
    }

    public void setFirstDt(Long firstDt) {
        this.firstDt = firstDt;
    }

    public Long getLastDt() {
        return lastDt;
    }

    public void setLastDt(Long lastDt) {
        this.lastDt = lastDt;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Long getMinSfs() {
        return minSfs;
    }

    public void setMinSfs(Long minSfs) {
        this.minSfs = minSfs;
    }

    public Long getMaxSfs() {
        return maxSfs;
    }

    public void setMaxSfs(Long maxSfs) {
        this.maxSfs = maxSfs;
    }

    public Long getIncSfs() {
        return incSfs;
    }

    public void setIncSfs(Long incSfs) {
        this.incSfs = incSfs;
    }

    public Long getFirstSfs() {
        return firstSfs;
    }

    public void setFirstSfs(Long firstSfs) {
        this.firstSfs = firstSfs;
    }

    public Long getLastSfs() {
        return lastSfs;
    }

    public void setLastSfs(Long lastSfs) {
        this.lastSfs = lastSfs;
    }

    public Long getMinSfe() {
        return minSfe;
    }

    public void setMinSfe(Long minSfe) {
        this.minSfe = minSfe;
    }

    public Long getMaxSfe() {
        return maxSfe;
    }

    public void setMaxSfe(Long maxSfe) {
        this.maxSfe = maxSfe;
    }

    public Long getIncSfe() {
        return incSfe;
    }

    public void setIncSfe(Long incSfe) {
        this.incSfe = incSfe;
    }

    public Long getFirstSfe() {
        return firstSfe;
    }

    public void setFirstSfe(Long firstSfe) {
        this.firstSfe = firstSfe;
    }

    public Long getLastSfe() {
        return lastSfe;
    }

    public void setLastSfe(Long lastSfe) {
        this.lastSfe = lastSfe;
    }

    public Long getMinSlen() {
        return minSlen;
    }

    public void setMinSlen(Long minSlen) {
        this.minSlen = minSlen;
    }

    public Long getMaxSlen() {
        return maxSlen;
    }

    public void setMaxSlen(Long maxSlen) {
        this.maxSlen = maxSlen;
    }

    public Long getIncSlen() {
        return incSlen;
    }

    public void setIncSlen(Long incSlen) {
        this.incSlen = incSlen;
    }

    public Long getFirstSlen() {
        return firstSlen;
    }

    public void setFirstSlen(Long firstSlen) {
        this.firstSlen = firstSlen;
    }

    public Long getLastSlen() {
        return lastSlen;
    }

    public void setLastSlen(Long lastSlen) {
        this.lastSlen = lastSlen;
    }

    public Long getSlen() {
        return slen;
    }

    public void setSlen(Long slen) {
        this.slen = slen;
    }

    public Long getMinStyp() {
        return minStyp;
    }

    public void setMinStyp(Long minStyp) {
        this.minStyp = minStyp;
    }

    public Long getMaxStyp() {
        return maxStyp;
    }

    public void setMaxStyp(Long maxStyp) {
        this.maxStyp = maxStyp;
    }

    public Long getIncStyp() {
        return incStyp;
    }

    public void setIncStyp(Long incStyp) {
        this.incStyp = incStyp;
    }

    public Long getFirstStyp() {
        return firstStyp;
    }

    public void setFirstStyp(Long firstStyp) {
        this.firstStyp = firstStyp;
    }

    public Long getLastStyp() {
        return lastStyp;
    }

    public void setLastStyp(Long lastStyp) {
        this.lastStyp = lastStyp;
    }

    public Long getMinStas() {
        return minStas;
    }

    public void setMinStas(Long minStas) {
        this.minStas = minStas;
    }

    public Long getMaxStas() {
        return maxStas;
    }

    public void setMaxStas(Long maxStas) {
        this.maxStas = maxStas;
    }

    public Long getIncStas() {
        return incStas;
    }

    public void setIncStas(Long incStas) {
        this.incStas = incStas;
    }

    public Long getFirstStas() {
        return firstStas;
    }

    public void setFirstStas(Long firstStas) {
        this.firstStas = firstStas;
    }

    public Long getLastStas() {
        return lastStas;
    }

    public void setLastStas(Long lastStas) {
        this.lastStas = lastStas;
    }

    public Long getMinStae() {
        return minStae;
    }

    public void setMinStae(Long minStae) {
        this.minStae = minStae;
    }

    public Long getMaxStae() {
        return maxStae;
    }

    public void setMaxStae(Long maxStae) {
        this.maxStae = maxStae;
    }

    public Long getIncStae() {
        return incStae;
    }

    public void setIncStae(Long incStae) {
        this.incStae = incStae;
    }

    public Long getFirstStae() {
        return firstStae;
    }

    public void setFirstStae(Long firstStae) {
        this.firstStae = firstStae;
    }

    public Long getLastStae() {
        return lastStae;
    }

    public void setLastStae(Long lastStae) {
        this.lastStae = lastStae;
    }

    public Long getStae() {
        return stae;
    }

    public void setStae(Long stae) {
        this.stae = stae;
    }

    public Long getMinAfilf() {
        return minAfilf;
    }

    public void setMinAfilf(Long minAfilf) {
        this.minAfilf = minAfilf;
    }

    public Long getMaxAfilf() {
        return maxAfilf;
    }

    public void setMaxAfilf(Long maxAfilf) {
        this.maxAfilf = maxAfilf;
    }

    public Long getIncAfilf() {
        return incAfilf;
    }

    public void setIncAfilf(Long incAfilf) {
        this.incAfilf = incAfilf;
    }

    public Long getFirstAfilf() {
        return firstAfilf;
    }

    public void setFirstAfilf(Long firstAfilf) {
        this.firstAfilf = firstAfilf;
    }

    public Long getLastAfilf() {
        return lastAfilf;
    }

    public void setLastAfilf(Long lastAfilf) {
        this.lastAfilf = lastAfilf;
    }

    public Long getAfilf() {
        return afilf;
    }

    public void setAfilf(Long afilf) {
        this.afilf = afilf;
    }

    public Long getMinAfils() {
        return minAfils;
    }

    public void setMinAfils(Long minAfils) {
        this.minAfils = minAfils;
    }

    public Long getMaxAfils() {
        return maxAfils;
    }

    public void setMaxAfils(Long maxAfils) {
        this.maxAfils = maxAfils;
    }

    public Long getIncAfils() {
        return incAfils;
    }

    public void setIncAfils(Long incAfils) {
        this.incAfils = incAfils;
    }

    public Long getFirstAfils() {
        return firstAfils;
    }

    public void setFirstAfils(Long firstAfils) {
        this.firstAfils = firstAfils;
    }

    public Long getLastAfils() {
        return lastAfils;
    }

    public void setLastAfils(Long lastAfils) {
        this.lastAfils = lastAfils;
    }

    public Long getAfils() {
        return afils;
    }

    public void setAfils(Long afils) {
        this.afils = afils;
    }

    public Long getMinLcf() {
        return minLcf;
    }

    public void setMinLcf(Long minLcf) {
        this.minLcf = minLcf;
    }

    public Long getMaxLcf() {
        return maxLcf;
    }

    public void setMaxLcf(Long maxLcf) {
        this.maxLcf = maxLcf;
    }

    public Long getIncLcf() {
        return incLcf;
    }

    public void setIncLcf(Long incLcf) {
        this.incLcf = incLcf;
    }

    public Long getFirstLcf() {
        return firstLcf;
    }

    public void setFirstLcf(Long firstLcf) {
        this.firstLcf = firstLcf;
    }

    public Long getLastLcf() {
        return lastLcf;
    }

    public void setLastLcf(Long lastLcf) {
        this.lastLcf = lastLcf;
    }

    public Long getLcf() {
        return lcf;
    }

    public void setLcf(Long lcf) {
        this.lcf = lcf;
    }

    public Long getMinLcs() {
        return minLcs;
    }

    public void setMinLcs(Long minLcs) {
        this.minLcs = minLcs;
    }

    public Long getMaxLcs() {
        return maxLcs;
    }

    public void setMaxLcs(Long maxLcs) {
        this.maxLcs = maxLcs;
    }

    public Long getIncLcs() {
        return incLcs;
    }

    public void setIncLcs(Long incLcs) {
        this.incLcs = incLcs;
    }

    public Long getFirstLcs() {
        return firstLcs;
    }

    public void setFirstLcs(Long firstLcs) {
        this.firstLcs = firstLcs;
    }

    public Long getLastLcs() {
        return lastLcs;
    }

    public void setLastLcs(Long lastLcs) {
        this.lastLcs = lastLcs;
    }

    public Long getLcs() {
        return lcs;
    }

    public void setLcs(Long lcs) {
        this.lcs = lcs;
    }

    public Long getMinHcs() {
        return minHcs;
    }

    public void setMinHcs(Long minHcs) {
        this.minHcs = minHcs;
    }

    public Long getMaxHcs() {
        return maxHcs;
    }

    public void setMaxHcs(Long maxHcs) {
        this.maxHcs = maxHcs;
    }

    public Long getIncHcs() {
        return incHcs;
    }

    public void setIncHcs(Long incHcs) {
        this.incHcs = incHcs;
    }

    public Long getFirstHcs() {
        return firstHcs;
    }

    public void setFirstHcs(Long firstHcs) {
        this.firstHcs = firstHcs;
    }

    public Long getLastHcs() {
        return lastHcs;
    }

    public void setLastHcs(Long lastHcs) {
        this.lastHcs = lastHcs;
    }

    public Long getMinYear() {
        return minYear;
    }

    public void setMinYear(Long minYear) {
        this.minYear = minYear;
    }

    public Long getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(Long maxYear) {
        this.maxYear = maxYear;
    }

    public Long getIncYear() {
        return incYear;
    }

    public void setIncYear(Long incYear) {
        this.incYear = incYear;
    }

    public Long getFirstYear() {
        return firstYear;
    }

    public void setFirstYear(Long firstYear) {
        this.firstYear = firstYear;
    }

    public Long getLastYear() {
        return lastYear;
    }

    public void setLastYear(Long lastYear) {
        this.lastYear = lastYear;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getMinDay() {
        return minDay;
    }

    public void setMinDay(Long minDay) {
        this.minDay = minDay;
    }

    public Long getMaxDay() {
        return maxDay;
    }

    public void setMaxDay(Long maxDay) {
        this.maxDay = maxDay;
    }

    public Long getIncDay() {
        return incDay;
    }

    public void setIncDay(Long incDay) {
        this.incDay = incDay;
    }

    public Long getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(Long firstDay) {
        this.firstDay = firstDay;
    }

    public Long getLastDay() {
        return lastDay;
    }

    public void setLastDay(Long lastDay) {
        this.lastDay = lastDay;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public Long getMinHour() {
        return minHour;
    }

    public void setMinHour(Long minHour) {
        this.minHour = minHour;
    }

    public Long getMaxHour() {
        return maxHour;
    }

    public void setMaxHour(Long maxHour) {
        this.maxHour = maxHour;
    }

    public Long getIncHour() {
        return incHour;
    }

    public void setIncHour(Long incHour) {
        this.incHour = incHour;
    }

    public Long getFirstHour() {
        return firstHour;
    }

    public void setFirstHour(Long firstHour) {
        this.firstHour = firstHour;
    }

    public Long getLastHour() {
        return lastHour;
    }

    public void setLastHour(Long lastHour) {
        this.lastHour = lastHour;
    }

    public Long getMinMinute() {
        return minMinute;
    }

    public void setMinMinute(Long minMinute) {
        this.minMinute = minMinute;
    }

    public Long getMaxMinute() {
        return maxMinute;
    }

    public void setMaxMinute(Long maxMinute) {
        this.maxMinute = maxMinute;
    }

    public Long getIncMinute() {
        return incMinute;
    }

    public void setIncMinute(Long incMinute) {
        this.incMinute = incMinute;
    }

    public Long getFirstMinute() {
        return firstMinute;
    }

    public void setFirstMinute(Long firstMinute) {
        this.firstMinute = firstMinute;
    }

    public Long getLastMinute() {
        return lastMinute;
    }

    public void setLastMinute(Long lastMinute) {
        this.lastMinute = lastMinute;
    }

    public Long getMinSec() {
        return minSec;
    }

    public void setMinSec(Long minSec) {
        this.minSec = minSec;
    }

    public Long getMaxSec() {
        return maxSec;
    }

    public void setMaxSec(Long maxSec) {
        this.maxSec = maxSec;
    }

    public Long getIncSec() {
        return incSec;
    }

    public void setIncSec(Long incSec) {
        this.incSec = incSec;
    }

    public Long getFirstSec() {
        return firstSec;
    }

    public void setFirstSec(Long firstSec) {
        this.firstSec = firstSec;
    }

    public Long getLastSec() {
        return lastSec;
    }

    public void setLastSec(Long lastSec) {
        this.lastSec = lastSec;
    }

    public Long getMinGrnors() {
        return minGrnors;
    }

    public void setMinGrnors(Long minGrnors) {
        this.minGrnors = minGrnors;
    }

    public Long getMaxGrnors() {
        return maxGrnors;
    }

    public void setMaxGrnors(Long maxGrnors) {
        this.maxGrnors = maxGrnors;
    }

    public Long getIncGrnors() {
        return incGrnors;
    }

    public void setIncGrnors(Long incGrnors) {
        this.incGrnors = incGrnors;
    }

    public Long getFirstGrnors() {
        return firstGrnors;
    }

    public void setFirstGrnors(Long firstGrnors) {
        this.firstGrnors = firstGrnors;
    }

    public Long getLastGrnors() {
        return lastGrnors;
    }

    public void setLastGrnors(Long lastGrnors) {
        this.lastGrnors = lastGrnors;
    }

    public Long getGrnors() {
        return grnors;
    }

    public void setGrnors(Long grnors) {
        this.grnors = grnors;
    }

    public Long getMinGrnofr() {
        return minGrnofr;
    }

    public void setMinGrnofr(Long minGrnofr) {
        this.minGrnofr = minGrnofr;
    }

    public Long getMaxGrnofr() {
        return maxGrnofr;
    }

    public void setMaxGrnofr(Long maxGrnofr) {
        this.maxGrnofr = maxGrnofr;
    }

    public Long getIncGrnofr() {
        return incGrnofr;
    }

    public void setIncGrnofr(Long incGrnofr) {
        this.incGrnofr = incGrnofr;
    }

    public Long getFirstGrnofr() {
        return firstGrnofr;
    }

    public void setFirstGrnofr(Long firstGrnofr) {
        this.firstGrnofr = firstGrnofr;
    }

    public Long getLastGrnofr() {
        return lastGrnofr;
    }

    public void setLastGrnofr(Long lastGrnofr) {
        this.lastGrnofr = lastGrnofr;
    }

    public Long getGrnofr() {
        return grnofr;
    }

    public void setGrnofr(Long grnofr) {
        this.grnofr = grnofr;
    }

    public Long getMinGaps() {
        return minGaps;
    }

    public void setMinGaps(Long minGaps) {
        this.minGaps = minGaps;
    }

    public Long getMaxGaps() {
        return maxGaps;
    }

    public void setMaxGaps(Long maxGaps) {
        this.maxGaps = maxGaps;
    }

    public Long getIncGaps() {
        return incGaps;
    }

    public void setIncGaps(Long incGaps) {
        this.incGaps = incGaps;
    }

    public Long getFirstGaps() {
        return firstGaps;
    }

    public void setFirstGaps(Long firstGaps) {
        this.firstGaps = firstGaps;
    }

    public Long getLastGaps() {
        return lastGaps;
    }

    public void setLastGaps(Long lastGaps) {
        this.lastGaps = lastGaps;
    }

    public Long getMinOtrav() {
        return minOtrav;
    }

    public void setMinOtrav(Long minOtrav) {
        this.minOtrav = minOtrav;
    }

    public Long getMaxOtrav() {
        return maxOtrav;
    }

    public void setMaxOtrav(Long maxOtrav) {
        this.maxOtrav = maxOtrav;
    }

    public Long getIncOtrav() {
        return incOtrav;
    }

    public void setIncOtrav(Long incOtrav) {
        this.incOtrav = incOtrav;
    }

    public Long getFirstOtrav() {
        return firstOtrav;
    }

    public void setFirstOtrav(Long firstOtrav) {
        this.firstOtrav = firstOtrav;
    }

    public Long getLastOtrav() {
        return lastOtrav;
    }

    public void setLastOtrav(Long lastOtrav) {
        this.lastOtrav = lastOtrav;
    }

    public Long getMinCdpx() {
        return minCdpx;
    }

    public void setMinCdpx(Long minCdpx) {
        this.minCdpx = minCdpx;
    }

    public Long getMaxCdpx() {
        return maxCdpx;
    }

    public void setMaxCdpx(Long maxCdpx) {
        this.maxCdpx = maxCdpx;
    }

    public Long getIncCdpx() {
        return incCdpx;
    }

    public void setIncCdpx(Long incCdpx) {
        this.incCdpx = incCdpx;
    }

    public Long getFirstCdpx() {
        return firstCdpx;
    }

    public void setFirstCdpx(Long firstCdpx) {
        this.firstCdpx = firstCdpx;
    }

    public Long getLastCdpx() {
        return lastCdpx;
    }

    public void setLastCdpx(Long lastCdpx) {
        this.lastCdpx = lastCdpx;
    }

    public Long getMinCdpy() {
        return minCdpy;
    }

    public void setMinCdpy(Long minCdpy) {
        this.minCdpy = minCdpy;
    }

    public Long getMaxCdpy() {
        return maxCdpy;
    }

    public void setMaxCdpy(Long maxCdpy) {
        this.maxCdpy = maxCdpy;
    }

    public Long getIncCdpy() {
        return incCdpy;
    }

    public void setIncCdpy(Long incCdpy) {
        this.incCdpy = incCdpy;
    }

    public Long getFirstCdpy() {
        return firstCdpy;
    }

    public void setFirstCdpy(Long firstCdpy) {
        this.firstCdpy = firstCdpy;
    }

    public Long getLastCdpy() {
        return lastCdpy;
    }

    public void setLastCdpy(Long lastCdpy) {
        this.lastCdpy = lastCdpy;
    }

    public Long getMinInline() {
        return minInline;
    }

    public void setMinInline(Long minInline) {
        this.minInline = minInline;
    }

    public Long getMaxInline() {
        return maxInline;
    }

    public void setMaxInline(Long maxInline) {
        this.maxInline = maxInline;
    }

    public Long getIncInline() {
        return incInline;
    }

    public void setIncInline(Long incInline) {
        this.incInline = incInline;
    }

    public Long getFirstInline() {
        return firstInline;
    }

    public void setFirstInline(Long firstInline) {
        this.firstInline = firstInline;
    }

    public Long getLastInline() {
        return lastInline;
    }

    public void setLastInline(Long lastInline) {
        this.lastInline = lastInline;
    }

    public Long getMinCrossline() {
        return minCrossline;
    }

    public void setMinCrossline(Long minCrossline) {
        this.minCrossline = minCrossline;
    }

    public Long getMaxCrossline() {
        return maxCrossline;
    }

    public void setMaxCrossline(Long maxCrossline) {
        this.maxCrossline = maxCrossline;
    }

    public Long getIncCrossline() {
        return incCrossline;
    }

    public void setIncCrossline(Long incCrossline) {
        this.incCrossline = incCrossline;
    }

    public Long getFirstCrossline() {
        return firstCrossline;
    }

    public void setFirstCrossline(Long firstCrossline) {
        this.firstCrossline = firstCrossline;
    }

    public Long getLastCrossline() {
        return lastCrossline;
    }

    public void setLastCrossline(Long lastCrossline) {
        this.lastCrossline = lastCrossline;
    }

    public Long getMinShotpoint() {
        return minShotpoint;
    }

    public void setMinShotpoint(Long minShotpoint) {
        this.minShotpoint = minShotpoint;
    }

    public Long getMaxShotpoint() {
        return maxShotpoint;
    }

    public void setMaxShotpoint(Long maxShotpoint) {
        this.maxShotpoint = maxShotpoint;
    }

    public Long getIncShotpoint() {
        return incShotpoint;
    }

    public void setIncShotpoint(Long incShotpoint) {
        this.incShotpoint = incShotpoint;
    }

    public Long getFirstShotpoint() {
        return firstShotpoint;
    }

    public void setFirstShotpoint(Long firstShotpoint) {
        this.firstShotpoint = firstShotpoint;
    }

    public Long getLastShotpoint() {
        return lastShotpoint;
    }

    public void setLastShotpoint(Long lastShotpoint) {
        this.lastShotpoint = lastShotpoint;
    }

    public Long getMinScalsp() {
        return minScalsp;
    }

    public void setMinScalsp(Long minScalsp) {
        this.minScalsp = minScalsp;
    }

    public Long getMaxScalsp() {
        return maxScalsp;
    }

    public void setMaxScalsp(Long maxScalsp) {
        this.maxScalsp = maxScalsp;
    }

    public Long getIncScalsp() {
        return incScalsp;
    }

    public void setIncScalsp(Long incScalsp) {
        this.incScalsp = incScalsp;
    }

    public Long getFirstScalsp() {
        return firstScalsp;
    }

    public void setFirstScalsp(Long firstScalsp) {
        this.firstScalsp = firstScalsp;
    }

    public Long getLastScalsp() {
        return lastScalsp;
    }

    public void setLastScalsp(Long lastScalsp) {
        this.lastScalsp = lastScalsp;
    }

    public Long getMinTcm() {
        return minTcm;
    }

    public void setMinTcm(Long minTcm) {
        this.minTcm = minTcm;
    }

    public Long getMaxTcm() {
        return maxTcm;
    }

    public void setMaxTcm(Long maxTcm) {
        this.maxTcm = maxTcm;
    }

    public Long getIncTcm() {
        return incTcm;
    }

    public void setIncTcm(Long incTcm) {
        this.incTcm = incTcm;
    }

    public Long getFirstTcm() {
        return firstTcm;
    }

    public void setFirstTcm(Long firstTcm) {
        this.firstTcm = firstTcm;
    }

    public Long getLastTcm() {
        return lastTcm;
    }

    public void setLastTcm(Long lastTcm) {
        this.lastTcm = lastTcm;
    }

    public Long getMinTid() {
        return minTid;
    }

    public void setMinTid(Long minTid) {
        this.minTid = minTid;
    }

    public Long getMaxTid() {
        return maxTid;
    }

    public void setMaxTid(Long maxTid) {
        this.maxTid = maxTid;
    }

    public Long getIncTid() {
        return incTid;
    }

    public void setIncTid(Long incTid) {
        this.incTid = incTid;
    }

    public Long getFirstTid() {
        return firstTid;
    }

    public void setFirstTid(Long firstTid) {
        this.firstTid = firstTid;
    }

    public Long getLastTid() {
        return lastTid;
    }

    public void setLastTid(Long lastTid) {
        this.lastTid = lastTid;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getMinSedm() {
        return minSedm;
    }

    public void setMinSedm(Long minSedm) {
        this.minSedm = minSedm;
    }

    public Long getMaxSedm() {
        return maxSedm;
    }

    public void setMaxSedm(Long maxSedm) {
        this.maxSedm = maxSedm;
    }

    public Long getIncSedm() {
        return incSedm;
    }

    public void setIncSedm(Long incSedm) {
        this.incSedm = incSedm;
    }

    public Long getFirstSedm() {
        return firstSedm;
    }

    public void setFirstSedm(Long firstSedm) {
        this.firstSedm = firstSedm;
    }

    public Long getLastSedm() {
        return lastSedm;
    }

    public void setLastSedm(Long lastSedm) {
        this.lastSedm = lastSedm;
    }

    public Long getMinSede() {
        return minSede;
    }

    public void setMinSede(Long minSede) {
        this.minSede = minSede;
    }

    public Long getMaxSede() {
        return maxSede;
    }

    public void setMaxSede(Long maxSede) {
        this.maxSede = maxSede;
    }

    public Long getIncSede() {
        return incSede;
    }

    public void setIncSede(Long incSede) {
        this.incSede = incSede;
    }

    public Long getFirstSede() {
        return firstSede;
    }

    public void setFirstSede(Long firstSede) {
        this.firstSede = firstSede;
    }

    public Long getLastSede() {
        return lastSede;
    }

    public void setLastSede(Long lastSede) {
        this.lastSede = lastSede;
    }

    public Long getSede() {
        return sede;
    }

    public void setSede(Long sede) {
        this.sede = sede;
    }

    public Long getMinSmm() {
        return minSmm;
    }

    public void setMinSmm(Long minSmm) {
        this.minSmm = minSmm;
    }

    public Long getMaxSmm() {
        return maxSmm;
    }

    public void setMaxSmm(Long maxSmm) {
        this.maxSmm = maxSmm;
    }

    public Long getIncSmm() {
        return incSmm;
    }

    public void setIncSmm(Long incSmm) {
        this.incSmm = incSmm;
    }

    public Long getFirstSmm() {
        return firstSmm;
    }

    public void setFirstSmm(Long firstSmm) {
        this.firstSmm = firstSmm;
    }

    public Long getLastSmm() {
        return lastSmm;
    }

    public void setLastSmm(Long lastSmm) {
        this.lastSmm = lastSmm;
    }

    public Long getMinSme() {
        return minSme;
    }

    public void setMinSme(Long minSme) {
        this.minSme = minSme;
    }

    public Long getMaxSme() {
        return maxSme;
    }

    public void setMaxSme(Long maxSme) {
        this.maxSme = maxSme;
    }

    public Long getIncSme() {
        return incSme;
    }

    public void setIncSme(Long incSme) {
        this.incSme = incSme;
    }

    public Long getFirstSme() {
        return firstSme;
    }

    public void setFirstSme(Long firstSme) {
        this.firstSme = firstSme;
    }

    public Long getLastSme() {
        return lastSme;
    }

    public void setLastSme(Long lastSme) {
        this.lastSme = lastSme;
    }

    public Long getMinSmu() {
        return minSmu;
    }

    public void setMinSmu(Long minSmu) {
        this.minSmu = minSmu;
    }

    public Long getMaxSmu() {
        return maxSmu;
    }

    public void setMaxSmu(Long maxSmu) {
        this.maxSmu = maxSmu;
    }

    public Long getIncSmu() {
        return incSmu;
    }

    public void setIncSmu(Long incSmu) {
        this.incSmu = incSmu;
    }

    public Long getFirstSmu() {
        return firstSmu;
    }

    public void setFirstSmu(Long firstSmu) {
        this.firstSmu = firstSmu;
    }

    public Long getLastSmu() {
        return lastSmu;
    }

    public void setLastSmu(Long lastSmu) {
        this.lastSmu = lastSmu;
    }

    public Long getMinUi1() {
        return minUi1;
    }

    public void setMinUi1(Long minUi1) {
        this.minUi1 = minUi1;
    }

    public Long getMaxUi1() {
        return maxUi1;
    }

    public void setMaxUi1(Long maxUi1) {
        this.maxUi1 = maxUi1;
    }

    public Long getIncUi1() {
        return incUi1;
    }

    public void setIncUi1(Long incUi1) {
        this.incUi1 = incUi1;
    }

    public Long getFirstUi1() {
        return firstUi1;
    }

    public void setFirstUi1(Long firstUi1) {
        this.firstUi1 = firstUi1;
    }

    public Long getLastUi1() {
        return lastUi1;
    }

    public void setLastUi1(Long lastUi1) {
        this.lastUi1 = lastUi1;
    }

    public Long getMinUi2() {
        return minUi2;
    }

    public void setMinUi2(Long minUi2) {
        this.minUi2 = minUi2;
    }

    public Long getMaxUi2() {
        return maxUi2;
    }

    public void setMaxUi2(Long maxUi2) {
        this.maxUi2 = maxUi2;
    }

    public Long getIncUi2() {
        return incUi2;
    }

    public void setIncUi2(Long incUi2) {
        this.incUi2 = incUi2;
    }

    public Long getFirstUi2() {
        return firstUi2;
    }

    public void setFirstUi2(Long firstUi2) {
        this.firstUi2 = firstUi2;
    }

    public Long getLastUi2() {
        return lastUi2;
    }

    public void setLastUi2(Long lastUi2) {
        this.lastUi2 = lastUi2;
    }

    public Long getUi2() {
        return ui2;
    }

    public void setUi2(Long ui2) {
        this.ui2 = ui2;
    }

    public Long getMinVer() {
        return minVer;
    }

    public void setMinVer(Long minVer) {
        this.minVer = minVer;
    }

    public Long getMaxVer() {
        return maxVer;
    }

    public void setMaxVer(Long maxVer) {
        this.maxVer = maxVer;
    }

    public Long getIncVer() {
        return incVer;
    }

    public void setIncVer(Long incVer) {
        this.incVer = incVer;
    }

    public Long getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(Long sampleRate) {
        this.sampleRate = sampleRate;
    }

    public Long getRecordLength() {
        return recordLength;
    }

    public void setRecordLength(Long recordLength) {
        this.recordLength = recordLength;
    }

    public Long getUnitVer() {
        return unitVer;
    }

    public void setUnitVer(Long unitVer) {
        this.unitVer = unitVer;
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

    public void setNumberOfRuns(Long numberOfRuns) {
        this.numberOfRuns = numberOfRuns;
    }

    public String getInsightVersion() {
        return insightVersion;
    }

    public void setInsightVersion(String insightVersion) {
        this.insightVersion = insightVersion;
    }

    public Long getWorkflowVersion() {
        return workflowVersion;
    }

    public void setWorkflowVersion(Long workflowVersion) {
        this.workflowVersion = workflowVersion;
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

    public Boolean getMultipleInstances() {
        return multipleInstances;
    }

    public void setMultipleInstances(Boolean multipleInstances) {
        this.multipleInstances = multipleInstances;
    }

    public Boolean getChosen() {
        return chosen;
    }

    public void setChosen(Boolean chosen) {
        this.chosen = chosen;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.fHeaderId);
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
        final Fheader other = (Fheader) obj;
        if (!Objects.equals(this.fHeaderId, other.fHeaderId)) {
            return false;
        }
        return true;
    }

    
    /**
     * generated using 
     * dugio read file=160_P_Overlapping_shot_attenuation.dugio line=696501061101_cable5_gun1 query=time:0 full_headers | durange raw=true output=bash | awk '{split($0,a,"=");print a[1]"=l;"}
     */
    public void setAll(long l) {
        totalTraces=l;
minTracr=l;
maxTracr=l;
incTracr=l;
firstTracr=l;
lastTracr=l;
minFldr=l;
maxFldr=l;
incFldr=l;
firstFldr=l;
lastFldr=l;
minTracf=l;
maxTracf=l;
incTracf=l;
firstTracf=l;
lastTracf=l;
minEp=l;
maxEp=l;
incEp=l;
firstEp=l;
lastEp=l;
minCdp=l;
maxCdp=l;
incCdp=l;
firstCdp=l;
lastCdp=l;
minCdpt=l;
maxCdpt=l;
incCdpt=l;
firstCdpt=l;
lastCdpt=l;
minTrid=l;
maxTrid=l;
incTrid=l;
firstTrid=l;
lastTrid=l;
minDuse=l;
maxDuse=l;
incDuse=l;
firstDuse=l;
lastDuse=l;
duse=l;
minOffset=l;
maxOffset=l;
incOffset=l;
firstOffset=l;
lastOffset=l;
minGelev=l;
maxGelev=l;
incGelev=l;
firstGelev=l;
lastGelev=l;
minSelev=l;
maxSelev=l;
incSelev=l;
firstSelev=l;
lastSelev=l;
minSdepth=l;
maxSdepth=l;
incSdepth=l;
firstSdepth=l;
lastSdepth=l;
minSwdep=l;
maxSwdep=l;
incSwdep=l;
firstSwdep=l;
lastSwdep=l;
minGwdep=l;
maxGwdep=l;
incGwdep=l;
firstGwdep=l;
lastGwdep=l;
minScalel=l;
maxScalel=l;
incScalel=l;
firstScalel=l;
lastScalel=l;
scalel=l;
minScalco=l;
maxScalco=l;
incScalco=l;
firstScalco=l;
lastScalco=l;
scalco=l;
minSx=l;
maxSx=l;
incSx=l;
firstSx=l;
lastSx=l;
minSy=l;
maxSy=l;
incSy=l;
firstSy=l;
lastSy=l;
minGx=l;
maxGx=l;
incGx=l;
firstGx=l;
lastGx=l;
minGy=l;
maxGy=l;
incGy=l;
firstGy=l;
lastGy=l;
minWevel=l;
maxWevel=l;
incWevel=l;
firstWevel=l;
lastWevel=l;
wevel=l;
minSwevel=l;
maxSwevel=l;
incSwevel=l;
firstSwevel=l;
lastSwevel=l;
swevel=l;
minSut=l;
maxSut=l;
incSut=l;
firstSut=l;
lastSut=l;
minGut=l;
maxGut=l;
incGut=l;
firstGut=l;
lastGut=l;
minSstat=l;
maxSstat=l;
incSstat=l;
firstSstat=l;
lastSstat=l;
minGstat=l;
maxGstat=l;
incGstat=l;
firstGstat=l;
lastGstat=l;
minLagb=l;
maxLagb=l;
incLagb=l;
firstLagb=l;
lastLagb=l;
minNs=l;
maxNs=l;
incNs=l;
firstNs=l;
lastNs=l;
ns=l;
minDt=l;
maxDt=l;
incDt=l;
firstDt=l;
lastDt=l;
dt=l;
minSfs=l;
maxSfs=l;
incSfs=l;
firstSfs=l;
lastSfs=l;
minSfe=l;
maxSfe=l;
incSfe=l;
firstSfe=l;
lastSfe=l;
minSlen=l;
maxSlen=l;
incSlen=l;
firstSlen=l;
lastSlen=l;
slen=l;
minStyp=l;
maxStyp=l;
incStyp=l;
firstStyp=l;
lastStyp=l;
minStas=l;
maxStas=l;
incStas=l;
firstStas=l;
lastStas=l;
minStae=l;
maxStae=l;
incStae=l;
firstStae=l;
lastStae=l;
stae=l;
minAfilf=l;
maxAfilf=l;
incAfilf=l;
firstAfilf=l;
lastAfilf=l;
afilf=l;
minAfils=l;
maxAfils=l;
incAfils=l;
firstAfils=l;
lastAfils=l;
afils=l;
minLcf=l;
maxLcf=l;
incLcf=l;
firstLcf=l;
lastLcf=l;
lcf=l;
minLcs=l;
maxLcs=l;
incLcs=l;
firstLcs=l;
lastLcs=l;
lcs=l;
minHcs=l;
maxHcs=l;
incHcs=l;
firstHcs=l;
lastHcs=l;
minYear=l;
maxYear=l;
incYear=l;
firstYear=l;
lastYear=l;
year=l;
minDay=l;
maxDay=l;
incDay=l;
firstDay=l;
lastDay=l;
day=l;
minHour=l;
maxHour=l;
incHour=l;
firstHour=l;
lastHour=l;
minMinute=l;
maxMinute=l;
incMinute=l;
firstMinute=l;
lastMinute=l;
minSec=l;
maxSec=l;
incSec=l;
firstSec=l;
lastSec=l;
minGrnors=l;
maxGrnors=l;
incGrnors=l;
firstGrnors=l;
lastGrnors=l;
grnors=l;
minGrnofr=l;
maxGrnofr=l;
incGrnofr=l;
firstGrnofr=l;
lastGrnofr=l;
grnofr=l;
minGaps=l;
maxGaps=l;
incGaps=l;
firstGaps=l;
lastGaps=l;
minOtrav=l;
maxOtrav=l;
incOtrav=l;
firstOtrav=l;
lastOtrav=l;
minCdpx=l;
maxCdpx=l;
incCdpx=l;
firstCdpx=l;
lastCdpx=l;
minCdpy=l;
maxCdpy=l;
incCdpy=l;
firstCdpy=l;
lastCdpy=l;
minInline=l;
maxInline=l;
incInline=l;
firstInline=l;
lastInline=l;
minCrossline=l;
maxCrossline=l;
incCrossline=l;
firstCrossline=l;
lastCrossline=l;
minShotpoint=l;
maxShotpoint=l;
incShotpoint=l;
firstShotpoint=l;
lastShotpoint=l;
minScalsp=l;
maxScalsp=l;
incScalsp=l;
firstScalsp=l;
lastScalsp=l;
minTcm=l;
maxTcm=l;
incTcm=l;
firstTcm=l;
lastTcm=l;
minTid=l;
maxTid=l;
incTid=l;
firstTid=l;
lastTid=l;
tid=l;
minSedm=l;
maxSedm=l;
incSedm=l;
firstSedm=l;
lastSedm=l;
minSede=l;
maxSede=l;
incSede=l;
firstSede=l;
lastSede=l;
sede=l;
minSmm=l;
maxSmm=l;
incSmm=l;
firstSmm=l;
lastSmm=l;
minSme=l;
maxSme=l;
incSme=l;
firstSme=l;
lastSme=l;
minSmu=l;
maxSmu=l;
incSmu=l;
firstSmu=l;
lastSmu=l;
minUi1=l;
maxUi1=l;
incUi1=l;
firstUi1=l;
lastUi1=l;
minUi2=l;
maxUi2=l;
incUi2=l;
firstUi2=l;
lastUi2=l;
ui2=l;
minVer=l;
maxVer=l;
incVer=l;
sampleRate=l;
recordLength=l;
unitVer=l;

    }





    
    
    
}
