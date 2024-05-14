package tn.esprit.controllers.FrontFatma;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import tn.esprit.controllers.AfficherLivUser;
import tn.esprit.controllers.AfficherShop;
import tn.esprit.controllers.BackFatma.AddGroupe;
import tn.esprit.controllers.BackFatma.GetAllEvents;
import tn.esprit.controllers.Quiz.Front.QuizList;
import tn.esprit.controllers.UserControllers.ProfileController;
import tn.esprit.controllers.home;
import tn.esprit.entities.Event;
import tn.esprit.entities.Groupe;
import tn.esprit.entities.User;
import tn.esprit.services.EventService;
import tn.esprit.services.GroupService;
import tn.esprit.services.UserService;
import tn.esprit.util.WeatherAPI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShowAllEvents implements Initializable {


    @FXML
    private GridPane menu_gridPane_event;
    private List<Event> events;
    private EventService se = new EventService();
    List<Event> serviceList = new ArrayList<>(se.getAll());
    @FXML
    private Label weather_label;

    @FXML
    private ComboBox<String> groupCB;

    private final GroupService groupService = new GroupService();
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

    @FXML
    void AddEVent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontFatma/AddEvent.fxml"));
            Parent root = loader.load();
            AddEvent controller = loader.getController();
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
    void searchByGroups(ActionEvent actionEvent) {
        // Get the selected group from the ComboBox
        GroupService gs = new GroupService();
        Groupe selectedGroup = gs.FindByName(groupCB.getValue());

        // If no group is selected, do nothing
        if (selectedGroup == null) {
            return;
        }

        // Clear existing event cards from the grid pane
        menu_gridPane_event.getChildren().clear();

        // Filter the serviceList to get only the events of the selected group
        List<Event> filteredEvents = serviceList.stream()
                .filter(event -> event.getGroupe_id() == selectedGroup.getId())
                .collect(Collectors.toList());

        // Display the filtered events
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < filteredEvents.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/FrontFatma/cardEvent.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CardEvent itemController = fxmlLoader.getController();
                itemController.setData(filteredEvents.get(i));

                if (column == 3) {
                    column = 0;
                    row++;
                }

                menu_gridPane_event.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void updateUIWithFilteredEvents(List<Event> filteredEvents) {
        // Clear the grid pane
        menu_gridPane_event.getChildren().clear();

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < filteredEvents.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/FrontFatma/cardEvent.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CardEvent itemController = fxmlLoader.getController();
                itemController.setData(filteredEvents.get(i));


                if (column == 3) {
                    column = 0;
                    row++;
                }

                menu_gridPane_event.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                menu_gridPane_event.setMinWidth(Region.USE_COMPUTED_SIZE);
                menu_gridPane_event.setPrefWidth(Region.USE_COMPUTED_SIZE);
                menu_gridPane_event.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                menu_gridPane_event.setMinHeight(Region.USE_COMPUTED_SIZE);
                menu_gridPane_event.setPrefHeight(Region.USE_COMPUTED_SIZE);
                menu_gridPane_event.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String weatherData = WeatherAPI.getWeather(36.8065, 10.1815); // Coordinates for Tunis
            weather_label.setText(weatherData);
        } catch (IOException e) {
            e.printStackTrace();
            weather_label.setText("Failed to fetch weather data");
        }
fetchUserData();
        List<Groupe> groupes = groupService.getAll();
        List<String> groupNames = new ArrayList<>();
        for (Groupe g : groupes) {
            groupNames.add(g.getName_group());
        }
        groupCB.setItems(FXCollections.observableList(groupNames));

        // Show all events on initialization
        updateUIWithFilteredEvents(serviceList);
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
