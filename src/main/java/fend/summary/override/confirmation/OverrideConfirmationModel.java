/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.summary.override.confirmation;

import fend.summary.override.OverrideModel;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class OverrideConfirmationModel {
    private OverrideModel overrideModel;
    private String comment; //user comment to be added to overrideModel.addToUserCommentStack() before commiting to the database

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        this.overrideModel.addToUserCommentStack(comment);
    }

    public OverrideConfirmationModel(OverrideModel overrideModel) {
        this.overrideModel = overrideModel;
    }

    public OverrideModel getOverrideModel() {
        return overrideModel;
    }
    
    
    
}
