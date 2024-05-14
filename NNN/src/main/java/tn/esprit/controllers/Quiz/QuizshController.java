package tn.esprit.controllers.Quiz;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.entities.Quiz;
import tn.esprit.services.QuizService;

import java.io.IOException;

public class QuizshController {
    @FXML
    private ComboBox<String> typeT;

    @FXML
    private TextArea Desc;

    @FXML
    private TextField Name;

    @FXML
    private TextField points;

    @FXML
    private TextField type;

    private final QuizService quiz=new QuizService();
    @FXML
    void initialize() {
        // Création d'une liste d'options de paiement
        ObservableList<String> optionsType = FXCollections.observableArrayList(
                "Nature",
                "Camping"
        );

        // Ajout des options de paiement au ComboBox
        typeT.setItems(optionsType);
    }
    @FXML
    void AjouterQuiz(ActionEvent event) {
        // Vérification des champs vides
        if (Name.getText().isEmpty() || Desc.getText().isEmpty() || typeT.getValue() == null || points.getText().isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        // Vérification si le champ points est un entier valide
        try {
            int pointsValue = Integer.parseInt(points.getText());
            if (pointsValue <= 0) {
                showAlert("Le nombre de points doit être supérieur à zéro.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Le nombre de points doit être un entier valide.");
            return;
        }

        // Si toutes les vérifications passent, ajoutez le quiz
        quiz.Create(new Quiz(Name.getText(), Desc.getText(), typeT.getValue(), Integer.parseInt(points.getText())));



        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/Admin/QuizList.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            QuizListcontroller Listcontroller = loader.getController();
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
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void QuizHome(ActionEvent event) {


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
}
