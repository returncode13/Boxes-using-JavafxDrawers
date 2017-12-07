/*
 * To change this license header, choose License Header in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.HeaderDAO;
import db.dao.HeaderDAOImpl;
import java.util.List;
import java.util.Set;
import db.model.Header;
import db.model.Job;
import db.model.Subsurface;
import db.model.Volume;

/**
 *
 * @author sharath nair
 */
public class HeaderServiceImpl implements HeaderService{

    HeaderDAO hDao=new HeaderDAOImpl();
    
    @Override
    public void createHeader(Header h) {
       hDao.createHeader(h);
    }

    @Override
    public Header getHeader(Long hid) {
        return hDao.getHeader(hid);
    }

    @Override
    public void updateHeader(Long hid, Header newH) {
        hDao.updateHeader(hid, newH);
    }

    @Override
    public void deleteHeader(Long hid) {
        hDao.deleteHeader(hid);
    }

    @Override
    public List<Header> getHeadersFor(Volume v) {
        return hDao.getHeadersFor(v);
    }
    
    @Override
    public List<Subsurface> getSubsurfacesToBeSummarized(Volume next) {
        return hDao.getSubsurfacesToBeSummarized(next);
    }

    @Override
    public List<Header> getHeadersFor(Job job) {
        return hDao.getHeadersFor(job);
    }

    @Override
    public void deleteHeadersFor(Volume v) {
        hDao.deleteHeadersFor(v);
    }

    @Override
    public void deleteHeadersFor(Job job) {
        hDao.deleteHeadersFor(job);
    }
    
    /*
    @Override
    public void setHeaderFor(Volume v, List<Header> headers) {
    hDao.setHeaderFor(v,headers);
    }
    
    @Override
    public void updateHeaderFor(Volume v, List<Header> headers) {
    hDao.updateHeader(v, headers);
    }*/

    /* @Override
    public void deleteHeadersFor(Volume v) {
    hDao.deleteHeadersFor(v);
    }*/

    /*@Override
    public Set<Volume> getVolumesContaining(String subsurface) {
    System.out.println("sub: "+subsurface);
    return hDao.getVolumesContaining(subsurface);
    }
    
    @Override
    public List<Header> getHeaderFor(Volume v, String subsurface) {
    return hDao.getHeaderFor(v, subsurface);
    }*/
    /*  @Override
    public Set<Volume> getVolumesContaining(Subsurface subsurface) {
    System.out.println("sub: "+subsurface.getSubsurface());
    return hDao.getVolumesContaining(subsurface);
    }
    
    @Override
    public List<Header> getHeaderFor(Volume v, Subsurface subsurface) {
    return hDao.getHeaderFor(v, subsurface);
    }*/
    
    
}
