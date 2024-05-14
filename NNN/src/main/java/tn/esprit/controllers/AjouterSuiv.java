package tn.esprit.controllers;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.entities.SuiviLiv;
import tn.esprit.services.SuiviService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AjouterSuiv implements Initializable {
    @FXML
    private Button back;
    @FXML
    private TextField LocationT;
    @FXML
    private DatePicker datePicker; // Référence au DatePicker dans le fichier FXML


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
        LocalDate dateCommande = datePicker.getValue(); // Récupérer la date sélectionnée

        // Vérifiez si la date est nulle ou non
        if (location.isEmpty() || dateCommande == null || !validerFormatDate(dateCommande.toString())) {
            afficherAlerteErreur("Veuillez remplir correctement tous les champs.");
            return;
        }


        String livreeText = livreT.getText();


        // Convert location to geocode using Google Maps Geocoding API
        try {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyCBAskCbIMqqRUoHd41aETAnYyUu8KeXUU&q")
                    .build();
            GeocodingResult[] results = GeocodingApi.geocode(context, location).await();
            if (results.length > 0) {
                double latitude = results[0].geometry.location.lat;
                double longitude = results[0].geometry.location.lng;

                // Append latitude and longitude to the location string
                location += " (Latitude: " + latitude + ", Longitude: " + longitude + ")";

                // Create SuiviLiv object with modified location
                SuiviLiv nouveauSuivi = new SuiviLiv(dateCommande.toString(), Boolean.parseBoolean(livreeText), location);
                ls.ajouter(nouveauSuivi);

                afficherAlerteSucces("Le suivi a été ajouté avec succès.");
                viderChampsSaisie();
            } else {
                afficherAlerteErreur("Impossible de trouver les coordonnées pour cette localisation.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            afficherAlerteErreur("Une erreur s'est produite lors de la conversion de la localisation en coordonnées géographiques.");
        }
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
