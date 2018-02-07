/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.dugex;

import db.model.Acquisition;
import db.model.Job;
import db.model.Sequence;
import db.model.Subsurface;
import db.services.AcquisitionService;
import db.services.AcquisitionServiceImpl;
import db.services.JobService;
import db.services.JobServiceImpl;
import fend.job.job0.JobType0Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import middleware.sequences.SequenceHeaders;
import middleware.sequences.acquisition.AcquisitionSequenceHeaders;
import middleware.sequences.acquisition.AcquisitionSubsurfaceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class AcquisitionLoader {
    private JobType0Model job;
    private JobService jobService=new JobServiceImpl();
    private AcquisitionService acquisitionService=new AcquisitionServiceImpl();
    private ObservableList<SequenceHeaders> sequenceHeaders=FXCollections.observableArrayList();
    private Map<Sequence,List<AcquisitionSubsurfaceHeaders>> lookupmap=new HashMap<>();
    
    public AcquisitionLoader(JobType0Model job){
        this.job=job;
        Job dbjob=jobService.getJob(this.job.getId());
        List<Acquisition> acquisitions=acquisitionService.getEntireAcquistion();
        Set<Subsurface> subsinJob=dbjob.getSubsurfaces();
        System.out.println("middleware.dugex.AcquisitionLoader.<init>(): "+dbjob.getNameJobStep()+" id: "+dbjob.getId());
        for(Subsurface s:subsinJob){
            System.out.println(""+s.getSubsurface());
        }
        
        
        for(Acquisition a:acquisitions){
            System.err.println("Seq: "+a.getSequence().getSequenceno()+" sub: "+a.getSubsurfaceFK().getSubsurface()+" fgsp: "+a.getFgsp());
            AcquisitionSubsurfaceHeaders acqsubhdr=new AcquisitionSubsurfaceHeaders();
            acqsubhdr.setSubsurfaceFK(a.getSubsurfaceFK());
            acqsubhdr.setSequence(a.getSequence());
            acqsubhdr.setSubsurfaceName(a.getSubsurfaceFK().getSubsurface());
            if(!lookupmap.containsKey(a.getSubsurfaceFK().getSequence())){
                    List<AcquisitionSubsurfaceHeaders> subMap=new ArrayList<>();
                    subMap.add(acqsubhdr);
                    lookupmap.put(a.getSubsurfaceFK().getSequence(), subMap);
                }else{
                    lookupmap.get(a.getSubsurfaceFK().getSequence()).add(acqsubhdr);
                }
        }
        
         for (Map.Entry<Sequence, List<AcquisitionSubsurfaceHeaders>> entry : lookupmap.entrySet()) {
            Sequence seq = entry.getKey();
            List<AcquisitionSubsurfaceHeaders>  listOfSubs = entry.getValue();
            AcquisitionSequenceHeaders seqHeader=new AcquisitionSequenceHeaders();
            seqHeader.setSequence(seq);
            seqHeader.setAcquisitionSubsurfaceHeaders(listOfSubs);
            sequenceHeaders.add(seqHeader);
        }
        
        
    }

    public ObservableList<SequenceHeaders> getSequenceHeaders() {
        return sequenceHeaders;
    }
}
