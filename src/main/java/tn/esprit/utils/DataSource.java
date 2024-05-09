package tn.esprit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private final String URL = "jdbc:mysql://127.0.0.1:3306/integration";
    private final String USER = "root";
    private final String PWD = "";
    private Connection cnx;

    private static DataSource instance;

    public DataSource(){
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("Connected to DB !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DataSource getInstance(){
        if(instance == null)
            instance = new DataSource();
        return instance;
    }

    public Connection getCnx(){
        return cnx;
    }
}
