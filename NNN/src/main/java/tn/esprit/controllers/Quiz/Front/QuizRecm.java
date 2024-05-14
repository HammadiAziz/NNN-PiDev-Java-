package tn.esprit.controllers.Quiz.Front;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tn.esprit.entities.Quiz;

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
