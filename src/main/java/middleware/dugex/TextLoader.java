/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.dugex;

import db.model.Job;
import db.model.Sequence;
import db.model.Theader;
import db.services.SubsurfaceService;
import db.services.SubsurfaceServiceImpl;
import db.services.TheaderService;
import db.services.TheaderServiceImpl;
import fend.job.job0.JobType0Model;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import middleware.sequences.text.TextSequenceHeader;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class TextLoader {
    private JobType0Model job;
    private SubsurfaceService subsurfaceService=new SubsurfaceServiceImpl();
    private TheaderService theaderService=new TheaderServiceImpl();
    private ObservableList<TextSequenceHeader> textSequenceHeaders=FXCollections.observableArrayList();

    public TextLoader(JobType0Model job) {
        this.job = job;
     }
    
    
    public void retrieveHeaders(){
         Job dbjob=this.job.getDatabaseJob();
         List<Object[]> theaders=theaderService.getTheadersFor(dbjob);  //each entry is seq,textfile,timestamp,md5,noOfRuns,modified,deleted,history 
        
        for(Object[] t:theaders){
            TextSequenceHeader tseq=new TextSequenceHeader();
            tseq.setSequence((Sequence) t[0]);
            
                String nameOfFile=(String) t[1];                                //t[1] is the absolute text name
                nameOfFile=nameOfFile.substring(nameOfFile.lastIndexOf("/")+1);
            
            tseq.setTextFile(nameOfFile);
            tseq.setTimestamp((String) t[2]);
            tseq.setMd5((String) t[3]);
            tseq.setNumberOfRuns((Long) t[4]);
            tseq.setModifiedProperty((Boolean) t[5]);
            tseq.setDeletedProperty((Boolean) t[6]);
            tseq.setHistoryProperty((String) t[7]);
            
            
            textSequenceHeaders.add(tseq);
        }
    }
    public ObservableList<TextSequenceHeader> getTextSequenceHeaders() {
        return textSequenceHeaders;
    }
    
    
    
    
    
    
}
