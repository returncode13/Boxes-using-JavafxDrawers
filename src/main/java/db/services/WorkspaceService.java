/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.Job;
import db.model.User;
import java.util.List;
import db.model.Workspace;
import java.util.Set;

/**
 *
 * @author sharath nair
 */
public interface WorkspaceService {
    public void createWorkspace(Workspace s);
    public Workspace getWorkspace(Long sessionId);
    public void updateWorkspace(Long sessionId,Workspace newSession);
    public void deleteWorkspace(Long sessionId);
    
    public List<Workspace> listWorkspaces();

    public List<Workspace> getWorkspacesForUser(User u);

    
   
}
