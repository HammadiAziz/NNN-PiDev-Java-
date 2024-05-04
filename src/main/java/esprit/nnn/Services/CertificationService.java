package esprit.nnn.Services;

import esprit.nnn.Models.Certification;
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

public class CertificationService implements Iservices<Certification> {
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
    public int Create(Certification certif) {
        int result = 0;
        String req = "INSERT INTO certification (quiz_id,user_id)" +
                "VALUES (?, ?);";
        try {
            PreparedStatement statement = this.connection.prepareStatement(req);

            statement.setInt(1, certif.getQuiz_id());
            statement.setInt(2, certif.getUser_id());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Certification> Read() {
        List<Certification> quizzes = new ArrayList<>();

        return quizzes;
    }

    @Override
    public int Update(Certification certif) {
        int result = 0;

        return result;
    }

    @Override
    public int Delete(int id) {
        int result = 0;

        return result;
    }



    @Override
    public Set<Certification> getAll() {

        Set<Certification> offreList = new HashSet<>();



        return offreList;
    }


    @Override
    public Certification getOneByID(int id) {
        Certification commande = null;

        return commande;
    }



}
