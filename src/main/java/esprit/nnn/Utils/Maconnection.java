package esprit.nnn.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Maconnection {


    private static Maconnection instance;
    private Connection cn;
    private final String databaseUrl = "localhost:3306";
    private final String username = "root";
    private final String password = "";
    private final String databaseName = "integration";

    private Maconnection() {
    }

    public static Maconnection getInstance(){
        if(instance == null)
            instance = new Maconnection();
        return instance;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if(cn == null) {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection(
                    "jdbc:mysql://" + databaseUrl + "/" + databaseName
                    , username, password
            );
            System.out.println("Connected to Database");
        }

        return cn;
    }

}
