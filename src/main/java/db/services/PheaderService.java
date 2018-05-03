/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Job;
import db.model.Pheader;
import db.model.Subsurface;
import db.model.Volume;
import db.model.Workspace;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface PheaderService {
    public void createHeader(Pheader h);
    public void createBulkHeaders(List<Pheader> headers);
    public Pheader getHeader(Long hid);
    public void updateHeader(Long hid,Pheader newH);
    public void deleteHeader(Long hid);
    
    
    public List<Pheader> getHeadersFor(Job job);
    public List<Pheader> getHeadersFor(Volume v);       //returns the list of headers records from the Header table that have their foreign key= v
    public void deleteHeadersFor(Volume v);                         //delete headers from teh headers table where foreign key =v;
    public void deleteHeadersFor(Job job);
    
    public List<Subsurface> getSubsurfacesToBeSummarized(Volume next);

    public Pheader getHeadersFor(Volume dbvol, Subsurface dbsub, String timestamp);
    public Set<Pheader> getMultipleInstances(Job job,Subsurface sub);

    public Pheader getChosenHeaderFor(Job job, Subsurface sub) throws Exception;

    public String getLatestTimeStampFor(Volume volume);

    public List<Pheader> getChosenHeadersForWorkspace(Workspace W);
    public void checkForMultipleSubsurfacesInHeadersForJob(Job job);
    public void setChosenToFalseForConflictingSubs(Subsurface conflictedSub, Job job, Volume volumeToBeExcluded);    //all conflicted except the one selected will have chosen=false and multiple=true;

    public void updateDeleteFlagsFor(Volume vol, List<String> subsurfacesOnDisk);    //if header has sub NOT belonging to subsurfacesOnDisk, then header.delete=true
    
}
