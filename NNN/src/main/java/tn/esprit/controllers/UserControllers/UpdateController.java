package tn.esprit.controllers.UserControllers;

import tn.esprit.entities.User;
import tn.esprit.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateController {



    @FXML
    private TextField nomq;

    @FXML
    private TextField prenomq;

    @FXML
    private Text errorText;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private UserService userService;

    @FXML
    void initialize() {
        userService = new UserService();
        loadUserData();

    }
    private void loadUserData() {
        User user = userService.fetchPlayerData();
        if (user != null) {
            nomq.setText(user.getNom());
            prenomq.setText(user.getPrenom());


        } else {
            errorText.setText("Failed to load user data");
        }
    }

    @FXML
    public void handleUpdateButton(ActionEvent event) {
        String nom = nomq.getText();
        String prenom = prenomq.getText();
        if (nom.isEmpty() || prenom.isEmpty()  ) {
            errorText.setText("Please fill in all fields");
            return;
        }
        User updatedUser = new User( nom, prenom);
        boolean success = userService.editUserInfo(updatedUser);
        if (success) {
            stage.close();
            showSuccessDialog();
            // Navigate back to profile page
            navigateToProfilePage();
        } else {
            errorText.setText("Failed to update profile");
        }

    }
    private void navigateToProfilePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/profileGUI.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);
            Stage profileStage = new Stage();
            profileStage.setScene(scene);
            profileStage.setTitle("User Profile");

            // Close the current stage
            stage.close();

            // Show the profile stage
            profileStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Profile updated successfully");
        alert.showAndWait();
    }

}
