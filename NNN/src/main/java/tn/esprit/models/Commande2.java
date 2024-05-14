package tn.esprit.models;

public class Commande2 {

    //Att
    private int id,prixtotale,user_id;
    private String produits;

    public Commande2() {
    }

    public Commande2(int prixtotale, int user_id, String produits) {
        this.prixtotale = prixtotale;
        this.user_id = user_id;
        this.produits = produits;
    }


    public Commande2(int id, int prixtotale, int user_id, String produits) {
        this.id = id;
        this.prixtotale = prixtotale;
        this.user_id = user_id;
        this.produits = produits;
    }


    //Getters and setters


    public String getProduits() {
        return produits;
    }

    public void setProduits(String produits) {
        this.produits = produits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrixtotale() {
        return prixtotale;
    }

    public void setPrixtotale(int prixtotale) {
        this.prixtotale = prixtotale;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

//Display



    @Override
    public String toString() {
        return "Commande2{" +
                "id=" + id +
                ", prixtotale=" + prixtotale +
                ", user_id=" + user_id +
                ", produits='" + produits + '\'' +
                '}';
    }
}
