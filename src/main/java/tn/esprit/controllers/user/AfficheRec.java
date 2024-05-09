package tn.esprit.controllers.user;

import javafx.stage.Stage;
import tn.esprit.services.RecService;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import tn.esprit.models.Reclamation;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.SelectionMode; // Importez la classe SelectionMode
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import java.io.IOException;

import javafx.scene.layout.HBox;


public class AfficheRec {
	@FXML
	private TableColumn<Reclamation, String> categorieCol;


	@FXML
	private TableColumn<Reclamation, String> etatCol;

	@FXML
	private TableColumn<Reclamation, String> objectCol;



	@FXML
	private TableView<Reclamation> tableView;

	private final RecService rs = new RecService();

	@FXML
	void initialize(){

			List<Reclamation> reclamations = rs.getAll();
			ObservableList <Reclamation> observableList = FXCollections.observableList(reclamations) ;
			reclamations= rs.getAll();
			tableView.setItems(observableList);
			objectCol.setCellValueFactory(new PropertyValueFactory<>("object"));
			categorieCol.setCellValueFactory(new PropertyValueFactory<>("categorie"));
			etatCol.setCellValueFactory(new PropertyValueFactory<>("etat"));


		    tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		//bouton dans table view
		// Créez une colonne de table pour les boutons
		TableColumn<Reclamation, Void> buttonColumn = new TableColumn<>("Action");

		// Définissez la Cell Factory pour la colonne des boutons
		buttonColumn.setCellFactory(tc -> new TableCell<Reclamation, Void>() {
			private final Button button = new Button("update");
			private final Button detailsButton = new Button("Show Details");
			{
				// Définissez l'action du bouton
				button.setOnAction(e -> {
					// Obtenez le produit associé à la ligne sélectionnée
					Reclamation selectedReclamation = getTableView().getItems().get(getIndex());

					try {
						// Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/EditRec.fxml"));
						Parent root = loader.load();

						// Obtenez le contrôleur de la nouvelle page
						EditRec controller = loader.getController();

						// Passez les données du produit sélectionné à la nouvelle page
						controller.setReclamationData(selectedReclamation, selectedReclamation.getId());
						// Obtenez la scène actuelle à partir de l'événement
						Scene currentScene = ((Node) e.getSource()).getScene();

						// Remplacez la racine de la scène actuelle avec la nouvelle page
						currentScene.setRoot(root);
					} catch (IOException ex) {
						// Gérez les exceptions d'E/S
						ex.printStackTrace();
						throw new RuntimeException(ex);
					}
				});

				// Définissez l'action du bouton "Show Details"
				detailsButton.setOnAction(e -> {
					// Obtenez le produit associé à la ligne sélectionnée
					Reclamation selectedReclamation = getTableView().getItems().get(getIndex());

					try {
						// Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ShowDetails.fxml"));
						Parent root = loader.load();

						// Obtenez le contrôleur de la nouvelle page
						ShowDetails controller = loader.getController();

						// Passez les données du produit sélectionné à la nouvelle page
						controller.setReclamationDetails(selectedReclamation);

						// Obtenez la scène actuelle à partir de l'événement
						Scene currentScene = ((Node) e.getSource()).getScene();

						// Créez une nouvelle fenêtre pour afficher les détails de la réclamation
						Stage stage = new Stage();
						stage.setScene(new Scene(root));
						stage.show();
					} catch (IOException ex) {
						// Gérez les exceptions d'E/S
						ex.printStackTrace();
						throw new RuntimeException(ex);
					}
				});

			}
			//la méthode updateItem() d'une cellule de la colonne d'une TableView.
			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					// Ajoutez les boutons à la cellule en fonction de la ligne
					HBox buttonsBox = new HBox(button, detailsButton);
					buttonsBox.setSpacing(5);
					setGraphic(buttonsBox);
				}
			}
		});

		// Ajoutez la colonne des boutons à la TableView
		tableView.getColumns().add(buttonColumn);


	}

	@FXML
	void deleteRec(ActionEvent event) {
		// Récupérer l'identifiant de l'élément sélectionné dans la table
		int selectedID = tableView.getSelectionModel().getSelectedItem().getId();

		// Appeler la méthode de suppression de la classe de service
		rs.delete(selectedID); // Appeler la méthode de suppression avec l'identifiant sélectionné

		// Supprimer l'élément de la table affichée
		tableView.getItems().remove(tableView.getSelectionModel().getSelectedIndex());
	}


	@FXML
	void back(ActionEvent event) {

			try {
				// Charger le fichier FXML de la deuxième page
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/AddRec.fxml"));
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
