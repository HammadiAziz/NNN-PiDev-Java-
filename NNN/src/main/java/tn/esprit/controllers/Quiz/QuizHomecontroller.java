package tn.esprit.controllers.Quiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import tn.esprit.controllers.AfficherProduits;
import tn.esprit.controllers.AfficherQuiv;
import tn.esprit.controllers.BackFatma.GetAllEvents;
import tn.esprit.controllers.BackFatma.GetAllGroups;
import tn.esprit.controllers.ReclamationControllers.Admin.AddRep;
import tn.esprit.controllers.UserControllers.DashboardController;

import java.io.IOException;

public class QuizHomecontroller {


    @FXML
    private VBox patient;


    @FXML
    void QuizList(MouseEvent event) {


        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/Admin/QuizList.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
            QuizListcontroller Listcontroller = loader.getController();
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
    void QuizADD(MouseEvent event) {



        try {
            // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/Admin/Quiz.fxml"));
            Parent root = loader.load();
            // Obtenez le contrôleur de la nouvelle page
           QuizshController controller = loader.getController();
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
        void gotoevent(ActionEvent event) {



            try {
                // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFATMA/GetAllEvents.fxml"));
                Parent root = loader.load();
                // Obtenez le contrôleur de la nouvelle page
                GetAllEvents Listcontroller = loader.getController();
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
        void gotogroup(ActionEvent event) {

            try {
                // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFATMA/GetAllGroups.fxml"));
                Parent root = loader.load();
                // Obtenez le contrôleur de la nouvelle page
                GetAllGroups Listcontroller = loader.getController();
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
        void gotolivraison(ActionEvent event) {
            try {
                // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSuiv.fxml"));
                Parent root = loader.load();
                // Obtenez le contrôleur de la nouvelle page
               AfficherQuiv Listcontroller = loader.getController();
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
        void gotomarket(ActionEvent event) {
            try {
                // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
                Parent root = loader.load();
                // Obtenez le contrôleur de la nouvelle page
                AfficherProduits Listcontroller = loader.getController();
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
        void gotoquiz(ActionEvent event) {
            try {
                // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/Admin/QuizHome.fxml"));
                Parent root = loader.load();
                // Obtenez le contrôleur de la nouvelle page
                QuizHomecontroller Listcontroller = loader.getController();
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
        void gotoreclamation(ActionEvent event) {

            try {
                // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Reclamation/Admin/AddRep.fxml"));
                Parent root = loader.load();
                // Obtenez le contrôleur de la nouvelle page
                AddRep Listcontroller = loader.getController();
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
        void gotouser(ActionEvent event) {
            try {
                // Utilisez FXMLLoader pour charger le fichier FXML de la nouvelle page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardGUI.fxml"));
                Parent root = loader.load();
                // Obtenez le contrôleur de la nouvelle page
                DashboardController Listcontroller = loader.getController();
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



