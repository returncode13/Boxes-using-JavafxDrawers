/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.WorkspaceDAO;
import db.dao.WorkspaceDAOImpl;
import java.util.List;
import db.model.Workspace;

/**
 *
 * @author sharath nair
 */
public class WorkspaceServiceImpl implements WorkspaceService {

    WorkspaceDAO sessDAO=new WorkspaceDAOImpl();
    @Override
    public void createWorkspace(Workspace s) {
        sessDAO.createWorkspace(s);
    }

    @Override
    public Workspace getWorkspace(Long sessionId) {
        return sessDAO.getWorkspace(sessionId);
    }

    @Override
    public void updateWorkspace(Long sessionId, Workspace newSession) {
        sessDAO.updateWorkspace(sessionId, newSession);
    }

    @Override
    public void deleteWorkspace(Long sessionId) {
        sessDAO.deleteWorkspace(sessionId);
    }

    @Override
    public List<Workspace> listWorkspaces() {
        return sessDAO.listWorkspaces();
    }
    
}
