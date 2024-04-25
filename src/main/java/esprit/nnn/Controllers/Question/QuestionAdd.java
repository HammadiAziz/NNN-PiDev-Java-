package esprit.nnn.Controllers.Question;

import esprit.nnn.Controllers.Quiz.QuizDetailsController;
import esprit.nnn.Models.Questions;
import esprit.nnn.Models.Quiz;
import esprit.nnn.Services.QuestionsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

}
