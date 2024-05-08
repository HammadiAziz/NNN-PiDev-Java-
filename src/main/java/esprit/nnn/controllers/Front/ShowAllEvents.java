package esprit.nnn.controllers.Front;

import esprit.nnn.models.Event;
import esprit.nnn.models.Groupe;
import esprit.nnn.services.EventService;
import esprit.nnn.services.GroupService;
import esprit.nnn.util.WeatherAPI;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.control.ComboBox;

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
                fxmlLoader.setLocation(getClass().getResource("/esprit/nnn/Front/cardEvent.fxml"));
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
                fxmlLoader.setLocation(getClass().getResource("/esprit/nnn/Front/cardEvent.fxml"));
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
    @FXML
    void viewStatsClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/esprit/nnn/FXML/StatsEvents.fxml"));
            AnchorPane root = fxmlLoader.load();
            // You may need to pass any necessary data to the StatsEvent controller here

            // Create a new scene
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            // Get the stage from the event source
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            // Set the new scene
            stage.setScene(scene);
            // Show the stage
            stage.show();
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

        List<Groupe> groupes = groupService.getAll();
        List<String> groupNames = new ArrayList<>();
        for (Groupe g : groupes) {
            groupNames.add(g.getName_group());
        }
        groupCB.setItems(FXCollections.observableList(groupNames));

        // Show all events on initialization
        updateUIWithFilteredEvents(serviceList);
    }

}
