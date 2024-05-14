package tn.esprit.entities;

public class Reponse {

	private int id;
	private String description_rep;
	private int rep_rec_id;

	public Reponse() {}

	public Reponse(int id, String description_rep) {
		this.id = id;
		this.description_rep = description_rep;
	}

	public Reponse(int id, String description_rep, int rep_rec_id) {
		this.id = id;
		this.description_rep = description_rep;
		this.rep_rec_id = rep_rec_id;
	}

	public Reponse(String description_rep) {
		this.description_rep = description_rep;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription_rep() {
		return description_rep;
	}

	public void setDescription_rep(String description_rep) {
		this.description_rep = description_rep;
	}

	public int getRep_rec_id() {
		return rep_rec_id;
	}

	public void setRep_rec_id(int rep_rec_id) {
		this.rep_rec_id = rep_rec_id;
	}

	@Override
	public String toString() {
		return "Reponse{" +
				       "id=" + id +
				       ", description_rep='" + description_rep + '\'' +
				       '}';
	}

}
