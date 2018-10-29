/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.dugex;

import app.properties.AppProperties;
import db.model.Fheader;
import db.model.Job;
import db.model.Sequence;
import db.model.Subsurface;
import db.services.FheaderService;
import db.services.FheaderServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import fend.job.job0.JobType0Model;
import fend.volume.volume0.Volume0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import middleware.sequences.fullHeaders.FullSubsurfaceHeaders;
import middleware.sequences.fullHeaders.FullSequenceHeaders;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class FullHeaderLoader {
    private JobType0Model job;
    private JobService jobService=new JobServiceImpl();
    private FheaderService headerService=new FheaderServiceImpl();
    private ObservableList<FullSubsurfaceHeaders> subsurfaceHeaders=FXCollections.observableArrayList();
    private ObservableList<FullSequenceHeaders> sequenceHeaders=FXCollections.observableArrayList();
    private Map<Sequence,List<FullSubsurfaceHeaders>> lookupmap=new HashMap<>();
     private SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();

    public FullHeaderLoader(JobType0Model job) {
        this.job = job;
    }
    
    
    
    public void retrieveHeaders(){
         Job dbjob=job.getDatabaseJob();
//        List<Header> headersInJob=headerService.getHeadersFor(dbjob);
        //Set<Header> headersInJob=dbjob.getHeaders();
        System.out.println("middleware.dugex.HeaderLoader.retrieveHeaders():"+timeNow()+" fetching headers");
        Set<Fheader> headersInJob=new HashSet<>(headerService.getHeadersFor(dbjob));
        System.out.println("middleware.dugex.HeaderLoader.retrieveHeaders():"+timeNow()+" retrieved "+headersInJob.size()+" headers");
        List<Volume0> feVolsInJob=job.getVolumes();
       // Set<Subsurface> subsinJob=dbjob.getSubsurfaces();
        System.out.println("middleware.dugex.HeaderLoader.retrieveHeaders():"+timeNow()+" fetching subsurfaces");
                Set<Subsurface> subsinJob=new HashSet<>(subsurfaceService.getSubsurfacesPresentInJob(dbjob));
        System.out.println("middleware.dugex.HeaderLoader.retrieveHeaders():"+timeNow()+" retrieved "+subsinJob.size()+" subsurfaces");

        System.out.println("middleware.dugex.HeaderLoader.<init>(): Listing all subs in the job.. "+dbjob.getNameJobStep()+" id: "+dbjob.getId());
        for(Subsurface s:subsinJob){
            System.out.println(""+s.getSubsurface());
        }
        Map<Long,Volume0> feVolMap=new HashMap<>();
            for(Volume0 v:feVolsInJob){
                feVolMap.put(v.getId(), v);
            }
            
        for(Fheader h :headersInJob){
            //headerService.getMultipleInstances(dbjob, h.getSubsurface());
            System.out.println("Seq: "+h.getSubsurface().getSequence().getSequenceno()+" sub: "+h.getSubsurface().getSubsurface()+" id:  "+h.getSubsurface().getId()+" duplicate: "+h.getMultipleInstances()+" chosen: "+h.getChosen() +"  Volume: "+h.getVolume().getNameVolume());
            FullSubsurfaceHeaders sub=new FullSubsurfaceHeaders(feVolMap.get(h.getVolume().getId()));
               /*generated using
            dugio read file=160_P_Overlapping_shot_attenuation.dugio line=696501061101_cable5_gun1 query=time:0 full_headers | durange raw=true output=bash | awk '{split($0,a,"=");print "sub.set"a[1]"(h.get"a[1]"());"}'
            */ 
            sub.setSequence(h.getSubsurface().getSequence());
            sub.setSubsurface(h.getSubsurface());
            sub.setSubsurfaceName(h.getSubsurface().getSubsurface());
            sub.setTotalTraces(h.getTotalTraces());
            sub.setMinTracr(h.getMinTracr());
            sub.setMaxTracr(h.getMaxTracr());
            sub.setIncTracr(h.getIncTracr());
            sub.setFirstTracr(h.getFirstTracr());
            sub.setLastTracr(h.getLastTracr());
            sub.setMinFldr(h.getMinFldr());
            sub.setMaxFldr(h.getMaxFldr());
            sub.setIncFldr(h.getIncFldr());
            sub.setFirstFldr(h.getFirstFldr());
            sub.setLastFldr(h.getLastFldr());
            sub.setMinTracf(h.getMinTracf());
            sub.setMaxTracf(h.getMaxTracf());
            sub.setIncTracf(h.getIncTracf());
            sub.setFirstTracf(h.getFirstTracf());
            sub.setLastTracf(h.getLastTracf());
            sub.setMinEp(h.getMinEp());
            sub.setMaxEp(h.getMaxEp());
            sub.setIncEp(h.getIncEp());
            sub.setFirstEp(h.getFirstEp());
            sub.setLastEp(h.getLastEp());
            sub.setMinCdp(h.getMinCdp());
            sub.setMaxCdp(h.getMaxCdp());
            sub.setIncCdp(h.getIncCdp());
            sub.setFirstCdp(h.getFirstCdp());
            sub.setLastCdp(h.getLastCdp());
            sub.setMinCdpt(h.getMinCdpt());
            sub.setMaxCdpt(h.getMaxCdpt());
            sub.setIncCdpt(h.getIncCdpt());
            sub.setFirstCdpt(h.getFirstCdpt());
            sub.setLastCdpt(h.getLastCdpt());
            sub.setMinTrid(h.getMinTrid());
            sub.setMaxTrid(h.getMaxTrid());
            sub.setIncTrid(h.getIncTrid());
            sub.setFirstTrid(h.getFirstTrid());
            sub.setLastTrid(h.getLastTrid());
            sub.setMinDuse(h.getMinDuse());
            sub.setMaxDuse(h.getMaxDuse());
            sub.setIncDuse(h.getIncDuse());
            sub.setFirstDuse(h.getFirstDuse());
            sub.setLastDuse(h.getLastDuse());
            sub.setDuse(h.getDuse());
            sub.setMinOffset(h.getMinOffset());
            sub.setMaxOffset(h.getMaxOffset());
            sub.setIncOffset(h.getIncOffset());
            sub.setFirstOffset(h.getFirstOffset());
            sub.setLastOffset(h.getLastOffset());
            sub.setMinGelev(h.getMinGelev());
            sub.setMaxGelev(h.getMaxGelev());
            sub.setIncGelev(h.getIncGelev());
            sub.setFirstGelev(h.getFirstGelev());
            sub.setLastGelev(h.getLastGelev());
            sub.setMinSelev(h.getMinSelev());
            sub.setMaxSelev(h.getMaxSelev());
            sub.setIncSelev(h.getIncSelev());
            sub.setFirstSelev(h.getFirstSelev());
            sub.setLastSelev(h.getLastSelev());
            sub.setMinSdepth(h.getMinSdepth());
            sub.setMaxSdepth(h.getMaxSdepth());
            sub.setIncSdepth(h.getIncSdepth());
            sub.setFirstSdepth(h.getFirstSdepth());
            sub.setLastSdepth(h.getLastSdepth());
            sub.setMinSwdep(h.getMinSwdep());
            sub.setMaxSwdep(h.getMaxSwdep());
            sub.setIncSwdep(h.getIncSwdep());
            sub.setFirstSwdep(h.getFirstSwdep());
            sub.setLastSwdep(h.getLastSwdep());
            sub.setMinGwdep(h.getMinGwdep());
            sub.setMaxGwdep(h.getMaxGwdep());
            sub.setIncGwdep(h.getIncGwdep());
            sub.setFirstGwdep(h.getFirstGwdep());
            sub.setLastGwdep(h.getLastGwdep());
            sub.setMinScalel(h.getMinScalel());
            sub.setMaxScalel(h.getMaxScalel());
            sub.setIncScalel(h.getIncScalel());
            sub.setFirstScalel(h.getFirstScalel());
            sub.setLastScalel(h.getLastScalel());
            sub.setScalel(h.getScalel());
            sub.setMinScalco(h.getMinScalco());
            sub.setMaxScalco(h.getMaxScalco());
            sub.setIncScalco(h.getIncScalco());
            sub.setFirstScalco(h.getFirstScalco());
            sub.setLastScalco(h.getLastScalco());
            sub.setScalco(h.getScalco());
            sub.setMinSx(h.getMinSx());
            sub.setMaxSx(h.getMaxSx());
            sub.setIncSx(h.getIncSx());
            sub.setFirstSx(h.getFirstSx());
            sub.setLastSx(h.getLastSx());
            sub.setMinSy(h.getMinSy());
            sub.setMaxSy(h.getMaxSy());
            sub.setIncSy(h.getIncSy());
            sub.setFirstSy(h.getFirstSy());
            sub.setLastSy(h.getLastSy());
            sub.setMinGx(h.getMinGx());
            sub.setMaxGx(h.getMaxGx());
            sub.setIncGx(h.getIncGx());
            sub.setFirstGx(h.getFirstGx());
            sub.setLastGx(h.getLastGx());
            sub.setMinGy(h.getMinGy());
            sub.setMaxGy(h.getMaxGy());
            sub.setIncGy(h.getIncGy());
            sub.setFirstGy(h.getFirstGy());
            sub.setLastGy(h.getLastGy());
            sub.setMinWevel(h.getMinWevel());
            sub.setMaxWevel(h.getMaxWevel());
            sub.setIncWevel(h.getIncWevel());
            sub.setFirstWevel(h.getFirstWevel());
            sub.setLastWevel(h.getLastWevel());
            sub.setWevel(h.getWevel());
            sub.setMinSwevel(h.getMinSwevel());
            sub.setMaxSwevel(h.getMaxSwevel());
            sub.setIncSwevel(h.getIncSwevel());
            sub.setFirstSwevel(h.getFirstSwevel());
            sub.setLastSwevel(h.getLastSwevel());
            sub.setSwevel(h.getSwevel());
            sub.setMinSut(h.getMinSut());
            sub.setMaxSut(h.getMaxSut());
            sub.setIncSut(h.getIncSut());
            sub.setFirstSut(h.getFirstSut());
            sub.setLastSut(h.getLastSut());
            sub.setMinGut(h.getMinGut());
            sub.setMaxGut(h.getMaxGut());
            sub.setIncGut(h.getIncGut());
            sub.setFirstGut(h.getFirstGut());
            sub.setLastGut(h.getLastGut());
            sub.setMinSstat(h.getMinSstat());
            sub.setMaxSstat(h.getMaxSstat());
            sub.setIncSstat(h.getIncSstat());
            sub.setFirstSstat(h.getFirstSstat());
            sub.setLastSstat(h.getLastSstat());
            sub.setMinGstat(h.getMinGstat());
            sub.setMaxGstat(h.getMaxGstat());
            sub.setIncGstat(h.getIncGstat());
            sub.setFirstGstat(h.getFirstGstat());
            sub.setLastGstat(h.getLastGstat());
            sub.setMinLagb(h.getMinLagb());
            sub.setMaxLagb(h.getMaxLagb());
            sub.setIncLagb(h.getIncLagb());
            sub.setFirstLagb(h.getFirstLagb());
            sub.setLastLagb(h.getLastLagb());
            sub.setMinNs(h.getMinNs());
            sub.setMaxNs(h.getMaxNs());
            sub.setIncNs(h.getIncNs());
            sub.setFirstNs(h.getFirstNs());
            sub.setLastNs(h.getLastNs());
            sub.setNs(h.getNs());
            sub.setMinDt(h.getMinDt());
            sub.setMaxDt(h.getMaxDt());
            sub.setIncDt(h.getIncDt());
            sub.setFirstDt(h.getFirstDt());
            sub.setLastDt(h.getLastDt());
            sub.setDt(h.getDt());
            sub.setMinSfs(h.getMinSfs());
            sub.setMaxSfs(h.getMaxSfs());
            sub.setIncSfs(h.getIncSfs());
            sub.setFirstSfs(h.getFirstSfs());
            sub.setLastSfs(h.getLastSfs());
            sub.setMinSfe(h.getMinSfe());
            sub.setMaxSfe(h.getMaxSfe());
            sub.setIncSfe(h.getIncSfe());
            sub.setFirstSfe(h.getFirstSfe());
            sub.setLastSfe(h.getLastSfe());
            sub.setMinSlen(h.getMinSlen());
            sub.setMaxSlen(h.getMaxSlen());
            sub.setIncSlen(h.getIncSlen());
            sub.setFirstSlen(h.getFirstSlen());
            sub.setLastSlen(h.getLastSlen());
            sub.setSlen(h.getSlen());
            sub.setMinStyp(h.getMinStyp());
            sub.setMaxStyp(h.getMaxStyp());
            sub.setIncStyp(h.getIncStyp());
            sub.setFirstStyp(h.getFirstStyp());
            sub.setLastStyp(h.getLastStyp());
            sub.setMinStas(h.getMinStas());
            sub.setMaxStas(h.getMaxStas());
            sub.setIncStas(h.getIncStas());
            sub.setFirstStas(h.getFirstStas());
            sub.setLastStas(h.getLastStas());
            sub.setMinStae(h.getMinStae());
            sub.setMaxStae(h.getMaxStae());
            sub.setIncStae(h.getIncStae());
            sub.setFirstStae(h.getFirstStae());
            sub.setLastStae(h.getLastStae());
            sub.setStae(h.getStae());
            sub.setMinAfilf(h.getMinAfilf());
            sub.setMaxAfilf(h.getMaxAfilf());
            sub.setIncAfilf(h.getIncAfilf());
            sub.setFirstAfilf(h.getFirstAfilf());
            sub.setLastAfilf(h.getLastAfilf());
            sub.setAfilf(h.getAfilf());
            sub.setMinAfils(h.getMinAfils());
            sub.setMaxAfils(h.getMaxAfils());
            sub.setIncAfils(h.getIncAfils());
            sub.setFirstAfils(h.getFirstAfils());
            sub.setLastAfils(h.getLastAfils());
            sub.setAfils(h.getAfils());
            sub.setMinLcf(h.getMinLcf());
            sub.setMaxLcf(h.getMaxLcf());
            sub.setIncLcf(h.getIncLcf());
            sub.setFirstLcf(h.getFirstLcf());
            sub.setLastLcf(h.getLastLcf());
            sub.setLcf(h.getLcf());
            sub.setMinLcs(h.getMinLcs());
            sub.setMaxLcs(h.getMaxLcs());
            sub.setIncLcs(h.getIncLcs());
            sub.setFirstLcs(h.getFirstLcs());
            sub.setLastLcs(h.getLastLcs());
            sub.setLcs(h.getLcs());
            sub.setMinHcs(h.getMinHcs());
            sub.setMaxHcs(h.getMaxHcs());
            sub.setIncHcs(h.getIncHcs());
            sub.setFirstHcs(h.getFirstHcs());
            sub.setLastHcs(h.getLastHcs());
            sub.setMinYear(h.getMinYear());
            sub.setMaxYear(h.getMaxYear());
            sub.setIncYear(h.getIncYear());
            sub.setFirstYear(h.getFirstYear());
            sub.setLastYear(h.getLastYear());
            sub.setYear(h.getYear());
            sub.setMinDay(h.getMinDay());
            sub.setMaxDay(h.getMaxDay());
            sub.setIncDay(h.getIncDay());
            sub.setFirstDay(h.getFirstDay());
            sub.setLastDay(h.getLastDay());
            sub.setDay(h.getDay());
            sub.setMinHour(h.getMinHour());
            sub.setMaxHour(h.getMaxHour());
            sub.setIncHour(h.getIncHour());
            sub.setFirstHour(h.getFirstHour());
            sub.setLastHour(h.getLastHour());
            sub.setMinMinute(h.getMinMinute());
            sub.setMaxMinute(h.getMaxMinute());
            sub.setIncMinute(h.getIncMinute());
            sub.setFirstMinute(h.getFirstMinute());
            sub.setLastMinute(h.getLastMinute());
            sub.setMinSec(h.getMinSec());
            sub.setMaxSec(h.getMaxSec());
            sub.setIncSec(h.getIncSec());
            sub.setFirstSec(h.getFirstSec());
            sub.setLastSec(h.getLastSec());
            sub.setMinGrnors(h.getMinGrnors());
            sub.setMaxGrnors(h.getMaxGrnors());
            sub.setIncGrnors(h.getIncGrnors());
            sub.setFirstGrnors(h.getFirstGrnors());
            sub.setLastGrnors(h.getLastGrnors());
            sub.setGrnors(h.getGrnors());
            sub.setMinGrnofr(h.getMinGrnofr());
            sub.setMaxGrnofr(h.getMaxGrnofr());
            sub.setIncGrnofr(h.getIncGrnofr());
            sub.setFirstGrnofr(h.getFirstGrnofr());
            sub.setLastGrnofr(h.getLastGrnofr());
            sub.setGrnofr(h.getGrnofr());
            sub.setMinGaps(h.getMinGaps());
            sub.setMaxGaps(h.getMaxGaps());
            sub.setIncGaps(h.getIncGaps());
            sub.setFirstGaps(h.getFirstGaps());
            sub.setLastGaps(h.getLastGaps());
            sub.setMinOtrav(h.getMinOtrav());
            sub.setMaxOtrav(h.getMaxOtrav());
            sub.setIncOtrav(h.getIncOtrav());
            sub.setFirstOtrav(h.getFirstOtrav());
            sub.setLastOtrav(h.getLastOtrav());
            sub.setMinCdpx(h.getMinCdpx());
            sub.setMaxCdpx(h.getMaxCdpx());
            sub.setIncCdpx(h.getIncCdpx());
            sub.setFirstCdpx(h.getFirstCdpx());
            sub.setLastCdpx(h.getLastCdpx());
            sub.setMinCdpy(h.getMinCdpy());
            sub.setMaxCdpy(h.getMaxCdpy());
            sub.setIncCdpy(h.getIncCdpy());
            sub.setFirstCdpy(h.getFirstCdpy());
            sub.setLastCdpy(h.getLastCdpy());
            sub.setMinInline(h.getMinInline());
            sub.setMaxInline(h.getMaxInline());
            sub.setIncInline(h.getIncInline());
            sub.setFirstInline(h.getFirstInline());
            sub.setLastInline(h.getLastInline());
            sub.setMinCrossline(h.getMinCrossline());
            sub.setMaxCrossline(h.getMaxCrossline());
            sub.setIncCrossline(h.getIncCrossline());
            sub.setFirstCrossline(h.getFirstCrossline());
            sub.setLastCrossline(h.getLastCrossline());
            sub.setMinShotpoint(h.getMinShotpoint());
            sub.setMaxShotpoint(h.getMaxShotpoint());
            sub.setIncShotpoint(h.getIncShotpoint());
            sub.setFirstShotpoint(h.getFirstShotpoint());
            sub.setLastShotpoint(h.getLastShotpoint());
            sub.setMinScalsp(h.getMinScalsp());
            sub.setMaxScalsp(h.getMaxScalsp());
            sub.setIncScalsp(h.getIncScalsp());
            sub.setFirstScalsp(h.getFirstScalsp());
            sub.setLastScalsp(h.getLastScalsp());
            sub.setMinTcm(h.getMinTcm());
            sub.setMaxTcm(h.getMaxTcm());
            sub.setIncTcm(h.getIncTcm());
            sub.setFirstTcm(h.getFirstTcm());
            sub.setLastTcm(h.getLastTcm());
            sub.setMinTid(h.getMinTid());
            sub.setMaxTid(h.getMaxTid());
            sub.setIncTid(h.getIncTid());
            sub.setFirstTid(h.getFirstTid());
            sub.setLastTid(h.getLastTid());
            sub.setTid(h.getTid());
            sub.setMinSedm(h.getMinSedm());
            sub.setMaxSedm(h.getMaxSedm());
            sub.setIncSedm(h.getIncSedm());
            sub.setFirstSedm(h.getFirstSedm());
            sub.setLastSedm(h.getLastSedm());
            sub.setMinSede(h.getMinSede());
            sub.setMaxSede(h.getMaxSede());
            sub.setIncSede(h.getIncSede());
            sub.setFirstSede(h.getFirstSede());
            sub.setLastSede(h.getLastSede());
            sub.setSede(h.getSede());
            sub.setMinSmm(h.getMinSmm());
            sub.setMaxSmm(h.getMaxSmm());
            sub.setIncSmm(h.getIncSmm());
            sub.setFirstSmm(h.getFirstSmm());
            sub.setLastSmm(h.getLastSmm());
            sub.setMinSme(h.getMinSme());
            sub.setMaxSme(h.getMaxSme());
            sub.setIncSme(h.getIncSme());
            sub.setFirstSme(h.getFirstSme());
            sub.setLastSme(h.getLastSme());
            sub.setMinSmu(h.getMinSmu());
            sub.setMaxSmu(h.getMaxSmu());
            sub.setIncSmu(h.getIncSmu());
            sub.setFirstSmu(h.getFirstSmu());
            sub.setLastSmu(h.getLastSmu());
            sub.setMinUi1(h.getMinUi1());
            sub.setMaxUi1(h.getMaxUi1());
            sub.setIncUi1(h.getIncUi1());
            sub.setFirstUi1(h.getFirstUi1());
            sub.setLastUi1(h.getLastUi1());
            sub.setMinUi2(h.getMinUi2());
            sub.setMaxUi2(h.getMaxUi2());
            sub.setIncUi2(h.getIncUi2());
            sub.setFirstUi2(h.getFirstUi2());
            sub.setLastUi2(h.getLastUi2());
            sub.setUi2(h.getUi2());
            sub.setMinVer(h.getMinVer());
            sub.setMaxVer(h.getMaxVer());
            sub.setIncVer(h.getIncVer());
            sub.setSampleRate(h.getSampleRate());
            sub.setRecordLength(h.getRecordLength());
            sub.setUnitVer(h.getUnitVer());

            
            
                if(!lookupmap.containsKey(h.getSubsurface().getSequence())){
                    List<FullSubsurfaceHeaders> subMap=new ArrayList<>();
                    subMap.add(sub);
                    lookupmap.put(h.getSubsurface().getSequence(), subMap);
                }else{
                    lookupmap.get(h.getSubsurface().getSequence()).add(sub);
                }
                
            
        }
        for (Map.Entry<Sequence, List<FullSubsurfaceHeaders>> entry : lookupmap.entrySet()) {
            Sequence seq = entry.getKey();
            List<FullSubsurfaceHeaders> listOfSubs = entry.getValue();
            FullSequenceHeaders seqHeader=new FullSequenceHeaders();
            seqHeader.setSequence(seq);
            seqHeader.setSubsurfaceHeaders(listOfSubs);
            sequenceHeaders.add(seqHeader);
        }
        
        
    }
    
    private String timeNow(){
        return DateTime.now(DateTimeZone.UTC).toString(AppProperties.TIMESTAMP_FORMAT);
    }
    
     
    public ObservableList<FullSequenceHeaders> getFullSequenceHeaders() {
        return sequenceHeaders;
    }
    
}
