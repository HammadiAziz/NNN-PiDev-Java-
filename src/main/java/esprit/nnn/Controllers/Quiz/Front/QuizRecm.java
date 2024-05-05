package esprit.nnn.Controllers.Quiz.Front;

import esprit.nnn.Models.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class QuizRecm {

    @FXML
    private Button details;

    @FXML
    private Label name;

    @FXML
    private Label type;


    private Quiz quiza;





    public void setdata(Quiz quiz){
        this.quiza = quiz;
        name.setText(quiz.getQuiz_name());
        type.setText(quiz.getType());
    }


    @FXML
    void showdetails(ActionEvent event) {


    }



}
