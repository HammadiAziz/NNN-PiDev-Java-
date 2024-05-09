package tn.esprit.controllers.user;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import java.io.IOException;

import tn.esprit.models.Reclamation;
import tn.esprit.services.RecService;
import javafx.event.ActionEvent;
import java.util.List;
import tn.esprit.models.Reponse;

public class ShowDetails {

	@FXML
	private Label label_categorie;

	@FXML
	private Label label_decription;

	@FXML
	private Label label_etat;

	@FXML
	private Label label_object;

	@FXML
	private Label label_reponse;

	// Méthode pour définir les détails de la réclamation à afficher
	public void setReclamationDetails(Reclamation reclamation) {
		label_object.setText(reclamation.getObject());
		label_decription.setText(reclamation.getDescription());
		label_categorie.setText(reclamation.getCategorie());
		label_etat.setText(reclamation.getEtat());


		// Récupérer les réponses correspondant à la réclamation
		RecService recService = new RecService();
		List<Reponse> reponses = recService.getReponsesForReclamation(reclamation.getId());

		// Vérifier s'il y a des réponses
		if (reponses.isEmpty()) {
			label_reponse.setText("Pas de réponse");
		} else {
			// Construire une chaîne contenant toutes les réponses
			StringBuilder reponsesText = new StringBuilder();
			for (Reponse reponse : reponses) {
				reponsesText.append(reponse.getDescription_rep()).append("\n");
			}
			// Afficher les réponses dans le label_reponse
			label_reponse.setText(reponsesText.toString());
		}

	}

	@FXML
	void back(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/AccueilRec.fxml"));
			Parent root = loader.load();

			AccueilRec controller = loader.getController();

			Scene currentScene = ((Node) event.getSource()).getScene();

			currentScene.setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}


}
