package tn.esprit;

import tn.esprit.util.MaConnexion;
import tn.esprit.services.RecService;
import tn.esprit.models.*;

public class Main {
    public static void main(String[] args) {

        MaConnexion M1 =  MaConnexion.getInstance();

        RecService rs = new RecService();
        Reclamation rec = new Reclamation("Certification","Obtenir certification","Quiz","NonTraite",54);
        //rs.insert(rec);
        //System.out.println(rs.getAll());
        //System.out.println(rs.getOne(9));
        //rs.delete(10);
        //rs.update(rec,54);

    }

}