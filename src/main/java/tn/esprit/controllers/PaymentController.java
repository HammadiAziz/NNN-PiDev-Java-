package tn.esprit.controllers;

import com.stripe.exception.StripeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.esprit.service.PaymentProcessor;

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
}