package tn.esprit.controllers.BackFatma;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.entities.Groupe;
import tn.esprit.services.GroupService;

import java.io.IOException;

public class AddGroupe {

    @FXML
    private TextArea descGroupTA;

    @FXML
    private TextField imageURLTF;

    @FXML
    private TextField nameGroupTF;

    private final GroupService gs = new GroupService();


    @FXML
    void addGroup(ActionEvent event) {
        String groupName = nameGroupTF.getText().trim();
        String desc = descGroupTA.getText().trim();
        String imageURL = imageURLTF.getText().trim();


        if (groupName.isEmpty() || desc.isEmpty() || imageURL.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Champs vides !");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        } else {
            //add
            gs.add(new Groupe(groupName, desc, imageURL));
            clearFields();
        }
    }

    @FXML
    void eventDash(ActionEvent event) {
        try {
            // Charger le fichier FXML de la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFATMA/GetAllEvents.fxml"));
            Parent root = loader.load();            // Obtenir le contrôleur de la deuxième page
            GetAllEvents controller = loader.getController();
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

    @FXML
    void groupDash(ActionEvent event) {
        try {
            // Charger le fichier FXML de la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/esprit/nnn/FXML/GetAllGroups.fxml"));
            Parent root = loader.load();            // Obtenir le contrôleur de la deuxième page
            GetAllGroups controller = loader.getController();
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



    private void clearFields() {
        nameGroupTF.clear();
        descGroupTA.clear();
        imageURLTF.clear();
    }

}
