package tn.esprit.controllers.admin;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import tn.esprit.models.Reponse;
import tn.esprit.services.RepService;

public class EditRep {

	@FXML
	private TextField reponseUpdate;

	private int selectedReponseId;

	private final RepService repService = new RepService();

	// Méthode pour charger les informations de la réponse à modifier
	public void loadReponseInfo(int reponseId) {
		selectedReponseId = reponseId;
		Reponse reponse = repService.getOne_Rep(reponseId);
		if (reponse != null) {
			reponseUpdate.setText(reponse.getDescription_rep());
		}
	}

	// Méthode pour mettre à jour la réponse
	@FXML
	private void updateReponse() {
		String newDescription = reponseUpdate.getText();
		Reponse updatedReponse = new Reponse();
		updatedReponse.setDescription_rep(newDescription);
		repService.update_Rep(updatedReponse, selectedReponseId);
		// Ajoutez ici le code pour notifier l'utilisateur que la réponse a été mise à jour avec succès
	}

}
