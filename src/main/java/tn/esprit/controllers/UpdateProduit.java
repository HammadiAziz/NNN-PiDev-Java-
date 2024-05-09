package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tn.esprit.models.Produit2;
import javafx.scene.control.TableView;
import tn.esprit.services.Produit2Service;

import java.io.IOException;


public class UpdateProduit {
    @FXML
    private TextField NameUpdate;
    @FXML
    private TextField desc1Update;

    @FXML
    private TextField desc2Update;

    @FXML
    private TextField pictureUpdate;

    @FXML
    private TextField priceUpdate;
    @FXML
    private Label labedpicture2;

    @FXML
    private Label labeldesc12;

    @FXML
    private Label labeldesc22;

    @FXML
    private Label labelnom2;

    @FXML
    private Label labelprice2;

    private Produit2 produit;

    private final Produit2Service pr = new Produit2Service();

    private void setErrorLabel(Label label, String message) {
        label.setText(message);
        label.setTextFill(Color.RED); // Définit la couleur du texte en rouge
    }

    private void clearErrorLabel(Label label) {
        label.setText("");
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (NameUpdate.getText().isEmpty()) {
            setErrorLabel(labelnom2, "Please enter a product name.");
            isValid = false;
        } else {
            clearErrorLabel(labelnom2);
        }

        if (priceUpdate.getText().isEmpty() || Double.parseDouble(priceUpdate.getText()) < 0) {
            setErrorLabel(labelprice2, "Please enter a valid price for the product.");
            isValid = false;
        } else {
            clearErrorLabel(labelprice2);
        }

        if (pictureUpdate.getText().isEmpty()) {
            setErrorLabel(labedpicture2, "Please enter a URL or path for the product picture.");
            isValid = false;
        } else {
            clearErrorLabel(labedpicture2);
        }

        if (desc1Update.getText().isEmpty() || desc1Update.getText().length() < 15) {
            setErrorLabel(labeldesc12, "Please enter a description with a length of at least 15 characters.");
            isValid = false;
        } else {
            clearErrorLabel(labeldesc12);
        }
        if (desc2Update.getText().isEmpty() || desc2Update.getText().length() < 5) {
            setErrorLabel(labeldesc22, "Please enter a description with a length of at least 5 characters.");
            isValid = false;
        } else {
            clearErrorLabel(labeldesc22);
        }

        return isValid;
    }
    @FXML
    void UpdateProduct(ActionEvent event) {
        if (!validateInputs()) {
            return; // Ne pas continuer si la validation a échoué
        }
        // Récupérer les données modifiées de l'interface
        String nomProduit = NameUpdate.getText();
        String pictureProduit = pictureUpdate.getText();
        int prixProduit;

        try {
            prixProduit = Integer.parseInt(priceUpdate.getText());
        } catch (NumberFormatException e) {
            // Affichez un message d'erreur si le prix est invalide
            System.err.println("Le prix doit être un nombre valide.");
            return;
        }
        String desc1Produit = desc1Update.getText();
        String desc2Produit = desc2Update.getText();

        // Mettre à jour les propriétés du produit
        produit.setNomprodui(nomProduit);
        produit.setImage(pictureProduit);
        produit.setPrixprodui(prixProduit);
        produit.setDesc1(desc1Produit);
        produit.setDesc2(desc2Produit);

        // Mettre à jour le produit dans la base de données
        pr.update(produit.getId(), produit);

        // Afficher une alerte
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Product Updated");
        alert.setContentText("The selected product has been successfully updated.");
        alert.showAndWait();

        // Rafraîchir la TableView dans l'interface AfficherProduits
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProductData(Produit2 produit) {
        //  Remplir les champs avec les données de `produit`
        this.produit = produit;
        NameUpdate.setText(produit.getNomprodui());
        desc1Update.setText(produit.getDesc1());
        desc2Update.setText(produit.getDesc2());
        pictureUpdate.setText(produit.getImage());
        priceUpdate.setText(String.valueOf(produit.getPrixprodui()));
    }
}
