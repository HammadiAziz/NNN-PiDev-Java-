package edu.esprit.controllers;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Livreur;
import edu.esprit.entities.User;
import edu.esprit.services.UserService;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegisterController {

    @FXML
    private Label successMessage;
    @FXML
    private ComboBox<String> ComboBoxR;

    @FXML
    private TextField nomR;

    @FXML
    private TextField prenomR;

    @FXML
    private TextField emailR;

    @FXML
    private TextField phoneR;

    @FXML
    private PasswordField passwordR;

    @FXML
    private DatePicker birthdayR;

    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;

    @FXML
    private Button Login;

    @FXML
    private Text nomError;

    @FXML
    private Text prenomError;

    @FXML
    private Text emailError;

    @FXML
    private Text passwordError;
    @FXML
    private Text phoneError;

    @FXML
    private UserService UserService;




    @FXML
    private ProgressIndicator loadingIndicator;



    public RegisterController() {
        this.UserService = new UserService();
    }
    @FXML
    void initialize() {
        ObservableList<String> statutOptions = FXCollections.observableArrayList("User", "Livreur");
        ComboBoxR.setItems(statutOptions);
    }
    private boolean isValidEmail(String email) {
        // Add your email validation logic here
        // For simplicity, I'll use a basic email pattern matching
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    private void handleError(TextField textField, Text errorText, String errorMessage) {
        // Display the error message in the text field
        textField.setStyle("-fx-border-color: red;");
        errorText.setText(errorMessage);

    }
    @FXML
    void handleRegistrationButton(ActionEvent event){
        //clearErrorMessage();
        String nomValue = nomR.getText();
        String prenomValue = prenomR.getText();
        String emailValue = emailR.getText();
        String phoneValue = phoneR.getText();
        int phoneNumber = Integer.parseInt(phoneValue);
        LocalDate dateValue = birthdayR.getValue();
        String passwordValue = passwordR.getText();
       // String selectedRole = roleChoiceBox.getValue();
        String selectedRole = ComboBoxR.getValue();

        if (nomValue.isEmpty()) {
            handleError(nomR, nomError, "Nom is required");
            return;
        }

        if (prenomValue.isEmpty()) {
            handleError(prenomR, prenomError, "Prenom is required");
            return;
        }

        if (emailValue.isEmpty()) {
            handleError(emailR, emailError, "Email is required");
            return;
        } else if (!isValidEmail(emailValue)) {
            handleError(emailR, emailError, "Invalid email address");
            return;
        }

        if (passwordValue.isEmpty()) {
            handleError(passwordR, passwordError, "Password is required");
            return;
        }
        if (phoneValue.isEmpty()) {
            handleError(phoneR, phoneError, "Phone Number is required");
            return;
        }
        if (UserService.emailExists(emailValue)) {
            handleError(emailR, emailError, "Email already exists");
            return;
        }


        try {
if (validateInputs()) {
    User newUser;
    /*switch(selectedRole) {
        case "Livreur":
            newUser = new Livreur();
            break;
        case "Admin":
            newUser= new Admin();
            break;
        default:
            newUser = new User();
            break;
    }*/
    newUser = new Livreur();
    newUser.setNom(nomValue);
    newUser.setPrenom(prenomValue);
    newUser.setEmail(emailValue);
    newUser.setPassword(passwordValue);
    newUser.setPhone(phoneNumber);
    newUser.setBirthday(dateValue);
    newUser.setPhone(phoneNumber);

    List<String> roles = new ArrayList<>();
    roles.add(selectedRole);
    newUser.setRoles(roles);

    boolean state = UserService.registerUser(newUser,emailValue,nomValue,passwordValue);
    if(state){
        ActionEvent actionEvent = new ActionEvent();
        navigateToLoginAction(actionEvent);
    }
}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private boolean validateInputs() {
        // Validation logic, if needed
        return true;
    }

/*private void clearErrorMessage(){
    nomError.setText("");
    prenomError.setText("");
    emailError.setText("");
    passwordError.setText("");

    nomR.setStyle(null);
    prenomR.setStyle(null);
    emailR.setStyle(null);
    passwordR.setStyle(null);
}*/

private void navigateToLoginAction(ActionEvent actionEvent){
    try {
        // Create a PauseTransition with a delay of 5 seconds
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(5));

        // Set the action to be performed after the delay
        pauseTransition.setOnFinished(e -> {
            try {
                // Load the FXML file for the ConnecterUser scene
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginGUI.fxml")));

                // Switch to the ConnecterUser scene
                passwordR.getScene().setRoot(root);
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Sorry");
                alert.setTitle("Error");
                alert.show();
            }
        });

        // Start the PauseTransition
        pauseTransition.play();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    @FXML
    void NavigateToLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/LoginGUI.fxml"));
            registerButton.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }







}
