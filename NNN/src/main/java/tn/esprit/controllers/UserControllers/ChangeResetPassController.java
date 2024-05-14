package tn.esprit.controllers.UserControllers;

import tn.esprit.services.SessionManager;
import tn.esprit.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

import java.io.IOException;

public class ChangeResetPassController {
    @FXML
    public PasswordField newpass;

    @FXML
    public PasswordField confirmnewpass;

    private UserService userService;

    @FXML
    public void initialize() {
        userService = new UserService();
    }

    @FXML
    public void handleButton() {
        String newPassword = newpass.getText().trim();
        String confirmNewPassword = confirmnewpass.getText().trim();

        if (!newPassword.isEmpty() && !confirmNewPassword.isEmpty()) {
            if (newPassword.equals(confirmNewPassword)) {
                Object resetTokenObject = SessionManager.getSession("resetToken");

                if (resetTokenObject != null) {
                    String resetToken = resetTokenObject.toString();
                    String email = userService.getEmailByResetToken(resetToken);

                    if (email != null) {
                        boolean passwordChanged = userService.changePassword(email, newPassword);

                        if (passwordChanged) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Password changed successfully. You will now be redirected to the login page.");
                            alert.showAndWait();

                            Stage stage = (Stage) newpass.getScene().getWindow();
                            stage.close();


                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("/loginGUI.fxml"));
                                Stage loginStage = new Stage();
                                loginStage.setTitle("Login");
                                loginStage.setScene(new Scene(root));
                                loginStage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Failed to change password. Please try again.");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("No email found for the reset token.");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Reset token session is null. Please try again.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Passwords do not match. Please check and try again.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a new password and confirm it.");
            alert.showAndWait();
        }
    }
}
