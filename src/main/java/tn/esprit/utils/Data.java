package tn.esprit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Data {
    ///DB
    final String URL = "jdbc:mysql://localhost:3306/integration";
    final String USR="root";
    final String PWD="";
    //att
   private Connection cnx ;
   private static Data instance ;
   //constructor
    ///sengleton step1
   private Data(){
        try {
            cnx= DriverManager.getConnection(URL , USR, PWD) ;
            System.out.println("connexion etablie avec succes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static Data getInstance() {
       if(instance==null)
           instance = new Data() ;
        return instance;
    }
}
