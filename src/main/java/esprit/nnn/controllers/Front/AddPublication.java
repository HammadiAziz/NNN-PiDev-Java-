package esprit.nnn.controllers.Front;

import esprit.nnn.models.Groupe;
import esprit.nnn.models.Publication;
import esprit.nnn.services.GroupService;
import esprit.nnn.services.PublicationService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddPublication {

    @FXML
    private Button Re;

    @FXML
    private Text descControl;

    @FXML
    private TextArea descPostTA;

    @FXML
    private ComboBox<String> groupeCB;

    @FXML
    private Text imageControl;

    @FXML
    private TextField imageUrlTF;

    @FXML
    private Text nameControl;

    @FXML
    private TextField namePostTF;

    private Scene previousScene;

    GroupService gs = new GroupService();
    PublicationService ps = new PublicationService();

    public void initialize() {
        List<Groupe> groupes = gs.getAll();
        List<String>names=new ArrayList<>();
        for(Groupe g : groupes) {
            names.add(g.getName_group());
        }
        groupeCB.setItems(FXCollections.observableList(names));
    }

    @FXML
    void addPublication(ActionEvent event) {
        String title = namePostTF.getText().trim();
        String desc = descPostTA.getText().trim();
        String imageUrl = imageUrlTF.getText().trim();
        String selectedGroup = groupeCB.getSelectionModel().getSelectedItem();
        LocalDate date = LocalDate.now();
        if (title.isEmpty() || desc == null || selectedGroup == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie !");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.show();
        }
         else {
            int groupId = gs.FindByName(selectedGroup).getId();
            //add
            ps.add(new Publication(title, desc, date, imageUrl, groupId));
            // Efface les champs après l'ajout
            clearFields();
        }


    }

    private void clearFields() {
        namePostTF.clear();
        descPostTA.clear();
        imageUrlTF.clear();
        groupeCB.getSelectionModel().clearSelection();
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }


    @FXML
    void returnButton(ActionEvent event) {
        // Navigate back to the previous scene
        if (previousScene != null) {
            Scene currentScene = ((Node) event.getSource()).getScene();
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(previousScene);
        }
    }


}
