/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import java.util.List;
import db.model.Ancestor;
import db.model.Job;

/**
 *
 * @author sharath nair
 */
public interface AncestorDAO {
    public void addAncestor(Ancestor A);
    public Ancestor getAncestor(Long aid);
    public void updateAncestor(Long aid,Ancestor newAncestor);
    public void deleteAncestor(Long aid);
    
    public Ancestor getAncestorFor(Job fkid,Long ancestor);     //get the entry where sessionsDetails=fkid and where the column ancestor = ancestor
   
   
    
    
}
