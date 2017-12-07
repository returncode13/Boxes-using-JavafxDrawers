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
    public void makeAncestorTableFor(Job fkid,Set<Job> listOfAncestor);  //delete existing user table to replace true descendant entries
    
    /* public void getAncestorFor(Job fkid,Set<Long>listOfAncestor); //remember to run the above two first before calling this method
    public Ancestor getAncestorFor(Job fkid,Long ancestor);     //get the entry where sessionsDetails=fkid and where the column ancestor = ancestor*/
}
