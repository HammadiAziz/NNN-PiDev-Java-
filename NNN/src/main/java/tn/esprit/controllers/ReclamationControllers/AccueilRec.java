package tn.esprit.controllers.ReclamationControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import java.io.IOException;

public class AccueilRec {

	@FXML
	void List_Traite(ActionEvent event) {

		try {
			// Charger le fichier FXML de la deuxième page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Reclamation/ListTraite.fxml"));
			Parent root = loader.load();
			// Obtenir le contrôleur de la deuxième page
			ListTraite controller = loader.getController();
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
	@FXML
	void List_Non_Traite(ActionEvent event) {

		try {
			// Charger le fichier FXML de la deuxième page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Reclamation/ListNonTraite.fxml"));
			Parent root = loader.load();
			// Obtenir le contrôleur de la deuxième page
			ListNonTraite controller = loader.getController();
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

	@FXML
	void navigateAdd(ActionEvent event) {

		try {
			// Charger le fichier FXML de la deuxième page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Reclamation/AddRec.fxml"));
			Parent root = loader.load();
			// Obtenir le contrôleur de la deuxième page
			AddRec controller = loader.getController();
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



