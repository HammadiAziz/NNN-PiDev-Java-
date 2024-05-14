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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import tn.esprit.controllers.AfficherLivUser;
import tn.esprit.controllers.AfficherShop;
import tn.esprit.controllers.Quiz.Front.QuizList;
import tn.esprit.controllers.UserControllers.ProfileController;
import tn.esprit.controllers.home;
import tn.esprit.entities.Groupe;
import tn.esprit.entities.Publication;
import tn.esprit.entities.User;
import tn.esprit.services.GroupService;
import tn.esprit.services.PublicationService;
import tn.esprit.services.SessionManager;
import tn.esprit.services.UserService;

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
                fxmlLoader.setLocation(getClass().getResource("/FrontFatma/CardPublication.fxml"));
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
       int userId = (int) SessionManager.getSession("userId");
       fetchUserData();

    }
    @FXML
    void addPublication(ActionEvent event) {
        try {
            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontFatma/AddPublication.fxml"));
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





}
