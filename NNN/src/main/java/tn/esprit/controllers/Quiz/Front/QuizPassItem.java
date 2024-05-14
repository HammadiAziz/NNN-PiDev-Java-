package tn.esprit.controllers.Quiz.Front;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import tn.esprit.entities.Questions;

public class QuizPassItem {

    @FXML
    private RadioButton ch1;

    @FXML
    private RadioButton ch2;

    @FXML
    private RadioButton ch3;

    @FXML
    private RadioButton ch4;

    @FXML
    private Label pts;

    @FXML
    private Label text;

    private ToggleGroup toggleGroup = new ToggleGroup();
    private Questions questa;


    public void initialize() {
        ch1.setToggleGroup(toggleGroup);
        ch2.setToggleGroup(toggleGroup);
        ch3.setToggleGroup(toggleGroup);
        ch4.setToggleGroup(toggleGroup);
    }

    public void setData(Questions quest) {
        this.questa = quest;
        ch1.setText(questa.getChoix1());
        ch2.setText(questa.getChoix2());
        ch3.setText(questa.getChoix3());
        ch4.setText(questa.getChoix4());
        pts.setText(String.valueOf(questa.getPoints()));


        text.setText(questa.getText());


    }

    public String getUserAnswer() {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            return selectedRadioButton.getText();
        }
        return null;
    }

    public Questions getQuestion() {
        return questa; // Provide access to the associated Questions object
    }




}
