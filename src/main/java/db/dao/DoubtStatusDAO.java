package db.dao;

import db.model.DoubtStatus;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface DoubtStatusDAO {
    public void createDoubtStatus(DoubtStatus ds);
    public DoubtStatus getDoubtStatus(Long id);
    public void deleteDoubtStatus(Long id);
    public void updateDoubtStatus(Long id,DoubtStatus newds);
}
