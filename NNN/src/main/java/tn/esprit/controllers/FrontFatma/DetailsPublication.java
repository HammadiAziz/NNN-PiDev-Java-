package tn.esprit.controllers.FrontFatma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.entities.Publication;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailsPublication implements Initializable {

    private Publication publication;

    @FXML
    private ImageView image_publication;

    @FXML
    private Label publication_date;

    @FXML
    private Label publication_desc;

    @FXML
    private Label publication_title;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    void returnToGroups(ActionEvent event) {

        // Handle return to groups action
    }
    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public void populateFields() {
        if (publication != null) {
            publication_title.setText(publication.getPost_title());
            publication_desc.setText(publication.getPost_desc());
            publication_date.setText(publication.getDate_post().toString());

            if (publication.getImage_post() != null && !publication.getImage_post().isEmpty()) {
                try {
                    Image image = new Image(publication.getImage_post());
                    image_publication.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                    image_publication.setImage(null); // Handle image loading error
                }
            } else {
                image_publication.setImage(null); // Handle missing image URL
            }
        }
    }
}
