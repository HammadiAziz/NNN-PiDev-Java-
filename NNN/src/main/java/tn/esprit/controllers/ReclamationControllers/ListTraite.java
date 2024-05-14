package tn.esprit.controllers.ReclamationControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.entities.Reclamation;
import tn.esprit.services.ReclamationService;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ListTraite {

	@FXML
	private TableView<Reclamation> tableTraite;

	@FXML
	private TableColumn<Reclamation, String> CATEGORIECol;

	@FXML
	private TableColumn<Reclamation, String> ETATCol;

	@FXML
	private TableColumn<Reclamation, String> OBJECTCol;

	private final ReclamationService rs = new ReclamationService();
	int user_id=12;

	@FXML
	void initialize() {
		List<Reclamation> reclamations = rs.getAllByUserId(user_id);
		// Filtrer les réclamations avec l'état "Traité"
		reclamations = reclamations.stream()
				               .filter(reclamation -> reclamation.getEtat().equals("Traite"))
				               .collect(Collectors.toList());
		ObservableList<Reclamation> observableList = FXCollections.observableList(reclamations);
		reclamations = rs.getAllByUserId(user_id);
		tableTraite.setItems(observableList);
		OBJECTCol.setCellValueFactory(new PropertyValueFactory<>("object"));
		CATEGORIECol.setCellValueFactory(new PropertyValueFactory<>("categorie"));
		ETATCol.setCellValueFactory(new PropertyValueFactory<>("etat"));

		tableTraite.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// bouton dans table view
		// Créez une colonne de table pour les boutons
		TableColumn<Reclamation, Void> buttonColumn = new TableColumn<>("Action");

		// Définissez la Cell Factory pour la colonne des boutons
		buttonColumn.setCellFactory(tc -> new TableCell<Reclamation, Void>() {
			ImageView showIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/Rec/details.png")));
			// Application d'un style CSS à l'ImageView
			{
				showIcon.setStyle("-fx-fit-width: 15px; -fx-fit-height: 15px;");
			}
			private final Button detailsButton = new Button("",showIcon);

			{

				// Définissez l'action du bouton "Show Details"
				detailsButton.setOnAction(e -> {
					// Obtenez le produit associé à la ligne sélectionnée
					Reclamation selectedReclamation = getTableView().getItems().get(getIndex());

					try {
						// Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Reclamation/ShowDetails.fxml"));
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

			// la méthode updateItem() d'une cellule de la colonne d'une TableView.
			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					// Ajoutez les boutons à la cellule en fonction de la ligne
					HBox buttonsBox = new HBox( detailsButton);
					buttonsBox.setSpacing(5);
					setGraphic(buttonsBox);
				}
			}
		});

		// Ajoutez la colonne des boutons à la TableView
		tableTraite.getColumns().add(buttonColumn);
	}


	@FXML
	void back(ActionEvent event) {
		try{
			// Charger le fichier FXML de la deuxième page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Reclamation/AccueilRec.fxml"));
			Parent root = loader.load();
			// Obtenir le contrôleur de la deuxième page
			AccueilRec controller = loader.getController();
			// Obtenir la scène actuelle à partir de l'événement
			Scene currentScene = ((Node) event.getSource()).getScene();
			// Remplacer la racine de la scène actuelle avec la nouvelle page
			currentScene.setRoot(root);

		}catch(IOException e){
			throw new RuntimeException(e);

		}
	}
}
