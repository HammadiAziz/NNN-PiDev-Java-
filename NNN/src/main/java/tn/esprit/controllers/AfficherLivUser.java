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
import tn.esprit.controllers.FrontFatma.ShowAllEvents;
import tn.esprit.controllers.FrontFatma.ShowAllGroupes;
import tn.esprit.controllers.Quiz.Front.QuizList;
import tn.esprit.controllers.UserControllers.ProfileController;
import tn.esprit.entities.Livraison;

import tn.esprit.entities.SuiviLiv;
import tn.esprit.entities.User;
import tn.esprit.services.*;
import tn.esprit.services.LivraisonService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherLivUser {
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
    private Button profilename;

    final UserService pf = new UserService();
    private void fetchUserData() {
        // Retrieve user data from UserService
        User user = pf.fetchPlayerData();
        if (user != null) {
            // Populate the UI with user data

            profilename.setText(user.getNom());

        } else {
            // Handle case where user data is not found

            profilename.setText("N/A");

        }
    }

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
        tn.esprit.entities.Livraison selectedSuivi = treeview.getSelectionModel().getSelectedItem();

        if (selectedSuivi != null) {
            // Supprimer l'élément de la base de données
            LivraisonService suiviService = new LivraisonService();
            suiviService.supprimer(selectedSuivi.getId());

            // Supprimer l'élément de la TableView
            treeview.getItems().remove(selectedSuivi);
            // Effacer la sélection dans la TableView pour éliminer le curseur
            treeview.getSelectionModel().clearSelection();
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
    @FXML
    void Suivi(ActionEvent event) {
        try {
            // Get the selected Livraison
            Livraison selectedLivraison = treeview.getSelectionModel().getSelectedItem();
            if (selectedLivraison != null) {
                // Get the SuiviLiv data associated with the selected Livraison
                tn.esprit.entities.SuiviLiv suiviLiv = ls.getSuiviLivByLivraisonId(selectedLivraison.getId());
                if (suiviLiv != null) {
                    // Load the Map page
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Map.fxml"));
                    Parent root = loader.load();

                    // Pass the SuiviLiv data to the Map controller
                    MapController mapController = loader.getController();
                    mapController.displaySuiviLivInfo(suiviLiv);

                    // Get the current scene and set the Map page as its root
                    Scene currentScene = ((Node) event.getSource()).getScene();
                    currentScene.setRoot(root);
                } else {
                    showError("SuiviLiv data not found for the selected Livraison.");
                }
            } else {
                showError("Veuillez sélectionner une livraison avant de continuer.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur lors du chargement de la page Suivi.");
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

    @FXML
    void goToEvents(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontFatma/ShowAllEvents.fxml"));
            Parent root = loader.load();
            ShowAllEvents controller = loader.getController();
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
    public void goToQuiz(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/User/QuizList.fxml"));
            Parent root = loader.load();
            QuizList controller = loader.getController();
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
    void goToGroups(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontFatma/ShowAllGroupes.fxml"));
            Parent root = loader.load();
            ShowAllGroupes controller = loader.getController();
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
    void goToMarket(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherShop.fxml"));
            Parent root = loader.load();
            AfficherShop controller = loader.getController();
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
    void goToLivraison(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLivUser.fxml"));
            Parent root = loader.load();
            AfficherLivUser controller = loader.getController();
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
    void gotoHome(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();
            home controller = loader.getController();
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
    void gotoprofile(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateUserGUI.fxml"));
            Parent root = loader.load();
            ProfileController controller = loader.getController();
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