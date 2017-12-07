/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import java.util.List;
import java.util.Set;
import db.model.Header;
import db.model.Job;
import db.model.Subsurface;
import db.model.Volume;
//import fend.session.node.headers.SubSurfaceHeaders;

/**
 *
 * @author sharath nair
 */
public interface HeaderDAO {
    public void createHeader(Header h);
    public Header getHeader(Long hid);
    public void updateHeader(Long hid,Header newH);
    public void deleteHeader(Long hid);
    
    
    public List<Header> getHeadersFor(Job job);
    public List<Header> getHeadersFor(Volume v);       //returns the list of headers records from the Header table that have their foreign key= v
   // public List<Headers> getHeadersFor(Volume v,String subsurface);       //returns the list of headers records from the Header table that have their foreign key= v and subsurface =s
   // public List<Headers> getHeadersFor(Volume v,Subsurface subsurface);       //returns the list of headers records from the Header table that have their foreign key= v and subsurface =s
   // public void setHeadersFor(Volume v, List<Headers> headers);       //why ? when the first insert takes place the foreign keys are assigned ..??
  //  public void updateHeader(Volume v, List<Headers> headers);         //update existing header records in the headers table where foreign key =v
    public void deleteHeadersFor(Volume v);                         //delete headers from teh headers table where foreign key =v;
    public void deleteHeadersFor(Job job);
    
//public Set<Volume> getVolumesContaining(String subsurface);           //a convenience function. return the volumes associated with the Subsurface=subsurface from the Header Table
   // public Set<Volume> getVolumesContaining(Subsurface subsurface);           //a convenience function. return the volumes associated with the Subsurface=subsurface from the Header Table

    public List<Subsurface> getSubsurfacesToBeSummarized(Volume next);
    
    
}
