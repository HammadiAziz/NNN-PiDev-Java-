package tn.esprit.controllers.UserControllers;

import tn.esprit.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ValidateTokenController {
    private UserService userService;

    @FXML
    public TextField codeTV;

    @FXML
    public void initialize() {
        userService = new UserService();
    }

    @FXML
    public void handleButton() throws IOException {
        String code = codeTV.getText().trim();

        if (!code.isEmpty()) {
            String email = userService.getEmailByResetToken(code);
            if (email != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resetpasswordnewpassword.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) codeTV.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid code. Please check and try again.");
                alert.showAndWait();
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the code.");
            alert.showAndWait();
        }
    }
}
