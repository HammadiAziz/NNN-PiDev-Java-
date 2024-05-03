package esprit.nnn.Controllers.Quiz.Front;

import esprit.nnn.Controllers.Quiz.QuizDetailsController;
import esprit.nnn.Models.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Quizitem {

    @FXML
    private Label desc;

    @FXML
    private Label name;

    @FXML
    private Button pass;
    @FXML
    private Label type;


    private Quiz quiza;
    public void setData(Quiz quiz) {
        this.quiza = quiz;
        name.setText(quiz.getQuiz_name());
        desc.setText(quiz.getDesc_quiz());
        type.setText(quiz.getType());


    }
    @FXML
    void QuizPlay(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/esprit/nnn/FXML/User/QuizPass.fxml"));
            Parent root = loader.load();


            QuizPass modifierServiceController = loader.getController();


            modifierServiceController.setQuizTopass(quiza);

            pass.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

}
