package esprit.nnn.controllers.Back;

import esprit.nnn.models.Event;
import esprit.nnn.services.EventService;
import esprit.nnn.services.GroupService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GetAllEvents {

    @FXML
    private TableColumn<Event, LocalDate> debutDateColumn;

    @FXML
    private TableColumn<Event, String>  descColumn;

    @FXML
    private TableColumn<Event, LocalDate> endDateColumn;

    @FXML
    private TableView<Event> eventTV;

    @FXML
    private TableColumn<Event, String> groupColumn;

    @FXML
    private TableColumn<Event, Integer> idColumn;

    @FXML
    private TableColumn<Event, String> nameColumn;

    EventService es=new EventService();
    GroupService gs=new GroupService();

    @FXML
    void initialize() {
        List<Event> events = es.getAll();
        List<String>groupes=new ArrayList<>();
        ObservableList<Event> observableList= FXCollections.observableList(events);
        eventTV.setItems(observableList);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("desc_event"));
        debutDateColumn.setCellValueFactory(new PropertyValueFactory<>("date_d"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("date_f"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("groupe_id"));
        eventTV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //bouton dans table view
        // Créez une colonne de table pour les boutons


    }

    @FXML
    void showEventFront(ActionEvent event) {

    }

}
