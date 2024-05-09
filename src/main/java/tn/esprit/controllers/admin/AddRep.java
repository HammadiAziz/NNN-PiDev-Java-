package tn.esprit.controllers.admin;


import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.models.Reclamation;
import tn.esprit.models.Reponse;
import tn.esprit.services.RepService;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;

public class AddRep {

    @FXML
    private TextField description_rep;

    @FXML
    private ComboBox<String> statusCombo;


    private final RepService reps = new RepService();

    @FXML
    private TextField idRecRep;
    Reclamation reclamation;


    @FXML
    public void initialize(Reclamation reclamation) {
        this.reclamation = reclamation;
        statusCombo.getItems().addAll("Traite", "Non traite");
        statusCombo.setValue("Non traite");
    }

    @FXML
    void addRep(ActionEvent event) {
        String description = description_rep.getText();

        if (description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer une description");
            alert.showAndWait();
            return;
        }

        Reponse rep = new Reponse();
        rep.setDescription_rep(description);
        rep.setRep_rec_id(reclamation.getId());

        if (statusCombo.getValue().equals("Traite")) {
            try {
                reps.updateReclamationStatus(reclamation.getId(), "Traite");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        reps.add_Rep(rep);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("La réponse a été ajoutée avec succès");
        alert.showAndWait();

        description_rep.clear();
        String body = "Cher " +reps.getusermail(reclamation.getId_user()) + ",\n\n" +
                "votre reclamation a ete bien traitée le " + LocalDateTime.now() + ",\n\n\n\n" +
                " reclamation detaille " + LocalDateTime.now() + ",\n\n" +
                reclamation.getCategorie()+ ".\n\n" +
                reclamation.getObject()+ ".\n\n" +
                reclamation.getDescription()+ ".\n\n" +
                " reponse detaille " + ",\n\n" +
                rep.getDescription_rep()+ ",\n\n\n\n" +
                "Merci de votre confiance.";
        System.out.println(body);
        sendEmail(reps.getusermail(reclamation.getId_user()),"votre reclamation a ete bien traitée",body);
    }

    @FXML
    void listReclamation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/ListRec.fxml"));
            Parent root = loader.load();

            ListRec controller = loader.getController();

            Scene currentScene = ((Node) event.getSource()).getScene();

            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void RegReponse() {

        try {
            OpenAiService openai = new OpenAiService("sk-proj-SKIjyYqiWFECClmoKNDAT3BlbkFJZrGKNP1p6jB0Otp94EPc");

            String prompt = description_rep.getText();

            CompletionRequest request = CompletionRequest.builder()
                    .prompt(prompt)
                    .maxTokens(50)
                    .model("babbage-002")
                    .build();

            CompletionResult response = openai.createCompletion(request);

            description_rep.setText(response.getChoices().get(0).getText().trim());
            System.out.println(response.getChoices());
        } catch (OpenAiHttpException e) {
            System.err.println("Une erreur s'est produite lors de l'appel à l'API OpenAI : " + e.getMessage());
        }


    }
    public void sendEmail(String recipient, String subject, String body) {
        // Paramètres SMTP
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "issmailchouikhi6@gmail.com";
        String password = "xreo usju fqar hvqi";

        // Propriétés de la session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Créer une session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Créer un message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);

            // Ajouter les détails de la réservation dans le corps de l'e-mail
            message.setText(body);

            // Envoyer le message
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
