package tn.esprit.controllers.UserControllers;

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
}
