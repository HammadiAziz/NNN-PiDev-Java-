package esprit.nnn.controllers.Front;

import esprit.nnn.models.Groupe;
import esprit.nnn.services.GroupService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllGroupes implements Initializable {

    @FXML
    private AnchorPane main_form_Groupes;

    @FXML
    private GridPane menu_gridPane_groupe;

    @FXML
    private ScrollPane ScrollPaneGroupe;


    private List<Groupe> groupe;
    private GroupService se = new GroupService();
    List<Groupe> serviceList = new ArrayList<>(se.getAll());

    public void showDetailsForGroup(Groupe selectedGroup) {
        try {
            // Charger le fichier FXML  de la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/esprit/nnn/Front/ShowGroupDetails.fxml"));
            Parent root = loader.load();            // Obtenir le contrôleur de la deuxième page
            ShowGroupDetails controller = loader.getController();

            // Pass selected group to the controller
            controller.showPosts(selectedGroup);

            if (loader == null) {
                System.out.println("FXML Loader is null");
                return;
            }

            // Obtenir la scène actuelle à partir de l'événement
            Scene currentScene = main_form_Groupes.getScene(); // Assuming the form is in a Scene
            // Remplacer la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FXML loading error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < serviceList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/esprit/nnn/Front/CardGroupe.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                anchorPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, null, new BorderWidths(2))));


                CardGroupe itemController = fxmlLoader.getController();

                // Set the parent controller
                itemController.setParentController(this);

                itemController.setData(serviceList.get(i));


                if (column == 3) {
                    column = 0;
                    row++;
                }

                menu_gridPane_groupe.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                menu_gridPane_groupe.setMinWidth(Region.USE_COMPUTED_SIZE);
                menu_gridPane_groupe.setPrefWidth(Region.USE_COMPUTED_SIZE);
                menu_gridPane_groupe.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                menu_gridPane_groupe.setMinHeight(Region.USE_COMPUTED_SIZE);
                menu_gridPane_groupe.setPrefHeight(Region.USE_COMPUTED_SIZE);
                menu_gridPane_groupe.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
