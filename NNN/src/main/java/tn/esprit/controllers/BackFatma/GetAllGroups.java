package tn.esprit.controllers.BackFatma;


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
import tn.esprit.controllers.AfficherProduits;
import tn.esprit.controllers.AfficherQuiv;
import tn.esprit.controllers.Quiz.QuizHomecontroller;
import tn.esprit.controllers.ReclamationControllers.Admin.AddRep;
import tn.esprit.controllers.ReclamationControllers.Admin.ListRec;
import tn.esprit.controllers.UserControllers.DashboardController;
import tn.esprit.entities.Groupe;
import tn.esprit.services.GroupService;

import java.io.IOException;
import java.util.List;

public class GetAllGroups {
    @FXML
    private TableColumn<Groupe, String>  nameColumn;

    @FXML
    private TableColumn<Groupe, String> descColumn;

    @FXML
    private TableColumn<Groupe, String>  imageColumn;

    @FXML
    private TableColumn<Groupe, Integer> idColumn;

    @FXML
    private TableView<Groupe> listGroupTV;





    private final GroupService gs = new GroupService();

    List<Groupe> groupes = gs.getAll();
    ObservableList<Groupe> observableList= FXCollections.observableList(groupes);
  /*
   this.name_group = name_group;
        this.desc_group = desc_group;
        this.group_image = group_image;
   */


    @FXML
    void initialize() {
        List<Groupe> groupes = gs.getAll();
        ObservableList<Groupe> observableList= FXCollections.observableList(groupes);
        listGroupTV.setItems(observableList);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name_group"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("desc_group"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("group_image"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        listGroupTV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //bouton dans table view
        // Créez une colonne de table pour les boutons
        TableColumn<Groupe, Void> buttonColumn = new TableColumn<>("Action");

        // Définissez la Cell Factory pour la colonne des boutons
        buttonColumn.setCellFactory(tc -> new TableCell<Groupe, Void>() {
            private final Button button = new Button("update");

            {
                // Définissez l'action du bouton
                button.setOnAction(e -> {
                    // Obtenez le produit associé à la ligne sélectionnée
                    Groupe selectedGroup = getTableView().getItems().get(getIndex());

                    try {
                        // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFATMA/UpdateGroupe.fxml"));
                        Parent root = loader.load();

                        // Obtenez le contrôleur de la nouvelle page
                        UpdateGroupe controller = loader.getController();

                        // Passez les données du produit sélectionné à la nouvelle page
                        controller.setGroupData(selectedGroup);

                        // Obtenez la scène actuelle à partir de l'événement
                        Scene currentScene = ((Node) e.getSource()).getScene();

                        // Remplacez la racine de la scène actuelle avec la nouvelle page
                        currentScene.setRoot(root);
                    } catch (IOException ex) {
                        // Gérez les exceptions d'E/S
                        ex.printStackTrace();
                        throw new RuntimeException(ex);
                    }
                });
            }
            //la méthode updateItem() d'une cellule de la colonne d'une TableView.
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });

        // Ajoutez la colonne des boutons à la TableView
        listGroupTV.getColumns().add(buttonColumn);




    }
    @FXML
    private void delete(ActionEvent event) {
        Groupe selectedItem = listGroupTV.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            gs.delete(selectedItem);
            ObservableList<Groupe> items = listGroupTV.getItems();
            items.remove(selectedItem);

        } else {
            // Show an alert if no item is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Item Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item to delete.");
            alert.showAndWait();
        }
    }


    @FXML
    void goAdd(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFATMA/AddGroupe.fxml"));
            Parent root = loader.load();
            AddGroupe controller = loader.getController();
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
    void gotoevent(ActionEvent event) {



        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFATMA/GetAllEvents.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            GetAllEvents Listcontroller = loader.getController();
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
    void gotogroup(ActionEvent event) {

        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFATMA/GetAllGroups.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            GetAllGroups Listcontroller = loader.getController();
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
    void gotolivraison(ActionEvent event) {
        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSuiv.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            AfficherQuiv Listcontroller = loader.getController();
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
    void gotomarket(ActionEvent event) {
        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            AfficherProduits Listcontroller = loader.getController();
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
    void gotoquiz(ActionEvent event) {
        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/Admin/QuizHome.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            QuizHomecontroller Listcontroller = loader.getController();
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
    void gotoreclamation(ActionEvent event) {


        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Reclamation/Admin/ListRec.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            ListRec Listcontroller = loader.getController();
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
    void gotouser(ActionEvent event) {
        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardGUI.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            DashboardController Listcontroller = loader.getController();
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
