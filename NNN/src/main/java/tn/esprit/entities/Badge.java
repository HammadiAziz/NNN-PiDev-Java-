package tn.esprit.entities;

public class Badge {
    int id ;
    String nombadge;
    int prix ;

    public Badge() {
    }

    public Badge(int id, String nombadge, int prix) {
        this.id = id;
        this.nombadge = nombadge;
        this.prix = prix;
    }

    public Badge(String nombadge, int prix) {
        this.nombadge = nombadge;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombadge() {
        return nombadge;
    }

    public void setNombadge(String nombadge) {
        this.nombadge = nombadge;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
}
