package tn.esprit.controllers.Quiz;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tn.esprit.entities.Quiz;
import tn.esprit.services.QuizService;

import java.io.IOException;

public class QuizcardController {

    @FXML
    private Button Details;

    @FXML
    private Label QuizName;

    @FXML
    private Label desc;


    private Quiz quiza;


    public void setData(Quiz quiz) {
        this.quiza = quiz;
        QuizName.setText(quiz.getQuiz_name());
        desc.setText(quiz.getDesc_quiz());


    }

    @FXML
    void supprimerServiceAction(ActionEvent event) {
        if(quiza != null) {
           QuizService serviceService = new QuizService();
            serviceService.Delete(quiza.getId());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le service a été supprimé avec succès.");
            alert.setTitle("Service supprimé");
            alert.show();

            try {
                // Reload the QuizList.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/Admin/QuizList.fxml"));
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
    void modifierServiceAction(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/Admin/QuizDetails.fxml"));
            Parent root = loader.load();


            QuizDetailsController modifierServiceController = loader.getController();


            modifierServiceController.setQuizToModify(quiza);

            Details.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }
}
