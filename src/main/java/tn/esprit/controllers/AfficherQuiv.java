package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entities.SuiviLiv;

import tn.esprit.services.SuiviService;

import java.io.IOException;
import java.util.List;

public class AfficherQuiv {
    @FXML
    private Button modifierSuiv;
    @FXML
    private Button AjouterSuiv;

    @FXML
    private Button back;
    @FXML
    private TableColumn<SuiviLiv, String> locationT;

    @FXML
    private TableView<SuiviLiv> table;
    @FXML
    private TableColumn<SuiviLiv, String> dateCT;

    @FXML
    private TableColumn<SuiviLiv,Integer> idT;

    @FXML
    private TableColumn<SuiviLiv, Boolean> livreeT;
    @FXML
    private TableView<SuiviLiv> tableViewId;

    private  final SuiviService ls = new SuiviService() ;
    @FXML
    void initialize(){
        try {
                List<SuiviLiv> suiviList = ls.getAllLiv();
                ObservableList<SuiviLiv> observableList = FXCollections.observableList(suiviList);
                tableViewId.setItems(observableList);
                idT.setCellValueFactory(new PropertyValueFactory<>("id"));
                dateCT.setCellValueFactory(new PropertyValueFactory<>("dateCommande"));
                locationT.setCellValueFactory(new PropertyValueFactory<>("location"));
                livreeT.setCellValueFactory(new PropertyValueFactory<>("livree"));

        } catch (Exception e) {
            afficherAlerteErreur("Une erreur s'est produite lors du chargement des suivis.");
        }

    }
    private void afficherAlerteErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void AjouterSuiv(ActionEvent event) {
        try {
            System.out.println("Resource URL: " + getClass().getResource("/AjouterSuiv.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterSuiv.fxml"));
            AjouterSuiv.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    @FXML
    void BackLiv(ActionEvent event) {
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
    @FXML
    void modifierSuiv(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSuiv.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur du fichier FXML chargé
            ModifierSuiv modifierSuivController = loader.getController();

            // Créer une instance de ModifierSuiv et passer le suivi sélectionné
            SuiviLiv suiviSelectionne = tableViewId.getSelectionModel().getSelectedItem();
            modifierSuivController.setSuiviToModify(suiviSelectionne);

            // Modifier la scène pour afficher la vue de modification
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            afficherAlerteErreur("Erreur lors de l'ouverture de la vue de modification.");
        }

    }
    @FXML
    void Suivi(ActionEvent event) {
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
    @FXML
    void NavigateToLiv(ActionEvent event) {
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
