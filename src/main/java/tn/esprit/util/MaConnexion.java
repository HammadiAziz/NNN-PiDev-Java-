package tn.esprit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {
    //DB
    final String URL="jdbc:mysql://localhost:3306/nnnesprit1";
    final String USR="root";
    final String PWD="";

    //att
    private Connection cnx;

    //Singleton step2 attr de type le nom que classe
    private static MaConnexion instance;
    //const
    //singleton step1: constructeur privé
    private MaConnexion() {
       try {
           cnx = DriverManager.getConnection(URL, USR, PWD);
           System.out.println("Connection etablie avec succes");
       }catch(SQLException e){
            throw new RuntimeException(e);
       }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static MaConnexion getInstance() {
      //Singelon step3
       if(instance == null)
           instance = new MaConnexion();
        return instance;
    }
}
