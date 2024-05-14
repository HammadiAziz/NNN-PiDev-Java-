package tn.esprit.entities;

import java.util.List;

public class Reclamation {

	//Att
	private String object,description,categorie,etat;
	private int id,id_user;
	//List Reclamation
	private List<Reponse> reponses;

	//cont

	public Reclamation(String object, String description, String categorie, String etat, int id,int id_user) {
		this.object = object;
		this.description = description;
		this.categorie = categorie;
		this.etat = etat;
		this.id = id;
		this.id_user=id_user;
	}

	public Reclamation(String object, String description, String categorie, String etat,int id_user) {
		this.object = object;
		this.description = description;
		this.categorie = categorie;
		this.etat = etat;
		this.id_user=id_user;
	}

	public Reclamation() {
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
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

	@Override
	public String toString() {
		return "Reclamation{" +
				       "object='" + object + '\'' +
				       ", description='" + description + '\'' +
				       ", categorie='" + categorie + '\'' +
				       ", etat='" + etat + '\'' +
				       ", id=" + id +
				       ", id_user=" + id_user +
				       ", reponses=" + reponses +
				       '}';
	}

	public List<Reponse> getReponses() {
		return reponses;
	}

	public void setReponses(List<Reponse> reponses) {
		this.reponses = reponses;
	}
}
