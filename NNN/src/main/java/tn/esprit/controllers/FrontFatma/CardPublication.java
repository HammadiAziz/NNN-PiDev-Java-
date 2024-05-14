package tn.esprit.controllers.FrontFatma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.entities.Like;
import tn.esprit.entities.Publication;
import tn.esprit.services.LikeService;
import tn.esprit.services.PublicationService;

import java.io.IOException;


public class CardPublication {

    @FXML
    private Label publication_date;

    @FXML
    private Label publication_description;

    @FXML
    private ImageView publication_image;

    @FXML
    private Label publication_title;


    private Publication publication;
    private String prod_image;
    private Image image;
    private double pr;

    private ShowGroupDetails parentController;


    

    public void setData(Publication publication) {
        this.publication = publication;
        publication_title.setText(publication.getPost_title());
        publication_description.setText(publication.getPost_desc());
        publication_date.setText(publication.getDate_post().toString());

        if (publication.getImage_post() != null && !publication.getImage_post().isEmpty()) {
            try {
                Image image = new Image(publication.getImage_post());
                publication_image.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to load image");
                alert.setContentText("An error occurred while loading the image.");
                alert.showAndWait();
            }
        }

    }


    PublicationService ps = new PublicationService();
    LikeService ls = new LikeService();

   /* @FXML
    void like(ActionEvent event) {
        int id_publication = publication.getId();
        LikePublication lp = null;
        if(publication.likes!=null){
            System.out.println(ls.getOne(1));
        }
        else
        {
            LikePublication lp1 = new LikePublication(publication.getId());
            ls.add(lp1);

        }

    }
    */



    @FXML
    void DetailsPublication(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontFatma/DetailsPublication.fxml"));
            Parent root = loader.load();

            DetailsPublication controller = loader.getController();
            controller.setPublication(publication);
            controller.populateFields();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
