package esprit.nnn.Services;

import esprit.nnn.Models.Certification;
import esprit.nnn.Models.HistoryQuiz;
import esprit.nnn.Utils.Maconnection;
import esprit.nnn.interfaces.Iservices;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class historyService implements Iservices <HistoryQuiz> {



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
    public int Create(HistoryQuiz hist) {
        int result = 0;
        String req = "INSERT INTO  history_quiz (quiz_id,user_id,date,result)" +
                "VALUES (?, ?,NOW(),?);";
        try {
            PreparedStatement statement = this.connection.prepareStatement(req);

            statement.setInt(1, hist.getQuiz_id());
            statement.setInt(2, hist.getUser_id());

            statement.setInt(3, hist.getResult());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<HistoryQuiz> Read() {
        List<HistoryQuiz> quizzes = new ArrayList<>();

        return quizzes;
    }

    @Override
    public int Update(HistoryQuiz hist) {
        int result = 0;

        return result;
    }

    @Override
    public int Delete(int id) {
        int result = 0;

        return result;
    }



    @Override
    public Set<HistoryQuiz> getAll() {

        Set<HistoryQuiz> offreList = new HashSet<>();



        return offreList;
    }


    @Override
    public HistoryQuiz getOneByID(int id) {
        HistoryQuiz commande = null;

        return commande;
    }



}
