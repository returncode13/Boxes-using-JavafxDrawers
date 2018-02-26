/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Dot;
import db.model.Workspace;
import javafx.beans.property.StringProperty;

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

    public void updateStatus(Long id, String status);

    public void updateError(Dot dbDot);

    public void updateTolerance(Dot dbDot);

    public void updateFunction(Dot dbDot);
    
}
