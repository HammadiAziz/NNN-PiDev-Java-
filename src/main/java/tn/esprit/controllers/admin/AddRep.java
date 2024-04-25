package tn.esprit.controllers.admin;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import tn.esprit.models.Reponse;
import java.sql.SQLException;
import tn.esprit.services.RepService;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import tn.esprit.controllers.*;
import tn.esprit.models.Reclamation;


public class AddRep {

	@FXML
	private TextField description_rep;

	private final RepService reps = new RepService();

	@FXML
	private TextField idRecRep;

	// Déclarez une variable pour stocker l'ID de la réclamation sélectionnée
	private int selectedReclamationId;

	// Méthode setter pour l'ID de la réclamation sélectionnée
	public void setSelectedReclamationId(int selectedReclamationId) {
		this.selectedReclamationId = selectedReclamationId;
	}

	@FXML
	void addRep(ActionEvent event) {
		// Récupérer la valeur du champ description_rep
		String description = description_rep.getText();

		// Vérifier si la description est vide
		if (description.isEmpty()) {
			// Afficher une alerte pour informer l'utilisateur
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("Veuillez entrer une description");
			alert.showAndWait();
			return;
		}

		// Créer un objet Reponse avec la description et l'ID de la réclamation sélectionnée
		Reponse rep = new Reponse();
		rep.setDescription_rep(description);
		rep.setRep_rec_id(selectedReclamationId);

		// Appeler la méthode add_Rep() du service RepService pour ajouter la réponse
		reps.add_Rep(rep);

		// Afficher une confirmation à l'utilisateur
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Succès");
		alert.setHeaderText(null);
		alert.setContentText("La réponse a été ajoutée avec succès");
		alert.showAndWait();

		// Effacer le champ description_rep
		description_rep.clear();
	}

	@FXML
	void listReclamation(ActionEvent event) {
		try {
			// Charger le fichier FXML de la classe Affiche_Rec
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/ListRec.fxml"));
			Parent root = loader.load();

			// Obtenir le contrôleur de la classe Affiche_Rec
			ListRec controller = loader.getController();

			// Obtenir la scène actuelle à partir de l'événement
			Scene currentScene = ((Node) event.getSource()).getScene();

			// Remplacer la racine de la scène actuelle avec la nouvelle page
			currentScene.setRoot(root);
		} catch (IOException e) {
			// Gérer les exceptions d'E/S
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


}
