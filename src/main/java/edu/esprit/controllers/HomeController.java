package edu.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import edu.esprit.controllers.ProfileController;
import edu.esprit.entities.User;
import edu.esprit.services.UserService;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;

public class HomeController {

    @FXML
    private Text nomq;

    @FXML
    private ImageView img;

    private Stage stage;
    private UserService userService;

    @FXML
    public void initialize() {
        userService = new UserService();
        fetchUserData();
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void fetchUserData() {
        User user = userService.fetchPlayerData();
        if (user != null) {
            nomq.setText(user.getNom());

            String photoPath = user.getPhoto();
            if (photoPath != null && !photoPath.isEmpty()) {
                try {
                    Image image = new Image(new File(photoPath).toURI().toString());
                    img.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            nomq.setText("N/A");
        }
    }


    public void NavToProfile(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateUserGUI.fxml"));
            Parent root = loader.load();
            ProfileController profileController = loader.getController();
            profileController.setStage(new Stage()); // Set the stage for profile controller
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Profile");
            stage.show();
            Stage currentStage = (Stage) nomq.getScene().getWindow();
            currentStage.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}

