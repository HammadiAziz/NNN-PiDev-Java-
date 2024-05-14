package tn.esprit.controllers.Quiz.Front;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import tn.esprit.controllers.Quiz.Front.Certification.result;
import tn.esprit.controllers.Quiz.QuizshController;
import tn.esprit.entities.Questions;
import tn.esprit.entities.Quiz;
import tn.esprit.services.QuestionsService;
import tn.esprit.services.QuizService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QuizPass {

    @FXML
    private GridPane grid;




    private final QuizService se = new QuizService();

    private Quiz quizToModify;
    private final QuestionsService us = new QuestionsService();

    private List<QuizPassItem> quizPassItemList;


    private QuestionsService qs = new QuestionsService();

    Set<Questions> questSet;
    List<Questions> questList;





    public void setQuizTopass(Quiz service) {
        this.quizToModify = service;
        // Retrieve questions for the quiz and populate questList
        if (quizToModify != null) {
            questSet = qs.getAll(quizToModify.getId());
            questList = new ArrayList<>(questSet);
            // Populate the grid with questions
            populateGridWithQuestions();
        }

    }


    private void populateGridWithQuestions() {
        // Clear existing grid content
        grid.getChildren().clear();

        int column = 0;
        int row = 1;
        quizPassItemList = new ArrayList<>();
        try {
            for (int i = 0; i < questList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/FXML/Quiz/User/QuizPassitem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                anchorPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, null, new BorderWidths(2))));

                QuizPassItem itemController = fxmlLoader.getController();
                itemController.setData(questList.get(i));

                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                GridPane.setMargin(anchorPane, new Insets(8));
                quizPassItemList.add(itemController);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void checkAnswers(ActionEvent event) {
        int score = 0;
        for (QuizPassItem item : quizPassItemList) {
            String userAnswer = item.getUserAnswer();
            if (userAnswer != null) {
                Questions question = item.getQuestion(); // Access the associated Questions object
                if (userAnswer.equals(question.getCorrect())) {
                    score += 1;
                }
            }
        }

        int totalQuestions = quizPassItemList.size();



        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/User/Result.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            result Listcontroller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();
Listcontroller.setdata(1,score,totalQuestions,quizToModify.getQuiz_name(),quizToModify.getId());
        // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }



    }


    @FXML
    void backtolist(ActionEvent event) {
        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/User/QuizList.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            QuizList controller = loader.getController();
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
