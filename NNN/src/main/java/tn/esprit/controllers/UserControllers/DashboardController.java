package tn.esprit.controllers.UserControllers;

import javafx.scene.Node;
import tn.esprit.controllers.AfficherProduits;
import tn.esprit.controllers.AfficherQuiv;
import tn.esprit.controllers.BackFatma.GetAllEvents;
import tn.esprit.controllers.BackFatma.GetAllGroups;
import tn.esprit.controllers.Quiz.QuizHomecontroller;
import tn.esprit.controllers.ReclamationControllers.Admin.AddRep;
import tn.esprit.controllers.ReclamationControllers.Admin.ListRec;
import tn.esprit.entities.User;
import tn.esprit.services.SessionManager;
import tn.esprit.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class DashboardController {
    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, Integer> iduserC;

    @FXML
    private TableColumn<User, String> firstnameC;

    @FXML
    private TableColumn<User, String> lasnnameC;
    @FXML
    private TableColumn<User, Integer> phoneC;
    @FXML
    private TableColumn<User, Date> birthdayC;
    @FXML
    private TableColumn<User, String> BlockRC;
    @FXML
    private TableColumn<User, String> statusC;

    @FXML
    private TextArea blockreason;

    @FXML
    ComboBox<String> blockCB;

    private UserService userService;
    @FXML
    public void initialize() {
        userService = new UserService();
        populateTableView();

        userService = new UserService();
        ObservableList<String> statutOptions = FXCollections.observableArrayList("active", "blocked");
        blockCB.setItems(statutOptions);


    }

    private void populateTableView() {
        List<User> userList = userService.getAllUsers();
        ObservableList<User> observableList = FXCollections.observableArrayList(userList);
        tableView.setItems(observableList);
        iduserC.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstnameC.setCellValueFactory(new PropertyValueFactory<>("nom"));
        lasnnameC.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        phoneC.setCellValueFactory(new PropertyValueFactory<>("phone"));
        birthdayC.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        statusC.setCellValueFactory(new PropertyValueFactory<>("status"));
        BlockRC.setCellValueFactory(new PropertyValueFactory<>("block_reason"));

    }

    @FXML
    private void blockAction(ActionEvent event) {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            String blockReason = blockreason.getText();
            String status = blockCB.getValue();
            if (blockReason != null && !blockReason.isEmpty() && status != null && !status.isEmpty()) {
                boolean success = userService.updateUserStatus(selectedUser.getId(), status, blockReason);
                if (success) {
                    selectedUser.setStatus(status);
                    selectedUser.setBlock_reason(blockReason);
                    tableView.refresh();
                } else {

                    System.out.println("Failed to update user status.");
                }
            } else {

                System.out.println("Please provide a block reason and select a status.");
            }
        } else {

            System.out.println("Please select a user to block.");
        }
    }

    @FXML
    private void logout() {

        SessionManager.clearSession();


        try {
            // Load the Login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginGUI.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Login");

            // Close the current profile window
            Stage currentStage = (Stage) blockreason.getScene().getWindow();
            currentStage.close();

            // Show the login stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    void gotoevent(ActionEvent event) {



        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFATMA/GetAllEvents.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            GetAllEvents Listcontroller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }




    }


    @FXML
    void gotogroup(ActionEvent event) {

        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFATMA/GetAllGroups.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            GetAllGroups Listcontroller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void gotolivraison(ActionEvent event) {
        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSuiv.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            AfficherQuiv Listcontroller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    @FXML
    void gotomarket(ActionEvent event) {
        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            AfficherProduits Listcontroller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void gotoquiz(ActionEvent event) {
        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/Admin/QuizHome.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            QuizHomecontroller Listcontroller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void gotoreclamation(ActionEvent event) {

        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Reclamation/Admin/ListRec.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            ListRec Listcontroller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void gotouser(ActionEvent event) {
        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardGUI.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            DashboardController Listcontroller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

}
