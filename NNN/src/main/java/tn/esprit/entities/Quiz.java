package tn.esprit.entities;


import java.util.List;

public class Quiz {

    int id;
    String quiz_name,desc_quiz,type;
    int points;

    List<Questions> questions;

    public Quiz(int id, String quiz_name, String desc_quiz, String type, int points) {
        this.id = id;
        this.quiz_name = quiz_name;
        this.desc_quiz = desc_quiz;
        this.type = type;
        this.points = points;
    }
    public Quiz(String quiz_name, String desc_quiz, String type, int points) {

        this.quiz_name = quiz_name;
        this.desc_quiz = desc_quiz;
        this.type = type;
        this.points = points;
    }

    public Quiz() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public String getDesc_quiz() {
        return desc_quiz;
    }

    public void setDesc_quiz(String desc_quiz) {
        this.desc_quiz = desc_quiz;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }
}
