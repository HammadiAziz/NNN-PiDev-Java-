package tn.esprit.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import tn.esprit.models.Reclamation;
import java.sql.SQLException;
import tn.esprit.services.RecService;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.control.ComboBox;



public class AddRec {

	@FXML
	private ComboBox<String> categorieComboBox;

	@FXML
	private TextField categoriePersonnaliseeTF;

	@FXML
	private TextField descriptionTF;

	@FXML
	private TextField etatTF;

	@FXML
	private TextField objectTF;

	@FXML
	private Label categorieErrorLabel;

	@FXML
	private Label descriptionErrorLabel;


	@FXML
	private Label objectErrorLabel;


	private final RecService rs = new RecService();


	private boolean isCustomCategoryOverLimit(String category) {
		// Compte le nombre de réclamations avec la catégorie personnalisée spécifiée
		int count = 0;
		for (Reclamation reclamation : rs.getAll()) {
			if (reclamation.getCategorie().equals(category)) {
				count++;
			}
		}
		// Vérifie si le nombre d'occurrences est supérieur à 5
		return count > 3;
	}


	// Remplir la combobox avec les catégories disponibles
	@FXML
	public void initialize() {
		categorieComboBox.getItems().addAll("Produit",  "Quiz", "Livraison","Publication","Compte","Autres");
		categorieComboBox.setValue("Produit");
		// Rend le champ de texte invisible par défaut
		categoriePersonnaliseeTF.setVisible(false);
		categorieComboBox.setOnAction(event -> categorieComboBoxChanged());

	}


	@FXML
	private void categorieComboBoxChanged() {
		String selectedCategory = categorieComboBox.getValue();
		// Affiche le champ de texte uniquement si "Autres" est sélectionné
		categoriePersonnaliseeTF.setVisible("Autres".equals(selectedCategory));
	}


	private void setErrorLabel(Label label, String message) {
		label.setText(message);
		label.setTextFill(Color.RED); // Définit la couleur du texte en rouge
	}

	private void clearErrorLabel(Label label) {
		label.setText("");
	}

	private boolean validateInputs() {
		boolean isValid = true;

		if (categorieComboBox.getValue().isEmpty()) {
			setErrorLabel(categorieErrorLabel, "Veuillez saisir une catégorie.");
			isValid = false;
		} else {
			clearErrorLabel(categorieErrorLabel);
		}

		if (descriptionTF.getText().isEmpty()) {
			setErrorLabel(descriptionErrorLabel, "Veuillez saisir une description.");
			isValid = false;
		} else {
			clearErrorLabel(descriptionErrorLabel);
		}


		if (objectTF.getText().isEmpty()) {
			setErrorLabel(objectErrorLabel, "Veuillez saisir un objet.");
			isValid = false;
		} else {
			clearErrorLabel(objectErrorLabel);
		}

		return isValid;
	}


	@FXML
	void AddRec(ActionEvent event) {
		if (!validateInputs()) {
			return; // Ne pas continuer si la validation a échoué
		}
		//Affecter automatiquement la valeur "Non traite" à l'attribut etat
		String Etat = "Non traite";

		String categorie;

		// Vérifie si la catégorie personnalisée est activée
		if (categoriePersonnaliseeTF.isVisible()) {
			// Si c'est le cas, utilise la valeur saisie dans le champ de texte comme catégorie
			categorie = categoriePersonnaliseeTF.getText();
		} else {
			// Sinon, utilise la valeur sélectionnée dans la combobox comme catégorie
			categorie = categorieComboBox.getValue();
		}

		//Affecter automatiquement la valeur "Non traite" à l'attribut etat
		String etat = "Non traite";

		// Si une catégorie personnalisée est utilisée plus de 5 fois, l'ajoute au combobox
		if (isCustomCategoryOverLimit(categorie)) {
			if (!categorieComboBox.getItems().contains(categorie)) {
				categorieComboBox.getItems().add(categorie);
			}
		}


		// Ajouter la réclamation si la validation a réussi
		rs.add(new Reclamation(objectTF.getText(), descriptionTF.getText(), categorie, Etat));
	}

	@FXML
	void navigate_list(ActionEvent event) {

		try{
		Parent root =  FXMLLoader.load(getClass().getResource("/AfficheRec.fxml")) ;
			categorieComboBox.getScene().setRoot(root);

		}catch(IOException e){
			throw new RuntimeException(e);

		}
	}
}
