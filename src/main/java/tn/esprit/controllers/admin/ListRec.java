package tn.esprit.controllers.admin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import tn.esprit.controllers.AddRec;
import tn.esprit.controllers.EditRec;
import tn.esprit.models.Reclamation;
import tn.esprit.services.RecService;
import javafx.stage.Stage;
import tn.esprit.controllers.admin.EditRep;


import java.io.IOException;
import java.util.List;

public class ListRec {

	@FXML
	private TableColumn<Reclamation, String> categorieRec;

	@FXML
	private TableColumn<Reclamation, String> etatRec;

	@FXML
	private TableColumn<Reclamation, String> objectRec;

	@FXML
	private TableView<Reclamation> tableRec;

	private final RecService rs = new RecService();

	private int selectedReclamationId;


	@FXML
	void initialize() {

		List<Reclamation> reclamations = rs.getAll();
		ObservableList<Reclamation> observableList = FXCollections.observableList(reclamations);
		reclamations = rs.getAll();
		tableRec.setItems(observableList);
		objectRec.setCellValueFactory(new PropertyValueFactory<>("object"));
		categorieRec.setCellValueFactory(new PropertyValueFactory<>("categorie"));
		etatRec.setCellValueFactory(new PropertyValueFactory<>("etat"));



		tableRec.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		//bouton dans table view
		// Créez une colonne de table pour les boutons
		TableColumn<Reclamation, Void> buttonColumn = new TableColumn<>("Action");

		// Définissez la Cell Factory pour la colonne des boutons
		buttonColumn.setCellFactory(tc -> new TableCell<Reclamation, Void>() {

			private final Button updateButton = new Button("Modifier");

			private final Button button = new Button("Traiter");


			{
				// Définissez l'action du bouton
				button.setOnAction(e -> {
					// Obtenez la réclamation associée à la ligne sélectionnée
					Reclamation selectedReclamation = getTableView().getItems().get(getIndex());

					// Stockez l'ID de la réclamation sélectionnée
					selectedReclamationId = selectedReclamation.getId();

					try {
						// Charger la vue FXML de la page AddRep
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/AddRep.fxml"));
						Parent root = loader.load();

						// Obtenir le contrôleur associé à la vue chargée
						AddRep controller = loader.getController();

						// Passer l'ID de la réclamation sélectionnée au contrôleur AddRep
						controller.setSelectedReclamationId(selectedReclamationId);

						// Créer une nouvelle scène
						Scene scene = new Scene(root);

						// Obtenir la fenêtre actuelle à partir de l'événement
						Node source = (Node) e.getSource();
						Stage stage = (Stage) source.getScene().getWindow();

						// Afficher la nouvelle scène dans la fenêtre
						stage.setScene(scene);
						stage.show();

					} catch (IOException ex) {
						// Gérez les exceptions d'E/S
						ex.printStackTrace();
						throw new RuntimeException(ex);
					}
				});

				updateButton.setOnAction(e -> {
					Reclamation selectedReclamation = getTableView().getItems().get(getIndex());
					selectedReclamationId = selectedReclamation.getId();

					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/EditRep.fxml"));
						Parent root = loader.load();
						EditRep controller = loader.getController();
						controller.loadReponseInfo(selectedReclamationId);
						Scene scene = new Scene(root);
						Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
						stage.setScene(scene);
						stage.show();
					} catch (IOException ex) {
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
					HBox buttonsBox = new HBox(button, updateButton);
					buttonsBox.setSpacing(5);
					setGraphic(buttonsBox);
				}
			}
		});

		// Ajoutez la colonne des boutons à la TableView

		tableRec.getColumns().add(buttonColumn);



	}
}