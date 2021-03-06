package db.dao;

import db.model.Doubt;
import db.model.DoubtStatus;
import db.model.Workspace;
import java.util.List;

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

    public void createBulkDoubtStatus(List<DoubtStatus> doubtStatuses);

    public void updateBulkDoubtStatus(List<DoubtStatus> doubtStatusToBeUpdated);

    public void deleteBulkDoubtStatus(List<Long> idsOfDoubtStatusToBeDeleted);

    public List<DoubtStatus> getDoubtStatusForDoubt(Doubt doubt);
    public List<DoubtStatus> getAllDoubtStatusInWorkspace(Workspace W);
}
