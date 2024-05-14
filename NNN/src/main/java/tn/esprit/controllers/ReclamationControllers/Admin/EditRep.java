package tn.esprit.controllers.ReclamationControllers.Admin;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.entities.Reponse;
import tn.esprit.services.ReponseService;

public class EditRep {

	@FXML
	private TextField reponseUpdate;
	private Reponse reponse;

	private final ReponseService repService = new ReponseService();

	@FXML
	void initialize(Reponse reponse) {
		this.reponse = reponse;
		reponseUpdate.setText(reponse.getDescription_rep());
	}

	@FXML
	private void updateReponse() {
		this.reponse.setDescription_rep(reponseUpdate.getText());
		repService.update_Rep(this.reponse);
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText("Réponse mise à jour avec succès !");
		alert.showAndWait();
	}

}
