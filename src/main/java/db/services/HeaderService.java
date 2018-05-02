/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import java.util.List;
import java.util.Set;
import db.model.Header;
import db.model.Job;
import db.model.Subsurface;
import db.model.Volume;
import db.model.Workspace;

/**
 *
 * @author sharath nair
 */
public interface HeaderService {
    public void createHeader(Header h);
    public void createBulkHeaders(List<Header> headers);
    public Header getHeader(Long hid);
    public void updateHeader(Long hid,Header newH);
    public void deleteHeader(Long hid);
    
    public List<Header> getHeadersFor(Job job);
    public List<Header> getHeadersFor(Volume v);          //returns the list of headers records from the Header table that have their foreign key= v
    //public List<Header> getHeaderFor(Volume v,String subsurface);       //returns the list of headers records from the Header table that have their foreign key= v and subsurface =s
    /*public List<Header> getHeaderFor(Volume v,Subsurface subsurface);       //returns the list of headers records from the Header table that have their foreign key= v and subsurface =s
    public void setHeaderFor(Volume v,List<Header> headers);  //insert into the headers table new records where foreign Key=v
    public void updateHeaderFor(Volume v,List<Header> headers); //update existing header records in the headers table where foreign key =v*/
    public void deleteHeadersFor(Volume v);         //delete headers from teh headers table where foreign key =v;
    public void deleteHeadersFor(Job job);
    //public Set<Volume> getVolumesContaining(String subsurface);       //a convenience function. return the volume associated with the Subsurface=subsurface from the Header Table
    //public Set<Volume> getVolumesContaining(Subsurface subsurface);       //a convenience function. return the volume associated with the Subsurface=subsurface from the Header Table

    public List<Subsurface> getSubsurfacesToBeSummarized(Volume next);

    public Header getHeadersFor(Volume dbvol, Subsurface dbsub, String latestTimestamp);
    public Set<Header> getMultipleInstances(Job job,Subsurface sub);
    public Header getChosenHeaderFor(Job job, Subsurface sub) throws Exception;

    public String getLatestTimeStampFor(Volume dbvol);
    public List<Header> getChosenHeadersForWorkspace(Workspace W);

    public void checkForMultipleSubsurfacesInHeadersForJob(Job dbjob);

    public void setChosenToFalseForConflictingSubs(Subsurface conflictedSub, Job job, Volume volumeToBeExcluded);    //all conflicted except the one selected will have chosen=false and multiple=true;
    
    
    
}
