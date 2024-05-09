package tn.esprit.tests;

import tn.esprit.entitiesModel.Livraison;
import tn.esprit.entitiesModel.SuiviLiv;
import tn.esprit.service.LivraisonService;
import tn.esprit.service.SuiviService;



import java.util.Date;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
       //Data nour = Data.getInstance();
       //Data nour1 =Data.getInstance();
      // Data nour2 =Data.getInstance();
        /////////////////livraison
        LivraisonService liv = new LivraisonService() ;
        //Livraison livraison1 = new Livraison("Nour Tzedet Tw","Aboussaoud","Cash","Tunisia","ariana","nour@esprit.tn", 27596886);
        Livraison livraison2 = new Livraison( "Nour insert1zdit","Aboussaoud","Cash","Tunisia","ariana","nour@esprit.tn", 27596886);
        //ajout
       ///liv.add(livraison2);
        // Modifier la livraison ajoutée
        //liv.Update(21 ,livraison2);
        ///delete
        //liv.Delete(23);
        ///insert
       // System.out.println(liv.getAll());
        //System.out.println(liv.getOneByID(20));
        //////////////////suivi
        SuiviService suiviLiv = new SuiviService();
        SuiviLiv sui = new SuiviLiv("15 / 7 / 2005",true,"tunisiaaaEdit");
        //add suiLiv
       // suiviLiv.add(sui);
        /////update suivi
       // suiviLiv.Update(7,sui);
        ///delete Sui
       // suiviLiv.Delete(2);
        ///////getall
        //System.out.println(suiviLiv.getAll());
        //getOne
       // System.out.println(suiviLiv.getOneByID(6));





    }
}