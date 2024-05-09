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
import tn.esprit.entitiesModel.Livraison;
import tn.esprit.entitiesModel.SuiviLiv;
import tn.esprit.service.SuiviService;

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
                List<SuiviLiv> suiviList = ls.getAll();
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
            System.out.println("Resource URL: " + getClass().getResource("/FXML/Admin/AjouterSuiv.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/Admin/AjouterSuiv.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/AfficherLiv.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/ModifierSuiv.fxml"));
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
    void SupprimerSuiv(ActionEvent event) {
// Obtenir l'élément sélectionné dans la TableView
        SuiviLiv selectedSuivi = tableViewId.getSelectionModel().getSelectedItem();

        if (selectedSuivi != null) {
            // Supprimer l'élément de la base de données
            SuiviService suiviService = new SuiviService();
            suiviService.Delete(selectedSuivi.getId());

            // Supprimer l'élément de la TableView
            tableViewId.getItems().remove(selectedSuivi);
            // Effacer la sélection dans la TableView pour éliminer le curseur
            tableViewId.getSelectionModel().clearSelection();
            afficherAlerteSucces("Le suivi a été supprimer avec succès.");

        } else {
            // Afficher un message d'erreur ou d'avertissement si aucun élément n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Suppression d'un suivi");
            alert.setContentText("Aucun suivi n'est sélectionné pour la suppression.");
            alert.showAndWait();
        }
    }
    private void afficherAlerteSucces(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setContentText(message);
        alert.show();
    }


}
