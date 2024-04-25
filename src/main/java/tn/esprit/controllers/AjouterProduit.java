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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tn.esprit.models.Produit2;
import tn.esprit.services.Produit2Service;

import java.io.IOException;
import java.util.List;

public class AjouterProduit {
    @FXML
    private TextField productdesc1;

    @FXML
    private TextField productdesc2;

    @FXML
    private TextField productnom;

    @FXML
    private TextField productpic;

    @FXML
    private TextField productprice;
    @FXML
    private Label labeldesc1;

    @FXML
    private Label labeldesc2;

    @FXML
    private Label labelnom;

    @FXML
    private Label labelpicture;

    @FXML
    private Label labelprice;


    private void setErrorLabel(Label label, String message) {
        label.setText(message);
        label.setTextFill(Color.RED); // Définit la couleur du texte en rouge
    }

    private void clearErrorLabel(Label label) {
        label.setText("");
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (productnom.getText().isEmpty()) {
            setErrorLabel(labelnom, "Please enter a product name.");
            isValid = false;
        } else {
            clearErrorLabel(labelnom);
        }

        if (productprice.getText().isEmpty() || Double.parseDouble(productprice.getText()) < 0) {
            setErrorLabel(labelprice, "Please enter a valid price for the product.");
            isValid = false;
        } else {
            clearErrorLabel(labelprice);
        }

        if (productpic.getText().isEmpty()) {
            setErrorLabel(labelpicture, "Please enter a URL or path for the product picture.");
            isValid = false;
        } else {
            clearErrorLabel(labelpicture);
        }

        if (productdesc1.getText().isEmpty() || productdesc1.getText().length() < 15) {
            setErrorLabel(labeldesc1, "Please enter a description with a length of at least 15 characters.");
            isValid = false;
        } else {
            clearErrorLabel(labeldesc1);
        }
        if (productdesc2.getText().isEmpty() || productdesc2.getText().length() < 5) {
            setErrorLabel(labeldesc2, "Please enter a description with a length of at least 5 characters.");
            isValid = false;
        } else {
            clearErrorLabel(labeldesc2);
        }

        return isValid;
    }
    private final Produit2Service pr = new Produit2Service();
    @FXML
    void AjouterProduit(ActionEvent event) {
        if (!validateInputs()) {
            return; // Ne pas continuer si la validation a échoué
        }

        pr.add(new Produit2(productnom.getText(), Integer.parseInt(productprice.getText()), productpic.getText(), productdesc1.getText(), productdesc2.getText()));
        // Afficher une alerte
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Product added");
        alert.setContentText("The selected product has been successfully added.");
        alert.showAndWait();
        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            AfficherProduits controller = loader.getController();
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

}
