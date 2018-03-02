/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.UserWorkspaceDAO;
import db.dao.UserWorkspaceDAOImpl;
import db.model.User;
import db.model.UserWorkspace;
import db.model.Workspace;

/**
 *
 * @author sharath
 */
public class UserWorkspaceServiceImpl implements UserWorkspaceService{
    
    private UserWorkspaceDAO uwDao=new UserWorkspaceDAOImpl();

    @Override
    public void createUserWorkspace(UserWorkspace userWorkspace) {
         uwDao.createUserWorkspace(userWorkspace);
    }

    @Override
    public UserWorkspace getUserWorkspace(Long id) {
        return uwDao.getUserWorkspace(id);
    }

    @Override
    public void deleteUserWorkspace(Long id) {
        uwDao.deleteUserWorkspace(id);
    }

    @Override
    public void updateUserWorkspace(Long id, UserWorkspace newUserWorkspace) {
        uwDao.updateUserWorkspace(id, newUserWorkspace);
    }

    @Override
    public UserWorkspace getUserWorkspaceFor(User user, Workspace workspace) {
        return uwDao.getUserWorkspaceFor(user, workspace);
    }

    @Override
    public void remove(User u, Workspace w) {
        uwDao.remove(u,w);
    }
    
}
