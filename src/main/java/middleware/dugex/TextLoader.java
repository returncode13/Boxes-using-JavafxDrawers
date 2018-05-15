/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.dugex;

import db.model.Job;
import db.model.Theader;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import db.services.TheaderService;
import db.services.TheaderServiceImpl;
import fend.job.job0.JobType0Model;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import middleware.sequences.text.TextSequenceHeaders;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class TextLoader {
    private JobType0Model job;
    private SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();
    private TheaderService theaderService=new TheaderServiceImpl();
    private ObservableList<TextSequenceHeaders> textSequenceHeaders=FXCollections.observableArrayList();

    public TextLoader(JobType0Model job) {
        this.job = job;
        Job dbjob=this.job.getDatabaseJob();
        List<Theader> theaders=theaderService.getTheadersFor(dbjob);
        
        for(Theader t:theaders){
            TextSequenceHeaders tseq=new TextSequenceHeaders();
            tseq.setSequence(t.getSequence());
            tseq.setTextFile(t.getTextFile());
            tseq.setTimestamp(t.getTimeStamp());
            tseq.setModifiedProperty(t.getModified());
            tseq.setDeletedProperty(t.getDeleted());
            tseq.setNumberOfRuns(t.getNumberOfRuns());
            tseq.setMd5(t.getMd5());
            textSequenceHeaders.add(tseq);
        }
        
    }

    public ObservableList<TextSequenceHeaders> getTextSequenceHeaders() {
        return textSequenceHeaders;
    }
    
    
    
    
    
    
}
