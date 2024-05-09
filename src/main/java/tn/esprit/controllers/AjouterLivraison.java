package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.esprit.entities.Livraison;
import tn.esprit.services.LivraisonService;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterLivraison {
    @FXML
    private ComboBox<String> PaiementCom;

    @FXML
    private TextField adresseText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField nomText;

    @FXML
    private TextField phoneText;

    @FXML
    private TextField prenomText;

    @FXML
    private TextField stateText;

    @FXML
    private Button navigateAjoutbtn;

    private final LivraisonService ls = new LivraisonService();
    @FXML
    private Button btnTelechargerPDF;

    @FXML
    void initialize() {
        // Création d'une liste d'options de paiement
        ObservableList<String> optionsPaiement = FXCollections.observableArrayList(
                "especes",
                "Stripe"
        );

        // Ajout des options de paiement au ComboBox
        PaiementCom.setItems(optionsPaiement);
    }
    private void genererPDF(Livraison livraison) {
        try {
            // Création du document PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Contenu du PDF
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLineAtOffset(50, 700);

            // Ajout des données de la livraison dans le PDF
            contentStream.showText("Nom: " + livraison.getNomC());
            contentStream.newLine();
            contentStream.showText("Prénom: " + livraison.getPrenomC());
            contentStream.newLine();
            contentStream.showText("State: " + livraison.getState());
            contentStream.newLine();
            contentStream.showText("Adresse: " + livraison.getAdress());
            contentStream.newLine();
            contentStream.showText("Email: " + livraison.getEmail());
            contentStream.newLine();
            contentStream.showText("numero de telephone: " + livraison.getPhoneNumber());
            contentStream.newLine();
            contentStream.showText("Type de paiement: " + livraison.getPaiementType());
            contentStream.newLine();
            // Ajoutez les autres champs de la livraison ici...

            contentStream.endText();
            contentStream.close();

            // Enregistrement et téléchargement du PDF
            File file = new File("Livraison.pdf");
            document.save(file);
            document.close();

            // Ouvrir le PDF téléchargé (facultatif)
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }

    @FXML
    void AjouterLiv(ActionEvent event) throws SQLException {
        // Vérification de la sélection dans le ComboBox
        String paiementSelectionne = PaiementCom.getValue();
        String nom = nomText.getText().trim();
        String prenom = prenomText.getText().trim();
        String email = emailText.getText().trim();
        String adresse = adresseText.getText().trim();
        String state = stateText.getText().trim();
        String phoneStr = phoneText.getText().trim(); // String to hold phone input


        // Check if any of the fields are empty
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || adresse.isEmpty() || state.isEmpty() || phoneStr.isEmpty()) {
            showAlert("Tous les champs doivent être remplis.");
            return;
        }

        // Vérification du format du numéro de téléphone
        if (!isValidPhoneNumber(phoneStr)) {
            showAlert("Le numéro de téléphone doit être un nombre entier à 10 chiffres.");
            return;
        }


        // Check if email is in the correct format
        if (!email.matches("\\b[\\w.%-]+@[\\w.-]+\\.[a-zA-Z]{2,}\\b")) {
            showAlert("L'adresse e-mail n'est pas dans un format valide (ex: cccc@hh.gd).");
            return;
        }

            // Ajouter une nouvelle livraison avec le paiement sélectionné
            ls.ajouter(new Livraison(nomText.getText(), prenomText.getText(),  paiementSelectionne,stateText.getText(),adresseText.getText(), emailText.getText(),  Integer.parseInt(phoneText.getText())));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("livraispn ajoutée");
            alert.setContentText("Les donnes de livraison ont été ajoutée avec succès !");
            alert.show();
            if (paiementSelectionne == "Stripe"){
                navigatetoPayment();
            }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void navigatetoAfficherLivAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherLiv.fxml"));
            nomText.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }


    }
    @FXML
    void navigatetoPayment() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Payment.fxml"));
            nomText.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
    private boolean isValidPhoneNumber(String phoneStr) {
        return phoneStr.matches("\\d{10}"); // Format de numéro de téléphone à 10 chiffres
    }


    public void telechargerPDF(ActionEvent actionEvent) {
        // Vérification de la sélection dans le ComboBox
        String paiementSelectionne = PaiementCom.getValue();

        Livraison livraison = new Livraison(
                nomText.getText(),
                prenomText.getText(),
                paiementSelectionne,
                stateText.getText(),
                adresseText.getText(),
                emailText.getText(),
                Integer.parseInt(phoneText.getText())
        );

        // Appel de la méthode pour générer le PDF
        genererPDF(livraison);

        // Afficher une confirmation à l'utilisateur
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setContentText("Le PDF a été généré et téléchargé avec succès.");
        alert.showAndWait();
    }
}

