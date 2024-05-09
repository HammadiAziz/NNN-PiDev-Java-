package tn.esprit.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import tn.esprit.entities.User;
import tn.esprit.models.Commande2;
import tn.esprit.models.Produit2;
import tn.esprit.services.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class panier {
    @FXML
    private TextField menu_amount;

    @FXML
    private Label menu_change;

    @FXML
    private TableColumn<Produit2, Integer> menu_price;

    @FXML
    private TableColumn<Produit2, String> menu_productName;
    @FXML
    private TableView<Produit2> menu_tableView;
    private PanierService panierService = new PanierService();
    @FXML
    private Label menu_total;
    @FXML
    void initialize() {
        populateTableView();
    }
    private void populateTableView() {
        int userId = (int) SessionManager.getSession("userId");
        List<Produit2> productsInPanier = panierService.getProductsInPanier(userId);
        ObservableList<Produit2> productList = FXCollections.observableArrayList(productsInPanier);
        menu_tableView.setItems(productList);
        menu_productName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomprodui()));
        menu_price.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrixprodui()).asObject());
        double totalPrice = productsInPanier.stream().mapToDouble(Produit2::getPrixprodui).sum();
        menu_total.setText("$" + totalPrice);
        double tva = totalPrice * 0.2;
        if(tva !=0){
            double totalPriceWithTVA = totalPrice + tva+5;
            menu_amount.setText("$" + String.format("%.2f", tva));
            menu_change.setText("$" + String.format("%.2f", totalPriceWithTVA));
        }else {
            double totalPriceWithTVA = totalPrice + tva;
            menu_amount.setText("$" + String.format("%.2f", tva));
            menu_change.setText("$" + String.format("%.2f", totalPriceWithTVA));
        }

    }
    @FXML
    void BackToMarket(ActionEvent event) {
        try {
            // Charger le fichier FXML de la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherShop.fxml"));
            Parent root = loader.load();
            // Obtenir le contrôleur de la deuxième page
            AfficherShop controller = loader.getController();
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
    public void deleteProduct(){

        Produit2 selectedProduct = menu_tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            panierService.deleteProductFromPanier(selectedProduct.getId());
            populateTableView();
        }
    }
    @FXML
    public void orderAction() {
        if (menu_tableView.getItems().isEmpty()) {
            Alert emptyAlert = new Alert(Alert.AlertType.WARNING);
            emptyAlert.setTitle("Warning");
            emptyAlert.setHeaderText(null);
            emptyAlert.setContentText("Your cart is empty. Please add some products before placing an order.");
            emptyAlert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to place this order?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                panierService.moveProductsToCommande((int) SessionManager.getSession("userId"));
                populateTableView();

                Commande2 commande2=new Commande2();
                User user = new UserService().fetchPlayerData();
                String phoneNumber = "+216" + Integer.toString(user.getPhone());
                SmsSender.sendSMS(phoneNumber, "Hi *"+ user.getNom() + " " + user.getPrenom() + "*!\nYour command has been successfully created with Total Price = " + commande2.getPrixtotale() + " \nIncluding Delivery");
            }
        }

    }



}
