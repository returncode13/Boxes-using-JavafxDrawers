/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import java.util.Set;
import db.model.Descendant;
import db.model.Job;

/**
 *
 * @author sharath nair
 */
public interface DescendantService {
    public void addDescendant(Descendant d);
    public Descendant getDescendant(Long did);
    public void updateDescendant(Long did,Descendant newD);
    public void deleteDescendant(Long did);
    
    
     //public void getInitialDescendantListFor(SessionDetails fkid,Set<Long> listOfDescendant);    //recursive call to generate descendants from existing User table
    public void makeDescendantTableFor(Job fkid,Set<Job> listOfDescendants); //delete existing user table to replace true descendant entries
    
    
    /*public void getDescendantsFor(SessionDetails fkid,Set<Long>listOfDescendants);         //remember to run the above two first before calling this method
    //listOfDescendants is now the actual descendant list.
    public Descendants getDescendantsFor(SessionDetails fkid,Long descendant);*/
}
