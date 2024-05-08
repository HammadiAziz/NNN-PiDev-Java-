package edu.esprit.controllers;

import edu.esprit.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.util.ResourceBundle;

public class DoubleAuthentication {


    public static final String ACCOUNT_SID = "ACcf4ca0cdba0661499e054841b9d86b76";
    public static final String AUTH_TOKEN = "a059e35e37eb3b3950129fe7ac274a4f";

    @FXML
    private TextField smsCodeField;

    private String email;
    private String password;
    private String smsCode;

    @FXML
    private ResourceBundle resources;

    @FXML
    private ImageView userImageView;

    private User currentUser;

    @FXML
    void initialize() {

    }






















    public void handleVerifyButton(ActionEvent event) {
    }
}
