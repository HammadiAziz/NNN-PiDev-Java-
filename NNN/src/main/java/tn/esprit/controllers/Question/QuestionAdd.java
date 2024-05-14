package tn.esprit.controllers.Question;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.controllers.Quiz.QuizDetailsController;
import tn.esprit.controllers.Quiz.QuizHomecontroller;
import tn.esprit.entities.Questions;
import tn.esprit.entities.Quiz;
import tn.esprit.services.QuestionsService;

import java.io.IOException;

public class QuestionAdd {

    @FXML
    private TextField ch1;

    @FXML
    private TextField ch2;

    @FXML
    private TextField ch3;

    @FXML
    private TextField ch4;

    @FXML
    private TextField correct;

    @FXML
    private TextField points;

    @FXML
    private TextField text;

    Quiz quiz;
    QuestionsService questionsService=new QuestionsService();
    public void getquiz(Quiz quiz) {
        this.quiz=quiz;

    }

    @FXML
    void add(ActionEvent event) {
        if (text.getText().isEmpty() || ch1.getText().isEmpty() || ch2.getText().isEmpty() ||ch3.getText().isEmpty() ||ch4.getText().isEmpty() ||correct.getText().isEmpty() || points.getText().isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        String correctValue = correct.getText();
        if (!correctValue.equals(ch1.getText()) && !correctValue.equals(ch2.getText()) && !correctValue.equals(ch3.getText()) && !correctValue.equals(ch4.getText())) {
            showAlert("Le champ 'correct' doit être égal à l'une des valeurs dans 'choix1', 'choix2' ou 'choix3' ou 'choix4'");
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

        Questions questions = new Questions(Integer.parseInt(points.getText()),quiz.getId(),text.getText(),ch1.getText(),ch2.getText(), ch3.getText(),ch4.getText(),correct.getText());
     questionsService.Create(quiz.getId(),questions);



        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/esprit/nnn/FXML/Admin/QuizDetails.fxml"));
            Parent root = loader.load();


            QuizDetailsController modifierServiceController = loader.getController();


            modifierServiceController.setQuizToModify(quiz);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Close the stage
            stage.close();
            Scene currentScene = ((Node) event.getSource()).getScene();
            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
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
    void clear(ActionEvent event) {

        ch1.clear();
        ch2.clear();
        ch3.clear();
        ch4.clear();
        correct.clear();
        points.clear();
        text.clear();

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
