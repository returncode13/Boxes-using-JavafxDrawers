/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.services;

import db.dao.UserPreferenceDAO;
import db.dao.UserPreferenceDAOImpl;
import db.model.CommentType;
import db.model.Job;
import db.model.UserPreference;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private UserPreferenceDAO upDAO=new UserPreferenceDAOImpl();
    
    @Override
    public void createUserPreference(UserPreference up) {
        upDAO.createUserPreference(up);
    }

    @Override
    public UserPreference getUserPreference(Long id) {
        return upDAO.getUserPreference(id);
    }

    @Override
    public void updateUserPreference(UserPreference up) {
        upDAO.updateUserPreference(up);
    }

    @Override
    public void deleteUserPreference(Long id) {
        upDAO.deleteUserPreference(id);
    }

    @Override
    public UserPreference getUserPreferenceFor(Job j, CommentType type) {
       return upDAO.getUserPreferenceFor(j, type);
    }

    @Override
    public void deleteAllUserPreferencesFor(Job j) {
        upDAO.deleteAllUserPreferencesFor(j);
    }
    
}
