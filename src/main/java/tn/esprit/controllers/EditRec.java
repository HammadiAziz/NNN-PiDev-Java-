package tn.esprit.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;
import tn.esprit.services.RecService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import tn.esprit.models.Reclamation;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.SelectionMode; // Importez la classe SelectionMode
import javafx.scene.control.SelectionModel;


public class EditRec {

	@FXML
	private Button back;

	@FXML
	private Button buttonUpdate;

	@FXML
	private TextField categorieUpdate;

	@FXML
	private TextField descriptionUpdate;

	@FXML
	private TextField etatUpdate;

	@FXML
	private TextField objectUpdate;

	private final RecService recService = new RecService();


	// L'objet Reclamation que vous modifiez
	private Reclamation reclamation;

	// L'identifiant de la réclamation à modifier
	private int reclamationId;

	// Initialisez votre service de réclamation, de préférence via injection de dépendance

	// Méthode pour initialiser les champs de texte avec les données de la réclamation
	public void setReclamationData(Reclamation reclamation, int reclamationId) {
		this.reclamation = reclamation;
		this.reclamationId = reclamationId;
		if (reclamation != null) {
			// Afficher les données de la réclamation dans les champs de texte
			categorieUpdate.setText(reclamation.getCategorie());
			descriptionUpdate.setText(reclamation.getDescription());
			etatUpdate.setText(reclamation.getEtat());
			objectUpdate.setText(reclamation.getObject());
		}
	}

	// Méthode pour gérer l'action de mise à jour
	@FXML
	private void handleUpdate(ActionEvent event) {
		// Mettez à jour les données de la réclamation avec les nouvelles valeurs des champs de texte
		reclamation.setCategorie(categorieUpdate.getText());
		reclamation.setDescription(descriptionUpdate.getText());
		reclamation.setEtat(etatUpdate.getText());
		reclamation.setObject(objectUpdate.getText());

		// Appelez la méthode de mise à jour dans votre service de réclamation
		recService.update(reclamation, reclamationId);

		// Affichez une boîte de dialogue pour informer l'utilisateur que la mise à jour a réussi
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText("Réclamation mise à jour avec succès !");
		alert.showAndWait();
	}


	@FXML
	void back_U(ActionEvent event) {

		try {
			// Charger le fichier FXML de la deuxième page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheRec.fxml"));
			Parent root = loader.load();
			// Obtenir le contrôleur de la deuxième page
			AfficheRec controller = loader.getController();
			// Obtenir la scène actuelle à partir de l'événement
			Scene currentScene = ((Node) event.getSource()).getScene();
			// Remplacer la racine de la scène actuelle avec la nouvelle page
			currentScene.setRoot(root);
		} catch (IOException e) {
			// Gérez les exceptions d'E/S
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
}
