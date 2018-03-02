/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import java.util.List;
import db.model.Ancestor;
import db.model.Job;
import db.model.Subsurface;

/**
 *
 * @author sharath nair
 */
public interface AncestorDAO {
    public void addAncestor(Ancestor A);
    public Ancestor getAncestor(Long aid);
    public void updateAncestor(Long aid,Ancestor newAncestor);
    public void deleteAncestor(Long aid);
    
   public List<Ancestor> getAncestorFor(Job fkid);

    public void clearTableForJob(Job dbjob);
    public Ancestor getAncestorFor(Job job,Job ancestor); 
   public List<Ancestor> getAncestorsForJobContainingSubsurface(Job job, Subsurface sub);
    
    
}
