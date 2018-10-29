/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.model.CommentType;
import db.model.Job;
import db.model.UserPreference;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public interface UserPreferenceService {
    public void createUserPreference(UserPreference up);
    public UserPreference getUserPreference(Long id);
    public void updateUserPreference(UserPreference up);
    public void deleteUserPreference(Long id);
    
    public UserPreference getUserPreferenceFor(Job j,CommentType type);
    public void deleteAllUserPreferencesFor(Job j);
}
