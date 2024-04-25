package esprit.nnn.Controllers.Question;

import esprit.nnn.Models.Questions;
import esprit.nnn.Models.Quiz;
import esprit.nnn.Services.QuestionsService;
import esprit.nnn.Services.QuizService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class QuestionDetailsController {

    @FXML
    private Label ch1;

    @FXML
    private TextField ch1up;

    @FXML
    private Label ch2;

    @FXML
    private TextField ch2up;

    @FXML
    private Label ch3;

    @FXML
    private TextField ch3up;

    @FXML
    private Label ch4;

    @FXML
    private TextField ch4up;

    @FXML
    private Label correct;

    @FXML
    private TextField correctup;

    @FXML
    private Label points;

    @FXML
    private TextField pointup;

    @FXML
    private Label text;

    @FXML
    private TextField textup;

    private final QuestionsService se = new QuestionsService();

    private Questions questomodify ;

    public void setQuesToModify(Questions service) {
        this.questomodify = service;
        displayServiceInfo();


    }
    private void displayServiceInfo() {
        if (questomodify != null) {
            text.setText(questomodify.getText());
            ch1.setText(questomodify.getChoix1());
            ch2.setText(questomodify.getChoix2());
            ch3.setText(questomodify.getChoix3());
            ch4.setText(questomodify.getChoix4());
            correct.setText(questomodify.getCorrect());
            points.setText(String.valueOf(questomodify.getPoints()));



        }
    }
    @FXML
    void clear(ActionEvent event) {
        textup.clear();
        ch1up.clear();
        ch2up.clear();
        ch3up.clear();
        ch4up.clear();
        correctup.clear();
        pointup.clear();

    }

    @FXML
    void update(ActionEvent event) {


        // Récupérer les données modifiées de l'interface

        questomodify.setText(textup.getText());
        questomodify.setChoix1(ch1up.getText());
        questomodify.setChoix2(ch2up.getText());
        questomodify.setChoix3(ch3up.getText());
        questomodify.setChoix4(ch4up.getText());
        questomodify.setCorrect(correct.getText());
        questomodify.setPoints(Integer.parseInt(pointup.getText()));

        String text =textup.getText();
        String ch1=ch1up.getText();
        String ch2=ch2up.getText();
        String ch3=ch3up.getText();
        String ch4=ch4up.getText();
        String correct=correctup.getText();

        int points;

        try {
            points = Integer.parseInt(pointup.getText());
        } catch (NumberFormatException e) {
            // Affichez un message d'erreur si le prix est invalide
            System.err.println("Points must be Valid");
            return;
        }



        // Mettre à jour les propriétés du produit



        // Mettre à jour le produit dans la base de données
        se.Update(questomodify);

        // Afficher une alerte
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Product Updated");
        alert.setContentText("The selected product has been successfully updated.");
        alert.showAndWait();

        // Rafraîchir la TableView dans l'interface AfficherProduits
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Close the stage
        stage.close();

    }



}
