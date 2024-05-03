package esprit.nnn.Services;

import com.mysql.cj.xdevapi.Statement;
import esprit.nnn.Models.Quiz;
import esprit.nnn.Utils.Maconnection;
import esprit.nnn.interfaces.Iservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuizService implements Iservices<Quiz> {



    static Connection connection;

    static {
        try {
            connection = Maconnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }





    @Override
    public int Create(Quiz quiz) {
        int result = 0;
        String req = "INSERT INTO quiz (quiz_name,desc_quiz,type,points)" +
                "VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement statement = this.connection.prepareStatement(req);

            statement.setString(1, quiz.getQuiz_name());
            statement.setString(2, quiz.getDesc_quiz());
            statement.setString(3, quiz.getType());
            statement.setInt(4, quiz.getPoints());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Quiz> Read() {
        List<Quiz> quizzes = new ArrayList<>();
        String req = "SELECT * FROM Quiz";
        try {
            PreparedStatement statement = this.connection.prepareStatement(req);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int quizid = resultSet.getInt("id");
                String name = resultSet.getString("quiz_name");
                String desc = resultSet.getString("desc_quiz");
                String type = resultSet.getString("type");
                int point= resultSet.getInt("points");
                quizzes.add(new Quiz(quizid, name, desc, type, point));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizzes;
    }

    @Override
    public int Update(Quiz quiz) {
        int result = 0;
        String req = "UPDATE quiz SET  quiz_name=?, desc_quiz=?,type=?, points=? WHERE id=?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(req);
            statement.setString(1, quiz.getQuiz_name());
            statement.setString(2, quiz.getDesc_quiz());
            statement.setString(3, quiz.getType());
            statement.setInt(4  , quiz.getPoints());

            statement.setInt(5, quiz.getId());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int Delete(int id) {
        int result = 0;
        String quizDeleteQuery = "DELETE FROM quiz WHERE id=?";
        String questionDeleteQuery = "DELETE FROM question WHERE quiz_id=?";
        try {
            // Deleting questions
            PreparedStatement questionStatement = this.connection.prepareStatement(questionDeleteQuery);
            questionStatement.setInt(1, id);
            result += questionStatement.executeUpdate();
            // Deleting quiz
            PreparedStatement quizStatement = this.connection.prepareStatement(quizDeleteQuery);
            quizStatement.setInt(1, id);
            result += quizStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }



    @Override
    public Set<Quiz> getAll() {

        Set<Quiz> offreList = new HashSet<>();


        String req = "SELECT * FROM quiz";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int quizid = rs.getInt("id");
                String name = rs.getString("quiz_name");
                String desc = rs.getString("desc_quiz");
                String type = rs.getString("type");
                int point= rs.getInt("points");
                Quiz quiz=new Quiz(quizid, name, desc, type, point);


                offreList.add(quiz);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des offres : " + e.getMessage());
        }
        return offreList;
    }


    @Override
    public Quiz getOneByID(int id) {
        Quiz commande = null;
        String req = "SELECT * FROM quiz WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int quizid = rs.getInt("id");
                String name = rs.getString("quiz_name");
                String desc = rs.getString("desc_quiz");
                String type = rs.getString("type");
                int point= rs.getInt("points");
                Quiz quiz=new Quiz(quizid, name, desc, type, point);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la commande : " + e.getMessage());
        }
        return commande;
    }




}
