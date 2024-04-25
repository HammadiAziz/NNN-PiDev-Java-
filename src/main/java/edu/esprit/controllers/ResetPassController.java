package edu.esprit.controllers;


import edu.esprit.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class ResetPassController {

    @FXML
    private TextField emailField;

    private UserService userService;

    @FXML
    public void initialize() {
        userService = new UserService();
    }

    @FXML
    public void handleButton() {
        String email = emailField.getText().trim();

        if (!email.isEmpty()) {
            if (UserService.emailExists(email)) {
                String resetToken = generateResetToken();

                userService.updateResetToken(email, resetToken);
                boolean emailSent = sendEmail(email, resetToken);
                if (emailSent) {
                    // Show success message
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Reset token has been sent to your email.");
                    alert.showAndWait();

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resetpasswordcode.fxml"));
                        Parent root = loader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Show error message
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to send reset token. Please try again later.");
                    alert.showAndWait();
                }
            } else {
                // Show error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Email does not exist.");
                alert.showAndWait();
            }
        } else {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter your email.");
            alert.showAndWait();
        }
    }

    private String generateResetToken() {
        Random random = new Random();
        int token = 100000 + random.nextInt(900000);
        return String.valueOf(token);
    }

    private boolean sendEmail(String recipientEmail, String resetToken) {
        String host = "smtp.office365.com";
        String port = "587";
        String username = "NaturalNerdNetwork@outlook.com";
        String password = "Wicem159";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Specify TLS protocol version
        properties.put("mail.smtp.ssl.ciphersuites", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256"); // Specify compatible cipher suite

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Password Reset Token");
            // Create HTML content for the email body
            String htmlContent = "<html><body style=\"font-family: Arial, sans-serif;\">"
                    + "<p style=\"color: #008000;\">Dear User,</p>"
                    + "<p style=\"color: #008000;\">Your password reset token is: <strong>" + resetToken + "</strong></p>"
                    + "<p style=\"color: #008000;\">Please use this token to reset your password.</p>"
                    + "<p style=\"color: #008000;\">Regards,<br/>Your Application Team</p>"
                    + "</body></html>";

            // Set email content as HTML
            message.setContent(htmlContent, "text/html");

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

}
