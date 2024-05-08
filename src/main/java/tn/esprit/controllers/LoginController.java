package tn.esprit.controllers;

import tn.esprit.services.UserService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button registerButton;
    @FXML
    private VBox MainLeftSidebar;

    @FXML
    private TextField emailLogin;

    @FXML
    private PasswordField passwordLogin;
    @FXML
    private ProgressIndicator loadingindicator;
    private Stage stage;
    private Scene scene;


@FXML
void login (ActionEvent event){



}


    @FXML
    void handleRegisterButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/SignUpGUI.fxml"));
            emailLogin.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }




    @FXML
    public void signup(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/SignUpGUI.fxml"));
            stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @FXML
    void handleLoginButton(ActionEvent event) {
        String email = emailLogin.getText();
        String password = passwordLogin.getText();

        if (email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter both email and password.");
            alert.show();
            return;
        }

        loadingindicator.setVisible(true); // Show loading indicator while logging in

        Task<Boolean> loginTask = new Task<>() {
            @Override
            protected Boolean call() {
                UserService userService = new UserService();
                return userService.loginUser(email, password);
            }
        };

        loginTask.setOnSucceeded(e -> {
            boolean loginSuccess = loginTask.getValue();
            loadingindicator.setVisible(false); // Hide loading indicator after login attempt

            if (loginSuccess) {
                // Redirect to main application window upon successful login
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/AfficherShop.fxml"));
                    emailLogin.getScene().setRoot(root);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // Handle error if unable to load main application window
                }
                
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid email or password. Please try again.");
                alert.show();
            }
        });

        loginTask.setOnFailed(e -> {
            loadingindicator.setVisible(false); // Hide loading indicator if login task fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("An error occurred while logging in. Please try again later.");
            alert.show();
        });

        new Thread(loginTask).start(); // Start the login task in a separate thread
    }








}
