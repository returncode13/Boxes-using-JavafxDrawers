/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Dot;
import db.model.Workspace;
import java.util.List;
import java.util.Set;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface DotService {
    public void createDot(Dot dot);
    public Dot getDot(Long id);
    public void updateDot(Long id,Dot newDot);
    public void deleteDot(Long id);

    public void clearUnattachedDots(Workspace ws);

    public void updateStatus(Long id, String status);

    public void updateFunction(Dot dbDot);

    public void updateTolerance(Dot dbDot);

    public void updateError(Dot dbDot);

    public List<Dot> getDotsInWorkspace(Workspace dbWorkspace);
}
