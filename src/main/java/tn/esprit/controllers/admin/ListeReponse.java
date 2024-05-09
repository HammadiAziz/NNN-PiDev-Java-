package tn.esprit.controllers.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.models.Reclamation;
import tn.esprit.models.Reponse;
import tn.esprit.services.RepService;

import java.io.IOException;
import java.util.List;

public class ListeReponse {

    @FXML
    private TableColumn<Reponse, String> descreptionTab;
    @FXML
    private TableView<Reponse> tableRec;
    private final RepService rs = new RepService();
    Reclamation reclamation;

    @FXML
    void initialize(Reclamation reclamation) {
        this.reclamation = reclamation;
        List<Reponse> reponses = rs.getAll_Rep(reclamation.getId());
        ObservableList<Reponse> observableList = FXCollections.observableList(reponses);
        tableRec.setItems(observableList);
        descreptionTab.setCellValueFactory(new PropertyValueFactory<>("description_rep"));

        // Définir la cellule de bouton après avoir défini la ValueFactory
        TableColumn<Reponse, Void> buttonColumn = new TableColumn<>("Action");
        buttonColumn.setCellFactory(tc -> new TableCell<Reponse, Void>() {
            private final Button updateButton = new Button("Modifier");

            {
                updateButton.setOnAction(e -> {
                    Reponse reponse = getTableView().getItems().get(getIndex());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/EditRep.fxml"));
                    Parent root = null;

                    try {
                        root = loader.load();
                        EditRep controller = loader.getController();
                        controller.initialize(reponse);
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
                    HBox buttonsBox = new HBox(updateButton);
                    buttonsBox.setSpacing(5);
                    setGraphic(buttonsBox);
                }
            }
        });
        tableRec.getColumns().add(buttonColumn);
    }

    @FXML
    void goAddReponse() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/AddRep.fxml"));
        Parent root = null;


        root = loader.load();
        AddRep controller = loader.getController();
        controller.initialize(this.reclamation);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Add Response");
        stage.show();
    }

    @FXML
    void listReclamation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/ListRec.fxml"));
            Parent root = loader.load();

            ListRec controller = loader.getController();

            Scene currentScene = ((Node) event.getSource()).getScene();

            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}