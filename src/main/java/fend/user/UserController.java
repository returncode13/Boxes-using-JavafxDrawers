/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fend.user;

import db.model.User;
import db.services.UserService;
import db.services.UserServiceImpl;
import fend.user.newUser.NewUserModel;
import fend.user.newUser.NewUserView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author sharath
 */
public class UserController extends Stage{
    private UserService userService=new UserServiceImpl();
    private UserModel model;
    private UserView view;
    
   @FXML
    private TextField initialTextField;
   
    @FXML
    private Button loginBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button addAUserBtn;

    @FXML
    void addANewUser(ActionEvent event) {
        NewUserModel newUserModel=new NewUserModel();
        NewUserView newUserView=new NewUserView(newUserModel);
    }

    @FXML
    void cancel(ActionEvent event) {
        close();
    }

    @FXML
    void login(ActionEvent event) {
        String ini=initialTextField.getText();
        User user=userService.getUserWithInitials(ini);
        if(user==null){
            System.out.println("fend.user.UserController.login(): Login successful: Login failure. No user with initials "+ini+" found");
            model.setLoginSucceeded(false);
            
        }else{
            System.out.println("fend.user.UserController.login(): Login successful: Logged in as "+user.getFullName()+" with initials: "+user.getInitials());
            model.setIntials(user.getInitials());
            model.setLoginSucceeded(true);
        }
        close();
    }

    void setModel(UserModel userModel) {
        model=userModel;
    }

    void setView(UserView vw) {
        view=vw;
        this.setScene(new Scene(view));
        this.showAndWait();
    }

    
}
