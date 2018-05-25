/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.user.newUser;

import db.model.User;
import db.services.UserServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import db.services.UserService;
import javafx.scene.control.TextField;

/**
 *
 * @author sharath
 */
public class NewUserController extends Stage{
    private UserService userService= new UserServiceImpl();
    NewUserModel model;
    NewUserView view;
    
    @FXML
    private TextField fullnameTextField;

    @FXML
    private TextField initialsTextField;

    @FXML
    private Button addBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void addNewUser(ActionEvent event) {
        String fname=fullnameTextField.getText();
        String ini=initialsTextField.getText();
        User user=userService.getUserWithInitials(ini);
        if(user==null) {
        user=new User();
        user.setFullName(fname);
        user.setInitials(ini);
        userService.createUser(user);
        }
        else{
            System.out.println("fend.user.newUser.NewUserController.addNewUser(): Error: User "+user.getFullName()+" with initials "+user.getInitials()+" present in the system id: "+user.getId());
        }
        close();
    }

    @FXML
    void cancel(ActionEvent event) {
        close();
    }

    void setModel(NewUserModel newUserModel) {
        model=newUserModel;
        String fname=fullnameTextField.getText();
        String ini=initialsTextField.getText();
        model.setFullname(fname);
        model.setInitials(ini);
    }

    void setView(NewUserView vw) {
        view=vw;
        this.setScene(new Scene(view));
        this.showAndWait();
    }

}
