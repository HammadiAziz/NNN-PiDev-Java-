package esprit.nnn.controllers.Front;

import esprit.nnn.models.Groupe;
import esprit.nnn.models.Publication;
import esprit.nnn.services.GroupService;
import esprit.nnn.services.PublicationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowGroupDetails implements Initializable {

    @FXML
    private AnchorPane main_posts;

    @FXML
    private GridPane menu_group_posts;

    @FXML
    private Label name_group;

    private Groupe groupe;


    public int setDataGroupe(Groupe group) {
        this.groupe= group;
        name_group.setText(group.getName_group());
        return gs.FindByName(name_group.getText()).getId();

    }


    private List<Publication> publications;
    private PublicationService ps = new PublicationService();
    private GroupService gs = new GroupService();

    public void showPosts(Groupe group){
        this.groupe= group;

        name_group.setText(group.getName_group());
        List<Publication> serviceList = new ArrayList<>(ps.getPublicationsByGroup(groupe.getId()));

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < serviceList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/esprit/nnn/Front/CardPublication.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CardPublication itemController = fxmlLoader.getController();

                itemController.setData(serviceList.get(i));


                if (column == 1) {
                    column = 0;
                    row++;
                }

                menu_group_posts.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                menu_group_posts.setMinWidth(Region.USE_COMPUTED_SIZE);
                menu_group_posts.setPrefWidth(Region.USE_COMPUTED_SIZE);
                menu_group_posts.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                menu_group_posts.setMinHeight(Region.USE_COMPUTED_SIZE);
                menu_group_posts.setPrefHeight(Region.USE_COMPUTED_SIZE);
                menu_group_posts.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void addPublication(ActionEvent event) {
        try {
            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/esprit/nnn/Front/AddPublication.fxml"));
            Parent root = loader.load();

            // Get controller and set previous scene
            AddPublication controller = loader.getController();
            Scene currentScene = ((Node) event.getSource()).getScene();
            controller.setPreviousScene(currentScene);

            // Show scene
            Scene scene = new Scene(root);
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Handle I/O exceptions
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }






}
