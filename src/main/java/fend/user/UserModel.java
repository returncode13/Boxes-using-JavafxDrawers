/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.user;

import db.model.User;

/**
 *
 * @author sharath
 */
public class UserModel {
    
    private String intials;
    private Boolean loginSucceeded;
    private User user;

    public String getIntials() {
        return intials;
    }

    public void setIntials(String intials) {
        this.intials = intials;
    }

    public Boolean getLoginSucceeded() {
        return loginSucceeded;
    }

    public void setLoginSucceeded(Boolean loginSucceeded) {
        this.loginSucceeded = loginSucceeded;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
    
}
