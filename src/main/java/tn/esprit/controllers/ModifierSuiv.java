package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.entities.SuiviLiv;


import java.io.IOException;

public class ModifierSuiv {
    @FXML
    private TextField LocationT;

    @FXML
    private Button back;

    @FXML
    private TextField dateT;

    @FXML
    private TextField livreT;

    @FXML
    private Button modif;
    @FXML
    public void initialize() {

    }
    private SuiviLiv suivToModify; // Instance de SuiviLiv à modifier
    // Méthode pour définir le suivi à modifier et afficher ses informations
    public void setSuiviToModify(SuiviLiv suiv) {
        this.suivToModify = suiv;
        displaySuiviInfo();
    }

    // Méthode pour afficher les informations du suivi à modifier
    private void displaySuiviInfo() {
        if (suivToModify != null) {
            LocationT.setText(suivToModify.getLocation());
            dateT.setText(suivToModify.getDateCommande());
            livreT.setText(Boolean.toString(suivToModify.isLivree()));
        }
    }
    @FXML
    void backToAffi(ActionEvent event) {
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
    //

    @FXML
    void modifierSuiv(ActionEvent event) {
        if (suivToModify != null) {
            String location = LocationT.getText();
            String date = dateT.getText();
            boolean livree = Boolean.parseBoolean(livreT.getText());

            // Mettre à jour les informations de suivi
            suivToModify.setLocation(location);
            suivToModify.setDateCommande(date);
            suivToModify.setLivree(livree);

            // Appeler la méthode de mise à jour appropriée, par exemple :
            // suiviService.updateSuivi(suivToModify);

            // Afficher une confirmation de modification
            System.out.println("Les informations de suivi ont été modifiées avec succès !");
        }

    }
}
