package tn.esprit.controllers.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.models.Reclamation;
import tn.esprit.services.RecService;

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

        TableColumn<Reclamation, Void> buttonColumn = new TableColumn<>("Action");
        buttonColumn.setCellFactory(tc -> new TableCell<Reclamation, Void>() {
            private final Button button = new Button("Show Response");
            {
                button.setOnAction(e -> {
                    Reclamation reclamation = getTableView().getItems().get(getIndex());
                    Scene scene1 = tableRec.getScene();
                    Stage stage1 = (Stage) scene1.getWindow();
                    stage1.close();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Listereponce.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                        ListeReponse controller = loader.getController();
                        controller.initialize(reclamation);
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("List Response");
                        stage.show();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonsBox = new HBox(button);
                    buttonsBox.setSpacing(5);
                    setGraphic(buttonsBox);
                }
            }
        });
        tableRec.getColumns().add(buttonColumn);
    }
	@FXML
	void ShowStat() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin/StatRec.fxml")) ;
		Scene scene = new Scene(fxmlLoader.load());
		Stage stage=new Stage();
		stage.setTitle("");
		stage.setScene(scene);
		stage.show();

	}
}