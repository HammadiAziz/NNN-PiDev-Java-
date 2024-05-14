package tn.esprit.controllers.UserControllers;

import tn.esprit.services.SessionManager;
import tn.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TwoFactorController {
    private Stage stage;


    @FXML
    private TextField codeField;

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    void handleButtonLogin(ActionEvent event) {
        String verificationCode = codeField.getText();
        if (verificationCode.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter the verification code.");
            alert.show();
            return;
        }



        // Compare verification code with the one generated and sent to the user's phone
        UserService userService = new UserService();
        boolean codeMatch = userService.verifyVerificationCode(email, verificationCode);

        if (codeMatch) {
            // Redirect based on user role
            Object roleObj = SessionManager.getSession("role");
            if (roleObj != null) {
                int role = (int) roleObj;
                if (role == -1) {

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateUserGUI.fxml"));
                        Parent root = loader.load();
                        ProfileController profileController = loader.getController();
                        profileController.setStage(new Stage());
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("Profile");
                        stage.show();

                        // Close the current stage
                        stage = (Stage) codeField.getScene().getWindow();
                        stage.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (role == 1) {

                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/DashboardGUI.fxml"));
                        codeField.getScene().setRoot(root);

                        stage = (Stage) codeField.getScene().getWindow();
                        stage.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // Handle case where role is not defined
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("User role not defined. Please contact the administrator.");
                    alert.show();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid verification code. Please try again.");
            alert.show();
        }


    }
}
