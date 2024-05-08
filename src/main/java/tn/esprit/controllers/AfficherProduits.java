package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.models.Produit2;
import tn.esprit.services.Produit2Service;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.List;

public class AfficherProduits {
    @FXML
    private TableColumn<Produit2, String> desc1col1;

    @FXML
    private TableColumn<Produit2, String> desc2col1;

    @FXML
    private TableColumn<Produit2, Integer> idProduitcol1;

    @FXML
    private TableColumn<Produit2, String> nomProduitcol1;

    @FXML
    private TableColumn<Produit2, Integer> pricecol1;
    @FXML
    private TableView<Produit2> table1;
    @FXML
    private TableColumn<Produit2, String> picturecol1;
    @FXML
    private TextField KeywordTF;

    private final Produit2Service pr1 = new Produit2Service();
    @FXML
    void initialize(){
        List<Produit2> produit2s = pr1.getAll();
        ObservableList<Produit2> observableList = FXCollections.observableList(produit2s);
        table1.setItems(observableList);
        pricecol1.setCellValueFactory(new PropertyValueFactory<>("prixprodui"));
        idProduitcol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomProduitcol1.setCellValueFactory(new PropertyValueFactory<>("nomprodui"));
        picturecol1.setCellValueFactory(new PropertyValueFactory<>("image"));
        desc1col1.setCellValueFactory(new PropertyValueFactory<>("desc1"));
        desc2col1.setCellValueFactory(new PropertyValueFactory<>("desc2"));
        //
        table1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //recherche
        FilteredList<Produit2> filterdData = new FilteredList<>(observableList,b -> true);
        KeywordTF.textProperty().addListener((observable ,oldValue, newValue)->{
            filterdData.setPredicate(Produit2 ->{
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true ;
                }
                String searchKeyword = newValue.toLowerCase();
                if(Produit2.getNomprodui().toLowerCase().indexOf(searchKeyword) > -1){
                    return true; // means we found a match in product name
                }else if(String.valueOf(Produit2.getPrixprodui()).indexOf(searchKeyword) > -1){
                    return true;
                }else if(Produit2.getDesc1().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(Produit2.getDesc2().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(String.valueOf(Produit2.getId()).indexOf(searchKeyword) > -1){
                    return true;
                }else
                    return false; // no match found

            });
        });

        SortedList<Produit2> sortedData = new SortedList<>(filterdData);
        //Bind sorted result wih table view
        sortedData.comparatorProperty().bind(table1.comparatorProperty());
        //apply filtred and sorted data to the table view
        table1.setItems(sortedData);
        /////fin recherche


        //bouton dans table view
        // Créez une colonne de table pour les boutons
        TableColumn<Produit2, Void> buttonColumn = new TableColumn<>("Action");

// Définissez la Cell Factory pour la colonne des boutons
        buttonColumn.setCellFactory(tc -> new TableCell<Produit2, Void>() {
            ImageView updateIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/editprod.png")));
            private final Button button = new Button("", updateIcon);
            ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/trashprod.png")));
            private final Button button2 = new Button("", deleteIcon);
            ImageView showIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/showprod.png")));
            private final Button button3 = new Button("", showIcon);

            {

                // Définissez l'action du bouton
                button.setOnAction(e -> {
                    // Obtenez le produit associé à la ligne sélectionnée
                    Produit2 selectedProduct = getTableView().getItems().get(getIndex());

                    try {
                        // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateProduit.fxml"));
                        Parent root = loader.load();

                        // Obtenez le contrôleur de la nouvelle page
                        UpdateProduit controller = loader.getController();

                        // Passez les données du produit sélectionné à la nouvelle page
                        controller.setProductData(selectedProduct);

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
                button2.setOnAction(e -> {
                    Produit2 selectedProduct = getTableView().getItems().get(getIndex());

                    // Obtenez une instance de Produit2Service (assurez-vous qu'elle est correctement injectée ou instanciée)
                    Produit2Service produit2Service = new Produit2Service(); // Par exemple, si c'est une classe non statique
                    // Appelez la méthode delete de votre service en utilisant l'ID du produit
                    produit2Service.delete(selectedProduct.getId());

                    // Ensuite, supprimez également le produit de la liste observable
                    getTableView().getItems().remove(selectedProduct);
                    button.setDisable(true); // Désactive le bouton "update"
                    button2.setDisable(true); // Désactive le bouton "delete"
                });
                button3.setOnAction(e -> {
                    Produit2 selectedProduct = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/showdtailsprod.fxml"));
                        Parent root = loader.load();
                        // Obtenez le contrôleur de la nouvelle page
                        Showdtailsprod Listcontroller = loader.getController();
                        // Obtenez la scène actuelle à partir de l'événement

                        Stage newStage = new Stage();
                        newStage.setTitle("Product detail"); // Set the title of the new window
                        newStage.setScene(new Scene(root)); // Set the scene with the content loaded from the FXML file
                        // Passez les données du produit sélectionné à la nouvelle page
                        Listcontroller.setProductData(selectedProduct);
                        // Show the new window
                        newStage.show();


                    } catch (IOException a) {
                        // Gérez les exceptions d'E/S
                        a.printStackTrace();
                        throw new RuntimeException(a);
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
                    // Créer une boîte horizontale pour placer les boutons
                    HBox buttonsContainer = new HBox(5);
                    buttonsContainer.getChildren().addAll(button, button2, button3);
                    updateIcon.setFitWidth(20); // Largeur souhaitée en pixels
                    updateIcon.setFitHeight(20); // Hauteur souhaitée en pixels
                    deleteIcon.setFitWidth(20); // Largeur souhaitée en pixels
                    deleteIcon.setFitHeight(20); // Hauteur souhaitée en pixels
                    showIcon.setFitWidth(20); // Largeur souhaitée en pixels
                    showIcon.setFitHeight(20); // Hauteur souhaitée en pixels
                    setGraphic(buttonsContainer);

                }
            }
        });

        // Ajoutez la colonne des boutons à la TableView
        table1.getColumns().add(buttonColumn);

    }


    @FXML
    void navigueToAdd(ActionEvent event) {
        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            AjouterProduit controller = loader.getController();
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
    void navigueToList(ActionEvent event) {
        try {
            // Charger le fichier FXML de la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCommandes.fxml"));
            Parent root = loader.load();
            // Obtenir le contrôleur de la deuxième page
            AfficherCommandes controller = loader.getController();
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
    void deleteProduct(ActionEvent event) {
        // Obtenir l'élément sélectionné dans la TableView
        Produit2 selectedProduct = table1.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            // Supprimer l'élément de la base de données
            Produit2Service produit2Service = new Produit2Service();
            produit2Service.delete(selectedProduct.getId());

            // Obtenir l'ObservableList de la table
            ObservableList<Produit2> items = table1.getItems();

            // Supprimer l'élément de l'ObservableList (et donc de la TableView)
            items.remove(selectedProduct);

            // Effacer la sélection dans la TableView pour éliminer le curseur
            table1.getSelectionModel().clearSelection();
        } else {
            // Afficher une alerte si aucun élément n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Deleting a product");
            alert.setContentText("No products are selected for deletion.");
            alert.showAndWait();
        }


    }


}
