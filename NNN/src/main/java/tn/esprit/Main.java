package tn.esprit;

import tn.esprit.models.Commande2;
import tn.esprit.models.Produit2;
import tn.esprit.services.Commande2Service;
import tn.esprit.services.Produit2Service;
import tn.esprit.util.MaConnexion;
import tn.esprit.services.ReclamationService;

public class Main {
    public static void main(String[] args) {
        //unique connection ( singleton )
        //MaConnexion mac = MaConnexion.getInstance();

        Produit2Service pr = new Produit2Service();
        //ReclamationService rs = new ReclamationService();

        //Commande2Service cm = new Commande2Service();

        //tester add produit
        //Produit2 produit2 = new Produit2("cat",22,"http","a","c");
        //pr.add(produit2);



        //Delete produit
       //pr.delete(89);

        //delete commande
        //cm.delete(90);

        //update product
        //Produit2 produit2 = new Produit2("upppp",222,"https","jj","kk");
        //pr.update(91 ,produit2);


        //affichage All produits
      //System.out.println(pr.getAll());

        //affichage All commandes
        //System.out.println(cm.getAll());

        //afficher un produit par id
        //System.out.println(pr.getOne(89));
        //System.out.println(pr.getOne(90));

        //System.out.println(rs.getAllByUserId(8));

    }
}