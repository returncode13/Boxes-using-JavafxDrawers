/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Job;
import db.model.Sequence;
import db.model.Subsurface;
import java.util.List;
import java.util.Set;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface SubsurfaceService {
     public void createSubsurface(Subsurface sub);
    public Subsurface getSubsurface(Long sid);
    public void deleteSubsurface(Long sid);
    public void updateSubsurface(Long sid, Subsurface newsub);

    public List<Subsurface> getSubsurfaceForSequence(Sequence seq);
    public Subsurface getSubsurfaceObjBysubsurfacename(String dugSubsurface);

    public List<Subsurface> getSubsurfaceList();

    public List<Subsurface> getSubsurfacesPresentInJob(Job parentJob);
    
     public List<Object[]> getSequenceSubsurfaceMap();
     
}
