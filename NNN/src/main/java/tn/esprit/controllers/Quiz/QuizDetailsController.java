package tn.esprit.controllers.Quiz;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tn.esprit.controllers.Question.QuestionAdd;
import tn.esprit.controllers.Question.QuestionCardController;
import tn.esprit.entities.Questions;
import tn.esprit.entities.Quiz;
import tn.esprit.services.QuestionsService;
import tn.esprit.services.QuizService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class QuizDetailsController implements Initializable {


    @FXML
    private ComboBox<String> Typeadd;

    @FXML
    private GridPane grid;

    @FXML
    private Label desc;

    @FXML
    private TextField nameadd;

    @FXML
    private TextField descadd;

    @FXML
    private Label name;

    @FXML
    private Label points;

    @FXML
    private TextField pointsadd;

    @FXML
    private Label type;



    private final QuizService se = new QuizService();

    private Quiz quizToModify;


    private final QuestionsService us = new QuestionsService();




    private QuestionsService qs = new QuestionsService();

    Set<Questions> questSet;
    List<Questions> questList;


    public void setQuizToModify(Quiz service) {
        this.quizToModify = service;
        // Retrieve questions for the quiz and populate questList
        if (quizToModify != null) {
            questSet = qs.getAll(quizToModify.getId());
            questList = new ArrayList<>(questSet);
            // Populate the grid with questions
            populateGridWithQuestions();
        }
        displayServiceInfo();
    }


    private void displayServiceInfo() {
        if (quizToModify != null) {
            name.setText(quizToModify.getQuiz_name());
            desc.setText(quizToModify.getDesc_quiz());
            type.setText(quizToModify.getType());
            points.setText(String.valueOf(quizToModify.getPoints()));



        }
    }


    // Other code...

    private void populateGridWithQuestions() {
        // Clear existing grid content
        grid.getChildren().clear();

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < questList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/esprit/nnn/FXML/Admin/QuestionCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                anchorPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, null, new BorderWidths(2))));

                QuestionCardController itemController = fxmlLoader.getController();
                itemController.setData(questList.get(i));

                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                GridPane.setMargin(anchorPane, new Insets(8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> optionsPaiement = FXCollections.observableArrayList(
                "Nature",
                "Camping"
        );

        // Ajout des options de paiement au ComboBox
        Typeadd.setItems(optionsPaiement);
        // No need to populate the grid here since it's already populated in setQuizToModify()
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

@FXML
    void Updatequiz(ActionEvent event) {
    if (nameadd.getText().isEmpty() || descadd.getText().isEmpty() || Typeadd.getValue() == null || pointsadd.getText().isEmpty()) {
        showAlert("Veuillez remplir tous les champs.");
        return;
    }

    // Vérification si le champ points est un entier valide
    try {
        int pointsValue = Integer.parseInt(pointsadd.getText());
        if (pointsValue <= 0) {
            showAlert("Le nombre de points doit être supérieur à zéro.");
            return;
        }
    } catch (NumberFormatException e) {
        showAlert("Le nombre de points doit être un entier valide.");
        return;
    }
        // Récupérer les données modifiées de l'interface

        String nom =nameadd.getText();
        String desctex = descadd.getText();
        int points;

        try {
            points = Integer.parseInt(pointsadd.getText());
        } catch (NumberFormatException e) {
            // Affichez un message d'erreur si le prix est invalide
            System.err.println("Points must be Valid");
            return;
        }
        String typat = Typeadd.getValue();


        // Mettre à jour les propriétés du produit
        quizToModify.setQuiz_name(nom);
        quizToModify.setDesc_quiz(desctex);
        quizToModify.setType(typat);
        quizToModify.setPoints(points);


        // Mettre à jour le produit dans la base de données
        se.Update(quizToModify);

        // Afficher une alerte
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Product Updated");
        alert.setContentText("The selected product has been successfully updated.");
        alert.showAndWait();

        // Rafraîchir la TableView dans l'interface AfficherProduits
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/Admin/QuizList.fxml"));
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



    @FXML
    void addQuestion(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/Admin/QuestionAdd.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            QuestionAdd Listcontroller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement

            Stage newStage = new Stage();
            newStage.setTitle("Question detail"); // Set the title of the new window
            newStage.setScene(new Scene(root)); // Set the scene with the content loaded from the FXML file
            Listcontroller.getquiz(quizToModify);
            // Show the new window
            newStage.show();

        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }




    @FXML
    void BacktoList(ActionEvent event) {

        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/esprit/nnn/FXML/Admin/QuizList.fxml"));
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




    @FXML
    void QuizHome(ActionEvent event) {


        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/esprit/nnn/FXML/Admin/QuizHome.fxml"));
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
