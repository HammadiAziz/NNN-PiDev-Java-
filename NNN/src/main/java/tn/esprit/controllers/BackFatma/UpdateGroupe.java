package tn.esprit.controllers.BackFatma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.entities.Groupe;
import tn.esprit.services.GroupService;

import java.io.IOException;

public class UpdateGroupe {

    @FXML
    private TextField IdDisabledTF;

    @FXML
    private TextArea descUpdatedTA;

    @FXML
    private TextField imageUpdatedTF;

    @FXML
    private TextField nameUpdatedTF;


    Groupe groupeToUpdate= new Groupe();
    GroupService gs= new GroupService();

    @FXML
    void RetrunButton(ActionEvent event) {
        try {
            // Charger le fichier FXML de la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFATMA/GetAllGroups.fxml"));
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



    @FXML
    void updateGroup(ActionEvent event) {
        System.out.println(groupeToUpdate.getId());
        groupeToUpdate.setName_group(nameUpdatedTF.getText());
        groupeToUpdate.setDesc_group(descUpdatedTA.getText());
        groupeToUpdate.setGroup_image(imageUpdatedTF.getText());
        groupeToUpdate.setId(Integer.parseInt(IdDisabledTF.getText()));
        gs.update(groupeToUpdate);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/esprit/nnn/FXML/GetAllGroups.fxml"));

            Parent root = loader.load();
            GetAllGroups controller = loader.getController();
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void initData(Groupe groupe) {
        descUpdatedTA.setText(groupe.getDesc_group());
        imageUpdatedTF.setText(groupe.getGroup_image());
        nameUpdatedTF.setText(groupe.getName_group());
    }

    public void setGroupData(Groupe groupe) {
        descUpdatedTA.setText(groupe.getDesc_group());
        nameUpdatedTF.setText(groupe.getName_group());
        imageUpdatedTF.setText(groupe.getGroup_image());
        IdDisabledTF.setText(String.valueOf(groupe.getId()));
    }


}
