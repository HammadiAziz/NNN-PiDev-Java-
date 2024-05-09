package tn.esprit.controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import tn.esprit.models.Reclamation;
import tn.esprit.services.RecService;

import java.io.IOException;
import java.util.prefs.Preferences;



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

	int user_id=1;
	private boolean isCustomCategoryOverLimit(String category) {
		// Compte le nombre de réclamations avec la catégorie personnalisée spécifiée
		int count = 0;
		for (Reclamation reclamation : rs.getAll()) {
			if (reclamation.getCategorie().equals(category)) {
				count++;
			}
		}
		// Affiche le nombre d'occurrences comptées
		System.out.println("Nombre d'occurrences pour la catégorie " + category + " : " + count);
		// Vérifie si le nombre d'occurrences est supérieur à 2
		return count > 2;
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

	private void clearFields() {
		// Effacer le contenu des champs de texte
		objectTF.clear();
		descriptionTF.clear();
		categoriePersonnaliseeTF.clear();

		// Réinitialiser les labels d'erreur
		clearErrorLabel(categorieErrorLabel);
		clearErrorLabel(descriptionErrorLabel);
		clearErrorLabel(objectErrorLabel);
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

		// Si une catégorie personnalisée est utilisée plus de 3 fois, l'ajoute au combobox
		if (isCustomCategoryOverLimit(categorie)) {
			// Vérifie si la catégorie n'est pas déjà dans les préférences utilisateur
			Preferences prefs = Preferences.userNodeForPackage(getClass());
			if (!prefs.get("categorie_" + categorie, "").equals("added")) {
				// Si elle n'est pas déjà dans les préférences, l'ajoute
				prefs.put("categorie_" + categorie, "added");
				// Puis ajoute la catégorie au ComboBox
				categorieComboBox.getItems().add(categorie);
			}
			// Sélectionne la catégorie dans le ComboBox
			categorieComboBox.getSelectionModel().select(categorie);
		}

		// Ajouter la réclamation si la validation a réussi
		rs.add(new Reclamation(objectTF.getText(), descriptionTF.getText(), categorie, Etat,user_id));
		clearFields();
	}


	@FXML
	void navigate_list(ActionEvent event) {

		try{
		Parent root =  FXMLLoader.load(getClass().getResource("/User/AccueilRec.fxml")) ;
			categorieComboBox.getScene().setRoot(root);

		}catch(IOException e){
			throw new RuntimeException(e);

		}
	}
}
