package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.entities.SuiviLiv;
import tn.esprit.services.SuiviService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjouterSuiv implements Initializable {
    @FXML
    private Button back;
    @FXML
    private TextField LocationT;

    @FXML
    private Button ajout;

    @FXML
    private TextField dateT;

    @FXML
    private TextField livreT;

    private final SuiviService ls = new SuiviService();

    @FXML
    void AjouterSuiv(ActionEvent event) {
        String location = LocationT.getText();
        String dateCommande = dateT.getText();
        String livreeText = livreT.getText();

        if (location.isEmpty() || dateCommande.isEmpty()
                || !validerFormatDate(dateCommande)
                || (!livreeText.equalsIgnoreCase("true") && !livreeText.equalsIgnoreCase("false"))) {
            afficherAlerteErreur("Veuillez remplir correctement tous les champs.");
            return; // Arrêter le traitement si la saisie est incorrecte
        }

        boolean livree = Boolean.parseBoolean(livreeText);
        SuiviLiv nouveauSuivi = new SuiviLiv(dateCommande, livree, location);
        ls.ajouter(nouveauSuivi);

        afficherAlerteSucces("Le suivi a été ajouté avec succès.");
        viderChampsSaisie();
    }

    private void afficherAlerteErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.show();
    }

    private void afficherAlerteSucces(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setContentText(message);
        alert.show();
    }

    private void viderChampsSaisie() {
        LocationT.clear();
        dateT.clear();
        livreT.clear();
    }

    private boolean validerFormatDate(String date) {
        // Vous pouvez implémenter ici la logique de validation du format de la date
        // Par exemple, vérifier si la chaîne correspond au format "jj/mm/aaaa"
        return true; // À modifier selon votre logique de validation
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void backToAff(ActionEvent event) {
        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSuiv.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            AfficherQuiv controller = loader.getController();
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
