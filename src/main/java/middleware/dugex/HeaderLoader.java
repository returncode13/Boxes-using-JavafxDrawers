/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.dugex;

import db.model.Header;
import db.model.Job;
import db.model.Sequence;
import db.model.Subsurface;
import db.model.Volume;
import db.services.HeaderService;
import db.services.HeaderServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import db.services.VolumeService;
import db.services.VolumeServiceImpl;
import fend.job.job0.JobType0Model;
import fend.volume.volume0.Volume0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import middleware.sequences.SequenceHeaders;
import middleware.sequences.SubsurfaceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class HeaderLoader {
    private JobType0Model job;
    private JobService jobService=new JobServiceImpl();
    private HeaderService headerService=new HeaderServiceImpl();
    private ObservableList<SubsurfaceHeaders> subsurfaceHeaders=FXCollections.observableArrayList();
    private ObservableList<SequenceHeaders> sequenceHeaders=FXCollections.observableArrayList();
    private Map<Sequence,List<SubsurfaceHeaders>> lookupmap=new HashMap<>();
    
    public HeaderLoader(JobType0Model job) {
        this.job = job;
        Job dbjob=jobService.getJob(this.job.getId());
//        List<Header> headersInJob=headerService.getHeadersFor(dbjob);
        Set<Header> headersInJob=dbjob.getHeaders();
        List<Volume0> feVolsInJob=job.getVolumes();
        Set<Subsurface> subsinJob=dbjob.getSubsurfaces();
        System.out.println("middleware.dugex.HeaderLoader.<init>(): Listing all subs in the job.. "+dbjob.getNameJobStep()+" id: "+dbjob.getId());
        for(Subsurface s:subsinJob){
            System.out.println(""+s.getSubsurface());
        }
        Map<Long,Volume0> feVolMap=new HashMap<>();
            for(Volume0 v:feVolsInJob){
                feVolMap.put(v.getId(), v);
            }
            
        for(Header h :headersInJob){
            //headerService.getMultipleInstances(dbjob, h.getSubsurface());
            System.out.println("Seq: "+h.getSubsurface().getSequence().getSequenceno()+" sub: "+h.getSubsurface().getSubsurface()+" id:  "+h.getSubsurface().getId()+" duplicate: "+h.getMultipleInstances()+" chosen: "+h.getChosen() +"  Volume: "+h.getVolume().getNameVolume());
            SubsurfaceHeaders sub=new SubsurfaceHeaders(feVolMap.get(h.getVolume().getId()));
                sub.setId(h.getHeaderId());
                sub.setSubsurface(h.getSubsurface());
                sub.setSubsurfaceName(h.getSubsurface().getSubsurface());
                sub.setTimeStamp(h.getTimeStamp());
                sub.setCmpInc(h.getCmpInc());
                sub.setCmpMax(h.getCmpMax());
                sub.setCmpMin(h.getCmpMin());
                sub.setDugChannelInc(h.getDugChannelInc());
            	sub.setDugChannelMax(h.getDugChannelMax());
                sub.setDugChannelMin(h.getDugChannelMin());
                sub.setDugShotInc(h.getDugShotInc());
                sub.setDugShotMax(h.getDugShotMax());
                sub.setDugShotMin(h.getDugShotMin());
                sub.setInlineInc(h.getInlineInc());
                sub.setInlineMax(h.getInlineMax());
                sub.setInlineMin(h.getInlineMin());
                sub.setOffsetInc(h.getOffsetInc());
                sub.setOffsetMax(h.getOffsetMax());
                sub.setOffsetMin(h.getOffsetMin());
          
                sub.setTraceCount(h.getTraceCount());
                sub.setXlineInc(h.getXlineInc());
                sub.setXlineMax(h.getXlineMax());
                sub.setXlineMin(h.getXlineMin());
                /*sub.setModified(h.getModified());
                sub.setDeleted(h.getDeleted());*/
                sub.setNumberOfRuns(h.getNumberOfRuns());
                sub.setInsight(h.getInsightVersion());
                sub.setWorkflow(h.getWorkflowVersion()+"");
                sub.setUpdateTime(h.getUpdateTime());
                sub.setChosen(h.getChosen());
                sub.setMultiple(h.getMultipleInstances());
                if(!lookupmap.containsKey(h.getSubsurface().getSequence())){
                    List<SubsurfaceHeaders> subMap=new ArrayList<>();
                    subMap.add(sub);
                    lookupmap.put(h.getSubsurface().getSequence(), subMap);
                }else{
                    lookupmap.get(h.getSubsurface().getSequence()).add(sub);
                }
                
            
        }
        for (Map.Entry<Sequence, List<SubsurfaceHeaders>> entry : lookupmap.entrySet()) {
            Sequence seq = entry.getKey();
            List<SubsurfaceHeaders> listOfSubs = entry.getValue();
            SequenceHeaders seqHeader=new SequenceHeaders();
            seqHeader.setSequence(seq);
            seqHeader.setSubsurfaceHeaders(listOfSubs);
            sequenceHeaders.add(seqHeader);
        }
        
        
        
    }

    public ObservableList<SequenceHeaders> getSequenceHeaders() {
        return sequenceHeaders;
    }
    
    
    
    
}
