/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.dugex;

import db.model.Header;
import db.model.Job;
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
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class HeaderLoader {
    private JobType0Model job;
    private JobService jobService=new JobServiceImpl();
    private HeaderService headerService=new HeaderServiceImpl();
    
    public HeaderLoader(JobType0Model job) {
        this.job = job;
        Job dbjob=jobService.getJob(this.job.getId());
        List<Header> headersInJob=headerService.getHeadersFor(dbjob);
        for(Header h :headersInJob){
            System.out.println("Seq: "+h.getSubsurface().getSequence().getSequenceno()+" sub: "+h.getSubsurface().getSubsurface()+" id:  "+h.getSubsurface().getId()+"duplicate: "+h.getMultipleInstances()+" chosen: "+h.getChosen());
        }
        
        
    }
    
    
    
}
