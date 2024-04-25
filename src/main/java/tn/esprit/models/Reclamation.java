package tn.esprit.models;
import java.util.List;
import tn.esprit.models.Reponse;

public class Reclamation {

    //Att
    private String object,description,categorie,etat;
    private int id;
    //List Reclamation
    private List<Reponse> reponses;

    //cont

    public Reclamation(String object, String description, String categorie, String etat, int id) {
        this.object = object;
        this.description = description;
        this.categorie = categorie;
        this.etat = etat;
        this.id = id;
    }

    public Reclamation(String object, String description, String categorie, String etat) {
        this.object = object;
        this.description = description;
        this.categorie = categorie;
        this.etat = etat;
    }

    public Reclamation() {
    }


    public String getObject() {
        return object;
    }

    public String getDescription() {
        return description;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getEtat() {
        return etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String toString() {
        return "Reclamation{" +
                       "id=" + id +
                       ", object='" + object + '\'' +
                       ", description='" + description + '\'' +
                       ", categorie='" + categorie + '\'' +
                       ", etat='" + etat + '\'' +
                       '}';
    }

    public List<Reponse> getReponses() {
        return reponses;
    }

    public void setReponses(List<Reponse> reponses) {
        this.reponses = reponses;
    }
}
