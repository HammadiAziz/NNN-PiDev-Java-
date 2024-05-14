package tn.esprit.controllers.Question;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.entities.Questions;
import tn.esprit.services.QuestionsService;

import java.io.IOException;

public class QuestionCardController {

    @FXML
    private Label correct;

    @FXML
    private Label text;

    private Questions questa;

    public void setData(Questions quest) {
        this.questa = quest;
        correct.setText(questa.getCorrect());
       text.setText(questa.getText());


    }





    @FXML
    void delete(ActionEvent event) {
        if(questa != null) {
            QuestionsService serviceService = new QuestionsService();
            serviceService.Delete(questa.getId());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(String.valueOf(questa.getId()));
            alert.setTitle("Question deleted");
            alert.show();

            try {
                // Reload the QuizList.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/esprit/nnn/FXML/Admin/QuizList.fxml"));
                Parent root = loader.load();

                // Get the scene from the current event
                Scene scene = ((Node) event.getSource()).getScene();

                // Set the new scene
                scene.setRoot(root);
            } catch (IOException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Une erreur s'est produite lors du rechargement de la page.");
                errorAlert.setTitle("Erreur de rechargement de la page");
                errorAlert.show();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Impossible de supprimer le service car aucun service n'est sélectionné.");
            errorAlert.setTitle("Erreur de suppression");
            errorAlert.show();
        }
    }



    @FXML
    void update(ActionEvent event) {


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/esprit/nnn/FXML/Admin/QuestionDetails.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            QuestionDetailsController Listcontroller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement

            Stage newStage = new Stage();
            newStage.setTitle("Question detail"); // Set the title of the new window
            newStage.setScene(new Scene(root)); // Set the scene with the content loaded from the FXML file
            Listcontroller.setQuesToModify(questa);
            // Show the new window
            newStage.show();



        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }


}
