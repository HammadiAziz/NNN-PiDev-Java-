package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tn.esprit.models.Produit2;
import tn.esprit.services.PanierService;
import tn.esprit.services.SessionManager;

import java.net.URL;
import java.util.ResourceBundle;

public class CardProduct implements Initializable {
    @FXML
    private AnchorPane card_form;
    @FXML
    private Button prod_addBtn;

    @FXML
    private Label prod_desc1;

    @FXML
    private Label prod_desc2;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;



    private Produit2 produit2;
    private String prod_image;
    private Image image;
    private double pr;

    public CardProduct() {
        // Default constructor
    }
    private PanierService panierService;
    // Constructor to inject PanierService
    public CardProduct(PanierService panierService) {
        this.panierService = panierService;
    }
    @FXML
    void addBtn(ActionEvent event) {
        if (produit2 != null) {
            int userId = (int) SessionManager.getSession("userId"); // Retrieve userId from session
            if (userId > 0) {
                panierService.addToPanier(produit2.getId(), userId); // Pass userId to addToPanier method
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Product added to panier successfully!");
                alert.showAndWait();
            } else {
                // Handle the case when userId is not valid or not found in session
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("User session not found or invalid userId!");
                alert.showAndWait();
            }
        }
    }

    public void setPanierService(PanierService panierService) {
        this.panierService = panierService;
    }
    public void setData(Produit2 produit2) {
        this.produit2 = produit2;
        prod_name.setText(produit2.getNomprodui());
        prod_desc1.setText(produit2.getDesc1());
        prod_desc2.setText(produit2.getDesc2());

        prod_price.setText(String.valueOf(produit2.getPrixprodui()));


        if (produit2.getImage() != null && !produit2.getImage().isEmpty()) {
            try {
                Image image = new Image(produit2.getImage());
                prod_imageView.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to load image");
                alert.setContentText("An error occurred while loading the image.");
                alert.showAndWait();
            }
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



}
