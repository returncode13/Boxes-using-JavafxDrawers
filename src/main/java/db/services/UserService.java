/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;


import db.model.User;


/**
 *
 * @author sharath nair
 */
public interface UserService {
     public void createUser(User user);
    public void updateUser(Long uid,User newUser);
    public User getUser(Long uid);
    public void deleteUser(Long uid);
    
  
}
