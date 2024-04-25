package tn.esprit.services;
import java.sql.*;

import tn.esprit.models.Reponse;
import tn.esprit.util.MaConnexion;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.interfaces.*;
import tn.esprit.models.*;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class RepService implements IReponse<Reponse> {

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


	public void update_Rep(Reponse rep, int id){
		String query = "UPDATE reponse SET description_rep = ? WHERE id = ?";
		try {
			PreparedStatement statement = cnx.prepareStatement(query);
			statement.setString(1, rep.getDescription_rep());
			statement.setInt(2, id);
			statement.executeUpdate();
			System.out.println("Reponse mise à jour avec succès");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete_Rep(int id){

	}

	public List<Reponse> getAll_Rep(){

		List<Reponse> reps = new ArrayList<>() ;
		String req = "SELECT * FROM reponse";

		try{
			Statement st = cnx.createStatement();
			ResultSet res = st.executeQuery(req);
			while (res.next()){

				Reponse rep = new Reponse();
				rep.setId(res.getInt("id"));

				rep.setDescription_rep(res.getString(2));

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

}
