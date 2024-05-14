package tn.esprit.entities;

import java.util.Date;

public class HistoryQuiz {


    int id;
    int quiz_id;
    int user_id;
    Date quiz_date;
    int result;




    public HistoryQuiz(int quiz_id, int user_id, int result) {
        this.quiz_id = quiz_id;
        this.user_id = user_id;

        this.result = result;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getQuiz_date() {
        return quiz_date;
    }

    public void setQuiz_date(Date quiz_date) {
        this.quiz_date = quiz_date;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
