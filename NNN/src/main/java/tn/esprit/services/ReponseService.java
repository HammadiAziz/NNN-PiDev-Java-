package tn.esprit.services;

import tn.esprit.util.MaConnexion;
import tn.esprit.entities.Reponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReponseService {

	//att
	Connection cnx= MaConnexion.getInstance().getCnx();

	public void add_Rep(Reponse rep) {
		String query = "INSERT INTO reponse (description_rep, rep_rec_id) VALUES (?, ?)";

		try {
			PreparedStatement statement = cnx.prepareStatement(query);
			statement.setString(1, rep.getDescription_rep());
			statement.setInt(2, rep.getRep_rec_id()); // Assurez-vous que la méthode getRep_rec_id() retourne l'ID de la réclamation associée à cette réponse

			// Exécutez la requête
			statement.executeUpdate();
			System.out.println("Reponse ajoutée avec succès");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public void update_Rep(Reponse rep){
		String query = "UPDATE reponse SET description_rep = ? WHERE id = ?";
		try {
			PreparedStatement statement = cnx.prepareStatement(query);
			statement.setString(1, rep.getDescription_rep());
			statement.setInt(2, rep.getId());
			statement.executeUpdate();
			System.out.println("Reponse mise à jour avec succès");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete_Rep(int id){

	}

	public List<Reponse> getAll_Rep(int id){

		List<Reponse> reps = new ArrayList<>() ;
		String query = "SELECT * FROM reponse WHERE rep_rec_id= ?";

		try{
			PreparedStatement statement = cnx.prepareStatement(query);
			statement.setInt(1,id);
			ResultSet res = statement.executeQuery();
			while (res.next()){

				Reponse rep = new Reponse();
				rep.setId(res.getInt("id"));

				rep.setDescription_rep(res.getString("description_rep"));

				reps.add(rep);
			}

		}catch(SQLException e ){
			throw new RuntimeException(e);
		}

		return reps;
	}


	public Reponse getOne_Rep(int id){

		String req = "SELECT * FROM reponse WHERE id = ?";
		Reponse rep = null;

		try {
			PreparedStatement ps = cnx.prepareStatement(req);
			ps.setInt(1, id);
			ResultSet res = ps.executeQuery();

			if (res.next()) {
				rep = new Reponse();
				rep.setId(res.getInt("id"));

				rep.setDescription_rep(res.getString("description_rep"));

			} else {
				System.out.println("No reponse found with ID: " + id);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return rep;
	}

	public void updateReclamationStatus(int reclamationId, String status) throws SQLException {
		String query = "UPDATE reclamation SET etat = ? WHERE id = ?";

		try {
			PreparedStatement statement = cnx.prepareStatement(query);
			statement.setString(1, status);
			statement.setInt(2, reclamationId);
			statement.executeUpdate();
			System.out.println("État de la réclamation mis à jour avec succès");
		} catch (SQLException e) {
			throw e;
		}
	}
	public String getusermail(int id){

		String req = "SELECT email FROM user WHERE id = ?";

		try {
			PreparedStatement ps = cnx.prepareStatement(req);
			ps.setInt(1, id);
			ResultSet res = ps.executeQuery();

			if (res.next()) {
				return res.getString("email");
			} else {
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return null;
	}
}
