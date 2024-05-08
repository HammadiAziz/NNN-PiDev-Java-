package esprit.nnn.controllers.Front;

import esprit.nnn.models.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

public class CardEvent {

    @FXML
    private Label Event_desc;

    @FXML
    private HBox desHbox;

    @FXML
    private Label event_debut_date;

    @FXML
    private Label event_end_date;

    @FXML
    private Label event_name;


    private Event event;
    private String prod_image;
    private int group_id;
    private Image image;
    private double pr;

    public void setData(Event event) {
        this.event = event;
        event_name.setText(event.getNom());
        Event_desc.setText(event.getDesc_event());
        event_debut_date.setText(event.getDate_d().toString());
        event_end_date.setText(event.getDate_f().toString());

    }

}