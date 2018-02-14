/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Descendant;
import db.model.Job;
import java.util.List;

/**
 *
 * @author sharath nair
 */
public interface DescendantDAO  {
    public void addDescendant(Descendant d);
    public Descendant getDescendant(Long did);
    public void updateDescendant(Long did,Descendant newD);
    public void deleteDescendant(Long did);

    public Descendant getDescendantFor(Job fkid, Long descendant);
    public List<Descendant> getDescendantsFor(Job job);
    public void clearTableForJob(Job dbjob);
    public Descendant getDescendantFor(Job job, Job descendant);
    
}
