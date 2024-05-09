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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tn.esprit.models.Commande2;
import tn.esprit.models.Produit2;
import tn.esprit.services.Commande2Service;
import tn.esprit.services.Produit2Service;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;

public class AfficherCommandes {
    @FXML
    private TableColumn<Commande2, String> nameCol2;

    @FXML
    private TableColumn<Commande2, Integer> idcommandecol;

    @FXML
    private TableView<Commande2> table2;

    @FXML
    private TableColumn<Commande2, Integer> totalepriceCol;

    @FXML
    private TableColumn<Commande2, Integer> usercol2;

    private final Commande2Service cm = new Commande2Service();

    @FXML
    void initialize(){
        List<Commande2> commande2s = cm.getAll();
        ObservableList<Commande2> observableList = FXCollections.observableList(commande2s);
        table2.setItems(observableList);
        idcommandecol.setCellValueFactory(new PropertyValueFactory<>("id"));
        usercol2.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        totalepriceCol.setCellValueFactory(new PropertyValueFactory<>("prixtotale"));
        nameCol2.setCellValueFactory(new PropertyValueFactory<>("produits"));
        table2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //button delete

        //bouton dans table view
        // Créez une colonne de table pour les boutons
        TableColumn<Commande2, Void> buttonColumn = new TableColumn<>("Action");

// Définissez la Cell Factory pour la colonne des boutons
        buttonColumn.setCellFactory(tc -> new TableCell<Commande2, Void>() {
            ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/trashprod.png")));
            private final Button button2 = new Button("", deleteIcon);

            {

                button2.setOnAction(e -> {
                    Commande2 selectedProduct = getTableView().getItems().get(getIndex());

                    // Obtenez une instance de Produit2Service (assurez-vous qu'elle est correctement injectée ou instanciée)
                    Commande2Service commande2Service = new Commande2Service(); // Par exemple, si c'est une classe non statique
                    // Appelez la méthode delete de votre service en utilisant l'ID du produit
                    commande2Service.delete(selectedProduct.getId());

                    // Ensuite, supprimez également le produit de la liste observable
                    getTableView().getItems().remove(selectedProduct);
                    getTableView().refresh();
                });
            }
            //la méthode updateItem() d'une cellule de la colonne d'une TableView.
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Créer une boîte horizontale pour placer les boutons
                    HBox buttonsContainer = new HBox(5);
                    buttonsContainer.getChildren().addAll( button2);
                    deleteIcon.setFitWidth(20); // Largeur souhaitée en pixels
                    deleteIcon.setFitHeight(20); // Hauteur souhaitée en pixels
                    setGraphic(buttonsContainer);

                }
            }
        });

        // Ajoutez la colonne des boutons à la TableView
        table2.getColumns().add(buttonColumn);

    }

    @FXML
    void back(ActionEvent event) {
        try {
            // Charger le fichier FXML de la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
            Parent root = loader.load();
            // Obtenir le contrôleur de la deuxième page
            AfficherProduits controller = loader.getController();
            // Obtenir la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();
            // Remplacer la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    @FXML
    void deleteorder(ActionEvent event) {
        // Obtenir l'élément sélectionné dans la TableView
        Commande2 selectedOrder = table2.getSelectionModel().getSelectedItem();

        if (selectedOrder != null) {
            // Supprimer l'élément de la base de données
            Commande2Service commande2Service = new Commande2Service();
            commande2Service.delete(selectedOrder.getId());

            // Supprimer l'élément de la TableView
            table2.getItems().remove(selectedOrder);
            // Effacer la sélection dans la TableView pour éliminer le curseur
            table2.getSelectionModel().clearSelection();
        } else {
            // Afficher un message d'erreur ou d'avertissement si aucun élément n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Deleting an order");
            alert.setContentText("No orders are selected for deletion.");
            alert.showAndWait();        }
    }





}
