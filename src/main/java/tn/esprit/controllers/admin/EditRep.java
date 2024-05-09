package tn.esprit.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.controllers.user.AccueilRec;
import tn.esprit.models.Reponse;
import tn.esprit.services.RepService;

import java.io.IOException;

public class EditRep {

    @FXML
    private TextField reponseUpdate;
    private Reponse reponse;

    private final RepService repService = new RepService();

    @FXML
    void initialize(Reponse reponse) {
        this.reponse = reponse;
        reponseUpdate.setText(reponse.getDescription_rep());
    }

    @FXML
    private void updateReponse() {
        this.reponse.setDescription_rep(reponseUpdate.getText());
        repService.update_Rep(this.reponse);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Réponse mise à jour avec succès !");
        alert.showAndWait();
    }

    @FXML
    void back(ActionEvent event) {

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
