/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.DoubtStatusDAO;
import db.dao.DoubtStatusDAOImpl;
import db.model.Doubt;
import db.model.DoubtStatus;
import db.model.Workspace;
import java.util.List;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class DoubtStatusServiceImpl implements DoubtStatusService{
    
    DoubtStatusDAO dsDao=new DoubtStatusDAOImpl();
    
    
    @Override
    public void createDoubtStatus(DoubtStatus ds) {
        dsDao.createDoubtStatus(ds);
    }

    @Override
    public DoubtStatus getDoubtStatus(Long id) {
        return dsDao.getDoubtStatus(id);
    }

    @Override
    public void deleteDoubtStatus(Long id) {
        dsDao.deleteDoubtStatus(id);
    }

    @Override
    public void updateDoubtStatus(Long id, DoubtStatus newds) {
        dsDao.updateDoubtStatus(id, newds);
    }

    @Override
    public void createBulkDoubtStatus(List<DoubtStatus> doubtStatuses) {
        dsDao.createBulkDoubtStatus(doubtStatuses);
    }

    @Override
    public void updateBulkDoubtStatus(List<DoubtStatus> doubtStatusToBeUpdated) {
        dsDao.updateBulkDoubtStatus(doubtStatusToBeUpdated);
    }

    @Override
    public void deleteBulkDoubtStatus(List<Long> idsOfDoubtStatusToBeDeleted) {
        dsDao.deleteBulkDoubtStatus(idsOfDoubtStatusToBeDeleted);
    }

    @Override
    public List<DoubtStatus> getDoubtStatusForDoubt(Doubt doubt) {
        return dsDao.getDoubtStatusForDoubt(doubt);
    }

    @Override
    public List<DoubtStatus> getAllDoubtStatusInWorkspace(Workspace W) {
        return dsDao.getAllDoubtStatusInWorkspace(W);
    }
    
}
