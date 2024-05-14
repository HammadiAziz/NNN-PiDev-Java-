package tn.esprit.controllers.FrontFatma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import tn.esprit.controllers.AfficherLivUser;
import tn.esprit.controllers.AfficherShop;
import tn.esprit.controllers.Quiz.Front.QuizList;
import tn.esprit.controllers.UserControllers.ProfileController;
import tn.esprit.controllers.home;
import tn.esprit.entities.Groupe;
import tn.esprit.entities.User;
import tn.esprit.services.GroupService;
import tn.esprit.services.UserService;

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
    @FXML
    private Button profilename;


    final UserService pf = new UserService();
    private void fetchUserData() {
        // Retrieve user data from UserService
        User user = pf.fetchPlayerData();
        if (user != null) {
            // Populate the UI with user data

            profilename.setText(user.getNom());

        } else {
            // Handle case where user data is not found

            profilename.setText("N/A");

        }
    }

    public void showDetailsForGroup(Groupe selectedGroup) {
        try {
            // Charger le fichier FXML  de la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontFatma/ShowGroupDetails.fxml"));
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
        fetchUserData();
        int row = 1;
        try {
            for (int i = 0; i < serviceList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/FrontFatma/CardGroupe.fxml"));
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


    @FXML
    void goToEvents(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontFatma/ShowAllEvents.fxml"));
            Parent root = loader.load();
            ShowAllEvents controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    @FXML
    public void goToQuiz(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/User/QuizList.fxml"));
            Parent root = loader.load();
            QuizList controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void goToGroups(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontFatma/ShowAllGroupes.fxml"));
            Parent root = loader.load();
            ShowAllGroupes controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void goToMarket(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherShop.fxml"));
            Parent root = loader.load();
            AfficherShop controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    @FXML
    void gotoprofile(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateUserGUI.fxml"));
            Parent root = loader.load();
            ProfileController controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    @FXML
    void gotoHome(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();
            home controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    @FXML
    void goToLivraison(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLivUser.fxml"));
            Parent root = loader.load();
            AfficherLivUser controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


}
