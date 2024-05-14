package tn.esprit.controllers.BackFatma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.entities.Event;

public class UpdateEvent {

    @FXML
    private TextField IdDisabledTF;


    @FXML
    private TextArea descUpdatedTA;

    @FXML
    private TextField nameUpdatedTF;
    @FXML
    private DatePicker debutDP;

    @FXML
    private DatePicker endDP;





    @FXML
    void goToAllEvents(ActionEvent event) {

    }

    @FXML
    void goToAllGroups(ActionEvent event) {


    }

    @FXML
    void updateEvent(ActionEvent event) {


    }
    public void setEventData(Event event) {
        nameUpdatedTF.setText(event.getNom());
        descUpdatedTA.setText(event.getDesc_event());
        debutDP.setValue(event.getDate_d());
        endDP.setValue(event.getDate_f());

        IdDisabledTF.setText(String.valueOf(event.getGroupe_id()));
    }

}
