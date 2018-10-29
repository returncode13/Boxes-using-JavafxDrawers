/*/*
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

    protected boolean isSequence = true;
    protected boolean isSubsurface = false;
    protected Sequence sequence;

    protected String insight = new String();
    protected String workflow = new String();
    protected Long numberOfRuns = 0L;
    protected Boolean chosen = true;
    protected Boolean multiple = false;
    protected Boolean deleted = false;
    protected Long sequenceNumber = 0L;
    protected String subsurfaceName = new String();
    protected String timeStamp = new String();

    private List<FullSubsurfaceHeaders> subsurfaceHeaders = new ArrayList<>();

    protected Long totalTraces = 0L;

    protected Long minTracr = 0L;

    protected Long maxTracr = 0L;

    protected Long incTracr = 0L;

    protected Long firstTracr = 0L;

    protected Long lastTracr = 0L;

    protected Long minFldr = 0L;

    protected Long maxFldr = 0L;

    protected Long incFldr = 0L;

    protected Long firstFldr = 0L;

    protected Long lastFldr = 0L;

    protected Long minTracf = 0L;

    protected Long maxTracf = 0L;

    protected Long incTracf = 0L;

    protected Long firstTracf = 0L;

    protected Long lastTracf = 0L;

    protected Long minEp = 0L;

    protected Long maxEp = 0L;

    protected Long incEp = 0L;

    protected Long firstEp = 0L;

    protected Long lastEp = 0L;

    protected Long minCdp = 0L;

    protected Long maxCdp = 0L;

    protected Long incCdp = 0L;

    protected Long firstCdp = 0L;

    protected Long lastCdp = 0L;

    protected Long minCdpt = 0L;

    protected Long maxCdpt = 0L;

    protected Long incCdpt = 0L;

    protected Long firstCdpt = 0L;

    protected Long lastCdpt = 0L;

    protected Long minTrid = 0L;

    protected Long maxTrid = 0L;

    protected Long incTrid = 0L;

    protected Long firstTrid = 0L;

    protected Long lastTrid = 0L;

    protected Long minDuse = 0L;

    protected Long maxDuse = 0L;

    protected Long incDuse = 0L;

    protected Long firstDuse = 0L;

    protected Long lastDuse = 0L;

    protected Long duse = 0L;

    protected Long minOffset = 0L;

    protected Long maxOffset = 0L;

    protected Long incOffset = 0L;

    protected Long firstOffset = 0L;

    protected Long lastOffset = 0L;

    protected Long minGelev = 0L;

    protected Long maxGelev = 0L;

    protected Long incGelev = 0L;

    protected Long firstGelev = 0L;

    protected Long lastGelev = 0L;

    protected Long minSelev = 0L;

    protected Long maxSelev = 0L;

    protected Long incSelev = 0L;

    protected Long firstSelev = 0L;

    protected Long lastSelev = 0L;

    protected Long minSdepth = 0L;

    protected Long maxSdepth = 0L;

    protected Long incSdepth = 0L;

    protected Long firstSdepth = 0L;

    protected Long lastSdepth = 0L;

    protected Long minSwdep = 0L;

    protected Long maxSwdep = 0L;

    protected Long incSwdep = 0L;

    protected Long firstSwdep = 0L;

    protected Long lastSwdep = 0L;

    protected Long minGwdep = 0L;

    protected Long maxGwdep = 0L;

    protected Long incGwdep = 0L;

    protected Long firstGwdep = 0L;

    protected Long lastGwdep = 0L;

    protected Long minScalel = 0L;

    protected Long maxScalel = 0L;

    protected Long incScalel = 0L;

    protected Long firstScalel = 0L;

    protected Long lastScalel = 0L;

    protected Long scalel = 0L;

    protected Long minScalco = 0L;

    protected Long maxScalco = 0L;

    protected Long incScalco = 0L;

    protected Long firstScalco = 0L;

    protected Long lastScalco = 0L;

    protected Long scalco = 0L;

    protected Long minSx = 0L;

    protected Long maxSx = 0L;

    protected Long incSx = 0L;

    protected Long firstSx = 0L;

    protected Long lastSx = 0L;

    protected Long minSy = 0L;

    protected Long maxSy = 0L;

    protected Long incSy = 0L;

    protected Long firstSy = 0L;

    protected Long lastSy = 0L;

    protected Long minGx = 0L;

    protected Long maxGx = 0L;

    protected Long incGx = 0L;

    protected Long firstGx = 0L;

    protected Long lastGx = 0L;

    protected Long minGy = 0L;

    protected Long maxGy = 0L;

    protected Long incGy = 0L;

    protected Long firstGy = 0L;

    protected Long lastGy = 0L;

    protected Long minWevel = 0L;

    protected Long maxWevel = 0L;

    protected Long incWevel = 0L;

    protected Long firstWevel = 0L;

    protected Long lastWevel = 0L;

    protected Long wevel = 0L;

    protected Long minSwevel = 0L;

    protected Long maxSwevel = 0L;

    protected Long incSwevel = 0L;

    protected Long firstSwevel = 0L;

    protected Long lastSwevel = 0L;

    protected Long swevel = 0L;

    protected Long minSut = 0L;

    protected Long maxSut = 0L;

    protected Long incSut = 0L;

    protected Long firstSut = 0L;

    protected Long lastSut = 0L;

    protected Long minGut = 0L;

    protected Long maxGut = 0L;

    protected Long incGut = 0L;

    protected Long firstGut = 0L;

    protected Long lastGut = 0L;

    protected Long minSstat = 0L;

    protected Long maxSstat = 0L;

    protected Long incSstat = 0L;

    protected Long firstSstat = 0L;

    protected Long lastSstat = 0L;

    protected Long minGstat = 0L;

    protected Long maxGstat = 0L;

    protected Long incGstat = 0L;

    protected Long firstGstat = 0L;

    protected Long lastGstat = 0L;

    protected Long minLagb = 0L;

    protected Long maxLagb = 0L;

    protected Long incLagb = 0L;

    protected Long firstLagb = 0L;

    protected Long lastLagb = 0L;

    protected Long minNs = 0L;

    protected Long maxNs = 0L;

    protected Long incNs = 0L;

    protected Long firstNs = 0L;

    protected Long lastNs = 0L;

    protected Long ns = 0L;

    protected Long minDt = 0L;

    protected Long maxDt = 0L;

    protected Long incDt = 0L;

    protected Long firstDt = 0L;

    protected Long lastDt = 0L;

    protected Long dt = 0L;

    protected Long minSfs = 0L;

    protected Long maxSfs = 0L;

    protected Long incSfs = 0L;

    protected Long firstSfs = 0L;

    protected Long lastSfs = 0L;

    protected Long minSfe = 0L;

    protected Long maxSfe = 0L;

    protected Long incSfe = 0L;

    protected Long firstSfe = 0L;

    protected Long lastSfe = 0L;

    protected Long minSlen = 0L;

    protected Long maxSlen = 0L;

    protected Long incSlen = 0L;

    protected Long firstSlen = 0L;

    protected Long lastSlen = 0L;

    protected Long slen = 0L;

    protected Long minStyp = 0L;

    protected Long maxStyp = 0L;

    protected Long incStyp = 0L;

    protected Long firstStyp = 0L;

    protected Long lastStyp = 0L;

    protected Long minStas = 0L;

    protected Long maxStas = 0L;

    protected Long incStas = 0L;

    protected Long firstStas = 0L;

    protected Long lastStas = 0L;

    protected Long minStae = 0L;

    protected Long maxStae = 0L;

    protected Long incStae = 0L;

    protected Long firstStae = 0L;

    protected Long lastStae = 0L;

    protected Long stae = 0L;

    protected Long minAfilf = 0L;

    protected Long maxAfilf = 0L;

    protected Long incAfilf = 0L;

    protected Long firstAfilf = 0L;

    protected Long lastAfilf = 0L;

    protected Long afilf = 0L;

    protected Long minAfils = 0L;

    protected Long maxAfils = 0L;

    protected Long incAfils = 0L;

    protected Long firstAfils = 0L;

    protected Long lastAfils = 0L;

    protected Long afils = 0L;

    protected Long minLcf = 0L;

    protected Long maxLcf = 0L;

    protected Long incLcf = 0L;

    protected Long firstLcf = 0L;

    protected Long lastLcf = 0L;

    protected Long lcf = 0L;

    protected Long minLcs = 0L;

    protected Long maxLcs = 0L;

    protected Long incLcs = 0L;

    protected Long firstLcs = 0L;

    protected Long lastLcs = 0L;

    protected Long lcs = 0L;

    protected Long minHcs = 0L;

    protected Long maxHcs = 0L;

    protected Long incHcs = 0L;

    protected Long firstHcs = 0L;

    protected Long lastHcs = 0L;

    protected Long minYear = 0L;

    protected Long maxYear = 0L;

    protected Long incYear = 0L;

    protected Long firstYear = 0L;

    protected Long lastYear = 0L;

    protected Long year = 0L;

    protected Long minDay = 0L;

    protected Long maxDay = 0L;

    protected Long incDay = 0L;

    protected Long firstDay = 0L;

    protected Long lastDay = 0L;

    protected Long day = 0L;

    protected Long minHour = 0L;

    protected Long maxHour = 0L;

    protected Long incHour = 0L;

    protected Long firstHour = 0L;

    protected Long lastHour = 0L;

    protected Long minMinute = 0L;

    protected Long maxMinute = 0L;

    protected Long incMinute = 0L;

    protected Long firstMinute = 0L;

    protected Long lastMinute = 0L;

    protected Long minSec = 0L;

    protected Long maxSec = 0L;

    protected Long incSec = 0L;

    protected Long firstSec = 0L;

    protected Long lastSec = 0L;

    protected Long minGrnors = 0L;

    protected Long maxGrnors = 0L;

    protected Long incGrnors = 0L;

    protected Long firstGrnors = 0L;

    protected Long lastGrnors = 0L;

    protected Long grnors = 0L;

    protected Long minGrnofr = 0L;

    protected Long maxGrnofr = 0L;

    protected Long incGrnofr = 0L;

    protected Long firstGrnofr = 0L;

    protected Long lastGrnofr = 0L;

    protected Long grnofr = 0L;

    protected Long minGaps = 0L;

    protected Long maxGaps = 0L;

    protected Long incGaps = 0L;

    protected Long firstGaps = 0L;

    protected Long lastGaps = 0L;

    protected Long minOtrav = 0L;

    protected Long maxOtrav = 0L;

    protected Long incOtrav = 0L;

    protected Long firstOtrav = 0L;

    protected Long lastOtrav = 0L;

    protected Long minCdpx = 0L;

    protected Long maxCdpx = 0L;

    protected Long incCdpx = 0L;

    protected Long firstCdpx = 0L;

    protected Long lastCdpx = 0L;

    protected Long minCdpy = 0L;

    protected Long maxCdpy = 0L;

    protected Long incCdpy = 0L;

    protected Long firstCdpy = 0L;

    protected Long lastCdpy = 0L;

    protected Long minInline = 0L;

    protected Long maxInline = 0L;

    protected Long incInline = 0L;

    protected Long firstInline = 0L;

    protected Long lastInline = 0L;

    protected Long minCrossline = 0L;

    protected Long maxCrossline = 0L;

    protected Long incCrossline = 0L;

    protected Long firstCrossline = 0L;

    protected Long lastCrossline = 0L;

    protected Long minShotpoint = 0L;

    protected Long maxShotpoint = 0L;

    protected Long incShotpoint = 0L;

    protected Long firstShotpoint = 0L;

    protected Long lastShotpoint = 0L;

    protected Long minScalsp = 0L;

    protected Long maxScalsp = 0L;

    protected Long incScalsp = 0L;

    protected Long firstScalsp = 0L;

    protected Long lastScalsp = 0L;

    protected Long minTcm = 0L;

    protected Long maxTcm = 0L;

    protected Long incTcm = 0L;

    protected Long firstTcm = 0L;

    protected Long lastTcm = 0L;

    protected Long minTid = 0L;

    protected Long maxTid = 0L;

    protected Long incTid = 0L;

    protected Long firstTid = 0L;

    protected Long lastTid = 0L;

    protected Long tid = 0L;

    protected Long minSedm = 0L;

    protected Long maxSedm = 0L;

    protected Long incSedm = 0L;

    protected Long firstSedm = 0L;

    protected Long lastSedm = 0L;

    protected Long minSede = 0L;

    protected Long maxSede = 0L;

    protected Long incSede = 0L;

    protected Long firstSede = 0L;

    protected Long lastSede = 0L;

    protected Long sede = 0L;

    protected Long minSmm = 0L;

    protected Long maxSmm = 0L;

    protected Long incSmm = 0L;

    protected Long firstSmm = 0L;

    protected Long lastSmm = 0L;

    protected Long minSme = 0L;

    protected Long maxSme = 0L;

    protected Long incSme = 0L;

    protected Long firstSme = 0L;

    protected Long lastSme = 0L;

    protected Long minSmu = 0L;

    protected Long maxSmu = 0L;

    protected Long incSmu = 0L;

    protected Long firstSmu = 0L;

    protected Long lastSmu = 0L;

    protected Long minUi1 = 0L;

    protected Long maxUi1 = 0L;

    protected Long incUi1 = 0L;

    protected Long firstUi1 = 0L;

    protected Long lastUi1 = 0L;

    protected Long minUi2 = 0L;

    protected Long maxUi2 = 0L;

    protected Long incUi2 = 0L;

    protected Long firstUi2 = 0L;

    protected Long lastUi2 = 0L;

    protected Long ui2 = 0L;

    protected Long minVer = 0L;

    protected Long maxVer = 0L;

    protected Long incVer = 0L;

    protected Long sampleRate = 0L;

    protected Long recordLength = 0L;

    protected Long unitVer = 0L;

   
    public FullSequenceHeaders() {

        isSequence = true;
        isSubsurface = false;
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

   
    public Long getTotalTraces() {
        return totalTraces;
    }

    public Long getMinTracr() {
        return minTracr;
    }

    public Long getMaxTracr() {
        return maxTracr;
    }

    public Long getIncTracr() {
        return incTracr;
    }

    public Long getFirstTracr() {
        return firstTracr;
    }

    public Long getLastTracr() {
        return lastTracr;
    }

    public Long getMinFldr() {
        return minFldr;
    }

    public Long getMaxFldr() {
        return maxFldr;
    }

    public Long getIncFldr() {
        return incFldr;
    }

    public Long getFirstFldr() {
        return firstFldr;
    }

    public Long getLastFldr() {
        return lastFldr;
    }

    public Long getMinTracf() {
        return minTracf;
    }

    public Long getMaxTracf() {
        return maxTracf;
    }

    public Long getIncTracf() {
        return incTracf;
    }

    public Long getFirstTracf() {
        return firstTracf;
    }

    public Long getLastTracf() {
        return lastTracf;
    }

    public Long getMinEp() {
        return minEp;
    }

    public Long getMaxEp() {
        return maxEp;
    }

    public Long getIncEp() {
        return incEp;
    }

    public Long getFirstEp() {
        return firstEp;
    }

    public Long getLastEp() {
        return lastEp;
    }

    public Long getMinCdp() {
        return minCdp;
    }

    public Long getMaxCdp() {
        return maxCdp;
    }

    public Long getIncCdp() {
        return incCdp;
    }

    public Long getFirstCdp() {
        return firstCdp;
    }

    public Long getLastCdp() {
        return lastCdp;
    }

    public Long getMinCdpt() {
        return minCdpt;
    }

    public Long getMaxCdpt() {
        return maxCdpt;
    }

    public Long getIncCdpt() {
        return incCdpt;
    }

    public Long getFirstCdpt() {
        return firstCdpt;
    }

    public Long getLastCdpt() {
        return lastCdpt;
    }

    public Long getMinTrid() {
        return minTrid;
    }

    public Long getMaxTrid() {
        return maxTrid;
    }

    public Long getIncTrid() {
        return incTrid;
    }

    public Long getFirstTrid() {
        return firstTrid;
    }

    public Long getLastTrid() {
        return lastTrid;
    }

    public Long getMinDuse() {
        return minDuse;
    }

    public Long getMaxDuse() {
        return maxDuse;
    }

    public Long getIncDuse() {
        return incDuse;
    }

    public Long getFirstDuse() {
        return firstDuse;
    }

    public Long getLastDuse() {
        return lastDuse;
    }

    public Long getDuse() {
        return duse;
    }

    public Long getMinOffset() {
        return minOffset;
    }

    public Long getMaxOffset() {
        return maxOffset;
    }

    public Long getIncOffset() {
        return incOffset;
    }

    public Long getFirstOffset() {
        return firstOffset;
    }

    public Long getLastOffset() {
        return lastOffset;
    }

    public Long getMinGelev() {
        return minGelev;
    }

    public Long getMaxGelev() {
        return maxGelev;
    }

    public Long getIncGelev() {
        return incGelev;
    }

    public Long getFirstGelev() {
        return firstGelev;
    }

    public Long getLastGelev() {
        return lastGelev;
    }

    public Long getMinSelev() {
        return minSelev;
    }

    public Long getMaxSelev() {
        return maxSelev;
    }

    public Long getIncSelev() {
        return incSelev;
    }

    public Long getFirstSelev() {
        return firstSelev;
    }

    public Long getLastSelev() {
        return lastSelev;
    }

    public Long getMinSdepth() {
        return minSdepth;
    }

    public Long getMaxSdepth() {
        return maxSdepth;
    }

    public Long getIncSdepth() {
        return incSdepth;
    }

    public Long getFirstSdepth() {
        return firstSdepth;
    }

    public Long getLastSdepth() {
        return lastSdepth;
    }

    public Long getMinSwdep() {
        return minSwdep;
    }

    public Long getMaxSwdep() {
        return maxSwdep;
    }

    public Long getIncSwdep() {
        return incSwdep;
    }

    public Long getFirstSwdep() {
        return firstSwdep;
    }

    public Long getLastSwdep() {
        return lastSwdep;
    }

    public Long getMinGwdep() {
        return minGwdep;
    }

    public Long getMaxGwdep() {
        return maxGwdep;
    }

    public Long getIncGwdep() {
        return incGwdep;
    }

    public Long getFirstGwdep() {
        return firstGwdep;
    }

    public Long getLastGwdep() {
        return lastGwdep;
    }

    public Long getMinScalel() {
        return minScalel;
    }

    public Long getMaxScalel() {
        return maxScalel;
    }

    public Long getIncScalel() {
        return incScalel;
    }

    public Long getFirstScalel() {
        return firstScalel;
    }

    public Long getLastScalel() {
        return lastScalel;
    }

    public Long getScalel() {
        return scalel;
    }

    public Long getMinScalco() {
        return minScalco;
    }

    public Long getMaxScalco() {
        return maxScalco;
    }

    public Long getIncScalco() {
        return incScalco;
    }

    public Long getFirstScalco() {
        return firstScalco;
    }

    public Long getLastScalco() {
        return lastScalco;
    }

    public Long getScalco() {
        return scalco;
    }

    public Long getMinSx() {
        return minSx;
    }

    public Long getMaxSx() {
        return maxSx;
    }

    public Long getIncSx() {
        return incSx;
    }

    public Long getFirstSx() {
        return firstSx;
    }

    public Long getLastSx() {
        return lastSx;
    }

    public Long getMinSy() {
        return minSy;
    }

    public Long getMaxSy() {
        return maxSy;
    }

    public Long getIncSy() {
        return incSy;
    }

    public Long getFirstSy() {
        return firstSy;
    }

    public Long getLastSy() {
        return lastSy;
    }

    public Long getMinGx() {
        return minGx;
    }

    public Long getMaxGx() {
        return maxGx;
    }

    public Long getIncGx() {
        return incGx;
    }

    public Long getFirstGx() {
        return firstGx;
    }

    public Long getLastGx() {
        return lastGx;
    }

    public Long getMinGy() {
        return minGy;
    }

    public Long getMaxGy() {
        return maxGy;
    }

    public Long getIncGy() {
        return incGy;
    }

    public Long getFirstGy() {
        return firstGy;
    }

    public Long getLastGy() {
        return lastGy;
    }

    public Long getMinWevel() {
        return minWevel;
    }

    public Long getMaxWevel() {
        return maxWevel;
    }

    public Long getIncWevel() {
        return incWevel;
    }

    public Long getFirstWevel() {
        return firstWevel;
    }

    public Long getLastWevel() {
        return lastWevel;
    }

    public Long getWevel() {
        return wevel;
    }

    public Long getMinSwevel() {
        return minSwevel;
    }

    public Long getMaxSwevel() {
        return maxSwevel;
    }

    public Long getIncSwevel() {
        return incSwevel;
    }

    public Long getFirstSwevel() {
        return firstSwevel;
    }

    public Long getLastSwevel() {
        return lastSwevel;
    }

    public Long getSwevel() {
        return swevel;
    }

    public Long getMinSut() {
        return minSut;
    }

    public Long getMaxSut() {
        return maxSut;
    }

    public Long getIncSut() {
        return incSut;
    }

    public Long getFirstSut() {
        return firstSut;
    }

    public Long getLastSut() {
        return lastSut;
    }

    public Long getMinGut() {
        return minGut;
    }

    public Long getMaxGut() {
        return maxGut;
    }

    public Long getIncGut() {
        return incGut;
    }

    public Long getFirstGut() {
        return firstGut;
    }

    public Long getLastGut() {
        return lastGut;
    }

    public Long getMinSstat() {
        return minSstat;
    }

    public Long getMaxSstat() {
        return maxSstat;
    }

    public Long getIncSstat() {
        return incSstat;
    }

    public Long getFirstSstat() {
        return firstSstat;
    }

    public Long getLastSstat() {
        return lastSstat;
    }

    public Long getMinGstat() {
        return minGstat;
    }

    public Long getMaxGstat() {
        return maxGstat;
    }

    public Long getIncGstat() {
        return incGstat;
    }

    public Long getFirstGstat() {
        return firstGstat;
    }

    public Long getLastGstat() {
        return lastGstat;
    }

    public Long getMinLagb() {
        return minLagb;
    }

    public Long getMaxLagb() {
        return maxLagb;
    }

    public Long getIncLagb() {
        return incLagb;
    }

    public Long getFirstLagb() {
        return firstLagb;
    }

    public Long getLastLagb() {
        return lastLagb;
    }

    public Long getMinNs() {
        return minNs;
    }

    public Long getMaxNs() {
        return maxNs;
    }

    public Long getIncNs() {
        return incNs;
    }

    public Long getFirstNs() {
        return firstNs;
    }

    public Long getLastNs() {
        return lastNs;
    }

    public Long getNs() {
        return ns;
    }

    public Long getMinDt() {
        return minDt;
    }

    public Long getMaxDt() {
        return maxDt;
    }

    public Long getIncDt() {
        return incDt;
    }

    public Long getFirstDt() {
        return firstDt;
    }

    public Long getLastDt() {
        return lastDt;
    }

    public Long getDt() {
        return dt;
    }

    public Long getMinSfs() {
        return minSfs;
    }

    public Long getMaxSfs() {
        return maxSfs;
    }

    public Long getIncSfs() {
        return incSfs;
    }

    public Long getFirstSfs() {
        return firstSfs;
    }

    public Long getLastSfs() {
        return lastSfs;
    }

    public Long getMinSfe() {
        return minSfe;
    }

    public Long getMaxSfe() {
        return maxSfe;
    }

    public Long getIncSfe() {
        return incSfe;
    }

    public Long getFirstSfe() {
        return firstSfe;
    }

    public Long getLastSfe() {
        return lastSfe;
    }

    public Long getMinSlen() {
        return minSlen;
    }

    public Long getMaxSlen() {
        return maxSlen;
    }

    public Long getIncSlen() {
        return incSlen;
    }

    public Long getFirstSlen() {
        return firstSlen;
    }

    public Long getLastSlen() {
        return lastSlen;
    }

    public Long getSlen() {
        return slen;
    }

    public Long getMinStyp() {
        return minStyp;
    }

    public Long getMaxStyp() {
        return maxStyp;
    }

    public Long getIncStyp() {
        return incStyp;
    }

    public Long getFirstStyp() {
        return firstStyp;
    }

    public Long getLastStyp() {
        return lastStyp;
    }

    public Long getMinStas() {
        return minStas;
    }

    public Long getMaxStas() {
        return maxStas;
    }

    public Long getIncStas() {
        return incStas;
    }

    public Long getFirstStas() {
        return firstStas;
    }

    public Long getLastStas() {
        return lastStas;
    }

    public Long getMinStae() {
        return minStae;
    }

    public Long getMaxStae() {
        return maxStae;
    }

    public Long getIncStae() {
        return incStae;
    }

    public Long getFirstStae() {
        return firstStae;
    }

    public Long getLastStae() {
        return lastStae;
    }

    public Long getStae() {
        return stae;
    }

    public Long getMinAfilf() {
        return minAfilf;
    }

    public Long getMaxAfilf() {
        return maxAfilf;
    }

    public Long getIncAfilf() {
        return incAfilf;
    }

    public Long getFirstAfilf() {
        return firstAfilf;
    }

    public Long getLastAfilf() {
        return lastAfilf;
    }

    public Long getAfilf() {
        return afilf;
    }

    public Long getMinAfils() {
        return minAfils;
    }

    public Long getMaxAfils() {
        return maxAfils;
    }

    public Long getIncAfils() {
        return incAfils;
    }

    public Long getFirstAfils() {
        return firstAfils;
    }

    public Long getLastAfils() {
        return lastAfils;
    }

    public Long getAfils() {
        return afils;
    }

    public Long getMinLcf() {
        return minLcf;
    }

    public Long getMaxLcf() {
        return maxLcf;
    }

    public Long getIncLcf() {
        return incLcf;
    }

    public Long getFirstLcf() {
        return firstLcf;
    }

    public Long getLastLcf() {
        return lastLcf;
    }

    public Long getLcf() {
        return lcf;
    }

    public Long getMinLcs() {
        return minLcs;
    }

    public Long getMaxLcs() {
        return maxLcs;
    }

    public Long getIncLcs() {
        return incLcs;
    }

    public Long getFirstLcs() {
        return firstLcs;
    }

    public Long getLastLcs() {
        return lastLcs;
    }

    public Long getLcs() {
        return lcs;
    }

    public Long getMinHcs() {
        return minHcs;
    }

    public Long getMaxHcs() {
        return maxHcs;
    }

    public Long getIncHcs() {
        return incHcs;
    }

    public Long getFirstHcs() {
        return firstHcs;
    }

    public Long getLastHcs() {
        return lastHcs;
    }

    public Long getMinYear() {
        return minYear;
    }

    public Long getMaxYear() {
        return maxYear;
    }

    public Long getIncYear() {
        return incYear;
    }

    public Long getFirstYear() {
        return firstYear;
    }

    public Long getLastYear() {
        return lastYear;
    }

    public Long getYear() {
        return year;
    }

    public Long getMinDay() {
        return minDay;
    }

    public Long getMaxDay() {
        return maxDay;
    }

    public Long getIncDay() {
        return incDay;
    }

    public Long getFirstDay() {
        return firstDay;
    }

    public Long getLastDay() {
        return lastDay;
    }

    public Long getDay() {
        return day;
    }

    public Long getMinHour() {
        return minHour;
    }

    public Long getMaxHour() {
        return maxHour;
    }

    public Long getIncHour() {
        return incHour;
    }

    public Long getFirstHour() {
        return firstHour;
    }

    public Long getLastHour() {
        return lastHour;
    }

    public Long getMinMinute() {
        return minMinute;
    }

    public Long getMaxMinute() {
        return maxMinute;
    }

    public Long getIncMinute() {
        return incMinute;
    }

    public Long getFirstMinute() {
        return firstMinute;
    }

    public Long getLastMinute() {
        return lastMinute;
    }

    public Long getMinSec() {
        return minSec;
    }

    public Long getMaxSec() {
        return maxSec;
    }

    public Long getIncSec() {
        return incSec;
    }

    public Long getFirstSec() {
        return firstSec;
    }

    public Long getLastSec() {
        return lastSec;
    }

    public Long getMinGrnors() {
        return minGrnors;
    }

    public Long getMaxGrnors() {
        return maxGrnors;
    }

    public Long getIncGrnors() {
        return incGrnors;
    }

    public Long getFirstGrnors() {
        return firstGrnors;
    }

    public Long getLastGrnors() {
        return lastGrnors;
    }

    public Long getGrnors() {
        return grnors;
    }

    public Long getMinGrnofr() {
        return minGrnofr;
    }

    public Long getMaxGrnofr() {
        return maxGrnofr;
    }

    public Long getIncGrnofr() {
        return incGrnofr;
    }

    public Long getFirstGrnofr() {
        return firstGrnofr;
    }

    public Long getLastGrnofr() {
        return lastGrnofr;
    }

    public Long getGrnofr() {
        return grnofr;
    }

    public Long getMinGaps() {
        return minGaps;
    }

    public Long getMaxGaps() {
        return maxGaps;
    }

    public Long getIncGaps() {
        return incGaps;
    }

    public Long getFirstGaps() {
        return firstGaps;
    }

    public Long getLastGaps() {
        return lastGaps;
    }

    public Long getMinOtrav() {
        return minOtrav;
    }

    public Long getMaxOtrav() {
        return maxOtrav;
    }

    public Long getIncOtrav() {
        return incOtrav;
    }

    public Long getFirstOtrav() {
        return firstOtrav;
    }

    public Long getLastOtrav() {
        return lastOtrav;
    }

    public Long getMinCdpx() {
        return minCdpx;
    }

    public Long getMaxCdpx() {
        return maxCdpx;
    }

    public Long getIncCdpx() {
        return incCdpx;
    }

    public Long getFirstCdpx() {
        return firstCdpx;
    }

    public Long getLastCdpx() {
        return lastCdpx;
    }

    public Long getMinCdpy() {
        return minCdpy;
    }

    public Long getMaxCdpy() {
        return maxCdpy;
    }

    public Long getIncCdpy() {
        return incCdpy;
    }

    public Long getFirstCdpy() {
        return firstCdpy;
    }

    public Long getLastCdpy() {
        return lastCdpy;
    }

    public Long getMinInline() {
        return minInline;
    }

    public Long getMaxInline() {
        return maxInline;
    }

    public Long getIncInline() {
        return incInline;
    }

    public Long getFirstInline() {
        return firstInline;
    }

    public Long getLastInline() {
        return lastInline;
    }

    public Long getMinCrossline() {
        return minCrossline;
    }

    public Long getMaxCrossline() {
        return maxCrossline;
    }

    public Long getIncCrossline() {
        return incCrossline;
    }

    public Long getFirstCrossline() {
        return firstCrossline;
    }

    public Long getLastCrossline() {
        return lastCrossline;
    }

    public Long getMinShotpoint() {
        return minShotpoint;
    }

    public Long getMaxShotpoint() {
        return maxShotpoint;
    }

    public Long getIncShotpoint() {
        return incShotpoint;
    }

    public Long getFirstShotpoint() {
        return firstShotpoint;
    }

    public Long getLastShotpoint() {
        return lastShotpoint;
    }

    public Long getMinScalsp() {
        return minScalsp;
    }

    public Long getMaxScalsp() {
        return maxScalsp;
    }

    public Long getIncScalsp() {
        return incScalsp;
    }

    public Long getFirstScalsp() {
        return firstScalsp;
    }

    public Long getLastScalsp() {
        return lastScalsp;
    }

    public Long getMinTcm() {
        return minTcm;
    }

    public Long getMaxTcm() {
        return maxTcm;
    }

    public Long getIncTcm() {
        return incTcm;
    }

    public Long getFirstTcm() {
        return firstTcm;
    }

    public Long getLastTcm() {
        return lastTcm;
    }

    public Long getMinTid() {
        return minTid;
    }

    public Long getMaxTid() {
        return maxTid;
    }

    public Long getIncTid() {
        return incTid;
    }

    public Long getFirstTid() {
        return firstTid;
    }

    public Long getLastTid() {
        return lastTid;
    }

    public Long getTid() {
        return tid;
    }

    public Long getMinSedm() {
        return minSedm;
    }

    public Long getMaxSedm() {
        return maxSedm;
    }

    public Long getIncSedm() {
        return incSedm;
    }

    public Long getFirstSedm() {
        return firstSedm;
    }

    public Long getLastSedm() {
        return lastSedm;
    }

    public Long getMinSede() {
        return minSede;
    }

    public Long getMaxSede() {
        return maxSede;
    }

    public Long getIncSede() {
        return incSede;
    }

    public Long getFirstSede() {
        return firstSede;
    }

    public Long getLastSede() {
        return lastSede;
    }

    public Long getSede() {
        return sede;
    }

    public Long getMinSmm() {
        return minSmm;
    }

    public Long getMaxSmm() {
        return maxSmm;
    }

    public Long getIncSmm() {
        return incSmm;
    }

    public Long getFirstSmm() {
        return firstSmm;
    }

    public Long getLastSmm() {
        return lastSmm;
    }

    public Long getMinSme() {
        return minSme;
    }

    public Long getMaxSme() {
        return maxSme;
    }

    public Long getIncSme() {
        return incSme;
    }

    public Long getFirstSme() {
        return firstSme;
    }

    public Long getLastSme() {
        return lastSme;
    }

    public Long getMinSmu() {
        return minSmu;
    }

    public Long getMaxSmu() {
        return maxSmu;
    }

    public Long getIncSmu() {
        return incSmu;
    }

    public Long getFirstSmu() {
        return firstSmu;
    }

    public Long getLastSmu() {
        return lastSmu;
    }

    public Long getMinUi1() {
        return minUi1;
    }

    public Long getMaxUi1() {
        return maxUi1;
    }

    public Long getIncUi1() {
        return incUi1;
    }

    public Long getFirstUi1() {
        return firstUi1;
    }

    public Long getLastUi1() {
        return lastUi1;
    }

    public Long getMinUi2() {
        return minUi2;
    }

    public Long getMaxUi2() {
        return maxUi2;
    }

    public Long getIncUi2() {
        return incUi2;
    }

    public Long getFirstUi2() {
        return firstUi2;
    }

    public Long getLastUi2() {
        return lastUi2;
    }

    public Long getUi2() {
        return ui2;
    }

    public Long getMinVer() {
        return minVer;
    }

    public Long getMaxVer() {
        return maxVer;
    }

    public Long getIncVer() {
        return incVer;
    }

    public Long getSampleRate() {
        return sampleRate;
    }

    public Long getRecordLength() {
        return recordLength;
    }

    public Long getUnitVer() {
        return unitVer;
    }

    public void setTotalTraces(Long totalTraces) {
        this.totalTraces = totalTraces;
    }

    public void setMinTracr(Long minTracr) {
        this.minTracr = minTracr;
    }

    public void setMaxTracr(Long maxTracr) {
        this.maxTracr = maxTracr;
    }

    public void setIncTracr(Long incTracr) {
        this.incTracr = incTracr;
    }

    public void setFirstTracr(Long firstTracr) {
        this.firstTracr = firstTracr;
    }

    public void setLastTracr(Long lastTracr) {
        this.lastTracr = lastTracr;
    }

    public void setMinFldr(Long minFldr) {
        this.minFldr = minFldr;
    }

    public void setMaxFldr(Long maxFldr) {
        this.maxFldr = maxFldr;
    }

    public void setIncFldr(Long incFldr) {
        this.incFldr = incFldr;
    }

    public void setFirstFldr(Long firstFldr) {
        this.firstFldr = firstFldr;
    }

    public void setLastFldr(Long lastFldr) {
        this.lastFldr = lastFldr;
    }

    public void setMinTracf(Long minTracf) {
        this.minTracf = minTracf;
    }

    public void setMaxTracf(Long maxTracf) {
        this.maxTracf = maxTracf;
    }

    public void setIncTracf(Long incTracf) {
        this.incTracf = incTracf;
    }

    public void setFirstTracf(Long firstTracf) {
        this.firstTracf = firstTracf;
    }

    public void setLastTracf(Long lastTracf) {
        this.lastTracf = lastTracf;
    }

    public void setMinEp(Long minEp) {
        this.minEp = minEp;
    }

    public void setMaxEp(Long maxEp) {
        this.maxEp = maxEp;
    }

    public void setIncEp(Long incEp) {
        this.incEp = incEp;
    }

    public void setFirstEp(Long firstEp) {
        this.firstEp = firstEp;
    }

    public void setLastEp(Long lastEp) {
        this.lastEp = lastEp;
    }

    public void setMinCdp(Long minCdp) {
        this.minCdp = minCdp;
    }

    public void setMaxCdp(Long maxCdp) {
        this.maxCdp = maxCdp;
    }

    public void setIncCdp(Long incCdp) {
        this.incCdp = incCdp;
    }

    public void setFirstCdp(Long firstCdp) {
        this.firstCdp = firstCdp;
    }

    public void setLastCdp(Long lastCdp) {
        this.lastCdp = lastCdp;
    }

    public void setMinCdpt(Long minCdpt) {
        this.minCdpt = minCdpt;
    }

    public void setMaxCdpt(Long maxCdpt) {
        this.maxCdpt = maxCdpt;
    }

    public void setIncCdpt(Long incCdpt) {
        this.incCdpt = incCdpt;
    }

    public void setFirstCdpt(Long firstCdpt) {
        this.firstCdpt = firstCdpt;
    }

    public void setLastCdpt(Long lastCdpt) {
        this.lastCdpt = lastCdpt;
    }

    public void setMinTrid(Long minTrid) {
        this.minTrid = minTrid;
    }

    public void setMaxTrid(Long maxTrid) {
        this.maxTrid = maxTrid;
    }

    public void setIncTrid(Long incTrid) {
        this.incTrid = incTrid;
    }

    public void setFirstTrid(Long firstTrid) {
        this.firstTrid = firstTrid;
    }

    public void setLastTrid(Long lastTrid) {
        this.lastTrid = lastTrid;
    }

    public void setMinDuse(Long minDuse) {
        this.minDuse = minDuse;
    }

    public void setMaxDuse(Long maxDuse) {
        this.maxDuse = maxDuse;
    }

    public void setIncDuse(Long incDuse) {
        this.incDuse = incDuse;
    }

    public void setFirstDuse(Long firstDuse) {
        this.firstDuse = firstDuse;
    }

    public void setLastDuse(Long lastDuse) {
        this.lastDuse = lastDuse;
    }

    public void setDuse(Long duse) {
        this.duse = duse;
    }

    public void setMinOffset(Long minOffset) {
        this.minOffset = minOffset;
    }

    public void setMaxOffset(Long maxOffset) {
        this.maxOffset = maxOffset;
    }

    public void setIncOffset(Long incOffset) {
        this.incOffset = incOffset;
    }

    public void setFirstOffset(Long firstOffset) {
        this.firstOffset = firstOffset;
    }

    public void setLastOffset(Long lastOffset) {
        this.lastOffset = lastOffset;
    }

    public void setMinGelev(Long minGelev) {
        this.minGelev = minGelev;
    }

    public void setMaxGelev(Long maxGelev) {
        this.maxGelev = maxGelev;
    }

    public void setIncGelev(Long incGelev) {
        this.incGelev = incGelev;
    }

    public void setFirstGelev(Long firstGelev) {
        this.firstGelev = firstGelev;
    }

    public void setLastGelev(Long lastGelev) {
        this.lastGelev = lastGelev;
    }

    public void setMinSelev(Long minSelev) {
        this.minSelev = minSelev;
    }

    public void setMaxSelev(Long maxSelev) {
        this.maxSelev = maxSelev;
    }

    public void setIncSelev(Long incSelev) {
        this.incSelev = incSelev;
    }

    public void setFirstSelev(Long firstSelev) {
        this.firstSelev = firstSelev;
    }

    public void setLastSelev(Long lastSelev) {
        this.lastSelev = lastSelev;
    }

    public void setMinSdepth(Long minSdepth) {
        this.minSdepth = minSdepth;
    }

    public void setMaxSdepth(Long maxSdepth) {
        this.maxSdepth = maxSdepth;
    }

    public void setIncSdepth(Long incSdepth) {
        this.incSdepth = incSdepth;
    }

    public void setFirstSdepth(Long firstSdepth) {
        this.firstSdepth = firstSdepth;
    }

    public void setLastSdepth(Long lastSdepth) {
        this.lastSdepth = lastSdepth;
    }

    public void setMinSwdep(Long minSwdep) {
        this.minSwdep = minSwdep;
    }

    public void setMaxSwdep(Long maxSwdep) {
        this.maxSwdep = maxSwdep;
    }

    public void setIncSwdep(Long incSwdep) {
        this.incSwdep = incSwdep;
    }

    public void setFirstSwdep(Long firstSwdep) {
        this.firstSwdep = firstSwdep;
    }

    public void setLastSwdep(Long lastSwdep) {
        this.lastSwdep = lastSwdep;
    }

    public void setMinGwdep(Long minGwdep) {
        this.minGwdep = minGwdep;
    }

    public void setMaxGwdep(Long maxGwdep) {
        this.maxGwdep = maxGwdep;
    }

    public void setIncGwdep(Long incGwdep) {
        this.incGwdep = incGwdep;
    }

    public void setFirstGwdep(Long firstGwdep) {
        this.firstGwdep = firstGwdep;
    }

    public void setLastGwdep(Long lastGwdep) {
        this.lastGwdep = lastGwdep;
    }

    public void setMinScalel(Long minScalel) {
        this.minScalel = minScalel;
    }

    public void setMaxScalel(Long maxScalel) {
        this.maxScalel = maxScalel;
    }

    public void setIncScalel(Long incScalel) {
        this.incScalel = incScalel;
    }

    public void setFirstScalel(Long firstScalel) {
        this.firstScalel = firstScalel;
    }

    public void setLastScalel(Long lastScalel) {
        this.lastScalel = lastScalel;
    }

    public void setScalel(Long scalel) {
        this.scalel = scalel;
    }

    public void setMinScalco(Long minScalco) {
        this.minScalco = minScalco;
    }

    public void setMaxScalco(Long maxScalco) {
        this.maxScalco = maxScalco;
    }

    public void setIncScalco(Long incScalco) {
        this.incScalco = incScalco;
    }

    public void setFirstScalco(Long firstScalco) {
        this.firstScalco = firstScalco;
    }

    public void setLastScalco(Long lastScalco) {
        this.lastScalco = lastScalco;
    }

    public void setScalco(Long scalco) {
        this.scalco = scalco;
    }

    public void setMinSx(Long minSx) {
        this.minSx = minSx;
    }

    public void setMaxSx(Long maxSx) {
        this.maxSx = maxSx;
    }

    public void setIncSx(Long incSx) {
        this.incSx = incSx;
    }

    public void setFirstSx(Long firstSx) {
        this.firstSx = firstSx;
    }

    public void setLastSx(Long lastSx) {
        this.lastSx = lastSx;
    }

    public void setMinSy(Long minSy) {
        this.minSy = minSy;
    }

    public void setMaxSy(Long maxSy) {
        this.maxSy = maxSy;
    }

    public void setIncSy(Long incSy) {
        this.incSy = incSy;
    }

    public void setFirstSy(Long firstSy) {
        this.firstSy = firstSy;
    }

    public void setLastSy(Long lastSy) {
        this.lastSy = lastSy;
    }

    public void setMinGx(Long minGx) {
        this.minGx = minGx;
    }

    public void setMaxGx(Long maxGx) {
        this.maxGx = maxGx;
    }

    public void setIncGx(Long incGx) {
        this.incGx = incGx;
    }

    public void setFirstGx(Long firstGx) {
        this.firstGx = firstGx;
    }

    public void setLastGx(Long lastGx) {
        this.lastGx = lastGx;
    }

    public void setMinGy(Long minGy) {
        this.minGy = minGy;
    }

    public void setMaxGy(Long maxGy) {
        this.maxGy = maxGy;
    }

    public void setIncGy(Long incGy) {
        this.incGy = incGy;
    }

    public void setFirstGy(Long firstGy) {
        this.firstGy = firstGy;
    }

    public void setLastGy(Long lastGy) {
        this.lastGy = lastGy;
    }

    public void setMinWevel(Long minWevel) {
        this.minWevel = minWevel;
    }

    public void setMaxWevel(Long maxWevel) {
        this.maxWevel = maxWevel;
    }

    public void setIncWevel(Long incWevel) {
        this.incWevel = incWevel;
    }

    public void setFirstWevel(Long firstWevel) {
        this.firstWevel = firstWevel;
    }

    public void setLastWevel(Long lastWevel) {
        this.lastWevel = lastWevel;
    }

    public void setWevel(Long wevel) {
        this.wevel = wevel;
    }

    public void setMinSwevel(Long minSwevel) {
        this.minSwevel = minSwevel;
    }

    public void setMaxSwevel(Long maxSwevel) {
        this.maxSwevel = maxSwevel;
    }

    public void setIncSwevel(Long incSwevel) {
        this.incSwevel = incSwevel;
    }

    public void setFirstSwevel(Long firstSwevel) {
        this.firstSwevel = firstSwevel;
    }

    public void setLastSwevel(Long lastSwevel) {
        this.lastSwevel = lastSwevel;
    }

    public void setSwevel(Long swevel) {
        this.swevel = swevel;
    }

    public void setMinSut(Long minSut) {
        this.minSut = minSut;
    }

    public void setMaxSut(Long maxSut) {
        this.maxSut = maxSut;
    }

    public void setIncSut(Long incSut) {
        this.incSut = incSut;
    }

    public void setFirstSut(Long firstSut) {
        this.firstSut = firstSut;
    }

    public void setLastSut(Long lastSut) {
        this.lastSut = lastSut;
    }

    public void setMinGut(Long minGut) {
        this.minGut = minGut;
    }

    public void setMaxGut(Long maxGut) {
        this.maxGut = maxGut;
    }

    public void setIncGut(Long incGut) {
        this.incGut = incGut;
    }

    public void setFirstGut(Long firstGut) {
        this.firstGut = firstGut;
    }

    public void setLastGut(Long lastGut) {
        this.lastGut = lastGut;
    }

    public void setMinSstat(Long minSstat) {
        this.minSstat = minSstat;
    }

    public void setMaxSstat(Long maxSstat) {
        this.maxSstat = maxSstat;
    }

    public void setIncSstat(Long incSstat) {
        this.incSstat = incSstat;
    }

    public void setFirstSstat(Long firstSstat) {
        this.firstSstat = firstSstat;
    }

    public void setLastSstat(Long lastSstat) {
        this.lastSstat = lastSstat;
    }

    public void setMinGstat(Long minGstat) {
        this.minGstat = minGstat;
    }

    public void setMaxGstat(Long maxGstat) {
        this.maxGstat = maxGstat;
    }

    public void setIncGstat(Long incGstat) {
        this.incGstat = incGstat;
    }

    public void setFirstGstat(Long firstGstat) {
        this.firstGstat = firstGstat;
    }

    public void setLastGstat(Long lastGstat) {
        this.lastGstat = lastGstat;
    }

    public void setMinLagb(Long minLagb) {
        this.minLagb = minLagb;
    }

    public void setMaxLagb(Long maxLagb) {
        this.maxLagb = maxLagb;
    }

    public void setIncLagb(Long incLagb) {
        this.incLagb = incLagb;
    }

    public void setFirstLagb(Long firstLagb) {
        this.firstLagb = firstLagb;
    }

    public void setLastLagb(Long lastLagb) {
        this.lastLagb = lastLagb;
    }

    public void setMinNs(Long minNs) {
        this.minNs = minNs;
    }

    public void setMaxNs(Long maxNs) {
        this.maxNs = maxNs;
    }

    public void setIncNs(Long incNs) {
        this.incNs = incNs;
    }

    public void setFirstNs(Long firstNs) {
        this.firstNs = firstNs;
    }

    public void setLastNs(Long lastNs) {
        this.lastNs = lastNs;
    }

    public void setNs(Long ns) {
        this.ns = ns;
    }

    public void setMinDt(Long minDt) {
        this.minDt = minDt;
    }

    public void setMaxDt(Long maxDt) {
        this.maxDt = maxDt;
    }

    public void setIncDt(Long incDt) {
        this.incDt = incDt;
    }

    public void setFirstDt(Long firstDt) {
        this.firstDt = firstDt;
    }

    public void setLastDt(Long lastDt) {
        this.lastDt = lastDt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public void setMinSfs(Long minSfs) {
        this.minSfs = minSfs;
    }

    public void setMaxSfs(Long maxSfs) {
        this.maxSfs = maxSfs;
    }

    public void setIncSfs(Long incSfs) {
        this.incSfs = incSfs;
    }

    public void setFirstSfs(Long firstSfs) {
        this.firstSfs = firstSfs;
    }

    public void setLastSfs(Long lastSfs) {
        this.lastSfs = lastSfs;
    }

    public void setMinSfe(Long minSfe) {
        this.minSfe = minSfe;
    }

    public void setMaxSfe(Long maxSfe) {
        this.maxSfe = maxSfe;
    }

    public void setIncSfe(Long incSfe) {
        this.incSfe = incSfe;
    }

    public void setFirstSfe(Long firstSfe) {
        this.firstSfe = firstSfe;
    }

    public void setLastSfe(Long lastSfe) {
        this.lastSfe = lastSfe;
    }

    public void setMinSlen(Long minSlen) {
        this.minSlen = minSlen;
    }

    public void setMaxSlen(Long maxSlen) {
        this.maxSlen = maxSlen;
    }

    public void setIncSlen(Long incSlen) {
        this.incSlen = incSlen;
    }

    public void setFirstSlen(Long firstSlen) {
        this.firstSlen = firstSlen;
    }

    public void setLastSlen(Long lastSlen) {
        this.lastSlen = lastSlen;
    }

    public void setSlen(Long slen) {
        this.slen = slen;
    }

    public void setMinStyp(Long minStyp) {
        this.minStyp = minStyp;
    }

    public void setMaxStyp(Long maxStyp) {
        this.maxStyp = maxStyp;
    }

    public void setIncStyp(Long incStyp) {
        this.incStyp = incStyp;
    }

    public void setFirstStyp(Long firstStyp) {
        this.firstStyp = firstStyp;
    }

    public void setLastStyp(Long lastStyp) {
        this.lastStyp = lastStyp;
    }

    public void setMinStas(Long minStas) {
        this.minStas = minStas;
    }

    public void setMaxStas(Long maxStas) {
        this.maxStas = maxStas;
    }

    public void setIncStas(Long incStas) {
        this.incStas = incStas;
    }

    public void setFirstStas(Long firstStas) {
        this.firstStas = firstStas;
    }

    public void setLastStas(Long lastStas) {
        this.lastStas = lastStas;
    }

    public void setMinStae(Long minStae) {
        this.minStae = minStae;
    }

    public void setMaxStae(Long maxStae) {
        this.maxStae = maxStae;
    }

    public void setIncStae(Long incStae) {
        this.incStae = incStae;
    }

    public void setFirstStae(Long firstStae) {
        this.firstStae = firstStae;
    }

    public void setLastStae(Long lastStae) {
        this.lastStae = lastStae;
    }

    public void setStae(Long stae) {
        this.stae = stae;
    }

    public void setMinAfilf(Long minAfilf) {
        this.minAfilf = minAfilf;
    }

    public void setMaxAfilf(Long maxAfilf) {
        this.maxAfilf = maxAfilf;
    }

    public void setIncAfilf(Long incAfilf) {
        this.incAfilf = incAfilf;
    }

    public void setFirstAfilf(Long firstAfilf) {
        this.firstAfilf = firstAfilf;
    }

    public void setLastAfilf(Long lastAfilf) {
        this.lastAfilf = lastAfilf;
    }

    public void setAfilf(Long afilf) {
        this.afilf = afilf;
    }

    public void setMinAfils(Long minAfils) {
        this.minAfils = minAfils;
    }

    public void setMaxAfils(Long maxAfils) {
        this.maxAfils = maxAfils;
    }

    public void setIncAfils(Long incAfils) {
        this.incAfils = incAfils;
    }

    public void setFirstAfils(Long firstAfils) {
        this.firstAfils = firstAfils;
    }

    public void setLastAfils(Long lastAfils) {
        this.lastAfils = lastAfils;
    }

    public void setAfils(Long afils) {
        this.afils = afils;
    }

    public void setMinLcf(Long minLcf) {
        this.minLcf = minLcf;
    }

    public void setMaxLcf(Long maxLcf) {
        this.maxLcf = maxLcf;
    }

    public void setIncLcf(Long incLcf) {
        this.incLcf = incLcf;
    }

    public void setFirstLcf(Long firstLcf) {
        this.firstLcf = firstLcf;
    }

    public void setLastLcf(Long lastLcf) {
        this.lastLcf = lastLcf;
    }

    public void setLcf(Long lcf) {
        this.lcf = lcf;
    }

    public void setMinLcs(Long minLcs) {
        this.minLcs = minLcs;
    }

    public void setMaxLcs(Long maxLcs) {
        this.maxLcs = maxLcs;
    }

    public void setIncLcs(Long incLcs) {
        this.incLcs = incLcs;
    }

    public void setFirstLcs(Long firstLcs) {
        this.firstLcs = firstLcs;
    }

    public void setLastLcs(Long lastLcs) {
        this.lastLcs = lastLcs;
    }

    public void setLcs(Long lcs) {
        this.lcs = lcs;
    }

    public void setMinHcs(Long minHcs) {
        this.minHcs = minHcs;
    }

    public void setMaxHcs(Long maxHcs) {
        this.maxHcs = maxHcs;
    }

    public void setIncHcs(Long incHcs) {
        this.incHcs = incHcs;
    }

    public void setFirstHcs(Long firstHcs) {
        this.firstHcs = firstHcs;
    }

    public void setLastHcs(Long lastHcs) {
        this.lastHcs = lastHcs;
    }

    public void setMinYear(Long minYear) {
        this.minYear = minYear;
    }

    public void setMaxYear(Long maxYear) {
        this.maxYear = maxYear;
    }

    public void setIncYear(Long incYear) {
        this.incYear = incYear;
    }

    public void setFirstYear(Long firstYear) {
        this.firstYear = firstYear;
    }

    public void setLastYear(Long lastYear) {
        this.lastYear = lastYear;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public void setMinDay(Long minDay) {
        this.minDay = minDay;
    }

    public void setMaxDay(Long maxDay) {
        this.maxDay = maxDay;
    }

    public void setIncDay(Long incDay) {
        this.incDay = incDay;
    }

    public void setFirstDay(Long firstDay) {
        this.firstDay = firstDay;
    }

    public void setLastDay(Long lastDay) {
        this.lastDay = lastDay;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public void setMinHour(Long minHour) {
        this.minHour = minHour;
    }

    public void setMaxHour(Long maxHour) {
        this.maxHour = maxHour;
    }

    public void setIncHour(Long incHour) {
        this.incHour = incHour;
    }

    public void setFirstHour(Long firstHour) {
        this.firstHour = firstHour;
    }

    public void setLastHour(Long lastHour) {
        this.lastHour = lastHour;
    }

    public void setMinMinute(Long minMinute) {
        this.minMinute = minMinute;
    }

    public void setMaxMinute(Long maxMinute) {
        this.maxMinute = maxMinute;
    }

    public void setIncMinute(Long incMinute) {
        this.incMinute = incMinute;
    }

    public void setFirstMinute(Long firstMinute) {
        this.firstMinute = firstMinute;
    }

    public void setLastMinute(Long lastMinute) {
        this.lastMinute = lastMinute;
    }

    public void setMinSec(Long minSec) {
        this.minSec = minSec;
    }

    public void setMaxSec(Long maxSec) {
        this.maxSec = maxSec;
    }

    public void setIncSec(Long incSec) {
        this.incSec = incSec;
    }

    public void setFirstSec(Long firstSec) {
        this.firstSec = firstSec;
    }

    public void setLastSec(Long lastSec) {
        this.lastSec = lastSec;
    }

    public void setMinGrnors(Long minGrnors) {
        this.minGrnors = minGrnors;
    }

    public void setMaxGrnors(Long maxGrnors) {
        this.maxGrnors = maxGrnors;
    }

    public void setIncGrnors(Long incGrnors) {
        this.incGrnors = incGrnors;
    }

    public void setFirstGrnors(Long firstGrnors) {
        this.firstGrnors = firstGrnors;
    }

    public void setLastGrnors(Long lastGrnors) {
        this.lastGrnors = lastGrnors;
    }

    public void setgrnors(Long grnors) {
        this.grnors = grnors;
    }

    public void setMinGrnofr(Long minGrnofr) {
        this.minGrnofr = minGrnofr;
    }

    public void setMaxGrnofr(Long maxGrnofr) {
        this.maxGrnofr = maxGrnofr;
    }

    public void setIncGrnofr(Long incGrnofr) {
        this.incGrnofr = incGrnofr;
    }

    public void setFirstGrnofr(Long firstGrnofr) {
        this.firstGrnofr = firstGrnofr;
    }

    public void setLastGrnofr(Long lastGrnofr) {
        this.lastGrnofr = lastGrnofr;
    }

    public void setgrnofr(Long grnofr) {
        this.grnofr = grnofr;
    }

    public void setMinGaps(Long minGaps) {
        this.minGaps = minGaps;
    }

    public void setMaxGaps(Long maxGaps) {
        this.maxGaps = maxGaps;
    }

    public void setIncGaps(Long incGaps) {
        this.incGaps = incGaps;
    }

    public void setFirstGaps(Long firstGaps) {
        this.firstGaps = firstGaps;
    }

    public void setLastGaps(Long lastGaps) {
        this.lastGaps = lastGaps;
    }

    public void setMinOtrav(Long minOtrav) {
        this.minOtrav = minOtrav;
    }

    public void setMaxOtrav(Long maxOtrav) {
        this.maxOtrav = maxOtrav;
    }

    public void setIncOtrav(Long incOtrav) {
        this.incOtrav = incOtrav;
    }

    public void setFirstOtrav(Long firstOtrav) {
        this.firstOtrav = firstOtrav;
    }

    public void setLastOtrav(Long lastOtrav) {
        this.lastOtrav = lastOtrav;
    }

    public void setMinCdpx(Long minCdpx) {
        this.minCdpx = minCdpx;
    }

    public void setMaxCdpx(Long maxCdpx) {
        this.maxCdpx = maxCdpx;
    }

    public void setIncCdpx(Long incCdpx) {
        this.incCdpx = incCdpx;
    }

    public void setFirstCdpx(Long firstCdpx) {
        this.firstCdpx = firstCdpx;
    }

    public void setLastCdpx(Long lastCdpx) {
        this.lastCdpx = lastCdpx;
    }

    public void setMinCdpy(Long minCdpy) {
        this.minCdpy = minCdpy;
    }

    public void setMaxCdpy(Long maxCdpy) {
        this.maxCdpy = maxCdpy;
    }

    public void setIncCdpy(Long incCdpy) {
        this.incCdpy = incCdpy;
    }

    public void setFirstCdpy(Long firstCdpy) {
        this.firstCdpy = firstCdpy;
    }

    public void setLastCdpy(Long lastCdpy) {
        this.lastCdpy = lastCdpy;
    }

    public void setMinInline(Long minInline) {
        this.minInline = minInline;
    }

    public void setMaxInline(Long maxInline) {
        this.maxInline = maxInline;
    }

    public void setIncInline(Long incInline) {
        this.incInline = incInline;
    }

    public void setFirstInline(Long firstInline) {
        this.firstInline = firstInline;
    }

    public void setLastInline(Long lastInline) {
        this.lastInline = lastInline;
    }

    public void setMinCrossline(Long minCrossline) {
        this.minCrossline = minCrossline;
    }

    public void setMaxCrossline(Long maxCrossline) {
        this.maxCrossline = maxCrossline;
    }

    public void setIncCrossline(Long incCrossline) {
        this.incCrossline = incCrossline;
    }

    public void setFirstCrossline(Long firstCrossline) {
        this.firstCrossline = firstCrossline;
    }

    public void setLastCrossline(Long lastCrossline) {
        this.lastCrossline = lastCrossline;
    }

    public void setMinShotpoint(Long minShotpoint) {
        this.minShotpoint = minShotpoint;
    }

    public void setMaxShotpoint(Long maxShotpoint) {
        this.maxShotpoint = maxShotpoint;
    }

    public void setIncShotpoint(Long incShotpoint) {
        this.incShotpoint = incShotpoint;
    }

    public void setFirstShotpoint(Long firstShotpoint) {
        this.firstShotpoint = firstShotpoint;
    }

    public void setLastShotpoint(Long lastShotpoint) {
        this.lastShotpoint = lastShotpoint;
    }

    public void setMinScalsp(Long minScalsp) {
        this.minScalsp = minScalsp;
    }

    public void setMaxScalsp(Long maxScalsp) {
        this.maxScalsp = maxScalsp;
    }

    public void setIncScalsp(Long incScalsp) {
        this.incScalsp = incScalsp;
    }

    public void setFirstScalsp(Long firstScalsp) {
        this.firstScalsp = firstScalsp;
    }

    public void setLastScalsp(Long lastScalsp) {
        this.lastScalsp = lastScalsp;
    }

    public void setMinTcm(Long minTcm) {
        this.minTcm = minTcm;
    }

    public void setMaxTcm(Long maxTcm) {
        this.maxTcm = maxTcm;
    }

    public void setIncTcm(Long incTcm) {
        this.incTcm = incTcm;
    }

    public void setFirstTcm(Long firstTcm) {
        this.firstTcm = firstTcm;
    }

    public void setLastTcm(Long lastTcm) {
        this.lastTcm = lastTcm;
    }

    public void setMinTid(Long minTid) {
        this.minTid = minTid;
    }

    public void setMaxTid(Long maxTid) {
        this.maxTid = maxTid;
    }

    public void setIncTid(Long incTid) {
        this.incTid = incTid;
    }

    public void setFirstTid(Long firstTid) {
        this.firstTid = firstTid;
    }

    public void setLastTid(Long lastTid) {
        this.lastTid = lastTid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public void setMinSedm(Long minSedm) {
        this.minSedm = minSedm;
    }

    public void setMaxSedm(Long maxSedm) {
        this.maxSedm = maxSedm;
    }

    public void setIncSedm(Long incSedm) {
        this.incSedm = incSedm;
    }

    public void setFirstSedm(Long firstSedm) {
        this.firstSedm = firstSedm;
    }

    public void setLastSedm(Long lastSedm) {
        this.lastSedm = lastSedm;
    }

    public void setMinSede(Long minSede) {
        this.minSede = minSede;
    }

    public void setMaxSede(Long maxSede) {
        this.maxSede = maxSede;
    }

    public void setIncSede(Long incSede) {
        this.incSede = incSede;
    }

    public void setFirstSede(Long firstSede) {
        this.firstSede = firstSede;
    }

    public void setLastSede(Long lastSede) {
        this.lastSede = lastSede;
    }

    public void setSede(Long sede) {
        this.sede = sede;
    }

    public void setMinSmm(Long minSmm) {
        this.minSmm = minSmm;
    }

    public void setMaxSmm(Long maxSmm) {
        this.maxSmm = maxSmm;
    }

    public void setIncSmm(Long incSmm) {
        this.incSmm = incSmm;
    }

    public void setFirstSmm(Long firstSmm) {
        this.firstSmm = firstSmm;
    }

    public void setLastSmm(Long lastSmm) {
        this.lastSmm = lastSmm;
    }

    public void setMinSme(Long minSme) {
        this.minSme = minSme;
    }

    public void setMaxSme(Long maxSme) {
        this.maxSme = maxSme;
    }

    public void setIncSme(Long incSme) {
        this.incSme = incSme;
    }

    public void setFirstSme(Long firstSme) {
        this.firstSme = firstSme;
    }

    public void setLastSme(Long lastSme) {
        this.lastSme = lastSme;
    }

    public void setMinSmu(Long minSmu) {
        this.minSmu = minSmu;
    }

    public void setMaxSmu(Long maxSmu) {
        this.maxSmu = maxSmu;
    }

    public void setIncSmu(Long incSmu) {
        this.incSmu = incSmu;
    }

    public void setFirstSmu(Long firstSmu) {
        this.firstSmu = firstSmu;
    }

    public void setLastSmu(Long lastSmu) {
        this.lastSmu = lastSmu;
    }

    public void setMinUi1(Long minUi1) {
        this.minUi1 = minUi1;
    }

    public void setMaxUi1(Long maxUi1) {
        this.maxUi1 = maxUi1;
    }

    public void setIncUi1(Long incUi1) {
        this.incUi1 = incUi1;
    }

    public void setFirstUi1(Long firstUi1) {
        this.firstUi1 = firstUi1;
    }

    public void setLastUi1(Long lastUi1) {
        this.lastUi1 = lastUi1;
    }

    public void setMinUi2(Long minUi2) {
        this.minUi2 = minUi2;
    }

    public void setMaxUi2(Long maxUi2) {
        this.maxUi2 = maxUi2;
    }

    public void setIncUi2(Long incUi2) {
        this.incUi2 = incUi2;
    }

    public void setFirstUi2(Long firstUi2) {
        this.firstUi2 = firstUi2;
    }

    public void setLastUi2(Long lastUi2) {
        this.lastUi2 = lastUi2;
    }

    public void setUi2(Long ui2) {
        this.ui2 = ui2;
    }

    public void setMinVer(Long minVer) {
        this.minVer = minVer;
    }

    public void setMaxVer(Long maxVer) {
        this.maxVer = maxVer;
    }

    public void setIncVer(Long incVer) {
        this.incVer = incVer;
    }

    public void setSampleRate(Long sampleRate) {
        this.sampleRate = sampleRate;
    }

    public void setRecordLength(Long recordLength) {
        this.recordLength = recordLength;
    }

    public void setUnitVer(Long unitVer) {
        this.unitVer = unitVer;
    }

    public void setGrnors(Long grnors) {
        this.grnors = grnors;
    }

    public void setGrnofr(Long grnofr) {
        this.grnofr = grnofr;
    }

    
}
