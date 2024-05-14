package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import tn.esprit.entities.Livraison;
import tn.esprit.entities.SuiviLiv;
import tn.esprit.services.LivraisonService;
import tn.esprit.services.SuiviService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherLiv {
    @FXML
    private Button UpdateLiv;
    @FXML
    private TableColumn<Livraison, String> adresseT;

    @FXML
    private TableColumn<Livraison, String> emailT;

    @FXML
    private TableColumn<Livraison, Integer> idT;

    @FXML
    private Button SuiviLiv;

    @FXML
    private TableColumn<Livraison, String> nomT;
    @FXML
    private Button archivee;
    @FXML
    private TableColumn<Livraison, Integer> numT;

    @FXML
    private TableColumn<Livraison, String> prenomT;

    @FXML
    private TableColumn<Livraison, String> stateT;

    @FXML
    private TableView<Livraison> treeview;

    @FXML
    private TableColumn<Livraison, String> typeT;
    private final LivraisonService ls = new LivraisonService();
    @FXML
    private Button addLiv;
    ObservableList<Livraison> obs;



    @FXML
    void initialize(){
        treeview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        try {
          List<Livraison> livs = ls.getAllLiv();


            if (livs != null) {
                ObservableList<Livraison> observableList = FXCollections.observableList(livs);
                treeview.setItems(observableList);
                idT.setCellValueFactory(new PropertyValueFactory<>("id"));
                nomT.setCellValueFactory(new PropertyValueFactory<>("nomC"));
                prenomT.setCellValueFactory(new PropertyValueFactory<>("prenomC"));
                emailT.setCellValueFactory(new PropertyValueFactory<>("email"));
                adresseT.setCellValueFactory(new PropertyValueFactory<>("adresse"));
                stateT.setCellValueFactory(new PropertyValueFactory<>("state"));
                typeT.setCellValueFactory(new PropertyValueFactory<>("typePaiement"));
                numT.setCellValueFactory(new PropertyValueFactory<>("phoneN"));

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("La liste des livraisons est vide.");
                alert.show();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors du chargement des livraisons.");
            alert.show();
        }
        // Dans la méthode initialize() ou après avoir initialisé votre TableView
        TableColumn<Livraison, Void> pdfColumn = new TableColumn<>("PDF");
        pdfColumn.setPrefWidth(100); // Définissez la largeur de la colonne selon vos besoins
        // Créez une cellule personnalisée pour la colonne PDF
        Callback<TableColumn<Livraison, Void>, TableCell<Livraison, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Livraison, Void> call(final TableColumn<Livraison, Void> param) {
                final TableCell<Livraison, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("PDF");

                    {
                        btn.setOnAction(event -> {
                            try {
                                // Code pour générer et télécharger le PDF à partir des données de la ligne sélectionnée
                                Livraison livraison = getTableView().getItems().get(getIndex());
                                generatePDF(livraison); // Méthode à implémenter pour générer le PDF
                                btn.getStyleClass().add("pdf"); // Add style class to button
                                showSuccess("pdf telechargee");
                            } catch (Exception e) {
                                showError(e.getMessage());
                            }


                        });
                        btn.setStyle("-fx-background-color: linear-gradient(to bottom left,#4CAF50,#4F4F4F); " + /* Couleur de fond */
                                "-fx-text-fill: white; " + /* Couleur du texte */
                                "-fx-font-weight: bold; " + /* Police en gras */
                                "-fx-padding: 5px 10px;");
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        pdfColumn.setCellFactory(cellFactory);
        treeview.getColumns().add(pdfColumn);

    }
    private void generatePDF(Livraison livraison) {
        PDFGenerator.generatePDF(livraison);
    }
    @FXML
    void ajouterLiv(ActionEvent event) {
        try {
            System.out.println("Resource URL: " + getClass().getResource("/AjouterLiv.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterLiv.fxml"));
            addLiv.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }


    public void UpdateLiv(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLiv.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur du fichier FXML chargé
            ModifierLiv modifierLivController = loader.getController();

            // Passer la livraison à modifier au contrôleur de modification
            Livraison livraisonSelectionnee = treeview.getSelectionModel().getSelectedItem();
            modifierLivController.setLivToModify(livraisonSelectionnee);

            // Modifier la scène pour afficher la vue de modification
            UpdateLiv.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Erreur lors de l'ouverture de la vue de modification.");
            alert.setTitle("Erreur");
            alert.show();
        }
    }
    @FXML
    void ArchiverLiv(ActionEvent event) {
        // Obtenir l'élément sélectionné dans la TableView
        Livraison selectedLivraison = treeview.getSelectionModel().getSelectedItem();

        if (selectedLivraison != null) {
            // Archiver la livraison dans la base de données
            LivraisonService livraisonService = new LivraisonService();
            livraisonService.supprimer(selectedLivraison.getId());

            // Supprimer l'élément de la TableView
            treeview.getItems().remove(selectedLivraison);
            // Effacer la sélection dans la TableView pour éliminer le curseur
            treeview.getSelectionModel().clearSelection();
            afficherAlerteSucces("La livraison a été archivée avec succès.");

        } else {
            // Afficher un message d'erreur ou d'avertissement si aucun élément n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Archivage d'une livraison");
            alert.setContentText("Aucune livraison n'est sélectionnée pour l'archivage.");
            alert.showAndWait();
        }
    }

    private void afficherAlerteSucces(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setContentText(message);
        alert.show();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Afficherliv.fxml"));
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
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Success Message
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }


}