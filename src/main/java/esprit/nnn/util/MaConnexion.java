package esprit.nnn.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {

    //DB
    final String URL = "jdbc:mysql://localhost:3306/integration";
    final String USER = "root";
    final String PWD = "";

    //att
    private Connection cnx;

    //singleton step 2 création d'une variable de même type que la classe

    private static MaConnexion instance;

    //constructeur

    //singleton step 1 make constructor private

    private MaConnexion(){
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("connection established");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    //step 3 check if instance null

    public static MaConnexion getInstance() {
        if(instance == null) {
            instance = new MaConnexion();
        }
        return instance;
    }
}
