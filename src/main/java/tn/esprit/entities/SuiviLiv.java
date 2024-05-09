package tn.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class SuiviLiv {
    private int id ;
    private boolean livree ;
    private String location ,dateCommande ;

    public SuiviLiv(int id, String dateCommande, boolean livree, String location) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.livree = livree;
        this.location = location;
    }

    public SuiviLiv(String dateCommande, boolean livree, String location) {
        this.dateCommande = dateCommande;
        this.livree = livree;
        this.location = location;
    }

    public SuiviLiv() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande;
    }

    public boolean isLivree() {
        return livree;
    }

    public void setLivree(boolean livree) {
        this.livree = livree;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "SuiviLiv{" +
                "id=" + id +
                ", dateCommande=" + dateCommande +
                ", livree=" + livree +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SuiviLiv)) return false;
        SuiviLiv suiviLiv = (SuiviLiv) o;
        return getId() == suiviLiv.getId() && isLivree() == suiviLiv.isLivree() && Objects.equals(getDateCommande(), suiviLiv.getDateCommande()) && Objects.equals(getLocation(), suiviLiv.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isLivree(), getLocation(), getDateCommande());
    }
}
