package tn.esprit.controllers.UserControllers;

import javafx.scene.Node;
import javafx.stage.Modality;
import tn.esprit.controllers.ReclamationControllers.AddRec;
import tn.esprit.controllers.UserControllers.UpdateController;
import tn.esprit.entities.User;
import tn.esprit.services.UserService;
import tn.esprit.services.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ProfileController {

    @FXML
    private Text nameq;

    @FXML
    private Text lastnameq;

    @FXML
    private Text emailq;
    @FXML
    private Text diamonsq;


    @FXML
    private Text numberq;

    @FXML
    private Text birthdayq;
    private Stage stage;
    private UserService userService;

    @FXML
    public void initialize() {
        userService = new UserService();
        fetchUserData();
    }

    private void fetchUserData() {
        // Retrieve user data from UserService
        User user = userService.fetchPlayerData();
        if (user != null) {
            // Populate the UI with user data

            nameq.setText(user.getNom());
            emailq.setText(user.getEmail());
            lastnameq.setText(user.getPrenom());
            diamonsq.setText(String.valueOf(user.getWallet()));
            numberq.setText(String.valueOf(user.getPhone()));
            birthdayq.setText(user.getBirthday().toString());
        } else {
            // Handle case where user data is not found

            nameq.setText("N/A");
            emailq.setText("N/A");
            lastnameq.setText("N/A");
            numberq.setText("N/A");
            birthdayq.setText("N/A");
        }
    }

    @FXML
    void NavigateToUpdate(ActionEvent event) {
        try {
            // Load the EditProfile.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateGUI.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);
            Stage editProfileStage = new Stage();
            editProfileStage.setScene(scene);
            editProfileStage.setTitle("Edit Profile");

            // Pass the stage reference to the EditProfileController
            UpdateController editProfileController = loader.getController();
            editProfileController.setStage(editProfileStage);

            // Close the current dialog
            Stage currentStage = (Stage) nameq.getScene().getWindow();
            currentStage.close();

            // Show the Edit Profile stage
            editProfileStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteaccount() {
        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete your account? This action cannot be undone.");

        // Wait for user's response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed deletion, proceed with deleting the account
            int userId = (int) SessionManager.getSession("userId");
            if (userService.deleteUserAccount(userId)) {
                // Account deleted successfully, logout
                logout();
            } else {
                // Failed to delete account, show error message
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Failed to delete your account. Please try again later.");
                errorAlert.showAndWait();
            }
        }
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    public void navToBadges(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BadgeGui.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Badges");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout() {

        SessionManager.clearSession();


        try {
            // Load the Login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginGUI.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Login");

            // Close the current profile window
            Stage currentStage = (Stage) nameq.getScene().getWindow();
            currentStage.close();

            // Show the login stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigate_reclamation(ActionEvent event) {

        try{
            // Charger le fichier FXML de la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Reclamation/AddRec.fxml"));
            Parent root = loader.load();
            // Obtenir le contrôleur de la deuxième page
            AddRec controller = loader.getController();
            // Obtenir la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();
            // Remplacer la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);

        }catch(IOException e){
            throw new RuntimeException(e);

        }
    }




}
