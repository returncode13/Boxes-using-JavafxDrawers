/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.sequences.fullHeaders;

import db.model.Sequence;
import db.model.Volume;
import fend.volume.volume0.Volume0;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class FullSequenceHeaders {
    protected boolean  isSequence=true;
    protected boolean isSubsurface=false;
       
    
protected LongProperty totalTraces=new SimpleLongProperty(0L);

protected LongProperty minTracr=new SimpleLongProperty(0L);

protected LongProperty maxTracr=new SimpleLongProperty(0L);

protected LongProperty incTracr=new SimpleLongProperty(0L);

protected LongProperty firstTracr=new SimpleLongProperty(0L);

protected LongProperty lastTracr=new SimpleLongProperty(0L);

protected LongProperty minFldr=new SimpleLongProperty(0L);

protected LongProperty maxFldr=new SimpleLongProperty(0L);

protected LongProperty incFldr=new SimpleLongProperty(0L);

protected LongProperty firstFldr=new SimpleLongProperty(0L);

protected LongProperty lastFldr=new SimpleLongProperty(0L);

protected LongProperty minTracf=new SimpleLongProperty(0L);

protected LongProperty maxTracf=new SimpleLongProperty(0L);

protected LongProperty incTracf=new SimpleLongProperty(0L);

protected LongProperty firstTracf=new SimpleLongProperty(0L);

protected LongProperty lastTracf=new SimpleLongProperty(0L);

protected LongProperty minEp=new SimpleLongProperty(0L);

protected LongProperty maxEp=new SimpleLongProperty(0L);

protected LongProperty incEp=new SimpleLongProperty(0L);

protected LongProperty firstEp=new SimpleLongProperty(0L);

protected LongProperty lastEp=new SimpleLongProperty(0L);

protected LongProperty minCdp=new SimpleLongProperty(0L);

protected LongProperty maxCdp=new SimpleLongProperty(0L);

protected LongProperty incCdp=new SimpleLongProperty(0L);

protected LongProperty firstCdp=new SimpleLongProperty(0L);

protected LongProperty lastCdp=new SimpleLongProperty(0L);

protected LongProperty minCdpt=new SimpleLongProperty(0L);

protected LongProperty maxCdpt=new SimpleLongProperty(0L);

protected LongProperty incCdpt=new SimpleLongProperty(0L);

protected LongProperty firstCdpt=new SimpleLongProperty(0L);

protected LongProperty lastCdpt=new SimpleLongProperty(0L);

protected LongProperty minTrid=new SimpleLongProperty(0L);

protected LongProperty maxTrid=new SimpleLongProperty(0L);

protected LongProperty incTrid=new SimpleLongProperty(0L);

protected LongProperty firstTrid=new SimpleLongProperty(0L);

protected LongProperty lastTrid=new SimpleLongProperty(0L);

protected LongProperty minDuse=new SimpleLongProperty(0L);

protected LongProperty maxDuse=new SimpleLongProperty(0L);

protected LongProperty incDuse=new SimpleLongProperty(0L);

protected LongProperty firstDuse=new SimpleLongProperty(0L);

protected LongProperty lastDuse=new SimpleLongProperty(0L);

protected LongProperty duse=new SimpleLongProperty(0L);

protected LongProperty minOffset=new SimpleLongProperty(0L);

protected LongProperty maxOffset=new SimpleLongProperty(0L);

protected LongProperty incOffset=new SimpleLongProperty(0L);

protected LongProperty firstOffset=new SimpleLongProperty(0L);

protected LongProperty lastOffset=new SimpleLongProperty(0L);

protected LongProperty minGelev=new SimpleLongProperty(0L);

protected LongProperty maxGelev=new SimpleLongProperty(0L);

protected LongProperty incGelev=new SimpleLongProperty(0L);

protected LongProperty firstGelev=new SimpleLongProperty(0L);

protected LongProperty lastGelev=new SimpleLongProperty(0L);

protected LongProperty minSelev=new SimpleLongProperty(0L);

protected LongProperty maxSelev=new SimpleLongProperty(0L);

protected LongProperty incSelev=new SimpleLongProperty(0L);

protected LongProperty firstSelev=new SimpleLongProperty(0L);

protected LongProperty lastSelev=new SimpleLongProperty(0L);

protected LongProperty minSdepth=new SimpleLongProperty(0L);

protected LongProperty maxSdepth=new SimpleLongProperty(0L);

protected LongProperty incSdepth=new SimpleLongProperty(0L);

protected LongProperty firstSdepth=new SimpleLongProperty(0L);

protected LongProperty lastSdepth=new SimpleLongProperty(0L);

protected LongProperty minSwdep=new SimpleLongProperty(0L);

protected LongProperty maxSwdep=new SimpleLongProperty(0L);

protected LongProperty incSwdep=new SimpleLongProperty(0L);

protected LongProperty firstSwdep=new SimpleLongProperty(0L);

protected LongProperty lastSwdep=new SimpleLongProperty(0L);

protected LongProperty minGwdep=new SimpleLongProperty(0L);

protected LongProperty maxGwdep=new SimpleLongProperty(0L);

protected LongProperty incGwdep=new SimpleLongProperty(0L);

protected LongProperty firstGwdep=new SimpleLongProperty(0L);

protected LongProperty lastGwdep=new SimpleLongProperty(0L);

protected LongProperty minScalel=new SimpleLongProperty(0L);

protected LongProperty maxScalel=new SimpleLongProperty(0L);

protected LongProperty incScalel=new SimpleLongProperty(0L);

protected LongProperty firstScalel=new SimpleLongProperty(0L);

protected LongProperty lastScalel=new SimpleLongProperty(0L);

protected LongProperty scalel=new SimpleLongProperty(0L);

protected LongProperty minScalco=new SimpleLongProperty(0L);

protected LongProperty maxScalco=new SimpleLongProperty(0L);

protected LongProperty incScalco=new SimpleLongProperty(0L);

protected LongProperty firstScalco=new SimpleLongProperty(0L);

protected LongProperty lastScalco=new SimpleLongProperty(0L);

protected LongProperty scalco=new SimpleLongProperty(0L);

protected LongProperty minSx=new SimpleLongProperty(0L);

protected LongProperty maxSx=new SimpleLongProperty(0L);

protected LongProperty incSx=new SimpleLongProperty(0L);

protected LongProperty firstSx=new SimpleLongProperty(0L);

protected LongProperty lastSx=new SimpleLongProperty(0L);

protected LongProperty minSy=new SimpleLongProperty(0L);

protected LongProperty maxSy=new SimpleLongProperty(0L);

protected LongProperty incSy=new SimpleLongProperty(0L);

protected LongProperty firstSy=new SimpleLongProperty(0L);

protected LongProperty lastSy=new SimpleLongProperty(0L);

protected LongProperty minGx=new SimpleLongProperty(0L);

protected LongProperty maxGx=new SimpleLongProperty(0L);

protected LongProperty incGx=new SimpleLongProperty(0L);

protected LongProperty firstGx=new SimpleLongProperty(0L);

protected LongProperty lastGx=new SimpleLongProperty(0L);

protected LongProperty minGy=new SimpleLongProperty(0L);

protected LongProperty maxGy=new SimpleLongProperty(0L);

protected LongProperty incGy=new SimpleLongProperty(0L);

protected LongProperty firstGy=new SimpleLongProperty(0L);

protected LongProperty lastGy=new SimpleLongProperty(0L);

protected LongProperty minWevel=new SimpleLongProperty(0L);

protected LongProperty maxWevel=new SimpleLongProperty(0L);

protected LongProperty incWevel=new SimpleLongProperty(0L);

protected LongProperty firstWevel=new SimpleLongProperty(0L);

protected LongProperty lastWevel=new SimpleLongProperty(0L);

protected LongProperty wevel=new SimpleLongProperty(0L);

protected LongProperty minSwevel=new SimpleLongProperty(0L);

protected LongProperty maxSwevel=new SimpleLongProperty(0L);

protected LongProperty incSwevel=new SimpleLongProperty(0L);

protected LongProperty firstSwevel=new SimpleLongProperty(0L);

protected LongProperty lastSwevel=new SimpleLongProperty(0L);

protected LongProperty swevel=new SimpleLongProperty(0L);

protected LongProperty minSut=new SimpleLongProperty(0L);

protected LongProperty maxSut=new SimpleLongProperty(0L);

protected LongProperty incSut=new SimpleLongProperty(0L);

protected LongProperty firstSut=new SimpleLongProperty(0L);

protected LongProperty lastSut=new SimpleLongProperty(0L);

protected LongProperty minGut=new SimpleLongProperty(0L);

protected LongProperty maxGut=new SimpleLongProperty(0L);

protected LongProperty incGut=new SimpleLongProperty(0L);

protected LongProperty firstGut=new SimpleLongProperty(0L);

protected LongProperty lastGut=new SimpleLongProperty(0L);

protected LongProperty minSstat=new SimpleLongProperty(0L);

protected LongProperty maxSstat=new SimpleLongProperty(0L);

protected LongProperty incSstat=new SimpleLongProperty(0L);

protected LongProperty firstSstat=new SimpleLongProperty(0L);

protected LongProperty lastSstat=new SimpleLongProperty(0L);

protected LongProperty minGstat=new SimpleLongProperty(0L);

protected LongProperty maxGstat=new SimpleLongProperty(0L);

protected LongProperty incGstat=new SimpleLongProperty(0L);

protected LongProperty firstGstat=new SimpleLongProperty(0L);

protected LongProperty lastGstat=new SimpleLongProperty(0L);

protected LongProperty minLagb=new SimpleLongProperty(0L);

protected LongProperty maxLagb=new SimpleLongProperty(0L);

protected LongProperty incLagb=new SimpleLongProperty(0L);

protected LongProperty firstLagb=new SimpleLongProperty(0L);

protected LongProperty lastLagb=new SimpleLongProperty(0L);

protected LongProperty minNs=new SimpleLongProperty(0L);

protected LongProperty maxNs=new SimpleLongProperty(0L);

protected LongProperty incNs=new SimpleLongProperty(0L);

protected LongProperty firstNs=new SimpleLongProperty(0L);

protected LongProperty lastNs=new SimpleLongProperty(0L);

protected LongProperty ns=new SimpleLongProperty(0L);

protected LongProperty minDt=new SimpleLongProperty(0L);

protected LongProperty maxDt=new SimpleLongProperty(0L);

protected LongProperty incDt=new SimpleLongProperty(0L);

protected LongProperty firstDt=new SimpleLongProperty(0L);

protected LongProperty lastDt=new SimpleLongProperty(0L);

protected LongProperty dt=new SimpleLongProperty(0L);

protected LongProperty minSfs=new SimpleLongProperty(0L);

protected LongProperty maxSfs=new SimpleLongProperty(0L);

protected LongProperty incSfs=new SimpleLongProperty(0L);

protected LongProperty firstSfs=new SimpleLongProperty(0L);

protected LongProperty lastSfs=new SimpleLongProperty(0L);

protected LongProperty minSfe=new SimpleLongProperty(0L);

protected LongProperty maxSfe=new SimpleLongProperty(0L);

protected LongProperty incSfe=new SimpleLongProperty(0L);

protected LongProperty firstSfe=new SimpleLongProperty(0L);

protected LongProperty lastSfe=new SimpleLongProperty(0L);

protected LongProperty minSlen=new SimpleLongProperty(0L);

protected LongProperty maxSlen=new SimpleLongProperty(0L);

protected LongProperty incSlen=new SimpleLongProperty(0L);

protected LongProperty firstSlen=new SimpleLongProperty(0L);

protected LongProperty lastSlen=new SimpleLongProperty(0L);

protected LongProperty slen=new SimpleLongProperty(0L);

protected LongProperty minStyp=new SimpleLongProperty(0L);

protected LongProperty maxStyp=new SimpleLongProperty(0L);

protected LongProperty incStyp=new SimpleLongProperty(0L);

protected LongProperty firstStyp=new SimpleLongProperty(0L);

protected LongProperty lastStyp=new SimpleLongProperty(0L);

protected LongProperty minStas=new SimpleLongProperty(0L);

protected LongProperty maxStas=new SimpleLongProperty(0L);

protected LongProperty incStas=new SimpleLongProperty(0L);

protected LongProperty firstStas=new SimpleLongProperty(0L);

protected LongProperty lastStas=new SimpleLongProperty(0L);

protected LongProperty minStae=new SimpleLongProperty(0L);

protected LongProperty maxStae=new SimpleLongProperty(0L);

protected LongProperty incStae=new SimpleLongProperty(0L);

protected LongProperty firstStae=new SimpleLongProperty(0L);

protected LongProperty lastStae=new SimpleLongProperty(0L);

protected LongProperty stae=new SimpleLongProperty(0L);

protected LongProperty minAfilf=new SimpleLongProperty(0L);

protected LongProperty maxAfilf=new SimpleLongProperty(0L);

protected LongProperty incAfilf=new SimpleLongProperty(0L);

protected LongProperty firstAfilf=new SimpleLongProperty(0L);

protected LongProperty lastAfilf=new SimpleLongProperty(0L);

protected LongProperty afilf=new SimpleLongProperty(0L);

protected LongProperty minAfils=new SimpleLongProperty(0L);

protected LongProperty maxAfils=new SimpleLongProperty(0L);

protected LongProperty incAfils=new SimpleLongProperty(0L);

protected LongProperty firstAfils=new SimpleLongProperty(0L);

protected LongProperty lastAfils=new SimpleLongProperty(0L);

protected LongProperty afils=new SimpleLongProperty(0L);

protected LongProperty minLcf=new SimpleLongProperty(0L);

protected LongProperty maxLcf=new SimpleLongProperty(0L);

protected LongProperty incLcf=new SimpleLongProperty(0L);

protected LongProperty firstLcf=new SimpleLongProperty(0L);

protected LongProperty lastLcf=new SimpleLongProperty(0L);

protected LongProperty lcf=new SimpleLongProperty(0L);

protected LongProperty minLcs=new SimpleLongProperty(0L);

protected LongProperty maxLcs=new SimpleLongProperty(0L);

protected LongProperty incLcs=new SimpleLongProperty(0L);

protected LongProperty firstLcs=new SimpleLongProperty(0L);

protected LongProperty lastLcs=new SimpleLongProperty(0L);

protected LongProperty lcs=new SimpleLongProperty(0L);

protected LongProperty minHcs=new SimpleLongProperty(0L);

protected LongProperty maxHcs=new SimpleLongProperty(0L);

protected LongProperty incHcs=new SimpleLongProperty(0L);

protected LongProperty firstHcs=new SimpleLongProperty(0L);

protected LongProperty lastHcs=new SimpleLongProperty(0L);

protected LongProperty minYear=new SimpleLongProperty(0L);

protected LongProperty maxYear=new SimpleLongProperty(0L);

protected LongProperty incYear=new SimpleLongProperty(0L);

protected LongProperty firstYear=new SimpleLongProperty(0L);

protected LongProperty lastYear=new SimpleLongProperty(0L);

protected LongProperty year=new SimpleLongProperty(0L);

protected LongProperty minDay=new SimpleLongProperty(0L);

protected LongProperty maxDay=new SimpleLongProperty(0L);

protected LongProperty incDay=new SimpleLongProperty(0L);

protected LongProperty firstDay=new SimpleLongProperty(0L);

protected LongProperty lastDay=new SimpleLongProperty(0L);

protected LongProperty day=new SimpleLongProperty(0L);

protected LongProperty minHour=new SimpleLongProperty(0L);

protected LongProperty maxHour=new SimpleLongProperty(0L);

protected LongProperty incHour=new SimpleLongProperty(0L);

protected LongProperty firstHour=new SimpleLongProperty(0L);

protected LongProperty lastHour=new SimpleLongProperty(0L);

protected LongProperty minMinute=new SimpleLongProperty(0L);

protected LongProperty maxMinute=new SimpleLongProperty(0L);

protected LongProperty incMinute=new SimpleLongProperty(0L);

protected LongProperty firstMinute=new SimpleLongProperty(0L);

protected LongProperty lastMinute=new SimpleLongProperty(0L);

protected LongProperty minSec=new SimpleLongProperty(0L);

protected LongProperty maxSec=new SimpleLongProperty(0L);

protected LongProperty incSec=new SimpleLongProperty(0L);

protected LongProperty firstSec=new SimpleLongProperty(0L);

protected LongProperty lastSec=new SimpleLongProperty(0L);

protected LongProperty minGrnors=new SimpleLongProperty(0L);

protected LongProperty maxGrnors=new SimpleLongProperty(0L);

protected LongProperty incGrnors=new SimpleLongProperty(0L);

protected LongProperty firstGrnors=new SimpleLongProperty(0L);

protected LongProperty lastGrnors=new SimpleLongProperty(0L);

protected LongProperty grnors=new SimpleLongProperty(0L);

protected LongProperty minGrnofr=new SimpleLongProperty(0L);

protected LongProperty maxGrnofr=new SimpleLongProperty(0L);

protected LongProperty incGrnofr=new SimpleLongProperty(0L);

protected LongProperty firstGrnofr=new SimpleLongProperty(0L);

protected LongProperty lastGrnofr=new SimpleLongProperty(0L);

protected LongProperty grnofr=new SimpleLongProperty(0L);

protected LongProperty minGaps=new SimpleLongProperty(0L);

protected LongProperty maxGaps=new SimpleLongProperty(0L);

protected LongProperty incGaps=new SimpleLongProperty(0L);

protected LongProperty firstGaps=new SimpleLongProperty(0L);

protected LongProperty lastGaps=new SimpleLongProperty(0L);

protected LongProperty minOtrav=new SimpleLongProperty(0L);

protected LongProperty maxOtrav=new SimpleLongProperty(0L);

protected LongProperty incOtrav=new SimpleLongProperty(0L);

protected LongProperty firstOtrav=new SimpleLongProperty(0L);

protected LongProperty lastOtrav=new SimpleLongProperty(0L);

protected LongProperty minCdpx=new SimpleLongProperty(0L);

protected LongProperty maxCdpx=new SimpleLongProperty(0L);

protected LongProperty incCdpx=new SimpleLongProperty(0L);

protected LongProperty firstCdpx=new SimpleLongProperty(0L);

protected LongProperty lastCdpx=new SimpleLongProperty(0L);

protected LongProperty minCdpy=new SimpleLongProperty(0L);

protected LongProperty maxCdpy=new SimpleLongProperty(0L);

protected LongProperty incCdpy=new SimpleLongProperty(0L);

protected LongProperty firstCdpy=new SimpleLongProperty(0L);

protected LongProperty lastCdpy=new SimpleLongProperty(0L);

protected LongProperty minInline=new SimpleLongProperty(0L);

protected LongProperty maxInline=new SimpleLongProperty(0L);

protected LongProperty incInline=new SimpleLongProperty(0L);

protected LongProperty firstInline=new SimpleLongProperty(0L);

protected LongProperty lastInline=new SimpleLongProperty(0L);

protected LongProperty minCrossline=new SimpleLongProperty(0L);

protected LongProperty maxCrossline=new SimpleLongProperty(0L);

protected LongProperty incCrossline=new SimpleLongProperty(0L);

protected LongProperty firstCrossline=new SimpleLongProperty(0L);

protected LongProperty lastCrossline=new SimpleLongProperty(0L);

protected LongProperty minShotpoint=new SimpleLongProperty(0L);

protected LongProperty maxShotpoint=new SimpleLongProperty(0L);

protected LongProperty incShotpoint=new SimpleLongProperty(0L);

protected LongProperty firstShotpoint=new SimpleLongProperty(0L);

protected LongProperty lastShotpoint=new SimpleLongProperty(0L);

protected LongProperty minScalsp=new SimpleLongProperty(0L);

protected LongProperty maxScalsp=new SimpleLongProperty(0L);

protected LongProperty incScalsp=new SimpleLongProperty(0L);

protected LongProperty firstScalsp=new SimpleLongProperty(0L);

protected LongProperty lastScalsp=new SimpleLongProperty(0L);

protected LongProperty minTcm=new SimpleLongProperty(0L);

protected LongProperty maxTcm=new SimpleLongProperty(0L);

protected LongProperty incTcm=new SimpleLongProperty(0L);

protected LongProperty firstTcm=new SimpleLongProperty(0L);

protected LongProperty lastTcm=new SimpleLongProperty(0L);

protected LongProperty minTid=new SimpleLongProperty(0L);

protected LongProperty maxTid=new SimpleLongProperty(0L);

protected LongProperty incTid=new SimpleLongProperty(0L);

protected LongProperty firstTid=new SimpleLongProperty(0L);

protected LongProperty lastTid=new SimpleLongProperty(0L);

protected LongProperty tid=new SimpleLongProperty(0L);

protected LongProperty minSedm=new SimpleLongProperty(0L);

protected LongProperty maxSedm=new SimpleLongProperty(0L);

protected LongProperty incSedm=new SimpleLongProperty(0L);

protected LongProperty firstSedm=new SimpleLongProperty(0L);

protected LongProperty lastSedm=new SimpleLongProperty(0L);

protected LongProperty minSede=new SimpleLongProperty(0L);

protected LongProperty maxSede=new SimpleLongProperty(0L);

protected LongProperty incSede=new SimpleLongProperty(0L);

protected LongProperty firstSede=new SimpleLongProperty(0L);

protected LongProperty lastSede=new SimpleLongProperty(0L);

protected LongProperty sede=new SimpleLongProperty(0L);

protected LongProperty minSmm=new SimpleLongProperty(0L);

protected LongProperty maxSmm=new SimpleLongProperty(0L);

protected LongProperty incSmm=new SimpleLongProperty(0L);


protected LongProperty firstSmm=new SimpleLongProperty(0L);

protected LongProperty lastSmm=new SimpleLongProperty(0L);

protected LongProperty minSme=new SimpleLongProperty(0L);

protected LongProperty maxSme=new SimpleLongProperty(0L);

protected LongProperty incSme=new SimpleLongProperty(0L);

protected LongProperty firstSme=new SimpleLongProperty(0L);

protected LongProperty lastSme=new SimpleLongProperty(0L);

protected LongProperty minSmu=new SimpleLongProperty(0L);

protected LongProperty maxSmu=new SimpleLongProperty(0L);

protected LongProperty incSmu=new SimpleLongProperty(0L);

protected LongProperty firstSmu=new SimpleLongProperty(0L);

protected LongProperty lastSmu=new SimpleLongProperty(0L);

protected LongProperty minUi1=new SimpleLongProperty(0L);

protected LongProperty maxUi1=new SimpleLongProperty(0L);

protected LongProperty incUi1=new SimpleLongProperty(0L);

protected LongProperty firstUi1=new SimpleLongProperty(0L);

protected LongProperty lastUi1=new SimpleLongProperty(0L);

protected LongProperty minUi2=new SimpleLongProperty(0L);

protected LongProperty maxUi2=new SimpleLongProperty(0L);

protected LongProperty incUi2=new SimpleLongProperty(0L);

protected LongProperty firstUi2=new SimpleLongProperty(0L);

protected LongProperty lastUi2=new SimpleLongProperty(0L);

protected LongProperty ui2=new SimpleLongProperty(0L);

protected LongProperty minVer=new SimpleLongProperty(0L);

protected LongProperty maxVer=new SimpleLongProperty(0L);

protected LongProperty incVer=new SimpleLongProperty(0L);

protected LongProperty sampleRate=new SimpleLongProperty(0L);

protected LongProperty recordLength=new SimpleLongProperty(0L);

protected LongProperty unitVer=new SimpleLongProperty(0L);

protected Sequence sequence;

    protected String insight=new String();
    protected String workflow=new String();
    protected Long numberOfRuns=0L;
    protected Boolean chosen=true;
    protected Boolean multiple=false;
    protected Boolean deleted=false;
    protected Long sequenceNumber=0L;
    protected String subsurfaceName=new String();
    protected String timeStamp=new String();
    
    
private List<FullSubsurfaceHeaders> subsurfaceHeaders=new ArrayList<>();

    public FullSequenceHeaders() {
        
        isSequence=true;
        isSubsurface=false;
    }



    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
        
    }

    public List<FullSubsurfaceHeaders> getSubsurfaceHeaders() {
        return subsurfaceHeaders;
    }

    public void setSubsurfaceHeaders(List<FullSubsurfaceHeaders> subsurfaceHeaders) {
        this.subsurfaceHeaders = subsurfaceHeaders;
    }

   

    public String getInsight() {
        return insight;
    }

    public void setInsight(String insight) {
        this.insight = insight;
    }

    public String getWorkflow() {
        return workflow;
    }

    public void setWorkflow(String workflow) {
        this.workflow = workflow;
    }

    public Long getNumberOfRuns() {
        return numberOfRuns;
    }

    public void setNumberOfRuns(Long numberOfRuns) {
        this.numberOfRuns = numberOfRuns;
    }

    public Boolean getChosen() {
        return chosen;
    }

    public void setChosen(Boolean chosen) {
        this.chosen = chosen;
    }

    public Boolean getMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getSubsurfaceName() {
        return subsurfaceName;
    }

    public void setSubsurfaceName(String subsurfaceName) {
        this.subsurfaceName = subsurfaceName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }



    

    public boolean isSequence() {
        return isSequence;
    }

    public boolean isSubsurface() {
        return isSubsurface;
    }

    public LongProperty getTotalTraces() {
        return totalTraces;
    }

    public LongProperty getMinTracr() {
        return minTracr;
    }

    public LongProperty getMaxTracr() {
        return maxTracr;
    }

    public LongProperty getIncTracr() {
        return incTracr;
    }

    public LongProperty getFirstTracr() {
        return firstTracr;
    }

    public LongProperty getLastTracr() {
        return lastTracr;
    }

    public LongProperty getMinFldr() {
        return minFldr;
    }

    public LongProperty getMaxFldr() {
        return maxFldr;
    }

    public LongProperty getIncFldr() {
        return incFldr;
    }

    public LongProperty getFirstFldr() {
        return firstFldr;
    }

    public LongProperty getLastFldr() {
        return lastFldr;
    }

    public LongProperty getMinTracf() {
        return minTracf;
    }

    public LongProperty getMaxTracf() {
        return maxTracf;
    }

    public LongProperty getIncTracf() {
        return incTracf;
    }

    public LongProperty getFirstTracf() {
        return firstTracf;
    }

    public LongProperty getLastTracf() {
        return lastTracf;
    }

    public LongProperty getMinEp() {
        return minEp;
    }

    public LongProperty getMaxEp() {
        return maxEp;
    }

    public LongProperty getIncEp() {
        return incEp;
    }

    public LongProperty getFirstEp() {
        return firstEp;
    }

    public LongProperty getLastEp() {
        return lastEp;
    }

    public LongProperty getMinCdp() {
        return minCdp;
    }

    public LongProperty getMaxCdp() {
        return maxCdp;
    }

    public LongProperty getIncCdp() {
        return incCdp;
    }

    public LongProperty getFirstCdp() {
        return firstCdp;
    }

    public LongProperty getLastCdp() {
        return lastCdp;
    }

    public LongProperty getMinCdpt() {
        return minCdpt;
    }

    public LongProperty getMaxCdpt() {
        return maxCdpt;
    }

    public LongProperty getIncCdpt() {
        return incCdpt;
    }

    public LongProperty getFirstCdpt() {
        return firstCdpt;
    }

    public LongProperty getLastCdpt() {
        return lastCdpt;
    }

    public LongProperty getMinTrid() {
        return minTrid;
    }

    public LongProperty getMaxTrid() {
        return maxTrid;
    }

    public LongProperty getIncTrid() {
        return incTrid;
    }

    public LongProperty getFirstTrid() {
        return firstTrid;
    }

    public LongProperty getLastTrid() {
        return lastTrid;
    }

    public LongProperty getMinDuse() {
        return minDuse;
    }

    public LongProperty getMaxDuse() {
        return maxDuse;
    }

    public LongProperty getIncDuse() {
        return incDuse;
    }

    public LongProperty getFirstDuse() {
        return firstDuse;
    }

    public LongProperty getLastDuse() {
        return lastDuse;
    }

    public LongProperty getDuse() {
        return duse;
    }

    public LongProperty getMinOffset() {
        return minOffset;
    }

    public LongProperty getMaxOffset() {
        return maxOffset;
    }

    public LongProperty getIncOffset() {
        return incOffset;
    }

    public LongProperty getFirstOffset() {
        return firstOffset;
    }

    public LongProperty getLastOffset() {
        return lastOffset;
    }

    public LongProperty getMinGelev() {
        return minGelev;
    }

    public LongProperty getMaxGelev() {
        return maxGelev;
    }

    public LongProperty getIncGelev() {
        return incGelev;
    }

    public LongProperty getFirstGelev() {
        return firstGelev;
    }

    public LongProperty getLastGelev() {
        return lastGelev;
    }

    public LongProperty getMinSelev() {
        return minSelev;
    }

    public LongProperty getMaxSelev() {
        return maxSelev;
    }

    public LongProperty getIncSelev() {
        return incSelev;
    }

    public LongProperty getFirstSelev() {
        return firstSelev;
    }

    public LongProperty getLastSelev() {
        return lastSelev;
    }

    public LongProperty getMinSdepth() {
        return minSdepth;
    }

    public LongProperty getMaxSdepth() {
        return maxSdepth;
    }

    public LongProperty getIncSdepth() {
        return incSdepth;
    }

    public LongProperty getFirstSdepth() {
        return firstSdepth;
    }

    public LongProperty getLastSdepth() {
        return lastSdepth;
    }

    public LongProperty getMinSwdep() {
        return minSwdep;
    }

    public LongProperty getMaxSwdep() {
        return maxSwdep;
    }

    public LongProperty getIncSwdep() {
        return incSwdep;
    }

    public LongProperty getFirstSwdep() {
        return firstSwdep;
    }

    public LongProperty getLastSwdep() {
        return lastSwdep;
    }

    public LongProperty getMinGwdep() {
        return minGwdep;
    }

    public LongProperty getMaxGwdep() {
        return maxGwdep;
    }

    public LongProperty getIncGwdep() {
        return incGwdep;
    }

    public LongProperty getFirstGwdep() {
        return firstGwdep;
    }

    public LongProperty getLastGwdep() {
        return lastGwdep;
    }

    public LongProperty getMinScalel() {
        return minScalel;
    }

    public LongProperty getMaxScalel() {
        return maxScalel;
    }

    public LongProperty getIncScalel() {
        return incScalel;
    }

    public LongProperty getFirstScalel() {
        return firstScalel;
    }

    public LongProperty getLastScalel() {
        return lastScalel;
    }

    public LongProperty getScalel() {
        return scalel;
    }

    public LongProperty getMinScalco() {
        return minScalco;
    }

    public LongProperty getMaxScalco() {
        return maxScalco;
    }

    public LongProperty getIncScalco() {
        return incScalco;
    }

    public LongProperty getFirstScalco() {
        return firstScalco;
    }

    public LongProperty getLastScalco() {
        return lastScalco;
    }

    public LongProperty getScalco() {
        return scalco;
    }

    public LongProperty getMinSx() {
        return minSx;
    }

    public LongProperty getMaxSx() {
        return maxSx;
    }

    public LongProperty getIncSx() {
        return incSx;
    }

    public LongProperty getFirstSx() {
        return firstSx;
    }

    public LongProperty getLastSx() {
        return lastSx;
    }

    public LongProperty getMinSy() {
        return minSy;
    }

    public LongProperty getMaxSy() {
        return maxSy;
    }

    public LongProperty getIncSy() {
        return incSy;
    }

    public LongProperty getFirstSy() {
        return firstSy;
    }

    public LongProperty getLastSy() {
        return lastSy;
    }

    public LongProperty getMinGx() {
        return minGx;
    }

    public LongProperty getMaxGx() {
        return maxGx;
    }

    public LongProperty getIncGx() {
        return incGx;
    }

    public LongProperty getFirstGx() {
        return firstGx;
    }

    public LongProperty getLastGx() {
        return lastGx;
    }

    public LongProperty getMinGy() {
        return minGy;
    }

    public LongProperty getMaxGy() {
        return maxGy;
    }

    public LongProperty getIncGy() {
        return incGy;
    }

    public LongProperty getFirstGy() {
        return firstGy;
    }

    public LongProperty getLastGy() {
        return lastGy;
    }

    public LongProperty getMinWevel() {
        return minWevel;
    }

    public LongProperty getMaxWevel() {
        return maxWevel;
    }

    public LongProperty getIncWevel() {
        return incWevel;
    }

    public LongProperty getFirstWevel() {
        return firstWevel;
    }

    public LongProperty getLastWevel() {
        return lastWevel;
    }

    public LongProperty getWevel() {
        return wevel;
    }

    public LongProperty getMinSwevel() {
        return minSwevel;
    }

    public LongProperty getMaxSwevel() {
        return maxSwevel;
    }

    public LongProperty getIncSwevel() {
        return incSwevel;
    }

    public LongProperty getFirstSwevel() {
        return firstSwevel;
    }

    public LongProperty getLastSwevel() {
        return lastSwevel;
    }

    public LongProperty getSwevel() {
        return swevel;
    }

    public LongProperty getMinSut() {
        return minSut;
    }

    public LongProperty getMaxSut() {
        return maxSut;
    }

    public LongProperty getIncSut() {
        return incSut;
    }

    public LongProperty getFirstSut() {
        return firstSut;
    }

    public LongProperty getLastSut() {
        return lastSut;
    }

    public LongProperty getMinGut() {
        return minGut;
    }

    public LongProperty getMaxGut() {
        return maxGut;
    }

    public LongProperty getIncGut() {
        return incGut;
    }

    public LongProperty getFirstGut() {
        return firstGut;
    }

    public LongProperty getLastGut() {
        return lastGut;
    }

    public LongProperty getMinSstat() {
        return minSstat;
    }

    public LongProperty getMaxSstat() {
        return maxSstat;
    }

    public LongProperty getIncSstat() {
        return incSstat;
    }

    public LongProperty getFirstSstat() {
        return firstSstat;
    }

    public LongProperty getLastSstat() {
        return lastSstat;
    }

    public LongProperty getMinGstat() {
        return minGstat;
    }

    public LongProperty getMaxGstat() {
        return maxGstat;
    }

    public LongProperty getIncGstat() {
        return incGstat;
    }

    public LongProperty getFirstGstat() {
        return firstGstat;
    }

    public LongProperty getLastGstat() {
        return lastGstat;
    }

    public LongProperty getMinLagb() {
        return minLagb;
    }

    public LongProperty getMaxLagb() {
        return maxLagb;
    }

    public LongProperty getIncLagb() {
        return incLagb;
    }

    public LongProperty getFirstLagb() {
        return firstLagb;
    }

    public LongProperty getLastLagb() {
        return lastLagb;
    }

    public LongProperty getMinNs() {
        return minNs;
    }

    public LongProperty getMaxNs() {
        return maxNs;
    }

    public LongProperty getIncNs() {
        return incNs;
    }

    public LongProperty getFirstNs() {
        return firstNs;
    }

    public LongProperty getLastNs() {
        return lastNs;
    }

    public LongProperty getNs() {
        return ns;
    }

    public LongProperty getMinDt() {
        return minDt;
    }

    public LongProperty getMaxDt() {
        return maxDt;
    }

    public LongProperty getIncDt() {
        return incDt;
    }

    public LongProperty getFirstDt() {
        return firstDt;
    }

    public LongProperty getLastDt() {
        return lastDt;
    }

    public LongProperty getDt() {
        return dt;
    }

    public LongProperty getMinSfs() {
        return minSfs;
    }

    public LongProperty getMaxSfs() {
        return maxSfs;
    }

    public LongProperty getIncSfs() {
        return incSfs;
    }

    public LongProperty getFirstSfs() {
        return firstSfs;
    }

    public LongProperty getLastSfs() {
        return lastSfs;
    }

    public LongProperty getMinSfe() {
        return minSfe;
    }

    public LongProperty getMaxSfe() {
        return maxSfe;
    }

    public LongProperty getIncSfe() {
        return incSfe;
    }

    public LongProperty getFirstSfe() {
        return firstSfe;
    }

    public LongProperty getLastSfe() {
        return lastSfe;
    }

    public LongProperty getMinSlen() {
        return minSlen;
    }

    public LongProperty getMaxSlen() {
        return maxSlen;
    }

    public LongProperty getIncSlen() {
        return incSlen;
    }

    public LongProperty getFirstSlen() {
        return firstSlen;
    }

    public LongProperty getLastSlen() {
        return lastSlen;
    }

    public LongProperty getSlen() {
        return slen;
    }

    public LongProperty getMinStyp() {
        return minStyp;
    }

    public LongProperty getMaxStyp() {
        return maxStyp;
    }

    public LongProperty getIncStyp() {
        return incStyp;
    }

    public LongProperty getFirstStyp() {
        return firstStyp;
    }

    public LongProperty getLastStyp() {
        return lastStyp;
    }

    public LongProperty getMinStas() {
        return minStas;
    }

    public LongProperty getMaxStas() {
        return maxStas;
    }

    public LongProperty getIncStas() {
        return incStas;
    }

    public LongProperty getFirstStas() {
        return firstStas;
    }

    public LongProperty getLastStas() {
        return lastStas;
    }

    public LongProperty getMinStae() {
        return minStae;
    }

    public LongProperty getMaxStae() {
        return maxStae;
    }

    public LongProperty getIncStae() {
        return incStae;
    }

    public LongProperty getFirstStae() {
        return firstStae;
    }

    public LongProperty getLastStae() {
        return lastStae;
    }

    public LongProperty getStae() {
        return stae;
    }

    public LongProperty getMinAfilf() {
        return minAfilf;
    }

    public LongProperty getMaxAfilf() {
        return maxAfilf;
    }

    public LongProperty getIncAfilf() {
        return incAfilf;
    }

    public LongProperty getFirstAfilf() {
        return firstAfilf;
    }

    public LongProperty getLastAfilf() {
        return lastAfilf;
    }

    public LongProperty getAfilf() {
        return afilf;
    }

    public LongProperty getMinAfils() {
        return minAfils;
    }

    public LongProperty getMaxAfils() {
        return maxAfils;
    }

    public LongProperty getIncAfils() {
        return incAfils;
    }

    public LongProperty getFirstAfils() {
        return firstAfils;
    }

    public LongProperty getLastAfils() {
        return lastAfils;
    }

    public LongProperty getAfils() {
        return afils;
    }

    public LongProperty getMinLcf() {
        return minLcf;
    }

    public LongProperty getMaxLcf() {
        return maxLcf;
    }

    public LongProperty getIncLcf() {
        return incLcf;
    }

    public LongProperty getFirstLcf() {
        return firstLcf;
    }

    public LongProperty getLastLcf() {
        return lastLcf;
    }

    public LongProperty getLcf() {
        return lcf;
    }

    public LongProperty getMinLcs() {
        return minLcs;
    }

    public LongProperty getMaxLcs() {
        return maxLcs;
    }

    public LongProperty getIncLcs() {
        return incLcs;
    }

    public LongProperty getFirstLcs() {
        return firstLcs;
    }

    public LongProperty getLastLcs() {
        return lastLcs;
    }

    public LongProperty getLcs() {
        return lcs;
    }

    public LongProperty getMinHcs() {
        return minHcs;
    }

    public LongProperty getMaxHcs() {
        return maxHcs;
    }

    public LongProperty getIncHcs() {
        return incHcs;
    }

    public LongProperty getFirstHcs() {
        return firstHcs;
    }

    public LongProperty getLastHcs() {
        return lastHcs;
    }

    public LongProperty getMinYear() {
        return minYear;
    }

    public LongProperty getMaxYear() {
        return maxYear;
    }

    public LongProperty getIncYear() {
        return incYear;
    }

    public LongProperty getFirstYear() {
        return firstYear;
    }

    public LongProperty getLastYear() {
        return lastYear;
    }

    public LongProperty getYear() {
        return year;
    }

    public LongProperty getMinDay() {
        return minDay;
    }

    public LongProperty getMaxDay() {
        return maxDay;
    }

    public LongProperty getIncDay() {
        return incDay;
    }

    public LongProperty getFirstDay() {
        return firstDay;
    }

    public LongProperty getLastDay() {
        return lastDay;
    }

    public LongProperty getDay() {
        return day;
    }

    public LongProperty getMinHour() {
        return minHour;
    }

    public LongProperty getMaxHour() {
        return maxHour;
    }

    public LongProperty getIncHour() {
        return incHour;
    }

    public LongProperty getFirstHour() {
        return firstHour;
    }

    public LongProperty getLastHour() {
        return lastHour;
    }

    public LongProperty getMinMinute() {
        return minMinute;
    }

    public LongProperty getMaxMinute() {
        return maxMinute;
    }

    public LongProperty getIncMinute() {
        return incMinute;
    }

    public LongProperty getFirstMinute() {
        return firstMinute;
    }

    public LongProperty getLastMinute() {
        return lastMinute;
    }

    public LongProperty getMinSec() {
        return minSec;
    }

    public LongProperty getMaxSec() {
        return maxSec;
    }

    public LongProperty getIncSec() {
        return incSec;
    }

    public LongProperty getFirstSec() {
        return firstSec;
    }

    public LongProperty getLastSec() {
        return lastSec;
    }

    public LongProperty getMinGrnors() {
        return minGrnors;
    }

    public LongProperty getMaxGrnors() {
        return maxGrnors;
    }

    public LongProperty getIncGrnors() {
        return incGrnors;
    }

    public LongProperty getFirstGrnors() {
        return firstGrnors;
    }

    public LongProperty getLastGrnors() {
        return lastGrnors;
    }

    public LongProperty getGrnors() {
        return grnors;
    }

    public LongProperty getMinGrnofr() {
        return minGrnofr;
    }

    public LongProperty getMaxGrnofr() {
        return maxGrnofr;
    }

    public LongProperty getIncGrnofr() {
        return incGrnofr;
    }

    public LongProperty getFirstGrnofr() {
        return firstGrnofr;
    }

    public LongProperty getLastGrnofr() {
        return lastGrnofr;
    }

    public LongProperty getGrnofr() {
        return grnofr;
    }

    public LongProperty getMinGaps() {
        return minGaps;
    }

    public LongProperty getMaxGaps() {
        return maxGaps;
    }

    public LongProperty getIncGaps() {
        return incGaps;
    }

    public LongProperty getFirstGaps() {
        return firstGaps;
    }

    public LongProperty getLastGaps() {
        return lastGaps;
    }

    public LongProperty getMinOtrav() {
        return minOtrav;
    }

    public LongProperty getMaxOtrav() {
        return maxOtrav;
    }

    public LongProperty getIncOtrav() {
        return incOtrav;
    }

    public LongProperty getFirstOtrav() {
        return firstOtrav;
    }

    public LongProperty getLastOtrav() {
        return lastOtrav;
    }

    public LongProperty getMinCdpx() {
        return minCdpx;
    }

    public LongProperty getMaxCdpx() {
        return maxCdpx;
    }

    public LongProperty getIncCdpx() {
        return incCdpx;
    }

    public LongProperty getFirstCdpx() {
        return firstCdpx;
    }

    public LongProperty getLastCdpx() {
        return lastCdpx;
    }

    public LongProperty getMinCdpy() {
        return minCdpy;
    }

    public LongProperty getMaxCdpy() {
        return maxCdpy;
    }

    public LongProperty getIncCdpy() {
        return incCdpy;
    }

    public LongProperty getFirstCdpy() {
        return firstCdpy;
    }

    public LongProperty getLastCdpy() {
        return lastCdpy;
    }

    public LongProperty getMinInline() {
        return minInline;
    }

    public LongProperty getMaxInline() {
        return maxInline;
    }

    public LongProperty getIncInline() {
        return incInline;
    }

    public LongProperty getFirstInline() {
        return firstInline;
    }

    public LongProperty getLastInline() {
        return lastInline;
    }

    public LongProperty getMinCrossline() {
        return minCrossline;
    }

    public LongProperty getMaxCrossline() {
        return maxCrossline;
    }

    public LongProperty getIncCrossline() {
        return incCrossline;
    }

    public LongProperty getFirstCrossline() {
        return firstCrossline;
    }

    public LongProperty getLastCrossline() {
        return lastCrossline;
    }

    public LongProperty getMinShotpoint() {
        return minShotpoint;
    }

    public LongProperty getMaxShotpoint() {
        return maxShotpoint;
    }

    public LongProperty getIncShotpoint() {
        return incShotpoint;
    }

    public LongProperty getFirstShotpoint() {
        return firstShotpoint;
    }

    public LongProperty getLastShotpoint() {
        return lastShotpoint;
    }

    public LongProperty getMinScalsp() {
        return minScalsp;
    }

    public LongProperty getMaxScalsp() {
        return maxScalsp;
    }

    public LongProperty getIncScalsp() {
        return incScalsp;
    }

    public LongProperty getFirstScalsp() {
        return firstScalsp;
    }

    public LongProperty getLastScalsp() {
        return lastScalsp;
    }

    public LongProperty getMinTcm() {
        return minTcm;
    }

    public LongProperty getMaxTcm() {
        return maxTcm;
    }

    public LongProperty getIncTcm() {
        return incTcm;
    }

    public LongProperty getFirstTcm() {
        return firstTcm;
    }

    public LongProperty getLastTcm() {
        return lastTcm;
    }

    public LongProperty getMinTid() {
        return minTid;
    }

    public LongProperty getMaxTid() {
        return maxTid;
    }

    public LongProperty getIncTid() {
        return incTid;
    }

    public LongProperty getFirstTid() {
        return firstTid;
    }

    public LongProperty getLastTid() {
        return lastTid;
    }

    public LongProperty getTid() {
        return tid;
    }

    public LongProperty getMinSedm() {
        return minSedm;
    }

    public LongProperty getMaxSedm() {
        return maxSedm;
    }

    public LongProperty getIncSedm() {
        return incSedm;
    }

    public LongProperty getFirstSedm() {
        return firstSedm;
    }

    public LongProperty getLastSedm() {
        return lastSedm;
    }

    public LongProperty getMinSede() {
        return minSede;
    }

    public LongProperty getMaxSede() {
        return maxSede;
    }

    public LongProperty getIncSede() {
        return incSede;
    }

    public LongProperty getFirstSede() {
        return firstSede;
    }

    public LongProperty getLastSede() {
        return lastSede;
    }

    public LongProperty getSede() {
        return sede;
    }

    public LongProperty getMinSmm() {
        return minSmm;
    }

    public LongProperty getMaxSmm() {
        return maxSmm;
    }

    public LongProperty getIncSmm() {
        return incSmm;
    }

    public LongProperty getFirstSmm() {
        return firstSmm;
    }

    public LongProperty getLastSmm() {
        return lastSmm;
    }

    public LongProperty getMinSme() {
        return minSme;
    }

    public LongProperty getMaxSme() {
        return maxSme;
    }

    public LongProperty getIncSme() {
        return incSme;
    }

    public LongProperty getFirstSme() {
        return firstSme;
    }

    public LongProperty getLastSme() {
        return lastSme;
    }

    public LongProperty getMinSmu() {
        return minSmu;
    }

    public LongProperty getMaxSmu() {
        return maxSmu;
    }

    public LongProperty getIncSmu() {
        return incSmu;
    }

    public LongProperty getFirstSmu() {
        return firstSmu;
    }

    public LongProperty getLastSmu() {
        return lastSmu;
    }

    public LongProperty getMinUi1() {
        return minUi1;
    }

    public LongProperty getMaxUi1() {
        return maxUi1;
    }

    public LongProperty getIncUi1() {
        return incUi1;
    }

    public LongProperty getFirstUi1() {
        return firstUi1;
    }

    public LongProperty getLastUi1() {
        return lastUi1;
    }

    public LongProperty getMinUi2() {
        return minUi2;
    }

    public LongProperty getMaxUi2() {
        return maxUi2;
    }

    public LongProperty getIncUi2() {
        return incUi2;
    }

    public LongProperty getFirstUi2() {
        return firstUi2;
    }

    public LongProperty getLastUi2() {
        return lastUi2;
    }

    public LongProperty getUi2() {
        return ui2;
    }

    public LongProperty getMinVer() {
        return minVer;
    }

    public LongProperty getMaxVer() {
        return maxVer;
    }

    public LongProperty getIncVer() {
        return incVer;
    }

    public LongProperty getSampleRate() {
        return sampleRate;
    }

    public LongProperty getRecordLength() {
        return recordLength;
    }

    public LongProperty getUnitVer() {
        return unitVer;
    }

////
    ////
public void setTotalTraces(Long totalTraces){
this.totalTraces.set(totalTraces);
}

public void setMinTracr(Long minTracr){
this.minTracr.set(minTracr);
}

public void setMaxTracr(Long maxTracr){
this.maxTracr.set(maxTracr);
}

public void setIncTracr(Long incTracr){
this.incTracr.set(incTracr);
}

public void setFirstTracr(Long firstTracr){
this.firstTracr.set(firstTracr);
}

public void setLastTracr(Long lastTracr){
this.lastTracr.set(lastTracr);
}

public void setMinFldr(Long minFldr){
this.minFldr.set(minFldr);
}

public void setMaxFldr(Long maxFldr){
this.maxFldr.set(maxFldr);
}

public void setIncFldr(Long incFldr){
this.incFldr.set(incFldr);
}

public void setFirstFldr(Long firstFldr){
this.firstFldr.set(firstFldr);
}

public void setLastFldr(Long lastFldr){
this.lastFldr.set(lastFldr);
}

public void setMinTracf(Long minTracf){
this.minTracf.set(minTracf);
}

public void setMaxTracf(Long maxTracf){
this.maxTracf.set(maxTracf);
}

public void setIncTracf(Long incTracf){
this.incTracf.set(incTracf);
}

public void setFirstTracf(Long firstTracf){
this.firstTracf.set(firstTracf);
}

public void setLastTracf(Long lastTracf){
this.lastTracf.set(lastTracf);
}

public void setMinEp(Long minEp){
this.minEp.set(minEp);
}

public void setMaxEp(Long maxEp){
this.maxEp.set(maxEp);
}

public void setIncEp(Long incEp){
this.incEp.set(incEp);
}

public void setFirstEp(Long firstEp){
this.firstEp.set(firstEp);
}

public void setLastEp(Long lastEp){
this.lastEp.set(lastEp);
}

public void setMinCdp(Long minCdp){
this.minCdp.set(minCdp);
}

public void setMaxCdp(Long maxCdp){
this.maxCdp.set(maxCdp);
}

public void setIncCdp(Long incCdp){
this.incCdp.set(incCdp);
}

public void setFirstCdp(Long firstCdp){
this.firstCdp.set(firstCdp);
}

public void setLastCdp(Long lastCdp){
this.lastCdp.set(lastCdp);
}

public void setMinCdpt(Long minCdpt){
this.minCdpt.set(minCdpt);
}

public void setMaxCdpt(Long maxCdpt){
this.maxCdpt.set(maxCdpt);
}

public void setIncCdpt(Long incCdpt){
this.incCdpt.set(incCdpt);
}

public void setFirstCdpt(Long firstCdpt){
this.firstCdpt.set(firstCdpt);
}

public void setLastCdpt(Long lastCdpt){
this.lastCdpt.set(lastCdpt);
}

public void setMinTrid(Long minTrid){
this.minTrid.set(minTrid);
}

public void setMaxTrid(Long maxTrid){
this.maxTrid.set(maxTrid);
}

public void setIncTrid(Long incTrid){
this.incTrid.set(incTrid);
}

public void setFirstTrid(Long firstTrid){
this.firstTrid.set(firstTrid);
}

public void setLastTrid(Long lastTrid){
this.lastTrid.set(lastTrid);
}

public void setMinDuse(Long minDuse){
this.minDuse.set(minDuse);
}

public void setMaxDuse(Long maxDuse){
this.maxDuse.set(maxDuse);
}

public void setIncDuse(Long incDuse){
this.incDuse.set(incDuse);
}

public void setFirstDuse(Long firstDuse){
this.firstDuse.set(firstDuse);
}

public void setLastDuse(Long lastDuse){
this.lastDuse.set(lastDuse);
}

public void setDuse(Long duse){
this.duse.set(duse);
}

public void setMinOffset(Long minOffset){
this.minOffset.set(minOffset);
}

public void setMaxOffset(Long maxOffset){
this.maxOffset.set(maxOffset);
}

public void setIncOffset(Long incOffset){
this.incOffset.set(incOffset);
}

public void setFirstOffset(Long firstOffset){
this.firstOffset.set(firstOffset);
}

public void setLastOffset(Long lastOffset){
this.lastOffset.set(lastOffset);
}

public void setMinGelev(Long minGelev){
this.minGelev.set(minGelev);
}

public void setMaxGelev(Long maxGelev){
this.maxGelev.set(maxGelev);
}

public void setIncGelev(Long incGelev){
this.incGelev.set(incGelev);
}

public void setFirstGelev(Long firstGelev){
this.firstGelev.set(firstGelev);
}

public void setLastGelev(Long lastGelev){
this.lastGelev.set(lastGelev);
}

public void setMinSelev(Long minSelev){
this.minSelev.set(minSelev);
}

public void setMaxSelev(Long maxSelev){
this.maxSelev.set(maxSelev);
}

public void setIncSelev(Long incSelev){
this.incSelev.set(incSelev);
}

public void setFirstSelev(Long firstSelev){
this.firstSelev.set(firstSelev);
}

public void setLastSelev(Long lastSelev){
this.lastSelev.set(lastSelev);
}

public void setMinSdepth(Long minSdepth){
this.minSdepth.set(minSdepth);
}

public void setMaxSdepth(Long maxSdepth){
this.maxSdepth.set(maxSdepth);
}

public void setIncSdepth(Long incSdepth){
this.incSdepth.set(incSdepth);
}

public void setFirstSdepth(Long firstSdepth){
this.firstSdepth.set(firstSdepth);
}

public void setLastSdepth(Long lastSdepth){
this.lastSdepth.set(lastSdepth);
}

public void setMinSwdep(Long minSwdep){
this.minSwdep.set(minSwdep);
}

public void setMaxSwdep(Long maxSwdep){
this.maxSwdep.set(maxSwdep);
}

public void setIncSwdep(Long incSwdep){
this.incSwdep.set(incSwdep);
}

public void setFirstSwdep(Long firstSwdep){
this.firstSwdep.set(firstSwdep);
}

public void setLastSwdep(Long lastSwdep){
this.lastSwdep.set(lastSwdep);
}

public void setMinGwdep(Long minGwdep){
this.minGwdep.set(minGwdep);
}

public void setMaxGwdep(Long maxGwdep){
this.maxGwdep.set(maxGwdep);
}

public void setIncGwdep(Long incGwdep){
this.incGwdep.set(incGwdep);
}

public void setFirstGwdep(Long firstGwdep){
this.firstGwdep.set(firstGwdep);
}

public void setLastGwdep(Long lastGwdep){
this.lastGwdep.set(lastGwdep);
}

public void setMinScalel(Long minScalel){
this.minScalel.set(minScalel);
}

public void setMaxScalel(Long maxScalel){
this.maxScalel.set(maxScalel);
}

public void setIncScalel(Long incScalel){
this.incScalel.set(incScalel);
}

public void setFirstScalel(Long firstScalel){
this.firstScalel.set(firstScalel);
}

public void setLastScalel(Long lastScalel){
this.lastScalel.set(lastScalel);
}

public void setScalel(Long scalel){
this.scalel.set(scalel);
}

public void setMinScalco(Long minScalco){
this.minScalco.set(minScalco);
}

public void setMaxScalco(Long maxScalco){
this.maxScalco.set(maxScalco);
}

public void setIncScalco(Long incScalco){
this.incScalco.set(incScalco);
}

public void setFirstScalco(Long firstScalco){
this.firstScalco.set(firstScalco);
}

public void setLastScalco(Long lastScalco){
this.lastScalco.set(lastScalco);
}

public void setScalco(Long scalco){
this.scalco.set(scalco);
}

public void setMinSx(Long minSx){
this.minSx.set(minSx);
}

public void setMaxSx(Long maxSx){
this.maxSx.set(maxSx);
}

public void setIncSx(Long incSx){
this.incSx.set(incSx);
}

public void setFirstSx(Long firstSx){
this.firstSx.set(firstSx);
}

public void setLastSx(Long lastSx){
this.lastSx.set(lastSx);
}

public void setMinSy(Long minSy){
this.minSy.set(minSy);
}

public void setMaxSy(Long maxSy){
this.maxSy.set(maxSy);
}

public void setIncSy(Long incSy){
this.incSy.set(incSy);
}

public void setFirstSy(Long firstSy){
this.firstSy.set(firstSy);
}

public void setLastSy(Long lastSy){
this.lastSy.set(lastSy);
}

public void setMinGx(Long minGx){
this.minGx.set(minGx);
}

public void setMaxGx(Long maxGx){
this.maxGx.set(maxGx);
}

public void setIncGx(Long incGx){
this.incGx.set(incGx);
}

public void setFirstGx(Long firstGx){
this.firstGx.set(firstGx);
}

public void setLastGx(Long lastGx){
this.lastGx.set(lastGx);
}

public void setMinGy(Long minGy){
this.minGy.set(minGy);
}

public void setMaxGy(Long maxGy){
this.maxGy.set(maxGy);
}

public void setIncGy(Long incGy){
this.incGy.set(incGy);
}

public void setFirstGy(Long firstGy){
this.firstGy.set(firstGy);
}

public void setLastGy(Long lastGy){
this.lastGy.set(lastGy);
}

public void setMinWevel(Long minWevel){
this.minWevel.set(minWevel);
}

public void setMaxWevel(Long maxWevel){
this.maxWevel.set(maxWevel);
}

public void setIncWevel(Long incWevel){
this.incWevel.set(incWevel);
}

public void setFirstWevel(Long firstWevel){
this.firstWevel.set(firstWevel);
}

public void setLastWevel(Long lastWevel){
this.lastWevel.set(lastWevel);
}

public void setWevel(Long wevel){
this.wevel.set(wevel);
}

public void setMinSwevel(Long minSwevel){
this.minSwevel.set(minSwevel);
}

public void setMaxSwevel(Long maxSwevel){
this.maxSwevel.set(maxSwevel);
}

public void setIncSwevel(Long incSwevel){
this.incSwevel.set(incSwevel);
}

public void setFirstSwevel(Long firstSwevel){
this.firstSwevel.set(firstSwevel);
}

public void setLastSwevel(Long lastSwevel){
this.lastSwevel.set(lastSwevel);
}

public void setSwevel(Long swevel){
this.swevel.set(swevel);
}

public void setMinSut(Long minSut){
this.minSut.set(minSut);
}

public void setMaxSut(Long maxSut){
this.maxSut.set(maxSut);
}

public void setIncSut(Long incSut){
this.incSut.set(incSut);
}

public void setFirstSut(Long firstSut){
this.firstSut.set(firstSut);
}

public void setLastSut(Long lastSut){
this.lastSut.set(lastSut);
}

public void setMinGut(Long minGut){
this.minGut.set(minGut);
}

public void setMaxGut(Long maxGut){
this.maxGut.set(maxGut);
}

public void setIncGut(Long incGut){
this.incGut.set(incGut);
}

public void setFirstGut(Long firstGut){
this.firstGut.set(firstGut);
}

public void setLastGut(Long lastGut){
this.lastGut.set(lastGut);
}

public void setMinSstat(Long minSstat){
this.minSstat.set(minSstat);
}

public void setMaxSstat(Long maxSstat){
this.maxSstat.set(maxSstat);
}

public void setIncSstat(Long incSstat){
this.incSstat.set(incSstat);
}

public void setFirstSstat(Long firstSstat){
this.firstSstat.set(firstSstat);
}

public void setLastSstat(Long lastSstat){
this.lastSstat.set(lastSstat);
}

public void setMinGstat(Long minGstat){
this.minGstat.set(minGstat);
}

public void setMaxGstat(Long maxGstat){
this.maxGstat.set(maxGstat);
}

public void setIncGstat(Long incGstat){
this.incGstat.set(incGstat);
}

public void setFirstGstat(Long firstGstat){
this.firstGstat.set(firstGstat);
}

public void setLastGstat(Long lastGstat){
this.lastGstat.set(lastGstat);
}

public void setMinLagb(Long minLagb){
this.minLagb.set(minLagb);
}

public void setMaxLagb(Long maxLagb){
this.maxLagb.set(maxLagb);
}

public void setIncLagb(Long incLagb){
this.incLagb.set(incLagb);
}

public void setFirstLagb(Long firstLagb){
this.firstLagb.set(firstLagb);
}

public void setLastLagb(Long lastLagb){
this.lastLagb.set(lastLagb);
}

public void setMinNs(Long minNs){
this.minNs.set(minNs);
}

public void setMaxNs(Long maxNs){
this.maxNs.set(maxNs);
}

public void setIncNs(Long incNs){
this.incNs.set(incNs);
}

public void setFirstNs(Long firstNs){
this.firstNs.set(firstNs);
}

public void setLastNs(Long lastNs){
this.lastNs.set(lastNs);
}

public void setNs(Long ns){
this.ns.set(ns);
}

public void setMinDt(Long minDt){
this.minDt.set(minDt);
}

public void setMaxDt(Long maxDt){
this.maxDt.set(maxDt);
}

public void setIncDt(Long incDt){
this.incDt.set(incDt);
}

public void setFirstDt(Long firstDt){
this.firstDt.set(firstDt);
}

public void setLastDt(Long lastDt){
this.lastDt.set(lastDt);
}

public void setDt(Long dt){
this.dt.set(dt);
}

public void setMinSfs(Long minSfs){
this.minSfs.set(minSfs);
}

public void setMaxSfs(Long maxSfs){
this.maxSfs.set(maxSfs);
}

public void setIncSfs(Long incSfs){
this.incSfs.set(incSfs);
}

public void setFirstSfs(Long firstSfs){
this.firstSfs.set(firstSfs);
}

public void setLastSfs(Long lastSfs){
this.lastSfs.set(lastSfs);
}

public void setMinSfe(Long minSfe){
this.minSfe.set(minSfe);
}

public void setMaxSfe(Long maxSfe){
this.maxSfe.set(maxSfe);
}

public void setIncSfe(Long incSfe){
this.incSfe.set(incSfe);
}

public void setFirstSfe(Long firstSfe){
this.firstSfe.set(firstSfe);
}

public void setLastSfe(Long lastSfe){
this.lastSfe.set(lastSfe);
}

public void setMinSlen(Long minSlen){
this.minSlen.set(minSlen);
}

public void setMaxSlen(Long maxSlen){
this.maxSlen.set(maxSlen);
}

public void setIncSlen(Long incSlen){
this.incSlen.set(incSlen);
}

public void setFirstSlen(Long firstSlen){
this.firstSlen.set(firstSlen);
}

public void setLastSlen(Long lastSlen){
this.lastSlen.set(lastSlen);
}

public void setSlen(Long slen){
this.slen.set(slen);
}

public void setMinStyp(Long minStyp){
this.minStyp.set(minStyp);
}

public void setMaxStyp(Long maxStyp){
this.maxStyp.set(maxStyp);
}

public void setIncStyp(Long incStyp){
this.incStyp.set(incStyp);
}

public void setFirstStyp(Long firstStyp){
this.firstStyp.set(firstStyp);
}

public void setLastStyp(Long lastStyp){
this.lastStyp.set(lastStyp);
}

public void setMinStas(Long minStas){
this.minStas.set(minStas);
}

public void setMaxStas(Long maxStas){
this.maxStas.set(maxStas);
}

public void setIncStas(Long incStas){
this.incStas.set(incStas);
}

public void setFirstStas(Long firstStas){
this.firstStas.set(firstStas);
}

public void setLastStas(Long lastStas){
this.lastStas.set(lastStas);
}

public void setMinStae(Long minStae){
this.minStae.set(minStae);
}

public void setMaxStae(Long maxStae){
this.maxStae.set(maxStae);
}

public void setIncStae(Long incStae){
this.incStae.set(incStae);
}

public void setFirstStae(Long firstStae){
this.firstStae.set(firstStae);
}

public void setLastStae(Long lastStae){
this.lastStae.set(lastStae);
}

public void setStae(Long stae){
this.stae.set(stae);
}

public void setMinAfilf(Long minAfilf){
this.minAfilf.set(minAfilf);
}

public void setMaxAfilf(Long maxAfilf){
this.maxAfilf.set(maxAfilf);
}

public void setIncAfilf(Long incAfilf){
this.incAfilf.set(incAfilf);
}

public void setFirstAfilf(Long firstAfilf){
this.firstAfilf.set(firstAfilf);
}

public void setLastAfilf(Long lastAfilf){
this.lastAfilf.set(lastAfilf);
}

public void setAfilf(Long afilf){
this.afilf.set(afilf);
}

public void setMinAfils(Long minAfils){
this.minAfils.set(minAfils);
}

public void setMaxAfils(Long maxAfils){
this.maxAfils.set(maxAfils);
}

public void setIncAfils(Long incAfils){
this.incAfils.set(incAfils);
}

public void setFirstAfils(Long firstAfils){
this.firstAfils.set(firstAfils);
}

public void setLastAfils(Long lastAfils){
this.lastAfils.set(lastAfils);
}

public void setAfils(Long afils){
this.afils.set(afils);
}

public void setMinLcf(Long minLcf){
this.minLcf.set(minLcf);
}

public void setMaxLcf(Long maxLcf){
this.maxLcf.set(maxLcf);
}

public void setIncLcf(Long incLcf){
this.incLcf.set(incLcf);
}

public void setFirstLcf(Long firstLcf){
this.firstLcf.set(firstLcf);
}

public void setLastLcf(Long lastLcf){
this.lastLcf.set(lastLcf);
}

public void setLcf(Long lcf){
this.lcf.set(lcf);
}

public void setMinLcs(Long minLcs){
this.minLcs.set(minLcs);
}

public void setMaxLcs(Long maxLcs){
this.maxLcs.set(maxLcs);
}

public void setIncLcs(Long incLcs){
this.incLcs.set(incLcs);
}

public void setFirstLcs(Long firstLcs){
this.firstLcs.set(firstLcs);
}

public void setLastLcs(Long lastLcs){
this.lastLcs.set(lastLcs);
}

public void setLcs(Long lcs){
this.lcs.set(lcs);
}

public void setMinHcs(Long minHcs){
this.minHcs.set(minHcs);
}

public void setMaxHcs(Long maxHcs){
this.maxHcs.set(maxHcs);
}

public void setIncHcs(Long incHcs){
this.incHcs.set(incHcs);
}


public void setFirstHcs(Long firstHcs){
this.firstHcs.set(firstHcs);
}

public void setLastHcs(Long lastHcs){
this.lastHcs.set(lastHcs);
}

public void setMinYear(Long minYear){
this.minYear.set(minYear);
}

public void setMaxYear(Long maxYear){
this.maxYear.set(maxYear);
}

public void setIncYear(Long incYear){
this.incYear.set(incYear);
}

public void setFirstYear(Long firstYear){
this.firstYear.set(firstYear);
}

public void setLastYear(Long lastYear){
this.lastYear.set(lastYear);
}

public void setYear(Long year){
this.year.set(year);
}

public void setMinDay(Long minDay){
this.minDay.set(minDay);
}

public void setMaxDay(Long maxDay){
this.maxDay.set(maxDay);
}

public void setIncDay(Long incDay){
this.incDay.set(incDay);
}

public void setFirstDay(Long firstDay){
this.firstDay.set(firstDay);
}

public void setLastDay(Long lastDay){
this.lastDay.set(lastDay);
}

public void setDay(Long day){
this.day.set(day);
}

public void setMinHour(Long minHour){
this.minHour.set(minHour);
}

public void setMaxHour(Long maxHour){
this.maxHour.set(maxHour);
}

public void setIncHour(Long incHour){
this.incHour.set(incHour);
}

public void setFirstHour(Long firstHour){
this.firstHour.set(firstHour);
}

public void setLastHour(Long lastHour){
this.lastHour.set(lastHour);
}

public void setMinMinute(Long minMinute){
this.minMinute.set(minMinute);
}

public void setMaxMinute(Long maxMinute){
this.maxMinute.set(maxMinute);
}

public void setIncMinute(Long incMinute){
this.incMinute.set(incMinute);
}

public void setFirstMinute(Long firstMinute){
this.firstMinute.set(firstMinute);
}

public void setLastMinute(Long lastMinute){
this.lastMinute.set(lastMinute);
}

public void setMinSec(Long minSec){
this.minSec.set(minSec);
}

public void setMaxSec(Long maxSec){
this.maxSec.set(maxSec);
}

public void setIncSec(Long incSec){
this.incSec.set(incSec);
}

public void setFirstSec(Long firstSec){
this.firstSec.set(firstSec);
}

public void setLastSec(Long lastSec){
this.lastSec.set(lastSec);
}

public void setMinGrnors(Long minGrnors){
this.minGrnors.set(minGrnors);
}

public void setMaxGrnors(Long maxGrnors){
this.maxGrnors.set(maxGrnors);
}

public void setIncGrnors(Long incGrnors){
this.incGrnors.set(incGrnors);
}

public void setFirstGrnors(Long firstGrnors){
this.firstGrnors.set(firstGrnors);
}

public void setLastGrnors(Long lastGrnors){
this.lastGrnors.set(lastGrnors);
}

public void setGrnors(Long grnors){
this.grnors.set(grnors);
}

public void setMinGrnofr(Long minGrnofr){
this.minGrnofr.set(minGrnofr);
}

public void setMaxGrnofr(Long maxGrnofr){
this.maxGrnofr.set(maxGrnofr);
}

public void setIncGrnofr(Long incGrnofr){
this.incGrnofr.set(incGrnofr);
}

public void setFirstGrnofr(Long firstGrnofr){
this.firstGrnofr.set(firstGrnofr);
}

public void setLastGrnofr(Long lastGrnofr){
this.lastGrnofr.set(lastGrnofr);
}

public void setGrnofr(Long grnofr){
this.grnofr.set(grnofr);
}

public void setMinGaps(Long minGaps){
this.minGaps.set(minGaps);
}

public void setMaxGaps(Long maxGaps){
this.maxGaps.set(maxGaps);
}

public void setIncGaps(Long incGaps){
this.incGaps.set(incGaps);
}

public void setFirstGaps(Long firstGaps){
this.firstGaps.set(firstGaps);
}

public void setLastGaps(Long lastGaps){
this.lastGaps.set(lastGaps);
}

public void setMinOtrav(Long minOtrav){
this.minOtrav.set(minOtrav);
}

public void setMaxOtrav(Long maxOtrav){
this.maxOtrav.set(maxOtrav);
}

public void setIncOtrav(Long incOtrav){
this.incOtrav.set(incOtrav);
}

public void setFirstOtrav(Long firstOtrav){
this.firstOtrav.set(firstOtrav);
}

public void setLastOtrav(Long lastOtrav){
this.lastOtrav.set(lastOtrav);
}

public void setMinCdpx(Long minCdpx){
this.minCdpx.set(minCdpx);
}

public void setMaxCdpx(Long maxCdpx){
this.maxCdpx.set(maxCdpx);
}

public void setIncCdpx(Long incCdpx){
this.incCdpx.set(incCdpx);
}

public void setFirstCdpx(Long firstCdpx){
this.firstCdpx.set(firstCdpx);
}

public void setLastCdpx(Long lastCdpx){
this.lastCdpx.set(lastCdpx);
}

public void setMinCdpy(Long minCdpy){
this.minCdpy.set(minCdpy);
}

public void setMaxCdpy(Long maxCdpy){
this.maxCdpy.set(maxCdpy);
}

public void setIncCdpy(Long incCdpy){
this.incCdpy.set(incCdpy);
}

public void setFirstCdpy(Long firstCdpy){
this.firstCdpy.set(firstCdpy);
}

public void setLastCdpy(Long lastCdpy){
this.lastCdpy.set(lastCdpy);
}

public void setMinInline(Long minInline){
this.minInline.set(minInline);
}

public void setMaxInline(Long maxInline){
this.maxInline.set(maxInline);
}

public void setIncInline(Long incInline){
this.incInline.set(incInline);
}

public void setFirstInline(Long firstInline){
this.firstInline.set(firstInline);
}

public void setLastInline(Long lastInline){
this.lastInline.set(lastInline);
}

public void setMinCrossline(Long minCrossline){
this.minCrossline.set(minCrossline);
}

public void setMaxCrossline(Long maxCrossline){
this.maxCrossline.set(maxCrossline);
}

public void setIncCrossline(Long incCrossline){
this.incCrossline.set(incCrossline);
}

public void setFirstCrossline(Long firstCrossline){
this.firstCrossline.set(firstCrossline);
}

public void setLastCrossline(Long lastCrossline){
this.lastCrossline.set(lastCrossline);
}

public void setMinShotpoint(Long minShotpoint){
this.minShotpoint.set(minShotpoint);
}

public void setMaxShotpoint(Long maxShotpoint){
this.maxShotpoint.set(maxShotpoint);
}

public void setIncShotpoint(Long incShotpoint){
this.incShotpoint.set(incShotpoint);
}

public void setFirstShotpoint(Long firstShotpoint){
this.firstShotpoint.set(firstShotpoint);
}

public void setLastShotpoint(Long lastShotpoint){
this.lastShotpoint.set(lastShotpoint);
}

public void setMinScalsp(Long minScalsp){
this.minScalsp.set(minScalsp);
}

public void setMaxScalsp(Long maxScalsp){
this.maxScalsp.set(maxScalsp);
}

public void setIncScalsp(Long incScalsp){
this.incScalsp.set(incScalsp);
}

public void setFirstScalsp(Long firstScalsp){
this.firstScalsp.set(firstScalsp);
}

public void setLastScalsp(Long lastScalsp){
this.lastScalsp.set(lastScalsp);
}

public void setMinTcm(Long minTcm){
this.minTcm.set(minTcm);
}

public void setMaxTcm(Long maxTcm){
this.maxTcm.set(maxTcm);
}

public void setIncTcm(Long incTcm){
this.incTcm.set(incTcm);
}

public void setFirstTcm(Long firstTcm){
this.firstTcm.set(firstTcm);
}

public void setLastTcm(Long lastTcm){
this.lastTcm.set(lastTcm);
}

public void setMinTid(Long minTid){
this.minTid.set(minTid);
}

public void setMaxTid(Long maxTid){
this.maxTid.set(maxTid);
}

public void setIncTid(Long incTid){
this.incTid.set(incTid);
}

public void setFirstTid(Long firstTid){
this.firstTid.set(firstTid);
}

public void setLastTid(Long lastTid){
this.lastTid.set(lastTid);
}

public void setTid(Long tid){
this.tid.set(tid);
}

public void setMinSedm(Long minSedm){
this.minSedm.set(minSedm);
}

public void setMaxSedm(Long maxSedm){
this.maxSedm.set(maxSedm);
}

public void setIncSedm(Long incSedm){
this.incSedm.set(incSedm);
}

public void setFirstSedm(Long firstSedm){
this.firstSedm.set(firstSedm);
}

public void setLastSedm(Long lastSedm){
this.lastSedm.set(lastSedm);
}

public void setMinSede(Long minSede){
this.minSede.set(minSede);
}

public void setMaxSede(Long maxSede){
this.maxSede.set(maxSede);
}

public void setIncSede(Long incSede){
this.incSede.set(incSede);
}

public void setFirstSede(Long firstSede){
this.firstSede.set(firstSede);
}

public void setLastSede(Long lastSede){
this.lastSede.set(lastSede);
}

public void setSede(Long sede){
this.sede.set(sede);
}

public void setMinSmm(Long minSmm){
this.minSmm.set(minSmm);
}

public void setMaxSmm(Long maxSmm){
this.maxSmm.set(maxSmm);
}

public void setIncSmm(Long incSmm){
this.incSmm.set(incSmm);
}

public void setFirstSmm(Long firstSmm){
this.firstSmm.set(firstSmm);
}

public void setLastSmm(Long lastSmm){
this.lastSmm.set(lastSmm);
}

public void setMinSme(Long minSme){
this.minSme.set(minSme);
}

public void setMaxSme(Long maxSme){
this.maxSme.set(maxSme);
}

public void setIncSme(Long incSme){
this.incSme.set(incSme);
}

public void setFirstSme(Long firstSme){
this.firstSme.set(firstSme);
}

public void setLastSme(Long lastSme){
this.lastSme.set(lastSme);
}

public void setMinSmu(Long minSmu){
this.minSmu.set(minSmu);
}

public void setMaxSmu(Long maxSmu){
this.maxSmu.set(maxSmu);
}

public void setIncSmu(Long incSmu){
this.incSmu.set(incSmu);
}

public void setFirstSmu(Long firstSmu){
this.firstSmu.set(firstSmu);
}

public void setLastSmu(Long lastSmu){
this.lastSmu.set(lastSmu);
}

public void setMinUi1(Long minUi1){
this.minUi1.set(minUi1);
}

public void setMaxUi1(Long maxUi1){
this.maxUi1.set(maxUi1);
}

public void setIncUi1(Long incUi1){
this.incUi1.set(incUi1);
}

public void setFirstUi1(Long firstUi1){
this.firstUi1.set(firstUi1);
}

public void setLastUi1(Long lastUi1){
this.lastUi1.set(lastUi1);
}

public void setMinUi2(Long minUi2){
this.minUi2.set(minUi2);
}

public void setMaxUi2(Long maxUi2){
this.maxUi2.set(maxUi2);
}

public void setIncUi2(Long incUi2){
this.incUi2.set(incUi2);
}

public void setFirstUi2(Long firstUi2){
this.firstUi2.set(firstUi2);
}

public void setLastUi2(Long lastUi2){
this.lastUi2.set(lastUi2);
}

public void setUi2(Long ui2){
this.ui2.set(ui2);
}

public void setMinVer(Long minVer){
this.minVer.set(minVer);
}

public void setMaxVer(Long maxVer){
this.maxVer.set(maxVer);
}

public void setIncVer(Long incVer){
this.incVer.set(incVer);
}

public void setSampleRate(Long sampleRate){
this.sampleRate.set(sampleRate);
}

public void setRecordLength(Long recordLength){
this.recordLength.set(recordLength);
}

public void setUnitVer(Long unitVer){
this.unitVer.set(unitVer);
}
    
    
    

}
