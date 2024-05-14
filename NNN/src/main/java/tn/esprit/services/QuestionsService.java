package tn.esprit.services;

import com.mysql.cj.xdevapi.Statement;

import tn.esprit.entities.Questions;
import tn.esprit.util.MaConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuestionsService  {


    Connection connection = MaConnexion.getInstance().getCnx();

    public int Create(int id , Questions questions) {
        int result = 0;
        String req = "INSERT INTO question (quiz_id,text,choix1,choix2,choix3,choix4,points,correct)" +
                "VALUES (?, ?, ?, ?,?,?,?,?);";
        try {
            PreparedStatement statement = this.connection.prepareStatement(req);
            statement.setInt(1, id);
            statement.setString(2, questions.getText());
            statement.setString(3, questions.getChoix1());
            statement.setString(4, questions.getChoix2());
            statement.setString(5, questions.getChoix3());
            statement.setString(6, questions.getChoix4());
            statement.setInt(7, questions.getPoints());
            statement.setString(8, questions.getCorrect());



            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }





    public int Update(Questions questions) {
        int result = 0;
        String req = "UPDATE question SET  text=?,choix1=?,choix2=?,choix3=?,choix4=?,points=?,correct=? WHERE id=?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(req);

            statement.setString(1, questions.getText());
            statement.setString(2, questions.getChoix1());
            statement.setString(3, questions.getChoix2());
            statement.setString(4, questions.getChoix3());
            statement.setString(5, questions.getChoix4());
            statement.setInt(6, questions.getPoints());
            statement.setString(7, questions.getCorrect());
            statement.setInt(8, questions.getId());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    public int Delete(int id) {
        int result = 0;
        String quizDeleteQuery = "DELETE FROM question WHERE id= "+ id;

        try {
            // Deleting quiz
            PreparedStatement quizStatement = this.connection.prepareStatement(quizDeleteQuery);

            result += quizStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }




    public Set<Questions> getAll(int id) {

        Set<Questions> offreList = new HashSet<>();


        String req = "SELECT * FROM question WHERE quiz_id="+ id;
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idq = rs.getInt("id");
                int quizid = rs.getInt("quiz_id");
                String text = rs.getString("text");
                String choix1 = rs.getString("choix1");
                String choix2 = rs.getString("choix2");
                String choix3 = rs.getString("choix3");
                String choix4 = rs.getString("choix4");
                int points = rs.getInt("points");
                String correct = rs.getString("correct");
                Questions quiz=new Questions(idq,points,quizid,text,choix1,choix2,choix3,choix4,correct) ;



                    offreList.add(quiz);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des offres : " + e.getMessage());
        }
        return offreList;
    }


    public Questions getOneByID(int id) {
        Questions commande = null;
        String req = "SELECT * FROM question WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idq = rs.getInt("id");
                int quizid = rs.getInt("quiz_id");
                String text = rs.getString("text");
                String choix1 = rs.getString("choix1");
                String choix2 = rs.getString("choix2");
                String choix3 = rs.getString("choix3");
                String choix4 = rs.getString("choix4");
                int points = rs.getInt("points");
                String correct = rs.getString("correct");


                Questions quest = new Questions(points, quizid, text, choix1, choix2, choix3, choix4, correct) ;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la commande : " + e.getMessage());
        }
        return commande;
    }






}
