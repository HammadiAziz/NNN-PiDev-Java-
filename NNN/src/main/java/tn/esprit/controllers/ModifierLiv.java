package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import tn.esprit.entities.Livraison;
import tn.esprit.services.LivraisonService;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ModifierLiv  {
    @FXML
    private ComboBox<String> PaiementCom;

    @FXML
    private TextField adresseText;


    @FXML
    private TextField emailText;

    @FXML
    private Button navigateModifbtn;

    @FXML
    private TextField nomText;

    @FXML
    private TextField phoneText;

    @FXML
    private TextField prenomText;

    @FXML
    private TextField stateText;
    private final LivraisonService ls = new LivraisonService();
    private Livraison livToModify ;
    @FXML
    public void initialize() {

        ObservableList<String> optionsPaiement = FXCollections.observableArrayList(
                "especes",
                "Stripe"
        );

        // Ajout des options de paiement au ComboBox
        PaiementCom.setItems(optionsPaiement);
    }
    public void setLivToModify(Livraison liv) {
        this.livToModify = liv;
        displayLivInfo();
    }

    private void displayLivInfo() {
        if (livToModify != null) {
            nomText.setText(String.valueOf(livToModify.getNomC()));
            prenomText.setText(livToModify.getPrenomC());
            emailText.setText(livToModify.getEmail());
            phoneText.setText(String.valueOf(livToModify.getPhoneNumber()));
            stateText.setText(livToModify.getState());
            adresseText.setText(livToModify.getAdress());
        }

    }

    @FXML
    void ModifierrLiv(ActionEvent event)  {
        if (livToModify != null) {
            String adresse = adresseText.getText();
            String nom = nomText.getText();
            String serviceName = PaiementCom.getValue();
            String prenom = prenomText.getText();
            String email = emailText.getText();
            String state = stateText.getText();
            String phoneStr = phoneText.getText();

            // Valider les champs
            if (!validateFields(adresse, nom, serviceName, prenom, email, state, phoneStr)) {
                return;
            }

            // Convertir le numéro de téléphone en int
            int phone = Integer.parseInt(phoneStr);

            // Obtenir la livraison sélectionnée
            Livraison selectedLiv = new Livraison();

            // Mettre à jour les informations de la livraison sélectionnée
            selectedLiv.setId(livToModify.getId());
            selectedLiv.setNomC(nom);
            selectedLiv.setPrenomC(prenom);
            selectedLiv.setEmail(email);
            selectedLiv.setAdress(adresse);
            selectedLiv.setPaiementType(serviceName);
            selectedLiv.setState(state);
            selectedLiv.setPhoneNumber(phone);

            // Appeler la méthode Update du service de livraison
            ls.modifier( selectedLiv);

            // Afficher une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("livraispn ajoutée");
            alert.setContentText("Les donnes de livraison ont été modifié_; avec succès !");
            alert.show();

        }

    }
    private boolean validateFields(String adresse, String nom, String serviceName,String prenom , String email , String state , String phoneStr) {
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || adresse.isEmpty() || state.isEmpty() || phoneStr.isEmpty() || serviceName == null || serviceName.isEmpty()) {

            showAlert("Tous les champs sont requis.");
            return false;
        }

        if (!isValidInteger(phoneStr)) {
            showAlert("Le numéro de téléphone doit être un entier valide.");
            return false;
        }

        return true;
    }
    private boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Livraison findSelectedLiv(String serviceName) {
        // Debug statement to check serviceName
        System.out.println("Service Name: " + serviceName);

        // Obtenir la liste des livraisons depuis le service
        List<Livraison> livraisons = ls.getAllLiv();

        // Parcourir la liste pour trouver la livraison correspondant au nom de service
        for (Livraison livraison : livraisons) {
            // Debug statement to check each NomC
            System.out.println("Livraison NomC: " + livraison.getNomC());

            if (livraison.getNomC().equals(serviceName)) {
                return livraison;
            }
        }

        return null; // Retourner null si aucune livraison correspondante n'est trouvée
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
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLiv.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            AfficherLiv controller = loader.getController();
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
