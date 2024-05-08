package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.models.Produit2;


public class Showdtailsprod {

    @FXML
    private Label desc1show;

    @FXML
    private Label desc2show;

    @FXML
    private Label nomshow;

    @FXML
    private ImageView pictureShow;

    @FXML
    private Label priceshow;

    private Produit2 produit;

    public void setProductData(Produit2 produit) {
        //  Remplir les champs avec les données de `produit`
        this.produit = produit;
        nomshow.setText(produit.getNomprodui());
        desc1show.setText(produit.getDesc1());
        desc2show.setText(produit.getDesc2());
        priceshow.setText(String.valueOf(produit.getPrixprodui()));
        if (produit.getImage() != null && !produit.getImage().isEmpty()) {
            try {
                Image image = new Image(produit.getImage());
                pictureShow.setImage(image);
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
    void initialize() {
    }
}
