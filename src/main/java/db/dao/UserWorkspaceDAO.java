/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.User;
import db.model.UserWorkspace;
import db.model.Workspace;

/**
 *
 * @author sharath.nair <sharath.nair@polarcus.com>
 */
public interface UserWorkspaceDAO {
    public void createUserWorkspace(UserWorkspace userWorkspace);
    public UserWorkspace getUserWorkspace(Long id);
    public void deleteUserWorkspace(Long id);
    public void updateUserWorkspace(Long id,UserWorkspace newUserWorkspace);
    public UserWorkspace getUserWorkspaceFor(User user,Workspace workspace);

    public void remove(User u, Workspace w);
}
