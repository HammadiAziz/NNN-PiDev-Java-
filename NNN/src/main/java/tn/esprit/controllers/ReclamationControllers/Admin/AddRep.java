package tn.esprit.controllers.ReclamationControllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.controllers.AfficherProduits;
import tn.esprit.controllers.AfficherQuiv;
import tn.esprit.controllers.BackFatma.GetAllEvents;
import tn.esprit.controllers.BackFatma.GetAllGroups;
import tn.esprit.controllers.Quiz.QuizHomecontroller;
import tn.esprit.controllers.UserControllers.DashboardController;
import tn.esprit.entities.Reclamation;
import tn.esprit.entities.Reponse;
import tn.esprit.services.ReponseService;
import java.io.IOException;
import java.sql.SQLException;

public class AddRep {

	@FXML
	private TextField description_rep;

	@FXML
	private ComboBox<String> statusCombo;


	private final ReponseService reps = new ReponseService();

	@FXML
	private TextField idRecRep;
	Reclamation reclamation;


	@FXML
	public void initialize(Reclamation reclamation) {
		this.reclamation = reclamation;
		statusCombo.getItems().addAll("Traite", "Non traite");
		statusCombo.setValue("Non traite");
	}

	@FXML
	void addRep(ActionEvent event) {
		String description = description_rep.getText();

		if (description.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText(null);
			alert.setContentText("Veuillez entrer une description");
			alert.showAndWait();
			return;
		}

		Reponse rep = new Reponse();
		rep.setDescription_rep(description);
		rep.setRep_rec_id(reclamation.getId());

		if (statusCombo.getValue().equals("Traite")) {
			try {
				reps.updateReclamationStatus(reclamation.getId(), "Traite");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		reps.add_Rep(rep);

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Succès");
		alert.setHeaderText(null);
		alert.setContentText("La réponse a été ajoutée avec succès");
		alert.showAndWait();

		description_rep.clear();

	}


	@FXML
	void listReclamation(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Reclamation/Admin/ListRec.fxml"));
			Parent root = loader.load();

			ListRec controller = loader.getController();

			Scene currentScene = ((Node) event.getSource()).getScene();

			currentScene.setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	@FXML
	void RegReponse() {

	}


	@FXML
	void gotoevent(ActionEvent event) {



		try {
			// Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFATMA/GetAllEvents.fxml"));
			Parent root = loader.load();
			// Obtenez le contrôleur de la nouvelle page
			GetAllEvents Listcontroller = loader.getController();
			// Obtenez la scène actuelle à partir de l'événement
			Scene currentScene = ((Node) event.getSource()).getScene();

			// Remplacez la racine de la scène actuelle avec la nouvelle page
			currentScene.setRoot(root);
		} catch (IOException e) {
			// Gérez les exceptions d'E/S
			e.printStackTrace();
			throw new RuntimeException(e);
		}




	}


	@FXML
	void gotogroup(ActionEvent event) {

		try {
			// Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFATMA/GetAllGroups.fxml"));
			Parent root = loader.load();
			// Obtenez le contrôleur de la nouvelle page
			GetAllGroups Listcontroller = loader.getController();
			// Obtenez la scène actuelle à partir de l'événement
			Scene currentScene = ((Node) event.getSource()).getScene();

			// Remplacez la racine de la scène actuelle avec la nouvelle page
			currentScene.setRoot(root);
		} catch (IOException e) {
			// Gérez les exceptions d'E/S
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@FXML
	void gotolivraison(ActionEvent event) {
		try {
			// Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSuiv.fxml"));
			Parent root = loader.load();
			// Obtenez le contrôleur de la nouvelle page
			AfficherQuiv Listcontroller = loader.getController();
			// Obtenez la scène actuelle à partir de l'événement
			Scene currentScene = ((Node) event.getSource()).getScene();

			// Remplacez la racine de la scène actuelle avec la nouvelle page
			currentScene.setRoot(root);
		} catch (IOException e) {
			// Gérez les exceptions d'E/S
			e.printStackTrace();
			throw new RuntimeException(e);
		}


	}

	@FXML
	void gotomarket(ActionEvent event) {
		try {
			// Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
			Parent root = loader.load();
			// Obtenez le contrôleur de la nouvelle page
			AfficherProduits Listcontroller = loader.getController();
			// Obtenez la scène actuelle à partir de l'événement
			Scene currentScene = ((Node) event.getSource()).getScene();

			// Remplacez la racine de la scène actuelle avec la nouvelle page
			currentScene.setRoot(root);
		} catch (IOException e) {
			// Gérez les exceptions d'E/S
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@FXML
	void gotoquiz(ActionEvent event) {
		try {
			// Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/Admin/QuizHome.fxml"));
			Parent root = loader.load();
			// Obtenez le contrôleur de la nouvelle page
			QuizHomecontroller Listcontroller = loader.getController();
			// Obtenez la scène actuelle à partir de l'événement
			Scene currentScene = ((Node) event.getSource()).getScene();

			// Remplacez la racine de la scène actuelle avec la nouvelle page
			currentScene.setRoot(root);
		} catch (IOException e) {
			// Gérez les exceptions d'E/S
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@FXML
	void gotoreclamation(ActionEvent event) {

		try {
			// Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Reclamation/Admin/AddRep.fxml"));
			Parent root = loader.load();
			// Obtenez le contrôleur de la nouvelle page
			AddRep Listcontroller = loader.getController();
			// Obtenez la scène actuelle à partir de l'événement
			Scene currentScene = ((Node) event.getSource()).getScene();

			// Remplacez la racine de la scène actuelle avec la nouvelle page
			currentScene.setRoot(root);
		} catch (IOException e) {
			// Gérez les exceptions d'E/S
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@FXML
	void gotouser(ActionEvent event) {
		try {
			// Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardGUI.fxml"));
			Parent root = loader.load();
			// Obtenez le contrôleur de la nouvelle page
			DashboardController Listcontroller = loader.getController();
			// Obtenez la scène actuelle à partir de l'événement
			Scene currentScene = ((Node) event.getSource()).getScene();

			// Remplacez la racine de la scène actuelle avec la nouvelle page
			currentScene.setRoot(root);
		} catch (IOException e) {
			// Gérez les exceptions d'E/S
			e.printStackTrace();
			throw new RuntimeException(e);
		}


	}






}
