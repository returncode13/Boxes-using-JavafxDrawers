/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.dao;

import db.model.Workspace;
import db.model.User;
import java.util.List;

/**
 *
 * @author sharath nair
 */
public interface UserDAO {
    public void createUser(User user);
    public void updateUser(Long uid,User newUser);
    public User getUser(Long uid);
    public void deleteUser(Long uid);
    
  // public List<User> getUsersForSession(Sessions sessions);  //get users for current session

    public User getUserWithInitials(String ini);

    public List<User> getUsersInWorkspace(Workspace w);
}
