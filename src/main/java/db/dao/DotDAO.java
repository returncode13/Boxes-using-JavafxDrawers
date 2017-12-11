/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Dot;
import db.model.Workspace;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface DotDAO {
    public void createDot(Dot dot);
    public Dot getDot(Long id);
    public void updateDot(Long id,Dot newDot);
    public void deleteDot(Long id);

    public void clearUnattachedDots(Workspace ws);
    
}
