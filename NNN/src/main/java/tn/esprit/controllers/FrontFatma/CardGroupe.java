package tn.esprit.controllers.FrontFatma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tn.esprit.entities.Groupe;

public class CardGroupe {

    @FXML
    private HBox buttonsHbox;

    @FXML
    private HBox desHbox;

    @FXML
    private Button detailsButton;

    @FXML
    private Label groupDesc;

    @FXML
    private Label group_name;

    @FXML
    private ImageView imgae_groupe_view;

    @FXML
    private HBox nameHbox;

    private Groupe groupe;
    private String prod_image;
    private Image image;
    private double pr;

    private ShowAllGroupes parentController;

    public void setParentController(ShowAllGroupes controller) {
        this.parentController = controller;
    }

    public void setData(Groupe groupe) {
        this.groupe = groupe;
        group_name.setText(groupe.getName_group());
        groupDesc.setText(groupe.getDesc_group());

        if (groupe.getGroup_image() != null && !groupe.getGroup_image().isEmpty()) {
            try {
                Image image = new Image(groupe.getGroup_image());
                imgae_groupe_view.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to load image");
                alert.setContentText("An error occurred while loading the image.");
                alert.showAndWait();
            }
        }

    }

    @FXML
    void ShowDetails(ActionEvent event) {
        parentController.showDetailsForGroup(groupe);
    }

   /* public void setData(Groupe groupe) {
        this.groupe = groupe;
        group_name.setText(groupe.getName_group());
        groupDesc.setText(groupe.getDesc_group());

        if (groupe.getGroup_image() != null && !groupe.getGroup_image().isEmpty()) {
            try {
                Image image = new Image(groupe.getGroup_image());
                imgae_groupe_view.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to load image");
                alert.setContentText("An error occurred while loading the image.");
                alert.showAndWait();
            }
        }

    }
    @FXML
    public void initialize() {

    }
    @FXML
    void ShowDetails(ActionEvent event) {
        try {
            // Charger le fichier FXML de la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/esprit/nnn/Front/ShowGroupDetails.fxml"));
            Parent root = loader.load();            // Obtenir le contrôleur de la deuxième page
            ShowGroupDetails controller = loader.getController();

            // Obtenir la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();
            // Remplacer la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }



    }*/

}
