package esprit.nnn.controllers.Front;

import esprit.nnn.models.Like;
import esprit.nnn.models.Publication;
import esprit.nnn.services.LikeService;
import esprit.nnn.services.PublicationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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

    @FXML
    private Button dislikeBtn;

    @FXML
    private Button likeBtn;


    private Publication publication;
    private String prod_image;
    private Image image;
    private double pr;

    private ShowGroupDetails parentController;

    @FXML
    void dislike(ActionEvent event) {

    }
    

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
   void like(ActionEvent event) {
       LikeService likeService = new LikeService();

       // Retrieve the Like corresponding to the current publication if it exists
       Like existingLike = likeService.getOneByPublicationId(publication.getId());

       if (existingLike != null) {
           // If a Like exists, delete it
           likeService.delete(existingLike);
           System.out.println("Like deleted");
       } else {
           // Otherwise, create a new Like
           Like newLike = new Like(publication.getId());
           likeService.add(newLike);
           System.out.println("Like added");
       }

       // Update the like state in the publication
       publication.updateLikeState(existingLike == null);
   }

    @FXML
    void DetailsPublication(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/esprit/nnn/Front/DetailsPublication.fxml"));
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
