/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.job.table.qctable.comment;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
public class CommentStackController extends Stage{
    
    
    
    CommentStackModel model;
    CommentStackView view;
    @FXML
    private TextArea commentTextArea;

    void setModel(CommentStackModel md) {
        commentTextArea.setEditable(false);
        model=md;
        commentTextArea.setText(model.getCommentStack());
    }

    void setView(CommentStackView vw) {
        view=vw;
        this.setTitle("CommentStack for "+model.getTitle());
        this.setScene(new Scene(this.view));
        this.showAndWait();
    }
    
}
