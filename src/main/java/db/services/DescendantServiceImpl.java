/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.DescendantDAO;
import db.dao.DescendantDAOImpl;

import app.connections.hibernate.HibernateUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import db.model.Descendant;
import db.model.Job;
import db.model.Subsurface;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sharath nair
 */
public class DescendantServiceImpl implements DescendantService{
    DescendantDAO descDao=new DescendantDAOImpl();
    
            

    @Override
    public void addDescendant(Descendant d) {
        descDao.addDescendant(d);
    }

    @Override
    public Descendant getDescendant(Long did) {
        return descDao.getDescendant(did);
    }

    @Override
    public void updateDescendant(Long did, Descendant newD) {
        descDao.updateDescendant(did, newD);
    }

    @Override
    public void deleteDescendant(Long did) {
        descDao.deleteDescendant(did);
    }
    
    @Override
    public void clearTableForJob(Job dbjob) {
        descDao.clearTableForJob(dbjob);
    }
    
 
    @Override
    public List<Descendant> getDescendantsFor(Job job) {
        return descDao.getDescendantsFor(job);
    }

    @Override
    public List<Descendant> getDescendantsForJobContainingSub(Job job, Subsurface sub) {
        List<Descendant> descendantsForJob=descDao.getDescendantsFor(job);
        System.out.println("db.services.DescendantServiceImpl.getDescendantsForJobContainingSub(): number of descendants for job "+job.getNameJobStep()+" size: "+descendantsForJob.size());
        List<Descendant> result=new ArrayList<>();
        for(Descendant desc:descendantsForJob){
            Job descJob=desc.getDescendant();
            System.out.println("db.services.DescendantServiceImpl.getDescendantsForJobContainingSub(): number of subsurfaces in job: "+descJob.getNameJobStep()+" ==> "+descJob.getSubsurfaces().size());
            if(descJob.getSubsurfaces().contains(sub)){
                System.out.println("db.services.DescendantServiceImpl.getDescendantsForJobContainingSub(): adding "+descJob.getNameJobStep());
                result.add(desc);
                        
            }
        }
        return  result;
    }

    @Override
    public Descendant getDescendantFor(Job job, Job descendant) {
        return descDao.getDescendantFor(job, descendant);
    }
    

   

    
    
}
