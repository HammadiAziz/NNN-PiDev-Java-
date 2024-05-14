package tn.esprit.controllers;

import com.stripe.exception.StripeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import tn.esprit.controllers.FrontFatma.ShowAllEvents;
import tn.esprit.controllers.FrontFatma.ShowAllGroupes;
import tn.esprit.controllers.Quiz.Front.QuizList;
import tn.esprit.controllers.UserControllers.ProfileController;
import tn.esprit.entities.User;
import tn.esprit.service.PaymentProcessor;
import tn.esprit.services.SessionManager;
import tn.esprit.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentController implements Initializable {

    @FXML
    private Label total;
    @FXML
    private Button pay_btn;
    @FXML
    private TextField email;
    @FXML
    private TextField cardToken;
    @FXML
    private Spinner<Integer> MM;
    @FXML
    private Spinner<Integer> YY;
    @FXML
    private Spinner<Integer> cvc;

    private float total_pay;
    @FXML
    private TextField client_name;
    @FXML
    private Button back_btn;

    @FXML
    private Button profilename;

    final UserService pf = new UserService();
    private void fetchUserData() {
        // Retrieve user data from UserService
        User user = pf.fetchPlayerData();
        if (user != null) {
            // Populate the UI with user data

            profilename.setText(user.getNom());

        } else {
            // Handle case where user data is not found

            profilename.setText("N/A");

        }
    }
    public void setData() {
        total_pay = 5; // Example value, replace with your actual total
        int mm = LocalDate.now().getMonthValue();
        int yy = LocalDate.now().getYear();

        MM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, mm, 1));
        YY.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2022, 2100, yy, 1));
        cvc.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999, 1, 1));

        total.setText("Total : " + total_pay + " Dt.");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setData();
        int userId = (int) SessionManager.getSession("userId");
        fetchUserData();
    }


    @FXML
    private void payment(ActionEvent event) throws StripeException {
        // Validate input fields
        if (!validateFields()) {
            return;
        }

        String name = client_name.getText();
        String email_txt = email.getText();
        String cardToken = this.cardToken.getText();

        boolean payment_result = PaymentProcessor.processPayment(total_pay, "usd" ,email_txt);
        if (payment_result) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Successful Payment.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Problem", "Payment Failed.");
        }
    }

    private boolean validateFields() {
        if (client_name.getText().isEmpty()) {
            showErrorAlert("You need to input your Name");
            return false;
        }
        if (email.getText().isEmpty() || !isValidEmail(email.getText())) {
            showErrorAlert("Please enter a valid Email address.");
            return false;
        }
        if (cardToken.getText().isEmpty()) {
            showErrorAlert("Please enter a valid card token.");
            return false;
        }
        if (!check_cvc(cvc.getValue())) {
            showErrorAlert("The CVC number should contain three digits");
            return false;
        }
        if (!check_expDate(YY.getValue(), MM.getValue())) {
            showErrorAlert("Please enter a valid expiration date");
            return false;
        }
        return true;
    }

    private boolean check_cvc(int value) {
        return String.valueOf(value).length() == 3;
    }

    private boolean check_expDate(int value_y, int value_mm) {
        LocalDate date = LocalDate.now();
        return (value_y >= date.getYear()) && (value_mm >= date.getMonthValue());
    }

    private boolean isValidEmail(String email) {
        // Trim the input string to remove any leading or trailing whitespace
        email = email.trim();
        // Regular expression pattern to match an email address
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);
        // Match the pattern against the email address
        Matcher matcher = pattern.matcher(email);
        // Return true if the pattern matches, false otherwise
        return matcher.matches();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setTitle("Problem");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    @FXML
    void goToEvents(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontFatma/ShowAllEvents.fxml"));
            Parent root = loader.load();
            ShowAllEvents controller = loader.getController();
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
    public void goToQuiz(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/User/QuizList.fxml"));
            Parent root = loader.load();
            QuizList controller = loader.getController();
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
    void goToGroups(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontFatma/ShowAllGroupes.fxml"));
            Parent root = loader.load();
            ShowAllGroupes controller = loader.getController();
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
    void goToMarket(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherShop.fxml"));
            Parent root = loader.load();
            AfficherShop controller = loader.getController();
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
    void goToLivraison(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLivUser.fxml"));
            Parent root = loader.load();
            AfficherLivUser controller = loader.getController();
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
    void gotoHome(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();
            home controller = loader.getController();
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
    void gotoprofile(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateUserGUI.fxml"));
            Parent root = loader.load();
            ProfileController controller = loader.getController();
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