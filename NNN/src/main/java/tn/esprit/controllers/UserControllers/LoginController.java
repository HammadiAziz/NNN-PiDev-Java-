package tn.esprit.controllers.UserControllers;

import tn.esprit.controllers.AfficherShop;
import tn.esprit.controllers.home;
import tn.esprit.services.SessionManager;
import tn.esprit.services.SmsSender;
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
    private SmsSender smsSender;
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

    private UserService userService;

    private Stage stage;
    private Scene scene;


    @FXML
    void login (ActionEvent event){

    }


    @FXML
    public void initialize() {
        userService = new UserService();
        smsSender = new SmsSender();
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
    public void handleReset() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resetpasswordenteremail.fxml"));
            emailLogin.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
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

        loadingindicator.setVisible(true);

        Task<Boolean> loginTask = new Task<>() {
            @Override
            protected Boolean call() {
                UserService userService = new UserService();
                String loginResult = userService.loginUser(email, password);
                return loginResult == null;
            }
        };

        loginTask.setOnSucceeded(e -> {
            boolean loginSuccess = loginTask.getValue();
            loadingindicator.setVisible(false);

            if (loginSuccess) {
                Object roleObj = SessionManager.getSession("role");
                if (roleObj != null) {
                    int role = (int) roleObj;
                    if (role == -1) {
                        // Coach or player, navigate to profile
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
                            Parent root = loader.load();
                            home afficheshop = loader.getController();
                            afficheshop.setStage(new Stage()); // Set the stage for profile controller
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("Profile");
                            stage.show();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else if (role == 1) {
                        // Admin, navigate to admin users page
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/DashboardGUI.fxml"));
                            emailLogin.getScene().setRoot(root);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        // Handle case where role is not defined
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("User role not defined. Please contact the administrator.");
                        alert.show();
                    }
                } else {
                    // Login unsuccessful
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Invalid email or password. Please try again.");
                    alert.show();
                }
            } else {
                // Login unsuccessful due to block
                String blockReason = userService.getBlockReason(email); // Assuming you have a method to fetch block reason
                if (blockReason != null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Your account is blocked. Reason: " + blockReason);
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Invalid email or password. Please try again.");
                    alert.show();
                }
            }
        });

        loginTask.setOnFailed(e -> {
            loadingindicator.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("An error occurred while logging in. Please try again later.");
            alert.show();
        });

        new Thread(loginTask).start();
    }






}

