/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Descendant;
import db.model.Job;

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
    
}
