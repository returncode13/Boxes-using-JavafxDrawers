/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Fheader;
import db.model.Job;
import db.model.Volume;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface FheaderService {
     public void createHeader(Fheader h);
    public void createBulkHeaders(List<Fheader> headers);
    public Fheader getHeader(Long hid);
    public void updateHeader(Long hid,Fheader newH);
    public void deleteHeader(Long hid);
    
    
    public List<Fheader> getHeadersFor(Job job);
    public List<Fheader> getHeadersFor(Volume v);       //returns the list of headers records from the Header table that have their foreign key= v
    public void deleteHeadersFor(Volume v);                         //delete headers from teh headers table where foreign key =v;
    public void deleteHeadersFor(Job job);

    public void updateDeleteFlagsFor(Volume dbvol, List<String> subsurfacesOnDisk);

    public void checkForMultipleSubsurfacesInHeadersForJob(Job dbjob);

    public String getLatestTimeStampFor(Volume dbvol);
}
