package tn.esprit.controllers.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.controllers.admin.ListRec;
import tn.esprit.models.Reclamation;
import tn.esprit.services.RecService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class ListNonTraite {

	@FXML
	private TableColumn<Reclamation, String> CATEGORIECol;

	@FXML
	private TableColumn<Reclamation, String> ETATCol;

	@FXML
	private TableColumn<Reclamation, String> OBJECTCol;

	@FXML
	private TableView<Reclamation> tableNonTraite;

	private final RecService rs = new RecService();

	int user_id=1;

	@FXML
	void initialize(){

		tableNonTraite.getStylesheets().add(getClass().getResource("/styles/button.css").toExternalForm());

		List<Reclamation> reclamations = rs.getAllByUserId(user_id);
		// Filtrer les réclamations avec l'état "Traité"
		reclamations = reclamations.stream()
				               .filter(reclamation -> reclamation.getEtat().equals("Non traite"))
				               .collect(Collectors.toList());
		ObservableList<Reclamation> observableList = FXCollections.observableList(reclamations) ;
		reclamations= rs.getAllByUserId(user_id);
		tableNonTraite.setItems(observableList);
		OBJECTCol.setCellValueFactory(new PropertyValueFactory<>("object"));
		CATEGORIECol.setCellValueFactory(new PropertyValueFactory<>("categorie"));
		ETATCol.setCellValueFactory(new PropertyValueFactory<>("etat"));


		tableNonTraite.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// bouton dans table view
		// Créez une colonne de table pour les boutons
		TableColumn<Reclamation, Void> buttonColumn = new TableColumn<>("Action");

		// Définissez la Cell Factory pour la colonne des boutons
		buttonColumn.setCellFactory(tc -> new TableCell<Reclamation, Void>() {
			ImageView updateIcon = new ImageView(new Image(getClass().getResourceAsStream("/Images/edit.png")));
			ImageView detailsIcon = new ImageView(new Image(getClass().getResourceAsStream("/Images/details.png")));
			ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/Images/delete.png")));


			// Application d'un style CSS à l'ImageView
			{
				updateIcon.setStyle("-fx-fit-width: 15px; -fx-fit-height: 15px;");
				detailsIcon.setStyle("-fx-fit-width: 15px; -fx-fit-height: 15px;");
				deleteIcon.setStyle("-fx-fit-width: 15px; -fx-fit-height: 15px;");

			}
			private final Button button = new Button("", updateIcon);
			private final Button detailsButton = new Button("",detailsIcon);
			private final Button deleteButton = new Button("",deleteIcon);


			{
				// Appliquez la classe de style CSS aux boutons
				button.getStyleClass().add("custom-button");
				detailsButton.getStyleClass().add("custom-button");
				deleteButton.getStyleClass().add("custom-button");

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

				//
				// Définissez l'action du bouton "Delete"
				deleteButton.setOnAction(e -> {
					// Obtenez la réclamation associée à la ligne sélectionnée
					Reclamation selectedReclamation = getTableView().getItems().get(getIndex());

					// Affichez une boîte de dialogue de confirmation pour confirmer la suppression
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Confirmation de suppression");
					alert.setHeaderText(null);
					alert.setContentText("Voulez-vous vraiment supprimer cette réclamation ?");

					// Obtenez la réponse de l'utilisateur
					alert.showAndWait().ifPresent(response -> {
						if (response == ButtonType.OK) {
							// Supprimez la réclamation en utilisant la méthode delete du service
							rs.delete(selectedReclamation.getId());
							// Mettez à jour la TableView en supprimant la réclamation de la liste observable
							tableNonTraite.getItems().remove(selectedReclamation);
							// Affichez un message de confirmation de suppression
							Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
							confirmationAlert.setTitle("Suppression réussie");
							confirmationAlert.setHeaderText(null);
							confirmationAlert.setContentText("La réclamation a été supprimée avec succès.");
							confirmationAlert.showAndWait();
						}
					});
				});


			}

			// la méthode updateItem() d'une cellule de la colonne d'une TableView.
			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					// Ajoutez les boutons à la cellule en fonction de la ligne
					HBox buttonsBox = new HBox(button, detailsButton,deleteButton);
					buttonsBox.setSpacing(5);
					setGraphic(buttonsBox);
				}
			}
		});

		// Ajoutez la colonne des boutons à la TableView
		tableNonTraite.getColumns().add(buttonColumn);
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




