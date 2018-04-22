/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import java.util.List;
import java.util.Set;
import db.model.Ancestor;
import db.model.Job;
import db.model.Subsurface;
import db.model.Workspace;

/**
 *
 * @author sharath nair
 */
public interface AncestorService {
    public void addAncestor(Ancestor A);
    public Ancestor getAncestor(Long aid);
    public void updateAncestor(Long aid,Ancestor newAncestor);
    public void deleteAncestor(Long aid);
    
    
   // public void getInitialAncestorListFor(Job fkid,Set<Long> listOfAncestor);  //recursive call to generate descendants from existing User table
   // public void makeAncestorTableFor(Job fkid,Set<Job> listOfAncestor);  //delete existing user table to replace true descendant entries
    
    /* public void getAncestorFor(Job fkid,Set<Long>listOfAncestor); //remember to run the above two first before calling this method
    public Ancestor getAncestorFor(Job fkid,Long ancestor);     //get the entry where sessionsDetails=fkid and where the column ancestor = ancestor*/

    public void clearTableForJob(Job dbjob);
    public Ancestor getAncestorFor(Job job,Job ancestor); 
    public List<Ancestor> getAncestorFor(Job job);
    public List<Ancestor> getAncestorsForJobContainingSub(Job job, Subsurface sub);
    public List<Object[]> getAncestorsSubsurfaceJobsForSummary(Workspace W);// Return all ancestors that contain the same subsurface as the job
}
